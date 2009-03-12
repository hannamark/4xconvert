package gov.nih.nci.coppa.po.grid.remote;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;
import gov.nih.nci.services.entity.NullifiedEntityException;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class InvokeOrganizationalContactEjb {
	static Logger logger = LogManager.getLogger(InvokeOrganizationalContactEjb.class); 
    private final JNDIUtil jndiUtil = JNDIUtil.getInstance();
    public OrganizationalContactDTO getOrganizationalContact(Ii ii) throws NullifiedEntityException,InvokeCoppaServiceException {
		try {
			OrganizationalContactDTO org_con_dto = jndiUtil.getOrganizationalContactService().getCorrelation(ii);
			return org_con_dto;
		} catch(Exception exception) {
			logger.error("Error getting OrganizationalContact.",exception);
			throw new InvokeCoppaServiceException(exception.toString(), exception);
		}
	}	
	public List<OrganizationalContactDTO> search(OrganizationalContactDTO orgConDTO) throws InvokeCoppaServiceException {
		try {
			List<OrganizationalContactDTO> orgCons = jndiUtil.getOrganizationalContactService().search(orgConDTO);
			return orgCons;
		} catch(Exception exception) {
			logger.error("Error searching OrganizationalContact .",exception);
			throw new InvokeCoppaServiceException(exception.toString(), exception);
		}
	}
}
