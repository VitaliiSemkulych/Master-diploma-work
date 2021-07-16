package com.example.demo.beans;

//class which contain model for user log in functionality
public class Login {

    private String userEmail;
    private String password;

    public Login() {
    }

    public Login(String userEmail,String password) {
        this.userEmail=userEmail;
        this.password = password;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Login{" + "userEmail=" + userEmail + ", password=" + password + '}';
    }




}
