/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.webserver.webbanhang.repository;

import com.webserver.webbanhang.model.ApplicationUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author HP
 */
public interface UserRepository extends JpaRepository<ApplicationUser, Integer>{
   Optional<ApplicationUser> findByUsername(String username);
    
}
