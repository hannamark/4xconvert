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
package gov.nih.nci.po.service;

import com.fiveamsolutions.nci.commons.data.persistent.PersistentObject;
import gov.nih.nci.po.data.bo.IdentifiedOrganizationCR;
import gov.nih.nci.po.data.bo.IdentifiedPersonCR;
import gov.nih.nci.po.service.external.CtepImportService;
import gov.nih.nci.po.service.external.CtepImportServiceBean;
import gov.nih.nci.po.util.EjbInterceptorHandler;
import gov.nih.nci.po.util.RemoteBeanHandler;
import gov.nih.nci.po.util.jms.TopicConnectionFactoryStub;
import gov.nih.nci.po.util.jms.TopicStub;
import gov.nih.nci.services.BusinessServiceBean;
import gov.nih.nci.services.BusinessServiceRemote;
import gov.nih.nci.services.correlation.ClinicalResearchStaffCorrelationServiceBean;
import gov.nih.nci.services.correlation.ClinicalResearchStaffCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareFacilityCorrelationServiceBean;
import gov.nih.nci.services.correlation.HealthCareFacilityCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareProviderCorrelationServiceBean;
import gov.nih.nci.services.correlation.HealthCareProviderCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedOrganizationCorrelationServiceBean;
import gov.nih.nci.services.correlation.IdentifiedOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedPersonCorrelationServiceBean;
import gov.nih.nci.services.correlation.IdentifiedPersonCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OrganizationalContactCorrelationServiceBean;
import gov.nih.nci.services.correlation.OrganizationalContactCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OversightCommitteeCorrelationServiceBean;
import gov.nih.nci.services.correlation.OversightCommitteeCorrelationServiceRemote;
import gov.nih.nci.services.correlation.PatientCorrelationServiceBean;
import gov.nih.nci.services.correlation.PatientCorrelationServiceRemote;
import gov.nih.nci.services.correlation.ResearchOrganizationCorrelationServiceBean;
import gov.nih.nci.services.correlation.ResearchOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.family.FamilyServiceRemoteBean;
import gov.nih.nci.services.organization.OrganizationEntityServiceBean;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.person.PersonEntityServiceBean;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

import javax.jms.JMSException;
import javax.jms.Topic;
import javax.jms.TopicConnectionFactory;
import javax.naming.InitialContext;

/**
 * @author Scott Miller
 */
public class EjbTestHelper {

    public static Object wrapWithProxies(Object bean) {
        bean = EjbInterceptorHandler.makeInterceptorProxy(bean);
        bean = RemoteBeanHandler.makeRemoteProxy(bean);
        return bean;
    }

    /**
     * Get a newly created org service.
     *
     * @return the service
     */
    public static OrganizationServiceBean getOrganizationServiceBean() {
        OrganizationServiceBean organizationServiceBean = new OrganizationServiceBean();
        organizationServiceBean.setPublisher(getMessageProducer());
        organizationServiceBean.setFamilyOrganizationRelationshipService(getFamilyOrganizationRelationshipService());
        organizationServiceBean.setIdenOrgServ(getIdentifiedOrganizationServiceBean());
        return organizationServiceBean;
    }

    public static OrganizationCRServiceBean getOrganizationCRServiceBean() {
        OrganizationCRServiceBean crService = new OrganizationCRServiceBean();
        return crService;
    }

    public static OrganizationEntityServiceBean getOrganizationEntityServiceBean() {
        OrganizationEntityServiceBean organizationServiceBean = new OrganizationEntityServiceBean();
        organizationServiceBean.setOrganizationServiceBean(getOrganizationServiceBean());
        organizationServiceBean.setOrganizationCRServiceBean(getOrganizationCRServiceBean());
        return organizationServiceBean;
    }
    
    public static FamilyServiceRemoteBean getFamilyServiceRemoteBean() {
        FamilyServiceRemoteBean familyServiceBean = new FamilyServiceRemoteBean();
        familyServiceBean.setFamilyServiceBean(getFamilyServiceBean());
        familyServiceBean.setFamilyOrganizationRelationshipServiceBean(getFamilyOrganizationRelationshipService());
        return familyServiceBean;
    }

    public static OrganizationEntityServiceRemote getOrganizationEntityServiceBeanAsRemote() {
        return (OrganizationEntityServiceRemote) wrapWithProxies(getOrganizationEntityServiceBean());
    }

