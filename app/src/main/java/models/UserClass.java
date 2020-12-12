package models;

public class UserClass {

    private String name, surname, email, turkishId, contact, type, address, birthdate;


    public UserClass(String name, String surname, String email, String turkishId, String address, String contact, String birthdate, String type) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.turkishId = turkishId;
        this.contact = contact;
        this.type = type;
        this.address = address;
        this.birthdate = birthdate;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
}
