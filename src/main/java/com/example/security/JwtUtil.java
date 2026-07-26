package com.example.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtil {

    private static final Key KEY =
            Keys.hmacShaKeyFor(SecurityConstants.SECRET.getBytes());

    // 🔹 Generate token
    public static String generateToken(String username, String role) {

        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)   // VERY IMPORTANT
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + 1000 * 60 * 60)
                )
                .signWith(KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    // 🔹 Extract username
    public static String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // 🔹 Extract role
    public static String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    // 🔹 Extract all claims
    private static Claims extractAllClaims(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}