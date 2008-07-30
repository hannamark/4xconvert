package gov.nih.nci.coppa.iso;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author lpower
 */
public class AdxpDalTest {

    private AdxpDal a;

    @Before
    public void init() {
        a = new AdxpDal();
    }
    
    
    @Test(expected = IllegalArgumentException.class)
    public void testAddressPartTypeCnt() {
        a.setType(AddressPartType.CNT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddressPartTypeAl() {
        a.setType(AddressPartType.AL);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddressPartTypeSta() {
        a.setType(AddressPartType.STA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddressPartTypeCty() {
        a.setType(AddressPartType.CTY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddressPartTypeZip() {
        a.setType(AddressPartType.ZIP);
    }

    @Test
    public void testAddressPartTypeDal() {
        a.setType(AddressPartType.DAL);
        assertEquals(a.getType(), AddressPartType.DAL);
    }

}