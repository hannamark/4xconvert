package gov.nih.nci.coppa.services.pa.grid.dto;

/**
 * DO NOT EDIT!!! COPY/PASTE FROM PO-GRID.  See PO-927 for refactoring task.
 * If you need to modify this file (bug?), change in po-grid and re-import to this location.
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
