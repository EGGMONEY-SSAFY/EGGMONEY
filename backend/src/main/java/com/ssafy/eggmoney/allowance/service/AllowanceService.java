package com.ssafy.eggmoney.allowance.service;


import com.ssafy.eggmoney.allowance.dto.request.AllowanceGetRequestDto;
import com.ssafy.eggmoney.allowance.dto.response.AllowanceCreateResponseDto;
import com.ssafy.eggmoney.allowance.dto.response.AllowanceUpdateResponseDto;
import com.ssafy.eggmoney.allowance.entity.Allowance;
import com.ssafy.eggmoney.allowance.entity.AllowancePeriod;
import com.ssafy.eggmoney.allowance.repository.AllowanceRepository;
import com.ssafy.eggmoney.notification.dto.request.NotificationRequest;
import com.ssafy.eggmoney.notification.entity.NotificationType;
import com.ssafy.eggmoney.notification.service.NotificationService;
import com.ssafy.eggmoney.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AllowanceService {

    private final AllowanceRepository allowanceRepository;
    private final NotificationService notificationService;


    public AllowanceCreateResponseDto createAllowance(User user){
        if("자녀".equals(user.getRole())){
            Allowance allowance = Allowance.createAllowance(user,0,AllowancePeriod.MONTH,0);
            allowanceRepository.save(allowance);

            return new AllowanceCreateResponseDto(allowance);
        }
        throw new IllegalArgumentException("user is not your child");
    }
    public void deleteAllowance(User user){
        Allowance allowance = allowanceRepository.findByChildId(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 용돈 정보가 존재하지 않습니다."));

        // 삭제 권한 체크: 자녀의 용돈인지 확인
        if (!allowance.getChild().getId().equals(user.getId())) {
            throw new IllegalArgumentException("해당 용돈을 삭제할 권한이 없습니다.");
        }
        allowanceRepository.delete(allowance);
    }

    public AllowanceUpdateResponseDto updateAllowance(Long allowanceId, AllowanceUpdateResponseDto updateDto){
          Allowance allowance = allowanceRepository.findById(allowanceId)
                  .orElseThrow(() -> new IllegalArgumentException("해당 ID의 용돈 정보가 존재하지 않습니다."));
//        Allowance allowance = allowanceRepository.findByChildId(childId)
//                .orElseThrow(()-> new IllegalArgumentException("User didnt have allowance"));
        allowance.setPrice(updateDto.getPrice());
        allowance.setAllowanceDay(updateDto.getAllowanceDay());
        allowance.setAllowancePeriod(AllowancePeriod.valueOf(updateDto.getAllowancePeriod()));
        allowanceRepository.save(allowance);

        NotificationRequest notificationRequest = NotificationRequest.builder()
                .notificationType(NotificationType.용돈변경)
                .message("용돈이 변경되었습니다.")
                .build();

//        allowanceId에서 해당하는 userId를 찾는다
        Long userId = allowance.getChild().getId();
        notificationService.saveNotification(userId, notificationRequest);

        return new AllowanceUpdateResponseDto(allowance);
    }

    public List<AllowanceGetRequestDto> getAllowancesForChildren(User user){
        return allowanceRepository.findAllByChildFamilyId(user.getFamily().getId()).stream()
                .map(AllowanceGetRequestDto::new)
                .collect(Collectors.toList());
    }
}
