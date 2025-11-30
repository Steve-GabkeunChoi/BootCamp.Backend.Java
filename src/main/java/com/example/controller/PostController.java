package com.example.controller;

import com.example.model.Post;
import com.example.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("posts", postService.findAll());
        return "posts";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("post", new Post());
        return "new_post";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute Post post, BindingResult br) {
        if (br.hasErrors()) {
            return "new_post";
        }
        postService.create(post);
        return "redirect:/posts";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model) {
        postService.findById(id).ifPresent(p -> model.addAttribute("post", p));
        return "post";
    }
}
