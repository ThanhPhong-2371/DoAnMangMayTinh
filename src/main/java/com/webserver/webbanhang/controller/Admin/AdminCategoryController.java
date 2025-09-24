/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webserver.webbanhang.controller.Admin;

import com.webserver.webbanhang.model.Category;
import com.webserver.webbanhang.model.Product;
import com.webserver.webbanhang.repository.CategoryRepository;
import java.util.List;
import java.util.Locale;
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
@RequestMapping("/admin/categorys")
public class AdminCategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
    // lấy doanh mục theo id
     @GetMapping( "/{id}")
    public Optional<Category> getCategoryById(@PathVariable Integer id){
        return categoryRepository.findById(id);
    }
    
     // cập nhật sap phẩm
    @PutMapping("/{id}")
    public Category Update(@PathVariable Integer id,@RequestBody Category categorys){
        Category category =categoryRepository.findById(id).orElseThrow(()-> new RuntimeException("Product not found"+id));
        category.setName(categorys.getName());
        category.setDescription(categorys.getDescription());
        category.setProducts(categorys.getProducts());
        category.setSlug(categorys.getSlug());
        category.setStatus(categorys.getStatus());
        return categoryRepository.save(category);
    }
   
    @PostMapping
    public Category Create(@RequestBody Category category){
        return  categoryRepository.save(category);
    }
 @DeleteMapping("/{id}")
    public String Detele(@PathVariable Integer id){
        Category category= categoryRepository.findById(id).orElseThrow(()->new RuntimeException("Product not found"+id));
        categoryRepository.delete(category);
        return "Xóa thành công";
    }
    
}
