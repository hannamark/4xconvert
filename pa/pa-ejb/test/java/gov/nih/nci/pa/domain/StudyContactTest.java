package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.util.TestSchema;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author NAmiruddin
 *
 */
public class StudyContactTest   {
    
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
        Transaction t = session.beginTransaction();
        Person p = PersonTest.createPersonObj();
        TestSchema.addUpdObject(p);
        Person savedP = (Person) session.load(Person.class, p.getId());
        assertEquals("Person  id does not match " , p.getId(), savedP.getId());

        Organization o = OrganizationTest.createOrganizationObj();
        TestSchema.addUpdObject(o);
        assertNotNull(o.getId());
        
        ClinicalResearchStaff crs = ClinicalResearchStaffTest.createClinicalResearchStaffObj(o,p);
        TestSchema.addUpdObject(crs);
        assertNotNull(crs.getId());
        
        HealthCareProvider hc = HealthCareProviderTest.createHealthCareProviderObj(p,o);
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
        
        StudyContact create = createStudyContactObj(sp , c , hc , crs);
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
        
        //t.commit();

    }
    
    @Test
    public void getPersonsAssociatedWithProtcol() {
        Session session = TestSchema.getSession();
        
        Person p = PersonTest.createPersonObj();
        TestSchema.addUpdObject(p);
        Person savedP = (Person) session.load(Person.class, p.getId());
        assertEquals("Person  id does not match " , p.getId(), savedP.getId());
        
        Organization o = OrganizationTest.createOrganizationObj();
        TestSchema.addUpdObject(o);
        assertNotNull(o.getId());

        HealthCareProvider hc = HealthCareProviderTest.createHealthCareProviderObj(p , o);
        TestSchema.addUpdObject(hc);
        HealthCareProvider savedhc = (HealthCareProvider) session.load(HealthCareProvider.class, hc.getId());
        assertEquals("Healtcare Provider does not match " , hc.getId(), savedhc.getId());

        ClinicalResearchStaff crs = ClinicalResearchStaffTest.createClinicalResearchStaffObj(o, p);
        TestSchema.addUpdObject(crs);
        assertNotNull(crs.getId());
        
        StudyProtocol sp = StudyProtocolTest.createStudyProtocolObj();
        TestSchema.addUpdObject(sp);
        assertNotNull(sp.getId());
        StudyProtocol savedsp = (StudyProtocol) session.load(StudyProtocol.class, sp.getId());
        assertEquals("Study Protocol does not match " , sp.getId(), savedsp.getId());
        
        Country c = CountryTest.createCountryObj();
        TestSchema.addUpdObject(c);
        assertNotNull(c.getId());
        
        StudyContact create = createStudyContactObj(sp , c , hc , crs);
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

        // insert second person
        Person p2 = PersonTest.createPersonObj();
        TestSchema.addUpdObject(p2);
        Person savedP2 = (Person) session.load(Person.class, p2.getId());
        assertEquals("Person  id does not match " , p2.getId(), savedP2.getId());

        HealthCareProvider hc2 = HealthCareProviderTest.createHealthCareProviderObj(p2, o);
        TestSchema.addUpdObject(hc2);
        HealthCareProvider savedhc2 = (HealthCareProvider) session.load(HealthCareProvider.class, hc2.getId());
        assertEquals("Healtcare Provider does not match " , hc2.getId(), savedhc2.getId());

        StudyProtocol sp2 = StudyProtocolTest.createStudyProtocolObj();
        TestSchema.addUpdObject(sp2);
        assertNotNull(sp2.getId());
        StudyProtocol savedsp2 = (StudyProtocol) session.load(StudyProtocol.class, sp2.getId());
        assertEquals("Study Protocol does not match " , sp2.getId(), savedsp2.getId());

//        StudyContact create2 = createStudyContactObj(sp2 , c , hc2 , crs);
//        TestSchema.addUpdObject(create2);
//        StudyContact saved2 = (StudyContact) session.load(StudyContact.class, create2.getId());

        
//        List<Person> persons = null;
//        StringBuffer hql = new StringBuffer();
//        hql.append(" select distinct p from Person  p " 
//        + " join p.healthCareProviders as hc "
//        + " join hc.studyContacts as sc" 
//        + " join sc.studyProtocol as sp" 
//        + " where sc.studyContactRoleCode = '" 
//        + StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR + "'");
//        session = HibernateUtil.getCurrentSession();
//        persons = session.createQuery(hql.toString()).list();
//        
//        assertEquals("Person count does not match " , persons.size() , 2);      
    }
    /**
     * 
     * @param sp StudyProtocol
     * @return StudyContact
     */
    public static  StudyContact createStudyContactObj(StudyProtocol sp , Country c , HealthCareProvider hc , ClinicalResearchStaff crs) {
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
        sc.setRoleCode(StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR);
        sc.setHealthCareProvider(hc);
        sc.setClinicalResearchStaff(crs);
        return sc;
    }

}
