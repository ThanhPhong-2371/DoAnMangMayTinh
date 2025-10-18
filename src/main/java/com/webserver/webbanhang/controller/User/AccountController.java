package com.webserver.webbanhang.controller.User;

import com.webserver.webbanhang.model.ApplicationUser;
import com.webserver.webbanhang.model.Order;
import com.webserver.webbanhang.model.Role;
import com.webserver.webbanhang.repository.OrderRepository;
import com.webserver.webbanhang.repository.RoleRepository;
import com.webserver.webbanhang.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder; //mã hóa mật khẩu trước khi lưu vào DB.
    @Autowired
    private AuthenticationManager authenticationManager;//Spring Security component dùng để xác thực login.

    // 🟩 Đăng ký
    @PostMapping("/register")
    @ResponseBody
    public Object register(@RequestBody ApplicationUser user,
            @RequestParam(required = false, defaultValue = "USER") String role) {

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return new ApiResponse(false, "Username đã tồn tại!");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Lấy role
        String roleName = role.equalsIgnoreCase("ADMIN") ? "ROLE_ADMIN" : "ROLE_USER";
        Optional<Role> roleOpt = roleRepository.findByName(roleName);
        Set<Role> roles = new HashSet<>();
        roleOpt.ifPresent(roles::add);
        user.setRoles(roles);
        userRepository.save(user);
        return new LoginResponse(true, "Đăng ký thành công!", user.getUsername(), user.getFullName(), roles, user.getId());
    }

    static class ApiResponse {

        public boolean success;
        public String message;

        public ApiResponse(boolean success, String message) {
            this.success = success;
            this.message = message;
        }
    }

    // 🟦 Đăng nhập
    @PostMapping("/login")
    public Object login(@RequestBody ApplicationUser loginUser) {
        try {
            // ✅ Gọi AuthenticationManager để Spring kiểm tra tài khoản
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword())
            );
            ApplicationUser user = (ApplicationUser) auth.getPrincipal();
            return new LoginResponse(
                    true,
                    "Đăng nhập thành công!",
                    user.getUsername(),
                    user.getFullName(),
                    user.getRoles().stream().map(Role::getName).toList(),
                    user.getId()
            );

        } catch (LockedException e) {
            return new LoginResponse(false, "Tài khoản đã bị khóa!", null, null, null, null);
        } catch (BadCredentialsException e) {
            return new LoginResponse(false, "Sai tên đăng nhập hoặc mật khẩu!", null, null, null, null);
        } catch (AuthenticationException e) {
            return new LoginResponse(false, "Lỗi xác thực!", null, null, null, null);
        }
    }

    record LoginResponse(boolean success, String message, String username, String fullName, Object roles, Integer id) {

    }

    @GetMapping("/profile/{id}")
    public Object getProfile(@PathVariable Integer id) {
        Optional<ApplicationUser> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            return new ApiResponse(false, "Không tìm thấy người dùng!");
        }
        return userOpt.get();
    }

    // 🟩 ✏️ Cập nhật thông tin hồ sơ
    @PutMapping("/profile/{id}")
    public Object updateProfile(@PathVariable Integer id, @RequestBody ApplicationUser updatedUser) {
        Optional<ApplicationUser> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            return new ApiResponse(false, "Không tìm thấy người dùng!");
        }

        ApplicationUser user = userOpt.get();

        // Cập nhật các thông tin cho phép
        user.setFullName(updatedUser.getFullName());
        user.setEmail(updatedUser.getEmail());
        user.setPhone(updatedUser.getPhone());
        user.setDob(updatedUser.getDob());
        user.setAvatar(updatedUser.getAvatar());

        userRepository.save(user);

        return new ApiResponse(true, "Cập nhật hồ sơ thành công!");
    }

    // 🟩 Lấy danh sách đơn hàng của 1 user cụ thể
    @GetMapping("/orders/{userId}")
    public Object getOrdersByUser(@PathVariable Integer userId) {
        Optional<ApplicationUser> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return new ApiResponse(false, "Không tìm thấy người dùng!");
        }

        List<Order> orders = orderRepository.findByUser_Id(userId);
        return orders;
    }

}
