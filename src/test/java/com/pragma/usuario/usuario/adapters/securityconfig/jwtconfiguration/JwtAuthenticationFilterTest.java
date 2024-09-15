package com.pragma.usuario.usuario.adapters.securityconfig.jwtconfiguration;

import com.pragma.usuario.usuario.configuration.Constants;
import com.pragma.usuario.usuario.domain.spi.JwtServicePort;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;

import static org.mockito.Mockito.*;

class JwtAuthenticationFilterTest {

    @Mock
    private JwtServicePort jwtServicePort;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void doFilterInternal_ValidToken_AuthenticatesUser() throws IOException, ServletException {
        String token = "validToken";
        String username = "testUser";
        UserDetails userDetails = mock(UserDetails.class);

        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer " + token);
        when(jwtServicePort.getUsernameFromToken(token)).thenReturn(username);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        when(jwtServicePort.isTokenValid(token, userDetails)).thenReturn(true);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(jwtServicePort, times(1)).getUsernameFromToken(token);
        verify(userDetailsService, times(1)).loadUserByUsername(username);
        verify(jwtServicePort, times(1)).isTokenValid(token, userDetails);
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void doFilterInternal_InvalidToken_SendsUnauthorizedError() throws IOException, ServletException {
        String token = "invalidToken";
        String username = "testUser";
        UserDetails userDetails = mock(UserDetails.class);

        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer " + token);
        when(jwtServicePort.getUsernameFromToken(token)).thenReturn(username);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        when(jwtServicePort.isTokenValid(token, userDetails)).thenReturn(false);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(response, times(1)).sendError(HttpServletResponse.SC_UNAUTHORIZED, Constants.INVALID_TOKEN);
        verify(filterChain, times(0)).doFilter(request, response);
    }

    @Test
    void doFilterInternal_NoToken_ContinuesFilterChain() throws IOException, ServletException {
        // Simula que no hay token en la cabecera de autorización
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(null);

        // Ejecuta el filtro
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Verifica que no se llamaron métodos relacionados con el token
        verify(jwtServicePort, times(0)).getUsernameFromToken(anyString());
        verify(userDetailsService, times(0)).loadUserByUsername(anyString());
        verify(jwtServicePort, times(0)).isTokenValid(anyString(), any(UserDetails.class));

        // Verifica que la cadena de filtros continúa
        verify(filterChain, times(2)).doFilter(request, response);
    }
}