/*
* caBIG Open Source Software License
* 
* Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Protocol  Abstraction (PA) Application
* was created with NCI funding and is part of  the caBIG initiative. The  software subject to  this notice  and license
* includes both  human readable source code form and machine readable, binary, object code form (the caBIG Software).
* 
* This caBIG Software License (the License) is between caBIG  Participant  and  You.  You (or Your) shall  mean a
* person or an entity, and all other entities that control, are  controlled by,  or  are under common  control  with the
* entity.  Control for purposes of this definition means
* 
* (i) the direct or indirect power to cause the direction or management of such entity,whether by contract 
* or otherwise,or
*  
* (ii) ownership of fifty percent (50%) or more of the outstanding shares, or 
* 
* (iii) beneficial ownership of such entity.
* License.  Provided that You agree to the conditions described below, caBIG Participant  grants  You a  non-exclusive,
* worldwide, perpetual, fully-paid-up, no-charge, irrevocable,  transferable  and royalty-free  right and license in its
* rights in the caBIG Software, including any copyright or patent rights therein, to 
* 
* (i) use,install, disclose, access, operate,  execute, reproduce,  copy, modify, translate,  market,  publicly display,
* publicly perform, and prepare derivative works of the caBIG Software in any manner and for any  purpose,  and to have
* or permit others to do so; 
* 
* (ii) make, have made, use, practice, sell, and offer  for sale,  import, and/or  otherwise  dispose of caBIG Software
* (or portions thereof); 
* 
* (iii) distribute and have distributed  to  and by third   parties the   caBIG  Software  and any   modifications  and
* derivative works thereof; and (iv) sublicense the  foregoing rights  set  out in (i), (ii) and (iii) to third parties,
* including the right to license such rights to further third parties. For sake of clarity,and not by way of limitation,
* caBIG Participant shall have no right of accounting or right of payment from You or Your sub licensees for the rights
* granted under this License.   This  License  is  granted  at no  charge  to You. Your downloading, copying, modifying,
* displaying, distributing or use of caBIG Software constitutes acceptance  of  all of the terms and conditions of this
* Agreement.  If You do not agree to such terms and conditions,  You have no right to download,  copy,  modify, display,
* distribute or use the caBIG Software.
* 
* 1.  Your redistributions of the source code for the caBIG Software must retain the above copyright notice, this  list
* of conditions and the disclaimer and limitation of liability of Article 6 below.   Your redistributions in object code
* form must reproduce the above copyright notice,  this list of  conditions  and the  disclaimer  of  Article  6  in the
* documentation and/or other materials provided with the distribution, if any.
* 
* 2.  Your end-user documentation included with the redistribution, if any,  must include the  following acknowledgment:
* This product includes software developed by ScenPro, Inc.   If  You  do not include such end-user documentation, You
* shall include this acknowledgment in the caBIG Software itself, wherever such third-party acknowledgments normally 
* appear.
* 
* 3.  You may not use the names ScenPro, Inc., The National Cancer Institute, NCI, Cancer Bioinformatics Grid or
* caBIG to endorse or promote products derived from this caBIG Software.  This License does not authorize You to use
* any trademarks, service marks, trade names, logos or product names of either caBIG Participant, NCI or caBIG, except
* as required to comply with the terms of this License.
* 
* 4.  For sake of clarity, and not by way of limitation, You  may incorporate this caBIG Software into Your proprietary
* programs and into any third party proprietary programs.  However, if You incorporate the  caBIG Software  into  third
* party proprietary programs,  You agree  that You are  solely responsible  for obtaining any permission from such third
* parties required to incorporate the caBIG Software  into such third party proprietary programs and for informing Your
* sub licensees, including without limitation Your end-users, of their obligation  to  secure  any  required permissions
* from such third parties before incorporating the caBIG Software into such third party proprietary  software programs.
* In the event that You fail to obtain such permissions,  You  agree  to  indemnify  caBIG  Participant  for any claims
* against caBIG Participant by such third parties, except to the extent prohibited by law,  resulting from Your failure
* to obtain such permissions.
* 
* 5.  For sake of clarity, and not by way of limitation, You may add Your own copyright statement  to Your modifications
* and to the derivative works, and You may provide  additional  or  different  license  terms  and  conditions  in  Your
* sublicenses of modifications of the caBIG  Software,  or  any  derivative  works  of  the caBIG Software as a whole,
* provided Your use, reproduction,  and  distribution  of the Work otherwise complies with the conditions stated in this
* License.
* 
* 6.  THIS caBIG SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES  ( INCLUDING, BUT NOT LIMITED TO,
* THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN 
* NO EVENT SHALL THE ScenPro, Inc. OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
* OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  LIMITED  TO,  PROCUREMENT OF SUBSTITUTE GOODS  OR SERVICES; LOSS OF USE,
* DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
* LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG SOFTWARE, EVEN
* IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
* 
* 
*/
package gov.nih.nci.pa.service.correlation;

