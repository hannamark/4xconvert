/**
 * 
 */
package gov.nih.nci.pa.iso.util;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.iso21090.Ad;

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

}
