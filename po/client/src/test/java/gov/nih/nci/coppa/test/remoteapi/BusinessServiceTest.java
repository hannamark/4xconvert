package gov.nih.nci.coppa.test.remoteapi;

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
import gov.nih.nci.coppa.test.DataGeneratorUtil;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.BusinessServiceRemote;
import gov.nih.nci.services.CorrelationDto;
import gov.nih.nci.services.EntityNodeDto;
import gov.nih.nci.services.RoleList;
import gov.nih.nci.services.correlation.ClinicalResearchStaffCorrelationServiceRemote;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import gov.nih.nci.services.correlation.CorrelationNodeDTO;
import gov.nih.nci.services.correlation.HealthCareProviderCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OversightCommitteeCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OversightCommitteeDTO;
import gov.nih.nci.services.correlation.ResearchOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.correlation.ResearchOrganizationDTO;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.person.PersonDTO;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.util.HashSet;

import javax.naming.NamingException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BusinessServiceTest {

    private BusinessServiceRemote busService;   
    private OrganizationEntityServiceRemote orgService; 
    private PersonEntityServiceRemote personService;
    private ResearchOrganizationCorrelationServiceRemote researchOrgService;
    private OversightCommitteeCorrelationServiceRemote oversightComService;
    private ClinicalResearchStaffCorrelationServiceRemote crsService;
    private HealthCareProviderCorrelationServiceRemote hcpService;
        
    
    
    @Test(expected = NullifiedEntityException.class)
    public void testInterceptorNotGetNullifiedByIdWithCorrelation() throws Exception {
        Ii newOrgId1 = createOrganization();
        ResearchOrganizationDTO roDto = new ResearchOrganizationDTO();
        roDto.setName(RemoteApiUtils.convertToEnOn("Research Org 1"));
        roDto.setPlayerIdentifier(newOrgId1);
        researchOrgService.createCorrelation(roDto);
        
        OversightCommitteeDTO ovsComDto = new OversightCommitteeDTO();
        ovsComDto.setPlayerIdentifier(newOrgId1);
        Cd typeCode = new Cd();
        typeCode.setCode("Ethics Committee");
        ovsComDto.setTypeCode(typeCode);
        oversightComService.createCorrelation(ovsComDto);
        
        Cd[] players = new Cd[1];
        Cd cd = new Cd();
        cd.setCode(RoleList.RESEARCH_ORGANIZATION.toString());
        players[0] = cd;

        EntityNodeDto entityNodeDto = busService.getEntityByIdWithCorrelations(newOrgId1, players, null);
        assertNotNull(entityNodeDto.getEntityDto());
           
        Connection c = DataGeneratorUtil.getJDBCConnection();
        c.createStatement()
            .execute("update organization set status = 'NULLIFIED' where id = "+ newOrgId1.getExtension());
        c.close();
       
        entityNodeDto = busService.getEntityByIdWithCorrelations(newOrgId1, players, null);
       
    }
    
    @Test
    public void testGetByIdWithCorrelation() throws Exception {
        OrganizationDTO orgDto = new OrganizationDTO();
        orgDto.setName(RemoteApiUtils.convertToEnOn("Oct. 19th Org"));
        orgDto.setPostalAddress(RemoteApiUtils.createAd("123 abc ave.", null, "mycity", "WY", "12345", "USA"));
        DSet<Tel> telco = new DSet<Tel>();
        telco.setItem(new HashSet<Tel>());
        orgDto.setTelecomAddress(telco);

        TelEmail email = new TelEmail();
        email.setValue(new URI("mailto:default@example.com"));
        orgDto.getTelecomAddress().getItem().add(email);

        TelUrl url = new TelUrl();
        url.setValue(new URI("http://default.example.com"));
        orgDto.getTelecomAddress().getItem().add(url);
        
        Ii newOrgId = orgService.createOrganization(orgDto);
        
        ResearchOrganizationDTO roDto = new ResearchOrganizationDTO();
        roDto.setName(RemoteApiUtils.convertToEnOn("Research Org 1"));
        roDto.setPlayerIdentifier(newOrgId);
        
        OversightCommitteeDTO ovsComDto = new OversightCommitteeDTO();
        ovsComDto.setPlayerIdentifier(newOrgId);
        Cd typeCode = new Cd();
        typeCode.setCode("Ethics Committee");
        ovsComDto.setTypeCode(typeCode);
        
        Ii roDtoId = researchOrgService.createCorrelation(roDto);
        assertNotNull(roDtoId);
        
        Ii ovsComDtoId = oversightComService.createCorrelation(ovsComDto);
        assertNotNull(ovsComDtoId);
        
        Cd[] players = new Cd[1];
        Cd cd = new Cd();
        cd.setCode(RoleList.RESEARCH_ORGANIZATION.toString());
        players[0] = cd;

        EntityNodeDto entityNodeDto = busService.getEntityByIdWithCorrelations(newOrgId, players, null);
        
        CorrelationDto[] playersDto = entityNodeDto.getPlayers();
        assertNotNull(entityNodeDto.getEntityDto());
        assertEquals(1, playersDto.length);
        
        
        entityNodeDto = busService.getEntityByIdWithCorrelations(newOrgId, null, null);        
        assertEquals(0, entityNodeDto.getPlayers().length);
        assertEquals(0, entityNodeDto.getScopers().length);

        entityNodeDto = busService.getEntityByIdWithCorrelations(newOrgId, new Cd[0], new Cd[0]);        
        assertEquals(0, entityNodeDto.getPlayers().length);
        assertEquals(0, entityNodeDto.getScopers().length);        
        
        PersonDTO p = new PersonDTO();
        p.setName(RemoteApiUtils.convertToEnPn("FirstName", "M", "LastName", null, null));
        p.setPostalAddress(RemoteApiUtils.createAd("strees", "delivery", "city", "MD", "20850", "USA"));
     
        telco = new DSet<Tel>();
        telco.setItem(new HashSet<Tel>());
        p.setTelecomAddress(telco);

        email = new TelEmail();
        email.setValue(new URI("mailto:default@example.com"));
        p.getTelecomAddress().getItem().add(email);

        url = new TelUrl();
        url.setValue(new URI("http://default.example.com"));
        p.getTelecomAddress().getItem().add(url);

        Ii newPersonId = personService.createPerson(p);
        
        ClinicalResearchStaffDTO crsdto = new ClinicalResearchStaffDTO();
        crsdto.setScoperIdentifier(newOrgId);
        crsdto.setPlayerIdentifier(newPersonId);
        crsdto.setTelecomAddress(new DSet<Tel>());
        crsdto.getTelecomAddress().setItem(new HashSet<Tel>());
        
        TelPhone ph1 = new TelPhone();
        ph1.setValue(new URI(TelPhone.SCHEME_TEL + ":123-123-654"));
        crsdto.getTelecomAddress().getItem().add(ph1);
        
        crsService.createCorrelation(crsdto);
            
        players = new Cd[1];
        cd = new Cd();
        cd.setCode(RoleList.CLINICAL_RESEARCH_STAFF.toString());
        players[0] = cd;
        
        entityNodeDto = busService.getEntityByIdWithCorrelations(newPersonId, players, null);
        playersDto = entityNodeDto.getPlayers();
        assertNotNull(entityNodeDto.getEntityDto());
        assertEquals(1, playersDto.length);
        
        entityNodeDto = busService.getEntityByIdWithCorrelations(newPersonId, null, null);        
        assertEquals(0, entityNodeDto.getPlayers().length);
        assertEquals(0, entityNodeDto.getScopers().length);

        entityNodeDto = busService.getEntityByIdWithCorrelations(newPersonId, new Cd[0], new Cd[0]);        
        assertEquals(0, entityNodeDto.getPlayers().length);
        assertEquals(0, entityNodeDto.getScopers().length);   
        
    }
    
    @Test
    public void testOrgRoleCorrelationsGetById() throws Exception {
        Ii newOrgId = createOrganization();
        
        ResearchOrganizationDTO roDto = new ResearchOrganizationDTO();
        roDto.setName(RemoteApiUtils.convertToEnOn("Research Org 1"));
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
        roDto2.setName(RemoteApiUtils.convertToEnOn("Research Org 2"));
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
         orgDto.setName(RemoteApiUtils.convertToEnOn("Oct. 19th Org"));

         orgDto.setPostalAddress(RemoteApiUtils.createAd("123 abc ave.", null, "mycity", "WY", "12345", "USA"));
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
         p.setName(RemoteApiUtils.convertToEnPn("FirstName", "M", "LastName", null, null));
         p.setPostalAddress(RemoteApiUtils.createAd("strees", "delivery", "city", "MD", "20850", "USA"));
      
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
            busService = RemoteServiceHelper.getBusinessService();
        }
        if (orgService == null) {
            orgService = RemoteServiceHelper.getOrganizationEntityService();
        }
        if (personService == null) {
            personService = RemoteServiceHelper.getPersonEntityService();
        }
        if (researchOrgService == null) {
            researchOrgService = RemoteServiceHelper.getResearchOrganizationCorrelationService();
        }
        if (oversightComService == null) {
            oversightComService = RemoteServiceHelper.getOversightCommitteeCorrelationService();
        }
        if (crsService == null) {
            crsService = RemoteServiceHelper.getClinicalResearchStaffCorrelationService();
        }
        if (hcpService == null) {
            hcpService = RemoteServiceHelper.getHealthCareProviderCorrelationService();
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
        hcpService = null;
        RemoteServiceHelper.close();
    }
    
}
