package gov.nih.nci.coppa.iso;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BlTest {
     @Test
     public void testEquality() {
         Bl first = new Bl();
         first.setNullFlavor(NullFlavor.ASKU);
         first.setValue(true);
         assertTrue(first.equals(first));
         assertFalse(first.equals(null));

         Bl second = new Bl();
         second.setNullFlavor(NullFlavor.ASKU);
         second.setValue(true);

         assertTrue(first.equals(second));

         second.setValue(false);

         assertFalse(first.equals(second));

        }

        @Test
        public void testHashCode() {

            Bl first = new Bl();
            first.setNullFlavor(NullFlavor.ASKU);
            first.setValue(true);

            Bl second = new Bl();
            second.setNullFlavor(NullFlavor.ASKU);
            second.setValue(true);

            assertEquals(first.hashCode(), second.hashCode());
            second.setValue(false);
            assertFalse(first.hashCode() == second.hashCode());

        }

        @Test
        public void testCloneable() throws CloneNotSupportedException {
            Bl first = new Bl();
            first.setNullFlavor(NullFlavor.ASKU);
            first.setValue(true);

            Bl second = (Bl) first.clone();

            assertTrue(first != second);
            assertTrue(first.equals(second));
            assertEquals(first.hashCode(), second.hashCode());

        }
}
