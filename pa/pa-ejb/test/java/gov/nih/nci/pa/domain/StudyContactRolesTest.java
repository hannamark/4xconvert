package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.util.TestSchema;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author NAmiruddin
 *
 */

public class StudyContactRolesTest  {

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
    public void createStudyContactRolesTest() {
        Session session = TestSchema.getSession();
        Person p = PersonTest.createPersonObj();
        TestSchema.addUpdObject(p);
        Person savedP = (Person) session.load(Person.class, p.getId());
        assertEquals("Person  id does not match " , p.getId(), savedP.getId());
        
        HealthCareProvider hc = HealthCareProviderTest.createHealthCareProviderObj(p);
        TestSchema.addUpdObject(hc);
        HealthCareProvider savedhc = (HealthCareProvider) session.load(HealthCareProvider.class, hc.getId());
        assertEquals("Healcare Provider does not match " , hc.getId(), savedhc.getId());

        
        StudyProtocol sp = StudyProtocolTest.createStudyProtocolObj();
        TestSchema.addUpdObject(sp);
        StudyProtocol savedsp = (StudyProtocol) session.load(StudyProtocol.class, sp.getId());
        assertEquals("Study Protocol does not match " , sp.getId(), savedsp.getId());
        
        Country c = CountryTest.createCountryObj();
        TestSchema.addUpdObject(c);
        
        StudyContact sc = StudyContactTest.createStudyContactObj(sp , c , hc );
        TestSchema.addUpdObject(sc);
        StudyContact savedsc = (StudyContact) session.load(StudyContact.class, sc.getId());
        
        StudyContactRole create =  createStudyContactRoleObj(sc, StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR);
        TestSchema.addUpdObject(create);
        StudyContactRole saved = (StudyContactRole) session.load(StudyContactRole.class, create.getId());
        
        assertNotNull(saved.getId());
        assertEquals("Study contact does not match " , create.getId(), saved.getId());
        assertEquals("Study contact / Healthcare  not match " , create.getStudyContactRoleCode(), 
                saved.getStudyContactRoleCode());
    }
    
    /**
     * 
     * @param sc StudyContact
     * @param scrr scrr 
     * @return StudyContact
     */
    public  static StudyContactRole createStudyContactRoleObj(StudyContact sc , StudyContactRoleCode scrr) {
        
        StudyContactRole scr = new StudyContactRole();
        scr.setStudyContact(sc);
        scr.setStudyContactRoleCode(scrr);
        scr.setUserLastUpdated("abstractor");
        java.sql.Timestamp now = new java.sql.Timestamp((new java.util.Date()).getTime());
        scr.setDateLastUpdated(now);
        
        return scr;
    }
    
    
        
        

}
