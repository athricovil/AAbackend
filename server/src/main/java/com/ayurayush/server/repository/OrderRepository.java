package com.ayurayush.server.repository;

import com.ayurayush.server.entity.Order;
import com.ayurayush.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserOrderByCreatedAtDesc(User user);
    
    @Query("SELECT COUNT(o) FROM Order o WHERE o.user = :user AND o.paymentStatus = 'COMPLETED' AND o.createdAt >= :startDate")
    long countCompletedOrdersByUserSince(@Param("user") User user, @Param("startDate") LocalDateTime startDate);
    
    @Query("SELECT COUNT(oi) FROM Order o JOIN o.orderItems oi WHERE o.user = :user AND o.paymentStatus = 'COMPLETED' AND o.createdAt >= :startDate")
    long countCompletedProductPurchasesByUserSince(@Param("user") User user, @Param("startDate") LocalDateTime startDate);
} 