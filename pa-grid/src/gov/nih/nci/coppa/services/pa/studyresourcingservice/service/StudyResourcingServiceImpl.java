package gov.nih.nci.coppa.services.pa.studyresourcingservice.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.services.pa.Id;
import gov.nih.nci.coppa.services.pa.StudyResourcing;
import gov.nih.nci.coppa.services.pa.armservice.service.ArmServiceImpl;
import gov.nih.nci.coppa.services.pa.faults.PAFault;
import gov.nih.nci.coppa.services.pa.grid.dto.DtoTransformException;
import gov.nih.nci.coppa.services.pa.grid.dto.IITransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.StudyResourcingTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.faults.FaultUtil;
import gov.nih.nci.coppa.services.pa.grid.remote.InvokeStudyResourcingEjb;
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

    public StudyResourcing getSummaryForReportedResource(Id studyProtocolId) throws RemoteException, PAFault {
        try {
            Ii iiDto = IITransformer.INSTANCE.toDto(studyProtocolId);
            StudyResourcingDTO studyResourcingDto = srService.getsummary4ReportedResource(iiDto);
            return StudyResourcingTransformer.INSTANCE.toXml(studyResourcingDto);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw FaultUtil.reThrowRemote(e);
        }
    }

    public StudyResourcing updateStudyResourcing(StudyResourcing studyResourcing) throws RemoteException, PAFault {
        throw new RemoteException("Not yet implemented");
    }

    public StudyResourcing createStudyResourcing(StudyResourcing studyResourcing) throws RemoteException, PAFault {
        throw new RemoteException("Not yet implemented");
    }

    public StudyResourcing[] getStudyResourceByStudyProtocol(Id studyProtocolId) throws RemoteException, PAFault {
        try {
            Ii iiDto = IITransformer.INSTANCE.toDto(studyProtocolId);
            List<StudyResourcingDTO> studyResourcingDto = srService.getstudyResourceByStudyProtocol(iiDto);
            return convert(studyResourcingDto);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw FaultUtil.reThrowRemote(e);
        }
    }

    public StudyResourcing getStudyResourceByID(Id studyResourceId) throws RemoteException, PAFault {
        try {
            Ii iiDto = IITransformer.INSTANCE.toDto(studyResourceId);
            StudyResourcingDTO studyResourcingDto = srService.getStudyResourceByID(iiDto);
            return StudyResourcingTransformer.INSTANCE.toXml(studyResourcingDto);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw FaultUtil.reThrowRemote(e);
        }
    }

    public void deleteStudyResourceByID(StudyResourcing studyResourcing) throws RemoteException, PAFault {
        throw new RemoteException("Not yet implemented");
    }

    private static StudyResourcing[] convert(List<StudyResourcingDTO> studyResourcingDto) throws DtoTransformException {
        StudyResourcing[] result = new StudyResourcing[studyResourcingDto.size()];
        for (int i = 0; i < studyResourcingDto.size(); ++i) {
            result[i] = StudyResourcingTransformer.INSTANCE.toXml(studyResourcingDto.get(i));
        }
        return result;
    }


}
