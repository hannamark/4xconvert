package gov.nih.nci.pa.report.service;


import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.pa.report.dto.criteria.Summ4RepCriteriaDto;
import gov.nih.nci.pa.report.dto.result.Summ4RepResultDto;
import gov.nih.nci.pa.service.PAException;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

/**
 * @author Max Shestopalov
 */
@Local
public interface Summ4RepLocal extends ViewerReport<Summ4RepCriteriaDto, Summ4RepResultDto> {
    
    /**
     * Given a partial org name, find anything that fits.
     * @param partial org name 
     * @param maxLimit max limit.
     * @return list
     * @throws PAException if error.
     * @throws TooManyResultsException if too many results 
     */
    List<String> searchPoOrgNames(String partial, int maxLimit) throws PAException, 
        TooManyResultsException;
    
    /**
     * Returns map of family in id, name pairs.
     * @param maxLimit max limit.
     * @return list of family names
     * @throws TooManyResultsException if too many results 
     */
    Map<String, String> getFamilies(int maxLimit) throws TooManyResultsException;
    
    /**
     * Returns list of organizations in a family in name, functional type pairs.
     * @param familyId name of the family.
     * @param maxLimit max limit.
     * @return list of organization names
     * @throws TooManyResultsException if too many results 
     */
    Map<String, String> getOrganizations(String familyId, int maxLimit) throws TooManyResultsException;
}
