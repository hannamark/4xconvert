package gov.nih.nci.coppa.iso;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EnTest {

    @Test
    public void testEquality() {
        En first = new En();
        first.setNullFlavor(NullFlavor.ASKU);

        first.getPart().add(new Enxp(EntityNamePartType.DEL));
        first.getPart().add(new Enxp(EntityNamePartType.FAM));
        first.getPart().add(new Enxp(EntityNamePartType.GIV));

        assertTrue(first.equals(first));
        assertFalse(first.equals(null));

        En second = new En();
        second.setNullFlavor(NullFlavor.ASKU);
        second.getPart().add(new Enxp(EntityNamePartType.DEL));
        second.getPart().add(new Enxp(EntityNamePartType.FAM));
        second.getPart().add(new Enxp(EntityNamePartType.GIV));

        assertTrue(first.equals(second));

        second.getPart().remove(new Enxp(EntityNamePartType.DEL));
        second.getPart().add(new Enxp(EntityNamePartType.SFX));

        assertFalse(first.equals(second));

    }

    @Test
    public void testHashCode() {
        En first = new En();
        first.setNullFlavor(NullFlavor.ASKU);

        first.getPart().add(new Enxp(EntityNamePartType.DEL));
        first.getPart().add(new Enxp(EntityNamePartType.FAM));
        first.getPart().add(new Enxp(EntityNamePartType.GIV));

        En second = new En();
        second.setNullFlavor(NullFlavor.ASKU);
        second.getPart().add(new Enxp(EntityNamePartType.DEL));
        second.getPart().add(new Enxp(EntityNamePartType.FAM));
        second.getPart().add(new Enxp(EntityNamePartType.GIV));

        assertEquals(first.hashCode(), second.hashCode());

        second.getPart().remove(new Enxp(EntityNamePartType.DEL));
        second.getPart().add(new Enxp(EntityNamePartType.SFX));

        assertFalse(first.hashCode() == second.hashCode());

    }

    @Test
    public void testCloneable() throws CloneNotSupportedException {
        En first = new En();
        first.setNullFlavor(NullFlavor.ASKU);

        first.getPart().add(new Enxp(EntityNamePartType.DEL));
        first.getPart().add(new Enxp(EntityNamePartType.FAM));
        first.getPart().add(new Enxp(EntityNamePartType.GIV));

        En second = (En) first.clone();

        assertTrue(first != second);
        assertTrue(first.equals(second));
        assertEquals(first.hashCode(), second.hashCode());
    }
}
