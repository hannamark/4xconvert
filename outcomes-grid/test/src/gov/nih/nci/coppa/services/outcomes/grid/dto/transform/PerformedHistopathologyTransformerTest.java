package gov.nih.nci.coppa.services.outcomes.grid.dto.transform;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.accrual.dto.PerformedHistopathologyDto;
import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.St;
import gov.nih.nci.coppa.services.grid.dto.transform.AbstractTransformerTestBase;
import gov.nih.nci.coppa.services.grid.dto.transform.DtoTransformException;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.CDTransformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.STTransformer;
import gov.nih.nci.coppa.services.outcomes.PerformedHistopathology;

public class PerformedHistopathologyTransformerTest extends 
AbstractTransformerTestBase<PerformedHistopathologyTransformer, PerformedHistopathology, PerformedHistopathologyDto> {

    private Cd gradeCode = new Cd();
    {
        gradeCode.setCode("grade code");
    }
    private St description = new St();
    {
        description.setValue("a description");
    }
    
    PerformedObservationResultTestHelper helper = new PerformedObservationResultTestHelper();
    
    @Override
    public PerformedHistopathologyDto makeDtoSimple() throws DtoTransformException {
        PerformedHistopathologyDto dto = new PerformedHistopathologyDto();
        helper.makeDtoSimple(dto);
        dto.setGradeCode(gradeCode);
        dto.setDescription(description);
        
        return dto;
    }

    @Override
    public PerformedHistopathology makeXmlSimple() throws DtoTransformException {
        PerformedHistopathology xml = new PerformedHistopathology();
        helper.makeXmlSimple(xml);
        xml.setGradeCode(CDTransformer.INSTANCE.toXml(gradeCode));
        xml.setDescription(STTransformer.INSTANCE.toXml(description));

        return xml;     
    }

    @Override
    public void verifyDtoSimple(PerformedHistopathologyDto dto) throws DtoTransformException {
        assertEquals(dto.getGradeCode().getCode(), gradeCode.getCode());
        assertEquals(dto.getDescription().getValue(), description.getValue());
    }

    @Override
    public void verifyXmlSimple(PerformedHistopathology xml) throws DtoTransformException {
        assertEquals(xml.getGradeCode().getCode(), gradeCode.getCode());
        assertEquals(xml.getDescription().getValue(), description.getValue());
    }
}