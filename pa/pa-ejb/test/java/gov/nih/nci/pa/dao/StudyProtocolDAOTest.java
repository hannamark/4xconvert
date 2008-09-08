package gov.nih.nci.pa.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.CountryTest;
import gov.nih.nci.pa.domain.HealthCareFacility;
import gov.nih.nci.pa.domain.HealthCareFacilityTest;
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
import gov.nih.nci.pa.domain.StudyParticipation;
import gov.nih.nci.pa.domain.StudyParticipationTest;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyProtocolTest;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.test.util.TestSchema;


import java.util.List;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;
/**
 * 
 * @author NAmiruddin
 *
 */
public class StudyProtocolDAOTest {
    
    /**
     * 
     * @throws Exception Exception
     */
    @Before
    public void setUp() throws Exception {
        TestSchema.reset();
               
    }
    
    /**
     * 
     */
    //@Test
    public void getStudyProtocolByCriteriaTest() {
        /*
        List<Object> obj = null;
        StudyProtocol sp = StudyProtocolTest.createStudyProtocolObj();
        TestSchema.addUpdObject(sp);
        Long id = sp.getId();
        
        StudyProtocolDAO spDAO = new StudyProtocolDAO();
        StudyProtocolQueryCriteria qspc = new StudyProtocolQueryCriteria();
        qspc.setOfficialTitle(sp.getOfficialTitle());
        try {
             obj = spDAO.getStudyProtocolByCriteria(qspc);
        } catch (PAException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        */
        StudyProtocolDAO spDAO = new StudyProtocolDAO();
        StudyProtocolQueryCriteria qspc = new StudyProtocolQueryCriteria();
        qspc.setLeadOrganizationTrialIdentifier("Ec");
        List<Object> obj = null;
        StudyProtocol sp = StudyProtocolTest.createStudyProtocolObj();
        TestSchema.addUpdObject(sp);
        Long id = sp.getId();

         sp = StudyProtocolTest.createStudyProtocolObj();
        TestSchema.addUpdObject(sp);
         id = sp.getId();
        
        try {
            obj = spDAO.getStudyProtocolByCriteria(qspc);
       } catch (PAException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       }
       //System.out.println(obj.size()); 
        assertNotNull(obj);
        
    }
    
    //@Test
    public void getPersonsAssociatedWithProtcol() {
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
        
        StudyContact create = StudyContactTest.createStudyContactObj(sp , c , hc);
        TestSchema.addUpdObject(create);
        StudyContact saved = (StudyContact) session.load(StudyContact.class, create.getId());
        
        StudyContactRole scr = StudyContactRolesTest.createStudyContactRoleObj(create, StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR);
        TestSchema.addUpdObject(scr);
        
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

        HealthCareProvider hc2 = HealthCareProviderTest.createHealthCareProviderObj(p2);
        TestSchema.addUpdObject(hc2);
        HealthCareProvider savedhc2 = (HealthCareProvider) session.load(HealthCareProvider.class, hc2.getId());
        assertEquals("Healtcare Provider does not match " , hc2.getId(), savedhc2.getId());

        StudyProtocol sp2 = StudyProtocolTest.createStudyProtocolObj();
        TestSchema.addUpdObject(sp2);
        assertNotNull(sp2.getId());
        StudyProtocol savedsp2 = (StudyProtocol) session.load(StudyProtocol.class, sp2.getId());
        assertEquals("Study Protocol does not match " , sp2.getId(), savedsp2.getId());

        StudyContact create2 = StudyContactTest.createStudyContactObj(sp2 , c , hc2);
        TestSchema.addUpdObject(create2);
        StudyContact saved2 = (StudyContact) session.load(StudyContact.class, create2.getId());

        StudyContactRole scr2 = StudyContactRolesTest.createStudyContactRoleObj(create2, StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR);
        TestSchema.addUpdObject(scr2);
        
        StudyProtocolDAO spDAO = new StudyProtocolDAO();
        StudyProtocolQueryCriteria qspc = new StudyProtocolQueryCriteria();
        qspc.setPrincipalInvestigatorId(Long.valueOf("1"));
        List<Object> obj = null;
        
        try {
            obj = spDAO.getStudyProtocolByCriteria(qspc);
       } catch (PAException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       }
       assertNotNull(obj);
             
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

        
        StudyParticipation create = StudyParticipationTest.createStudyParticipationObj(sp, hcf) ;
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
        
        StudyParticipation create2 = StudyParticipationTest.createStudyParticipationObj(sp2, hcf2) ;
        TestSchema.addUpdObject(create2);
        assertNotNull(create2.getId());
        StudyParticipation saved2 = (StudyParticipation) session.load(StudyParticipation.class, create2.getId());
        assertEquals("StudyParticipation id does not match " , create2.getId() , saved2.getId());
        assertEquals("Functional Code does not match " , create2.getFunctionalCode() , saved2.getFunctionalCode());
        assertEquals("Local Study Protocol Identifier does not match " , create2.getLocalStudyProtocolIdentifier() , 
                    saved2.getLocalStudyProtocolIdentifier());        

        
        StudyProtocolDAO spDAO = new StudyProtocolDAO();
        StudyProtocolQueryCriteria qspc = new StudyProtocolQueryCriteria();
        qspc.setLeadOrganizationId(Long.valueOf("1"));
        List<Object> obj = null;
        
        try {
            obj = spDAO.getStudyProtocolByCriteria(qspc);
       } catch (PAException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       }
       assertNotNull(obj);
        
        
    }
    

}
