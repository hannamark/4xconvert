package gov.nih.nci.pa.service.correlation;

import static org.junit.Assert.assertNotNull;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.NullFlavor;
import gov.nih.nci.pa.domain.HealthCareFacility;
import gov.nih.nci.pa.domain.HealthCareFacilityTest;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.OrganizationTest;
import gov.nih.nci.pa.domain.StudyParticipation;
import gov.nih.nci.pa.domain.StudyParticipationTest;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyProtocolTest;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.StudyParticipationServiceBean;
import gov.nih.nci.pa.service.StudyParticipationServiceLocal;
import gov.nih.nci.pa.util.MockPoServiceLocator;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.pa.util.TestSchema;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

public class OrganizationSynchronizationServiceBeanTest {

    private OrganizationSynchronizationServiceBean bean = new OrganizationSynchronizationServiceBean();
    private OrganizationSynchronizationServiceRemote remoteEjb = bean;
    StudyParticipationServiceLocal spsService = new StudyParticipationServiceBean();
    Long createdHcfId = null;
    Long createdSpsId = null;

    Ii pid;
    Session session = null;
    
    @Before
    public void setUp() throws Exception {
        PoRegistry.getInstance().setPoServiceLocator(new MockPoServiceLocator());
        bean.spsLocal = spsService;
        session = TestSchema.getSession();
        TestSchema.reset1();
        //TestSchema.reset1();
        //TestSchema.primeData();

    }

    @Test
    public void synchronizeHealthCareFacilityActiveToPending() throws Exception {
        Ii hcfIi = IiConverter.converToPoHealthCareFacilityIi("abc");
        createTestData1();
        remoteEjb.synchronizeHealthCareFacility(hcfIi);
    }
    
    
    @Test
    public void synchronizeHealthCareFacilityNullify() throws Exception {
        Ii hcfIi = IiConverter.converToPoHealthCareFacilityIi("abc");
        hcfIi.setNullFlavor(NullFlavor.NA);
        createTestData1();
        remoteEjb.synchronizeHealthCareFacility(hcfIi);
        
    }
    
    @Test
    public void synchronizeResearchOrganizationActiveToPending() throws Exception {
        Ii roIi = IiConverter.converToPoResearchOrganizationIi("abc");
        createTestData1();
        remoteEjb.synchronizeResearchOrganization(roIi);

    }
    
    @Test
    public void synchronizeResearchOrganizationNullify() throws Exception {
        Ii roIi = IiConverter.converToPoResearchOrganizationIi("abc");
        roIi.setNullFlavor(NullFlavor.NA);
        createTestData1();
        remoteEjb.synchronizeResearchOrganization(roIi);
    }

    @Test
    public void synchronizeOversightCommitteeActiveToPending() throws Exception {
        Ii roIi = IiConverter.converToPoOversightCommitteeIi("abc");
        createTestData1();
        remoteEjb.synchronizeOversightCommittee(roIi);

    }
    
    @Test
    public void synchronizeOversightCommitteeNullify() throws Exception {
        Ii roIi = IiConverter.converToPoOversightCommitteeIi("abc");
        roIi.setNullFlavor(NullFlavor.NA);
        createTestData1();
        remoteEjb.synchronizeOversightCommittee(roIi);
    }
    
    @Test
    public void synchronizeOrganization()  throws Exception {
        Ii roIi = IiConverter.converToPoOrganizationIi("abc");
        createTestData1();
        remoteEjb.synchronizeOrganization(roIi);
    }
    
    @Test
    public void synchronizeOrganizationNullify()  throws Exception {
        Ii roIi = IiConverter.converToPoOrganizationIi("abc");
        roIi.setNullFlavor(NullFlavor.NA);
        createTestData1();
        remoteEjb.synchronizeOrganization(roIi);
    }

    private void createTestData1() {

        Organization o  = OrganizationTest.createOrganizationObj();
        TestSchema.addUpdObject(o);
        assertNotNull(o.getId());

        HealthCareFacility hcf = HealthCareFacilityTest.createHealthCareFacilityObj(o);
        TestSchema.addUpdObject(hcf);
        HealthCareFacility savedhc = (HealthCareFacility) session.load(HealthCareFacility.class, hcf.getId());
        createdHcfId = savedhc.getId();

        StudyProtocol sp = StudyProtocolTest.createStudyProtocolObj();
        TestSchema.addUpdObject(sp);
        assertNotNull(sp.getId());

        StudyParticipation create = StudyParticipationTest.createStudyParticipationObj(sp, hcf) ;
        //create.setStatusCode(StatusCode.PENDING);
        TestSchema.addUpdObject(create);
        assertNotNull(create.getId());
        StudyParticipation saved = (StudyParticipation) session.load(StudyParticipation.class, create.getId());
        createdSpsId = saved.getId();
    }

    


}
