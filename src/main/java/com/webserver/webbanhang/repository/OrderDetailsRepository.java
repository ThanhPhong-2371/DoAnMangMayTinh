/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.webserver.webbanhang.repository;
import com.webserver.webbanhang.model.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;



/**
 *
 * @author HP
 */
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Integer> {
}