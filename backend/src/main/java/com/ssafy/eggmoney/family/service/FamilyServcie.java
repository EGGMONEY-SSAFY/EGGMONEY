package com.ssafy.eggmoney.family.service;

import com.ssafy.eggmoney.common.exception.ErrorType;
import com.ssafy.eggmoney.family.dto.request.ChangeFamilyPresentRequestDto;
import com.ssafy.eggmoney.family.dto.request.ConnectFamilyRequestDto;
import com.ssafy.eggmoney.family.dto.request.CreateFamilyRequestDto;
import com.ssafy.eggmoney.family.dto.response.FamilyMemberResponseDto;
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
import java.util.NoSuchElementException;
import java.util.Optional;
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
                .profileImageUrl(fam.getProfileImageUrl())
                .members( userList.stream()
                        .filter(user -> !user.getId().equals(fam.getPresentId())) // presentId와 다른 사용자만 포함
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
        Optional<Family> existingFamily = familyRepository.findByPresentId(user.getId());// 해당 유저가 가족 테이블에서 present_id로 이미 가족을 가지고 있는지 확인
        if (existingFamily.isPresent()) {
            throw new IllegalStateException("already Can Find Family");
        }

        Family newFamily = Family.builder()
                        .intro(dto.getIntro())
                                .qrCode(dto.getQrCode())
                                        .presentId(user.getId())
                                            .profileImageUrl(dto.getProfileImageUrl())
                                                    .build();
//        familyRepository.save(Family.builder()
//                .intro(dto.getIntro())
//                .qrCode(dto.getQrCode())
//                .presentId(user.getId())//dto.getPresentId()
//                .build());
        familyRepository.save(newFamily);
        user.setFamily(newFamily);
        userRepository.save(user);

    }

//    가족 연결
    public boolean connectFamily(User user, ConnectFamilyRequestDto dto){
//        User user = userRepository.findById(dto.getUserId()).get();
//        Family fam = familyRepository.findById(user.getId()).get();
        Family fam = familyRepository.findById(dto.getFamilyId())
                .orElseThrow(() -> new IllegalArgumentException("해당 ID를 가진 가족을 찾을 수 없습니다."));

        user.setFamily(fam);
        familyRepository.save(fam);
        userRepository.save(user);
        return true;
    }

//    가족 대표 변경
    public void changeFamilyPresent(User user){
        Optional<Family> opfam = familyRepository.findById(user.getFamily().getId());
        Family family;
        if ( opfam.isPresent() ) {
            family = opfam.get();
        }
        else {
            throw new NoSuchElementException(ErrorType.NOT_FOUND_FAMILY.toString());
        }
        family.setPresentId(user.getId());
        familyRepository.save(family);
    }

    //가족 맴버 조회
    public List<FamilyMemberResponseDto> getFamilyMembers(Long familyId, Long currentUserId){

        List<User> familyMembers = userRepository.findAllByFamilyId(familyId);

        return familyMembers.stream()
                .filter(user -> !user.getId().equals(currentUserId))
                .map(user -> new FamilyMemberResponseDto(
                        user.getId(),
                        user.getName(),
                        user.getRole(),
                        user.getProfileImageUrl()
                ))
                .collect(Collectors.toList());

    }

    // 가족 삭제
    public void deleteFamily(Long familyId){
        Family family = familyRepository.findById(familyId)
                .orElseThrow(()-> new IllegalStateException("Can't Find Family"));

        List<User> familyMembers = userRepository.findAllByFamilyId(familyId);
        for(User member: familyMembers){
            member.setFamily(null);
            userRepository.save(member);
        }
        familyRepository.delete(family);
    }
    // 가족 구성원 삭제
    public void deleteFamilyMember(Long memberId){
         User member = userRepository.findById(memberId)
                         .orElseThrow(()-> new IllegalArgumentException("Can't Find Family Member"));
         member.setFamily(null);
         userRepository.save(member);
    }
    // 가족 정보 업데이트
    public void updateFamily(Long familyId, CreateFamilyRequestDto dto){
        Family family = familyRepository.findById(familyId)
                        .orElseThrow(()->new IllegalArgumentException("Can't Find Family"));

        family.setIntro(dto.getIntro());
        family.setProfileImageUrl(dto.getProfileImageUrl());
        familyRepository.save(family);

    }

}
