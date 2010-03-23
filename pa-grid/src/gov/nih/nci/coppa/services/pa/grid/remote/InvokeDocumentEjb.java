package gov.nih.nci.coppa.services.pa.grid.remote;

import gov.nih.nci.coppa.services.grid.remote.InvokeCoppaServiceException;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.service.DocumentServiceRemote;
import gov.nih.nci.pa.service.PAException;

import java.util.List;

/**
 * Wrapper class for invoking the Document remote EJB.
 */
public class InvokeDocumentEjb extends InvokeStudyPaServiceEjb<DocumentDTO> implements DocumentServiceRemote {

    /**
     * @param type
     */
    public InvokeDocumentEjb() {
        super(DocumentDTO.class);
    }

    /**
     * {@inheritDoc}
     */
    public List<DocumentDTO> getDocumentsByStudyProtocol(Ii studyProtocolIi) throws PAException {
        try {
            return GridSecurityJNDIServiceLocator.newInstance().getDocumentService().getDocumentsByStudyProtocol(
                    studyProtocolIi);
        } catch (PAException pae) {
            throw pae;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

}
