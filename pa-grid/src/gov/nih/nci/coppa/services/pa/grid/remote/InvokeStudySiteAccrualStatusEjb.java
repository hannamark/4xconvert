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
public class InvokeStudySiteAccrualStatusEjb implements
        StudySiteAccrualStatusServiceRemote {

    private final ServiceLocator locator = JNDIServiceLocator.getInstance();

    /**
     * {@inheritDoc}
     */
    public List<StudySiteAccrualStatusDTO> getCurrentStudySiteAccrualStatusByStudyParticipation(
            Ii arg0) throws PAException {
        try {
            List<StudySiteAccrualStatusDTO> result = locator.getStudySiteAccrualStatusService()
                    .getCurrentStudySiteAccrualStatusByStudyParticipation(arg0);
            return result;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public StudySiteAccrualStatusDTO getStudySiteAccrualStatus(Ii arg0)
            throws PAException {
        try {
            StudySiteAccrualStatusDTO result = locator.getStudySiteAccrualStatusService()
                    .getStudySiteAccrualStatus(arg0);
            return result;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<StudySiteAccrualStatusDTO> getStudySiteAccrualStatusByStudyParticipation(
            Ii arg0) throws PAException {
        try {
            List<StudySiteAccrualStatusDTO> result = locator.getStudySiteAccrualStatusService()
                    .getStudySiteAccrualStatusByStudyParticipation(arg0);
            return result;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public StudySiteAccrualStatusDTO createStudySiteAccrualStatus(
            StudySiteAccrualStatusDTO arg0) throws PAException {
        throw new PAException("Not yet implemented");
    }

    /**
     * {@inheritDoc}
     */
    public StudySiteAccrualStatusDTO updateStudySiteAccrualStatus(
            StudySiteAccrualStatusDTO arg0) throws PAException {
        throw new PAException("Not yet implemented");
    }

}
