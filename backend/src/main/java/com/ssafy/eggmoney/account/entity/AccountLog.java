package com.ssafy.eggmoney.account.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "account_logs")
@NoArgsConstructor(access = PROTECTED)
public class AccountLog {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
}
