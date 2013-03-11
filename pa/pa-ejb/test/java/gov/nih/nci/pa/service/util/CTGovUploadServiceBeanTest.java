/**
 * 
 */
package gov.nih.nci.pa.service.util;

import static org.junit.Assert.assertTrue;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.AbstractMockitoTest;
import gov.nih.nci.pa.util.PaEarPropertyReader;

import java.lang.reflect.Field;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockftpserver.fake.FakeFtpServer;
import org.mockftpserver.fake.UserAccount;
import org.mockftpserver.fake.filesystem.FileEntry;
import org.mockftpserver.fake.filesystem.UnixFakeFileSystem;

/**
 * @author Denis G. Krylov
 * 
 */
public class CTGovUploadServiceBeanTest extends AbstractMockitoTest {

    private CTGovUploadServiceBeanLocal serviceBean;
    private FakeFtpServer fakeFtpServer;
    private UnixFakeFileSystem fileSystem;

    /*
     * (non-Javadoc)
     * 
     * @see gov.nih.nci.pa.util.AbstractMockitoTest#setUp()
     */
    @Before
    public void setUp() throws Exception {
        super.setUp();
        serviceBean = new CTGovUploadServiceBeanLocal();
        serviceBean.setQueryServiceLocal(protocolQueryServiceLocal);
        serviceBean.setGeneratorServiceLocal(ctGovXmlGeneratorServiceLocal);
        serviceBean.setLookUpTableService(lookupSvc);

        fakeFtpServer = new FakeFtpServer();
        fakeFtpServer.setServerControlPort(51239); // use any free port

        fileSystem = new UnixFakeFileSystem();
        fileSystem.add(new FileEntry("/test.txt", ""));
        fakeFtpServer.setFileSystem(fileSystem);

        UserAccount userAccount = new UserAccount("ctrppa", "ctrppa", "/");
        userAccount.setAccountRequiredForLogin(true);
        userAccount.setPasswordCheckedDuringValidation(true);
        fakeFtpServer.addUserAccount(userAccount);

        fakeFtpServer.start();
        
        final Field propsField = PaEarPropertyReader.class.getDeclaredField("PROPS");
        propsField.setAccessible(true);
        Properties props = (Properties) propsField.get(null);
        props.put("ctgov.ftp.url", "ftp://ctrppa:ctrppa@localhost:51239/");

    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        try {
            fakeFtpServer.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.service.util.CTGovUploadServiceBeanLocal#uploadToCTGov()}
     * .
     * @throws PAException 
     */
    @Test
    public final void testUploadToCTGov() throws PAException {
        serviceBean.uploadToCTGov();
        assertTrue(fileSystem.exists("/clinical.txt"));        
    }

}
