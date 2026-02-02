package com.example.backend.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

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
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .csrf(csrf -> csrf.disable())

     
        .formLogin(form -> form
                .loginPage("/admin/login")
                .loginProcessingUrl("/admin/login")
                .defaultSuccessUrl("/admin/dashboard", true)
                .failureUrl("/admin/login?error=true")
                .permitAll()
            )
            .authorizeHttpRequests(auth -> auth
                // ✅ allow admin login page
                .requestMatchers(org.springframework.http.HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers("/admin/login").permitAll()
                .requestMatchers("/favicon.ico").permitAll()
                // ✅ admin pages need ADMIN role
                .requestMatchers("/admin/**").hasRole("ADMIN")

                // ✅ public APIs
                .requestMatchers("/api/login", "/api/register", "/api/movies/**", "/posters/**").permitAll()

                // everything else needs auth
                .anyRequest().authenticated()
            )

            // JWT filter (only affects /api/** now)
            .addFilterBefore(apiAuthenticationFilter,
                    org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

   
    @Autowired
    public void configure (AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }     
}