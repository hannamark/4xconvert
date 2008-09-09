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
        assertNull(CdConverter.convertToStudyStatusCode(CdConverter.convertToCd(null)));
        for (StudyStatusCode xxx : StudyStatusCode.values()) {
            assertTrue(xxx.equals(CdConverter.convertToStudyStatusCode(CdConverter.convertToCd(xxx))));            
        }
    }
    @Test
    public void actualAnticipatedTypeCodeTest () {
        assertNull(CdConverter.convertToActualAnticipatedTypeCode(CdConverter.convertToCd(null)));
        for (ActualAnticipatedTypeCode xxx : ActualAnticipatedTypeCode.values()) {
            assertTrue(xxx.equals(CdConverter.convertToActualAnticipatedTypeCode(CdConverter.convertToCd(xxx))));            
        }
    }
}