import gov.nih.nci.pa.service.ArmServiceRemote;
import gov.nih.nci.pa.service.DiseaseServiceRemote;
import gov.nih.nci.pa.service.DocumentServiceRemote;
import gov.nih.nci.pa.service.DocumentWorkflowStatusServiceRemote;
import gov.nih.nci.pa.service.InterventionAlternateNameServiceRemote;
import gov.nih.nci.pa.service.InterventionServiceRemote;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PlannedActivityServiceRemote;
import gov.nih.nci.pa.service.StudyContactServiceRemote;
import gov.nih.nci.pa.service.StudyDiseaseServiceRemote;
import gov.nih.nci.pa.service.StudyIndldeServiceRemote;
import gov.nih.nci.pa.service.StudyOutcomeMeasureServiceRemote;
import gov.nih.nci.pa.service.StudyOverallStatusServiceRemote;
import gov.nih.nci.pa.service.StudyParticipationContactServiceRemote;
import gov.nih.nci.pa.service.StudyParticipationServiceRemote;
import gov.nih.nci.pa.service.StudyProtocolServiceRemote;
import gov.nih.nci.pa.service.StudyRecruitmentStatusServiceRemote;
import gov.nih.nci.pa.service.StudyRegulatoryAuthorityServiceRemote;
import gov.nih.nci.pa.service.StudyResourcingServiceRemote;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceRemote;
import gov.nih.nci.pa.service.util.MailManagerServiceRemote;
import gov.nih.nci.pa.service.util.PAOrganizationServiceRemote;
import gov.nih.nci.pa.service.util.PAPersonServiceRemote;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.service.util.RegistryUserServiceRemote;
import gov.nih.nci.pa.service.util.RegulatoryInformationServiceRemote;
import gov.nih.nci.pa.util.JNDIUtil;
import gov.nih.nci.pa.util.PaEarPropertyReader;
import gov.nih.nci.pa.util.PoPaServiceLocator;
import gov.nih.nci.services.correlation.ClinicalResearchStaffCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareFacilityCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareProviderCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OrganizationalContactCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OversightCommitteeCorrelationServiceRemote;
import gov.nih.nci.services.correlation.ResearchOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

/**
 * 
 * @author NAmiruddin
 *
 */
@SuppressWarnings({ "PMD.AvoidDuplicateLiterals" })
public class PoPaServiceBeanLookup implements PoPaServiceLocator {

    /**
     * @return PersonEntityServiceRemote
     * @throws PAException on error
     */
    public static PersonEntityServiceRemote getPersonEntityService() throws PAException {
        String serverInfo = "jnp://" + PaEarPropertyReader.getLookUpServerInfo() + "/po/PersonEntityServiceBean/remote";
        return (PersonEntityServiceRemote) JNDIUtil.lookupPo(serverInfo);
    }    
    
    /**
     * @return HealthCareFacilityCorrelationServiceRemote
     * @throws PAException on error
     */
    public static OversightCommitteeCorrelationServiceRemote
        getOversightCommitteeCorrelationService() throws PAException {
        String serverInfo = "jnp://" + PaEarPropertyReader.getLookUpServerInfo()
                                + "/po/OversightCommitteeCorrelationServiceBean/remote";
        return (OversightCommitteeCorrelationServiceRemote) JNDIUtil.lookupPo(serverInfo);
    } 
    
    /** 
     * @return OrganizationEntityServiceRemote
     * @throws PAException on error
     */
    public static OrganizationEntityServiceRemote getOrganizationEntityService() throws PAException {
        String serverInfo = "jnp://" + PaEarPropertyReader.getLookUpServerInfo()
                + "/po/OrganizationEntityServiceBean/remote";
        return (OrganizationEntityServiceRemote) JNDIUtil.lookupPo(serverInfo);
    }

