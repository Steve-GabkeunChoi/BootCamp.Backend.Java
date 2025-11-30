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
        // 기본: 페이지 0, size 10
        return listWithPagination(0, 10, null, model);
    }

    @GetMapping(params = {"page", "size"})
    public String listWithPagination(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size,
                                     @RequestParam(required = false) String q,
                                     Model model) {
        org.springframework.data.domain.Pageable pageable = org.springframework.data.domain.PageRequest.of(page, size, org.springframework.data.domain.Sort.by("createdAt").descending());
        org.springframework.data.domain.Page<com.example.model.Post> postsPage;
        if (q != null && !q.isBlank()) {
            postsPage = postService.search(q, pageable);
            model.addAttribute("q", q);
        } else {
            postsPage = postService.findAll(pageable);
        }
        model.addAttribute("postsPage", postsPage);
        model.addAttribute("posts", postsPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", postsPage.getTotalPages());
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

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        postService.findById(id).ifPresent(p -> model.addAttribute("post", p));
        return "edit_post";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute Post post, BindingResult br) {
        if (br.hasErrors()) {
            return "edit_post";
        }
        postService.findById(id).ifPresent(existing -> {
            existing.setTitle(post.getTitle());
            existing.setContent(post.getContent());
            existing.setAuthor(post.getAuthor());
            postService.create(existing);
        });
        return "redirect:/posts/" + id;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        postService.delete(id);
        return "redirect:/posts";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model) {
        postService.findById(id).ifPresent(p -> model.addAttribute("post", p));
        return "post";
    }
}
