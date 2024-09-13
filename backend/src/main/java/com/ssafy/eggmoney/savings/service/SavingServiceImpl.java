package com.ssafy.eggmoney.savings.service;

import com.ssafy.eggmoney.account.entity.AccountLogType;
import com.ssafy.eggmoney.account.service.AccountLogService;
import com.ssafy.eggmoney.savings.dto.requestDto.SavingsCreateRequestDto;
import com.ssafy.eggmoney.savings.dto.responseDto.SavingsProductListResponseDto;
import com.ssafy.eggmoney.savings.entity.Savings;
import com.ssafy.eggmoney.savings.entity.SavingsProduct;
import com.ssafy.eggmoney.savings.repository.SavingsProductRepository;
import com.ssafy.eggmoney.savings.repository.SavingsRepository;
import com.ssafy.eggmoney.user.entity.User;
import com.ssafy.eggmoney.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SavingServiceImpl implements SavingService {

    private final SavingsRepository savingsRepository;
    private final SavingsProductRepository savingsProductRepository;
    private final UserRepository userRepository;
    private final AccountLogService accountLogService;

    @Override
    public List<SavingsProductListResponseDto> getSavingProducts() {
        List<SavingsProduct> productList =  savingsProductRepository.findAll();

        List<SavingsProductListResponseDto> productListDto = productList.stream().map(
                (product) -> SavingsProductListResponseDto.builder()
                        .id(product.getId())
                        .savingsRate(product.getSavingsRate())
                        .savingsDate(product.getSavingsDate())
                        .max_price(product.getMaxPrice())
                        .build()
                ).collect(Collectors.toList());

        return productListDto;
    }

    @Override
    public void createSaving(SavingsCreateRequestDto requestDto){

        User user = userRepository.findById(requestDto.getUserId()).orElse(null);
        SavingsProduct savingsProduct = savingsProductRepository.findById(requestDto.getSavingsProductId()).orElse(null);


        Savings savings = Savings.builder()
                .user(user)
                .savingsProduct(savingsProduct)
                .expireDate(LocalDateTime.now().plusMonths(savingsProduct.getSavingsDate()))
                .paymentDate(LocalDateTime.now().getDayOfMonth())
                .balance(requestDto.getPrice())
                .build();

        savingsRepository.save(savings);

        accountLogService.createAccountLog(user.getId(),  AccountLogType.SAVINGS, -1 * requestDto.getPrice());

    }

}
