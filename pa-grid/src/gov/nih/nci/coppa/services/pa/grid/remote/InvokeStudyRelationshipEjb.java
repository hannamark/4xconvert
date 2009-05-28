package gov.nih.nci.coppa.services.pa.grid.remote;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.coppa.services.grid.remote.InvokeCoppaServiceException;
import gov.nih.nci.pa.iso.dto.StudyRelationshipDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyRelationshipServiceRemote;

import java.util.List;

/**
 * Wrapper class for invoking the StudyRelationship remote EJB.
 */
public class InvokeStudyRelationshipEjb
    extends InvokePaServiceEjb<StudyRelationshipDTO>
    implements StudyRelationshipServiceRemote {

    private final ServiceLocator locator = JNDIServiceLocator.getInstance();

    /**
     * Const.
     */
    public InvokeStudyRelationshipEjb() {
        super(StudyRelationshipDTO.class);
    }

    /**
     * {@inheritDoc}
     */
    public List<StudyRelationshipDTO> search(StudyRelationshipDTO arg0, LimitOffset arg1) throws PAException,
            TooManyResultsException {
        try {
            return locator.getStudyRelationshipService().search(arg0, arg1);
        } catch (PAException e) {
            throw e;
        } catch (TooManyResultsException e) {
            throw e;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     * @deprecated
     */
    @Deprecated
    public List<StudyRelationshipDTO> search(StudyRelationshipDTO arg0)
            throws PAException {
        try {
            return locator.getStudyRelationshipService().search(arg0);
        } catch (PAException pae) {
            throw pae;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }



}
