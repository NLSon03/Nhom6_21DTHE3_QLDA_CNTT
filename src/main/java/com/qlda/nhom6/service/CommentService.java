package com.qlda.nhom6.service;

import com.NgocHieu.Buoi22.model.Comment;
import com.NgocHieu.Buoi22.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private BlogService blogService;
    public long countCommentsByBlogId(int blogId) {
        return commentRepository.countByBlogId(blogId);
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }
    public List<Comment> getVisibleCommentsByBlogId(int blogId) {
        return commentRepository.findByBlogIdAndHideFalse(blogId);
    }

    public List<Comment> getAllCommentsByBlogId(int blogId) {
        return commentRepository.findByBlogId(blogId);
    }

    public List<Comment> getCommentsByBlogId(int blogId) {
        return commentRepository.findByBlogId(blogId);
    }

    public Comment addComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public Optional<Comment> getCommentById(int id) {
        return commentRepository.findById(id);
    }

    public Comment updateComment(Comment comment) {
        return commentRepository.save(comment);
    }

}
