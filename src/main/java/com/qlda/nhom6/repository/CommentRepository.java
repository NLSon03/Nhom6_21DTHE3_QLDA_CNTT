package com.qlda.nhom6.repository;

import com.NgocHieu.Buoi22.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByBlogIdAndHideFalse(int blogId);
    List<Comment> findByBlogId(int blogId);

    List<Comment> findAll();
    long countByBlogId(int blogId);
}
