package gov.nih.nci.po.service.external;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.coppa.iso.IdentifierReliability;
import gov.nih.nci.coppa.iso.IdentifierScope;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.Country;
import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.HealthCareFacility;
import gov.nih.nci.po.data.bo.IdentifiedOrganization;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.ResearchOrganization;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.data.convert.IdConverter;
import gov.nih.nci.po.service.AbstractCuratableServiceBean;
import gov.nih.nci.po.service.AbstractServiceBeanTest;
import gov.nih.nci.po.service.AnnotatedBeanSearchCriteria;
import gov.nih.nci.po.service.EjbTestHelper;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.service.HealthCareFacilityServiceBean;
import gov.nih.nci.po.service.HealthCareFacilityServiceLocal;
import gov.nih.nci.po.service.IdentifiedOrganizationServiceBean;
import gov.nih.nci.po.service.MessageProducerTest;
import gov.nih.nci.po.service.OrganizationCRServiceBean;
import gov.nih.nci.po.service.OrganizationServiceBean;
import gov.nih.nci.po.service.ResearchOrganizationServiceBean;
import gov.nih.nci.po.service.correlation.HealthCareFacilityServiceTest;
import gov.nih.nci.po.service.correlation.ResearchOrganizationServiceTest;
import gov.nih.nci.po.service.external.stubs.CTEPOrgServiceStubBuilder;
import gov.nih.nci.po.service.external.stubs.CTEPOrganizationServiceStub;
import gov.nih.nci.po.util.PoHibernateUtil;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jms.JMSException;
import javax.naming.Context;

import org.junit.Before;
import org.junit.Test;

import com.fiveamsolutions.nci.commons.search.SearchCriteria;

public class CtepOrganizationImporterTest extends AbstractServiceBeanTest {
    private CtepOrganizationImporter importer = null;
    private OrganizationServiceBean oSvc;
    private IdentifiedOrganizationServiceBean ioSvc;
    private HealthCareFacilityServiceLocal hcfSvc;
    private Organization ctep;
    private OrganizationCRServiceBean oCRSvc;
    private ResearchOrganizationServiceBean roSvc;

