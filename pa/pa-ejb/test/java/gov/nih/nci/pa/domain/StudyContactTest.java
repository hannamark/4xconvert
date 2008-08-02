package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.test.util.TestSchema;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author NAmiruddin
 *
 */
public class StudyContactTest {
    
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
    public void createStudyContactTest() {
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
        
        StudyContact create = createStudyContactObj(hc, sp);
        TestSchema.addUpdObject(create);
        StudyContact saved = (StudyContact) session.load(StudyContact.class, create.getId());
        
        assertNotNull(saved.getId());
        assertEquals("Study contact does not match " , create.getId(), saved.getId());
        assertEquals("Study contact / Healthcare  not match " , 
                create.getHealthCareProvider().getId(), saved.getHealthCareProvider().getId());
        assertEquals("Study contact / Study Protocol  not match " , 
                create.getStudyProtocol().getId(), saved.getStudyProtocol().getId());
        assertEquals("Study contact / Healthcare /Person not match " , 
                create.getHealthCareProvider().getPerson().getId(), saved.getHealthCareProvider().getPerson().getId());
        
    }
    
    /**
     * 
     * @param hc HealthCareProvider
     * @param sp StudyProtocol
     * @return StudyContact
     */
    public static  StudyContact createStudyContactObj(HealthCareProvider hc , StudyProtocol sp) {
        StudyContact sc = new StudyContact();
        sc.setPrimaryIndicator(true);
        sc.setHealthCareProvider(hc);
        sc.setStudyProtocol(sp);
        return sc;
    }

}
