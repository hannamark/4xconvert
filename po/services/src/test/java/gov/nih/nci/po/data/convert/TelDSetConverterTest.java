package gov.nih.nci.po.data.convert;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.NullFlavor;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.iso21090.TelEmail;
import gov.nih.nci.iso21090.TelPhone;
import gov.nih.nci.iso21090.TelUrl;
import gov.nih.nci.po.data.bo.Contact;
import gov.nih.nci.po.data.bo.Contactable;
import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.PhoneNumber;
import gov.nih.nci.po.data.bo.URL;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author gax
 */
public class TelDSetConverterTest {

    private final List<Email> email = new ArrayList<Email>();
    private final List<PhoneNumber> fax = new ArrayList<PhoneNumber>();
    private final List<PhoneNumber> phone = new ArrayList<PhoneNumber>();
    private final List<URL> url = new ArrayList<URL>();
    private final List<PhoneNumber> text = new ArrayList<PhoneNumber>();
    private final Contactable c = new Contactable() {
        public List<Email> getEmail() {
            return email;
        }

        public List<PhoneNumber> getFax() {
            return fax;
        }

        public List<PhoneNumber> getPhone() {
            return phone;
        }

        public List<PhoneNumber> getTty() {
            return text;
        }

        public List<URL> getUrl() {
            return url;
        }
    };

    @Before
    public void setup() {
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

        TelDSetConverter.convertToContactList(value, c);

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

    @Test
    public void testConvertToContactListIgnoreCase() {
        DSet<Tel> value = new DSet<Tel>();
        Set<Tel> set = new HashSet<Tel>();
        value.setItem(set);
         
        Tel t = new TelEmail();
        t.setValue(URI.create("mailTO:foo"));
        set.add(t);
        
        t = new TelPhone();
        t.setValue(URI.create("x-text-FAX:foo"));
        set.add(t);
        
        t = new TelPhone();
        t.setValue(URI.create("TEL:foo"));
        set.add(t);
        
        t = new TelUrl();
        t.setValue(URI.create("File:foo"));
        set.add(t);
        
        t = new TelPhone();
        t.setValue(URI.create("X-TEXT-TEL:foo"));
        set.add(t);
        
        TelDSetConverter.convertToContactList(value, c);
        
        assertEquals(1, email.size());
        assertEquals(1, fax.size());
        assertEquals(1, phone.size());
        assertEquals(1, url.size());
        assertEquals(1, text.size());
        
        assertEquals("foo", email.get(0).getValue().toString());
        assertEquals("foo", fax.get(0).getValue().toString());
        assertEquals("foo", phone.get(0).getValue().toString());
        assertEquals("File:foo", url.get(0).getValue().toString());
        assertEquals("foo", text.get(0).getValue().toString());
    }

    @Test
    public void sameOrder() {
        Comparator<Tel> tcomp = new Comparator<Tel>(){
                public int compare(Tel o1, Tel o2) {
                    return o1.getValue().compareTo(o2.getValue());
                }
            };

        DSet<Tel> value = new DSet<Tel>();
        Set<Tel> set = new TreeSet<Tel>(tcomp);
        value.setItem(set);
        for (int i = 0; i<10; i++) {
            Tel t = new TelEmail();
            t.setValue(URI.create("mailto:foo"+i));
            set.add(t);

            t = new TelPhone();
            t.setValue(URI.create("x-text-fax:foo"+i));
            set.add(t);

            t = new TelPhone();
            t.setValue(URI.create("tel:foo"+i));
            set.add(t);

            t = new TelUrl();
            t.setValue(URI.create("file:foo"+i));
            set.add(t);

            t = new TelPhone();
            t.setValue(URI.create("x-text-tel:foo"+i));
            set.add(t);
        }

        @SuppressWarnings("unchecked")
        List<List<? extends Contact>> all = Arrays.asList((List<? extends Contact>)email, fax, phone, url, text);
        Comparator<Contact> ccomp = new Comparator<Contact>(){
                public int compare(Contact o1, Contact o2) {
                    return o1.getValue().compareTo(o2.getValue());
                }
            };

        TelDSetConverter.convertToContactList(value, c);

        for(List<? extends Contact> l : all){
            ArrayList<Contact> tmp = new ArrayList<Contact>(l);
            Collections.sort(tmp, ccomp);
            assertEquals(l, tmp);
        }
    }

    @Test
    public void edgeCases() throws Exception {
        Email e = new Email();
        e.setValue("test@example.com");
        email.add(e);

        // proves null value -> no change
        TelDSetConverter.convertToContactList(null, c);
        assertEquals(1, email.size());

        // proves null itmes list -> no change
        DSet<Tel> value = new DSet<Tel>();
        TelDSetConverter.convertToContactList(value, c);
        assertEquals(1, email.size());

        // proves empty items list -> empties all contactable lists
        value.setItem(new HashSet<Tel>());
        TelDSetConverter.convertToContactList(value, c);
        assertEquals(0, email.size());
    }
}