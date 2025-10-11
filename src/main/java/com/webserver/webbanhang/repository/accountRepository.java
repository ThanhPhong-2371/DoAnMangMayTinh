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
public interface accountRepository extends JpaRepository<ApplicationUser, Integer> {

  //  public static Optional<ApplicationUser> findById(Integer id);

    public boolean existsById(Integer id);

    public void deleteById(Integer id);
    
}
