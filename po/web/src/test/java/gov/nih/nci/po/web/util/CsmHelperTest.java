package gov.nih.nci.po.web.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class CsmHelperTest {

    private static final String username = "myUser";
    
    @Test
    public void testConst() {
        CsmHelper helper = new CsmHelper("myUser");        
        assertEquals(username, helper.getUsername());
        
        try {
            helper.getFirstName();
        } catch (Throwable t) {
            
        }
        try {
            helper.getLastName();
        } catch (Throwable t) {
            
        }
    }
    
}
