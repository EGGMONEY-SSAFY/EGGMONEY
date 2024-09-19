package com.ssafy.eggmoney.loan.service;

import com.ssafy.eggmoney.loan.dto.request.LoanCreateRequestDto;
import com.ssafy.eggmoney.loan.dto.response.LoanDetailResponseDto;
import com.ssafy.eggmoney.loan.dto.response.LoanPrivateListResponseDto;
import com.ssafy.eggmoney.loan.entity.Loan;
import com.ssafy.eggmoney.loan.entity.LoanStatus;
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

    @Override
    @Transactional
    public void createLoan(LoanCreateRequestDto requestDto) {

        User user = userRepository.findById(requestDto.getUserId()).orElse(null);
        if(user.getRole().equals("부모")){
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

        return loanDetail;
    }
}
