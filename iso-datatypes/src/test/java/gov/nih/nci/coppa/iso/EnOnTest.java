package gov.nih.nci.coppa.iso;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

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

        e = new Enxp(null);
        enon.getPart().add(e);

        assertEquals(2, enon.getPart().size());

    }
}