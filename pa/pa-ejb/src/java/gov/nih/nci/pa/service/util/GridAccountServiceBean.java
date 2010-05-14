/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The pa
 * Software was developed in conjunction with the National Cancer Institute 
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent 
 * government employees are authors, any rights in such works shall be subject 
 * to Title 17 of the United States Code, section 105. 
 *
 * This pa Software License (the License) is between NCI and You. You (or 
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
 * its rights in the pa Software to (i) use, install, access, operate, 
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the pa Software; (ii) distribute and 
 * have distributed to and by third parties the pa Software and any 
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
package gov.nih.nci.pa.service.util;

import gov.nih.nci.cagrid.opensaml.SAMLAssertion;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.enums.USStateCode;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PaEarPropertyReader;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.security.cgmm.CGMMManager;
import gov.nih.nci.security.cgmm.CGMMManagerImpl;
import gov.nih.nci.security.cgmm.helper.impl.SAMLToAttributeMapperImpl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.log4j.Logger;

import org.cagrid.gaards.authentication.BasicAuthentication;
import org.cagrid.gaards.authentication.client.AuthenticationClient;
import org.cagrid.gaards.dorian.client.GridUserClient;
import org.cagrid.gaards.dorian.client.LocalUserClient;
import org.cagrid.gaards.dorian.common.DorianFault;
import org.cagrid.gaards.dorian.federation.CertificateLifetime;
import org.cagrid.gaards.dorian.idp.Application;
import org.cagrid.gaards.dorian.idp.CountryCode;
import org.cagrid.gaards.dorian.idp.DictionaryCheck;
import org.cagrid.gaards.dorian.idp.PasswordUtils;
import org.cagrid.gaards.dorian.idp.StateCode;
import org.cagrid.gaards.dorian.stubs.types.DorianInternalFault;
import org.cagrid.gaards.dorian.stubs.types.InvalidUserPropertyFault;
import org.cagrid.gaards.dorian.stubs.types.PermissionDeniedFault;
import org.globus.gsi.GlobusCredential;

/**
 * @author aevansel
 */
@Stateless
@SuppressWarnings({"PMD.CyclomaticComplexity" })
public class GridAccountServiceBean implements GridAccountServiceRemote {
    private static final Logger LOG = Logger.getLogger(GridAccountServiceBean.class);
    /**
     * Default dorian url.
     */
    public static final String GRID_URL = PaEarPropertyReader.getProperties().getProperty("grid.dorian.url");
    private static final int MAX_PASSWORD_LENGTH = 20;
    private static final int MIN_PASSWORD_LENGTH = 6;

    /**
     * {@inheritDoc}
     */
    public String createGridAccount(RegistryUser registryUser, String username, String password) throws PAException {
        LocalUserClient gridClient = null;
        try {
            gridClient = new LocalUserClient(GRID_URL);
        } catch (Exception e) {
            throw new PAException("Error connecting to remote grid.", e);
        }

        Application app = new Application();
        app.setUserId(username);
        app.setPassword(password);
        app.setFirstName(registryUser.getFirstName());
        app.setLastName(registryUser.getLastName());
        app.setAddress(registryUser.getAddressLine());
        app.setCity(registryUser.getCity());

        USStateCode state = USStateCode.getByCode(registryUser.getState());
        app.setState(state.equals(USStateCode.INTERNATIONAL) 
                ? StateCode.Outside_US : StateCode.fromString(state.name()));

        Country country = PaRegistry.getLookUpTableService().getCountryByName(registryUser.getCountry());
        app.setCountry(CountryCode.fromString(country.getAlpha2()));
        app.setZipcode(registryUser.getPostalCode());
        app.setPhoneNumber(registryUser.getPhone());
        app.setEmail(registryUser.getEmailAddress());
        app.setOrganization(registryUser.getAffiliateOrg());

        LOG.debug("Creating grid user: " + ToStringBuilder.reflectionToString(app));

        try {
            return gridClient.register(app);
        } catch (DorianFault e) {
            throw new PAException("DorianFault ERROR: " + e.getFaultReason(), e);
        } catch (DorianInternalFault e) {
            throw new PAException("DorianInternalFault ERROR: " + e.getFaultReason(), e);
        } catch (InvalidUserPropertyFault e) {
            throw new PAException("InvalidUserPropertyFault ERROR: " + e.getMessage(), e);   
        }
    }

    /**
     * {@inheritDoc}
     */
    public boolean doesGridAccountExist(String username) {
        boolean results = true;
        try {
            LocalUserClient gridClient = new LocalUserClient(GRID_URL);
            results =  gridClient.doesUserExist(username);
        } catch (Exception e) {
            LOG.error("ERROR querying grid", e);
        }
        //Returning true by default should any error happen so we don't try to create an account that already exists.
        return results;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isValidGridPassword(String password) {
        //Note: A valid grid password has to have at least 1 upper case letter, 1 lower case letter, 
        //1 symbol and 1 number. It can be between 6 and 20 characters long and may not
        //contain a dictionary word.

        boolean results = false;
        if (StringUtils.isNotEmpty(password) && PasswordUtils.hasSymbol(password) 
                && PasswordUtils.hasCapitalLetter(password) && PasswordUtils.hasLowerCaseLetter(password) 
                && PasswordUtils.hasNumber(password) && password.length() >= MIN_PASSWORD_LENGTH 
                && password.length() <= MAX_PASSWORD_LENGTH) { 
            try {
                results = !DictionaryCheck.doesStringContainDictionaryWord(password);
            } catch (IOException e) {
                LOG.error("ERROR checking password for dictionary words.");
            }
        }
        return results;
    }

    /**
     * {@inheritDoc}
     */
    public void changePassword(String username, String oldPassword, String newPassword) throws PAException {
        try {
            LocalUserClient gridClient = new LocalUserClient(GRID_URL);
            BasicAuthentication cred = new BasicAuthentication();
            cred.setUserId(username);
            cred.setPassword(oldPassword);
            gridClient.changePassword(cred, newPassword);
        } catch (PermissionDeniedFault e) {
            throw new PAException("PermissionDeniedFault ERROR: " + e.getFaultReason(), e);
        } catch (DorianInternalFault e) {
            throw new PAException("DorianInternalFault ERROR: " + e.getFaultReason(), e);
        } catch (InvalidUserPropertyFault e) {
            throw new PAException("InvalidUserPropertyFault ERROR: " + e.getMessage(), e);   
        } catch (Exception e) {
            throw new PAException("Exception: " + e.getMessage(), e);   
        }
    }


    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public Map<String, String> getIdentityProviders() {
        Map<String, String> urls = new HashMap<String, String>();
        try {
            CGMMManager cgmmManager = new CGMMManagerImpl();
            urls.putAll(cgmmManager.getAuthenticationServiceURLMap());
        } catch (Exception e) {
            LOG.error("ERROR retrieving idps", e);
        }
        return urls;
    }

    /**
     * {@inheritDoc}
     */
    public Map<String, String> authenticateUser(String username, String password, String authUrl) {
        BasicAuthentication auth = new BasicAuthentication();
        auth.setUserId(username);
        auth.setPassword(password);

        Map<String, String> userInfo = new HashMap<String, String>();

        try {
            AuthenticationClient authClient = new AuthenticationClient(authUrl);
            SAMLAssertion saml = authClient.authenticate(auth);
            userInfo = new SAMLToAttributeMapperImpl().convertSAMLtoHashMap(saml);
        } catch (Exception e) {
            LOG.error("ERROR Authenticating User.", e);
        }
        return userInfo;
    }

    /**
     * {@inheritDoc}
     */
    public String getFullyQualifiedUsername(String username, String password, String authUrl) {
        BasicAuthentication auth = new BasicAuthentication();
        auth.setUserId(username);
        auth.setPassword(password);
        
        String results = username;
        try {
            AuthenticationClient authClient = new AuthenticationClient(authUrl);
            SAMLAssertion saml = authClient.authenticate(auth);


            CertificateLifetime lifetime = new CertificateLifetime();
            lifetime.setHours(12);

            // Request PKI/Grid Credential
            GridUserClient dorian = new GridUserClient(authUrl);
            GlobusCredential credential = dorian.requestUserCertificate(saml, lifetime);
            results = credential.getIdentity();
        } catch (Exception e) {
            LOG.error("ERROR Authenticating User.", e);

        }
        return results;
    }    
}
