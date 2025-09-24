/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webserver.webbanhang.controller.Admin;

import com.webserver.webbanhang.model.Brand;
import com.webserver.webbanhang.model.Category;
import com.webserver.webbanhang.repository.BrandRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author HP
 */
@RestController
@RequestMapping("/admin/brands")
public class AdminBrandController {
    @Autowired
    private BrandRepository brandRepository;
    
    
    @GetMapping
    public List<Brand> getAllBrands(){
        return  brandRepository.findAll();
    }
    
     // lấy doanh mục theo id
     @GetMapping( "/{id}")
    public Optional<Brand> getBrandById(@PathVariable Integer id){
        return brandRepository.findById(id);
    }
    
     // cập nhật sap phẩm
    @PutMapping("/{id}")
    public Brand Update(@PathVariable Integer id,@RequestBody Brand brand){
        Brand brands =brandRepository.findById(id).orElseThrow(()-> new RuntimeException("Product not found"+id));
        brands.setName(brand.getName());
        brands.setProducts(brand.getProducts());
        
        return brandRepository.save(brands);
    }
   
  
 @DeleteMapping("/{id}")
    public String Detele(@PathVariable Integer id){
        Brand brand= brandRepository.findById(id).orElseThrow(()->new RuntimeException("Product not found"+id));
        brandRepository.delete(brand);
        return "Xóa thành công";
    }
    
    @PostMapping
    public Brand Create(@RequestBody Brand brand){
        return brandRepository.save(brand);
    }
}
