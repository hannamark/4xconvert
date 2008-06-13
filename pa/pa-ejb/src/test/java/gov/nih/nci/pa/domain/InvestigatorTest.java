package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertEquals;
import org.junit.Test;


public class InvestigatorTest {
    
    @Test
    public void getFullNameTest() {
        Investigator inv = new Investigator();
        inv.setLastName("Smith");
        inv.setFirstName("John");
        inv.setMiddleName("R.");
        assertEquals("Smith, R., John", inv.getFullName());
        
    }
}
