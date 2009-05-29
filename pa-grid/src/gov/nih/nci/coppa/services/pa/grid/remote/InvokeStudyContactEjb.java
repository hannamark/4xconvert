package gov.nih.nci.coppa.services.pa.grid.remote;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.services.grid.remote.InvokeCoppaServiceException;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyContactServiceRemote;

import java.util.List;

/**
 * Wrapper class for invoking the StudyContact remote EJB.
 * @author mshestopalov
 */
public class InvokeStudyContactEjb
    extends InvokeStudyPaServiceEjb<StudyContactDTO>
    implements StudyContactServiceRemote {

    private final ServiceLocator locator = JNDIServiceLocator.getInstance();

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
            List<StudyContactDTO> result =
                locator.getStudyContactService().getByStudyProtocol(studyProtocolIi, dto);
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
            List<StudyContactDTO> result =
                locator.getStudyContactService().getByStudyProtocol(studyProtocolIi, dtos);
            return result;
        } catch (PAException pae) {
            throw pae;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

}
