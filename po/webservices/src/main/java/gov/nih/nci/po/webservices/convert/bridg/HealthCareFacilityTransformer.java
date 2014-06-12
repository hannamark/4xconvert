/**
 *
 */
package gov.nih.nci.po.webservices.convert.bridg;

import gov.nih.nci.coppa.po.HealthCareFacility;
import gov.nih.nci.iso21090.grid.dto.transform.AbstractTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.DtoTransformException;
import gov.nih.nci.iso21090.grid.dto.transform.Transformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.CDTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.DSETADTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.DSETIITransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.DSETTELTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.ENTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.IITransformer;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;

/**
 * Transforms HealthCareFacility instances.
 */
public final class HealthCareFacilityTransformer
    extends AbstractTransformer<HealthCareFacility, HealthCareFacilityDTO>
    implements Transformer<HealthCareFacility, HealthCareFacilityDTO> {
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
        d.setIdentifier(DSETIITransformer.INSTANCE.toDto(input.getIdentifier()));
        d.setPlayerIdentifier(IITransformer.INSTANCE.toDto(input.getPlayerIdentifier()));
        d.setStatus(CDTransformer.INSTANCE.toDto(input.getStatus()));
        d.setName(ENTransformer.ENON_INSTANCE.toDto(input.getName()));
        d.setTelecomAddress(DSETTELTransformer.INSTANCE.toDto(input.getTelecomAddress()));
        d.setPostalAddress(DSETADTransformer.INSTANCE.toDto(input.getPostalAddress()));
        return d;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public HealthCareFacility toXml(HealthCareFacilityDTO input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        HealthCareFacility d = new HealthCareFacility();
        d.setIdentifier(DSETIITransformer.INSTANCE.toXml(input.getIdentifier()));
        d.setPlayerIdentifier(IITransformer.INSTANCE.toXml(input.getPlayerIdentifier()));
        d.setStatus(CDTransformer.INSTANCE.toXml(input.getStatus()));
        d.setName(ENTransformer.ENON_INSTANCE.toXml(input.getName()));
        d.setTelecomAddress(DSETTELTransformer.INSTANCE.toXml(input.getTelecomAddress()));
        d.setPostalAddress(DSETADTransformer.INSTANCE.toXml(input.getPostalAddress()));
        return d;
    }

    /**
     * {@inheritDoc}
     */
    public HealthCareFacility[] createXmlArray(int arg0)
            throws DtoTransformException {
        return new HealthCareFacility[arg0];
    }
}
