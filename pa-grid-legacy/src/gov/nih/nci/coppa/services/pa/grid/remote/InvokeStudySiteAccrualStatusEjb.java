package gov.nih.nci.coppa.services.pa.grid.remote;

import gov.nih.nci.coppa.services.grid.remote.InvokeCoppaServiceException;
import gov.nih.nci.iso21090.Ii;
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
    public StudySiteAccrualStatusDTO getCurrentStudySiteAccrualStatusByStudySite(Ii studySiteIi)
            throws PAException {
        try {
            StudySiteAccrualStatusServiceRemote service = locator.getStudySiteAccrualStatusService();
            return service.getCurrentStudySiteAccrualStatusByStudySite(studySiteIi);
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
    public List<StudySiteAccrualStatusDTO> getStudySiteAccrualStatusByStudySite(Ii studySiteIi)
            throws PAException {
        try {
            StudySiteAccrualStatusServiceRemote service = locator.getStudySiteAccrualStatusService();
            List<StudySiteAccrualStatusDTO> result =
                    service.getStudySiteAccrualStatusByStudySite(studySiteIi);
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
