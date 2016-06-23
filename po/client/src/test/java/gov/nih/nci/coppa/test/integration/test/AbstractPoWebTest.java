/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The po
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This po Software License (the License) is between NCI and You. You (or
 * Your) shall mean a person or an entity, and all other entities that control,
 * are controlled by, or are under common control with the entity. Control for
 * purposes of this definition means (i) the direct or indirect power to cause
 * the direction or management of such entity, whether by contract or otherwise,
 * or (ii) ownership of fifty percent (50%) or more of the outstanding shares,
 * or (iii) beneficial ownership of such entity.
 *
 * This License is granted provided that You agree to the conditions described
 * below. NCI grants You a non-exclusive, worldwide, perpetual, fully-paid-up,
 * no-charge, irrevocable, transferable and royalty-free right and license in
 * its rights in the po Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the po Software; (ii) distribute and
 * have distributed to and by third parties the po Software and any
 * modifications and derivative works thereof; and (iii) sublicense the
 * foregoing rights set out in (i) and (ii) to third parties, including the
 * right to license such rights to further third parties. For sake of clarity,
 * and not by way of limitation, NCI shall have no right of accounting or right
 * of payment from You or Your sub-licensees for the rights granted under this
 * License. This License is granted at no charge to You.
 *
 * Your redistributions of the source code for the Software must retain the
 * above copyright notice, this list of conditions and the disclaimer and
 * limitation of liability of Article 6, below. Your redistributions in object
 * code form must reproduce the above copyright notice, this list of conditions
 * and the disclaimer of Article 6 in the documentation and/or other materials
 * provided with the distribution, if any.
 *
 * Your end-user documentation included with the redistribution, if any, must
 * include the following acknowledgment: This product includes software
 * developed by 5AM and the National Cancer Institute. If You do not include
 * such end-user documentation, You shall include this acknowledgment in the
 * Software itself, wherever such third-party acknowledgments normally appear.
 *
 * You may not use the names "The National Cancer Institute", "NCI", or "5AM"
 * to endorse or promote products derived from this Software. This License does
 * not authorize You to use any trademarks, service marks, trade names, logos or
 * product names of either NCI or 5AM, except as required to comply with the
 * terms of this License.
 *
 * For sake of clarity, and not by way of limitation, You may incorporate this
 * Software into Your proprietary programs and into any third party proprietary
 * programs. However, if You incorporate the Software into third party
 * proprietary programs, You agree that You are solely responsible for obtaining
 * any permission from such third parties required to incorporate the Software
 * into such third party proprietary programs and for informing Your
 * sub-licensees, including without limitation Your end-users, of their
 * obligation to secure any required permissions from such third parties before
 * incorporating the Software into such third party proprietary software
 * programs. In the event that You fail to obtain such permissions, You agree
 * to indemnify NCI for any claims against NCI by such third parties, except to
 * the extent prohibited by law, resulting from Your failure to obtain such
 * permissions.
 *
 * For sake of clarity, and not by way of limitation, You may add Your own
 * copyright statement to Your modifications and to the derivative works, and
 * You may provide additional or different license terms and conditions in Your
 * sublicenses of modifications of the Software, or any derivative works of the
 * Software as a whole, provided Your use, reproduction, and distribution of the
 * Work otherwise complies with the conditions stated in this License.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO
 * EVENT SHALL THE NATIONAL CANCER INSTITUTE, 5AM SOLUTIONS, INC. OR THEIR
 * AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.nih.nci.coppa.test.integration.test;

