package com.ssafy.eggmoney.simplepwd.service;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

public class EncryptionService {
    private final SecretKey secretKey;

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
