/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webserver.webbanhang.controller.Admin;

import com.webserver.webbanhang.model.Order;
import com.webserver.webbanhang.repository.OrderRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author HP
 */
@RestController
@RequestMapping("/admin/orders")
public class AdminOrderController {
      @Autowired
    private OrderRepository orderRepository;

    // 🟩 Lấy toàn bộ đơn hàng của tất cả tài khoản
    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // 🟦 Lấy chi tiết 1 đơn hàng theo ID
    @GetMapping("/{id}")
    public Object getOrderById(@PathVariable Integer id) {
        Optional<Order> orderOpt = orderRepository.findById(id);
        if (orderOpt.isPresent()) {
            return orderOpt.get();
        }
        return "❌ Không tìm thấy đơn hàng!";
    }

    // 🟨 Cập nhật trạng thái đơn hàng (0: Chờ duyệt, 1: Xác nhận, 2: Giao hàng, 3: Hoàn tất, 4: Hủy)
    @PutMapping("/{id}/status")
    public String updateOrderStatus(@PathVariable Integer id, @RequestParam int status) {
        Optional<Order> orderOpt = orderRepository.findById(id);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            order.setStatus(status);
            orderRepository.save(order);
            return "✅ Cập nhật trạng thái đơn hàng thành công!";
        }
        return "❌ Không tìm thấy đơn hàng!";
    }

     

    // 🔴 Xóa đơn hàng
    @DeleteMapping("/{id}")
    public String deleteOrder(@PathVariable Integer id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return "🗑️ Xóa đơn hàng thành công!";
        } else {
            return "❌ Không tìm thấy đơn hàng!";
        }
    }
}
