package com.ssafy.eggmoney.user.config;

import com.ssafy.eggmoney.user.service.AuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthService authService;

    public SecurityConfig(JwtTokenProvider jwtTokenProvider, AuthService authService){
        this.jwtTokenProvider = jwtTokenProvider;
        this.authService = authService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/login","/refresh-token").permitAll()
                                .anyRequest().authenticated()
                )
                .sessionManagement(sessionManagement->
                        sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, authService), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public void configure(WebSecurity web) {
        web.ignoring().requestMatchers("/v2/api-docs", "/swagger-resources/**","/swagger-ui.html","/webjars/**");
    }
}
