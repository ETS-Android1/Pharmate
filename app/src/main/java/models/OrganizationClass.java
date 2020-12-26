package models;

import com.google.firebase.firestore.GeoPoint;

public class OrganizationClass {

    private String city, email, manager, province;
    GeoPoint location;


    public OrganizationClass() {
    }


    public OrganizationClass(String city, String email, String manager, String province, GeoPoint location) {
        this.city = city;
        this.email = email;
        this.manager = manager;
        this.province = province;
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

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    public void setEmail(String email) {
        this.email = city;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

}
