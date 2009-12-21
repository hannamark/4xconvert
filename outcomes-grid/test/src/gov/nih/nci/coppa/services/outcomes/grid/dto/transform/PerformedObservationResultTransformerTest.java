package gov.nih.nci.coppa.services.outcomes.grid.dto.transform;

import gov.nih.nci.accrual.dto.PerformedObservationResultDto;
import gov.nih.nci.coppa.services.grid.dto.transform.AbstractTransformerTestBase;
import gov.nih.nci.coppa.services.grid.dto.transform.DtoTransformException;
import gov.nih.nci.coppa.services.outcomes.PerformedObservationResult;

public class PerformedObservationResultTransformerTest extends 
    AbstractTransformerTestBase<PerformedObservationResultTransformer<PerformedObservationResult, PerformedObservationResultDto>
        , PerformedObservationResult, PerformedObservationResultDto> {

    PerformedObservationResultTestHelper helper = new PerformedObservationResultTestHelper();
    
    @Override
    public PerformedObservationResultDto makeDtoSimple() throws DtoTransformException {
        PerformedObservationResultDto dto = new PerformedObservationResultDto();         
        helper.makeDtoSimple(dto);
        return dto;
    }

    @Override
    public PerformedObservationResult makeXmlSimple() throws DtoTransformException {
        PerformedObservationResult xml = new PerformedObservationResult();
        helper.makeXmlSimple(xml);
        return xml;
    }

    @Override
    public void verifyDtoSimple(PerformedObservationResultDto dto) throws DtoTransformException {
        helper.verifyDtoSimple(dto);
    }

    @Override
    public void verifyXmlSimple(PerformedObservationResult xml) throws DtoTransformException {
         helper.verifyXmlSimple(xml);
    }
    
}
