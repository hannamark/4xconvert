package gov.nih.nci.coppa.services.pa.grid.remote;

import gov.nih.nci.coppa.iso.Bl;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.services.grid.remote.InvokeCoppaServiceException;
import gov.nih.nci.pa.iso.dto.StudyOnholdDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyOnholdServiceRemote;

/**
 * Wrapper class for invoking the StudyOnhold remote EJB.
 */
public class InvokeStudyOnholdEjb extends InvokeStudyPaServiceEjb<StudyOnholdDTO> implements StudyOnholdServiceRemote {


    /**
     * Const.
     */
    public InvokeStudyOnholdEjb() {
        super(StudyOnholdDTO.class);
    }

    /**
     * {@inheritDoc}
     */
    public Bl isOnhold(Ii ii) throws PAException {
        try {
            Bl result = GridSecurityJNDIServiceLocator.newInstance().getStudyOnholdService().isOnhold(ii);
            return result;
        } catch (PAException pae) {
            throw pae;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }
}
