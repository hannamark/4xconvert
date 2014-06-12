package gov.nih.nci.po.webservices.convert.bridg;

import gov.nih.nci.coppa.po.Organization;
import gov.nih.nci.iso21090.grid.dto.transform.AbstractTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.DtoTransformException;
import gov.nih.nci.iso21090.grid.dto.transform.Transformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.ADTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.CDTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.DSETTELTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.ENTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.IITransformer;
import gov.nih.nci.services.organization.OrganizationDTO;


/**
 * Transforms Organization instances.
 */
public final class OrganizationTransformer
    extends AbstractTransformer<Organization, OrganizationDTO>
    implements Transformer<Organization, OrganizationDTO> {

    /**
     * Public singleton.
     */
    public static final OrganizationTransformer INSTANCE = new OrganizationTransformer();

    private OrganizationTransformer() { }

    /**
     * {@inheritDoc}
     */
    public Organization toXml(OrganizationDTO input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        Organization x = new Organization();
        x.setIdentifier(IITransformer.INSTANCE.toXml(input.getIdentifier()));
        x.setName(ENTransformer.ENON_INSTANCE.toXml(input.getName()));
        x.setPostalAddress(ADTransformer.INSTANCE.toXml(input.getPostalAddress()));
        x.setStatusCode(CDTransformer.INSTANCE.toXml(input.getStatusCode()));
        x.setTelecomAddress(DSETTELTransformer.INSTANCE.toXml(input.getTelecomAddress()));
        return x;
    }

    /**
     * {@inheritDoc}
     */
    public OrganizationDTO toDto(Organization input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        OrganizationDTO d = new OrganizationDTO();
        d.setIdentifier(IITransformer.INSTANCE.toDto(input.getIdentifier()));
        d.setName(ENTransformer.ENON_INSTANCE.toDto(input.getName()));
        d.setPostalAddress(ADTransformer.INSTANCE.toDto(input.getPostalAddress()));
        d.setStatusCode(CDTransformer.INSTANCE.toDto(input.getStatusCode()));
        d.setTelecomAddress(DSETTELTransformer.INSTANCE.toDto(input.getTelecomAddress()));
        return d;
    }

    /**
     * {@inheritDoc}
     */
    public Organization[] createXmlArray(int size) throws DtoTransformException {
        return new Organization[size];
    }
}
