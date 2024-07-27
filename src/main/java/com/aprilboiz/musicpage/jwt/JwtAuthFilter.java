package com.aprilboiz.musicpage.jwt;

import com.aprilboiz.musicpage.exception.AccessDeniedException;
import com.aprilboiz.musicpage.exception.dto.ApiErrorResponse;
import com.aprilboiz.musicpage.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
@PropertySource("classpath:jwt.properties")
public class JwtAuthFilter extends OncePerRequestFilter {
    private final UserService userService;
    private final ObjectMapper objectMapper;
    private final JwtHelper jwtHelper;
    @Value("${jwt.token.prefix}")
    private String TOKEN_TYPE;

    public JwtAuthFilter(UserService userService, ObjectMapper objectMapper, JwtHelper jwtHelper) {
        this.userService = userService;
        this.objectMapper = objectMapper;
        this.jwtHelper = jwtHelper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String authHeader = request.getHeader("Authorization");
            String username;
            String token;
            if (authHeader != null && authHeader.startsWith("%s ".formatted(TOKEN_TYPE))){
                token = authHeader.substring(7);
                username = jwtHelper.extractUsername(token);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                    UserDetails userDetails = userService.loadUserByUsername(username);

                    if (jwtHelper.validateToken(token, userDetails)){
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities()
                        );
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
            }
            filterChain.doFilter(request, response);
        } catch (AccessDeniedException ex){
            ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
                    HttpServletResponse.SC_FORBIDDEN,
                    ex.getMessage()
            );
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write(toJson(apiErrorResponse));
        }
    }

    private String toJson(ApiErrorResponse response) {
        try {
            return objectMapper.writeValueAsString(response);
        } catch (Exception e) {
            return ""; // Return an empty string if serialization fails
        }
    }
}
