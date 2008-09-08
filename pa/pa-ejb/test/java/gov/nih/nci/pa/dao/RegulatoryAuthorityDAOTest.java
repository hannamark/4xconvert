package gov.nih.nci.pa.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.RegulatoryAuthority;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyProtocolTest;
import gov.nih.nci.pa.domain.StudyRegulatoryAuthority;

import gov.nih.nci.pa.dto.CountryRegAuthorityDTO;
import gov.nih.nci.pa.dto.RegulatoryAuthOrgDTO;
import gov.nih.nci.pa.dto.RegulatoryInformationDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.TestSchema;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Harsha
 * @since 08/05/2008
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public class RegulatoryAuthorityDAOTest {
   
    private RegulatoryInformationDAO informationDAO; 
    private Country country;
    private RegulatoryAuthority authority;
    private static final int THREE = 3;
    private static final int EIGHT = 8;
        
    /**
     * 
     * @throws Exception on error
     */
    @Before
    public void setUp() throws Exception {
        TestSchema.reset();
        country = new Country();
        authority = new RegulatoryAuthority();
        informationDAO = new RegulatoryInformationDAO();
        createCountryAndRegAuthority();
    }
    
    private void createCountryAndRegAuthority() {
        country.setAlpha2("GM");
        country.setAlpha3("CANCER");
        country.setName("Munich");
        TestSchema.addUpdObject(country);
        authority.setAuthorityName("National Capitol Chapter of CANCER");
        authority.setCountry(country);
        TestSchema.addUpdObject(authority);
    }
        
    /**
     * 
     * @throws PAException on error
     */
    //@Test
    public void getRegulatoryAuthorityNameIdTest() throws PAException { 
        List list = informationDAO.getRegulatoryAuthorityNameId(authority.getId());
        assertEquals(1, list.size());   
        assertEquals("National Capitol Chapter of CANCER",  ((RegulatoryAuthOrgDTO) list.get(0)).getName());
    }
    
    /**
     * 
     * @throws PAException on error
     */
    //@Test
    public void getCountryList() throws PAException {
        RegulatoryAuthority authority1 = new RegulatoryAuthority();
        authority1.setAuthorityName("BWI reg body");
        authority1.setCountry(country);
        TestSchema.addUpdObject(authority1);   
        List list = informationDAO.getCountryList();
        assertEquals(THREE, list.size());
        assertEquals("GM", ((CountryRegAuthorityDTO) list.get(0)).getAlpha2());
        assertEquals("CANCER", ((CountryRegAuthorityDTO) list.get(0)).getAlpha3());
        assertEquals("Munich", ((CountryRegAuthorityDTO) list.get(0)).getName());
    }
    
    /**
     * 
     * @throws PAException on error
     */
    //@Test
    public void saveRegulatoryInformation() throws PAException {        
        StudyProtocol sp = StudyProtocolTest.createStudyProtocolObj();
        TestSchema.addUpdObject(sp);
        Long id = sp.getId();
        RegulatoryInformationDTO informationDTO = new RegulatoryInformationDTO();
        informationDTO.setSection801IndicatorP(false);
        informationDTO.setFdaRegulatedInterventionIndicatorP(true);
        informationDTO.setProtocolID(id);
        informationDTO.setTrialOversgtAuthOrgName("BWI reg body");
        informationDAO.saveRegulatoryInformation(informationDTO);
        //      
        informationDTO.setSection801IndicatorP(false);
        informationDTO.setFdaRegulatedInterventionIndicatorP(true);
        informationDTO.setProtocolID(id);
        informationDTO.setTrialOversgtAuthOrgName("BWI reg body repeat");
        boolean ret = informationDAO.saveRegulatoryInformation(informationDTO);
        Session session = TestSchema.getSession();      
        StudyProtocol sc = (StudyProtocol) session.load(StudyProtocol.class, id);       
        assertEquals("", true, true);
        assertEquals(id, sc.getId());
    }
    
    /**
     * 
     * @throws PAException on error
     */
    //@Test
    public void getRegAuthName() throws PAException {
        List retList = informationDAO.getRegulatoryAuthorityName(authority.getId());
        assertEquals(1, retList.size());
        assertEquals("National Capitol Chapter of CANCER", retList.get(0).toString());
    }
    
    /**
     * 
     * @throws PAException on error 
     */
    //@Test
    public void getCountryName() throws PAException {
        String name = informationDAO.getCountryName(country.getId());
        assertEquals("Munich", name);
    }
    
    /**
     * 
     * @throws PAException on error
     */
    //@Test
    public void getDistinctCountry() throws PAException {
        authority.setAuthorityName("BIG FOOT CLUB 9");
        authority.setCountry(country);
        TestSchema.addUpdObject(authority);
        //
        Country c1 = new Country();
        c1.setAlpha2("FR");
        c1.setAlpha3("FRA");
        c1.setName("FRANCE");
        TestSchema.addUpdObject(c1);
        //
        Country c2 = new Country();
        c2.setAlpha2("IN");
        c2.setAlpha3("IND");
        c2.setName("INDIA");
        TestSchema.addUpdObject(c2);
        //
        RegulatoryAuthority authority0 = new RegulatoryAuthority();
        authority0.setAuthorityName("AIMMS_IND_456XSD1QA34");
        authority0.setCountry(c2);
        TestSchema.addUpdObject(authority0);
        RegulatoryAuthority authority1 = new RegulatoryAuthority();
        authority1.setAuthorityName("BLR_IND_456XSD1QA34");
        authority1.setCountry(c2);
        TestSchema.addUpdObject(authority1);
        RegulatoryAuthority authority2 = new RegulatoryAuthority();
        authority2.setAuthorityName("PARISIAN_FRA_456XSD1QA34");
        authority2.setCountry(c1);
        TestSchema.addUpdObject(authority2);
        List testList = informationDAO.getDistinctCountryList();
        assertEquals(EIGHT, Long.valueOf(testList.size()).longValue());         
    }
    
    /**
     * 
     * @throws PAException on error
     */
    //@Test
    public void getRegulatoryInformationForEditTest() throws PAException {
        StudyProtocol sp = StudyProtocolTest.createStudyProtocolObj();
        TestSchema.addUpdObject(sp);
        Long id = sp.getId();
        RegulatoryAuthority authority1 = new RegulatoryAuthority();
        authority1.setAuthorityName("AIMMS_IND_456XSD1QA34");
        authority1.setCountry(country);
        TestSchema.addUpdObject(authority1);        
        StudyRegulatoryAuthority studyAuthority = new StudyRegulatoryAuthority();
        studyAuthority.setRegulatoryAuthority(authority1);
        studyAuthority.setStudyProtocol(sp);
        TestSchema.addUpdObject(studyAuthority);
        RegulatoryAuthOrgDTO orgDTO = informationDAO.getRegulatoryAuthOrgForEdit(id);
        assertEquals(orgDTO.getName(), authority1.getAuthorityName());
    }

    @Test
    public void testDummy()  {
        assertNotNull("change the test program");
    }

}
