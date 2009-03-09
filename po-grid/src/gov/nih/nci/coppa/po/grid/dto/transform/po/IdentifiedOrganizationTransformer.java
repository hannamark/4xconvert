/**
 *
 */
package gov.nih.nci.coppa.po.grid.dto.transform.po;

import gov.nih.nci.coppa.po.IdentifiedOrganization;
import gov.nih.nci.coppa.po.grid.dto.transform.CDTransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.DtoTransformException;
import gov.nih.nci.coppa.po.grid.dto.transform.IITransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.Transformer;
import gov.nih.nci.services.correlation.IdentifiedOrganizationDTO;

/**
 * Transforms IdentifiedOrganization instances.
 */
public final class IdentifiedOrganizationTransformer implements 
      Transformer<IdentifiedOrganization, IdentifiedOrganizationDTO> {
    
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
       d.setIdentifier(IITransformer.INSTANCE.toDto(input.getIdentifier()));
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
        d.setIdentifier(IITransformer.INSTANCE.toXml(input.getIdentifier()));
        d.setPlayerIdentifier(IITransformer.INSTANCE.toXml(input.getPlayerIdentifier()));
        d.setStatus(CDTransformer.INSTANCE.toXml(input.getStatus()));
        d.setAssignedId(IITransformer.INSTANCE.toXml(input.getAssignedId()));
        d.setScoperIdentifier(IITransformer.INSTANCE.toXml(input.getScoperIdentifier()));
        return d;
    }
}
