package gov.nih.nci.coppa.services.outcomes.grid.dto.transform;

import gov.nih.nci.accrual.dto.PerformedClinicalResultDto;
import gov.nih.nci.coppa.services.grid.dto.transform.DtoTransformException;
import gov.nih.nci.coppa.services.grid.dto.transform.Transformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.CDTransformer;
import gov.nih.nci.coppa.services.outcomes.PerformedClinicalResult;

/**
 * Transformer for PerformedClinicalResult.
 * 
 * @author ludetc
 *
 */
public class PerformedClinicalResultTransformer  extends 
PerformedObservationResultTransformer<PerformedClinicalResult, PerformedClinicalResultDto>
implements Transformer<PerformedClinicalResult, PerformedClinicalResultDto> {
    /**
     * Public singleton.
     */
    public static final PerformedClinicalResultTransformer INSTANCE = new PerformedClinicalResultTransformer();

    /**
     * {@inheritDoc}
     */
    public PerformedClinicalResultDto toDto(PerformedClinicalResult input) throws DtoTransformException {
        PerformedClinicalResultDto dto = super.toDto(input);
        if (dto == null) {
            return null;
        }

        dto.setStageCodingSystem(CDTransformer.INSTANCE.toDto(input.getStageCodingSystem()));

        return dto;
    }

    /**
     * {@inheritDoc}
     */
    public PerformedClinicalResult toXml(PerformedClinicalResultDto input) throws DtoTransformException {
        PerformedClinicalResult xml = super.toXml(input);
        if (xml == null) {
            return null;
        }

        xml.setStageCodingSystem(CDTransformer.INSTANCE.toXml(input.getStageCodingSystem()));

        return xml;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    protected PerformedClinicalResultDto newDto() {
        return new PerformedClinicalResultDto();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PerformedClinicalResult newXml() {
        return new PerformedClinicalResult();
    }

    /**
     * {@inheritDoc}
     */
    public PerformedClinicalResult[] createXmlArray(int size) throws DtoTransformException {
        return new PerformedClinicalResult[size];
    }
}
