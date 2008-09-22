/**
 * 
 */
package gov.nih.nci.pa.iso.util;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.StudyStatusCode;

import org.junit.Test;

public class CdConverterTest {    
    @Test
    public void studyStatusCodeTest () {
        assertNull(CdConverter.convertToCd(null).getCode());
        for (StudyStatusCode xxx : StudyStatusCode.values()) {
            assertTrue(xxx.getCode().equals(CdConverter.convertToCd(xxx).getCode()));
        }
    }
    @Test
    public void actualAnticipatedTypeCodeTest () {
        assertNull(CdConverter.convertToCd(null).getCode());
        for (ActualAnticipatedTypeCode xxx : ActualAnticipatedTypeCode.values()) {
            assertTrue(xxx.getCode().equals(CdConverter.convertToCd(xxx).getCode()));
        }
    }
}