    /**
     * Get a newly created person service.
     *
     * @return the service
     */
    public static PersonServiceBean getPersonServiceBean() {
        PersonServiceBean personServiceBean = new PersonServiceBean();
        personServiceBean.setPublisher(getMessageProducer());
        return personServiceBean;
    }

    public static PersonCRServiceBean getPersonCRServiceBean() {
        PersonCRServiceBean personCRServiceBean = new PersonCRServiceBean();
        return personCRServiceBean;
    }

    public static PersonEntityServiceBean getPersonEntityServiceBean() {
        PersonEntityServiceBean personServiceBean = new PersonEntityServiceBean();
        personServiceBean.setPersonServiceBean(getPersonServiceBean());
        personServiceBean.setPersonCRServiceBean(getPersonCRServiceBean());
        personServiceBean.setPatientServiceBean(getPatientServiceBean());
        return personServiceBean;
    }

    public static PersonEntityServiceRemote getPersonEntityServiceBeanAsRemote() {
        return (PersonEntityServiceRemote) wrapWithProxies(getPersonEntityServiceBean());
    }

    public static HealthCareProviderServiceBean getHealthCareProviderServiceBean() {
        HealthCareProviderServiceBean hcpsb = new HealthCareProviderServiceBean();
        hcpsb.setPublisher(getMessageProducer());
        return hcpsb;
    }

    public static HealthCareProviderCRServiceBean getHealthCareProviderCRServiceBean() {
        HealthCareProviderCRServiceBean s = new HealthCareProviderCRServiceBean();
        return s;
    }

    public static HealthCareProviderCorrelationServiceRemote getHealthCareProviderCorrelationServiceRemote() {
        HealthCareProviderCorrelationServiceBean hcpService = new HealthCareProviderCorrelationServiceBean();
        hcpService.setHcpService(EjbTestHelper.getHealthCareProviderServiceBean());
        hcpService.setHcpCRService(EjbTestHelper.getHealthCareProviderCRServiceBean());
        return (HealthCareProviderCorrelationServiceRemote) wrapWithProxies(hcpService);
    }

    public static ClinicalResearchStaffCorrelationServiceRemote getClinicalResearchStaffCorrelationServiceRemote() {
        ClinicalResearchStaffCorrelationServiceBean crsService = new ClinicalResearchStaffCorrelationServiceBean();
        crsService.setCrsService(getClinicalResearchStaffServiceBean());
        crsService.setCrsCRService(getClinicalResearchStaffCRServiceBean());
        return (ClinicalResearchStaffCorrelationServiceRemote) wrapWithProxies(crsService);
    }

    public static PatientCorrelationServiceRemote getPatientCorrelationServiceRemote() {
        PatientCorrelationServiceBean patientService = new PatientCorrelationServiceBean();
        patientService.setPatService(getPatientServiceBean());
        return (PatientCorrelationServiceRemote) wrapWithProxies(patientService);
    }

    public static HealthCareFacilityCorrelationServiceRemote getHealthCareFacilityCorrelationServiceRemote() {
        HealthCareFacilityCorrelationServiceBean hcfService = new HealthCareFacilityCorrelationServiceBean();
        hcfService.setHcfService(EjbTestHelper.getHealthCareFacilityServiceBean());
        hcfService.setHcfCRService(getHealthCareFacilityCRServiceBean());
        return (HealthCareFacilityCorrelationServiceRemote) wrapWithProxies(hcfService);
    }

    /**
     * Get a newly created and configured generic service.
     *
     * @return the service
     */
    public static GenericServiceBean getGenericServiceBean() {
        return new GenericServiceBean();
    }

    public static CountryServiceBean getCountryServiceBean() {
        return new CountryServiceBean();
    }

    /**
     * @return the service
     */
    public static OversightCommitteeServiceLocal getOversightCommitteeServiceBean() {
        OversightCommitteeServiceBean bean = new OversightCommitteeServiceBean();
        bean.setPublisher(getMessageProducer());
        return bean;
    }

    public static OversightCommitteeCRServiceBean getOversightCommitteeRCServiceBean() {
        OversightCommitteeCRServiceBean ocService = new OversightCommitteeCRServiceBean();
        return ocService;
    }

    public static OversightCommitteeCorrelationServiceRemote getOversightCommitteeCorrelationServiceRemote() {
        OversightCommitteeCorrelationServiceBean ocService = new OversightCommitteeCorrelationServiceBean();
        ocService.setOcService(EjbTestHelper.getOversightCommitteeServiceBean());
        ocService.setOcCRService(getOversightCommitteeRCServiceBean());
        return (OversightCommitteeCorrelationServiceRemote) wrapWithProxies(ocService);
    }

