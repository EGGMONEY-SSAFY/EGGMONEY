package com.ssafy.eggmoney.user.controller;

import com.ssafy.eggmoney.auth.service.KakaoAuthService;
import com.ssafy.eggmoney.user.dto.reqeust.CreateUserReqeusetDto;
import com.ssafy.eggmoney.user.dto.reqeust.InvestmentRatioRequest;
import com.ssafy.eggmoney.user.dto.reqeust.UpdateUserRequestDto;
import com.ssafy.eggmoney.user.dto.response.GetUserResponseDto;
import com.ssafy.eggmoney.user.dto.response.InvestmentRatioResponse;
import com.ssafy.eggmoney.user.entity.User;
import com.ssafy.eggmoney.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/profile")
public class UserController {
    private final UserService userService;
    private final KakaoAuthService kakaoAuthService;

    // 유저 임시 Controller

//    유저 조회
    @GetMapping("")
    public GetUserResponseDto getUser(@RequestHeader(value = "Authorization") String token)
    {
        User user = kakaoAuthService.verifyKakaoToken(token);
        return userService.getUser(user);
    }

//    유저 생성
    @PostMapping("/create")
    public void createuser(@RequestBody CreateUserReqeusetDto dto) {
        userService.createUser(dto);
    }

    @PostMapping("/update")
    public void updateUser(@RequestHeader(value = "Authorization") String token, @RequestBody UpdateUserRequestDto dto){
        User user = kakaoAuthService.verifyKakaoToken(token);
        userService.updateUser(user, dto);
    }

    @GetMapping("/investment-ratio")
    public ResponseEntity<List<InvestmentRatioResponse>> getInvestmentRatio(@RequestHeader("Authorization") String token) {
        Long userId = kakaoAuthService.verifyKakaoToken(token).getId();
        return new ResponseEntity<>(userService.findInvestmentRatio(userId), HttpStatus.OK);
    }

    @PostMapping("/investment-ratio/update")
    public ResponseEntity<Integer> updateInvestmentRatio(@RequestBody InvestmentRatioRequest investmentRatioReq,
                                                         @RequestHeader("Authorization") String token){
        Long presentId = kakaoAuthService.verifyKakaoToken(token).getId();
        return new ResponseEntity<>(userService.updateInvestmentRatio(presentId, investmentRatioReq), HttpStatus.OK);
    }

    // Token 기반 컨트롤러
    @PostMapping("/update/ExInfo")
    public void tokenUpdateUser(@RequestHeader(value = "Authorization", required = false) String token, @RequestBody UpdateUserRequestDto dto){
        User user = kakaoAuthService.verifyKakaoToken(token);
        userService.updateUser(user, dto);
    }
    @GetMapping("/search")
    public GetUserResponseDto tokenGetUser(@RequestHeader(value = "Authorization", required = false) String token) {
        User user = kakaoAuthService.verifyKakaoToken(token);
        return userService.getUser(user);
    }
}
