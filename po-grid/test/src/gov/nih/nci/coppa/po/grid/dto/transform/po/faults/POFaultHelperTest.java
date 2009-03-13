package gov.nih.nci.coppa.po.grid.dto.transform.po.faults;

import static org.junit.Assert.*;

import org.junit.Test;
import org.oasis.wsrf.faults.BaseFaultType;

public class POFaultHelperTest {

    @Test
    public void testPOFaultHelperBaseFaultType() {
        POFaultHelper faultHelper = new POFaultHelper(new BaseFaultType());
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testPOFaultHelperBaseFaultType_NullParam() {
        POFaultHelper faultHelper = new POFaultHelper(null);
        
    }

}
