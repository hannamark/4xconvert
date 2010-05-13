/*
 * caBIG Open Source Software License
 * 
 * Copyright Notice. Copyright 2008, ScenPro, Inc, (caBIG Participant). The Protocol Abstraction (PA) Application was
 * created with NCI funding and is part of the caBIG initiative. The software subject to this notice and license
 * includes both human readable source code form and machine readable, binary, object code form (the caBIG Software).
 * 
 * This caBIG Software License (the License) is between caBIG Participant and You. You (or Your) shall mean a person or
 * an entity, and all other entities that control, are controlled by, or are under common control with the entity.
 * Control for purposes of this definition means
 * 
 * (i) the direct or indirect power to cause the direction or management of such entity,whether by contract or
 * otherwise,or
 * 
 * (ii) ownership of fifty percent (50%) or more of the outstanding shares, or
 * 
 * (iii) beneficial ownership of such entity. License. Provided that You agree to the conditions described below, caBIG
 * Participant grants You a non-exclusive, worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable and
 * royalty-free right and license in its rights in the caBIG Software, including any copyright or patent rights therein,
 * to
 * 
 * (i) use,install, disclose, access, operate, execute, reproduce, copy, modify, translate, market, publicly display,
 * publicly perform, and prepare derivative works of the caBIG Software in any manner and for any purpose, and to have
 * or permit others to do so;
 * 
 * (ii) make, have made, use, practice, sell, and offer for sale, import, and/or otherwise dispose of caBIG Software (or
 * portions thereof);
 * 
 * (iii) distribute and have distributed to and by third parties the caBIG Software and any modifications and derivative
 * works thereof; and (iv) sublicense the foregoing rights set out in (i), (ii) and (iii) to third parties, including
 * the right to license such rights to further third parties. For sake of clarity,and not by way of limitation, caBIG
 * Participant shall have no right of accounting or right of payment from You or Your sub licensees for the rights
 * granted under this License. This License is granted at no charge to You. Your downloading, copying, modifying,
 * displaying, distributing or use of caBIG Software constitutes acceptance of all of the terms and conditions of this
 * Agreement. If You do not agree to such terms and conditions, You have no right to download, copy, modify, display,
 * distribute or use the caBIG Software.
 * 
 * 1. Your redistributions of the source code for the caBIG Software must retain the above copyright notice, this list
 * of conditions and the disclaimer and limitation of liability of Article 6 below. Your redistributions in object code
 * form must reproduce the above copyright notice, this list of conditions and the disclaimer of Article 6 in the
 * documentation and/or other materials provided with the distribution, if any.
 * 
 * 2. Your end-user documentation included with the redistribution, if any, must include the following acknowledgment:
 * This product includes software developed by ScenPro, Inc. If You do not include such end-user documentation, You
 * shall include this acknowledgment in the caBIG Software itself, wherever such third-party acknowledgments normally
 * appear.
 * 
 * 3. You may not use the names ScenPro, Inc., The National Cancer Institute, NCI, Cancer Bioinformatics Grid or caBIG
 * to endorse or promote products derived from this caBIG Software. This License does not authorize You to use any
 * trademarks, service marks, trade names, logos or product names of either caBIG Participant, NCI or caBIG, except as
 * required to comply with the terms of this License.
 * 
 * 4. For sake of clarity, and not by way of limitation, You may incorporate this caBIG Software into Your proprietary
 * programs and into any third party proprietary programs. However, if You incorporate the caBIG Software into third
 * party proprietary programs, You agree that You are solely responsible for obtaining any permission from such third
 * parties required to incorporate the caBIG Software into such third party proprietary programs and for informing Your
 * sub licensees, including without limitation Your end-users, of their obligation to secure any required permissions
 * from such third parties before incorporating the caBIG Software into such third party proprietary software programs.
 * In the event that You fail to obtain such permissions, You agree to indemnify caBIG Participant for any claims
 * against caBIG Participant by such third parties, except to the extent prohibited by law, resulting from Your failure
 * to obtain such permissions.
 * 
 * 5. For sake of clarity, and not by way of limitation, You may add Your own copyright statement to Your modifications
 * and to the derivative works, and You may provide additional or different license terms and conditions in Your
 * sublicenses of modifications of the caBIG Software, or any derivative works of the caBIG Software as a whole,
 * provided Your use, reproduction, and distribution of the Work otherwise complies with the conditions stated in this
 * License.
 * 
 * 6. THIS caBIG SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES ( INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN
 * NO EVENT SHALL THE ScenPro, Inc. OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS
 * OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * caBIG SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.nih.nci.outcomes.svc;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import gov.nih.nci.cagrid.opensaml.SAMLAssertion;
import gov.nih.nci.outcomes.svc.exception.OutcomesException;
import gov.nih.nci.outcomes.svc.util.OutcomesPropertyReader;

import javax.ejb.Stateless;
import javax.xml.rpc.ServiceException;

import org.apache.axis.EngineConfigurationFactory;
import org.apache.axis.types.URI.MalformedURIException;
import org.cagrid.gaards.authentication.BasicAuthentication;
import org.cagrid.gaards.authentication.client.AuthenticationClient;
import org.cagrid.gaards.cds.client.ClientConstants;
import org.cagrid.gaards.cds.client.DelegationUserClient;
import org.cagrid.gaards.cds.common.IdentityDelegationPolicy;
import org.cagrid.gaards.cds.common.ProxyLifetime;
import org.cagrid.gaards.cds.common.Utils;
import org.cagrid.gaards.cds.delegated.stubs.types.DelegatedCredentialReference;
import org.cagrid.gaards.dorian.client.GridUserClient;
import org.cagrid.gaards.dorian.common.DorianFault;
import org.cagrid.gaards.dorian.federation.CertificateLifetime;
import org.cagrid.gaards.dorian.stubs.types.DorianInternalFault;
import org.cagrid.gaards.dorian.stubs.types.InvalidAssertionFault;
import org.cagrid.gaards.dorian.stubs.types.PermissionDeniedFault;
import org.cagrid.gaards.dorian.stubs.types.UserPolicyFault;
import org.globus.gsi.GlobusCredential;

import com.saic.phrconnector.PHRConnector;
import com.saic.phrconnector.PHRConnectorServiceLocator;
import com.saic.phrconnector.ServiceResponse;
import com.saic.phrconnector.axis.PHREngineConfigurationFactory;

/**
 * @author Igor Merenko
 * @since May 11, 2010
 * 
 */
