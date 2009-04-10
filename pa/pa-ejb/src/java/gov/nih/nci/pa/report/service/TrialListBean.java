package gov.nih.nci.pa.report.service;

import gov.nih.nci.pa.report.service.criteria.TrialListCriteriaDto;
import gov.nih.nci.pa.report.service.result.TrialListResultDto;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

/**
* @author Hugh Reinhart
* @since 4/10/2008
*/
@Stateless
public class TrialListBean implements TrialListLocal {

    /**
     * @param criteria criteria
     * @return report
     */
    public List<TrialListResultDto> get(TrialListCriteriaDto criteria) {
        return new ArrayList<TrialListResultDto>();
    }
}
