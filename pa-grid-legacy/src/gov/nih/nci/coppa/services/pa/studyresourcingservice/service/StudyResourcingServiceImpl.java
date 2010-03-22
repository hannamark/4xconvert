package gov.nih.nci.coppa.services.pa.studyresourcingservice.service;

import gov.nih.nci.coppa.services.pa.armservice.service.ArmServiceImpl;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.StudyResourcingTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.faults.FaultUtil;
import gov.nih.nci.coppa.services.pa.grid.remote.InvokeStudyResourcingEjb;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.grid.dto.transform.iso.IITransformer;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;

import java.rmi.RemoteException;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Implementation of the StudyResourcingService. Dispatches to the remote EJBs and the Transformers.
 */
public class StudyResourcingServiceImpl extends StudyResourcingServiceImplBase {

    private static final Logger logger = LogManager.getLogger(ArmServiceImpl.class);
    private final InvokeStudyResourcingEjb srService = new InvokeStudyResourcingEjb();

    public StudyResourcingServiceImpl() throws RemoteException {
        super();
    }

  public gov.nih.nci.coppa.services.pa.StudyResourcing getSummaryForReportedResource(gov.nih.nci.coppa.services.pa.Id studyProtocolId) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        try {
            Ii iiDto = IITransformer.INSTANCE.toDto(studyProtocolId);
            StudyResourcingDTO studyResourcingDto = srService.getsummary4ReportedResource(iiDto);
            return StudyResourcingTransformer.INSTANCE.toXml(studyResourcingDto);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw FaultUtil.reThrowRemote(e);
        }
    }

  public gov.nih.nci.coppa.services.pa.StudyResourcing updateStudyResourcing(gov.nih.nci.coppa.services.pa.StudyResourcing studyResourcing) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        throw new RemoteException("Not yet implemented");
    }

  public gov.nih.nci.coppa.services.pa.StudyResourcing createStudyResourcing(gov.nih.nci.coppa.services.pa.StudyResourcing studyResourcing) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        throw new RemoteException("Not yet implemented");
    }

  public gov.nih.nci.coppa.services.pa.StudyResourcing[] getStudyResourceByStudyProtocol(gov.nih.nci.coppa.services.pa.Id studyProtocolId) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        try {
            Ii iiDto = IITransformer.INSTANCE.toDto(studyProtocolId);
            List<StudyResourcingDTO> studyResourcingDto = srService.getstudyResourceByStudyProtocol(iiDto);
            return StudyResourcingTransformer.INSTANCE.convert(studyResourcingDto);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw FaultUtil.reThrowRemote(e);
        }
    }

  public gov.nih.nci.coppa.services.pa.StudyResourcing getStudyResourceByID(gov.nih.nci.coppa.services.pa.Id studyResourceId) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        try {
            Ii iiDto = IITransformer.INSTANCE.toDto(studyResourceId);
            StudyResourcingDTO studyResourcingDto = srService.getStudyResourceByID(iiDto);
            return StudyResourcingTransformer.INSTANCE.toXml(studyResourcingDto);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw FaultUtil.reThrowRemote(e);
        }
    }

  public void deleteStudyResourceByID(gov.nih.nci.coppa.services.pa.StudyResourcing studyResourcing) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        throw new RemoteException("Not yet implemented");
    }

}
