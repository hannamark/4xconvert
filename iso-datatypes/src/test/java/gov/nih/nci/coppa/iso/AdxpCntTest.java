package gov.nih.nci.coppa.iso;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author lpower
 */
public class AdxpCntTest {

    private AdxpCnt a;

    @Before
    public void init() {
        a = new AdxpCnt();
    }
    
    
    @Test(expected = IllegalArgumentException.class)
    public void testAddressPartTypeCty() {
        a.setType(AddressPartType.CTY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddressPartTypeAl() {
        a.setType(AddressPartType.AL);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddressPartTypeDal() {
        a.setType(AddressPartType.DAL);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddressPartTypeSta() {
        a.setType(AddressPartType.STA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddressPartTypeZip() {
        a.setType(AddressPartType.ZIP);
    }

    @Test
    public void testAddressPartTypeCnt() {
        a.setType(AddressPartType.CNT);
        assertEquals(a.getType(), AddressPartType.CNT);
    }

}