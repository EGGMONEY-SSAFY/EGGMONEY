package com.ssafy.eggmoney.family.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FamilyMemberResponseDto {
    private Long id;
    private String name;
    private String role;
    private String profileImageUrl;

}
