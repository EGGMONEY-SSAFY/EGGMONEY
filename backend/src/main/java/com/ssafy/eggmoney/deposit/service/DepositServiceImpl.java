package com.ssafy.eggmoney.deposit.service;

import com.ssafy.eggmoney.account.entity.AccountLogType;
import com.ssafy.eggmoney.account.service.AccountService;
import com.ssafy.eggmoney.common.exception.ErrorType;
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
import com.ssafy.eggmoney.notification.dto.request.NotificationRequest;
import com.ssafy.eggmoney.notification.entity.NotificationType;
import com.ssafy.eggmoney.notification.service.NotificationService;
import com.ssafy.eggmoney.user.entity.User;
import com.ssafy.eggmoney.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class DepositServiceImpl implements DepositService {

    private final DepositRepository depositRepository;
    private final DepositProductRepository depositProductRepository;
    private final AccountService accountService;
    private final NotificationService notificationService;

    @Override
    @Transactional(readOnly = true)
    public List<DepositProductListResponseDto> getDepositProducts() {
        List<DepositProduct> productList = depositProductRepository.findAllByOrderByDepositDate();

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
    @Transactional(readOnly = true)
    public DepositProductDto getDepositProduct(Long depositId) {
        Optional<DepositProduct> op = depositProductRepository.findById(depositId);
        DepositProduct product;
        if ( op.isPresent() ){
            product = op.get();
        }
        else {
            throw new NoSuchElementException("조회하려는 상품이 없습니다.");
        }
        return DepositProductDto.builder()
                .productId(depositId)
                .productName(product.getProductName())
                .depositRate(product.getDepositRate())
                .build();
    }

    @Override
    @Transactional
    public void createDeposit(DepositCreateRequestDto requestDto, User user){
        DepositProduct depositProduct = depositProductRepository.findById(requestDto.getDepositProductId()).orElseThrow(
                () -> new NoSuchElementException(ErrorType.NOT_FOUND_PRODUCT.toString())
        );

        // 메인 계좌의 돈 깎여야함.
        accountService.updateAccount(AccountLogType.DEPOSIT, requestDto.getUserId(), -1 * requestDto.getDepositMoney());

        LocalDateTime expiration = LocalDateTime.now().plusMonths(depositProduct.getDepositDate());

        if(!user.getRole().equals("자녀")){
            log.error("예금 가입 권한이 없는 유저입니다.");
            throw new AccessDeniedException(ErrorType.NOT_CREATED_ROLE.toString());
        }

        if(depositRepository.findByUserIdAndDepositStatus(requestDto.getUserId(), DepositStatus.AVAILABLE).isPresent()){
            log.error("이미 사용자가 예금상품을 가지고 있습니다.");
            throw new AccessDeniedException(ErrorType.NOT_CREATED_ACCOUNT.toString());
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


    // 예금 조회하기
    @Override
    @Transactional(readOnly = true)
    public DepositResponseDto getDeposits(Long userId){
        Deposit deposit = depositRepository.findByUserIdAndDepositStatus(userId, DepositStatus.AVAILABLE).orElseThrow(
                () -> new NoSuchElementException(ErrorType.NOT_FOUND_DEPOSIT.toString())
        );

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

        Deposit deposit = depositRepository.findByIdAndDepositStatus(depositId, DepositStatus.AVAILABLE).orElseThrow(
                () -> new NoSuchElementException(ErrorType.NOT_FOUND_DEPOSIT.toString())
        );
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

        // 만기 시 알림 보내기
        NotificationRequest notificationRequest = NotificationRequest.builder()
                .notificationType(NotificationType.예금만기)
                .message("예금이 만기에 도달하여 자동해지되었습니다.")
                .receiveUserId(updatedDeposit.getUser().getId())
                .build();
        notificationService.saveNotification(null, notificationRequest);

        log.info("예금 계좌 삭제 {}", depositId);

        return deleteResponseDto;
    }

    // 만료 체크하기(scheduler)
    @Override
    @Transactional(readOnly = true)
    public List<Long> checkExpiredDeposit(){
        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = LocalDate.now().atTime(23, 59, 59);
        List<Long> depositIds = depositRepository.findIdByExpireDateBetweenAndDepositStatus(start, end, DepositStatus.AVAILABLE);

        return depositIds;
    }


    // 만기일 3일 전, 알림 전송
    @Override
    public boolean expiredDepositNotification(){
        LocalDateTime start = LocalDate.now().plusDays(3).atStartOfDay();
        LocalDateTime end = LocalDate.now().plusDays(3).atTime(23, 59, 59);
        List<Deposit> deposits = depositRepository.findAllByExpireDateBetweenAndDepositStatus(start, end, DepositStatus.AVAILABLE);

        if(!deposits.isEmpty()){

            for(Deposit deposit : deposits){

                    log.info("depositId notification : {}", deposit.getId());
                }
        }else{
            return false;
        }

        return true;

    }
}
