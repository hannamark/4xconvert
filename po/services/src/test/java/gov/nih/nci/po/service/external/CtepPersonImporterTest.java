package gov.nih.nci.po.service.external;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.iso21090.IdentifierReliability;
import gov.nih.nci.iso21090.IdentifierScope;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.HealthCareProvider;
import gov.nih.nci.po.data.bo.IdentifiedOrganization;
import gov.nih.nci.po.data.bo.IdentifiedPerson;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.service.AbstractServiceBeanTest;
import gov.nih.nci.po.service.AnnotatedBeanSearchCriteria;
import gov.nih.nci.po.service.EjbTestHelper;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.service.HealthCareProviderServiceBean;
import gov.nih.nci.po.service.IdentifiedOrganizationServiceBean;
import gov.nih.nci.po.service.IdentifiedPersonServiceBean;
import gov.nih.nci.po.service.MessageProducerTest;
import gov.nih.nci.po.service.OrganizationServiceBean;
import gov.nih.nci.po.service.external.stubs.CTEPOrgServiceStubBuilder;
import gov.nih.nci.po.service.external.stubs.CTEPPerServiceStubBuilder;
import gov.nih.nci.po.service.external.stubs.CTEPPersonServiceStub;
import gov.nih.nci.services.person.PersonDTO;

import java.util.Date;
import java.util.List;

import javax.jms.JMSException;
import javax.naming.Context;

import org.junit.Before;
import org.junit.Test;

import com.fiveamsolutions.nci.commons.search.SearchCriteria;

public class CtepPersonImporterTest extends AbstractServiceBeanTest {

    private IdentifiedPersonServiceBean ipSvc;
    private Organization affOrg;
    private OrganizationServiceBean oSvc;
    private IdentifiedOrganizationServiceBean ioSvc;
    private CtepPersonImporter importer;
    private HealthCareProviderServiceBean hcpSvc;

    @Before
    public void setup() throws Exception {
        ipSvc = EjbTestHelper.getIdentifiedPersonServiceBean();
        oSvc = EjbTestHelper.getOrganizationServiceBean();
        ioSvc = EjbTestHelper.getIdentifiedOrganizationServiceBean();
        hcpSvc = EjbTestHelper.getHealthCareProviderServiceBean();
        
        CtepOrganizationImporter orgImport = new CtepOrganizationImporter(null) {
            @Override
            protected void initCtepServices(Context ctepContext) {
            }
        };
        
        orgImport.setCtepOrgService(CTEPOrgServiceStubBuilder.INSTANCE.buildCreateROStub());

        
        importer = new CtepPersonImporter(null, orgImport) {
            @Override
            protected void initCtepServices(Context ctepContext) {
            }
        };
       
        createCTEPOrg();
    }
    
    private void createCTEPOrg() throws Exception {

        affOrg = new Organization();
        affOrg.setName("Cancer Therapy Evaluation Program");
        affOrg.setStatusCode(EntityStatus.ACTIVE);
        affOrg.setPostalAddress(new Address());
        affOrg.getPostalAddress().setStreetAddressLine("bogus");
        affOrg.getPostalAddress().setCityOrMunicipality("city");
        affOrg.getPostalAddress().setStateOrProvince("VA");
        affOrg.getPostalAddress().setPostalCode("12345");
        affOrg.getPostalAddress().setCountry(getDefaultCountry());
        affOrg.getEmail().add(new Email("abc@example.com"));
        oSvc.curate(affOrg);
        MessageProducerTest.assertMessageCreated(affOrg, oSvc, true);
        MessageProducerTest.clearMessages(oSvc);
        
        // this is org with id of 1
        
        IdentifiedOrganization io = new IdentifiedOrganization();
        io.setStatus(RoleStatus.ACTIVE);
        io.setStatusDate(new Date());
        io.setPlayer(affOrg);
        io.setScoper(affOrg);
        io.setAssignedIdentifier(new Ii());
        io.getAssignedIdentifier().setDisplayable(true);
        io.getAssignedIdentifier().setExtension("CTEP");
        io.getAssignedIdentifier().setIdentifierName("CTEP ID");
        io.getAssignedIdentifier().setReliability(IdentifierReliability.VRF);
        io.getAssignedIdentifier().setRoot(CtepOrganizationImporterTest.CTEP_ORG_ROOT);
        io.getAssignedIdentifier().setScope(IdentifierScope.OBJ);

        ioSvc.curate(io);
        MessageProducerTest.assertMessageCreated(io, ioSvc, true);
        MessageProducerTest.clearMessages(ioSvc);

    }
   
    private IdentifiedPerson getByCtepPersonId(Ii ctepPerId) {
        IdentifiedPerson identifiedPer = new IdentifiedPerson();
        identifiedPer.setAssignedIdentifier(ctepPerId);
        SearchCriteria<IdentifiedPerson> sc = new AnnotatedBeanSearchCriteria<IdentifiedPerson>(
                identifiedPer);
        List<IdentifiedPerson> identifiedPers = ipSvc.search(sc);
        if (identifiedPers.isEmpty()) {
            return null;
        }
        return identifiedPers.get(0);
    }
    
    @Test
    public void testPersonImport()
    throws Exception, JMSException, EntityValidationException {
        // create Person and IdentifiedPerson via Import
        CTEPPersonServiceStub service = CTEPPerServiceStubBuilder.INSTANCE.buildCreateBaseStub();
        importer.setCtepPersonService(service);
        PersonDTO per = service.getPer();
        assertNotNull(per);
        assertNotNull(service.getPerId());
        
        Person importedPer = importer.importPerson(per.getIdentifier());
        assertNotNull(importedPer);
        assertNotNull(service.getPerId());
        IdentifiedPerson ip = getByCtepPersonId(service.getPerId());
        assertNotNull(ip);
        assertNotNull(ip.getPlayer());
        assertEquals(IdentifierReliability.VRF, ip.getAssignedIdentifier().getReliability());
        
        // update record to add HCP
        
        service = CTEPPerServiceStubBuilder.INSTANCE.buildCreateHCPStub();
        importer.setCtepPersonService(service);
        per = service.getPer();
        assertNotNull(per);
        assertNotNull(service.getPerId());
        
        importedPer = importer.importPerson(per.getIdentifier());
        assertNotNull(importedPer);
        assertNotNull(service.getPerId());
        ip = getByCtepPersonId(service.getPerId());
        assertNotNull(ip);
        assertNotNull(ip.getPlayer());
        assertEquals(IdentifierReliability.VRF, ip.getAssignedIdentifier().getReliability());
        List<HealthCareProvider> hcpList = hcpSvc.getByPlayerIds(new Long[] {ip.getPlayer().getId()});
        assertEquals(1, hcpList.size());    
    }
    
}
