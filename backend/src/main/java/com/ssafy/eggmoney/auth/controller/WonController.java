package com.ssafy.eggmoney.auth.controller;

import com.ssafy.eggmoney.auth.service.KakaoAuthService;
import com.ssafy.eggmoney.auth.service.WonService;
import com.ssafy.eggmoney.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth/won")
public class WonController {
    private final WonService wonService;
    private final KakaoAuthService kakaoAuthService;

    @PostMapping("/send")
    public Mono<String> sendmessage(@RequestBody Map<String, String> request,
                                    @RequestHeader(value = "Authorization") String token)
    {
        User user = kakaoAuthService.verifyKakaoToken(token);
        String accountnum = request.get("accountnum");
        String bank = request.get("bank");
        return wonService.sendmessage(accountnum, bank, user);
    }

    @PostMapping("/check")
    public Mono<String> checkmessage(@RequestHeader(value = "Authorization") String token,
                                     @RequestBody Map<String, String> request){
        String accountnum = request.get("accountnum");
        String authText = request.get("authText");
        String authnum = request.get("authnum");
        String bank = request.get("bank");
        User user = kakaoAuthService.verifyKakaoToken(token);

        return wonService.checkmessage(accountnum,authText,bank,authnum,user);
    }

}
