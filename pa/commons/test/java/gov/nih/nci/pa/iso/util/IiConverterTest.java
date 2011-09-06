package gov.nih.nci.pa.iso.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.iso21090.IdentifierReliability;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.NullFlavor;

import org.apache.commons.lang.StringUtils;
import org.junit.Ignore;
import org.junit.Test;

public class IiConverterTest {

    public static Ii makePersonPoIi(String extension) {
        Ii personIi = new Ii();
        personIi.setRoot(IiConverter.PERSON_ROOT);
        personIi.setIdentifierName(IiConverter.PERSON_IDENTIFIER_NAME);
        personIi.setExtension(extension);
        personIi.setReliability(IdentifierReliability.UNV);
        return personIi;
    }

    @Test
    public void testConvertNullToIiLong() {
        Long id = null;
        assertNull(IiConverter.convertToLong(IiConverter.convertToIi(id)));
    }

    @Test
    public void testConvertNullToIiString() {
        String id = null;
        assertNull(IiConverter.convertToString(IiConverter.convertToIi(id)));
    }

    @Test
    public void testConvertNullToStudyProtocolIi() {
        Long id = null;
        assertNull(IiConverter.convertToLong(IiConverter.convertToStudyProtocolIi(id)));
    }

    @Test
    public void testConvertNullToStudyOutcomeMeasureIi() {
        Long id = null;
        assertNull(IiConverter.convertToLong(IiConverter.convertToStudyOutcomeMeasureIi(id)));
    }

    @Test
    public void testConvertNullToStudyIndIdeIi() {
        Long id = null;
        assertNull(IiConverter.convertToLong(IiConverter.convertToStudyIndIdeIi(id)));
    }

    @Test
    public void testConvertNullToArmIi() {
        Long id = null;
        assertNull(IiConverter.convertToLong(IiConverter.convertToArmIi(id)));
    }

    @Test
    public void testConvertNullToStratumGroupIi() {
        Long id = null;
        assertNull(IiConverter.convertToLong(IiConverter.convertToStratumGroupIi(id)));
    }

    @Test
    public void testConvertNullToStudyOverallStatusIi() {
        Long id = null;
        assertNull(IiConverter.convertToLong(IiConverter.convertToStudyOverallStatusIi(id)));
    }

    @Test
    public void testConvertNullToActivityIi() {
        Long id = null;
        assertNull(IiConverter.convertToLong(IiConverter.convertToActivityIi(id)));
    }

    @Test
    public void testConvertNullToStudyResourcingIi() {
        Long id = null;
        assertNull(IiConverter.convertToLong(IiConverter.convertToStudyResourcingIi(id)));
    }

    @Test
    public void testConvertNullToDocumentIi() {
        Long id = null;
        assertNull(IiConverter.convertToLong(IiConverter.convertToDocumentIi(id)));
    }

    @Test
    public void testConvertNullToPoPersonIi() {
        Ii poPersonIi = IiConverter.convertToPoPersonIi(null);
        assertTrue("Null ID shouldn't populate extension", StringUtils.isEmpty(poPersonIi.getExtension()));
        assertEquals("Null II should have root set", IiConverter.PERSON_ROOT, poPersonIi.getRoot());
        assertEquals("Null II should have identifier name set", IiConverter.PERSON_IDENTIFIER_NAME,
                     poPersonIi.getIdentifierName());
        assertEquals("Null II should have reliability", IdentifierReliability.UNV, poPersonIi.getReliability());
        assertEquals("Null II should have NI null flavor", NullFlavor.NI, poPersonIi.getNullFlavor());
    }

    @Test
    public void testConvertNonNullToPoPersonIi() {
        Ii poPersonIi = IiConverter.convertToPoPersonIi("1234");
        assertEquals(makePersonPoIi("1234"), poPersonIi);
    }

    @Test
    public void testConvertNullIiToString() {
        assertNull("Null II should be converted to null String", IiConverter.convertToString(null));
    }

    @Test
    public void testConvertNullFlavorIiToString() {
        Ii ii = new Ii();
        ii.setNullFlavor(NullFlavor.NI);
        assertNull("Null-flavored II should be converted to null String", IiConverter.convertToString(ii));
    }

    @Test
    public void testConvertNullFlavorIiWithExtensionToString() {
        Ii ii = new Ii();
        ii.setNullFlavor(NullFlavor.NI);
        ii.setExtension("1234");
        assertNull("II with null flavor and extenstion should be converted to null String", IiConverter.convertToString(ii));
    }

    @Test(expected = Exception.class)
    @Ignore("convertToString currently sets the null flavor if there's no extension, rather than throwing an exception or just leaving the input unmodified")
    public void testConvertIiWithNullExtensionToString() {
        Ii ii = new Ii();
        assertNull("II with no extension and no null flavor should be converted to null String", IiConverter.convertToString(ii));
        assertNull("Converting II to String shouldn't modify II", ii.getNullFlavor());
    }

    @Test
    public void testConvertNonNullIiToString() {
        Ii ii = new Ii();
        ii.setExtension("1234");
        assertEquals("II's extension should be returned as String", "1234", IiConverter.convertToString(ii));
    }

    @Test
    public void testConvertNullToPoOrganizationIi() {
        String id = null;
        assertNull(IiConverter.convertToString(IiConverter.convertToIdentifiedPersonEntityIi(id)));
    }

    @Test
    public void testConvertNullToIdentifiedEntityIi() {
        String id = null;
        assertNull(IiConverter.convertToString(IiConverter.convertToIdentifiedEntityIi(id)));
    }

    @Test
    public void testConvertNullToIdentifiedOrgEntityIi() {
        String id = null;
        assertNull(IiConverter.convertToString(IiConverter.convertToIdentifiedOrgEntityIi(id)));
    }

    @Test
    public void testConvertNullToIdentifiedPersonEntityIi() {
        String id = null;
        assertNull(IiConverter.convertToString(IiConverter.convertToIdentifiedPersonEntityIi(id)));
    }

    @Test
    public void testConvertNullToPoOrganizationalContactIi() {
        String id = null;
        assertNull(IiConverter.convertToString(IiConverter.convertToPoOrganizationalContactIi(id)));
    }

    @Test
    public void testConvertNullToPoClinicalResearchStaffIi() {
        String id = null;
        assertNull(IiConverter.convertToString(IiConverter.convertToPoClinicalResearchStaffIi(id)));
    }

    @Test
    public void testConvertNullToPoHealtcareProviderIi() {
        String id = null;
        assertNull(IiConverter.convertToString(IiConverter.convertToPoHealtcareProviderIi(id)));
    }

    @Test
    public void testConvertNullToPoHealthCareFacilityIi() {
        String id = null;
        assertNull(IiConverter.convertToString(IiConverter.convertToPoHealthCareFacilityIi(id)));
    }

    @Test
    public void testConvertNullToPoOversightCommitteeIi() {
        String id = null;
        assertNull(IiConverter.convertToString(IiConverter.convertToPoOversightCommitteeIi(id)));
    }

    @Test
    public void convertToSubjectAccrualIi() {
        Ii ii = IiConverter.convertToSubjectAccrualIi(1L);
        assertEquals(IiConverter.SUBJECT_ACCRUAL_IDENTIFIER_NAME, ii.getIdentifierName());
        assertEquals(IiConverter.SUBJECT_ACCRUAL_ROOT, ii.getRoot());
        assertEquals("1", ii.getExtension());
    }
}
