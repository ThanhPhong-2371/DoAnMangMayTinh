/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webserver.webbanhang.controller.Admin;

import com.webserver.webbanhang.model.ApplicationUser;
import com.webserver.webbanhang.repository.accountRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.GetExchange;

/**
 *
 * @author HP
 */
@RestController
@RequestMapping("/admin/accounts")
public class AdminAccountController {

    @Autowired
    private accountRepository AccountRepository;

    @GetExchange
    public List<ApplicationUser> getAllAccount() {
        return AccountRepository.findAll();
    }
    //@PutMapping("/{id}/lock")
    // public String lockOrUnlockAccount(@PathVariable Integer id) {
    //    Optional<ApplicationUser> accountOpt = accountRepository.findById(id);
    //    if (accountOpt.isPresent()) {
     //       ApplicationUser account = accountOpt.get();
     //       account.setIsApproved(!account.isIsApproved()); // Đảo trạng thái khóa
      //      AccountRepository.save(account);
     //       return account.isIsApproved() ? "Tài khoản đã bị khóa!" : "Tài khoản đã được mở khóa!";
     //   } else {
      //      return "Không tìm thấy tài khoản!";
        
     //   }
     
    // }
    @DeleteMapping("/{id}")
    public String deleteAccount(@PathVariable Integer id) {
        if (AccountRepository.existsById(id)) {
            AccountRepository.deleteById(id);
            return "Xóa tài khoản thành công!";
        } else {
            return "Không tìm thấy tài khoản!";
        }
    }
}
