package gov.nih.nci.po.webservices.convert.bridg;

import gov.nih.nci.coppa.po.ClinicalResearchStaff;
import gov.nih.nci.coppa.po.Correlation;
import gov.nih.nci.coppa.po.CorrelationType;
import gov.nih.nci.coppa.po.EntityNode;
import gov.nih.nci.coppa.po.EntityType;
import gov.nih.nci.coppa.po.HealthCareFacility;
import gov.nih.nci.coppa.po.Organization;
import gov.nih.nci.iso21090.grid.dto.transform.AbstractTransformerTestBase;
import gov.nih.nci.iso21090.grid.dto.transform.iso.BLTransformerTest;
import gov.nih.nci.services.CorrelationDto;
import gov.nih.nci.services.EntityNodeDto;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.util.ArrayList;
import java.util.List;

public class EntityNodeTransformerTest extends 
    AbstractTransformerTestBase<EntityNodeTransformer, EntityNode , EntityNodeDto> {
    
    private EntityNode createNode() {
        EntityNode corr = new EntityNode();
        corr.setCorrelationOverflow(new BLTransformerTest().makeXmlSimple());
        EntityType entType = new EntityType();
        entType.getContent().add(new OrganizationTransformerTest().makeXmlSimple());
        corr.setEntity(entType);
        List<Correlation> players = new ArrayList<Correlation>();
        players.add(new HealthCareFacilityTransformerTest().makeXmlSimple());
        players.add(new HealthCareFacilityTransformerTest().makeXmlSimple());
        CorrelationType playerType = new CorrelationType();
        playerType.getContent().addAll(players);
        corr.setPlayers(playerType);
        
        List<Correlation> scopers = new ArrayList<Correlation>();
        scopers.add(new ClinicalResearchStaffTransformerTest().makeXmlSimple());
        scopers.add(new ClinicalResearchStaffTransformerTest().makeXmlSimple());
        CorrelationType scopeType = new CorrelationType();
        scopeType.getContent().addAll(scopers);
        corr.setScopers(scopeType); 
        return corr;
    }
    
    private void verifyNodeDto(EntityNodeDto x) {
        new OrganizationTransformerTest().verifyDtoSimple((OrganizationDTO) x.getEntityDto());
      
        new ClinicalResearchStaffTransformerTest().verifyDtoSimple((ClinicalResearchStaffDTO) x.getScopers()[0]);
        new ClinicalResearchStaffTransformerTest().verifyDtoSimple((ClinicalResearchStaffDTO) x.getScopers()[1]);
        
        new HealthCareFacilityTransformerTest().verifyDtoSimple((HealthCareFacilityDTO) x.getPlayers()[0]);
        new HealthCareFacilityTransformerTest().verifyDtoSimple((HealthCareFacilityDTO) x.getPlayers()[1]);
        
        new BLTransformerTest().verifyDtoSimple(x.getCorrelationOverflow());
    }
    
    private EntityNodeDto createNodeDto() {
        EntityNodeDto corrDto = new EntityNodeDto();
        corrDto.setCorrelationOverflow(new BLTransformerTest().makeDtoSimple());
        corrDto.setEntityDto(new OrganizationTransformerTest().makeDtoSimple());
        
        CorrelationDto[] players = new CorrelationDto[2];
        players[0] = new HealthCareFacilityTransformerTest().makeDtoSimple();
        players[1] = new HealthCareFacilityTransformerTest().makeDtoSimple();     
        corrDto.setPlayers(players);
        
        CorrelationDto[] scopers = new CorrelationDto[2];
        scopers[0] = new ClinicalResearchStaffTransformerTest().makeDtoSimple();
        scopers[1] = new ClinicalResearchStaffTransformerTest().makeDtoSimple(); 
        corrDto.setScopers(scopers);
        
        return corrDto; 
    }
    
   
    
    private void verifyNode(EntityNode x) {
        new OrganizationTransformerTest().verifyXmlSimple((Organization) x.getEntity().getContent().get(0));
       
        new ClinicalResearchStaffTransformerTest().verifyXmlSimple((ClinicalResearchStaff) 
                x.getScopers().getContent().get(0));
        new ClinicalResearchStaffTransformerTest().verifyXmlSimple((ClinicalResearchStaff) 
                x.getScopers().getContent().get(1));
        
        new HealthCareFacilityTransformerTest().verifyXmlSimple((HealthCareFacility) 
                x.getPlayers().getContent().get(0));
        new HealthCareFacilityTransformerTest().verifyXmlSimple((HealthCareFacility) 
                x.getPlayers().getContent().get(1));
               
        new BLTransformerTest().verifyXmlSimple(x.getCorrelationOverflow());
    }

    
    
    @Override
    public EntityNodeDto makeDtoSimple() {
        return createNodeDto();
    }


    @Override
    public EntityNode makeXmlSimple() {
        return createNode();
    }

    @Override
    public void verifyDtoSimple(EntityNodeDto arg0) {
        verifyNodeDto(arg0);
    }

    @Override
    public void verifyXmlSimple(EntityNode arg0) {
        verifyNode(arg0);        
    }
   

}
