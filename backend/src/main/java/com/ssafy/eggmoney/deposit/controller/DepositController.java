package com.ssafy.eggmoney.deposit.controller;


import com.ssafy.eggmoney.auth.service.KakaoAuthService;
import com.ssafy.eggmoney.deposit.dto.DepositProductDto;
import com.ssafy.eggmoney.deposit.dto.request.DepositCreateRequestDto;
import com.ssafy.eggmoney.deposit.dto.request.DepositRequestDto;
import com.ssafy.eggmoney.deposit.dto.response.DeleteDepositResponseDto;
import com.ssafy.eggmoney.deposit.dto.response.DepositProductListResponseDto;
import com.ssafy.eggmoney.deposit.dto.response.DepositResponseDto;
import com.ssafy.eggmoney.deposit.service.DepositService;
import com.ssafy.eggmoney.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/fin/deposit")
public class DepositController {

    private final DepositService depositService;
    private final KakaoAuthService kakaoAuthService;

    /**
     * 전체 예금 상품 조회
     * return DepositProductResponseDto
     * */
    @GetMapping("/product")
    public ResponseEntity<List<DepositProductListResponseDto>> getAllDepositProduct() {
        List<DepositProductListResponseDto> result = depositService.getDepositProducts();
        return ResponseEntity.ok().body(result);
    }

    /**
     * 단일 예금 상품 조회
     * return DepositProductResponseDto
     * */
    @GetMapping("/product/{depositId}")
    public DepositProductDto getDepositProduct(@PathVariable("depositId") Long depositId) {
        return depositService.getDepositProduct(depositId);
    }

    /**
    * 예금생성
    * @param requestDto
    * return
    * */
    @PostMapping("/create")
    public ResponseEntity<?> createDeposit(@RequestHeader(value = "Authorization") String token,
                                           @RequestBody DepositCreateRequestDto requestDto) {
        User user = kakaoAuthService.verifyKakaoToken(token);
        depositService.createDeposit(requestDto, user);

        return ResponseEntity.ok().build();
    }

    /**
     * 개인 예금 조회
     * @param dto
     * return DepositResponseDto
    * */
    @PostMapping("")
    public ResponseEntity<DepositResponseDto> getDeposits(@RequestHeader(value = "Authorization") String token,
                                                          @RequestBody DepositRequestDto dto) {
        User user = kakaoAuthService.verifyKakaoToken(token);
        DepositResponseDto result = depositService.getDeposits(dto.getUserId());
        return ResponseEntity.ok().body(result);
    }


    /**
     * 예금삭제
     * @param depositId
     * return DeleteDepositResponseDto
    */
    @PostMapping("/delete/{depositId}")
    public ResponseEntity<DeleteDepositResponseDto> deleteDeposit(@RequestHeader(value = "Authorization") String token, @PathVariable long depositId) {
        DeleteDepositResponseDto result = depositService.deleteDeposit(depositId);

        return ResponseEntity.ok().body(result);
    }

}
