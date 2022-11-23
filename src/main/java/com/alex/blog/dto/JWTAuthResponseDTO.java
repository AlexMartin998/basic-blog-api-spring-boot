package com.alex.blog.dto;

public class JWTAuthResponseDTO {

    private String accessToken;
    private String typeOfToken = "Bearer";

    public JWTAuthResponseDTO(String accessToken) {
        this.accessToken = accessToken;
    }
    public JWTAuthResponseDTO(String accessToken, String typeOfToken) {
        this.accessToken = accessToken;
        this.typeOfToken = typeOfToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTypeOfToken() {
        return typeOfToken;
    }

    public void setTypeOfToken(String typeOfToken) {
        this.typeOfToken = typeOfToken;
    }

}
