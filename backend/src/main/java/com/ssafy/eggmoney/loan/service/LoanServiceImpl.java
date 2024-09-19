package com.ssafy.eggmoney.loan.service;

import com.ssafy.eggmoney.account.entity.AccountLogType;
import com.ssafy.eggmoney.account.service.AccountService;
import com.ssafy.eggmoney.loan.dto.request.LoanCreateRequestDto;
import com.ssafy.eggmoney.loan.dto.request.LoanEvaluationRequestDto;
import com.ssafy.eggmoney.loan.dto.response.LoanDetailResponseDto;
import com.ssafy.eggmoney.loan.dto.response.LoanLogListResponseDto;
import com.ssafy.eggmoney.loan.dto.response.LoanPrivateListResponseDto;
import com.ssafy.eggmoney.loan.entity.Loan;
import com.ssafy.eggmoney.loan.entity.LoanLog;
import com.ssafy.eggmoney.loan.entity.LoanStatus;
import com.ssafy.eggmoney.loan.entity.LoanType;
import com.ssafy.eggmoney.loan.repository.LoanLogRepository;
import com.ssafy.eggmoney.loan.repository.LoanRepository;
import com.ssafy.eggmoney.user.entity.User;
import com.ssafy.eggmoney.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final UserRepository userRepository;
    private final LoanRepository loanRepository;
    private final LoanLogRepository loanLogRepository;
    private final AccountService accountService;


    // 대출 생성하기
    @Override
    @Transactional
    public void createLoan(LoanCreateRequestDto requestDto) {

        User user = userRepository.findById(requestDto.getUserId()).orElse(null);
        if(!user.getRole().equals("자녀")){
            log.error("대출생성 권한이 없습니다.");
        }

        Loan loan = Loan.builder()
                .user(user)
                .loanType(requestDto.getLoanType())
                .loanStatus(LoanStatus.PROGRESS)
                .loanAmount(requestDto.getLoanAmount())
                .loanDate(requestDto.getLoanDate())
                .balance(requestDto.getLoanAmount())
                .loanReason(requestDto.getLoanReason())
                .expirationDate(LocalDateTime.now().plusMonths(requestDto.getLoanDate()))
                .build();

        loanRepository.save(loan);

        log.info("대출이 생성되었습니다.");

    }

    // 개인 대출 내역 조회하기(부모면 자녀 것 모두, 자녀는 본인 것)
    @Override
    @Transactional(readOnly = true)
    public List<LoanPrivateListResponseDto> getPrivateLoans(long userId) {
        User user = userRepository.findById(userId).orElse(null);
        List<LoanPrivateListResponseDto> loanList = new ArrayList<>();
        if(user.getRole().equals("부모")){
            List<User> family = userRepository.findAllByFamilyId(user.getFamily().getId());
            for(User u : family){
                if(u.getRole().equals("자녀")){
                    loanList = addLoanList(u.getId(), loanList);
                }
            }
        }else {
            loanList = addLoanList(user.getId(), loanList);
        }
        log.info("개인 대출 리스트를 성공적으로 조회했습니다.");

        return loanList;
    }

    // 자녀의 대출 리스트 조회
    public List<LoanPrivateListResponseDto> addLoanList(long userId, List<LoanPrivateListResponseDto> list) {
        List<Loan> loans = loanRepository.findAllByUserIdOrderByCreatedAtDesc(userId);

        List<LoanPrivateListResponseDto> loanList = loans.stream().map(
                loan -> LoanPrivateListResponseDto.builder()
                        .loanId(loan.getId())
                        .userName(loan.getUser().getName())
                        .loanType(loan.getLoanType())
                        .loanStatus(loan.getLoanStatus())
                        .loanAmount(loan.getLoanAmount())
                        .loanDate(loan.getLoanDate())
                        .balance(loan.getBalance())
                        .loanReason(loan.getLoanReason())
                        .refuseReason(loan.getRefuseReason())
                        .loanRate(loan.getLoanRate())
                        .expirationDate(loan.getExpirationDate())
                        .build()
        ).collect(Collectors.toList());

        list.addAll(loanList);

        return list;
    }

    // 상세대출내역 조회
    @Override
    @Transactional(readOnly = true)
    public LoanDetailResponseDto getDetailLoan(long loanId) {

        Loan loan = loanRepository.findById(loanId).orElse(null);

        LoanDetailResponseDto loanDetail = LoanDetailResponseDto.builder()
                .createdAt(loan.getCreatedAt())
                .expirationDate(loan.getExpirationDate())
                .loanAmount(loan.getLoanAmount())
                .balance(loan.getBalance())
                .refuseReason(loan.getRefuseReason())
                .loanRate(loan.getLoanRate())
                .loanType(loan.getLoanType())
                .build();

        log.info("대출 상세 조회 성공");
        return loanDetail;
    }

    // 대출심사하기
    @Override
    @Transactional
    public void loanEvaluation(long loanId, LoanEvaluationRequestDto requestDto) {
        Loan loan = loanRepository.findById(loanId).orElse(null);

        if(!loan.getUser().getRole().equals("부모")){
            log.error("대출심사 권한이 없습니다.");
        }

        Loan updateLoan = loan.toBuilder()
                .loanStatus(requestDto.getLoanStatus())
                .loanRate(requestDto.getLoanRate())
                .refuseReason(requestDto.getRefuseReason())
                .build();

        loanRepository.save(updateLoan);
        log.info("대출 심사 성공");
    }

    // 대출금 상환하기
    @Override
    @Transactional
    public void sendRepayment(long loanId) {
        Loan loan = loanRepository.findById(loanId).orElse(null);

        double interest = loan.getLoanAmount() * loan.getLoanRate() / loan.getLoanDate();
        int repayment = (loan.getLoanType() == LoanType.EQUALR) ? loan.getLoanAmount() / loan.getLoanDate() : 0;

        if(loan.getBalance() == 0){
            repayment = 0;
        }else if(loan.getBalance() < repayment){
            repayment = loan.getBalance();
        }

        accountService.updateAccount(AccountLogType.LOAN, loan.getUser().getId(), -1 * ((int) (interest) + repayment));

        log.info("이자: {interest}, 원리금: {repayment}", interest, repayment);

        Loan updateLoan = loan.toBuilder()
                .balance(loan.getBalance() - repayment) // 상환한 금액 차감
                .build();

        loanRepository.save(updateLoan);

        LoanLog loanLog = LoanLog.builder()
                .loan(updateLoan)
                .balance(updateLoan.getBalance())
                .build();

        loanLogRepository.save(loanLog);

        log.info("대출 상환");
    }

//    @Override
//    public List<LoanLogListResponseDto> getLoanLogs(long loanId) {
//        loanLogRepository.
//        return List.of();
//    }
}
