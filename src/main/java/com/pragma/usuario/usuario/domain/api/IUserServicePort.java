package com.pragma.usuario.usuario.domain.api;



import com.pragma.usuario.usuario.adapters.driving.http.dto.user.response.AuthorizationResponse;
import com.pragma.usuario.usuario.adapters.driving.http.mapper.login.response.AuthResponse;
import com.pragma.usuario.usuario.domain.model.Login;
import com.pragma.usuario.usuario.domain.model.User;

import java.util.Optional;

public interface IUserServicePort {
    void createWareHouseAssistant(User user);
    AuthorizationResponse validateToken(String token);
    void createCustomer(User user);
    void createAdmin(User user);
    Optional<Long> findIdByUsername(String username);
    AuthResponse login(Login login);

}
