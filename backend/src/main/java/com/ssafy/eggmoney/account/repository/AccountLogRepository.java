package com.ssafy.eggmoney.account.repository;

import com.ssafy.eggmoney.account.entity.AccountLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AccountLogRepository extends JpaRepository<AccountLog, Long> {

    // accountID값이 같은 로그 가져오기
//    @Query("SELECT al FROM AccountLog al WHERE al.account.id = :accountId")
//    List<AccountLog> findLogsByAccountId(@Param("accountId") Long accountId);

    // accountID값이 같은 로그 가져오기 (페이징 적용)
    @Query("SELECT al FROM AccountLog al WHERE al.account.id = :accountId ORDER BY al.createdAt DESC")
    Page<AccountLog> findLogsByAccountId(@Param("accountId") Long accountId, Pageable pageable);


    @Query("SELECT a FROM AccountLog a " +
            "WHERE a.account.id = :accountId " +  // Account ID 필터 추가
            "AND a.createdAt IN (SELECT MAX(al.createdAt) " +
            "FROM AccountLog al " +
            "WHERE al.account.id = :accountId " + // Account ID 필터 추가
            "AND al.createdAt >= :startDate AND al.createdAt < :endDate " +
            "GROUP BY FUNCTION('DATE', al.createdAt)) " +
            "ORDER BY a.createdAt DESC")
    List<AccountLog> findLatestLogsByDayAndAccountId(@Param("accountId") Long accountId,
                                                     @Param("startDate") LocalDateTime startDate,
                                                     @Param("endDate") LocalDateTime endDate);


}
