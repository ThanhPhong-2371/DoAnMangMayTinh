/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webserver.webbanhang.controller.User;

import org.springframework.ui.Model;
import com.webserver.webbanhang.model.ApplicationUser;
import com.webserver.webbanhang.model.Role;
import com.webserver.webbanhang.repository.RoleRepository;
import com.webserver.webbanhang.repository.UserRepository;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author HP
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public AccountController(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    // trang đăng ký 
    //xử lý đăng ký
    @PostMapping("/register")
    @ResponseBody
    public Object register(@RequestBody ApplicationUser user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return new ApiResponse(false, "Username đã tồn tại!");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // mặc định ROLE_USER
        Optional<Role> roleOpt = roleRepository.findByName("ROLE_USER");
        Set<Role> roles = new HashSet<>();
        roleOpt.ifPresent(roles::add);
        user.setRoles(roles);

        userRepository.save(user);
        return new ApiResponse(true, "Đăng ký thành công!");
    }

    static class ApiResponse {

        public boolean success;
        public String message;

        public ApiResponse(boolean success, String message) {
            this.success = success;
            this.message = message;
        }
    }

    // Trang login
    @PostMapping("/login")
    public ApiResponse login(@RequestBody ApplicationUser loginUser) {
        return userRepository.findByUsername(loginUser.getUsername())
                .filter(user -> passwordEncoder.matches(loginUser.getPassword(), user.getPassword()))
                .map(user -> new ApiResponse(true, "Đăng nhập thành công!"))
                .orElse(new ApiResponse(false, "Sai username hoặc password!"));
    }
   

}
