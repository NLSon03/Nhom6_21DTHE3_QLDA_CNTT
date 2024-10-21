package com.qlda.nhom6.repository;

import com.NgocHieu.Buoi22.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
public interface IRoleRepository extends JpaRepository<Role, Long> {
    Role findRoleById(Long id);
}
