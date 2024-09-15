package com.pragma.usuario.usuario.domain.spi;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtServicePort {
   String getUsernameFromToken(String token);

   String getAuthoritiesFromToken(String token);

   String getToken(UserDetails user);

    boolean isTokenValid(String token, UserDetails userDetails);
}
