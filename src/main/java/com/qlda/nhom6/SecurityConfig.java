package com.qlda.nhom6;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // Đánh dấu lớp này là một lớp cấu hình cho Spring Context.
@EnableWebSecurity // Kích hoạt tính năng bảo mật web của Spring Security.
@RequiredArgsConstructor // Lombok tự động tạo constructor có tham số cho tất cả các trường final.
public class SecurityConfig {
    private final OAuthService oAuthService;
    private final UserService userService; // Tiêm UserService vào lớp cấu hình này.
    @Bean // Đánh dấu phương thức trả về một bean được quản lý bởi Spring Context.
    public UserDetailsService userDetailsService() {
        return userService;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Bean mã hóa mật khẩu sử dụng BCrypt.
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        var auth = new DaoAuthenticationProvider(); // Tạo nhà cung cấp xác thực.
        auth.setUserDetailsService(userDetailsService()); // Thiết lập dịch vụ chi tiết người dùng.
                auth.setPasswordEncoder(passwordEncoder()); // Thiết lập cơ chế mã hóa mật khẩu.
        return auth; // Trả về nhà cung cấp xác thực.
    }
    @Bean
    public SecurityFilterChain securityFilterChain(@NotNull HttpSecurity http)
            throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/categories").hasAuthority("ADMIN")
                                .requestMatchers("/blogs").hasAuthority("ADMIN")
                                .requestMatchers("/comments").hasAuthority("ADMIN")
                                .requestMatchers("/css/**", "/js/**", "/", "/oauth/**","/fe/**","/login",
                                        "/register", "/error", "/products", "/products/detail/**","/cart", "/thuocs/search/**","/cart/**", "/admin/**")
                                .permitAll() // Cho phép truy cập không cần xác thực.
                                .requestMatchers("/thuocs/edit/**", "/thuocs/add",
                                        "/thuocs/delete","/thuocs/thuocAdmin","/blogs","/blogs/add","/comments","/datlichs","/doctors/add","/doctors/delete","/doctors/edit","/dvts","dvts/add","/khoas","/khoas/add","/khoa/edit","khoa/delete","/loaithuocs","/loaithuocs/add","/loaithuocs/edit","/loaithuocs/delete","/nhapps","/nhapps/add","/nhapps/edit","/nhapps/delete","/nhasanxuats","/nhasanxuats/add","/nhasanxuats/edit","/nhasanxuats/delete","/timings","/timings/add","/timings/delete")
                                .hasAnyAuthority("ADMIN") // Chỉ cho phép ADMIN truy cập.
                                .requestMatchers("/comments/edit/**", "/blogs/add",
                                "/blogs/delete")
                                 .hasAnyAuthority("ADMIN")
                                .requestMatchers("/categories/edit/**", "/categories/add", "/categories/delete")
                                .hasAnyAuthority("ADMIN") // Chỉ cho phép ADMIN truy cập vào categories.
                                .requestMatchers("/api/**")
                        .hasAnyAuthority("ADMIN", "USER")

                                .anyRequest().authenticated() // Bất kỳ yêu cầu nào khác cần xác thực.
                )
                .logout(logout -> logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/login") // Trang chuyển hướng sau khi đăng xuất.
                .deleteCookies("JSESSIONID") // Xóa cookie.
                .invalidateHttpSession(true) // Hủy phiên làm việc.
                .clearAuthentication(true) // Xóa xác thực.
                .permitAll()
                )

                .formLogin(formLogin -> formLogin
                .loginPage("/login") // Trang đăng nhập.
//                .loginProcessingUrl("/login") // URL xử lý đăng nhập.
//                .defaultSuccessUrl("/") // Trang sau đăng nhập thành công.
//                .failureUrl("/login?error") // Trang đăng nhập thất bại.
                .permitAll()
                )

                .oauth2Login(
                        oauth2Login -> oauth2Login
                                .loginPage("/login")
                                .failureUrl("/login?error")
                                .userInfoEndpoint(userInfoEndpoint ->
                                        userInfoEndpoint
                                                .userService(oAuthService)
                                )
                                .successHandler(
                                        (request, response,
                                         authentication) -> {
                                            var oidcUser =
                                                    (DefaultOidcUser) authentication.getPrincipal();

                                            userService.saveOauthUser(oidcUser.getEmail(), oidcUser.getName());
                                            response.sendRedirect("/");
                                        }
                                )
                                .permitAll()
                )


                                .rememberMe(rememberMe -> rememberMe
                                .key("hutech")
                                .rememberMeCookieName("hutech")
                                .tokenValiditySeconds(24 * 60 * 60) // Thời gian nhớ đăng nhập.
                .userDetailsService(userDetailsService())
                )

                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.sendRedirect("/error"); // Chuyển hướng về trang /products khi bị từ chối truy cập.
                        })
                )

                .sessionManagement(sessionManagement -> sessionManagement
                        .maximumSessions(1) // Giới hạn số phiên đăng nhập.
                        .expiredUrl("/login") // Trang khi phiên hết hạn.
                )

                .httpBasic(httpBasic -> httpBasic
                        .realmName("hutech") // Tên miền cho xác thực cơ bản.
                )
                .build(); // Xây dựng và trả về chuỗi lọc bảo mật.
    }
}