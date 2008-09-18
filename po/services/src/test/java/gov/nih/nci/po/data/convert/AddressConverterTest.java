
package gov.nih.nci.po.data.convert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import gov.nih.nci.coppa.iso.Ad;
import gov.nih.nci.coppa.iso.Adxp;
import gov.nih.nci.coppa.iso.AdxpAdl;
import gov.nih.nci.coppa.iso.AdxpAl;
import gov.nih.nci.coppa.iso.AdxpCnt;
import gov.nih.nci.coppa.iso.AdxpCty;
import gov.nih.nci.coppa.iso.AdxpSta;
import gov.nih.nci.coppa.iso.AdxpZip;
import gov.nih.nci.coppa.iso.NullFlavor;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.Country;

import java.util.List;

import org.junit.Test;

/**
 *
 * @author gax
 */
public class AddressConverterTest {

    @Test(expected = UnsupportedOperationException.class)
    public void testConvert() {
        AddressConverter.SimpleConverter instance = new AddressConverter.SimpleConverter();
        instance.convert(Integer.class, null);
    }

    @Test
    public void testConvertToAd() {
        Address addr = null;
        Ad iso = AddressConverter.SimpleConverter.convertToAd(addr);
        assertEquals(NullFlavor.NI, iso.getNullFlavor());

        Country country = new Country("name", "numeric", "a2", "USA");
        addr = new Address("streetAddressLine", "cityOrMunicipality", "stateOrProvince", "postalCode", country);

        iso = AddressConverter.SimpleConverter.convertToAd(addr);
        List<Adxp> parts = iso.getPart();
        assertEquals(5, parts.size());
        for(Adxp a : parts) {
            if (a instanceof AdxpCnt) { assertEquals("USA", a.getCode());}
            else if (a instanceof AdxpZip) { assertEquals("postalCode", a.getValue());}
            else if (a instanceof AdxpSta) { assertEquals("stateOrProvince", a.getValue());}
            else if (a instanceof AdxpCty) { assertEquals("cityOrMunicipality", a.getValue());}
            else if (a instanceof AdxpAl) { assertEquals("streetAddressLine", a.getValue());} else {
                fail(a.getClass().getName());
            }
        }


        addr.setDeliveryAddressLine("deliveryAddressLine");
        iso = AddressConverter.SimpleConverter.convertToAd(addr);
        parts = iso.getPart();
        assertEquals(6, parts.size());
        for(Adxp a : parts) {
            if (a instanceof AdxpCnt) { assertEquals("USA", a.getCode());}
            else if (a instanceof AdxpZip) { assertEquals("postalCode", a.getValue());}
            else if (a instanceof AdxpSta) { assertEquals("stateOrProvince", a.getValue());}
            else if (a instanceof AdxpCty) { assertEquals("cityOrMunicipality", a.getValue());}
            else if (a instanceof AdxpAdl) { assertEquals("deliveryAddressLine", a.getValue());}
            else if (a instanceof AdxpAl) { assertEquals("streetAddressLine", a.getValue());} else {
                fail(a.getClass().getName());
            }
        }
    }

}