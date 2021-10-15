package com.sneydr.roomrv2.Entities.Login;


public class Login {
    private String email;
    private String password;

    public Login(String email, String password){
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean validateEmail() {
        return this.email != null && !this.email.isEmpty();
    }

    public boolean validatePassword() {
        return this.password != null && !this.password.isEmpty();
    }


}
