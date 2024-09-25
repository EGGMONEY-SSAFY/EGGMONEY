package com.ssafy.eggmoney.auth.controller;

import com.ssafy.eggmoney.auth.service.WonService;
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

    @PostMapping("/send")
    public Mono<String> sendmessage(@RequestBody Map<String, String> request){
        String accountnum = request.get("accountnum");
        return wonService.sendmessage(accountnum);
    }

    @PostMapping("/check")
    public Mono<String> checkmessage(@RequestBody Map<String, String> request){
        String accountnum = request.get("accountnum");
        String authText = request.get("authText");
        String authnum = request.get("authnum");
        return wonService.checkmessage(accountnum,authText,authnum);
    }

}
