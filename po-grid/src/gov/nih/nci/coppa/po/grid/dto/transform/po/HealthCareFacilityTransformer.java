/**
 *
 */
package gov.nih.nci.coppa.po.grid.dto.transform.po;

import gov.nih.nci.coppa.po.HealthCareFacility;
import gov.nih.nci.coppa.services.grid.dto.transform.DtoTransformException;
import gov.nih.nci.coppa.services.grid.dto.transform.Transformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.CDTransformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.IITransformer;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;

/**
 * Transforms HealthCareFacility instances.
 */
public final class HealthCareFacilityTransformer implements Transformer<HealthCareFacility, HealthCareFacilityDTO> {
    /**
     * Public singleton.
     */
    public static final HealthCareFacilityTransformer INSTANCE = new HealthCareFacilityTransformer();

    private HealthCareFacilityTransformer() {
    }

    /**
     * {@inheritDoc}
     */
    public HealthCareFacilityDTO toDto(HealthCareFacility input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        HealthCareFacilityDTO d = new HealthCareFacilityDTO();
        d.setIdentifier(IITransformer.INSTANCE.toDto(input.getIdentifier()));
        d.setPlayerIdentifier(IITransformer.INSTANCE.toDto(input.getPlayerIdentifier()));
        d.setStatus(CDTransformer.INSTANCE.toDto(input.getStatus()));
        return d;
    }

    /**
     * {@inheritDoc}
     */
    public HealthCareFacility toXml(HealthCareFacilityDTO input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        HealthCareFacility d = new HealthCareFacility();
        d.setIdentifier(IITransformer.INSTANCE.toXml(input.getIdentifier()));
        d.setPlayerIdentifier(IITransformer.INSTANCE.toXml(input.getPlayerIdentifier()));
        d.setStatus(CDTransformer.INSTANCE.toXml(input.getStatus()));
        return d;
    }
}
