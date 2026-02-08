package com.devstephen.library_management_system_with_jwt_auth.services;

import com.devstephen.library_management_system_with_jwt_auth.dtos.LoginRequestDto;
import com.devstephen.library_management_system_with_jwt_auth.dtos.LoginResponseDto;
import com.devstephen.library_management_system_with_jwt_auth.dtos.RegisterRequestDto;
import com.devstephen.library_management_system_with_jwt_auth.entities.User;
import com.devstephen.library_management_system_with_jwt_auth.jwt.JwtService;
import com.devstephen.library_management_system_with_jwt_auth.repositories.UserRepo;
import java.util.HashSet;
import java.util.Set;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

  private JwtService jwtService;

  private UserRepo userRepo;
  private PasswordEncoder passwordEncoder;
  private AuthenticationManager authManager;

  public AuthenticationService(JwtService jwtService, UserRepo userRepo, PasswordEncoder passwordEncoder,
      AuthenticationManager authManager) {
    this.jwtService = jwtService;
    this.userRepo = userRepo;
    this.passwordEncoder = passwordEncoder;
    this.authManager = authManager;
  }

  public User registerAdmin(RegisterRequestDto registerRequestDto) {
    if (userRepo.findByUsername(registerRequestDto.getUsername()).isPresent()) {
      throw new RuntimeException("User already registered.");
    }

    Set<String> role = new HashSet<>();
    role.add("ROLE_ADMIN");
    role.add("ROLE_USER");

    User user = new User();
    user.setUsername(registerRequestDto.getUsername());
    user.setEmail(registerRequestDto.getEmail());
    user.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
    user.setRoles(role);

    return userRepo.save(user);
  }

  public LoginResponseDto login(LoginRequestDto loginRequestDto) {

    authManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(),
            loginRequestDto.getPassword())
    );

    User user = userRepo.findByUsername(loginRequestDto.getUsername())
        .orElseThrow(() -> new RuntimeException("User not found."));

    String jwtToken = jwtService.generateToken(user);

    return LoginResponseDto.builder()
        .jwtToken(jwtToken)
        .username(user.getUsername())
        .role(user.getRoles())
        .build();

  }

  public User registerNormalUser(RegisterRequestDto registerRequestDto) {
    if (userRepo.findByUsername(registerRequestDto.getUsername()).isPresent()) {
      throw new RuntimeException("User already registered.");
    }

    Set<String> role = new HashSet<>();
    role.add("ROLE_USER");

    User user = new User();
    user.setUsername(registerRequestDto.getUsername());
    user.setEmail(registerRequestDto.getEmail());
    user.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
    user.setRoles(role);

    return userRepo.save(user);
  }
}
