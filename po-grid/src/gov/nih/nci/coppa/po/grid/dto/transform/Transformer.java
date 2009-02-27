package gov.nih.nci.coppa.po.grid.dto.transform;

/**
 * 
 * @author gax
 * 
 * @param <XML>
 * @param <DTO>
 */
public interface Transformer<XML, DTO> {

    /**
     * Convert a PO DTO into a XML(JAXB) object.
     * @param input the DTO object to convert.
     * @return the equivalent XML object, or null if input was null.
     * @throws DtoTransformException if conversion failes.
     */
    public XML toXml(DTO input) throws DtoTransformException;

    /**
     * Copy the properties from a source DTO object to a target XML object.
     * assumes that both parameters are NON-NULL!
     * @param source the DTO object that provides the properties to copy. Cannot be null.
     * @param target the XML object who's properties will be set from the corresponding properties in the source. Cannot be null.
     * @throws DtoTransformException if conversion fails.
     */
    public void copyToXml(DTO source, XML target) throws DtoTransformException;

    /**
     * Convert an XML(JAXB) object into a PO DTO object.
     * @param input the XML object to convert.
     * @return the equivalent DTO object, or null if input was null.
     * @throws DtoTransformException if conversion failes.
     */
    public DTO toDto(XML input) throws DtoTransformException;

    /**
     * Copy the properties from a source XML object to a target DTO object.
     * assumes that both parameters are NON-NULL!
     * @param source the XML object that provides the properties to copy. Cannot be null.
     * @param target the DTO object who's properties will be set from the corresponding properties in the source. Cannot be null.
     * @throws DtoTransformException if conversion fails.
     */
    public void copyToDto(XML source, DTO target) throws DtoTransformException;

}
