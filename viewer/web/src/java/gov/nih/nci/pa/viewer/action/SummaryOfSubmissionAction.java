package gov.nih.nci.pa.viewer.action;

import gov.nih.nci.pa.report.dto.result.TrialCountsResultDto;
import gov.nih.nci.pa.report.service.TrialCountsLocal;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.viewer.dto.criteria.StandardCriteriaWebDto;
import gov.nih.nci.pa.viewer.dto.result.TrialCountsResultWebDto;
import gov.nih.nci.pa.viewer.util.ViewerServiceLocator;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.Preparable;

/**
 * @author Hugh Reinhart
 * @since 4/16/2009
 */
public class SummaryOfSubmissionAction extends AbstractReportAction<StandardCriteriaWebDto, TrialCountsResultWebDto>
        implements Preparable {

    private static final long serialVersionUID = -5741635299746623009L;
    
    private TrialCountsLocal trialCountsReportService;
    private StandardCriteriaWebDto criteria;

    /**
     * {@inheritDoc}
     */
    @Override
    public void prepare() {
        setTrialCountsReportService(ViewerServiceLocator.getInstance().getTrialCountsReportService());
    }

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
        try {
            List<TrialCountsResultDto> isoList = trialCountsReportService.get(criteria.getIsoDto());
            setResultList(getWebList(isoList));
            return super.getReport();
        } catch (PAException e) {
            addActionError(e.getMessage());
            return super.execute();
        }
    }
    
    /**
     * Generate a list of web dto's from a list of service dto's.
     * @param serviceDtoList service dto list
     * @return web dto list
     */
    List<TrialCountsResultWebDto> getWebList(List<TrialCountsResultDto> serviceDtoList) {
        List<TrialCountsResultWebDto> resultList = new ArrayList<TrialCountsResultWebDto>();
        int original = 0;
        int amendments = 0;
        for (TrialCountsResultDto dto : serviceDtoList) {
            TrialCountsResultWebDto webDto = new TrialCountsResultWebDto(dto);
            original += webDto.getInitial();
            amendments += webDto.getAmendment();
            resultList.add(webDto);
        }
        TrialCountsResultWebDto webDto = new TrialCountsResultWebDto();
        webDto.setOrganization("All Sites");
        webDto.setInitial(original);
        webDto.setAmendment(amendments);
        resultList.add(webDto);
        return resultList;
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

    /**
     * @param trialCountsReportService the trialCountsReportService to set
     */
    public void setTrialCountsReportService(TrialCountsLocal trialCountsReportService) {
        this.trialCountsReportService = trialCountsReportService;
    }

}
