package com.ssafy.eggmoney.allowance.repository;

import com.ssafy.eggmoney.allowance.entity.Allowance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AllowanceRepository extends JpaRepository<Allowance, Long> {
    Optional<Allowance> findByChildId(Long childId);
    List<Allowance> findAllByChildFamilyId(Long familyId);
}