@Stateless
public class PHRConnectorSvcBeanLocal implements PHRConnectorSvcLocal {

    private static final String ERROR_DELEGATE_CREDENTIAL = "cannot delegate credential";
    private static final String ERROR_DELEGATION_USER_CLIENT = "cannot create DelegationUserClient";
    private static final String ERROR_GRID_USER_CLIENT = "cannot crete GridUserClient";
    private static final String ERROR_AUTHENTICATE_WITH_BASIC_AUTHENTICATION = "cannot authenticate with"
            + " basic authentication";
    private static final String ERROR_AUTHENTICATION_CLIENT = "cannot create AuthenticationClient";
    private static final String ERROR_USER_CERTIFICATE = "cannot retreive user certificate";
    private static final int CERTIFICATION_LIFETIME = 12;

    /**
     * {@inheritDoc}
     */
    public String getCDAIdentifierMessage(String id, String user, String password) throws OutcomesException {
        String result = null;

        /*
         * The use of the PHREngineConfigurationFactory prevents conflicts between the https transport implementations
         * used by cagrid/globus vs. phrconnector. You can use the PHREngineConfigurationFactory, which uses a
         * configuration file called phr-client-config.wsdd (included), or you can determine your own configuration.
         */
        EngineConfigurationFactory fac = PHREngineConfigurationFactory.newFactory(null);
        PHRConnectorServiceLocator loc = new PHRConnectorServiceLocator(fac.getClientEngineConfig());
        PHRConnector client;
        try {
            client = loc.getPHRConnectorService();
        } catch (ServiceException e) {
            throw new OutcomesException("cannot get PHRConnector", e);
        }

        GlobusCredential credential = getLocalCredential(user, password);

        DelegatedCredentialReference ref = delegateCredential(OutcomesPropertyReader.getCDSUrl(),
                OutcomesPropertyReader.getPHRSystemId(), credential);
        ServiceResponse cdaResponse;
        try {
            cdaResponse = client.getCDAIdentifier(credential.getIdentity(), id, ref.getEndpointReference());
        } catch (RemoteException e) {
            throw new OutcomesException("cannot get PHRConnector", e);
        }

        if (cdaResponse.isSuccess()) {
            result = cdaResponse.getData();
        } else {
            throw new OutcomesException("The CDA could not be retrieved. Message: " + cdaResponse.getMessage());
        }
        return result;

    }

