package gov.nih.nci.po.web.util;

import gov.nih.nci.po.web.AbstractPoTest;

import org.junit.Test;

public class PO1129UpdaterListenerTest extends AbstractPoTest {

    public void testContextInitialized() {
        new PO1129UpdaterListener().contextInitialized(null);
    }
    
    @Test
    public void testContextDestroyed() {
        new PO1129UpdaterListener().contextDestroyed(null);
    }
    
}