    /**
     * @return HealthCareFacilityCorrelationServiceRemote
     * @throws PAException e
     */
    public static ClinicalResearchStaffCorrelationServiceRemote getClinicalResearchStaffCorrelationService()  
    throws PAException { 
        String serverInfo = "jnp://" + PaEarPropertyReader.getLookUpServerInfo()
            + "/po/ClinicalResearchStaffCorrelationServiceBean/remote";
        return (ClinicalResearchStaffCorrelationServiceRemote) JNDIUtil.lookupPo(serverInfo);
    }

    /**
     * @return HealthCareProviderCorrelationServiceRemote
     * @throws PAException on error
     */
    public static HealthCareProviderCorrelationServiceRemote 
        getHealthCareProviderCorrelationService() throws PAException {
        String serverInfo = "jnp://" + PaEarPropertyReader.getLookUpServerInfo()
                                + "/po/HealthCareProviderCorrelationServiceBean/remote";
        return (HealthCareProviderCorrelationServiceRemote) JNDIUtil.lookupPo(serverInfo);
    } 
    
    /**
     * @return HealthCareFacilityCorrelationServiceRemote
     * @throws PAException on error
     */
    public static HealthCareFacilityCorrelationServiceRemote
        getHealthCareFacilityCorrelationService() throws PAException {
        String serverInfo = "jnp://" + PaEarPropertyReader.getLookUpServerInfo()
                                + "/po/HealthCareFacilityCorrelationServiceBean/remote";
        return (HealthCareFacilityCorrelationServiceRemote) JNDIUtil.lookupPo(serverInfo);
    } 

    /**
     * @return HealthCareFacilityCorrelationServiceRemote
     * @throws PAException on error
     */
    public static ResearchOrganizationCorrelationServiceRemote
        getResearchOrganizationCorrelationService() throws PAException {
        String serverInfo = "jnp://" + PaEarPropertyReader.getLookUpServerInfo()
                                + "/po/ResearchOrganizationCorrelationServiceBean/remote";
        return (ResearchOrganizationCorrelationServiceRemote) JNDIUtil.lookupPo(serverInfo);
    } 

    /**
     * @return OrganizationalContactCorrelationServiceRemote
     * @throws PAException on error
     */
    public static OrganizationalContactCorrelationServiceRemote
        getOrganizationalContactCorrelationService() throws PAException {
        String serverInfo = "jnp://" + PaEarPropertyReader.getLookUpServerInfo()
                                + "/po/OrganizationalContactCorrelationServiceBean/remote";
        return (OrganizationalContactCorrelationServiceRemote) JNDIUtil.lookupPo(serverInfo);
    } 

    /**
     * @return StudyProtocolServiceRemote
     * @throws PAException on error
     */
    public static StudyProtocolServiceRemote
        getStudyProtocolService() throws PAException {
        return (StudyProtocolServiceRemote) JNDIUtil.lookup("/pa/StudyProtocolServiceBean/remote");
    } 

    /**
     * @return StudyProtocolServiceRemote
     * @throws PAException on error
     */
    public static StudyParticipationServiceRemote
        getStudyParticipationService() throws PAException {
        return (StudyParticipationServiceRemote) JNDIUtil.lookup("/pa/StudyParticipationServiceBean/remote");
    } 

    /**
     * @return StudyParticipationContactRemote
     * @throws PAException on error
     */
    public static StudyParticipationContactServiceRemote
        getStudyParticipationContactService() throws PAException {
        return (StudyParticipationContactServiceRemote) 
            JNDIUtil.lookup("/pa/StudyParticipationContactServiceBean/remote");
    } 

    /**
     * @return StudyProtocolServiceRemote
     * @throws PAException on error
     */
    public static StudyContactServiceRemote
        getStudyContactService() throws PAException {
        return (StudyContactServiceRemote) JNDIUtil.lookup("/pa/StudyContactServiceBean/remote");
    } 

    /**
     * @return StudyResourcingServiceRemote
     * @throws PAException on error
     */
    public static StudyResourcingServiceRemote
        getStudyResourcingService() throws PAException {
        return (StudyResourcingServiceRemote) JNDIUtil.lookup("/pa/StudyResourcingServiceBean/remote");
    } 
    
