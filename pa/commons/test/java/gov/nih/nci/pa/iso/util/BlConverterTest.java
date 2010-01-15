/**
 * 
 */
package gov.nih.nci.pa.iso.util;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.coppa.iso.Bl;

import org.junit.Test;

/**
 * @author asharma
 *
 */
public class BlConverterTest {

	/**
	 * Test method for {@link gov.nih.nci.pa.iso.util.BlConverter#convertToBl(java.lang.Boolean)}.
	 */
	@Test
	public void testConvertToBl() {
		Boolean bool =true;
		Bl bl =BlConverter.convertToBl(bool);
		assertEquals(BlConverter.covertToBoolean(bl),bool);
		assertEquals(BlConverter.covertToBool(bl),bool.booleanValue());
		assertEquals(BlConverter.convertToString(bl),bool.toString());
	}
}
