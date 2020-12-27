package models;

import com.google.firebase.firestore.GeoPoint;

public class OrganizationClass {

    private String city, email, organizationName, contact;
    GeoPoint location;


    public OrganizationClass() {
    }

    public OrganizationClass(String city, String email, String organizationName, String contact, GeoPoint location) {
        this.city = city;
        this.email = email;
        this.organizationName = organizationName;
        this.contact = contact;
        this.location = location;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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


    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

}
