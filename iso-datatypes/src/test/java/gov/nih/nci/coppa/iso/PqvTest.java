package gov.nih.nci.coppa.iso;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;

public class PqvTest {

    @Test
    public void testEquality() {

        Pqv first = new Pqv();
        first.setNullFlavor(NullFlavor.ASKU);
        EdText firstText = new EdText();
        firstText.setCharset("char");
        firstText.setData("char".getBytes());
        firstText.setMediaType("text/plain");
        St firstStForText = new St();
        firstStForText.setValue("value");
        firstText.setDescription(firstStForText);
        first.setOriginalText(firstText);
        first.setUncertaintyType(UncertaintyType.B);
        first.setPrecision(111);
        first.setValue(new BigDecimal(222));

        assertTrue(first.equals(first));
        assertFalse(first.equals(null));

        Pqv second = new Pqv();
        second.setNullFlavor(NullFlavor.ASKU);
        EdText secondText = new EdText();
        secondText.setCharset("char");
        secondText.setData("char".getBytes());
        secondText.setMediaType("text/plain");
        St secondStForText = new St();
        secondStForText.setValue("value");
        secondText.setDescription(secondStForText);
        second.setOriginalText(secondText);
        second.setUncertaintyType(UncertaintyType.B);
        second.setPrecision(111);
        second.setValue(new BigDecimal(222));

        assertTrue(first.equals(second));

        second.setPrecision(333);

        assertFalse(first.equals(second));

       }

       @Test
       public void testHashCode() {

           Pqv first = new Pqv();
           first.setNullFlavor(NullFlavor.ASKU);
           EdText firstText = new EdText();
           firstText.setCharset("char");
           firstText.setData("char".getBytes());
           firstText.setMediaType("text/plain");
           St firstStForText = new St();
           firstStForText.setValue("value");
           firstText.setDescription(firstStForText);
           first.setOriginalText(firstText);
           first.setUncertaintyType(UncertaintyType.B);
           first.setPrecision(111);
           first.setValue(new BigDecimal(222));

           assertTrue(first.equals(first));
           assertFalse(first.equals(null));

           Pqv second = new Pqv();
           second.setNullFlavor(NullFlavor.ASKU);
           EdText secondText = new EdText();
           secondText.setCharset("char");
           secondText.setData("char".getBytes());
           secondText.setMediaType("text/plain");
           St secondStForText = new St();
           secondStForText.setValue("value");
           secondText.setDescription(secondStForText);
           second.setOriginalText(secondText);
           second.setUncertaintyType(UncertaintyType.B);
           second.setPrecision(111);
           second.setValue(new BigDecimal(222));

           assertEquals(first.hashCode(), second.hashCode());

           second.setPrecision(333);

           assertFalse(first.hashCode() == second.hashCode());

       }

       @Test
       public void testCloneable() throws CloneNotSupportedException {
           Pqv first = new Pqv();
           first.setNullFlavor(NullFlavor.ASKU);
           EdText firstText = new EdText();
           firstText.setCharset("char");
           firstText.setData("char".getBytes());
           firstText.setMediaType("text/plain");
           St firstStForText = new St();
           firstStForText.setValue("value");
           firstText.setDescription(firstStForText);
           first.setOriginalText(firstText);
           first.setUncertaintyType(UncertaintyType.B);
           first.setPrecision(111);
           first.setValue(new BigDecimal(222));

           Pqv second = (Pqv) first.clone();

           assertTrue(first != second);
           assertTrue(first.equals(second));
           assertEquals(first.hashCode(), second.hashCode());

       }
}
