package gov.nih.nci.coppa.services.outcomes.grid.dto.transform;

import gov.nih.nci.accrual.dto.PerformedDiagnosisDto;
import gov.nih.nci.coppa.services.grid.dto.transform.DtoTransformException;
import gov.nih.nci.coppa.services.grid.dto.transform.Transformer;
import gov.nih.nci.coppa.services.outcomes.PerformedDiagnosis;

/**
 * Transformer for Performed Diagnosis.
 * 
 * @author ludetc
 *
 */
public class PerformedDiagnosisTransformer
    extends PerformedObservationResultTransformer<PerformedDiagnosis, PerformedDiagnosisDto>
    implements Transformer<PerformedDiagnosis, PerformedDiagnosisDto> {
    /**
     * Public singleton.
     */
    public static final PerformedDiagnosisTransformer INSTANCE = new PerformedDiagnosisTransformer();
    
    /**
     * {@inheritDoc}
     */
    public PerformedDiagnosisDto toDto(PerformedDiagnosis input) throws DtoTransformException {
        PerformedDiagnosisDto dto = super.toDto(input);
        if (dto == null) {
            return null;
        }
        
        return dto;
    }

    /**
     * {@inheritDoc}
     */
    public PerformedDiagnosis toXml(PerformedDiagnosisDto input) throws DtoTransformException {
        PerformedDiagnosis xml = super.toXml(input);
        if (xml == null) {
            return null;
        }

        return xml;
    }

    
    /**
     * {@inheritDoc}
     */
    @Override
    protected PerformedDiagnosisDto newDto() {
        return new PerformedDiagnosisDto();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PerformedDiagnosis newXml() {
        return new PerformedDiagnosis();
    }

    /**
     * {@inheritDoc}
     */
    public PerformedDiagnosis[] createXmlArray(int size) throws DtoTransformException {
        return new PerformedDiagnosis[size];
    }
}
