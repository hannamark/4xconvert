package gov.nih.nci.coppa.services.pa.grid.remote;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.services.grid.remote.InvokeCoppaServiceException;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceRemote;

import java.util.List;

/**
 * Wrapper class for invoking the StudySiteAccrualStatusService remote EJB.
 *
 * @author Hugh Reinhart
 */
public class InvokeStudySiteAccrualStatusEjb implements StudySiteAccrualStatusServiceRemote {

    private final ServiceLocator locator = JNDIServiceLocator.getInstance();

    /**
     * {@inheritDoc}
     */
    public List<StudySiteAccrualStatusDTO> getCurrentStudySiteAccrualStatusByStudyParticipation(Ii studyParticipationIi)
            throws PAException {
        try {
            StudySiteAccrualStatusServiceRemote service = locator.getStudySiteAccrualStatusService();
            List<StudySiteAccrualStatusDTO> result =
                    service.getCurrentStudySiteAccrualStatusByStudyParticipation(studyParticipationIi);
            return result;
        } catch (PAException pae) {
            throw pae;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public StudySiteAccrualStatusDTO getStudySiteAccrualStatus(Ii ii) throws PAException {
        try {
            StudySiteAccrualStatusServiceRemote service = locator.getStudySiteAccrualStatusService();
            StudySiteAccrualStatusDTO result = service.getStudySiteAccrualStatus(ii);
            return result;
        } catch (PAException pae) {
            throw pae;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<StudySiteAccrualStatusDTO> getStudySiteAccrualStatusByStudyParticipation(Ii studyParticipationIi)
            throws PAException {
        try {
            StudySiteAccrualStatusServiceRemote service = locator.getStudySiteAccrualStatusService();
            List<StudySiteAccrualStatusDTO> result =
                    service.getStudySiteAccrualStatusByStudyParticipation(studyParticipationIi);
            return result;
        } catch (PAException pae) {
            throw pae;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public StudySiteAccrualStatusDTO createStudySiteAccrualStatus(StudySiteAccrualStatusDTO dto) throws PAException {
        throw new PAException("Not yet implemented");
    }

    /**
     * {@inheritDoc}
     */
    public StudySiteAccrualStatusDTO updateStudySiteAccrualStatus(StudySiteAccrualStatusDTO dto) throws PAException {
        throw new PAException("Not yet implemented");
    }

}
