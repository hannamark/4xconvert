/**
 * 
 */
package gov.nih.nci.accrual.util;

import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.FundingMechanism;
import gov.nih.nci.pa.domain.NIHinstitute;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kalpana Guthikonda
 *
 */
public class MockPaLookupTableServiceBean implements LookUpTableServiceRemote {

    public List<Country> getCountries() throws PAException {
        List<Country> countries = new ArrayList<Country>();
        Country c = new Country();
        c.setAlpha2("US");
        c.setAlpha3("USA");
        c.setName("USA");
        countries.add(c);
        return countries;
    }
    
    /**
     * {@inheritDoc}
     */
    public Country getCountryByName(String name) throws PAException {
        Country country = new Country();
        country.setName(name);
        return country;
    }

    public List<FundingMechanism> getFundingMechanisms() throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<NIHinstitute> getNihInstitutes() throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    public String getPropertyValue(String name) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

   

}
