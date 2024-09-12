package com.ssafy.eggmoney.deposit.controller;


import com.ssafy.eggmoney.deposit.dto.requestdto.DepositCreateRequestDto;
import com.ssafy.eggmoney.deposit.service.DepositService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/fin/deposit")
public class DepositController {

    private final DepositService depositService;


    /**
    * 예금생성
    * @param : DepositCreateRequestDto
    * return
    * */
    @PostMapping("/create")
    public ResponseEntity<?> createDeposit(@RequestBody DepositCreateRequestDto requestDto) {
        depositService.createDeposit(requestDto);

        return ResponseEntity.ok().build();
    }

//    @GetMapping("/{userId}")
//    public ResponseEntity<?> getDeposits(@PathVariable long userId) {
//        depositService.getDeposits(userId);
//
//    }



}
