package gov.nih.nci.coppa.services.pa.studyonholdservice.service;

import gov.nih.nci.coppa.iso.Bl;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.IITransformer;
import gov.nih.nci.coppa.services.pa.BL;
import gov.nih.nci.coppa.services.pa.StudyOnhold;
import gov.nih.nci.coppa.services.pa.grid.GenericStudyPaGridServiceImpl;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.faults.FaultUtil;
import gov.nih.nci.coppa.services.pa.grid.remote.InvokeStudyOnholdEjb;
import gov.nih.nci.pa.iso.dto.StudyOnholdDTO;
import gov.nih.nci.pa.iso.util.BlConverter;

import java.rmi.RemoteException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Implementation of the StudyOnholdService. Dispatches to the remote EJBs and the Transformers.
 */
public class StudyOnholdServiceImpl extends StudyOnholdServiceImplBase {

    private static final Logger logger = LogManager.getLogger(StudyOnholdServiceImpl.class);
    private final InvokeStudyOnholdEjb studyOnholdService = new InvokeStudyOnholdEjb();

    public StudyOnholdServiceImpl() throws RemoteException {
        super();
    }

    private GenericStudyPaGridServiceImpl<StudyOnholdDTO, StudyOnhold> impl
    = new GenericStudyPaGridServiceImpl<StudyOnholdDTO, StudyOnhold>(StudyOnhold.class, StudyOnholdDTO.class);

  public gov.nih.nci.coppa.services.pa.StudyOnhold get(gov.nih.nci.coppa.services.pa.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        return impl.get(id);
    }

  public gov.nih.nci.coppa.services.pa.StudyOnhold[] getByStudyProtocol(gov.nih.nci.coppa.services.pa.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        return impl.getByStudyProtocol(id);
    }

  public gov.nih.nci.coppa.services.pa.StudyOnhold getCurrentByStudyProtocol(gov.nih.nci.coppa.services.pa.Id studyProtocolId) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
      return impl.getCurrentByStudyProtocol(studyProtocolId);
    }

  public gov.nih.nci.coppa.services.pa.BL isOnhold(gov.nih.nci.coppa.services.pa.Id studyProtocolId) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        try {
            Ii iiDto = IITransformer.INSTANCE.toDto(studyProtocolId);
            Bl isOnhold = studyOnholdService.isOnhold(iiDto);
            BL result = new BL();
            result.setValue(BlConverter.covertToBoolean(isOnhold));
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw FaultUtil.reThrowRemote(e);
        }
      }

  public void copy(gov.nih.nci.coppa.services.pa.Id fromStudyProtocolId,gov.nih.nci.coppa.services.pa.Id toStudyProtocolId) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        throw new RemoteException("Not yet implemented");
    }

  public gov.nih.nci.coppa.services.pa.StudyOnhold create(gov.nih.nci.coppa.services.pa.StudyOnhold studyOnhold) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        throw new RemoteException("Not yet implemented");
    }

  public gov.nih.nci.coppa.services.pa.StudyOnhold update(gov.nih.nci.coppa.services.pa.StudyOnhold studyOnhold) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        throw new RemoteException("Not yet implemented");
    }

  public void delete(gov.nih.nci.coppa.services.pa.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        throw new RemoteException("Not yet implemented");
    }

}

