package ussurrogacy.com.surrogateapp;

/**
 * Created by tgarn on 2/13/2018.
 */

public class Name {
    private String firstName;

    private String preferredNamed;

    private String lastName;

    public Name() {
        firstName = "";
        preferredNamed = "";
        lastName = "";
    }

    public Name(String firstName, String preferredNamed, String lastName) {
        this.firstName = firstName;
        this.preferredNamed = preferredNamed;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPreferredNamed() {
        return preferredNamed;
    }

    public void setPreferredNamed(String preferredNamed) {
        this.preferredNamed = preferredNamed;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
