package com.ssafy.eggmoney.auth.config;

import com.ssafy.eggmoney.auth.service.AuthService;
import com.ssafy.eggmoney.auth.service.KakaoAuthService;
import com.ssafy.eggmoney.auth.service.KakaoOAuth2UserService;
import com.ssafy.eggmoney.user.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthService authService;
    private final UserRepository userRepository;
    private final WebClient.Builder webClientBuilder;


    public SecurityConfig(JwtTokenProvider jwtTokenProvider, AuthService authService
,    UserRepository userRepository, WebClient.Builder webClientBuilder){
        this.jwtTokenProvider = jwtTokenProvider;
        this.authService = authService;
        this.userRepository = userRepository;
        this.webClientBuilder = webClientBuilder;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
//                .csrf(AbstractHttpConfigurer::disable)
                .csrf(csrf -> csrf.disable())  // CSRF 비활성화
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/login","/refresh-token", "/kakao/login", "/kakao/callback").permitAll()
                                .anyRequest().authenticated()
                )
                .oauth2Login(oauth2Login ->
                        oauth2Login
                                .defaultSuccessUrl("/home",true)
                                .failureUrl("/login?error=true")
                                .userInfoEndpoint(userInfoEndpoint->
                                        userInfoEndpoint
                                                .userService(kakaoOAuth2UserService()))
                );
//                .sessionManagement(sessionManagement->
//                        sessionManagement
//                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
//                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, authService), UsernamePasswordAuthenticationFilter.class);
                //.cors(cors -> cors.configurationSource(corsConfigurationSource())); // CORS 설정 추가
        return http.build();
    }
    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> kakaoOAuth2UserService(){
        return new KakaoOAuth2UserService(webClientBuilder);
    }
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration.setAllowedOrigins(Collections.singletonList("http://localhost:5173")); // 허용할 오리진 설정
//        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // 허용할 HTTP 메서드 설정
//        corsConfiguration.setAllowedHeaders(Arrays.asList("*")); // 허용할 헤더 설정
//        corsConfiguration.setAllowCredentials(true);
//        corsConfiguration.setMaxAge(3600L);
//        source.registerCorsConfiguration("/**", corsConfiguration);
//        return source;
//    }
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web)->web.ignoring().requestMatchers("/v2/api-docs", "/swagger-resources/**","/swagger-ui.html","/webjars/**");
//    }
}
