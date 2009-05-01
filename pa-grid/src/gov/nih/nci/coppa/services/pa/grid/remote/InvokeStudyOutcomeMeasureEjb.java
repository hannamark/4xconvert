package gov.nih.nci.coppa.services.pa.grid.remote;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.iso.dto.StudyOutcomeMeasureDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyOutcomeMeasureServiceRemote;
import gov.nih.nci.coppa.services.grid.remote.InvokeCoppaServiceException;

import java.util.List;


/**
 * Wrapper class for invoking the StudyParticipantContact remote EJB.
 */
public class InvokeStudyOutcomeMeasureEjb implements StudyOutcomeMeasureServiceRemote {

    private final ServiceLocator locator = JNDIServiceLocator.getInstance();


    /**
     * {@inheritDoc}
     */
    public void copy(Ii arg0, Ii arg1) throws PAException {
        try {
            locator.getStudyOutcomeMeasureService().copy(arg0, arg1);
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }

    }

    /**
     * {@inheritDoc}
     */
    public List<StudyOutcomeMeasureDTO> getByStudyProtocol(Ii arg0)
            throws PAException {
        try {
            List<StudyOutcomeMeasureDTO> result =
                locator.getStudyOutcomeMeasureService().getByStudyProtocol(arg0);
            return result;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<StudyOutcomeMeasureDTO> getCurrentByStudyProtocol(Ii arg0)
            throws PAException {
        try {
            List<StudyOutcomeMeasureDTO> result =
                locator.getStudyOutcomeMeasureService().getCurrentByStudyProtocol(arg0);
            return result;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public StudyOutcomeMeasureDTO create(StudyOutcomeMeasureDTO arg0)
            throws PAException {
        try {
            StudyOutcomeMeasureDTO result =
                locator.getStudyOutcomeMeasureService().create(arg0);
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
            locator.getStudyOutcomeMeasureService().delete(arg0);
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }

    }

    /**
     * {@inheritDoc}
     */
    public StudyOutcomeMeasureDTO get(Ii arg0) throws PAException {

        try {
            StudyOutcomeMeasureDTO result =
                locator.getStudyOutcomeMeasureService().get(arg0);
            return result;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public StudyOutcomeMeasureDTO update(StudyOutcomeMeasureDTO arg0)
            throws PAException {
        try {
            StudyOutcomeMeasureDTO result =
                locator.getStudyOutcomeMeasureService().update(arg0);
            return result;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }



}
