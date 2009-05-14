package gov.nih.nci.coppa.services.pa.studyparticipationcontactservice.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.services.grid.dto.transform.DtoTransformException;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.IITransformer;
import gov.nih.nci.coppa.services.pa.Id;
import gov.nih.nci.coppa.services.pa.StudyParticipationContact;
import gov.nih.nci.coppa.services.pa.faults.PAFault;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.StudyParticipationContactTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.faults.FaultUtil;
import gov.nih.nci.coppa.services.pa.grid.remote.InvokeStudyParticipationContactEjb;
import gov.nih.nci.pa.iso.dto.StudyParticipationContactDTO;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Implementation of the StudyParticipationContactService. Dispatches to the remote EJBs and the Transformers.
 *
 * @author mshestopalov
 */
public class StudyParticipationContactServiceImpl extends StudyParticipationContactServiceImplBase {

    private static final Logger logger = LogManager.getLogger(StudyParticipationContactServiceImpl.class);
    private final InvokeStudyParticipationContactEjb studyParContService = new InvokeStudyParticipationContactEjb();

    public StudyParticipationContactServiceImpl() throws RemoteException {
        super();
    }

    /**
     * {@inheritDoc}
     */
  public gov.nih.nci.coppa.services.pa.StudyParticipationContact[] getByStudyParticipation(gov.nih.nci.coppa.services.pa.Id studyParticipationId) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        StudyParticipationContact[] result = null;
        try {
            Ii iiDto = IITransformer.INSTANCE.toDto(studyParticipationId);
            List<StudyParticipationContactDTO> dtosList = studyParContService.getByStudyParticipation(iiDto);

            result = new StudyParticipationContact[dtosList.size()];
            int i = 0;
            for (StudyParticipationContactDTO tEmp : dtosList) {
                result[i] = StudyParticipationContactTransformer.INSTANCE.toXml(tEmp);
                i++;
            }
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw FaultUtil.reThrowRemote(e);
        }
    }

    /**
     * {@inheritDoc}
     */
  public gov.nih.nci.coppa.services.pa.StudyParticipationContact[] getByStudyProtocolAndRole(gov.nih.nci.coppa.services.pa.Id studyProtocolId,gov.nih.nci.coppa.services.pa.StudyParticipationContact studyParticipationContact) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        StudyParticipationContact[] result = null;
        try {
            Ii iiDto = IITransformer.INSTANCE.toDto(studyProtocolId);
            StudyParticipationContactDTO spcDto = StudyParticipationContactTransformer.INSTANCE
                    .toDto(studyParticipationContact);
            List<StudyParticipationContactDTO> dtosList = studyParContService.getByStudyProtocol(iiDto, spcDto);

            result = new StudyParticipationContact[dtosList.size()];
            int i = 0;
            for (StudyParticipationContactDTO tEmp : dtosList) {
                result[i] = StudyParticipationContactTransformer.INSTANCE.toXml(tEmp);
                i++;
            }
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw FaultUtil.reThrowRemote(e);
        }
    }

    /**
     * {@inheritDoc}
     */
  public gov.nih.nci.coppa.services.pa.StudyParticipationContact[] getByStudyProtocolAndRoles(gov.nih.nci.coppa.services.pa.Id studyProtocolId,gov.nih.nci.coppa.services.pa.StudyParticipationContact[] studyParticipationContact) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        StudyParticipationContact[] result = null;
        try {
            Ii iiDto = IITransformer.INSTANCE.toDto(studyProtocolId);
            List<StudyParticipationContactDTO> dtosList =
                    studyParContService.getByStudyProtocol(iiDto, convert(studyParticipationContact));
            result = new StudyParticipationContact[dtosList.size()];
            int i = 0;
            for (StudyParticipationContactDTO tEmp : dtosList) {
                result[i] = StudyParticipationContactTransformer.INSTANCE.toXml(tEmp);
                i++;
            }
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw FaultUtil.reThrowRemote(e);
        }
    }

    private static final List<StudyParticipationContactDTO> convert(StudyParticipationContact[] studyParticipationContact)
            throws DtoTransformException {
        List<StudyParticipationContactDTO> spcDtos = new ArrayList<StudyParticipationContactDTO>();
        for (int i = 0; i < studyParticipationContact.length; i++) {
            spcDtos.add(StudyParticipationContactTransformer.INSTANCE.toDto(studyParticipationContact[i]));
        }
        return spcDtos;
    }

    /**
     * {@inheritDoc}
     */
  public gov.nih.nci.coppa.services.pa.StudyParticipationContact[] getByStudyProtocol(gov.nih.nci.coppa.services.pa.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        StudyParticipationContact[] result = null;
        try {
            Ii iiDto = IITransformer.INSTANCE.toDto(id);
            List<StudyParticipationContactDTO> dtosList = studyParContService.getByStudyProtocol(iiDto);
            result = new StudyParticipationContact[dtosList.size()];
            int i = 0;
            for (StudyParticipationContactDTO tEmp : dtosList) {
                result[i] = StudyParticipationContactTransformer.INSTANCE.toXml(tEmp);
                i++;
            }
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw FaultUtil.reThrowRemote(e);
        }
    }

    /**
     * {@inheritDoc}
     */
  public void copy(gov.nih.nci.coppa.services.pa.Id fromStudyProtocolId,gov.nih.nci.coppa.services.pa.Id toStudyProtocolId) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        throw new RemoteException("Not yet implemented");
    }

    /**
     * {@inheritDoc}
     */
  public gov.nih.nci.coppa.services.pa.StudyParticipationContact[] getCurrentByStudyProtocol(gov.nih.nci.coppa.services.pa.Id studyProtocolId) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        StudyParticipationContact[] result = null;
        try {
            Ii iiDto = IITransformer.INSTANCE.toDto(studyProtocolId);
            List<StudyParticipationContactDTO> dtosList = studyParContService.getCurrentByStudyProtocol(iiDto);
            result = new StudyParticipationContact[dtosList.size()];
            int i = 0;
            for (StudyParticipationContactDTO tEmp : dtosList) {
                result[i] = StudyParticipationContactTransformer.INSTANCE.toXml(tEmp);
                i++;
            }
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw FaultUtil.reThrowRemote(e);
        }
    }

    /**
     * {@inheritDoc}
     */
  public gov.nih.nci.coppa.services.pa.StudyParticipationContact get(gov.nih.nci.coppa.services.pa.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        try {
            Ii iiDto = IITransformer.INSTANCE.toDto(id);
            StudyParticipationContactDTO result = studyParContService.get(iiDto);
            return StudyParticipationContactTransformer.INSTANCE.toXml(result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw FaultUtil.reThrowRemote(e);
        }

    }

    /**
     * {@inheritDoc}
     */
  public gov.nih.nci.coppa.services.pa.StudyParticipationContact create(gov.nih.nci.coppa.services.pa.StudyParticipationContact studyParticipationContact) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        throw new RemoteException("Not yet implemented");
    }

    /**
     * {@inheritDoc}
     */
  public gov.nih.nci.coppa.services.pa.StudyParticipationContact update(gov.nih.nci.coppa.services.pa.StudyParticipationContact studyParticipationContact) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        throw new RemoteException("Not yet implemented");
    }

    /**
     * {@inheritDoc}
     */
  public void delete(gov.nih.nci.coppa.services.pa.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        throw new RemoteException("Not yet implemented");
    }

}
