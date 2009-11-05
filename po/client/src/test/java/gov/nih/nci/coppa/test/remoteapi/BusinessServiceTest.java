package gov.nih.nci.coppa.test.remoteapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.coppa.iso.Ad;
import gov.nih.nci.coppa.iso.Bl;
import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.IdentifierReliability;
import gov.nih.nci.coppa.iso.IdentifierScope;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Tel;
import gov.nih.nci.coppa.iso.TelEmail;
import gov.nih.nci.coppa.iso.TelPhone;
import gov.nih.nci.coppa.iso.TelUrl;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.coppa.test.DataGeneratorUtil;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.ResearchOrganization;
import gov.nih.nci.po.data.convert.AdConverter;
import gov.nih.nci.po.data.convert.CorrelationNodeDTOConverter;
import gov.nih.nci.po.data.convert.IdConverter;
import gov.nih.nci.po.service.BusinessServiceTestHelper;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.service.TestConvertHelper;
import gov.nih.nci.services.BusinessServiceRemote;
import gov.nih.nci.services.CorrelationDto;
import gov.nih.nci.services.EntityNodeDto;
import gov.nih.nci.services.RoleList;
import gov.nih.nci.services.correlation.AbstractPersonRoleDTO;
import gov.nih.nci.services.correlation.ClinicalResearchStaffCorrelationServiceRemote;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import gov.nih.nci.services.correlation.HealthCareFacilityCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.correlation.HealthCareProviderCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareProviderDTO;
import gov.nih.nci.services.correlation.IdentifiedOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedOrganizationDTO;
import gov.nih.nci.services.correlation.IdentifiedPersonCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedPersonDTO;
import gov.nih.nci.services.correlation.OrganizationalContactCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;
import gov.nih.nci.services.correlation.OversightCommitteeCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OversightCommitteeDTO;
import gov.nih.nci.services.correlation.ResearchOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.correlation.ResearchOrganizationDTO;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.person.AbstractPersonDTO;
import gov.nih.nci.services.person.PersonDTO;
import gov.nih.nci.services.person.PersonEntityServiceRemote;
import gov.nih.nci.services.correlation.CorrelationNodeDTO;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.naming.NamingException;

import org.apache.commons.collections.set.ListOrderedSet;
import org.iso._21090.AD;
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
    private HealthCareFacilityCorrelationServiceRemote hcfService;        
    private IdentifiedPersonCorrelationServiceRemote idpService;
    private IdentifiedOrganizationCorrelationServiceRemote idoService;
    private OrganizationalContactCorrelationServiceRemote ocService;
    
    @Test(expected = NullifiedEntityException.class)
    public void testInterceptorNotGetNullifiedByIdWithCorrelation() throws Exception {
        Ii newOrgId1 = BusinessServiceTestHelper.createOrganization(orgService);
        ResearchOrganizationDTO roDto = new ResearchOrganizationDTO();
        roDto.setName(TestConvertHelper.convertToEnOn("Research Org 1"));
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
        
        Ii newOrgId = BusinessServiceTestHelper.createOrganization(orgService);
        
        ResearchOrganizationDTO roDto = new ResearchOrganizationDTO();
        roDto.setName(TestConvertHelper.convertToEnOn("Research Org 1"));
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
        
        Ii newPersonId = BusinessServiceTestHelper.createPerson(personService);
        
        ClinicalResearchStaffDTO crsdto = new ClinicalResearchStaffDTO();
        TelPhone ph1 = new TelPhone();
        ph1.setValue(new URI(TelPhone.SCHEME_TEL + ":123-688-654"));
        DSet<Tel> telco = new DSet<Tel>();
        telco.setItem(new HashSet<Tel>());
        telco.getItem().add(ph1);
        crsdto.setTelecomAddress(telco);
        
        crsdto.setScoperIdentifier(newOrgId);
        crsdto.setPlayerIdentifier(newPersonId);
        
        TelPhone ph2 = new TelPhone();
        ph2.setValue(new URI(TelPhone.SCHEME_TEL + ":123-123-654"));
        crsdto.getTelecomAddress().getItem().add(ph2);
        
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
    public void testSearchWithEntities() throws Exception {
        
    }
    
    @Test
    public void testOrgRoleCorrelationsGetById() throws Exception {
        BusinessServiceTestHelper.helpTestOrgRoleCorrelationsGetById(
                researchOrgService, busService, orgService);   
    }
    
    
    @Test
    public void testPersonRoleCorrelationsGetById() throws Exception {
        BusinessServiceTestHelper.helpTestPersonRoleCorrelationsGetById(
                crsService, busService, orgService, personService);
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
        if (hcfService == null) {
            hcfService = RemoteServiceHelper.getHealthCareFacilityCorrelationService();
        }
        if (idpService == null) {
            idpService = RemoteServiceHelper.getIdentifiedPersonCorrelationServiceRemote();
        }
        if (idoService == null) {
            idoService = RemoteServiceHelper.getIdentifiedOrganizationCorrelationServiceRemote();
        }
        if (ocService == null) {
            ocService = RemoteServiceHelper.getOrganizationalContactCorrelationService();
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
        hcfService = null;
        idpService = null;
        idoService = null;
        ocService = null;
        RemoteServiceHelper.close();
    }
    
}
