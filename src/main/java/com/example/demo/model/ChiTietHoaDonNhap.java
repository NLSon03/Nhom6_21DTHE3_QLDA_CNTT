package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "ChiTietHoaDonNhap")
public class ChiTietHoaDonNhap {
    @Id
    private Long MaCTHDN;



    @Column(name = "SoLuongNhap")
    private int SoLuongNhap;

    @Column(name = "GiaNhap")
    private double GiaNhap;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_thuoc", referencedColumnName = "Idthuoc")
    @ToString.Exclude
    private Thuoc thuoc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Ma_HDN", referencedColumnName = "MaHDN")
    @ToString.Exclude
    private HoaDonNHap hoaDonNHap;
    


}
