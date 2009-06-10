package gov.nih.nci.pa.report.service;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.pa.iso.util.StConverter;

import org.junit.Test;

public class AverageMilestoneTest {


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
