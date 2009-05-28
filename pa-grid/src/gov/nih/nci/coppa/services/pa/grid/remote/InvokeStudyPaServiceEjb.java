package gov.nih.nci.coppa.services.pa.grid.remote;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.services.grid.remote.InvokeCoppaServiceException;
import gov.nih.nci.pa.iso.dto.StudyDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyPaService;

import java.util.List;

/**
 * Generic Invoke service for Study services.
 * @param <DTO> pa DTO type
 */
public class InvokeStudyPaServiceEjb<DTO extends StudyDTO>
    extends InvokePaServiceEjb<DTO>
    implements StudyPaService<DTO> {

    /**
     * Const.
     * @param type correlation DTO class
     */
    public InvokeStudyPaServiceEjb(Class<DTO> type) {
        super(type);
    }

    /**
     * {@inheritDoc}
     */
    public void copy(Ii arg0, Ii arg1) throws PAException {
        try {
            getLocator().getStudyPaService(getType()).copy(arg0, arg1);
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }

    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<DTO> getByStudyProtocol(Ii arg0) throws PAException {
        try {
            return (List<DTO>) getLocator().getStudyPaService(getType()).getByStudyProtocol(arg0);
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<DTO> getCurrentByStudyProtocol(Ii arg0) throws PAException {
        try {
            return (List<DTO>) getLocator().getStudyPaService(getType()).getCurrentByStudyProtocol(arg0);
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

}
