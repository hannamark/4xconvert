
package gov.nih.nci.po.data.convert;

import gov.nih.nci.coppa.iso.IdentifierReliability;
import gov.nih.nci.coppa.iso.IdentifierScope;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.data.bo.URL;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gax
 */
public class IdConverterTest {

    private IdConverter idConverter = new IdConverter();

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
    public void convertToLong() {
        assertNull(idConverter.convertToLong(null));
        Ii ii = new Ii();
        assertNull(idConverter.convertToLong(ii));
        ii.setExtension("1");
        Long id = idConverter.convertToLong(ii);
        assertNotNull(id);
        assertEquals(Long.valueOf(1l), id);
    }
    
    
}