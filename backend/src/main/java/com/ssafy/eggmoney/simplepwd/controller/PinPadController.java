package com.ssafy.eggmoney.simplepwd.controller;

import com.ssafy.eggmoney.auth.service.KakaoAuthService;
import com.ssafy.eggmoney.simplepwd.dto.request.PinVerificationRequest;
import com.ssafy.eggmoney.simplepwd.dto.response.PinPadResponse;
import com.ssafy.eggmoney.simplepwd.service.EncryptionService;
import com.ssafy.eggmoney.simplepwd.service.PinPadService;
import com.ssafy.eggmoney.user.dto.reqeust.UpdateUserRequestDto;
import com.ssafy.eggmoney.user.entity.User;
import com.ssafy.eggmoney.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
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
    public ResponseEntity<PinPadResponse> getPinPad() throws Exception{
        PinPadResponse response = pinPadService.generatePinPadResponse();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/public-key")
    public ResponseEntity<Map<String,String>> getPublicKey(){
        Map<String,String> response = new HashMap<>();
        response.put("publicKey", Base64.getEncoder().encodeToString(encryptionService.getPublicKey().getEncoded()));
        return ResponseEntity.ok(response);
    }
    @PostMapping("/api/pinpad/verify")
    public ResponseEntity<Map<String, String>> verifyPin(@RequestHeader(value="Authorization") String token,@RequestBody Map<String, String> requestBody) throws Exception{
//        byte[] decodedPassword = Base64.getDecoder().decode(encryptyedPassword);

//        Cipher cipher = Cipher.getInstance("RSA");
//        cipher.init(Cipher.DECRYPT_MODE, encryptionService.getPrivateKey());
//        byte[] decryptedPassword = cipher.doFinal(decodedPassword);
//
//        String decryptedPasswordString = new String(decryptedPassword, StandardCharsets.UTF_8);
//        System.out.println("복호화된 비밀번호:" + decryptedPasswordString);
//
        Map<String, String> response = new HashMap<>();
        User user = kakaoAuthService.verifyKakaoToken(token);
        String encryptedPin = requestBody.get("encryptedPin");
        UpdateUserRequestDto updateUserRequestDto = new UpdateUserRequestDto();
        updateUserRequestDto.setSimplePwd(encryptedPin);
        userService.updateUser(user.getId(), updateUserRequestDto);
        response.put("message","비밀번호 생성 성공");
//        response.put("Pwd",decryptedPasswordString);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/api/pinpad/verify/check")
    public ResponseEntity<Map<String, String>> checkUserSimplePwd(@RequestHeader(value="Authorization") String token, @RequestBody PinVerificationRequest request) throws Exception{
        String encryptedPassword = request.getEncryptedPin();

        User user = kakaoAuthService.verifyKakaoToken(token);

        // 저장된 비밀번호 확인
        String userStoredPwd = userService.getUser(user.getId()).getPwd();

        Map<String, String> response = new HashMap<>();
//        if(decryptedPassword.equals(userStoredPwd)){
        if(encryptedPassword.equals(userStoredPwd)){
            response.put("status","success");
        }else{
            response.put("status","fail");
        }
        System.out.println(response);
        return ResponseEntity.ok(response);
    }
}
