package gov.nih.nci.coppa.pa.grid.dto.transform.pa.faults;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import gov.nih.nci.accrual.service.exception.IndexedInputValidationException;
import gov.nih.nci.coppa.po.faults.EntityValidationFault;
import gov.nih.nci.coppa.po.faults.NullifiedEntityFault;
import gov.nih.nci.coppa.po.faults.NullifiedRoleFault;
import gov.nih.nci.coppa.po.faults.SimpleIIMapTypeEntry;
import gov.nih.nci.coppa.po.faults.StringMapTypeEntry;
import gov.nih.nci.coppa.services.accrual.faults.IndexedInputValidationFault;
import gov.nih.nci.coppa.services.pa.faults.DuplicateParticipatingSiteFault;
import gov.nih.nci.coppa.services.pa.faults.PAFault;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.faults.FaultUtil;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.grid.dto.transform.DtoTransformException;
import gov.nih.nci.iso21090.grid.dto.transform.iso.IITransformerTest;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.exception.DuplicateParticipatingSiteException;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.entity.NullifiedEntityException;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class FaultUtilTest {

    @Test
    public void testReThrowRemote_RE() {
        RemoteException cause = new RemoteException();
        RemoteException result = FaultUtil.reThrowRemote(cause);
        assertSame(cause, result);
    }

    @Test
    public void testReThrowRemote_Unknown() {
        RuntimeException cause = new RuntimeException("123");
        RemoteException result = FaultUtil.reThrowRemote(cause);
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
    public void testReThrowRemoteNullifiedEntityException() throws DtoTransformException {
        Ii ii = new Ii();
        ii.setExtension("123");
        Ii duplicateOf = new Ii();
        duplicateOf.setExtension("456");
        NullifiedEntityException cause = new NullifiedEntityException(ii, duplicateOf);
        NullifiedEntityFault result = (NullifiedEntityFault) FaultUtil.reThrowRemote(cause);
        assertNull(result.getCause());
        SimpleIIMapTypeEntry[] nullifiedEntities = result.getNullifiedEntries().getEntry();
        assertNotNull(nullifiedEntities);
        assertEquals(1, nullifiedEntities.length);
        assertEquals("123", nullifiedEntities[0].getKey());
        assertEquals("456", nullifiedEntities[0].getValue());
    }

    @Test
    public void testReThrowRemoteNullifiedRoleException() {
        Ii ii = new Ii();
        ii.setExtension("123");
        Ii duplicateOf = new Ii();
        duplicateOf.setExtension("456");
        NullifiedRoleException cause = new NullifiedRoleException(ii, duplicateOf);
        NullifiedRoleFault result = (NullifiedRoleFault) FaultUtil.reThrowRemote(cause);
        assertNull(result.getCause());
        SimpleIIMapTypeEntry[] nullifiedEntities = result.getNullifiedEntries().getEntry();
        assertNotNull(nullifiedEntities);
        assertEquals(1, nullifiedEntities.length);
        assertEquals("123", nullifiedEntities[0].getKey());
        assertEquals("456", nullifiedEntities[0].getValue());
    }

    @Test
    public void testReThrowRemoteEntityValidationException() {
        Map<String, String[]> errors = new HashMap<String, String[]>();
        errors.put("test", new String[]{"invalid", "not found"});
        EntityValidationException cause = new EntityValidationException(errors);
        EntityValidationFault result = (EntityValidationFault) FaultUtil.reThrowRemote(cause);
        assertNull(result.getCause());
        StringMapTypeEntry[] errorEntry = result.getErrors().getEntry();
        assertNotNull(errorEntry);
        assertEquals(1, errorEntry.length);
        assertEquals("test", errorEntry[0].getKey());
        assertEquals("invalid", errorEntry[0].getValue()[0]);
        assertEquals("not found", errorEntry[0].getValue()[1]);
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

    @Test
    public void testReThrowRemoteIndexedInputValidationException() {
        IndexedInputValidationException cause = new IndexedInputValidationException("Validation Exception", 1);
        IndexedInputValidationFault result = (IndexedInputValidationFault) FaultUtil.reThrowRemote(cause);
        assertNull(result.getCause());
        assertEquals("Validation Exception", result.getDescription()[0].get_value());
        assertEquals(1, result.getIndex());
    }

}
