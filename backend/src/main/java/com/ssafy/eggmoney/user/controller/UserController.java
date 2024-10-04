package com.ssafy.eggmoney.user.controller;

import com.ssafy.eggmoney.auth.service.KakaoAuthService;
import com.ssafy.eggmoney.user.dto.reqeust.CreateUserReqeusetDto;
import com.ssafy.eggmoney.user.dto.reqeust.InvestmentRatioRequest;
import com.ssafy.eggmoney.user.dto.reqeust.UpdateUserRequestDto;
import com.ssafy.eggmoney.user.dto.response.GetUserResponseDto;
import com.ssafy.eggmoney.user.entity.User;
import com.ssafy.eggmoney.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/profile")
public class UserController {
    private final UserService userService;
    private final KakaoAuthService kakaoAuthService;

    // 유저 임시 Controller

//    유저 조회
    @GetMapping("/{userId}")
    public GetUserResponseDto getUser(@PathVariable("userId") Long userId) {
        return userService.getUser(userId);
    }

//    유저 생성
    @PostMapping("/create")
    public void createuser(@RequestBody CreateUserReqeusetDto dto) {
        System.out.println(dto.getBank());
        userService.createUser(dto);
    }

    @PostMapping("/investment-ratio/update")
    public ResponseEntity<Integer> updateInvestmentRatio(@RequestBody InvestmentRatioRequest investmentRatioReq,
                                                         @RequestHeader("Authorization") String token){
        return new ResponseEntity<>(userService.updateInvestmentRatio(2L, investmentRatioReq), HttpStatus.OK);
    }


    // Token 기반 컨트롤러
    @PostMapping("/update/ExInfo")
    public void tokenUpdateUser(@RequestHeader(value = "Authorization", required = false) String token, @RequestBody UpdateUserRequestDto dto){
        User user = kakaoAuthService.verifyKakaoToken(token);
        userService.updateUser(user.getId(), dto);
    }
    @GetMapping("/search")
    public GetUserResponseDto tokenGetUser(@RequestHeader(value = "Authorization", required = false) String token) {
        User user = kakaoAuthService.verifyKakaoToken(token);
        return userService.getUser(user.getId());
    }
}
