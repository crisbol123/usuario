package com.pragma.usuario.usuario.adapters.driven.jpa.mysql.adapter;

import com.pragma.usuario.usuario.adapters.driven.jpa.mysql.entity.UserEntity;
import com.pragma.usuario.usuario.adapters.driven.jpa.mysql.exception.DuplicateDocumentException;
import com.pragma.usuario.usuario.adapters.driven.jpa.mysql.exception.DuplicateEmailException;
import com.pragma.usuario.usuario.adapters.driven.jpa.mysql.mapper.IUserEntityMapper;
import com.pragma.usuario.usuario.adapters.driven.jpa.mysql.repository.IRoleRepository;
import com.pragma.usuario.usuario.adapters.driven.jpa.mysql.repository.IUserRepository;
import com.pragma.usuario.usuario.adapters.driving.http.mapper.login.response.AuthResponse;
import com.pragma.usuario.usuario.domain.model.Login;
import com.pragma.usuario.usuario.domain.model.User;
import com.pragma.usuario.usuario.domain.spi.JwtServicePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserAdapterTest {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private IUserEntityMapper userEntityMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private IRoleRepository iRoleRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserDetailsService userDetailsServiceImp;

    @Mock
    private JwtServicePort jwtServicePort;

    @InjectMocks
    private UserAdapter userAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveUser_ValidUser_Success() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setIdentityDocument("1234567890");
        user.setPassword("password");

        when(userRepository.findIdByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(userRepository.findIdByIdentityDocument(user.getIdentityDocument())).thenReturn(Optional.empty());
        when(userEntityMapper.toEntity(user, iRoleRepository)).thenReturn(new UserEntity());
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");

        assertDoesNotThrow(() -> userAdapter.saveUser(user));
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void saveUser_DuplicateEmail_ThrowsDuplicateEmailException() {
        User user = new User();
        user.setEmail("test@example.com");

        when(userRepository.findIdByEmail(user.getEmail())).thenReturn(Optional.of(1L));

        assertThrows(DuplicateEmailException.class, () -> userAdapter.saveUser(user));
    }

    @Test
    void saveUser_DuplicateDocument_ThrowsDuplicateDocumentException() {
        User user = new User();
        user.setIdentityDocument("1234567890");

        when(userRepository.findIdByIdentityDocument(user.getIdentityDocument())).thenReturn(Optional.of(1L));

        assertThrows(DuplicateDocumentException.class, () -> userAdapter.saveUser(user));
    }

    @Test
    void login_ValidLogin_ReturnsAuthResponse() {
        Login login = new Login();
        login.setEmail("test@example.com");
        login.setPassword("password");

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetailsServiceImp.loadUserByUsername(login.getEmail())).thenReturn(userDetails);
        when(jwtServicePort.getToken(userDetails)).thenReturn("token");

        AuthResponse response = userAdapter.login(login);

        assertNotNull(response);
        assertEquals("token", response.getToken());
    }

    @Test
    void findIdByUsername_ExistingUser_ReturnsId() {
        String username = "test@example.com";
        when(userRepository.findIdByEmail(username)).thenReturn(Optional.of(1L));

        Optional<Long> userId = userAdapter.findIdByUsername(username);

        assertTrue(userId.isPresent());
        assertEquals(1L, userId.get());
    }

    @Test
    void findIdByUsername_NonExistingUser_ReturnsEmpty() {
        String username = "test@example.com";
        when(userRepository.findIdByEmail(username)).thenReturn(Optional.empty());

        Optional<Long> userId = userAdapter.findIdByUsername(username);

        assertFalse(userId.isPresent());
    }
}