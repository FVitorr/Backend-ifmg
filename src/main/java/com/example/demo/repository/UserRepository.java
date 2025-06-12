package com.example.demo.repository;

import com.example.demo.entities.User;
import com.example.demo.projections.UserDetailsProjection;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    User findByEmail(String email);
    User findByEmailAndPassword(String email, String password);

    @Query(nativeQuery = true,
           value = "SELECT user.email as username, u.password, r.id as roleId, r.authority FROM tb_user u" +
                    "INNER JOIN tb_user_role ur ON u.id = ur.user_id" +
                    "INNER JOIN tb_role r ON r.id = ur.role_id " +
                    "WHERE u.email = :email"
    )
            
    List<UserDetailsProjection> searchUserAndRoleByEmail(String email);
}
