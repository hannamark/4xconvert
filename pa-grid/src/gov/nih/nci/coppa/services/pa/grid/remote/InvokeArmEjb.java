package gov.nih.nci.coppa.services.pa.grid.remote;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.iso.dto.ArmDTO;
import gov.nih.nci.pa.service.ArmServiceRemote;
import gov.nih.nci.pa.service.PAException;

import java.util.List;

/**
 * Wrapper class for invoking the Arm remote ejb.
 */
public class InvokeArmEjb implements ArmServiceRemote {

    ServiceLocator locator = JNDIServiceLocator.getInstance();

    /**
     * {@inheritDoc}
     */
    public List<ArmDTO> getByPlannedActivity(Ii ii) throws PAException {
        try {
            List<ArmDTO> result = locator.getArmService().getByPlannedActivity(ii);
            return result;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<ArmDTO> getByStudyProtocol(Ii ii) throws PAException {
        try {
            List<ArmDTO> result = locator.getArmService().getByStudyProtocol(ii);
            return result;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<ArmDTO> getCurrentByStudyProtocol(Ii ii) throws PAException {
        try {
            List<ArmDTO> result = locator.getArmService().getCurrentByStudyProtocol(ii);
            return result;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public ArmDTO get(Ii ii) throws PAException {
        try {
            ArmDTO result = locator.getArmService().get(ii);
            return result;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void copy(Ii arg0, Ii arg1) throws PAException {
        throw new PAException("Not yet implemented");
    }

    /**
     * {@inheritDoc}
     */
    public ArmDTO create(ArmDTO arg0) throws PAException {
        throw new PAException("Not yet implemented");
    }

    /**
     * {@inheritDoc}
     */
    public void delete(Ii arg0) throws PAException {
        throw new PAException("Not yet implemented");
    }

    /**
     * {@inheritDoc}
     */
    public ArmDTO update(ArmDTO arg0) throws PAException {
        throw new PAException("Not yet implemented");
    }
}
