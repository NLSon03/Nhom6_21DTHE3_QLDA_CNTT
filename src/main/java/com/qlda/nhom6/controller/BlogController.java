package com.qlda.nhom6.controller;

import com.qlda.nhom6.model.Blog;
import com.qlda.nhom6.model.Comment;
import com.qlda.nhom6.service.BlogService;
import com.qlda.nhom6.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/blogs")
public class BlogController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private CommentService commentService;

    @GetMapping
    public String showBlogList(@RequestParam(defaultValue = "1") int page,
                               @RequestParam(defaultValue = "10") int pageSize,
                               Model model) {
        List<Blog> blogs = blogService.getProductsByPage(page, pageSize);
        model.addAttribute("blogs", blogs);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalPages", blogService.getTotalPages(pageSize));
        return "/blogs/blog-list";
    }

    @GetMapping("/blog-list-user")
    public String showUserBlogList(@RequestParam(defaultValue = "1") int page,
                                   @RequestParam(defaultValue = "2") int pageSize,
                                   Model model) {

        List<Blog> blogs = blogService.getProductsByPage(page, pageSize);
        model.addAttribute("blogs", blogs);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalPages", blogService.getTotalPages(pageSize));
        return "/blogs/blog-list-user";
    }

    @GetMapping("/detail/{id}")
    public String showBlogDetail(@PathVariable int id, Model model) {
        Blog blog = blogService.getBlogById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid blog Id:" + id));
        long commentCount = commentService.countCommentsByBlogId(id);
        List<Blog> blogs = blogService.getAllBlogs();
        List<Comment> comments = commentService.getVisibleCommentsByBlogId(id);
        // Lấy hai blog đầu tiên từ danh sách
        List<Blog> limitedBlogs = blogs.stream().limit(2).collect(Collectors.toList());
        List<Blog> limitedBlogs3 = blogs.stream().limit(3).collect(Collectors.toList());
        model.addAttribute("blog", blog);
        model.addAttribute("blogs", limitedBlogs);
        model.addAttribute("blogs3", limitedBlogs3);
        model.addAttribute("comments", comments);
        model.addAttribute("commentCount", commentCount);
        return "/blogs/blog-detail";
    }

    @PostMapping("/detail/{id}/addComment")
    public String addComment(@PathVariable int id, @RequestParam String author, @RequestParam String content) {
        Comment comment = new Comment();
        comment.setBlogId(id);
        comment.setAuthor(author);
        comment.setContent(content);
        comment.setDate(new Date(System.currentTimeMillis()));
        comment.setHide(false);
        commentService.addComment(comment);
        return "redirect:/blogs/detail/" + id;
    }

    @PostMapping("/detail/toggleComment/{commentId}")
    public String toggleComment(@PathVariable int commentId) {
        Comment comment = commentService.getCommentById(commentId).orElseThrow(() -> new RuntimeException("Comment not found"));
        comment.toggleHide();
        commentService.updateComment(comment);
        return "redirect:/blogs/detail/" + comment.getBlogId();
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("blog", new Blog());
        return "/blogs/add-blog";
    }

    @PostMapping("/add")
    public String addBlog(@Valid Blog blog, BindingResult result, @RequestParam("image") MultipartFile imageFile) {
        if (result.hasErrors()) {
            return "/blogs/add-blog";
        }
        if (!imageFile.isEmpty()) {
            try {
                String imageName = saveImageStatic(imageFile);
                blog.setImg("fe/images/" + imageName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        blogService.createBlog(blog);
        return "redirect:/blogs";
    }

    private String saveImageStatic(MultipartFile image) throws IOException {
        File saveFile = new ClassPathResource("/static/fe/images").getFile();
        String fileName = UUID.randomUUID() + "." + StringUtils.getFilenameExtension(image.getOriginalFilename());
        Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + fileName);
        Files.copy(image.getInputStream(), path);
        return fileName;
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Blog blog = blogService.getBlogById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid blog Id:" + id));
        model.addAttribute("blog", blog);
        return "/blogs/update-blog";
    }

    @PostMapping("/update/{id}")
    public String updateBlog(@PathVariable int id, @Valid Blog blog, BindingResult result,
                             @RequestParam("image") MultipartFile imageFile) {
        if (result.hasErrors()) {
            return "/blogs/update-blog";
        }

        Blog existingBlog = blogService.getBlogById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid blog Id:" + id));

        if (!imageFile.isEmpty()) {
            try {
                String imageName = saveImageStatic(imageFile);
                blog.setImg("fe/images/" + imageName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            blog.setImg(existingBlog.getImg());
        }

        blogService.updateBlog(id, blog);
        return "redirect:/blogs";
    }

    @GetMapping("/delete/{id}")
    public String deleteBlog(@PathVariable int id) {
        blogService.deleteBlog(id);
        return "redirect:/blogs";
    }
}
