package com.cbol.usuario.configuration;

import com.cbol.usuario.adapters.driven.jpa.mysql.adapter.UserAdapter;
import com.cbol.usuario.adapters.driven.jpa.mysql.entity.UserEntity;
import com.cbol.usuario.adapters.driven.jpa.mysql.mapper.IUserEntityMapper;
import com.cbol.usuario.adapters.driven.jpa.mysql.repository.IUserRepository;
import com.cbol.usuario.adapters.securityconfig.entity.UserDetailsImp;
import com.cbol.usuario.domain.api.IUserServicePort;
import com.cbol.usuario.domain.spi.IUserPersistencePort;
import com.cbol.usuario.domain.usecases.UserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {


    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
                throws Exception {
            return config.getAuthenticationManager();
        }

        @Bean
        public AuthenticationProvider authenticationProvider() {
            DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
            authProvider.setUserDetailsService(userDetailsService());
            authProvider.setPasswordEncoder(encoder());
            return authProvider;
        }

        @Bean
        public UserDetailsService userDetailsService() {

            return username -> {
                Optional<UserEntity> user = userRepository.findByEmail(username);
                if (user.isEmpty()) {
                    throw new UsernameNotFoundException("User not found");
                }
                return new UserDetailsImp(user.get());
            };
        }


        @Bean
        public PasswordEncoder encoder() {
            return new BCryptPasswordEncoder();
        }
    @Bean
    public IUserPersistencePort userPersistencePort() {
        return new UserAdapter(userRepository, userEntityMapper, encoder());
    }
    @Bean
    public IUserServicePort userServicePort() {
        return new UserUseCase(userPersistencePort());
    }
}

