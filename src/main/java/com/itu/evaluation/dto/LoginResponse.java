package com.itu.evaluation.dto;

public class LoginResponse {
    private String status;
    private LoginData data;
    private String error;

    // Getters et Setters
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LoginData getData() { return data; }
    public void setData(LoginData data) { this.data = data; }

    public String getError() { return error; }
    public void setError(String error) { this.error = error; }
}



