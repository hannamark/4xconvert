package gov.nih.nci.coppa.services.pa.grid.remote;

import gov.nih.nci.coppa.services.grid.remote.InvokeCoppaServiceException;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.iso.dto.StudyDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyPaService;

import java.util.List;
import java.util.Map;

/**
 * Generic Invoke service for Study services.
 *
 * @param <DTO> pa DTO type
 */
public class InvokeStudyPaServiceEjb<DTO extends StudyDTO> extends InvokePaServiceEjb<DTO> implements
        StudyPaService<DTO> {

    /**
     * Const.
     *
     * @param type correlation DTO class
     */
    public InvokeStudyPaServiceEjb(Class<DTO> type) {
        super(type);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public Map<Ii , Ii> copy(Ii fromStudyProtocolIi, Ii toStudyProtocolIi) throws PAException {
        try {
            return getLocator().getStudyPaService(getType()).copy(fromStudyProtocolIi, toStudyProtocolIi);
        } catch (PAException pae) {
            throw pae;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }

    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<DTO> getByStudyProtocol(Ii studyProtocolIi) throws PAException {
        try {
            return getLocator().getStudyPaService(getType()).getByStudyProtocol(studyProtocolIi);
        } catch (PAException pae) {
            throw pae;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

}
