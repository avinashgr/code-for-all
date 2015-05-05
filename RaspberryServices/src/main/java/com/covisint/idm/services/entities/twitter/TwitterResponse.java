package com.covisint.idm.services.entities.twitter;

public class TwitterResponse {
    private Posted posted;

    public Posted getPosted ()
    {
        return posted;
    }

    public void setPosted (Posted posted)
    {
        this.posted = posted;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [posted = "+posted+"]";
    }
}
