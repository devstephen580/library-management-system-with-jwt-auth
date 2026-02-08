package com.devstephen.library_management_system_with_jwt_auth.controllers;

import com.devstephen.library_management_system_with_jwt_auth.dtos.LoginRequestDto;
import com.devstephen.library_management_system_with_jwt_auth.dtos.LoginResponseDto;
import com.devstephen.library_management_system_with_jwt_auth.dtos.RegisterRequestDto;
import com.devstephen.library_management_system_with_jwt_auth.entities.User;
import com.devstephen.library_management_system_with_jwt_auth.services.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

  private AuthenticationService authenticationService;

  public AdminController(AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
  }

  @PostMapping("/registeradmin")
  public ResponseEntity<User> registerAdmin(@RequestBody RegisterRequestDto registerRequestDto){
    return ResponseEntity.ok(authenticationService.registerAdmin(registerRequestDto));
  }

}


