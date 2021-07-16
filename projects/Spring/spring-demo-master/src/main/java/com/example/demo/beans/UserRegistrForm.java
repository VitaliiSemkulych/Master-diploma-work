package com.example.demo.beans;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

// form model in which data will be written after registration
public class UserRegistrForm {

    private String userName;
    private String telephoneNumber;
    private String userEmail;
    private String password;
    private MultipartFile image;


    public UserRegistrForm() {


    }


    public UserRegistrForm(String userName, String telephoneNumber, String userEmail, String password, MultipartFile image) {
        this.userName = userName;
        this.telephoneNumber = telephoneNumber;
        this.userEmail = userEmail;
        this.password = password;
        this.image = image;
    }



    public String getUserName() {
        return userName;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
    public String getBase64ImagetoString() throws IOException { return Base64.encodeBase64String(image.getBytes());}
}

