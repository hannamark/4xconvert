package gov.nih.nci.coppa.pa.grid.dto.transform.pa.faults;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.coppa.services.pa.faults.DuplicateParticipatingSiteFault;
import gov.nih.nci.coppa.services.pa.faults.PAFault;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.faults.FaultUtil;
import gov.nih.nci.iso21090.grid.dto.transform.iso.IITransformerTest;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.exception.DuplicateParticipatingSiteException;

import java.rmi.RemoteException;

import org.junit.Test;

public class FaultUtilTest {

    @Test
    public void testReThrowRemote_RE() {
        RemoteException cause = new RemoteException();
        RemoteException result = FaultUtil.reThrowRemote(cause);
        assertTrue(result instanceof RemoteException);
        assertSame(cause, result);
    }

    @Test
    public void testReThrowRemote_Unknown() {
        RuntimeException cause = new RuntimeException("123");
        RemoteException result = FaultUtil.reThrowRemote(cause);
        assertTrue(result instanceof RemoteException);
        assertEquals(cause, result.getCause());
    }

    @Test
    public void testReThrowRemotePAException() {
        PAException cause = new PAException("123");
        PAFault result = (PAFault) FaultUtil.reThrowRemote(cause);
        assertNull(result.getCause());
        assertEquals("123", result.getDescription()[0].get_value());
    }
    
    @Test
    public void testReThrowRemoteDuplicateParticipatingSiteException() {
        DuplicateParticipatingSiteException cause = 
            new DuplicateParticipatingSiteException(new IITransformerTest().makeDtoSimple(), 
                    new IITransformerTest().makeDtoSimple());
        DuplicateParticipatingSiteFault result = (DuplicateParticipatingSiteFault) FaultUtil.reThrowRemote(cause);
        assertNull(result.getCause());
        assertEquals("A Participating Site with trial id 123 and HCF id 123 already exists.", 
                result.getDescription()[0].get_value());
    }

}
