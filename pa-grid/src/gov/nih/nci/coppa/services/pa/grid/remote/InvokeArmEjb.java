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
    public void copy(Ii arg0, Ii arg1) throws PAException {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    public List<ArmDTO> getByStudyProtocol(Ii arg0) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public List<ArmDTO> getCurrentByStudyProtocol(Ii arg0) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public ArmDTO create(ArmDTO arg0) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public void delete(Ii arg0) throws PAException {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    public ArmDTO get(Ii arg0) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public ArmDTO update(ArmDTO arg0) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }
}
