package com.ssafy.eggmoney.simplepwd.controller;

import com.ssafy.eggmoney.global.dto.ResponseApi;
import com.ssafy.eggmoney.simplepwd.dto.response.PinPadResponse;
import com.ssafy.eggmoney.simplepwd.service.EncryptionService;
import com.ssafy.eggmoney.simplepwd.service.PinPadService;
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

    @Autowired
    public PinPadController(PinPadService pinPadService, EncryptionService encryptionService){
        this.pinPadService=pinPadService;
        this.encryptionService=encryptionService;
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
    public ResponseEntity<Map<String, String>> verifyPin(@RequestBody String encryptyedPassword) throws Exception{
        byte[] decodedPassword = Base64.getDecoder().decode(encryptyedPassword);

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, encryptionService.getPrivateKey());
        byte[] decryptedPassword = cipher.doFinal(decodedPassword);

        String decryptedPasswordString = new String(decryptedPassword, StandardCharsets.UTF_8);
        System.out.println("복호화된 비밀번호:" + decryptedPasswordString);

        Map<String, String> response = new HashMap<>();
        response.put("message","비밀번호 검증 성공");
        return ResponseEntity.ok(response);
    }
}