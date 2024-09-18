package com.ssafy.eggmoney.simplepwd.dto.response;

import java.util.List;

public class PinPadResponse {
    private List<Integer> pinPad;
    private String encryptedImage;

    public PinPadResponse(List<Integer> pinPad, String encryptedImage) {
        this.pinPad = pinPad;
        this.encryptedImage = encryptedImage;
    }
}
