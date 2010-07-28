/**
 * 
 */
package gov.nih.nci.registry.service;

import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.FundingMechanism;
import gov.nih.nci.pa.domain.NIHinstitute;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Vrushali
 *
 */
public class MockLookUpTableService implements LookUpTableServiceRemote {
    static Map<String,String> map;
    static {
        map = new HashMap<String, String>();
        map.put("smtp", "mailfwd.nih.gov");
        map.put("fromaddress", "ncictrp@mail.nih.gov");
        map.put("allowed.uploadfile.types", "doc");
        map.put("user.account.subject", "Junit user account subject");
        map.put("user.account.body", "Junit user account body url");
    }

    /**
     * {@inheritDoc}
     */
    public List<Country> getCountries() throws PAException {
        List<Country> countryList = new ArrayList<Country>();
        Country country = new Country();
        country.setAlpha2("US");
        country.setAlpha3("USA");
        countryList.add(country);
        country = new Country();
        country.setAlpha2("CA");
        country.setAlpha3("CAN");
        countryList.add(country);
        country = new Country();
        country.setAlpha2("AS");
        country.setAlpha3("AUS");
        countryList.add(country);
        country = new Country();
        country.setAlpha2("JP");
        country.setAlpha3("JPN");
        countryList.add(country);
        return countryList;
    }

    /**
     * {@inheritDoc}
     */
    public Country getCountryByName(String name) throws PAException {
        Country country = new Country();
        country.setName(name);
        return country;
    }

    /**
     * {@inheritDoc}
     */
    public List<FundingMechanism> getFundingMechanisms() throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public List<NIHinstitute> getNihInstitutes() throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public String getPropertyValue(String name) throws PAException {
        return map.get(name);
    }

	public List<Country> searchCountry(Country country) throws PAException {
		// TODO Auto-generated method stub
		return null;
	}

}
