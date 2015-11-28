package com.covisint.idm.services.entities.personv2;

public class Name
{
    private String given;

    private String surname;

    private String middle;

    public String getGiven ()
    {
        return given;
    }

    public void setGiven (String given)
    {
        this.given = given;
    }

    public String getSurname ()
    {
        return surname;
    }

    public void setSurname (String surname)
    {
        this.surname = surname;
    }

    public String getMiddle ()
    {
        return middle;
    }

    public void setMiddle (String middle)
    {
        this.middle = middle;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [given = "+given+", surname = "+surname+", middle = "+middle+"]";
    }
}