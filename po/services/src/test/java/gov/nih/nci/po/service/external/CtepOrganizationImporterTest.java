package gov.nih.nci.po.service.external;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import gov.nih.nci.common.exceptions.CTEPEntException;
import gov.nih.nci.iso21090.IdentifierReliability;
import gov.nih.nci.iso21090.IdentifierScope;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.Alias;
import gov.nih.nci.po.data.bo.Country;
import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.HealthCareFacility;
import gov.nih.nci.po.data.bo.IdentifiedOrganization;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.ResearchOrganization;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.data.convert.AdConverter;
import gov.nih.nci.po.data.convert.IdConverter;
import gov.nih.nci.po.data.convert.util.AddressConverterUtil;
import gov.nih.nci.po.service.AbstractCuratableServiceBean;
import gov.nih.nci.po.service.AbstractServiceBeanTest;
import gov.nih.nci.po.service.AnnotatedBeanSearchCriteria;
import gov.nih.nci.po.service.EjbTestHelper;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.service.HealthCareFacilityServiceBean;
import gov.nih.nci.po.service.HealthCareFacilityServiceLocal;
import gov.nih.nci.po.service.IdentifiedOrganizationServiceBean;
import gov.nih.nci.po.service.MessageProducerTest;
import gov.nih.nci.po.service.OrganizationServiceBean;
import gov.nih.nci.po.service.ResearchOrganizationServiceBean;
import gov.nih.nci.po.service.external.CtepMessageBean.OrganizationType;
import gov.nih.nci.po.service.external.stubs.CTEPOrgServiceStubBuilder;
import gov.nih.nci.po.service.external.stubs.CTEPOrganizationServiceStub;
import gov.nih.nci.po.util.PoHibernateUtil;
import gov.nih.nci.po.util.PoServiceUtil;
import gov.nih.nci.security.SecurityServiceProvider;
import gov.nih.nci.security.UserProvisioningManager;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.security.exceptions.CSException;
import gov.nih.nci.services.correlation.HealthCareFacilityServiceTest;
import gov.nih.nci.services.correlation.ResearchOrganizationServiceTest;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jms.JMSException;
import javax.naming.Context;

import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.criterion.Restrictions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import static org.mockito.Matchers.isA;

import com.fiveamsolutions.nci.commons.search.SearchCriteria;

@RunWith(PowerMockRunner.class)
@PrepareForTest( { SecurityServiceProvider.class, PoServiceUtil.class })
public class CtepOrganizationImporterTest extends AbstractServiceBeanTest {
    /**
     * Root used by CTEP to identify organizations
     */
    public static final String CTEP_ORG_ROOT = "2.16.840.1.113883.3.26.6.2";
    private static final String CTEP_LOGIN_NAME = "ctepecm";
    private CtepOrganizationImporter importer = null;
    private OrganizationServiceBean oSvc;
    private IdentifiedOrganizationServiceBean ioSvc;
    private HealthCareFacilityServiceLocal hcfSvc;
    private Organization ctep;
    private ResearchOrganizationServiceBean roSvc;

    @Before
    public void setup() throws Exception {
        mockStatic(PoServiceUtil.class);
        PowerMockito.when(PoServiceUtil.isEntityEditableByUser(anyString(), isA(User.class), isA(User.class))).thenReturn(true);
        
        oSvc = EjbTestHelper.getOrganizationServiceBean();
        ioSvc = EjbTestHelper.getIdentifiedOrganizationServiceBean();
        hcfSvc = EjbTestHelper.getHealthCareFacilityServiceBean();
        roSvc = (ResearchOrganizationServiceBean) EjbTestHelper.getResearchOrganizationServiceBean();

        importer = new CtepOrganizationImporter(null) {
            @Override
            protected void initCtepServices(Context ctepContext) {
            }
        };

        createCTEPOrg();
        createCTEPUser();
    }

    private void createCTEPUser() {
        User ctepUser = new User();
        ctepUser.setFirstName("CTEP");
        ctepUser.setLastName("ECM");
        ctepUser.setLoginName(CTEP_LOGIN_NAME);
        ctepUser.setUpdateDate(new Date());
        PoHibernateUtil.getCurrentSession().save(ctepUser);
    }

    /**
     * Used to persist the default Organization that represents CTEP and has an IdentifiedOrganization referring to
     * itself
     *
     * @throws Exception
     */
    private void createCTEPOrg() throws Exception {

        ctep = new Organization();
        ctep.setName("Cancer Therapy Evaluation Program");
        ctep.setStatusCode(EntityStatus.ACTIVE);
        ctep.setPostalAddress(new Address());
        ctep.getPostalAddress().setStreetAddressLine("bogus");
        ctep.getPostalAddress().setCityOrMunicipality("city");
        ctep.getPostalAddress().setStateOrProvince("VA");
        ctep.getPostalAddress().setPostalCode("12345");
        ctep.getPostalAddress().setCountry(getDefaultCountry());
        ctep.getEmail().add(new Email("abc@example.com"));
        oSvc.curate(ctep);
        MessageProducerTest.assertMessageCreated(ctep, oSvc, true);
        MessageProducerTest.clearMessages(oSvc);

        IdentifiedOrganization io = new IdentifiedOrganization();
        io.setStatus(RoleStatus.ACTIVE);
        io.setStatusDate(new Date());
        io.setPlayer(ctep);
        io.setScoper(ctep);
        io.setAssignedIdentifier(new Ii());
        io.getAssignedIdentifier().setDisplayable(true);
        io.getAssignedIdentifier().setExtension("CTEP");
        io.getAssignedIdentifier().setIdentifierName("CTEP ID");
        io.getAssignedIdentifier().setReliability(IdentifierReliability.ISS);
        io.getAssignedIdentifier().setRoot(CTEP_ORG_ROOT);
        io.getAssignedIdentifier().setScope(IdentifierScope.OBJ);

        ioSvc.curate(io);
        MessageProducerTest.assertMessageCreated(io, ioSvc, true);
        MessageProducerTest.clearMessages(ioSvc);
    }

