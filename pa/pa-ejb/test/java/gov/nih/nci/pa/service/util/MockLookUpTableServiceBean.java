/**
 *
 */
package gov.nih.nci.pa.service.util;

import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.FundingMechanism;
import gov.nih.nci.pa.domain.NIHinstitute;
import gov.nih.nci.pa.service.PAException;

import java.util.ArrayList;
import java.util.List;


/**
 * @author asharma
 *
 */
public class MockLookUpTableServiceBean implements LookUpTableServiceRemote {


	public List<Country> getCountries() throws PAException {
		 List<Country> countries = new ArrayList<Country>();

		 Country country = new Country();
         country.setAlpha2("ZZ");
         country.setAlpha3("ZZZ");
         country.setName("Zanzibar");
         country.setNumeric("67");
         countries.add(country);

         Country c1 = new Country();
         c1.setAlpha2("CA");
         c1.setAlpha3("CAM");
         c1.setName("Cayman Islands");
         countries.add(c1);
		return countries;
	}

	public List<FundingMechanism> getFundingMechanisms() throws PAException {
		List<FundingMechanism> fmList = new ArrayList<FundingMechanism>();
		FundingMechanism fm = new FundingMechanism();
        fm.setFundingMechanismCode("B09");
        fmList.add(fm);
		return fmList;
	}


	public List<NIHinstitute> getNihInstitutes() throws PAException {
		List<NIHinstitute> nihList = new ArrayList<NIHinstitute>();
		NIHinstitute nih = new NIHinstitute();
        nih.setNihInstituteCode("AA");
        nihList.add(nih);
		return nihList;
	}


	public String getPropertyValue(String name) throws PAException {
	   String value = "";
	   if (name.equals("tsr.subject"))
		 return "NCI Clinical Trials Reporting Program (CTRP) Trial Summary Report and ClinicalTrials.gov Registration File";
	   else if (name.equals("CADSR_CS_ID"))
		   return "2960572";
	   else if (name.equals("CADSR_CS_VERSION"))
		   return "1";
	   else if (name.equals("CDE_REQUEST_TO_EMAIL"))
		   return "asharma@scenpro.com";
	   else if (name.equals("CDE_REQUEST_TO_EMAIL_SUBJECT"))
		   return "New CDE Request";
	   else if (name.equals("CDE_REQUEST_TO_EMAIL_TEXT"))
		   return "Please create the new CDE. Thanks, CTRO";
	   else
		  return value;
	}


}
