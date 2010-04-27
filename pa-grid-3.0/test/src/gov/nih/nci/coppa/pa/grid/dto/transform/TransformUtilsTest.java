package gov.nih.nci.coppa.pa.grid.dto.transform;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.TransformUtils;
import gov.nih.nci.iso21090.Constants;
import gov.nih.nci.iso21090.IdentifierReliability;
import gov.nih.nci.iso21090.IdentifierScope;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.grid.dto.transform.iso.IdTransformer;

import org.junit.Test;

/**
 * Tests the converting of old types to new types
 * @author aevansel
 */
public class TransformUtilsTest {
    private static final String NAME = "Test Name";
    
    @Test
    public void testConvertToOldId() {
        Ii id = new Ii();
        id.setRoot(Constants.NCI_OID);
        id.setIdentifierName(NAME);
        id.setExtension("123");
        id.setReliability(IdentifierReliability.ISS);
        id.setScope(IdentifierScope.OBJ);
        
        gov.nih.nci.coppa.services.pa.Id oldId = TransformUtils.convertToOldId(IdTransformer.INSTANCE.toXml(id));
        
        assertEquals(oldId.getRoot(), Constants.NCI_OID);
        assertEquals(oldId.getIdentifierName(), NAME);
        assertEquals(oldId.getExtension(), "123");
        assertEquals(oldId.getReliability(), org.iso._21090.IdentifierReliability.ISS);
        assertEquals(oldId.getScope(), org.iso._21090.IdentifierScope.OBJ);
    }

}
