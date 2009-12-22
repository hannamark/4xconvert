/**
 * 
 */
package gov.nih.nci.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.service.DocumentServiceLocal;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.iso.util.EdConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;

import java.util.List;
import java.util.Map;

/**
 * @author asharma
 *
 */
public class MockDocumentService implements DocumentServiceLocal{

	
	public List<DocumentDTO> getDocumentsByStudyProtocol(Ii studyProtocolIi)
			throws PAException {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Map<Ii, Ii> copy(Ii fromStudyProtocolIi, Ii toStudyProtocolIi)
			throws PAException {
		// TODO Auto-generated method stub
		return null;
	}

	
	public List<DocumentDTO> getByStudyProtocol(Ii ii) throws PAException {
		// TODO Auto-generated method stub
		return null;
	}

	
	public DocumentDTO create(DocumentDTO dto) throws PAException {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void delete(Ii ii) throws PAException {
		// TODO Auto-generated method stub
		
	}

	
	public DocumentDTO get(Ii ii) throws PAException {
		DocumentDTO docDTO = new DocumentDTO();
		docDTO.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.OTHER));
        docDTO.setFileName(StConverter.convertToSt("Protocol_Document.doc"));
        docDTO.setText(EdConverter.convertToEd("test".getBytes()));
        docDTO.setStudyProtocolIdentifier(IiConverter.convertToIi("1"));
		return docDTO;
	}

	
	public DocumentDTO update(DocumentDTO dto) throws PAException {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void validate(DocumentDTO dto) throws PAException {
		// TODO Auto-generated method stub
		
	}

}
