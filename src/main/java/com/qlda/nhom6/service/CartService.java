package com.qlda.nhom6.service;

import com.qlda.nhom6.exception.InsufficientStockException;
import com.qlda.nhom6.model.CartItem;
import com.qlda.nhom6.model.Thuoc;
import com.qlda.nhom6.repository.IThuocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;

@Service
@SessionScope
public class CartService {
    private final List<CartItem> cartItems = new ArrayList<>();
    @Autowired
    private IThuocRepository productRepository;

    public void addToCart(Long productId, int quantity) throws InsufficientStockException {
        Thuoc product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));

        if (product.getSoluong() < quantity) {
            throw new InsufficientStockException("Không đủ số lượng cho yêu cầu của bạn: " + product.getTenthuoc());
        }

        CartItem existingCartItem = findCartItemByProductId(productId);
        if (existingCartItem != null) {
            int newQuantity = existingCartItem.getQuantity() + quantity;
            if (product.getSoluong() < newQuantity) {
                throw new InsufficientStockException("Insufficient stock for product: " + product.getTenthuoc());
            }
            existingCartItem.setQuantity(newQuantity);
        } else {
            cartItems.add(new CartItem(product, quantity));
        }
    }

    public void updateCartItemQuantity(Long productId, int quantity) throws InsufficientStockException {
        CartItem cartItem = findCartItemByProductId(productId);
        if (cartItem != null) {
            Thuoc product = cartItem.getProduct();
            validateStockAvailability(product, quantity);
            cartItem.setQuantity(quantity);
        }
    }

    private void validateStockAvailability(Thuoc product, int quantity) throws InsufficientStockException {
        if (product.getSoluong() < quantity) {
            throw new InsufficientStockException("Insufficient stock for product: " + product.getTenthuoc());
        }
    }

    public double calculateTotalAmount() {
        return cartItems.stream()
                .mapToDouble(item -> item.getProduct().getGiaban() * item.getQuantity())
                .sum();
    }

    private CartItem findCartItemByProductId(Long productId) {
        return cartItems.stream()
                .filter(item -> Long.valueOf(item.getProduct().getIdthuoc()).equals(productId))
                .findFirst()
                .orElse(null);
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void removeFromCart(Long productId) {
        cartItems.removeIf(item -> Long.valueOf(item.getProduct().getIdthuoc()).equals(productId));
    }

    public void clearCart() {
        cartItems.clear();
    }


}