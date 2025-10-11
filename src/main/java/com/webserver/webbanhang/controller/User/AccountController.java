package com.webserver.webbanhang.controller.User;

import com.webserver.webbanhang.model.ApplicationUser;
import com.webserver.webbanhang.model.Role;
import com.webserver.webbanhang.repository.RoleRepository;
import com.webserver.webbanhang.repository.UserRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(
        origins = {"http://127.0.0.1:5500", "http://localhost:5500"},
        allowCredentials = "true"
)

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

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
        Optional<ApplicationUser> userOpt = userRepository.findByUsername(loginUser.getUsername());

        if (userOpt.isPresent()) {
            ApplicationUser user = userOpt.get();

            if (passwordEncoder.matches(loginUser.getPassword(), user.getPassword())) {

                // ✅ Gán quyền cho Spring Security
                List<GrantedAuthority> authorities = user.getRoles()
                        .stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toList());

                Authentication auth = new UsernamePasswordAuthenticationToken(user, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(auth);

                // 🟩 TRẢ THÊM user.getId()
                return new LoginResponse(
                        true,
                        "Đăng nhập thành công!",
                        user.getUsername(),
                        user.getFullName(),
                        user.getRoles().stream().map(Role::getName).toList(),
                        user.getId()
                );
            }
        }

        return new LoginResponse(false, "Sai username hoặc password!", null, null, null, null);
    }

    record LoginResponse(boolean success, String message, String username, String fullName, Object roles, Integer id) {
}


}
