package com.ssafy.eggmoney.deposit.controller;


import com.ssafy.eggmoney.auth.service.KakaoAuthService;
import com.ssafy.eggmoney.deposit.dto.request.DepositCreateRequestDto;
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
    @CrossOrigin("http://localhost:5173")
    @GetMapping("/product")
    public ResponseEntity<List<DepositProductListResponseDto>> getAllDepositProduct() {
        List<DepositProductListResponseDto> result = depositService.getDepositProducts();
        return ResponseEntity.ok().body(result);
    }


    /**
    * 예금생성
    * @param requestDto
    * return
    * */
    @PostMapping("/create")
    public ResponseEntity<?> createDeposit(@RequestBody DepositCreateRequestDto requestDto) {
        depositService.createDeposit(requestDto);

        return ResponseEntity.ok().build();
    }

    /**
     * 개인 예금 조회
     * @param userId
     * return DepositResponseDto
    * */
    @GetMapping("")
    public ResponseEntity<DepositResponseDto> getDeposits(@RequestHeader(value = "Authorization") String token) {
        User user = kakaoAuthService.verifyKakaoToken(token);
        DepositResponseDto result = depositService.getDeposits(user);
        return ResponseEntity.ok().body(result);
    }


    /**
     * 예금삭제
     * @param depositId
     * return DeleteDepositResponseDto
    */
    @PostMapping("/delete/{depositId}")
    public ResponseEntity<DeleteDepositResponseDto> deleteDeposit(@PathVariable long depositId) {
        DeleteDepositResponseDto result = depositService.deleteDeposit(depositId);

        return ResponseEntity.ok().body(result);
    }

}
