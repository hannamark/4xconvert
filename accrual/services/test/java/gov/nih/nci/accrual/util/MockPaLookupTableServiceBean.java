/**
 *
 */
package gov.nih.nci.accrual.util;

import gov.nih.nci.pa.domain.AbstractLookUpEntity;
import gov.nih.nci.pa.domain.AnatomicSite;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.FundingMechanism;
import gov.nih.nci.pa.domain.NIHinstitute;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.util.TestSchema;

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
        return null;
    }

    public List<NIHinstitute> getNihInstitutes() throws PAException {
        return null;
    }

    public String getPropertyValue(String name) throws PAException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public List<Country> searchCountry(Country country) throws PAException {
        List<Country> retList = new ArrayList<Country>();
        for (Country c: getCountries()) {
            if (country.getAlpha3().equalsIgnoreCase(c.getAlpha3())) {
                retList.add(c);
            }
        }
        return retList;
    }

    /**
     * {@inheritDoc}
     */
    public List<AnatomicSite> getAnatomicSites() throws PAException {
        List<AnatomicSite> returnVal = new ArrayList<AnatomicSite>();
        returnVal.add(TestSchema.createAnatomicSiteObj("Lung"));
        returnVal.add(TestSchema.createAnatomicSiteObj("Kidney"));
        returnVal.add(TestSchema.createAnatomicSiteObj("Heart"));
        return returnVal;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public <T extends AbstractLookUpEntity> T getLookupEntityByCode(Class<T> clazz, String code) throws PAException {
        if (AnatomicSite.class.getName().equals(clazz.getName())) {
            return (T) TestSchema.createAnatomicSiteObj(code);
        }
        return null;
    }

}
