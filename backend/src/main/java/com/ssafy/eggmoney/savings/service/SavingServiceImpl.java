package com.ssafy.eggmoney.savings.service;

import com.ssafy.eggmoney.account.entity.AccountLogType;
import com.ssafy.eggmoney.account.service.AccountService;
import com.ssafy.eggmoney.common.exception.ErrorType;
import com.ssafy.eggmoney.notification.dto.request.NotificationRequest;
import com.ssafy.eggmoney.notification.entity.NotificationType;
import com.ssafy.eggmoney.notification.service.NotificationService;
import com.ssafy.eggmoney.savings.dto.request.SavingsCreateRequestDto;
import com.ssafy.eggmoney.savings.dto.response.SavingsDeleteResponseDto;
import com.ssafy.eggmoney.savings.dto.response.SavingsLogResponseDto;
import com.ssafy.eggmoney.savings.dto.response.SavingsProductListResponseDto;
import com.ssafy.eggmoney.savings.dto.response.SavingsResponseDto;
import com.ssafy.eggmoney.savings.entity.Savings;
import com.ssafy.eggmoney.savings.entity.SavingsLog;
import com.ssafy.eggmoney.savings.entity.SavingsProduct;
import com.ssafy.eggmoney.savings.entity.SavingsStatus;
import com.ssafy.eggmoney.savings.repository.SavingsLogRepository;
import com.ssafy.eggmoney.savings.repository.SavingsProductRepository;
import com.ssafy.eggmoney.savings.repository.SavingsRepository;
import com.ssafy.eggmoney.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SavingServiceImpl implements SavingService {
    private final SavingsRepository savingsRepository;
    private final SavingsProductRepository savingsProductRepository;
    private final SavingsLogRepository savingsLogRepository;
    private final AccountService accountService;
    private final NotificationService notificationService;


    // 적금 상품 전체 조회하기
    @Override
    @Transactional(readOnly = true)
    public List<SavingsProductListResponseDto> getSavingProducts() {
        List<SavingsProduct> productList =  savingsProductRepository.findAll();

        List<SavingsProductListResponseDto> productListDto = productList.stream().map(
                (product) -> SavingsProductListResponseDto.builder()
                        .id(product.getId())
                        .productName(product.getProductName())
                        .savingsRate(product.getSavingsRate())
                        .savingsDate(product.getSavingsDate())
                        .maxPrice(product.getMaxPrice())
                        .build()
                ).collect(Collectors.toList());

        log.info("적금 상품 리스트 조회");
        return productListDto;
    }

    // 적금 생성하기
    @Override
    @Transactional
    public void createSaving(SavingsCreateRequestDto requestDto, User user){

        SavingsProduct savingsProduct = savingsProductRepository.findById(requestDto.getSavingsProductId()).orElseThrow(
                () -> new NoSuchElementException(ErrorType.NOT_FOUND_PRODUCT.toString())
        );

        if(!user.getRole().equals("자녀")){
            log.error("적금 가입 권한이 없는 유저입니다.");
            throw new AccessDeniedException(ErrorType.NOT_CREATED_ROLE.toString());
        }

        if(savingsRepository.findByUserIdAndSavingsStatus(user.getId(), SavingsStatus.AVAILABLE).isPresent()){
            log.error("이미 사용자가 적금상품을 가지고 있습니다.");
            throw new AccessDeniedException(ErrorType.NOT_CREATED_ACCOUNT.toString());
        }

        if(savingsProduct.getMaxPrice() < requestDto.getPaymentMoney() * savingsProduct.getSavingsDate()){
            log.error("적금 최대 납입 금액을 넘는 값입니다.");
            throw new IllegalArgumentException(ErrorType.EXCEED_MAX_PRICE.toString());
        }

        Savings savings = Savings.builder()
                .user(user)
                .savingsProduct(savingsProduct)
                .expireDate(LocalDateTime.now().plusMonths(savingsProduct.getSavingsDate()))
                .paymentDate(savingsProduct.getSavingsDate() - 1) // 적금 생성 시 1회 납부를 하기 때문에
                .balance(requestDto.getPaymentMoney())
                .paymentMoney(requestDto.getPaymentMoney())
                .savingsStatus(SavingsStatus.AVAILABLE)
                .build();

        savingsRepository.save(savings);

        accountService.updateAccount(AccountLogType.SAVINGS, user.getId(), -1 * requestDto.getPaymentMoney());

        log.info("적금 생성");

        SavingsLog savingsLog = SavingsLog.builder()
                .savings(savings)
                .balance(savings.getBalance())
                .build();

        savingsLogRepository.save(savingsLog);

        log.info("적금 로그 저장");
    }

    // 개인 적금 조회하기
    @Override
    @Transactional(readOnly = true)
    public SavingsResponseDto getSavings(Long userId){
        Savings savings = savingsRepository.findByUserIdAndSavingsStatus(userId, SavingsStatus.AVAILABLE).orElseThrow(
                () -> new NoSuchElementException(ErrorType.NOT_FOUND_SAVINGS.toString())
        );

        log.info("개인 적금 조회 성공");
        return SavingsResponseDto.builder()
                .savingsId(savings.getId())
                .savingsRate(savings.getSavingsProduct().getSavingsRate())
                .savingsDate(savings.getSavingsProduct().getSavingsDate())
                .productName(savings.getSavingsProduct().getProductName())
                .balance(savings.getBalance())
                .expireDate(savings.getExpireDate())
                .createdAt(savings.getCreatedAt())
                .paymentDate(savings.getPaymentDate())
                .paymentMoney(savings.getPaymentMoney())
                .build();
    }

    // 적금 납부하기
    @Override
    @Transactional
    public void sendSavings(Long userId){
        Savings savings = savingsRepository.findByUserIdAndSavingsStatus(userId, SavingsStatus.AVAILABLE).orElseThrow(
                () -> new NoSuchElementException(ErrorType.NOT_FOUND_SAVINGS.toString())
        );
        log.info("Savings userId : {}" , userId);
        log.info("savings saviongs.getPaymentDate : {}", savings.getPaymentDate());

        // 메인계좌에서 돈 빼오기
        accountService.updateAccount(AccountLogType.SAVINGS, userId, -1 * savings.getPaymentMoney());

        // Todo : 메인계좌 로그도 넣어야하나?


        if(savings.getPaymentDate() <= 0){
            log.error("적금을 모두 납부하셨습니다.");
            throw new IllegalArgumentException(ErrorType.ALREADY_PAY_SAVINGS.toString());
        }
        Savings updateSavings = savings.toBuilder()
                .balance(savings.getPaymentMoney() + savings.getBalance())
                .paymentDate(savings.getPaymentDate() - 1)
                .build();

        savingsRepository.save(updateSavings);

        SavingsLog savingsLog = SavingsLog.builder()
                .savings(savings)
                .balance(updateSavings.getBalance())
                .build();

        savingsLogRepository.save(savingsLog);
        log.info("적금 납부하기 성공");
    }

    // 적금 로그 조회
    @Override
    @Transactional(readOnly = true)
    public List<SavingsLogResponseDto> getSavingsLogs(Long savingsId) {

        List<SavingsLog> savingsLogs = savingsLogRepository.findAllBySavingsIdOrderByCreatedAtDesc(savingsId);

        List<SavingsLogResponseDto> logDto = savingsLogs.stream().map(
                (savingsLog) -> SavingsLogResponseDto.builder()
                        .paymentMoney(savingsLog.getSavings().getPaymentMoney())
                        .balance(savingsLog.getBalance())
                        .createdAt(savingsLog.getCreatedAt())
                        .build()
        ).collect(Collectors.toList());

        log.info("적금로그 조회 성공");
        return logDto;
    }

    // 적금 삭제하기
    @Override
    @Transactional
    public SavingsDeleteResponseDto deleteSavings(Long savingsId) {

        Savings savings = savingsRepository.findByIdAndSavingsStatus(savingsId, SavingsStatus.AVAILABLE).orElseThrow(
                () -> new NoSuchElementException(ErrorType.NOT_FOUND_SAVINGS.toString())
        );

        double interest;
        int paymentDate;
        int expiredMoney;
        double interestMoney = 0;
        String notificationMessage ="적금이 만기에 도달하여 자동해지 되었습니다.";
        if(savings.getExpireDate().toLocalDate().isAfter(LocalDate.now())){
            interest = (savings.getSavingsProduct().getSavingsRate() - 2.0);
            paymentDate = savings.getSavingsProduct().getSavingsDate() - savings.getPaymentDate();
            notificationMessage = "적금이 해지되었습니다.";
        }else{
            interest= savings.getSavingsProduct().getSavingsRate();
            paymentDate = savings.getSavingsProduct().getSavingsDate();
        }

        for(int i = 1; i <= paymentDate; i++){
            interestMoney += Math.round(savings.getPaymentMoney() * (interest / 100 * i / 12));
            log.info("interestMoney : {}", interestMoney);
        }

        expiredMoney = savings.getBalance() + (int) interestMoney;
        log.info("interestMoney: {}, expiredMoney: {}", interestMoney, expiredMoney);

        accountService.updateAccount(AccountLogType.SAVINGS, savings.getUser().getId(), expiredMoney);

        Savings updateSavings = savings.toBuilder()
                .savingsStatus(SavingsStatus.EXPIRED)
                .build();

        savingsRepository.save(updateSavings);
        log.info("적금 해지 성공 : {}", savingsId);

        SavingsDeleteResponseDto deleteResponseDto = SavingsDeleteResponseDto.builder()
                .savingsId(savings.getId())
                .interestMoney(interestMoney)
                .expiredMoney(expiredMoney)
                .paymentDate(savings.getSavingsProduct().getSavingsDate())
                .build();


        NotificationRequest notificationRequest = NotificationRequest.builder()
                .notificationType(NotificationType.적금만기)
                .message(notificationMessage)
                .receiveUserId(updateSavings.getUser().getId())
                .build();
        notificationService.saveNotification(null, notificationRequest);

        return deleteResponseDto;
    }

    // 만기일 된 통장 확인(scheduler)
    @Override
    public List<Long> checkExpiredSavings() {
        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = LocalDate.now().atTime(23, 59, 59);
        List<Long> savingsIds = savingsRepository.findIdByExpireDateBetweenAndSavingsStatus(start, end, SavingsStatus.AVAILABLE);

        return savingsIds;
    }

    // 매달 적금 넣었는지 확인하기
    @Override
    public List<Long> checkingPayId(){
        List<Long> savingsIds = savingsRepository.findIdBySavingsStatusAndPaymentDateNot(SavingsStatus.AVAILABLE, 0);
        if(!savingsIds.isEmpty()){
            LocalDateTime start = LocalDate.now().minusMonths(1).withDayOfMonth(1).atStartOfDay();
            LocalDateTime end = LocalDate.now().minusDays(1).atTime(23, 59, 59);
            List<Long> sendIds = savingsLogRepository.findSavingsIdByCreatedAtBetween(start, end);
            savingsIds.removeAll(sendIds);
        }else{
            log.info("미납 계좌가 없습니다.");
        }

        return savingsIds;

    }

    @Override
    public boolean sendSavingsNotification(){
        List<Long> userIds = savingsRepository.findUserIdBySavingsStatusAndPaymentDateNot(SavingsStatus.AVAILABLE, 0);
        if(!userIds.isEmpty()){
            LocalDateTime start = LocalDate.now().withDayOfMonth(1).atStartOfDay();
            LocalDateTime end = LocalDateTime.now();
            List<Long> sendIds = savingsLogRepository.findUserIdByCreatedAtBetween(start, end);
            userIds.removeAll(sendIds);
        }else{
            log.info("미납 계좌가 없습니다.");
            return true;
        }
        if(!userIds.isEmpty()){

            for(Long userId : userIds){
                log.info("userId {} : ", userId);
                NotificationRequest notificationRequest = NotificationRequest.builder()
                        .receiveUserId(userId)
                        .message("이번 달 적금을 납부하지 않으셨습니다! \n미납시 만기일이 한달 연장됩니다.")
                        .notificationType(NotificationType.적금납부)
                        .build();
                notificationService.saveNotification(null, notificationRequest );
            }

            return true;
        }
        return false;
    }



    // 적금 납부 안한 계좌의 만기일 + 1 하기
    @Override
    public void plusExpired(List<Long> savingsId){

        savingsRepository.extendSavingsExpireDateByOneMonth(savingsId);
        log.info("적금 미납자 계좌 만기일 연장");
    }
}
