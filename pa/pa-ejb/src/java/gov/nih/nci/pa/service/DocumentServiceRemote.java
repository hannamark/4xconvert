package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.iso.dto.DocumentDTO;

import java.util.List;

import javax.ejb.Remote;

/**
 * @author Kalpana Guthikonda
 * @since 09/30/2008
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Remote
public interface DocumentServiceRemote {

    /**
     * @param studyProtocolIi Ii 
     * @return DocumentDTO
     * @throws PAException PAException
     */
    List<DocumentDTO> getDocumentsByStudyProtocol(Ii studyProtocolIi) throws PAException;
    
    /**
     * @param docDTO DocumentDTO 
     * @return DocumentDTO
     * @throws PAException PAException
     */
    DocumentDTO createTrialDocument(DocumentDTO docDTO) throws PAException;
    
    /**
     * @param id Ii 
     * @return DocumentDTO
     * @throws PAException PAException
     */
    DocumentDTO getTrialDocumentById(Ii id) throws PAException;

    /**
     * @param docDTO DocumentDTO 
     * @return DocumentDTO
     * @throws PAException PAException
     */
    DocumentDTO updateTrialDocument(DocumentDTO docDTO) throws PAException;

}
