package com.ssafy.eggmoney.user.controller;

import com.ssafy.eggmoney.auth.service.KakaoAuthService;
import com.ssafy.eggmoney.user.dto.reqeust.CreateUserReqeusetDto;
import com.ssafy.eggmoney.user.dto.reqeust.UpdateUserRequestDto;
import com.ssafy.eggmoney.user.dto.response.GetUserResponseDto;
import com.ssafy.eggmoney.user.entity.User;
import com.ssafy.eggmoney.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
//        System.out.println(dto.getBank());
        userService.createUser(dto);
    }

    @PostMapping("/update")
    public void updateUser(@RequestHeader(value = "Authorization") String token, @RequestBody UpdateUserRequestDto dto){
        User user = kakaoAuthService.verifyKakaoToken(token);
        userService.updateUser(user, dto);
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
