package com.ssafy.eggmoney.user.service;

import com.ssafy.eggmoney.account.service.AccountService;
import com.ssafy.eggmoney.family.entity.Family;
import com.ssafy.eggmoney.family.repository.FamilyRepository;
import com.ssafy.eggmoney.family.service.FamilyServcie;
import com.ssafy.eggmoney.user.dto.reqeust.CreateUserReqeusetDto;
import com.ssafy.eggmoney.user.dto.reqeust.UpdateUserRequestDto;
import com.ssafy.eggmoney.user.dto.response.GetUserResponseDto;
import com.ssafy.eggmoney.user.entity.User;
import com.ssafy.eggmoney.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
//@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final AccountService accountService;
    private final FamilyRepository familyRepository;
    private final FamilyServcie familyServcie;

//    유저 조회
    public GetUserResponseDto getUser(Long userId) {
        User user = userRepository.findById(userId).get();
        Family fam = user.getFamily();

        GetUserResponseDto.GetUserResponseDtoBuilder builder = GetUserResponseDto.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole())
                .realAccount(user.getRealAccount())
                .bank(user.getBank())
                .pwd(user.getSimplePwd());

        // family가 null이 아닌 경우에만 familyService 호출
        if (fam != null) {
            builder.family(familyServcie.getFamily(fam.getId()));
        }

        return builder.build();
    }

//    유저 생성 ( 생성과 동시에 메인 계좌 생성 )
    public void createUser(CreateUserReqeusetDto dto) {
//        유저 생성
        User user = User.builder()
                .email(dto.getEmail())
                .name(dto.getName())
                .role(dto.getRole())
                .realAccount(dto.getRealAccount())
                .bank(dto.getBank())
                .simplePwd(dto.getPwd())
                .build();
//        자녀 주식제한비율 설정
        if ( dto.getRole().equals("자녀") )
            user.setStockRatio(50);
        userRepository.saveAndFlush(user);
        System.out.println("유저 생성 완료");

//        메인 계좌 생성
        accountService.createAccount(user.getId());
        System.out.println("계좌 생성 완료");
    }

    // 유저 정보 업데이트
    public void updateUser(Long userId, UpdateUserRequestDto dto){
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            user.updateUserInfo(dto.getName(), dto.getBank(), dto.getRealAccount(), dto.getSimplePwd(),dto.getRole());
            userRepository.save(user);
        }else {
            throw new IllegalArgumentException("유저를 찾을 수 없습니다.");
        }
    }

    public int findInvestableRatio(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NoSuchElementException("유저를 찾을 수 없습니다.")
        );

        return user.getStockRatio();
    }

    public User findUserEntity(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("해당 유저가 조회되지 않습니다."));
    }
}
