package gov.nih.nci.pa.iso.util;

import static org.junit.Assert.assertNull;

import org.junit.Test;

public class EnOnConverterTest {
	
	
	@Test
	public void testConvertToEnOn() {
		
		String str=null;
		assertNull(EnOnConverter.convertEnOnToString(EnOnConverter.convertToEnOn(str)));
	}

	
}
