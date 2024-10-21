package com.qlda.nhom6.controller;

import com.NgocHieu.Buoi22.exception.InsufficientStockException;
import com.NgocHieu.Buoi22.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @GetMapping
    public String showCart(Model model) {
        var cartItems = cartService.getCartItems();
        double totalAmount = cartItems.stream()
                .mapToDouble(item -> item.getProduct().getGiaban() * item.getQuantity())
                .sum();
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalAmount", totalAmount);
        return "/cart/cart";
    }
    @PostMapping("/add")
    public String addToCart(@RequestParam Long productId, @RequestParam int quantity, Model model) {
        try {
            cartService.addToCart(productId, quantity);
            return "redirect:/cart";
        } catch (InsufficientStockException e) {
            var cartItems = cartService.getCartItems();
            double totalAmount = cartItems.stream()
                    .mapToDouble(item -> item.getProduct().getGiaban() * item.getQuantity())
                    .sum();
            model.addAttribute("error", e.getMessage());
            model.addAttribute("cartItems", cartItems);
            model.addAttribute("totalAmount", totalAmount);
            return "/cart/cart";
        }
    }
    @PostMapping("/update")
    public String updateQuantity(@RequestParam Long productId, @RequestParam int quantity, Model model) {
        try {
            cartService.updateCartItemQuantity(productId, quantity);
            return "redirect:/cart";
        } catch (InsufficientStockException e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/cart";
        }
    }

    @GetMapping("/remove/{productId}")
    public String removeFromCart(@PathVariable Long productId) {
        cartService.removeFromCart(productId);
        return "redirect:/cart";
    }
    @GetMapping("/clear")
    public String clearCart() {
        cartService.clearCart();
        return "redirect:/cart";
    }
}
