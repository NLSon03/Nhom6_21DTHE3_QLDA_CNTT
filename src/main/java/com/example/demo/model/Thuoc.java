package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "thuoc")
public class Thuoc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idthuoc")
    private Long idthuoc;
    @Column(name = "Tenthuoc", length = 50, nullable = false)

    private String Tenthuoc;
   


    @Column(name = "Nguongoc", length = 50, nullable = false)
    private String Nguongoc;


    @Column(name = "Soluong")
    private int Soluong;

    @Column(name = "giaban")
    private double giaban;

    @Column(name = "thanhPhan", length = 50, nullable = false)


    private String thanhPhan;

    @Column(name = "Congdung", length = 50, nullable = false)
    private String Congdung;

    @Column(name = "phantacdung", length = 50, nullable = false)
    private String  phantacdung;


    @Column(name = "cachdung", length = 50, nullable = false)
    private String cachdung;

    @Column(name = "ChuY", length = 50, nullable = false)
    private String ChuY;

    @Column(name = "hansudung")
    private Date hansudung;
    @Column(name = "baoquan", length = 50, nullable = false)
    private String baoquan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="Maloai",referencedColumnName = "id")
    @ToString.Exclude
    private LoaiThuoc loaithuoc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id",referencedColumnName = "id")
    @ToString.Exclude
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="MaNSX",referencedColumnName = "id")
    @ToString.Exclude
    private NhaSanXuat nhaSanXuat;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_dvt",referencedColumnName = "MaDVT")
    @ToString.Exclude
    private DonViTinh donViTinh;

    @OneToMany(mappedBy = "thuoc", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<ChiTietHoaDonXuat> itemInvoices = new ArrayList<>();

    @OneToMany(mappedBy = "thuoc",cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<ChiTietHoaDonNhap> itemNhaps = new ArrayList<>();

    private String Image;

}
