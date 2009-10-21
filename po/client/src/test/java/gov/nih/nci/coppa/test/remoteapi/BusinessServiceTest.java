package gov.nih.nci.coppa.test.remoteapi;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.URI;
import java.util.HashSet;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Tel;
import gov.nih.nci.coppa.iso.TelEmail;
import gov.nih.nci.coppa.iso.TelPhone;
import gov.nih.nci.coppa.iso.TelUrl;
import gov.nih.nci.po.data.bo.OrganizationPlayedRole;
import gov.nih.nci.po.data.bo.PersonPlayedRole;
import gov.nih.nci.services.BusinessServiceRemote;
import gov.nih.nci.services.CorrelationDto;
import gov.nih.nci.services.EntityNodeDto;
import gov.nih.nci.services.correlation.ClinicalResearchStaffCorrelationServiceRemote;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import gov.nih.nci.services.correlation.OversightCommitteeCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OversightCommitteeDTO;
import gov.nih.nci.services.correlation.ResearchOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.correlation.ResearchOrganizationDTO;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.person.PersonDTO;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

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
        cd.setCode(OrganizationPlayedRole.RESEARCH_ORGANIZATION.toString());
        players[0] = cd;

        EntityNodeDto entityNodeDto = busService.getEntityByIdWithCorrelations(newOrgId, players, null);
        
        CorrelationDto[] playersDto = entityNodeDto.getPlayers();
        assertNotNull(entityNodeDto.getEntityDto());
        assertEquals(1, playersDto.length);
        
        
        entityNodeDto = busService.getEntityByIdWithCorrelations(newOrgId, null, null);        
        assertNull(entityNodeDto.getPlayers());
        assertNull(entityNodeDto.getScopers());

        entityNodeDto = busService.getEntityByIdWithCorrelations(newOrgId, new Cd[0], new Cd[0]);        
        assertNull(entityNodeDto.getPlayers());
        assertNull(entityNodeDto.getScopers());        
        
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
        cd.setCode(PersonPlayedRole.CLINICAL_RESEARCH_STAFF.toString());
        players[0] = cd;
        
        entityNodeDto = busService.getEntityByIdWithCorrelations(newPersonId, players, null);
        playersDto = entityNodeDto.getPlayers();
        assertNotNull(entityNodeDto.getEntityDto());
        assertEquals(1, playersDto.length);
        
        entityNodeDto = busService.getEntityByIdWithCorrelations(newPersonId, null, null);        
        assertNull(entityNodeDto.getPlayers());
        assertNull(entityNodeDto.getScopers());

        entityNodeDto = busService.getEntityByIdWithCorrelations(newPersonId, new Cd[0], new Cd[0]);        
        assertNull(entityNodeDto.getPlayers());
        assertNull(entityNodeDto.getScopers());   
        
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
        RemoteServiceHelper.close();
    }
    
}
