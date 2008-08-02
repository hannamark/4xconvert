package gov.nih.nci.pa.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.OrganizationTest;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyProtocolTest;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.domain.StudySiteTest;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.test.util.TestSchema;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author NAmiruddin
 *
 */
public class StudySiteDAOTest {

    /**
     * 
     * @throws Exception Exception
     */
    @Before
    public void setUp() throws Exception {
        TestSchema.reset();
               
    }
    
    /**
     * @throws PAException pe
     * 
     */
    @Test
    public void getOrganizationsAssociatedWithStudyProtocol() throws PAException {
        
        Session session = TestSchema.getSession();
        
        Organization org = OrganizationTest.organizationObj();
        TestSchema.addUpdObject(org);

        
        StudyProtocol sp1 = StudyProtocolTest.createStudyProtocolObj();
        TestSchema.addUpdObject(sp1);

        
        StudySite create1 = StudySiteTest.createStudySiteObj(sp1, org , Boolean.TRUE);
        TestSchema.addUpdObject(create1);
        
        StudyProtocol sp2 = StudyProtocolTest.createStudyProtocolObj();
        TestSchema.addUpdObject(sp2);
        
        StudySite create2 = StudySiteTest.createStudySiteObj(sp2, org , Boolean.TRUE);
        TestSchema.addUpdObject(create2);

        
        StudySiteDAO ssdao = new StudySiteDAO();
        List<Organization> o  = ssdao.getOrganizationsAssociatedWithStudyProtocol();
        assertNotNull(o);
        assertEquals("Number of expect organization does not match ", o.size(), 1);
        
        
    }


}
