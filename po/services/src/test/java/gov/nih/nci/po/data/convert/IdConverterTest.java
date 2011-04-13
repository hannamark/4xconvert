
package gov.nih.nci.po.data.convert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import gov.nih.nci.iso21090.IdentifierReliability;
import gov.nih.nci.iso21090.IdentifierScope;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.po.data.bo.ClinicalResearchStaff;
import gov.nih.nci.po.data.bo.Family;
import gov.nih.nci.po.data.bo.FamilyOrganizationRelationship;
import gov.nih.nci.po.data.bo.HealthCareFacility;
import gov.nih.nci.po.data.bo.HealthCareProvider;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.OrganizationalContact;
import gov.nih.nci.po.data.bo.OversightCommittee;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.data.bo.URL;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import org.junit.Test;

/**
 *
 * @author gax
 */
public class IdConverterTest {

    @Test (expected = UnsupportedOperationException.class)
    public void testConvert() {
        Class<URL> returnClass = URL.class;
        IdConverter instance = new IdConverter();
        instance.convert(returnClass, 1L);
    }

    @Test
    public void testConvertToIiOrg() throws Exception {
        Long value = null;
        IdConverter instance = new IdConverter.OrgIdConverter();
        Ii result = instance.convertToIi(value);
        assertNotNull(result.getNullFlavor());

        value = 1L;
        result = instance.convertToIi(value);
        assertNull(result.getNullFlavor());
        assertNotNull(result.getRoot());
        assertEquals(value.toString(), result.getExtension());

        // make sure we use the right id IdentifierReliability type.
        GeneratedValue a = Organization.class.getMethod("getId").getAnnotation(GeneratedValue.class);
        assertEquals(GenerationType.AUTO, a.strategy());
        assertEquals(IdentifierReliability.ISS, result.getReliability());

        assertEquals(IdentifierScope.OBJ, result.getScope());
    }

    @Test
    public void testConvertToIiPerson() throws Exception {
        Long value = null;
        IdConverter instance = new IdConverter.PersonIdConverter();
        Ii result = instance.convertToIi(value);
        assertNotNull(result.getNullFlavor());

        value = 1L;
        result = instance.convertToIi(value);
        assertNull(result.getNullFlavor());
        assertNotNull(result.getRoot());
        assertEquals(value.toString(), result.getExtension());

        // make sure we use the right id IdentifierReliability type.
        GeneratedValue a = Person.class.getMethod("getId").getAnnotation(GeneratedValue.class);
        assertEquals(GenerationType.AUTO, a.strategy());
        assertEquals(IdentifierReliability.ISS, result.getReliability());

        assertEquals(IdentifierScope.OBJ, result.getScope());
    }
    
    @Test
    public void testConvertToIiFamily() throws Exception {
        Long value = null;
        IdConverter instance = new IdConverter.FamilyIdConverter();
        Ii result = instance.convertToIi(value);
        assertNotNull(result.getNullFlavor());

        value = 1L;
        result = instance.convertToIi(value);
        assertNull(result.getNullFlavor());
        assertNotNull(result.getRoot());
        assertEquals(value.toString(), result.getExtension());
        GeneratedValue a = Family.class.getMethod("getId").getAnnotation(GeneratedValue.class);
        assertEquals(GenerationType.AUTO, a.strategy());
        assertEquals(IdentifierReliability.ISS, result.getReliability());

        assertEquals(IdentifierScope.OBJ, result.getScope());
    }
    
    @Test
    public void testConvertToIiFamilyOrganizationRelationship() throws Exception {
        Long value = null;
        IdConverter instance = new IdConverter.FamilyOrganizationRelationshipIdConverter();
        Ii result = instance.convertToIi(value);
        assertNotNull(result.getNullFlavor());

        value = 1L;
        result = instance.convertToIi(value);
        assertNull(result.getNullFlavor());
        assertNotNull(result.getRoot());
        assertEquals(value.toString(), result.getExtension());
        GeneratedValue a = FamilyOrganizationRelationship.class.getMethod("getId").getAnnotation(GeneratedValue.class);
        assertEquals(GenerationType.AUTO, a.strategy());
        assertEquals(IdentifierReliability.ISS, result.getReliability());

        assertEquals(IdentifierScope.OBJ, result.getScope());
    }
    
