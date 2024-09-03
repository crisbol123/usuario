package com.cbol.usuario.adapters.driven.jpa.mysql.adapter;


import com.cbol.usuario.adapters.driven.jpa.mysql.entity.UserEntity;
import com.cbol.usuario.adapters.driven.jpa.mysql.mapper.IUserEntityMapper;
import com.cbol.usuario.adapters.driven.jpa.mysql.repository.IUserRepository;
import com.cbol.usuario.domain.model.User;
import com.cbol.usuario.domain.spi.IUserPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserAdapter implements IUserPersistencePort {

    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void saveUser(User user) {

        UserEntity userEntity = userEntityMapper.toEntity(user);
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(userEntity);
    }

    @Override
    public User getUserByUsername(String username) {

        UserEntity userEntity = userRepository.findByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return userEntityMapper.toDomain(userEntity);
    }

}
