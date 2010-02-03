package gov.nih.nci.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.iso21090.Bl;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.EnOn;
import gov.nih.nci.iso21090.Enxp;
import gov.nih.nci.po.data.bo.ClinicalResearchStaff;
import gov.nih.nci.po.data.bo.Entity;
import gov.nih.nci.po.data.bo.HealthCareFacility;
import gov.nih.nci.po.data.bo.HealthCareProvider;
import gov.nih.nci.po.data.bo.IdentifiedOrganization;
import gov.nih.nci.po.data.bo.IdentifiedPerson;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.OrganizationalContact;
import gov.nih.nci.po.data.bo.OversightCommittee;
import gov.nih.nci.po.data.bo.OversightCommitteeType;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.data.bo.ResearchOrganization;
import gov.nih.nci.po.data.convert.util.PersonNameConverterUtil;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.correlation.HealthCareProviderDTO;
import gov.nih.nci.services.correlation.IdentifiedOrganizationDTO;
import gov.nih.nci.services.correlation.IdentifiedPersonDTO;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;
import gov.nih.nci.services.correlation.OversightCommitteeDTO;
import gov.nih.nci.services.correlation.ResearchOrganizationDTO;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

/**
 * 
 * @author ludetc
 *
 */
public class EntityNodeDtoConverterTest {

    private static String PERSON_LAST_NAME = "The Person's Name";
    private static String ORG_NAME = "The Org Name";
    private static String ORG_NAME_2 = "Another Org Name";
    private static String RES_ORG_NAME = "The Research Org Name";
    private static String ORGANIZATION_CONTACT_TITLE = "The Org Title";
    private static String OVERSIGHT_COMMITTEE_TYPE = "The Committee Type";
    private static String HEALTH_CARE_FACILITY_NAME = "The HealthCare Facility Name";
    private static Long TEST_ID = 1783L;
    
    @Test
    public void testConstructor() {
        EntityNodeDtoConverter conv = new EntityNodeDtoConverter();
    }
    
    @Test
    public void testConvertPersonDtoToPoEntity() {
        EntityNodeDto entityNodeDto = new EntityNodeDto();
        
        PersonDTO personDto = new PersonDTO();
        personDto.setName(PersonNameConverterUtil.convertToEnPn(null, null, PERSON_LAST_NAME, null, null));
        entityNodeDto.setEntityDto(personDto);
        
        CorrelationDto[] players = new CorrelationDto[4];
        players[0] = new OrganizationalContactDTO();
        players[1] = new ClinicalResearchStaffDTO();
        players[2] = new HealthCareProviderDTO();
        players[3] = new IdentifiedPersonDTO();
        
        entityNodeDto.setPlayers(players);
        
        Entity entity = EntityNodeDtoConverter.convertToEntity(entityNodeDto);
        
        assertTrue(entity instanceof Person);
        Person person = (Person) entity;
        assertEquals(person.getLastName(), PERSON_LAST_NAME);
    }
    
    @Test
    public void testConvertOrganizationDtoToPoEntity() {
        EntityNodeDto entityNodeDto = new EntityNodeDto();
        
        EnOn enop = new EnOn();
        Enxp enxp = new Enxp(null);
        enxp.setValue(ORG_NAME);
        enop.getPart().add(enxp);
        
        OrganizationDTO orgDto = new OrganizationDTO();
        orgDto.setName(enop);
        entityNodeDto.setEntityDto(orgDto);
        
        CorrelationDto[] players = new CorrelationDto[3];
        players[0] = new OversightCommitteeDTO();
        players[1] = new ResearchOrganizationDTO();
        players[2] = new HealthCareFacilityDTO();

        CorrelationDto[] scopers = new CorrelationDto[5];
        scopers[0] = new OrganizationalContactDTO();        
        scopers[1] = new IdentifiedOrganizationDTO();
        scopers[2] = new IdentifiedPersonDTO();
        scopers[3] = new HealthCareProviderDTO();
        scopers[4] = new ClinicalResearchStaffDTO();
        
        entityNodeDto.setPlayers(players);
        entityNodeDto.setScopers(scopers);
        Entity entity = EntityNodeDtoConverter.convertToEntity(entityNodeDto);
        
        assertTrue(entity instanceof Organization);
        Organization org = (Organization) entity;
        assertEquals(org.getName(), ORG_NAME);
    }
    