    /**
     * @param user user name
     * @param password user password
     * @return Globus Credential
     * @throws OutcomesException exception
     */
    private GlobusCredential getLocalCredential(String user, String pwd) throws OutcomesException {
        GlobusCredential result;
        /*
         * For portability, a cagrid test account is used for authentication
         */

        BasicAuthentication auth = new BasicAuthentication();
        auth.setUserId(user);
        auth.setPassword(pwd);

        SAMLAssertion saml = getSAMLAssertion(auth);

        CertificateLifetime lifetime = new CertificateLifetime();
        lifetime.setHours(CERTIFICATION_LIFETIME);

        // Request PKI/Grid Credential
        result = getLocalCredential(saml, lifetime);

        return result;

    }

    /**
     * @param auth
     * @return
     * @throws OutcomesException
     */
    private SAMLAssertion getSAMLAssertion(BasicAuthentication auth) throws OutcomesException {
        String authServiceUrl = OutcomesPropertyReader.getAuthServiceUrl();
        // Authenticate to the IdP (DorianIdP) using credential
        AuthenticationClient authClient;
        try {
            authClient = new AuthenticationClient(authServiceUrl);
        } catch (RemoteException e) {
            throw new OutcomesException(ERROR_AUTHENTICATION_CLIENT, e);
        } catch (MalformedURIException e) {
            throw new OutcomesException(ERROR_AUTHENTICATION_CLIENT, e);
        }

        SAMLAssertion saml;
        try {
            saml = authClient.authenticate(auth);
        } catch (RemoteException e) {
            throw new OutcomesException(ERROR_AUTHENTICATE_WITH_BASIC_AUTHENTICATION, e);
        }
        return saml;
    }

    /**
     * @param result
     * @param authServiceUrl
     * @param saml
     * @param lifetime
     * @return
     * @throws OutcomesException
     */
    private GlobusCredential getLocalCredential(SAMLAssertion saml, CertificateLifetime lifetime)
            throws OutcomesException {
        GlobusCredential result;
        String authServiceUrl = OutcomesPropertyReader.getAuthServiceUrl();
        GridUserClient dorian = getGridUserClient(authServiceUrl);

        try {
            result = dorian.requestUserCertificate(saml, lifetime);
        } catch (DorianFault e) {
            throw new OutcomesException(ERROR_USER_CERTIFICATE, e);
        } catch (DorianInternalFault e) {
            throw new OutcomesException(ERROR_USER_CERTIFICATE, e);
        } catch (InvalidAssertionFault e) {
            throw new OutcomesException(ERROR_USER_CERTIFICATE, e);
        } catch (UserPolicyFault e) {
            throw new OutcomesException(ERROR_USER_CERTIFICATE, e);
        } catch (PermissionDeniedFault e) {
            throw new OutcomesException(ERROR_USER_CERTIFICATE, e);
        }
        return result;
    }

