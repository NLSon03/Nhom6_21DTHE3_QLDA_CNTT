package com.qlda.nhom6.repository;

import com.qlda.nhom6.model.Slide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface SlideRepository extends JpaRepository<Slide, Long> {
}
