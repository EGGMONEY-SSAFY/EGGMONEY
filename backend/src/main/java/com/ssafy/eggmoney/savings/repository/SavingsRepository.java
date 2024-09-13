package com.ssafy.eggmoney.savings.repository;


import com.ssafy.eggmoney.savings.entity.Savings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavingsRepository extends JpaRepository<Savings, Long> {

}
