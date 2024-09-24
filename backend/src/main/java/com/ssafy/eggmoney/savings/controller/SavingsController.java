package com.ssafy.eggmoney.savings.controller;

import com.ssafy.eggmoney.savings.dto.request.SavingsCreateRequestDto;
import com.ssafy.eggmoney.savings.dto.response.SavingsDeleteResponseDto;
import com.ssafy.eggmoney.savings.dto.response.SavingsLogResponseDto;
import com.ssafy.eggmoney.savings.dto.response.SavingsProductListResponseDto;
import com.ssafy.eggmoney.savings.dto.response.SavingsResponseDto;
import com.ssafy.eggmoney.savings.service.SavingService;
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
     * return SavingsProductListResponseDto
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
        System.out.println(savingsCreateRequestDto.getSavingsProductId());
        System.out.println(savingsCreateRequestDto.getPaymentMoney());
        savingService.createSaving(savingsCreateRequestDto);

        return ResponseEntity.ok().build();
    }

    /**
     * 개인적금 조회
     * @param userId
     * return SavingsResponseDto
    * */
    @GetMapping("/{userId}")
    public ResponseEntity<SavingsResponseDto> getSavings(@PathVariable Long userId){
        SavingsResponseDto result = savingService.getSavings(userId);

        return ResponseEntity.ok().body(result);
    }

    /**
     * 적금납입
     * @param userId
     * */
    @PostMapping("/send/{userId}")
    public ResponseEntity<?> sendSavings(@PathVariable Long userId){
        savingService.sendSavings(userId);
        return ResponseEntity.ok().build();
    }

    /**
     * 적금로그 조회
     * @param savingsId
     * return List<SavingsLogResponseDto>
     * */
    @GetMapping("/log/{savingsId}")
    public ResponseEntity<List<SavingsLogResponseDto>> getSavingsLogs(@PathVariable Long savingsId){

        List<SavingsLogResponseDto> result = savingService.getSavingsLogs(savingsId);


        return ResponseEntity.ok().body(result);
    }


    /**
     * 적금삭제
     * @param savingsId
     * return SavingsDeleteResponseDto
     */

    @PostMapping("/delete/{savingsId}")
    public ResponseEntity<SavingsDeleteResponseDto> deleteSavings(@PathVariable Long savingsId){
        SavingsDeleteResponseDto result = savingService.deleteSavings(savingsId);

        return ResponseEntity.ok().body(result);
    }

}
