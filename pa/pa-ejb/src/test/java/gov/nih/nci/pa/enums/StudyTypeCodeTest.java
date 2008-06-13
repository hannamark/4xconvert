package gov.nih.nci.pa.enums;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class StudyTypeCodeTest {
    private StudyTypeCode xxx;

    @Before
    public void setUp() {
        xxx = StudyTypeCode.ANCILLARY;
    }
    
    @Test
    public void getCodeTest() {
        assertEquals("Ancillary", xxx.getCode());
    }
    
    @Test
    public void getDisplayNameTest() {
        assertEquals("Ancillary", xxx.getDisplayName());
    }

    @Test
    public void getNameTest() {
        assertEquals("ANCILLARY", xxx.getName());
    }

    @Test
    public void getByCodeTest() {
        assertEquals(StudyTypeCode.ANCILLARY
                , StudyTypeCode.getByCode("Ancillary"));
    }


}
