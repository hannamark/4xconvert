package gov.nih.nci.pa.report.service;

import gov.nih.nci.pa.service.PAException;

import java.util.List;

/**
 * @author Hugh Reinhart
 * @since 03/19/2009
 *
 * @param <CRITERIA> criteria dto
 * @param <RESULT> result dto
 */
public interface ViewerReport<CRITERIA, RESULT> {
    /**
     * Return run the report and return the results.
     * @param criteria criteria
     * @return report results
     * @throws PAException exception
     */
    List<RESULT> get(CRITERIA criteria) throws PAException;
}
