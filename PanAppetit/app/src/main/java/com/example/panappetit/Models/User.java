package com.example.panappetit.Models;

public class User {
    private String email;
    private String password;
    private String confPassword;

    public boolean validateFieldsUser() {
        return email != null && !email.isEmpty() && password != null && !password.isEmpty();
    }

    public boolean validatePassEqualConfirPass() {
        return password.equals(confPassword);
    }

    // Getters and setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfPassword() {
        return confPassword;
    }

    public void setConfPassword(String confPassword) {
        this.confPassword = confPassword;
    }
}
