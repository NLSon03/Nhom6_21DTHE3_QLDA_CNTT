package com.qlda.nhom6.repository;

import com.qlda.nhom6.model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface BlogRepository extends JpaRepository<Blog, Integer> {
}
