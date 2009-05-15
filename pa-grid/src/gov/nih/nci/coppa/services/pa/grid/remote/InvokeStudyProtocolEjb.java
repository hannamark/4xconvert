package gov.nih.nci.coppa.services.pa.grid.remote;

import java.util.List;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.coppa.services.grid.remote.InvokeCoppaServiceException;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.ObservationalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyProtocolServiceRemote;

/**
 * Wrapper class for invoking the StudyProtocol remote EJB.
 */
public class InvokeStudyProtocolEjb implements StudyProtocolServiceRemote {

    private final ServiceLocator locator = JNDIServiceLocator.getInstance();

    /**
     * {@inheritDoc}
     */
    public Ii createInterventionalStudyProtocol(InterventionalStudyProtocolDTO arg0) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public InterventionalStudyProtocolDTO getInterventionalStudyProtocol(Ii arg0) throws PAException {
        try {
            InterventionalStudyProtocolDTO result = locator.getStudyProtocolService().getInterventionalStudyProtocol(
                    arg0);
            return result;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public StudyProtocolDTO getStudyProtocol(Ii arg0) throws PAException {
        try {
            StudyProtocolDTO result = locator.getStudyProtocolService().getStudyProtocol(arg0);
            return result;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public InterventionalStudyProtocolDTO updateInterventionalStudyProtocol(InterventionalStudyProtocolDTO arg0)
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public StudyProtocolDTO updateStudyProtocol(StudyProtocolDTO arg0) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * ObservationalStudyProtocol methods are not implemented!
     * @param arg0 ignored
     * @return always throws exception!
     * @exception PAException always thrown
     */
    public Ii createObservationalStudyProtocol(ObservationalStudyProtocolDTO arg0) throws PAException {
        throw new PAException("ObservationStudyProtocal methods are not implemented!");
    }

    /**
     * ObservationalStudyProtocol methods are not implemented!
     * @param arg0 ignored
     * @return always throws exception!
     * @exception PAException always thrown
     */
    public ObservationalStudyProtocolDTO getObservationalStudyProtocol(Ii arg0) throws PAException {
        throw new PAException("ObservationStudyProtocal methods are not implemented!");
    }

    /**
     * ObservationalStudyProtocol methods are not implemented!
     * @param arg0 ignored
     * @return always throws exception!
     * @exception PAException always thrown
     */
    public ObservationalStudyProtocolDTO updateObservationalStudyProtocol(ObservationalStudyProtocolDTO arg0)
            throws PAException {
        throw new PAException("ObservationStudyProtocal methods are not implemented!");
    }

    /**
     * {@inheritDoc}
     */
    public List<StudyProtocolDTO> search(StudyProtocolDTO arg0, LimitOffset arg1) throws PAException,
            TooManyResultsException {
        try {
            return locator.getStudyProtocolService().search(arg0, arg1);
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
     */
    public void deleteStudyProtocol(Ii arg0) throws PAException {
        return;
    }
    
    /**
     * {@inheritDoc}
     * @deprecated -- DO NOT USE
     */
    @Deprecated
    public List<StudyProtocolDTO> search(StudyProtocolDTO arg0) throws PAException {
        try {
            return locator.getStudyProtocolService().search(arg0);
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }
}
