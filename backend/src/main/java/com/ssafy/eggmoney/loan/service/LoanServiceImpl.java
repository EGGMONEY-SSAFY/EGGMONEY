package com.ssafy.eggmoney.loan.service;

import com.ssafy.eggmoney.loan.dto.request.LoanCreateRequestDto;
import com.ssafy.eggmoney.loan.entity.Loan;
import com.ssafy.eggmoney.loan.entity.LoanStatus;
import com.ssafy.eggmoney.loan.entity.LoanType;
import com.ssafy.eggmoney.loan.repository.LoanLogRepository;
import com.ssafy.eggmoney.loan.repository.LoanRepository;
import com.ssafy.eggmoney.user.entity.User;
import com.ssafy.eggmoney.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final UserRepository userRepository;
    private final LoanRepository loanRepository;
    private final LoanLogRepository loanLogRepository;

    @Override
    @Transactional
    public void createLoan(LoanCreateRequestDto requestDto) {

        User user = userRepository.findById(requestDto.getUserId()).orElse(null);
        int balance = requestDto.getBalance();
//        if(requestDto.getLoanType() == LoanType.LUMPSUM){
//            balance = 0;
//        }else{
//            balance = requestDto.getBalance();
//
//        }

        Loan loan = Loan.builder()
                .user(user)
                .loanType(requestDto.getLoanType())
                .loanStatus(LoanStatus.PROGRESS)
                .loanAmount(requestDto.getLoanAmount())
                .loanDate(requestDto.getLoanDate())
                .balance(balance)
                .loanReason(requestDto.getLoanReason())
                .build();

        loanRepository.save(loan);

    }
}
