package gov.nih.nci.pa.report.service;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.report.dto.criteria.TrialListCriteriaDto;
import gov.nih.nci.pa.report.dto.result.TrialListResultDto;
import gov.nih.nci.pa.report.util.TestSchema;
import gov.nih.nci.pa.service.PAException;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TrialListTest {
	TrialListLocal localSvc;

    @Before
    public void setUp() throws Exception {
        TestSchema.reset();
    	localSvc = new TrialListReportBean();
     }

    @Test
    public void getTest() throws Exception {
        TrialListCriteriaDto crit = new TrialListCriteriaDto();
        crit.setCtep(BlConverter.convertToBl(true));
        crit.setTimeInterval(IvlConverter.convertTs().convertToIvl("1/1/2000", "6/1/2009"));
        List<TrialListResultDto> resultList = localSvc.get(crit);
        assertEquals(1, resultList.size());
        for (TrialListResultDto dto : resultList) {
            System.out.println(StConverter.convertToString(dto.getAssignedIdentifier()));
            System.out.println(StConverter.convertToString(dto.getOfficialTitle()));
            System.out.println(StConverter.convertToString(dto.getOrganization()));
            System.out.println(TsConverter.convertToString(dto.getDateLastCreated()));
            System.out.println(IntConverter.convertToInteger(dto.getSubmissionNumber()));
            System.out.println(CdConverter.convertCdToString(dto.getStatusCode()));
        }
    }

    @Test (expected=PAException.class)
    public void criteriaValidateTest() throws Exception {
        TrialListCriteriaDto crit = new TrialListCriteriaDto();
        localSvc.get(crit);
    }
}
