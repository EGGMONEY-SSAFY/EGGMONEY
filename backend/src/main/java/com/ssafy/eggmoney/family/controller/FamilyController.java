package com.ssafy.eggmoney.family.controller;

import com.ssafy.eggmoney.allowance.dto.response.AllowanceCreateResponseDto;
import com.ssafy.eggmoney.allowance.service.AllowanceService;
import com.ssafy.eggmoney.auth.service.KakaoAuthService;
import com.ssafy.eggmoney.common.service.S3Service;
import com.ssafy.eggmoney.family.dto.request.ChangeFamilyPresentRequestDto;
import com.ssafy.eggmoney.family.dto.request.ConnectFamilyRequestDto;
import com.ssafy.eggmoney.family.dto.request.CreateFamilyRequestDto;
import com.ssafy.eggmoney.family.dto.response.FamilyMemberResponseDto;
import com.ssafy.eggmoney.family.dto.response.GetFamilyResponseDto;
import com.ssafy.eggmoney.family.service.FamilyServcie;
import com.ssafy.eggmoney.notification.dto.request.NotificationRequest;
import com.ssafy.eggmoney.notification.entity.NotificationType;
import com.ssafy.eggmoney.notification.service.NotificationService;
import com.ssafy.eggmoney.user.dto.response.DeleteFamilyMemberRequestDto;
import com.ssafy.eggmoney.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/family")
public class FamilyController {
    private static final Logger logger = LoggerFactory.getLogger(FamilyController.class);
    private final FamilyServcie familyServcie;
    private final KakaoAuthService kakaoAuthService;
    private final S3Service s3Service;
    private final AllowanceService allowanceService;
    private final NotificationService notificationService;



//    가족 조회
    @GetMapping("/{familyId}")
    public GetFamilyResponseDto getFamily(@PathVariable("familyId") Long familyId){

        return familyServcie.getFamily(familyId);
    }
    @GetMapping("/searchFamily")
    public GetFamilyResponseDto getFamily(@RequestHeader(value = "Authorization") String token){

        User user = kakaoAuthService.verifyKakaoToken(token);
        Long familyId = user.getFamily().getId();
        return familyServcie.getFamily(familyId);
    }
    @GetMapping("/checkFamilyLeader")
    public Map<String,String>getFamilyleader(@RequestHeader(value = "Authorization") String token, @RequestBody Map<String, Long> famId){
        User user = kakaoAuthService.verifyKakaoToken(token);
        Long familyId = user.getFamily().getPresentId();
        if(familyId.equals(famId.get("famId"))){
            return Collections.singletonMap("status", "success");
        } else {
            // 리더가 일치하지 않는 경우 실패 상태 반환
            return Collections.singletonMap("status", "fail");
        }
    }
    @PostMapping("/approvenoti")
    public ResponseEntity<Void> sendApproveNotification(@RequestHeader("Authorization") String token, @RequestBody Map<String, Long> body) {
        // Kakao 토큰 검증 후, 요청 보낸 사용자의 ID 확인
        Long sendUserId = kakaoAuthService.verifyKakaoToken(token).getId();

        Long userId = body.get("userId");
        NotificationRequest notificationRequest = NotificationRequest.builder()
                .receiveUserId(userId)  // 알림을 받을 사용자 ID
                .notificationType(NotificationType.승인대기)  // 알림 유형 설정
                .message("승인대기 중 입니다.")  // 알림 메시지 설정
                .build();
        // 알림 서비스 호출하여 알림 저장
        notificationService.saveNotification(sendUserId, notificationRequest);

        // 성공적으로 처리된 경우 OK 상태 반환
        return ResponseEntity.ok().build();
    }
    // 승인 대기 알람@PostMapping("/notification/send")
    //    public ResponseEntity<Void> sendNotification(@RequestHeader("Authorization") String token,
    //                                                 @RequestBody NotificationRequest notificationReq) {
    //        Long sendUserId = kakaoAuthService.verifyKakaoToken(token).getId();
    //        notificationService.saveNotification(sendUserId, notificationReq);
    //        return ResponseEntity.ok().build();
    //    }
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
    public void changeFamilyPresent(@RequestHeader(value = "Authorization") String token) {
        User user = kakaoAuthService.verifyKakaoToken(token);
        familyServcie.changeFamilyPresent(user);
    }


    // Token 기반 가족 연결
    @PostMapping("/join/fam")
    public ResponseEntity<Map<String, Object>> connectFamily(
                              @RequestHeader(value = "Authorization", required = false) String token,
                              @RequestBody ConnectFamilyRequestDto dto) {

        User user = kakaoAuthService.verifyKakaoToken(token);
//        familyServcie.connectFamily(user, dto);
        boolean isConnected = familyServcie.connectFamily(user, dto);

        Map<String, Object> response = new HashMap<>();
        if (isConnected) {
            response.put("status", "success");
            response.put("message", "Family connected successfully.");
            response.put("additionalData", "예시 데이터"); // 필요한 추가 데이터
            allowanceService.createAllowance(user);
            return ResponseEntity.ok(response);
        } else {
            response.put("status", "fail");
            response.put("message", "Failed to connect family.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
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
    @PostMapping("/delete")
    public ResponseEntity<String> deleteFamily(@RequestHeader(value = "Authorization", required = false) String token){
        User user = kakaoAuthService.verifyKakaoToken(token);
        Long familyId = user.getFamily().getId();
        familyServcie.deleteFamily(familyId);
        return ResponseEntity.ok("가족 삭제 완료");
    }

    // 소속 멤버 삭제
    @PostMapping("/delete/member")
    public ResponseEntity<String> deleteFamilyMember(@RequestHeader(value = "Authorization", required = false) String token, @RequestBody DeleteFamilyMemberRequestDto requestDto){
        Long memberId = requestDto.getMemberId();
        if (memberId == null) {
            return ResponseEntity.badRequest().body("Member ID가 누락되었습니다.");
        }
        familyServcie.deleteFamilyMember(memberId);
        return ResponseEntity.ok("구성원 삭제 완료");
    }

    // 소속 가족 정보 변경
    @PostMapping("/update")
    public ResponseEntity<String> updateFamily(@RequestHeader(value = "Authorization", required = false) String token, @RequestBody CreateFamilyRequestDto dto){
        User user = kakaoAuthService.verifyKakaoToken(token);
        Long familyId = user.getFamily().getId();
        familyServcie.updateFamily(familyId,dto);
        return ResponseEntity.ok("가족 정보 업데이트 완료");

    }
    @PostMapping("/upload-profile")
    public ResponseEntity<Map<String, String>> uploadProfileImage(
            @RequestHeader(value="Authorization") String token,
            @RequestParam("file") MultipartFile file){
        try {
            User user = kakaoAuthService.verifyKakaoToken(token);
            String fileUrl = s3Service.uploadFile(file);

            Map<String, String> response = new HashMap<>();
            response.put("imageUrl", fileUrl);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
//            String fileName = file.getOriginalFilename();
//            String tempFilePath = System.getProperty("java.io.tmpdir") + "/"+fileName;
//            file.transferTo(new File(tempFilePath));
//
//            s3Service.uploadFile(fileName,tempFilePath);
//            String fileUrl = s3Service.uploadFile(file);