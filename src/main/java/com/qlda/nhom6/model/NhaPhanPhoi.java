package com.qlda.nhom6.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "nhaphanphoi")

public class NhaPhanPhoi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long MaNPP;

    @Column(name = "TenNPP", length = 50, nullable = false)
    private String TenNPP;
    @Column(name = "DiaChi", length = 50, nullable = false)
    private String DiaChi;
    @Column(name = "email", length = 50, nullable = false)
    private String email;
    @Column(name = "MaSoThue")
    private int MaSoThue;

    private String GhiChu;
    @OneToMany(mappedBy = "nhaphanphoi", cascade = CascadeType.ALL)
    private final Set<HoaDonNHap> InvoiceEntrys = new HashSet<>();


}

