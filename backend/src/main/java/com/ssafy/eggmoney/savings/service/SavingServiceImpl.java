package com.ssafy.eggmoney.savings.service;

import com.ssafy.eggmoney.savings.dto.responseDto.SavingsProductListResponseDto;
import com.ssafy.eggmoney.savings.entity.SavingsProduct;
import com.ssafy.eggmoney.savings.repository.SavingsProductRepository;
import com.ssafy.eggmoney.savings.repository.SavingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SavingServiceImpl implements SavingService {

    private final SavingsRepository savingsRepository;
    private final SavingsProductRepository savingsProductRepository;


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

}
