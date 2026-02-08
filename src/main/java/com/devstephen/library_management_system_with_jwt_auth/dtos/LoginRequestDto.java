package com.devstephen.library_management_system_with_jwt_auth.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //Creates getters but not constructors
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequestDto {

  private String username;
  private String password;

}