    /**
     * @return the service
     */
    public static ResearchOrganizationServiceLocal getResearchOrganizationServiceBean() {
        ResearchOrganizationServiceBean bean = new ResearchOrganizationServiceBean();
        bean.setPublisher(getMessageProducer());
        return bean;
    }

    public static ResearchOrganizationCRServiceBean getResearchOrganizationCRServiceBean() {
        ResearchOrganizationCRServiceBean ocService = new ResearchOrganizationCRServiceBean();
        return ocService;
    }

    public static ResearchOrganizationCorrelationServiceRemote getResearchOrganizationCorrelationServiceRemote() {
        ResearchOrganizationCorrelationServiceBean ocService = new ResearchOrganizationCorrelationServiceBean();
        ocService.setRoService(EjbTestHelper.getResearchOrganizationServiceBean());
        ocService.setRoCRService(getResearchOrganizationCRServiceBean());
        return (ResearchOrganizationCorrelationServiceRemote) wrapWithProxies(ocService);
    }

    /**
     * @return the service
     */
    public static HealthCareFacilityServiceLocal getHealthCareFacilityServiceBean() {
        HealthCareFacilityServiceBean bean = new HealthCareFacilityServiceBean();
        bean.setPublisher(getMessageProducer());
        return bean;
    }

    /**
     * @return the service
     */
    public static HealthCareFacilityCRServiceLocal getHealthCareFacilityCRServiceBean() {
        HealthCareFacilityCRServiceBean s = new HealthCareFacilityCRServiceBean();
        return s;
    }

    /**
     * Get the bean.
     *
     * @return Get the bean.
     */
    public static ClinicalResearchStaffServiceLocal getClinicalResearchStaffServiceBean() {
        ClinicalResearchStaffServiceBean bean = new ClinicalResearchStaffServiceBean();
        bean.setPublisher(getMessageProducer());
        return bean;
    }

    public static PatientServiceLocal getPatientServiceBean() {
        PatientServiceBean bean = new PatientServiceBean();
        bean.setPublisher(getMessageProducer());
        return bean;
    }

    public static ClinicalResearchStaffCRServiceLocal getClinicalResearchStaffCRServiceBean() {
        ClinicalResearchStaffCRServiceBean s = new ClinicalResearchStaffCRServiceBean();
        return s;
    }

    public static IdentifiedOrganizationServiceBean getIdentifiedOrganizationServiceBean() {
        IdentifiedOrganizationServiceBean bean = new IdentifiedOrganizationServiceBean();
        bean.setPublisher(getMessageProducer());
        return bean;
    }

    public static IdentifiedPersonServiceBean getIdentifiedPersonServiceBean() {
        IdentifiedPersonServiceBean bean = new IdentifiedPersonServiceBean();
        bean.setPublisher(getMessageProducer());
        return bean;
    }

    public static IdentifiedOrganizationCorrelationServiceRemote getIdentifiedOrganizationServiceBeanAsRemote() {
        IdentifiedOrganizationCorrelationServiceBean svc = new IdentifiedOrganizationCorrelationServiceBean();
        svc.setLocalService(getIdentifiedOrganizationServiceBean());
        svc.setLocalCRService(new IdentifiedOrganizationCrServiceBean());
        return (IdentifiedOrganizationCorrelationServiceRemote) wrapWithProxies(svc);
    }

    public static IdentifiedPersonCorrelationServiceRemote getIdentifiedPersonServiceBeanAsRemote() {
        IdentifiedPersonCorrelationServiceBean svc = new IdentifiedPersonCorrelationServiceBean();
        svc.setLocalService(getIdentifiedPersonServiceBean());
        svc.setLocalCRService(new IdentifiedPersonCrServiceBean());
        return (IdentifiedPersonCorrelationServiceRemote) wrapWithProxies(svc);
    }

