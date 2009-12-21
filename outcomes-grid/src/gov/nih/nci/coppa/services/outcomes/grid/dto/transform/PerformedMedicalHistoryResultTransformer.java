package gov.nih.nci.coppa.services.outcomes.grid.dto.transform;

import gov.nih.nci.accrual.dto.PerformedMedicalHistoryResultDto;
import gov.nih.nci.coppa.services.grid.dto.transform.DtoTransformException;
import gov.nih.nci.coppa.services.grid.dto.transform.Transformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.STTransformer;
import gov.nih.nci.coppa.services.outcomes.PerformedMedicalHistoryResult;

/**
 * Transformer for PerformedMedicalHistoryResult.
 * 
 * @author ludetc
 *
 */
public class PerformedMedicalHistoryResultTransformer extends 
PerformedObservationResultTransformer<PerformedMedicalHistoryResult, PerformedMedicalHistoryResultDto>
implements Transformer<PerformedMedicalHistoryResult, PerformedMedicalHistoryResultDto>
{
    /**
     * Public singleton.
     */
    public static final PerformedMedicalHistoryResultTransformer INSTANCE = 
        new PerformedMedicalHistoryResultTransformer();

    
    /**
     * {@inheritDoc}
     */
    public PerformedMedicalHistoryResultDto toDto(PerformedMedicalHistoryResult input) throws DtoTransformException {
        PerformedMedicalHistoryResultDto dto = super.toDto(input);
        if (dto == null) {
            return null;
        }

        dto.setDescription(STTransformer.INSTANCE.toDto(input.getDescription()));

        return dto;
    }

    /**
     * {@inheritDoc}
     */
    public PerformedMedicalHistoryResult toXml(PerformedMedicalHistoryResultDto input) throws DtoTransformException {
        PerformedMedicalHistoryResult xml = super.toXml(input);
        if (xml == null) {
            return null;
        }

        xml.setDescription(STTransformer.INSTANCE.toXml(input.getDescription()));
        
        return xml;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    protected PerformedMedicalHistoryResultDto newDto() {
        return new PerformedMedicalHistoryResultDto();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PerformedMedicalHistoryResult newXml() {
        return new PerformedMedicalHistoryResult();
    }

    /**
     * {@inheritDoc}
     */
    public PerformedMedicalHistoryResult[] createXmlArray(int size) throws DtoTransformException {
        return new PerformedMedicalHistoryResult[size];
    }
}
