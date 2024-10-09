package com.ssafy.eggmoney.deposit.scheduler;

import com.ssafy.eggmoney.deposit.service.DepositService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DepositScheduler {

    private final DepositService depositService;

    @Scheduled(cron ="0 0 12 * * ?")
    public void run(){
        log.info("{} 예금 만기 스케줄러 시작", LocalDate.now());
        List<Long> depositIds = depositService.checkExpiredDeposit();
        if(!depositIds.isEmpty()) {
            for (Long depositId : depositIds) {
                log.info("예금 만기 계좌번호 : {}", depositId);
                depositService.deleteDeposit(depositId);
            }
        }else{
            log.info("예금 만기 계좌가 없습니다.");
        }

        log.info("예금 만기 스케줄러 종료");
    }

    @Scheduled(cron = "0 0 12 * * ?")
    public void run2() {
        log.info("예금 만기알림 스케줄러 시작");
        if (depositService.expiredDepositNotification()){
            log.info("예금 만기 알림 전송");
        }else{
            log.info("만기 계좌 없습니다.");
        }
        log.info("예금 만기알림 스케줄러 종료");
;
    }

}
