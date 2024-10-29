package com.qlda.nhom6.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Chitiethoadonxuat")
public class ChiTietHoaDonXuat {
    @Id
    private Long MaCTHDX;

    @Column(name = "Soluong")
    private int Soluong;

    @Column(name = "giaban")
    private double giaban;

    @Column(name = "Thue")
    private double Thue;

    @Column(name = "Donvi")
    private String Donvi;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "thuoc_id", referencedColumnName = "Idthuoc")
    @ToString.Exclude
    private Thuoc thuoc;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Hdx_id", referencedColumnName = "MaHDX")
    @ToString.Exclude
    private HoaDonXuat hoadonxuat;
}
