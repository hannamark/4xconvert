
package gov.nih.nci.po.data.convert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import gov.nih.nci.iso21090.Ad;
import gov.nih.nci.iso21090.AddressPartType;
import gov.nih.nci.iso21090.Adxp;
import gov.nih.nci.iso21090.AdxpAdl;
import gov.nih.nci.iso21090.AdxpAl;
import gov.nih.nci.iso21090.AdxpBnn;
import gov.nih.nci.iso21090.AdxpBnr;
import gov.nih.nci.iso21090.AdxpBns;
import gov.nih.nci.iso21090.AdxpCar;
import gov.nih.nci.iso21090.AdxpCen;
import gov.nih.nci.iso21090.AdxpCnt;
import gov.nih.nci.iso21090.AdxpCpa;
import gov.nih.nci.iso21090.AdxpCty;
import gov.nih.nci.iso21090.AdxpDal;
import gov.nih.nci.iso21090.AdxpDel;
import gov.nih.nci.iso21090.AdxpDinst;
import gov.nih.nci.iso21090.AdxpDinsta;
import gov.nih.nci.iso21090.AdxpDinstq;
import gov.nih.nci.iso21090.AdxpDir;
import gov.nih.nci.iso21090.AdxpDmod;
import gov.nih.nci.iso21090.AdxpDmodid;
import gov.nih.nci.iso21090.AdxpInt;
import gov.nih.nci.iso21090.AdxpPob;
import gov.nih.nci.iso21090.AdxpPre;
import gov.nih.nci.iso21090.AdxpSal;
import gov.nih.nci.iso21090.AdxpSta;
import gov.nih.nci.iso21090.AdxpStb;
import gov.nih.nci.iso21090.AdxpStr;
import gov.nih.nci.iso21090.AdxpSttyp;
import gov.nih.nci.iso21090.AdxpUnid;
import gov.nih.nci.iso21090.AdxpUnit;
import gov.nih.nci.iso21090.AdxpZip;
import gov.nih.nci.iso21090.NullFlavor;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.Country;
import gov.nih.nci.po.data.bo.State;
import gov.nih.nci.po.service.AbstractHibernateTestCase;
import gov.nih.nci.po.util.PoHibernateUtil;
import gov.nih.nci.services.PoIsoConstraintException;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author gax
 */
public class AdConverterTest extends AbstractHibernateTestCase {
    private Country c;
    private State state;

    @Before
    public void init() {
        Session s = PoHibernateUtil.getCurrentSession();
        c = new Country("Super Country", "999", "ZZ", "USA");
        s.save(c);

        state = new State();
        state.setCode("IN");
        state.setName("INDIANA");
        state.setCountry(c);
        s.save(state);

        Country c2 = new Country("Super Country2", "888", "AA", "AAA");
        s.save(c2);

        s.flush();
        s.clear();
    }
    @Test(expected = UnsupportedOperationException.class)
    public void testConvert() {
        AdConverter.SimpleConverter instance = new AdConverter.SimpleConverter();
        instance.convert(Integer.class, null);
    }

    /**
     * 7.7.1.6 - The value cannot be empty
     */
    @Test
    public void testAdxpValueIsRequired() {
        for (AddressPartType type : AddressPartType.values()) {
            if (type == AddressPartType.DEL || type == AddressPartType.CNT) {
                continue;
            }
            Ad iso = new Ad();
            List<Adxp> part = new ArrayList<Adxp>();
            iso.setPart(part);
            Adxp adxp = createAddressPart(type);
            adxp.setValue(null);
            part.add(adxp);
            try {
                AdConverter.SimpleConverter.convertToAddress(iso);
                fail();
            } catch (PoIsoConstraintException e) {
                assertEquals("Adxp.value is required", e.getMessage());
            }

            adxp.setValue("");
            try {
                AdConverter.SimpleConverter.convertToAddress(iso);
                fail();
            } catch (PoIsoConstraintException e) {
                assertEquals("Adxp.value is required", e.getMessage());
            }
        }
    }
    
