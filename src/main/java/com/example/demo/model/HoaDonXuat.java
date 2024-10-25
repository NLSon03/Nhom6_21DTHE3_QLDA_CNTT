package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "hoadonxuat")
public class HoaDonXuat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long MaHDX;


    @Column(name = "NgayLap",  nullable = false)
    private Date NgayLap=new Date();
    @Column(name = "TongTienThuoc")
    private double TongTienThuoc;
    @Column(name = "TongThue")
    private double TongThue;
    @Column(name = "TongTienHD")
    private double TongTienHD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ToString.Exclude
    private User user;

@OneToMany(mappedBy = "hoadonxuat",cascade = CascadeType.ALL)
@ToString.Exclude
    private List<ChiTietHoaDonXuat> detailivoid = new ArrayList<>();
}   
