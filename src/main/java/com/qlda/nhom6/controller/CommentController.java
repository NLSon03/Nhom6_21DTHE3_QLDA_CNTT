package com.qlda.nhom6.controller;

import com.qlda.nhom6.model.Blog;
import com.qlda.nhom6.model.Comment;
import com.qlda.nhom6.service.BlogService;
import com.qlda.nhom6.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private BlogService blogService;

    @GetMapping
    public String getAllComments(Model model) {
        List<Comment> comments = commentService.getAllComments();
        List<Blog> blogs = blogService.getAllBlogs();
        model.addAttribute("comments", comments);
        model.addAttribute("blogs", blogs);

        return "/comments/admin-comments";
    }

    @PostMapping("/toggle/{commentId}")
    public String toggleComment(@PathVariable int commentId) {
        Comment comment = commentService.getCommentById(commentId).orElseThrow(() -> new RuntimeException("Comment not found"));
        comment.toggleHide();
        commentService.updateComment(comment);
        return "redirect:/comments";
    }
}
