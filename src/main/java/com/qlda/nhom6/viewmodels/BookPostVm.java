package com.qlda.nhom6.viewmodels;

import com.NgocHieu.Buoi22.model.NhaSanXuat;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.Builder;

@Builder
public record BookPostVm( String TenNSX,  int DienThoai, String Diachi) {


    public static BookPostVm from(@NotNull NhaSanXuat book) {
        return new BookPostVm(book.getTenNSX(), book.getDienThoai(), book.getDiachi());
    }
}

