package com.qlda.nhom6.repository;

import com.qlda.nhom6.model.NhaPhanPhoi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface INhaPPRepository extends JpaRepository<NhaPhanPhoi, Long> {


}
