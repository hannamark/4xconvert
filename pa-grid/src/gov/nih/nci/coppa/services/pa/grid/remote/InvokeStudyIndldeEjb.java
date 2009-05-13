package gov.nih.nci.coppa.services.pa.grid.remote;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.services.grid.remote.InvokeCoppaServiceException;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyIndldeServiceRemote;

import java.util.List;

/**
 * Wrapper class for invoking the Indlde remote EJB.
 */
public class InvokeStudyIndldeEjb implements StudyIndldeServiceRemote {

    private final ServiceLocator locator = JNDIServiceLocator.getInstance();

    /**
     * {@inheritDoc}
     */
    public List<StudyIndldeDTO> getByStudyProtocol(Ii ii) throws PAException {
        try {
            List<StudyIndldeDTO> result = locator.getStudyIndldeService().getByStudyProtocol(ii);
            return result;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<StudyIndldeDTO> getCurrentByStudyProtocol(Ii ii) throws PAException {
        try {
            List<StudyIndldeDTO> result = locator.getStudyIndldeService().getCurrentByStudyProtocol(ii);
            return result;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public StudyIndldeDTO get(Ii ii) throws PAException {
        try {
            StudyIndldeDTO result = locator.getStudyIndldeService().get(ii);
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
            locator.getStudyIndldeService().copy(arg0, arg1);
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public StudyIndldeDTO create(StudyIndldeDTO arg0) throws PAException {
        try {
            StudyIndldeDTO result = locator.getStudyIndldeService().create(arg0);
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
            locator.getStudyIndldeService().delete(arg0);
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public StudyIndldeDTO update(StudyIndldeDTO arg0) throws PAException {
        try {
            StudyIndldeDTO result = locator.getStudyIndldeService().update(arg0);
            return result;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }
}
