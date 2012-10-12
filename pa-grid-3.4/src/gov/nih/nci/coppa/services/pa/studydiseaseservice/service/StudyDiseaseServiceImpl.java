package gov.nih.nci.coppa.services.pa.studydiseaseservice.service;

import gov.nih.nci.coppa.services.pa.StudyDisease;
import gov.nih.nci.coppa.services.pa.grid.GenericStudyPaGridServiceImpl;
import gov.nih.nci.pa.iso.dto.StudyDiseaseDTO;

import java.rmi.RemoteException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Implementation of the StudyDiseaseService. Dispatches to the remote EJBs and the Transformers.
 *
 * @author mshestopalov
 */
public class StudyDiseaseServiceImpl extends StudyDiseaseServiceImplBase {

    private static final Logger logger = LogManager.getLogger(StudyDiseaseServiceImpl.class);

    static {
        gov.nih.nci.coppa.services.pa.grid.PAGridUtils.initIso21090Transformers();
    }


    public StudyDiseaseServiceImpl() throws RemoteException {
        super();
    }

    private GenericStudyPaGridServiceImpl<StudyDiseaseDTO, StudyDisease> impl
    = new GenericStudyPaGridServiceImpl<StudyDiseaseDTO, StudyDisease>(StudyDisease.class, StudyDiseaseDTO.class);

  public gov.nih.nci.coppa.services.pa.StudyDisease[] getByStudyProtocol(gov.nih.nci.iso21090.extensions.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
      return impl.getByStudyProtocol(id);
  }

  public void copy(gov.nih.nci.iso21090.extensions.Id fromStudyProtocolId,gov.nih.nci.iso21090.extensions.Id toStudyProtocolId) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    //TODO: Implement this autogenerated method
    throw new RemoteException("Not yet implemented");
  }

  public gov.nih.nci.coppa.services.pa.StudyDisease get(gov.nih.nci.iso21090.extensions.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
      return impl.get(id);
  }

  public gov.nih.nci.coppa.services.pa.StudyDisease create(gov.nih.nci.coppa.services.pa.StudyDisease studyDisease) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    //TODO: Implement this autogenerated method
      throw new RemoteException("Not yet implemented");
  }

  public gov.nih.nci.coppa.services.pa.StudyDisease update(gov.nih.nci.coppa.services.pa.StudyDisease studyDisease) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    //TODO: Implement this autogenerated method
      throw new RemoteException("Not yet implemented");
  }

  public void delete(gov.nih.nci.iso21090.extensions.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    //TODO: Implement this autogenerated method
      throw new RemoteException("Not yet implemented");
  }

}

