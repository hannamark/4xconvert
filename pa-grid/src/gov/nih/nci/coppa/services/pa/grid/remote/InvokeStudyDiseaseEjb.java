package gov.nih.nci.coppa.services.pa.grid.remote;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.services.grid.remote.InvokeCoppaServiceException;
import gov.nih.nci.pa.iso.dto.StudyDiseaseDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyDiseaseServiceRemote;

import java.util.List;

/**
 * Wrapper class for invoking the StudyDisease remote EJB.
 * @author mshestopalov
 */
public class InvokeStudyDiseaseEjb implements StudyDiseaseServiceRemote {

    private final ServiceLocator locator = JNDIServiceLocator.getInstance();

    /**
     * {@inheritDoc}
     */
    public void copy(Ii arg0, Ii arg1) throws PAException {
        try {
            locator.getStudyDiseaseService().copy(arg0, arg1);
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<StudyDiseaseDTO> getByStudyProtocol(Ii arg0) throws PAException {
        try {
            List<StudyDiseaseDTO> result =
                locator.getStudyDiseaseService().getByStudyProtocol(arg0);
            return result;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<StudyDiseaseDTO> getCurrentByStudyProtocol(Ii arg0)
            throws PAException {
        try {
            List<StudyDiseaseDTO> result =
                locator.getStudyDiseaseService().getCurrentByStudyProtocol(arg0);
            return result;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public StudyDiseaseDTO create(StudyDiseaseDTO arg0) throws PAException {
        try {
            StudyDiseaseDTO result =
                locator.getStudyDiseaseService().create(arg0);
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
            locator.getStudyDiseaseService().delete(arg0);
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public StudyDiseaseDTO get(Ii arg0) throws PAException {
        try {
            StudyDiseaseDTO result =
                locator.getStudyDiseaseService().get(arg0);
            return result;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public StudyDiseaseDTO update(StudyDiseaseDTO arg0) throws PAException {
        try {
            StudyDiseaseDTO result =
                locator.getStudyDiseaseService().update(arg0);
            return result;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }


}
