package gov.nih.nci.coppa.services.pa.grid.remote;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.services.grid.remote.InvokeCoppaServiceException;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyContactServiceRemote;

import java.util.List;

/**
 * Wrapper class for invoking the StudyContact remote EJB.
 * @author mshestopalov
 */
public class InvokeStudyContactEjb implements StudyContactServiceRemote {

    private final ServiceLocator locator = JNDIServiceLocator.getInstance();

    /**
     * {@inheritDoc}
     */
    public void copy(Ii arg0, Ii arg1) throws PAException {
        try {
            locator.getStudyContactService().copy(arg0, arg1);
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<StudyContactDTO> getByStudyProtocol(Ii arg0) throws PAException {
        try {
            List<StudyContactDTO> result =
                locator.getStudyContactService().getByStudyProtocol(arg0);
            return result;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<StudyContactDTO> getCurrentByStudyProtocol(Ii arg0)
            throws PAException {
        try {
            List<StudyContactDTO> result =
                locator.getStudyContactService().getCurrentByStudyProtocol(arg0);
            return result;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public StudyContactDTO create(StudyContactDTO arg0) throws PAException {
        try {
            StudyContactDTO result =
                locator.getStudyContactService().create(arg0);
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
            locator.getStudyContactService().delete(arg0);
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public StudyContactDTO get(Ii arg0) throws PAException {
        try {
            StudyContactDTO result =
                locator.getStudyContactService().get(arg0);
            return result;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public StudyContactDTO update(StudyContactDTO arg0) throws PAException {
        try {
            StudyContactDTO result =
                locator.getStudyContactService().update(arg0);
            return result;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<StudyContactDTO> getByStudyProtocol(Ii arg0,
            StudyContactDTO arg1) throws PAException {
        try {
            List<StudyContactDTO> result =
                locator.getStudyContactService().getByStudyProtocol(arg0, arg1);
            return result;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<StudyContactDTO> getByStudyProtocol(Ii arg0,
            List<StudyContactDTO> arg1) throws PAException {
        try {
            List<StudyContactDTO> result =
                locator.getStudyContactService().getByStudyProtocol(arg0, arg1);
            return result;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

}
