package gov.nih.nci.pa.report.service;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.report.dto.criteria.AbstractStandardCriteriaDto;
import gov.nih.nci.pa.report.dto.criteria.SubmissionTypeCriteriaDto;
import gov.nih.nci.pa.report.dto.result.TrialListResultDto;
import gov.nih.nci.pa.report.enums.SubmissionTypeCode;
import gov.nih.nci.pa.service.PAException;

import java.util.List;

import org.junit.Before;
import org.junit.Test;


public class TrialCountsTest
        extends AbstractReportBeanTest<AbstractStandardCriteriaDto, TrialListResultDto, TrialListReportBean> {

    @Override
    @Before
    public void setUp() throws Exception {
        bean = new TrialListReportBean();
        super.setUp();
    }

    @Override
    @Test (expected=PAException.class)
    public void criteriaValidateTest() throws Exception {
        SubmissionTypeCriteriaDto crit = new SubmissionTypeCriteriaDto();
        bean.get(crit);
    }

    @Override
    @Test
    public void getTest() throws Exception {
        SubmissionTypeCriteriaDto crit = new SubmissionTypeCriteriaDto();
        crit.setCtep(BlConverter.convertToBl(true));
        crit.setSubmissionType(CdConverter.convertStringToCd(SubmissionTypeCode.BOTH.name()));
        crit.setTimeInterval(IvlConverter.convertTs().convertToIvl("01/01/2009", "06/01/2009"));
        List<TrialListResultDto> resultList = bean.get(crit);
        assertEquals(1, resultList.size());
        for (TrialListResultDto dto : resultList) {
            System.out.println(StConverter.convertToString(dto.getAssignedIdentifier()));
            System.out.println(TsConverter.convertToString(dto.getDateLastCreated()));
        }
    }
}
