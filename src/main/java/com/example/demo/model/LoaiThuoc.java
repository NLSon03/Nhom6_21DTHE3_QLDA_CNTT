package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "loaithuoc")
public class LoaiThuoc {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "TenLoai", length = 50, nullable = false)
        private String tenLoai;

        @Column(name = "GhiChu", length = 50, nullable = false)
        private String ghiChu;

        @OneToMany(mappedBy = "loaithuoc", cascade = CascadeType.ALL)
        @ToString.Exclude
        private List<Thuoc> thuocs = new ArrayList<>();

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "category_id",referencedColumnName = "id")
        @ToString.Exclude
        private Category category;

}