    /**
     * 7.7.1.3.3 - The value cannot be empty
     */
    @Test
    public void testAdxpCodeSystemIsRequired() {
        for (AddressPartType type : AddressPartType.values()) {
            if (type != AddressPartType.CNT) {
                continue;
            }
            Ad iso = new Ad();
            List<Adxp> part = new ArrayList<Adxp>();
            iso.setPart(part);
            Adxp adxp = createAddressPart(type);
            adxp.setValue(null);
            adxp.setCode("USA");
            adxp.setCodeSystem(null);
            part.add(adxp);
            try {
                AdConverter.SimpleConverter.convertToAddress(iso);
                fail();
            } catch (PoIsoConstraintException e) {
                assertEquals("Adxp.codeSystem is required", e.getMessage());
            }

            adxp.setValue("");
            try {
                AdConverter.SimpleConverter.convertToAddress(iso);
                fail();
            } catch (PoIsoConstraintException e) {
                assertEquals("Adxp.codeSystem is required", e.getMessage());
            }
        }
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
        Adxp a = new Adxp();
        a.setValue("1050 W Wishard Blvd,");
        part.add(a);

        part.add(createAddressPart(AddressPartType.DEL));

        a = new Adxp();
        a.setValue("RG 5th floor,");
        part.add(a);

        part.add(createAddressPart(AddressPartType.DEL));

        a = new Adxp();
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
        Adxp a = createAddressPart(AddressPartType.AL);
        a.setValue("1050 W Wishard Blvd");
        part.add(a);

        a = createAddressPart(AddressPartType.AL);
        a.setValue("RG 5th floor");
        part.add(a);

        a = createAddressPart(AddressPartType.CTY);
        a.setValue("Indianapolis");
        part.add(a);

        a = createAddressPart(AddressPartType.STA);
        a.setValue("IN");
        part.add(a);

        a = createAddressPart(AddressPartType.ZIP);
        a.setValue("46240");
        part.add(a);

        a = createAddressPart(AddressPartType.CNT);
        a.setValue("adxp.value required");
        a.setCode("USA");
        a.setCodeSystem("ISO 3166-1 alpha-3 code");
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
        Adxp a = createAddressPart(AddressPartType.SAL);
        a.setValue("1050 W Wishard Blvd");
        part.add(a);

        a = createAddressPart(AddressPartType.ADL);
        a.setValue("RG 5th floor");
        part.add(a);

        a = createAddressPart(AddressPartType.CTY);
        a.setValue("Indianapolis");
        part.add(a);

        a = createAddressPart(AddressPartType.STA);
        a.setValue("IN");
        part.add(a);

        a = createAddressPart(AddressPartType.ZIP);
        a.setValue("46240");
        part.add(a);

        a = createAddressPart(AddressPartType.CNT);
        a.setValue("adxp.value required");
        a.setCode("USA");
        a.setCodeSystem("ISO 3166-1 alpha-3 code");
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
        Adxp a = createAddressPart(AddressPartType.BNR);
        a.setValue("1050");
        part.add(a);
        a = createAddressPart(AddressPartType.DIR);
        a.setValue("W");
        part.add(a);
        a = createAddressPart(AddressPartType.STB);
        a.setValue("Wishard");
        part.add(a);
        a = createAddressPart(AddressPartType.STTYP);
        a.setValue("Blvd");
        part.add(a);

        a = createAddressPart(AddressPartType.ADL);
        a.setValue("RG 5th floor");
        part.add(a);

        a = createAddressPart(AddressPartType.CTY);
        a.setValue("Indianapolis");
        part.add(a);

        a = createAddressPart(AddressPartType.STA);
        a.setValue("IN");
        part.add(a);

        a = createAddressPart(AddressPartType.ZIP);
        a.setValue("46240");
        part.add(a);

        a = createAddressPart(AddressPartType.CNT);
        a.setValue("adxp.value required");
        a.setCode("USA");
        a.setCodeSystem("ISO 3166-1 alpha-3 code");
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

        Adxp a = createAddressPart(AddressPartType.POB);
        a.setValue("909");
        part.add(a);

        a = createAddressPart(AddressPartType.CTY);
        a.setValue("Indianapolis");
        part.add(a);

        a = createAddressPart(AddressPartType.STA);
        a.setValue("IN");
        part.add(a);

        a = createAddressPart(AddressPartType.ZIP);
        a.setValue("46240");
        part.add(a);

        a = createAddressPart(AddressPartType.CNT);
        a.setValue("adxp.value required");
        a.setCode("USA");
        a.setCodeSystem("ISO 3166-1 alpha-3 code");
        part.add(a);

        Address result = AdConverter.SimpleConverter.convertToAddress(iso);
        assertEquals("46240", result.getPostalCode());
        assertEquals("IN", result.getStateOrProvince());
        assertEquals("USA", result.getCountry().getAlpha3());

        assertEquals("P.O.Box 909", result.getStreetAddressLine());
    }

    @Test
    public void testConvertToAddressWithStateValueButNoCountry() {
        Ad iso = new Ad();
        List<Adxp> part = new ArrayList<Adxp>();
        iso.setPart(part);
        Adxp a = null;

        a = createAddressPart(AddressPartType.STA);
        a.setValue("INDIANA");
        part.add(a);

        Address result = AdConverter.SimpleConverter.convertToAddress(iso);
        assertEquals("INDIANA", result.getStateOrProvince());
        assertNull(result.getCountry());
    }

