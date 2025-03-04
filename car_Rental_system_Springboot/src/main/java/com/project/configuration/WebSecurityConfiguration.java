//package com.project.configuration;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import com.project.enums.UserRole;
//import com.project.service.jwt.UserService;
//
//import lombok.AllArgsConstructor;
//
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity
//@AllArgsConstructor
//public class WebSecurityConfiguration {
//	private final JWTAuthenticationFilter jwtFilter;
//	private final UserService service;
//	
//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//	    http.csrf(csrf -> csrf.disable()) // Disable CSRF
//	        .authorizeHttpRequests(request -> request
//	            .requestMatchers("/api/auth/**").permitAll() // Allow access to auth endpoints
//	            .requestMatchers("/api/admin/**").hasAnyAuthority(UserRole.ADMIN.name()) // Restrict admin endpoints
//	            .requestMatchers("/api/customer/**").hasAnyAuthority(UserRole.CUSTOMER.name()) // Restrict customer endpoints
//	            .anyRequest().authenticated() // Require authentication for all other requests
//	        )
//	        .sessionManagement(manager -> manager
//	            .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Set session management to stateless
//	        )
//	        .authenticationProvider(authenticationProvider1()) // Set the custom authentication provider
//	        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); // Add JWT filte)r
//
//	    return http.build();
//	}
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//	@Bean
//	public AuthenticationProvider authenticationProvider1() {
//		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//		authProvider.setUserDetailsService(service.userdetailsservice());
//		authProvider.setPasswordEncoder(passwordEncoder());
//		return authProvider;
//		
//	}
//	@Bean
//	public AuthenticationManager authenticationManager( AuthenticationConfiguration config) throws Exception {
//		
//		return config.getAuthenticationManager();
//	}
//	
//}


package com.project.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.project.service.jwt.UserService;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
public class WebSecurityConfiguration {
    private final JWTAuthenticationFilter jwtFilter;
    private final UserService service;
    
    
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("http://localhost:4200").allowedMethods("*").allowedHeaders("*");
            }
        };
    }

    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF protection
            .cors(cors -> cors.configure(http)) // Enable CORS for frontend communication
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll() // Publicly accessible endpoints
                .requestMatchers("/api/admin/**").hasAuthority("ADMIN") // Restrict admin endpoints
                .requestMatchers("/api/customer/**").hasAuthority("CUSTOMER") // Restrict customer endpoints
                .anyRequest().authenticated() // Require authentication for all other endpoints
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Stateless session management
            )
            .authenticationProvider(authenticationProvider()) // Set authentication provider
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); // Add JWT filter
        
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(service.userdetailsservice());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
