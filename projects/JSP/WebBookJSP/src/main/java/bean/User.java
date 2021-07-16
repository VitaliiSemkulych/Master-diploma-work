/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import tool.Formatting;

/**
 *
 * @author Admin
 */
// form model in which data will be written after registration
public class User {
    private String userName;
    private String telephoneNumber;
    private String userEmail;
    private String password;
    private byte[] image;


    public User() {

      
    }

    public User(String userEmail, String password) {
        this.userEmail = userEmail;
        this.password = password;

    }
    


    public User(String userName, String telephoneNumber, String userEmail, String password, byte[] image) {
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

    

  

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }




    public String getFormedEmail(){
    
    return Formatting.handleEmailText(userEmail);
    }





}
