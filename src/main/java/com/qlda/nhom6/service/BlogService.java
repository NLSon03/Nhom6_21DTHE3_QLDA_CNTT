package com.qlda.nhom6.service;

import com.NgocHieu.Buoi22.model.Blog;
import com.NgocHieu.Buoi22.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BlogService {
    @Autowired
    private BlogRepository blogRepository;

    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }

    public Optional<Blog> getBlogById(int id) {
        return blogRepository.findById(id);
    }

    public Blog createBlog(Blog blog) {
        return blogRepository.save(blog);
    }

    public Blog updateBlog(int id, Blog blogDetails) {
        Blog blog = blogRepository.findById(id).orElseThrow(() -> new RuntimeException("Blog not found"));
        blog.setTitle(blogDetails.getTitle());
        blog.setImg(blogDetails.getImg());
        blog.setDetail(blogDetails.getDetail());
        blog.setDateBegin(blogDetails.getDateBegin());
        blog.setMeta(blogDetails.getMeta());
        blog.setOrder(blogDetails.getOrder());
        blog.setLink(blogDetails.getLink());
        blog.setHide(blogDetails.isHide());
        return blogRepository.save(blog);
    }

    public void deleteBlog(int id) {
        Blog blog = blogRepository.findById(id).orElseThrow(() -> new RuntimeException("Blog not found"));
        blogRepository.delete(blog);
    }
    public List<Blog> getProductsByPage(int page, int pageSize) {
        // Tính toán vị trí bắt đầu của danh sách sản phẩm
        int startIndex = (page - 1) * pageSize;

        // Lấy danh sách sản phẩm từ vị trí bắt đầu và kích thước trang
        List<Blog> allBlogs = getAllBlogs();
        List<Blog> blogs = new ArrayList<>();

        for (int i = startIndex; i < startIndex + pageSize && i < allBlogs.size(); i++) {
            blogs.add(allBlogs.get(i));
        }

        return blogs;
    }

    public int getTotalPages(int pageSize) {
        List<Blog> allBlogs = getAllBlogs();
        return (int) Math.ceil((double) allBlogs.size() / pageSize);
    }


}
