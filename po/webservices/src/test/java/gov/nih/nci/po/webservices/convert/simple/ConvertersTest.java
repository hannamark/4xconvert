package gov.nih.nci.po.webservices.convert.simple;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * This is the test class for Converters.
 * 
 * @author Rohit Gupta
 * 
 */
public class ConvertersTest {

    @Test
    public void testGetConverter() throws Exception {
        assertEquals("gov.nih.nci.po.webservices.convert.simple.PersonConverter",
                Converters.get(PersonConverter.class).getClass().getName());

        assertEquals(
                "gov.nih.nci.po.webservices.convert.simple.OrganizationConverter",
                Converters.get(OrganizationConverter.class).getClass()
                        .getName());

        assertEquals(
                "gov.nih.nci.po.webservices.convert.simple.HealthCareProviderConverter",
                Converters.get(HealthCareProviderConverter.class).getClass()
                        .getName());

        assertEquals(
                "gov.nih.nci.po.webservices.convert.simple.OrganizationalContactConverter",
                Converters.get(OrganizationalContactConverter.class).getClass()
                        .getName());

        assertEquals(
                "gov.nih.nci.po.webservices.convert.simple.ClinicalResearchStaffConverter",
                Converters.get(ClinicalResearchStaffConverter.class).getClass()
                        .getName());

        assertEquals(
                "gov.nih.nci.po.webservices.convert.simple.ResearchOrganizationConverter",
                Converters.get(ResearchOrganizationConverter.class).getClass()
                        .getName());

        assertEquals(
                "gov.nih.nci.po.webservices.convert.simple.OversightCommitteeConverter",
                Converters.get(OversightCommitteeConverter.class).getClass()
                        .getName());

        assertEquals(
                "gov.nih.nci.po.webservices.convert.simple.HealthCareFacilityConverter",
                Converters.get(HealthCareFacilityConverter.class).getClass()
                        .getName());

        assertEquals("gov.nih.nci.po.webservices.convert.simple.FamilyConverter",
                Converters.get(FamilyConverter.class).getClass().getName());

        assertEquals(
                "gov.nih.nci.po.webservices.convert.simple.FamilyMemberRelationshipConverter",
                Converters.get(FamilyMemberRelationshipConverter.class)
                        .getClass().getName());
    }

    @Test(expected = ConverterException.class)
    public void testGetMissingConverter() throws Exception {
        Converters.get(AbstractConverter.class);
    }
}
