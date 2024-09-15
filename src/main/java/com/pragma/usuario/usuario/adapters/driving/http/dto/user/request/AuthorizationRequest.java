package com.pragma.usuario.usuario.adapters.driving.http.dto.user.request;


public class AuthorizationRequest {
    private String token;

public  AuthorizationRequest(){

}
    public AuthorizationRequest(String token) {
        this.token = token;

    }

    public String getToken() {
        return token;
    }


}
