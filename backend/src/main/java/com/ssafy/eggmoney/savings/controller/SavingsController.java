package com.ssafy.eggmoney.savings.controller;

import com.ssafy.eggmoney.auth.service.KakaoAuthService;
import com.ssafy.eggmoney.savings.dto.request.SavingsCreateRequestDto;
import com.ssafy.eggmoney.savings.dto.request.SavingsRequestDto;
import com.ssafy.eggmoney.savings.dto.response.SavingsDeleteResponseDto;
import com.ssafy.eggmoney.savings.dto.response.SavingsLogResponseDto;
import com.ssafy.eggmoney.savings.dto.response.SavingsProductListResponseDto;
import com.ssafy.eggmoney.savings.dto.response.SavingsResponseDto;
import com.ssafy.eggmoney.savings.service.SavingService;
import com.ssafy.eggmoney.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/fin/savings")
public class SavingsController {

    private final SavingService savingService;
    private final KakaoAuthService kakaoAuthService;

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
     * 적금상품 단일 조회
     * return SavingsProductListResponseDto
     * */
    @GetMapping("/product/{savingsId}")
    public SavingsProductListResponseDto getSavingsProduct(@PathVariable("savingsId") Long savingsId){
        return savingService.getSavingProduct(savingsId);
    }

    /**
     * 적금상품 가입하기
     * return
    * */
    @PostMapping("/create")
    public ResponseEntity<?> createSaving(@RequestBody SavingsCreateRequestDto savingsCreateRequestDto,
                                          @RequestHeader(value = "Authorization") String token){
        System.out.println(savingsCreateRequestDto.getSavingsProductId());
        System.out.println(savingsCreateRequestDto.getPaymentMoney());
        User user = kakaoAuthService.verifyKakaoToken(token);
        savingService.createSaving(savingsCreateRequestDto, user);

        return ResponseEntity.ok().build();
    }

    /**
     * 개인적금 조회
     * @param dto
     * return SavingsResponseDto
    * */
    @PostMapping("")
    public ResponseEntity<SavingsResponseDto> getSavings(@RequestHeader(value = "Authorization") String token,
                                                         @RequestBody SavingsRequestDto dto) {
        User user = kakaoAuthService.verifyKakaoToken(token);
        SavingsResponseDto result = savingService.getSavings(dto.getUserId());
        return ResponseEntity.ok().body(result);
    }

    /**
     * 적금납입
     * @param dto
     * */
    @PostMapping("/send")
    public ResponseEntity<?> sendSavings(@RequestHeader(value = "Authorization") String token,
                                         @RequestBody SavingsRequestDto dto) {
        User user = kakaoAuthService.verifyKakaoToken(token);
        savingService.sendSavings(dto.getUserId());
        return ResponseEntity.ok().build();
    }

    /**
     * 적금로그 조회
     * @param savingsId
     * return List<SavingsLogResponseDto>
     * */
    @GetMapping("/log/{savingsId}")
    public ResponseEntity<List<SavingsLogResponseDto>> getSavingsLogs(@RequestHeader(value = "Authorization") String token, @PathVariable Long savingsId){
        User user = kakaoAuthService.verifyKakaoToken(token);
        List<SavingsLogResponseDto> result = savingService.getSavingsLogs(savingsId);


        return ResponseEntity.ok().body(result);
    }


    /**
     * 적금삭제
     * @param savingsId
     * return SavingsDeleteResponseDto
     */

    @PostMapping("/delete/{savingsId}")
    public ResponseEntity<SavingsDeleteResponseDto> deleteSavings(@RequestHeader(value = "Authorization") String token, @PathVariable Long savingsId){
        User user = kakaoAuthService.verifyKakaoToken(token);
        SavingsDeleteResponseDto result = savingService.deleteSavings(savingsId);

        return ResponseEntity.ok().body(result);
    }

}
