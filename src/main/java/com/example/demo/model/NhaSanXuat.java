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
@Table(name = "nhaSanXuat")

public class NhaSanXuat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @Column(name = "tenNSX", length = 50, nullable = false)
    private String TenNSX;


    @Column(name = "Diachi", length = 50, nullable = false)
    private String Diachi;
    @Column(name = "DienThoai" , length = 10, nullable = false  )
    private int DienThoai;

    @OneToMany(mappedBy= "nhaSanXuat",cascade= CascadeType.ALL)
    @ToString.Exclude
    private List<Thuoc> thuocs= new ArrayList<>();


}
