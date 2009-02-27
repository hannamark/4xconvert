package gov.nih.nci.coppa.po.grid.dto.transform;

import gov.nih.nci.coppa.po.Organization;
import gov.nih.nci.services.organization.OrganizationDTO;


public class OrganizationTransformer implements Transformer<Organization, OrganizationDTO> {

    public static final OrganizationTransformer INSTANCE = new OrganizationTransformer();

    private OrganizationTransformer() {}

    public Organization toXml(OrganizationDTO input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        Organization x = new Organization();
        copyToXml(input, x);
        return x;
    }

    public void copyToXml(OrganizationDTO source, Organization target) throws DtoTransformException {
        target.setIdentifier(IITransformer.INSTANCE.toXml(source.getIdentifier()));
        target.setName(ENTransformer.ENON_INSTANCE.toXml(source.getName()));
        target.setPostalAddress(ADTransformer.INSTANCE.toXml(source.getPostalAddress()));
        target.setStatusCode(CDTransformer.INSTANCE.toXml(source.getStatusCode()));
        target.setTelecomAddress(DSET_TELTransformer.INSTANCE.toXml(source.getTelecomAddress()));
    }

    public OrganizationDTO toDto(Organization input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        OrganizationDTO d = new OrganizationDTO();
        copyToDto(input, d);
        return d;
    }

    public void copyToDto(Organization source, OrganizationDTO target) throws DtoTransformException {
        target.setIdentifier(IITransformer.INSTANCE.toDto(source.getIdentifier()));
        target.setName(ENTransformer.ENON_INSTANCE.toDto(source.getName()));
        target.setPostalAddress(ADTransformer.INSTANCE.toDto(source.getPostalAddress()));
        target.setStatusCode(CDTransformer.INSTANCE.toDto(source.getStatusCode()));
        target.setTelecomAddress(DSET_TELTransformer.INSTANCE.toDto(source.getTelecomAddress()));
    }
}