    @Test
    public void testConvertToAddressWithStateCodeButNoCountry() {
        Ad iso = new Ad();
        List<Adxp> part = new ArrayList<Adxp>();
        iso.setPart(part);
        Adxp a = null;

        a = createAddressPart(AddressPartType.STA);
        a.setValue("INDIANA");
        a.setCode("IN");
        part.add(a);

        Address result = AdConverter.SimpleConverter.convertToAddress(iso);
        assertEquals("INDIANA", result.getStateOrProvince());
        assertNull(result.getCountry());
    }

    @Test
    public void testConvertToAddressWithStateValueSpecifiedButNoSuchStateForGivenCountry() {
        Ad iso = new Ad();
        List<Adxp> part = new ArrayList<Adxp>();
        iso.setPart(part);
        Adxp a = null;

        a = createAddressPart(AddressPartType.STA);
        a.setValue("ZZZZZ");
        part.add(a);

        a = createAddressPart(AddressPartType.CNT);
        a.setValue("adxp.value required");
        a.setCode("USA");
        a.setCodeSystem("ISO 3166-1 alpha-3 code");
        part.add(a);

        Address result = AdConverter.SimpleConverter.convertToAddress(iso);
        assertEquals("ZZZZZ", result.getStateOrProvince());
        assertEquals("USA",  result.getCountry().getAlpha3());
    }
    @Test
    public void testConvertToAddressWithStateCodeSpecifiedButNoSuchStateForGivenCountry() {
        Ad iso = new Ad();
        List<Adxp> part = new ArrayList<Adxp>();
        iso.setPart(part);
        Adxp a = null;

        a = createAddressPart(AddressPartType.STA);
        a.setValue("ZZZZZ");
        a.setCode("ZZ");
        part.add(a);

        a = createAddressPart(AddressPartType.CNT);
        a.setValue("adxp.value required");
        a.setCode("USA");
        a.setCodeSystem("ISO 3166-1 alpha-3 code");
        part.add(a);

        try {
            AdConverter.SimpleConverter.convertToAddress(iso);
        } catch (PoIsoConstraintException e) {
            assertEquals("unsupported ISO 3166 state or province code 'ZZ' for Country code 'USA'", e.getMessage());
        }
    }

    @Test
    public void testConvertToAddressWithStateSpecifiedButNoSuchStateForGivenCountry2() {
        Ad iso = new Ad();
        List<Adxp> part = new ArrayList<Adxp>();
        iso.setPart(part);
        Adxp a = null;

        a = createAddressPart(AddressPartType.STA);
        a.setValue("ZZZZZ");
        part.add(a);

        a = createAddressPart(AddressPartType.CNT);
        a.setValue("adxp.value required");
        a.setCode("AAA");
        a.setCodeSystem("ISO 3166-1 alpha-3 code");
        part.add(a);

        Address result = AdConverter.SimpleConverter.convertToAddress(iso);
        assertEquals("ZZZZZ", result.getStateOrProvince());
        assertEquals("AAA",  result.getCountry().getAlpha3());
    }

    private static Adxp createAddressPart(AddressPartType type) {
        switch (type) {
            case ADL: return new AdxpAdl();
            case AL : return new AdxpAl();
            case BNN: return new AdxpBnn();
            case BNR: return new AdxpBnr();
            case BNS: return new AdxpBns();
            case CAR: return new AdxpCar();
            case CEN: return new AdxpCen();
            case CNT: return new AdxpCnt();
            case CPA: return new AdxpCpa();
            case CTY: return new AdxpCty();
            case DAL: return new AdxpDal();
            case DEL: return new AdxpDel();
            case DINST: return new AdxpDinst();
            case DINSTA: return new AdxpDinsta();
            case DINSTQ: return new AdxpDinstq();
            case DIR: return new AdxpDir();
            case DMOD: return new AdxpDmod();
            case DMODID: return new AdxpDmodid();
            case INT: return new AdxpInt();
            case POB: return new AdxpPob();
            case PRE: return new AdxpPre();
            case SAL: return new AdxpSal();
            case STA: return new AdxpSta();
            case STB: return new AdxpStb();
            case STR: return new AdxpStr();
            case STTYP: return new AdxpSttyp();
            case UNID: return new AdxpUnid();
            case UNIT: return new AdxpUnit();
            case ZIP: return new AdxpZip();

            // there must be a new type added
            default: throw new UnsupportedOperationException(type.name());
        }
    }
}