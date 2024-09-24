package com.ssafy.eggmoney.simplepwd.controller;

import com.ssafy.eggmoney.global.dto.ResponseApi;
import com.ssafy.eggmoney.simplepwd.dto.response.PinPadResponse;
import com.ssafy.eggmoney.simplepwd.service.EncryptionService;
import com.ssafy.eggmoney.simplepwd.service.PinPadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

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
    public PinPadResponse getPinPad() throws Exception{
        return pinPadService.generatePinPadResponse();
    }

    @GetMapping("/api/public-key")
    public String getPublicKey(){
        return Base64.getEncoder().encodeToString(encryptionService.getPublicKey().getEncoded());
    }
    @PostMapping("/api/pinpad/verify")
    public String verifyPin(@RequestBody String encryptyedPassword) throws Exception{
        byte[] decodedPassword = Base64.getDecoder().decode(encryptyedPassword);

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, encryptionService.getPrivateKey());
        byte[] decryptedPassword = chiper.doFinal(decodedPassword);

        String decryptedPasswordString = new String(decryptedPassword, StandardCharsets.UTF_8);
        System.out.println("복호화된 비밀번호:" + decryptedPasswordString);

        return ResponseApi.success("비밀번호 검증 성공");
    }
}
