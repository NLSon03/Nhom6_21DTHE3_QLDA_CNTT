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
@Table(name = "timming")
public class Timming {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "day", length = 50, nullable = false)
    private String day;
    @Column(name = "hour", length = 50, nullable = false)
    private String hours;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id",referencedColumnName = "id")
    @ToString.Exclude
    private Doctor doctor;

}