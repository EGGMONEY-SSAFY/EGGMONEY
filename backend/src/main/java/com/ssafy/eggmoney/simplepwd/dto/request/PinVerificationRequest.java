package com.ssafy.eggmoney.simplepwd.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PinVerificationRequest {
    private String encryptedPin;

}
