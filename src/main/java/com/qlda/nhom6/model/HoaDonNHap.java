package com.qlda.nhom6.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "HoaDonNHap")
public class HoaDonNHap {
    @Id
    private Long MaHDN;


    @Column(name = "TongTienThuoc")
    private double TongTienThuoc;
    @Column(name = "TongThue")
    private double TongThue;
    @Column(name = "TongTienHD")
    private double TongTienHD;
    @Column(name = "NgayNhap")
    private Date NgayNhap;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NPP", referencedColumnName = "MaNPP")
    @ToString.Exclude
    private NhaPhanPhoi nhaphanphoi;


}
