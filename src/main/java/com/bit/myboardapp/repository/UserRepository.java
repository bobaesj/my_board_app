package com.bit.myboardapp.repository;

import com.bit.myboardapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    int countByEmail(String email);

    int countByNickname(String nickname);

    int countByTel(String tel);
}