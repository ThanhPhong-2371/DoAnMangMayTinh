package com.webserver.webbanhang.security;

import com.webserver.webbanhang.model.CustomUserDetailsService;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())// Tắt CSRF (REST API không cần)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))// Cấu hình CORS Cho phép frontend (localhost:5500) gửi request đến.
                .authenticationProvider(authenticationProvider())// Gắn Authentication Provider
                .authorizeHttpRequests(auth -> auth// định nghĩa quyền truy cập theo URL.
                .requestMatchers("/account/**").permitAll() // cho phép gọi API login/register
                .requestMatchers("/admin/**").permitAll()
                .anyRequest().permitAll()
                )
                // ❌ Bỏ phần formLogin
                .httpBasic(httpBasic -> httpBasic.disable()) // Không dùng login form mặc định
                .formLogin(form -> form.disable()) // ❌ Tắt form login của Spring
                .logout(logout -> logout
                .logoutUrl("/account/logout")
                .logoutSuccessHandler((req, res, auth) -> {
                    res.setContentType("application/json;charset=UTF-8");
                    res.getWriter().write("{\"success\": true, \"message\": \"Đăng xuất thành công!\"}");
                })
                .permitAll()
                );

        return http.build();
    }
    //Quản lý toàn bộ quá trình xác thực.
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder.class)
                .authenticationProvider(authenticationProvider())
                .build();
    }
    //Bộ xử lý xác thực người dùng kiểu “Username + Password”, lấy thông tin từ database.
    //“Lấy user từ DB bằng CustomUserDetailsService, rồi kiểm tra password với BCryptPasswordEncoder.”
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    //Lưu thông tin đăng nhập của người dùng vào HttpSession.
    //Khi user login thành công, session chứa SecurityContext giúp nhận diện user trong các request tiếp theo.
    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new HttpSessionSecurityContextRepository();
    }
   //Cho phép trình duyệt ở cổng khác (localhost:5500) gọi API của bạn.
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(Arrays.asList("http://127.0.0.1:5500", "http://localhost:5500"));
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
