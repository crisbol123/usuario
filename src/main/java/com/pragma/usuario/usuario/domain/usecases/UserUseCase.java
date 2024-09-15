package com.pragma.usuario.usuario.domain.usecases;


import com.pragma.usuario.usuario.adapters.driving.http.dto.user.response.AuthorizationResponse;
import com.pragma.usuario.usuario.adapters.driving.http.mapper.login.response.AuthResponse;
import com.pragma.usuario.usuario.domain.api.IUserServicePort;
import com.pragma.usuario.usuario.domain.exception.ValidationException;
import com.pragma.usuario.usuario.domain.model.Login;
import com.pragma.usuario.usuario.domain.model.RoleEnum;
import com.pragma.usuario.usuario.domain.model.User;
import com.pragma.usuario.usuario.domain.spi.IUserPersistencePort;
import com.pragma.usuario.usuario.domain.spi.JwtServicePort;
import com.pragma.usuario.usuario.domain.util.DomainConstants;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class UserUseCase implements IUserServicePort {

    private final IUserPersistencePort userPersistencePort;
    private final JwtServicePort jwtServicePort;



    private void validateUser(User user) {
        List<String> errors = new ArrayList<>();

        if (user.getBirthDate().isAfter(LocalDate.now().minusYears(18))) {
            errors.add(DomainConstants.USER_MUST_BE_AT_LEAST_18_YEARS_OLD);
        }

        if (user.getPhoneNumber() != null && !user.getPhoneNumber().matches(DomainConstants.PHONE_NUMBER_REGEX)) {
            errors.add(DomainConstants.PHONE_NUMBER_IS_INVALID);
        }

        if (user.getEmail() != null && !user.getEmail().matches(DomainConstants.EMAIL_REGEX)) {
            errors.add(DomainConstants.EMAIL_IS_INVALID);
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }

    @Override
    public void createCustomer(User user) {
        validateUser(user);
        user.setRoleName(RoleEnum.ROLE_CUSTOMER.toString());
        userPersistencePort.saveUser(user);
    }

    @Override
    public void createWareHouseAssistant(User user) {
        validateUser(user);
        user.setRoleName(RoleEnum.ROLE_WAREHOUSE_ASSISTANT.toString());
        userPersistencePort.saveUser(user);
    }

    @Override
    public void createAdmin(User user) {
        validateUser(user);
        user.setRoleName(RoleEnum.ROLE_ADMIN.toString());
        userPersistencePort.saveUser(user);
    }
    @Override
    public AuthorizationResponse validateToken(String token) {
        String email = jwtServicePort.getUsernameFromToken(token);
        Optional<Long> id = findIdByUsername(email);
        String role = jwtServicePort.getAuthoritiesFromToken(token);
        boolean isPresent = id.isPresent();

        return new AuthorizationResponse(isPresent, email, role);
    }

    @Override
    public AuthResponse login(Login login) {

    return userPersistencePort.login(login);

    }



    @Override
    public Optional<Long> findIdByUsername(String username) {
        return userPersistencePort.findIdByUsername(username);
    }
}