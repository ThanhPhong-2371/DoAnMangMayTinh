/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webserver.webbanhang.repository;

import com.webserver.webbanhang.model.Product;
import com.webserver.webbanhang.model.Category; // ✅ đúng

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author HP
 */
public interface CategoryRepository extends JpaRepository<Category, Integer>{

   

   

    
}
