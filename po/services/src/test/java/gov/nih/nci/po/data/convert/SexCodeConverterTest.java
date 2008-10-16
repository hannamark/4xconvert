package gov.nih.nci.po.data.convert;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.NullFlavor;
import gov.nih.nci.po.data.bo.SexCode;
import gov.nih.nci.services.PoIsoConstraintException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gax
 */
public class SexCodeConverterTest {

    @Test
    public void testConvertToSexCode() {
        Cd cd = new Cd();
        for (SexCode sc : SexCode.values()) {
            cd.setCode(sc.name());
            SexCode result = SexCodeConverter.convertToSexCode(cd);
            assertEquals(sc, result);
        }
    }

    @Test
    public void testConvertToCd() {
        for (SexCode sc : SexCode.values()) {
            Cd result = SexCodeConverter.convertToCd(sc);
            assertEquals(sc.name(), result.getCode());
        }
    }
    
    @Test
    public void testCdConverter() {
        SexCodeConverter.CdConverter c = new SexCodeConverter.CdConverter();
        
        assertNull(c.convert(SexCode.class, null));
        
        Cd cd = new Cd();
        cd.setNullFlavor(NullFlavor.NA);
        assertNull(c.convert(SexCode.class, cd));
        
        cd.setNullFlavor(null);
        try {
            c.convert(SexCode.class, cd);
            fail();
        } catch(PoIsoConstraintException ex) {
        }
        
        cd.setCode("");
        try {
            c.convert(SexCode.class, cd);
            fail();
        } catch(PoIsoConstraintException ex) {
        }
        
        cd.setCode(SexCode.F.name());
        try {
            c.convert(Thread.class, cd);
            fail();
        } catch(UnsupportedOperationException ex) {
        }
    }
    
    @Test
    public void testEnumConverter() {
        SexCodeConverter.EnumConverter c = new SexCodeConverter.EnumConverter();
        
        Cd result = c.convert(Cd.class, null);
        assertNotNull(result.getNullFlavor());
        
        try {
            c.convert(Thread.class, SexCode.M);
            fail();
        } catch(UnsupportedOperationException ex) {
        }
        
    }

}