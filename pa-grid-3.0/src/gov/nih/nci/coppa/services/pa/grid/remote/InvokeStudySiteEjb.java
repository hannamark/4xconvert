package gov.nih.nci.coppa.services.pa.grid.remote;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.coppa.services.grid.remote.InvokeCoppaServiceException;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudySiteServiceRemote;

import java.util.List;

/**
 * Wrapper class for invoking the StudyParticipant remote EJB.
 */
public class InvokeStudySiteEjb extends InvokeStudyPaServiceEjb<StudySiteDTO> implements
        StudySiteServiceRemote {

    /**
     * Const.
     */
    public InvokeStudySiteEjb() {
        super(StudySiteDTO.class);
    }

    private final ServiceLocator locator = JNDIServiceLocator.getInstance();

    /**
     * {@inheritDoc}
     */
    public List<StudySiteDTO> getByStudyProtocol(Ii studyProtocolIi, StudySiteDTO dto)
            throws PAException {
        try {
            List<StudySiteDTO> result =
                    locator.getStudySiteService().getByStudyProtocol(studyProtocolIi, dto);
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
    public List<StudySiteDTO> getByStudyProtocol(Ii ii, List<StudySiteDTO> dto) throws PAException {
        try {
            List<StudySiteDTO> result = locator.getStudySiteService().getByStudyProtocol(ii, dto);
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
            locator.getStudySiteService().cascadeRoleStatus(ii, roleStatusCode);
        } catch (PAException pae) {
            throw pae;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<StudySiteDTO> search(StudySiteDTO arg0, LimitOffset arg1)
            throws PAException, TooManyResultsException {
        // TODO Auto-generated method stub
        return null;
    }

}
