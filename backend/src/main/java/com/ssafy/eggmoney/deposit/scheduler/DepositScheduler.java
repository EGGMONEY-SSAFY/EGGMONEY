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
        log.info("{} 스케줄러 시작", LocalDate.now());
        List<Long> depositIds = depositService.checkExpiredDeposit();

        for(Long depositId : depositIds){
            depositService.deleteDeposit(depositId);
        }
        log.info("스케줄러 종료");
    }

}
