package gov.nih.nci.pa.report.util;

import static org.junit.Assert.assertTrue;

import gov.nih.nci.iso21090.DSet;

import java.util.HashSet;

import org.junit.Test;

public class ReportUtilTest {

    
    @Test
    public void testConvertMethods() throws Exception {
        assertTrue(ReportUtil.convertToDSet(null) instanceof DSet);
        assertTrue(ReportUtil.convertToString(null) instanceof HashSet);
    }
}
