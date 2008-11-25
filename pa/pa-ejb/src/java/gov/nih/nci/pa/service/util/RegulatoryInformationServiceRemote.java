package gov.nih.nci.pa.service.util;

import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.RegulatoryAuthority;
import gov.nih.nci.pa.service.PAException;

import java.util.List;

import javax.ejb.Remote;

/**
 * 
 * @author Harsha
 * @since 08/05/2008
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Remote
public interface RegulatoryInformationServiceRemote  {
    
    /**
     * Method to get a list of distinct country names only.
     * 
     * The DTOS here are not ISO
     * 
     * @return List of distinct country names
     * @throws PAException on error
     */    
     List getDistinctCountryNames() throws PAException;
     
     
     /**
      * Method to get a list of Regulatory Authority organizations for a given
      * regulatory authority id.
      * 
      * @param regAuthID as Long
      * @return List of RegulatoryAuthOrgDTO(s)
      * @throws PAException on error
     */     
     List getRegulatoryAuthorityNameId(Long regAuthID) throws PAException;

     /**
      * Method to get the regulatory authority name.
      * 
      * @param regAuthID
      *            for the Regulatory Authority table
      * @return String containing the regulatory authority
      * @throws PAException
      *             on error
      */
     List<Long> getRegulatoryAuthorityInfo(Long regAuthID) throws PAException;
     
     /**
      * 
      * @param id to be searched
      * @param className to be used for searching
      * @return String value
      * @throws PAException on error
      */
     String getCountryOrOrgName(Long id, String className) throws PAException;
     
     /**
      * 
      * @param id primary id
      * @return RegulatoryAuthority
      * @throws PAException on error
      */
     RegulatoryAuthority get(Long id) throws PAException;
     
     /**
      * 
      * @param id regulatory id
      * @return Country 
      * @throws PAException on error
      */
     Country getRegulatoryAuthorityCountry(Long id)  throws PAException;
     
}
