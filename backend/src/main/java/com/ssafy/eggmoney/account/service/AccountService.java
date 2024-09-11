package com.ssafy.eggmoney.account.service;

import com.ssafy.eggmoney.account.dto.responseDto.GetAccountResponseDto;
import com.ssafy.eggmoney.account.entity.Account;
import com.ssafy.eggmoney.account.repository.AccountLogRepository;
import com.ssafy.eggmoney.account.repository.AccountRepository;
import com.ssafy.eggmoney.user.entity.User;
import com.ssafy.eggmoney.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountLogRepository accountLogRepository;
    private final UserRepository userRepository;

//    내 메인 계좌 조회
    public GetAccountResponseDto getAccount() {
//        일단 user 고정값 입력해둠
        Optional<User> ou =  userRepository.findById(1L);
        User us = ou.get();
        Account ac = accountRepository.findByUserId(us.getId()).get();
        GetAccountResponseDto getAccountResponseDto = GetAccountResponseDto.builder()
                .userId(us.getId())
                .balance(ac.getBalance())
                .build();

        return getAccountResponseDto;
    }
}
