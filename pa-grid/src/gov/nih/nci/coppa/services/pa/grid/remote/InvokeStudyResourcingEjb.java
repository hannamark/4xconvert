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
public class InvokeStudyResourcingEjb implements StudyResourcingServiceRemote {

    private final ServiceLocator locator = JNDIServiceLocator.getInstance();

    /**
     * {@inheritDoc}
     */
    public StudyResourcingDTO createStudyResourcing(StudyResourcingDTO studyResourcingDTO) throws PAException {
        try {
            StudyResourcingDTO result = locator.getStudyResourcingService().createStudyResourcing(studyResourcingDTO);
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
            Boolean result = locator.getStudyResourcingService().deleteStudyResourceByID(studyResourcingDTO);
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
            StudyResourcingDTO result = locator.getStudyResourcingService().getStudyResourceByID(ii);
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
            List<StudyResourcingDTO> result =
                    locator.getStudyResourcingService().getstudyResourceByStudyProtocol(studyProtocolIi);
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
            StudyResourcingDTO result =
                    locator.getStudyResourcingService().getsummary4ReportedResource(studyProtocolIi);
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
            StudyResourcingDTO result = locator.getStudyResourcingService().updateStudyResourcing(studyResourcingDTO);
            return result;
        } catch (PAException pae) {
            throw pae;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

}
