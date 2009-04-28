package gov.nih.nci.coppa.services.grid.dto.transform.iso;

/**
 * Interface for transforming from XML representations to DTO representations, and vice versa.
 *
 * @param <XML> XML class to transform to/from.
 * @param <DTO> DTO class to transform to/from.
 */
public interface Transformer<XML, DTO> {

    /**
     * Convert a PO DTO into a XML(JAXB) object.
     *
     * General Rules:
     * <ol>
     * <li> Null inputs produce null outputs
     * <li> NullFlavors are preserved across the transform
     * <li> Empty sets produce NullFlavor.NI DSET<?>.
     * <li> ocl constraints are not otherwise enforced.
     * </ol>
     *
     * @param input the DTO object to convert.  may be null.
     * @return the equivalent XML object.
     * @throws DtoTransformException if conversion fails.
     */
    XML toXml(DTO input) throws DtoTransformException;

    /**
     * Convert an XML(JAXB) object into a PO DTO object.
     *
     * General Rules:
     * <ol>
     * <li>null inputs produce null outputs.
     * <li>nullfalvored non-sets (ie st, bl, etc.) produce NullFlavor outputs.
     * <li>nullflavored sets -> null output.
     * <li>values are copied.
     * </ol>
     *
     * @param input the XML object to convert.  may be null.
     * @return the equivalent DTO object.
     * @throws DtoTransformException if conversion fails.
     */
    DTO toDto(XML input) throws DtoTransformException;
}