    private IdentifiedOrganization getByCtepOrgId(Ii ctepOrgId) {
        IdentifiedOrganization identifiedOrg = new IdentifiedOrganization();
        identifiedOrg.setAssignedIdentifier(ctepOrgId);
        SearchCriteria<IdentifiedOrganization> sc = new AnnotatedBeanSearchCriteria<IdentifiedOrganization>(
                identifiedOrg);
        List<IdentifiedOrganization> identifiedOrgs = ioSvc.search(sc);
        if (identifiedOrgs.isEmpty()) {
            return null;
        }
        return identifiedOrgs.get(0);
    }

    /**
     * Verifies Org & HCF is set to PENDING upon create, https://jira.5amsolutions.com/browse/PO-1243
     *
     * @throws Exception
     */
    @Test
    public void testOrgAndAllHCFRolesAreSetToActiveOnCreate() throws Exception {
        helperOrgAndAllHCFRolesAreSetToActiveOnCreate();
    }

    /**
     * Verifies Org & RO is set to PENDING upon create, https://jira.5amsolutions.com/browse/PO-1243
     *
     * @throws Exception
     */
    @Test
    public void testOrgAndAllRORolesAreSetToActiveOnCreate() throws Exception {
        helperOrgAndAllRORolesAreSetToActiveOnCreate();
    }

    private Organization helperOrgAndAllRORolesAreSetToActiveOnCreate() throws Exception, JMSException,
            EntityValidationException {
        // feed the proper CTEP service stub into our importer
        CTEPOrganizationServiceStub service = CTEPOrgServiceStubBuilder.INSTANCE.buildCreateROStub();
        return helperOrgAndAllRolesAreSetToActiveOnCreate(service);
    }

    private Organization helperOrgAndAllHCFRolesAreSetToActiveOnCreate() throws Exception, JMSException,
            EntityValidationException {
        return helperOrgAndAllRolesAreSetToActiveOnCreate(CTEPOrgServiceStubBuilder.INSTANCE.buildCreateHCFStub());
    }

    private Organization helperOrgAndAllRolesAreSetToActiveOnCreate(CTEPOrganizationServiceStub service)
            throws Exception, JMSException, EntityValidationException {
        importer.setCtepOrgService(service);
        OrganizationDTO org = service.getOrg();
        assertNotNull(org);
        assertNotNull(service.getOrgId());

        Organization importedOrg = importer.importOrganization(org.getIdentifier());
        assertNotNull(importedOrg);

        MessageProducerTest.assertMessageCreated(importedOrg, (OrganizationServiceBean) importer.getOrgService(), true);

        assertNull("the ctep localOrganization's id is erased before converting to a Organization (BO)", service.getOrg()
                .getIdentifier());
        assertNotNull(service.getOrgId());
        IdentifiedOrganization io = getByCtepOrgId(service.getOrgId());
        assertEquals(ctep.getId(), io.getScoper().getId());
        assertEquals(IdentifierReliability.VRF, io.getAssignedIdentifier().getReliability());
        MessageProducerTest.assertMessageCreated(io, (IdentifiedOrganizationServiceBean) importer
                .getIdentifiedOrgService(), true);

        Organization persistedOrg = io.getPlayer();
        assertNotNull(persistedOrg);
        assertEquals(EntityStatus.ACTIVE, persistedOrg.getStatusCode());

        if (service.getHcf() != null) {
            List<HealthCareFacility> hcfs = hcfSvc.getByPlayerIds(new Long[] { importedOrg.getId() });
            assertEquals(1, hcfs.size());
            HealthCareFacility persistedHCF = hcfs.get(0);
            assertEquals(IdentifierReliability.VRF,
                         (persistedHCF.getOtherIdentifiers().iterator().next()).getReliability());
            assertEquals(RoleStatus.ACTIVE, persistedHCF.getStatus());
            MessageProducerTest.assertMessageCreated(persistedHCF, (HealthCareFacilityServiceBean) importer
                    .getHCFService(), true);
        }
        if (service.getRo() != null) {
            List<ResearchOrganization> ros = roSvc.getByPlayerIds(new Long[] { importedOrg.getId() });
            assertEquals(1, ros.size());

            ResearchOrganization persistedRO = ros.get(0);
            assertEquals(IdentifierReliability.VRF,
                         (persistedRO.getOtherIdentifiers().iterator().next()).getReliability());
            assertEquals(RoleStatus.ACTIVE, persistedRO.getStatus());
            MessageProducerTest.assertMessageCreated(persistedRO, (ResearchOrganizationServiceBean) importer
                    .getROService(), true);
        }
        return importedOrg;
    }

