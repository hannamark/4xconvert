package gov.nih.nci.po.data.convert;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.Ivl;
import gov.nih.nci.coppa.iso.NullFlavor;
import gov.nih.nci.coppa.iso.Tel;
import gov.nih.nci.coppa.iso.TelEmail;
import gov.nih.nci.coppa.iso.TelPhone;
import gov.nih.nci.coppa.iso.TelUrl;
import gov.nih.nci.coppa.iso.TelecommunicationAddressUse;
import gov.nih.nci.coppa.iso.Ts;
import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.PhoneNumber;
import gov.nih.nci.po.data.bo.URL;
import gov.nih.nci.services.PoIsoConstraintException;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Test(expected = PoIsoConstraintException.class)
    public void testFlavorId() {
        DSet<Tel> value = new DSet<Tel>();
        Set<Tel> set = new HashSet<Tel>();
        value.setItem(set);

        Tel t = new TelEmail();
        t.setValue(URI.create("mailto:foo"));
        set.add(t);
        t.setFlavorId("flavorId");
        TelDSetConverter.convertToContactList(value, email, fax, phone, url, text);
    }

    /**
     * Verify that adding items in to the USE set causes an error.
     */
    @Test(expected = PoIsoConstraintException.class)
    public void testUseThrowsError() {
        DSet<Tel> value = new DSet<Tel>();
        Set<Tel> set = new HashSet<Tel>();
        value.setItem(set);

        Tel t = new TelEmail();
        t.setValue(URI.create("mailto:foo"));
        HashSet<TelecommunicationAddressUse> uses = new HashSet<TelecommunicationAddressUse>();
        uses.add(TelecommunicationAddressUse.AS);
        t.setUse(uses);
        set.add(t);

        TelDSetConverter.convertToContactList(value, email, fax, phone, url, text);
    }

    /**
     * Verify that adding items in to the USE set causes an error.
     */
    @Test(expected = PoIsoConstraintException.class)
    public void testUsablePeriodThrowsError() {
        DSet<Tel> value = new DSet<Tel>();
        Set<Tel> set = new HashSet<Tel>();
        value.setItem(set);

        Tel t = new TelEmail();
        t.setValue(URI.create("mailto:foo"));
        t.setUseablePeriod(new Ivl<Ts>());
        set.add(t);

        TelDSetConverter.convertToContactList(value, email, fax, phone, url, text);
    }
}