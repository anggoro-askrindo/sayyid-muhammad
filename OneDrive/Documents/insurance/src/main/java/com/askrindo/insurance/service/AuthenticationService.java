package com.askrindo.insurance.service;

import com.askrindo.insurance.model.User;
import com.askrindo.insurance.security.JwtTokenUtil;

import lombok.extern.slf4j.Slf4j;

import com.askrindo.insurance.repository.UserRepository;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String authenticate(String username, String password) {
        // find user by username
        User user = userRepository.findByUsername(username);
        if (user == null) {
            // instead of returning runtime exception, return as a response to the client
            throw new RuntimeException("User not found");
        }
        log.info("input password: " + password);
        log.info("encoded password: " + user.getPassword());
        log.info("encoded input password: " + passwordEncoder.encode(password));
        log.info("password matches: " + passwordEncoder.matches(password, user.getPassword()));
        if (passwordEncoder.matches(password, user.getPassword())) {
            String role = user.getRoles().iterator().next().getRoleName();
            return jwtTokenUtil.generateToken(username, role);
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }

    public String refreshToken(String accesToken) {
        // check if access token is expired or invalid
        if (jwtTokenUtil.isTokenExpired(accesToken)) {
            throw new RuntimeException("Access token expired");
        }

        // extract username from access token
        String username = jwtTokenUtil.extractUsername(accesToken);
        
        return jwtTokenUtil.generateRefreshToken(username);
    }

    public String refreshAccessToken(String refreshToken) {
        String username = jwtTokenUtil.extractUsername(refreshToken);
        // extract date of expiration from refresh token
        Date expirationDate = jwtTokenUtil.extractClaims(refreshToken).getExpiration();
        // check if refresh token is expired
        if (expirationDate.before(new Date())) {
            throw new RuntimeException("Refresh token expired");
        }
        
        return jwtTokenUtil.generateToken(username, userRepository.findByUsername(username).getRoles().iterator().next().getRoleName());
    }
}
