package gov.nih.nci.coppa.po.grid.remote;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.services.correlation.IdentifiedPersonDTO;
import gov.nih.nci.services.entity.NullifiedEntityException;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author mshestopalov
 *
 */
public class InvokeIdentifiedPersonEjb {
    static Logger logger = LogManager.getLogger(InvokeIdentifiedPersonEjb.class);
    JNDIUtil jndiUtil = JNDIUtil.getInstance();

    public IdentifiedPersonDTO getIdentifiedPerson(Ii ii) throws NullifiedEntityException,InvokeCoppaServiceException {
        try {
            IdentifiedPersonDTO idOrg = jndiUtil.getIdentifiedPersonService().getCorrelation(ii);
            return idOrg;
        } catch(Exception exception) {
            logger.error("Error getting IdentifiedPersonDTO.",exception);
            throw new InvokeCoppaServiceException(exception.toString(), exception);
        }
    }
    public List<IdentifiedPersonDTO> search(IdentifiedPersonDTO idPersonDTO) throws InvokeCoppaServiceException {
        try {
            List<IdentifiedPersonDTO> idPersons = jndiUtil.getIdentifiedPersonService().search(idPersonDTO);
            return idPersons;
        } catch(Exception exception) {
            logger.error("Error searching IdentifiedPersons .",exception);
            throw new InvokeCoppaServiceException(exception.toString(), exception);
        }
    }

}
