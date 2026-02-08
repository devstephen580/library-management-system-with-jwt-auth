package com.devstephen.library_management_system_with_jwt_auth.repositories;

import com.devstephen.library_management_system_with_jwt_auth.entities.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {

  Optional<User> findByUsername(String username);
}
