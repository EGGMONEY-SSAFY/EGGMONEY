package com.ssafy.eggmoney.user.repository;

import com.ssafy.eggmoney.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByFamilyId(long familyId);
}
