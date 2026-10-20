package com.foodie.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer_orders")
public class CustomerOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerName;
    private String phone;
    @Column(length = 1000)
    private String address;
    private double total;
    private LocalDateTime createdAt;

    @ElementCollection
    @CollectionTable(name = "order_lines", joinColumns = @JoinColumn(name = "order_id"))
    private List<OrderLine> items = new ArrayList<>();

    public CustomerOrder() {}

    public CustomerOrder(String customerName, String phone, String address, double total, List<OrderLine> items) {
        this.customerName = customerName;
        this.phone = phone;
        this.address = address;
        this.total = total;
        this.createdAt = LocalDateTime.now();
        this.items = new ArrayList<>(items);
    }

    @Embeddable
    public static class OrderLine {
        private String foodName;
        private int quantity;
        private double unitPrice;

        public OrderLine() {}
        public OrderLine(String foodName, int quantity, double unitPrice) {
            this.foodName = foodName;
            this.quantity = quantity;
            this.unitPrice = unitPrice;
        }
        public String getFoodName() { return foodName; }
        public void setFoodName(String foodName) { this.foodName = foodName; }
        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
        public double getUnitPrice() { return unitPrice; }
        public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }
        public double getLineTotal() { return quantity * unitPrice; }
    }

    public Long getId() { return id; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public List<OrderLine> getItems() { return items; }
    public void setItems(List<OrderLine> items) { this.items = items; }
}
