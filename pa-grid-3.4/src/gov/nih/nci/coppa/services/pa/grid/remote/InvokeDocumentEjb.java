package gov.nih.nci.coppa.services.pa.grid.remote;

import gov.nih.nci.coppa.services.grid.remote.InvokeCoppaServiceException;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.StudyInboxDTO;
import gov.nih.nci.pa.service.DocumentServiceRemote;
import gov.nih.nci.pa.service.PAException;

import java.util.List;
import java.util.Map;

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
    
    /**
     * {@inheritDoc}
     */
    public List<DocumentDTO> getDocumentsAndAllTSRByStudyProtocol(
            Ii studyProtocolIi) throws PAException {
        try {
            return GridSecurityJNDIServiceLocator.newInstance()
                    .getDocumentService()
                    .getDocumentsAndAllTSRByStudyProtocol(studyProtocolIi);
        } catch (PAException pae) {
            throw pae;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }

    }
    
    /**
     * @param documentIi documentIi
     * @throws PAException PAException
     */
    public void forceDelete(Ii documentIi) throws PAException {    
        throw new PAException("Unavailable from the grid services");
    }

    /**
     * {@inheritDoc}
     */
    public void markAsOriginalSubmission(List<DocumentDTO> savedDocs)
            throws PAException {
        throw new PAException("Unavailable from the grid services");

    }

    /**
     * {@inheritDoc}
     */
    public void associateDocumentsWithStudyInbox(List<DocumentDTO> docs,
            StudyInboxDTO createdInbox) throws PAException {
        throw new PAException("Unavailable from the grid services");

    }
    
    /**
     * {@inheritDoc}
     */
    public List<DocumentDTO> getOriginalDocumentsByStudyProtocol(Ii identifier)
            throws PAException {
        throw new PAException("Unavailable from the grid services");
    }
    
    /**
     * {@inheritDoc}
     */
    public List<DocumentDTO> getOriginalDocumentsByStudyInbox(StudyInboxDTO dto)
            throws PAException {
        throw new PAException("Unavailable from the grid services");
    }
    
    /**
     * {@inheritDoc}
     * @throws PAException 
     */
    public List<DocumentDTO> getDeletedDocumentsByTrial(Ii studyProtocolIi) throws PAException {
        throw new PAException("Unavailable from the grid services");
    }

    /**
     * {@inheritDoc}
     * @throws PAException 
     */
    public void delete(Ii docID, St reasonToDelete) throws PAException {
        throw new PAException("Unavailable from the grid services");        
    }

    @Override
    public Map<Long, DocumentDTO> getDocumentByIDListAndType(
              List<Long> listOfTrialIDs, DocumentTypeCode type)
              throws PAException {
        // TODO Auto-generated method stub
        throw new PAException("Unavailable from the grid services");
    }

    /**
     * {@inheritDoc}
     * @throws PAException 
     */
    public List<DocumentDTO> getReportsDocumentsByStudyProtocol(
            Ii studyProtocolIi) throws PAException {
        throw new PAException("Unavailable from the grid services");
    }

    /**
     * {@inheritDoc}
     * @throws PAException 
     */
    public void updateForReview(DocumentDTO docDTO) throws PAException {
        throw new PAException("Unavailable from the grid services");        
    }
}
