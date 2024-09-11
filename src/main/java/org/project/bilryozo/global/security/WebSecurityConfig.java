package org.project.bilryozo.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.project.bilryozo.domain.users.repository.UsersRepository;
import org.project.bilryozo.global.redis.RedisDao;
import org.project.bilryozo.global.security.jwt.JwtProvider;
import org.project.bilryozo.global.security.jwt.filter.JwtAuthenticationProcessingFilter;
import org.project.bilryozo.global.security.login.filter.CustomJsonUsernamePasswordAuthenticationFilter;
import org.project.bilryozo.global.security.login.handler.LoginFailureHandler;
import org.project.bilryozo.global.security.login.handler.LoginSuccessHandler;
import org.project.bilryozo.global.security.login.service.LoginService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity // Spring Security 지원을 가능하게 함
@RequiredArgsConstructor
public class WebSecurityConfig {

    private static final String[] USERS_API_URL = {
            "/api/users/register",
            "/api/users/logout"
    };

    private static final String[] BOOKS_API_URL = {
            "/api/books/create",
            "/api/books/{id}",
            "/api/books",
            "/api/books/popular",
    };

    private final LoginService loginService;
    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper;
    private final UsersRepository usersRepository;
    private final RedisDao redisDao;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(loginService);
        return new ProviderManager(provider);
    }

    @Bean
    public LoginSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler(jwtProvider, redisDao);
    }

    @Bean
    public LoginFailureHandler loginFailureHandler() {
        return new LoginFailureHandler();
    }

    @Bean
    public CustomJsonUsernamePasswordAuthenticationFilter customJsonUsernamePasswordAuthenticationFilter() {
        CustomJsonUsernamePasswordAuthenticationFilter customJsonUsernamePasswordAuthenticationFilter
                = new CustomJsonUsernamePasswordAuthenticationFilter(objectMapper);
        customJsonUsernamePasswordAuthenticationFilter.setAuthenticationManager(authenticationManager());
        customJsonUsernamePasswordAuthenticationFilter.setAuthenticationSuccessHandler(loginSuccessHandler());
        customJsonUsernamePasswordAuthenticationFilter.setAuthenticationFailureHandler(loginFailureHandler());
        return customJsonUsernamePasswordAuthenticationFilter;
    }

    @Bean
    public JwtAuthenticationProcessingFilter jwtAuthenticationProcessingFilter() {
        return new JwtAuthenticationProcessingFilter(jwtProvider, redisDao, usersRepository);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()  // 정적 리소스
                        .requestMatchers("/").permitAll()
                        .requestMatchers(USERS_API_URL).permitAll()
                        .requestMatchers(BOOKS_API_URL).permitAll()
                )
                .addFilterAfter(customJsonUsernamePasswordAuthenticationFilter(), LogoutFilter.class)
                .addFilterBefore(jwtAuthenticationProcessingFilter(), CustomJsonUsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
