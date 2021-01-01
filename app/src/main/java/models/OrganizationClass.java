package models;

import com.google.firebase.firestore.GeoPoint;

public class OrganizationClass {

    private String orgID, city, email, organizationName, contact;
    GeoPoint location;


    public OrganizationClass() {
    }

    public OrganizationClass(String orgID, String city, String email, String organizationName, String contact, GeoPoint location) {
        this.orgID = orgID;
        this.city = city;
        this.email = email;
        this.organizationName = organizationName;
        this.contact = contact;
        this.location = location;
    }

    public String getOrgID() {
        return orgID;
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID;
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
