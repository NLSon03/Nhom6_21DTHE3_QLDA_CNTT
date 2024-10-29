package com.qlda.nhom6.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    private Long Mathuoc;
    private String Tenthuoc;
    private Double giatien;
    private int Soluong;
}
