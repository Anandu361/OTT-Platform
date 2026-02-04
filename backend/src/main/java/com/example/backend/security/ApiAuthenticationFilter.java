package com.example.backend.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.backend.models.UserModel;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.CustomUserDetail;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class ApiAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private TokenGenerator tokenGenerator;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String uri = request.getRequestURI();

        // ✅ Only process /api/** routes
        if (!uri.startsWith("/api/")) {
            filterChain.doFilter(request, response);
            return;
        }

        // ✅ Public auth endpoints (no token required)
        if (uri.equals("/api/login") || uri.equals("/api/register")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");

        /*
         * ⭐ VERY IMPORTANT FIX
         * If no token is sent → allow request to continue.
         * This keeps public endpoints like /api/movies accessible.
         */
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        // ✅ Validate token
        if (!tokenGenerator.validateToken(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid or expired token");
            return;
        }

        UserModel user = userRepository.findByToken(token);

        if (user == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("User not found for token");
            return;
        }

        // ✅ Blocked users are denied access
        if (user.isBlocked()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("User blocked by admin");
            return;
        }

        // ✅ Set authentication for valid users
        CustomUserDetail userDetails = new CustomUserDetail(user);

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}
