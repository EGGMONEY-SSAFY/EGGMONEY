package com.ssafy.eggmoney.withdrawal.dto.response;

import com.ssafy.eggmoney.user.dto.response.GetUserResponseDto;
import com.ssafy.eggmoney.withdrawal.entity.WithdrawalStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class GetWithdrawalResponseDto {
//    ID
    Long withdrawalId;
//    신청한 사람
    GetUserResponseDto applyer;
//    신청받은 사람
    GetUserResponseDto applyee;
//    가격
    int withdrawalPrice;
//    상태
    WithdrawalStatus type;
//    생성일
    LocalDateTime createdAt;
//    수정일
    LocalDateTime updatedAt;
}
