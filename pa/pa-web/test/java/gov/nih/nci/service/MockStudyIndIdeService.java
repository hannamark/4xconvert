/**
 * 
 */
package gov.nih.nci.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.enums.ExpandedAccessStatusCode;
import gov.nih.nci.pa.enums.GrantorCode;
import gov.nih.nci.pa.enums.HolderTypeCode;
import gov.nih.nci.pa.enums.IndldeTypeCode;
import gov.nih.nci.pa.enums.NihInstituteCode;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyIndldeServiceLocal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Vrushali
 *
 */
public class MockStudyIndIdeService extends MockAbstractBaseIsoService <StudyIndldeDTO> implements StudyIndldeServiceLocal {

	public Map<Ii, Ii> copy(Ii fromStudyProtocolIi, Ii toStudyProtocolIi)
			throws PAException {
		// TODO Auto-generated method stub
		return null;
	}

	
	public List<StudyIndldeDTO> getByStudyProtocol(Ii ii) throws PAException {
		// TODO Auto-generated method stub
		return new ArrayList<StudyIndldeDTO>();
	}


	public StudyIndldeDTO get(Ii ii) throws PAException {
		StudyIndldeDTO dto = null;
		if(ii.getExtension().equals("1")) {
		    dto = new StudyIndldeDTO();
	        dto.setIdentifier(IiConverter.convertToIi(1L));
	        dto.setExpandedAccessStatusCode(CdConverter.convertToCd(ExpandedAccessStatusCode.AVAILABLE));
	        dto.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(1L));
	        dto.setExpandedAccessIndicator(BlConverter.convertToBl(Boolean.TRUE));
	        dto.setHolderTypeCode(CdConverter.convertToCd(HolderTypeCode.NIH));
	        dto.setNihInstHolderCode(CdConverter.convertToCd(NihInstituteCode.NCRR));
	        dto.setIndldeTypeCode(CdConverter.convertToCd(IndldeTypeCode.IND));
	        dto.setGrantorCode(CdConverter.convertToCd(GrantorCode.CDER));
	        dto.setIndldeNumber(StConverter.convertToSt("123456"));
		}   
		return dto;
	}

   
}
