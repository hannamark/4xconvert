package gov.nih.nci.coppa.iso;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class AdTest {
    @Test
    public void testEquality() {
        Ad first = new Ad();
        first.setNullFlavor(NullFlavor.ASKU);
        first.setPart(new ArrayList<Adxp>());
        first.getPart().add(Adxp.createAddressPart(AddressPartType.ADL));
        first.getPart().add(Adxp.createAddressPart(AddressPartType.AL));
        first.getPart().add(Adxp.createAddressPart(AddressPartType.BNN));

        assertTrue(first.equals(first));
        assertFalse(first.equals(null));

        Ad second = new Ad();
        second.setNullFlavor(NullFlavor.DER);
        second.setPart(new ArrayList<Adxp>());
        second.getPart().add(Adxp.createAddressPart(AddressPartType.ADL));
        second.getPart().add(Adxp.createAddressPart(AddressPartType.AL));
        second.getPart().add(Adxp.createAddressPart(AddressPartType.BNN));

        assertFalse(first.equals(second));

        second.setNullFlavor(NullFlavor.ASKU);

        assertTrue(first.equals(second));

        second.getPart().remove(Adxp.createAddressPart(AddressPartType.ADL));
        second.getPart().add(Adxp.createAddressPart(AddressPartType.CEN));

        assertFalse(first.equals(second));

    }

    @Test
    public void testHashCode() {
        Ad first = new Ad();
        first.setNullFlavor(NullFlavor.ASKU);
        first.setPart(new ArrayList<Adxp>());
        first.getPart().add(Adxp.createAddressPart(AddressPartType.ADL));
        first.getPart().add(Adxp.createAddressPart(AddressPartType.AL));
        first.getPart().add(Adxp.createAddressPart(AddressPartType.BNN));

        Ad second = new Ad();
        second.setNullFlavor(NullFlavor.ASKU);
        second.setPart(new ArrayList<Adxp>());
        second.getPart().add(Adxp.createAddressPart(AddressPartType.ADL));
        second.getPart().add(Adxp.createAddressPart(AddressPartType.AL));
        second.getPart().add(Adxp.createAddressPart(AddressPartType.BNN));


        assertEquals(first.hashCode(), second.hashCode());

        second.getPart().remove(Adxp.createAddressPart(AddressPartType.ADL));
        second.getPart().add(Adxp.createAddressPart(AddressPartType.CEN));
        second.getPart().add(Adxp.createAddressPart(AddressPartType.DAL));
        second.getPart().add(Adxp.createAddressPart(AddressPartType.DINST));

        assertFalse(first.hashCode() == second.hashCode());

    }

    @Test
    public void testCloneable() throws CloneNotSupportedException {

        Ad first = new Ad();
        first.setNullFlavor(NullFlavor.ASKU);
        first.setPart(new ArrayList<Adxp>());
        first.getPart().add(Adxp.createAddressPart(AddressPartType.ADL));
        first.getPart().add(Adxp.createAddressPart(AddressPartType.AL));
        first.getPart().add(Adxp.createAddressPart(AddressPartType.BNN));


        Ad second = (Ad) first.clone();

        assertTrue(first != second);
        assertTrue(first.equals(second));
        assertEquals(first.hashCode(), second.hashCode());

        Ad third = new Ad();
        third.setNullFlavor(NullFlavor.MSK);

        Ad fourth = (Ad) third.clone();

        assertTrue(third != fourth);
        assertTrue(third.equals(fourth));
        assertEquals(third.hashCode(), fourth.hashCode());

    }
}
