package com.devstephen.library_management_system_with_jwt_auth.repositories;

import com.devstephen.library_management_system_with_jwt_auth.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepo extends JpaRepository<Book, Long> {

}
