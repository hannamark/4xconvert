
package gov.nih.nci.po.data.convert;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.coppa.iso.EnOn;
import gov.nih.nci.coppa.iso.EntityNamePartQualifier;
import gov.nih.nci.coppa.iso.EntityNamePartType;
import gov.nih.nci.coppa.iso.Enxp;

import org.junit.Test;
import java.util.Collections;

/**
 *
 * @author gax
 */
public class EnConverterTest {


    @Test(expected=UnsupportedOperationException.class)
    public void testConvert() {
        Class<Long> returnClass = Long.class;
        EnConverter<EnOn> instance = new EnConverter<EnOn>();
        instance.transform(returnClass, null, null, null);
    }

    @Test
    public void testConvertToString() {
        EnOn iso = new EnOn();
        Enxp p = new Enxp(null);
        p.setValue("5AM Solutions, ");
        iso.getPart().add(p);
        p = new Enxp(EntityNamePartType.SFX);// no effect
        p.setValue("Inc");
        p.setQualifier(Collections.singleton(EntityNamePartQualifier.LS));// no effect
        iso.getPart().add(p);

        EnConverter<EnOn> instance = new EnConverter<EnOn>();
        String expResult = "5AM Solutions, Inc";
        String result = instance.convertToString(iso);
        assertEquals(expResult, result);
    }

}