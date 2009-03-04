package gov.nih.nci.coppa.po.grid.dto.transform;

/**
 * Interface for transforming from XML representations to DTO representations, and vice versa.
 *
 * @param <XML> XML class to transform to/from.
 * @param <DTO> DTO class to transform to/from.
 */
public interface Transformer<XML, DTO> {

    /**
     * Convert a PO DTO into a XML(JAXB) object.
     * @param input the DTO object to convert.
     * @return the equivalent XML object, or null if input was null.
     * @throws DtoTransformException if conversion fails.
     */
    public XML toXml(DTO input) throws DtoTransformException;

    /**
     * Convert an XML(JAXB) object into a PO DTO object.
     * @param input the XML object to convert.
     * @return the equivalent DTO object, or null if input was null.
     * @throws DtoTransformException if conversion fails.
     */
    public DTO toDto(XML input) throws DtoTransformException;
}
