package com.ssafy.eggmoney.loan.scheduler;

import com.ssafy.eggmoney.loan.service.LoanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class LoanScheduler {

    private final LoanService loanService;

    // 대출 만기시 상환
    @Scheduled(cron = "0 0 12 * * ?")
    public void loanSchedule() {

    }

}
