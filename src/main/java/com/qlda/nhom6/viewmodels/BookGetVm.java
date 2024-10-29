package com.qlda.nhom6.viewmodels;

import com.qlda.nhom6.model.NhaSanXuat;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.Builder;

@Builder
public record BookGetVm(Long id, String TenNSX,  int DienThoai,
                        String Diachi) {



    public static BookGetVm from(@NotNull NhaSanXuat book) {
        return BookGetVm.builder()
                .id(book.getId())
                .TenNSX(book.getTenNSX())
                .Diachi(book.getDiachi())
                .DienThoai(book.getDienThoai())
                .build();
    }
}