package com.devstephen.library_management_system_with_jwt_auth.controllers;

import com.devstephen.library_management_system_with_jwt_auth.dtos.LoginRequestDto;
import com.devstephen.library_management_system_with_jwt_auth.dtos.LoginResponseDto;
import com.devstephen.library_management_system_with_jwt_auth.dtos.RegisterRequestDto;
import com.devstephen.library_management_system_with_jwt_auth.entities.User;
import com.devstephen.library_management_system_with_jwt_auth.services.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class UserController {

  private AuthenticationService authenticationService;

  public UserController(AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
  }

  @PostMapping("/registeruser")
  public ResponseEntity<User> registerNormalUser(@RequestBody RegisterRequestDto registerRequestDto){
    return ResponseEntity.ok(authenticationService.registerNormalUser(registerRequestDto));
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto){
    return ResponseEntity.ok(authenticationService.login(loginRequestDto));
  }


}
