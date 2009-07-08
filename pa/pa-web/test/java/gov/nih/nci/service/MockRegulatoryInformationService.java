/**
 * 
 */
package gov.nih.nci.service;

import java.util.ArrayList;
import java.util.List;

import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.RegulatoryAuthority;
import gov.nih.nci.pa.dto.CountryRegAuthorityDTO;
import gov.nih.nci.pa.dto.RegulatoryAuthOrgDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.RegulatoryInformationServiceRemote;

/**
 * @author Administrator
 *
 */
public class MockRegulatoryInformationService implements
        RegulatoryInformationServiceRemote {

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.util.RegulatoryInformationServiceRemote#get(java.lang.Long)
     */
    public RegulatoryAuthority get(Long id) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.util.RegulatoryInformationServiceRemote#getCountryOrOrgName(java.lang.Long, java.lang.String)
     */
    public String getCountryOrOrgName(Long id, String className)
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.util.RegulatoryInformationServiceRemote#getDistinctCountryNames()
     */
    public List<CountryRegAuthorityDTO> getDistinctCountryNames()
            throws PAException {
        return new ArrayList<CountryRegAuthorityDTO>();
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.util.RegulatoryInformationServiceRemote#getRegulatoryAuthorityCountry(java.lang.Long)
     */
    public Country getRegulatoryAuthorityCountry(Long id) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.util.RegulatoryInformationServiceRemote#getRegulatoryAuthorityInfo(java.lang.Long)
     */
    public List<Long> getRegulatoryAuthorityInfo(Long regAuthID)
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.util.RegulatoryInformationServiceRemote#getRegulatoryAuthorityNameId(java.lang.Long)
     */
    public List<RegulatoryAuthOrgDTO> getRegulatoryAuthorityNameId(
            Long regAuthID) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

}
