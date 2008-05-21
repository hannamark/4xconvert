package gov.nih.nci.ctom.domain;

import gov.nih.nci.ctom.domain.*;
import gov.nih.nci.system.applicationservice.*;
import gov.nih.nci.common.util.HQLCriteria;
import java.util.*;
import org.apache.log4j.Logger;

/**
 * <!-- LICENSE_TEXT_START -->
 * <!-- LICENSE_TEXT_END -->
 */

  /**
   * A facility or organization that provides healthcare, which may be associated with the execution
   * of clinical trial(s).
   *
   */

public  class HealthcareSiteRw 	implements java.io.Serializable
{
	private static final long serialVersionUID = 1234567890L;

	public java.lang.String city;
	public java.lang.String getCity()
	{
		return city;
	}
	public void setCity(java.lang.String city)
	{
		this.city = city;
	}


	public java.lang.Long id;
	public java.lang.Long getId()
	{
		return id;
	}
	public void setId(java.lang.Long id)
	{
		this.id = id;
	}


	public java.lang.String countryCode;
	public java.lang.String getCountryCode()
	{
		return countryCode;
	}
	public void setCountryCode(java.lang.String countryCode)
	{
		this.countryCode = countryCode;
	}


	public java.lang.String descriptionText;
	public java.lang.String getDescriptionText()
	{
		return descriptionText;
	}
	public void setDescriptionText(java.lang.String descriptionText)
	{
		this.descriptionText = descriptionText;
	}


	public java.lang.String name;
	public java.lang.String getName()
	{
		return name;
	}
	public void setName(java.lang.String name)
	{
		this.name = name;
	}


	public java.lang.String postalCode;
	public java.lang.String getPostalCode()
	{
		return postalCode;
	}
	public void setPostalCode(java.lang.String postalCode)
	{
		this.postalCode = postalCode;
	}


	public java.lang.String stateCode;
	public java.lang.String getStateCode()
	{
		return stateCode;
	}
	public void setStateCode(java.lang.String stateCode)
	{
		this.stateCode = stateCode;
	}


	public java.lang.String statusCode;
	public java.lang.String getStatusCode()
	{
		return statusCode;
	}
	public void setStatusCode(java.lang.String statusCode)
	{
		this.statusCode = statusCode;
	}


	public java.util.Date statusDate;
	public java.util.Date getStatusDate()
	{
		return statusDate;
	}
	public void setStatusDate(java.util.Date statusDate)
	{
		this.statusDate = statusDate;
	}


	public java.lang.String telecomAddress;
	public java.lang.String getTelecomAddress()
	{
		return telecomAddress;
	}
	public void setTelecomAddress(java.lang.String telecomAddress)
	{
		this.telecomAddress = telecomAddress;
	}


	public java.lang.String nciInstituteCode;
	public java.lang.String getNciInstituteCode()
	{
		return nciInstituteCode;
	}
	public void setNciInstituteCode(java.lang.String nciInstituteCode)
	{
		this.nciInstituteCode = nciInstituteCode;
	}



	public boolean equals(Object obj)
	{
		boolean eq = false;
		if(obj instanceof HealthcareSite)
		{
			HealthcareSite c =(HealthcareSite)obj;
			Long thisId = getId();

			if(thisId != null && thisId.equals(c.getId()))
				eq = true;

			}
			return eq;
		}

	public int hashCode()
	{
		int h = 0;

		if(getId() != null)
			h += getId().hashCode();

		return h;
	}
}