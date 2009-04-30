package gov.nih.nci.coppa.services.grid.faults;

import org.junit.Test;
import org.oasis.wsrf.faults.BaseFaultType;

public class CoppaFaultHelperTest {

    @Test
    public void testPOFaultHelperBaseFaultType() {
        CoppaFaultHelper faultHelper = new CoppaFaultHelper(new BaseFaultType());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testPOFaultHelperBaseFaultType_NullParam() {
        CoppaFaultHelper faultHelper = new CoppaFaultHelper(null);

    }

}
