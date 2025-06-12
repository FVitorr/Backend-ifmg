package com.example.demo.repository;

import com.example.demo.entities.PasswordRecover;
import com.example.demo.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface PasswordRecoverRepository extends JpaRepository<PasswordRecover, Long>{

        @Query("SELECT obj FROM PasswordRecover obj WHERE (obj.email = :token) AND (obj.expiration > :now)")
        public List<PasswordRecover> searchValidToken(String token, Instant now);


}
