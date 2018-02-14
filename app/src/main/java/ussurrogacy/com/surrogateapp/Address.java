package ussurrogacy.com.surrogateapp;

/**
 * Created by tgarn on 2/13/2018.
 */

public class Address {

    private String street;

    private String city;

    private String state;

    private String zip;

    public Address() {
        street = "";
        city = "";
        state = "";
        zip = "";
    }

    public Address (String street, String city, String state, String zip) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    public String getCity() { return city; }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() { return state; }

    public void setState(String state) {
        this.state = state;
    }

    public String getStreet() { return street; }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZip() { return zip; }
}
