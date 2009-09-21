package gov.nih.nci.coppa.services.pa.grid.remote;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.services.grid.remote.InvokeCoppaServiceException;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudySiteContactServiceRemote;

import java.util.List;

/**
 * Wrapper class for invoking the StudyParticipantContact remote EJB.
 */
public class InvokeStudySiteContactEjb extends InvokeStudyPaServiceEjb<StudySiteContactDTO> implements
        StudySiteContactServiceRemote {

    private final ServiceLocator locator = JNDIServiceLocator.getInstance();

    /**
     * Const.
     */
    public InvokeStudySiteContactEjb() {
        super(StudySiteContactDTO.class);
    }

    /**
     * {@inheritDoc}
     */
    public List<StudySiteContactDTO> getByStudySite(Ii studySiteIi) throws PAException {
        try {
            List<StudySiteContactDTO> result =
                    locator.getStudySiteContactService().getByStudySite(studySiteIi);
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
    public List<StudySiteContactDTO> getByStudyProtocol(Ii studyProtocolIi, StudySiteContactDTO dto)
            throws PAException {
        try {
            List<StudySiteContactDTO> result =
                    locator.getStudySiteContactService().getByStudyProtocol(studyProtocolIi, dto);
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
    public List<StudySiteContactDTO> getByStudyProtocol(Ii studyProtocolIi,
            List<StudySiteContactDTO> dtos) throws PAException {
        try {
            List<StudySiteContactDTO> result =
                    locator.getStudySiteContactService().getByStudyProtocol(studyProtocolIi, dtos);
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
    public void cascadeRoleStatus(Ii ii , Cd roleStatusCode) throws PAException {
        try {
            locator.getStudyContactService().cascadeRoleStatus(ii, roleStatusCode);
        } catch (PAException pae) {
            throw pae;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }
}
