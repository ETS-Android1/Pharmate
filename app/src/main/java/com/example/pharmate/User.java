package com.example.pharmate;

import android.widget.EditText;

public class User {
    private String email;
    private String password;
    private String userType;
    private String name;
    private String userSurname;
    private String userTurkishID;
    private String userContact;
    private String userAddress;
    private String userBirthDate;

    public User(String email, String password, EditText userType, EditText name, EditText userSurname, EditText userTurkishID, EditText userAddress, EditText userContact, EditText userBirthDate){

    }
    public User(String email,String password){
        this.email=email;
        this.password=password;
    }

    public User(String email,String password,String userType,String name,String userSurname,String userTurkishID,String userContact,String userAddress,String userBirthDate){
        this.email=email;
        this.password=password;
        this.userType=userType;
        this.name=name;
        this.userSurname=userSurname;
        this.userTurkishID=userTurkishID;
        this.userContact=userContact;
        this.userAddress=userAddress;
        this.userBirthDate=userBirthDate;

    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUserType() {
        return userType;
    }

    public String getName() {
        return name;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public String getUserTurkishID() {
        return userTurkishID;
    }

    public String getUserContact() {
        return userContact;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public String getUserBirthDate() {
        return userBirthDate;
    }
}
