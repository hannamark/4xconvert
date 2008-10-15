package gov.nih.nci.pa.service.util;

import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.FundingMechanism;
import gov.nih.nci.pa.domain.NIHinstitute;
import gov.nih.nci.pa.service.PAException;

import java.util.List;

import javax.ejb.Remote;

/**
 *  Bean implementation for providing access to look up tables. .
 * @author Naveen Amiruddin
 * @since 07/22/2008
 
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 */

@Remote
public interface LookUpTableServiceRemote {

    /**
     * 
     * @return FundingMechanism  FundingMechanism
     * @throws PAException PAException
     */
    List<FundingMechanism> getFundingMechanisms() throws PAException;
    
    /**
     * 
     * @return nihList  FundingMechanism
     * @throws PAException PAException
     */
    List<NIHinstitute> getNihInstitutes() throws PAException;    
    
    /**
     * 
     * @return country  Country
     * @throws PAException PAException
     */
    List<Country> getCountries() throws PAException;    
    
}