    /**
     * Verifies import and update w/ address change.
     *
     * @throws Exception
     */
    @Test
    public void testHCFImportAndUpdateWithRoleAddressChange() throws Exception {
        // feed the proper CTEP service stub into our importer
        CTEPOrganizationServiceStub service = CTEPOrgServiceStubBuilder.INSTANCE.buildCreateHCFStub();
        importer.setCtepOrgService(service);
        OrganizationDTO org = service.getOrg();

        // create the org.
        Organization importedOrg = importer.importOrganization(org.getIdentifier());
        MessageProducerTest.assertMessageCreated(importedOrg, (OrganizationServiceBean) importer.getOrgService(), true);

        List<HealthCareFacility> hcfs = hcfSvc.getByPlayerIds(new Long[] { importedOrg.getId() });
        assertEquals(1, hcfs.size());
        HealthCareFacility persistedHCF = hcfs.get(0);
        assertEquals(IdentifierReliability.VRF, (persistedHCF.getOtherIdentifiers().iterator().next()).getReliability());
        Ii hcfPOID = new IdConverter.HealthCareFacilityIdConverter().convertToIi(persistedHCF.getId());

        MessageProducerTest.assertMessageCreated(persistedHCF,
                (HealthCareFacilityServiceBean) importer.getHCFService(), true);

        // try updating the exact same data again, without setting a PO ID on the imported HCF
        // a new HCF shouldn't be created, nor should a status change occur
        service = CTEPOrgServiceStubBuilder.INSTANCE.buildCreateHCFStub();
        importer.setCtepOrgService(service);
        org = service.getOrg();
        importedOrg = importer.importOrganization(org.getIdentifier());
        hcfs = hcfSvc.getByPlayerIds(new Long[] { importedOrg.getId() });
        assertEquals(1, hcfs.size());
        MessageProducerTest.assertNoMessageCreated(importedOrg, (OrganizationServiceBean) importer.getOrgService());
        MessageProducerTest.assertNoMessageCreated(persistedHCF, (HealthCareFacilityServiceBean) importer
                .getHCFService());

        // try an update w/ no difference in data again. now no status changes should occur
        service = CTEPOrgServiceStubBuilder.INSTANCE.buildCreateHCFWithNoUpdatesStub(hcfPOID);
        importer.setCtepOrgService(service);
        org = service.getOrg();
        importedOrg = importer.importOrganization(org.getIdentifier());
        hcfs = hcfSvc.getByPlayerIds(new Long[] { importedOrg.getId() });
        assertEquals(1, hcfs.size());
        assertEquals(IdentifierReliability.VRF, (hcfs.get(0).getOtherIdentifiers().iterator().next()).getReliability());
        MessageProducerTest.assertNoMessageCreated(importedOrg, (OrganizationServiceBean) importer.getOrgService());
        MessageProducerTest.assertNoMessageCreated(persistedHCF, (HealthCareFacilityServiceBean) importer
                .getHCFService());

        // redo import should cause an update message to go out on HCF
        service = CTEPOrgServiceStubBuilder.INSTANCE.buildCreateHCFWithNameUpdateStub(hcfPOID);
        importer.setCtepOrgService(service);
        org = service.getOrg();
        // MessageProducerTest.clearMessages((HealthCareFacilityServiceBean) importer.getHcfService());
        importedOrg = importer.importOrganization(org.getIdentifier());
        hcfs = hcfSvc.getByPlayerIds(new Long[] { importedOrg.getId() });
        assertEquals(1, hcfs.size());
        MessageProducerTest.assertNoMessageCreated(importedOrg, (OrganizationServiceBean) importer.getOrgService());
    }

    /**
     * Verifies import and update w/ address change.
     *
     * @throws Exception
     */
    @Test
    public void testROImportAndUpdateWithRoleAddressChange() throws Exception {

        // feed the proper CTEP service stub into our importer
        CTEPOrganizationServiceStub service = CTEPOrgServiceStubBuilder.INSTANCE.buildCreateROStub();
        importer.setCtepOrgService(service);
        OrganizationDTO org = service.getOrg();

        // create the org.
        Organization importedOrg = importer.importOrganization(org.getIdentifier());
        MessageProducerTest.assertMessageCreated(importedOrg, (OrganizationServiceBean) importer.getOrgService(), true);

        List<ResearchOrganization> ros = roSvc.getByPlayerIds(new Long[] { importedOrg.getId() });
        assertEquals(1, ros.size());
        ResearchOrganization persistedRO = ros.get(0);
        assertEquals(IdentifierReliability.VRF, (persistedRO.getOtherIdentifiers().iterator().next()).getReliability());
        Ii roPOID = new IdConverter.ResearchOrganizationIdConverter().convertToIi(persistedRO.getId());

        MessageProducerTest.assertMessageCreated(persistedRO,
                (ResearchOrganizationServiceBean) importer.getROService(), true);

        // try updating the exact same data again, without setting a PO ID on the imported RO
        // a new RO shouldn't be created, nor should a status change occur
        service = CTEPOrgServiceStubBuilder.INSTANCE.buildCreateROStub();
        importer.setCtepOrgService(service);
        org = service.getOrg();
        importedOrg = importer.importOrganization(org.getIdentifier());
        ros = roSvc.getByPlayerIds(new Long[] { importedOrg.getId() });
        assertEquals(1, ros.size());
        assertEquals(IdentifierReliability.VRF, (ros.get(0).getOtherIdentifiers().iterator().next()).getReliability());
        MessageProducerTest.assertNoMessageCreated(importedOrg, (OrganizationServiceBean) importer.getOrgService());
        MessageProducerTest.assertNoMessageCreated(persistedRO, (ResearchOrganizationServiceBean) importer
                .getROService());

        // try an update w/ no difference in data again. now no status changes should occur
        service = CTEPOrgServiceStubBuilder.INSTANCE.buildCreateROWithNoUpdatesStub(roPOID);
        importer.setCtepOrgService(service);
        org = service.getOrg();
        importedOrg = importer.importOrganization(org.getIdentifier());
        ros = roSvc.getByPlayerIds(new Long[] { importedOrg.getId() });
        assertEquals(1, ros.size());
        MessageProducerTest.assertNoMessageCreated(importedOrg, (OrganizationServiceBean) importer.getOrgService());
        MessageProducerTest.assertNoMessageCreated(persistedRO, (ResearchOrganizationServiceBean) importer
                .getROService());

        // redo import should cause an update message to go out on RO
        service = CTEPOrgServiceStubBuilder.INSTANCE.buildCreateROWithNameUpdateStub(roPOID);
        importer.setCtepOrgService(service);
        org = service.getOrg();
        // MessageProducerTest.clearMessages((HealthCareFacilityServiceBean) importer.getHcfService());
        importedOrg = importer.importOrganization(org.getIdentifier());
        ros = roSvc.getByPlayerIds(new Long[] { importedOrg.getId() });
        assertEquals(1, ros.size());
        MessageProducerTest.assertNoMessageCreated(importedOrg, (OrganizationServiceBean) importer.getOrgService());
    }

