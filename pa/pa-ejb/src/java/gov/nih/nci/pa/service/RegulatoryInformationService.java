package gov.nih.nci.pa.service;

/**
 * 
 * @author Harsha
 * @since 08/05/2008
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
import gov.nih.nci.pa.dto.CountryRegAuthorityDTO;
import gov.nih.nci.pa.dto.RegulatoryAuthOrgDTO;
import gov.nih.nci.pa.dto.RegulatoryInformationDTO;

import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

public interface RegulatoryInformationService {	
    /**
     * @return List CountryRegAuthorityDTO 
     */
	List<CountryRegAuthorityDTO> getCountryList();
	
	/**
	 * @param RegulatoryInformationDTO containing regulatoryinfo
	 * @throws PAException on error
	 */	
	void saveRegulatoryInformation(RegulatoryInformationDTO regulatoryDTO) throws PAException;
	
    /**
     * @param regAuthID Regulatory authorization id
     * @return List of Authority name(s) as String
	 * @throws PAException on error 
     */	
	List<String> getRegulatoryAuthorityName(Long regAuthID) throws PAException;
	
    /**
     * @param countryId as long
     * @return country name as String
	 * @throws PAException on error 
     */	
	String getCountryName(long countryId) throws PAException;
	
    /**
     * @return List of distinct country names
     * @throws PAException on error
     */	
	List getDistinctCountryNames() throws PAException;
	
    /**
     * @param regAuthID as Long
     * @return List of RegulatoryAuthOrgDTO(s)
     * @throws PAException on error
     */	
	List<RegulatoryAuthOrgDTO> getRegulatoryAuthorityNameId(Long regAuthID) throws PAException;

    /**
     * @param protocol Id
     * @return RegulatoryInformationDTO information
     * @throws PAException
     */
	RegulatoryInformationDTO getProtocolForEdit(long protocolId) throws PAException;
	
    /**
     * @param protocol Id
     * @return RegulatoryAuthOrgDTO information
     * @throws PAException
     */	
	RegulatoryAuthOrgDTO getRegulatoryAuthOrgForEdit(long protocolId) throws PAException;
}
