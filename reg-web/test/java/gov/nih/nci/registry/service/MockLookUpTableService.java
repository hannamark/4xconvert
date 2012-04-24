/**
 *
 */
package gov.nih.nci.registry.service;

import gov.nih.nci.pa.domain.AbstractLookUpEntity;
import gov.nih.nci.pa.domain.AnatomicSite;
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
    public static final String FROM_ADDRESS = "ncictrp@mail.nih.gov";
    public static final String LOG_ADDRESS = "ctrplogs@mail.nih.gov";

    static Map<String,String> map;
    static {
        map = new HashMap<String, String>();
        map.put("smtp", "mailfwd.nih.gov");
        map.put("fromaddress", FROM_ADDRESS);
        map.put("allowed.uploadfile.types", "doc");
        map.put("user.account.subject", "Junit user account subject");
        map.put("user.account.body", "Junit user account body url");
        map.put("trial.batchUpload.bodyHeader", "Junit ${SubmitterName}  ${CurrentDate}");
        map.put("trial.batchUpload.body", "Junit ${totalCount}  ${successCount} ${failedCount}");
        map.put("trial.batchUpload.errorMsg", "Junit ${ReleaseNumber} error");
        map.put("current.release.no", "Junit test");
        map.put("log.email.address", LOG_ADDRESS);
    }

    /**
     * {@inheritDoc}
     */
    @Override
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
    @Override
    public Country getCountryByName(String name) throws PAException {
        Country country = new Country();
        country.setName(name);
        return country;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FundingMechanism> getFundingMechanisms() throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<NIHinstitute> getNihInstitutes() throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPropertyValue(String name) throws PAException {
        return map.get(name);
    }

    /**
     * {@inheritDoc}
     */
	@Override
    public List<Country> searchCountry(Country country) throws PAException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
     * {@inheritDoc}
     */
    @Override
    public List<AnatomicSite> getAnatomicSites() throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends AbstractLookUpEntity> T getLookupEntityByCode(Class<T> clazz, String code) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

}
