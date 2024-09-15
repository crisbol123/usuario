package com.pragma.usuario.usuario.domain.spi;


import com.pragma.usuario.usuario.adapters.driving.http.mapper.login.response.AuthResponse;
import com.pragma.usuario.usuario.domain.model.Login;
import com.pragma.usuario.usuario.domain.model.User;

import java.util.Optional;

public interface IUserPersistencePort {
    void saveUser(User user);
    Optional<Long> findIdByUsername(String username);
    AuthResponse login(Login login);

}
