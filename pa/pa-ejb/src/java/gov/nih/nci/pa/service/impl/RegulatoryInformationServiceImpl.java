package gov.nih.nci.pa.service.impl;

import java.util.List;

import gov.nih.nci.pa.dao.RegulatoryInformationDAO;
import gov.nih.nci.pa.dto.CountryRegAuthorityDTO;
import gov.nih.nci.pa.dto.RegulatoryAuthOrgDTO;
import gov.nih.nci.pa.dto.RegulatoryInformationDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.RegulatoryInformationServiceRemote;

/**
 * 
 * @author Harsha
 * @since 08/05/2008
 * copyright NCI 2007.  All rights reserved.
 * 
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public class RegulatoryInformationServiceImpl implements RegulatoryInformationServiceRemote {

    /**
     * Return a list of countries.
     * 
     * @return List CountryRegAuthorityDTO 
     */
        public List<CountryRegAuthorityDTO> getCountryList() {
                List<CountryRegAuthorityDTO> retList = null;    
                try {
                        RegulatoryInformationDAO informationDAO = new RegulatoryInformationDAO();
                        retList = informationDAO.getCountryList();
                } catch (Exception e) {
                        //TODO DO NOT DO THIS
                        /*****************************
                         * WILL BE REFACTORED*********
                         *****************************/
                        e.printStackTrace();                    
                }
                return retList;

        }
        
    /**
     * Method to get a Country name using the country id.
     *  
     * @param countryId as long
     * @return country name as String
         * @throws PAException on error 
     */
        public String getCountryName(long countryId) throws PAException {
                RegulatoryInformationDAO informationDAO = new RegulatoryInformationDAO();
                return informationDAO.getCountryName(countryId);                
        }
        
    /**
     * Method to get a list of distinct country names only.
     * 
     * @return List of distinct country names
     * @throws PAException on error
     */
        public List getDistinctCountryNames() throws PAException {
                RegulatoryInformationDAO informationDAO = new RegulatoryInformationDAO();
                return informationDAO.getDistinctCountryList();
        }

    /**
     * Method to get the protocol for edit action.
     * 
     * @param protocolId for a given protocol
     * @return RegulatoryInformationDTO information
     * @throws PAException on error
     */ 
        public RegulatoryInformationDTO getProtocolForEdit(long protocolId)
                        throws PAException {
                RegulatoryInformationDAO informationDAO = new RegulatoryInformationDAO();
                return informationDAO.getProtocolForEdit(protocolId);
        }

    /**
     * Method to Regulatory Org DTO for a given protocol ID. 
     * 
     * @param protocolId for a given protocol
     * @return RegulatoryAuthOrgDTO information
     * @throws PAException on error
     */ 
        public RegulatoryAuthOrgDTO getRegulatoryAuthOrgForEdit(long protocolId)
                        throws PAException {
                RegulatoryInformationDAO informationDAO = new RegulatoryInformationDAO();
                return informationDAO.getRegulatoryAuthOrgForEdit(protocolId);          
        }

    /**
     * Method to get a list of Authority Organization names.
     *  
     * @param regAuthID Regulatory authorization id
     * @return List of Authority name(s) as String
         * @throws PAException on error 
     */
        public List<String> getRegulatoryAuthorityName(Long regAuthID)
                        throws PAException {
                RegulatoryInformationDAO informationDAO = new RegulatoryInformationDAO();
                return informationDAO.getRegulatoryAuthorityName(regAuthID);
        }

    /**
     * Method to get a list of Regulatory Authority organizations for a 
     * given regulatory authority id.
     * 
     * @param regAuthID as Long
     * @return List of RegulatoryAuthOrgDTO(s)
     * @throws PAException on error
     */ 
        public List<RegulatoryAuthOrgDTO> getRegulatoryAuthorityNameId(
                        Long regAuthID) throws PAException {
                RegulatoryInformationDAO informationDAO = new RegulatoryInformationDAO();
                return informationDAO.getRegulatoryAuthorityNameId(regAuthID);
        }

        /**
         * Method to save the Regulatory Information DTO.
         * 
         * @param regulatoryDTO containing regulatoryinfo
         * @throws PAException on error
         */
        public void saveRegulatoryInformation(RegulatoryInformationDTO regulatoryDTO)
                        throws PAException {
                RegulatoryInformationDAO informationDAO = new RegulatoryInformationDAO();
                informationDAO.saveRegulatoryInformation(regulatoryDTO);
        }

}
