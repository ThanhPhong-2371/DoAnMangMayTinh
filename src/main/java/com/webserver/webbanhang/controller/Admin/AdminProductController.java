/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webserver.webbanhang.controller.Admin;

import com.webserver.webbanhang.model.Product;
import com.webserver.webbanhang.repository.ProductRepository;
import jakarta.annotation.Priority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import javax.management.RuntimeErrorException;
/**
 *
 * @author HP
 */
@RestController
@RequestMapping("/admin/products")
public class AdminProductController {

    @Autowired
    private ProductRepository productRepository;

    // lấy tất cả sản phẩm
    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    //lấy sản phẩm theo id
    @GetMapping( "/{id}")
    public Optional<Product> getProductById(@PathVariable Integer id){
        return productRepository.findById(id);
    }
    //Thêm sản phẩm
    @PostMapping
    public  Product Create(@RequestBody Product product){
        return  productRepository.save(product);
    }
    // cập nhật sap phẩm
    @PutMapping("/{id}")
    public  Product Update(@PathVariable Integer id,@RequestBody Product productdetails){
        Product product =productRepository.findById(id).orElseThrow(()-> new RuntimeException("Product not found"+id));
        product.setName(productdetails.getName());
        product.setDescription(productdetails.getDescription());
        product.setImage(productdetails.getImage());
        product.setSlug(productdetails.getSlug());
        product.setPrice(productdetails.getPrice());
        product.setCategory(productdetails.getCategory());
        product.setBrand(productdetails.getBrand()); 
        return productRepository.save(product);
        
    }
    
    @DeleteMapping("/{id}")
    public String Detele(@PathVariable Integer id){
        Product product= productRepository.findById(id).orElseThrow(()->new RuntimeException("Product not found"+id));
        productRepository.delete(product);
        return "Xóa thành công";
    }
    
}
