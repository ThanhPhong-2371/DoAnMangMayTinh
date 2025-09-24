/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webserver.webbanhang.repository;

import com.webserver.webbanhang.model.Brand;
import com.webserver.webbanhang.model.Product;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author HP
 */
@Repository
public interface BrandRepository extends JpaRepository<Brand, Long>{

    public Optional<Brand> findById(Integer id);
    //      public Optional<Brand> findById(Integer id);
}
