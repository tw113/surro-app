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

    public Profile (List<String> questions, List<String> answers, int num){

        //set the values of data using the questions as the key and answers as the values
        for (int i = 0; i < questions.size(); i++)
        {
            data.put((questions.get(i)), answers.get(i));
        }

        //set the id of the profile
        id = num;

        //set the status as created. Will later be changed to
        //approved or denied
        status = "Profile created.";

        //set the bmi
    }

    //function to change status of the profile
    public void changeStatus(String newStatus)
    {
        status = newStatus;
    }

    //function to return the status of the profile
    public String getStatus()
    {
        return status;
    }

    //function to return profile id number
    public int getId()
    {
        return id;
    }

    //function to get the bmi of the profile
    public float getBmi() {return bmi;}

    //public void setBmi() {bmi = (weight * 703)/(height * height);}

}
