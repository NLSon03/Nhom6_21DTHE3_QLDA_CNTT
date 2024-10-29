package com.qlda.nhom6.repository;

import com.NgocHieu.Buoi22.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Cập nhật số lượng của sản phẩm sau khi thanh toán
    @Modifying
    @Query("UPDATE Product p SET p.nums = p.nums - :quantity WHERE p.id = :productId")
    void updateProductQuantity(@Param("productId") Long productId, @Param("quantity") int quantity);
}
