package com.devstephen.library_management_system_with_jwt_auth.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDto {
  private String username;
  private String email;
  private String password;

}
