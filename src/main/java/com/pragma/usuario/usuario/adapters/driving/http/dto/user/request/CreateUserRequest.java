package com.pragma.usuario.usuario.adapters.driving.http.dto.user.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
 @Getter
 @Setter
public class CreateUserRequest {
        private String firstName;
        private String lastName;
        private String identityDocument;
        private String phoneNumber;
        private LocalDate birthDate;
        private String email;
        private String password;
}
