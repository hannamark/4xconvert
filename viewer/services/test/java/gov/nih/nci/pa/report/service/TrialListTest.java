package gov.nih.nci.pa.report.service;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.report.dto.criteria.TrialListCriteriaDto;
import gov.nih.nci.pa.report.dto.result.TrialListResultDto;
import gov.nih.nci.pa.report.test.util.TestSchema;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TrialListTest {
	TrialListLocal localSvc;

    @Before
    public void setUp() throws Exception {
        TestSchema.reset();
    	localSvc = new TrialListBean();
     }

    @Test
    public void getTest() throws Exception {
    	TrialListCriteriaDto crit = new TrialListCriteriaDto();
        List<TrialListResultDto> resultList = localSvc.get(crit);
        assertEquals(1, resultList.size());
        for (TrialListResultDto dto : resultList) {
            System.out.println(StConverter.convertToString(dto.getAssignedIdentifier()));
            System.out.println(StConverter.convertToString(dto.getOfficialTitle()));
            System.out.println(StConverter.convertToString(dto.getOrganization()));
            System.out.println(TsConverter.convertToString(dto.getDateLastCreated()));
            System.out.println(CdConverter.convertCdToString(dto.getStatusCode()));
        }
    }
}
