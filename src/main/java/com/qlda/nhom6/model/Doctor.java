package com.qlda.nhom6.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "doctor")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name", length = 50, nullable = false)
    private String Name;
    @Column(name = "introduction", length = 50, nullable = false)
    private String introduction;
    @Column(name = "speciality", length = 50, nullable = false)
    private String speciality;
    @Column(name = "education", length = 50, nullable = false)
    private String education;

    @Column(name = "experience", length = 50, nullable = false)
    private String experience;
    @Column(name = "address", length = 50, nullable = false)
    private String address;
    @Column(name = "phone", length = 50, nullable = false)
    private int phone;

    @Column(name = "email", length = 50, nullable = false)
    private String email;

    private String Image;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Timming> timmings = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Ma_Khoa", referencedColumnName = "id")
    @ToString.Exclude
    private khoa Khoa;


}
