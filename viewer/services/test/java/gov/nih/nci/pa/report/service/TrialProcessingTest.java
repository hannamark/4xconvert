package gov.nih.nci.pa.report.service;

import static org.junit.Assert.assertTrue;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.report.dto.criteria.AssignedIdentifierCriteriaDto;
import gov.nih.nci.pa.report.dto.result.TrialProcessingResultDto;
import gov.nih.nci.pa.report.util.TestSchema;
import gov.nih.nci.pa.service.PAException;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TrialProcessingTest
    extends AbstractReportBeanTest<AssignedIdentifierCriteriaDto, TrialProcessingResultDto, TrialProcessingReportBean> {

    @Override
    @Before
    public void setUp() throws Exception {
        bean = new TrialProcessingReportBean();
        super.setUp();
    }

    @Override
    @Test (expected=PAException.class)
    public void criteriaValidateTest() throws Exception {
        AssignedIdentifierCriteriaDto criteria = new AssignedIdentifierCriteriaDto();
        bean.get(criteria);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Test
    public void getTest() throws Exception {
        AssignedIdentifierCriteriaDto criteria = new AssignedIdentifierCriteriaDto();
        criteria.setAssignedIdentifier(StConverter.convertToSt(TestSchema.studyProtocol.get(0).getIdentifier()));
        List<TrialProcessingResultDto> resultList = bean.get(criteria);
        assertTrue(resultList.size() == TestSchema.studyMilestone.size());
    }
}
