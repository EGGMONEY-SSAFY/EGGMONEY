package com.ssafy.eggmoney.user.entity;

import com.ssafy.eggmoney.account.entity.Account;
import com.ssafy.eggmoney.common.entity.BaseTime;
import com.ssafy.eggmoney.family.entity.Family;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor(access = PROTECTED)
public class User extends BaseTime {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "family_id")
    private Family family;

    @Column(unique = true)
    private String email;

    private String name;
    private String role;
    private String realAccount;
    private String bank;
    private String simplePwd;

    private String profileImageUrl;

    @ColumnDefault("50")
    private int stockRatio;

    @Builder
    private User(String email, String name, String role, String realAccount, String bank,
                 String simplePwd) {
        this.email = email;
        this.name = name;
        this.role = role;
        this.realAccount = realAccount;
        this.bank = bank;
        this.simplePwd = simplePwd;
    }

//    가족 넣기
    public void setFamily(Family family){
        this.family = family;
    }

//    간편비밀번호 설정
    public void setSimplePwd(String simplePwd){
        this.simplePwd = simplePwd;
    }

//    주식 제한 비율 설정
    public void setStockRatio(int ratio) {
        this.stockRatio = ratio;
    }

// 가족 정보 얻기

    public void updateUserInfo(String name, String bank, String realAccount, String simplePwd, String role){
        if(name != null) this.name = name;
        if(bank!=null) this.bank = bank;
        if(realAccount != null) this.realAccount = realAccount;
        if(simplePwd != null) this.simplePwd = simplePwd;
        if(role != null) this.role = role;
    }

    public void changeStockRatio(int ratio){
        if(ratio >= 0 && ratio <= 100) {
            this.stockRatio = ratio;
        } else {
            throw new IllegalArgumentException("주식 비율은 0 ~ 100로 설정 가능합니다.");
        }
    }

//    메인계좌 존재 여부 확인 메소드
    @OneToMany(mappedBy ="user", fetch = FetchType.LAZY)
    private List<Account> accounts;

//    public boolean hasAccount() {
//        return accountRepository.findByUserId(this.id).isPresent();
//    }
}
