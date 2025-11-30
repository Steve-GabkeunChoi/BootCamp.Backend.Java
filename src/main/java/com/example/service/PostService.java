package com.example.service;

import com.example.model.Post;
import com.example.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Page<Post> findAll(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    public Page<Post> search(String q, Pageable pageable) {
        return postRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(q, q, pageable);
    }

    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    public Post create(Post post) {
        return postRepository.save(post);
    }

    public void delete(Long id) {
        postRepository.deleteById(id);
    }
}
