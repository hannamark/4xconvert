package gov.nih.nci.coppa.po.grid.remote;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.services.correlation.ResearchOrganizationDTO;
import gov.nih.nci.services.entity.NullifiedEntityException;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 *
 * @author mshestopalov
 *
 */
public class InvokeResearchOrganizationEjb {
    static Logger logger = LogManager.getLogger(InvokeResearchOrganizationEjb.class);
    JNDIUtil jndiUtil = JNDIUtil.getInstance();

    public ResearchOrganizationDTO getResearchOrganization(Ii ii) throws NullifiedEntityException,InvokeCoppaServiceException {
        try {
            ResearchOrganizationDTO idOrg = jndiUtil.getResearchOrganizationService().getCorrelation(ii);
            return idOrg;
        } catch(Exception exception) {
            logger.error("Error getting ResearchOrganizationDTO.",exception);
            throw new InvokeCoppaServiceException(exception.toString(), exception);
        }
    }
    public List<ResearchOrganizationDTO> search(ResearchOrganizationDTO idOrgDTO) throws InvokeCoppaServiceException {
        try {
            List<ResearchOrganizationDTO> idOrgs = jndiUtil.getResearchOrganizationService().search(idOrgDTO);
            return idOrgs;
        } catch(Exception exception) {
            logger.error("Error searching ResearchOrganization .",exception);
            throw new InvokeCoppaServiceException(exception.toString(), exception);
        }
    }

}
