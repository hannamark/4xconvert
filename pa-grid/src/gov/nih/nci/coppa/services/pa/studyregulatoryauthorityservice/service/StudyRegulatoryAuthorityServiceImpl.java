package gov.nih.nci.coppa.services.pa.studyregulatoryauthorityservice.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.services.pa.Id;
import gov.nih.nci.coppa.services.pa.StudyRegulatoryAuthority;
import gov.nih.nci.coppa.services.pa.armservice.service.ArmServiceImpl;
import gov.nih.nci.coppa.services.pa.faults.PAFault;
import gov.nih.nci.coppa.services.pa.grid.dto.IITransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.StudyRegulatoryAuthorityTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.faults.FaultUtil;
import gov.nih.nci.coppa.services.pa.grid.remote.InvokeStudyRegulatoryAuthorityEjb;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;
import gov.nih.nci.pa.service.StudyRegulatoryAuthorityServiceRemote;

import java.rmi.RemoteException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Implementation of the StudyRegulatoryAuthorityService. Dispatches to the remote EJBs and the Transformers.
 *
 */
public class StudyRegulatoryAuthorityServiceImpl extends StudyRegulatoryAuthorityServiceImplBase {

    private static final Logger logger = LogManager.getLogger(ArmServiceImpl.class);
    private final StudyRegulatoryAuthorityServiceRemote sraService = new InvokeStudyRegulatoryAuthorityEjb();

    public StudyRegulatoryAuthorityServiceImpl() throws RemoteException {
        super();
    }

    public StudyRegulatoryAuthority getByStudyProtocol(Id studyProtocolId) throws RemoteException, PAFault {
        try {
            Ii iiDto = IITransformer.INSTANCE.toDto(studyProtocolId);
            StudyRegulatoryAuthorityDTO studyRegulatoryAuthorityDto = sraService.getByStudyProtocol(iiDto);
            return StudyRegulatoryAuthorityTransformer.INSTANCE.toXml(studyRegulatoryAuthorityDto);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw FaultUtil.reThrowRemote(e);
        }
    }

    public StudyRegulatoryAuthority create(StudyRegulatoryAuthority studyRegulatoryAuthority) throws RemoteException,
            PAFault {
        throw new RemoteException("Not yet implemented");
    }

    public StudyRegulatoryAuthority update(StudyRegulatoryAuthority studyRegulatoryAuthority) throws RemoteException,
            PAFault {
        throw new RemoteException("Not yet implemented");
    }

    public void copy(Id fromStudyProtocolId, Id toStudyProtocolId) throws RemoteException, PAFault {
        throw new RemoteException("Not yet implemented");
    }

}
