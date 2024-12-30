package com.askrindo.insurance.controller;

import com.askrindo.insurance.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        try {
            return authenticationService.authenticate(username, password);
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    @PostMapping("/refresh-token")
    public String refreshToken(@RequestParam String accessToken) {
        try{
            return authenticationService.refreshToken(accessToken);
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    // endpoint to refresh access token using refresh token
    @PostMapping("/refresh-access-token")
    public String refreshAccessToken(@RequestParam String refreshToken) {
        try {
            return authenticationService.refreshAccessToken(refreshToken);
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }
}
