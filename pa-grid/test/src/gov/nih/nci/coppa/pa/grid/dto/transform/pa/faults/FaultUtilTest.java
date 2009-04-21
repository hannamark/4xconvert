package gov.nih.nci.coppa.pa.grid.dto.transform.pa.faults;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.faults.FaultUtil;

import java.rmi.RemoteException;

import org.junit.Test;

/**
 * DO NOT EDIT!!! COPY/PASTE FROM PO-GRID.  See PO-927 for refactoring task.
 * If you need to modify this file (bug?), change in po-grid and re-import to this location.
 */
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

}
