package gov.nih.nci.po.data.convert;

import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.NullFlavor;
import gov.nih.nci.coppa.iso.Tel;
import gov.nih.nci.coppa.iso.TelEmail;
import gov.nih.nci.coppa.iso.TelPhone;
import gov.nih.nci.coppa.iso.TelUrl;
import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.PhoneNumber;
import gov.nih.nci.po.data.bo.URL;
import java.net.URI;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gax
 */
public class TelDSetConverterTest {

    private List<Email> email = new ArrayList<Email>();
    private List<PhoneNumber> fax = new ArrayList<PhoneNumber>();
    private List<PhoneNumber> phone = new ArrayList<PhoneNumber>();
    private List<URL> url = new ArrayList<URL>();
    private List<PhoneNumber> text = new ArrayList<PhoneNumber>();
    
    @Before public void setup() {
        email.clear();
        fax.clear();
        phone.clear();
        url.clear();
        text.clear();    
    }
    
    @Test
    public void testConvertToContactList() {
        DSet<Tel> value = new DSet<Tel>();
        Set<Tel> set = new HashSet<Tel>();
        value.setItem(set);
        
        Tel t = new TelEmail();
        t.setValue(URI.create("mailto:foo"));
        set.add(t);

        t = new TelPhone();
        t.setValue(URI.create("x-text-fax:foo"));
        set.add(t);

        t = new TelPhone();
        t.setValue(URI.create("tel:foo"));
        set.add(t);

        t = new TelUrl();
        t.setValue(URI.create("file:foo"));
        set.add(t);

        t = new TelPhone();
        t.setValue(URI.create("x-text-tel:foo"));
        set.add(t);

        t = new TelPhone();
        t.setNullFlavor(NullFlavor.UNK);
        set.add(t);

        TelDSetConverter.convertToContactList(value, email, fax, phone, url, text);
        
        assertEquals(1, email.size());
        assertEquals(1, fax.size());
        assertEquals(1, phone.size());
        assertEquals(1, url.size());
        assertEquals(1, text.size());
        
        assertEquals("foo", email.get(0).getValue().toString());
        assertEquals("foo", fax.get(0).getValue().toString());
        assertEquals("foo", phone.get(0).getValue().toString());
        assertEquals("file:foo", url.get(0).getValue().toString());
        assertEquals("foo", text.get(0).getValue().toString());
    }

    
}