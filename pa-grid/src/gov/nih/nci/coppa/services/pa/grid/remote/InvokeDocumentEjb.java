package gov.nih.nci.coppa.services.pa.grid.remote;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.services.grid.remote.InvokeCoppaServiceException;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.service.DocumentServiceRemote;
import gov.nih.nci.pa.service.PAException;

import java.util.List;

/**
 * Wrapper class for invoking the Document remote EJB.
 */
public class InvokeDocumentEjb implements DocumentServiceRemote {


    private final ServiceLocator locator = JNDIServiceLocator.getInstance();

    /**
     * {@inheritDoc}
     */
    public DocumentDTO get(Ii ii) throws PAException {
        try {
            return locator.getDocumentService().get(ii);
        } catch (PAException pae) {
            throw pae;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<DocumentDTO> getDocumentsByStudyProtocol(Ii studyProtocolIi)
            throws PAException {
        try {
            return locator.getDocumentService().getDocumentsByStudyProtocol(studyProtocolIi);
        } catch (PAException pae) {
            throw pae;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void delete(DocumentDTO arg0) throws PAException {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    public DocumentDTO create(DocumentDTO arg0) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public DocumentDTO update(DocumentDTO arg0) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }
}
