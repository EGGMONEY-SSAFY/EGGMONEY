package com.ssafy.eggmoney.user.repository;

import com.ssafy.eggmoney.family.entity.Family;
import com.ssafy.eggmoney.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findAllByFamilyId(long familyId);

    @Query("select u from User u where u.id = :childId and u.family.id = :familyId")
    Optional<Family> findChild(@Param("familyId") Long familyId, @Param("childId") Long childId);
}
