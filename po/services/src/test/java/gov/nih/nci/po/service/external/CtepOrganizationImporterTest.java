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
import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.HealthCareFacility;
import gov.nih.nci.po.data.bo.IdentifiedOrganization;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.data.convert.IdConverter;
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

    @Before
    public void setup() throws Exception {
        oSvc = EjbTestHelper.getOrganizationServiceBean();
        oCRSvc = EjbTestHelper.getOrganizationCRServiceBean();
        ioSvc = EjbTestHelper.getIdentifiedOrganizationServiceBean();
        hcfSvc = EjbTestHelper.getHealthCareFacilityServiceBean();

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
    public void testOrgAndAllRolesAreSetToPendingOnCreate() throws Exception {
        helperOrgAndAllRolesAreSetToPendingOnCreate();
    }

    private Organization helperOrgAndAllRolesAreSetToPendingOnCreate() throws Exception, JMSException,
            EntityValidationException {
        // feed the proper CTEP service stub into our importer
        CTEPOrganizationServiceStub service = CTEPOrgServiceStubBuilder.INSTANCE.buildCreateHcfStub();
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

        List<HealthCareFacility> hcfs = hcfSvc.getByPlayerIds(new Long[] { importedOrg.getId() });
        assertEquals(1, hcfs.size());

        HealthCareFacility persistedHCF = hcfs.get(0);
        assertEquals(RoleStatus.PENDING, persistedHCF.getStatus());
        MessageProducerTest.assertMessageCreated(persistedHCF,
                (HealthCareFacilityServiceBean) importer.getHcfService(), true);
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
    public void testOrgAndAllRolesAreLeftPendingIfPendingInitiallyOnUpdateAndChangesAreMadeDirectlyToOrganization()
            throws Exception {
        Organization importedOrg = helperOrgAndAllRolesAreSetToPendingOnCreate();
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();

        clearMessages();
        //get the II for the HCF that was added during the 1st pass (creation)
        Ii hcfPOID = getHCFII(importedOrg);
        
        CTEPOrganizationServiceStub service = CTEPOrgServiceStubBuilder.INSTANCE.buildCreateHcfWithNameUpdateStub(hcfPOID);
        importer.setCtepOrgService(service);
        OrganizationDTO org = service.getOrg();
        assertNotNull(org);
        assertNotNull(service.getOrgId());
        Organization updatedOrg = importer.importOrganization(org.getIdentifier());
        assertNotNull(updatedOrg);

        MessageProducerTest
                .assertMessageCreated(updatedOrg, (OrganizationServiceBean) importer.getOrgService(), false);

        IdentifiedOrganization io = getByCtepOrgId(service.getOrgId());
        assertEquals(ctep.getId(), io.getScoper().getId());
        MessageProducerTest.assertMessageCreated(io, (IdentifiedOrganizationServiceBean) importer
                .getIdentifiedOrgService(), false);

        Organization persistedOrg = io.getPlayer();
        assertNotNull(persistedOrg);
        assertEquals(EntityStatus.PENDING, persistedOrg.getStatusCode());

        List<HealthCareFacility> hcfs = hcfSvc.getByPlayerIds(new Long[] { importedOrg.getId() });
        assertEquals(1, hcfs.size());

        HealthCareFacility persistedHCF = hcfs.get(0);
        assertEquals(RoleStatus.PENDING, persistedHCF.getStatus());
        MessageProducerTest.assertMessageCreated(persistedHCF,
                (HealthCareFacilityServiceBean) importer.getHcfService(), false);
    }

    
    /**
     * Verifies import and update w/ address change.
     * 
     * @throws Exception
     */
    @Test
    public void testHcfImportAndUpdateWithRoleAddressChange() throws Exception {
        
        // feed the proper CTEP service stub into our importer
        CTEPOrganizationServiceStub service = CTEPOrgServiceStubBuilder.INSTANCE.buildCreateHcfStub();
        importer.setCtepOrgService(service);
        OrganizationDTO org = service.getOrg();
                
        // create the org.
        Organization importedOrg = importer.importOrganization(org.getIdentifier());
        MessageProducerTest.assertMessageCreated(importedOrg, (OrganizationServiceBean) importer.getOrgService(), true);
       
        List<HealthCareFacility> hcfs = hcfSvc.getByPlayerIds(new Long[] { importedOrg.getId() });
        HealthCareFacility persistedHCF = hcfs.get(0); 
        Ii hcfPOID = new IdConverter.HealthCareFacilityIdConverter().convertToIi(persistedHCF.getId());
        
        MessageProducerTest.assertMessageCreated(persistedHCF, (HealthCareFacilityServiceBean) importer.getHcfService(), true);
        
        // try an update w/ no difference in data again. now no status changes should occur
        service = CTEPOrgServiceStubBuilder.INSTANCE.buildCreateHcfWithNoUpdatesStub(hcfPOID);
        importer.setCtepOrgService(service);
        org = service.getOrg();
        importedOrg = importer.importOrganization(org.getIdentifier());
        MessageProducerTest.assertNoMessageCreated(importedOrg, (OrganizationServiceBean) importer.getOrgService());
        MessageProducerTest.assertNoMessageCreated(persistedHCF, (HealthCareFacilityServiceBean) importer.getHcfService());
        
        // redo import should cause an update message to go out on HCF
        service = CTEPOrgServiceStubBuilder.INSTANCE.buildCreateHcfWithAddedAddressStub(hcfPOID, "mystreet", "mycity", "mystate",
                "mypostal", getDefaultCountry());
        importer.setCtepOrgService(service);
        org = service.getOrg();
        //MessageProducerTest.clearMessages((HealthCareFacilityServiceBean) importer.getHcfService());
        importedOrg = importer.importOrganization(org.getIdentifier());
        MessageProducerTest.assertNoMessageCreated(importedOrg, (OrganizationServiceBean) importer.getOrgService());
        MessageProducerTest.assertMessageCreated(persistedHCF, 
                (HealthCareFacilityServiceBean) importer.getHcfService(), false);
    }

    private Ii getHCFII(Organization importedOrg) {
        List<HealthCareFacility> hcfs = hcfSvc.getByPlayerIds(new Long[] { importedOrg.getId() });
        assertEquals(1, hcfs.size());
        HealthCareFacility persistedHCF = hcfs.get(0);
        Ii hcfPOID = new IdConverter.HealthCareFacilityIdConverter().convertToIi(hcfs.get(0).getId());
        return hcfPOID;
    }

    private void clearMessages() {
        MessageProducerTest.clearMessages((OrganizationServiceBean) importer.getOrgService());
        MessageProducerTest.clearMessages((IdentifiedOrganizationServiceBean) importer.getIdentifiedOrgService());
        MessageProducerTest.clearMessages((HealthCareFacilityServiceBean) importer.getHcfService());
        MessageProducerTest.clearMessages((ResearchOrganizationServiceBean) importer.getRoService());
    }

}
