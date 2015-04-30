package com.covisint.idm.services.entities.personv2;

public class Addresses {

	    private String postal;

	    private String state;

	    private String[] streets;

	    private String country;

	    private String city;

	    public String getPostal ()
	    {
	        return postal;
	    }

	    public void setPostal (String postal)
	    {
	        this.postal = postal;
	    }

	    public String getState ()
	    {
	        return state;
	    }

	    public void setState (String state)
	    {
	        this.state = state;
	    }

	    public String[] getStreets ()
	    {
	        return streets;
	    }

	    public void setStreets (String[] streets)
	    {
	        this.streets = streets;
	    }

	    public String getCountry ()
	    {
	        return country;
	    }

	    public void setCountry (String country)
	    {
	        this.country = country;
	    }

	    public String getCity ()
	    {
	        return city;
	    }

	    public void setCity (String city)
	    {
	        this.city = city;
	    }

	    @Override
	    public String toString()
	    {
	        return "ClassPojo [postal = "+postal+", state = "+state+", streets = "+streets+", country = "+country+", city = "+city+"]";
	    }

}