    /**
     * @param authServiceUrl
     * @return
     * @throws OutcomesException
     */
    private GridUserClient getGridUserClient(String authServiceUrl) throws OutcomesException {
        GridUserClient dorian;
        try {
            dorian = new GridUserClient(authServiceUrl);
        } catch (RemoteException e) {
            throw new OutcomesException(ERROR_GRID_USER_CLIENT, e);
        } catch (MalformedURIException e) {
            throw new OutcomesException(ERROR_GRID_USER_CLIENT, e);
        }
        return dorian;
    }

    /**
     * @param cdsURL cds url
     * @param cdsUser cds user
     * @param credential globus credential
     * @return Delegated Credential Reference
     * @throws OutcomesException exception
     */
    private DelegatedCredentialReference delegateCredential(String cdsURL, String cdsUser, GlobusCredential credential)
            throws OutcomesException {

        DelegatedCredentialReference result;
        ProxyLifetime delegationLifetime = getDelegationLifetime();

        // Specifies the path length of the credential being delegate the minumum is 1.
        int delegationPathLength = 1;

        ProxyLifetime issuedCredentialLifetime = getIssuedLifetime();

        // Specifies the path length of the credentials issued to allowed parties. A path length of 0 means that
        // the requesting party cannot further delegate the credential.

        int issuedCredentialPathLength = 0;

        // Specifies the key length of the delegated credential

        int keySize = ClientConstants.DEFAULT_KEY_SIZE;

        IdentityDelegationPolicy policy = getIdentityDelegationPolicy(cdsUser);

        DelegationUserClient client = getDelegationUserClient(cdsURL, credential);

        // Delegates the credential and returns a reference which can later be used by allowed parties to
        // obtain a credential.

        try {
            result = client.delegateCredential(delegationLifetime, delegationPathLength, policy,
                    issuedCredentialLifetime, issuedCredentialPathLength, keySize);
        } catch (RemoteException e) {
            throw new OutcomesException(ERROR_DELEGATE_CREDENTIAL, e);
        } catch (MalformedURIException e) {
            throw new OutcomesException(ERROR_DELEGATE_CREDENTIAL, e);
        }

        return result;
    }

    /**
     * @param cdsURL
     * @param credential
     * @return
     * @throws OutcomesException
     */
    private DelegationUserClient getDelegationUserClient(String cdsURL, GlobusCredential credential)
            throws OutcomesException {
        // Create an instance of the delegation client, specifies the CDS Service URL and the credential
        // to be delegated.

        DelegationUserClient client;
        try {
            client = new DelegationUserClient(cdsURL, credential);
        } catch (Exception e) {
            throw new OutcomesException(ERROR_DELEGATION_USER_CLIENT, e);
        }
        return client;
    }

    /**
     * @param cdsUser
     * @return
     */
    private IdentityDelegationPolicy getIdentityDelegationPolicy(String cdsUser) {
        // The policy stating which parties will be allowed to obtain a delegated credential. The CDS will only
        // issue credentials to parties listed in this policy.
        IdentityDelegationPolicy result;
        List<String> parties = new ArrayList<String>();
        parties.add(cdsUser);
        result = Utils.createIdentityDelegationPolicy(parties);
        return result;
    }

    /**
     * @return
     */
    private ProxyLifetime getIssuedLifetime() {
        // Specifies the how long credentials issued to allowed parties will be valid for.

        ProxyLifetime issuedCredentialLifetime = new ProxyLifetime();
        issuedCredentialLifetime.setSeconds(OutcomesPropertyReader.getDelegationLifetime());
        return issuedCredentialLifetime;
    }

    /**
     * @return
     */
    private ProxyLifetime getDelegationLifetime() {
        // Specifies how long the delegation service can delegated this credential to other parties.
        ProxyLifetime delegationLifetime = new ProxyLifetime();
        delegationLifetime.setSeconds(OutcomesPropertyReader.getIssuedCredentialLifetime());
        return delegationLifetime;
    }

}
