package gov.nih.nci.po.util;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.Contact;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.Person;

import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.junit.Assert;


/**
 *
 * @author gax
 */
public class EqualsByValue {

    /**
     * Compare two entities.
     */
    public static boolean equals(Organization a, Organization b) {
        if (a == b) { return true; }
        if ((a == null && b != null) || (a != null && b == null)) { return false; }
        EqualsBuilder eq = new EqualsBuilder()
                .append(a.getName(), b.getName())
                .append(a.getStatusCode(), b.getStatusCode())
                .append(a.getDuplicateOf(), b.getDuplicateOf())
                .append(a.getPriorEntityStatus(), b.getPriorEntityStatus());

        eq.appendSuper(equals(a.getEmail(), b.getEmail()))
                .appendSuper(equals(a.getFax(), b.getFax()))
                .appendSuper(equals(a.getPhone(), b.getPhone()))
                .appendSuper(equals(a.getTty(), b.getTty()))
                .appendSuper(equals(a.getUrl(), b.getUrl()));

        append(eq, a.getPostalAddress(), b.getPostalAddress());

        return eq.isEquals();
    }

    public static boolean equals(Person a, Person b) {
        if (a == b) { return true; }
        if ((a == null && b != null) || (a != null && b == null)) { return false; }
        EqualsBuilder eq = new EqualsBuilder()
                .append(a.getFirstName(), b.getFirstName())
                .append(a.getLastName(), b.getLastName())
                .append(a.getMiddleName(), b.getMiddleName())
                .append(a.getPrefix(), b.getPrefix())
                .append(a.getSuffix(), b.getSuffix())

                .append(a.getStatusCode(), b.getStatusCode())
                .append(a.getDuplicateOf(), b.getDuplicateOf())
                .append(a.getPriorEntityStatus(), b.getPriorEntityStatus());

        eq.appendSuper(equals(a.getEmail(), b.getEmail()))
                .appendSuper(equals(a.getFax(), b.getFax()))
                .appendSuper(equals(a.getPhone(), b.getPhone()))
                .appendSuper(equals(a.getTty(), b.getTty()))
                .appendSuper(equals(a.getUrl(), b.getUrl()));

        append(eq, a.getPostalAddress(), b.getPostalAddress());

        return eq.isEquals();
    }

    public static <C extends Contact> boolean equals(List<C> a, List<C> b) {
        if (a == b) { return true; }
        if ((a == null && b != null) || (a != null && b == null)) { return false; }
        if (a.size() != b.size()) { return false; }
        for (int i=0; i< a.size(); i++) {
            if (!ObjectUtils.equals(a.get(i).getValue(), b.get(i).getValue())) { return false; }
        }
        return true;
    }

    public static boolean equals(Address a, Address b) {
        return append(new EqualsBuilder(), a, b).isEquals();
    }

    private static EqualsBuilder append(EqualsBuilder eq, Address a, Address b) {
        if (a == b) { return eq; }
        if ((a == null && b != null) || (a != null && b == null)) { return eq.appendSuper(false); }
        eq.append(a.getStreetAddressLine(), b.getStreetAddressLine())
                .append(a.getDeliveryAddressLine(), b.getDeliveryAddressLine())
                .append(a.getCityOrMunicipality(), b.getCityOrMunicipality())
                .append(a.getStateOrProvince(), b.getStateOrProvince())
                .append(a.getPostalCode(), b.getPostalCode());
        eq.appendSuper(
                ObjectUtils.equals(a.getCountry(), b.getCountry())
                || ObjectUtils.equals(a.getCountry().getId(), b.getCountry().getId()));
        return eq;

    }

