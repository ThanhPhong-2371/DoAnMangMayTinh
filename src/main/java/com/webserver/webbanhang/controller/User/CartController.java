/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webserver.webbanhang.controller.User;

import com.webserver.webbanhang.model.ApplicationUser;
import com.webserver.webbanhang.model.Cart;
import com.webserver.webbanhang.model.Product;
import com.webserver.webbanhang.repository.CartRepository;
import com.webserver.webbanhang.repository.ProductRepository;
import com.webserver.webbanhang.repository.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author HP
 */
@RestController
@RequestMapping("/carts")
public class CartController {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    // 1. Xem cart theo user
    @GetMapping("/{userId}")
    public List<Cart> getUserCart(@PathVariable Integer userId) {
        ApplicationUser user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user với id " + userId));
        return cartRepository.findByUser(user);
    }

    // 2. Thêm sản phẩm vào cart
    @PostMapping("/{userId}/add/{productId}")
    public Cart addToCart(@PathVariable Integer userId,
            @PathVariable Integer productId,
            @RequestParam(defaultValue = "1") int quantity) {

        ApplicationUser user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user với id " + userId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với id " + productId));

        Cart cart = new Cart();
        cart.setUser(user);
        cart.setProduct(product);
        cart.setQuantity(quantity);

        return cartRepository.save(cart);
    }

    // 3. Cập nhật số lượng trong cart
    @PutMapping("/{cartId}")
    public Cart updateCart(@PathVariable Integer cartId, @RequestParam int quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy cart với id " + cartId));
        cart.setQuantity(quantity);
        return cartRepository.save(cart);
    }

    // 4. Xóa sản phẩm khỏi cart
    @DeleteMapping("/{cartId}")
    public String removeFromCart(@PathVariable Integer cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy cart với id " + cartId));
        cartRepository.delete(cart);
        return "Đã xóa sản phẩm khỏi giỏ hàng!";
    }

    @DeleteMapping("/user/{userId}")
    public String clearCart(@PathVariable Integer userId) {
        ApplicationUser user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user với id " + userId));
        List<Cart> carts = cartRepository.findByUser(user);
        cartRepository.deleteAll(carts);
        return "Đã xóa toàn bộ giỏ hàng!";
    }
}
