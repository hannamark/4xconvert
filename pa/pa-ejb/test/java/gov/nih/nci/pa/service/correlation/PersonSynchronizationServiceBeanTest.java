package gov.nih.nci.pa.service.correlation;

import static org.junit.Assert.assertNotNull;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.NullFlavor;
import gov.nih.nci.pa.domain.ClinicalResearchStaff;
import gov.nih.nci.pa.domain.ClinicalResearchStaffTest;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.OrganizationTest;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.domain.PersonTest;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.StudyContactServiceBean;
import gov.nih.nci.pa.service.StudyContactServiceLocal;
import gov.nih.nci.pa.service.StudyParticipationContactServiceBean;
import gov.nih.nci.pa.service.StudyParticipationContactServiceLocal;
import gov.nih.nci.pa.util.MockPoServiceLocator;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.pa.util.TestSchema;

import org.junit.Before;
import org.junit.Test;

public class PersonSynchronizationServiceBeanTest {

    private PersonSynchronizationServiceBean bean = new PersonSynchronizationServiceBean();
    private PersonSynchronizationServiceRemote remoteEjb = bean;
    StudyParticipationContactServiceLocal spcService = new StudyParticipationContactServiceBean();
    StudyContactServiceLocal scService = new StudyContactServiceBean();

    Ii pid;
    //Session session = null;
    Long personId = Long.valueOf(1);
    @Before
    public void setUp() throws Exception {

        PoRegistry.getInstance().setPoServiceLocator(new MockPoServiceLocator());
        bean.spcLocal = spcService;
        bean.scLocal = scService;
        TestSchema.reset1();
        createTestData();

    }
    
    @Test
    public void synchronizePersonUpdateTest()  throws Exception {
        Ii roIi = IiConverter.converToPoPersonIi("abc");
        remoteEjb.synchronizePerson(roIi);
        Person np = null;
        np = (Person) TestSchema.getSession().load(Person.class, personId);
        // todo : somehow the update is happening in a different session and the changes are not committed, so unable to
        // do assert with the changed values
    }    
    @Test
    public void synchronizePersonNullifiy()  throws Exception {
        Ii roIi = IiConverter.converToPoPersonIi("abc");
        roIi.setNullFlavor(NullFlavor.NA);
        remoteEjb.synchronizePerson(roIi);
        // todo : somehow the update is happening in a different session and the changes are not committed, so unable to
        // do assert with the changed values
    }    

    @Test
    public void synchronizeClinicalResearchStaffUpdateTest()  throws Exception {
        Ii crsIi = IiConverter.converToPoClinicalResearchStaffIi("abc");
        remoteEjb.synchronizeClinicalResearchStaff(crsIi);
        // todo : somehow the update is happening in a different session and the changes are not committed, so unable to
        // do assert with the changed values
    }     

    @Test
    public void synchronizeClinicalResearchStaffNullifyTest()  throws Exception {
        Ii crsIi = IiConverter.converToPoClinicalResearchStaffIi("abc");
        crsIi.setNullFlavor(NullFlavor.NA);
        remoteEjb.synchronizeClinicalResearchStaff(crsIi);
        // todo : somehow the update is happening in a different session and the changes are not committed, so unable to
        // do assert with the changed values
    }     

    @Test
    public void synchronizeHealthCareProviderUpdateTest()  throws Exception {
        Ii hcpIi = IiConverter.converToPoHealtcareProviderIi("abc");
        remoteEjb.synchronizeHealthCareProvider(hcpIi);
        // todo : somehow the update is happening in a different session and the changes are not committed, so unable to
        // do assert with the changed values
    }     

    @Test
    public void synchronizeHealthCareProviderNulllifyTest()  throws Exception {
        Ii hcpIi = IiConverter.converToPoHealtcareProviderIi("abc");
        hcpIi.setNullFlavor(NullFlavor.NA);
        remoteEjb.synchronizeHealthCareProvider(hcpIi);
        // todo : somehow the update is happening in a different session and the changes are not committed, so unable to
        // do assert with the changed values
    }  

    @Test
    public void synchronizeOrganizationalContactUpdateTest()  throws Exception {
        Ii ocIi = IiConverter.converToPoOrganizationalContactIi("abc");
        remoteEjb.synchronizeOrganizationalContact(ocIi);
        // todo : somehow the update is happening in a different session and the changes are not committed, so unable to
        // do assert with the changed values
    }     

    @Test
    public void synchronizeOrganizationalContactNullifyTest()  throws Exception {
        Ii ocIi = IiConverter.converToPoOrganizationalContactIi("abc");
        ocIi.setNullFlavor(NullFlavor.NA);
        remoteEjb.synchronizeOrganizationalContact(ocIi);
        // todo : somehow the update is happening in a different session and the changes are not committed, so unable to
        // do assert with the changed values
    }     
    
    private void createTestData() {
        
        Person p  = PersonTest.createPersonObj();
        TestSchema.addUpdObject(p);
        assertNotNull(p.getId());
        personId = p.getId();
        Person np = null;
        np = (Person) TestSchema.getSession().load(Person.class, personId);
//        System.out.println("id = "+personId);
//        System.out.println("id = "+np.getId());
//        System.out.println("name id = "+np.getFirstMiddleLastName());
//        System.out.println("name id = "+np.getStatusCode());
//        System.out.println("---------------");
        
        Organization o  = OrganizationTest.createOrganizationObj();
        TestSchema.addUpdObject(o);
        assertNotNull(o.getId());        
        
        ClinicalResearchStaff crs = ClinicalResearchStaffTest.createClinicalResearchStaffObj(o, p);
        TestSchema.addUpdObject(crs);
        assertNotNull(crs.getId());        

    }    

    
}
