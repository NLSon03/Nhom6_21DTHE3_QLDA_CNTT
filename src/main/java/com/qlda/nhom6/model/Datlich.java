package com.qlda.nhom6.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Datlich")
public class Datlich {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    private String Name;

    private Date ngay;

    private String Email;

    private String Content;

    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="User_id",referencedColumnName = "id")
    @ToString.Exclude
    private User user;
*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Doctor-id", referencedColumnName = "id")
    @ToString.Exclude
    private Doctor doctor;
}

