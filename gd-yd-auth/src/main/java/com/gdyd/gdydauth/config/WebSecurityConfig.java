package com.gdyd.gdydauth.config;

import com.gdyd.gdydauth.jwt.JwtAuthenticationEntryPoint;
import com.gdyd.gdydauth.jwt.JwtFilter;
import com.gdyd.gdydauth.jwt.JwtProvider;
import com.gdyd.gdydauth.utils.HttpRequestEndpointChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;
import java.util.stream.Stream;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtProvider jwtProvider;
    private final HttpRequestEndpointChecker endpointChecker;

    private static final String[] AUTH_WHITELIST = {
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/api-docs/json/**",

            "/api/v1/auth/login",
            "/api/v1/auth/signup/**",
            "/api/v1/auth/refresh",
            "/api/v1/auth/send-verification-email",
            "/api/v1/auth/verify-code",
            "/api/v1/auth/forgot-password",
            "/api/v1/auth/forgot-password/**",
            "/api/v1/member/existing-email",
            "/api/v1/member/existing-nickname",

            "/h2-console",
            "/h2-console/**"
    };

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(c -> c.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOriginPatterns(List.of("*"));
                    config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "HEAD", "OPTIONS"));
                    config.setAllowedHeaders(List.of("*"));
                    config.setAllowCredentials(true);
                    return config;
                }))
                .addFilterBefore(new JwtFilter(jwtProvider, endpointChecker), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(req -> req
                        .requestMatchers(permittedWhiteList()).permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private AntPathRequestMatcher[] permittedWhiteList() {
        return Stream.of(AUTH_WHITELIST)
                .map(AntPathRequestMatcher::new)
                .toArray(AntPathRequestMatcher[]::new);
    }
}
