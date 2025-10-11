/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webserver.webbanhang.controller.User;

import com.webserver.webbanhang.model.Category;
import com.webserver.webbanhang.repository.CategoryRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author HP
 */
@RestController
@RequestMapping("/categorys")
public class CategoryController {
    @Autowired
    CategoryRepository categoryRepository;
    
     @GetMapping
    public List<Category> getAllCategorys(){
        return  categoryRepository.findAll();
    }
}