    @Before
    public void setup() throws Exception {
        oSvc = EjbTestHelper.getOrganizationServiceBean();
        oCRSvc = EjbTestHelper.getOrganizationCRServiceBean();
        ioSvc = EjbTestHelper.getIdentifiedOrganizationServiceBean();
        hcfSvc = EjbTestHelper.getHealthCareFacilityServiceBean();
        roSvc = (ResearchOrganizationServiceBean) EjbTestHelper.getResearchOrganizationServiceBean();

        importer = new CtepOrganizationImporter(null) {
            @Override
            protected void initCtepServices(Context ctepContext) {
            }
        };

        createCTEPOrg();
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
        io.getAssignedIdentifier().setRoot("Cancer Therapy Evaluation Program Organization Identifier");
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

    @Test
    public void areEmailListsEqual() {
        List<Email> list1 = new ArrayList<Email>();
        List<Email> list2 = new ArrayList<Email>();
        assertTrue(CtepEntityImporter.areEmailListsEqual(list1, list2));

        Email email1 = new Email("1@example.com");
        Email email1copy = new Email("1@example.com");
        Email email2 = new Email("2@example.com");
        Email email2copy = new Email("2@example.com");

        list1.add(email1);
        assertFalse(CtepEntityImporter.areEmailListsEqual(list1, list2));

        list2.add(email2);
        assertFalse(CtepEntityImporter.areEmailListsEqual(list1, list2));

        list1.add(email2copy);
        list2.add(email1copy);
        assertTrue(CtepEntityImporter.areEmailListsEqual(list1, list2));
    }

    /**
     * Verifies Org & HCF is set to PENDING upon create, https://jira.5amsolutions.com/browse/PO-1243
     * 
     * @throws Exception
     */
    @Test
    public void testOrgAndAllHCFRolesAreSetToPendingOnCreate() throws Exception {
        helperOrgAndAllHCFRolesAreSetToPendingOnCreate();
    }

    /**
     * Verifies Org & RO is set to PENDING upon create, https://jira.5amsolutions.com/browse/PO-1243
     * 
     * @throws Exception
     */
    @Test
    public void testOrgAndAllRORolesAreSetToPendingOnCreate() throws Exception {
        helperOrgAndAllRORolesAreSetToPendingOnCreate();
    }

    private Organization helperOrgAndAllRORolesAreSetToPendingOnCreate() throws Exception, JMSException,
            EntityValidationException {
        // feed the proper CTEP service stub into our importer
        CTEPOrganizationServiceStub service = CTEPOrgServiceStubBuilder.INSTANCE.buildCreateROStub();
        return helperOrgAndAllRolesAreSetToPendingOnCreate(service);
    }

    private Organization helperOrgAndAllHCFRolesAreSetToPendingOnCreate() throws Exception, JMSException,
            EntityValidationException {
        return helperOrgAndAllRolesAreSetToPendingOnCreate(CTEPOrgServiceStubBuilder.INSTANCE.buildCreateHCFStub());
    }

    private Organization helperOrgAndAllRolesAreSetToPendingOnCreate(CTEPOrganizationServiceStub service)
            throws Exception, JMSException, EntityValidationException {
        importer.setCtepOrgService(service);
        OrganizationDTO org = service.getOrg();
        assertNotNull(org);
        assertNotNull(service.getOrgId());

        Organization importedOrg = importer.importOrganization(org.getIdentifier());
        assertNotNull(importedOrg);

        MessageProducerTest.assertMessageCreated(importedOrg, (OrganizationServiceBean) importer.getOrgService(), true);

        assertNull("the ctep organization's id is erased before converting to a Organization (BO)", service.getOrg()
                .getIdentifier());
        assertNotNull(service.getOrgId());
        IdentifiedOrganization io = getByCtepOrgId(service.getOrgId());
        assertEquals(ctep.getId(), io.getScoper().getId());
        MessageProducerTest.assertMessageCreated(io, (IdentifiedOrganizationServiceBean) importer
                .getIdentifiedOrgService(), true);

        Organization persistedOrg = io.getPlayer();
        assertNotNull(persistedOrg);
        assertEquals(EntityStatus.PENDING, persistedOrg.getStatusCode());

        if (service.getHcf() != null) {
            List<HealthCareFacility> hcfs = hcfSvc.getByPlayerIds(new Long[] { importedOrg.getId() });
            assertEquals(1, hcfs.size());

            HealthCareFacility persistedHCF = hcfs.get(0);
            assertEquals(RoleStatus.PENDING, persistedHCF.getStatus());
            MessageProducerTest.assertMessageCreated(persistedHCF, (HealthCareFacilityServiceBean) importer
                    .getHCFService(), true);
        }
        if (service.getRo() != null) {
            List<ResearchOrganization> ros = roSvc.getByPlayerIds(new Long[] { importedOrg.getId() });
            assertEquals(1, ros.size());

            ResearchOrganization persistedRO = ros.get(0);
            assertEquals(RoleStatus.PENDING, persistedRO.getStatus());
            MessageProducerTest.assertMessageCreated(persistedRO, (ResearchOrganizationServiceBean) importer
                    .getROService(), true);
        }
        return importedOrg;
    }

    /**
     * If the Organization is ACTIVE, the changes to the organization should be created as a change request on the org,
     * allowing the curator to curate them like normal.
     * 
     * @see https://jira.5amsolutions.com/browse/PO-1244
     * 
     * @throws Exception
     */
    @Test
    public void testOrgAndAllHCFRolesAreLeftPendingIfPendingInitiallyOnUpdateAndChangesAreMadeDirectlyToOrganization()
            throws Exception {
        
        Organization importedOrg = helperOrgAndAllHCFRolesAreSetToPendingOnCreate();
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();

        clearMessages();
        // get the II for the HCF that was added during the 1st pass (creation)
        Ii hcfPOID = getHCFII(importedOrg);

        CTEPOrganizationServiceStub service = CTEPOrgServiceStubBuilder.INSTANCE
                .buildCreateHCFWithNameUpdateStub(hcfPOID);
        
        helperOrgAndAllHCFRolesAreLeftPendingIfPendingInitiallyOnUpdateAndChangesAreMadeDirectlyToOrganization(
                importedOrg, service);
    }

    /**
     * If the Organization is ACTIVE, the changes to the organization should be created as a change request on the org,
     * allowing the curator to curate them like normal.
     * 
     * @see https://jira.5amsolutions.com/browse/PO-1244
     * 
     * @throws Exception
     */
    @Test
    public void testOrgAndAllRORolesAreLeftPendingIfPendingInitiallyOnUpdateAndChangesAreMadeDirectlyToOrganization()
            throws Exception {
        Organization importedOrg = helperOrgAndAllRORolesAreSetToPendingOnCreate();
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();

        clearMessages();
        // get the II for the HCF that was added during the 1st pass (creation)
        Ii roPOID = getROII(importedOrg);

        CTEPOrganizationServiceStub service = CTEPOrgServiceStubBuilder.INSTANCE.buildCreateROWithNameUpdateStub(roPOID);
        
        helperOrgAndAllHCFRolesAreLeftPendingIfPendingInitiallyOnUpdateAndChangesAreMadeDirectlyToOrganization(importedOrg, service);
    }
    
    private void helperOrgAndAllHCFRolesAreLeftPendingIfPendingInitiallyOnUpdateAndChangesAreMadeDirectlyToOrganization(
            Organization importedOrg, CTEPOrganizationServiceStub service) throws JMSException,
            EntityValidationException {
        importer.setCtepOrgService(service);
        OrganizationDTO org = service.getOrg();
        assertNotNull(org);
        assertNotNull(service.getOrgId());
        Organization updatedOrg = importer.importOrganization(org.getIdentifier());
        assertNotNull(updatedOrg);

        MessageProducerTest.assertMessageCreated(updatedOrg, (OrganizationServiceBean) importer.getOrgService(), false);

        IdentifiedOrganization io = getByCtepOrgId(service.getOrgId());
        assertEquals(ctep.getId(), io.getScoper().getId());
        MessageProducerTest.assertMessageCreated(io, (IdentifiedOrganizationServiceBean) importer
                .getIdentifiedOrgService(), false);

        Organization persistedOrg = io.getPlayer();
        assertNotNull(persistedOrg);
        assertEquals(EntityStatus.PENDING, persistedOrg.getStatusCode());

        if (service.getHcf() != null) {
            List<HealthCareFacility> hcfs = hcfSvc.getByPlayerIds(new Long[] { importedOrg.getId() });
            assertEquals(1, hcfs.size());

            HealthCareFacility persistedHCF = hcfs.get(0);
            assertEquals(RoleStatus.PENDING, persistedHCF.getStatus());
            MessageProducerTest.assertMessageCreated(persistedHCF, (HealthCareFacilityServiceBean) importer
                    .getHCFService(), false);
        }
        if (service.getRo() != null) {
            List<ResearchOrganization> ros = roSvc.getByPlayerIds(new Long[] { importedOrg.getId() });
            assertEquals(1, ros.size());

            ResearchOrganization persistedRO = ros.get(0);
            assertEquals(RoleStatus.PENDING, persistedRO.getStatus());
            MessageProducerTest.assertMessageCreated(persistedRO, (ResearchOrganizationServiceBean) importer
                    .getROService(), false);
        }
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
        HealthCareFacility persistedHCF = hcfs.get(0);
        Ii hcfPOID = new IdConverter.HealthCareFacilityIdConverter().convertToIi(persistedHCF.getId());

        MessageProducerTest.assertMessageCreated(persistedHCF,
                (HealthCareFacilityServiceBean) importer.getHCFService(), true);

        // try an update w/ no difference in data again. now no status changes should occur
        service = CTEPOrgServiceStubBuilder.INSTANCE.buildCreateHCFWithNoUpdatesStub(hcfPOID);
        importer.setCtepOrgService(service);
        org = service.getOrg();
        importedOrg = importer.importOrganization(org.getIdentifier());
        MessageProducerTest.assertNoMessageCreated(importedOrg, (OrganizationServiceBean) importer.getOrgService());
        MessageProducerTest.assertNoMessageCreated(persistedHCF, (HealthCareFacilityServiceBean) importer
                .getHCFService());

        // redo import should cause an update message to go out on HCF
        service = CTEPOrgServiceStubBuilder.INSTANCE.buildCreateHCFWithAddedAddressStub(hcfPOID, "mystreet", "mycity",
                "mystate", "mypostal", getDefaultCountry());
        importer.setCtepOrgService(service);
        org = service.getOrg();
        // MessageProducerTest.clearMessages((HealthCareFacilityServiceBean) importer.getHcfService());
        importedOrg = importer.importOrganization(org.getIdentifier());
        MessageProducerTest.assertNoMessageCreated(importedOrg, (OrganizationServiceBean) importer.getOrgService());
        MessageProducerTest.assertMessageCreated(persistedHCF,
                (HealthCareFacilityServiceBean) importer.getHCFService(), false);
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
        ResearchOrganization persistedRO = ros.get(0);
        Ii roPOID = new IdConverter.ResearchOrganizationIdConverter().convertToIi(persistedRO.getId());
        
        MessageProducerTest.assertMessageCreated(persistedRO,
                (ResearchOrganizationServiceBean) importer.getROService(), true);
        
        // try an update w/ no difference in data again. now no status changes should occur
        service = CTEPOrgServiceStubBuilder.INSTANCE.buildCreateROWithNoUpdatesStub(roPOID);
        importer.setCtepOrgService(service);
        org = service.getOrg();
        importedOrg = importer.importOrganization(org.getIdentifier());
        MessageProducerTest.assertNoMessageCreated(importedOrg, (OrganizationServiceBean) importer.getOrgService());
        MessageProducerTest.assertNoMessageCreated(persistedRO, (ResearchOrganizationServiceBean) importer
                .getROService());
        
        // redo import should cause an update message to go out on HCF
        service = CTEPOrgServiceStubBuilder.INSTANCE.buildCreateROWithAddedAddressStub(roPOID, "mystreet", "mycity",
                "mystate", "mypostal", getDefaultCountry());
        importer.setCtepOrgService(service);
        org = service.getOrg();
        // MessageProducerTest.clearMessages((HealthCareFacilityServiceBean) importer.getHcfService());
        importedOrg = importer.importOrganization(org.getIdentifier());
        MessageProducerTest.assertNoMessageCreated(importedOrg, (OrganizationServiceBean) importer.getOrgService());
        MessageProducerTest.assertMessageCreated(persistedRO,
                (ResearchOrganizationServiceBean) importer.getROService(), false);
    }

    private Ii getHCFII(Organization importedOrg) {
        List<HealthCareFacility> hcfs = hcfSvc.getByPlayerIds(new Long[] { importedOrg.getId() });
        assertEquals(1, hcfs.size());
        HealthCareFacility persistedHCF = hcfs.get(0);
        Ii hcfPOID = new IdConverter.HealthCareFacilityIdConverter().convertToIi(hcfs.get(0).getId());
        return hcfPOID;
    }

    private Ii getROII(Organization importedOrg) {
        List<ResearchOrganization> ros = roSvc.getByPlayerIds(new Long[] { importedOrg.getId() });
        assertEquals(1, ros.size());
        ResearchOrganization persistedRO = ros.get(0);
        Ii hcfPOID = new IdConverter.ResearchOrganizationIdConverter().convertToIi(ros.get(0).getId());
        return hcfPOID;
    }

    private void clearMessages() {
        MessageProducerTest.clearMessages((OrganizationServiceBean) importer.getOrgService());
        MessageProducerTest.clearMessages((IdentifiedOrganizationServiceBean) importer.getIdentifiedOrgService());
        MessageProducerTest.clearMessages((HealthCareFacilityServiceBean) importer.getHCFService());
        MessageProducerTest.clearMessages((ResearchOrganizationServiceBean) importer.getROService());
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
        Organization org1 = ro.getPlayer();
        assertNotNull(org1);
        assertEquals(RoleStatus.PENDING, ro.getStatus());
        assertTrue(ro.isCtepOwned());

        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();

        // 2
        Organization org2 = helperOrgAndAllHCFRolesAreSetToPendingOnCreate();
        HealthCareFacility hcf = (HealthCareFacility) PoHibernateUtil.getCurrentSession().createCriteria(
                HealthCareFacility.class).uniqueResult();
        assertEquals(RoleStatus.PENDING, hcf.getStatus());
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
        Organization org1 = hcf.getPlayer();
        assertNotNull(org1);
        assertEquals(RoleStatus.PENDING, hcf.getStatus());
        assertTrue(hcf.isCtepOwned());
        
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();
        
        // 2
        Organization org2 = helperOrgAndAllRORolesAreSetToPendingOnCreate();
        ResearchOrganization ro = (ResearchOrganization) PoHibernateUtil.getCurrentSession().createCriteria(
                ResearchOrganization.class).uniqueResult();
        assertEquals(RoleStatus.PENDING, ro.getStatus());
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
}
