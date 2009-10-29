package gov.nih.nci.coppa.po.grid.dto.transform.po;

import gov.nih.nci.coppa.po.ClinicalResearchStaff;
import gov.nih.nci.coppa.po.Correlation;
import gov.nih.nci.coppa.po.EntityNode;
import gov.nih.nci.coppa.po.HealthCareFacility;
import gov.nih.nci.coppa.po.HealthCareProvider;
import gov.nih.nci.coppa.po.Organization;
import gov.nih.nci.coppa.po.ResearchOrganization;
import gov.nih.nci.coppa.services.grid.dto.transform.AbstractTransformerTestBase;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.BLTransformerTest;
import gov.nih.nci.services.CorrelationDto;
import gov.nih.nci.services.EntityNodeDto;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.correlation.HealthCareProviderDTO;
import gov.nih.nci.services.correlation.ResearchOrganizationDTO;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.util.ArrayList;
import java.util.List;

public class EntityNodeTransformerTest extends
     AbstractTransformerTestBase <EntityNodeTransformer , EntityNode ,EntityNodeDto> {
    
    @Override
    public EntityNodeDto makeDtoSimple() {
        EntityNodeDto corrDto = new EntityNodeDto();
        corrDto.setCorrelationOverflow(new BLTransformerTest().makeDtoSimple());
        corrDto.setEntityDto(new OrganizationTransformerTest().makeDtoSimple());
        CorrelationDto[] players = new CorrelationDto[2];
        players[0] = new ResearchOrganizationTransformerTest().makeDtoSimple();
        players[1] = new HealthCareFacilityTransformerTest().makeDtoSimple();     
        corrDto.setPlayers(players);
        
        CorrelationDto[] scopers = new CorrelationDto[2];
        scopers[0] = new HealthCareProviderTransformerTest().makeDtoSimple();
        scopers[1] = new ClinicalResearchStaffTransformerTest().makeDtoSimple(); 
        corrDto.setScopers(scopers);
        return corrDto;
    }

    @Override
    public EntityNode makeXmlSimple() {
    
        EntityNode corr = new EntityNode();
        corr.setCorrelationOverflow(new BLTransformerTest().makeXmlSimple());
        corr.setEntity(new OrganizationTransformerTest().makeXmlSimple());
        List<Correlation> players = new ArrayList<Correlation>();
        players.add(new ResearchOrganizationTransformerTest().makeXmlSimple());
        players.add(new HealthCareFacilityTransformerTest().makeXmlSimple());     
        corr.getPlayers().addAll(players);
        
        List<Correlation> scopers = new ArrayList<Correlation>();
        scopers.add(new HealthCareProviderTransformerTest().makeXmlSimple());
        scopers.add(new ClinicalResearchStaffTransformerTest().makeXmlSimple()); 
        corr.getScopers().addAll(scopers);
        return corr;
    }

    @Override
    public void verifyDtoSimple(EntityNodeDto x) {
        
        new OrganizationTransformerTest().verifyDtoSimple((OrganizationDTO) x.getEntityDto());
        
        new HealthCareProviderTransformerTest().verifyDtoSimple((HealthCareProviderDTO) x.getScopers()[0]);
        new ClinicalResearchStaffTransformerTest().verifyDtoSimple((ClinicalResearchStaffDTO) x.getScopers()[1]);
        
        new ResearchOrganizationTransformerTest().verifyDtoSimple((ResearchOrganizationDTO) x.getPlayers()[0]);
        new HealthCareFacilityTransformerTest().verifyDtoSimple((HealthCareFacilityDTO) x.getScopers()[0]);
        
        new BLTransformerTest().verifyDtoSimple(x.getCorrelationOverflow());
    }

    @Override
    public void verifyXmlSimple(EntityNode x) {

        new OrganizationTransformerTest().verifyXmlSimple((Organization) x.getEntity());
    
        new HealthCareProviderTransformerTest().verifyXmlSimple((HealthCareProvider) x.getScopers().get(0));
        new ClinicalResearchStaffTransformerTest().verifyXmlSimple((ClinicalResearchStaff) x.getScopers().get(1));
        
        new ResearchOrganizationTransformerTest().verifyXmlSimple((ResearchOrganization) x.getPlayers().get(0));
        new HealthCareFacilityTransformerTest().verifyXmlSimple((HealthCareFacility) x.getScopers().get(0));
        
        new BLTransformerTest().verifyXmlSimple(x.getCorrelationOverflow());
    }

}
