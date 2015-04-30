package com.covisint.idm.services.entities.personv2;

import java.util.Arrays;
import java.util.List;

import com.covisint.idm.services.entities.packagev2.Packages;

public class Person {
	  private String creation;

	    private String status;

	    private String version;

	    private Phones[] phones;

	    private String id;

	    private Organization organization;

	    private String timezone;

	    private String title;

	    private String realm;

	    private String email;

	    private Name name;

	    private String language;

	    private Addresses[] addresses;
	    
	    private List<Packages> packages;

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

	    public String getVersion ()
	    {
	        return version;
	    }

	    public void setVersion (String version)
	    {
	        this.version = version;
	    }

	    public Phones[] getPhones ()
	    {
	        return phones;
	    }

	    public void setPhones (Phones[] phones)
	    {
	        this.phones = phones;
	    }

	    public String getId ()
	    {
	        return id;
	    }

	    public void setId (String id)
	    {
	        this.id = id;
	    }

	    public Organization getOrganization ()
	    {
	        return organization;
	    }

	    public void setOrganization (Organization organization)
	    {
	        this.organization = organization;
	    }

	    public String getTimezone ()
	    {
	        return timezone;
	    }

	    public void setTimezone (String timezone)
	    {
	        this.timezone = timezone;
	    }

	    public String getTitle ()
	    {
	        return title;
	    }

	    public void setTitle (String title)
	    {
	        this.title = title;
	    }

	    public String getRealm ()
	    {
	        return realm;
	    }

	    public void setRealm (String realm)
	    {
	        this.realm = realm;
	    }

	    public String getEmail ()
	    {
	        return email;
	    }

	    public void setEmail (String email)
	    {
	        this.email = email;
	    }

	    public Name getName ()
	    {
	        return name;
	    }

	    public void setName (Name name)
	    {
	        this.name = name;
	    }

	    public String getLanguage ()
	    {
	        return language;
	    }

	    public void setLanguage (String language)
	    {
	        this.language = language;
	    }

	    public Addresses[] getAddresses ()
	    {
	        return addresses;
	    }

	    public void setAddresses (Addresses[] addresses)
	    {
	        this.addresses = addresses;
	    }

		public List<Packages> getPackages() {
			return packages;
		}

		public void setPackages(List<Packages> packages) {
			this.packages = packages;
		}

		@Override
		public String toString() {
			return "Person [creation=" + creation + ", status=" + status
					+ ", version=" + version + ", phones="
					+ Arrays.toString(phones) + ", id=" + id
					+ ", organization=" + organization + ", timezone="
					+ timezone + ", title=" + title + ", realm=" + realm
					+ ", email=" + email + ", name=" + name + ", language="
					+ language + ", addresses=" + Arrays.toString(addresses)
					+ ", packages=" + packages + "]";
		}
		
		

}
