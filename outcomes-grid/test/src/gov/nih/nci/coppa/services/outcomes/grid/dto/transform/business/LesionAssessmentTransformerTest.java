package gov.nih.nci.coppa.services.outcomes.grid.dto.transform.business;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.coppa.services.outcomes.business.LesionAssessment;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Pq;
import gov.nih.nci.iso21090.Ts;
import gov.nih.nci.iso21090.grid.dto.transform.AbstractTransformerTestBase;
import gov.nih.nci.iso21090.grid.dto.transform.DtoTransformException;
import gov.nih.nci.iso21090.grid.dto.transform.iso.CDTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.IITransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.PQTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.TSTransformer;
import gov.nih.nci.outcomes.svc.dto.LesionAssessmentSvcDto;

import java.util.Date;

public class LesionAssessmentTransformerTest extends 
    AbstractTransformerTestBase<LesionAssessmentTransformer, LesionAssessment, LesionAssessmentSvcDto> {

    Ii lesionNum = new Ii();
    {
        lesionNum.setRoot("1.1.1");
        lesionNum.setIdentifierName("Lesion Num");
        lesionNum.setExtension("1");
    }

    Ii imageSerId = new Ii();
    {
        imageSerId.setRoot("1.1.2");
        imageSerId.setIdentifierName("Image Ser Id");
        imageSerId.setExtension("2");
    }

    Ii imageId = new Ii();
    {
        imageId.setRoot("1.1.3");
        imageId.setIdentifierName("Image Id");
        imageId.setExtension("3");
    }

    Ii treatRegId = new Ii();
    {
        imageId.setRoot("1.1.4");
        imageId.setIdentifierName("Treat Reg Id");
        imageId.setExtension("4");
    }
    
    Cd lesionSite = new Cd();
    {
        lesionSite.setCode("lesion site code");
    }

    Cd mesEvalDisType = new Cd();
    {
        mesEvalDisType.setCode("Mes Eval Dis Type");
    }

    Cd lesionMesMeth = new Cd();
    {
        lesionMesMeth.setCode("Lesion Mes Meth");
    }
    
    Pq lesionLongDiam = new Pq();
    {
        lesionLongDiam.setUnit("lesion long diam unit");
    }
    
    Ts clinAssessDate = new Ts();
    {
        clinAssessDate.setValue(new Date());
    }
    
    @Override
    public LesionAssessmentSvcDto makeDtoSimple() throws DtoTransformException {
        LesionAssessmentSvcDto dto = new LesionAssessmentSvcDto();
        dto.setClinicalAssessmentDate(clinAssessDate);
        dto.setImageIdentifier(imageId);
        dto.setImageSeriesIdentifier(imageSerId);
        dto.setLesionLongestDiameter(lesionLongDiam);
        dto.setLesionMeasurementMethod(lesionMesMeth);
        dto.setLesionNum(lesionNum);
        dto.setLesionSite(lesionSite);
        dto.setMeasurableEvaluableDiseaseType(mesEvalDisType);
        
        return dto;
    }

    @Override
    public LesionAssessment makeXmlSimple() throws DtoTransformException {
        LesionAssessment xml = new LesionAssessment();
        xml.setClinicalAssessmentDate(TSTransformer.INSTANCE.toXml(clinAssessDate));
        xml.setImageIdentifier(IITransformer.INSTANCE.toXml(imageId));
        xml.setImageSeriesIdentifier(IITransformer.INSTANCE.toXml(imageSerId));
        xml.setLesionLongestDiameter(PQTransformer.INSTANCE.toXml(lesionLongDiam));
        xml.setLesionMeasurementMethod(CDTransformer.INSTANCE.toXml(lesionMesMeth));
        xml.setLesionNum(IITransformer.INSTANCE.toXml(lesionNum));
        xml.setLesionSite(CDTransformer.INSTANCE.toXml(lesionSite));
        xml.setMeasurableEvaluableDiseaseType(CDTransformer.INSTANCE.toXml(mesEvalDisType));        
        
        return xml;
    }

    @Override
    public void verifyDtoSimple(LesionAssessmentSvcDto dto) throws DtoTransformException {
        assertEquals(dto.getImageIdentifier().getExtension(), imageId.getExtension());        
        assertEquals(dto.getImageSeriesIdentifier().getExtension(), imageSerId.getExtension());        
        assertEquals(dto.getLesionNum().getExtension(), lesionNum.getExtension());
        assertEquals(dto.getLesionSite().getCode(), lesionSite.getCode());
        assertEquals(dto.getLesionMeasurementMethod().getCode(), lesionMesMeth.getCode());
        assertEquals(dto.getMeasurableEvaluableDiseaseType().getCode(), mesEvalDisType.getCode());
        assertEquals(dto.getLesionLongestDiameter().getUnit(), lesionLongDiam.getUnit());
        assertEquals(dto.getClinicalAssessmentDate(), clinAssessDate);
    }

    @Override
    public void verifyXmlSimple(LesionAssessment xml) throws DtoTransformException {
        assertEquals(xml.getImageIdentifier().getExtension(), imageId.getExtension());        
        assertEquals(xml.getImageSeriesIdentifier().getExtension(), imageSerId.getExtension());        
        assertEquals(xml.getLesionNum().getExtension(), lesionNum.getExtension());
        assertEquals(xml.getLesionSite().getCode(), lesionSite.getCode());
        assertEquals(xml.getLesionMeasurementMethod().getCode(), lesionMesMeth.getCode());
        assertEquals(xml.getMeasurableEvaluableDiseaseType().getCode(), mesEvalDisType.getCode());
        assertEquals(xml.getLesionLongestDiameter().getUnit(), lesionLongDiam.getUnit());
        assertEquals(xml.getClinicalAssessmentDate().getValue(), TSTransformer.INSTANCE.toXml(clinAssessDate).getValue());
    }


}
