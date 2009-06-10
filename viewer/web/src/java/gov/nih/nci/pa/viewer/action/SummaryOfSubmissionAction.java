package gov.nih.nci.pa.viewer.action;

import gov.nih.nci.pa.report.dto.result.TrialCountsResultDto;
import gov.nih.nci.pa.report.service.TrialCountsLocal;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.viewer.dto.criteria.StandardCriteriaWebDto;
import gov.nih.nci.pa.viewer.dto.result.TrialCountsResultWebDto;
import gov.nih.nci.pa.viewer.util.ViewerServiceLocator;

import java.util.List;

/**
 * @author Hugh Reinhart
 * @since 4/16/2009
 */
public class SummaryOfSubmissionAction extends AbstractReportAction
        <StandardCriteriaWebDto, TrialCountsResultWebDto> {

    private static final long serialVersionUID = -4372196546339166462L;
    private StandardCriteriaWebDto criteria;

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute() {
        criteria = new StandardCriteriaWebDto();
        return super.execute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getReport() {
        TrialCountsLocal local = ViewerServiceLocator.getInstance().getTrialCountsReportService();
        List<TrialCountsResultDto> isoList;
        try {
            isoList = local.get(criteria.getIsoDto());
        } catch (PAException e) {
            addActionError(e.getMessage());
            return super.execute();
        }
        setResultList(TrialCountsResultWebDto.getWebList(isoList));
        return super.getReport();
    }

    /**
     * @return the criteria
     */
    public StandardCriteriaWebDto getCriteria() {
        return criteria;
    }

    /**
     * @param criteria the criteria to set
     */
    public void setCriteria(StandardCriteriaWebDto criteria) {
        this.criteria = criteria;
    }
}
