package com.ssafy.eggmoney.stock.controller;

import com.ssafy.eggmoney.auth.service.KakaoAuthService;
import com.ssafy.eggmoney.stock.dto.request.StockBuyRequest;
import com.ssafy.eggmoney.stock.dto.request.StockSellRequest;
import com.ssafy.eggmoney.stock.dto.response.StockLogResponse;
import com.ssafy.eggmoney.stock.dto.response.StockUserResponse;
import com.ssafy.eggmoney.stock.service.StockLogService;
import com.ssafy.eggmoney.stock.service.StockUserService;
//import com.ssafy.eggmoney.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class StockUserController {
    private final StockUserService stockUserService;
    private final StockLogService stockLogService;
    private final KakaoAuthService kakaoAuthService;
//    private final UserRepository userRepository;

//    @GetMapping("/stock/user/{childId}/available-balance")
//    public ResponseEntity<Map<String, Object>> getInvestablePrice(@PathVariable Long childId,
//                                                                  @RequestHeader("Authorization") String token) {
//        Long familyId = kakaoAuthService.verifyKakaoToken(token).getFamily().getId();
//
//        if(!userRepository.existsByIdAndFamilyId(childId, familyId)) {
//            throw new AccessDeniedException("[증권] 타인의 정보는 볼 수 없습니다.");
//        }
//
//        return new ResponseEntity<>(stockUserService.findInvestablePrice(childId), HttpStatus.OK);
//    }
@GetMapping("/stock/user/available-balance")
public ResponseEntity<Map<String, Object>> getInvestablePrice(@RequestHeader("Authorization") String token) {
    Long userId = kakaoAuthService.verifyKakaoToken(token).getId();
    return new ResponseEntity<>(stockUserService.findInvestablePrice(userId), HttpStatus.OK);
}




//    @GetMapping("/stock/user/{childId}/portfolio")
//    public ResponseEntity<Map<String, Object>> getUserStocks(@PathVariable Long childId,
//                                                             @RequestHeader("Authorization") String token) {
//        Long familyId = kakaoAuthService.verifyKakaoToken(token).getFamily().getId();
//
//        if(!userRepository.existsByIdAndFamilyId(childId, familyId)) {
//            throw new AccessDeniedException("[증권] 타인의 정보는 볼 수 없습니다.");
//        }
//
//        return new ResponseEntity<>(stockUserService.findUserStocks(childId), HttpStatus.OK);
//    }
@GetMapping("/stock/user/portfolio")
public ResponseEntity<Map<String, Object>> getUserStocks(@RequestHeader("Authorization") String token) {
    Long userId = kakaoAuthService.verifyKakaoToken(token).getId();
    return new ResponseEntity<>(stockUserService.findUserStocks(userId), HttpStatus.OK);
}




    @PostMapping("/stock/user/buy")
    public ResponseEntity<Void> buyStock(@RequestBody StockBuyRequest stockBuyReq,
                                                        @RequestHeader("Authorization") String token) {
        Long userId = kakaoAuthService.verifyKakaoToken(token).getId();
        stockUserService.buyStock(stockBuyReq, userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/stock/user/sell")
    public ResponseEntity<Void> sellStock(@RequestBody StockSellRequest stockSellReq,
                                                         @RequestHeader("Authorization") String token) {
        Long userId = kakaoAuthService.verifyKakaoToken(token).getId();
        stockUserService.sellStock(stockSellReq, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/stock/{stockId}/user/info")
    public ResponseEntity<StockUserResponse> getStockUser(@PathVariable Long stockId,
                                                          @RequestHeader("Authorization") String token) {
        Long userId = kakaoAuthService.verifyKakaoToken(token).getId();
        return new ResponseEntity<>(stockUserService.findStockUserInfo(stockId, userId), HttpStatus.OK);
    }

    @GetMapping("/stock/user/log")
    public ResponseEntity<List<StockLogResponse>> getStockLogByUser(@RequestHeader("Authorization") String token) {
        Long userId = kakaoAuthService.verifyKakaoToken(token).getId();
        return new ResponseEntity<>(stockLogService.findStockLogByUserId(userId), HttpStatus.OK);
    }
}
