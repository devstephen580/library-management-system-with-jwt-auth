package com.devstephen.library_management_system_with_jwt_auth.jwt;

import com.devstephen.library_management_system_with_jwt_auth.entities.User;
import com.devstephen.library_management_system_with_jwt_auth.repositories.UserRepo;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final UserRepo userRepo;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    final String authHeader = request.getHeader("Authorization");
    final String jwtToken;
    final String username;

    //Checking if authHeader is present and starts with Bearer
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    //Extract jwtToken from Header
    jwtToken = authHeader.substring(7);
    username = jwtService.extractUsername(jwtToken);

    //Check if we have a username and not authentication yet - prevents re-authentication
    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

      //Get user details from db
      User userDetails = userRepo.findByUsername(username)
          .orElseThrow(() -> new RuntimeException("User not found."));

      //Validate the token
      if (jwtService.isTokenValid(jwtToken, userDetails)) {

        List<SimpleGrantedAuthority> authorities = userDetails.getRoles().stream()
            .map(SimpleGrantedAuthority::new).toList();

        //Create the authentication with user roles
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            authorities
        );

        //Set authentication details
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        //Update the security context holder
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
      }
    }
    filterChain.doFilter(request, response);

  }

}
