package gov.nih.nci.po.web.mailable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.ClinicalResearchStaff;
import gov.nih.nci.po.data.bo.Country;
import gov.nih.nci.po.web.AbstractPoTest;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.Action;


public class ManageMailableActionTest extends AbstractPoTest {
    private ManageMailableAction action;

    @Before
    public void setUp() throws Exception {
        action = new ManageMailableAction();
        assertNotNull(action.getAddress());

        String rootKey = "abc";
        getSession().setAttribute(rootKey, new ClinicalResearchStaff());
        action.setRootKey(rootKey);
        action.prepare();
    }

    @Test
    public void testUsFormat() {

        assertFalse(action.isUsOrCanadaFormat());
        Address addr1 = new Address("defaultStreetAddress", "cityOrMunicipality", "defaultState", "12345",
                new Country("United States", "840", "US", "USA"));
        action.getMailable().getPostalAddresses().add(addr1);
        assertTrue(action.isUsOrCanadaFormat());
        Address addr2 = new Address("defaultStreetAddress", "cityOrMunicipality", "defaultState", "12345",
                new Country("Tazmania", "999", "TZ", "TAZ"));
        action.getMailable().getPostalAddresses().add(addr2);
        assertFalse(action.isUsOrCanadaFormat());
    }


    @Test
    public void testPrepare_AddressLoadedByIndex() throws Exception {
        Address tmp = action.getAddress();
        action.setIndex(null);
        action.prepare();
        assertSame(tmp, action.getAddress());

        action.setIndex(-1);
        action.prepare();
        assertSame(tmp, action.getAddress());

        action.setIndex(0);
        action.prepare();
        assertSame(tmp, action.getAddress());


        action.getAddress().setStreetAddressLine("street1");
        action.add();
        action.getAddress().setStreetAddressLine("street2");
        action.add();
        action.getAddress().setStreetAddressLine("street3");
        action.add();
        assertEquals(3, action.getMailable().getPostalAddresses().size());

        //set the tmp vars based on iterating the HashSet
        Iterator<Address> iterator = action.getMailable().getPostalAddresses().iterator();
        Address tmp1 = iterator.next();
        Address tmp2 = iterator.next();
        Address tmp3 = iterator.next();

        action.setIndex(0);
        action.prepare();
        assertSame(tmp1, action.getAddress());

        action.setIndex(1);
        action.prepare();
        assertSame(tmp2, action.getAddress());

        action.setIndex(2);
        action.prepare();
        assertSame(tmp3, action.getAddress());
    }

    @Test
    public void testRootKeyProperty() {
        assertNotNull(action.getRootKey());
        action.setRootKey(null);
        assertNull(action.getRootKey());
        action.setRootKey("abc-123");
        assertNotNull(action.getRootKey());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidRootKeyProperty() {
        assertNotNull(action.getRootKey());
        action.setRootKey("abc-321");
    }

    @Test
    public void testReadonlyProperty() {
        assertFalse(action.isReadonly());
        action.setReadonly(true);
        assertTrue(action.isReadonly());
    }

    @Test
    public void testAddresses() {
        assertEquals(Action.SUCCESS, action.addresses());
    }

    @Test
    public void testAddAddress() {
        action.getAddress().setStreetAddressLine("street1");
        assertEquals("input", action.add());
        assertEquals(1, action.getMailable().getPostalAddresses().size());
        assertNotSame(action.getAddress(), action.getMailable().getPostalAddresses().iterator().next());
    }

    @Test
    public void testRemoveAddress() {

        assertEquals(0, action.getMailable().getPostalAddresses().size());
        assertEquals(Action.SUCCESS, action.remove());
        assertEquals(0, action.getMailable().getPostalAddresses().size());

        action.getAddress().setStreetAddressLine("street1");
        action.add();
        assertEquals(1, action.getMailable().getPostalAddresses().size());

        action.setIndex(null);
        assertEquals(Action.SUCCESS, action.remove());
        assertEquals(1, action.getMailable().getPostalAddresses().size());

        action.setIndex(-1);
        assertEquals(Action.SUCCESS, action.remove());
        assertEquals(1, action.getMailable().getPostalAddresses().size());

        action.setIndex(1);
        assertEquals(Action.SUCCESS, action.remove());
        assertEquals(1, action.getMailable().getPostalAddresses().size());

        action.setIndex(0);
        assertEquals(Action.SUCCESS, action.remove());
        assertEquals(0, action.getMailable().getPostalAddresses().size());
    }

    @Test
    public void testEdit() {
        assertEquals(Action.INPUT, action.edit());
    }

    @Test
    public void testAddressProperty() {
        assertNotNull(action.getAddress());
        action.setAddress(null);
        assertNull(action.getAddress());
    }

    @Test
    public void testAddressIndexProperty() {
        assertNotNull(action.getIndex());
        action.setIndex(null);
        assertNull(action.getIndex());
    }

    @Test
    public void testInputAddress() {
        assertEquals(Action.INPUT, action.input());
    }
}
