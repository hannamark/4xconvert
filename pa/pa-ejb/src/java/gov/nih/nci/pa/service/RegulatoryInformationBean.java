package gov.nih.nci.pa.service;

import gov.nih.nci.pa.dao.RegulatoryInformationDAO;
import gov.nih.nci.pa.dto.CountryRegAuthorityDTO;
import gov.nih.nci.pa.dto.RegulatoryAuthOrgDTO;
import gov.nih.nci.pa.dto.RegulatoryInformationDTO;
import gov.nih.nci.pa.service.impl.RegulatoryInformationServiceImpl;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 * Stateless Enterprise Java Bean (EJB) 
 * 
 * @author Harsha
 * @since 08/05/2008
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Stateless
public class RegulatoryInformationBean implements RegulatoryInformationServiceRemote{

    /**
     * Return a list of countries.
     * 
     * @return List CountryRegAuthorityDTO 
     */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<CountryRegAuthorityDTO> getCountryList() {			
		return new RegulatoryInformationServiceImpl().getCountryList();
	}

	/**
	 * Method to save the Regulatory Information DTO.
	 * 
	 * @param RegulatoryInformationDTO containing regulatoryinfo
	 * @throws PAException on error
	 */
	public void saveRegulatoryInformation(RegulatoryInformationDTO regulatoryDTO) throws PAException{
    	new RegulatoryInformationServiceImpl().saveRegulatoryInformation(regulatoryDTO);
	}

    /**
     * Method to get a list of Authority Organization names.
     * 
     * @param regAuthID Regulatory authorization id
     * @return List of Authority name(s) as String
	 * @throws PAException on error 
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)    
	public List<String> getRegulatoryAuthorityName(Long regAuthID)
			throws PAException {
    	return new RegulatoryInformationServiceImpl().getRegulatoryAuthorityName(regAuthID);
	}
    
    /**
     * Method to get a Country name using the country id.
     * 
     * @param countryId as long
     * @return country name as String
	 * @throws PAException on error 
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)	
	public String getCountryName(long countryId) throws PAException{
    	return new RegulatoryInformationServiceImpl().getCountryName(countryId);
	}
	
    /**
     * Method to get a list of distinct country names only.
     *  
     * @return List of distinct country names
     * @throws PAException on error
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)    
	public List getDistinctCountryNames() throws PAException {
    	return new RegulatoryInformationServiceImpl().getDistinctCountryNames();
	}
    
    /**
     * Method to get a list of Regulatory Authority organizations for a 
     * given regulatory authority id.
     *  
     * @param regAuthID as Long
     * @return List of RegulatoryAuthOrgDTO(s)
     * @throws PAException on error
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)	
	public List<RegulatoryAuthOrgDTO> getRegulatoryAuthorityNameId(Long regAuthID) throws PAException {
    	return new RegulatoryInformationServiceImpl().getRegulatoryAuthorityNameId(regAuthID);
	}

    /**
     * Method to get the protocol for edit action.
     *       
     * @param protocol Id
     * @return RegulatoryInformationDTO information
     * @throws PAException
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)    
	public RegulatoryInformationDTO getProtocolForEdit(long protocolId) throws PAException {
    	return new RegulatoryInformationServiceImpl().getProtocolForEdit(protocolId);
	}

    /**
     * Method to Regulatory Org DTO for a given protocol ID.
     * 
     * @param protocol Id
     * @return RegulatoryAuthOrgDTO information
     * @throws PAException
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)    
	public RegulatoryAuthOrgDTO getRegulatoryAuthOrgForEdit(long protocolId)
			throws PAException {
    	return new RegulatoryInformationServiceImpl().getRegulatoryAuthOrgForEdit(protocolId);
	}
}

