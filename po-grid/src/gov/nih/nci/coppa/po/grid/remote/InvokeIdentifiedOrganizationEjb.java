package gov.nih.nci.coppa.po.grid.remote;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.services.correlation.IdentifiedOrganizationDTO;
import gov.nih.nci.services.entity.NullifiedEntityException;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author Vrushali
 *
 */
public class InvokeIdentifiedOrganizationEjb {
	static Logger logger = LogManager.getLogger(InvokeIdentifiedOrganizationEjb.class); 
    JNDIUtil jndiUtil = JNDIUtil.getInstance();
    
	public IdentifiedOrganizationDTO getIdentifiedOrganization(Ii ii) throws NullifiedEntityException,InvokeCoppaServiceException {
		try {
			IdentifiedOrganizationDTO idOrg = jndiUtil.getIdentifiedOrganizationService().getCorrelation(ii);
			return idOrg;
		} catch(Exception exception) {
			logger.error("Error getting IdentifiedOrganizationDTO.",exception);
			throw new InvokeCoppaServiceException(exception.toString(), exception);
		}
	}	
	public List<IdentifiedOrganizationDTO> search(IdentifiedOrganizationDTO idOrgDTO) throws InvokeCoppaServiceException {
		try {
			List<IdentifiedOrganizationDTO> idOrgs = jndiUtil.getIdentifiedOrganizationService().search(idOrgDTO);
			return idOrgs;
		} catch(Exception exception) {
			logger.error("Error searching IdentifiedOrganization .",exception);
			throw new InvokeCoppaServiceException(exception.toString(), exception);
		}
	}

}
