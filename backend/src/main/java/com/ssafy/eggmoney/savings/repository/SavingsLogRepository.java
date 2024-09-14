package com.ssafy.eggmoney.savings.repository;

import com.ssafy.eggmoney.savings.entity.SavingsLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavingsLogRepository extends JpaRepository<SavingsLog, Long> {

}
