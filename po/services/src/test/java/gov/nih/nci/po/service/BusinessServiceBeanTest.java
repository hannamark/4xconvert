package gov.nih.nci.po.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Tel;
import gov.nih.nci.coppa.iso.TelEmail;
import gov.nih.nci.coppa.iso.TelPhone;
import gov.nih.nci.coppa.iso.TelUrl;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.services.BusinessServiceBean;
import gov.nih.nci.services.BusinessServiceRemote;
import gov.nih.nci.services.RoleList;
import gov.nih.nci.services.correlation.ClinicalResearchStaffCorrelationServiceRemote;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import gov.nih.nci.services.correlation.CorrelationNodeDTO;
import gov.nih.nci.services.correlation.HealthCareProviderCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OversightCommitteeCorrelationServiceRemote;
import gov.nih.nci.services.correlation.ResearchOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.correlation.ResearchOrganizationDTO;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.person.PersonDTO;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;

import javax.naming.NamingException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BusinessServiceBeanTest extends AbstractBeanTest {

    protected BusinessServiceRemote busService;   
    protected OrganizationEntityServiceRemote orgService; 
    protected PersonEntityServiceRemote personService;
    protected ResearchOrganizationCorrelationServiceRemote researchOrgService;
    protected OversightCommitteeCorrelationServiceRemote oversightComService;
    protected ClinicalResearchStaffCorrelationServiceRemote crsService;
    protected HealthCareProviderCorrelationServiceRemote hcpService;
        
    @Test
    public void testOrgRoleCorrelationsGetById() throws Exception {
        Ii newOrgId = createOrganization();
        
        ResearchOrganizationDTO roDto = new ResearchOrganizationDTO();
        roDto.setName(TestConvertHelper.convertToEnOn("Research Org 1"));
        roDto.setPlayerIdentifier(newOrgId);
       
        Ii roDtoId = researchOrgService.createCorrelation(roDto);
        assertNotNull(roDtoId);
       
        CorrelationNodeDTO corrNodeDto = busService.getCorrelationByIdWithEntities(roDtoId, true, false);
        assertTrue(corrNodeDto.getCorrelation() instanceof ResearchOrganizationDTO);
        assertEquals("Research Org 1", ((ResearchOrganizationDTO) 
                corrNodeDto.getCorrelation()).getName().getPart().get(0).getValue());
        assertTrue(corrNodeDto.getPlayer() instanceof OrganizationDTO);
        assertEquals("Oct. 19th Org", ((OrganizationDTO) 
                corrNodeDto.getPlayer()).getName().getPart().get(0).getValue());
        assertNull(corrNodeDto.getScoper());
        
        corrNodeDto = busService.getCorrelationByIdWithEntities(roDtoId, true, true);
        assertNotNull(corrNodeDto.getPlayer());
        assertNull(corrNodeDto.getScoper());
        
        corrNodeDto = busService.getCorrelationByIdWithEntities(roDtoId, false, true);
        assertNull(corrNodeDto.getPlayer());
        assertNull(corrNodeDto.getScoper());
        
        corrNodeDto = busService.getCorrelationByIdWithEntities(roDtoId, false, false);
        assertNull(corrNodeDto.getPlayer());
        assertNull(corrNodeDto.getScoper());
        
        ResearchOrganizationDTO roDto2 = new ResearchOrganizationDTO();
        roDto2.setName(TestConvertHelper.convertToEnOn("Research Org 2"));
        roDto2.setPlayerIdentifier(newOrgId);
       
        Ii roDtoId2 = researchOrgService.createCorrelation(roDto2);
        assertNotNull(roDtoId2);
        
        CorrelationNodeDTO[] corrNodeDtos = busService.getCorrelationsByIdsWithEntities(
                new Ii[]{roDtoId, roDtoId2}, true, false);
        assertEquals(2, corrNodeDtos.length);
        
        for (CorrelationNodeDTO corr : corrNodeDtos) {
                assertTrue(((ResearchOrganizationDTO) corr.getCorrelation())
                        .getName().getPart().get(0).getValue().contains("Research Org"));
            assertEquals(newOrgId.getExtension(), corr.getPlayer().getIdentifier().getExtension());
            assertNull(corr.getScoper());
        }
        
        Cd cd = new Cd();
        cd.setCode(RoleList.RESEARCH_ORGANIZATION.toString());
        
        corrNodeDtos = busService.getCorrelationsByPlayerIdsWithEntities(cd, new Ii[]{newOrgId}, true, false);        
        assertEquals(2, corrNodeDtos.length);
        for (CorrelationNodeDTO corr : corrNodeDtos) {
            assertTrue(corr.getCorrelation() instanceof ResearchOrganizationDTO);
            assertTrue(((ResearchOrganizationDTO) corr.getCorrelation())
                    .getName().getPart().get(0).getValue().contains("Research Org"));
            assertEquals(newOrgId.getExtension(), corr.getPlayer().getIdentifier().getExtension());
            assertNull(corr.getScoper());
        }   
    }
    
    
    @Test
    public void testPersonRoleCorrelationsGetById() throws Exception {
        Ii newOrgId = createOrganization();
        
        Ii newPersonId = createPerson();
        
        ClinicalResearchStaffDTO crsdto = new ClinicalResearchStaffDTO();
        crsdto.setScoperIdentifier(newOrgId);
        crsdto.setPlayerIdentifier(newPersonId);
        crsdto.setTelecomAddress(new DSet<Tel>());
        crsdto.getTelecomAddress().setItem(new HashSet<Tel>());
        
        TelPhone ph1 = new TelPhone();
        ph1.setValue(new URI(TelPhone.SCHEME_TEL + ":123-123-654"));
        crsdto.getTelecomAddress().getItem().add(ph1);
        
        Ii crsDtoId = crsService.createCorrelation(crsdto);
        
        CorrelationNodeDTO corrNodeDto = busService.getCorrelationByIdWithEntities(crsDtoId, true, true);
        assertTrue(corrNodeDto.getCorrelation() instanceof ClinicalResearchStaffDTO);
        assertEquals(crsDtoId.getExtension(), 
                ((ClinicalResearchStaffDTO) corrNodeDto.getCorrelation())
                .getIdentifier().getItem().iterator().next().getExtension());
        assertTrue(corrNodeDto.getPlayer() instanceof PersonDTO);
        
        assertEquals("LastName", ((PersonDTO) corrNodeDto.getPlayer()).getName().getPart().get(0).getValue());
        assertTrue(corrNodeDto.getScoper() instanceof OrganizationDTO);
        assertEquals("Oct. 19th Org", ((OrganizationDTO) corrNodeDto.getScoper())
                .getName().getPart().get(0).getValue());
        
        corrNodeDto = busService.getCorrelationByIdWithEntities(crsDtoId, false, false);
        assertNull(corrNodeDto.getPlayer());
        assertNull(corrNodeDto.getScoper());
        
        corrNodeDto = busService.getCorrelationByIdWithEntities(crsDtoId, true, false);
        assertNotNull(corrNodeDto.getPlayer());
        assertNull(corrNodeDto.getScoper());
        
        corrNodeDto = busService.getCorrelationByIdWithEntities(crsDtoId, false, true);
        assertNull(corrNodeDto.getPlayer());
        assertNotNull(corrNodeDto.getScoper());
        
        Ii newPersonId2 = createPerson();
                
        ClinicalResearchStaffDTO crsdto2 = new ClinicalResearchStaffDTO();
        crsdto2.setScoperIdentifier(newOrgId);
        crsdto2.setPlayerIdentifier(newPersonId2);
        crsdto2.setTelecomAddress(new DSet<Tel>());
        crsdto2.getTelecomAddress().setItem(new HashSet<Tel>());
        
        
        crsdto2.getTelecomAddress().getItem().add(ph1);
        
        Ii crsDtoId2 = crsService.createCorrelation(crsdto2);
        
        CorrelationNodeDTO[] corrNodeDtos = busService.getCorrelationsByIdsWithEntities(
                new Ii[]{crsDtoId, crsDtoId2}, true, true);
        assertEquals(2, corrNodeDtos.length);
        
        for (CorrelationNodeDTO corr : corrNodeDtos) {
            assertEquals(new URI(TelPhone.SCHEME_TEL + ":123-123-654"), 
                    ((ClinicalResearchStaffDTO) corr.getCorrelation())
                    .getTelecomAddress().getItem().iterator().next().getValue());
            assertEquals("LastName", ((PersonDTO) corr.getPlayer()).getName().getPart().get(0).getValue());
            assertEquals(newOrgId.getExtension(), corr.getScoper().getIdentifier().getExtension());
        }
        
        Cd cd = new Cd();
        cd.setCode(RoleList.CLINICAL_RESEARCH_STAFF.toString());
        
        corrNodeDtos = busService.getCorrelationsByPlayerIdsWithEntities(cd, new Ii[]{newPersonId, newPersonId2}, true, true);        
        assertEquals(2, corrNodeDtos.length);
        for (CorrelationNodeDTO corr : corrNodeDtos) {
            assertTrue(corr.getCorrelation() instanceof ClinicalResearchStaffDTO);
            assertEquals(new URI(TelPhone.SCHEME_TEL + ":123-123-654"), 
                    ((ClinicalResearchStaffDTO) corr.getCorrelation())
                    .getTelecomAddress().getItem().iterator().next().getValue());
            assertEquals("LastName", ((PersonDTO) corr.getPlayer()).getName().getPart().get(0).getValue());
            assertEquals(newOrgId.getExtension(), corr.getScoper().getIdentifier().getExtension());
        } 
    }
    
     protected Ii createOrganization() throws URISyntaxException, EntityValidationException, CurationException {
         OrganizationDTO orgDto = new OrganizationDTO();
         orgDto.setName(TestConvertHelper.convertToEnOn("Oct. 19th Org"));

         orgDto.setPostalAddress(TestConvertHelper.createAd("123 abc ave.", null, "mycity", "WY", "12345", "USA"));
         DSet<Tel> telco = new DSet<Tel>();
         telco.setItem(new HashSet<Tel>());
         orgDto.setTelecomAddress(telco);

         TelEmail email = new TelEmail();
         email.setValue(new URI("mailto:default@example.com"));
         orgDto.getTelecomAddress().getItem().add(email);

         TelUrl url = new TelUrl();
         url.setValue(new URI("http://default.example.com"));
         orgDto.getTelecomAddress().getItem().add(url);
         
         return orgService.createOrganization(orgDto);
     }
     
     protected Ii createPerson() throws URISyntaxException, EntityValidationException, CurationException {
         PersonDTO p = new PersonDTO();
         p.setName(TestConvertHelper.convertToEnPn("FirstName", "M", "LastName", null, null));
         p.setPostalAddress(TestConvertHelper.createAd("strees", "delivery", "city", "MD", "20850", "USA"));
      
         DSet<Tel> telco = new DSet<Tel>();
         telco.setItem(new HashSet<Tel>());
         p.setTelecomAddress(telco);

         TelEmail email = new TelEmail();
         email.setValue(new URI("mailto:default@example.com"));
         p.getTelecomAddress().getItem().add(email);

         TelUrl url = new TelUrl();
         url.setValue(new URI("http://default.example.com"));
         p.getTelecomAddress().getItem().add(url);

         return personService.createPerson(p);
     }
 
    
    /**
     * Init the test.
     * 
     * @throws NamingException on error.
     */
    @Before
    public void init() throws NamingException {
        if (busService == null) {
            busService = (BusinessServiceBean) EjbTestHelper.getBusinessService();
        }
        if (orgService == null) {
            orgService = EjbTestHelper.getOrganizationEntityServiceBeanAsRemote();
        }
        if (personService == null) {
            personService = EjbTestHelper.getPersonEntityServiceBeanAsRemote();
        }
        if (researchOrgService == null) {
            researchOrgService = EjbTestHelper.getResearchOrganizationCorrelationServiceRemote();
        }
        if (oversightComService == null) {
            oversightComService = EjbTestHelper.getOversightCommitteeCorrelationServiceRemote();
        }
        if (crsService == null) {
            crsService = EjbTestHelper.getClinicalResearchStaffCorrelationServiceRemote();
        }
        if (hcpService == null) {
            hcpService = EjbTestHelper.getHealthCareProviderCorrelationServiceRemote();
        }
        
    }
    
    /**
     * cleanup after test is complete.
     * 
     * @throws NamingException on error.
     */
    @After
    public void cleanup() throws NamingException {
        busService = null;
        orgService = null;
        oversightComService = null;
        researchOrgService = null;
        personService = null;
        crsService = null;
    }
    
}
