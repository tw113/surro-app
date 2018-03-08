package ussurrogacy.com.surrogateapp;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Profile {

    //
    //
    //
    //ADD BOOLEANS FOR THE CHECKMARKS LISTED IN THE UI ELEMENTS NEEDED DOC
    //AND METHODS TO CHANGE THEM IF/WHEN NEEDED
    //
    //
    //

    private LinkedHashMap<String, String> data;
    private int id;
    private String status;
    private float bmi;

    //profile checkmarks
    private boolean selected = false;
    private boolean isContacted = false;
    private boolean hasAppointment = false;
    private boolean reviewed = false;

    //personal checks
    private boolean background = false;
    private boolean medRecords = false;
    private boolean interviewed = false;

    Profile (List<String> questions, List<String> answers, int num){

        data = new LinkedHashMap<>();

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
    public float getBmi() { return bmi;}

    //function that sets the bmi in the constructor only
    private void setBmi()
    {
        //pull height data and parse it
        String height = data.get("WhatIsYourHeight");
        int inches;
        if (height.charAt(0) == 'S' || height.charAt(0) == 'T')
        {
            bmi = 0;
        }
        else
        {
            //convert the height into inches as an integer
            inches = height.charAt(0) * 12;

            //if inches is less than 10
            if (height.charAt(2) != 1)
            {
                inches += height.charAt(3);
            }
            //if inches is 10 or greater
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


    public void setSelected() { selected = !selected; }

    public boolean getSelected() { return selected; }

    public void setIsContacted() { isContacted = !isContacted; }

    public boolean getIsContacted() { return isContacted; }

    public void setHasAppointment() { hasAppointment = !hasAppointment; }

    public boolean getHasAppointment() { return hasAppointment; }

    public void setReviewed () { reviewed = !reviewed; }

    public boolean getReviewed() { return reviewed; }

    public void setBackground() { background = !background; }

    public boolean getBackround() { return background; }

    public void setMedRecords() { medRecords = !medRecords; }

    public boolean getMedRecords() { return medRecords; }

    public void setInterviewed() { interviewed = !interviewed; }

    public boolean getInterviewed() { return interviewed; }
}
