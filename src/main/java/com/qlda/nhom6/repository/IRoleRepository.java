package com.qlda.nhom6.repository;

import com.qlda.nhom6.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
public interface IRoleRepository extends JpaRepository<Role, Long> {
    Role findRoleById(Long id);
}
