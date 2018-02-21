package ussurrogacy.com.surrogateapp;



public class Profile {
    private Name name;
    private Address address;
    private String profileName;
    private double height;
    private double weight;
    private double bmi;
    private boolean hasCar;
    private boolean movePlans;
    private boolean hasInsurance;
    private boolean coversSurrogacy;
    private double compensation;


    public Profile() {
        name = new Name();
        address = new Address();
        profileName = "";
        height = 0;
        weight = 0;
        bmi = 0;
        hasCar = false;
        movePlans = false;
        hasInsurance = false;
        coversSurrogacy = false;
        compensation = 0;
    }

    public Profile(Name name, Address address, double height, double weight, boolean hasCar, boolean movePlans,
                   boolean hasInsurance, boolean coversSurrogacy, double compensation){
        this.name = name;
        this.address = address;
        this.height = height;
        this.weight = weight;
        this.hasCar = hasCar;
        this.movePlans = movePlans;
        this.hasInsurance = hasInsurance;
        this.coversSurrogacy = coversSurrogacy;
        this.compensation = compensation;
    }

    public String getProfileName() {return profileName;}

    public void setProfileName() {
        if (name.getPreferredNamed() != null){
            profileName = name.getPreferredNamed() + " " + name.getLastName();
        }
        else {
            profileName = name.getFullName();
        }
    }

    public double getHeight(){return height;}

    public void setHeight(double height) {this.height = height;}

    public double getWeight() {return weight;}

    public void setWeight(double weight) {this.weight = weight;}

    public double getBmi() {return bmi;}

    public void setBmi() {bmi = (weight * 703)/(height * height);}

    public boolean getHasCar() {return hasCar;}

    public void setHasCar(boolean hasCar){this.hasCar = hasCar;}

    public boolean getMovePlans() {return movePlans;}

    public void setMovePlans(boolean movePlans) {this.movePlans = movePlans;}

    public boolean getHasInsurance() {return hasInsurance;}

    public void setHasInsurance(boolean hasInsurance) {this.hasInsurance = hasInsurance;}

    public boolean getCoversSurrogacy() {return coversSurrogacy;}

    public void setCoversSurrogacy(boolean coversSurrogacy) {this.coversSurrogacy = coversSurrogacy;}

    public double getCompensation(){return compensation;}

    public void setCompensation(double compensation) {this.compensation = compensation;}
}
