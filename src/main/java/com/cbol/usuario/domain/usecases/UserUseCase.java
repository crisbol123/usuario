package com.cbol.usuario.domain.usecases;

import com.cbol.usuario.domain.api.IUserServicePort;
import com.cbol.usuario.domain.model.User;
import com.cbol.usuario.domain.spi.IUserPersistencePort;

import java.time.LocalDate;


public class UserUseCase implements IUserServicePort {

        private final IUserPersistencePort userPersistencePort;

        public UserUseCase(IUserPersistencePort userPersistencePort) {
            this.userPersistencePort = userPersistencePort;

        }
@Override
        public void createUser(User user) {
            // Validar que el usuario sea mayor de edad
            if (user.getBirthDate().isAfter(LocalDate.now().minusYears(18))) {
                throw new IllegalArgumentException("User must be at least 18 years old");
            }

             userPersistencePort.saveUser(user);
        }

    @Override
    public User getUserByUsername(String username) {
        return userPersistencePort.getUserByUsername(username);
    }


}

