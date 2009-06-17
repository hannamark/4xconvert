package gov.nih.nci.pa.report.service;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.report.dto.criteria.StandardCriteriaDto;
import gov.nih.nci.pa.report.dto.result.TrialCountsResultDto;
import gov.nih.nci.pa.service.PAException;

import java.util.List;

import org.junit.Before;
import org.junit.Test;


public class TrialCountsTest
        extends AbstractReportBeanTest<StandardCriteriaDto, TrialCountsResultDto, TrialCountsReportBean> {

    @Override
    @Before
    public void setUp() throws Exception {
        bean = new TrialCountsReportBean();
        super.setUp();
    }

    @Override
    @Test (expected=PAException.class)
    public void criteriaValidateTest() throws Exception {
    	StandardCriteriaDto crit = new StandardCriteriaDto();
        bean.get(crit);
    }

    @Override
    @Test
    public void getTest() throws Exception {
    	StandardCriteriaDto crit = new StandardCriteriaDto();
        crit.setCtep(BlConverter.convertToBl(true));
        crit.setTimeInterval(IvlConverter.convertTs().convertToIvl("01/01/2009", "06/01/2009"));
        List<TrialCountsResultDto> resultList = bean.get(crit);
        assertEquals(1, resultList.size());
    }
}
