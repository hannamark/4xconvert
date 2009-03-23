package gov.nih.nci.coppa.iso;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

public class TsTest {
    private final Date date = new Date();
    @Test
    public void testEquality() {

        Int uncertainty1 = new Int();
        uncertainty1.setNullFlavor(NullFlavor.DER);
        uncertainty1.setOriginalText(new EdText());
        uncertainty1.setValue(111);
        uncertainty1.setUncertaintyType(UncertaintyType.F);

        Ts first = new Ts();
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
        first.setUncertainty(uncertainty1);
        first.setValue(date);


        assertTrue(first.equals(first));
        assertFalse(first.equals(null));

        Int uncertainty2 = new Int();
        uncertainty2.setNullFlavor(NullFlavor.DER);
        uncertainty2.setOriginalText(new EdText());
        uncertainty2.setValue(111);
        uncertainty2.setUncertaintyType(UncertaintyType.F);

        Ts second = new Ts();
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
        second.setUncertainty(uncertainty2);
        second.setValue(date);

        assertTrue(first.equals(second));

        second.setValue(new Date());

        assertFalse(first.equals(second));

       }

       @Test
       public void testHashCode() throws ParseException  {

           Int uncertainty1 = new Int();
           uncertainty1.setNullFlavor(NullFlavor.DER);
           uncertainty1.setOriginalText(new EdText());
           uncertainty1.setValue(111);
           uncertainty1.setUncertaintyType(UncertaintyType.F);

           Ts first = new Ts();
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
           first.setUncertainty(uncertainty1);
           first.setValue(date);

           Int uncertainty2 = new Int();
           uncertainty2.setNullFlavor(NullFlavor.DER);
           uncertainty2.setOriginalText(new EdText());
           uncertainty2.setValue(111);
           uncertainty2.setUncertaintyType(UncertaintyType.F);

           Ts second = new Ts();
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
           second.setUncertainty(uncertainty2);
           second.setValue(date);

           assertEquals(first.hashCode(), second.hashCode());
           SimpleDateFormat sdf = new SimpleDateFormat("MM/DD/yyyy");
           second.setValue(sdf.parse("09/28/1980"));
           assertFalse(first.getValue().getTime() == second.getValue().getTime());
           assertFalse(first.hashCode() == second.hashCode());

       }

       @Test
       public void testCloneable() throws CloneNotSupportedException {
           Int uncertainty1 = new Int();
           uncertainty1.setNullFlavor(NullFlavor.DER);
           uncertainty1.setOriginalText(new EdText());
           uncertainty1.setValue(111);
           uncertainty1.setUncertaintyType(UncertaintyType.F);

           Ts first = new Ts();
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
           first.setUncertainty(uncertainty1);
           first.setValue(date);

           Ts second = (Ts) first.clone();

           assertTrue(first != second);
           assertTrue(first.equals(second));
           assertEquals(first.hashCode(), second.hashCode());

       }

}
