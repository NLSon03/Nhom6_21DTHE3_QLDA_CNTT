package com.qlda.nhom6.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_COMMENT")
    private int id;

    @Column(name = "BLOG_ID", nullable = false)
    private int blogId;

    @Column(name = "AUTHOR", nullable = false)
    private String author;

    @Column(name = "CONTENT", nullable = false)
    private String content;

    @Column(name = "DATE", nullable = false)
    private java.sql.Date date;

    @Column(name = "HIDE", nullable = false)
    private boolean hide;


    // Phương thức để ẩn/hiện bình luận
    public void toggleHide() {
        this.hide = !this.hide;
    }
}
