package gov.nih.nci.coppa.po.grid.dto.transform.po;

import gov.nih.nci.coppa.po.CorrelationNode;
import gov.nih.nci.coppa.po.HealthCareProvider;
import gov.nih.nci.coppa.po.Organization;
import gov.nih.nci.coppa.po.Person;
import gov.nih.nci.coppa.services.grid.dto.transform.AbstractTransformerTestBase;
import gov.nih.nci.services.correlation.CorrelationNodeDTO;
import gov.nih.nci.services.correlation.HealthCareProviderDTO;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

public class CorrelationNodeTransformerTest extends
     AbstractTransformerTestBase <CorrelationNodeTransformer , CorrelationNode ,CorrelationNodeDTO> {
    
    @Override
    public CorrelationNodeDTO makeDtoSimple() {
        CorrelationNodeDTO corrDto = new CorrelationNodeDTO();
        corrDto.setCorrelation(new HealthCareProviderTransformerTest().makeDtoSimple());
        corrDto.setPlayer(new PersonTransformerTest().makeDtoSimple());
        corrDto.setScoper(new OrganizationTransformerTest().makeDtoSimple());
        return corrDto;
    }

    @Override
    public CorrelationNode makeXmlSimple() {
    
        CorrelationNode corr = new CorrelationNode();
        corr.setCorrelation(new HealthCareProviderTransformerTest().makeXmlSimple());
        corr.setPlayer(new PersonTransformerTest().makeXmlSimple());
        corr.setScoper(new OrganizationTransformerTest().makeXmlSimple());
        return corr;
    }

    @Override
    public void verifyDtoSimple(CorrelationNodeDTO x) {
        new HealthCareProviderTransformerTest().verifyDtoSimple((HealthCareProviderDTO) x.getCorrelation());
        new PersonTransformerTest().verifyDtoSimple((PersonDTO) x.getPlayer());
        new OrganizationTransformerTest().verifyDtoSimple((OrganizationDTO) x.getScoper());
    }

    @Override
    public void verifyXmlSimple(CorrelationNode x) {
        new HealthCareProviderTransformerTest().verifyXmlSimple((HealthCareProvider) x.getCorrelation());
        new PersonTransformerTest().verifyXmlSimple((Person) x.getPlayer());
        new OrganizationTransformerTest().verifyXmlSimple((Organization) x.getScoper());
    }

}
