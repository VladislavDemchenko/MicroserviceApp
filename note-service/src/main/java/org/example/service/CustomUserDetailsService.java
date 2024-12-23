package org.example.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Замініть на логіку отримання користувача з бази даних
        if ("admin".equals(username)) {
            return User.builder()
                    .username("admin")
                    .password(new BCryptPasswordEncoder().encode("password"))
                    .roles("ADMIN")
                    .build();
        }
        throw new UsernameNotFoundException("User not found");
    }
}

