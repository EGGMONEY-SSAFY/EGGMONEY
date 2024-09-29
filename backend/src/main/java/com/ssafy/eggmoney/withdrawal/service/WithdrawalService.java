package com.ssafy.eggmoney.withdrawal.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.ssafy.eggmoney.account.entity.Account;
import com.ssafy.eggmoney.account.entity.AccountLogType;
import com.ssafy.eggmoney.account.repository.AccountRepository;
import com.ssafy.eggmoney.account.service.AccountService;
import com.ssafy.eggmoney.common.exception.ErrorType;
import com.ssafy.eggmoney.common.webClient.ApiClient;
import com.ssafy.eggmoney.family.dto.response.GetFamilyResponseDto;
import com.ssafy.eggmoney.family.entity.Family;
import com.ssafy.eggmoney.user.dto.response.GetUserResponseDto;
import com.ssafy.eggmoney.user.entity.User;
import com.ssafy.eggmoney.user.repository.UserRepository;
import com.ssafy.eggmoney.withdrawal.dto.request.CreateWithdrawalRequestDto;
import com.ssafy.eggmoney.withdrawal.dto.request.JudgeWithdrawalRequestDto;
import com.ssafy.eggmoney.withdrawal.dto.response.GetWithdrawalResponseDto;
import com.ssafy.eggmoney.withdrawal.entity.Withdrawal;
import com.ssafy.eggmoney.withdrawal.entity.WithdrawalStatus;
import com.ssafy.eggmoney.withdrawal.repository.WithdrawalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class WithdrawalService {
    private final WithdrawalRepository withdrawalRepository;
    private final UserRepository userRepository;
    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final ApiClient apiClient;

    private BigInteger institutionTransactionUniqueNo = new BigInteger("0");

//  출금요청 로그 조회
    public List<GetWithdrawalResponseDto> getWithdrawalLogs(Long userId){
        User child = userRepository.findById(userId)
                .orElseThrow( () -> new NoSuchElementException(ErrorType.NOT_FOUND_CHILD.toString()));
        User parent = userRepository.findById(child.getFamily().getPresentId())
                .orElseThrow( () -> new NoSuchElementException(ErrorType.NOT_FOUND_PARENT.toString()));
        List<Withdrawal> lst = withdrawalRepository.findLogsByUserId(child.getId());
        Family childFam = child.getFamily();
        Family parentFam = parent.getFamily();

        List<GetWithdrawalResponseDto> logs = lst.stream()
                .map( withdrawal -> GetWithdrawalResponseDto.builder()
                    .withdrawalId(withdrawal.getId())
                    .applyer(
                            GetUserResponseDto.builder()
                                    .userId(child.getId())
                                    .email(child.getEmail())
                                    .role(child.getRole())
                                    .family(
                                            GetFamilyResponseDto.builder()
                                                    .presentId(parentFam.getPresentId())
                                                    .intro(parentFam.getIntro())
                                                    .qrcode(parentFam.getQrCode())
                                                    .build()
                                    )
                                    .bank(child.getBank())
                                    .pwd(child.getSimplePwd())
                                    .realAccount(child.getRealAccount())
                                    .name(child.getName())
                                    .stockRatio(child.getStockRatio())
                                    .build()
                    )
                    .applyee(
                            GetUserResponseDto.builder()
                            .userId(parent.getId())
                            .email(parent.getEmail())
                            .family(
                                    GetFamilyResponseDto.builder()
                                        .presentId(childFam.getPresentId())
                                        .intro(childFam.getIntro())
                                        .qrcode(childFam.getQrCode())
                                    .build())
                            .role(parent.getRole())
                            .bank(parent.getBank())
                            .pwd(parent.getSimplePwd())
                            .realAccount(parent.getRealAccount())
                            .name(parent.getName())
                            .stockRatio(parent.getStockRatio())
                            .build())
                    .withdrawalPrice(withdrawal.getWithdrawalPrice())
                    .type(withdrawal.getWithdrawalStatus())
                    .createdAt(withdrawal.getCreatedAt())
                    .updatedAt(withdrawal.getUpdatedAt())
                .build()
                )
                .collect(Collectors.toList());

        return logs;
    }

//  출금요청 생성
    public void createWithdrawal(CreateWithdrawalRequestDto dto){
        User user = userRepository.findById(dto.getUserId()).get();

        Account account = accountRepository.findByUserId(user.getId()).get();

        System.out.println("Acc Bal : "+account.getBalance()+"/ dto Bal"+dto.getPrice());
//        예외처리 : 계좌에 출금요청할 돈 있어야 함
        if ( account.getBalance() - dto.getPrice() < 0 ) {
            throw new IllegalArgumentException(ErrorType.NOT_ENOUGH_MONEY.toString());
        }
        
        withdrawalRepository.save(
                Withdrawal.builder()
                    .user(user)
                    .withdrawalStatus(WithdrawalStatus.PROGRESS)
                    .withdrawalPrice(dto.getPrice())
                .build()
        );

    }

//    출금 심사
    public void judgeWithdrawal(Long Wid, JudgeWithdrawalRequestDto dto){
        Withdrawal with = withdrawalRepository.findById(Wid)
                .orElseThrow( () -> new NoSuchElementException(ErrorType.NOT_FOUND_WITHDRAWAL.toString()));
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow( () -> new NoSuchElementException(ErrorType.NOT_FOUND_UESR.toString()));
        String judge = dto.getJudge();

//        예외처리: 부모가 아닌데 심사하려는 경우
        if ( dto.getUserId() != with.getUser().getFamily().getPresentId() ) {
            throw new IllegalArgumentException(ErrorType.NOT_PRESENT_USER.toString());
        }
//        예외처리: judge가 null값
        if ( judge == null )
            throw new NoSuchElementException(ErrorType.IS_ILLEGAL_REQUEST.toString());

//        예외처리: 이미 심사가 이루어진 상태
        if ( with.getWithdrawalStatus() != WithdrawalStatus.PROGRESS ) {
            throw new IllegalArgumentException(ErrorType.WITH_ALREADY_JUDGED.toString());
        }

//      대출 승인
        if (judge.equals("승인")) {
            // UserKey 조회
            apiClient.findUserKey(user.getEmail())
                    .flatMap( userKeyResponse -> {
                        HashMap<String, Object> responseMap = apiClient.parseResponse(userKeyResponse, new TypeReference<HashMap<String, Object>>() {});
                        String userKey = String.valueOf(responseMap.get("userKey"));
                        institutionTransactionUniqueNo = institutionTransactionUniqueNo.add(BigInteger.ONE);

                        // 계좌 잔고 조회
                        String transactionUniqueNo = apiClient.generateTransactionUniqueNo(institutionTransactionUniqueNo);

                        return apiClient.getAccountBalance(userKey, transactionUniqueNo)
                                .flatMap( accountBalanceResponse ->
                                {
                                    HashMap<String, Object> balanceResponseMap = apiClient.parseResponse(accountBalanceResponse, new TypeReference<HashMap<String, Object>>() {});
                                    List<HashMap<String, Object>> recList = (List<HashMap<String, Object>>) balanceResponseMap.get("REC");
                                    HashMap<String, Object> innerMap = recList.get(0);

                                    // 잔고 확인 후 처리
                                    if (Integer.parseInt(String.valueOf(innerMap.get("accountBalance"))) < with.getWithdrawalPrice()) {
                                        throw new IllegalArgumentException(ErrorType.NOT_ENOUGH_MONEY.toString());
                                    }

                                    // 잔고가 충분하면 계좌 이체 수행
                                    String childAccount = with.getUser().getRealAccount();
                                    String parentAccount = user.getRealAccount();
                                    return apiClient.transferAccount(userKey, childAccount, parentAccount, transactionUniqueNo, with.getWithdrawalPrice());
                                });
                    })
                    .subscribe( transferResponse -> {
                        // 대출 인스턴스 상태 변경 및 계좌 로그 갱신
                        with.setWithdrawalStatus(WithdrawalStatus.APPROVAL);
                        accountService.updateAccount(AccountLogType.WITHDRAWAL, with.getUser().getId(), -with.getWithdrawalPrice());
                        withdrawalRepository.save(with);
                    }, error -> {
                        throw new IllegalArgumentException(ErrorType.API_NETWORK_ERROR.toString());
                    });
        }

//      거절
        else {
            with.setWithdrawalStatus(WithdrawalStatus.REFUSAL);
        }
    }

}
