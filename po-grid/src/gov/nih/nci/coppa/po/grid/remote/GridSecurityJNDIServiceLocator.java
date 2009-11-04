/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The po-grid
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This po-grid Software License (the License) is between NCI and You. You (or
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
 * its rights in the po-grid Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the po-grid Software; (ii) distribute and
 * have distributed to and by third parties the po-grid Software and any
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
package gov.nih.nci.coppa.po.grid.remote;

import gov.nih.nci.cagrid.introduce.servicetools.security.SecurityUtils;
import gov.nih.nci.coppa.services.grid.remote.InvokeCoppaServiceException;
import gov.nih.nci.coppa.services.service.CoreServicesConfiguration;
import gov.nih.nci.services.BusinessServiceRemote;
import gov.nih.nci.services.CorrelationService;
import gov.nih.nci.services.EntityNodeDto;
import gov.nih.nci.services.PoDto;
import gov.nih.nci.services.correlation.ClinicalResearchStaffCorrelationServiceRemote;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import gov.nih.nci.services.correlation.CorrelationNodeDTO;
import gov.nih.nci.services.correlation.HealthCareFacilityCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.correlation.HealthCareProviderCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareProviderDTO;
import gov.nih.nci.services.correlation.IdentifiedOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedOrganizationDTO;
import gov.nih.nci.services.correlation.IdentifiedPersonCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedPersonDTO;
import gov.nih.nci.services.correlation.OrganizationalContactCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;
import gov.nih.nci.services.correlation.OversightCommitteeCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OversightCommitteeDTO;
import gov.nih.nci.services.correlation.PatientCorrelationServiceRemote;
import gov.nih.nci.services.correlation.PatientDTO;
import gov.nih.nci.services.correlation.ResearchOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.correlation.ResearchOrganizationDTO;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.naming.CommunicationException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Service locator that uses JNDI to look up services using Grid Security.
 */
public class GridSecurityJNDIServiceLocator implements ServiceLocator {

    private static final Logger LOG = LogManager.getLogger(GridSecurityJNDIServiceLocator.class);
    private static final int MAX_RETRIES = 2;
    private InitialContext context;
    private static Map<Class<?>, Method> values = new HashMap<Class<?>, Method>();
    private static final String JNDI_PRINCIPAL = "java.naming.security.principal";
    private static final String JNDI_CREDENTIALS = "java.naming.security.credentials";

    /**
     * @return a ServiceLocator with the caller's identity
     * @throws Exception if a problem occurs 
     */
    public static ServiceLocator newInstance() throws Exception {
        return new GridSecurityJNDIServiceLocator(SecurityUtils.getCallerIdentity());
    }
    
    /**
     * Get an instance of the service locator. specific to the grid user.
     * 
     * @param userIdentity user identity of the grid user
     */
    public GridSecurityJNDIServiceLocator(String userIdentity) {
    
        try {
            /*
             * Cache the Method instead of the actual Remote instance as it would be very difficult to handle
             * NamingException, etc..
             */
            values.put(ClinicalResearchStaffDTO.class, this.getClass().getMethod(
                    "getClinicalResearchStaffService"));
            values.put(HealthCareFacilityDTO.class, this.getClass().getMethod("getHealthCareFacilityService"));
            values.put(HealthCareProviderDTO.class, this.getClass().getMethod("getHealthCareProviderService"));
            values.put(IdentifiedOrganizationDTO.class, this.getClass().getMethod(
                    "getIdentifiedOrganizationService"));
            values.put(IdentifiedPersonDTO.class, this.getClass().getMethod("getIdentifiedPersonService"));
            values.put(ResearchOrganizationDTO.class, this.getClass().getMethod(
                    "getResearchOrganizationService"));
            values.put(OversightCommitteeDTO.class, this.getClass().getMethod("getOversightCommitteeService"));
            values.put(OrganizationalContactDTO.class, this.getClass().getMethod(
                    "getOrganizationalContactService"));
            values.put(PatientDTO.class, this.getClass().getMethod(
                "getPatientService"));
            values.put(EntityNodeDto.class, this.getClass().getMethod(
                "getBusinessService"));
            values.put(CorrelationNodeDTO.class, this.getClass().getMethod(
                "getBusinessService"));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        
        try {
            Properties props = new Properties();
            props.load(GridSecurityJNDIServiceLocator.class.getClassLoader().getResourceAsStream("jndi.properties"));

            // set grid service principal and grid identity as java.naming.security.principal
            CoreServicesConfiguration coreConfiguration = CoreServicesConfiguration.getConfiguration();
            String principal = props.getProperty(JNDI_PRINCIPAL)
                    + coreConfiguration.getGridServicePrincipalSeparator() + userIdentity;
            props.setProperty(JNDI_PRINCIPAL, principal);
            
            LOG.debug("Properties " + props.toString());
            
            context = new InitialContext(props);
            
        } catch (Exception e) {
            LOG.error("Unable to load jndi properties.", e);
            throw new RuntimeException("Unable to load jndi properties.", e);
        }
    }


    private Object lookup(String name) throws NamingException {
        Object object = null;
        int i = 0;
        while (object == null && i < MAX_RETRIES) {
             try {
                 LOG.debug("Performing JNDI Lookup of : " + name);
                 object = context.lookup(name);
             } catch (CommunicationException com) {
                 LOG.warn("Unable to lookup: " + name);
             }
             i++;
        }

        return object;
    }

    /**
     * {@inheritDoc}
     */
    public PersonEntityServiceRemote getPersonService() throws NamingException {
        PersonEntityServiceRemote object = (PersonEntityServiceRemote) lookup("po/PersonEntityServiceBean/remote");
        return object;
    }

    /**
     * {@inheritDoc}
     */
    public OrganizationEntityServiceRemote getOrganizationService() throws NamingException {
        OrganizationEntityServiceRemote object = (OrganizationEntityServiceRemote)
            lookup("po/OrganizationEntityServiceBean/remote");
        return object;
    }

    /**
     * {@inheritDoc}
     */
    public HealthCareFacilityCorrelationServiceRemote getHealthCareFacilityService() throws NamingException {
        HealthCareFacilityCorrelationServiceRemote object = (HealthCareFacilityCorrelationServiceRemote)
            lookup("po/HealthCareFacilityCorrelationServiceBean/remote");
        return object;
    }

    /**
     * {@inheritDoc}
     */
    public ClinicalResearchStaffCorrelationServiceRemote getClinicalResearchStaffService() throws NamingException {
        ClinicalResearchStaffCorrelationServiceRemote object = (ClinicalResearchStaffCorrelationServiceRemote)
            lookup("po/ClinicalResearchStaffCorrelationServiceBean/remote");
        return object;
    }

    /**
     * {@inheritDoc}
     */
    public HealthCareProviderCorrelationServiceRemote getHealthCareProviderService() throws NamingException {
        HealthCareProviderCorrelationServiceRemote object = (HealthCareProviderCorrelationServiceRemote)
            lookup("po/HealthCareProviderCorrelationServiceBean/remote");
        return object;
    }

    /**
     * {@inheritDoc}
     */
    public IdentifiedOrganizationCorrelationServiceRemote getIdentifiedOrganizationService() throws NamingException {
        IdentifiedOrganizationCorrelationServiceRemote object = (IdentifiedOrganizationCorrelationServiceRemote)
            lookup("po/IdentifiedOrganizationCorrelationServiceBean/remote");
        return object;
    }

    /**
     * {@inheritDoc}
     */
    public IdentifiedPersonCorrelationServiceRemote getIdentifiedPersonService() throws NamingException {
        IdentifiedPersonCorrelationServiceRemote object = (IdentifiedPersonCorrelationServiceRemote)
            lookup("po/IdentifiedPersonCorrelationServiceBean/remote");
        return object;
    }

    /**
     * {@inheritDoc}
     */
    public ResearchOrganizationCorrelationServiceRemote getResearchOrganizationService() throws NamingException {
        ResearchOrganizationCorrelationServiceRemote object = (ResearchOrganizationCorrelationServiceRemote)
            lookup("po/ResearchOrganizationCorrelationServiceBean/remote");
        return object;
    }

    /**
     * {@inheritDoc}
     */
    public OversightCommitteeCorrelationServiceRemote getOversightCommitteeService() throws NamingException {
        OversightCommitteeCorrelationServiceRemote object = (OversightCommitteeCorrelationServiceRemote)
            lookup("po/OversightCommitteeCorrelationServiceBean/remote");
        return object;
    }

    /**
     * {@inheritDoc}
     */
    public OrganizationalContactCorrelationServiceRemote getOrganizationalContactService() throws NamingException {
        OrganizationalContactCorrelationServiceRemote object = (OrganizationalContactCorrelationServiceRemote)
            lookup("po/OrganizationalContactCorrelationServiceBean/remote");
        return object;
    }
    
    /**
     * {@inheritDoc}
     */
    public PatientCorrelationServiceRemote getPatientService() throws NamingException {
        PatientCorrelationServiceRemote object = (PatientCorrelationServiceRemote)
            lookup("po/PatientCorrelationServiceBean/remote");
        return object;
    }
    
    /**
     * {@inheritDoc}
     */
    public BusinessServiceRemote getBusinessService() throws NamingException {
        BusinessServiceRemote object = (BusinessServiceRemote)
            lookup("po/BusinessServiceBean/remote");
        return object;
    }
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public <Z extends PoDto> CorrelationService getService(Class<Z> type) throws NamingException {
        Method serviceMethod = values.get(type);
        CorrelationService service = null;
        try {
            service = (CorrelationService) serviceMethod.invoke(this);
        } catch (Exception e) {
            throw new InvokeCoppaServiceException("Unable to invoke method, " + serviceMethod.getName());
        }
        if (service == null) {
            throw new IllegalArgumentException("Unable to locate service for type, " + type);
        }
        return service;
    }

}
