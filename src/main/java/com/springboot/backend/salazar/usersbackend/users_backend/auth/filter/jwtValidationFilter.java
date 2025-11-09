package com.springboot.backend.salazar.usersbackend.users_backend.auth.filter;

import static com.springboot.backend.salazar.usersbackend.users_backend.auth.TokenJwtConfig.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.backend.salazar.usersbackend.users_backend.auth.SimpleGrantedAuthorityJsonCreator;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class jwtValidationFilter extends BasicAuthenticationFilter{

    public jwtValidationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {


                    String path = request.getRequestURI();

                    if (path.startsWith("/api/v1/groundtruth")) {
                        chain.doFilter(request, response);
                        return;
                    }

                    String header = request.getHeader(HEADER_AUTHORIZATION);
                    if (header == null || !header.startsWith(PREFIX_TOKEN)) {
                        chain.doFilter(request, response);
                        return;
                    }

                
                if(header == null || !header.startsWith(PREFIX_TOKEN)){
                    chain.doFilter(request, response);
                    return;
                }

                String token = header.replace(PREFIX_TOKEN, "");
                // flujo validacion token

                try{
                Claims claims = Jwts.parser().verifyWith(SECRET_KEY)
                .build()
                // here the token validates
                .parseSignedClaims(token)
                .getPayload();
                
                // we get the username
                String username = (String) claims.get("username");
                Object authoritiesClaims = claims.get("authorities");
                
                Collection<? extends GrantedAuthority> roles = Arrays.asList(
                     new ObjectMapper()
                     .addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityJsonCreator.class)
                     .readValue(authoritiesClaims
                     .toString()
                     .getBytes(),
                    SimpleGrantedAuthority[].class));

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    username, null, roles);

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    chain.doFilter(request, response);
                }


                catch(JwtException e){
                    Map<String, String> body = new HashMap<>();
                    body.put("error", e.getMessage());
                    body.put("message", "Invalid token");
                    response.getWriter().write(new ObjectMapper().writeValueAsString(body));
                    response.setStatus(401);
                }

            }
}