    public static MessageProducerBean getMessageProducer() {
        try {
            MessageProducerBean mp = new MessageProducerBean() {
                private TopicConnectionFactory connectionFactory;
                private Topic topic;

                /**
                 * {@inheritDoc}
                 */
                @Override
                protected TopicConnectionFactory getTopicConnectionFactory(InitialContext ic) {
                    if (connectionFactory == null) {
                        connectionFactory = new TopicConnectionFactoryStub();
                    }
                    return connectionFactory;
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                protected Topic getTopic(InitialContext ic) {
                    if (topic == null) {
                        topic = new TopicStub(MessageProducerBean.TOPIC_NAME);
                    }
                    return topic;
                }
            };
            return mp;
        } catch (Exception ex) {
            throw new Error("bad test init", ex);
        }
    }

    public static OrganizationalContactCorrelationServiceRemote getOrganizationalContactCorrelationServiceRemote() {
        OrganizationalContactCorrelationServiceBean svc = new OrganizationalContactCorrelationServiceBean();
        svc.setOcService(getOrganizationalContactService());
        svc.setOcCRService(getOrganizationalContactCrService());
        return svc;

    }

    public static OrganizationalContactCRServiceLocal getOrganizationalContactCrService() {
        OrganizationalContactCRServiceBean svc = new OrganizationalContactCRServiceBean();
        OrganizationalContactServiceBean ocs = (OrganizationalContactServiceBean) getOrganizationalContactService();
        return svc;
    }

    public static OrganizationalContactServiceLocal getOrganizationalContactService() {
        OrganizationalContactServiceBean svc = new OrganizationalContactServiceBean();
        svc.setPublisher(getMessageProducer());
        return svc;
    }

    public static MessageProducerBean getNoPublishMessageProducer() {
        try {
            MessageProducerBean mp = new MessageProducerBean() {
                private TopicConnectionFactory connectionFactory;
                private Topic topic;

                /**
                 * {@inheritDoc}
                 */
                @Override
                protected TopicConnectionFactory getTopicConnectionFactory(InitialContext ic) {
                    if (connectionFactory == null) {
                        connectionFactory = new TopicConnectionFactoryStub();
                    }
                    return connectionFactory;
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                protected Topic getTopic(InitialContext ic) {
                    if (topic == null) {
                        topic = new TopicStub(MessageProducerBean.TOPIC_NAME);
                    }
                    return topic;
                }
                /**
                 * {@inheritDoc}
                 */
                @Override
                public void sendCreate(Class c, PersistentObject entity) throws JMSException {
                       throw new JMSException("operation is not supported");
                }
                /**
                 * {@inheritDoc}
                 */
                 @Override
                 public  void sendUpdate(Class c, PersistentObject entity) throws JMSException {
                     throw new JMSException("operation is not supported.");
                 }
            };
            return mp;
        } catch (Exception ex) {
            throw new Error("bad test init", ex);
        }
    }

    public static FamilyOrganizationRelationshipServiceLocal getFamilyOrganizationRelationshipService() {
        FamilyOrganizationRelationshipServiceBean bean = new FamilyOrganizationRelationshipServiceBean();
        bean.setPublisher(getMessageProducer());
        return bean;
    }

    public static OrganizationRelationshipServiceLocal getOrganizationRelationshipService() {
       return new OrganizationRelationshipServiceBean();
    }

    public static BusinessServiceRemote getBusinessService() {
        BusinessServiceBean svc = new BusinessServiceBean();
        svc.setCrsService(getClinicalResearchStaffServiceBean());
        svc.setHcfService(getHealthCareFacilityServiceBean());
        svc.setHcpService(getHealthCareProviderServiceBean());
        svc.setIdfOrgService(getIdentifiedOrganizationServiceBean());
        svc.setIdfPerService(getIdentifiedPersonServiceBean());
        svc.setOrganizationServiceBean(getOrganizationServiceBean());
        svc.setOrgContService(getOrganizationalContactService());
        svc.setOvSightCommService(getOversightCommitteeServiceBean());
        svc.setPersonServiceBean(getPersonServiceBean());
        svc.setResearchOrgService(getResearchOrganizationServiceBean());
        return svc;
    }

    public static GenericCodeValueServiceLocal getGenericCodeValueServiceBean() {
        GenericCodeValueServiceBean bean = new GenericCodeValueServiceBean();
        return bean;
    }

    public static CtepImportService getCtepImportService() {
        CtepImportServiceBean bean = new CtepImportServiceBean();
        return bean;
    }
    /**
     * Get a newly created family service.
     *
     * @return the service
     */
    public static FamilyServiceBean getFamilyServiceBean() {
        return new FamilyServiceBean();
    }

    public static GenericStructrualRoleCRServiceLocal<IdentifiedOrganizationCR> getIdentifiedOrganizationCrServiceBean() {
        return new IdentifiedOrganizationCrServiceBean();
    }

    public static GenericStructrualRoleCRServiceLocal<IdentifiedPersonCR> getIdentifiedPersonCRServiceBean() {
        return new IdentifiedPersonCrServiceBean();
    }
}
