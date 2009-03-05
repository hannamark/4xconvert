package gov.nih.nci.coppa.po.grid.remote;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import gov.nih.nci.services.entity.NullifiedEntityException;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author vrushali
 *
 */
public class InvokeClinicalResearchStaffEjb {
	static Logger logger = LogManager.getLogger(InvokeClinicalResearchStaffEjb.class); 
    JNDIUtil jndiUtil = JNDIUtil.getInstance();
    
	public ClinicalResearchStaffDTO getClinicalResearchStaff(Ii ii) throws NullifiedEntityException,InvokeCoppaServiceException {
		try {
			ClinicalResearchStaffDTO facility = jndiUtil.getClinicalResearchStaffService().getCorrelation(ii);
			return facility;
		} catch(Exception exception) {
			logger.error("Error getting ClinicalResearchStaff.",exception);
			throw new InvokeCoppaServiceException(exception.toString(), exception);
		}
	}	
	public List<ClinicalResearchStaffDTO> search(ClinicalResearchStaffDTO crsDTO) throws InvokeCoppaServiceException {
		try {
			List<ClinicalResearchStaffDTO> crss = jndiUtil.getClinicalResearchStaffService().search(crsDTO);
			return crss;
		} catch(Exception exception) {
			logger.error("Error searching ClinicalResearchStaff .",exception);
			throw new InvokeCoppaServiceException(exception.toString(), exception);
		}
	}

}
