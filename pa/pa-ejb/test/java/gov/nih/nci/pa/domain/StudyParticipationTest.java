package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.Serializable;
import gov.nih.nci.pa.enums.FunctionalCode;
import gov.nih.nci.pa.util.TestSchema;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;


/**
 * 
 * @author Naveen Amiruddin
 *
 */
public class StudyParticipationTest  {
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
    /*
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
    */
    @Test
    public void createStudyParticipation() {
        Session session = TestSchema.getSession();
        StudyProtocol sp = StudyProtocolTest.createStudyProtocolObj();
        TestSchema.addUpdObject(sp);
        assertNotNull(sp.getId());

        Organization o  = OrganizationTest.organizationObj();
        TestSchema.addUpdObject(o);
        assertNotNull(o.getId());
        StudyParticipation create = createStudyParticipationObj(sp, o , Boolean.TRUE);
        TestSchema.addUpdObject(create);
        assertNotNull(create.getId());
        StudyParticipation saved = (StudyParticipation) session.load(StudyParticipation.class, create.getId());
        assertEquals("StudyParticipation id does not match " , create.getId() , saved.getId());
        assertEquals("Functional Code does not match " , create.getFunctionalCode() , saved.getFunctionalCode());
        assertEquals("Lead Organization Indicator does not match " , create.getLeadOrganizationIndicator() , 
                    saved.getLeadOrganizationIndicator());
        assertEquals("Local Study Protocol Identifier does not match " , create.getLocalStudyProtocolIdentifier() , 
                    saved.getLocalStudyProtocolIdentifier());        
    }

    public static StudyParticipation createStudyParticipationObj(StudyProtocol sp , Organization org , Boolean leadIndi) {
        StudyParticipation create = new StudyParticipation();
        create.setFunctionalCode(FunctionalCode.DATA_MANAGEMENT_CENTER);
        create.setLeadOrganizationIndicator(leadIndi);
        create.setLocalStudyProtocolIdentifier("Ecog1");
        create.setUserLastUpdated("abstractor");
        java.sql.Timestamp now = new java.sql.Timestamp((new java.util.Date()).getTime());
        create.setDateLastUpdated(now);
        create.setStudyProtocol(sp);
        //create.set(sp);
        return create;
    }

}
