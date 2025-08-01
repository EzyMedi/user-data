package com.EzyMedi.user.data.config;

import com.EzyMedi.user.data.service.JWTService;
import com.EzyMedi.user.data.service.MyUserDetailsService;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JWTService jwtService;
    @Autowired
    ApplicationContext applicationContext;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //Bearer token
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String accountName = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            accountName = jwtService.extractUserName(token);
        }
        if (accountName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = (UserDetails) applicationContext.getBean(MyUserDetailsService.class).loadUserByUsername(accountName);
            if(jwtService.validateToken(token, userDetails)){
                UsernamePasswordAuthenticationToken tokenAuthentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                tokenAuthentication.setDetails(new org.springframework.security.web.authentication.WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(tokenAuthentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}
