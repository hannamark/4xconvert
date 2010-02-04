/**
 * 
 */
package gov.nih.nci.registry.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.DocumentServiceLocal;
import gov.nih.nci.pa.service.DocumentServiceRemote;
import gov.nih.nci.pa.service.PAException;

/**
 * @author Vrushali
 *
 */
public class MockDocumentService  extends MockAbstractBaseIsoService<DocumentDTO> implements DocumentServiceLocal {

    static List<DocumentDTO> docList; 
    static {
        docList = new ArrayList<DocumentDTO>();
        DocumentDTO dto = new DocumentDTO();
        dto.setFileName(StConverter.convertToSt("fileName"));
        dto.setTypeCode(CdConverter.convertStringToCd("typeCode"));
        dto.setIdentifier(IiConverter.convertToIi("1"));
        dto.setStudyProtocolIdentifier(IiConverter.convertToIi("1"));
        docList.add(dto);
        dto = new DocumentDTO();
        dto.setFileName(StConverter.convertToSt("fileName"));
        dto.setTypeCode(CdConverter.convertStringToCd("IRB Approval Document"));
        dto.setIdentifier(IiConverter.convertToIi("2"));
        dto.setStudyProtocolIdentifier(IiConverter.convertToIi("1"));
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
    public void delete(DocumentDTO docDTO) throws PAException {
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.DocumentService#get(gov.nih.nci.iso21090.Ii)
     */
    public DocumentDTO get(Ii id) throws PAException {
        DocumentDTO  matchingDto = new DocumentDTO();
        for (DocumentDTO dto: docList){
            if(dto.getIdentifier().getExtension().equals(id.getExtension())) {
                matchingDto = dto;
            }
        }
        return matchingDto;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.DocumentService#getDocumentsByStudyProtocol(gov.nih.nci.iso21090.Ii)
     */
    public List<DocumentDTO> getDocumentsByStudyProtocol(Ii studyProtocolIi)
            throws PAException {
        List<DocumentDTO> matchingDtos = new ArrayList<DocumentDTO>();
        for (DocumentDTO dto: docList){
            if(dto.getStudyProtocolIdentifier().getExtension().equals(studyProtocolIi.getExtension())) {
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

	/* (non-Javadoc)
	 * @see gov.nih.nci.pa.service.StudyPaService#copy(gov.nih.nci.iso21090.Ii, gov.nih.nci.iso21090.Ii)
	 */
	public Map<Ii, Ii> copy(Ii fromStudyProtocolIi, Ii toStudyProtocolIi)
			throws PAException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<DocumentDTO> getByStudyProtocol(Ii ii) throws PAException {
		// TODO Auto-generated method stub
		return null;
	}

	public void delete(Ii ii) throws PAException {
		// TODO Auto-generated method stub
		
	}

   
}
