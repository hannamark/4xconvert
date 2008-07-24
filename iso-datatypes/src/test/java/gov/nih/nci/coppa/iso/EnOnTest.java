package gov.nih.nci.coppa.iso;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gax
 */
public class EnOnTest {

    public EnOnTest() {
    }

    @Test
    public void testRestrictions() {
        EnOn enon = new EnOn();
        Enxp e = new Enxp(EntityNamePartType.FAM);
        try { 
            enon.getPart().add(e);
            fail();
        } catch (IllegalArgumentException ex) {
        }
        
        e = new Enxp(EntityNamePartType.GIV);
        try { 
            enon.getPart().add(e);
            fail();
        } catch (IllegalArgumentException ex) {
        }
        
        e = new Enxp(EntityNamePartType.DEL);
        enon.getPart().add(e);
        assertEquals(1, enon.getPart().size());
    }
}