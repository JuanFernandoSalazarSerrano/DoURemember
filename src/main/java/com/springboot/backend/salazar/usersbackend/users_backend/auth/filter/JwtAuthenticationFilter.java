package com.springboot.backend.salazar.usersbackend.users_backend.auth.filter;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.backend.salazar.usersbackend.users_backend.entities.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.springboot.backend.salazar.usersbackend.users_backend.auth.TokenJwtConfig.*;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
                
                String username = null;
                String password = null;

                try {
                    User user = new ObjectMapper().readValue(request.getInputStream(),User.class);
                    username = user.getUsername();
                    password = user.getPassword();                   
                } catch (StreamReadException e) {
                    e.printStackTrace();
                } catch (DatabindException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    username, password);
            
                    // this authenticate calls the service
            return this.authenticationManager.authenticate(authenticationToken);



        }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        
        org.springframework.security.core.userdetails.User user =
        (org.springframework.security.core.userdetails.User) authResult.getPrincipal(); 

        String username = user.getUsername();

        // Adding roles

        Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();
        
        // claims, extra data

        Claims claims = Jwts
        .claims()
        .add("authorities", new ObjectMapper().writeValueAsString(roles))
        .add("username", username)
        .build();



        // generate token but key is in config
        String jwt = Jwts.builder()
        .subject(username)
        .claims(claims)
        .signWith(SECRET_KEY)
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + 10800000))
        .compact(); // genera el token
            
        response.addHeader(HEADER_AUTHORIZATION, PREFIX_TOKEN + jwt);
        
        Map<String, String> body = new HashMap<>();
        body.put("token", jwt);
        body.put("username", username);
        body.put("message", String.format("Hi you logged in", username));

        response.getWriter().write(new ObjectMapper().writeValueAsString(body)); // convierte de string a json y se le pasa al body 
        response.setContentType(CONTENT_TYPE);
        response.setStatus(200);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {

                Map<String, String> body = new HashMap<>();
                body.put("Message", "Auth error with username or password");
                body.put("error", failed.getMessage());

                response.getWriter().write(new ObjectMapper().writeValueAsString(body));
                response.setContentType(CONTENT_TYPE);
                response.setStatus(401);
    }
}
