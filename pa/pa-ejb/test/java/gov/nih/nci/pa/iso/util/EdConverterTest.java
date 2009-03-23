package gov.nih.nci.pa.iso.util;

import static org.junit.Assert.assertNull;
import gov.nih.nci.coppa.iso.Ed;

import org.junit.Test;

public class EdConverterTest {

	@Test
	public void testConvertToEd() {
		byte [] data =null;
		assertNull("Null test",EdConverter.convertToByte(EdConverter.convertToEd(data)));
	}


}
