
package gov.nih.nci.po.data.convert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import gov.nih.nci.coppa.iso.Ad;
import gov.nih.nci.coppa.iso.AddressPartType;
import gov.nih.nci.coppa.iso.Adxp;
import gov.nih.nci.coppa.iso.NullFlavor;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.util.MockCountryServiceLocator;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.util.ServiceLocator;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author gax
 */
public class AdConverterTest {

    ServiceLocator oldLocator = null;

    @Before
    public void setUpTest() {
        oldLocator = PoRegistry.getInstance().getServiceLocator();
        PoRegistry.getInstance().setServiceLocator(new MockCountryServiceLocator());
    }

    @After
    public void tearDownTest() {
        PoRegistry.getInstance().setServiceLocator(oldLocator);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testConvert() {
        AdConverter.SimpleConverter instance = new AdConverter.SimpleConverter();
        instance.convert(Integer.class, null);
    }


    /*7.7.3.5.5 Unknown Addresses
<example xsi:type="AD" use="WP" nullFlavor="UNK"/>
     */
    @Test
    public void testConvertToAddress() {
        Ad iso = new Ad();
        iso.setNullFlavor(NullFlavor.UNK);
        Address result = AdConverter.SimpleConverter.convertToAddress(iso);
        assertNull(result);
    }

    /*
     * 7.7.3.5.1 Address with Layout
<example xsi:type="AD" use="WP">
   <part>1050 W Wishard Blvd,</part>
   <br/>
   <part>RG 5th floor,</part>
   <br/>
   <part>Indianapolis, IN 46240</part>
</example>
This work address consists of 3 unknown parts with 2 line delimiters. None of the parts are
labelled in regard to their semantic significance.
     *
     *
     * this example will not create a usable address for PO!
     * it is here to illustrate the comvertions limitations.
     * also, there seems to be some confusion about the DEL and &lt;br/&gt;
     */
    @Test
    public void testConvertToAddress1() {
        Ad iso = new Ad();
        List<Adxp> part = new ArrayList<Adxp>();
        iso.setPart(part);
        Adxp a = Adxp.createAddressPart(null);
        a.setValue("1050 W Wishard Blvd,");
        part.add(a);

        part.add(Adxp.createAddressPart(AddressPartType.DEL));

        a = Adxp.createAddressPart(null);
        a.setValue("RG 5th floor,");
        part.add(a);

        part.add(Adxp.createAddressPart(AddressPartType.DEL));

        a = Adxp.createAddressPart(null);
        a.setValue("Indianapolis, IN 46240");
        part.add(a);

        Address result = AdConverter.SimpleConverter.convertToAddress(iso);
        assertNull(result.getPostalCode());
        assertNull(result.getStateOrProvince());
        assertNull(result.getCountry());
        assertEquals("1050 W Wishard Blvd,\nRG 5th floor,\nIndianapolis, IN 46240", result.getStreetAddressLine());
    }

    /*
     * 7.7.3.5.2 Address with Types
<example xsi:type="AD" use="WP">
   <addressLine>1050 W Wishard Blvd</addressLine>
   <addressLine>RG 5th floor</addressLine>
   <city>Indianapolis</city>
   <state>IN</state>
   <postalCode>46240</postalCode>
</example>
This is the same address using standard typing rather than a presentation focus. This is
probably be the most common form of presentation for addresses - a series of address lines
followed by city, state, zip and possibly country.
Note: Although this presentation of the address suggests that lines are required after the two
address lines, this is not implied by this example. See Section 7.7.3.6.
     */
    @Test
    public void testConvertToAddress2() {
        Ad iso = new Ad();
        List<Adxp> part = new ArrayList<Adxp>();
        iso.setPart(part);
        Adxp a = Adxp.createAddressPart(AddressPartType.AL);
        a.setValue("1050 W Wishard Blvd");
        part.add(a);

        a = Adxp.createAddressPart(AddressPartType.AL);
        a.setValue("RG 5th floor");
        part.add(a);

        a = Adxp.createAddressPart(AddressPartType.CTY);
        a.setValue("Indianapolis");
        part.add(a);

        a = Adxp.createAddressPart(AddressPartType.STA);
        a.setValue("IN");
        part.add(a);

        a = Adxp.createAddressPart(AddressPartType.ZIP);
        a.setValue("46240");
        part.add(a);

        a = Adxp.createAddressPart(AddressPartType.CNT);
        a.setCode("USA");
        part.add(a);

        Address result = AdConverter.SimpleConverter.convertToAddress(iso);
        assertEquals("46240", result.getPostalCode());
        assertEquals("IN", result.getStateOrProvince());
        assertEquals("USA", result.getCountry().getAlpha3());

        assertEquals("1050 W Wishard Blvd RG 5th floor", result.getStreetAddressLine());
    }
/*
7.7.3.5.3 Line Types
<example xsi:type="AD" use="WP">
   <streetAddressLine>1050 W Wishard Blvd</streetAddressLine>
   <additionalLocator>RG 5th floor</additionalLocator>
   <city>Indianapolis</city>
   <state>IN</state>
   <postalCode>46240</postalCode>
</example>
This is the same address from a system that differentiates between different line types .
 * */
    @Test
    public void testConvertToAddress3() {
        Ad iso = new Ad();
        List<Adxp> part = new ArrayList<Adxp>();
        iso.setPart(part);
        Adxp a = Adxp.createAddressPart(AddressPartType.SAL);
        a.setValue("1050 W Wishard Blvd");
        part.add(a);

        a = Adxp.createAddressPart(AddressPartType.ADL);
        a.setValue("RG 5th floor");
        part.add(a);

        a = Adxp.createAddressPart(AddressPartType.CTY);
        a.setValue("Indianapolis");
        part.add(a);

        a = Adxp.createAddressPart(AddressPartType.STA);
        a.setValue("IN");
        part.add(a);

        a = Adxp.createAddressPart(AddressPartType.ZIP);
        a.setValue("46240");
        part.add(a);

        a = Adxp.createAddressPart(AddressPartType.CNT);
        a.setCode("USA");
        part.add(a);

        Address result = AdConverter.SimpleConverter.convertToAddress(iso);
        assertEquals("46240", result.getPostalCode());
        assertEquals("IN", result.getStateOrProvince());
        assertEquals("USA", result.getCountry().getAlpha3());

        assertEquals("1050 W Wishard Blvd", result.getStreetAddressLine());
        assertEquals("RG 5th floor", result.getDeliveryAddressLine());

    }
    /*
7.7.3.5.4 Fully Typed Addresses
<example xsi:type="AD" use="WP">
   <houseNumber>1050</houseNumber>
   <direction>W</direction>
   <streetNameBase>Wishard</streetNameBase>
   <streetType>Blvd</streetType>
   <additionalLocator>RG 5th floor</additionalLocator>
   <city>Indianapolis</city>
   <state>IN</state>
   <postalCode>46240</postalCode>
</example>
The same address fully broken down. The form above is not used in the USA. However, it is
useful in Germany, where many systems keep house number as a distinct field
 */
    @Test
    public void testConvertToAddress4() {
        Ad iso = new Ad();
        List<Adxp> part = new ArrayList<Adxp>();
        iso.setPart(part);
        Adxp a = Adxp.createAddressPart(AddressPartType.BNR);
        a.setValue("1050");
        part.add(a);
        a = Adxp.createAddressPart(AddressPartType.DIR);
        a.setValue("W");
        part.add(a);
        a = Adxp.createAddressPart(AddressPartType.STB);
        a.setValue("Wishard");
        part.add(a);
        a = Adxp.createAddressPart(AddressPartType.STTYP);
        a.setValue("Blvd");
        part.add(a);

        a = Adxp.createAddressPart(AddressPartType.ADL);
        a.setValue("RG 5th floor");
        part.add(a);

        a = Adxp.createAddressPart(AddressPartType.CTY);
        a.setValue("Indianapolis");
        part.add(a);

        a = Adxp.createAddressPart(AddressPartType.STA);
        a.setValue("IN");
        part.add(a);

        a = Adxp.createAddressPart(AddressPartType.ZIP);
        a.setValue("46240");
        part.add(a);

        a = Adxp.createAddressPart(AddressPartType.CNT);
        a.setCode("USA");
        part.add(a);

        Address result = AdConverter.SimpleConverter.convertToAddress(iso);
        assertEquals("46240", result.getPostalCode());
        assertEquals("IN", result.getStateOrProvince());
        assertEquals("USA", result.getCountry().getAlpha3());

        assertEquals("1050 W Wishard Blvd", result.getStreetAddressLine());
        assertEquals("RG 5th floor", result.getDeliveryAddressLine());
    }

    @Test
    public void testConvertToAddressDelivery() {
        Ad iso = new Ad();
        List<Adxp> part = new ArrayList<Adxp>();
        iso.setPart(part);

        Adxp a = Adxp.createAddressPart(AddressPartType.POB);
        a.setValue("909");
        part.add(a);

        a = Adxp.createAddressPart(AddressPartType.CTY);
        a.setValue("Indianapolis");
        part.add(a);

        a = Adxp.createAddressPart(AddressPartType.STA);
        a.setValue("IN");
        part.add(a);

        a = Adxp.createAddressPart(AddressPartType.ZIP);
        a.setValue("46240");
        part.add(a);

        a = Adxp.createAddressPart(AddressPartType.CNT);
        a.setCode("USA");
        part.add(a);

        Address result = AdConverter.SimpleConverter.convertToAddress(iso);
        assertEquals("46240", result.getPostalCode());
        assertEquals("IN", result.getStateOrProvince());
        assertEquals("USA", result.getCountry().getAlpha3());

        assertEquals("P.O.Box 909", result.getStreetAddressLine());
    }
}