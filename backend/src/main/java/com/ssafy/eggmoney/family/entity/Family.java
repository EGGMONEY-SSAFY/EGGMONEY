package com.ssafy.eggmoney.family.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ssafy.eggmoney.common.entity.BaseTime;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Setter
@Entity
@Table(name = "families")
@NoArgsConstructor(access = PROTECTED)
public class Family extends BaseTime {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "family_id")
    private Long id;

    private String intro;
    private String qrCode;
    private Long presentId;
    private String profileImageUrl;

//    대표 ID 설정하기
    public void setPresentId(Long userId) {
        this.presentId = userId;
    }

    // Intro 설정하기
    public void setIntro(String intro){this.intro = intro;}

    @Builder
    private Family(String intro, String qrCode, Long presentId, String profileImageUrl) {
        this.intro = intro;
        this.qrCode = qrCode;
        this.presentId = presentId;
        this.profileImageUrl = profileImageUrl;
    }
}
