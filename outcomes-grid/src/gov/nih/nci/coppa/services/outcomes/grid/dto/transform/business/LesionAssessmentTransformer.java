package gov.nih.nci.coppa.services.outcomes.grid.dto.transform.business;

import gov.nih.nci.coppa.services.outcomes.business.LesionAssessment;
import gov.nih.nci.iso21090.grid.dto.transform.DtoTransformException;
import gov.nih.nci.iso21090.grid.dto.transform.iso.CDTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.IITransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.PQTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.TSTransformer;
import gov.nih.nci.outcomes.svc.dto.LesionAssessmentSvcDto;

/**
 * Lesion Assessment Transformer.
 * 
 * @author ludetc
 *
 */
public class LesionAssessmentTransformer extends BaseTransformer<LesionAssessment, LesionAssessmentSvcDto> {

    /**
     * Singleton.
     */
    public static final LesionAssessmentTransformer INSTANCE = new LesionAssessmentTransformer();
    
    /**
     * {@inheritDoc}
     * 
     * @see gov.nih.nci.coppa.services.outcomes.grid.dto.transform.business.BaseTransformer#newDto()
     */
    @Override
    protected LesionAssessmentSvcDto newDto() {
        return new LesionAssessmentSvcDto();
    }

    /**
     * {@inheritDoc}
     * 
     * @see gov.nih.nci.coppa.services.outcomes.grid.dto.transform.business.BaseTransformer#newXml()
     */
    @Override
    protected LesionAssessment newXml() {
        return new LesionAssessment();
    }
    
    /**
     * {@inheritDoc}
     * 
     * @see gov.nih.nci.coppa.services.grid.dto.transform.Transformer#createXmlArray(int)
     */
    public LesionAssessment[] createXmlArray(int size) throws DtoTransformException {
        return new LesionAssessment[size];
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public LesionAssessmentSvcDto toDto(LesionAssessment xml) throws DtoTransformException {
        LesionAssessmentSvcDto result = super.toDto(xml);
        if (null != result) {
            result.setLesionNum(IITransformer.INSTANCE.toDto(xml.getLesionNum()));
            result.setLesionSite(CDTransformer.INSTANCE.toDto(xml.getLesionSite()));
            result.setMeasurableEvaluableDiseaseType(
                    CDTransformer.INSTANCE.toDto(xml.getMeasurableEvaluableDiseaseType()));
            result.setLesionMeasurementMethod(CDTransformer.INSTANCE.toDto(xml.getLesionMeasurementMethod()));
            result.setImageSeriesIdentifier(IITransformer.INSTANCE.toDto(xml.getImageSeriesIdentifier()));
            result.setImageIdentifier(IITransformer.INSTANCE.toDto(xml.getImageIdentifier()));
            result.setLesionLongestDiameter(PQTransformer.INSTANCE.toDto(xml.getLesionLongestDiameter()));
            result.setClinicalAssessmentDate(TSTransformer.INSTANCE.toDto(xml.getClinicalAssessmentDate()));
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LesionAssessment toXml(LesionAssessmentSvcDto dto) throws DtoTransformException {
        LesionAssessment result = super.toXml(dto);
        if (null != result) {
            result.setLesionNum(IITransformer.INSTANCE.toXml(dto.getLesionNum()));
            result.setLesionSite(CDTransformer.INSTANCE.toXml(dto.getLesionSite()));
            result.setMeasurableEvaluableDiseaseType(
                    CDTransformer.INSTANCE.toXml(dto.getMeasurableEvaluableDiseaseType()));
            result.setLesionMeasurementMethod(CDTransformer.INSTANCE.toXml(dto.getLesionMeasurementMethod()));
            result.setImageSeriesIdentifier(IITransformer.INSTANCE.toXml(dto.getImageSeriesIdentifier()));
            result.setImageIdentifier(IITransformer.INSTANCE.toXml(dto.getImageIdentifier()));
            result.setLesionLongestDiameter(PQTransformer.INSTANCE.toXml(dto.getLesionLongestDiameter()));
            result.setClinicalAssessmentDate(TSTransformer.INSTANCE.toXml(dto.getClinicalAssessmentDate()));  
        }
        return result;
    }

}
