package gov.nih.nci.coppa.po.grid.remote;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.services.correlation.HealthCareProviderDTO;
import gov.nih.nci.services.entity.NullifiedEntityException;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 *
 * @author mshestopalov
 *
 */
public class InvokeHealthCareProviderEjb {
    static Logger logger = LogManager.getLogger(InvokeHealthCareProviderEjb.class);
    JNDIUtil jndiUtil = JNDIUtil.getInstance();

    public HealthCareProviderDTO getHealthCareProvider(Ii ii) throws NullifiedEntityException,InvokeCoppaServiceException {
        try {
               HealthCareProviderDTO provider = jndiUtil.getHealthCareProviderService().getCorrelation(ii);
               return provider;
           } catch(Exception exception) {
               logger.error("Error getting HealthCareProvider.",exception);
               throw new InvokeCoppaServiceException(exception.toString(), exception);
           }
       }

       public List<HealthCareProviderDTO> search(HealthCareProviderDTO hcpDTO) throws InvokeCoppaServiceException {
           try {
               List<HealthCareProviderDTO> hcps = jndiUtil.getHealthCareProviderService().search(hcpDTO);
                   return hcps;
           } catch(Exception exception) {
               logger.error("Error searching HealthCareProvider .",exception);
               throw new InvokeCoppaServiceException(exception.toString(), exception);
           }
       }
}
