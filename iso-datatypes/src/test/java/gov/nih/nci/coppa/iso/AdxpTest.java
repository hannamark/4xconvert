package gov.nih.nci.coppa.iso;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gax
 */
public class AdxpTest {

    @Test
    public void testCreateAddressPart() {
        for (AddressPartType type: AddressPartType.values()){
            Adxp result = Adxp.createAddressPart(type);
            assertEquals(type, result.getType());
            assertTrue("Adxp"+type.name(), result.getClass().getSimpleName().equalsIgnoreCase("Adxp"+type.name()));
        }


        Adxp result = Adxp.createAddressPart(null);
        assertEquals(null, result.getType());
    }

    @Test
    public void testEquality() {
        for (AddressPartType type: AddressPartType.values()){
            Adxp first = Adxp.createAddressPart(type);
            first.setCode("COD");
            first.setValue("value");
            assertTrue(first.equals(first));
            assertFalse(first.equals(null));

            Adxp second = Adxp.createAddressPart(type);
            second.setCode("COD");
            second.setValue("value");

            assertTrue(first.equals(second));

            second.setCode("NCD");

            assertFalse(first.equals(second));
        }
    }

    @Test
    public void testHashCode() {
        for (AddressPartType type: AddressPartType.values()){
            Adxp first = Adxp.createAddressPart(type);
            first.setCode("COD");
            first.setValue("value");

            Adxp second = Adxp.createAddressPart(type);
            second.setCode("COD");
            second.setValue("value");

            assertEquals(first.hashCode(), second.hashCode());

            second.setCode("NCD");

            assertFalse(first.hashCode() == second.hashCode());
        }
    }

    @Test
    public void testCloneable() throws CloneNotSupportedException {
        for (AddressPartType type: AddressPartType.values()){
            Adxp first = Adxp.createAddressPart(type);
            first.setCode("COD");
            first.setValue("value");

            Adxp second = (Adxp) first.clone();

            assertTrue(first != second);
            assertTrue(first.equals(second));
            assertEquals(first.hashCode(), second.hashCode());
        }
    }
}