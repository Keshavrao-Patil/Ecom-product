package com.edu.Ecom_product.Controller;

import com.edu.Ecom_product.Model.Users;
import com.edu.Ecom_product.Service.JWTService;
import com.edu.Ecom_product.Service.TokenBlacklistService;
import com.edu.Ecom_product.Service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class UserController
{
    @Autowired
    private UserService userService;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private TokenBlacklistService blacklistService;


    @PostMapping("/register")
    public Users register(@RequestBody Users users){
        return userService.registerUser(users);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Users users, HttpServletResponse response){
        String token = userService.verifyUser(users);
        // If login failed service returns a message (not a JWT) - we keep behavior simple:
        // if token contains spaces or not JWT-looking, return bad request
        if (token == null || token.isBlank() || !token.contains(".")) {
            return ResponseEntity.badRequest().body(token);
        }

        // create HttpOnly cookie with token (30 minutes)
        ResponseCookie cookie = ResponseCookie.from("JWT_TOKEN", token)
                .httpOnly(true)
                .secure(false) // set to true when running over HTTPS in production
                .path("/")
                .maxAge(30 * 60)
                .sameSite("Lax")
                .build();

        response.addHeader("Set-Cookie", cookie.toString());

        // return token in body as well for API clients
        return ResponseEntity.ok(token);
    }

    @PostMapping("/logouta")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        log.info("logging out user, invalidating JWT token and cookie");

        // find token from Authorization header or cookie
        String authHeader = request.getHeader("Authorization");
        String token = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        } else {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie c : cookies) {
                    if ("JWT_TOKEN".equals(c.getName())) {
                        token = c.getValue();
                        break;
                    }
                }
            }
        }

        if (token != null && !token.isBlank()) {
            try {
                long expiry = jwtService.extractExpiration(token).getTime();
                blacklistService.blacklistToken(token, expiry);
            } catch (Exception ex) {
                log.warn("Failed to parse token during logout (may be invalid): {}", ex.getMessage());
                // continue to clear cookie even if token invalid
            }
        }

        // clear SecurityContext for current thread
        SecurityContextHolder.clearContext();

        // clear cookie by setting maxAge=0
        ResponseCookie clear = ResponseCookie.from("JWT_TOKEN", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .sameSite("Lax")
                .build();
        response.addHeader("Set-Cookie", clear.toString());

        log.info("JWT cookie invalidated, user logged out");
        return ResponseEntity.ok("Logged out successfully");
    }
}
