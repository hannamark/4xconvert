package gov.nih.nci.pa.service.impl;

import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.service.PAException;
//import gov.nih.nci.pa.util.TestSchema;
import gov.nih.nci.pa.test.util.TestSchema;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author NAmiruddin
 *
 */
public class PAOrganizationServiceImplTest {
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
    public  void getOrganizationsAssociatedWithStudyProtocol() throws PAException {
        /*
        Session session = TestSchema.getSession();
        
        Organization org = OrganizationTest.organizationObj();
        TestSchema.addUpdObject(org);

        
        StudyProtocol sp1 = StudyProtocolTest.createStudyProtocolObj();
        TestSchema.addUpdObject(sp1);

        
        StudyParticipation stp1 = StudyParticipationTest.createStudyParticipationObj(sp1, org , Boolean.TRUE);
        TestSchema.addUpdObject(stp1);
        
        StudyProtocol sp2 = StudyProtocolTest.createStudyProtocolObj();
        TestSchema.addUpdObject(sp2);
        
        StudyParticipation stp2 = StudyParticipationTest.createStudyParticipationObj(sp2, org , Boolean.TRUE);
        TestSchema.addUpdObject(stp2);
        
        PAOrganizationServiceImpl impl = new PAOrganizationServiceImpl();
        List<OrganizationDTO> orgzDtos = impl.getOrganizationsAssociatedWithStudyProtocol();

        assertNotNull(orgzDtos);
        assertEquals(" size of StudyProtocolQueryDTO does not match " , orgzDtos.size() , 1);        
        */
        assertNotNull("REMOVE THE ABOVE COMMENT AND FIX THE CODE");
        
    }

}