    @After
    public void clearMessages() {
        MessageProducerTest.clearMessages((OrganizationServiceBean) importer.getOrgService());
        MessageProducerTest.clearMessages((IdentifiedOrganizationServiceBean) importer.getIdentifiedOrgService());
        MessageProducerTest.clearMessages((HealthCareFacilityServiceBean) importer.getHCFService());
        MessageProducerTest.clearMessages((ResearchOrganizationServiceBean) importer.getROService());
        MessageProducerTest.clearMessages(getHealthCareFacilityServiceBean());
        MessageProducerTest.clearMessages(getResearchOrganizationServiceBean());
    }

    /**
     * CTEP Integration - Scenario 4 - a user creates an org w/ an ro in the po-web system. this ro will be one that is
     * going to be modified by ctep
     *
     * @throws Exception
     */
//    @Test
    // TODO:: Uncomment the testcase once code for RO is complete
    public void verifyScenario4() throws Exception {
        final Country c = getDefaultCountry();
        final ResearchOrganizationServiceBean getService = roSvc;
        ResearchOrganizationServiceTest test = new ResearchOrganizationServiceTest() {
            @Override
            public Country getDefaultCountry() {
                return c;
            }

            @Override
            protected AbstractCuratableServiceBean<ResearchOrganization> getService() {
                return getService;
            }
        };
        test.setUpData();
        test.setupType();
        test.testSimpleCreateCtepOwnedAndGet();
        ResearchOrganization ro = (ResearchOrganization) PoHibernateUtil.getCurrentSession().createCriteria(
                ResearchOrganization.class).uniqueResult();
        Organization org1 = ro.getPlayer();
        assertNotNull(org1);
        assertEquals(RoleStatus.PENDING, ro.getStatus());
        assertTrue(ro.isCtepOwned());
        assertTrue(CollectionUtils.isEmpty(ro.getPostalAddresses()));

        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();

        // see message sent to out on ro creation
        MessageProducerTest.assertMessageCreated(ro, getService, true);
        assertEquals(ro.getPlayer().getName(), ro.getName());
        assertNotNull(ro.getFundingMechanism());

        // a user creates an HCF in the system, this hcf will have a ctep id added to it
        HealthCareFacility hcf = new HealthCareFacility();
        hcf.setStatus(RoleStatus.PENDING);
        hcf.setName("NAME");
        Address addr = AdConverter.SimpleConverter.convertToAddress(AddressConverterUtil.create("streetAddressLine", "deliveryAddressLine", "cityOrMunicipality", "VA",
                "20110", "USA", "United States"));
        Set<Address> addrs = new HashSet<Address>();
        addrs.add(addr);
        hcf.setPostalAddresses(addrs);
        hcf.setPlayer(org1);

        Long hcfId = hcfSvc.create(hcf);

        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();

        // see message sent to out on hcf creation
        MessageProducerTest.assertMessageCreated(hcf, (HealthCareFacilityServiceBean) hcfSvc, true);

        // feed the proper CTEP service stub into our importer
        CTEPOrganizationServiceStub service = CTEPOrgServiceStubBuilder.INSTANCE.buildCreateROWithPlayerStub(org1.getId(), ro.getId(), hcfId);
        importer.setCtepOrgService(service);
        OrganizationDTO org = service.getOrg();

        // update the org.
        Organization importedOrg = importer.importOrganization(org.getIdentifier());
        importedOrg.getResearchOrganizations().size();
        importedOrg.getHealthCareFacilities().size();
        
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();
        Organization freshOrg = (Organization) PoHibernateUtil.getCurrentSession().get(Organization.class, org1.getId());
        IdentifiedOrganization idOrg = freshOrg.getIdentifiedOrganizations().iterator().next();
        Ii assignedId = idOrg.getAssignedIdentifier();
        assertEquals(CtepOrganizationImporter.CTEP_ORG_ROOT, assignedId.getRoot());

        // update the ro
        MessageProducerTest.assertMessageCreated(importedOrg.getResearchOrganizations().iterator().next(),
                (ResearchOrganizationServiceBean) importer.getROService(), false);
        ResearchOrganization freshRo = (ResearchOrganization) PoHibernateUtil.getCurrentSession().createCriteria(
                ResearchOrganization.class).uniqueResult();

        Alias alias = (Alias) CollectionUtils.find(
                                   freshRo.getAlias(),
                                   new BeanPropertyValueEqualsPredicate("value", "NAME")
                              );

        assertNotNull(alias);

        assertFalse(ro.getFundingMechanism().getCode().equals(freshRo.getFundingMechanism().getCode()));
        assertEquals("20110", freshRo.getPostalAddresses().iterator().next().getPostalCode());

        // only update here is the adding of the ctep id
        MessageProducerTest.assertMessageCreated(importedOrg.getHealthCareFacilities().iterator().next(),
                (HealthCareFacilityServiceBean) importer.getHCFService(), false);
        HealthCareFacility freshHcf = (HealthCareFacility) PoHibernateUtil.getCurrentSession().createCriteria(
                HealthCareFacility.class).uniqueResult();
        Ii ctepIdForHcf = freshHcf.getOtherIdentifiers().iterator().next();
        assertEquals(IdentifierReliability.VRF, ctepIdForHcf.getReliability());
        assertEquals(CtepOrganizationImporter.CTEP_ORG_ROOT, ctepIdForHcf.getRoot());
        assertEquals("20110", freshHcf.getPostalAddresses().iterator().next().getPostalCode());
    }

