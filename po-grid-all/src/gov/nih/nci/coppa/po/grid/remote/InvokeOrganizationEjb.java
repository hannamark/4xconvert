package gov.nih.nci.coppa.po.grid.remote;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class InvokeOrganizationEjb {
    static Logger logger = LogManager.getLogger(InvokeOrganizationEjb.class);
    JNDIUtil jndiUtil = JNDIUtil.getInstance();

    public List<OrganizationDTO> search(OrganizationDTO org) throws InvokeCoppaServiceException {
        try {
            List<OrganizationDTO> orgs = jndiUtil.getOrganizationService().search(org);
            return orgs;
        } catch (Exception exception) {
            logger.error("Error searching organizations.", exception);
            throw new InvokeCoppaServiceException(exception.toString(), exception);
        }
    }

    public OrganizationDTO getOrganization(Ii ii) throws NullifiedEntityException, InvokeCoppaServiceException {
        try {
            OrganizationDTO person = jndiUtil.getOrganizationService().getOrganization(ii);
            return person;
        } catch (NullifiedEntityException nee) {
            logger.error("Nullified entity exception getting organizations.", nee);
            throw nee;
        } catch (Exception exception) {
            logger.error("Error getting organizations.", exception);
            throw new InvokeCoppaServiceException(exception.toString(), exception);
        }
    }
}
