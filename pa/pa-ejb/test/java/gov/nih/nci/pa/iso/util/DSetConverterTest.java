package gov.nih.nci.pa.iso.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.TelPhone;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class DSetConverterTest {

    @Test
    public void convertPhoneListToDSetTest () {
        List<String> phones = new ArrayList<String>();
        phones.add("7037071111");
        phones.add("7037071112");
        phones.add("7037071113");
        DSet<TelPhone> dset = DSetConverter.convertPhoneListToDSet(phones);
        assertNotNull(dset);
        for (TelPhone t : dset.getItem()) {
            assertTrue(phones.contains(t.getValue().getSchemeSpecificPart()));
        }
    }
    
    @Test
    public void convertDsetPhoneToList(){
        List<String> in = new ArrayList<String>();
        in.add("7037071111");
        in.add("7037071112");
        in.add("7037071113");
        DSet<TelPhone> dset = DSetConverter.convertPhoneListToDSet(in);
        
        List<String> out = DSetConverter.convertDsetPhoneToList(dset);
        assertNotNull(out);
        assertEquals(in.size(),out.size());
        
    }
    

}