    /**
     * CTEP Integration - Scenario 9 - Illegal arg when multiple RO's are found with the
     * same ctep id.
     *
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @Test(expected = IllegalArgumentException.class)
    public void verifyScenario9ROIllegalArg() throws Exception {

        final Country c = getDefaultCountry();
        final ResearchOrganizationServiceBean getService = roSvc;
        ResearchOrganizationServiceTest test = new ResearchOrganizationServiceTest() {
            @Override
            public Country getDefaultCountry() {
                return c;
            }

            @Override
            protected AbstractCuratableServiceBean<ResearchOrganization> getService() {
                return getService;
            }
        };
        test.setUpData();
        test.setupType();
        // create org1 with ro1
        test.testSimpleCreateCtepOwnedAndGet();
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();

        // create org2 with ro2
        test.testSimpleCreateCtepOwnedAndGet();
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();

        List<ResearchOrganization> roList =
                PoHibernateUtil.getCurrentSession().createCriteria(ResearchOrganization.class).list();

        ResearchOrganization ro1 = roList.get(0);

        Ii ctepId = ro1.getOtherIdentifiers().iterator().next();


        // nullify the org through the ctep importer
        importer.nullifyCtepOrganization(ctepId, ctepId, OrganizationType.RESEARCHORGANIZATION);
    }

    /**
     * CTEP Integration - Scenario 9 - Illegal arg when no ROs are found
     * with ctep id.
     *
     * @throws Exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void verifyScenario9ROIllegalArgEmptySet() throws Exception {

        Ii ctepId = new Ii();
        ctepId.setRoot(CtepOrganizationImporter.CTEP_ORG_ROOT);
        ctepId.setExtension("SCN9ORG2");
        ctepId.setIdentifierName("unimportant value");

        Ii duplicateOfId = new Ii();
        duplicateOfId.setRoot(CtepOrganizationImporter.CTEP_ORG_ROOT);
        duplicateOfId.setExtension("SCN9DUP2");
        duplicateOfId.setIdentifierName("some name");


        // nullify the org through the ctep importer
        importer.nullifyCtepOrganization(ctepId, duplicateOfId, OrganizationType.RESEARCHORGANIZATION);
    }

    /**
     * CTEP Integration - Scenario 9 - for RO
     *
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @Test
    public void verifyScenario9RO() throws Exception {

        final Country c = getDefaultCountry();
        final ResearchOrganizationServiceBean getService = roSvc;
        ResearchOrganizationServiceTest test = new ResearchOrganizationServiceTest() {
            @Override
            public Country getDefaultCountry() {
                return c;
            }

            @Override
            protected AbstractCuratableServiceBean<ResearchOrganization> getService() {
                return getService;
            }
        };
        test.setUpData();
        test.setupType();
        // create org1 with ro1
        test.testSimpleCreateCtepOwnedAndGet();
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();

        // create org2 with ro2
        test.testSimpleCreateCtepOwnedAndGet();
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();

        List<ResearchOrganization> roList =
                PoHibernateUtil.getCurrentSession().createCriteria(ResearchOrganization.class).list();

        ResearchOrganization ro1 = roList.get(0);
        Organization org1 = ro1.getPlayer();
        assertNotNull(org1);
        assertEquals(RoleStatus.PENDING, ro1.getStatus());
        assertTrue(ro1.isCtepOwned());
        assertEquals(ro1.getPlayer().getName(), ro1.getName());
        assertNotNull(ro1.getFundingMechanism());

        ResearchOrganization ro2 = roList.get(1);
        Organization org2 = ro2.getPlayer();
        assertNotNull(org2);
        assertEquals(RoleStatus.PENDING, ro2.getStatus());
        assertTrue(ro2.isCtepOwned());
        assertEquals(ro2.getPlayer().getName(), ro2.getName());
        assertNotNull(ro2.getFundingMechanism());

        Ii ctepId = new Ii();
        ctepId.setExtension("SCN9ORG");
        ctepId.setRoot(CtepOrganizationImporter.CTEP_ORG_ROOT);
        ctepId.setIdentifierName("no name for ctep id");

        ro1.getOtherIdentifiers().clear();
        ro1.getOtherIdentifiers().add(ctepId);
        roSvc.curate(ro1);
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();

        Ii duplicateOfId = new Ii();
        duplicateOfId.setExtension("SCN9DUP");
        duplicateOfId.setRoot(CtepOrganizationImporter.CTEP_ORG_ROOT);
        duplicateOfId.setIdentifierName("no name for dup id");

        ro2.getOtherIdentifiers().clear();
        ro2.getOtherIdentifiers().add(duplicateOfId);
        roSvc.curate(ro2);
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();


        // nullify the org through the ctep importer
        importer.nullifyCtepOrganization(ctepId, duplicateOfId, OrganizationType.RESEARCHORGANIZATION);
        // update the ro
        MessageProducerTest.assertMessageCreated(ro1,
                (ResearchOrganizationServiceBean) importer.getROService(), false);
        ResearchOrganization freshRo = (ResearchOrganization) PoHibernateUtil.getCurrentSession().get(
                ResearchOrganization.class, ro1.getId());
        assertEquals(RoleStatus.NULLIFIED, freshRo.getStatus());
        assertEquals(ro2.getId(), freshRo.getDuplicateOf().getId());
    }

    /**
     * CTEP Integration - Scenario 9 - for HCF
     *
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @Test
    public void verifyScenario9HCF() throws Exception {

        final Country c = getDefaultCountry();
        final HealthCareFacilityServiceBean getService = (HealthCareFacilityServiceBean) hcfSvc;
        HealthCareFacilityServiceTest test = new HealthCareFacilityServiceTest() {
            @Override
            public Country getDefaultCountry() {
                return c;
            }

            @Override
            protected AbstractCuratableServiceBean<HealthCareFacility> getService() {
                return getService;
            }
        };
        test.setUpData();
        // create org1 with hcf1
        test.testSimpleCreateCtepOwnedAndGet();
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();

        // create org2 with hcf2
        test.testSimpleCreateCtepOwnedAndGet();
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();

        List<HealthCareFacility> hcfList =
                PoHibernateUtil.getCurrentSession().createCriteria(HealthCareFacility.class).list();

        HealthCareFacility hcf1 = hcfList.get(0);
        Organization org1 = hcf1.getPlayer();
        assertNotNull(org1);
        assertEquals(RoleStatus.PENDING, hcf1.getStatus());
        assertTrue(hcf1.isCtepOwned());
        assertEquals(hcf1.getPlayer().getName(), hcf1.getName());

        HealthCareFacility hcf2 = hcfList.get(1);
        Organization org2 = hcf2.getPlayer();
        assertNotNull(org2);
        assertEquals(RoleStatus.PENDING, hcf2.getStatus());
        assertTrue(hcf2.isCtepOwned());
        assertEquals(hcf2.getPlayer().getName(), hcf2.getName());

        Ii ctepId = new Ii();
        ctepId.setExtension("SCN9ORGHCF");
        ctepId.setRoot(CtepOrganizationImporter.CTEP_ORG_ROOT);
        ctepId.setIdentifierName("no name for ctep id");



        Ii duplicateOfId = new Ii();
        duplicateOfId.setExtension("SCN9DUPHCF");
        duplicateOfId.setRoot(CtepOrganizationImporter.CTEP_ORG_ROOT);
        duplicateOfId.setIdentifierName("no name for dup id");

        hcf1.getOtherIdentifiers().clear();
        hcf1.getOtherIdentifiers().add(ctepId);
        hcfSvc.curate(hcf1);
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();

        hcf2.getOtherIdentifiers().clear();
        hcf2.getOtherIdentifiers().add(duplicateOfId);
        hcfSvc.curate(hcf2);
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();

        // nullify the org through the ctep importer
        importer.nullifyCtepOrganization(ctepId, duplicateOfId, OrganizationType.HEALTHCAREFACILITY);
        // update the ro
        MessageProducerTest.assertMessageCreated(hcf1,
                (HealthCareFacilityServiceBean) importer.getHCFService(), false);
        HealthCareFacility freshRo = (HealthCareFacility) PoHibernateUtil.getCurrentSession().get(
                HealthCareFacility.class, hcf1.getId());
        assertEquals(RoleStatus.NULLIFIED, freshRo.getStatus());
        assertEquals(hcf2.getId(), freshRo.getDuplicateOf().getId());
    }


    /**
     * CTEP Integration - Scenario 5 - CTEP adds a new Structural Role on an existing Organization
     *
     * <pre>
     * 1. Org1 has a RO (exists already in system)
     * 2. Org2 w/ HCF is created via import (JMS messages sent out)
     * 3. Curator nullifies Org2 and specifies Org1 as duplicate thereby merging Org2's HCF into Org1 (JMS messages are sent out)
     * 4. Org1 remains with both an RO and HCF
     * </pre>
     *
     * @throws Exception
     */
    @Test
    public void verifyScenario5ForHCF() throws Exception {
        // 1
        final Country c = getDefaultCountry();
        final ResearchOrganizationServiceBean getService = roSvc;
        ResearchOrganizationServiceTest test = new ResearchOrganizationServiceTest() {
            @Override
            public Country getDefaultCountry() {
                return c;
            }

            @Override
            protected AbstractCuratableServiceBean<ResearchOrganization> getService() {
                return getService;
            }
        };
        test.setUpData();
        test.setupType();
        test.testSimpleCreateCtepOwnedAndGet();
        ResearchOrganization ro = (ResearchOrganization) PoHibernateUtil.getCurrentSession().createCriteria(
                ResearchOrganization.class).uniqueResult();

        ro.getPlayer().setStatusCode(EntityStatus.ACTIVE);
        oSvc.curate(ro.getPlayer());
        MessageProducerTest.assertMessageCreated(ro.getPlayer(), oSvc, false);

        ro.setStatus(RoleStatus.ACTIVE);
        roSvc.curate(ro);

        Organization org1 = ro.getPlayer();
        assertNotNull(org1);
        assertEquals(RoleStatus.ACTIVE, ro.getStatus());
        assertTrue(ro.isCtepOwned());

        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();

        // 2
        Organization org2 = helperOrgAndAllHCFRolesAreSetToActiveOnCreate();
        HealthCareFacility hcf = (HealthCareFacility) PoHibernateUtil.getCurrentSession().createCriteria(
                HealthCareFacility.class).uniqueResult();
        assertEquals(RoleStatus.ACTIVE, hcf.getStatus());
        assertTrue(hcf.isCtepOwned());
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();
        clearMessages();

        // 3
        ro = (ResearchOrganization) PoHibernateUtil.getCurrentSession().createCriteria(ResearchOrganization.class)
                .uniqueResult();
        hcf = (HealthCareFacility) PoHibernateUtil.getCurrentSession().createCriteria(HealthCareFacility.class)
                .uniqueResult();
        org2.setStatusCode(EntityStatus.NULLIFIED);
        org2.setDuplicateOf(ro.getPlayer());

        oSvc.curate(org2);
        MessageProducerTest.assertMessageCreated(org2, oSvc, false);
        HealthCareFacilityServiceBean hcfSBUsedDuringCurate = getHealthCareFacilityServiceBean();
        MessageProducerTest.assertMessageCreated(hcf, hcfSBUsedDuringCurate, false);

        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();

        // 4
        hcf = (HealthCareFacility) PoHibernateUtil.getCurrentSession().createCriteria(HealthCareFacility.class)
                .uniqueResult();
        ro = (ResearchOrganization) PoHibernateUtil.getCurrentSession().createCriteria(ResearchOrganization.class)
                .uniqueResult();
        assertEquals(ro.getPlayer(), hcf.getPlayer());
    }
    /**
     * CTEP Integration - Scenario 5 - CTEP adds a new Structural Role on an existing Organization
     *
     * <pre>
     * 1. Org1 has a HCF (exists already in system)
     * 2. Org2 w/ RO is created via import (JMS messages sent out)
     * 3. Curator nullifies Org2 and specifies Org1 as duplicate thereby merging Org2's RO into Org1 (JMS messages are sent out)
     * 4. Org1 remains with both an RO and HCF
     * </pre>
     *
     * @throws Exception
     */
    @Test
    public void verifyScenario5ForRO() throws Exception {
        // 1
        final Country c = getDefaultCountry();
        final HealthCareFacilityServiceBean getService = (HealthCareFacilityServiceBean) hcfSvc;
        HealthCareFacilityServiceTest test = new HealthCareFacilityServiceTest(){
            @Override
            public Country getDefaultCountry() {
                return c;
            }

            @Override
            protected AbstractCuratableServiceBean<HealthCareFacility> getService() {
                return getService;
            }
        };
        test.setUpData();
        test.testSimpleCreateCtepOwnedAndGet();
        HealthCareFacility hcf = (HealthCareFacility) PoHibernateUtil.getCurrentSession().createCriteria(
                HealthCareFacility.class).uniqueResult();

        hcf.getPlayer().setStatusCode(EntityStatus.ACTIVE);
        oSvc.curate(hcf.getPlayer());
        MessageProducerTest.assertMessageCreated(hcf.getPlayer(), oSvc, false);

        hcf.setStatus(RoleStatus.ACTIVE);
        hcfSvc.curate(hcf);

        Organization org1 = hcf.getPlayer();
        assertNotNull(org1);
        assertEquals(RoleStatus.ACTIVE, hcf.getStatus());
        assertTrue(hcf.isCtepOwned());

        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();

        // 2
        Organization org2 = helperOrgAndAllRORolesAreSetToActiveOnCreate();
        ResearchOrganization ro = (ResearchOrganization) PoHibernateUtil.getCurrentSession().createCriteria(
                ResearchOrganization.class).uniqueResult();
        assertEquals(RoleStatus.ACTIVE, ro.getStatus());
        assertTrue(ro.isCtepOwned());
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();
        clearMessages();

        // 3
        ro = (ResearchOrganization) PoHibernateUtil.getCurrentSession().createCriteria(ResearchOrganization.class)
        .uniqueResult();
        hcf = (HealthCareFacility) PoHibernateUtil.getCurrentSession().createCriteria(HealthCareFacility.class)
        .uniqueResult();
        org2.setStatusCode(EntityStatus.NULLIFIED);
        org2.setDuplicateOf(ro.getPlayer());

        oSvc.curate(org2);
        MessageProducerTest.assertMessageCreated(org2, oSvc, false);
        ResearchOrganizationServiceBean roSBUsedDuringCurate = getResearchOrganizationServiceBean();
        MessageProducerTest.assertMessageCreated(ro, roSBUsedDuringCurate, false);

        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();

        // 4
        hcf = (HealthCareFacility) PoHibernateUtil.getCurrentSession().createCriteria(HealthCareFacility.class)
        .uniqueResult();
        ro = (ResearchOrganization) PoHibernateUtil.getCurrentSession().createCriteria(ResearchOrganization.class)
        .uniqueResult();
        assertEquals(hcf.getPlayer(), hcf.getPlayer());
    }

