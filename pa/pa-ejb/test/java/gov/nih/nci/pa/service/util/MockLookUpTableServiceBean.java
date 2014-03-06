/**
 *
 */
package gov.nih.nci.pa.service.util;

import gov.nih.nci.pa.domain.AbstractLookUpEntity;
import gov.nih.nci.pa.domain.AnatomicSite;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.FundingMechanism;
import gov.nih.nci.pa.domain.NIHinstitute;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.TestSchema;

import java.util.ArrayList;
import java.util.List;

/**
 * @author asharma
 * 
 */
public class MockLookUpTableServiceBean implements LookUpTableServiceRemote {

    public static String CTGOV_SYNC_IMPORT_PERSONS = "true";
    public static String CTGOV_SYNC_IMPORT_ORGS = "true";
    public static String CTGOV_SYNC_FIELDS_OF_INTEREST = "studyProtocol.scientificDescription;eligibilityCriteria";
    public static String CTGOV_SYNC_FIELDS_OF_INTEREST_LABEL_MAPPING = "studyProtocol.scientificDescription=Detailed Description\r\neligibilityCriteria=Eligibility Criteria";
    public static String CTGOV_SYNC_FIELDS_OF_INTEREST_SECTION_MAPPING = "studyProtocol.scientificDescription=Scientific\r\neligibilityCriteria=Admin";

    /**
     * {@inheritDoc}
     */
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
        List<FundingMechanism> fmList = new ArrayList<FundingMechanism>();
        FundingMechanism fm = new FundingMechanism();
        fm.setFundingMechanismCode("B09");
        fmList.add(fm);
        return fmList;
    }

    /**
     * {@inheritDoc}
     */
    public List<NIHinstitute> getNihInstitutes() throws PAException {
        List<NIHinstitute> nihList = new ArrayList<NIHinstitute>();
        NIHinstitute nih = new NIHinstitute();
        nih.setNihInstituteCode("AA");
        nihList.add(nih);
        return nihList;
    }

    /**
     * {@inheritDoc}
     */
    public String getPropertyValue(String name) throws PAException {
        String value = "";
        if (name.equals("tsr.subject")) {
            return "NCI Clinical Trials Reporting Program (CTRP) Trial Summary Report and ClinicalTrials.gov Registration File";
        } else if (name.equals("CADSR_CS_ID")) {
            return "2960572";
        } else if (name.equals("CADSR_CS_VERSION")) {
            return "1";
        } else if (name.equals("CDE_REQUEST_TO_EMAIL")) {
            return "asharma@scenpro.com";
        } else if (name.equals("CDE_REQUEST_TO_EMAIL_SUBJECT")) {
            return "New CDE Request";
        } else if (name.equals("CDE_REQUEST_TO_EMAIL_TEXT")) {
            return "Please create the new CDE. Thanks, CTRO";
        } else if (name.equals("allowed.uploadfile.types")) {
            return "doc,pdf,xls,wpd,docx,docm,xlsx,xlsm,xlsb";
        } else if (name.equals("fromaddress")) {
            return "ncictro@mail.nih.gov";
        } else if (name.equals("GrantsRequiredRegServiceEffectiveDate")) {
            return "31-DEC-2100";
        } else if (name.equals("ctgov.api.getByNct")) {
            return "http://localhost:"
                    + CTGovSyncServiceBeanTest.CTGOV_API_MOCK_PORT + "/ctgov?${nctid}";
        } else if (name.equals("ctgov.sync.import_persons")) {
            return CTGOV_SYNC_IMPORT_PERSONS;
        } else if (name.equals("ctgov.sync.import_orgs")) {
            return CTGOV_SYNC_IMPORT_ORGS;
        } else if (name.equals("ctgov.sync.fields_of_interest")) {
            return CTGOV_SYNC_FIELDS_OF_INTEREST;
        } else if (name.equals("ctgov.sync.fields_of_interest.key_to_label_mapping")) {
            return CTGOV_SYNC_FIELDS_OF_INTEREST_LABEL_MAPPING;
        } else if (name.equals("ctgov.sync.fields_of_interest.key_to_sect_mapping")) {
            return CTGOV_SYNC_FIELDS_OF_INTEREST_SECTION_MAPPING;
        } else {
            return value;
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<Country> searchCountry(Country country) throws PAException {
        List<Country> retList = new ArrayList<Country>();
        for (Country c : getCountries()) {
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
    public <T extends AbstractLookUpEntity> T getLookupEntityByCode(
            Class<T> clazz, String code) throws PAException {
        if (AnatomicSite.class.getName().equals(clazz.getName())) {
            return (T) TestSchema.createAnatomicSiteObj(code);
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public List<String> getStudyAlternateTitleTypes() throws PAException {
        List<String> studyAlternateTitleTypes = new ArrayList<String>();
        studyAlternateTitleTypes.add("Test");
        return studyAlternateTitleTypes;
    }
}