    @Test
    public void testConvertToIiClinicalResearchStaff() throws Exception {
        Long value = null;
        IdConverter instance = new IdConverter.ClinicalResearchStaffIdConverter();
        Ii result = instance.convertToIi(value);
        assertNotNull(result.getNullFlavor());

        value = 1L;
        result = instance.convertToIi(value);
        assertNull(result.getNullFlavor());
        assertNotNull(result.getRoot());
        assertEquals(value.toString(), result.getExtension());
        GeneratedValue a = ClinicalResearchStaff.class.getMethod("getId").getAnnotation(GeneratedValue.class);
        assertEquals(GenerationType.AUTO, a.strategy());
        assertEquals(IdentifierReliability.ISS, result.getReliability());

        assertEquals(IdentifierScope.OBJ, result.getScope());
    }
    
    @Test
    public void testConvertToIiHealthCareFacility() throws Exception {
        Long value = null;
        IdConverter instance = new IdConverter.HealthCareFacilityIdConverter();
        Ii result = instance.convertToIi(value);
        assertNotNull(result.getNullFlavor());

        value = 1L;
        result = instance.convertToIi(value);
        assertNull(result.getNullFlavor());
        assertNotNull(result.getRoot());
        assertEquals(value.toString(), result.getExtension());
        GeneratedValue a = HealthCareFacility.class.getMethod("getId").getAnnotation(GeneratedValue.class);
        assertEquals(GenerationType.AUTO, a.strategy());
        assertEquals(IdentifierReliability.ISS, result.getReliability());

        assertEquals(IdentifierScope.OBJ, result.getScope());
    }
    
    @Test
    public void testConvertToIiHealthCareProvider() throws Exception {
        Long value = null;
        IdConverter instance = new IdConverter.HealthCareProviderIdConverter();
        Ii result = instance.convertToIi(value);
        assertNotNull(result.getNullFlavor());

        value = 1L;
        result = instance.convertToIi(value);
        assertNull(result.getNullFlavor());
        assertNotNull(result.getRoot());
        assertEquals(value.toString(), result.getExtension());
        GeneratedValue a = HealthCareProvider.class.getMethod("getId").getAnnotation(GeneratedValue.class);
        assertEquals(GenerationType.AUTO, a.strategy());
        assertEquals(IdentifierReliability.ISS, result.getReliability());

        assertEquals(IdentifierScope.OBJ, result.getScope());
    }
    
    @Test
    public void testConvertToIiOrganizationalContact() throws Exception {
        Long value = null;
        IdConverter instance = new IdConverter.OrganizationalContactIdConverter();
        Ii result = instance.convertToIi(value);
        assertNotNull(result.getNullFlavor());

        value = 1L;
        result = instance.convertToIi(value);
        assertNull(result.getNullFlavor());
        assertNotNull(result.getRoot());
        assertEquals(value.toString(), result.getExtension());
        GeneratedValue a = OrganizationalContact.class.getMethod("getId").getAnnotation(GeneratedValue.class);
        assertEquals(GenerationType.AUTO, a.strategy());
        assertEquals(IdentifierReliability.ISS, result.getReliability());

        assertEquals(IdentifierScope.OBJ, result.getScope());
    }
    
    @Test
    public void testConvertToIiOversightCommittee() throws Exception {
        Long value = null;
        IdConverter instance = new IdConverter.OversightCommitteeIdConverter();
        Ii result = instance.convertToIi(value);
        assertNotNull(result.getNullFlavor());

        value = 1L;
        result = instance.convertToIi(value);
        assertNull(result.getNullFlavor());
        assertNotNull(result.getRoot());
        assertEquals(value.toString(), result.getExtension());
        GeneratedValue a = OversightCommittee.class.getMethod("getId").getAnnotation(GeneratedValue.class);
        assertEquals(GenerationType.AUTO, a.strategy());
        assertEquals(IdentifierReliability.ISS, result.getReliability());

        assertEquals(IdentifierScope.OBJ, result.getScope());
    }

}