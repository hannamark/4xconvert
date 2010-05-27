package gov.nih.nci.pa.service.correlation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.NullFlavor;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.OrganizationTest;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.StudySiteBeanLocal;
import gov.nih.nci.pa.service.StudySiteServiceLocal;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.MockPoServiceLocator;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.pa.util.TestSchema;

import org.hibernate.Criteria;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.junit.Before;
import org.junit.Test;

public class OrganizationSynchronizationServiceBeanTest {

    private OrganizationSynchronizationServiceBean bean = new OrganizationSynchronizationServiceBean();
    StudySiteServiceLocal spsService = new StudySiteBeanLocal();
    Long createdHcfId = null;
    Long createdSpsId = null;

    Ii pid;
    Session session = null;
    
    @Before
    public void setUp() throws Exception {
        PoRegistry.getInstance().setPoServiceLocator(new MockPoServiceLocator());
        bean.spsLocal = spsService;
//        TestSchema.reset();
        TestSchema.reset1();
//        session = TestSchema.getSession();
        //TestSchema.reset1();
        TestSchema.primeData();
        
        session  = HibernateUtil.getCurrentSession();
        createTestData();
        
    }

    @Test
    public void synchronizeHealthCareFacilityActiveToPending() throws Exception {
        Ii hcfIi = IiConverter.convertToPoHealthCareFacilityIi("abc");
        bean.synchronizeHealthCareFacility(hcfIi);
    }
    
    
//    @Test
    public void synchronizeHealthCareFacilityNullify() throws Exception {
        Ii hcfIi = IiConverter.convertToPoHealthCareFacilityIi("abc");
        hcfIi.setNullFlavor(NullFlavor.NA);
        bean.synchronizeHealthCareFacility(hcfIi);
        
    }
    
    @Test
    public void synchronizeResearchOrganizationActiveToPending() throws Exception {
        Ii roIi = IiConverter.convertToPoResearchOrganizationIi("abc");
        bean.synchronizeResearchOrganization(roIi);

    }
    
    @Test
    public void synchronizeResearchOrganizationNullify() throws Exception {
        Ii roIi = IiConverter.convertToPoResearchOrganizationIi("abc");
        roIi.setNullFlavor(NullFlavor.NA);
        bean.synchronizeResearchOrganization(roIi);
    }

    @Test
    public void synchronizeOversightCommitteeActiveToPending() throws Exception {
        Ii roIi = IiConverter.convertToPoOversightCommitteeIi("abc");
        bean.synchronizeOversightCommittee(roIi);

    }
    
    @Test
    public void synchronizeOversightCommitteeNullify() throws Exception {
        Ii roIi = IiConverter.convertToPoOversightCommitteeIi("abc");
        roIi.setNullFlavor(NullFlavor.NA);
        bean.synchronizeOversightCommittee(roIi);
    }
    
    @Test
    public void synchronizeOrganization()  throws Exception {
        Ii roIi = IiConverter.convertToPoOrganizationIi("abc");
        bean.synchronizeOrganization(roIi);
    }
    
   @Test
    public void synchronizeOrganizationNullify()  throws Exception {
        Ii roIi = IiConverter.convertToPoOrganizationIi("abc");
        roIi.setNullFlavor(NullFlavor.NA);
        bean.synchronizeOrganization(roIi);
    }

   @Test
   public void testOrgNullification()  throws Exception {
       Criteria criteria = session.createCriteria(Organization.class);
       criteria.add(Expression.eq("identifier", "22"));
       assertTrue("new pa org should not exist yet", criteria.list().size() == 0);
       
       RegistryUser ru = new RegistryUser();
       ru.setAffiliatedOrganizationId(2L);
       ru.setAffiliateOrg("isNullified");
       
       Long ruId = (Long)session.save(ru);
       session.flush();
       
       Ii roIi = IiConverter.convertToPoOrganizationIi("2");
       bean.synchronizeOrganization(roIi);

       session.flush();

       RegistryUser dbRu = (RegistryUser) session.load(RegistryUser.class, ruId);

       assertTrue(dbRu.getAffiliatedOrganizationId().equals(22L));
       assertTrue("Org Name was not updated", dbRu.getAffiliateOrg().equals("OrgName"));
   
       criteria = session.createCriteria(Organization.class);
       criteria.add(Expression.eq("identifier", "22"));
       assertTrue("new pa org should exist", criteria.list().size() == 1);
              
   }

    private void createTestData() {

        Organization org  = OrganizationTest.createOrganizationObj();
        org.setIdentifier("2");
        org.setName("Will be nullified Org");
        session.save(org);
        session.flush();
        
        
//        Organization o  = OrganizationTest.createOrganizationObj();
//        TestSchema.addUpdObject(o);
//        assertNotNull(o.getId());
//
//        HealthCareFacility hcf = HealthCareFacilityTest.createHealthCareFacilityObj(o);
//        TestSchema.addUpdObject(hcf);
////        HealthCareFacility savedhc = (HealthCareFacility) session.load(HealthCareFacility.class, hcf.getId());
////        createdHcfId = savedhc.getId();
//
//        StudyProtocol sp = StudyProtocolTest.createStudyProtocolObj();
//        TestSchema.addUpdObject(sp);
//        assertNotNull(sp.getId());
//
//        StudySite create = StudySiteTest.createStudySiteObj(sp, hcf) ;
//        //create.setStatusCode(StatusCode.PENDING);
//        TestSchema.addUpdObject(create);
//        assertNotNull(create.getId());
////        StudySite saved = (StudySite) session.load(StudySite.class, create.getId());
////        createdSpsId = saved.getId();
    }

    


}