    @Test 
    public void testConvertOrganizationToEntityNodeDto() {
        Organization org = new Organization();
        org.setName(ORG_NAME);
        
        // 1 player
        ResearchOrganization resOrg = new ResearchOrganization();
        resOrg.setName(RES_ORG_NAME);
        org.getResearchOrganizations().add(resOrg);
        
        // 1 player
        OversightCommittee oversightCom = new OversightCommittee();
        oversightCom.setTypeCode(new OversightCommitteeType(OVERSIGHT_COMMITTEE_TYPE));
        org.getOversightCommittees().add(oversightCom);
        
        // 1 player
        HealthCareFacility hcf = new HealthCareFacility();
        hcf.setName(HEALTH_CARE_FACILITY_NAME);
        org.getHealthCareFacilities().add(hcf);
        
        Organization anotherOrg = new Organization();
        anotherOrg.setName(ORG_NAME_2);
        
        // 1 player
        IdentifiedOrganization idOrg = new IdentifiedOrganization();
        idOrg.setPlayer(org);
        idOrg.setScoper(anotherOrg);
        org.getIdentifiedOrganizations().add(idOrg);

        // 1 scoper
        idOrg = new IdentifiedOrganization();
        idOrg.setPlayer(anotherOrg);
        idOrg.setScoper(org);
        org.getIdentifiedOrganizations().add(idOrg);
        
        // 1 player + 1 scoper
        idOrg = new IdentifiedOrganization();
        idOrg.setPlayer(org);
        idOrg.setScoper(org);
        org.getIdentifiedOrganizations().add(idOrg);
        
        // 1 scoper
        OrganizationalContact orgContact = new OrganizationalContact();
        orgContact.setTitle(ORGANIZATION_CONTACT_TITLE);
        org.getOrganizationalContacts().add(orgContact);
        
        // 1 scoper
        ClinicalResearchStaff clinicalRessearchStaff = new ClinicalResearchStaff();
        org.getClinicalResearchStaff().add(clinicalRessearchStaff);
        
        // 1 scoper 
        HealthCareProvider hcp = new HealthCareProvider();
        org.getHealthCareProviders().add(hcp);

        // 1 scoper 
        IdentifiedPerson idp = new IdentifiedPerson();
        org.getIdentifiedPersons().add(idp);
        
        Cd[] playerFilters = new Cd[4];
        Cd cd = new Cd();
        cd.setCode(RoleList.RESEARCH_ORGANIZATION.toString());
        playerFilters[0] = cd;
        cd = new Cd();
        cd.setCode(RoleList.OVERSIGHT_COMMITTEE.toString());
        playerFilters[1] = cd;
        cd = new Cd();
        cd.setCode(RoleList.HEALTH_CARE_FACILITY.toString());
        playerFilters[2] = cd;
        cd = new Cd();
        cd.setCode(RoleList.IDENTIFIED_ORGANIZATION.toString());
        playerFilters[3] = cd;
 
        Cd[] scoperFilters = new Cd[5];
        cd = new Cd();
        cd.setCode(RoleList.ORGANIZATIONAL_CONTACT.toString());
        scoperFilters[0] = cd;
        cd = new Cd();
        cd.setCode(RoleList.IDENTIFIED_ORGANIZATION.toString());
        scoperFilters[1] = cd;
        cd = new Cd();
        cd.setCode(RoleList.CLINICAL_RESEARCH_STAFF.toString());
        scoperFilters[2] = cd;
        cd = new Cd();
        cd.setCode(RoleList.HEALTH_CARE_PROVIDER.toString());
        scoperFilters[3] = cd;
        cd = new Cd();
        cd.setCode(RoleList.IDENTIFIED_PERSON.toString());
        scoperFilters[4] = cd;
        
        
        EntityNodeDto entityNodeDto = EntityNodeDtoConverter.convertToEntityNodeDto(org, playerFilters, scoperFilters);
        CorrelationDto[] players = entityNodeDto.getPlayers();
        CorrelationDto[] scopers = entityNodeDto.getScopers();
    
        assertTrue(entityNodeDto.getEntityDto() instanceof OrganizationDTO);
        OrganizationDTO orgDto = (OrganizationDTO) entityNodeDto.getEntityDto();
        assertEquals(orgDto.getName().getPart().get(0).getValue(), ORG_NAME);

        assertEquals(players.length, 5);
        assertEquals(scopers.length, 6);

        // do the players
        Map<Class, List<CorrelationDto>> corDtoMap = new HashMap<Class, List<CorrelationDto>>();        
        for(CorrelationDto corDto : players) {
            List<CorrelationDto> l = corDtoMap.get(corDto.getClass());
            if (l == null) {
                l = new ArrayList<CorrelationDto>();
                corDtoMap.put(corDto.getClass(), l);
            }
            l.add(corDto);
        }

        List<CorrelationDto> dtoList = corDtoMap.get(ResearchOrganizationDTO.class);
        assertNotNull(dtoList);
        assertEquals(dtoList.size(), 1);
        assertTrue(dtoList.get(0) instanceof ResearchOrganizationDTO);
        ResearchOrganizationDTO rodto = (ResearchOrganizationDTO) dtoList.get(0);
        assertEquals(rodto.getName().getPart().get(0).getValue(), RES_ORG_NAME);

        dtoList = corDtoMap.get(OversightCommitteeDTO.class);
        assertNotNull(dtoList);
        assertEquals(dtoList.size(), 1);
        assertTrue(dtoList.get(0) instanceof OversightCommitteeDTO);
        OversightCommitteeDTO ocdto = (OversightCommitteeDTO) dtoList.get(0);
        assertEquals(ocdto.getTypeCode().getCode(), OVERSIGHT_COMMITTEE_TYPE);
        
        dtoList = corDtoMap.get(HealthCareFacilityDTO.class);
        assertNotNull(dtoList);
        assertEquals(dtoList.size(), 1);
        assertTrue(dtoList.get(0) instanceof HealthCareFacilityDTO);
        HealthCareFacilityDTO hcfdto = (HealthCareFacilityDTO) dtoList.get(0);
        assertEquals(hcfdto.getName().getPart().get(0).getValue(), HEALTH_CARE_FACILITY_NAME);

        dtoList = corDtoMap.get(IdentifiedOrganizationDTO.class);
        assertNotNull(dtoList);
        assertEquals(dtoList.size(), 2);
        assertTrue(dtoList.get(0) instanceof IdentifiedOrganizationDTO);
        assertTrue(dtoList.get(1) instanceof IdentifiedOrganizationDTO);
    
        // do the scopers
        corDtoMap = new HashMap<Class, List<CorrelationDto>>();        
        for(CorrelationDto corDto : scopers) {
            List<CorrelationDto> l = corDtoMap.get(corDto.getClass());
            if (l == null) {
                l = new ArrayList<CorrelationDto>();
                corDtoMap.put(corDto.getClass(), l);
            }
            l.add(corDto);
        }
        
        dtoList = corDtoMap.get(OrganizationalContactDTO.class);
        assertNotNull(dtoList);
        assertEquals(dtoList.size(), 1);
        assertTrue(dtoList.get(0) instanceof OrganizationalContactDTO);
        OrganizationalContactDTO orgcdto = (OrganizationalContactDTO) dtoList.get(0);
        assertEquals(orgcdto.getTitle().getValue(), ORGANIZATION_CONTACT_TITLE);

        dtoList = corDtoMap.get(IdentifiedOrganizationDTO.class);
        assertNotNull(dtoList);
        assertEquals(dtoList.size(), 2);
        assertTrue(dtoList.get(0) instanceof IdentifiedOrganizationDTO);
        assertTrue(dtoList.get(1) instanceof IdentifiedOrganizationDTO);

        dtoList = corDtoMap.get(ClinicalResearchStaffDTO.class);
        assertNotNull(dtoList);
        assertEquals(dtoList.size(), 1);
        assertTrue(dtoList.get(0) instanceof ClinicalResearchStaffDTO);

        dtoList = corDtoMap.get(HealthCareProviderDTO.class);
        assertNotNull(dtoList);
        assertEquals(dtoList.size(), 1);
        assertTrue(dtoList.get(0) instanceof HealthCareProviderDTO);

        // try selecting only a few filters
        playerFilters = new Cd[1];
        cd = new Cd();
        cd.setCode(RoleList.HEALTH_CARE_FACILITY.toString());
        playerFilters[0] = cd;

        scoperFilters = new Cd[1];
        cd = new Cd();
        cd.setCode(RoleList.CLINICAL_RESEARCH_STAFF.toString());
        scoperFilters[0] = cd;
                
        entityNodeDto = EntityNodeDtoConverter.convertToEntityNodeDto(org, playerFilters, scoperFilters);
        players = entityNodeDto.getPlayers();
        scopers = entityNodeDto.getScopers();
    
        assertTrue(entityNodeDto.getEntityDto() instanceof OrganizationDTO);
        orgDto = (OrganizationDTO) entityNodeDto.getEntityDto();
        assertEquals(orgDto.getName().getPart().get(0).getValue(), ORG_NAME);

        assertEquals(players.length, 1);
        assertEquals(scopers.length, 1);
        
        entityNodeDto = EntityNodeDtoConverter.convertToEntityNodeDto(org, null, null);
        
        assertTrue(entityNodeDto.getEntityDto() instanceof OrganizationDTO);
        orgDto = (OrganizationDTO) entityNodeDto.getEntityDto();
        assertEquals(orgDto.getName().getPart().get(0).getValue(), ORG_NAME);

        players = entityNodeDto.getPlayers();
        scopers = entityNodeDto.getScopers();
    
        assertEquals(players.length, 0);
        assertEquals(scopers.length, 0);
        

    }
    
