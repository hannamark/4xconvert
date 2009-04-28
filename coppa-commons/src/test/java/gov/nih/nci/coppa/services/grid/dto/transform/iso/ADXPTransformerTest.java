package gov.nih.nci.coppa.services.grid.dto.transform.iso;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.coppa.iso.AddressPartType;
import gov.nih.nci.coppa.iso.Adxp;
import gov.nih.nci.coppa.iso.AdxpAdl;
import gov.nih.nci.coppa.iso.AdxpAl;
import gov.nih.nci.coppa.iso.AdxpBnn;
import gov.nih.nci.coppa.iso.AdxpBnr;
import gov.nih.nci.coppa.iso.AdxpBns;
import gov.nih.nci.coppa.iso.AdxpCar;
import gov.nih.nci.coppa.iso.AdxpCen;
import gov.nih.nci.coppa.iso.AdxpCnt;
import gov.nih.nci.coppa.iso.AdxpCpa;
import gov.nih.nci.coppa.iso.AdxpCty;
import gov.nih.nci.coppa.iso.AdxpDal;
import gov.nih.nci.coppa.iso.AdxpDel;
import gov.nih.nci.coppa.iso.AdxpDinst;
import gov.nih.nci.coppa.iso.AdxpDinsta;
import gov.nih.nci.coppa.iso.AdxpDinstq;
import gov.nih.nci.coppa.iso.AdxpDir;
import gov.nih.nci.coppa.iso.AdxpDmod;
import gov.nih.nci.coppa.iso.AdxpDmodid;
import gov.nih.nci.coppa.iso.AdxpInt;
import gov.nih.nci.coppa.iso.AdxpPob;
import gov.nih.nci.coppa.iso.AdxpPre;
import gov.nih.nci.coppa.iso.AdxpSal;
import gov.nih.nci.coppa.iso.AdxpSta;
import gov.nih.nci.coppa.iso.AdxpStb;
import gov.nih.nci.coppa.iso.AdxpStr;
import gov.nih.nci.coppa.iso.AdxpSttyp;
import gov.nih.nci.coppa.iso.AdxpUnid;
import gov.nih.nci.coppa.iso.AdxpUnit;
import gov.nih.nci.coppa.iso.AdxpZip;

import org.junit.Test;

public class ADXPTransformerTest {

    @Test
    public void testCreateAddressPart() {
        verify(new AdxpAdl(), AddressPartType.ADL);
        verify(new AdxpAl(), AddressPartType.AL);
        verify(new AdxpBnn(), AddressPartType.BNN);
        verify(new AdxpBnr(), AddressPartType.BNR);
        verify(new AdxpBns(), AddressPartType.BNS);
        verify(new AdxpCar(), AddressPartType.CAR);
        verify(new AdxpCen(), AddressPartType.CEN);
        verify(new AdxpCnt(), AddressPartType.CNT);
        verify(new AdxpCpa(), AddressPartType.CPA);
        verify(new AdxpCty(), AddressPartType.CTY);
        verify(new AdxpDal(), AddressPartType.DAL);
        verify(new AdxpDel(), AddressPartType.DEL);
        verify(new AdxpDinst(), AddressPartType.DINST);
        verify(new AdxpDinsta(), AddressPartType.DINSTA);
        verify(new AdxpDinstq(), AddressPartType.DINSTQ);
        verify(new AdxpDir(), AddressPartType.DIR);
        verify(new AdxpDmod(), AddressPartType.DMOD);
        verify(new AdxpDmodid(), AddressPartType.DMODID);
        verify(new AdxpInt(), AddressPartType.INT);
        verify(new AdxpPob(), AddressPartType.POB);
        verify(new AdxpPre(), AddressPartType.PRE);
        verify(new AdxpSal(), AddressPartType.SAL);
        verify(new AdxpSta(), AddressPartType.STA);
        verify(new AdxpStb(), AddressPartType.STB);
        verify(new AdxpStr(), AddressPartType.STR);
        verify(new AdxpSttyp(), AddressPartType.STTYP);
        verify(new AdxpUnid(), AddressPartType.UNID);
        verify(new AdxpUnit(), AddressPartType.UNIT);
        verify(new AdxpZip(), AddressPartType.ZIP);
    }

    private void verify(Adxp expectedType, AddressPartType adl) {
        Adxp adxp = ADXPTransformer.createAddressPart(adl);
        assertNotNull(adxp);
        assertEquals(expectedType.getClass(), adxp.getClass());
    }

    /**
     * This test will uncover missing entries in the Map< AddressPartType, Adxp>, if an exception is thrown then a AddressPartType is missing
     */
    @Test
    public void testCreateAddressPartUnknown() {
        for (AddressPartType type : AddressPartType.values()) {
            assertNotNull(ADXPTransformer.createAddressPart(type));
        }
    }
    @Test
    public void testCreateAddressPartWhenParamIsNull() {
        verify(new Adxp(), null);
    }

}
