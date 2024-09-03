package com.cbol.usuario.domain.api;

import com.cbol.usuario.domain.model.User;

public interface IUserServicePort {
    void createUser(User user);
    User getUserByUsername(String username);
}
