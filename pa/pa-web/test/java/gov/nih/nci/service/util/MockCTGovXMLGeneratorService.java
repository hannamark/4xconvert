/**
 * 
 */
package gov.nih.nci.service.util;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.CTGovXmlGeneratorServiceRemote;

/**
 * @author Vrushali
 *
 */
public class MockCTGovXMLGeneratorService implements CTGovXmlGeneratorServiceRemote {

	
	public String generateCTGovXml(Ii studyProtocolIi) throws PAException {
		 String xmlData = "test xml";
		return xmlData;
	}
	
	

}
