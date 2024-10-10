package com.ssafy.eggmoney.account.service;

import com.ssafy.eggmoney.account.dto.response.GetAccountResponseDto;
import com.ssafy.eggmoney.account.dto.response.GetAnalyticsResponseDto;
import com.ssafy.eggmoney.account.entity.Account;
import com.ssafy.eggmoney.account.entity.AccountLogType;
import com.ssafy.eggmoney.account.repository.AccountLogRepository;
import com.ssafy.eggmoney.account.repository.AccountRepository;
import com.ssafy.eggmoney.deposit.entity.Deposit;
import com.ssafy.eggmoney.deposit.entity.DepositStatus;
import com.ssafy.eggmoney.deposit.repository.DepositRepository;
import com.ssafy.eggmoney.loan.entity.Loan;
import com.ssafy.eggmoney.loan.entity.LoanStatus;
import com.ssafy.eggmoney.savings.entity.Savings;
import com.ssafy.eggmoney.savings.entity.SavingsStatus;
import com.ssafy.eggmoney.savings.repository.SavingsRepository;
import com.ssafy.eggmoney.stock.entity.StockUser;
import com.ssafy.eggmoney.stock.repository.StockUserRepository;
import com.ssafy.eggmoney.stock.service.StockPendingService;
import com.ssafy.eggmoney.user.entity.User;
import com.ssafy.eggmoney.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountLogRepository accountLogRepository;
    private final AccountLogService accountLogService;
    private final UserRepository userRepository;
    private final SavingsRepository savingsRepository;
    private final DepositRepository depositRepository;
    private final com.ssafy.eggmoney.loan.repository.LoanRepository loanRepository;
    private final StockUserRepository stockUserRepository;
    private final StockPendingService stockPendingService;

//    내 메인 계좌 조회
    public GetAccountResponseDto getAccount(Long userId) {
        Optional<User> ou =  userRepository.findById(userId);

        if ( ou.isEmpty() )
            throw new NoSuchElementException("해당 유저가 존재하기 않습니다.");

        User us = ou.get();
        Account ac = accountRepository.findByUserId(us.getId()).get();
        GetAccountResponseDto getAccountResponseDto = GetAccountResponseDto.builder()
                .userId(us.getId())
                .balance(ac.getBalance())
                .build();

        return getAccountResponseDto;
    }

//    메인 계좌 생성
    public void createAccount(Long userId) {
        System.out.println("user "+ userId + "의 메인 계좌 생성 완료");
        accountRepository.save(Account
                .builder()
                        .user(userRepository.findById(userId).orElse(null))
                        .balance(0)
                .build()
        );
    }

//    메인 계좌 입출금
    public void updateAccount(AccountLogType type, Long userId, int price) {
//        계좌에 입출금 반영
        Account account = accountRepository.findByUserId(userId).get();
        int totalPendingPrice = stockPendingService.findPendingBuyTotalPrice(userId);

        if(account.getBalance() + price < 0) {
            throw new IllegalArgumentException("[자산] 계좌의 잔액이 부족합니다.");
        } else if(account.getBalance() + price - totalPendingPrice < 0) {
            throw new IllegalArgumentException("[자산] 지정 매수가 설정되어 있어 계좌의 잔액을 사용하실 수 없습니다.");
        }

        account.setBalance( account.getBalance() + price );
        accountRepository.save(account);
//        로그 생성
        accountLogService.createAccountLog(userId, type, price);
    }

//    자산 분석 ( 예적금, 대출, 주식 보유 파악 )
    public GetAnalyticsResponseDto getAnalytics(Long userId) {
        Account account = accountRepository.findByUserId(userId).orElse(null);
        Savings savings = savingsRepository.findByUserIdAndSavingsStatus(userId, SavingsStatus.AVAILABLE).orElse(null);
        List<Loan> loans = loanRepository.findAllByUserIdAndLoanStatus(userId, LoanStatus.APPROVAL);
        int totalLoan = 0;
        System.out.println(loans.toString());
        if ( !loans.isEmpty() ) {
            for ( Loan loan : loans ) {
                totalLoan += loan.getBalance();
            }
        }

        Deposit deposit = depositRepository.findByUserIdAndDepositStatus(userId, DepositStatus.AVAILABLE).orElse(null);

        GetAnalyticsResponseDto dto = GetAnalyticsResponseDto.builder()
                .mainAccountBalance(account != null ? account.getBalance() : null)
                .savings(savings != null ? savings.getBalance() : null)
                .deposit(deposit != null ? deposit.getDepositMoney() : null)
                .stock(findUserTotalStockPrice(userId) != 0 ? findUserTotalStockPrice(userId) : null )
                .loan(totalLoan != 0 ? totalLoan : null)
                .build();
        return dto;
    }

    public Integer findUserTotalStockPrice(Long userId) {
        List<StockUser> stockUsers = stockUserRepository.findJoinStockByUserIdOrderByStockId(userId);

        if(stockUsers == null || stockUsers.isEmpty()) {
            return 0;
        }

        int totalStockPrice = 0;
        for(StockUser stockUser : stockUsers) {
            totalStockPrice += stockUser.getAmount() * stockUser.getStock().getCurrentPrice();
        }

        return totalStockPrice;
    }
}
