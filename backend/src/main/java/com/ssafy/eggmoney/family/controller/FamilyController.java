package com.ssafy.eggmoney.family.controller;

import com.ssafy.eggmoney.auth.service.KakaoAuthService;
import com.ssafy.eggmoney.family.dto.request.ChangeFamilyPresentRequestDto;
import com.ssafy.eggmoney.family.dto.request.ConnectFamilyRequestDto;
import com.ssafy.eggmoney.family.dto.request.CreateFamilyRequestDto;
import com.ssafy.eggmoney.family.dto.response.FamilyMemberResponseDto;
import com.ssafy.eggmoney.family.dto.response.GetFamilyResponseDto;
import com.ssafy.eggmoney.family.service.FamilyServcie;
import com.ssafy.eggmoney.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/family")
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


    User user = kakaoAuthService.verifyKakaoToken(token);

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
//    @PostMapping("/{family_id}/join")
//    public void connectFamily(@PathVariable("family_id") Long familyId, @RequestBody ConnectFamilyRequestDto dto) {
//        System.out.println("family 연결 Controller");
//        familyServcie.connectFamily(familyId, dto);
//    }

//    가족 대표 변경
    @PostMapping("/change")
    public void changeFamilyPresent(@RequestBody ChangeFamilyPresentRequestDto dto) {
        familyServcie.changeFamilyPresent(dto);
    }


    // Token 기반 가족 연결
    @PostMapping("/{family_id}/join")
    public void connectFamily(@PathVariable("family_id") Long familyId,
                              @RequestHeader(value = "Authorization", required = false) String token,
                              @RequestBody ConnectFamilyRequestDto dto) {

        User user = kakaoAuthService.verifyKakaoToken(token);
        familyServcie.connectFamily(familyId, user, dto);
    }
    // Token 기반 가족 멤버 조회
    @GetMapping("/searchMember")
    public ResponseEntity<List<FamilyMemberResponseDto>> searchFamilyMembers(@RequestHeader(value = "Authorization") String token){
        User user = kakaoAuthService.verifyKakaoToken(token);

        List<FamilyMemberResponseDto> familyMembers = familyServcie.getFamilyMembers(user.getFamily().getId(), user.getId());
        //familyServcie.searchFamily(user.getFamily());
        return ResponseEntity.ok(familyMembers);
    }

    // 생성된 가족 삭제
    // 삭제하면서 해당 가족에 소속된 User들 가족 id null값으로 변경
    @PostMapping("/{family_id}/delete")
    public ResponseEntity<String> deleteFamily(@PathVariable("familyId") Long familyId){

        familyServcie.deleteFamily(familyId);
        return ResponseEntity.ok("가족 삭제 완료");
    }

    // 소속 멤버 삭제
    @PostMapping("/delete/member/{memberId}")
    public ResponseEntity<String> deleteFamilyMember(@PathVariable("memberId") Long memberId){
        familyServcie.deleteFamilyMember(memberId);
        return ResponseEntity.ok("구성원 삭제 완료");
    }

    // 소속 가족 정보 변경
    @PostMapping("/{family_id}/update")
    public ResponseEntity<String> updateFamily(@PathVariable("familyId") Long familyId, @RequestBody CreateFamilyRequestDto dto){

        familyServcie.updateFamily(familyId,dto);
        return ResponseEntity.ok("가족 정보 업데이트 완료");

    }
}
