package gov.nih.nci.coppa.services.pa.grid.remote;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.iso.dto.StudyParticipationContactDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyParticipationContactServiceRemote;

import java.util.List;


/**
 * Wrapper class for invoking the StudyParticipantContact remote EJB.
 */
public class InvokeStudyParticipationContactEjb implements StudyParticipationContactServiceRemote {

    private final ServiceLocator locator = JNDIServiceLocator.getInstance();

    /**
     * {@inheritDoc}
     */
    public List<StudyParticipationContactDTO> getByStudyParticipation(Ii arg0)
            throws PAException {
        try {
            List<StudyParticipationContactDTO> result =
                locator.getStudyParticipationContactService().getByStudyParticipation(arg0);
            return result;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<StudyParticipationContactDTO> getByStudyProtocol(Ii arg0,
            StudyParticipationContactDTO arg1) throws PAException {
        try {
            List<StudyParticipationContactDTO> result =
                locator.getStudyParticipationContactService().getByStudyProtocol(arg0, arg1);
            return result;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<StudyParticipationContactDTO> getByStudyProtocol(Ii arg0,
            List<StudyParticipationContactDTO> arg1) throws PAException {
        try {
            List<StudyParticipationContactDTO> result =
                locator.getStudyParticipationContactService().getByStudyProtocol(arg0, arg1);
            return result;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void copy(Ii arg0, Ii arg1) throws PAException {
        try {
            locator.getStudyParticipationContactService().copy(arg0, arg1);
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }

    }

    /**
     * {@inheritDoc}
     */
    public List<StudyParticipationContactDTO> getByStudyProtocol(Ii arg0)
            throws PAException {
        try {
            List<StudyParticipationContactDTO> result =
                locator.getStudyParticipationContactService().getByStudyProtocol(arg0);
            return result;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<StudyParticipationContactDTO> getCurrentByStudyProtocol(Ii arg0)
            throws PAException {
        try {
            List<StudyParticipationContactDTO> result =
                locator.getStudyParticipationContactService().getCurrentByStudyProtocol(arg0);
            return result;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public StudyParticipationContactDTO create(StudyParticipationContactDTO arg0)
            throws PAException {
        try {
            StudyParticipationContactDTO result =
                locator.getStudyParticipationContactService().create(arg0);
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
            locator.getStudyParticipationContactService().delete(arg0);
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }

    }

    /**
     * {@inheritDoc}
     */
    public StudyParticipationContactDTO get(Ii arg0) throws PAException {
        try {
            StudyParticipationContactDTO result =
                locator.getStudyParticipationContactService().get(arg0);
            return result;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public StudyParticipationContactDTO update(StudyParticipationContactDTO arg0)
            throws PAException {
        try {
            StudyParticipationContactDTO result =
                locator.getStudyParticipationContactService().update(arg0);
            return result;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }



}
