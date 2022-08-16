package com.board.board.repository;

import com.board.board.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    /* Security */
    Optional<User> findByEmail(String email);

    /* 회원가입 이메일 중복체크 */
    boolean existsByEmail(String email);
    boolean existsByName(String name);

    /* user GET */
    User findByName(String name);

}