    @Test
    public void badOrganizationAddress() throws Exception {
        CTEPOrganizationServiceStub service = CTEPOrgServiceStubBuilder.INSTANCE.buildCreateHCFWithBadOrgAddressStub();
        try {
            helperOrgAndAllRolesAreSetToActiveOnCreate(service);
            fail();
        }catch (CtepImportException e) {
            assertEquals("city missing", e.getShortMessage());
        }
    }

    @Test
    public void badHcfAddress() throws Exception {
        CTEPOrganizationServiceStub service = CTEPOrgServiceStubBuilder.INSTANCE.buildCreateHCFWithBadRoleAddressStub();
        try {
            helperOrgAndAllRolesAreSetToActiveOnCreate(service);
            fail();
        }catch (CtepImportException e) {
            assertEquals("city missing", e.getShortMessage());
        }
    }

    @Test
    public void badRoAddress() throws Exception {
        CTEPOrganizationServiceStub service = CTEPOrgServiceStubBuilder.INSTANCE.buildCreateROWithBadRoleAddressStub();
        try {
            helperOrgAndAllRolesAreSetToActiveOnCreate(service);
            fail();
        }catch (CtepImportException e) {
            assertEquals("city missing", e.getShortMessage());
        }
    }

    @Test
    public void ctepException() throws Exception {
        // create org with status pending
        CTEPOrganizationServiceStub service = CTEPOrgServiceStubBuilder.INSTANCE.buildCreateHCFStub();
        Ii ctepOrgIi = service.getOrgId();
        helperOrgAndAllRolesAreSetToActiveOnCreate(service);

        // throw exception, fail to set org inactive because status pending
        CTEPOrganizationServiceStub mockService = mock(CTEPOrganizationServiceStub.class);
        when(mockService.getOrganizationById(any(Ii.class))).thenThrow(new CTEPEntException());
        importer.setCtepOrgService(mockService);
        importer.importOrganization(ctepOrgIi);


        importer.setCtepOrgService(service);
        IdentifiedOrganization io = getByCtepOrgId(service.getOrgId());
        Organization persistedOrg = io.getPlayer();
        assertNotNull(persistedOrg);
        assertEquals(EntityStatus.INACTIVE, persistedOrg.getStatusCode());
    }


