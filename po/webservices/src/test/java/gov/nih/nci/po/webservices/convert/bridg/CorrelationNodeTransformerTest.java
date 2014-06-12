package gov.nih.nci.po.webservices.convert.bridg;

import gov.nih.nci.coppa.po.CorrelationNode;
import gov.nih.nci.coppa.po.CorrelationType;
import gov.nih.nci.coppa.po.EntityType;
import gov.nih.nci.coppa.po.HealthCareProvider;
import gov.nih.nci.coppa.po.Organization;
import gov.nih.nci.coppa.po.Person;
import gov.nih.nci.iso21090.grid.dto.transform.AbstractTransformerTestBase;
import gov.nih.nci.services.correlation.CorrelationNodeDTO;
import gov.nih.nci.services.correlation.HealthCareProviderDTO;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

public class CorrelationNodeTransformerTest  extends 
AbstractTransformerTestBase<CorrelationNodeTransformer, CorrelationNode , CorrelationNodeDTO> {
    
  
    private void verifyNode(CorrelationNode x) {
        new HealthCareProviderTransformerTest().verifyXmlSimple((HealthCareProvider) 
                x.getCorrelation().getContent().get(0));
        new PersonTransformerTest().verifyXmlSimple((Person) 
                x.getPlayer().getContent().get(0));
        new OrganizationTransformerTest().verifyXmlSimple((Organization) 
                x.getScoper().getContent().get(0));
    }

    
    private void verifyNodeDto(CorrelationNodeDTO x) {
        new HealthCareProviderTransformerTest().verifyDtoSimple((HealthCareProviderDTO) x.getCorrelation());
        new PersonTransformerTest().verifyDtoSimple((PersonDTO) x.getPlayer());
        new OrganizationTransformerTest().verifyDtoSimple((OrganizationDTO) x.getScoper());
    }
    
    private CorrelationNode createNode() {
        CorrelationNode corr = new CorrelationNode();
        CorrelationType corrType = new CorrelationType();
        corrType.getContent().add(new HealthCareProviderTransformerTest().makeXmlSimple());
        corr.setCorrelation(corrType);
        EntityType entType = new EntityType();
        entType.getContent().add(new PersonTransformerTest().makeXmlSimple());
        corr.setPlayer(entType);
        EntityType scopType = new EntityType();
        scopType.getContent().add(new OrganizationTransformerTest().makeXmlSimple());
        corr.setScoper(scopType);
        return corr;
    }
    
    private CorrelationNodeDTO createNodeDto() {
        CorrelationNodeDTO corrDto = new CorrelationNodeDTO();
        corrDto.setCorrelation(new HealthCareProviderTransformerTest().makeDtoSimple());
        corrDto.setPlayer(new PersonTransformerTest().makeDtoSimple());
        corrDto.setScoper(new OrganizationTransformerTest().makeDtoSimple());
        return corrDto;
    }


    @Override
    public CorrelationNodeDTO makeDtoSimple() {
        return createNodeDto();
    }


    @Override
    public CorrelationNode makeXmlSimple() {
        return createNode();
    }

    @Override
    public void verifyDtoSimple(CorrelationNodeDTO arg0) {
        verifyNodeDto(arg0);
    }

    @Override
    public void verifyXmlSimple(CorrelationNode arg0) {
        verifyNode(arg0);
    }
    
}
