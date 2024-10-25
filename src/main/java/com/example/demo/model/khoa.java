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
@Table(name = "khoa")
public class khoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "Name", length = 50, nullable = false)
    private String Name;

    @OneToMany(mappedBy = "Khoa", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Doctor> doctors = new ArrayList<>();






}
