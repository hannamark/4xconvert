package gov.nih.nci.pa.report.service;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.report.dto.criteria.SubmissionTypeCriteriaDto;
import gov.nih.nci.pa.report.dto.result.TrialListResultDto;
import gov.nih.nci.pa.report.enums.SubmissionTypeCode;
import gov.nih.nci.pa.report.util.TestSchema;

import java.util.List;

import org.junit.Before;
import org.junit.Test;


public class TrialCountsTest {
    TrialListLocal localSvc;

    @Before
    public void setUp() throws Exception {
        TestSchema.reset();
        localSvc = new TrialListReportBean();
    }

    @Test
    public void getTest() throws Exception {
        SubmissionTypeCriteriaDto crit = new SubmissionTypeCriteriaDto();
        crit.setCtep(BlConverter.convertToBl(true));
        crit.setSubmissionType(CdConverter.convertStringToCd(SubmissionTypeCode.BOTH.name()));
        crit.setTimeInterval(IvlConverter.convertTs().convertToIvl("01/01/2009", "06/01/2009"));
        List<TrialListResultDto> resultList = localSvc.get(crit);
        assertEquals(1, resultList.size());
        for (TrialListResultDto dto : resultList) {
            System.out.println(StConverter.convertToString(dto.getAssignedIdentifier()));
            System.out.println(TsConverter.convertToString(dto.getDateLastCreated()));
        }
    }
}
