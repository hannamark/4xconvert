package gov.nih.nci.pa.report.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.report.dto.criteria.SubmissionTypeCriteriaDto;
import gov.nih.nci.pa.report.dto.result.AverageMilestoneResultDto;
import gov.nih.nci.pa.report.enums.SubmissionTypeCode;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PAUtil;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class AverageMilestoneTest
    extends AbstractReportBeanTest<SubmissionTypeCriteriaDto, AverageMilestoneResultDto, AverageMilestoneReportBean>{

    @Override
    @Before
    public void setUp() throws Exception {
        bean = new AverageMilestoneReportBean();
        super.setUp();
    }

    @Override
    @Test (expected=PAException.class)
    public void criteriaValidateTest() throws Exception {
        SubmissionTypeCriteriaDto criteria = new SubmissionTypeCriteriaDto();
        bean.get(criteria);
    }

    @Override
    @Test
    public void getTest() throws Exception {
        SubmissionTypeCriteriaDto criteria = new SubmissionTypeCriteriaDto();
        criteria.setCtep(BlConverter.convertToBl(true));
        criteria.setSubmissionType(CdConverter.convertToCd(SubmissionTypeCode.BOTH));
        criteria.setTimeInterval(IvlConverter.convertTs().convertToIvl("1/1/2000", PAUtil.today()));
        List<AverageMilestoneResultDto> resultList = bean.get(criteria);
        assertNotNull(resultList);
    }

    @Test
    public void setResultLowTest() throws Exception {
        Integer[] array = { 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 33 };
        assertEquals ("2", StConverter.convertToString(AverageMilestoneReportBean.setResultLow(array)));
    }

    @Test
    public void setResultHighTest() throws Exception {
        Integer[] array = { 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 33 };
        assertEquals (">10", StConverter.convertToString(AverageMilestoneReportBean.setResultHigh(array)));
    }

    @Test
    public void setResultLowAverage() throws Exception {
        Integer[] array = { 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 33 };
        assertEquals ("13.333333", StConverter.convertToString(AverageMilestoneReportBean.setResultAverage(array)));
    }
}
