package gov.nih.nci.coppa.po.grid.dto.transform.po.faults;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.po.faults.EntityValidationFault;
import gov.nih.nci.coppa.po.faults.NullifiedEntityFault;
import gov.nih.nci.coppa.po.faults.NullifiedRoleFault;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.entity.NullifiedEntityException;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class FaultUtilTest {

    @Test
    public void testReThrowRemote_NEE_1() {
        NullifiedEntityException cause = new NullifiedEntityException((Ii) null);
        RemoteException result = FaultUtil.reThrowRemote(cause);
        assertTrue(result instanceof NullifiedEntityFault);
    }
    @Test
    public void testReThrowRemote_NEE_2() {
        Ii id = new Ii();
        id.setExtension("1");
        Ii duplicateOf = new Ii();
        duplicateOf.setExtension("2");
        HashMap<Ii,Ii> map = new HashMap<Ii,Ii>();
        map.put(id, duplicateOf);
        NullifiedEntityException cause = new NullifiedEntityException(map);

        RemoteException result = FaultUtil.reThrowRemote(cause);
        
        assertTrue(result instanceof NullifiedEntityFault);
        NullifiedEntityFault fault = (NullifiedEntityFault) result;
        assertNotNull(fault.getNullifiedEntries());
        assertNotNull(fault.getNullifiedEntries().getEntry());
        assertEquals(1, fault.getNullifiedEntries().getEntry().length);
        
        assertEquals(id.getExtension(), fault.getNullifiedEntries().getEntry()[0].getKey());
        assertEquals(duplicateOf.getExtension(), fault.getNullifiedEntries().getEntry()[0].getValue());
    }

    @Test
    public void testReThrowRemote_NRE_1() {
        NullifiedRoleException cause = new NullifiedRoleException((Ii) null);
        RemoteException result = FaultUtil.reThrowRemote(cause);
        assertTrue(result instanceof NullifiedRoleFault);
    }
    @Test
    public void testReThrowRemote_NRE_2() {
        Ii id = new Ii();
        id.setExtension("1");
        Ii duplicateOf = new Ii();
        duplicateOf.setExtension("2");
        HashMap<Ii,Ii> map = new HashMap<Ii,Ii>();
        map.put(id, duplicateOf);
        NullifiedRoleException cause = new NullifiedRoleException(map);

        RemoteException result = FaultUtil.reThrowRemote(cause);
        
        assertTrue(result instanceof NullifiedRoleFault);
        NullifiedRoleFault fault = (NullifiedRoleFault) result;
        assertNotNull(fault.getNullifiedEntries());
        assertNotNull(fault.getNullifiedEntries().getEntry());
        assertEquals(1, fault.getNullifiedEntries().getEntry().length);
        
        assertEquals(id.getExtension(), fault.getNullifiedEntries().getEntry()[0].getKey());
        assertEquals(duplicateOf.getExtension(), fault.getNullifiedEntries().getEntry()[0].getValue());
    }

    @Test
    public void testReThrowRemote_EVE() {
        EntityValidationException cause = new EntityValidationException((Map<String, String[]>) null);
        RemoteException result = FaultUtil.reThrowRemote(cause);
        assertTrue(result instanceof EntityValidationFault);
    }

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
