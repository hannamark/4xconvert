package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.St;
import gov.nih.nci.pa.dto.CountryRegAuthorityDTO;
import gov.nih.nci.pa.dto.RegulatoryAuthOrgDTO;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryInformationDTO;
import java.util.List;

/**
 * 
 * @author Harsha
 * @since 08/05/2008 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
public interface RegulatoryInformationService {
    /**
     * @return List CountryRegAuthorityDTO
     */
    List<CountryRegAuthorityDTO> getCountryList();

    /**
     * @param regulatoryDTO containing regulatoryinfo
     * @throws PAException on error
     */
    void saveRegulatoryInformation(StudyRegulatoryInformationDTO regulatoryDTO) throws PAException;

    /**
     * 
     * @param ii as identifier for the authority
     * @return list of St
     * @throws PAException on error
     */
    St getRegulatoryAuthorityName(Ii ii) throws PAException;

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
     * @param protocolId for a given protocol
     * @return RegulatoryInformationDTO information
     * @throws PAException on error
     */
    StudyRegulatoryInformationDTO getProtocolForEdit(long protocolId) throws PAException;

    /**
     * @param protocolId for a given protocol
     * @return RegulatoryAuthOrgDTO information
     * @throws PAException on error
     */
    RegulatoryAuthOrgDTO getRegulatoryAuthOrgForEdit(long protocolId) throws PAException;
}
