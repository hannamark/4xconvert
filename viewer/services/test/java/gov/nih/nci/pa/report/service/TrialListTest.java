package gov.nih.nci.pa.report.service;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.pa.report.dto.criteria.TrialListCriteriaDto;
import gov.nih.nci.pa.report.dto.result.TrialListResultDto;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TrialListTest {
	TrialListLocal localSvc;

    @Before
    public void setUp() throws Exception {
    	localSvc = new TrialListBean();
     }

    @Test
    public void getTest() throws Exception {
    	TrialListCriteriaDto crit = new TrialListCriteriaDto();
        List<TrialListResultDto> resultList = localSvc.get(crit);
        assertEquals(1, resultList.size());
    }
}
