package gov.nih.nci.coppa.services.outcomes.grid.dto.transform;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import gov.nih.nci.accrual.dto.PerformedObservationResultDto;
import gov.nih.nci.coppa.iso.Bl;
import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Ivl;
import gov.nih.nci.coppa.iso.St;
import gov.nih.nci.coppa.iso.Ts;
import gov.nih.nci.coppa.services.grid.dto.transform.DtoTransformException;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.BLTransformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.CDTransformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.IITransformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.IITransformerTest;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.IVLTSTransformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.PQTransformerTest;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.STTransformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.TSTransformer;
import gov.nih.nci.coppa.services.outcomes.PerformedObservationResult;
import gov.nih.nci.coppa.services.outcomes.PerformedObservationResultType;

public class PerformedObservationResultTestHelper {

    /**
     * Study Protocol root.
     */
    public static final String STUDY_PROTOCOL_ROOT = "1.2.3";

    /**
     * Study Protocol name.
     */
    public static final String STUDY_PROTOCOL_NAME = "protocol name";

    
    /**
     * Performance Observation Result root.
     */
    public static final String PERFORMANCE_OBSERVATION_RESULT_ROOT = "1.2.3.4";

    /**
     * Performance Observation Result name.
     */
    public static final String PERFORMANCE_OBSERVATION_RESULT_NAME = "Perf Obs Result name";
    
    Cd resultCode = new Cd();
    {
        resultCode.setCode("result code");
    }
    Cd typeCode = new Cd();
    {
        typeCode.setCode("type code");
    }
    Cd unitOfMeasureCode = new Cd();
    {
        unitOfMeasureCode.setCode("u code");
    }
    St resultCodeModifierTextSt = new St();
    {
        resultCodeModifierTextSt.setValue("mod text");
    }
    St resultTextSt = new St();
    {
        resultTextSt.setValue("result text");
    }
    Ts cutoffts = new Ts();
    {
        cutoffts.setValue(new Date());
    }
    Ivl<Ts> ivlTs = new Ivl<Ts>();
    {
        ivlTs.setHigh(cutoffts);
    }
    Bl resultIndicator = new Bl();
    {
        resultIndicator.setValue(false);
    }
    Ii studyProtocolId = new Ii();
    {
        studyProtocolId.setRoot(STUDY_PROTOCOL_ROOT);
        studyProtocolId.setIdentifierName(STUDY_PROTOCOL_NAME);
        studyProtocolId.setExtension("346");
    }
    Ii perfObsId = new Ii();
    {
        perfObsId.setRoot(PERFORMANCE_OBSERVATION_RESULT_ROOT);
        perfObsId.setIdentifierName(PERFORMANCE_OBSERVATION_RESULT_NAME);
        perfObsId.setExtension("177");
    }
    
    public void makeDtoSimple(PerformedObservationResultDto dto) throws DtoTransformException {
        dto.setIdentifier(new IITransformerTest().makeDtoSimple());
        dto.setStudyProtocolIdentifier(studyProtocolId);
        dto.setPerformedObservationIdentifier(perfObsId);
        dto.setResultCode(resultCode);
        dto.setResultCodeModifiedText(resultCodeModifierTextSt);
        dto.setResultDateRange(ivlTs);
        dto.setResultIndicator(resultIndicator);
        dto.setResultQuantity(new PQTransformerTest().makeDtoSimple());
        dto.setResultText(resultTextSt);
        dto.setTypeCode(typeCode);
        dto.setUnitOfMeasureCode(unitOfMeasureCode);        
    }
    
    public void makeXmlSimple(PerformedObservationResultType xml) throws DtoTransformException {
        xml.setIdentifier(new IITransformerTest().makeXmlSimple());
        xml.setStudyProtocolIdentifier(IITransformer.INSTANCE.toXml(studyProtocolId));
        xml.setPerformedObservationIdentifier(IITransformer.INSTANCE.toXml(perfObsId));
        xml.setResultCode(CDTransformer.INSTANCE.toXml(resultCode));
        xml.setResultCodeModifiedText(STTransformer.INSTANCE.toXml(resultCodeModifierTextSt));
        xml.setResultDateRange(IVLTSTransformer.INSTANCE.toXml(ivlTs));
        xml.setResultIndicator(BLTransformer.INSTANCE.toXml(resultIndicator));
        xml.setResultQuantity(new PQTransformerTest().makeXmlSimple());
        xml.setResultText(STTransformer.INSTANCE.toXml(resultTextSt));
        xml.setTypeCode(CDTransformer.INSTANCE.toXml(typeCode));
        xml.setUnitOfMeasureCode(CDTransformer.INSTANCE.toXml(unitOfMeasureCode));
    }
    
    public void verifyDtoSimple(PerformedObservationResultDto dto) {
        assertEquals(dto.getIdentifier().getExtension(), "123");
        assertEquals(dto.getStudyProtocolIdentifier().getExtension(), "346");
        assertEquals(dto.getPerformedObservationIdentifier().getExtension(), "177");
        assertEquals(dto.getResultCode().getCode(), resultCode.getCode());
        assertEquals(dto.getResultCodeModifiedText().getValue(), resultCodeModifierTextSt.getValue());
        assertEquals(dto.getResultDateRange().getHigh(), cutoffts);
        assertEquals(dto.getResultIndicator().getValue(), resultIndicator.getValue());
        assertEquals(dto.getResultQuantity().getUnit(), new PQTransformerTest().makeDtoSimple().getUnit());
        assertEquals(dto.getResultText().getValue(), resultTextSt.getValue());
        assertEquals(dto.getTypeCode().getCode(), typeCode.getCode());
        assertEquals(dto.getUnitOfMeasureCode().getCode(), unitOfMeasureCode.getCode()); 
    }
    
    public void verifyXmlSimple(PerformedObservationResult xml) throws DtoTransformException {
        assertEquals(xml.getIdentifier().getExtension(), "123");
        assertEquals(xml.getStudyProtocolIdentifier().getExtension(), "346");
        assertEquals(xml.getPerformedObservationIdentifier().getExtension(), "177");
        assertEquals(xml.getResultCode().getCode(), resultCode.getCode());
        assertEquals(xml.getResultCodeModifiedText().getValue(), resultCodeModifierTextSt.getValue());
        assertEquals(xml.getResultDateRange().getHigh().getValue(), TSTransformer.INSTANCE.toXml(cutoffts).getValue());
        assertEquals(xml.getResultIndicator().isValue(), resultIndicator.getValue());
        assertEquals(xml.getResultQuantity().getUnit(), new PQTransformerTest().makeXmlSimple().getUnit());
        assertEquals(xml.getResultText().getValue(), resultTextSt.getValue());
        assertEquals(xml.getTypeCode().getCode(), typeCode.getCode());
        assertEquals(xml.getUnitOfMeasureCode().getCode(), unitOfMeasureCode.getCode()); 
    }
    
}
