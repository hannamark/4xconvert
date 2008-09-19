package gov.nih.nci.pa.service;

import gov.nih.nci.pa.dto.PersonDTO;

import java.util.List;

import javax.ejb.Remote;

/**
 * Person Service for integrating with Persons .
 * @author Naveen Amiruddin
 * @since 07/22/2008
 
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 */

@Remote
public interface PAPersonServiceRemote  {
    
    /**
     * returns a list of PI name who have been associated with study protocol. 
     * @return list PersonDTO   
     * @throws PAException on error 
     */
   List<PersonDTO> getAllPrincipalInvestigators() throws PAException;
    

}
