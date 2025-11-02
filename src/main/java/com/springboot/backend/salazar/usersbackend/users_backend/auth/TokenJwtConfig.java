package com.springboot.backend.salazar.usersbackend.users_backend.auth;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Jwts;

public class TokenJwtConfig {

    // generate secret key

    public static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();

    public static final String HEADER_AUTHORIZATION = "Authorization";

    public static final String PREFIX_TOKEN = "Bearer ";

    public static final String CONTENT_TYPE = "application/json";
}
