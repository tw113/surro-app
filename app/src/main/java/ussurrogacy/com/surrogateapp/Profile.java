package ussurrogacy.com.surrogateapp;


import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

public class Profile {

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
        setBmi();

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

    private void setBmi()
    {
        //pull height data and parse it
        String height = data.get("WhatIsYourHeight");
        int inches = 0;
        if (height.charAt(0) == 'S' || height.charAt(0) == 'T')
        {
            bmi = 0;
        }
        else
        {
            //convert the height into inches as an integer
            inches = height.charAt(0) * 12;
            if (height.charAt(2) != 1)
            {
                inches += height.charAt(2);
            }
            else
            {
                inches += height.charAt(2) * 10;
                inches += height.charAt(3);
            }

            //get the weight and parse into an integer
            int weight = Integer.parseInt(data.get("WhatIsYourWeightInPounds"));

            //calculate the bmi using the retrieved data
            bmi = (float) (weight / Math.pow(inches, 2));
        }
    }
}
