/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webserver.webbanhang.controller.User;

import com.webserver.webbanhang.model.Product;
import com.webserver.webbanhang.repository.ProductRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author HP
 */
public class HomeController {
     @Autowired
    private ProductRepository productRepository;

    // 🟩 Lấy tất cả hoặc lọc theo category / brand
    @GetMapping
    public List<Product> getProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String brand) {

        if (category != null && brand != null) {
            return productRepository.findByCategory_NameAndBrand_Name(category, brand);
        } else if (category != null) {
            return productRepository.findByCategory_Name(category);
        } else if (brand != null) {
            return productRepository.findByBrand_Name(brand);
        } else {
            return productRepository.findAll();
        }
    }
}
