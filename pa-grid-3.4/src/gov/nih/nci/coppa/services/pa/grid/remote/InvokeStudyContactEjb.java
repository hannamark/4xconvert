package gov.nih.nci.coppa.services.pa.grid.remote;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.coppa.services.grid.remote.InvokeCoppaServiceException;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyContactServiceRemote;

import java.util.List;

/**
 * Wrapper class for invoking the StudyContact remote EJB.
 * 
 * @author mshestopalov
 */
public class InvokeStudyContactEjb extends InvokeStudyPaServiceEjb<StudyContactDTO> implements
        StudyContactServiceRemote {

    /**
     * Const.
     */
    public InvokeStudyContactEjb() {
        super(StudyContactDTO.class);
    }

    /**
     * {@inheritDoc}
     */
    public List<StudyContactDTO> getByStudyProtocol(Ii studyProtocolIi, StudyContactDTO dto) throws PAException {
        try {
            List<StudyContactDTO> result = GridSecurityJNDIServiceLocator.newInstance().getStudyContactService()
                    .getByStudyProtocol(studyProtocolIi, dto);
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
    public List<StudyContactDTO> getByStudyProtocol(Ii studyProtocolIi, List<StudyContactDTO> dtos) throws PAException {
        try {
            List<StudyContactDTO> result = GridSecurityJNDIServiceLocator.newInstance().getStudyContactService()
                    .getByStudyProtocol(studyProtocolIi, dtos);
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
    public void cascadeRoleStatus(Ii ii, Cd roleStatusCode) throws PAException {
        try {
            GridSecurityJNDIServiceLocator.newInstance().getStudyContactService().cascadeRoleStatus(ii, roleStatusCode);
        } catch (PAException pae) {
            throw pae;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }
    /**
     * {@inheritDoc}
     */
    public List<StudyContactDTO> search(StudyContactDTO studyContact, LimitOffset pagingParams)
            throws PAException, TooManyResultsException {
        try {
            return GridSecurityJNDIServiceLocator.newInstance().getStudyContactService()
                        .search(studyContact, pagingParams);
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
    public StudyContactDTO getResponsiblePartyContact(Ii spId)
            throws PAException {       
        return null;
    }
    
    /**
     * {@inheritDoc}
     */
    public void removeResponsiblePartyContact(Ii studyProtocolIi)
            throws PAException {
        
    }
}
