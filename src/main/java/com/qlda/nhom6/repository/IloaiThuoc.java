package com.qlda.nhom6.repository;

import com.qlda.nhom6.model.LoaiThuoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IloaiThuoc extends JpaRepository<LoaiThuoc, Long> {
    List<LoaiThuoc> findByCategoryId(Long categoryId);
}
