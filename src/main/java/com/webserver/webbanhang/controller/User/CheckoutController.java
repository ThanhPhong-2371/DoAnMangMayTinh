/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webserver.webbanhang.controller.User;

import com.webserver.webbanhang.model.ApplicationUser;
import com.webserver.webbanhang.model.CheckoutService;
import com.webserver.webbanhang.model.Order;
import com.webserver.webbanhang.repository.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author HP
 */
@RestController
@RequestMapping("/checkout")
public class CheckoutController {
     private final CheckoutService checkoutService;
    private final  UserRepository userRepository;

    public CheckoutController(CheckoutService checkoutService, UserRepository userRepository) {
        this.checkoutService = checkoutService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public Order checkout(@RequestParam Integer userId,
                      @RequestParam String shippingAddress,
                      @RequestParam String shippingMethod) {

    ApplicationUser user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng ID: " + userId));

    return checkoutService.checkout(user, shippingAddress, shippingMethod);

    }
}
