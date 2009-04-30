package gov.nih.nci.coppa.services.pa.grid.remote;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.services.grid.remote.InvokeCoppaServiceException;
import gov.nih.nci.pa.iso.dto.StudyRecruitmentStatusDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyRecruitmentStatusServiceRemote;

import java.util.List;

/**
 * Wrapper class for invoking the StudyProtocol remote EJB.
 */
public class InvokeStudyRecruitmentStatusEjb implements StudyRecruitmentStatusServiceRemote {

    private final ServiceLocator locator = JNDIServiceLocator.getInstance();

    /**
     * {@inheritDoc}
     */
    public void copy(Ii arg0, Ii arg1) throws PAException {
        try {
            locator.getStudyRecruitmentStatusService().copy(arg0, arg1);
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }

    }

    /**
     * {@inheritDoc}
     */
    public List<StudyRecruitmentStatusDTO> getByStudyProtocol(Ii arg0)
            throws PAException {
        try {
            List<StudyRecruitmentStatusDTO> result =
                locator.getStudyRecruitmentStatusService().getByStudyProtocol(arg0);
            return result;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<StudyRecruitmentStatusDTO> getCurrentByStudyProtocol(Ii arg0)
            throws PAException {
        try {
            List<StudyRecruitmentStatusDTO> result =
                locator.getStudyRecruitmentStatusService().getCurrentByStudyProtocol(arg0);
            return result;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }

    }

    /**
     * {@inheritDoc}
     */
    public StudyRecruitmentStatusDTO create(StudyRecruitmentStatusDTO arg0)
            throws PAException {
        try {
            StudyRecruitmentStatusDTO result =
                locator.getStudyRecruitmentStatusService().create(arg0);
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
            locator.getStudyRecruitmentStatusService().delete(arg0);
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public StudyRecruitmentStatusDTO get(Ii arg0) throws PAException {
        try {
            StudyRecruitmentStatusDTO result =
                locator.getStudyRecruitmentStatusService().get(arg0);
            return result;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public StudyRecruitmentStatusDTO update(StudyRecruitmentStatusDTO arg0)
            throws PAException {
        try {
            StudyRecruitmentStatusDTO result =
                locator.getStudyRecruitmentStatusService().update(arg0);
            return result;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }


}
