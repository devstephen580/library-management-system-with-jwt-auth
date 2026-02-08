package com.devstephen.library_management_system_with_jwt_auth.services;

import com.devstephen.library_management_system_with_jwt_auth.entities.User;
import com.devstephen.library_management_system_with_jwt_auth.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepo userRepo;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepo.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found."));
  }
}
