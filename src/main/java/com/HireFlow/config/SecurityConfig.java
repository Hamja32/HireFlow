package com.HireFlow.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.HireFlow.filter.JwtAuthFilter;

import jakarta.servlet.http.HttpServletResponse;
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	@Autowired
	private JwtAuthFilter jwtAuthFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception  {
		  http.csrf(c -> c.disable())
          .sessionManagement(s -> s
              .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
          .exceptionHandling(ex -> ex
        	        .authenticationEntryPoint((request, response, authException) -> {
        	            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, 
        	                "Unauthorized");
        	        }) .accessDeniedHandler((request, response, accessDeniedException) -> {
        	            response.sendError(HttpServletResponse.SC_FORBIDDEN,
        	                    "Forbidden");
        	            })
        	    )
          
          .authorizeHttpRequests(auth -> auth
              .requestMatchers("/api/auth/**").permitAll()
//              .requestMatchers("/api/admin/**").hasAuthority("ROLE_ADMIN")
//              .requestMatchers("/api/company/**").hasAuthority("ROLE_COMPANY")
//              .requestMatchers("/api/seeker/**").hasAuthority("ROLE_SEEKER")
              .anyRequest().authenticated()
          ).addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
}
