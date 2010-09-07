package gov.nih.nci.pa.report.service;


import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.pa.report.dto.criteria.Summ4RepCriteriaDto;
import gov.nih.nci.pa.report.dto.result.Summ4RepResultDto;
import gov.nih.nci.pa.service.PAException;

import java.util.List;

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
     * @throws gov.nih.nci.coppa.services.TooManyResultsException if error.
     */
    List<String> searchPoOrgNames(String partial, int maxLimit) throws PAException, 
        TooManyResultsException;
}
