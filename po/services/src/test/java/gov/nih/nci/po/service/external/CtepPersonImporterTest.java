package gov.nih.nci.po.service.external;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.coppa.services.PersonService;
import gov.nih.nci.iso21090.Ad;
import gov.nih.nci.iso21090.Adxp;
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
import gov.nih.nci.po.data.convert.IdConverter;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jms.JMSException;
import javax.naming.Context;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.fiveamsolutions.nci.commons.search.SearchCriteria;

public class CtepPersonImporterTest extends AbstractServiceBeanTest {

    private IdentifiedPersonServiceBean ipSvc;
    private Organization affOrg;
    private Organization scoper;
    private OrganizationServiceBean oSvc;
    private IdentifiedOrganizationServiceBean ioSvc;
    private CtepPersonImporter importer;
    private HealthCareProviderServiceBean hcpSvc;
    private Ii ctepIi;

    @Rule public ExpectedException thrown = ExpectedException.none();
    
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

        ctepIi = new Ii();
        ctepIi.setRoot(CtepOrganizationImporterTest.CTEP_ORG_ROOT);
        ctepIi.setIdentifierName("CTEP ID");
        ctepIi.setExtension("CTEP");
    }

    private Ii createPendingScoper() throws Exception {
        scoper = new Organization();
        scoper.setName("Scoper Organization");
        scoper.setStatusCode(EntityStatus.PENDING);
        scoper.setPostalAddress(new Address());
        scoper.getPostalAddress().setStreetAddressLine("bogus");
        scoper.getPostalAddress().setCityOrMunicipality("city");
        scoper.getPostalAddress().setStateOrProvince("VA");
        scoper.getPostalAddress().setPostalCode("12345");
        scoper.getPostalAddress().setCountry(getDefaultCountry());
        scoper.getEmail().add(new Email("abc@example.com"));
        oSvc.curate(scoper);
        MessageProducerTest.assertMessageCreated(scoper, oSvc, true);
        MessageProducerTest.clearMessages(oSvc);
        Ii result = new Ii();
        result.setExtension(scoper.getId().toString());
        result.setIdentifierName(IdConverter.ORG_IDENTIFIER_NAME);
        result.setRoot(IdConverter.ORG_ROOT);
        return result;

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
    public void testPersonImportActiveOrg() throws Exception, JMSException, EntityValidationException {
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
        service = CTEPPerServiceStubBuilder.INSTANCE.buildCreateHCPStub(ctepIi);
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
        assertEquals(RoleStatus.ACTIVE, hcpList.get(0).getStatus());
    }

    @Test
    public void testPersonImportPendingOrg() throws Exception, JMSException, EntityValidationException {
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
        service = CTEPPerServiceStubBuilder.INSTANCE.buildCreateHCPStub(createPendingScoper());
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
        assertEquals(RoleStatus.ACTIVE, hcpList.get(0).getStatus());
    }

    @Test
    public void testInvalidPersonImportReturnNullPersonId() throws Exception, JMSException, EntityValidationException {
        CTEPPersonServiceStub stubPersonService = CTEPPerServiceStubBuilder.INSTANCE.buildCreateBaseStub();
        PersonService service = mock(PersonService.class);
        PersonDTO personDTO = mock(PersonDTO.class);
        when(personDTO.getIdentifier()).thenReturn(null);
        when(service.getPersonById(any(Ii.class))).thenReturn(personDTO);
        importer.setCtepPersonService(service);
        PersonDTO per = stubPersonService.getPer();
        thrown.expect(CtepImportException.class);
        thrown.expectMessage("Person import aborted, null CTEP Id found");
        importer.importPerson(per.getIdentifier());
    }
    
    @Test
    public void testInvalidPersonImportReturnIncorrectPersonId() throws Exception, JMSException, EntityValidationException {
        CTEPPersonServiceStub stubPersonService = CTEPPerServiceStubBuilder.INSTANCE.buildCreateBaseStub();
        PersonService service = mock(PersonService.class);
        PersonDTO personDTO = mock(PersonDTO.class);
        when(personDTO.getIdentifier()).thenReturn(new Ii());
        when(service.getPersonById(any(Ii.class))).thenReturn(personDTO);
        importer.setCtepPersonService(service);
        PersonDTO per = stubPersonService.getPer();
        thrown.expect(CtepImportException.class);
        thrown.expectMessage("Person import aborted, mismatch in CTEP Ids");
        importer.importPerson(per.getIdentifier());
    }
    
    @Test
    public void testInvalidPersonImportReturnBadAddress() throws Exception, JMSException, EntityValidationException {
        CTEPPersonServiceStub stubPersonService = CTEPPerServiceStubBuilder.INSTANCE.buildCreateBaseStub();
        PersonDTO per = stubPersonService.getPer();
        per.getIdentifier().setExtension("xyzzy");
        per.setPostalAddress(new Ad());
        per.getPostalAddress().setPart(new ArrayList<Adxp>());
        PersonService service = mock(PersonService.class);
        when(service.getPersonById(any(Ii.class))).thenReturn(per);
        importer.setCtepPersonService(service);
        thrown.expect(CtepImportException.class);
        thrown.expectMessage("Street missing in CTEP address.");
        importer.importPerson(per.getIdentifier());
    }
}
