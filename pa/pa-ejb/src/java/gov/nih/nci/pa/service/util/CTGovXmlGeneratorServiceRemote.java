package gov.nih.nci.pa.service.util;

import javax.ejb.Remote;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.service.PAException;

/** 
* service for generating ct.gov.xml.
* 
* @author Naveen Amiruddin
* @since 06/26/2008
* copyright NCI 2007.  All rights reserved.
* This code may not be used without the express written permission of the
* copyright holder, NCI.
*/
@Remote 
public interface CTGovXmlGeneratorServiceRemote {
    
    /**
     * @param studyProtocolIi Ii of studyprotocol  
     * @return String xml string
     * @throws PAException on error
     */
    String generateCTGovXml(Ii studyProtocolIi) throws PAException;

}
