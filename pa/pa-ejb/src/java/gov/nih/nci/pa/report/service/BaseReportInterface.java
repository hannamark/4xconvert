package gov.nih.nci.pa.report.service;

import java.util.List;

/**
 * @author Hugh Reinhart
 * @since 03/19/2009
 *
 * @param <CRITERIA> criteria dto
 * @param <RESULT> result dto
 */
public interface BaseReportInterface<CRITERIA, RESULT> {
    /**
     * @param criteria criteria
     * @return report
     */
    List<RESULT> get(CRITERIA criteria);
}
