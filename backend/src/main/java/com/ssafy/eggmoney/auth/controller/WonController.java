package com.ssafy.eggmoney.auth.controller;

import com.ssafy.eggmoney.auth.dto.request.WonRequest;
import com.ssafy.eggmoney.auth.dto.response.WonResponse;
import com.ssafy.eggmoney.auth.service.WonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth/won")
public class WonController {
    private final WonService wonService;

    @GetMapping("/send")
    public Mono<String> sendmessage(@RequestParam String accountnum){
        return wonService.sendmessage(accountnum);
    }

    @GetMapping("/check")
    public Mono<String> checkmessage(@RequestParam String accountnum, @RequestParam String authText, @RequestParam String authnum){
        return wonService.checkmessage(accountnum,authText,authnum);
    }

}
