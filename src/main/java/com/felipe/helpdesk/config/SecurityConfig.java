package com.felipe.helpdesk.config;

import com.felipe.helpdesk.security.CustomBearerAuthenticationEntryPoint;
import com.felipe.helpdesk.security.CustomBearerTokenAccessDeniedHandler;
import com.felipe.helpdesk.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final CustomBearerAuthenticationEntryPoint customBearerAuthenticationEntryPoint;
    private final CustomBearerTokenAccessDeniedHandler customBearerTokenAccessDeniedHandler;
    private final Environment env;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        if (Arrays.asList(env.getActiveProfiles()).contains("test")){
            http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()));
        }

        http
                .cors()
                .and()
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests()
                .requestMatchers("/login").permitAll()
                .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exceptions -> exceptions.authenticationEntryPoint(this.customBearerAuthenticationEntryPoint)
                        .accessDeniedHandler(this.customBearerTokenAccessDeniedHandler))
        ;

        return http.build();
    }

    @Bean
    CorsConfigurationSource configurationSource(){
        CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
        configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
