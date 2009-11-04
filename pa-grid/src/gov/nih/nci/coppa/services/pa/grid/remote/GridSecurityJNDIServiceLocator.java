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
package gov.nih.nci.coppa.services.pa.grid.remote;

import gov.nih.nci.cagrid.introduce.servicetools.security.SecurityUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.naming.CommunicationException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import gov.nih.nci.coppa.services.grid.remote.InvokeCoppaServiceException;
import gov.nih.nci.coppa.services.pa.service.PAServicesConfiguration;
import gov.nih.nci.pa.iso.dto.ArmDTO;
import gov.nih.nci.pa.iso.dto.BaseDTO;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.DocumentWorkflowStatusDTO;
import gov.nih.nci.pa.iso.dto.PlannedActivityDTO;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudyDTO;
import gov.nih.nci.pa.iso.dto.StudyDiseaseDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyOnholdDTO;
import gov.nih.nci.pa.iso.dto.StudyOutcomeMeasureDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyRecruitmentStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.dto.StudyRelationshipDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.service.ArmServiceRemote;
import gov.nih.nci.pa.service.BasePaService;
import gov.nih.nci.pa.service.DocumentServiceRemote;
import gov.nih.nci.pa.service.DocumentWorkflowStatusServiceRemote;
import gov.nih.nci.pa.service.PlannedActivityServiceRemote;
import gov.nih.nci.pa.service.StudyContactServiceRemote;
import gov.nih.nci.pa.service.StudyCurrentPaService;
import gov.nih.nci.pa.service.StudyDiseaseServiceRemote;
import gov.nih.nci.pa.service.StudyIndldeServiceRemote;
import gov.nih.nci.pa.service.StudyOnholdServiceRemote;
import gov.nih.nci.pa.service.StudyOutcomeMeasureServiceRemote;
import gov.nih.nci.pa.service.StudyOverallStatusServiceRemote;
import gov.nih.nci.pa.service.StudyPaService;
import gov.nih.nci.pa.service.StudyProtocolServiceRemote;
import gov.nih.nci.pa.service.StudyRecruitmentStatusServiceRemote;
import gov.nih.nci.pa.service.StudyRegulatoryAuthorityServiceRemote;
import gov.nih.nci.pa.service.StudyRelationshipServiceRemote;
import gov.nih.nci.pa.service.StudyResourcingServiceRemote;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceRemote;
import gov.nih.nci.pa.service.StudySiteContactServiceRemote;
import gov.nih.nci.pa.service.StudySiteServiceRemote;
import gov.nih.nci.pa.service.TrialRegistrationServiceRemote;

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
            /*
             * Cache the Method instead of the actual Remote instance as it would be very difficult to handle
             * NamingException, etc..
             */
            values.put(ArmDTO.class, this.getClass().getMethod("getArmService"));
            values.put(StudyProtocolDTO.class, this.getClass().getMethod("getStudyProtocolService"));
            values.put(StudyResourcingDTO.class, this.getClass().getMethod("getStudyResourcingService"));
            values.put(StudyRegulatoryAuthorityDTO.class, this.getClass().getMethod(
                    "getStudyRegulatoryAuthorityService"));
            values.put(StudyRecruitmentStatusDTO.class, this.getClass().getMethod("getStudyRecruitmentStatusService"));
            values.put(StudySiteAccrualStatusDTO.class, this.getClass().getMethod("getStudySiteAccrualStatusService"));
            values.put(StudySiteContactDTO.class, this.getClass().getMethod("getStudySiteContactService"));
            values.put(StudyOutcomeMeasureDTO.class, this.getClass().getMethod("getStudyOutcomeMeasureService"));
            values.put(StudySiteDTO.class, this.getClass().getMethod("getStudySiteService"));
            values.put(StudyOverallStatusDTO.class, this.getClass().getMethod("getStudyOverallStatusService"));
            values.put(StudyDiseaseDTO.class, this.getClass().getMethod("getStudyDiseaseService"));
            values.put(StudyOnholdDTO.class, this.getClass().getMethod("getStudyOnholdService"));
            values.put(StudyContactDTO.class, this.getClass().getMethod("getStudyContactService"));
            values.put(StudyIndldeDTO.class, this.getClass().getMethod("getStudyIndldeService"));
            values.put(StudyRelationshipDTO.class, this.getClass().getMethod("getStudyRelationshipService"));
            values.put(DocumentWorkflowStatusDTO.class, this.getClass().getMethod("getDocumentWorkflowStatusService"));
            values.put(DocumentDTO.class, this.getClass().getMethod("getDocumentService"));
            values.put(PlannedActivityDTO.class, this.getClass().getMethod("getPlannedActivityService"));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        try {
            Properties props = new Properties();
            props.load(GridSecurityJNDIServiceLocator.class.getClassLoader().getResourceAsStream("jndi.properties"));

            // set grid service principal and grid identity as java.naming.security.principal
            PAServicesConfiguration coreConfiguration = PAServicesConfiguration.getConfiguration();
            String principal = props.getProperty(JNDI_PRINCIPAL) + coreConfiguration.getGridServicePrincipalSeparator()
                    + userIdentity;
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
    public ArmServiceRemote getArmService() throws NamingException {
        ArmServiceRemote result = (ArmServiceRemote) lookup("pa/ArmServiceBean/remote");
        return result;
    }

    /**
     * {@inheritDoc}
     */
    public StudyProtocolServiceRemote getStudyProtocolService() throws NamingException {
        StudyProtocolServiceRemote result = (StudyProtocolServiceRemote) lookup("pa/StudyProtocolServiceBean/remote");
        return result;
    }

    /**
     * {@inheritDoc}
     */
    public StudyResourcingServiceRemote getStudyResourcingService() throws NamingException {
        StudyResourcingServiceRemote result = 
            (StudyResourcingServiceRemote) lookup("pa/StudyResourcingServiceBean/remote");
        return result;
    }

    /**
     * {@inheritDoc}
     */
    public StudyRegulatoryAuthorityServiceRemote getStudyRegulatoryAuthorityService() throws NamingException {
        StudyRegulatoryAuthorityServiceRemote result = 
            (StudyRegulatoryAuthorityServiceRemote) lookup("pa/StudyRegulatoryAuthorityServiceBean/remote");
        return result;
    }

    /**
     * {@inheritDoc}
     */
    public StudyRecruitmentStatusServiceRemote getStudyRecruitmentStatusService() throws NamingException {
        StudyRecruitmentStatusServiceRemote result = 
            (StudyRecruitmentStatusServiceRemote) lookup("pa/StudyRecruitmentStatusServiceBean/remote");
        return result;
    }

    /**
     * {@inheritDoc}
     */
    public StudySiteAccrualStatusServiceRemote getStudySiteAccrualStatusService() throws NamingException {
        StudySiteAccrualStatusServiceRemote result = 
            (StudySiteAccrualStatusServiceRemote) lookup("pa/StudySiteAccrualStatusServiceBean/remote");
        return result;
    }

    /**
     * {@inheritDoc}
     */
    public StudySiteContactServiceRemote getStudySiteContactService() throws NamingException {
        StudySiteContactServiceRemote result = 
            (StudySiteContactServiceRemote) lookup("pa/StudySiteContactServiceBean/remote");
        return result;
    }

    /**
     * {@inheritDoc}
     */
    public StudyOutcomeMeasureServiceRemote getStudyOutcomeMeasureService() throws NamingException {
        StudyOutcomeMeasureServiceRemote result = 
            (StudyOutcomeMeasureServiceRemote) lookup("pa/StudyOutcomeMeasureServiceBean/remote");
        return result;
    }

    /**
     * {@inheritDoc}
     */
    public StudySiteServiceRemote getStudySiteService() throws NamingException {
        StudySiteServiceRemote result = (StudySiteServiceRemote) lookup("pa/StudySiteServiceBean/remote");
        return result;
    }

    /**
     * {@inheritDoc}
     */
    public StudyOverallStatusServiceRemote getStudyOverallStatusService() throws NamingException {
        StudyOverallStatusServiceRemote result = 
            (StudyOverallStatusServiceRemote) lookup("pa/StudyOverallStatusServiceBean/remote");
        return result;
    }

    /**
     * {@inheritDoc}
     */
    public StudyDiseaseServiceRemote getStudyDiseaseService() throws NamingException {
        StudyDiseaseServiceRemote result = (StudyDiseaseServiceRemote) lookup("pa/StudyDiseaseServiceBean/remote");
        return result;
    }

    /**
     * {@inheritDoc}
     */
    public StudyOnholdServiceRemote getStudyOnholdService() throws NamingException {
        StudyOnholdServiceRemote result = (StudyOnholdServiceRemote) lookup("pa/StudyOnholdServiceBean/remote");
        return result;
    }

    /**
     * {@inheritDoc}
     */
    public StudyContactServiceRemote getStudyContactService() throws NamingException {
        StudyContactServiceRemote result = (StudyContactServiceRemote) lookup("pa/StudyContactServiceBean/remote");
        return result;
    }

    /**
     * {@inheritDoc}
     */
    public StudyIndldeServiceRemote getStudyIndldeService() throws NamingException {
        StudyIndldeServiceRemote result = (StudyIndldeServiceRemote) lookup("pa/StudyIndldeServiceBean/remote");
        return result;
    }

    /**
     * {@inheritDoc}
     */
    public StudyRelationshipServiceRemote getStudyRelationshipService() throws NamingException {
        StudyRelationshipServiceRemote result = 
            (StudyRelationshipServiceRemote) lookup("pa/StudyRelationshipServiceBean/remote");
        return result;
    }

    /**
     * {@inheritDoc}
     */
    public DocumentWorkflowStatusServiceRemote getDocumentWorkflowStatusService() throws NamingException {
        DocumentWorkflowStatusServiceRemote result = 
            (DocumentWorkflowStatusServiceRemote) lookup("pa/DocumentWorkflowStatusServiceBean/remote");
        return result;
    }

    /**
     * {@inheritDoc}
     */
    public DocumentServiceRemote getDocumentService() throws NamingException {
        DocumentServiceRemote result = (DocumentServiceRemote) lookup("pa/DocumentServiceBean/remote");
        return result;
    }

    /**
     * {@inheritDoc}
     */
    public PlannedActivityServiceRemote getPlannedActivityService() throws NamingException {
        PlannedActivityServiceRemote result = 
            (PlannedActivityServiceRemote) lookup("pa/PlannedActivityServiceBean/remote");
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public <Z extends BaseDTO> BasePaService<Z> getBasePaService(Class<Z> type) throws NamingException {
        Method serviceMethod = values.get(type);
        BasePaService<Z> service = null;
        try {
            service = (BasePaService<Z>) serviceMethod.invoke(this);
        } catch (Exception e) {

            throw new InvokeCoppaServiceException("Unable to invoke method " + serviceMethod.getName(), e);
        }
        if (service == null) {
            throw new IllegalArgumentException("Unable to locate service for type, " + type);
        }
        return service;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public <S extends StudyDTO> StudyPaService<S> getStudyPaService(Class<S> type) throws NamingException {
        Method serviceMethod = values.get(type);
        StudyPaService<S> service = null;
        try {
            service = (StudyPaService<S>) serviceMethod.invoke(this);
        } catch (Exception e) {
            throw new InvokeCoppaServiceException("Unable to invoke method " + serviceMethod.getName(), e);
        }
        if (service == null) {
            throw new IllegalArgumentException("Unable to locate service for type, " + type);
        }
        return service;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public <S extends StudyDTO> StudyCurrentPaService getStudyCurrentPaService(Class<S> type) throws NamingException {
        Method serviceMethod = values.get(type);
        StudyCurrentPaService<S> service = null;
        try {
            service = (StudyCurrentPaService<S>) serviceMethod.invoke(this);
        } catch (Exception e) {
            throw new InvokeCoppaServiceException("Unable to invoke method " + serviceMethod.getName(), e);
        }
        if (service == null) {
            throw new IllegalArgumentException("Unable to locate service for type, " + type);
        }
        return service;
    }

    /**
     * {@inheritDoc}
     */
    public TrialRegistrationServiceRemote getTrialRegistrationService() throws NamingException {
        return (TrialRegistrationServiceRemote) lookup("/pa/TrialRegistrationServiceBean/remote");
    }

}