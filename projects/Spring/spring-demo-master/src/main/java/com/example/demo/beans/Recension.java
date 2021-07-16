package com.example.demo.beans;

import com.example.demo.dao.RecensionDAO;
import com.example.demo.tool.Formatting;
import org.springframework.beans.factory.annotation.Autowired;

//class which contain model for book recension
public class Recension {

    private long recensionId;
    private String userEmail;
    private long bookId;
    private String recensionText;

    private String userName;

    public Recension() {

    }

    public Recension(String userName,long recensionId, String userEmail, long bookId, String recensionText) {
        this.recensionId = recensionId;
        this.userEmail = userEmail;
        this.bookId = bookId;
        this.recensionText = recensionText;
        this.userName=userName;
    }

    public long getRecensionId() {
        return recensionId;
    }



    public String getFormedUserName(){
        return Formatting.handleEmailText(userName);
    }
    public String getUserEmail() {
        return Formatting.handleEmailText(userEmail);
    }
    public String getUnformedUserEmail() {
        return userEmail;
    }

    public long getBookId() {
        return bookId;
    }

    public String getRecensionText() {
        return recensionText;
    }

    public void setRecensionId(long recensionId) {
        this.recensionId = recensionId;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public void setRecensionText(String recensionText) {
        this.recensionText = recensionText;
    }


}
