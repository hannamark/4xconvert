package gov.nih.nci.coppa.services.pa.grid.remote;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.services.grid.remote.InvokeCoppaServiceException;
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
    public Boolean deleteStudyResourceByID(StudyResourcingDTO studyResourcingDTO) throws PAException {
        try {
            Boolean result = GridSecurityJNDIServiceLocator.newInstance().getStudyResourcingService()
                    .deleteStudyResourceByID(studyResourcingDTO);
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
    public StudyResourcingDTO getStudyResourceByID(Ii ii) throws PAException {
        try {
            StudyResourcingDTO result = GridSecurityJNDIServiceLocator.newInstance().getStudyResourcingService()
                    .getStudyResourceByID(ii);
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
    public List<StudyResourcingDTO> getstudyResourceByStudyProtocol(Ii studyProtocolIi) throws PAException {
        try {
            List<StudyResourcingDTO> result = GridSecurityJNDIServiceLocator.newInstance().getStudyResourcingService()
                    .getstudyResourceByStudyProtocol(studyProtocolIi);
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
    public StudyResourcingDTO getsummary4ReportedResource(Ii studyProtocolIi) throws PAException {
        try {
            StudyResourcingDTO result = GridSecurityJNDIServiceLocator.newInstance().getStudyResourcingService()
                    .getsummary4ReportedResource(studyProtocolIi);
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

}
