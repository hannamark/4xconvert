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
package gov.nih.nci.coppa.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Environment properties passed in to tests.
 */
public final class TstProperties {

    public static final String SERVER_HOSTNAME_KEY = "server.hostname";
    public static final String SERVER_PORT_KEY = "server.port";
    public static final String SERVER_JNDI_PORT_KEY = "server.jndi.port";

    public static final String SERVER_HOSTNAME_DEFAULT = "localhost";
    public static final String SERVER_PORT_DEFAULT = "39080";
    public static final String SERVER_JNDI_PORT_DEFAULT = "31099";

    public static final String WEBDRIVER_CLASS_KEY = "webdriver.class";
    public static final String WEBDRIVER_CLASS_DEFAULT = "org.openqa.selenium.firefox.FirefoxDriver";
    public static final String WEBDRIVER_PHANTOMJS_PATH = "phantomjs.binary.path";
    private static final String WEBDRIVER_PHANTOMJS_PATH_DEFAULT = "/local/home/cislave/phantomjs-1.9.7-linux-x86_64/bin/phantomjs";

    public static final String JMX_USERNAME_KEY = "jboss.jmx.username";
    public static final String JMS_USERNAME_DEFAULT = "admin";

    public static final String JMX_PASSWORD_KEY = "jboss.jmx.password";
    public static final String JMS_PASSWORD_DEFAULT = "admin";
    public static final String WS_CLIENT_USERNAME = "webservice.client.username";
    public static final String WS_CLIENT_PASSWORD = "webservice.client.password";

    public static Properties properties = new Properties();
    static {
        try {
            InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("test.properties");
            properties.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Unable to load test.properties; using defaults.");
        }
    }

    public static String getServerHostname() {
        return properties.getProperty(SERVER_HOSTNAME_KEY, SERVER_HOSTNAME_DEFAULT);
    }

    public static int getServerPort() {
        return Integer.parseInt(properties.getProperty(SERVER_PORT_KEY, SERVER_PORT_DEFAULT));
    }

    public static int getServerJndiPort() {
        return Integer.parseInt(properties.getProperty(SERVER_JNDI_PORT_KEY, SERVER_JNDI_PORT_DEFAULT));
    }

    public static String getDriverClass() {
        return properties.getProperty(WEBDRIVER_CLASS_KEY, WEBDRIVER_CLASS_DEFAULT);
    }
    
    public static String getPhantomJsPath() {
        return properties.getProperty(WEBDRIVER_PHANTOMJS_PATH, WEBDRIVER_PHANTOMJS_PATH_DEFAULT);
    }


    public static String getJmxUsername() {
        return properties.getProperty(JMX_USERNAME_KEY, JMS_USERNAME_DEFAULT);
    }

    public static String getJmxPassword() {
        return properties.getProperty(JMX_PASSWORD_KEY, JMS_PASSWORD_DEFAULT);
    }
    
    public static String getPersonServiceURL() {
        return "http://" + getServerHostname() + ":"
                + getServerPort()
                + "/po-webservices/services/PersonService?wsdl";
    }

    public static String getPersonRESTServiceURL() {
        return "http://" + getServerHostname() + ":"
                + getServerPort()
                + "/po-webservices/services/person-rest-service";
    }

    public static String getOrgServiceURL() {
        return "http://" + getServerHostname() + ":"
                + getServerPort()
                + "/po-webservices/services/OrganizationService?wsdl";
    }
    
    public static String getOrgRESTServiceURL() {
        return "http://" + getServerHostname() + ":"
                + getServerPort()
                + "/po-webservices/services/organization-rest-service";
    }

    public static String getFamilyServiceURL() {
        return "http://" + getServerHostname() + ":"
                + getServerPort()
                + "/po-webservices/services/FamilyService?wsdl";
    }
    
    public static String getFamilyRESTServiceURL() {
        return "http://" + getServerHostname() + ":"
                + getServerPort()
                + "/po-webservices/services/family-rest-service";
    }

    public static String getWebserviceUsername() {
        return properties.getProperty(WS_CLIENT_USERNAME,"jdoe01");
    }

    public static String getWebservicePassword() {
        return properties.getProperty(WS_CLIENT_PASSWORD, "pass");
    }
}
