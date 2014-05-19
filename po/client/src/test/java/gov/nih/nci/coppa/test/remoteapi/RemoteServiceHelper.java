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
package gov.nih.nci.coppa.test.remoteapi;

import gov.nih.nci.coppa.test.TstProperties;
import gov.nih.nci.services.BusinessServiceRemote;
import gov.nih.nci.services.correlation.ClinicalResearchStaffCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareFacilityCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareProviderCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedPersonCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OrganizationalContactCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OversightCommitteeCorrelationServiceRemote;
import gov.nih.nci.services.correlation.PatientCorrelationServiceRemote;
import gov.nih.nci.services.correlation.ResearchOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

import java.util.Properties;

import javax.management.MBeanServerConnection;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * @author Scott Miller
 */
public class RemoteServiceHelper {

    static final String PERSON_SERVICE_BEAN_REMOTE = "po/po-services/PersonEntityServiceBean"
            + "!gov.nih.nci.services.person.PersonEntityServiceRemote";
    static final String ORG_SERVICE_BEAN_REMOTE = "po/po-services/"
            + "OrganizationEntityServiceBean!"
            + "gov.nih.nci.services.organization.OrganizationEntityServiceRemote";
    static final String BUSINESS_SERVICE_BEAN_REMOTE = "po/po-services/BusinessServiceBean"
            + "!gov.nih.nci.services.BusinessServiceRemote";

    static final String HEALTH_CARE_PROVIDER_CORRELATION_SERVICE_BEAN_REMOTE = "po/po-services/HealthCareProviderCorrelationServiceBean"
            + "!gov.nih.nci.services.correlation.HealthCareProviderCorrelationServiceRemote";
    static final String CLINICAL_RESEARCH_STAFF_CORRELATION_BEAN_REMOTE = "po/po-services/ClinicalResearchStaffCorrelationServiceBean"
            + "!gov.nih.nci.services.correlation.ClinicalResearchStaffCorrelationServiceRemote";
    static final String PATIENT_CORRELATION_BEAN_REMOTE = "po/po-services/PatientCorrelationServiceBean"
            + "!gov.nih.nci.services.correlation.PatientCorrelationServiceRemote";
    static final String HEALTH_CARE_FACILITY_CORRELATION_SERVICE_BEAN_REMOTE = "po/po-services/"
            + "HealthCareFacilityCorrelationServiceBean"
            + "!gov.nih.nci.services.correlation.HealthCareFacilityCorrelationServiceRemote";
    static final String OVERSIGHT_COMMITTEE_CORRELATION_SERVICE_BEAN_REMOTE = "po/po-services/OversightCommitteeCorrelationServiceBean"
            + "!gov.nih.nci.services.correlation.OversightCommitteeCorrelationServiceRemote";
    static final String RESEARCH_ORG_CORRELATION_SERVICE_BEAN_REMOTE = "po/po-services/ResearchOrganizationCorrelationServiceBean!"
            + "gov.nih.nci.services.correlation.ResearchOrganizationCorrelationServiceRemote";
    static final String QUALIFIED_ENTITY_CORRELATION_SERVICE_BEAN_REMOTE = "po/QualifiedEntityCorrelationServiceBean/remote";

    private static String username = "ejbclient";
    private static String password = "pass";

    private static InitialContext ctx;
    private static InitialContext jmxCtx;

    private static Object lookup(String resource) throws NamingException {
        initializeJndiCtx();
        return ctx.lookup(resource);
        
    }

    /**
     * @throws NamingException
     */
    private static void initializeJndiCtx() throws NamingException {
        if (ctx == null) {            
            ctx = createNewJndiContext();
        }
    }

    /**
     * @throws NamingException
     */
    public static InitialContext createNewJndiContext() throws NamingException {
        Properties jndiProps = new Properties();
        jndiProps.put(Context.INITIAL_CONTEXT_FACTORY,
                "org.jboss.naming.remote.client.InitialContextFactory");
        jndiProps.put(Context.PROVIDER_URL,
                "remote://" + TstProperties.getServerHostname() + ":"
                        + TstProperties.getServerJndiPort());
        jndiProps.put(Context.SECURITY_PRINCIPAL, username);
        jndiProps.put(Context.SECURITY_CREDENTIALS, password);
        jndiProps.put("jboss.naming.client.ejb.context", true);
        jndiProps
                .put("jboss.naming.client.connect.options.org.xnio.Options.SASL_POLICY_NOPLAINTEXT",
                        "false");
        return new InitialContext(jndiProps);
    }
    
    public static InitialContext getCtx() throws NamingException {
        initializeJndiCtx();
        return ctx;
    }

