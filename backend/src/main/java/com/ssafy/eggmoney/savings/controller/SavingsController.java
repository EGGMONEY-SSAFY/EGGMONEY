package com.ssafy.eggmoney.savings.controller;

import com.ssafy.eggmoney.deposit.dto.responsedto.DepositProductListResponseDto;
import com.ssafy.eggmoney.savings.dto.requestDto.SavingsCreateRequestDto;
import com.ssafy.eggmoney.savings.dto.responseDto.SavingsProductListResponseDto;
import com.ssafy.eggmoney.savings.service.SavingService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/fin/savings")
public class SavingsController {

    private final SavingService savingService;

    /**
     * 적금상품 전체 조회
     * return savingsProductDto
    * */
    @GetMapping("/product")
    public ResponseEntity<List<SavingsProductListResponseDto>> productList(){
        List<SavingsProductListResponseDto> result = savingService.getSavingProducts();
        return ResponseEntity.ok().body(result);
    }

    /**
     * 적금상품 가입하기
     * return
    * */
    @PostMapping("/create")
    public ResponseEntity<?> createSaving(@RequestBody SavingsCreateRequestDto savingsCreateRequestDto){
        savingService.createSaving(savingsCreateRequestDto);

        return ResponseEntity.ok().build();
    }



}
