package gov.nih.nci.po.webservices.convert.bridg;

import gov.nih.nci.coppa.po.IdentifiedPerson;
import gov.nih.nci.iso21090.grid.dto.transform.AbstractTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.DtoTransformException;
import gov.nih.nci.iso21090.grid.dto.transform.Transformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.CDTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.DSETIITransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.IITransformer;
import gov.nih.nci.services.correlation.IdentifiedPersonDTO;

/**
 * Transforms IdentifiedPerson instances.
 * @author mshestopalov
 */
public final class IdentifiedPersonTransformer
    extends AbstractTransformer<IdentifiedPerson, IdentifiedPersonDTO>
    implements Transformer<IdentifiedPerson, IdentifiedPersonDTO> {

    /**
    * Public singleton.
    */
    public static final IdentifiedPersonTransformer INSTANCE = new IdentifiedPersonTransformer();

    private IdentifiedPersonTransformer() {

    }
      /**
     * {@inheritDoc}
     */

     public IdentifiedPersonDTO toDto(IdentifiedPerson input)
       throws DtoTransformException {
       if (input == null) {
            return null;
        }
       IdentifiedPersonDTO d = new IdentifiedPersonDTO();
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
    public IdentifiedPerson toXml(IdentifiedPersonDTO input)
        throws DtoTransformException {
        if (input == null) {
            return null;
        }
        IdentifiedPerson d = new IdentifiedPerson();
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
    public IdentifiedPerson[] createXmlArray(int arg0)
            throws DtoTransformException {
        return new IdentifiedPerson[arg0];
    }
}
