package com.ssafy.eggmoney.family.service;

import com.ssafy.eggmoney.family.dto.request.ConnectFamilyRequestDto;
import com.ssafy.eggmoney.family.dto.request.CreateFamilyRequestDto;
import com.ssafy.eggmoney.family.dto.response.GetFamilyResponseDto;
import com.ssafy.eggmoney.family.entity.Family;
import com.ssafy.eggmoney.family.repository.FamilyRepository;
import com.ssafy.eggmoney.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FamilyServcie {
    private final FamilyRepository familyRepository;
    private final UserRepository userRepository;

//    가족 조회
    public GetFamilyResponseDto getFamily(Long familyId){
        Family fam = familyRepository.findById(familyId).get();
        GetFamilyResponseDto getFamilyResponseDto = GetFamilyResponseDto.builder()
                .presentId(fam.getPresentId())
                .intro(fam.getIntro())
                .qrcode(fam.getQrCode())
                .build();

        return getFamilyResponseDto;
    }

//    가족 생성
    public void createFamily(CreateFamilyRequestDto dto) {
        familyRepository.save(Family.builder()
                .intro(dto.getIntro())
                .qrCode(dto.getQrCode())
                .presentId(dto.getPresentId())
                .build());
    }

//    가족 연결
    public void connectFamily(Long familyId, ConnectFamilyRequestDto dto){
        Family fam = familyRepository.findById(familyId).get();
        fam.setPresentId(dto.getUserId());
        familyRepository.save(fam);
    }

}