    /**
     * @return StudyOutcomeMeasureServiceRemote
     * @throws PAException on error
     */
    public static StudyOutcomeMeasureServiceRemote
        getStudyOutcomeMeasureService() throws PAException {
        return (StudyOutcomeMeasureServiceRemote) JNDIUtil.lookup("/pa/StudyOutcomeMeasureServiceBean/remote");
    } 
    /**
     * @return ProtocolQueryServiceRemote
     * @throws PAException on error
     */
    public static ProtocolQueryServiceLocal
        getProtocolQueryService() throws PAException {
        return (ProtocolQueryServiceLocal) JNDIUtil.lookup("/pa/ProtocolQueryServiceBean/local");
    } 
    /**
     * @return ArmServiceRemote
     * @throws PAException on error
     */
    public static ArmServiceRemote
        getArmService() throws PAException {
        return (ArmServiceRemote) JNDIUtil.lookup("/pa/ArmServiceBean/remote");
    } 
    
    /**
     * @return PlannedActivityServiceRemote
     * @throws PAException on error
     */
    public static PlannedActivityServiceRemote
        getPlannedActivityService() throws PAException {
        return (PlannedActivityServiceRemote) JNDIUtil.lookup("/pa/PlannedActivityServiceBean/remote");
    } 
    
    /**
     * @return StudySiteAccrualStatusServiceRemote
     * @throws PAException on error
     */
    public static StudySiteAccrualStatusServiceRemote
        getStudySiteAccrualStatusService() throws PAException {
        return (StudySiteAccrualStatusServiceRemote) JNDIUtil.lookup("/pa/StudySiteAccrualStatusServiceBean/remote");
    } 

    /**
     * @return StudyIndldeServiceRemote
     * @throws PAException on error
     */
    public static StudyIndldeServiceRemote
        getStudyIndldeService() throws PAException {
        return (StudyIndldeServiceRemote) JNDIUtil.lookup("/pa/StudyIndldeServiceBean/remote");
    } 

    /**
     * @return StudyRegulatoryAuthorityServiceRemote
     * @throws PAException on error
     */
    public static StudyRegulatoryAuthorityServiceRemote
        getStudyRegulatoryAuthorityService() throws PAException {
        return (StudyRegulatoryAuthorityServiceRemote) 
            JNDIUtil.lookup("/pa/StudyRegulatoryAuthorityServiceBean/remote");
    } 

    /**
     * @return RegulatoryInformationServiceRemote
     * @throws PAException on error
     */
    public static RegulatoryInformationServiceRemote
        getRegulatoryInformationService() throws PAException {
        return (RegulatoryInformationServiceRemote) JNDIUtil.lookup("/pa/RegulatoryInformationBean/remote");
    } 

    /**
     * @return StudyOverallStatusServiceRemote
     * @throws PAException on error
     */
    public static StudyOverallStatusServiceRemote
        getStudyOverallStatusService() throws PAException {
        return (StudyOverallStatusServiceRemote) JNDIUtil.lookup("/pa/StudyOverallStatusServiceBean/remote");
    } 

    /**
     * @return InterventionServiceRemote
     * @throws PAException on error
     */
    public static InterventionServiceRemote
        getInterventionService() throws PAException {
        return (InterventionServiceRemote) JNDIUtil.lookup("/pa/InterventionServiceBean/remote");
    } 
    
    /**
     * @return DocumentServiceRemote
     * @throws PAException on error
     */
    public static DocumentServiceRemote
        getDocumentService() throws PAException {
        return (DocumentServiceRemote) JNDIUtil.lookup("/pa/DocumentServiceBean/remote");
    } 


    /**
     * @return OrganizationSynchronizationServiceRemote
     * @throws PAException on error
     */
    public static OrganizationSynchronizationServiceRemote
        getOrganizationSynchronizationService() throws PAException {
        return (OrganizationSynchronizationServiceRemote) 
            JNDIUtil.lookup("/pa/OrganizationSynchronizationServiceBean/remote");
    } 
    
