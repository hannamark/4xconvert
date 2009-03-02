package gov.nih.nci.coppa.po.grid.dto.transform.po;

import gov.nih.nci.coppa.po.Organization;
import gov.nih.nci.coppa.po.grid.dto.transform.ADTransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.CDTransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.DSET_TELTransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.DtoTransformException;
import gov.nih.nci.coppa.po.grid.dto.transform.ENTransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.IITransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.Transformer;
import gov.nih.nci.services.organization.OrganizationDTO;


public class OrganizationTransformer implements Transformer<Organization, OrganizationDTO> {

    public static final OrganizationTransformer INSTANCE = new OrganizationTransformer();

    private OrganizationTransformer() {}

    public Organization toXml(OrganizationDTO input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        Organization x = new Organization();
        x.setIdentifier(IITransformer.INSTANCE.toXml(input.getIdentifier()));
        x.setName(ENTransformer.ENON_INSTANCE.toXml(input.getName()));
        x.setPostalAddress(ADTransformer.INSTANCE.toXml(input.getPostalAddress()));
        x.setStatusCode(CDTransformer.INSTANCE.toXml(input.getStatusCode()));
        x.setTelecomAddress(DSET_TELTransformer.INSTANCE.toXml(input.getTelecomAddress()));
        return x;
    }

    public OrganizationDTO toDto(Organization input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        OrganizationDTO d = new OrganizationDTO();
        d.setIdentifier(IITransformer.INSTANCE.toDto(input.getIdentifier()));
        d.setName(ENTransformer.ENON_INSTANCE.toDto(input.getName()));
        d.setPostalAddress(ADTransformer.INSTANCE.toDto(input.getPostalAddress()));
        d.setStatusCode(CDTransformer.INSTANCE.toDto(input.getStatusCode()));
        d.setTelecomAddress(DSET_TELTransformer.INSTANCE.toDto(input.getTelecomAddress()));
        return d;
    }
}
