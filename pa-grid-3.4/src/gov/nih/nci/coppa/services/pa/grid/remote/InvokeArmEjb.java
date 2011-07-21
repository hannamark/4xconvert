package gov.nih.nci.coppa.services.pa.grid.remote;

import gov.nih.nci.coppa.services.grid.remote.InvokeCoppaServiceException;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.iso.dto.ArmDTO;
import gov.nih.nci.pa.service.ArmServiceRemote;
import gov.nih.nci.pa.service.PAException;

import java.util.List;

/**
 * Wrapper class for invoking the Arm remote EJB.
 */
public class InvokeArmEjb extends InvokeStudyPaServiceEjb<ArmDTO> implements ArmServiceRemote {


    /**
     * Const.
     */
    public InvokeArmEjb() {
        super(ArmDTO.class);
    }

    /**
     * {@inheritDoc}
     */
    public List<ArmDTO> getByPlannedActivity(Ii ii) throws PAException {
        try {
            List<ArmDTO> result = GridSecurityJNDIServiceLocator.newInstance().getArmService().getByPlannedActivity(ii);
            return result;
        } catch (PAException pae) {
            throw pae;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }
}
