package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "이름은 필수입니다")
    @Column(nullable = false)
    private String name;
    
    @NotBlank(message = "이메일은 필수입니다")
    @Email(message = "올바른 이메일 형식을 입력하세요")
    @Column(nullable = false, unique = true)
    private String email;
    
    @NotBlank(message = "비밀번호는 필수입니다")
    @Size(min = 6, message = "비밀번호는 최소 6자 이상이어야 합니다")
    @Column(nullable = false)
    private String password;
    
    @NotBlank(message = "전화번호는 필수입니다")
    @Pattern(regexp = "^[0-9-]{10,}$", message = "올바른 전화번호 형식을 입력하세요")
    @Column(nullable = false)
    private String phone;
    
    @NotNull(message = "나이는 필수입니다")
    @Min(value = 18, message = "18세 이상이어야 합니다")
    @Max(value = 120, message = "올바른 나이를 입력하세요")
    @Column(nullable = false)
    private Integer age;
    
    @Column(nullable = false)
    private Boolean agreeToTerms;
    
    @Column(name = "created_at")
    private Long createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = System.currentTimeMillis();
    }
}
