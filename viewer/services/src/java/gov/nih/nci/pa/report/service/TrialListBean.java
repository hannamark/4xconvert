package gov.nih.nci.pa.report.service;

import gov.nih.nci.pa.iso.util.StConverter;
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
        ArrayList<TrialListResultDto> rList = new ArrayList<TrialListResultDto>();
        TrialListResultDto dto = new TrialListResultDto();
        dto.setOfficialTitle(StConverter.convertToSt("Hello world"));
        rList.add(dto);
        return rList;
    }
}
