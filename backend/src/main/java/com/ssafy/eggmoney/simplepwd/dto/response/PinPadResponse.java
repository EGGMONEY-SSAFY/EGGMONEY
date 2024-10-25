package com.ssafy.eggmoney.simplepwd.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PinPadResponse {
    private List<Integer> pinPad;
    private String image;
    private byte[] imageBytes;
    private String encryptedImage;

   // public PinPadResponse(){}

    public PinPadResponse(List<Integer> pinPad, String encryptedImage) {
        this.pinPad = pinPad;
        this.encryptedImage = encryptedImage;
    }
//    public PinPadResponse(List<Integer> pinPad, byte[] imageBytes) {
//        this.pinPad = pinPad;
//        this.imageBytes = imageBytes;
//    }

}
