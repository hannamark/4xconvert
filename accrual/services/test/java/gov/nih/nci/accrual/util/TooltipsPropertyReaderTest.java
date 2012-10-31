package gov.nih.nci.accrual.util;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class TooltipsPropertyReaderTest {

	@Test
	public void testTooltipsPropertyReader() throws Exception {
		TooltipsPropertyReader reader = new TooltipsPropertyReader();
		assertNotNull(reader.getTooltip(""));
	}
}
