package com.qlda.nhom6.repository;

import com.qlda.nhom6.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdocterRepository extends JpaRepository<Doctor,Long> {
}
