package com.example.controller;

import com.example.model.User;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@Controller
@RequiredArgsConstructor
public class SignupController {
    
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    
    @GetMapping("/")
    public String index() {
        return "index";
    }
    
    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }
    
    @PostMapping("/api/signup")
    @ResponseBody
    public SignupResponse signup(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new SignupResponse(false, "유효하지 않은 입력입니다");
        }
        
        try {
            User registeredUser = userService.registerUser(user);
            return new SignupResponse(true, "회원가입이 완료되었습니다");
        } catch (Exception e) {
            return new SignupResponse(false, e.getMessage());
        }
    }
    
    @PostMapping("/signup")
    public String signupSubmit(@Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "signup";
        }
        
        try {
            // keep raw password for authentication after registration
            String rawPassword = user.getPassword();
            userService.registerUser(user);

            // authenticate the newly registered user
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), rawPassword)
            );
            SecurityContextHolder.getContext().setAuthentication(auth);

            return "redirect:/posts";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "signup";
        }
    }
    
    @GetMapping("/success")
    public String success() {
        return "success";
    }
    
    public static class SignupResponse {
        public Boolean success;
        public String message;
        
        public SignupResponse(Boolean success, String message) {
            this.success = success;
            this.message = message;
        }
        
        public Boolean getSuccess() { return success; }
        public String getMessage() { return message; }
    }
}
