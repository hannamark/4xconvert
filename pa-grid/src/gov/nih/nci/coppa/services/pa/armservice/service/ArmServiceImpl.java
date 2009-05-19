package gov.nih.nci.coppa.services.pa.armservice.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.IITransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.ArmTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.faults.FaultUtil;
import gov.nih.nci.coppa.services.pa.grid.remote.InvokeArmEjb;
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
  public gov.nih.nci.coppa.services.pa.Arm get(gov.nih.nci.coppa.services.pa.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        try {
            Ii iiDto = IITransformer.INSTANCE.toDto(id);
            ArmDTO armDto = armService.get(iiDto);
            return ArmTransformer.INSTANCE.toXml(armDto);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw FaultUtil.reThrowRemote(e);
        }
    }

    /**
     * {@inheritDoc}
     */
  public gov.nih.nci.coppa.services.pa.Arm[] getByPlannedActivity(gov.nih.nci.coppa.services.pa.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
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
  public gov.nih.nci.coppa.services.pa.Arm[] getByStudyProtocol(gov.nih.nci.coppa.services.pa.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        try {
            Ii iiDto = IITransformer.INSTANCE.toDto(id);
            List<ArmDTO> armDto = armService.getByStudyProtocol(iiDto);
            return ArmTransformer.INSTANCE.convert(armDto);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw FaultUtil.reThrowRemote(e);
        }
    }

    /**
     * {@inheritDoc}
     */
  public gov.nih.nci.coppa.services.pa.Arm[] getCurrentByStudyProtocol(gov.nih.nci.coppa.services.pa.Id studyProtocolId) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        try {
            Ii iiDto = IITransformer.INSTANCE.toDto(studyProtocolId);
            List<ArmDTO> armDto = armService.getCurrentByStudyProtocol(iiDto);
            return ArmTransformer.INSTANCE.convert(armDto);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw FaultUtil.reThrowRemote(e);
        }
    }

    /**
     * {@inheritDoc}
     */
  public void copy(gov.nih.nci.coppa.services.pa.Id fromStudyProtocolId,gov.nih.nci.coppa.services.pa.Id toStudyProtocolId) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        throw new RemoteException("Not yet implemented");
    }

    /**
     * {@inheritDoc}
     */
  public gov.nih.nci.coppa.services.pa.Arm create(gov.nih.nci.coppa.services.pa.Arm arm) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        throw new RemoteException("Not yet implemented");
    }

    /**
     * {@inheritDoc}
     */
  public gov.nih.nci.coppa.services.pa.Arm update(gov.nih.nci.coppa.services.pa.Arm arm) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        throw new RemoteException("Not yet implemented");
    }

    /**
     * {@inheritDoc}
     */
  public void delete(gov.nih.nci.coppa.services.pa.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        throw new RemoteException("Not yet implemented");
    }
}
