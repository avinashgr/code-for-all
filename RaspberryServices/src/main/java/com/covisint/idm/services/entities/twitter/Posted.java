package com.covisint.idm.services.entities.twitter;

public class Posted {

	    private String string;

	    private String chars;

	    private String valueType;

	    public String getString ()
	    {
	        return string;
	    }

	    public void setString (String string)
	    {
	        this.string = string;
	    }

	    public String getChars ()
	    {
	        return chars;
	    }

	    public void setChars (String chars)
	    {
	        this.chars = chars;
	    }

	    public String getValueType ()
	    {
	        return valueType;
	    }

	    public void setValueType (String valueType)
	    {
	        this.valueType = valueType;
	    }

	    @Override
	    public String toString()
	    {
	        return "ClassPojo [string = "+string+", chars = "+chars+", valueType = "+valueType+"]";
	    }
}
