package gov.nih.nci.coppa.services.pa.grid.remote;

import gov.nih.nci.coppa.services.grid.remote.InvokeCoppaServiceException;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyResourcingServiceRemote;

import java.util.List;

/**
 * Wrapper class for invoking the StudyResourcing remote EJB.
 */
public class InvokeStudyResourcingEjb extends InvokeStudyPaServiceEjb<StudyResourcingDTO> implements
        StudyResourcingServiceRemote {

    /**
     * Default constructor.
     */
    public InvokeStudyResourcingEjb() {
        super(StudyResourcingDTO.class);
    }

    /**
     * {@inheritDoc}
     */
    public StudyResourcingDTO createStudyResourcing(StudyResourcingDTO studyResourcingDTO) throws PAException {
        try {
            StudyResourcingDTO result = GridSecurityJNDIServiceLocator.newInstance().getStudyResourcingService()
                    .createStudyResourcing(studyResourcingDTO);
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
    public Boolean deleteStudyResourcingById(StudyResourcingDTO studyResourcingDTO) throws PAException {
        try {
            Boolean result = GridSecurityJNDIServiceLocator.newInstance().getStudyResourcingService()
                    .deleteStudyResourcingById(studyResourcingDTO);
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
    public StudyResourcingDTO getStudyResourcingById(Ii ii) throws PAException {
        try {
            StudyResourcingDTO result = GridSecurityJNDIServiceLocator.newInstance().getStudyResourcingService()
                    .getStudyResourcingById(ii);
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
    public List<StudyResourcingDTO> getStudyResourcingByStudyProtocol(Ii studyProtocolIi) throws PAException {
        try {
            List<StudyResourcingDTO> result = GridSecurityJNDIServiceLocator.newInstance().getStudyResourcingService()
                    .getStudyResourcingByStudyProtocol(studyProtocolIi);
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
    public StudyResourcingDTO getSummary4ReportedResourcing(Ii studyProtocolIi) throws PAException {
        try {
            StudyResourcingDTO result = GridSecurityJNDIServiceLocator.newInstance().getStudyResourcingService()
                    .getSummary4ReportedResourcing(studyProtocolIi);
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
    public StudyResourcingDTO updateStudyResourcing(StudyResourcingDTO studyResourcingDTO) throws PAException {
        try {
            StudyResourcingDTO result = GridSecurityJNDIServiceLocator.newInstance().getStudyResourcingService()
                    .updateStudyResourcing(studyResourcingDTO);
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
    public void validate(StudyResourcingDTO studyResourcingDTO) throws PAException {
    }

  
    // CHECKSTYLE:OFF
    /* (non-Javadoc)
     * @see 
     * gov.nih.nci.pa.service.StudyResourcingService#getActiveStudyResourcingByStudyProtocol(gov.nih.nci.iso21090.Ii)
     */
    public List<StudyResourcingDTO> getActiveStudyResourcingByStudyProtocol(
            Ii studyProtocolIi) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

   
    public void matchToExistentGrants(
            List<StudyResourcingDTO> studyResourcingDTOs, Ii identifier)
            throws PAException {
        // TODO Auto-generated method stub
        
    }

}
