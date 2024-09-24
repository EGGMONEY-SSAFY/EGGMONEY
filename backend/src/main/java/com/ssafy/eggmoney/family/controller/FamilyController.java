package com.ssafy.eggmoney.family.controller;

import com.ssafy.eggmoney.auth.service.KakaoAuthService;
import com.ssafy.eggmoney.family.dto.request.ChangeFamilyPresentRequestDto;
import com.ssafy.eggmoney.family.dto.request.ConnectFamilyRequestDto;
import com.ssafy.eggmoney.family.dto.request.CreateFamilyRequestDto;
import com.ssafy.eggmoney.family.dto.response.GetFamilyResponseDto;
import com.ssafy.eggmoney.family.service.FamilyServcie;
import com.ssafy.eggmoney.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/family")
public class FamilyController {
    private static final Logger logger = LoggerFactory.getLogger(FamilyController.class);
    private final FamilyServcie familyServcie;
    private final KakaoAuthService kakaoAuthService;

//    가족 조회
    @GetMapping("/{familyId}")
    public GetFamilyResponseDto getFamily(@PathVariable("familyId") Long familyId){

        return familyServcie.getFamily(familyId);
    }

//    가족 생성
// 가족 생성
@PostMapping("/create")
public ResponseEntity<String> createFamily(@RequestHeader(value = "Authorization", required = false) String token,
                                           @RequestBody CreateFamilyRequestDto dto) {
    if (token == null || !token.startsWith("Bearer ")) {
    System.out.println("1");
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Authorization 헤더가 필요합니다.");
    }
    System.out.println(token);
    token = token.replace("Bearer ", "");
    System.out.println(token);
    User user = kakaoAuthService.verifyKakaoToken(token);

    if (user == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 토큰입니다.");
    }
    logger.info("Create Family 요청 시작");
    logger.info("Authorization 헤더 값: {}", token);
    logger.info("CreateFamilyRequestDto 값: {}", dto);

    // 토큰 디버깅
    token = token.replace("Bearer ", "");
    logger.info("Bearer 제거 후 토큰 값: {}", token);

    // 토큰 검증
    //User user = kakaoAuthService.verifyKakaoToken(token);
    if (user == null) {
        logger.error("유효하지 않은 토큰입니다.");
        return ResponseEntity.badRequest().body("유효하지 않은 토큰입니다.");
    }
    logger.info("토큰이 유효합니다. 사용자: {}", user.getId());

    // 가족 생성 서비스 호출
    try {
        familyServcie.createFamily(dto, user);
        logger.info("가족 생성 성공");
        return ResponseEntity.ok("가족 생성 완료");
    } catch (Exception e) {
        logger.error("가족 생성 중 오류 발생", e);
        return ResponseEntity.status(500).body("가족 생성 중 오류 발생");
    }
}
//    public void createFamily(@RequestBody CreateFamilyRequestDto dto){
//        familyServcie.createFamily(dto);
//    }

//    가족 연결
    @PostMapping("/{family_id}/join")
    public void connectFamily(@PathVariable("family_id") Long familyId, @RequestBody ConnectFamilyRequestDto dto) {
        System.out.println("family 연결 Controller");
        familyServcie.connectFamily(familyId, dto);
    }

//    가족 대표 변경
    @PostMapping("/change")
    public void changeFamilyPresent(@RequestBody ChangeFamilyPresentRequestDto dto) {
        familyServcie.changeFamilyPresent(dto);
    }

}
