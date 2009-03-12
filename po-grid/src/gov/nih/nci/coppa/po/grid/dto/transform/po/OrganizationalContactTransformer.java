package gov.nih.nci.coppa.po.grid.dto.transform.po;

import gov.nih.nci.coppa.po.OrganizationalContact;
import gov.nih.nci.coppa.po.grid.dto.transform.CDTransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.DSETADTransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.DSETCDTransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.DSETTELTransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.DtoTransformException;
import gov.nih.nci.coppa.po.grid.dto.transform.IITransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.Transformer;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;

/**
 * Transforms OrganizationalContact instances.
 */
public final class OrganizationalContactTransformer  
      implements Transformer<OrganizationalContact, OrganizationalContactDTO> {
      /**
     * Public singleton.
     */
    public static final OrganizationalContactTransformer INSTANCE = new OrganizationalContactTransformer();

    /**
     * {@inheritDoc}
     */
    public OrganizationalContactDTO toDto(OrganizationalContact input)
           throws DtoTransformException {
        if (input == null) {
            return null;
        }
        OrganizationalContactDTO dto = new OrganizationalContactDTO();
        dto.setIdentifier(IITransformer.INSTANCE.toDto(input.getIdentifier()));
        dto.setPlayerIdentifier(IITransformer.INSTANCE.toDto(input.getPlayerIdentifier()));
        dto.setScoperIdentifier(IITransformer.INSTANCE.toDto(input.getScoperIdentifier()));
        dto.setStatus(CDTransformer.INSTANCE.toDto(input.getStatus()));
        dto.setPostalAddress(DSETADTransformer.INSTANCE.toDto(input.getPostalAddress()));
        dto.setTelecomAddress(DSETTELTransformer.INSTANCE.toDto(input.getTelecomAddress()));
        dto.setTypeCode(DSETCDTransformer.INSTANCE.toDto(input.getTypeCode()));
        
        return dto;
    }
    /**
     * {@inheritDoc}
     */
    public OrganizationalContact toXml(OrganizationalContactDTO input)
           throws DtoTransformException {
        if (input == null) {
            return null;
        }
        OrganizationalContact xml = new OrganizationalContact();
        xml.setIdentifier(IITransformer.INSTANCE.toXml(input.getIdentifier()));
        xml.setPlayerIdentifier(IITransformer.INSTANCE.toXml(input.getPlayerIdentifier()));
        xml.setScoperIdentifier(IITransformer.INSTANCE.toXml(input.getScoperIdentifier()));
        xml.setStatus(CDTransformer.INSTANCE.toXml(input.getStatus()));
        xml.setPostalAddress(DSETADTransformer.INSTANCE.toXml(input.getPostalAddress()));
        xml.setTelecomAddress(DSETTELTransformer.INSTANCE.toXml(input.getTelecomAddress()));
        xml.setTypeCode(DSETCDTransformer.INSTANCE.toXml(input.getTypeCode()));
        return xml;
    }

}
