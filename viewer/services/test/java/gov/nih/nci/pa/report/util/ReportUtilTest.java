package gov.nih.nci.pa.report.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.pa.util.PAUtil;

import org.junit.Test;

public class ReportUtilTest {

    @Test
    public void invalidIntervalValuesTest() {
        assertTrue(ReportUtil.invalidIntervalValues(null, PAUtil.dateStringToTimestamp("1/1/2000")));
        assertTrue(ReportUtil.invalidIntervalValues(PAUtil.dateStringToTimestamp("1/1/2000"), null));
        assertTrue(ReportUtil.invalidIntervalValues(PAUtil.dateStringToTimestamp("1/1/2009"), PAUtil.dateStringToTimestamp("1/1/2000")));
        assertFalse(ReportUtil.invalidIntervalValues(PAUtil.dateStringToTimestamp("1/1/2000"), PAUtil.dateStringToTimestamp("1/1/2009")));
    }
}
