package gov.nih.nci.coppa.services.pa.grid.remote;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.services.grid.remote.InvokeCoppaServiceException;
import gov.nih.nci.pa.iso.dto.StudyParticipationDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyParticipationServiceRemote;

import java.util.List;


/**
 * Wrapper class for invoking the StudyParticipant remote EJB.
 */
public class InvokeStudyParticipationEjb
    extends InvokeStudyPaServiceEjb<StudyParticipationDTO>
    implements StudyParticipationServiceRemote {

    /**
     * Const.
     */
    public InvokeStudyParticipationEjb() {
        super(StudyParticipationDTO.class);
    }

    private final ServiceLocator locator = JNDIServiceLocator.getInstance();

    /**
     * {@inheritDoc}
     */
    public List<StudyParticipationDTO> getByStudyProtocol(Ii arg0,
            StudyParticipationDTO arg1) throws PAException {
        try {
            List<StudyParticipationDTO> result =
                locator.getStudyParticipationService().getByStudyProtocol(arg0, arg1);
            return result;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<StudyParticipationDTO> getByStudyProtocol(Ii arg0,
            List<StudyParticipationDTO> arg1) throws PAException {
        try {
            List<StudyParticipationDTO> result =
                locator.getStudyParticipationService().getByStudyProtocol(arg0, arg1);
            return result;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }



}
