package com.qlda.nhom6.repository;

import com.qlda.nhom6.model.khoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface IkhoaRepository extends JpaRepository<khoa,Long> {


}