import gov.nih.nci.coppa.test.DataGeneratorUtil;
import gov.nih.nci.coppa.test.TstProperties;
import gov.nih.nci.coppa.test.remoteapi.RemoteServiceHelper;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.iso21090.TelEmail;
import gov.nih.nci.iso21090.TelUrl;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.service.TestConvertHelper;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.naming.NamingException;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public abstract class AbstractPoWebTest extends AbstractSelenese2TestCase {

    private static final String PHANTOM_JS_DRIVER = "org.openqa.selenium.phantomjs.PhantomJSDriver";
    protected static final String DEFAULT_URL = "http://default.example.com";
    protected static final String DEFAULT_EMAIL = "default@example.com";

    private static int PAGE_SIZE = 20;

    private static final Logger LOG = Logger.getLogger(AbstractPoWebTest.class);

    protected static final String SELECT_A_COUNTRY = "--Select a Country--";

    protected static final String SELECT_A_STATUS = "--Select a Status--";

    protected static final String COUNTRY_MUST_BE_SET = "Country must be set";

    protected static final String STATUS_MUST_BE_SET = "Status must be set";

    protected static final String ALIAS_STRING = "test_alias_123";

    protected Connection conn = null;
    private ResultSetHandler<Object[]> h = null;

    static {
        new Timer(true).schedule(new TimerTask() {
            @SuppressWarnings("rawtypes")
            @Override
            public void run() {
                if (SystemUtils.IS_OS_WINDOWS) {
                    return;
                }
                System.out
                        .println("---------------------------------------------------------------------------------");
                System.out
                        .println("I am a periodic thread dump logger. Please excuse me for verbose output and ignore for now.");
                Map allThreads = Thread.getAllStackTraces();
                Iterator iterator = allThreads.keySet().iterator();
                StringBuffer stringBuffer = new StringBuffer();
                while (iterator.hasNext()) {
                    Thread key = (Thread) iterator.next();
                    StackTraceElement[] trace = (StackTraceElement[]) allThreads
                            .get(key);
                    stringBuffer.append(key + "\r\n");
                    for (int i = 0; i < trace.length; i++) {
                        stringBuffer.append(" " + trace[i] + "\r\n");
                    }
                    stringBuffer.append("\r\n");
                }
                System.out.println(stringBuffer);
            }
        }, 120000, 120000);
    }

    @Override
    public void setUp() throws Exception {
        super.setServerHostname(TstProperties.getServerHostname());
        super.setServerPort(TstProperties.getServerPort());
        super.setDriverClass(TstProperties.getDriverClass());
        // super.setDriverClass(PHANTOM_JS_DRIVER);
        System.setProperty("phantomjs.binary.path",
                TstProperties.getPhantomJsPath());

        // getting the database connection
        conn = DataGeneratorUtil.getJDBCConnection();
        // Create a ResultSetHandler implementation to convert the first row
        // into an Object[].
        h = new ResultSetHandler<Object[]>() {
            public Object[] handle(ResultSet rs) throws SQLException {
                if (!rs.next()) {
                    return null;
                }
                ResultSetMetaData meta = rs.getMetaData();
                int cols = meta.getColumnCount();
                Object[] result = new Object[cols];
                for (int i = 0; i < cols; i++) {
                    result[i] = rs.getObject(i + 1);
                }
                return result;
            }
        };

        super.setUp();
    }

    @Override
    public void tearDown() throws Exception {
        takeScreenShot();
        logoutUser();
        closeBrowser();
        DbUtils.closeQuietly(conn);
        super.tearDown();
    }

    protected void takeScreenShot() {
        try {
            final String screenShotFileName = getClass().getSimpleName()
                    + "_ScreenShot_"
                    + new Timestamp(System.currentTimeMillis()).toString()
                            .replaceAll("\\D+", "_") + ".png";
            File destFile = new File(SystemUtils.JAVA_IO_TMPDIR,
                    screenShotFileName);

            File scrFile = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, destFile);
            System.out.println("Saved screen shot: "
                    + destFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @SuppressWarnings("deprecation")
    protected void reInitializeWebDriver() throws Exception {
        closeBrowser();
        super.tearDown();
        setUp();
    }

    /**
     * @return
     */
    private boolean isPhantomJS() {
        return driver.getClass().getName().equals(PHANTOM_JS_DRIVER);
    }

    /**
     * 
     */
    private void open(String url) {
        if (!isPhantomJS()) {
            selenium.open(url);
        } else {
            openAndHandleStuckPhantomJsDriver(url);
        }
    }

    private void openAndHandleStuckPhantomJsDriver(final String url) {
        int tries = 0;
        while (tries < 5) {
            tries++;
            Future<Boolean> f = Executors.newSingleThreadExecutor().submit(
                    new Callable<Boolean>() {
                        @Override
                        public Boolean call() throws Exception {
                            selenium.open(url);
                            return true;
                        }
                    });
            try {
                if (f.get(30, TimeUnit.SECONDS)) {
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out
                        .println("PhantomJS stuck in 'get' (an odd issue on Linux); restarting and trying again. Attempt # "
                                + tries);
            }
            f.cancel(true);
            try {
                this.reInitializeWebDriver();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private void closeBrowser() {
        driver.quit();
    }

    protected void logoutUser() {
        openAndWait("/po-web/login/logout.action");
    }

    protected void login(String username, String password) {
        performLoginAndAcceptDisclaimer(username, password);
        assertTrue(selenium.isElementPresent("id=EntityInboxOrganization"));
        assertTrue(selenium.isElementPresent("id=SearchOrganization"));
        assertTrue(selenium.isElementPresent("id=CreateOrganization"));
        assertTrue(selenium.isElementPresent("id=ImportCtepOrgs"));
        assertTrue(selenium.isElementPresent("id=EntityInboxPerson"));
        assertTrue(selenium.isElementPresent("id=SearchPerson"));
        assertTrue(selenium.isElementPresent("id=CreatePerson"));
        assertTrue(selenium.isElementPresent("id=ImportCtepPeople"));
        assertTrue(selenium.isElementPresent("id=ListFamily"));
        assertTrue(selenium.isElementPresent("id=CreateFamily"));
        System.out.println("Completed login().");
    }

    /**
     * @param username
     * @param password
     */
    protected void performLoginAndAcceptDisclaimer(String username,
            String password) {
        attemptLogin(username, password);
        assertTrue(selenium.isElementPresent("id=Help"));
        assertTrue(selenium.isElementPresent("id=Logout"));
        assertTrue(selenium.isElementPresent("id=accept_disclaimer"));
        assertTrue(selenium.isElementPresent("id=reject_disclaimer"));
        System.out.println("About to click id=accept_disclaimer...");
        clickAndWait("id=accept_disclaimer");
    }

    /**
     * @param username
     * @param password
     */
    protected void attemptLogin(String username, String password) {
        System.out.println("About to call application...");
        open("/po-web");
        assertTrue(selenium.isTextPresent("Login"));
        assertTrue(selenium.isTextPresent("CONTACT US"));
        System.out.println("About to click Login link...");
        clickAndWait("link=Login");
        selenium.type("j_username", username);
        selenium.type("j_password", password);
        clickAndWait("id=loginButton");
    }

    public void loginAsCurator() {
        login("curator", "pass");
    }

    public void loginAsJohnDoe() {
        login("jdoe01", "pass");
    }

    protected boolean isLoggedIn() {
        return selenium.isElementPresent("link=Logout")
                && !selenium.isElementPresent("link=Login");
    }

    protected void openCreateOrganization() {
        goHome();
        clickAndWait("CreateOrganization");
        waitForTelecomFormsToLoad();
        waitForAliasFormsToLoad();
    }

    protected void openOrganizationFamilyList() {
        goHome();
        clickAndWait("ListFamily");
        waitForElementById("createNewFamily", 10);
    }

    protected void openOrganizationFamilyCreate() {
        goHome();
        clickAndWait("CreateFamily");
        waitForElementById("save_button", 10);
    }

    protected void waitForTelecomFormsToLoad() {
        waitForElementById("emailEntry_value", 10);
        waitForElementById("phoneEntry_value", 10);
        waitForElementById("faxEntry_value", 10);
        waitForElementById("ttyEntry_value", 10);
        waitForElementById("urlEntry_value", 10);
    }

    protected void waitForAliasFormsToLoad() {
        waitForElementById("alias_value", 10);
    }

    private void goHome() {
        if (!isLoggedIn()) {
            loginAsCurator();
        }
        openAndWait("/po-web/protected/home.action");
    }

    protected void openAndWait(String url) {
        open(url);
        waitForPageToLoad();
    }

    protected void openCreatePerson() {
        goHome();
        clickAndWait("CreatePerson");
        waitForTelecomFormsToLoad();
    }

    protected void openSearchPerson() {
        goHome();
        clickAndWait("SearchPerson");
    }

    protected void openSearchOrganization() {
        goHome();
        clickAndWait("SearchOrganization");
    }

    /**
     * Searches in the given column for the given text
     * 
     * @param text
     *            - string to search for
     * @param column
     *            - table column to search in
     * @return the row number where the text was found. -1 if not found
     */
    protected int getRow(String text, int column) {
        return getRowHelper(text, column, false);
    }

    /**
     * Searches in the given column for the given text
     * 
     * @param text
     *            - string to search for (contains search)
     * @param column
     *            - table column to search in
     * @return the row number where the text was found. -1 if not found
     */
    protected int getRowThatContainsText(String text, int column) {
        return getRowHelper(text, column, true);
    }

    private int getRowHelper(String text, int column, boolean contains) {
        String tblValue = null;
        for (int row = 1;; row++) {
            tblValue = null;
            try {
                tblValue = selenium.getTable("row." + row + "." + column);
            } catch (RuntimeException e) {
                if (tblValue == null) {
                    // there are no rows on the page
                    return -1;
                }
                LOG.info("problem looking for " + text + " at (" + row + ","
                        + column + ") Stopped at : " + tblValue);
                throw e;
            }
            if (contains && tblValue.contains(text)) {
                return row;
            } else if (text.equalsIgnoreCase(tblValue)) {
                return row;
            }

            if (row % PAGE_SIZE == 0) {
                // Moving to next page
                // this will fail once there are no more pages and the text
                // parameter is not found
                try {
                    clickAndWait("link=Next");
                } catch (Exception e1) {
                    return -1;
                }
                pause(2000);
                row = 0;
            }
        }
    }

    protected void clickLinkInTable(String locator) {
        if (selenium.isElementPresent(locator)) {
            clickAndWait(locator);
            return;
        } else {
            while (selenium.isElementPresent("link=Next")) {
                selenium.click("link=Next");
                pause(2000);
                if (selenium.isElementPresent(locator)) {
                    clickAndWait(locator);
                    return;
                }
            }
        }
        fail("Locator not found in table: " + locator);
    }

    /**
     * Verify the Telecom fields behave properly
     */
    protected void verifyTelecom() {
        verifyEmail();
        verifyPhone();
        verifyFax();
        verifyTty();
        verifyUrl();
    }

    private void verifyEmail() {
        waitForElementById("email-remove-0", 5);
        assertEquals(DEFAULT_EMAIL + " | Remove",
                selenium.getText("email-entry-0"));

        waitForElementById("emailEntry_value", 5);
        clear("emailEntry_value");
        selenium.click("email-add");
        waitForElementById("wwerr_emailEntry_value", 5);
        assertTrue(selenium.isTextPresent("Email Address must be set"));

        waitForElementById("emailEntry_value", 5);
        selenium.type("emailEntry_value", "abc@example.com");
        selenium.click("email-add");
        waitForElementById("email-remove-1", 5);
        assertEquals("abc@example.com | Remove",
                selenium.getText("email-entry-1"));

        waitForElementById("emailEntry_value", 5);
        selenium.type("emailEntry_value", "example.com");
        selenium.click("email-add");
        waitForElementById("wwerr_emailEntry_value", 5);
        assertTrue(selenium
                .isTextPresent("Email Address is not a well-formed email address"));

        clear("emailEntry_value");
    }

    /**
     * Verify the Alias behave properly at Org & OrgRole level.
     */
    protected void verifyAlias() {
        waitForElementById("alias_value", 5);
        selenium.type("alias_value", ALIAS_STRING);
        selenium.click("alias-add");
        waitForElementById("alias-remove-0", 5);
        assertEquals("test_alias_123 | Remove",
                selenium.getText("alias-entry-0"));

        waitForElementById("alias_value", 5);
        selenium.type("alias_value", " ");// left blank
        selenium.click("alias-add");
        pause(1000);
        assertTrue(selenium.isTextPresent("Alias must be set"));

        clear("alias_value");
    }

    /**
     * Verify an existing Alias at Org & OrgRole level.
     */
    protected void verifyExistingAlias() {
        waitForElementById("alias-remove-0", 5);
        assertEquals("test_alias_123 | Remove",
                selenium.getText("alias-entry-0"));
    }

    private void clear(String locator) {
        selenium.type(locator, "");
    }

    private void verifyPhone() {
        waitForElementById("phoneEntry_value", 5);
        selenium.type("phoneEntry_value", "");
        selenium.click("phone-add");
        waitForElementById("wwerr_phoneEntry_value", 5);
        assertTrue(selenium.isTextPresent("Phone must be set"));

        waitForElementById("phoneEntry_value", 5);
        selenium.type("phoneEntry_value", "123-456-7890");
        selenium.click("phone-add");
        waitForElementById("phone-remove-0", 5);
        assertEquals("123-456-7890 | Remove", selenium.getText("phone-entry-0"));

        waitForElementById("phoneEntry_value", 5);
        selenium.type("phoneEntry_value", "1234567890123456789012345678901");
        selenium.click("phone-add");
        waitForElementById("wwerr_phoneEntry_value", 5);
        assertTrue(selenium
                .isTextPresent("Phone length must be between 0 and 30"));

        clear("phoneEntry_value");
    }

    private void verifyFax() {
        waitForElementById("faxEntry_value", 5);
        selenium.type("faxEntry_value", "");
        selenium.click("fax-add");
        waitForElementById("wwerr_faxEntry_value", 5);
        assertTrue(selenium.isTextPresent("Fax must be set"));

        waitForElementById("faxEntry_value", 5);
        selenium.type("faxEntry_value", "234-567-8901");
        selenium.click("fax-add");
        waitForElementById("fax-remove-0", 5);
        assertEquals("234-567-8901 | Remove", selenium.getText("fax-entry-0"));

        waitForElementById("faxEntry_value", 5);
        selenium.type("faxEntry_value", "1234567890123456789012345678901");
        selenium.click("fax-add");
        waitForElementById("wwerr_faxEntry_value", 5);
        assertTrue(selenium
                .isTextPresent("Fax length must be between 0 and 30"));

        clear("faxEntry_value");
    }

    private void verifyTty() {
        waitForElementById("ttyEntry_value", 5);
        selenium.type("ttyEntry_value", "");
        selenium.click("tty-add");
        waitForElementById("wwerr_ttyEntry_value", 5);
        assertTrue(selenium.isTextPresent("TTY must be set"));

        waitForElementById("ttyEntry_value", 5);
        selenium.type("ttyEntry_value", "345-678-9012");
        selenium.click("tty-add");
        waitForElementById("tty-remove-0", 5);
        assertEquals("345-678-9012 | Remove", selenium.getText("tty-entry-0"));

        waitForElementById("ttyEntry_value", 5);
        selenium.type("ttyEntry_value", "1234567890123456789012345678901");
        selenium.click("tty-add");
        waitForElementById("wwerr_ttyEntry_value", 5);
        assertTrue(selenium
                .isTextPresent("TTY length must be between 0 and 30"));

        clear("ttyEntry_value");
    }

    private void verifyUrl() {
        waitForElementById("url-remove-0", 5);
        assertEquals(DEFAULT_URL + " | Remove", selenium.getText("url-entry-0"));

        waitForElementById("urlEntry_value", 5);
        selenium.type("urlEntry_value", "");
        selenium.click("url-add");
        waitForElementById("wwerr_urlEntry_value", 5);
        assertTrue(selenium.isTextPresent("URL must be set"));

        waitForElementById("urlEntry_value", 5);
        selenium.type("urlEntry_value", "http://www.example.com");
        selenium.click("url-add");
        waitForElementById("url-remove-1", 5);
        assertEquals("http://www.example.com | Remove",
                selenium.getText("url-entry-1"));

        waitForElementById("urlEntry_value", 5);
        selenium.type("urlEntry_value", "example.com");
        selenium.click("url-add");
        waitForElementById("wwerr_urlEntry_value", 5);
        assertTrue(selenium.isTextPresent("URL is not well formed"));

        clear("urlEntry_value");
    }

    protected void verifyPostalAddress(ENTITYTYPE type) {
        assertEquals(
                "123 abc ave.",
                selenium.getValue("curateEntityForm_" + type.name()
                        + "_postalAddress_streetAddressLine"));
        assertEquals(
                "",
                selenium.getValue("curateEntityForm_" + type.name()
                        + "_postalAddress_deliveryAddressLine"));
        assertEquals(
                "mycity",
                selenium.getValue("curateEntityForm_" + type.name()
                        + "_postalAddress_cityOrMunicipality"));
        assertEquals(
                "VA",
                selenium.getValue(type.name()
                        + ".postalAddress.stateOrProvince"));
        assertEquals(
                "12345",
                selenium.getValue("curateEntityForm_" + type.name()
                        + "_postalAddress_postalCode"));
    }

    protected void createPerson(String status, String prefix, String fName,
            String mName, String lName, String suffix, Address address,
            String email, String phone, String url, String fax) {
        // wait for ajax page content to load
        waitForElementById("emailEntry_value", 10);
        waitForElementById("phoneEntry_value", 10);
        waitForElementById("urlEntry_value", 10);
        waitForElementById("faxEntry_value", 10);
        // add person info
        selenium.select("curateEntityForm.person.statusCode", "label=" + status);
        selenium.type("curateEntityForm_person_prefix", prefix);
        selenium.type("curateEntityForm_person_firstName", fName);
        selenium.type("curateEntityForm_person_middleName", mName);
        selenium.type("curateEntityForm_person_lastName", lName);
        selenium.type("curateEntityForm_person_suffix", suffix);
        // add postal addresss info
        inputAddressInfo(address, "curateEntityForm", "person");
        // add contact info
        inputContactInfo(email, phone, fax, null, url);
        // save the person
        clickAndWaitSaveButton();
        // verify person created message
        assertTrue("Success message is missing",
                selenium.isTextPresent("Person was successfully created"));
    }

    protected void createOrganization(String status, String name,
            Address address, String email, String phone, String fax,
            String tty, String url) {
        openCreateOrganization();
        selenium.select("curateEntityForm.organization.statusCode", "label="
                + status);
        selenium.type("curateEntityForm_organization_name", name);

        // add postal address info
        inputAddressInfo(address, "curateEntityForm", "organization");

        // add contact info
        inputContactInfo(email, phone, fax, tty, url);

        // add Alias
        inputAlias();

        // save the organization
        clickAndWaitSaveButton();
        // verify organization created message
        assertTrue("Success message is missing",
                selenium.isTextPresent("Organization was successfully created"));
    }

    protected void createGenericOrganizationalContact(String status,
            String title, String type, Address address, String email,
            String phone, String fax, String tty, String url, boolean verify) {
        waitForTelecomFormsToLoad();
        selenium.type("curateRoleForm_role_title", title);
        selenium.select("curateRoleForm.role.status", "label=" + status);
        selenium.select("curateRoleForm.role.type", "label=" + type);

        // add postal address info
        if (address != null) {
            inputAddressInfoPopup(address);
            waitForTelecomFormsToLoad();
        }

        // add contact info
        inputContactInfo(email, phone, fax, tty, url);

        // save the OC
        clickAndWaitSaveButton();
        if (verify) {
            // verify OC created message
            assertTrue(
                    "Success message is missing",
                    selenium.isTextPresent("Organizational Contact was successfully created"));
        }
    }

    protected void inputAddressInfo(Address address, String formName,
            String objectType) {
        selenium.select(formName + "." + objectType + ".postalAddress.country",
                "label=" + address.getCountry());
        waitForElementById(objectType + ".postalAddress.stateOrProvince", 10);
        selenium.type(formName + "_" + objectType
                + "_postalAddress_streetAddressLine",
                address.getStreetAddressLine());
        selenium.type(formName + "_" + objectType
                + "_postalAddress_deliveryAddressLine",
                address.getDeliveryAddressLine());
        selenium.type(formName + "_" + objectType
                + "_postalAddress_cityOrMunicipality",
                address.getCityOrMunicipality());
        selenium.select(objectType + ".postalAddress.stateOrProvince", "value="
                + address.getStateOrProvince());
        selenium.type(
                formName + "_" + objectType + "_postalAddress_postalCode",
                address.getPostalCode());
    }

    protected void inputAddressInfoPopup(Address address) {
        clickAndWait("add_address");
        pause(3000);
        selenium.selectFrame("popupFrame");
        selenium.select("postalAddressForm.address.country",
                "label=" + address.getCountry());
        waitForElementById("address.stateOrProvince", 10);
        selenium.type("postalAddressForm_address_streetAddressLine",
                address.getStreetAddressLine());
        selenium.type("postalAddressForm_address_deliveryAddressLine",
                address.getDeliveryAddressLine());
        selenium.type("postalAddressForm_address_cityOrMunicipality",
                address.getCityOrMunicipality());
        selenium.select("address.stateOrProvince",
                "value=" + address.getStateOrProvince());
        selenium.type("postalAddressForm_address_postalCode",
                address.getPostalCode());
        selenium.click("id=submitPostalAddressForm");
        pause(10000);
        driver.switchTo().defaultContent();
    }

    protected void inputContactInfo(String email, String phone, String fax,
            String tty, String url) {

        inputEmailAndUrl(email, url);

        if (StringUtils.isNotBlank(phone)) {
            selenium.type("phoneEntry_value", phone);
            selenium.click("phone-add");
            waitForElementById("phone-entry-0", 5);
        }
        if (StringUtils.isNotBlank(fax)) {
            selenium.type("faxEntry_value", fax);
            selenium.click("fax-add");
            waitForElementById("fax-entry-0", 5);
        }
        if (StringUtils.isNotBlank(tty)) {
            selenium.type("ttyEntry_value", tty);
            selenium.click("tty-add");
            waitForElementById("tty-entry-0", 5);
        }

    }

    protected void inputContactInfoForUSAndCan(String email, String[] phone,
            String[] fax, String[] tty, String url) {
        inputContactInfo(email, StringUtils.join(phone, "-"),
                StringUtils.join(fax, "-"), StringUtils.join(tty, "-"), url);
    }

    private void inputEmailAndUrl(String email, String url) {
        if (StringUtils.isNotBlank(email)) {
            selenium.type("emailEntry_value", email);
            selenium.click("email-add");
            waitForElementById("email-entry-0", 5);
        }

        if (StringUtils.isNotBlank(url)) {
            selenium.type("urlEntry_value", url);
            selenium.click("url-add");
            waitForElementById("url-entry-0", 5);
        }
    }

    protected void inputForTel(String[] tel, String type) {
        if (tel.length >= 3) {
            selenium.type(type + "Entry_part1", tel[0]);
            selenium.type(type + "Entry_part2", tel[1]);
            selenium.type(type + "Entry_part3", tel[2]);
            if (tel.length == 4) {
                selenium.type(type + "Entry_part4", tel[3]);
            }
            selenium.click("//div[@id='us_format_" + type + "']//a[@id='"
                    + type + "-add']");
            waitForElementById(type + "-entry-0", 5);
        }
    }

    protected void inputNonUSFormatTel(String tel, String type) {
        selenium.type(type + "Entry_value", tel);
        selenium.click("//div[@id='no_format_" + type + "']//a[@id='" + type
                + "-add']");
        waitForElementById(type + "-entry-0", 5);
    }

    protected void inputAlias() {
        selenium.type("alias_value", ALIAS_STRING);
        selenium.click("alias-add");
        waitForElementById("alias-entry-0", 5);
    }

    public Address getAddress() {
        return new Address("123 Main Street", "40 5th Street", "Ashburn", "VA",
                "20147", "United States");
    }

    public Address getForeignAddress() {
        return new Address("123 Main Street", "40 5th Street", "Bogata", null,
                "20147", "Columbia");
    }

    protected String createPerson() {
        String lname = "lastName" + System.currentTimeMillis();
        createPerson("PENDING", "Dr", "Jakson", "L", lname, "III",
                getAddress(), "sample@example.com", "703-111-2345",
                "http://www.example.com", "703-111-1234");
        return lname;
    }

    protected String createOrganization() {
        String name = "orgName" + System.currentTimeMillis();
        createOrganization("ACTIVE", name, getAddress(), "sample@example.com",
                "703-111-2345", "703-111-1234", null, "http://www.example.com");
        return name;
    }

    protected void accessManageClinicalResearchStaffScreen() {
        clickAndWait("link=Manage Clinical Research Staff(s)");
        assertTrue(selenium
                .isTextPresent("Clinical Research Staff Information"));
    }

    protected void accessManageIdentifiedOrganizationScreen() {
        clickAndWait("link=Identified Org (0)");
    }

    protected void accessManageOrganizationalContactScreen() {
        if (selenium.isElementPresent("link=Org Contact (0)")) {
            clickAndWait("link=Org Contact (0)");
        } else if (selenium.isElementPresent("link=OC (0)")) {
            clickAndWait("link=OC (0)");
        }
    }

    protected void accessManageIdentifiedPersonScreen() {
        clickAndWait("link=OPI (0)");
    }

    protected void accessManageResearchOrganizationScreen() {
        clickAndWait("link=RO (0)");
    }

    protected void accessManageHealthCareProviderScreen() {
        clickAndWait("link=Manage Health Care Provider(s)");
        assertTrue(selenium.isTextPresent("Health Care Provider Information"));
    }

    protected void accessFamilyScreen() {
        clickAndWait("link=Manage Family(s)");
        assertTrue(selenium
                .isTextPresent("Family Organization Relationship Information"));
    }

    protected void selectOrganizationScoper(String orgId, String orgName) {
        clickAndWait("select_scoper");
        selenium.selectFrame("popupFrame");
        selenium.type("duplicateOrganizationForm_criteria_id", orgId);
        /* search for dups */
        selenium.click("//a[@id='submitDuplicateOrganizationForm']/span/span");
        /* wait for results to load */
        waitForElementById("mark_as_dup_" + orgId, 10);
        /* select record to use at duplicate */
        clickAndWait("mark_as_dup_" + orgId);
        driver.switchTo().defaultContent();
        assertEquals(orgName + " (" + orgId + ")",
                selenium.getText("wwctrl_curateRoleForm_role_scoper_id"));
    }

    /**
     * Use this to add postal addresses using the popup. Verifies values exist
     * somewhere on the page after the popup save button is pressed.
     * Unfortunately, we're unable to control the order the postalAdresses are
     * displayed (backed by a HashSet), so we can only verify the existence of
     * text on the page.
     * 
     * *** SPECIFY UNIQUE VALUES ON THE PAGE
     */
    protected void addPostalAddressUsingPopup(String street1, String street2,
            String city, String stateCode, String zip, String countryName,
            int totalNumberOfAddressesAfterAdd) {
        assertTrue(totalNumberOfAddressesAfterAdd > 0);
        waitForElementById("add_address", 10);
        clickAndWait("add_address");
        selenium.selectFrame("popupFrame");
        selenium.select("postalAddressForm.address.country", "label="
                + countryName);
        // might need to wait for //div[@id=address.div_stateOrProvince] to
        // reload
        pause(3000);
        waitForElementById("address.stateOrProvince", 10);

        selenium.type("postalAddressForm_address_streetAddressLine", street1);
        selenium.type("postalAddressForm_address_deliveryAddressLine", street2);
        selenium.type("postalAddressForm_address_cityOrMunicipality", city);

        try {
            selenium.select("address.stateOrProvince", "value=" + stateCode);
        } catch (Exception e) {
            System.out.println("Oops; not a SELECT element.");
            selenium.type("address.stateOrProvince", stateCode);
        }

        selenium.type("postalAddressForm_address_postalCode", zip);
        selenium.click("//a[@id='submitPostalAddressForm']/span/span");
        driver.switchTo().defaultContent();
        totalNumberOfAddressesAfterAdd--;
        waitForElementById("postalAddress" + totalNumberOfAddressesAfterAdd, 10);
        selenium.isTextPresent(street1);
        selenium.isTextPresent(street2);
        selenium.isTextPresent(city);
        selenium.isTextPresent(stateCode);
        selenium.isTextPresent(zip);
        selenium.isTextPresent(countryName);
    }

    public enum ENTITYTYPE {
        person, organization;
    }

    protected Ii createRemoteOrg(String orgName)
            throws EntityValidationException, NamingException,
            URISyntaxException, CurationException {

        OrganizationDTO dto = new OrganizationDTO();
        dto.setName(TestConvertHelper.convertToEnOn(orgName));
        dto.setPostalAddress(TestConvertHelper.createAd("123 abc ave.", null,
                "mycity", "WY", "12345", "USA"));
        DSet<Tel> telco = new DSet<Tel>();
        telco.setItem(new HashSet<Tel>());
        dto.setTelecomAddress(telco);

        TelEmail email = new TelEmail();
        email.setValue(new URI("mailto:" + DEFAULT_EMAIL));
        dto.getTelecomAddress().getItem().add(email);

        TelUrl url = new TelUrl();
        url.setValue(new URI(DEFAULT_URL));
        dto.getTelecomAddress().getItem().add(url);
        Ii id = RemoteServiceHelper.getOrganizationEntityService()
                .createOrganization(dto);
        dto.setIdentifier(id);
        return id;
    }

    protected void savePersonAsActive(Ii id) {
        selenium.select("curateEntityForm.person.statusCode", "label=ACTIVE");
        clickAndWaitSaveButton();
        assertEquals("PO: Persons and Organizations - Person Details",
                selenium.getTitle());
        assertFalse(selenium.isElementPresent("//a[@id='person_id_"
                + id.getExtension() + "']/span/span"));
    }

    protected void saveOrganizationAsActive(Ii id) {
        selenium.select("curateEntityForm.organization.statusCode",
                "label=ACTIVE");
        clickAndWaitSaveButton();
        assertTrue("Organization was successfully created!",
                selenium.isTextPresent("Organization was successfully created"));
        assertEquals("PO: Persons and Organizations - Person Details",
                selenium.getTitle());
        assertFalse(selenium.isElementPresent("//a[@id='organization_id_"
                + id.getExtension() + "']/span/span"));
    }

    protected void verifyPresenceOfRequiredIndicator(boolean expectedValue,
            String labelFor) {
        assertEquals(expectedValue, isRequiredIndicatorPresent(labelFor));
    }

    private boolean isRequiredIndicatorPresent(String labelFor) {
        String xpath = "//label[@for='" + labelFor
                + "']/span[@class='required' and ./text()='*']";
        return selenium.isElementPresent(xpath);
    }

    protected void verifyReliabilityChange() {
        String confirmationMessagePattern = "^Changing the reliability is usually not recommended\\. Are you sure you want to continue[\\s\\S]$";
        String elementId = "curateRoleForm.role.assignedIdentifier.reliability";
        String labelVRF = "label=VRF";
        String labelUNV = "label=UNV";
        String labelSelectReliability = "label=--Select a Reliability--";

        /*
         * 1. Set value to Select A Reliability. 2. Select VRF. Verify that
         * there was no confirmation message. 3. Switch value to UNF. Verify the
         * presence of a confirmation message. 4. Switch value to Select A
         * Reliability. Verify the presence of a confirmation message. 5. Switch
         * value to UNV. Verify the absence of a confirmation message. 6. Switch
         * back to Select A Reliability. 7. Test that the Cancel button behaves
         * as expected. 8. Test that the OK button behaves as expected.
         */

        selenium.select(elementId, labelSelectReliability);
        selenium.select(elementId, labelVRF);
        assertFalse(selenium.isConfirmationPresent());
        selenium.select(elementId, labelUNV);
        assertTrue(selenium.isConfirmationPresent());
        assertTrue(selenium.getConfirmation().matches(
                confirmationMessagePattern));
        selenium.select(elementId, labelSelectReliability);
        assertTrue(selenium.isConfirmationPresent());
        assertTrue(selenium.getConfirmation().matches(
                confirmationMessagePattern));
        selenium.select(elementId, labelUNV);
        assertFalse(selenium.isConfirmationPresent());
        selenium.select(elementId, labelSelectReliability);
        assertTrue(selenium.isConfirmationPresent());
        assertTrue(selenium.getConfirmation().matches(
                confirmationMessagePattern));

        selenium.select(elementId, labelUNV);
        // handle pressing cancel on confirmation for changing reliability
        // press cancel to abort the value change
        selenium.chooseCancelOnNextConfirmation();
        selenium.select(elementId, labelVRF);
        assertTrue(selenium.isConfirmationPresent());
        assertTrue(selenium.getConfirmation().matches(
                confirmationMessagePattern));
        assertEquals("UNV", selenium.getValue(elementId).trim());
        // press OK to accept the change
        selenium.select(elementId, labelVRF);
        assertTrue(selenium.isConfirmationPresent());
        assertTrue(selenium.getConfirmation().matches(
                confirmationMessagePattern));
        assertEquals("VRF", selenium.getValue(elementId).trim());
    }
    

    protected void setCreatedByInOrg(Long orgId) {
        long curatorDbId = 0;
        try {
            QueryRunner queryRunner = new QueryRunner();
            Object[] result = queryRunner.query(conn,
                    "select user_id from csm_user where login_name ='curator'",
                    h);
            // get the DB Id of 'curator'
            curatorDbId = ((Long) result[0]).longValue();

            // set 'createdBy' in the Org
            queryRunner.update(conn,
                    "update organization set created_by_id = ? WHERE id = ?",
                    curatorDbId, orgId);

        } catch (SQLException e) {
            Assert.fail("Exception occured inside setCreatedByInOrg. The exception is: "
                    + e);
        }
    }

    protected Number createCSMUser(String loginName) throws SQLException {

        QueryRunner runner = new QueryRunner();
        String idSql = "select max(user_id) + 1 from csm_user";

        Number id = (Number) runner.query(conn, idSql, new ArrayHandler())[0];
        String sql = "INSERT INTO csm_user (user_id, login_name,first_name,last_name,update_date,migrated_flag) VALUES ("
                + id.intValue()
                + ", "
                + "'"
                + loginName
                + "', '', '', now(),0)";

        runner.update(conn, sql);

        return id;
    }

    protected void assignUserToGroup(Number userID, String group)
            throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "insert into csm_user_group (user_id, group_id) values ("
                + userID
                + ", (select group_id from csm_group where group_name='"
                + group + "')  )";
        runner.update(conn, sql);
    }

    protected boolean isUserInGroup(Number userID, String group)
            throws SQLException {
        QueryRunner runner = new QueryRunner();
        final String sql = "select user_group_id from csm_user_group where user_id="
                + userID
                + " and group_id=(select group_id from csm_group where group_name='"
                + group + "')";
        final Object[] results = runner.query(conn, sql, new ArrayHandler());
        return results != null;
    }


    protected void enterDate(String selector, String value) {
        selenium.type(selector, value);
        ((JavascriptExecutor) driver).executeScript("jQuery(\"div.ui-datepicker\").hide()");    //hide popup
    }
}