    @Test 
    public void testOrgScoperOverflow() {
        Organization org = new Organization();
        org.setName(ORG_NAME);
            
        for (int i = 0; i < 501; i++) {
            ClinicalResearchStaff clinicalRessearchStaff = new ClinicalResearchStaff();
            org.getClinicalResearchStaff().add(clinicalRessearchStaff);  
        }
        
        Cd[] filters = new Cd[2];
        Cd cd = new Cd();
        cd.setCode(RoleList.CLINICAL_RESEARCH_STAFF.toString());
        filters[0] = cd;
        
        // try out scoper cd with no code.
        cd = new Cd();
        filters[1] = cd;
        
        EntityNodeDto entityNodeDto = EntityNodeDtoConverter.convertToEntityNodeDto(org, null, filters);
        Bl bl = entityNodeDto.getCorrelationOverflow();
        assertTrue(bl.getValue());
        
        assertNull(entityNodeDto.getScopers());    
    }

    @Test 
    public void testOrgPlayerOverflow() {
        Organization org = new Organization();
        org.setName(ORG_NAME);
        
        for (int i = 0; i < 501; i++) {
            HealthCareFacility hcf = new HealthCareFacility();
            org.getHealthCareFacilities().add(hcf);  
        }
        
        Cd[] filters = new Cd[2];
        Cd cd = new Cd();
        cd.setCode(RoleList.HEALTH_CARE_FACILITY.toString());
        filters[0] = cd;
        
        // try out player cd with no code.
        cd = new Cd();
        filters[1] = cd;
          
        EntityNodeDto entityNodeDto = EntityNodeDtoConverter.convertToEntityNodeDto(org, filters, null);
        Bl bl = entityNodeDto.getCorrelationOverflow();
        assertTrue(bl.getValue());
        
        assertNull(entityNodeDto.getPlayers());  
    }
    
