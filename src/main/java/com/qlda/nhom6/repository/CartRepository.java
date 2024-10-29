package com.qlda.nhom6.repository;

import com.qlda.nhom6.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
}
