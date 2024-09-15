package com.pragma.usuario.usuario.adapters.driving.http.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthorizationResponse
{
    private boolean isPresent;
    private String email;
    private String role;

}
