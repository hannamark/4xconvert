package gov.nih.nci.pa.report.service;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.report.dto.criteria.AbstractStandardCriteriaDto;
import gov.nih.nci.pa.report.dto.criteria.SubmissionTypeCriteriaDto;
import gov.nih.nci.pa.report.dto.result.TrialListResultDto;
import gov.nih.nci.pa.report.enums.SubmissionTypeCode;
import gov.nih.nci.pa.service.PAException;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TrialListTest
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
        crit.setTimeInterval(IvlConverter.convertTs().convertToIvl("1/1/2000", "6/1/2009"));
        crit.setSubmissionType(CdConverter.convertStringToCd(SubmissionTypeCode.BOTH.name()));
        List<TrialListResultDto> resultList = bean.get(crit);
        assertEquals(1, resultList.size());
    }
}
