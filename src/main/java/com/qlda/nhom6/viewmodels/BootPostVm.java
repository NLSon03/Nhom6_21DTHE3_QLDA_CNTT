package com.qlda.nhom6.viewmodels;

import com.NgocHieu.Buoi22.model.Product;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
@Builder
public record BootPostVm(String title, String author, Double price,
                            Long categoryId) {
    public static BootPostVm from(@NotNull Product product) {
        return new BootPostVm(product.getName(), product.getThumnail(),
                product.getPrice(), (long) product.getCategory().getId());
    }
}
