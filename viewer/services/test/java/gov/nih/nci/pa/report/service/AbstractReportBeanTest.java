package gov.nih.nci.pa.report.service;

import gov.nih.nci.pa.report.dto.criteria.AbstractCriteriaDto;
import gov.nih.nci.pa.report.util.TestSchema;

abstract class AbstractReportBeanTest<CRITERIA extends AbstractCriteriaDto,
                           RESULT,
                           BEAN extends AbstractReportBean<CRITERIA, RESULT>> {

    public BEAN bean = null;

    public void setUp() throws Exception {
        TestSchema.reset();
    }

    public abstract void criteriaValidateTest() throws Exception;

    public abstract void getTest() throws Exception;
}
