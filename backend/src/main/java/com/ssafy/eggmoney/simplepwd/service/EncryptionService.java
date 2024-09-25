package com.ssafy.eggmoney.simplepwd.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

@Service
public class EncryptionService {
    private PublicKey publicKey;
    private PrivateKey privateKey;
    private final String secretKey;

    @PostConstruct
    public void initKeys() throws Exception{
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        publicKey = keyPair.getPublic();
        privateKey = keyPair.getPrivate();
    }

    public PublicKey getPublicKey(){
        return publicKey;
    }

    public PrivateKey getPrivateKey(){
        return privateKey;
    }
    public EncryptionService(@Value("${secret.aesKey}") String secretKey) {
        this.secretKey = secretKey;
    }

    private SecretKey generateKey() throws Exception{
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128);
        return keyGen.generateKey();
    }

    public String encryptImage(byte[] imageBytes) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            //cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            SecretKeySpec keySpec =new SecretKeySpec(secretKey.getBytes(), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] encryptedImage = cipher.doFinal(imageBytes);
            if (encryptedImage == null || encryptedImage.length == 0) {
                throw new RuntimeException("이미지 암호화 실패: 암호화된 데이터가 유효하지 않습니다.");
            }
            return Base64.getEncoder().encodeToString(encryptedImage);
        } catch (Exception e) {
            throw new Exception("이미지 암호화 중 오류가 발생했습니다.", e);
        }
    }
}
// private final SecretKey secretKey;
//
//    public EncryptionService() throws Exception{
//        this.secretKey = generateKey();
//    }
//
//    private SecretKey generateKey() throws Exception{
//        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
//        keyGen.init(128);
//        return keyGen.generateKey();
//    }
//
//    public String encryptImage(byte[] imageBytes) throws Exception {
//        Cipher cipher = Cipher.getInstance("AES");
//        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
//        byte[] encryptedImage = cipher.doFinal(imageBytes);
//        return Base64.getEncoder().encodeToString(encryptedImage);
//    }