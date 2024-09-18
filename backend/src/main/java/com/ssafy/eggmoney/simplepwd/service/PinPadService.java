package com.ssafy.eggmoney.simplepwd.service;

import com.ssafy.eggmoney.simplepwd.dto.response.PinPadResponse;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PinPadService {
    private final EncryptionService encryptionService;

    public PinPadService(EncryptionService encryptionService){
        this.encryptionService = encryptionService;
    }

    public PinPadResponse generatePinPadResponse() throws Exception{
        List<Integer> pinPad = IntStream.range(0,9).boxed().collect(Collectors.toList());
        Collections.shuffle(pinPad);
        String encryptedImage = encryptionService.encryptImage(generateIamge(pinPad));
        return new PinPadResponse(pinPad, encryptedImage);
    }

    private byte[] generateIamge(List<Integer> pinPad){
        //이미지 패드 생성
        return new byte[0];
    }
}
