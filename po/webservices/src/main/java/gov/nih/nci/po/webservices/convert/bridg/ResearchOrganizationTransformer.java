/**
 *
 */
package gov.nih.nci.po.webservices.convert.bridg;

import gov.nih.nci.coppa.po.ResearchOrganization;
import gov.nih.nci.iso21090.grid.dto.transform.AbstractTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.DtoTransformException;
import gov.nih.nci.iso21090.grid.dto.transform.Transformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.CDTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.DSETADTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.DSETIITransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.DSETTELTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.ENTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.IITransformer;
import gov.nih.nci.services.correlation.ResearchOrganizationDTO;

/**
 * Transforms ResearchOrganization instances.
 */
public final class ResearchOrganizationTransformer
    extends AbstractTransformer<ResearchOrganization, ResearchOrganizationDTO>
    implements Transformer<ResearchOrganization, ResearchOrganizationDTO> {

    /**
    * Public singleton.
    */
    public static final ResearchOrganizationTransformer INSTANCE = new ResearchOrganizationTransformer();

    private ResearchOrganizationTransformer() {

    }
      /**
     * {@inheritDoc}
     */

     public ResearchOrganizationDTO toDto(ResearchOrganization input)
       throws DtoTransformException {
       if (input == null) {
            return null;
        }
       ResearchOrganizationDTO d = new ResearchOrganizationDTO();
       d.setIdentifier(DSETIITransformer.INSTANCE.toDto(input.getIdentifier()));
       d.setPlayerIdentifier(IITransformer.INSTANCE.toDto(input.getPlayerIdentifier()));
       d.setStatus(CDTransformer.INSTANCE.toDto(input.getStatus()));
       d.setFundingMechanism(CDTransformer.INSTANCE.toDto(input.getFundingMechanism()));
       d.setTypeCode(CDTransformer.INSTANCE.toDto(input.getTypeCode()));
       d.setName(ENTransformer.ENON_INSTANCE.toDto(input.getName()));
       d.setTelecomAddress(DSETTELTransformer.INSTANCE.toDto(input.getTelecomAddress()));
       d.setPostalAddress(DSETADTransformer.INSTANCE.toDto(input.getPostalAddress()));
      return d;
    }
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public ResearchOrganization toXml(ResearchOrganizationDTO input)
        throws DtoTransformException {
        if (input == null) {
            return null;
        }
        ResearchOrganization d = new ResearchOrganization();
        d.setIdentifier(DSETIITransformer.INSTANCE.toXml(input.getIdentifier()));
        d.setPlayerIdentifier(IITransformer.INSTANCE.toXml(input.getPlayerIdentifier()));
        d.setStatus(CDTransformer.INSTANCE.toXml(input.getStatus()));
        d.setFundingMechanism(CDTransformer.INSTANCE.toXml(input.getFundingMechanism()));
        d.setTypeCode(CDTransformer.INSTANCE.toXml(input.getTypeCode()));
        d.setName(ENTransformer.ENON_INSTANCE.toXml(input.getName()));
        d.setTelecomAddress(DSETTELTransformer.INSTANCE.toXml(input.getTelecomAddress()));
        d.setPostalAddress(DSETADTransformer.INSTANCE.toXml(input.getPostalAddress()));
        return d;
    }

    /**
     * {@inheritDoc}
     */
    public ResearchOrganization[] createXmlArray(int arg0)
            throws DtoTransformException {
        return new ResearchOrganization[arg0];
    }
}
