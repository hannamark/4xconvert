package gov.nih.nci.coppa.services.pa.studyindideservice.service;

import gov.nih.nci.coppa.services.pa.StudyIndlde;
import gov.nih.nci.coppa.services.pa.grid.GenericStudyPaGridServiceImpl;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;

import java.rmi.RemoteException;

/**
 * Implementation of the StudyIndIdeService. Dispatches to the remote EJBs and the Transformers.
 *
 * @author mshestopalov
 */
public class StudyIndIdeServiceImpl extends StudyIndIdeServiceImplBase {

    public StudyIndIdeServiceImpl() throws RemoteException {
        super();
    }

    private GenericStudyPaGridServiceImpl<StudyIndldeDTO, StudyIndlde> impl
    = new GenericStudyPaGridServiceImpl<StudyIndldeDTO, StudyIndlde>(StudyIndlde.class, StudyIndldeDTO.class);

  public gov.nih.nci.coppa.services.pa.StudyIndlde[] getByStudyProtocol(gov.nih.nci.coppa.services.pa.Id studyProtocolId) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
      return impl.getByStudyProtocol(studyProtocolId);
  }

  public void copy(gov.nih.nci.coppa.services.pa.Id fromStudyProtocolId,gov.nih.nci.coppa.services.pa.Id toStudyProtocolId) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    //TODO: Implement this autogenerated method
    throw new RemoteException("Not yet implemented");
  }

  public gov.nih.nci.coppa.services.pa.StudyIndlde getCurrentByStudyProtocol(gov.nih.nci.coppa.services.pa.Id studyProtocolId) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
      return impl.getCurrentByStudyProtocol(studyProtocolId);
  }

  public gov.nih.nci.coppa.services.pa.StudyIndlde get(gov.nih.nci.coppa.services.pa.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
      return impl.get(id);
  }

  public gov.nih.nci.coppa.services.pa.StudyIndlde create(gov.nih.nci.coppa.services.pa.StudyIndlde studyIndlde) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    //TODO: Implement this autogenerated method
    throw new RemoteException("Not yet implemented");
  }

  public gov.nih.nci.coppa.services.pa.StudyIndlde update(gov.nih.nci.coppa.services.pa.StudyIndlde studyIndlde) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    //TODO: Implement this autogenerated method
    throw new RemoteException("Not yet implemented");
  }

  public void delete(gov.nih.nci.coppa.services.pa.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    //TODO: Implement this autogenerated method
    throw new RemoteException("Not yet implemented");
  }

}

