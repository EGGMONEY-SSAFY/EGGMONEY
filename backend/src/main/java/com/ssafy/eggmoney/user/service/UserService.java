package com.ssafy.eggmoney.user.service;

import com.ssafy.eggmoney.account.service.AccountService;
import com.ssafy.eggmoney.family.entity.Family;
import com.ssafy.eggmoney.family.repository.FamilyRepository;
import com.ssafy.eggmoney.family.service.FamilyServcie;
import com.ssafy.eggmoney.notification.dto.request.NotificationRequest;
import com.ssafy.eggmoney.notification.entity.NotificationType;
import com.ssafy.eggmoney.notification.service.NotificationService;
import com.ssafy.eggmoney.user.dto.reqeust.CreateUserReqeusetDto;
import com.ssafy.eggmoney.user.dto.reqeust.InvestmentRatioRequest;
import com.ssafy.eggmoney.user.dto.reqeust.UpdateUserRequestDto;
import com.ssafy.eggmoney.user.dto.response.GetUserResponseDto;
import com.ssafy.eggmoney.user.dto.response.InvestmentRatioResponse;
import com.ssafy.eggmoney.user.entity.User;
import com.ssafy.eggmoney.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final AccountService accountService;
    private final FamilyRepository familyRepository;
    private final FamilyServcie familyServcie;
    private final NotificationService notificationService;

//    유저 조회
    public GetUserResponseDto getUser(User user) {
//        User user = userRepository.findById(dto.).get();
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

//        메인 계좌 생성
        accountService.createAccount(user.getId());
    }

    // 유저 정보 업데이트
    @Transactional
    public void updateUser(User user, UpdateUserRequestDto dto){
        user.updateUserInfo(dto.getName(), dto.getBank(), dto.getRealAccount(), dto.getSimplePwd(),dto.getRole());
        User updatedUser = userRepository.save(user);

    }

    public List<InvestmentRatioResponse> findInvestmentRatio(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("[회원] 해당 유저를 찾을 수 없습니다."));
        List<InvestmentRatioResponse> investmentRatios = new ArrayList<>();

        if(user.getRole().equals("부모")) {
            List<User> family = userRepository.findAllByFamilyId(user.getFamily().getId());

            if(family.isEmpty()) {
                throw new NoSuchElementException("[회원] 해당 가족을 찾을 수 없습니다.");
            }

            for(User u : family) {
                if(u.getRole().equals("자녀")) {
                    investmentRatios.add(new InvestmentRatioResponse(u.getId(), u.getName(), u.getStockRatio()));
                }
            }
        } else {
            investmentRatios.add(new InvestmentRatioResponse(userId, user.getName(), user.getStockRatio()));
        }

        return investmentRatios;
    }

    @Transactional
    public int updateInvestmentRatio(Long presentId, InvestmentRatioRequest investmentRatioReq){
        User child = userRepository.findJoinFamilyById(investmentRatioReq.getChildId())
                .orElseThrow(() -> new NoSuchElementException("[회원] 해당 유저를 찾을 수 없습니다."));

        if(!presentId.equals(child.getFamily().getPresentId())) {
            throw new AccessDeniedException("[회원] 투자 비율 설정은 대표 부모만 가능합니다.");
        }

        child.changeStockRatio(investmentRatioReq.getRatio());

        // 알림 생성
        String message = "투자 비율이 " + child.getStockRatio() + "%로 변경되었습니다.";
        NotificationRequest notificationReq = new NotificationRequest(NotificationType.투자비율변경, message, child.getId());
        notificationService.saveNotification(presentId, notificationReq);

        return child.getStockRatio();
    }
}
