package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;


import java.util.Date;
import java.util.List;
@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerName;

    private String shippingAddress;
    private String phoneNumber;
    private String email;
    private String notes;
    private String paymentMethod;
    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails;

    private Double totalAmount;
    private Date paymentDate;

    public Double calculateTotalAmount() {
        return orderDetails.stream().mapToDouble(OrderDetail::getSubtotal).sum();
    }

    @PrePersist
    @PreUpdate
    public void updateTotalAmount() {
        this.totalAmount = calculateTotalAmount();
    }
}
