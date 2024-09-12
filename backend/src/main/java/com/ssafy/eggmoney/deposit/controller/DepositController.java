package com.ssafy.eggmoney.deposit.controller;


import com.ssafy.eggmoney.deposit.dto.requestdto.DepositCreateRequestDto;
import com.ssafy.eggmoney.deposit.dto.responsedto.DepositResponseDto;
import com.ssafy.eggmoney.deposit.service.DepositService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/fin/deposit")
public class DepositController {

    private final DepositService depositService;


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
     * return
    * */
    @GetMapping("/{userId}")
    public ResponseEntity<DepositResponseDto> getDeposits(@PathVariable long userId) {
        DepositResponseDto result = depositService.getDeposits(userId);
        log.info(result.toString());
        return ResponseEntity.ok().body(result);
    }



}
