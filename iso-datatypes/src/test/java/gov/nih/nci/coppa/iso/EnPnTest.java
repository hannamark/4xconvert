package gov.nih.nci.coppa.iso;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author lpower
 */
public class EnPnTest {

    public EnPnTest() {
    }

    @Test
    public void testRestrictions() {
        EnPn enpn = new EnPn();
        Enxp e = new Enxp(EntityNamePartType.DEL);
        try { 
            enpn.getPart().add(e);
            fail();
        } catch (IllegalArgumentException ex) {
        }
        
        e = new Enxp(EntityNamePartType.FAM);
        enpn.getPart().add(e);
        
        e = new Enxp(EntityNamePartType.GIV);
        enpn.getPart().add(e);

        e = new Enxp(EntityNamePartType.PFX);
        enpn.getPart().add(e);

        e = new Enxp(EntityNamePartType.SFX);
        enpn.getPart().add(e);

        e = new Enxp(null);
        enpn.getPart().add(e);
        assertEquals(5, enpn.getPart().size());
        
    }
}