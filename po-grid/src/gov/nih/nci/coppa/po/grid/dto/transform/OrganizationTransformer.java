package gov.nih.nci.coppa.po.grid.dto.transform;

import gov.nih.nci.coppa.iso.Tel;
import gov.nih.nci.coppa.po.Organization;
import gov.nih.nci.services.organization.OrganizationDTO;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.iso._21090.DSETTEL;

public class OrganizationTransformer implements Transformer<Organization, OrganizationDTO> {
    protected static Logger logger = LogManager.getLogger(OrganizationTransformer.class);

    public OrganizationDTO transform(Organization input) throws DtoTransformException {
        OrganizationDTO res = new OrganizationDTO();
        res = transform(input, res);
        return res;
    }

    public OrganizationDTO transform(Organization input, OrganizationDTO res) throws DtoTransformException {
        if (input == null)
            return null;
        res.setIdentifier(new IITransformer().transform(input.getIdentifier()));
        res.setName(new ENONTransformer().transform(input.getName()));
        res.setPostalAddress(new ADTransformer().transform(input.getPostalAddress()));
        res.setStatusCode(new CDTransformer().transform(input.getStatusCode()));
        DSET_TELTransformer<Tel> dsetTransformer = new DSET_TELTransformer<Tel>();
        gov.nih.nci.coppa.iso.DSet<Tel> telAddress = dsetTransformer.transform(input.getTelecomAddress());
        res.setTelecomAddress(telAddress);
        return res;
    }

    public Organization transform(OrganizationDTO input) throws DtoTransformException {
        Organization res = new Organization();
        res = transform(input, res);
        return res;
    }

    public Organization transform(OrganizationDTO input, Organization res) throws DtoTransformException {
        if (input == null)
            return null;
        res.setIdentifier(new IITransformer().transform(input.getIdentifier()));
        res.setName(new ENONTransformer().transform(input.getName()));
        res.setPostalAddress(new ADTransformer().transform(input.getPostalAddress()));
        res.setStatusCode(new CDTransformer().transform(input.getStatusCode()));
        DSETTEL telAddress = new DSET_TELTransformer<Tel>().transform(input.getTelecomAddress());
        res.setTelecomAddress(telAddress);
        return res;
    }
}
