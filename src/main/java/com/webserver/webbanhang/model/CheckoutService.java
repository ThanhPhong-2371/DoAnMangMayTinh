/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webserver.webbanhang.model;

import com.webserver.webbanhang.model.*;
import com.webserver.webbanhang.repository.CartRepository;
import com.webserver.webbanhang.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author HP
 */
@Service
public class CheckoutService {
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;

    public CheckoutService(CartRepository cartRepository, OrderRepository orderRepository) {
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public Order checkout(ApplicationUser user, String shippingAddress, String shippingMethod) {
        List<Cart> carts = cartRepository.findByUser(user);
        if (carts.isEmpty()) throw new RuntimeException("Giỏ hàng trống!");

        Order order = new Order();
        order.setUser(user);
        order.setCode("ORD-" + UUID.randomUUID().toString().substring(0, 8));
        order.setCustomerName(user.getFullName());
        order.setShippingAddress(shippingAddress);
        order.setShippingMethod(shippingMethod);

        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderDetails> detailsList = new ArrayList<>();

        for (Cart cart : carts) {
            BigDecimal unitPrice = BigDecimal.valueOf(cart.getProduct().getPrice());
            OrderDetails detail = new OrderDetails();
            detail.setOrder(order);
            detail.setProduct(cart.getProduct());
            detail.setQuantity(cart.getQuantity());
            detail.setPrice(unitPrice);
            totalAmount = totalAmount.add(unitPrice.multiply(BigDecimal.valueOf(cart.getQuantity())));
            detailsList.add(detail);
        }

        // Thiết lập 2 chiều
        detailsList.forEach(d -> d.setOrder(order));
        order.setOrderDetails(detailsList);
        order.setTotalAmount(totalAmount);

        // Lưu order
        Order savedOrder = orderRepository.save(order);

        // Xóa giỏ hàng
        cartRepository.deleteAll(carts);
        cartRepository.flush(); // đảm bảo delete thật

        return savedOrder;
    }
}
