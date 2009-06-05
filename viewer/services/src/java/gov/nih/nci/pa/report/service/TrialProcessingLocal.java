package gov.nih.nci.pa.report.service;

import gov.nih.nci.coppa.iso.St;
import gov.nih.nci.pa.report.dto.result.TrialProcessingHeaderResultDto;
import gov.nih.nci.pa.report.dto.result.TrialProcessingResultDto;
import gov.nih.nci.pa.service.PAException;

import javax.ejb.Local;


/**
 * @author Hugh Reinhart
 * @since 06/04/2009
 */
@Local
public interface TrialProcessingLocal extends ViewerReport<St, TrialProcessingResultDto> {
    /**
     * Return run the report and return the results.
     * @param criteria criteria
     * @return header information
     * @throws PAException exception
     */
    TrialProcessingHeaderResultDto getHeader(St criteria) throws PAException;
}
