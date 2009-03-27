package gov.nih.nci.coppa.iso;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 *
 * @author gax
 */
public class AdxpTest {

    @Test
    public void testCreateAddressPart() {
        for (AddressPartType type: AddressPartType.values()){
            Adxp result = createAddressPart(type);
            assertEquals(type, result.getType());
            assertTrue("Adxp"+type.name(), result.getClass().getSimpleName().equalsIgnoreCase("Adxp"+type.name()));
        }


        Adxp result = createAddressPart(null);
        assertEquals(null, result.getType());
    }

    @Test
    public void testEquality() {
        for (AddressPartType type: AddressPartType.values()){
            Adxp first = createAddressPart(type);
            first.setCode("COD");
            first.setValue("value");
            assertTrue(first.equals(first));
            assertFalse(first.equals(null));

            Adxp second = createAddressPart(type);
            second.setCode("COD");
            second.setValue("value");

            assertTrue(first.equals(second));

            second.setCode("NCD");

            assertFalse(first.equals(second));
        }
    }

    @Test
    public void testHashCode() {
        for (AddressPartType type: AddressPartType.values()){
            Adxp first = createAddressPart(type);
            first.setCode("COD");
            first.setValue("value");

            Adxp second = createAddressPart(type);
            second.setCode("COD");
            second.setValue("value");

            assertEquals(first.hashCode(), second.hashCode());

            second.setCode("NCD");

            assertFalse(first.hashCode() == second.hashCode());
        }
    }

    @Test
    public void testCloneable() {
        for (AddressPartType type: AddressPartType.values()){
            Adxp first = createAddressPart(type);
            first.setCode("COD");
            first.setValue("value");

            Adxp second = first.clone();

            assertTrue(first != second);
            assertTrue(first.equals(second));
            assertEquals(first.hashCode(), second.hashCode());
        }
    }

     private static Adxp createAddressPart(AddressPartType type) {
        if (type == null) { return new Adxp(null); }

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