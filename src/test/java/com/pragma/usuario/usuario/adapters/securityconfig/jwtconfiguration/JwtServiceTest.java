package com.pragma.usuario.usuario.adapters.securityconfig.jwtconfiguration;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    @Mock
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getToken_ValidUser_ReturnsToken() {
        when(userDetails.getUsername()).thenReturn("testUser");
        when(userDetails.getAuthorities()).thenReturn(Collections.emptyList());

        String token = jwtService.getToken(userDetails);

        assertNotNull(token);
    }

    @Test
    void getUsernameFromToken_ValidToken_ReturnsUsername() {
        String token = createTestToken("testUser");

        String username = jwtService.getUsernameFromToken(token);

        assertEquals("testUser", username);
    }

    @Test
    void getAuthoritiesFromToken_ValidToken_ReturnsAuthorities() {
        String token = createTestTokenWithRole("testUser", "ROLE_USER");

        String role = jwtService.getAuthoritiesFromToken(token);

        assertEquals("ROLE_USER", role);
    }

    @Test
    void isTokenValid_ValidToken_ReturnsTrue() {
        String token = createTestToken("testUser");
        when(userDetails.getUsername()).thenReturn("testUser");

        boolean isValid = jwtService.isTokenValid(token, userDetails);

        assertTrue(isValid);
    }

    @Test
    void isTokenValid_InvalidToken_ReturnsFalse() {
        String token = createTestToken("testUser");
        when(userDetails.getUsername()).thenReturn("anotherUser");

        boolean isValid = jwtService.isTokenValid(token, userDetails);

        assertFalse(isValid);
    }



    private String createTestToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getTestKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private String createTestTokenWithRole(String username, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getTestKey(), SignatureAlgorithm.HS256)
                .compact();
    }



    private Key getTestKey() {
        byte[] keyBytes = Decoders.BASE64.decode("586E3272357538782F413F4428472B4B6250655368566B597033733676397924");
        return Keys.hmacShaKeyFor(keyBytes);
    }
}