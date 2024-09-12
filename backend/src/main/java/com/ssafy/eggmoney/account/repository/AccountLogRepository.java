package com.ssafy.eggmoney.account.repository;

import com.ssafy.eggmoney.account.entity.AccountLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface AccountLogRepository extends JpaRepository<AccountLog, Long> {

}
