package com.ssafy.eggmoney.family.entity;

import com.ssafy.eggmoney.common.entity.BaseTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
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
    private long presentId;
}
