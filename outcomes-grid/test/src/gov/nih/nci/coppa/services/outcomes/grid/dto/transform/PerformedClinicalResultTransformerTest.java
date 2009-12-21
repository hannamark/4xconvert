package gov.nih.nci.coppa.services.outcomes.grid.dto.transform;

import static org.junit.Assert.assertEquals;


import gov.nih.nci.accrual.dto.PerformedClinicalResultDto;
import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.services.grid.dto.transform.AbstractTransformerTestBase;
import gov.nih.nci.coppa.services.grid.dto.transform.DtoTransformException;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.CDTransformer;
import gov.nih.nci.coppa.services.outcomes.PerformedClinicalResult;

public class PerformedClinicalResultTransformerTest  extends 
AbstractTransformerTestBase<PerformedClinicalResultTransformer, PerformedClinicalResult, PerformedClinicalResultDto> {

    private Cd stageCodingSystem = new Cd();
    {
        stageCodingSystem.setCode("stage coding system code");
    }
    
    PerformedObservationResultTestHelper helper = new PerformedObservationResultTestHelper();
    
    @Override
    public PerformedClinicalResultDto makeDtoSimple() throws DtoTransformException {
        PerformedClinicalResultDto dto = new PerformedClinicalResultDto();
        helper.makeDtoSimple(dto);
        dto.setStageCodingSystem(stageCodingSystem);
        return dto;
    }

    @Override
    public PerformedClinicalResult makeXmlSimple() throws DtoTransformException {
        PerformedClinicalResult xml = new PerformedClinicalResult();
        helper.makeXmlSimple(xml);
        xml.setStageCodingSystem(CDTransformer.INSTANCE.toXml(stageCodingSystem));
        return xml;     
    }

    @Override
    public void verifyDtoSimple(PerformedClinicalResultDto dto) throws DtoTransformException {
        assertEquals(dto.getStageCodingSystem().getCode(), stageCodingSystem.getCode());
    }

    @Override
    public void verifyXmlSimple(PerformedClinicalResult xml) throws DtoTransformException {
        assertEquals(xml.getStageCodingSystem().getCode(), stageCodingSystem.getCode());
    }
}