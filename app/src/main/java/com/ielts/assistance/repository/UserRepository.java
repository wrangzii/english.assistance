package com.ielts.assistance.repository;

import com.ielts.assistance.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    @Query(value = "SELECT * FROM users WHERE is_active = 'true' AND username = ?1 ", nativeQuery = true)
    Optional<User> findByUsername(String username);

    @Query(value = "SELECT * FROM users WHERE is_active = 'true' AND email = ?1 ", nativeQuery = true)
    Optional<User> findByEmail(String email);

    @Query(value = "SELECT * FROM users WHERE is_active = 'true' AND department_id = ?1 ", nativeQuery = true)
    List<User> findByDepartmentId(Long departmentId);

    @Query(value = "SELECT * FROM users WHERE is_active = 'true'", nativeQuery = true)
    Page<User> findAll(Pageable pageable);

    User findByResetPasswordToken(String confirmationToken);

    Boolean existsByUsername(String username);
}
