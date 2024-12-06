package com.ssafy.eggmoney.allowance.controller;

import com.ssafy.eggmoney.allowance.dto.request.AllowanceGetRequestDto;
import com.ssafy.eggmoney.allowance.dto.response.AllowanceCreateResponseDto;
import com.ssafy.eggmoney.allowance.dto.response.AllowanceUpdateResponseDto;
import com.ssafy.eggmoney.allowance.service.AllowanceService;
import com.ssafy.eggmoney.auth.service.KakaoAuthService;
import com.ssafy.eggmoney.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/total/money")
public class AllowanceController {

    private final KakaoAuthService kakaoAuthService;
    private final AllowanceService allowanceService;

    @PostMapping("/create")
    public ResponseEntity<AllowanceCreateResponseDto> allowanceCreate(@RequestHeader(value="Authorization") String token){
        User user = kakaoAuthService.verifyKakaoToken(token);
        // 가족 등록과 동시에 유저의 role이 자녀일 경우, 용돈 테이블의 price, allowance_period, allowance_day null 값을 주고, user_id에 user.getId()를 하고 DB에 넣는다.
        AllowanceCreateResponseDto response = allowanceService.createAllowance(user);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/update")
    public ResponseEntity<AllowanceUpdateResponseDto> allowancePost(@RequestHeader(value="Authorization") String token,@RequestBody AllowanceUpdateResponseDto updateDto){
        User user = kakaoAuthService.verifyKakaoToken(token);
        // user가 해당 user_id의 가족 테이블의 present_id일 경우, 용돈테이블의 user_id와 일치하는 유저를 찾아서 업데이트를 한다.
        // 아닐경우 실패를 던진다. 물론 그럴 경우는 없을 것이다.
        AllowanceUpdateResponseDto response = allowanceService.updateAllowance(updateDto.getAllowanceId(), updateDto, user);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/search")
    public ResponseEntity<List<AllowanceGetRequestDto>> allowanceGet(@RequestHeader(value = "Authorization")String token){
        User user = kakaoAuthService.verifyKakaoToken(token);
        // 해당 유저의 가족 테이블에 연결된 role이 '자녀'인 유저들의 용돈 테이블 전체 정보를 로드한다.
        List<AllowanceGetRequestDto> response = allowanceService.getAllowancesForChildren(user);
        return ResponseEntity.ok(response);
    }
}
