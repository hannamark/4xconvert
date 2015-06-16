/**
 *
 */
package gov.nih.nci.service.util;

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
 * @author asharma
 * 
 */
public class MockLookUpTableServiceBean implements LookUpTableServiceRemote {

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
        else if (name.equals("CADSR_LABTEST_ID"))
            return "2003746";
        else if (name.equals("CADSR_LABTEST_UoM_ID"))
            return "2787947";
        else if (name.equals("accrual.outofscope.actions"))
            return "Rejected;Out of Scope";
        else if (name.equals("dashboard.workload.milestones"))
            return "dashboard.workload.milestones','Submission Received Date,Submission Acceptance Date,Submission Reactivated Date,Administrative Processing Start Date,Administrative Processing Completed Date,Ready for Administrative QC Date,Administrative QC Start Date,Administrative QC Completed Date,Scientific Processing Start Date,Scientific Processing Completed Date,Ready for Scientific QC Date,Scientific QC Start Date,Scientific QC Completed Date,Ready for Trial Summary Report Date";
        else if (name.equals("dashboard.counts.milestones"))
            return "Submission Received Date,Submission Acceptance Date,Administrative Processing Start Date,Ready for Administrative QC Date,Administrative QC Start Date,Scientific Processing Start Date,Ready for Scientific QC Date,Scientific QC Start Date,Ready for Trial Summary Report Date";
        else
            return value;
    }

    /**
     * {@inheritDoc}
     */
    public String getPropertyValueFromCache(String name) throws PAException {
        return getPropertyValue(name);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.nih.nci.pa.service.util.LookUpTableServiceRemote#searchCountry(gov
     * .nih.nci.pa.domain.Country)
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
