package com.covisint.idm.services.entities.personv2.passwordv1;

public class Password {
	
	   private String id;
	    private Integer version;

	    private String username;

	    private Long expiration;

		private PasswordPolicy passwordPolicy;

	    private Long unlockInstant;

	    private Boolean locked;

	    private AuthenticationPolicy authenticationPolicy;

	    private String password;



	    public String getId ()
	    {
	        return id;
	    }

	    public void setId (String id)
	    {
	        this.id = id;
	    }

	    public String getUsername ()
	    {
	        return username;
	    }

	    public void setUsername (String username)
	    {
	        this.username = username;
	    }



	    public PasswordPolicy getPasswordPolicy ()
	    {
	        return passwordPolicy;
	    }

	    public void setPasswordPolicy (PasswordPolicy passwordPolicy)
	    {
	        this.passwordPolicy = passwordPolicy;
	    }

	    public Long getUnlockInstant ()
	    {
	        return unlockInstant;
	    }

	    public void setUnlockInstant (Long unlockInstant)
	    {
	        this.unlockInstant = unlockInstant;
	    }



	    public Boolean getLocked() {
			return locked;
		}

		public void setLocked(Boolean locked) {
			this.locked = locked;
		}

		public AuthenticationPolicy getAuthenticationPolicy ()
	    {
	        return authenticationPolicy;
	    }

	    public void setAuthenticationPolicy (AuthenticationPolicy authenticationPolicy)
	    {
	        this.authenticationPolicy = authenticationPolicy;
	    }

	    public String getPassword ()
	    {
	        return password;
	    }

	    public void setPassword (String password)
	    {
	        this.password = password;
	    }

	    public Integer getVersion ()
	    {
	        return version;
	    }

	    public void setVersion (Integer version)
	    {
	        this.version = version;
	    }
	    
	    public Long getExpiration() {
			return expiration;
		}

		public void setExpiration(Long expiration) {
			this.expiration = expiration;
		}

	    @Override
	    public String toString()
	    {
	        return "Password [id = "+id+", username = "+username+", expiration = "+expiration+", passwordPolicy = "+passwordPolicy+", unlockInstant = "+unlockInstant+", locked = "+locked+", authenticationPolicy = "+authenticationPolicy+", password = "+password+", version = "+version+"]";
	    }
}
