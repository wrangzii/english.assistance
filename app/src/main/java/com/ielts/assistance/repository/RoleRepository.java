package com.ielts.assistance.repository;

import com.ielts.assistance.models.ERole;
import com.ielts.assistance.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(ERole roleName);
}
