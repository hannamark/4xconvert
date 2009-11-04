package gov.nih.nci.coppa.services.pa.grid.remote;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.coppa.services.grid.remote.InvokeCoppaServiceException;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.ObservationalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyProtocolServiceRemote;

import java.util.List;

/**
 * Wrapper class for invoking the StudyProtocol remote EJB.
 */
public class InvokeStudyProtocolEjb implements StudyProtocolServiceRemote {

    /**
     * {@inheritDoc}
     */
    public InterventionalStudyProtocolDTO getInterventionalStudyProtocol(Ii ii) throws PAException {
        try {
            InterventionalStudyProtocolDTO result = GridSecurityJNDIServiceLocator.newInstance()
                    .getStudyProtocolService().getInterventionalStudyProtocol(ii);
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
    public StudyProtocolDTO getStudyProtocol(Ii ii) throws PAException {
        try {
            StudyProtocolDTO result = GridSecurityJNDIServiceLocator.newInstance().getStudyProtocolService()
                    .getStudyProtocol(ii);
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
    public List<StudyProtocolDTO> search(StudyProtocolDTO dto, LimitOffset pagingParams) throws PAException,
            TooManyResultsException {
        try {
            return GridSecurityJNDIServiceLocator.newInstance().getStudyProtocolService().search(dto, pagingParams);
        } catch (PAException pae) {
            throw pae;
        } catch (TooManyResultsException tmre) {
            throw tmre;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

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
    public void deleteStudyProtocol(Ii arg0) throws PAException {
        return;
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
     * 
     * @param arg0 ignored
     * @return always throws exception!
     * @exception PAException always thrown
     */
    public Ii createObservationalStudyProtocol(ObservationalStudyProtocolDTO arg0) throws PAException {
        throw new PAException("ObservationStudyProtocal methods are not implemented!");
    }

    /**
     * ObservationalStudyProtocol methods are not implemented!
     * 
     * @param arg0 ignored
     * @return always throws exception!
     * @exception PAException always thrown
     */
    public ObservationalStudyProtocolDTO getObservationalStudyProtocol(Ii arg0) throws PAException {
        throw new PAException("ObservationStudyProtocal methods are not implemented!");
    }

    /**
     * ObservationalStudyProtocol methods are not implemented!
     * 
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
    public void validate(StudyProtocolDTO studyProtocolDto) throws PAException {
        try {
            GridSecurityJNDIServiceLocator.newInstance().getStudyProtocolService().validate(studyProtocolDto);
        } catch (PAException pae) {
            throw pae;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }
}
