package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.util.TestSchema;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author NAmiruddin
 *
 */
public class StudyContactTest  {
    
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
        assertEquals("Healtcare Provider does not match " , hc.getId(), savedhc.getId());


        StudyProtocol sp = StudyProtocolTest.createStudyProtocolObj();
        TestSchema.addUpdObject(sp);
        assertNotNull(sp.getId());
        StudyProtocol savedsp = (StudyProtocol) session.load(StudyProtocol.class, sp.getId());
        assertEquals("Study Protocol does not match " , sp.getId(), savedsp.getId());
        
        Country c = CountryTest.createCountryObj();
        TestSchema.addUpdObject(c);
        assertNotNull(c.getId());
        
        StudyContact create = createStudyContactObj(sp , c , hc);
        TestSchema.addUpdObject(create);
        StudyContact saved = (StudyContact) session.load(StudyContact.class, create.getId());
        
        assertNotNull(saved.getId());
        assertEquals("Study contact does not match " , create.getId(), saved.getId());
        assertEquals("Address line  does not match " , create.getAddressLine(), saved.getAddressLine());
        assertEquals("Delivery line  does not match " , 
                create.getDeliveryAddressLine(), saved.getDeliveryAddressLine());
        assertEquals("City  does not match " , create.getCity(), saved.getCity());
        assertEquals("State  does not match " , create.getState(), saved.getState());
        assertEquals("Country does not match " , create.getCountry().getId(), saved.getCountry().getId());
        assertEquals("Study contact / Healthcare  not match " , 
              create.getHealthCareProvider().getId(), saved.getHealthCareProvider().getId());
        assertEquals("Study contact / Study Protocol  not match " , 
                create.getStudyProtocol().getId(), saved.getStudyProtocol().getId());
        assertEquals("Study contact / Healthcare /Person not match " , 
                create.getHealthCareProvider().getPerson().getId(), saved.getHealthCareProvider().getPerson().getId());
        
   
    }
    
    /**
     * 
     * @param sp StudyProtocol
     * @return StudyContact
     */
    public static  StudyContact createStudyContactObj(StudyProtocol sp , Country c , HealthCareProvider hc) {
        StudyContact sc = new StudyContact();
        sc.setPrimaryIndicator(Boolean.TRUE);
        sc.setAddressLine("1111, terra cotta cirle");
        sc.setDeliveryAddressLine("xxx");
        sc.setCity("herndon");
        sc.setCountry(c);
        sc.setPostalCode("20111");
        sc.setUserLastUpdated("abstractor");
        java.sql.Timestamp now = new java.sql.Timestamp((new java.util.Date()).getTime());
        sc.setDateLastUpdated(now);
        sc.setStudyProtocol(sp);
        sc.setHealthCareProvider(hc);
        return sc;
    }

}
