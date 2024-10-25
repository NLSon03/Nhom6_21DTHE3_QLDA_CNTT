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
@Table(name = "donViTinh")
public class DonViTinh {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long MaDVT;

    @Column(name = "TenDVT", length = 50, nullable = false)
    private String TenDVT;

    @OneToMany(mappedBy = "donViTinh",cascade = CascadeType.ALL)
    private List<Thuoc> thuocs = new ArrayList<>();
}
