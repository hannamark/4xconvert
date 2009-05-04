/**
 * 
 */
package gov.nih.nci.registry.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.FundingMechanism;
import gov.nih.nci.pa.domain.NIHinstitute;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;

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
    }
    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.util.LookUpTableServiceRemote#getCountries()
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
        country.setAlpha3("ASU");
        countryList.add(country);
        return countryList;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.util.LookUpTableServiceRemote#getFundingMechanisms()
     */
    public List<FundingMechanism> getFundingMechanisms() throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.util.LookUpTableServiceRemote#getNihInstitutes()
     */
    public List<NIHinstitute> getNihInstitutes() throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.util.LookUpTableServiceRemote#getPropertyValue(java.lang.String)
     */
    public String getPropertyValue(String name) throws PAException {
        return map.get(name);
    }

}
