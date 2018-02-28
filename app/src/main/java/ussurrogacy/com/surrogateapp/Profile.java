package ussurrogacy.com.surrogateapp;


import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

public class Profile {
    //use treemap or hash to hold all data
    // have one or two separate variables to search the profile by

    //hash or whatever
    //int/double id
    //string approved or denied
    //float bmi
    private LinkedHashMap<String, String> data;
    private int id;
    private String status;
    private float bmi;

    public Profile (List<String> keys, List<String> values, int num){

    };

    /*
    public float getBmi() {return bmi;}

    public void setBmi() {bmi = (weight * 703)/(height * height);}
    */
}
