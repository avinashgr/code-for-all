package com.covisint.idm.services.entities.packagev2;

public class ServicePackage
{
    private String id;

    private String realm;

    private String type;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getRealm ()
    {
        return realm;
    }

    public void setRealm (String realm)
    {
        this.realm = realm;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    @Override
    public String toString()
    {
        return "ServicePackage [id = "+id+", realm = "+realm+", type = "+type+"]";
    }
}
