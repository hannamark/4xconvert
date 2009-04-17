package gov.nih.nci.coppa.services.pa.armservice.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.services.pa.Arm;
import gov.nih.nci.coppa.services.pa.Id;
import gov.nih.nci.coppa.services.pa.faults.PAFault;
import gov.nih.nci.coppa.services.pa.grid.dto.DtoTransformException;
import gov.nih.nci.coppa.services.pa.grid.dto.IITransformer;
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
    public Arm get(Id id) throws RemoteException, PAFault {
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
    public Arm[] getByPlannedActivity(Id id) throws RemoteException, PAFault {
        try {
            Ii iiDto = IITransformer.INSTANCE.toDto(id);
            List<ArmDTO> armDto = armService.getByPlannedActivity(iiDto);
            return convert(armDto);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw FaultUtil.reThrowRemote(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public Arm[] getByStudyProtocol(Id id) throws RemoteException, PAFault {
        try {
            Ii iiDto = IITransformer.INSTANCE.toDto(id);
            List<ArmDTO> armDto = armService.getByStudyProtocol(iiDto);
            return convert(armDto);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw FaultUtil.reThrowRemote(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public Arm[] getCurrentByStudyProtocol(Id studyProtocolId) throws RemoteException, PAFault {
        try {
            Ii iiDto = IITransformer.INSTANCE.toDto(studyProtocolId);
            List<ArmDTO> armDto = armService.getCurrentByStudyProtocol(iiDto);
            return convert(armDto);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw FaultUtil.reThrowRemote(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void copy(Id fromStudyProtocolId, Id toStudyProtocolId) throws RemoteException, PAFault {
        throw new RemoteException("Not yet implemented");
    }

    /**
     * {@inheritDoc}
     */
    public Arm create(Arm arm) throws RemoteException, PAFault {
        throw new RemoteException("Not yet implemented");
    }

    /**
     * {@inheritDoc}
     */
    public Arm update(Arm arm) throws RemoteException, PAFault {
        throw new RemoteException("Not yet implemented");
    }

    /**
     * {@inheritDoc}
     */
    public void delete(Id id) throws RemoteException, PAFault {
        throw new RemoteException("Not yet implemented");
    }

    private static Arm[] convert(List<ArmDTO> armDto) throws DtoTransformException {
        Arm[] result = new Arm[armDto.size()];
        for (int i = 0; i < armDto.size(); ++i) {
            result[i] = ArmTransformer.INSTANCE.toXml(armDto.get(i));
        }
        return result;
    }
}
