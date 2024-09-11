package com.ssafy.eggmoney.family.service;

import com.ssafy.eggmoney.family.controller.FamilyController;
import com.ssafy.eggmoney.family.dto.response.GetFamilyResponseDto;
import com.ssafy.eggmoney.family.entity.Family;
import com.ssafy.eggmoney.family.repository.FamilyRepository;
import com.ssafy.eggmoney.user.entity.User;
import com.ssafy.eggmoney.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FamilyServcie {
    private final FamilyRepository familyRepository;
    private final UserRepository userRepository;

//    가족 조회
    public GetFamilyResponseDto getFamily(){
//    일단 고정값 넣어놓음
        User user = userRepository.findById(1L).get();

        Family fam = familyRepository.findById(user.getFamily().getId()).get();
        GetFamilyResponseDto getFamilyResponseDto = GetFamilyResponseDto.builder()
                .presentId(fam.getPresentId())
                .intro(fam.getIntro())
                .qrcode(fam.getQrCode())
                .build();

        return getFamilyResponseDto;
    }

    public

}
