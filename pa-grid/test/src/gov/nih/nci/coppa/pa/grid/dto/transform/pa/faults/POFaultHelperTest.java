package gov.nih.nci.coppa.pa.grid.dto.transform.pa.faults;

import gov.nih.nci.coppa.services.pa.grid.dto.pa.faults.POFaultHelper;

import org.junit.Test;
import org.oasis.wsrf.faults.BaseFaultType;

/**
 * DO NOT EDIT!!! COPY/PASTE FROM PO-GRID. See PO-927 for refactoring task. If you need to modify this file (bug?),
 * change in po-grid and re-import to this location.
 */
public class POFaultHelperTest {

    @Test
    public void testPOFaultHelperBaseFaultType() {
        POFaultHelper faultHelper = new POFaultHelper(new BaseFaultType());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPOFaultHelperBaseFaultType_NullParam() {
        POFaultHelper faultHelper = new POFaultHelper(null);

    }

}
