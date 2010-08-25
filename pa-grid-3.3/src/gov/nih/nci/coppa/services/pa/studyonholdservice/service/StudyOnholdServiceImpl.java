package gov.nih.nci.coppa.services.pa.studyonholdservice.service;

import gov.nih.nci.coppa.services.pa.StudyOnhold;
import gov.nih.nci.coppa.services.pa.grid.GenericStudyPaGridServiceImpl;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.faults.FaultUtil;
import gov.nih.nci.coppa.services.pa.grid.remote.InvokeStudyOnholdEjb;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.extensions.Bl;
import gov.nih.nci.iso21090.grid.dto.transform.iso.IITransformer;
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

  public gov.nih.nci.coppa.services.pa.StudyOnhold get(gov.nih.nci.iso21090.extensions.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        return impl.get(id);
    }

  public gov.nih.nci.coppa.services.pa.StudyOnhold[] getByStudyProtocol(gov.nih.nci.iso21090.extensions.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        return impl.getByStudyProtocol(id);
    }

  public gov.nih.nci.iso21090.extensions.Bl isOnhold(gov.nih.nci.iso21090.extensions.Id studyProtocolId) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        try {
            Ii iiDto = IITransformer.INSTANCE.toDto(studyProtocolId);
            gov.nih.nci.iso21090.Bl isOnhold = studyOnholdService.isOnhold(iiDto);
            Bl result = new Bl();
            result.setValue(BlConverter.convertToBoolean(isOnhold));
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw FaultUtil.reThrowRemote(e);
        }
      }

  public void copy(gov.nih.nci.iso21090.extensions.Id fromStudyProtocolId,gov.nih.nci.iso21090.extensions.Id toStudyProtocolId) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        throw new RemoteException("Not yet implemented");
    }

  public gov.nih.nci.coppa.services.pa.StudyOnhold create(gov.nih.nci.coppa.services.pa.StudyOnhold studyOnhold) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        throw new RemoteException("Not yet implemented");
    }

  public gov.nih.nci.coppa.services.pa.StudyOnhold update(gov.nih.nci.coppa.services.pa.StudyOnhold studyOnhold) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        throw new RemoteException("Not yet implemented");
    }

  public void delete(gov.nih.nci.iso21090.extensions.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        throw new RemoteException("Not yet implemented");
    }

}

