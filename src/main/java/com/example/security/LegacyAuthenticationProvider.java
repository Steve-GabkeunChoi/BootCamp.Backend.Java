package com.example.security;

import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class LegacyAuthenticationProvider implements AuthenticationProvider {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String presentedPassword = (String) authentication.getCredentials();

        User user = userRepository.findByEmail(username).orElse(null);
        if (user == null) {
            throw new BadCredentialsException("Invalid username or password");
        }

        String stored = user.getPassword();

        boolean matches = false;

        // If stored looks like a bcrypt hash, use encoder
        if (stored != null && (stored.startsWith("$2a$") || stored.startsWith("$2b$") || stored.startsWith("$2y$"))) {
            matches = passwordEncoder.matches(presentedPassword, stored);
        } else {
            // legacy: plain text stored
            if (presentedPassword != null && presentedPassword.equals(stored)) {
                matches = true;
                // upgrade password to encoded form
                String encoded = passwordEncoder.encode(presentedPassword);
                userService.updatePassword(user, encoded);
            }
        }

        if (!matches) {
            throw new BadCredentialsException("Invalid username or password");
        }

        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
        Collection<? extends GrantedAuthority> authorities = Collections.singleton(authority);

        // Use AppUserDetails as principal so templates can access user's name
        AppUserDetails userDetails = new AppUserDetails(user);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
        token.setDetails(user);
        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
