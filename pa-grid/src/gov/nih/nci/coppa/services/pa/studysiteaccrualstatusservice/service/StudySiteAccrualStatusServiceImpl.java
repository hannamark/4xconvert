package gov.nih.nci.coppa.services.pa.studysiteaccrualstatusservice.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.DtoTransformException;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.IITransformer;
import gov.nih.nci.coppa.services.pa.Id;
import gov.nih.nci.coppa.services.pa.StudySiteAccrualStatus;
import gov.nih.nci.coppa.services.pa.faults.PAFault;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.StudySiteAccrualStatusTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.faults.FaultUtil;
import gov.nih.nci.coppa.services.pa.grid.remote.InvokeStudySiteAccrualStatusEjb;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;

import java.rmi.RemoteException;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Implementation of the StudySiteAccrualStatusService. Dispatches to the remote EJBs and the Transformers.
 */
public class StudySiteAccrualStatusServiceImpl extends StudySiteAccrualStatusServiceImplBase {

    private static final Logger logger = LogManager.getLogger(StudySiteAccrualStatusServiceImpl.class);
    private final InvokeStudySiteAccrualStatusEjb ejb = new InvokeStudySiteAccrualStatusEjb();

    public StudySiteAccrualStatusServiceImpl() throws RemoteException {
        super();
    }

    /**
     * {@inheritDoc}
     */
  public gov.nih.nci.coppa.services.pa.StudySiteAccrualStatus getStudySiteAccrualStatus(gov.nih.nci.coppa.services.pa.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        try {
            Ii iiDto = IITransformer.INSTANCE.toDto(id);
            StudySiteAccrualStatusDTO dto = ejb.getStudySiteAccrualStatus(iiDto);
            return StudySiteAccrualStatusTransformer.INSTANCE.toXml(dto);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw FaultUtil.reThrowRemote(e);
        }
    }

    /**
     * {@inheritDoc}
     */
  public gov.nih.nci.coppa.services.pa.StudySiteAccrualStatus[] getStudySiteAccrualStatusByStudyParticipation(gov.nih.nci.coppa.services.pa.Id studyParticipationId) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        try {
            Ii iiDto = IITransformer.INSTANCE.toDto(studyParticipationId);
            List<StudySiteAccrualStatusDTO> dtoList = ejb.getStudySiteAccrualStatusByStudyParticipation(iiDto);
            return convert(dtoList);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw FaultUtil.reThrowRemote(e);
        }
    }

    /**
     * {@inheritDoc}
     */
  public gov.nih.nci.coppa.services.pa.StudySiteAccrualStatus[] getCurrentStudySiteAccrualStatusByStudyParticipation(gov.nih.nci.coppa.services.pa.Id studyParticipationId) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        try {
            Ii iiDto = IITransformer.INSTANCE.toDto(studyParticipationId);
            List<StudySiteAccrualStatusDTO> dtoList = ejb.getCurrentStudySiteAccrualStatusByStudyParticipation(iiDto);
            return convert(dtoList);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw FaultUtil.reThrowRemote(e);
        }
    }

    /**
     * {@inheritDoc}
     */
  public gov.nih.nci.coppa.services.pa.StudySiteAccrualStatus createStudySiteAccrualStatus(gov.nih.nci.coppa.services.pa.StudySiteAccrualStatus studySiteAccrualStatus) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        throw new RemoteException("Not yet implemented");
    }

    /**
     * {@inheritDoc}
     */
  public gov.nih.nci.coppa.services.pa.StudySiteAccrualStatus updateStudySiteAccrualStatus(gov.nih.nci.coppa.services.pa.StudySiteAccrualStatus studySiteAccrualStatus) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        throw new RemoteException("Not yet implemented");
    }

    private static StudySiteAccrualStatus[] convert(List<StudySiteAccrualStatusDTO> dtoList)
            throws DtoTransformException {
        StudySiteAccrualStatus[] result = new StudySiteAccrualStatus[dtoList.size()];
        for (int i = 0; i < dtoList.size(); ++i) {
            result[i] = StudySiteAccrualStatusTransformer.INSTANCE.toXml(dtoList.get(i));
        }
        return result;
    }
}
