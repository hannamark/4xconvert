package gov.nih.nci.coppa.services.pa.armservice.service;

import gov.nih.nci.coppa.services.pa.Arm;
import gov.nih.nci.coppa.services.pa.grid.GenericStudyPaGridServiceImpl;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.ArmTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.faults.FaultUtil;
import gov.nih.nci.coppa.services.pa.grid.remote.InvokeArmEjb;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.grid.dto.transform.iso.IITransformer;
import gov.nih.nci.pa.iso.dto.ArmDTO;

import java.rmi.RemoteException;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Implementation of the ArmService. Dispatches to the remote EJBs and the Transformers.
 */
public class ArmServiceImpl extends ArmServiceImplBase {

    private static final Logger logger = LogManager.getLogger(ArmServiceImpl.class);
    private final InvokeArmEjb armService = new InvokeArmEjb();

    static {
        gov.nih.nci.coppa.services.pa.grid.PAGridUtils.initIso21090Transformers();
    }

    private GenericStudyPaGridServiceImpl<ArmDTO, Arm> impl
        = new GenericStudyPaGridServiceImpl<ArmDTO, Arm>(Arm.class, ArmDTO.class);
    /**
     * Simple constructor.
     *
     * @throws RemoteException on error
     */
    public ArmServiceImpl() throws RemoteException {
        super();
    }

    /**
     * {@inheritDoc}
     */
  public gov.nih.nci.coppa.services.pa.Arm get(gov.nih.nci.iso21090.extensions.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {

      return impl.get(id);
    }

    /**
     * {@inheritDoc}
     */
  public gov.nih.nci.coppa.services.pa.Arm[] getByPlannedActivity(gov.nih.nci.iso21090.extensions.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        try {
            Ii iiDto = IITransformer.INSTANCE.toDto(id);
            List<ArmDTO> armDto = armService.getByPlannedActivity(iiDto);
            return ArmTransformer.INSTANCE.convert(armDto);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw FaultUtil.reThrowRemote(e);
        }
    }

    /**
     * {@inheritDoc}
     */
  public gov.nih.nci.coppa.services.pa.Arm[] getByStudyProtocol(gov.nih.nci.iso21090.extensions.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        return impl.getByStudyProtocol(id);
    }

    /**
     * {@inheritDoc}
     */

    /**
     * {@inheritDoc}
     */
  public void copy(gov.nih.nci.iso21090.extensions.Id fromStudyProtocolId,gov.nih.nci.iso21090.extensions.Id toStudyProtocolId) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    //TODO: Implement this autogenerated method
      throw new RemoteException("Not yet implemented");
    }

    /**
     * {@inheritDoc}
     */
  public gov.nih.nci.coppa.services.pa.Arm create(gov.nih.nci.coppa.services.pa.Arm arm) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    //TODO: Implement this autogenerated method
      throw new RemoteException("Not yet implemented");
    }

    /**
     * {@inheritDoc}
     */
  public gov.nih.nci.coppa.services.pa.Arm update(gov.nih.nci.coppa.services.pa.Arm arm) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    //TODO: Implement this autogenerated method
      throw new RemoteException("Not yet implemented");
    }

    /**
     * {@inheritDoc}
     */
  public void delete(gov.nih.nci.iso21090.extensions.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    //TODO: Implement this autogenerated method
      throw new RemoteException("Not yet implemented");
    }
}
