package com.ssafy.eggmoney.loan.scheduler;

import com.ssafy.eggmoney.loan.service.LoanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class LoanScheduler {

    private final LoanService loanService;

    // 대출 만기시 상환
    @Scheduled(cron = "0 0 12 * * ?")
    public void loanSchedule() {
        log.info("{} 대출 만기 시 상환 스케줄러 시작", LocalDate.now());
        List<Long> loanIds = loanService.checkingExpired();
        if(!loanIds.isEmpty()){
            for(Long loanId : loanIds) {
                loanService.expiredRepayment(loanId);
            }
        }else{
            log.info("대출 만기 계좌가 없습니다.");
        }

        log.info("대출 만기 시 상환 스케줄러 종료");
    }

}
