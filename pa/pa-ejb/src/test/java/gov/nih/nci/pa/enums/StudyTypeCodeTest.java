package gov.nih.nci.pa.enums;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class StudyTypeCodeTest {
    private StudyTypeCode xxx;

    @Before
    public void setUp() {
        xxx = StudyTypeCode.OTHER;
    }
    
    @Test
    public void getCodeTest() {
        assertEquals("Other", xxx.getCode());
    }
    
    @Test
    public void getDisplayNameTest() {
        assertEquals("Other", xxx.getDisplayName());
    }

    @Test
    public void getNameTest() {
        assertEquals("OTHER", xxx.getName());
    }

    @Test
    public void getByCodeTest() {
        assertEquals(StudyTypeCode.OTHER
                , StudyTypeCode.getByCode("Other"));
    }


}
