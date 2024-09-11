package com.ssafy.eggmoney.user.entity;

import com.ssafy.eggmoney.family.entity.Family;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "family_id")
    private Family family;

    private String email;
    private String name;
    private String role;
    private String realAccount;
    private String bank;
    private String pwd;

    @ColumnDefault("50")
    private int stockRatio;
}
