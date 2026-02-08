package com.devstephen.library_management_system_with_jwt_auth.dtos;

import java.util.Set;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDto {

  private String jwtToken;
  private String username;
  private Set<String> role;

}
