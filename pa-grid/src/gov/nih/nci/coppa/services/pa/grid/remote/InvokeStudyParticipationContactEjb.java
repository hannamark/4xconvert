package gov.nih.nci.coppa.services.pa.grid.remote;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.services.grid.remote.InvokeCoppaServiceException;
import gov.nih.nci.pa.iso.dto.StudyParticipationContactDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyParticipationContactServiceRemote;

import java.util.List;

/**
 * Wrapper class for invoking the StudyParticipantContact remote EJB.
 */
public class InvokeStudyParticipationContactEjb extends InvokeStudyPaServiceEjb<StudyParticipationContactDTO> implements
        StudyParticipationContactServiceRemote {

    private final ServiceLocator locator = JNDIServiceLocator.getInstance();

    /**
     * Const.
     */
    public InvokeStudyParticipationContactEjb() {
        super(StudyParticipationContactDTO.class);
    }

    /**
     * {@inheritDoc}
     */
    public List<StudyParticipationContactDTO> getByStudyParticipation(Ii studyParticipationIi) throws PAException {
        try {
            List<StudyParticipationContactDTO> result =
                    locator.getStudyParticipationContactService().getByStudyParticipation(studyParticipationIi);
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
    public List<StudyParticipationContactDTO> getByStudyProtocol(Ii studyProtocolIi, StudyParticipationContactDTO dto)
            throws PAException {
        try {
            List<StudyParticipationContactDTO> result =
                    locator.getStudyParticipationContactService().getByStudyProtocol(studyProtocolIi, dto);
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
    public List<StudyParticipationContactDTO> getByStudyProtocol(Ii studyProtocolIi,
            List<StudyParticipationContactDTO> dtos) throws PAException {
        try {
            List<StudyParticipationContactDTO> result =
                    locator.getStudyParticipationContactService().getByStudyProtocol(studyProtocolIi, dtos);
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
