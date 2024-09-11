package com.ssafy.eggmoney.family.controller;

import com.ssafy.eggmoney.family.dto.response.GetFamilyResponseDto;
import com.ssafy.eggmoney.family.service.FamilyServcie;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/family")
public class FamilyController {

    private final FamilyServcie familyServcie;

    @GetMapping("")
    public GetFamilyResponseDto getFamily(){
        return familyServcie.getFamily();
    }
}
