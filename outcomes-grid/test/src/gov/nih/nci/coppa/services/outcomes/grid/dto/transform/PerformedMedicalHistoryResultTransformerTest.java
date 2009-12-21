package gov.nih.nci.coppa.services.outcomes.grid.dto.transform;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.accrual.dto.PerformedMedicalHistoryResultDto;
import gov.nih.nci.coppa.iso.St;
import gov.nih.nci.coppa.services.grid.dto.transform.AbstractTransformerTestBase;
import gov.nih.nci.coppa.services.grid.dto.transform.DtoTransformException;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.STTransformer;
import gov.nih.nci.coppa.services.outcomes.PerformedMedicalHistoryResult;

public class PerformedMedicalHistoryResultTransformerTest extends 
AbstractTransformerTestBase<PerformedMedicalHistoryResultTransformer, PerformedMedicalHistoryResult, PerformedMedicalHistoryResultDto> {


    private St description = new St();
    {
        description.setValue("med hist result desc");
    }
    PerformedObservationResultTestHelper helper = new PerformedObservationResultTestHelper();
    
    @Override
    public PerformedMedicalHistoryResultDto makeDtoSimple() throws DtoTransformException {
        PerformedMedicalHistoryResultDto dto = new PerformedMedicalHistoryResultDto();
        helper.makeDtoSimple(dto);
        dto.setDescription(description);
        return dto;
    }

    @Override
    public PerformedMedicalHistoryResult makeXmlSimple() throws DtoTransformException {
        PerformedMedicalHistoryResult xml = new PerformedMedicalHistoryResult();
        helper.makeXmlSimple(xml);
        xml.setDescription(STTransformer.INSTANCE.toXml(description));
        return xml;     
    }

    @Override
    public void verifyDtoSimple(PerformedMedicalHistoryResultDto dto) throws DtoTransformException {
        assertEquals(dto.getDescription().getValue(), description.getValue());
    }
    
    @Override
    public void verifyXmlSimple(PerformedMedicalHistoryResult xml) throws DtoTransformException {
        assertEquals(xml.getDescription().getValue(), description.getValue());
    }
}