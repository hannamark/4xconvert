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
		assertNull(IiConverter.convertToLong(IiConverter.converToStudyProtocolIi(id)));
	}

	@Test
	public void testConverToStudyOutcomeMeasureIi() {
		Long id =null;
		assertNull(IiConverter.convertToLong(IiConverter.converToStudyOutcomeMeasureIi(id)));
	}

	@Test
	public void testConverToStudyIndIdeIi() {
		Long id =null;
		assertNull(IiConverter.convertToLong(IiConverter.converToStudyIndIdeIi(id)));
	}

	@Test
	public void testConverToArmIi() {
		Long id =null;
		assertNull(IiConverter.convertToLong(IiConverter.converToArmIi(id)));
	}

	@Test
	public void testConverToStratumGroupIi() {
		Long id =null;
		assertNull(IiConverter.convertToLong(IiConverter.converToStratumGroupIi(id)));
	}

	@Test
	public void testConverToStudyOverallStatusIi() {
		Long id =null;
		assertNull(IiConverter.convertToLong(IiConverter.converToStudyOverallStatusIi(id)));
	}

	@Test
	public void testConverToActivityIi() {
		Long id =null;
		assertNull(IiConverter.convertToLong(IiConverter.converToActivityIi(id)));
	}

	@Test
	public void testConverToStudyResourcingIi() {
		Long id =null;
		assertNull(IiConverter.convertToLong(IiConverter.converToStudyResourcingIi(id)));
	}

	@Test
	public void testConverToDocumentIi() {
		Long id =null;
		assertNull(IiConverter.convertToLong(IiConverter.converToDocumentIi(id)));
	}

	@Test
	public void testConverToPoPersonIi() {
		String id =null;
		assertNull(IiConverter.convertToString(IiConverter.converToPoPersonIi(id)));
	}

	@Test
	public void testConverToPoOrganizationIi() {
		String id =null;
		assertNull(IiConverter.convertToString(IiConverter.converToIdentifiedPersonEntityIi(id)));
	}

	@Test
	public void testConverToIdentifiedEntityIi() {
		String id =null;
		assertNull(IiConverter.convertToString(IiConverter.converToIdentifiedEntityIi(id)));
	}

	@Test
	public void testConverToIdentifiedOrgEntityIi() {
		String id =null;
		assertNull(IiConverter.convertToString(IiConverter.converToIdentifiedOrgEntityIi(id)));
	}

	@Test
	public void testConverToIdentifiedPersonEntityIi() {
		String id =null;
		assertNull(IiConverter.convertToString(IiConverter.converToIdentifiedPersonEntityIi(id)));
	}

	@Test
	public void testConverToPoOrganizationalContactIi() {
		String id =null;
		assertNull(IiConverter.convertToString(IiConverter.converToPoOrganizationalContactIi(id)));
	}

	@Test
	public void testConverToPoClinicalResearchStaffIi() {
		String id =null;
		assertNull(IiConverter.convertToString(IiConverter.converToPoClinicalResearchStaffIi(id)));
	}

	@Test
	public void testConverToPoHealtcareProviderIi() {
		String id =null;
		assertNull(IiConverter.convertToString(IiConverter.converToPoHealtcareProviderIi(id)));
	}

	@Test
	public void testConverToPoHealthCareFacilityIi() {
		String id =null;
		assertNull(IiConverter.convertToString(IiConverter.converToPoHealthCareFacilityIi(id)));
	}

	@Test
	public void testConverToPoOversightCommitteeIi() {
		String id =null;
		assertNull(IiConverter.convertToString(IiConverter.converToPoOversightCommitteeIi(id)));
	}

}
