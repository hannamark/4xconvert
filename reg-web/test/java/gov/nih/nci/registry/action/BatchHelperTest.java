/**
 * 
 */
package gov.nih.nci.registry.action;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.net.URL;

import org.junit.Test;

/**
 * @author Vrushali
 *
 */
public class BatchHelperTest {
    private BatchHelper helper;
    @Test
    public void testProperty() {
        helper = new BatchHelper("uploadLoc","dataFileName","unzipLoc","userName");
        assertNotNull(helper.getTrialDataFileName());
        assertNotNull(helper.getUploadLoc());
        assertNotNull(helper.getUnzipLoc());
        assertNotNull(helper.getUserName());
        helper.setTrialDataFileName(null);
        helper.setUploadLoc(null);
        helper.setUnzipLoc(null);
        helper.setUserName(null);
        assertNull(helper.getTrialDataFileName());
        assertNull(helper.getUploadLoc());
        assertNull(helper.getUnzipLoc());
        assertNull(helper.getUserName());
    }
    @Test
    public void testProcessExcel() {
        helper = new BatchHelper("uploadLoc","dataFileName","unzipLoc","userName");
        try {
            helper.processExcel("fileName");
        } catch (Exception e) {
         fail();
        }
        try {
        URL fileUrl = ClassLoader.getSystemClassLoader().getResource("batchUploadTest.xls");
        File f = new File(fileUrl.toURI());
        assertNotNull(helper.processExcel(f.getAbsolutePath()));
        } catch (Exception e) {
            
        }
    }
}
