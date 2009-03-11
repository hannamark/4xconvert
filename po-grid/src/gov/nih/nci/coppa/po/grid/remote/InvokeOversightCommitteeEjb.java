package gov.nih.nci.coppa.po.grid.remote;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.services.correlation.OversightCommitteeDTO;
import gov.nih.nci.services.entity.NullifiedEntityException;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class InvokeOversightCommitteeEjb {
	static Logger logger = LogManager.getLogger(InvokeOversightCommitteeEjb.class); 
    private final JNDIUtil jndiUtil = JNDIUtil.getInstance();
    public OversightCommitteeDTO getOversightCommittee(Ii ii) throws NullifiedEntityException,InvokeCoppaServiceException {
		try {
			OversightCommitteeDTO oc_dto = jndiUtil.getOversightCommitteeService().getCorrelation(ii);
			return oc_dto;
		} catch(Exception exception) {
			logger.error("Error getting OversightCommittee.",exception);
			throw new InvokeCoppaServiceException(exception.toString(), exception);
		}
	}	
	public List<OversightCommitteeDTO> search(OversightCommitteeDTO ocDTO) throws InvokeCoppaServiceException {
		try {
			List<OversightCommitteeDTO> ocs = jndiUtil.getOversightCommitteeService().search(ocDTO);
			return ocs;
		} catch(Exception exception) {
			logger.error("Error searching OversightCommittee .",exception);
			throw new InvokeCoppaServiceException(exception.toString(), exception);
		}
	}

}
