package com.ssafy.eggmoney.family.controller;

import com.ssafy.eggmoney.auth.service.KakaoAuthService;
import com.ssafy.eggmoney.family.dto.request.ChangeFamilyPresentRequestDto;
import com.ssafy.eggmoney.family.dto.request.ConnectFamilyRequestDto;
import com.ssafy.eggmoney.family.dto.request.CreateFamilyRequestDto;
import com.ssafy.eggmoney.family.dto.response.GetFamilyResponseDto;
import com.ssafy.eggmoney.family.service.FamilyServcie;
import com.ssafy.eggmoney.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/family")
public class FamilyController {

    private final FamilyServcie familyServcie;
    private final KakaoAuthService kakaoAuthService;

//    가족 조회
    @GetMapping("/{familyId}")
    public GetFamilyResponseDto getFamily(@PathVariable("familyId") Long familyId){

        return familyServcie.getFamily(familyId);
    }

//    가족 생성
    @PostMapping("/create")
    public ResponseEntity<String> createFamily(@RequestHeader("Authorization") String token,
                                               @RequestBody CreateFamilyRequestDto dto){
        System.out.println("1");
        token = token.replace("Bearer ","");
        User user = kakaoAuthService.verifyKakaoToken(token);

        familyServcie.createFamily(dto, user);
        return ResponseEntity.ok("가족 생성 완료");
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
