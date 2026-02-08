package com.devstephen.library_management_system_with_jwt_auth.dtos;

import lombok.Data;

@Data
public class BookDto {

  private String title;
  private String author;
  private String isbn;
  private Integer quantity;
  private Boolean isAvailable;

}
