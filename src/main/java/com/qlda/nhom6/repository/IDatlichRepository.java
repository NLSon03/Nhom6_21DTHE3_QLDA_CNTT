package com.qlda.nhom6.repository;

import com.qlda.nhom6.model.Datlich;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDatlichRepository extends JpaRepository<Datlich,Long> {
}
