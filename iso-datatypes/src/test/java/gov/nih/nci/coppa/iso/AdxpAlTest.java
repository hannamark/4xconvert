package gov.nih.nci.coppa.iso;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author lpower
 */
public class AdxpAlTest {

    private AdxpAl a;

    @Before
    public void init() {
        a = new AdxpAl();
    }
    
    
    @Test(expected = IllegalArgumentException.class)
    public void testAddressPartTypeCnt() {
        a.setType(AddressPartType.CNT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddressPartTypeCty() {
        a.setType(AddressPartType.CTY);
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
    public void testAddressPartTypeAl() {
        a.setType(AddressPartType.AL);
        assertEquals(a.getType(), AddressPartType.AL);
    }

}