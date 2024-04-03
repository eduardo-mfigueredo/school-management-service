package com.schoolmanagement.poc.config.security;

import com.schoolmanagement.poc.enums.Roles;
import com.schoolmanagement.poc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserService userService;
    private static final String API_ACTIVITIES = "/api/v1/activities/**";
    private static  final String API_GRADES = "/api/v1/grades/**";
    private static final String API_STUDENTS = "/api/v1/students/**";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .antMatchers("/**/no-auth/**", "/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**").permitAll()
                                .antMatchers("/api/v1/auth/**").permitAll()
                                .antMatchers(HttpMethod.GET, API_ACTIVITIES).hasAnyAuthority(Roles.ADMIN.name(), Roles.MANAGER.name())
                                .antMatchers(HttpMethod.POST, API_ACTIVITIES).hasAnyAuthority(Roles.ADMIN.name())
                                .antMatchers(HttpMethod.PUT, API_ACTIVITIES).hasAnyAuthority(Roles.ADMIN.name())
                                .antMatchers(HttpMethod.DELETE, API_ACTIVITIES).hasAnyAuthority(Roles.ADMIN.name())
                                .antMatchers(HttpMethod.GET, API_GRADES).hasAnyAuthority(Roles.ADMIN.name(), Roles.MANAGER.name())
                                .antMatchers(HttpMethod.POST, API_GRADES).hasAnyAuthority(Roles.ADMIN.name(), Roles.MANAGER.name())
                                .antMatchers(HttpMethod.PUT, API_GRADES).hasAnyAuthority(Roles.ADMIN.name())
                                .antMatchers(HttpMethod.DELETE, API_GRADES).hasAnyAuthority(Roles.ADMIN.name())
                                .antMatchers(API_STUDENTS).hasAnyAuthority(Roles.ADMIN.name(), Roles.MANAGER.name())
                                .anyRequest().authenticated()
                )
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider()).addFilterBefore(
                        jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService.userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}