package gov.nih.nci.coppa.services.pa.grid.remote;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.services.grid.remote.InvokeCoppaServiceException;
import gov.nih.nci.pa.iso.dto.StudyParticipationDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyParticipationServiceRemote;

import java.util.List;


/**
 * Wrapper class for invoking the StudyParticipant remote EJB.
 */
public class InvokeStudyParticipationEjb implements StudyParticipationServiceRemote {

    private final ServiceLocator locator = JNDIServiceLocator.getInstance();


    /**
     * {@inheritDoc}
     */
    public void copy(Ii arg0, Ii arg1) throws PAException {
        try {
            locator.getStudyParticipationService().copy(arg0, arg1);
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }

    }

    /**
     * {@inheritDoc}
     */
    public List<StudyParticipationDTO> getByStudyProtocol(Ii arg0)
            throws PAException {
        try {
            List<StudyParticipationDTO> result =
                locator.getStudyParticipationService().getByStudyProtocol(arg0);
            return result;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<StudyParticipationDTO> getCurrentByStudyProtocol(Ii arg0)
            throws PAException {
        try {
            List<StudyParticipationDTO> result =
                locator.getStudyParticipationService().getCurrentByStudyProtocol(arg0);
            return result;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public StudyParticipationDTO create(StudyParticipationDTO arg0)
            throws PAException {
        try {
            StudyParticipationDTO result =
                locator.getStudyParticipationService().create(arg0);
            return result;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void delete(Ii arg0) throws PAException {
        try {
            locator.getStudyParticipationService().delete(arg0);
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }

    }

    /**
     * {@inheritDoc}
     */
    public StudyParticipationDTO get(Ii arg0) throws PAException {

        try {
            StudyParticipationDTO result =
                locator.getStudyParticipationService().get(arg0);
            return result;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public StudyParticipationDTO update(StudyParticipationDTO arg0)
            throws PAException {
        try {
            StudyParticipationDTO result =
                locator.getStudyParticipationService().update(arg0);
            return result;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<StudyParticipationDTO> getByStudyProtocol(Ii arg0,
            StudyParticipationDTO arg1) throws PAException {
        try {
            List<StudyParticipationDTO> result =
                locator.getStudyParticipationService().getByStudyProtocol(arg0, arg1);
            return result;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<StudyParticipationDTO> getByStudyProtocol(Ii arg0,
            List<StudyParticipationDTO> arg1) throws PAException {
        try {
            List<StudyParticipationDTO> result =
                locator.getStudyParticipationService().getByStudyProtocol(arg0, arg1);
            return result;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }



}
