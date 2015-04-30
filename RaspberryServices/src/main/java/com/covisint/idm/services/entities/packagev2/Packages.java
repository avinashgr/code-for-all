package com.covisint.idm.services.entities.packagev2;

public class Packages {

	    private String creation;

	    private String status;

	    private ServicePackage servicePackage;

	    private Grantee grantee;

	    private String version;

	    public String getCreation ()
	    {
	        return creation;
	    }

	    public void setCreation (String creation)
	    {
	        this.creation = creation;
	    }

	    public String getStatus ()
	    {
	        return status;
	    }

	    public void setStatus (String status)
	    {
	        this.status = status;
	    }

	    public ServicePackage getServicePackage ()
	    {
	        return servicePackage;
	    }

	    public void setServicePackage (ServicePackage servicePackage)
	    {
	        this.servicePackage = servicePackage;
	    }

	    public Grantee getGrantee ()
	    {
	        return grantee;
	    }

	    public void setGrantee (Grantee grantee)
	    {
	        this.grantee = grantee;
	    }

	    public String getVersion ()
	    {
	        return version;
	    }

	    public void setVersion (String version)
	    {
	        this.version = version;
	    }

	    @Override
	    public String toString()
	    {
	        return "Packages [creation = "+creation+", status = "+status+", servicePackage = "+servicePackage+", grantee = "+grantee+", version = "+version+"]";
	    }

}
