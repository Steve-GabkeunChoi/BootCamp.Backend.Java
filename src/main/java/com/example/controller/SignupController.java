package com.example.controller;

import com.example.model.User;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import com.example.security.CustomUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@Controller
@RequiredArgsConstructor
public class SignupController {
    
    private final UserService userService;
    private final CustomUserDetailsService customUserDetailsService;
    
    @GetMapping("/")
    public String index(org.springframework.security.core.Authentication authentication) {
        // If the user is authenticated, send them to the posts list
        if (authentication != null && authentication.isAuthenticated() &&
                !(authentication instanceof org.springframework.security.authentication.AnonymousAuthenticationToken)) {
            return "redirect:/posts";
        }

        // Otherwise send to login page
        return "redirect:/login";
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
                userService.registerUser(user);

                // Auto-login: load UserDetails and set authentication in SecurityContext
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getEmail());
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authToken);

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
