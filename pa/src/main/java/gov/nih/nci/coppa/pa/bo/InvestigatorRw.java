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
   * A researcher in a clinical trial or clinical study who oversees all aspects of the trial, #NOTES#such
   * as concept development, protocol writing, protocol submission for IRB approval, participant
   * recruitment, informed consent, data collection, analysis, interpretation and presentation.
   *
   */

public  class InvestigatorRw implements java.io.Serializable
{
	private static final long serialVersionUID = 1234567890L;

	public java.lang.String administrativeGenderCode;
	public java.lang.String getAdministrativeGenderCode()
	{
		return administrativeGenderCode;
	}
	public void setAdministrativeGenderCode(java.lang.String administrativeGenderCode)
	{
		this.administrativeGenderCode = administrativeGenderCode;
	}


	public java.util.Date birthDate;
	public java.util.Date getBirthDate()
	{
		return birthDate;
	}
	public void setBirthDate(java.util.Date birthDate)
	{
		this.birthDate = birthDate;
	}


	public java.lang.String city;
	public java.lang.String getCity()
	{
		return city;
	}
	public void setCity(java.lang.String city)
	{
		this.city = city;
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


	public java.lang.String educationLevelCode;
	public java.lang.String getEducationLevelCode()
	{
		return educationLevelCode;
	}
	public void setEducationLevelCode(java.lang.String educationLevelCode)
	{
		this.educationLevelCode = educationLevelCode;
	}


	public java.lang.String employmentStatusCode;
	public java.lang.String getEmploymentStatusCode()
	{
		return employmentStatusCode;
	}
	public void setEmploymentStatusCode(java.lang.String employmentStatusCode)
	{
		this.employmentStatusCode = employmentStatusCode;
	}


	public java.lang.String employmentStatusOtherText;
	public java.lang.String getEmploymentStatusOtherText()
	{
		return employmentStatusOtherText;
	}
	public void setEmploymentStatusOtherText(java.lang.String employmentStatusOtherText)
	{
		this.employmentStatusOtherText = employmentStatusOtherText;
	}


	public java.lang.String ethnicGroupCode;
	public java.lang.String getEthnicGroupCode()
	{
		return ethnicGroupCode;
	}
	public void setEthnicGroupCode(java.lang.String ethnicGroupCode)
	{
		this.ethnicGroupCode = ethnicGroupCode;
	}


	public java.lang.String firstName;
	public java.lang.String getFirstName()
	{
		return firstName;
	}
	public void setFirstName(java.lang.String firstName)
	{
		this.firstName = firstName;
	}


	public java.lang.String householdIncomeCode;
	public java.lang.String getHouseholdIncomeCode()
	{
		return householdIncomeCode;
	}
	public void setHouseholdIncomeCode(java.lang.String householdIncomeCode)
	{
		this.householdIncomeCode = householdIncomeCode;
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


	public java.lang.Long investigatorId;
	public java.lang.Long getInvestigatorId()
	{
		return investigatorId;
	}
	public void setInvestigatorId(java.lang.Long investigatorId)
	{
		this.investigatorId = investigatorId;
	}


	public java.lang.String lastName;
	public java.lang.String getLastName()
	{
		return lastName;
	}
	public void setLastName(java.lang.String lastName)
	{
		this.lastName = lastName;
	}


	public java.lang.String maritalStatusCode;
	public java.lang.String getMaritalStatusCode()
	{
		return maritalStatusCode;
	}
	public void setMaritalStatusCode(java.lang.String maritalStatusCode)
	{
		this.maritalStatusCode = maritalStatusCode;
	}


	public java.lang.String middleName;
	public java.lang.String getMiddleName()
	{
		return middleName;
	}
	public void setMiddleName(java.lang.String middleName)
	{
		this.middleName = middleName;
	}


	public java.lang.Long participantId;
	public java.lang.Long getParticipantId()
	{
		return participantId;
	}
	public void setParticipantId(java.lang.Long participantId)
	{
		this.participantId = participantId;
	}


	public java.lang.String phone;
	public java.lang.String getPhone()
	{
		return phone;
	}
	public void setPhone(java.lang.String phone)
	{
		this.phone = phone;
	}


	public java.lang.String raceCode;
	public java.lang.String getRaceCode()
	{
		return raceCode;
	}
	public void setRaceCode(java.lang.String raceCode)
	{
		this.raceCode = raceCode;
	}


	public java.lang.String state;
	public java.lang.String getState()
	{
		return state;
	}
	public void setState(java.lang.String state)
	{
		this.state = state;
	}


	public java.lang.String streetAddress;
	public java.lang.String getStreetAddress()
	{
		return streetAddress;
	}
	public void setStreetAddress(java.lang.String streetAddress)
	{
		this.streetAddress = streetAddress;
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


	public java.lang.String zipCode;
	public java.lang.String getZipCode()
	{
		return zipCode;
	}
	public void setZipCode(java.lang.String zipCode)
	{
		this.zipCode = zipCode;
	}


	public java.lang.String nciIdentifier;
	public java.lang.String getNciIdentifier()
	{
		return nciIdentifier;
	}
	public void setNciIdentifier(java.lang.String nciIdentifier)
	{
		this.nciIdentifier = nciIdentifier;
	}


	public boolean equals(Object obj)
	{
		boolean eq = false;
		if(obj instanceof Investigator)
		{
			Investigator c =(Investigator)obj;
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