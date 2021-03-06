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
public class InvokeStudyRelationshipEjb extends InvokePaServiceEjb<StudyRelationshipDTO> implements
        StudyRelationshipServiceRemote {

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
            return GridSecurityJNDIServiceLocator.newInstance().getStudyRelationshipService().search(arg0, arg1);
        } catch (PAException e) {
            throw e;
        } catch (TooManyResultsException e) {
            throw e;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

}
