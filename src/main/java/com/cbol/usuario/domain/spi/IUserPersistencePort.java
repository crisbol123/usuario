package com.cbol.usuario.domain.spi;

import com.cbol.usuario.domain.model.User;

public interface IUserPersistencePort {
    void saveUser(User user);
    User getUserByUsername(String username);
}
