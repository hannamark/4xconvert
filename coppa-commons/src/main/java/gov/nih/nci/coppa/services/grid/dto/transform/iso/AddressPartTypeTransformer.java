package gov.nih.nci.coppa.services.grid.dto.transform.iso;

import gov.nih.nci.coppa.iso.AddressPartType;
import gov.nih.nci.coppa.services.grid.dto.transform.DtoTransformException;
import gov.nih.nci.coppa.services.grid.dto.transform.Transformer;

/**
 * Transforms individual address parts.
 */
public final class AddressPartTypeTransformer
    extends AbstractTransformer<org.iso._21090.AddressPartType, AddressPartType>
    implements Transformer<org.iso._21090.AddressPartType, AddressPartType> {
    /**
     * Public singleton.
     */

    public static final AddressPartTypeTransformer INSTANCE = new AddressPartTypeTransformer();

    private AddressPartTypeTransformer() {
    }

    /**
     * {@inheritDoc}
     */
    public org.iso._21090.AddressPartType toXml(AddressPartType input) {
        if (input == null) {
            return null;
        }
        return org.iso._21090.AddressPartType.fromValue(input.name());
    }

    /**
     * {@inheritDoc}
     */
    public AddressPartType toDto(org.iso._21090.AddressPartType input) {
        if (input == null) {
            return null;
        }
        return AddressPartType.valueOf(input.name());
    }

    /**
     * {@inheritDoc}
     */
    public org.iso._21090.AddressPartType[] createXmlArray(int size)
            throws DtoTransformException {
        return new org.iso._21090.AddressPartType[size];
    }


}
