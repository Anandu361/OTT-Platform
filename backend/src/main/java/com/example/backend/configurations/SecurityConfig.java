package com.example.backend.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.backend.security.ApiAuthenticationFilter;
import com.example.backend.service.CustomUserDetailsService;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
   
   
    @Autowired
    CustomUserDetailsService customUserDetailsService;
    
    @Autowired
    private ApiAuthenticationFilter apiAuthenticationFilter;
   
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
   
    @Bean
    public org.springframework.web.cors.CorsConfigurationSource corsConfigurationSource() {

        org.springframework.web.cors.CorsConfiguration config =
                new org.springframework.web.cors.CorsConfiguration();

        config.setAllowedOrigins(java.util.List.of(
                "http://localhost:5173",   // React (Vite)
                "http://localhost:3000"    // React (CRA)
        ));

        config.setAllowedMethods(java.util.List.of(
                "GET", "POST", "PUT", "DELETE", "OPTIONS"
        ));

        config.setAllowedHeaders(java.util.List.of("*"));
        config.setAllowCredentials(true);

        org.springframework.web.cors.UrlBasedCorsConfigurationSource source =
                new org.springframework.web.cors.UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", config);

        return source;
    }

    
   
    @Bean
    @org.springframework.core.annotation.Order(1)
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {

        http
            .securityMatcher("/api/**")

            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/api/login",
                    "/api/register",
                    "/api/movies/**"
                ).permitAll()
                .anyRequest().authenticated()
            )

            // ❌ NO formLogin
            // ❌ NO logout

            .addFilterBefore(
                apiAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
            )

            // ⛔ Never redirect APIs to login page
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint((req, res, e) -> {
                    res.setStatus(401);
                    res.setContentType("application/json");
                    res.getWriter().write("{\"error\":\"Unauthorized\"}");
                })
            );

        return http.build();
    }
    
    @Bean
    @org.springframework.core.annotation.Order(2)
    public SecurityFilterChain adminSecurityFilterChain(HttpSecurity http) throws Exception {

        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(auth -> auth

                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                // admin auth
                .requestMatchers(
                    "/admin/login",
                    "/admin/login-process",
                    "/admin/logout",
                    "/favicon.ico",
                    "/posters/**",
                    "/videos/**"
                ).permitAll()

                // admin area
                .requestMatchers("/admin/**").hasRole("ADMIN")

                .anyRequest().authenticated()
            )

            .formLogin(form -> form
                .loginPage("/admin/login")
                .loginProcessingUrl("/admin/login-process")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/admin/dashboard", true)
                .failureUrl("/admin/login?error=true")
            )

            .logout(logout -> logout
                .logoutUrl("/admin/logout")
                .logoutSuccessUrl("/admin/login?logout=true")
            );

        return http.build();
    }



   
    @Autowired
    public void configure (AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }     
}