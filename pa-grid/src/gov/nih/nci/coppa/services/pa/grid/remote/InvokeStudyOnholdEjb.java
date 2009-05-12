package gov.nih.nci.coppa.services.pa.grid.remote;

import gov.nih.nci.coppa.iso.Bl;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.services.grid.remote.InvokeCoppaServiceException;
import gov.nih.nci.pa.iso.dto.StudyOnholdDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyOnholdServiceRemote;

import java.util.List;

/**
 * Wrapper class for invoking the StudyOnhold remote EJB.
 */
public class InvokeStudyOnholdEjb implements StudyOnholdServiceRemote {

    private final ServiceLocator locator = JNDIServiceLocator.getInstance();

    /**
     * {@inheritDoc}
     */
    public StudyOnholdDTO get(Ii ii) throws PAException {
        try {
            StudyOnholdDTO result = locator.getStudyOnholdService().get(ii);
            return result;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<StudyOnholdDTO> getByStudyProtocol(Ii ii) throws PAException {
        try {
            List<StudyOnholdDTO> result = locator.getStudyOnholdService().getByStudyProtocol(ii);
            return result;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<StudyOnholdDTO> getCurrentByStudyProtocol(Ii ii)
            throws PAException {
        try {
            List<StudyOnholdDTO> result = locator.getStudyOnholdService().getCurrentByStudyProtocol(ii);
            return result;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public Bl isOnhold(Ii ii) throws PAException {
        try {
            Bl result = locator.getStudyOnholdService().isOnhold(ii);
            return result;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void copy(Ii ii0, Ii ii1) throws PAException {
        try {
            locator.getStudyOnholdService().copy(ii0, ii1);
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public StudyOnholdDTO create(StudyOnholdDTO studyOnholdDTO) throws PAException {
        try {
            StudyOnholdDTO result = locator.getStudyOnholdService().create(studyOnholdDTO);
            return result;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void delete(Ii ii) throws PAException {
        try {
            locator.getStudyOnholdService().delete(ii);
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
        
    }

    /**
     * {@inheritDoc}
     */
    public StudyOnholdDTO update(StudyOnholdDTO studyOnholdDTO) throws PAException {
        try {
            StudyOnholdDTO result = locator.getStudyOnholdService().update(studyOnholdDTO);
            return result;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }
}
