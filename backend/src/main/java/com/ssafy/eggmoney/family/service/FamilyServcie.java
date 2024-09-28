package com.ssafy.eggmoney.family.service;

import com.ssafy.eggmoney.family.dto.request.ChangeFamilyPresentRequestDto;
import com.ssafy.eggmoney.family.dto.request.ConnectFamilyRequestDto;
import com.ssafy.eggmoney.family.dto.request.CreateFamilyRequestDto;
import com.ssafy.eggmoney.family.dto.response.GetFamilyResponseDto;
import com.ssafy.eggmoney.family.entity.Family;
import com.ssafy.eggmoney.family.repository.FamilyRepository;
import com.ssafy.eggmoney.user.dto.response.GetUserResponseDto;
import com.ssafy.eggmoney.user.entity.User;
import com.ssafy.eggmoney.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class FamilyServcie {
    private final FamilyRepository familyRepository;
    private final UserRepository userRepository;

//    가족 조회
    public GetFamilyResponseDto getFamily(Long familyId){
        Family fam = familyRepository.findById(familyId).get();
        List<User> userList = userRepository.findAllByFamilyId(familyId);

        GetFamilyResponseDto getFamilyResponseDto = GetFamilyResponseDto.builder()
                .familyId(familyId)
                .presentId(fam.getPresentId())
                .intro(fam.getIntro())
                .qrcode(fam.getQrCode())
                .members( userList.stream()
                        .map( user -> GetUserResponseDto.builder()
                                .userId(user.getId())
                                .email(user.getEmail())
                                .realAccount(user.getRealAccount())
                                .role(user.getRole())
                                .name(user.getName())
                                .bank(user.getBank())
                                .pwd(user.getSimplePwd())
                                .stockRatio(user.getStockRatio())
                                .build()
                        )
                        .collect(Collectors.toList())
                )
                .build();

        return getFamilyResponseDto;
    }

//    가족 생성
    public void createFamily(CreateFamilyRequestDto dto, User user) {
        familyRepository.save(Family.builder()
                .intro(dto.getIntro())
                .qrCode(dto.getQrCode())
                .presentId(user.getId())//dto.getPresentId()
                .build());
    }

//    가족 연결
    public void connectFamily(Long familyId, ConnectFamilyRequestDto dto){
        User user = userRepository.findById(dto.getUserId()).get();
        Family fam = familyRepository.findById(familyId).get();
        user.setFamily(fam);
        familyRepository.save(fam);
    }

//    가족 대표 변경
    public void changeFamilyPresent(ChangeFamilyPresentRequestDto dto){
        User user = userRepository.findById(dto.getUserId()).get();
        Family fam = familyRepository.findById(dto.getFamilyId()).get();
        System.out.println(user.getId());
//        유저가 대표를 바꾸려는 가족에 속해있는지 확인하고 변경
        if ( user.getFamily().getId().equals(fam.getId()) ) {
            fam.setPresentId(user.getId());
            System.out.println(fam.getId());
        }
        familyRepository.save(fam);
    }

}
