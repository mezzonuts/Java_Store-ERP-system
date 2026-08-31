package com.sosha.core.security;

import com.sosha.core.domain.User;
import com.sosha.core.repository.UserRepository;
import com.sosha.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired private UserRepository userRepo;
    @Autowired private JwtService jwtService;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public String login(String username, String password) {
        User user = userRepo.findByUsername(username).orElseThrow(() -> new RuntimeException("Invalid credentials"));
        if (!encoder.matches(password, user.getPasswordHash())) {
            throw new RuntimeException("Invalid credentials");
        }
        return jwtService.generate(user.getId(), user.getTenantId(), user.getBranchId(), user.getRole().name());
    }
}
