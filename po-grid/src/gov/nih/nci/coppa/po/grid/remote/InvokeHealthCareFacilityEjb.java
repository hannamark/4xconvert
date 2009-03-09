package gov.nih.nci.coppa.po.grid.remote;

/**
 * @author Vrushali
 *
 */
import java.util.List;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.entity.NullifiedEntityException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


public class InvokeHealthCareFacilityEjb {
	static Logger logger = LogManager.getLogger(InvokeHealthCareFacilityEjb.class); 
    JNDIUtil jndiUtil = JNDIUtil.getInstance();
    
	public HealthCareFacilityDTO getHealthCareFacility(Ii ii) throws NullifiedEntityException,InvokeCoppaServiceException {
		try {
			HealthCareFacilityDTO facility = jndiUtil.getHealthCareFacilityService().getCorrelation(ii);
			return facility;
		} catch(Exception exception) {
			logger.error("Error getting HealthCareFacility.",exception);
			throw new InvokeCoppaServiceException(exception.toString(), exception);
		}
	}	
	public List<HealthCareFacilityDTO> search(HealthCareFacilityDTO hcfDTO) throws InvokeCoppaServiceException {
		try {
			List<HealthCareFacilityDTO> hcfs = jndiUtil.getHealthCareFacilityService().search(hcfDTO);
			return hcfs;
		} catch(Exception exception) {
			logger.error("Error searching HealthCareFacility .",exception);
			throw new InvokeCoppaServiceException(exception.toString(), exception);
		}
	}
}
