package com.devstephen.library_management_system_with_jwt_auth.security;

import com.devstephen.library_management_system_with_jwt_auth.jwt.JwtAuthFilter;
import com.devstephen.library_management_system_with_jwt_auth.repositories.UserRepo;
import com.devstephen.library_management_system_with_jwt_auth.services.CustomUserDetailsService;
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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

  private JwtAuthFilter jwtAuthFilter;
  private UserRepo userRepo;
  public SecurityConfig(JwtAuthFilter jwtAuthFilter, UserRepo userRepo) {
    this.jwtAuthFilter = jwtAuthFilter;
    this.userRepo = userRepo;
  }


  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
    http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(request -> request
            .requestMatchers("/api/auth/**").permitAll()
            .requestMatchers("/api/admin/**").hasRole("ADMIN")
            .anyRequest().authenticated()
        )
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authenticationProvider(authenticationProvider())
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }



  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration){
    return configuration.getAuthenticationManager();
  }


  @Bean
  public AuthenticationProvider authenticationProvider() {

    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(userDetailsService());
//    authenticationProvider.setUserDetailsService(userDetailsService());
//    authenticationProvider.setUserDetailsPasswordService();
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    return authenticationProvider;
  }


  @Bean //Spring will auto-inject cos of the service annotation on the class
  public UserDetailsService userDetailsService(){
    return new CustomUserDetailsService(userRepo);
  }

  @Bean
  public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
  }
}
