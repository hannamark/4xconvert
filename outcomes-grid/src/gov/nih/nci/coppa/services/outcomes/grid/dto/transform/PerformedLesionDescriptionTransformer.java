package gov.nih.nci.coppa.services.outcomes.grid.dto.transform;

import gov.nih.nci.accrual.dto.PerformedLesionDescriptionDto;
import gov.nih.nci.coppa.services.grid.dto.transform.DtoTransformException;
import gov.nih.nci.coppa.services.grid.dto.transform.Transformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.BLTransformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.INTTransformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.PQTransformer;
import gov.nih.nci.coppa.services.outcomes.PerformedLesionDescription;

/**
 * Transformer for PerformedLesionDescription.
 * 
 * @author ludetc
 *
 */
public class PerformedLesionDescriptionTransformer extends 
PerformedObservationResultTransformer<PerformedLesionDescription, PerformedLesionDescriptionDto>
implements Transformer<PerformedLesionDescription, PerformedLesionDescriptionDto> {
    /**
     * Public singleton.
     */
    public static final PerformedLesionDescriptionTransformer INSTANCE = new PerformedLesionDescriptionTransformer();

    /**
     * {@inheritDoc}
     */
    public PerformedLesionDescriptionDto toDto(PerformedLesionDescription input) throws DtoTransformException {
        PerformedLesionDescriptionDto dto = super.toDto(input);
        if (dto == null) {
            return null;
        }

        dto.setEvaluableIndicator(BLTransformer.INSTANCE.toDto(input.getEvaluableIndicator()));
        dto.setLesionNumber(INTTransformer.INSTANCE.toDto(input.getLesionNumber()));
        dto.setMeasurableIndicator(BLTransformer.INSTANCE.toDto(input.getMeasurableIndicator()));
        dto.setLongestDiameter(PQTransformer.INSTANCE.toDto(input.getLongestDiameter()));
        
        return dto;
    }

    /**
     * {@inheritDoc}
     */
    public PerformedLesionDescription toXml(PerformedLesionDescriptionDto input) throws DtoTransformException {
        PerformedLesionDescription xml = super.toXml(input);
        if (xml == null) {
            return null;
        }

        xml.setEvaluableIndicator(BLTransformer.INSTANCE.toXml(input.getEvaluableIndicator()));
        xml.setLesionNumber(INTTransformer.INSTANCE.toXml(input.getLesionNumber()));
        xml.setMeasurableIndicator(BLTransformer.INSTANCE.toXml(input.getMeasurableIndicator()));
        xml.setLongestDiameter(PQTransformer.INSTANCE.toXml(input.getLongestDiameter()));
        
        return xml;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    protected PerformedLesionDescriptionDto newDto() {
        return new PerformedLesionDescriptionDto();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PerformedLesionDescription newXml() {
        return new PerformedLesionDescription();
    }

    /**
     * {@inheritDoc}
     */
    public PerformedLesionDescription[] createXmlArray(int size) throws DtoTransformException {
        return new PerformedLesionDescription[size];
    }
}