    @Test 
    public void testPersonPlayerOverflow() {
        Person person = new Person();
        
        for (int i = 0; i < 501; i++) {
            OrganizationalContact contact = new OrganizationalContact();
            person.getOrganizationalContacts().add(contact);
        }
        
        Cd[] filters = new Cd[1];
        Cd cd = new Cd();
        cd.setCode(RoleList.ORGANIZATIONAL_CONTACT.toString());
        filters[0] = cd;
        
        EntityNodeDto entityNodeDto = EntityNodeDtoConverter.convertToEntityNodeDto(person, filters, null);
        Bl bl = entityNodeDto.getCorrelationOverflow();
        assertTrue(bl.getValue());
        
        assertNull(entityNodeDto.getPlayers());  
    }
    
    @Test
    public void testConvertPersonToEntityNodeDto() {
        Person person = new Person();
        person.setLastName(PERSON_LAST_NAME);

        OrganizationalContact orgContact = new OrganizationalContact();
        orgContact.setTitle(ORGANIZATION_CONTACT_TITLE);
        person.getOrganizationalContacts().add(orgContact);

        HealthCareProvider hcProvider = new HealthCareProvider();
        hcProvider.setId(TEST_ID);
        person.getHealthCareProviders().add(hcProvider);
        
        ClinicalResearchStaff clinicalRessearchStaff = new ClinicalResearchStaff();
        clinicalRessearchStaff.setId(TEST_ID);
        person.getClinicalResearchStaff().add(clinicalRessearchStaff);
        
        IdentifiedPerson identifiedPerson = new IdentifiedPerson();
        identifiedPerson.setId(TEST_ID);
        person.getIdentifiedPersons().add(identifiedPerson);
        
        Cd[] playerFilters = new Cd[4];
        Cd cd = new Cd();
        cd.setCode(RoleList.ORGANIZATIONAL_CONTACT.toString());
        playerFilters[0] = cd;
        cd = new Cd();
        cd.setCode(RoleList.HEALTH_CARE_PROVIDER.toString());
        playerFilters[1] = cd;
        cd = new Cd();
        cd.setCode(RoleList.CLINICAL_RESEARCH_STAFF.toString());
        playerFilters[2] = cd;
        cd = new Cd();
        cd.setCode(RoleList.IDENTIFIED_PERSON.toString());
        playerFilters[3] = cd;
  
        EntityNodeDto entityNodeDto = EntityNodeDtoConverter.convertToEntityNodeDto(person, playerFilters, null);
        CorrelationDto[] players = entityNodeDto.getPlayers();

        // check person
        assertTrue(entityNodeDto.getEntityDto() instanceof PersonDTO);
        PersonDTO personDto = (PersonDTO) entityNodeDto.getEntityDto();
        assertEquals(personDto.getName(), PersonNameConverterUtil.convertToEnPn(null, null, PERSON_LAST_NAME, null, null));

        assertTrue(players.length == 4);

        Map<Class, CorrelationDto> corDtoMap = new HashMap<Class, CorrelationDto>();
        
        for(CorrelationDto corDto : players) {
            corDtoMap.put(corDto.getClass(), corDto);
        }

        OrganizationalContactDTO orgContactDto = (OrganizationalContactDTO) corDtoMap.get(OrganizationalContactDTO.class);
        assertNotNull(orgContactDto);
        assertEquals(orgContactDto.getTitle().getValue(), ORGANIZATION_CONTACT_TITLE);

        IdentifiedPersonDTO idpDto = (IdentifiedPersonDTO) corDtoMap.get(IdentifiedPersonDTO.class);
        assertNotNull(idpDto);
        
        ClinicalResearchStaffDTO clinRSDto = (ClinicalResearchStaffDTO) corDtoMap.get(ClinicalResearchStaffDTO.class);
        assertNotNull(clinRSDto);
        
        HealthCareProviderDTO hcpDto = (HealthCareProviderDTO) corDtoMap.get(HealthCareProviderDTO.class);
        assertNotNull(hcpDto);

        playerFilters = new Cd[1];
        cd = new Cd();
        cd.setCode(RoleList.ORGANIZATIONAL_CONTACT.toString());
        playerFilters[0] = cd;

        entityNodeDto = EntityNodeDtoConverter.convertToEntityNodeDto(person, playerFilters, null);
        players = entityNodeDto.getPlayers();

        // check person
        assertTrue(entityNodeDto.getEntityDto() instanceof PersonDTO);
        personDto = (PersonDTO) entityNodeDto.getEntityDto();
        assertEquals(personDto.getName(), PersonNameConverterUtil.convertToEnPn(null, null, PERSON_LAST_NAME, null, null));

        assertTrue(players.length == 1);
  
        entityNodeDto = EntityNodeDtoConverter.convertToEntityNodeDto(person, null, null);
        players = entityNodeDto.getPlayers();

        assertTrue(players.length == 0);
        
    }
    
}
