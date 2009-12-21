package gov.nih.nci.coppa.services.outcomes.grid.dto.transform;

import gov.nih.nci.accrual.dto.PerformedDiagnosisDto;
import gov.nih.nci.coppa.services.grid.dto.transform.AbstractTransformerTestBase;
import gov.nih.nci.coppa.services.grid.dto.transform.DtoTransformException;
import gov.nih.nci.coppa.services.outcomes.PerformedDiagnosis;

public class PerformedDiagnosisTransformerTest extends 
AbstractTransformerTestBase<PerformedDiagnosisTransformer, PerformedDiagnosis, PerformedDiagnosisDto> {

    PerformedObservationResultTestHelper helper = new PerformedObservationResultTestHelper();

    @Override
    public PerformedDiagnosisDto makeDtoSimple() throws DtoTransformException {
        PerformedDiagnosisDto dto = new PerformedDiagnosisDto();
        helper.makeDtoSimple(dto);            
        return dto;
    }

    @Override
    public PerformedDiagnosis makeXmlSimple() throws DtoTransformException {
        PerformedDiagnosis xml = new PerformedDiagnosis();
        helper.makeXmlSimple(xml);
        return xml;     
    }

    @Override
    public void verifyDtoSimple(PerformedDiagnosisDto dto) throws DtoTransformException {
        // nothing to check here. All checked at above level.
    }

    @Override
    public void verifyXmlSimple(PerformedDiagnosis xml) throws DtoTransformException {
        // nothing to check here. All checked at above level.
    }
}