package com.example.security;

public class SecurityConstants {

    // 🔐 MUST be at least 64 characters for HS512
    public static final String SECRET =
            "THIS_IS_A_VERY_LONG_SECURE_SECRET_KEY_FOR_JWT_SIGNING_1234567890";

    public static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000; // 1 day

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}
