/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webserver.webbanhang.controller.User;

import com.webserver.webbanhang.model.ApplicationUser;
import com.webserver.webbanhang.model.CheckoutService;
import com.webserver.webbanhang.model.Order;
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

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping
    public Order checkout(@AuthenticationPrincipal ApplicationUser user,
                          @RequestParam String shippingAddress,
                          @RequestParam String shippingMethod) {
        return checkoutService.checkout(user, shippingAddress, shippingMethod);
    }
}
