package com.qlda.nhom6.repository;

import com.NgocHieu.Buoi22.model.khoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface IkhoaRepository extends JpaRepository<khoa,Long> {


}
