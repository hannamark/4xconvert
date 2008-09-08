package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.enums.StudyParticipationFunctionalCode;
import gov.nih.nci.pa.test.util.TestSchema;

import java.util.List;

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
    

    @Test
    public void createStudyParticipation() {
        Session session = TestSchema.getSession();

        Organization o  = OrganizationTest.createOrganizationObj();
        TestSchema.addUpdObject(o);
        assertNotNull(o.getId());

        HealthCareFacility hcf = HealthCareFacilityTest.createHealthCareFacilityObj(o);
        TestSchema.addUpdObject(hcf);
        HealthCareFacility savedhc = (HealthCareFacility) session.load(HealthCareFacility.class, hcf.getId());
        assertEquals("Healcare Provider does not match " , hcf.getId(), savedhc.getId());
        
        
        StudyProtocol sp = StudyProtocolTest.createStudyProtocolObj();
        TestSchema.addUpdObject(sp);
        assertNotNull(sp.getId());

        
        StudyParticipation create = createStudyParticipationObj(sp, hcf) ;
        TestSchema.addUpdObject(create);
        assertNotNull(create.getId());
        StudyParticipation saved = (StudyParticipation) session.load(StudyParticipation.class, create.getId());
        assertEquals("StudyParticipation id does not match " , create.getId() , saved.getId());
        assertEquals("Functional Code does not match " , create.getFunctionalCode() , saved.getFunctionalCode());
        assertEquals("Local Study Protocol Identifier does not match " , create.getLocalStudyProtocolIdentifier() , 
                    saved.getLocalStudyProtocolIdentifier());        
    }
    
    @Test
    public void getOrganizationAssociatedWithProtcol() {
        Session session = TestSchema.getSession();
        Organization o  = OrganizationTest.createOrganizationObj();
        TestSchema.addUpdObject(o);
        assertNotNull(o.getId());


        
        HealthCareFacility hcf = HealthCareFacilityTest.createHealthCareFacilityObj(o);
        TestSchema.addUpdObject(hcf);
        HealthCareFacility savedhc = (HealthCareFacility) session.load(HealthCareFacility.class, hcf.getId());
        assertEquals("Healcare Provider does not match " , hcf.getId(), savedhc.getId());
        
        
        StudyProtocol sp = StudyProtocolTest.createStudyProtocolObj();
        TestSchema.addUpdObject(sp);
        assertNotNull(sp.getId());

        
        StudyParticipation create = createStudyParticipationObj(sp, hcf) ;
        TestSchema.addUpdObject(create);
        assertNotNull(create.getId());
        StudyParticipation saved = (StudyParticipation) session.load(StudyParticipation.class, create.getId());
        assertEquals("StudyParticipation id does not match " , create.getId() , saved.getId());
        assertEquals("Functional Code does not match " , create.getFunctionalCode() , saved.getFunctionalCode());
        assertEquals("Local Study Protocol Identifier does not match " , create.getLocalStudyProtocolIdentifier() , 
                    saved.getLocalStudyProtocolIdentifier());        

        Organization o2  = OrganizationTest.createOrganizationObj();
        TestSchema.addUpdObject(o2);
        assertNotNull(o2.getId());

        HealthCareFacility hcf2 = HealthCareFacilityTest.createHealthCareFacilityObj(o2);
        TestSchema.addUpdObject(hcf2);
        HealthCareFacility savedhc2 = (HealthCareFacility) session.load(HealthCareFacility.class, hcf2.getId());
        assertEquals("Healcare Provider does not match " , hcf2.getId(), savedhc2.getId());

        StudyProtocol sp2 = StudyProtocolTest.createStudyProtocolObj();
        TestSchema.addUpdObject(sp2);
        assertNotNull(sp2.getId());
        
        StudyParticipation create2 = createStudyParticipationObj(sp2, hcf2) ;
        TestSchema.addUpdObject(create2);
        assertNotNull(create2.getId());
        StudyParticipation saved2 = (StudyParticipation) session.load(StudyParticipation.class, create2.getId());
        assertEquals("StudyParticipation id does not match " , create2.getId() , saved2.getId());
        assertEquals("Functional Code does not match " , create2.getFunctionalCode() , saved2.getFunctionalCode());
        assertEquals("Local Study Protocol Identifier does not match " , create2.getLocalStudyProtocolIdentifier() , 
                    saved2.getLocalStudyProtocolIdentifier());        

        
        StringBuffer hql = new StringBuffer();
        hql.append(
                " Select distinct o from Organization o  " +
                " join o.healthCareFacilities as hcfs " + 
                " join hcfs.studyParticipations as sps " +
                " join sps.studyProtocol as sp " +
                "  where sps.functionalCode = '" +StudyParticipationFunctionalCode.LEAD_ORAGANIZATION  + "'");

        
        List<Organization> organizations = null;
        organizations =  session.createQuery(hql.toString()).list();
        assertEquals("Size  does not match " , organizations.size() , 3);
        
        
    }
    public static StudyParticipation createStudyParticipationObj(StudyProtocol sp , HealthCareFacility hcp ) {
        StudyParticipation create = new StudyParticipation();
        create.setFunctionalCode(StudyParticipationFunctionalCode.LEAD_ORAGANIZATION);
        create.setLocalStudyProtocolIdentifier("Ecog1");
        create.setUserLastUpdated("abstractor");
        java.sql.Timestamp now = new java.sql.Timestamp((new java.util.Date()).getTime());
        create.setDateLastUpdated(now);
        create.setStudyProtocol(sp);
        create.setHealthCareFacility(hcp);
        return create;
    }

}
