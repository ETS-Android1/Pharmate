package models;

import com.google.firebase.firestore.GeoPoint;

public class UserClass {

    private String name, surname, email, turkishId, contact, address, birthdate, photoURL;
    GeoPoint location;


    public UserClass() {
    }


    public UserClass(String name, String surname, String email, String turkishId, String contact, String address, String birthdate, String photoURL, GeoPoint location) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.turkishId = turkishId;
        this.contact = contact;
        this.address = address;
        this.birthdate = birthdate;
        this.photoURL = photoURL;
        this.location = location;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    public String getTurkishId() {
        return turkishId;
    }

    public void setTurkishId(String turkishId) {
        this.turkishId = turkishId;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

}
