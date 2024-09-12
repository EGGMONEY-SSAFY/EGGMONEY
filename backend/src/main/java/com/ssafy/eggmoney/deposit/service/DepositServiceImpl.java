package com.ssafy.eggmoney.deposit.service;

import com.ssafy.eggmoney.deposit.dto.requestdto.DepositCreateRequestDto;
import com.ssafy.eggmoney.deposit.dto.responsedto.DepositResponseDto;
import com.ssafy.eggmoney.deposit.entity.Deposit;
import com.ssafy.eggmoney.deposit.entity.DepositProduct;
import com.ssafy.eggmoney.deposit.repository.DepositProductRepository;
import com.ssafy.eggmoney.deposit.repository.DepositRepository;
import com.ssafy.eggmoney.user.entity.User;
import com.ssafy.eggmoney.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class DepositServiceImpl implements DepositService {

    private final UserRepository userRepository;
    private final DepositRepository depositRepository;
    private final DepositProductRepository depositProductRepository;

    @Override
    public void createDeposit(DepositCreateRequestDto requestDto){
        User user = userRepository.findById(requestDto.getUserId()).get();
        DepositProduct depositProduct = depositProductRepository.findById(requestDto.getDepositProductId()).get();

        LocalDateTime expiration = LocalDateTime.now().plusMonths(depositProduct.getDepositDate());

        if(!user.getRole().equals("자녀")){
            // 에러발생
            log.error("예금 가입 권한이 없는 유저입니다.");
        }
        if(depositRepository.findByUserId(requestDto.getUserId()).isPresent()){
            // 에러발생
            log.error("이미 사용자가 예금상품을 가지고 있습니다.");
        }

        Deposit deposit = Deposit.builder()
                .user(user)
                .depositProduct(depositProduct)
                .expireDate(expiration)
                .depositMoney(requestDto.getDepositMoney())
                .build();

        Deposit savedDeposit = depositRepository.save(deposit);

    }

//    @Override
//    public DepositResponseDto getDeposit(long id){
//        User user = userRepository.findById(id).get();
//        int accountId = 1;
//        if(user.getRole().equals("자녀")){
//
//        }
//        Deposit deposit = depositRepository.findById().get();
//    }

}