    /**
     * @return PersonSynchronizationServiceRemote
     * @throws PAException on error
     */
    public static PersonSynchronizationServiceRemote
        getPersonSynchronizationService() throws PAException {
        return (PersonSynchronizationServiceRemote) 
            JNDIUtil.lookup("/pa/PersonSynchronizationServiceBean/remote");
    } 

    /**
     * @return InterventionAlternateNameServiceRemote
     * @throws PAException on error
     */
    public static InterventionAlternateNameServiceRemote getInterventionAlternateNameService() throws PAException  {
        return (InterventionAlternateNameServiceRemote) 
        JNDIUtil.lookup("/pa/InterventionAlternateNameServiceBean/remote");
        
    }

    /**
     * @return RegistryUserServiceRemote
     * @throws PAException on error
     */
    public static RegistryUserServiceRemote getRegistryUserService() throws PAException  {
        return (RegistryUserServiceRemote) 
        JNDIUtil.lookup("/pa/RegistryUserServiceBean/remote");
        
    }
    /**
     * @return StudyDiseaseServiceRemote
     * @throws PAException on error
     */
    public static StudyDiseaseServiceRemote getStudyDiseaseService() throws PAException  {
        return (StudyDiseaseServiceRemote) 
        JNDIUtil.lookup("/pa/StudyDiseaseServiceBean/remote");
        
    }    
    /**
     * @return DiseaseServiceRemote
     * @throws PAException on error
     */
    public static DiseaseServiceRemote getDiseaseService() throws PAException  {
        return (DiseaseServiceRemote) 
        JNDIUtil.lookup("/pa/DiseaseServiceBean/remote");
    }    
    /**
     * @return StudyRecruitmentStatusServiceRemote
     * @throws PAException on error
     */
    public static StudyRecruitmentStatusServiceRemote getStudyRecruitmentStatusService() throws PAException  {
        return (StudyRecruitmentStatusServiceRemote) 
        JNDIUtil.lookup("/pa/StudyRecruitmentStatusServiceBean/remote");
    }    
    /**
     * @return PAOrganizationServiceRemote
     * @throws PAException on error
     */
    public static PAOrganizationServiceRemote getPAOrganizationService() throws PAException {
      return (PAOrganizationServiceRemote)
      JNDIUtil.lookup("pa/PAOrganizationServiceBean/remote");
    }
    
    /**
     * @return OrganizationCorrelationServiceRemote
     * @throws PAException on error
     */
    public static OrganizationCorrelationServiceRemote getOrganizationCorrelationService() throws PAException {
      return (OrganizationCorrelationServiceRemote)
      JNDIUtil.lookup("pa/OrganizationCorrelationServiceBean/remote");
    }
    
    /**
     * @return PAPersonServiceRemote
     * @throws PAException on error
     */
    public static PAPersonServiceRemote getPAPersonService() throws PAException {
      return (PAPersonServiceRemote)
      JNDIUtil.lookup("pa/PAPersonServiceBean/remote");
    }

    /**
     * @return DocumentWorkflowStatusServiceRemote
     * @throws PAException on error
     */
    public static DocumentWorkflowStatusServiceRemote getDocumentWorkflowStatusService() throws PAException {
      return (DocumentWorkflowStatusServiceRemote)
      JNDIUtil.lookup("pa/DocumentWorkflowStatusServiceBean/remote");
    }
    
    /**
     * 
     * @return MailManagerServiceRemote
     * @throws PAException ex
     */
    public static MailManagerServiceRemote getMailManagerService() throws PAException {
        return (MailManagerServiceRemote)
        JNDIUtil.lookup("pa/MailManagerServiceBean/remote");
      }
    
    /**
     * @return StudyProtocolServiceRemote
     * @throws PAException on error
     */
    public StudyProtocolServiceRemote
        getStudyProtocolServiceJU() throws PAException {
        return (StudyProtocolServiceRemote) JNDIUtil.lookup("/pa/StudyProtocolServiceBean/remote");
    }     
    
    /** 
     * @return OrganizationEntityServiceRemote
     * @throws PAException on error
     */
    public OrganizationEntityServiceRemote getOrganizationEntityServiceJU() throws PAException {
        String serverInfo = "jnp://" + PaEarPropertyReader.getLookUpServerInfo()
                + "/po/OrganizationEntityServiceBean/remote";
        return (OrganizationEntityServiceRemote) JNDIUtil.lookupPo(serverInfo);
    }

}
