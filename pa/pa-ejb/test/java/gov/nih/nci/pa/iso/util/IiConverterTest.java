package gov.nih.nci.pa.iso.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class IiConverterTest {

	@Test
	public void testConvertToIiLong() {
		Long id =null;
		assertNull(IiConverter.convertToLong(IiConverter.convertToIi(id)));
	}

	@Test
	public void testConvertToIiString() {
		String id =null;
		assertNull(IiConverter.convertToString(IiConverter.convertToIi(id)));
	}

	@Test
	public void testConverToStudyProtocolIi() {
		Long id =null;
		assertNull(IiConverter.convertToLong(IiConverter.convertToStudyProtocolIi(id)));
	}

	@Test
	public void testConverToStudyOutcomeMeasureIi() {
		Long id =null;
		assertNull(IiConverter.convertToLong(IiConverter.convertToStudyOutcomeMeasureIi(id)));
	}

	@Test
	public void testConverToStudyIndIdeIi() {
		Long id =null;
		assertNull(IiConverter.convertToLong(IiConverter.convertToStudyIndIdeIi(id)));
	}

	@Test
	public void testConverToArmIi() {
		Long id =null;
		assertNull(IiConverter.convertToLong(IiConverter.convertToArmIi(id)));
	}

	@Test
	public void testConverToStratumGroupIi() {
		Long id =null;
		assertNull(IiConverter.convertToLong(IiConverter.convertToStratumGroupIi(id)));
	}

	@Test
	public void testConverToStudyOverallStatusIi() {
		Long id =null;
		assertNull(IiConverter.convertToLong(IiConverter.convertToStudyOverallStatusIi(id)));
	}

	@Test
	public void testConverToActivityIi() {
		Long id =null;
		assertNull(IiConverter.convertToLong(IiConverter.convertToActivityIi(id)));
	}

	@Test
	public void testConverToStudyResourcingIi() {
		Long id =null;
		assertNull(IiConverter.convertToLong(IiConverter.convertToStudyResourcingIi(id)));
	}

	@Test
	public void testConverToDocumentIi() {
		Long id =null;
		assertNull(IiConverter.convertToLong(IiConverter.convertToDocumentIi(id)));
	}

	@Test
	public void testConverToPoPersonIi() {
		String id =null;
		assertNull(IiConverter.convertToString(IiConverter.convertToPoPersonIi(id)));
	}

	@Test
	public void testConverToPoOrganizationIi() {
		String id =null;
		assertNull(IiConverter.convertToString(IiConverter.convertToIdentifiedPersonEntityIi(id)));
	}

	@Test
	public void testConverToIdentifiedEntityIi() {
		String id =null;
		assertNull(IiConverter.convertToString(IiConverter.convertToIdentifiedEntityIi(id)));
	}

	@Test
	public void testConverToIdentifiedOrgEntityIi() {
		String id =null;
		assertNull(IiConverter.convertToString(IiConverter.convertToIdentifiedOrgEntityIi(id)));
	}

	@Test
	public void testConverToIdentifiedPersonEntityIi() {
		String id =null;
		assertNull(IiConverter.convertToString(IiConverter.convertToIdentifiedPersonEntityIi(id)));
	}

	@Test
	public void testConverToPoOrganizationalContactIi() {
		String id =null;
		assertNull(IiConverter.convertToString(IiConverter.convertToPoOrganizationalContactIi(id)));
	}

	@Test
	public void testConverToPoClinicalResearchStaffIi() {
		String id =null;
		assertNull(IiConverter.convertToString(IiConverter.convertToPoClinicalResearchStaffIi(id)));
	}

	@Test
	public void testConverToPoHealtcareProviderIi() {
		String id =null;
		assertNull(IiConverter.convertToString(IiConverter.convertToPoHealtcareProviderIi(id)));
	}

	@Test
	public void testConverToPoHealthCareFacilityIi() {
		String id =null;
		assertNull(IiConverter.convertToString(IiConverter.convertToPoHealthCareFacilityIi(id)));
	}

	@Test
	public void testConverToPoOversightCommitteeIi() {
		String id =null;
		assertNull(IiConverter.convertToString(IiConverter.convertToPoOversightCommitteeIi(id)));
	}

}