    public static void assertEquals(String prefix, Address a, Address b) {
        if (a == b) { return; }
        if ((a == null && b != null) || (a != null && b == null)) { Assert.fail("one is null"); }
        Assert.assertEquals(prefix+".streetAddressLine", a.getStreetAddressLine(), b.getStreetAddressLine());
        Assert.assertEquals(prefix+".deliveryAddressLine", a.getDeliveryAddressLine(), b.getDeliveryAddressLine());
        Assert.assertEquals(prefix+".cityOrMunicipality", a.getCityOrMunicipality(), b.getCityOrMunicipality());
        Assert.assertEquals(prefix+".stateOrProvince", a.getStateOrProvince(), b.getStateOrProvince());
        Assert.assertEquals(prefix+".postalCode", a.getPostalCode(), b.getPostalCode());
        Assert.assertTrue(prefix+".country", a.getCountry() == b.getCountry() || ObjectUtils.equals(a.getCountry().getId(), b.getCountry().getId()));
    }

    public static <C extends Contact> void assertEquals(String prefix, List<C> a, List<C> b) {
        if (a == b) { return; }
        if ((a == null && b != null) || (a != null && b == null)) { Assert.fail("one is null"); }
        Assert.assertEquals("list size",  a.size(), b.size());
        for (int i = 0; i< a.size(); i++) {
            Assert.assertEquals(prefix+"["+i+"]", a.get(i).getValue(), b.get(i).getValue());
        }
    }

    public static void assertEquals(Organization a, Organization b) {
        if (a == b) { return; }
        if ((a == null && b != null) || (a != null && b == null)) { Assert.fail("one is null"); }

        Assert.assertEquals("name", a.getName(), b.getName());
        Assert.assertEquals("statusCode", a.getStatusCode(), b.getStatusCode());
        Assert.assertEquals("duplicateOf", a.getDuplicateOf(), b.getDuplicateOf());
        Assert.assertEquals("priorEntityStatus", a.getPriorEntityStatus(), b.getPriorEntityStatus());

        assertEquals("email", a.getEmail(), b.getEmail());
        assertEquals("fax", a.getFax(), b.getFax());
        assertEquals("phone", a.getPhone(), b.getPhone());
        assertEquals("tty", a.getTty(), b.getTty());
        assertEquals("url", a.getUrl(), b.getUrl());

        assertEquals("postalAddress", a.getPostalAddress(), b.getPostalAddress());
    }

    public static void assertEquals(Person a, Person b) {
        if (a == b) { return; }
        if ((a == null && b != null) || (a != null && b == null)) { Assert.fail("one is null"); }

        Assert.assertEquals("firstName", a.getFirstName(), b.getFirstName());
        Assert.assertEquals("lastName", a.getLastName(), b.getLastName());
        Assert.assertEquals("middleName", a.getMiddleName(), b.getMiddleName());
        Assert.assertEquals("prefix", a.getPrefix(), b.getPrefix());
        Assert.assertEquals("suffix", a.getSuffix(), b.getSuffix());

        Assert.assertEquals("statusCode", a.getStatusCode(), b.getStatusCode());
        Assert.assertEquals("duplicateOf", a.getDuplicateOf(), b.getDuplicateOf());
        Assert.assertEquals("priorEntityStatus", a.getPriorEntityStatus(), b.getPriorEntityStatus());

        assertEquals("email", a.getEmail(), b.getEmail());
        assertEquals("fax", a.getFax(), b.getFax());
        assertEquals("phone", a.getPhone(), b.getPhone());
        assertEquals("tty", a.getTty(), b.getTty());
        assertEquals("url", a.getUrl(), b.getUrl());

        assertEquals("postalAddress", a.getPostalAddress(), b.getPostalAddress());
    }

    public static void assertEquals(Ii a, Ii b) {
        if (a == b) { return; }
        if ((a == null && b != null) || (a != null && b == null)) { Assert.fail("one is null"); }

        Assert.assertEquals(a.getDisplayable(), b.getDisplayable());
        Assert.assertEquals(a.getExtension(), b.getExtension());
        Assert.assertEquals(a.getIdentifierName(), b.getIdentifierName());
        Assert.assertEquals(a.getNullFlavor(), b.getNullFlavor());
        Assert.assertEquals(a.getReliability(), b.getReliability());
        Assert.assertEquals(a.getRoot(), b.getRoot());
        Assert.assertEquals(a.getScope(), b.getScope());
    }
}
