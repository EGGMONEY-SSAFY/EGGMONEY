package com.ssafy.eggmoney.simplepwd.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

@Service
public class EncryptionService {
    private PublicKey publicKey;
    private PrivateKey privateKey;
    private final SecretKey secretKey;

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
    public EncryptionService() throws Exception{
        this.secretKey = generateKey();
    }

    private SecretKey generateKey() throws Exception{
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128);
        return keyGen.generateKey();
    }

    public String encryptImage(byte[] imageBytes) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedImage = cipher.doFinal(imageBytes);
        return Base64.getEncoder().encodeToString(encryptedImage);
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