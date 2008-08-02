package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.test.util.TestSchema;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;


/**
 * 
 * @author Naveen Amiruddin
 *
 */
public class StudySiteTest {
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
     */
    @Test
    public void getOrganizationsAssociatedWithStudyProtocolTest() {
        
        Session session = TestSchema.getSession();
        Organization org = OrganizationTest.organizationObj();
        TestSchema.addUpdObject(org);
        Long oid = org.getId();
        
        StudyProtocol sp = StudyProtocolTest.createStudyProtocolObj();
        TestSchema.addUpdObject(sp);
        Long spid = sp.getId();
        
        StudySite create = createStudySiteObj(sp, org , Boolean.TRUE);
        TestSchema.addUpdObject(create);
        Long id = create.getId();
        assertNotNull(id);
        StudySite saved = (StudySite) session.load(StudySite.class, id);
        assertNotNull(saved);
        assertEquals("Study Site id does not match " , create.getId() , saved.getId());
        assertEquals("Lead indicator does not match " , create.getLeadOrganizationIndicator() , 
                    saved.getLeadOrganizationIndicator());
        assertEquals("Orginzatiom id does not match " , 
                create.getOrganization().getId() , saved.getOrganization().getId());
        
    }
    
    /**
     * 
     * @param sp sp
     * @param org org
     * @param leadIndi Boolean
     * @return StudySite
     */
    public static StudySite createStudySiteObj(StudyProtocol sp , Organization org , Boolean leadIndi) {
        StudySite ss = new StudySite();
        ss.setStudyProtocol(sp);
        ss.setOrganization(org);
        ss.setLeadOrganizationIndicator(leadIndi);
        return ss;
    }

}
