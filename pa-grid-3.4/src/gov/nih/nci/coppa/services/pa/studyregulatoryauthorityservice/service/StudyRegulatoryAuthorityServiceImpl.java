package gov.nih.nci.coppa.services.pa.studyregulatoryauthorityservice.service;

import gov.nih.nci.coppa.services.pa.StudyRegulatoryAuthority;
import gov.nih.nci.coppa.services.pa.grid.GenericStudyCurrentPaGridServiceImpl;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;

import java.rmi.RemoteException;

/**
 * Implementation of the StudyRegulatoryAuthorityService. Dispatches to the remote EJBs and the Transformers.
 */
public class StudyRegulatoryAuthorityServiceImpl extends StudyRegulatoryAuthorityServiceImplBase {

    static {
        gov.nih.nci.coppa.services.pa.grid.PAGridUtils.initIso21090Transformers();
    }


    private GenericStudyCurrentPaGridServiceImpl<StudyRegulatoryAuthorityDTO, StudyRegulatoryAuthority> impl =
            new GenericStudyCurrentPaGridServiceImpl<StudyRegulatoryAuthorityDTO, StudyRegulatoryAuthority>(
                    StudyRegulatoryAuthority.class, StudyRegulatoryAuthorityDTO.class);

    public StudyRegulatoryAuthorityServiceImpl() throws RemoteException {
        super();
    }

  public gov.nih.nci.coppa.services.pa.StudyRegulatoryAuthority[] getByStudyProtocol(gov.nih.nci.iso21090.extensions.Id studyProtocolId) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        return impl.getByStudyProtocol(studyProtocolId);
    }

  public gov.nih.nci.coppa.services.pa.StudyRegulatoryAuthority getCurrentByStudyProtocol(gov.nih.nci.iso21090.extensions.Id studyProtocolId) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
      return impl.getCurrentByStudyProtocol(studyProtocolId);
    }

  public gov.nih.nci.coppa.services.pa.StudyRegulatoryAuthority get(gov.nih.nci.iso21090.extensions.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        return impl.get(id);
    }

  public gov.nih.nci.coppa.services.pa.StudyRegulatoryAuthority create(gov.nih.nci.coppa.services.pa.StudyRegulatoryAuthority studyRegulatoryAuthority) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        throw new RemoteException("Not yet implemented");
    }

  public gov.nih.nci.coppa.services.pa.StudyRegulatoryAuthority update(gov.nih.nci.coppa.services.pa.StudyRegulatoryAuthority studyRegulatoryAuthority) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        throw new RemoteException("Not yet implemented");
    }

  public void copy(gov.nih.nci.iso21090.extensions.Id fromStudyProtocolId,gov.nih.nci.iso21090.extensions.Id toStudyProtocolId) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        throw new RemoteException("Not yet implemented");
    }

  public void delete(gov.nih.nci.iso21090.extensions.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        throw new RemoteException("Not yet implemented");
    }

}
