package com.pragma.usuario.usuario.domain.usecases;

import com.pragma.usuario.usuario.adapters.driving.http.dto.user.response.AuthorizationResponse;
import com.pragma.usuario.usuario.adapters.driving.http.mapper.login.response.AuthResponse;
import com.pragma.usuario.usuario.domain.exception.ValidationException;
import com.pragma.usuario.usuario.domain.model.Login;
import com.pragma.usuario.usuario.domain.model.User;
import com.pragma.usuario.usuario.domain.spi.IUserPersistencePort;
import com.pragma.usuario.usuario.domain.spi.JwtServicePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserUseCaseTest {

    @Mock
    private IUserPersistencePort userPersistencePort;

    @Mock
    private JwtServicePort jwtServicePort;

    @InjectMocks
    private UserUseCase userUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCustomer_ValidUser_Success() {
        User user = new User();
        user.setBirthDate(LocalDate.now().minusYears(20));
        user.setEmail("test@example.com");
        user.setPhoneNumber("1234567890");

        doNothing().when(userPersistencePort).saveUser(user);

        assertDoesNotThrow(() -> userUseCase.createCustomer(user));
        verify(userPersistencePort, times(1)).saveUser(user);
    }

    @Test
    void createCustomer_InvalidUser_ThrowsValidationException() {
        User user = new User();
        user.setBirthDate(LocalDate.now().minusYears(10)); // Invalid age

        ValidationException exception = assertThrows(ValidationException.class, () -> userUseCase.createCustomer(user));
        assertTrue(exception.getErrors().contains("User must be at least 18 years old"));
    }

    @Test
    void validateToken_ValidToken_ReturnsAuthorizationResponse() {
        String token = "validToken";
        String email = "test@example.com";
        String role = "ROLE_USER";

        when(jwtServicePort.getUsernameFromToken(token)).thenReturn(email);
        when(jwtServicePort.getAuthoritiesFromToken(token)).thenReturn(role);
        when(userPersistencePort.findIdByUsername(email)).thenReturn(Optional.of(1L));

        AuthorizationResponse response = userUseCase.validateToken(token);

        assertTrue(response.isPresent());
        assertEquals(email, response.getEmail());
        assertEquals(role, response.getRole());
    }

    @Test
    void login_ValidLogin_ReturnsAuthResponse() {
        Login login = new Login();
        login.setEmail("test");
        login.setPassword("password");

        AuthResponse expectedResponse = new AuthResponse();
        when(userPersistencePort.login(login)).thenReturn(expectedResponse);

        AuthResponse actualResponse = userUseCase.login(login);

        assertEquals(expectedResponse, actualResponse);
    }
}