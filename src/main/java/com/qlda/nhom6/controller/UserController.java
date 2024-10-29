package com.qlda.nhom6.controller;

import com.qlda.nhom6.model.User;
import com.qlda.nhom6.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
@Controller // Đánh dấu lớp này là một Controller trong Spring MVC.
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
//    @GetMapping("/login")
//    public String login() {
//        return "users/login";
//    }
@GetMapping("/login")
public String login() {
    return "login";
}

    @PostMapping("/login")
    public String authenticateUser(@RequestParam String username,
                                   @RequestParam String password,
                                   Model model) {
        // Xử lý đăng nhập: xác thực username và password
        if (authenticate(username, password)) {
            return "redirect:/home"; // Chuyển hướng đến trang chủ nếu đăng nhập thành công
        } else {
            model.addAttribute("error", true);
            return "login"; // Quay lại trang login nếu đăng nhập thất bại
        }
    }
    private boolean authenticate(String username, String password) {
        // Thực hiện xác thực tài khoản (ví dụ: kiểm tra từ cơ sở dữ liệu)
        return "user".equals(username) && "password".equals(password); // Ví dụ xác thực đơn giản
    }
    @GetMapping("/register")
    public String register(@NotNull Model model) {
        model.addAttribute("user", new User()); // Thêm một đối tượng User mới vào model
        return "register";
    }
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") User user, // Validateđối tượng User
                                   @NotNull BindingResult bindingResult, // Kết quả của quá trình validate
Model model) {
        if (userService.existsByUsername(user.getUsername()))
        {
            bindingResult.rejectValue("username", "error.user", "Username already exists");
        }

        if (userService.existsByEmail(user.getEmail()))
        {
            bindingResult.rejectValue("Email", "error.user", "Email already exists");
        }

        if (userService.existsByPhone(user.getPhone()))
        {
            bindingResult.rejectValue("username", "error.user", "Phone already exists");
        }
        if (bindingResult.hasErrors()) { // Kiểm tra nếu có lỗi validate
            var errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toArray(String[]::new);
            model.addAttribute("errors", errors);
            return "register"; // Trả về lại view "register" nếu có lỗi
        }
        userService.save(user); // Lưu người dùng vào cơ sở dữ liệu
        userService.setDefaultRole(user.getUsername()); // Gán vai trò mặc định cho người dùng
        return "redirect:/login"; // Chuyển hướng người dùng tới trang "login"
    }
}
