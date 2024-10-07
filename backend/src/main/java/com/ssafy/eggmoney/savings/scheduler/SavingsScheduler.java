package com.ssafy.eggmoney.savings.scheduler;

import com.ssafy.eggmoney.savings.service.SavingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class SavingsScheduler {

    private final SavingService savingService;

    // 적금 이자를 플러스 하는 것이 아니라 마지막에 달마다 낸 것을 단리로 계산하는 것임.
    // 만기시 자동 해지(낮 12시 실행)
    @Scheduled(cron ="0 0 12 * * ?")
    @Transactional
    public void expired(){
        log.info("{} 적금 만기 확인 스케줄러 시작", LocalDate.now());
        List<Long> savingsIds = savingService.checkExpiredSavings();
        if(!savingsIds.isEmpty()){
            for(Long savingsId : savingsIds){
                savingService.deleteSavings(savingsId);
            }
            log.info("적금 만기 확인 스케줄러 종료");
        }else{
            log.info("적금 만기인 계좌가 없습니다.");
        }
    }

    // 해당 월 적금 납부 했는지(매월 1일에 전월 납부여부 확인)
    @Scheduled(cron ="0 0 12 1 * ?")
    public void checkingPay(){
        log.info("{} 적금 미납자 확인 스케줄러 시작", LocalDate.now());
        List<Long> savingsIds = savingService.checkingPayId();

        savingService.plusExpired(savingsIds);

        log.info("적금 미납자 확인 스케줄러 종료");
    }
}
