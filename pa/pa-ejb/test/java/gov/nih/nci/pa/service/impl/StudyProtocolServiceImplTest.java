package gov.nih.nci.pa.service.impl;

import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.CountryTest;
import gov.nih.nci.pa.domain.DocumentWorkFlowStatusTest;
import gov.nih.nci.pa.domain.DocumentWorkflowStatus;
import gov.nih.nci.pa.domain.HealthCareProvider;
import gov.nih.nci.pa.domain.HealthCareProviderTest;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.OrganizationTest;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.domain.PersonTest;
import gov.nih.nci.pa.domain.StudyContact;
import gov.nih.nci.pa.domain.StudyContactRole;
import gov.nih.nci.pa.domain.StudyContactRolesTest;
import gov.nih.nci.pa.domain.StudyContactTest;
import gov.nih.nci.pa.domain.StudyCoordinatingCenter;
import gov.nih.nci.pa.domain.StudyCoordinatingCenterRole;
import gov.nih.nci.pa.domain.StudyCoordinatingCenterRoleTest;
import gov.nih.nci.pa.domain.StudyCoordinatingCenterTest;
import gov.nih.nci.pa.domain.StudyOverallStatus;
import gov.nih.nci.pa.domain.StudyOverallStatusTest;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyProtocolTest;
import gov.nih.nci.pa.domain.StudyParticipation; 
import gov.nih.nci.pa.domain.StudyParticipationTest;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.ResponsibilityCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.test.util.TestSchema;

import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author NAmiruddin
 *
 */
public class StudyProtocolServiceImplTest {
    
    /**
     * 
     * @throws Exception e
     */
    @Before
    public void setUp() throws Exception {
        TestSchema.reset();
               
    }
    
    /**
     * 
     * @throws PAException  PAException
     */
    @Test
    public  void getStudyProtocolByCriteriaTest() throws PAException {

        StudyProtocol sp = StudyProtocolTest.createStudyProtocolObj();
        TestSchema.addUpdObject(sp);
        Long id = sp.getId();

         sp = StudyProtocolTest.createStudyProtocolObj();
        TestSchema.addUpdObject(sp);
         id = sp.getId();
         StudyProtocolServiceImpl spsImpl = new StudyProtocolServiceImpl();
         StudyProtocolQueryCriteria spqc = new StudyProtocolQueryCriteria();

         List<StudyProtocolQueryDTO> spDtos = spsImpl.getStudyProtocolByCriteria(spqc); 
         assertNotNull(spDtos);
         assertEquals(" size of StudyProtocolQueryDTO does not match " , spDtos.size() , 2);

       // Long id = createDate().getId();
       // assertNotNull(id);
        /*
        StudyProtocolServiceImpl spsImpl = new StudyProtocolServiceImpl();
        StudyProtocolQueryCriteria spqc = new StudyProtocolQueryCriteria();
        spqc.setStudyProtocolId(id);
        List<StudyProtocolQueryDTO> spDtos = spsImpl.getStudyProtocolByCriteria(spqc); 
        assertNotNull(spDtos);
        assertEquals(" size of StudyProtocolQueryDTO does not match " , spDtos.size() , 1);
        //assertEquals(" protocol title  does not match " , spDtos.get(0).getOfficialTitle() , sp.getOfficialTitle());
        //assertEquals(" PI id  does not match " , spDtos.get(0).getPiId() , p.getId());
        //assertEquals(" Lead organization id  does not match " , spDtos.get(0).getLeadOrganizationId() , o.getId());
        */
    }
    
    /**
     * 
     * @throws PAException  PAException
     */
    //@Test(expected = PAException.class)
    public  void testForNoDataPAException() throws PAException {
        //Long id = createDate().getId();
        StudyProtocolServiceImpl spsImpl = new StudyProtocolServiceImpl();
        //spsImpl.getTrialSummaryByStudyProtocolId(new Long(-1));
    }
    
    /**
     * 
     * @throws PAException  PAException
     */
    //@Test(expected = PAException.class)
    public  void testForNullPAException() throws PAException {
        //Long id = createDate().getId();
        StudyProtocolServiceImpl spsImpl = new StudyProtocolServiceImpl();
        spsImpl.getTrialSummaryByStudyProtocolId(null);
    }    
    
    private StudyProtocol createDate() {

        StudyProtocol sp = StudyProtocolTest.createStudyProtocolObj();
        TestSchema.addUpdObject(sp);
        Long id = sp.getId();
        
        StudyOverallStatus sos = StudyOverallStatusTest.createStudyOverallStatusobj(sp);
        TestSchema.addUpdObject(sos);
        Long sid = sp.getId();
        
        DocumentWorkflowStatus dwfs = DocumentWorkFlowStatusTest.createDocumentWorkflowStatus(sp);
        TestSchema.addUpdObject(dwfs);
        Long dwfsid = dwfs.getId();
        
        
        Person p = PersonTest.createPersonObj();
        TestSchema.addUpdObject(p);
        Long pid = p.getId();
        
        
        HealthCareProvider hc = HealthCareProviderTest.createHealthCareProviderObj(p);
        TestSchema.addUpdObject(hc);
        Long hcid = hc.getId();
        
        Country c = CountryTest.createCountryObj();
        TestSchema.addUpdObject(c);
        StudyContact sc = StudyContactTest.createStudyContactObj(sp, c, hc);
        TestSchema.addUpdObject(sc);
        Long scid = sc.getId();
        
        StudyContactRole scr = StudyContactRolesTest.createStudyContactRoleObj(sc , 
                StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR);
        TestSchema.addUpdObject(scr);
        Long scrid = scr.getId();
        
        Organization o  = OrganizationTest.organizationObj();
        TestSchema.addUpdObject(o);
        Long oid = o.getId();

        StudyCoordinatingCenter scc = StudyCoordinatingCenterTest.createStudyCoordinatingCenterObj(sp, o);
        TestSchema.addUpdObject(scc);
        Long sccid = scc.getId();
        
        StudyCoordinatingCenterRole sccr = 
                StudyCoordinatingCenterRoleTest.createStudyCoordinatingCenterRoleObj(
                        scc , ResponsibilityCode.PROTOCOL_MANAGEMENT);
        TestSchema.addUpdObject(sccr);
        Long sccrid = sccr.getId();
        
        
        return sp;
        
    }
    
    
}