    @Test
    public void testCreatedBySetOnHealthCareFacilityCreation() throws Exception {
        mockSecurity();

        CTEPOrganizationServiceStub service = CTEPOrgServiceStubBuilder.INSTANCE.buildCreateHCFStub();
        importer.setCtepOrgService(service);
        OrganizationDTO org = service.getOrg();

        // create the org.
        Organization importedOrg = importer.importOrganization(org.getIdentifier());
        assertEquals(CTEP_LOGIN_NAME, importedOrg.getCreatedBy().getLoginName());

        List<HealthCareFacility> healthCareFacilities = hcfSvc.getByPlayerIds(new Long[]{importedOrg.getId()});
        assertEquals(1, healthCareFacilities.size());
        assertEquals(CTEP_LOGIN_NAME, healthCareFacilities.get(0).getCreatedBy().getLoginName());

    }

    @Test
    public void testCreatedBySetOnResearchOrganizationCreation() throws Exception {
        mockSecurity();
        CTEPOrganizationServiceStub service = CTEPOrgServiceStubBuilder.INSTANCE.buildCreateROStub();
        importer.setCtepOrgService(service);
        OrganizationDTO org = service.getOrg();

        // create the org.
        Organization importedOrg = importer.importOrganization(org.getIdentifier());
        assertEquals(CTEP_LOGIN_NAME, importedOrg.getCreatedBy().getLoginName());

        List<ResearchOrganization> researchOrganizations = roSvc.getByPlayerIds(new Long[]{importedOrg.getId()});
        assertEquals(1, researchOrganizations.size());
        assertEquals(CTEP_LOGIN_NAME, researchOrganizations.get(0).getCreatedBy().getLoginName());

    }


    private void mockSecurity() {
        UserProvisioningManager userProvisioningManager = mock(UserProvisioningManager.class);
        when(userProvisioningManager.getUser(anyString())).thenAnswer(
                new Answer<User>() {
                    @Override
                    public User answer(InvocationOnMock invocation) throws Throwable {
                        return (User) PoHibernateUtil.getCurrentSession().createCriteria(User.class)
                                .add(Restrictions.eq("loginName", invocation.getArguments()[0])).uniqueResult();
                    }
                }
        );


        mockStatic(SecurityServiceProvider.class);
        try {
            PowerMockito.when(SecurityServiceProvider.getUserProvisioningManager("po")).thenReturn(userProvisioningManager);
        } catch (CSException e) {
            throw new RuntimeException(e);
        }
    }




}
