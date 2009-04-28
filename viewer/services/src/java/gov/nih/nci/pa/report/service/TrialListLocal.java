package gov.nih.nci.pa.report.service;

import gov.nih.nci.pa.report.service.criteria.TrialListCriteriaDto;
import gov.nih.nci.pa.report.service.result.TrialListResultDto;

import javax.ejb.Local;

/**
 * @author Hugh Reinhart
 * @since 03/19/2009
 */
@Local
public interface TrialListLocal extends BaseReportInterface<TrialListCriteriaDto, TrialListResultDto> {
}
