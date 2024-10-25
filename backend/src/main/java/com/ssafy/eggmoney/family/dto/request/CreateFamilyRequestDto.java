package com.ssafy.eggmoney.family.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateFamilyRequestDto {
    String intro;
    String qrCode;
    String profileImageUrl;
    Long presentId;
}