    /**
     * Closes the context.
     *
     * @throws NamingException on error.
     */
    public static void close() throws NamingException {
        if (ctx != null) {
            ctx.close();
            ctx = null;
        }
        if (jmxCtx != null) {
            jmxCtx.close();
            jmxCtx = null;
        }
    }

    /**
     * Get the person service.
     *
     * @return the service.
     * @throws NamingException on error.
     */
    public static PersonEntityServiceRemote getPersonEntityService() throws NamingException {
        return (PersonEntityServiceRemote) lookup(PERSON_SERVICE_BEAN_REMOTE);
    }
    
    /**
     * Get the research organization service.
     * @return the service.
     * @throws NamingException on error.
     */
    public static ResearchOrganizationCorrelationServiceRemote getResearchOrganizationCorrelationService()
    throws NamingException {
        return (ResearchOrganizationCorrelationServiceRemote) lookup(RESEARCH_ORG_CORRELATION_SERVICE_BEAN_REMOTE);
    }

    /**
     * Get the person service.
     *
     * @return the service.
     * @throws NamingException on error.
     */
    public static OrganizationEntityServiceRemote getOrganizationEntityService() throws NamingException {
        return (OrganizationEntityServiceRemote) lookup(ORG_SERVICE_BEAN_REMOTE);
    }

    public static ClinicalResearchStaffCorrelationServiceRemote getClinicalResearchStaffCorrelationService()
            throws NamingException {
        return (ClinicalResearchStaffCorrelationServiceRemote) lookup(CLINICAL_RESEARCH_STAFF_CORRELATION_BEAN_REMOTE);
    }
    
    public static PatientCorrelationServiceRemote getPatientCorrelationService()
        throws NamingException {
        return (PatientCorrelationServiceRemote) lookup(PATIENT_CORRELATION_BEAN_REMOTE);
    }

    public static HealthCareFacilityCorrelationServiceRemote getHealthCareFacilityCorrelationService()
            throws NamingException {
        return (HealthCareFacilityCorrelationServiceRemote) lookup(HEALTH_CARE_FACILITY_CORRELATION_SERVICE_BEAN_REMOTE);
    }

    public static HealthCareProviderCorrelationServiceRemote getHealthCareProviderCorrelationService()
            throws NamingException {
        return (HealthCareProviderCorrelationServiceRemote) lookup(HEALTH_CARE_PROVIDER_CORRELATION_SERVICE_BEAN_REMOTE);
    }

    public static OversightCommitteeCorrelationServiceRemote getOversightCommitteeCorrelationService()
            throws NamingException {
        return (OversightCommitteeCorrelationServiceRemote) lookup(OVERSIGHT_COMMITTEE_CORRELATION_SERVICE_BEAN_REMOTE);
    }

    public static IdentifiedOrganizationCorrelationServiceRemote getIdentifiedOrganizationCorrelationServiceRemote()
            throws NamingException {
        return (IdentifiedOrganizationCorrelationServiceRemote) lookup("po/po-services/IdentifiedOrganizationCorrelationServiceBean"
                + "!gov.nih.nci.services.correlation.IdentifiedOrganizationCorrelationServiceRemote");
    }

    public static IdentifiedPersonCorrelationServiceRemote getIdentifiedPersonCorrelationServiceRemote()
            throws NamingException {
        return (IdentifiedPersonCorrelationServiceRemote) lookup("po/po-services/IdentifiedPersonCorrelationServiceBean"
                + "!gov.nih.nci.services.correlation.IdentifiedPersonCorrelationServiceRemote");
    }

    public static BusinessServiceRemote getBusinessService()
            throws NamingException {
        return (BusinessServiceRemote) lookup(BUSINESS_SERVICE_BEAN_REMOTE);
    }

    public static MBeanServerConnection lookupMBeanServerProxy() throws Exception {
        String jmxUsername = TstProperties.getJmxUsername();
        String jmxPassword = TstProperties.getJmxPassword();

        if (jmxCtx == null) {
            Properties env = new Properties();
            env.setProperty(Context.SECURITY_PRINCIPAL, jmxUsername);
            env.setProperty(Context.SECURITY_CREDENTIALS, jmxPassword);
            env.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.security.jndi.JndiLoginInitialContextFactory");
            jmxCtx = new InitialContext(env);
        }
        return (MBeanServerConnection) jmxCtx.lookup("jmx/invoker/RMIAdaptor");
    }

    public static OrganizationalContactCorrelationServiceRemote getOrganizationalContactCorrelationService()
            throws NamingException {
        return (OrganizationalContactCorrelationServiceRemote) lookup("po/po-services/OrganizationalContactCorrelationServiceBean"
                + "!gov.nih.nci.services.correlation.OrganizationalContactCorrelationServiceRemote");
    }
}
