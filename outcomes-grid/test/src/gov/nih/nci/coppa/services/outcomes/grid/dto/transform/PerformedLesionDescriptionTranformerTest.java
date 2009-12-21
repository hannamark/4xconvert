package gov.nih.nci.coppa.services.outcomes.grid.dto.transform;

import static org.junit.Assert.assertEquals;

import org.iso._21090.PQ;

import gov.nih.nci.accrual.dto.PerformedLesionDescriptionDto;
import gov.nih.nci.coppa.iso.Bl;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Int;
import gov.nih.nci.coppa.iso.Pq;
import gov.nih.nci.coppa.services.grid.dto.transform.AbstractTransformerTestBase;
import gov.nih.nci.coppa.services.grid.dto.transform.DtoTransformException;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.BLTransformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.IITransformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.INTTransformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.PQTransformer;
import gov.nih.nci.coppa.services.outcomes.PerformedLesionDescription;

public class PerformedLesionDescriptionTranformerTest extends 
AbstractTransformerTestBase<PerformedLesionDescriptionTransformer, PerformedLesionDescription, PerformedLesionDescriptionDto> {

    private Bl evaluableIndicator = new Bl();
    {
        evaluableIndicator.setValue(false);
    }
    private Int lesionNumber = new Int();
    {
        lesionNumber.setValue(18);
    }
    private Bl measurableIndicator = new Bl();
    {
        measurableIndicator.setValue(true);
    }
    private Pq longestDiameter = new Pq();
    {
        longestDiameter.setUnit("diam unit");
    }
    
    PerformedObservationResultTestHelper helper = new PerformedObservationResultTestHelper();
    
    @Override
    public PerformedLesionDescriptionDto makeDtoSimple() throws DtoTransformException {
        PerformedLesionDescriptionDto dto = new PerformedLesionDescriptionDto();
        helper.makeDtoSimple(dto);
        dto.setEvaluableIndicator(evaluableIndicator);
        dto.setLesionNumber(lesionNumber);
        dto.setMeasurableIndicator(measurableIndicator);
        dto.setLongestDiameter(longestDiameter);
        return dto;
    }

    @Override
    public PerformedLesionDescription makeXmlSimple() throws DtoTransformException {
        PerformedLesionDescription xml = new PerformedLesionDescription();
        helper.makeXmlSimple(xml);

        xml.setEvaluableIndicator(BLTransformer.INSTANCE.toXml(evaluableIndicator));
        xml.setLesionNumber(INTTransformer.INSTANCE.toXml(lesionNumber));
        xml.setMeasurableIndicator(BLTransformer.INSTANCE.toXml(measurableIndicator));
        xml.setLongestDiameter(PQTransformer.INSTANCE.toXml(longestDiameter));
        return xml;     
    }

    @Override
    public void verifyDtoSimple(PerformedLesionDescriptionDto dto) throws DtoTransformException {
        assertEquals(dto.getEvaluableIndicator().getValue(), evaluableIndicator.getValue());
        assertEquals(dto.getLesionNumber().getValue(), lesionNumber.getValue());
        assertEquals(dto.getMeasurableIndicator().getValue(), measurableIndicator.getValue());
        assertEquals(dto.getLongestDiameter().getUnit(), longestDiameter.getUnit());
    }

    @Override
    public void verifyXmlSimple(PerformedLesionDescription xml) throws DtoTransformException {
        assertEquals(xml.getEvaluableIndicator().isValue(), evaluableIndicator.getValue());
        assertEquals(xml.getLesionNumber().getValue(), lesionNumber.getValue());
        assertEquals(xml.getMeasurableIndicator().isValue(), measurableIndicator.getValue());
        assertEquals(xml.getLongestDiameter().getUnit(), longestDiameter.getUnit());
    }
}