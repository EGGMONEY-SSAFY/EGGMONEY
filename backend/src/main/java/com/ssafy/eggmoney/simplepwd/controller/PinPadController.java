package com.ssafy.eggmoney.simplepwd.controller;

import com.ssafy.eggmoney.auth.service.KakaoAuthService;
import com.ssafy.eggmoney.simplepwd.dto.request.PinVerificationRequest;
import com.ssafy.eggmoney.simplepwd.dto.response.PinPadResponse;
import com.ssafy.eggmoney.simplepwd.service.EncryptionService;
import com.ssafy.eggmoney.simplepwd.service.PinPadService;
import com.ssafy.eggmoney.user.dto.reqeust.UpdateUserRequestDto;
import com.ssafy.eggmoney.user.entity.User;
import com.ssafy.eggmoney.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class PinPadController {
    private final PinPadService pinPadService;
    private final EncryptionService encryptionService;
    private final KakaoAuthService kakaoAuthService;
    private final UserService userService;


    @Autowired
    public PinPadController(PinPadService pinPadService, EncryptionService encryptionService, KakaoAuthService kakaoAuthService, UserService userService){
        this.pinPadService=pinPadService;
        this.encryptionService=encryptionService;
        this.kakaoAuthService = kakaoAuthService;
        this.userService = userService;
    }

    @GetMapping(value = "/api/pinpad", produces = "application/json")
    public ResponseEntity<PinPadResponse> getPinPad(HttpSession session) throws Exception{
        PinPadResponse response = pinPadService.generatePinPadResponse(session);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/public-key")
    public ResponseEntity<Map<String,String>> getPublicKey(){
        Map<String,String> response = new HashMap<>();
        response.put("publicKey", Base64.getEncoder().encodeToString(encryptionService.getPublicKey().getEncoded()));
        return ResponseEntity.ok(response);
    }
    @PostMapping("/api/pinpad/verify")
    public ResponseEntity<Map<String, String>> verifyPin(@RequestHeader(value="Authorization") String token,
                                                         @RequestBody Map<String, String> requestBody, HttpSession session) throws Exception{
//        byte[] decodedPassword = Base64.getDecoder().decode(encryptyedPassword);

//        Cipher cipher = Cipher.getInstance("RSA");
//        cipher.init(Cipher.DECRYPT_MODE, encryptionService.getPrivateKey());
//        byte[] decryptedPassword = cipher.doFinal(decodedPassword);
//
//        String decryptedPasswordString = new String(decryptedPassword, StandardCharsets.UTF_8);
//        System.out.println("복호화된 비밀번호:" + decryptedPasswordString);
//
        // 세션에서 섞인 카드 순서를 가져옴
        List<Integer> shuffledPinPad = (List<Integer>) session.getAttribute("shuffledPinPad");
        if (shuffledPinPad == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "세션이 만료되었습니다."));
        }
        Map<String, String> response = new HashMap<>();
        User user = kakaoAuthService.verifyKakaoToken(token);
        String encryptedPin = requestBody.get("encryptedPin");
        // 암호화된 핀패드 처리
        List<Integer> decryptedPin = encryptionService.decryptPin(encryptedPin,session);
        System.out.println(decryptedPin);
        // 복호화된 숫자 리스트를 문자열로 변환
        StringBuilder simplePwdBuilder = new StringBuilder();
        for (Integer num : decryptedPin) {
            simplePwdBuilder.append(num);  // 숫자를 문자열로 변환하여 하나의 문자열로 합침
        }
        String simplePwd = simplePwdBuilder.toString();
        UpdateUserRequestDto updateUserRequestDto = new UpdateUserRequestDto();
        updateUserRequestDto.setSimplePwd(simplePwd);
        //encryptedPin
        userService.updateUser(user, updateUserRequestDto);
        response.put("message","비밀번호 생성 성공");
//        response.put("Pwd",decryptedPasswordString);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/api/pinpad/verify/check")
    public ResponseEntity<Map<String, String>> checkUserSimplePwd(@RequestHeader(value="Authorization") String token, @RequestBody PinVerificationRequest request,HttpSession session) throws Exception{
        String encryptedPassword = request.getEncryptedPin();

        User user = kakaoAuthService.verifyKakaoToken(token);

        // 저장된 비밀번호 확인
        String userStoredPwd = userService.getUser(user).getPwd();
        System.out.println(encryptedPassword);
        // 암호화된 핀패드 복호화
        List<Integer> decryptedPin = encryptionService.decryptPin(encryptedPassword, session);
        // 복호화된 숫자 리스트를 문자열로 변환
        StringBuilder decryptedPinBuilder = new StringBuilder();
        for (Integer num : decryptedPin) {
            decryptedPinBuilder.append(num);  // 숫자를 문자열로 변환하여 하나의 문자열로 합침
        }

        // 사용자가 입력한 비밀번호의 문자열 (복호화 후 하나로 합친 것)
        String decryptedPinString = decryptedPinBuilder.toString();

        System.out.println("복호화된 비밀번호: " + decryptedPinString);
        System.out.println("저장된 비밀번호: " + userStoredPwd);

        Map<String, String> response = new HashMap<>();
//        if(decryptedPassword.equals(userStoredPwd)){
        if(decryptedPinString.equals(userStoredPwd)){
            response.put("status","success");
        }else{
            response.put("status","fail");
        }
        System.out.println(response);
        return ResponseEntity.ok(response);
    }
}
