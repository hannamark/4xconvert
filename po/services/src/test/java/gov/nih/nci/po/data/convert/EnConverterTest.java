
package gov.nih.nci.po.data.convert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gov.nih.nci.iso21090.EnOn;
import gov.nih.nci.iso21090.EntityNamePartQualifier;
import gov.nih.nci.iso21090.EntityNamePartType;
import gov.nih.nci.iso21090.Enxp;
import gov.nih.nci.iso21090.NullFlavor;
import gov.nih.nci.services.PoIsoConstraintException;

import java.util.Collections;

import org.junit.Test;

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
        EnConverter<EnOn> instance = new EnConverter<EnOn>();
        try{
            instance.convertToString(iso);
            fail();
        }catch (PoIsoConstraintException ex) {
            // expected
        }

        Enxp p = new Enxp(null);
        p.setValue("5AM Solutions, ");
        iso.getPart().add(p);
        p = new Enxp(EntityNamePartType.SFX);// no effect
        p.setValue("Inc");
        p.setQualifier(Collections.singleton(EntityNamePartQualifier.LS));// no effect
        iso.getPart().add(p);

        String expResult = "5AM Solutions, Inc";
        String result = instance.convertToString(iso);
        assertEquals(expResult, result);
    }

    @Test
    public void testValidation() {
        EnOn iso = new EnOn();
        EnConverter<EnOn> instance = new EnConverter<EnOn>();
        try{
            instance.convertToString(iso);
            fail();
        }catch(PoIsoConstraintException ex){
            assertTrue(ex.getMessage().contains("is null or it has at least one part"));
        }

        iso.setNullFlavor(NullFlavor.OTH);
        Enxp p = new Enxp(null);
        p.setValue("5AM Solutions, ");
        iso.getPart().add(p);
        try{
            instance.convertToString(iso);
            fail();
        }catch(PoIsoConstraintException ex){
            assertTrue(ex.getMessage().contains("is null or it has at least one part"));
        }
    }
}