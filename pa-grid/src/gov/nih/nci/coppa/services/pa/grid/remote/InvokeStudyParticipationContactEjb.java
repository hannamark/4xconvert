package gov.nih.nci.coppa.services.pa.grid.remote;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.services.grid.remote.InvokeCoppaServiceException;
import gov.nih.nci.pa.iso.dto.StudyParticipationContactDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyParticipationContactServiceRemote;

import java.util.List;


/**
 * Wrapper class for invoking the StudyParticipantContact remote EJB.
 */
public class InvokeStudyParticipationContactEjb
    extends InvokeStudyPaServiceEjb<StudyParticipationContactDTO>
    implements StudyParticipationContactServiceRemote {

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
    public List<StudyParticipationContactDTO> getByStudyParticipation(Ii arg0)
            throws PAException {
        try {
            List<StudyParticipationContactDTO> result =
                locator.getStudyParticipationContactService().getByStudyParticipation(arg0);
            return result;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<StudyParticipationContactDTO> getByStudyProtocol(Ii arg0,
            StudyParticipationContactDTO arg1) throws PAException {
        try {
            List<StudyParticipationContactDTO> result =
                locator.getStudyParticipationContactService().getByStudyProtocol(arg0, arg1);
            return result;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<StudyParticipationContactDTO> getByStudyProtocol(Ii arg0,
            List<StudyParticipationContactDTO> arg1) throws PAException {
        try {
            List<StudyParticipationContactDTO> result =
                locator.getStudyParticipationContactService().getByStudyProtocol(arg0, arg1);
            return result;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }
}
