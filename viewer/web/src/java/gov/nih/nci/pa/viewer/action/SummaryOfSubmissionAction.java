package gov.nih.nci.pa.viewer.action;

import gov.nih.nci.pa.report.dto.result.TrialCountsResultDto;
import gov.nih.nci.pa.report.service.TrialCountsLocal;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.viewer.dto.criteria.TrialCountsCriteriaWebDto;
import gov.nih.nci.pa.viewer.dto.result.TrialCountsResultWebDto;
import gov.nih.nci.pa.viewer.util.ViewerServiceLocator;

import java.util.List;

/**
 * @author Hugh Reinhart
 * @since 4/16/2009
 */
public class SummaryOfSubmissionAction extends AbstractReportAction
        <TrialCountsCriteriaWebDto, TrialCountsResultWebDto> {

    private static final long serialVersionUID = -4372196546339166462L;
    private TrialCountsCriteriaWebDto criteria;

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute() throws PAException {
        criteria = new TrialCountsCriteriaWebDto();
        return super.execute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getReport() throws PAException {
        TrialCountsLocal local = ViewerServiceLocator.getInstance().getTrialCountsReportService();
        criteria.setGroupByTimeUnit(NONE);
        List<TrialCountsResultDto> isoList = local.get(criteria.getIsoDto());
        setResultList(TrialCountsResultWebDto.getWebList(isoList));
        return super.getReport();
    }

    /**
     * @return the criteria
     */
    public TrialCountsCriteriaWebDto getCriteria() {
        return criteria;
    }

    /**
     * @param criteria the criteria to set
     */
    public void setCriteria(TrialCountsCriteriaWebDto criteria) {
        this.criteria = criteria;
    }
}
