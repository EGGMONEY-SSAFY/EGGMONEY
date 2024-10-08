package com.ssafy.eggmoney.family.dto.response;

import com.ssafy.eggmoney.family.entity.Family;
import com.ssafy.eggmoney.user.dto.response.GetUserResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetFamilyResponseDto {
    Long familyId;
    Long presentId;
    String intro;
    String qrcode;
    String profileImageUrl;
    List<GetUserResponseDto> members;
}
