package com.qlda.nhom6.service;

import com.NgocHieu.Buoi22.model.*;
import com.NgocHieu.Buoi22.repository.IThuocRepository;
import com.NgocHieu.Buoi22.repository.OrderDetailRepository;
import com.NgocHieu.Buoi22.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private CartService cartService; // Assuming you have a CartService
    private List<OrderDetail> orderDetails;
    @Autowired
    private IThuocRepository productRepository;

    public List<Order> getAllOrder() {
        return orderRepository.findAll();
    }



    @Transactional
    public Order createOrder(String customerName, String shippingAddress, String phoneNumber, String email, String notes, String paymentMethod, List<CartItem> cartItems) {
        Order order = new Order();
        order.setCustomerName(customerName);
        order.setShippingAddress(shippingAddress);
        order.setPhoneNumber(phoneNumber);
        order.setEmail(email);
        order.setNotes(notes);
        order.setPaymentMethod(paymentMethod);
        order.setOrderDetails(new ArrayList<>());  // Khởi tạo danh sách orderDetails

        order = orderRepository.save(order);
        double totalAmount = 0.0;
        for (CartItem item : cartItems) {
            Thuoc product = item.getProduct();
            int quantity = item.getQuantity();

            // Tạo đối tượng OrderDetail
            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setProduct(product);
            detail.setQuantity(quantity);

            // Tính subtotal và cộng vào totalAmount
            double itemTotal = detail.getSubtotal();
            totalAmount += itemTotal;

            // Thêm chi tiết đơn hàng vào danh sách
            order.getOrderDetails().add(detail);

            // Lưu chi tiết đơn hàng vào cơ sở dữ liệu
            orderDetailRepository.save(detail);

            // Trừ số lượng sản phẩm từ cơ sở dữ liệu
            product.setSoluong(product.getSoluong() - quantity);
            productRepository.save(product);
        }
        // Cập nhật tổng tiền và ngày thanh toán
        order.setTotalAmount(totalAmount);
        order.setPaymentDate(new Date());

        // Xóa giỏ hàng sau khi đặt hàng (tùy chọn)
        cartService.clearCart();


        orderRepository.save(order);
        return order;
    }

}


