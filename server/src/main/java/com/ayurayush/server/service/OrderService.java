package com.ayurayush.server.service;

import com.ayurayush.server.dto.OrderRequest;
import com.ayurayush.server.entity.*;
import com.ayurayush.server.repository.OrderRepository;
import com.ayurayush.server.repository.ProductRepository;
import com.ayurayush.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private UserRepository userRepository;

    public Order createOrder(User user, OrderRequest request) throws Exception {
        // Validate purchase limit (max 3 products per person)
        validatePurchaseLimit(user);
        
        // Validate order items
        validateOrderItems(request.getOrderItems());
        
        // Create order
        Order order = new Order();
        order.setUser(user);
        order.setOrderNumber(generateOrderNumber());
        order.setShippingAddress(request.getShippingAddress());
        order.setPaymentMethod(request.getPaymentMethod());
        order.setTransactionId(request.getTransactionId());
        
        // Calculate total amount and create order items
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderItem> orderItems = request.getOrderItems().stream()
            .map(itemRequest -> {
                Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + itemRequest.getProductId()));
                
                OrderItem orderItem = new OrderItem(order, product, itemRequest.getQuantity(), product.getPrice());
                totalAmount = totalAmount.add(orderItem.getTotalPrice());
                return orderItem;
            })
            .toList();
        
        order.setTotalAmount(totalAmount);
        order.setOrderItems(orderItems);
        
        return orderRepository.save(order);
    }

    public List<Order> getUserOrders(User user) {
        return orderRepository.findByUserOrderByCreatedAtDesc(user);
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    public Order updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setOrderStatus(status);
        order.setUpdatedAt(LocalDateTime.now());
        return orderRepository.save(order);
    }

    public Order updatePaymentStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setPaymentStatus(status);
        order.setUpdatedAt(LocalDateTime.now());
        return orderRepository.save(order);
    }

    private void validatePurchaseLimit(User user) throws Exception {
        // Check if user has purchased more than 3 products in the last 30 days
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
        long purchasedProducts = orderRepository.countCompletedProductPurchasesByUserSince(user, thirtyDaysAgo);
        
        if (purchasedProducts >= 3) {
            throw new Exception("You have reached the maximum limit of 3 products per person. Please wait 30 days before making another purchase.");
        }
    }

    private void validateOrderItems(List<OrderRequest.OrderItemRequest> orderItems) throws Exception {
        if (orderItems.size() > 3) {
            throw new Exception("Maximum 3 products allowed per order");
        }
        
        for (OrderRequest.OrderItemRequest item : orderItems) {
            if (item.getQuantity() <= 0) {
                throw new Exception("Quantity must be greater than 0");
            }
            
            Product product = productRepository.findById(item.getProductId())
                .orElseThrow(() -> new Exception("Product not found: " + item.getProductId()));
            
            if (!product.isAvailable()) {
                throw new Exception("Product is not available: " + product.getName());
            }
        }
    }

    private String generateOrderNumber() {
        return "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
} 