package com.ssafy.eggmoney.withdrawal.service;

import com.ssafy.eggmoney.account.entity.AccountLogType;
import com.ssafy.eggmoney.account.service.AccountService;
import com.ssafy.eggmoney.family.repository.FamilyRepository;
import com.ssafy.eggmoney.user.dto.response.GetUserResponseDto;
import com.ssafy.eggmoney.user.entity.User;
import com.ssafy.eggmoney.user.repository.UserRepository;
import com.ssafy.eggmoney.withdrawal.dto.request.CreateWithdrawalRequestDto;
import com.ssafy.eggmoney.withdrawal.dto.request.GetWithdrawalRequestDto;
import com.ssafy.eggmoney.withdrawal.dto.request.JudgeWithdrawalRequestDto;
import com.ssafy.eggmoney.withdrawal.dto.response.GetWithdrawalResponseDto;
import com.ssafy.eggmoney.withdrawal.entity.Withdrawal;
import com.ssafy.eggmoney.withdrawal.entity.WithdrawalType;
import com.ssafy.eggmoney.withdrawal.repository.WithdrawalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class WithdrawalService {
    private final WithdrawalRepository withdrawalRepository;
    private final UserRepository userRepository;
    private final FamilyRepository familyRepository;
    private final AccountService accountService;

//  출금요청 로그 조회
    public List<GetWithdrawalResponseDto> getWithdrawalLogs(GetWithdrawalRequestDto dto){
        User child = userRepository.findById(dto.getUserId()).get();
        User parent = userRepository.findById(child.getFamily().getPresentId()).get();
        List<Withdrawal> lst = withdrawalRepository.findLogsByUserId(child.getId());

        List<GetWithdrawalResponseDto> logs = lst.stream()
                .map( withdrawal -> GetWithdrawalResponseDto.builder()
                    .applyer(
                            GetUserResponseDto.builder()
                                    .email(child.getEmail())
                                    .role(child.getRole())
                                    .bank(child.getBank())
                                    .pwd(child.getPwd())
                                    .realAccount(child.getRealAccount())
                                    .name(child.getName())
                                    .stockRatio(child.getStockRatio())
                                    .build()
                    )
                    .applyee(
                            GetUserResponseDto.builder()
                            .email(parent.getEmail())
                            .role(parent.getRole())
                            .bank(parent.getBank())
                            .pwd(parent.getPwd())
                            .realAccount(parent.getRealAccount())
                            .name(parent.getName())
                            .stockRatio(parent.getStockRatio())
                            .build())
                    .withdrawalPrice(withdrawal.getWithdrawalPrice())
                    .type(withdrawal.getWithdrawalStatus())
                .build()
                )
                .collect(Collectors.toList());

        return logs;
    }

//  출금요청 생성
    public void createWithdrawal(CreateWithdrawalRequestDto dto){
        User user = userRepository.findById(dto.getUserId()).get();
        withdrawalRepository.save(
                Withdrawal.builder()
                    .user(user)
                    .withdrawalStatus(WithdrawalType.CHECK)
                    .withdrawalPrice(dto.getPrice())
                .build()
        );

    }

//    출금 심사
    public void judgeWithdrawal(Long Wid, JudgeWithdrawalRequestDto dto){
        Withdrawal with = withdrawalRepository.findById(Wid).get();
        String judge = dto.getJudge();

        if ( judge.equals("승인") ) {
            with.setWithdrawalStatus(WithdrawalType.ALLOW);
            accountService.updateAccount(AccountLogType.WITHDRAWL_APPLY, with.getUser().getId(), -with.getWithdrawalPrice());
        }
//        거절
        else {
            with.setWithdrawalStatus(WithdrawalType.REJECT);
        }
    }

}
