package gov.nih.nci.po.web.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class CsmHelperTest {

    @Test
    public void testGrid() {
        CsmHelper helper = new CsmHelper("myUser");
        assertEquals("myUser", helper.getUsername());
        assertEquals("myUser", helper.getDisplayname());
    }


    @Test
    public void testNonGrid() {
        CsmHelper helper = new CsmHelper("/O=caBIG/OU=caGrid/OU=LOA1/OU=NCI/CN=myUser");
        assertEquals("/O=caBIG/OU=caGrid/OU=LOA1/OU=NCI/CN=myUser", helper.getUsername());
        assertEquals("myUser", helper.getDisplayname());
    }
}
