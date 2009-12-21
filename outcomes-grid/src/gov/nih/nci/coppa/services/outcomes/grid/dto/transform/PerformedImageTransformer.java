package gov.nih.nci.coppa.services.outcomes.grid.dto.transform;

import gov.nih.nci.accrual.dto.PerformedImageDto;
import gov.nih.nci.coppa.services.grid.dto.transform.DtoTransformException;
import gov.nih.nci.coppa.services.grid.dto.transform.Transformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.IITransformer;
import gov.nih.nci.coppa.services.outcomes.PerformedImage;

/**
 * Transformer for PerformedImage.
 * @author ludetc
 *
 */
public class PerformedImageTransformer extends 
PerformedObservationResultTransformer<PerformedImage, PerformedImageDto>
implements Transformer<PerformedImage, PerformedImageDto> {
    /**
     * Public singleton.
     */
    public static final PerformedImageTransformer INSTANCE = new PerformedImageTransformer();

    /**
     * {@inheritDoc}
     */
    public PerformedImageDto toDto(PerformedImage input) throws DtoTransformException {
        PerformedImageDto dto = super.toDto(input);
        if (dto == null) {
            return null;
        }

        dto.setImageIdentifier(IITransformer.INSTANCE.toDto(input.getImageIdentifier()));
        dto.setSeriesIdentifier(IITransformer.INSTANCE.toDto(input.getSeriesIdentifier()));

        return dto;
    }

    /**
     * {@inheritDoc}
     */
    public PerformedImage toXml(PerformedImageDto input) throws DtoTransformException {
        PerformedImage xml = super.toXml(input);
        if (xml == null) {
            return null;
        }

        xml.setImageIdentifier(IITransformer.INSTANCE.toXml(input.getImageIdentifier()));
        xml.setSeriesIdentifier(IITransformer.INSTANCE.toXml(input.getSeriesIdentifier()));

        return xml;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    protected PerformedImageDto newDto() {
        return new PerformedImageDto();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PerformedImage newXml() {
        return new PerformedImage();
    }

    /**
     * {@inheritDoc}
     */
    public PerformedImage[] createXmlArray(int size) throws DtoTransformException {
        return new PerformedImage[size];
    }
}
