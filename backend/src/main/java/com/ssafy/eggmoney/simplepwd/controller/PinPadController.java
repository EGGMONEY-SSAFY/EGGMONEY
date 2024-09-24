package com.ssafy.eggmoney.simplepwd.controller;

import com.ssafy.eggmoney.simplepwd.dto.response.PinPadResponse;
import com.ssafy.eggmoney.simplepwd.service.EncryptionService;
import com.ssafy.eggmoney.simplepwd.service.PinPadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
