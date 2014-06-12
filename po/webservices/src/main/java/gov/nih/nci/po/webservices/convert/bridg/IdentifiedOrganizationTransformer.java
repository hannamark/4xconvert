/**
 *
 */
package gov.nih.nci.po.webservices.convert.bridg;

import gov.nih.nci.coppa.po.IdentifiedOrganization;
import gov.nih.nci.iso21090.grid.dto.transform.AbstractTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.DtoTransformException;
import gov.nih.nci.iso21090.grid.dto.transform.Transformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.CDTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.DSETIITransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.IITransformer;
import gov.nih.nci.services.correlation.IdentifiedOrganizationDTO;

/**
 * Transforms IdentifiedOrganization instances.
 */
public final class IdentifiedOrganizationTransformer
    extends AbstractTransformer<IdentifiedOrganization, IdentifiedOrganizationDTO>
    implements Transformer<IdentifiedOrganization, IdentifiedOrganizationDTO> {

    /**
    * Public singleton.
    */
    public static final IdentifiedOrganizationTransformer INSTANCE = new IdentifiedOrganizationTransformer();

    private IdentifiedOrganizationTransformer() {

    }
      /**
     * {@inheritDoc}
     */

     public IdentifiedOrganizationDTO toDto(IdentifiedOrganization input)
       throws DtoTransformException {
       if (input == null) {
            return null;
        }
       IdentifiedOrganizationDTO d = new IdentifiedOrganizationDTO();
       d.setIdentifier(DSETIITransformer.INSTANCE.toDto(input.getIdentifier()));
       d.setPlayerIdentifier(IITransformer.INSTANCE.toDto(input.getPlayerIdentifier()));
       d.setStatus(CDTransformer.INSTANCE.toDto(input.getStatus()));
       d.setAssignedId(IITransformer.INSTANCE.toDto(input.getAssignedId()));
       d.setScoperIdentifier(IITransformer.INSTANCE.toDto(input.getScoperIdentifier()));
      return d;
    }
    /**
     * {@inheritDoc}
     */
    public IdentifiedOrganization toXml(IdentifiedOrganizationDTO input)
        throws DtoTransformException {
        if (input == null) {
            return null;
        }
        IdentifiedOrganization d = new IdentifiedOrganization();
        d.setIdentifier(DSETIITransformer.INSTANCE.toXml(input.getIdentifier()));
        d.setPlayerIdentifier(IITransformer.INSTANCE.toXml(input.getPlayerIdentifier()));
        d.setStatus(CDTransformer.INSTANCE.toXml(input.getStatus()));
        d.setAssignedId(IITransformer.INSTANCE.toXml(input.getAssignedId()));
        d.setScoperIdentifier(IITransformer.INSTANCE.toXml(input.getScoperIdentifier()));
        return d;
    }

    /**
     * {@inheritDoc}
     */
    public IdentifiedOrganization[] createXmlArray(int arg0)
            throws DtoTransformException {
        return new IdentifiedOrganization[arg0];
    }
}
