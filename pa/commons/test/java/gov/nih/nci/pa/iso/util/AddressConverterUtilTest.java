/**
 * 
 */
package gov.nih.nci.pa.iso.util;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.iso21090.Ad;
import gov.nih.nci.iso21090.AdxpAl;
import gov.nih.nci.iso21090.AdxpCnt;
import gov.nih.nci.iso21090.AdxpCty;
import gov.nih.nci.iso21090.AdxpSta;
import gov.nih.nci.iso21090.AdxpZip;

import java.util.Map;

import org.junit.Test;
/**
 * @author asharma
 *
 */
public class AddressConverterUtilTest {

	/**
	 * Test method for {@link gov.nih.nci.pa.iso.util.AddressConverterUtil#convertToAddress(gov.nih.nci.iso21090.Ad)}.
	 */
	@Test
	public void testConvertToAddress() {
   
		String sb = "101 Renner rd, Richardson, TX, 75081 USA";
		Ad address = AddressConverterUtil.create("101 Renner rd", "deliveryAddress", "Richardson", "TX", "75081", "USA");
		assertEquals("Create and convert to address test pass",sb,AddressConverterUtil.convertToAddress(address));
	}
	
	@Test
    public void testConvertToMap() {
   
        Ad address = AddressConverterUtil.create("101 Renner rd", "deliveryAddress", "Richardson", "TX", "75081", "USA");
        Map<String, String> myMap = AddressConverterUtil.convertToAddressBo(address);
        assertEquals(myMap.get(AdxpAl.class.getName()), "101 Renner rd");
        assertEquals(myMap.get(AdxpCty.class.getName()), "Richardson");
        assertEquals(myMap.get(AdxpSta.class.getName()), "TX");
        assertEquals(myMap.get(AdxpZip.class.getName()), "75081");
        assertEquals(myMap.get(AdxpCnt.class.getName()), "USA");
    }

}
