package models;

public class OrganizationClass {

    private String city, email, manager;
    private Integer province;




    public OrganizationClass(String orgtext, String orgaddresstext, String orgcontact) {
    }

    public OrganizationClass(String city, String email, String manager,Integer province){
        this.city = city;
        this.email = email ;
        this.manager = manager;
        this.province = province;
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

    public void setEmail(String email) { this.email = city; }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) { this.manager = manager; }

    public Integer getProvince() {
        return province;
    }

    public void setProvince(Integer province) {
        this.province = province;
    }

}
