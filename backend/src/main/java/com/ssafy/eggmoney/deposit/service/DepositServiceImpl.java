package com.ssafy.eggmoney.deposit.service;

import com.ssafy.eggmoney.account.entity.AccountLogType;
import com.ssafy.eggmoney.account.service.AccountService;
import com.ssafy.eggmoney.deposit.dto.request.DepositCreateRequestDto;
import com.ssafy.eggmoney.deposit.dto.response.DeleteDepositResponseDto;
import com.ssafy.eggmoney.deposit.dto.response.DepositProductListResponseDto;
import com.ssafy.eggmoney.deposit.dto.response.DepositResponseDto;
import com.ssafy.eggmoney.deposit.dto.DepositProductDto;
import com.ssafy.eggmoney.deposit.entity.Deposit;
import com.ssafy.eggmoney.deposit.entity.DepositProduct;
import com.ssafy.eggmoney.deposit.entity.DepositStatus;
import com.ssafy.eggmoney.deposit.repository.DepositProductRepository;
import com.ssafy.eggmoney.deposit.repository.DepositRepository;
import com.ssafy.eggmoney.user.entity.User;
import com.ssafy.eggmoney.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class DepositServiceImpl implements DepositService {

    private final UserRepository userRepository;
    private final DepositRepository depositRepository;
    private final DepositProductRepository depositProductRepository;
    private final AccountService accountService;

    @Override
    @Transactional(readOnly = true)
    public List<DepositProductListResponseDto> getDepositProducts() {
        List<DepositProduct> productList = depositProductRepository.findAll();

        List<DepositProductListResponseDto> productListDto = productList.stream().map(
                (product) -> DepositProductListResponseDto.builder()
                        .productId(product.getId())
                        .productName(product.getProductName())
                        .depositDate(product.getDepositDate())
                        .depositRate(product.getDepositRate())
                        .build())
                .collect(Collectors.toList());

        log.info("예금 상품 리스트 조회");

        return productListDto;
    }

    @Override
    @Transactional
    public void createDeposit(DepositCreateRequestDto requestDto){
        User user = userRepository.findById(requestDto.getUserId()).orElse(null);
        DepositProduct depositProduct = depositProductRepository.findById(requestDto.getDepositProductId()).orElse(null);

        // 메인 계좌의 돈 깎여야함.
        accountService.updateAccount(AccountLogType.DEPOSIT, requestDto.getUserId(), -1 * requestDto.getDepositMoney());

        LocalDateTime expiration = LocalDateTime.now().plusMonths(depositProduct.getDepositDate());

        if(!user.getRole().equals("자녀")){
            // 에러발생
            log.error("예금 가입 권한이 없는 유저입니다.");
        }

        if(depositRepository.findByUserIdAndDepositStatus(requestDto.getUserId(), DepositStatus.AVAILABLE).isPresent()){
            // 에러발생
            log.error("이미 사용자가 예금상품을 가지고 있습니다.");
        }

        Deposit deposit = Deposit.builder()
                .user(user)
                .depositProduct(depositProduct)
                .expireDate(expiration)
                .depositMoney(requestDto.getDepositMoney())
                .depositStatus(DepositStatus.AVAILABLE)
                .build();

        Deposit savedDeposit = depositRepository.save(deposit);
        log.info("예금 생성 완료");
        // return 저장된 예금 정보?

    }


    @Override
    @Transactional(readOnly = true)
    public DepositResponseDto getDeposits(Long userId){
        Deposit deposit = depositRepository.findByUserIdAndDepositStatus(userId, DepositStatus.AVAILABLE).orElse(null);

        if(deposit == null){
            log.info("가입된 예금 상품이 없습니다.");
        }
        DepositProduct depositProduct = deposit.getDepositProduct();
        DepositProductDto depositProductDto = DepositProductDto.builder()
                .productId(depositProduct.getId())
                .productName(depositProduct.getProductName())
                .depositRate(depositProduct.getDepositRate())
                .depositDate(depositProduct.getDepositDate()).build();

        log.info("예금 조회");
        return DepositResponseDto.builder()
                .depositId(deposit.getId())
                .depositProduct(depositProductDto)
                .expireDate(deposit.getExpireDate())
                .depositMoney(deposit.getDepositMoney())
                .createdAt(deposit.getCreatedAt())
                .build();
    }


    // 예금 해지(만기일 이전 해지시 이율, 만기일 이후 해지시 이율 고려)
    @Override
    @Transactional
    public DeleteDepositResponseDto deleteDeposit(long depositId) {

        Deposit deposit = depositRepository.findByIdAndDepositStatus(depositId, DepositStatus.AVAILABLE).orElse(null);
        int expiredMoney;
        double interestMoney;
        if(deposit.getExpireDate().toLocalDate().isAfter(LocalDate.now())){
            interestMoney = deposit.getDepositMoney() * (deposit.getDepositProduct().getDepositRate() - 2.0) / 100 * deposit.getDepositProduct().getDepositDate() / 12;
            expiredMoney = deposit.getDepositMoney() + (int) interestMoney;
        }else{
            interestMoney = deposit.getDepositMoney() * deposit.getDepositProduct().getDepositRate() / 100 * deposit.getDepositProduct().getDepositDate() / 12;
            expiredMoney = deposit.getDepositMoney() + (int) interestMoney;
        }
        log.info("interestMoney: {}, expiredMoney: {}", interestMoney, expiredMoney);
        accountService.updateAccount(AccountLogType.DEPOSIT, deposit.getUser().getId(), expiredMoney);

        DeleteDepositResponseDto deleteResponseDto = DeleteDepositResponseDto.builder()
                .depositId(depositId)
                .depositMoney(deposit.getDepositMoney())
                .interestMoney(interestMoney)
                .expiredMoney(expiredMoney)
                .build();

        Deposit updatedDeposit = deposit.toBuilder()
                .depositStatus(DepositStatus.EXPIRED)
                .build();

        depositRepository.save(updatedDeposit);

        log.info("예금 계좌 삭제");

        return deleteResponseDto;
    }

    // 만료 체크하기(scheduler)
    @Override
    public List<Long> checkExpiredDeposit(){
        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = LocalDate.now().atTime(23, 59, 59);
        List<Long> depositIds = depositRepository.findIdByExpireDateBetween(start, end);

        return depositIds;
    }
}
