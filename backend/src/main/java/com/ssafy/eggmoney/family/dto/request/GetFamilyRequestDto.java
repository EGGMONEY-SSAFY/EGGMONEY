package com.ssafy.eggmoney.family.dto.request;

import com.ssafy.eggmoney.family.dto.response.FamilyMemberResponseDto;
import lombok.*;

import java.util.List;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetFamilyRequestDto {
    private Long id;
    private String intro; // 가훈
    private String profileImageUrl; // 가족 이미지 URL
    private List<FamilyMemberResponseDto> members; // 가족 구성원 정보


}
