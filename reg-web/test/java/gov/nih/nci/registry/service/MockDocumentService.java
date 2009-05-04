/**
 * 
 */
package gov.nih.nci.registry.service;

import java.util.ArrayList;
import java.util.List;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.DocumentServiceRemote;
import gov.nih.nci.pa.service.PAException;

/**
 * @author Vrushali
 *
 */
public class MockDocumentService implements DocumentServiceRemote {

    static List<DocumentDTO> docList; 
    static {
        docList = new ArrayList<DocumentDTO>();
        DocumentDTO dto = new DocumentDTO();
        dto.setFileName(StConverter.convertToSt("fileName"));
        dto.setTypeCode(CdConverter.convertStringToCd("typeCode"));
        dto.setIdentifier(IiConverter.convertToIi("1"));
        dto.setStudyProtocolIi(IiConverter.convertToIi("1"));
        docList.add(dto);
    }
    
    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.DocumentService#create(gov.nih.nci.pa.iso.dto.DocumentDTO)
     */
    public DocumentDTO create(DocumentDTO docDTO) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.DocumentService#delete(gov.nih.nci.pa.iso.dto.DocumentDTO)
     */
    public Boolean delete(DocumentDTO docDTO) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.DocumentService#get(gov.nih.nci.coppa.iso.Ii)
     */
    public DocumentDTO get(Ii id) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.DocumentService#getDocumentsByStudyProtocol(gov.nih.nci.coppa.iso.Ii)
     */
    public List<DocumentDTO> getDocumentsByStudyProtocol(Ii studyProtocolIi)
            throws PAException {
        List<DocumentDTO> matchingDtos = new ArrayList<DocumentDTO>();
        for (DocumentDTO dto: docList){
            if(dto.getStudyProtocolIi().getExtension().equals(studyProtocolIi.getExtension())) {
                matchingDtos.add(dto);
            }
        }
        return matchingDtos;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.DocumentService#update(gov.nih.nci.pa.iso.dto.DocumentDTO)
     */
    public DocumentDTO update(DocumentDTO docDTO) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

}
