/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webserver.webbanhang.controller.User;

import com.webserver.webbanhang.model.Product;
import com.webserver.webbanhang.repository.ProductRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author HP
 */
@RestController
@RequestMapping("/products")
public class ProductController {
     @Autowired
    private ProductRepository productRepository;

    // Trang hiển thị danh sách sản phẩm cho user
    

    // Lấy danh sách sản phẩm (JSON)
    @GetMapping
    public List<Product> listProducts() {
        return productRepository.findAll();
    }

    // Lấy chi tiết sản phẩm (JSON)
    @GetMapping("/{id}")
    public Product productDetails(@PathVariable("id") Integer id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sản phẩm với id: " + id));
    }
}
