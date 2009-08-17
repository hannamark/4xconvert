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
package gov.nih.nci.pa.service.util;

import gov.nih.nci.coppa.iso.Bl;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.RegulatoryAuthority;
import gov.nih.nci.pa.dto.AbstractionCompletionDTO;
import gov.nih.nci.pa.enums.ActiveInactiveCode;
import gov.nih.nci.pa.enums.ActivityCategoryCode;
import gov.nih.nci.pa.enums.ArmTypeCode;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.enums.InterventionTypeCode;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.enums.ReviewBoardApprovalStatusCode;
import gov.nih.nci.pa.enums.StructuralRoleStatusCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.enums.StudyRecruitmentStatusCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.dto.ArmDTO;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.InterventionDTO;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.ObservationalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.PlannedActivityDTO;
import gov.nih.nci.pa.iso.dto.PlannedEligibilityCriterionDTO;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudyDiseaseDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyOutcomeMeasureDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyRecruitmentStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.ArmServiceLocal;
import gov.nih.nci.pa.service.DocumentServiceLocal;
import gov.nih.nci.pa.service.InterventionServiceRemote;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PlannedActivityServiceLocal;
import gov.nih.nci.pa.service.StudyContactServiceLocal;
import gov.nih.nci.pa.service.StudyDiseaseServiceLocal;
import gov.nih.nci.pa.service.StudyIndldeServiceLocal;
import gov.nih.nci.pa.service.StudyOutcomeMeasureServiceLocal;
import gov.nih.nci.pa.service.StudyOverallStatusServiceLocal;
import gov.nih.nci.pa.service.StudySiteContactServiceLocal;
import gov.nih.nci.pa.service.StudySiteServiceLocal;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.StudyRecruitmentStatusServiceRemote;
import gov.nih.nci.pa.service.StudyRegulatoryAuthorityServiceLocal;
import gov.nih.nci.pa.service.StudyResourcingServiceLocal;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceLocal;
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
import gov.nih.nci.pa.util.HibernateSessionInterceptor;
import gov.nih.nci.pa.util.PAAttributeMaxLen;
import gov.nih.nci.pa.util.PAUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

/**
* service bean for validating the Abstraction.
*
* @author Kalpana Guthikonda
* @since 11/27/2008
* copyright NCI 2007.  All rights reserved.
* This code may not be used without the express written permission of the
* copyright holder, NCI.
*/
@SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.ExcessiveMethodLength" , "PMD.TooManyMethods",
  "PMD.AvoidDuplicateLiterals", "PMD.ExcessiveClassLength", "PMD.NPathComplexity" , "PMD.TooManyFields" })
@Stateless
@Interceptors(HibernateSessionInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class AbstractionCompletionServiceBean implements AbstractionCompletionServiceRemote {

    
    @EJB
    StudyProtocolServiceLocal studyProtocolService = null;
    @EJB
    StudyOverallStatusServiceLocal studyOverallStatusService = null;
    @EJB
    StudyIndldeServiceLocal studyIndldeService  = null;
    @EJB
    StudyDiseaseServiceLocal studyDiseaseService = null;
    @EJB
    StudyResourcingServiceLocal studyResourcingService = null;
    @EJB
    ArmServiceLocal armService = null;
    @EJB
    PlannedActivityServiceLocal plannedActivityService = null;
    @EJB
    StudySiteServiceLocal studySiteService = null;
    @EJB
    StudySiteContactServiceLocal studySiteContactService = null;
    @EJB
    StudyContactServiceLocal studyContactService = null;
    @EJB
    StudyOutcomeMeasureServiceLocal studyOutcomeMeasureService = null;
    @EJB
    StudyRegulatoryAuthorityServiceLocal studyRegulatoryAuthorityService = null;
    @EJB
    StudySiteAccrualStatusServiceLocal studySiteAccrualStatusServicLocal = null;
    @EJB
    StudyRecruitmentStatusServiceRemote studyRecruitmentStatusServiceRemote = null;
    @EJB
    DocumentServiceLocal documentServiceLocal = null;
    @EJB
    RegulatoryInformationServiceRemote regulatoryInfoBean = null;
    @EJB
    InterventionServiceRemote interventionSvc = null;
    private static final String YES = "Yes";
    private static final String NO = "No";

    /**
   * @param studyProtocolIi studyProtocolIi
   * @return AbstractionCompletionDTO list
   * @throws PAException on error
   */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public List<AbstractionCompletionDTO> validateAbstractionCompletion(Ii studyProtocolIi) throws PAException {
    if (studyProtocolIi == null) {
      throw new PAException("Study Protocol Identifer is null");
    }
    List<AbstractionCompletionDTO> abstractionList = new ArrayList<AbstractionCompletionDTO>();
    List<AbstractionCompletionDTO> abstractionWarnList = new ArrayList<AbstractionCompletionDTO>();
    
    StudyProtocolDTO studyProtocolDTO = studyProtocolService .getStudyProtocol(studyProtocolIi); 
   
    enforceIdentifierLength(studyProtocolDTO, abstractionList);
    enforceGeneralTrailDetails(studyProtocolDTO, abstractionList);
    enforceNCISpecificInfo(studyProtocolDTO, abstractionList);
    enforceRegulatoryInfo(studyProtocolIi, abstractionList);
    enforceIRBInfo(studyProtocolDTO, abstractionList, abstractionWarnList);
    enforceTrialINDIDE(studyProtocolDTO, abstractionList);
    enforceTrialStatus(studyProtocolDTO, abstractionList);
    enforceRecruitmentStatus(studyProtocolIi, abstractionList);

    List<DocumentDTO> isoList = documentServiceLocal.getDocumentsByStudyProtocol(studyProtocolIi);
    String protocolDoc = null;
    String irbDoc = null;
    if (!(isoList.isEmpty())) {
      for (DocumentDTO dto : isoList) {
        if (dto.getTypeCode().getCode().equalsIgnoreCase(
            DocumentTypeCode.PROTOCOL_DOCUMENT.getCode())) {
          protocolDoc = dto.getTypeCode().getCode().toString();
        } else if (dto.getTypeCode().getCode().equalsIgnoreCase(
            DocumentTypeCode.IRB_APPROVAL_DOCUMENT.getCode())) {
          irbDoc = dto.getTypeCode().getCode().toString();
        }
      }
    }
    enforceDocument(protocolDoc, irbDoc, abstractionList);

    if (studyProtocolDTO.getStudyProtocolType().getValue().equalsIgnoreCase("InterventionalStudyProtocol")) {
      InterventionalStudyProtocolDTO ispDTO = new InterventionalStudyProtocolDTO();
      ispDTO = studyProtocolService.getInterventionalStudyProtocol(studyProtocolIi);
      enforceInterventional(ispDTO, abstractionList);
      if (ispDTO.getNumberOfInterventionGroups().getValue() != null) {
        List<ArmDTO> aList = armService.getByStudyProtocol(studyProtocolIi);
        if (aList.size() != ispDTO.getNumberOfInterventionGroups().getValue()) {
         abstractionList.add(createError("Error", "Select Arm from Interventional Trial Design under Scientific"
              + " Data menu.", "Number of interventional trial arm records must be the same"
              + " as Number of Arms assigned in Interventional Trial Design."));
        }
      }
    } else if (studyProtocolDTO.getStudyProtocolType().getValue().equalsIgnoreCase("ObservationalStudyProtocol")) {
      ObservationalStudyProtocolDTO ospDTO = new ObservationalStudyProtocolDTO();
      ospDTO = studyProtocolService.getObservationalStudyProtocol(studyProtocolIi); 
      enforceObservational(ospDTO, abstractionList);
      if (ospDTO.getNumberOfGroups().getValue() != null) {
        List<ArmDTO> aList = armService.getByStudyProtocol(studyProtocolIi);
        if (aList.size() != ospDTO.getNumberOfGroups().getValue()) {
          abstractionList.add(createError("Error", "Select Groups from Observational Trial Design under Scientific "
              + "Data menu.", "Number of Observational study group records must be the same"
              + " as Number of Groups assigned in Observational Study Design."));
        }
      }
    }
    enforceTrialDescriptionDetails(studyProtocolDTO, abstractionList);
    enforceOutcomeMeasure(studyProtocolIi, abstractionList);
    enforceInterventions(studyProtocolIi, abstractionList, abstractionWarnList);
    enforceTreatingSite(studyProtocolIi, abstractionList);
    enforceStudyContactNullification(studyProtocolIi, abstractionWarnList);
    enforceStudySiteNullification(studyProtocolIi, abstractionWarnList);
    enforceStudySiteContactNullification(studyProtocolIi, abstractionWarnList, abstractionList);
    enforceArmGroup(studyProtocolIi, studyProtocolDTO, abstractionList);
    enforceTrialFunding(studyProtocolIi, abstractionList);
    enforceDisease(studyProtocolIi, abstractionList);
    enforceArmInterventional(studyProtocolIi, abstractionList);
    enforceEligibility(studyProtocolIi, abstractionList);
    enforceCollaborator(studyProtocolIi, abstractionList);
    abstractionList.addAll(abstractionWarnList);
    return abstractionList;
  }
  
  private void enforceStudyContactNullification(
          Ii studyProtocolIi, List<AbstractionCompletionDTO> abstractionWarnList) throws PAException {
      
      List<StudyContactDTO> scDtos = studyContactService.getByStudyProtocol(studyProtocolIi);
            
      if (scDtos != null && !scDtos.isEmpty()) {
          
          for (StudyContactDTO studyContactDTO : scDtos) {
             
             if (StructuralRoleStatusCode.NULLIFIED.getCode().
                     equalsIgnoreCase(studyContactDTO.getStatusCode().getCode())) { 
              if (StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR.getCode().equalsIgnoreCase(studyContactDTO.
                      getRoleCode().getCode())) {
                      
                  abstractionWarnList.add(createError("Warning", 
                               "Select General Trial Details from Administrative Data menu.",
                               "Prinicipal Investigator status has been set to nullified, " 
                               + "Please select another Principal Investigator")); 
                      }
              if (StudyContactRoleCode.CENTRAL_CONTACT.getCode().equalsIgnoreCase(studyContactDTO.
                      getRoleCode().getCode())) {
                      
                  abstractionWarnList.add(createError("Warning", 
                               "Select General Trial Details from Administrative Data menu.",
                               "Central Contact status has been set to nullified, " 
                               + "Please select another Central contact")); 
                      }
              if (StudyContactRoleCode.RESPONSIBLE_PARTY_STUDY_PRINCIPAL_INVESTIGATOR.getCode().
                      equalsIgnoreCase(studyContactDTO.getRoleCode().getCode())) {
                      
                  abstractionWarnList.add(createError("Warning", 
                               "Select General Trial Details from Administrative Data menu.",
                               "Responsible Party Study Principal Investigator status has been set to nullified, " 
                               + "Please select another Responsible Party Study Principal Investigator")); 
                      }
          }
         }     
      }
      
  }
  private void enforceStudySiteNullification(
          Ii studyProtocolIi, List<AbstractionCompletionDTO> abstractionWarnList)  throws PAException {
      
      List<StudySiteDTO> scDtos = studySiteService.getByStudyProtocol(studyProtocolIi);
     
      if (scDtos != null && !scDtos.isEmpty()) {
          
      for (StudySiteDTO studySiteDTO : scDtos) {
            
          if (StructuralRoleStatusCode.NULLIFIED.getCode().
                  equalsIgnoreCase(studySiteDTO.getStatusCode().getCode())) {
               
             if (StudySiteFunctionalCode.FUNDING_SOURCE.getCode().
                     equalsIgnoreCase(studySiteDTO.getFunctionalCode().getCode())) {
                      
                 abstractionWarnList.add(createError("Warning", 
                               "Select Collaborators from Administrative Data menu.",
                               "Funding Source status has been set to nullified, " 
                               + "Please select another Funding Source")); 
                 }
              if (StudySiteFunctionalCode.AGENT_SOURCE.getCode().
                      equalsIgnoreCase(studySiteDTO.getFunctionalCode().getCode())) {
                      
                  abstractionWarnList.add(createError("Warning", 
                               "Select Collaborators from Administrative Data menu.",
                               "Agent Source status has been set to nullified, " 
                               + "Please select another Agent Source")); 
                      }
              if (StudySiteFunctionalCode.LABORATORY.getCode().
                      equalsIgnoreCase(studySiteDTO.getFunctionalCode().getCode())) {
                      
                  abstractionWarnList.add(createError("Warning", 
                               "Select Collaborators from Administrative Data menu.",
                               "Laboratory status has been set to nullified, " 
                               + "Please select another Laboratory")); 
                      }
              if (StudySiteFunctionalCode.LEAD_ORGANIZATION.getCode().
                      equalsIgnoreCase(studySiteDTO.getFunctionalCode().getCode())) {
                      
                  abstractionWarnList.add(createError("Warning", 
                               "Select General Trial Details from Administrative Data menu.",
                               "Lead Organization status has been set to nullified, " 
                               + "Please select another Lead Organization")); 
                  }
              if (StudySiteFunctionalCode.RESPONSIBLE_PARTY_SPONSOR.getCode().
                      equalsIgnoreCase(studySiteDTO.getFunctionalCode().getCode())) {
                      
                  abstractionWarnList.add(createError("Warning", 
                               "Select General Trial Details from Administrative Data menu.",
                               "Responsible Party Sponsor status has been set to nullified, " 
                               + "Please select another Responsible Party Sponsor")); 
                      }
              if (StudySiteFunctionalCode.SPONSOR.getCode().
                      equalsIgnoreCase(studySiteDTO.getFunctionalCode().getCode())) {
                      
                  abstractionWarnList.add(createError("Warning", 
                               "Select General Trial Details from Administrative Data menu.",
                               "Sponsor status has been set to nullified, " 
                               + "Please select another Sponsor")); 
                      } 
              if (StudySiteFunctionalCode.TREATING_SITE.getCode().
                      equalsIgnoreCase(studySiteDTO.getFunctionalCode().getCode())) {
                     
                  abstractionWarnList.add(createError("Warning", 
                               "Select Participating Sites from Administrative Data menu.",
                               "Participating Site status has been set to nullified, " 
                               + "Please select another Participating Site")); 
                      }  
              if (StudySiteFunctionalCode.STUDY_OVERSIGHT_COMMITTEE.getCode().
                      equalsIgnoreCase(studySiteDTO.getFunctionalCode().getCode())) {
                      
                  abstractionWarnList.add(createError("Warning", 
                             "Select Human Subject Safety under Regulatory Information from Administrative Data menu.",
                             "Board status has been set to nullified, " 
                             + "Please select another Board")); 
                      } 
              }
        }   
      }
  }
  
   private void enforceStudySiteContactNullification(
          Ii studyProtocolIi, List<AbstractionCompletionDTO> abstractionWarnList, 
          List<AbstractionCompletionDTO> abstractionList)  throws PAException {
      
        List<StudySiteContactDTO> scDtos = 
          studySiteContactService.getByStudyProtocol(studyProtocolIi);
      
      if (scDtos != null && !scDtos.isEmpty()) {

        for (StudySiteContactDTO studySiteContactDTO : scDtos) {
  
          if (StructuralRoleStatusCode.NULLIFIED.getCode().
                  equalsIgnoreCase(studySiteContactDTO.getStatusCode().getCode())) {      
             
              if (StudySiteContactRoleCode.PRIMARY_CONTACT.getCode().
                      equalsIgnoreCase(studySiteContactDTO.getRoleCode().getCode())) {
          
                  abstractionWarnList.add(createError("Warning", 
                   "Select Contact tab under Participating Sites from Administrative Data menu.",
                   "Primary Contact status has been set to nullified, " 
                   + "Please select another Primary Contact")); 
              }
              
              if (StudySiteContactRoleCode.PRINCIPAL_INVESTIGATOR.getCode().
                      equalsIgnoreCase(studySiteContactDTO.getRoleCode().getCode())) {
          
                  abstractionWarnList.add(createError("Warning", 
                   "Select Investigators tab under Participating sites from Administrative Data menu.",
                   "Investigator status has been set to nullified, " 
                   + "Please select another Investigator")); 
              }
              
              if (StudySiteContactRoleCode.RESPONSIBLE_PARTY_SPONSOR_CONTACT.getCode().
                      equalsIgnoreCase(studySiteContactDTO.getRoleCode().getCode())) {
          
                  abstractionList.add(createError("Error", 
                   "Select General Trial Details from Administrative Data menu.",
                   "Responsible Party Sponsor Contact status has been set to nullified, " 
                   + "Please select another Responsible Party Sponsor Contact")); 
              }
             }
        }  
      }   
      
  }

  private void enforceDisease(Ii studyProtocolIi, List<AbstractionCompletionDTO> abstractionList) throws PAException {
      boolean leadExist = false;
      List<StudyDiseaseDTO> sdDtos = studyDiseaseService.getByStudyProtocol(studyProtocolIi);
      for (StudyDiseaseDTO sdDto : sdDtos) {
          if (sdDto.getLeadDiseaseIndicator() != null && sdDto.getLeadDiseaseIndicator().getValue()) {
              leadExist = true;
              break;
          }
      }
      if (!leadExist) {
          abstractionList.add(createError("Error", "Select Disease/Condition from Scientific Data Menu",
                  "Trial must include at least one LEAD disease"));
      }

  }
  @SuppressWarnings({"PMD" })
  private void enforceTrialFunding(Ii studyProtocolIi, List<AbstractionCompletionDTO> abstractionList)
  throws PAException {
    List<StudyResourcingDTO> srList = studyResourcingService.getstudyResourceByStudyProtocol(studyProtocolIi);
      
    if (!(srList.isEmpty())) {
      for (int i = 0; i < srList.size(); i++) {
        int j = 0;
        if (srList.size() > 1 && (!(i == 0))) {
          if (srList.get(j).getFundingMechanismCode().getCode().toString().equalsIgnoreCase(
              srList.get(i).getFundingMechanismCode().getCode().toString())
              && srList.get(j).getNihInstitutionCode().getCode().toString().equalsIgnoreCase(
                  srList.get(i).getNihInstitutionCode().getCode().toString())
               && srList.get(j).getNciDivisionProgramCode().getCode().toString().equalsIgnoreCase(
                   srList.get(i).getNciDivisionProgramCode().getCode().toString())
                && srList.get(j).getSerialNumber().getValue().toString().equalsIgnoreCase(
                    srList.get(i).getSerialNumber().getValue().toString())) {
           abstractionList.add(createError("Error", "Select Trial Funding from Administrative Data menu.",
                "Trial should not have Duplicate grants."));
            if (i == srList.size()) { //NOPMD
            } else {
              j++;
            }
          }
        }
      }
    }
  }

  private void enforceArmGroup(Ii studyProtocolIi,
          StudyProtocolDTO studyProtocolDTO, List<AbstractionCompletionDTO> abstractionList) throws PAException {
      List<ArmDTO> dtos = armService.getByStudyProtocol(studyProtocolIi); //PoPaServiceBeanLookup.getArmService()
      if (dtos.isEmpty()) {
          if (studyProtocolDTO.getStudyProtocolType().getValue().equalsIgnoreCase("InterventionalStudyProtocol")) {
              abstractionList.add(createError("Error", "Select Arm from Interventional Trial Design "
              + "under Scientific Data menu.", "No Arm exists for the trial."));
          } else if (studyProtocolDTO.getStudyProtocolType().getValue().
              equalsIgnoreCase("ObservationalStudyProtocol")) {
              abstractionList.add(createError("Error", "Select Groups from Observational Trial Design "
              + "under Scientific Data menu.", "No Groups exists for the trial."));
          }
      } else {
          for (ArmDTO dto : dtos) {
              if (PAUtil.isGreatenThan(dto.getName() , PAAttributeMaxLen.ARM_NAME)) {
              abstractionList.add(createError("Error", "Select Arm/Group under Scientific Data menu.",
              dto.getName().getValue() + "  must not be more than 62 characters  "));
              }
          }
      }
  }

  private void enforceTrialStatus(StudyProtocolDTO studyProtocolDTO, List<AbstractionCompletionDTO> abstractionList) 
  throws PAException {
      StudyOverallStatusDTO sos = studyOverallStatusService.getCurrentByStudyProtocol(studyProtocolDTO.getIdentifier());
      if (sos == null) {
      abstractionList.add(createError("Error", "Select Trial Status from Administrative Data menu.",
          "No Trial Status exists for the trial."));
      }
    if (studyProtocolDTO.getStartDate().getValue() == null
        && studyProtocolDTO.getStartDateTypeCode().getCode() == null
        && studyProtocolDTO.getPrimaryCompletionDate().getValue() == null
        && studyProtocolDTO.getPrimaryCompletionDateTypeCode().getCode() == null) {
        
        abstractionList.add(createError("Error", "Select Trial Status from Administrative Data menu.",
          "StartDate/StartDateType and PrimaryCompletionDate/PrimaryCompletionDateType must be Entered."));
    }
  }

  @SuppressWarnings({"PMD" })
  private void enforceTrialINDIDE(StudyProtocolDTO studyProtocolDto, List<AbstractionCompletionDTO> abstractionList)
  throws PAException {
    List<StudyIndldeDTO> siList = studyIndldeService.getByStudyProtocol(studyProtocolDto.getIdentifier());
    if (!(siList.isEmpty())) {
      for (int i = 0; i < siList.size(); i++) {
        int j = 0;
        if (siList.size() > 1 && (!(i == 0))) {
          if (siList.get(j).getGrantorCode().getCode().toString().equalsIgnoreCase(
                   siList.get(i).getGrantorCode().getCode().toString())
                && siList.get(j).getHolderTypeCode().getCode().toString().equalsIgnoreCase(
                    siList.get(i).getHolderTypeCode().getCode().toString())
                && siList.get(j).getIndldeNumber().getValue().toString().equalsIgnoreCase(
                        siList.get(i).getIndldeNumber().getValue().toString())
                && siList.get(j).getIndldeTypeCode().getCode().toString().equalsIgnoreCase(
                            siList.get(i).getIndldeTypeCode().getCode().toString())) {
           abstractionList.add(createError("Error", "Select Trial IND/IDE under Regulatory Information"
                + " from Administrative Data menu.", "Trial IND/IDE should not have Duplicate values."));
            if (i == siList.size()) { //NOPMD
            } else {
              j++;
            }
          } // if
        } // if
      } // for
      if (!BlConverter.covertToBool(studyProtocolDto.getFdaRegulatedIndicator())) {
          abstractionList.add(createError("Error", "Select Regulatory Information from Administrative Data menu.",
                  "FDA Regulated Intervention Indicator must be Yes since it has Trial IND/IDE records."));
      }
      //if IND is is there for Trial Oversight Authority Country  =USA
      //then Trial Oversight Authority Organization Name  shld be FDA if not throw err
      //get the country and check if its usa if so then check if Org name is FDA if not throw err
      StudyRegulatoryAuthorityDTO sraFromDatabaseDTO = 
          studyRegulatoryAuthorityService.getCurrentByStudyProtocol(studyProtocolDto.getIdentifier());
      if (sraFromDatabaseDTO != null) {
      Long sraId = Long.valueOf(sraFromDatabaseDTO.getRegulatoryAuthorityIdentifier().getExtension());
      RegulatoryAuthority regAuth = regulatoryInfoBean.get(sraId);
      if (regAuth.getCountry().getAlpha3().equals("USA") 
              && !regAuth.getAuthorityName().equalsIgnoreCase("Food and Drug Administration")) {
          abstractionList.add(createError("Error", "Select Regulatory under Regulatory Information"
                  + " from Administrative Data menu.", "For IND protocols, Oversight Authorities " 
                  + " must include United States: Food and Drug Administration."));
      }
    }
    } // if
  } // method


  private void enforceRegulatoryInfo(Ii studyProtocolIi, List<AbstractionCompletionDTO> abstractionList)
  throws PAException {
      
    List<StudyRegulatoryAuthorityDTO> sraDTOList = studyRegulatoryAuthorityService.getByStudyProtocol(studyProtocolIi);
    StudyRegulatoryAuthorityDTO sraDTO = null;
    if (!sraDTOList.isEmpty()) {
        sraDTO = sraDTOList.get(0);
    }
    if (sraDTO == null) {
      abstractionList.add(createError("Error", "Select Regulatory under Regulatory Information"
          + " from Administrative Data menu.", "Regulatory Information fields must be Entered."));
    }
        //Display error in abstraction validation if section 801 indicator = ‘yes’,  
        //delayed posting indicator is yes and trial does not include Intervention with type ‘Device’ 
        StudyProtocolDTO spDTO = studyProtocolService.getStudyProtocol(studyProtocolIi);
        if (YES.equalsIgnoreCase(convertBLToString(spDTO.getSection801Indicator()))
                && YES.equalsIgnoreCase(convertBLToString(spDTO.getDelayedpostingIndicator()))
                && !isDeviceFound(studyProtocolIi)) {
            abstractionList.add(createError("Error", "Select Regulatory under Regulatory Information"
                    + " from Administrative Data menu.", "Delay posting indicator can only be set to \'yes\' " 
                    + " if study includes at least one intervention with type \'device\'."));
        }
      
  }
  
  private void enforceIRBInfo(StudyProtocolDTO spDto, List<AbstractionCompletionDTO> abstractionList, 
          List<AbstractionCompletionDTO> abstractionWarnList)
  throws PAException {
      
      Boolean reviewBoardIndicator = spDto.getReviewBoardApprovalRequiredIndicator().getValue();
     
    if (reviewBoardIndicator == null) {
          abstractionList.add(createError("Error", "Select Human Subject Safety under Regulatory Information"
          + " from Administrative Data menu.", 
          "Review Board Approval Status is missing, Please complete Human Subject Review information."));
    }
    StudySiteDTO srDTO = new StudySiteDTO();
    srDTO.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.STUDY_OVERSIGHT_COMMITTEE));
    List<StudySiteDTO> spList = studySiteService.getByStudyProtocol(spDto.getIdentifier(), srDTO);
       
    StudyOverallStatusDTO sos = studyOverallStatusService.getCurrentByStudyProtocol(spDto.getIdentifier());
    if (sos != null && !spList.isEmpty()) {
        StudySiteDTO studySite = spList.get(0);
        if (StudyStatusCode.IN_REVIEW.getCode().equalsIgnoreCase(sos.getStatusCode().getCode()) 
                && !studySite.getReviewBoardApprovalStatusCode().getCode()
                .equals(ReviewBoardApprovalStatusCode.SUBMITTED_PENDING.getCode())) {
            abstractionWarnList.add(createError("Warning", "Select Human Subject Safety under Regulatory Information",
                    "Data inconsistency: \'Submitted, pending\' value (Review Board Approval Status) " 
                    + "is only valid for the current trial status \'In-Review\'."));
            
        }
        if (StudyStatusCode.DISAPPROVED.getCode().equalsIgnoreCase(sos.getStatusCode().getCode()) 
                && !studySite.getReviewBoardApprovalStatusCode().getCode()
                .equals(ReviewBoardApprovalStatusCode.SUBMITTED_DENIED.getCode())) {
            abstractionWarnList.add(createError("Warning", "Select Human Subject Safety under Regulatory Information",
                    "Data inconsistency: \'Submitted, denied\' value (Review Board Approval Status) is "
                    + "only valid for the current trial status \'Disapproved\'."));
            
        }
    }
    
  }
  @SuppressWarnings({"PMD" })
  private void enforceRecruitmentStatus(Ii studyProtocolIi, List<AbstractionCompletionDTO> abstractionList) 
  throws PAException {
      
      StudySiteDTO srDTO = new StudySiteDTO();
      srDTO.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.TREATING_SITE));
      List<StudySiteDTO> spList = studySiteService.getByStudyProtocol(studyProtocolIi, srDTO);
     
    //check recruitment status
      StudyRecruitmentStatusDTO recruitmentStatusDto = 
              studyRecruitmentStatusServiceRemote.getCurrentByStudyProtocol(studyProtocolIi);
     
      if (StudyRecruitmentStatusCode.RECRUITING_ACTIVE.getCode().
              equalsIgnoreCase(recruitmentStatusDto.getStatusCode().getCode())) {
          boolean recruiting = false;
          for (StudySiteDTO spartDto : spList) {
              List<StudySiteAccrualStatusDTO> studySiteList =  new ArrayList<StudySiteAccrualStatusDTO>();   
              studySiteList.
                  addAll(studySiteAccrualStatusServicLocal.
                      getStudySiteAccrualStatusByStudySite(spartDto.getIdentifier()));
          
              Long tmp = 1L;
              StudySiteAccrualStatusDTO latestDTO = null;
              for (StudySiteAccrualStatusDTO studySiteAccuralStatus : studySiteList) {
                  Long latestId = IiConverter.convertToLong(studySiteAccuralStatus.getIdentifier());
                  if (latestId > tmp) {
                      tmp = latestId;
                      latestDTO = studySiteAccuralStatus;
                  }
              
              }
          
              if (latestDTO != null) {
                  if (RecruitmentStatusCode.RECRUITING.getCode().
                          equalsIgnoreCase(latestDTO.getStatusCode().getCode())) {
                      recruiting = true;
                      break;
               }
           }
         }
          
          if (!recruiting) {
              abstractionList.add(createError("Error", "Select Participating Sites from "
                      + "Administrative Data menu.", "Data inconsistency: Atleast one location needs to be recruiting"
                      + " if the overall status recruitment status is\'Recruiting\'"));   
          }
       }
    }

  private void enforceTreatingSite(Ii studyProtocolIi, List<AbstractionCompletionDTO> abstractionList)
  throws PAException {
    StudySiteDTO srDTO = new StudySiteDTO();
    srDTO.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.TREATING_SITE));
    List<StudySiteDTO> spList = studySiteService.getByStudyProtocol(studyProtocolIi, srDTO);
    if (spList == null || spList.isEmpty()) {
     abstractionList.add(createError("Error", "Select Participating Sites from "
          + " Administrative Data menu.",  "No Participating Sites exists for the trial."));
      return;
    }
   //check if central contact exits for the study
    boolean centralContactDefined = isCentralContactDefined(studyProtocolIi);
    
    for (StudySiteDTO spartDto : spList) {
      List<StudySiteContactDTO> spContactDtos =
                studySiteContactService.getByStudySite(spartDto.getIdentifier());
        boolean piFound = false;
        boolean contactFound = false;
        for (StudySiteContactDTO spContactDto : spContactDtos) {
            if (StudySiteContactRoleCode.PRINCIPAL_INVESTIGATOR.getCode()
                        .equalsIgnoreCase(spContactDto.getRoleCode().getCode())
                || StudySiteContactRoleCode.SUB_INVESTIGATOR.getCode()
                        .equalsIgnoreCase(spContactDto.getRoleCode().getCode())) {
                piFound = true;
            } else if (StudySiteContactRoleCode.PRIMARY_CONTACT.getCode()
                    .equalsIgnoreCase(spContactDto.getRoleCode().getCode())) {
                contactFound = true;
            }
          
        }
        Organization orgBo = getPoOrg(spartDto);
        if (!piFound) {
            // Error Message ID Does Not Match Participating Site PO ID#
            abstractionList.add(createError("Error",
                    "Select Participating Sites from Administrative Data menu.",
                    "Participating site # " + orgBo.getIdentifier()
                    + " Must have an Investigator"));

        }
      // No investigator duplicates must exist on the same treating site for the same trial.
        if (piFound && hasDuplicate(getPIForTreatingSite(spContactDtos))) {
                    abstractionList.add(createError("Error", "Select Participating Sites from "
                    + " Administrative Data menu.",  "Treating site can not have duplicate investigator."));
                    break;
        } 
         //abstraction validation rule for participating site contact and central contact  
        if (!contactFound && !centralContactDefined) {
                abstractionList.add(createError("Error", " ", "Participating Site Contact OR Central Contact"
                  +  " information is mandatory. Complete Central Contact (General Trial Details screen)"
                  +  " or each Participating Site Contact (Participating Sites screen)  information."));
        }

    }
    //No participating site duplicates playing same role must exist on the same trial
    if (hasDuplicate(getTreatingSiteOrg(spList))) {
           abstractionList.add(createError("Error", "Select Participating Sites from "
               + " Administrative Data menu.",  "Trial can not have dupicate Treating Site."));
    }    

  }
  
  private boolean isCentralContactDefined(Ii studyProtocolIi) throws PAException {
      
      boolean ccDefined = false;
      List<StudyContactDTO> scDtos = studyContactService.getByStudyProtocol(studyProtocolIi);
      if (scDtos != null && !scDtos.isEmpty()) {
             for (StudyContactDTO studyContactDTO : scDtos) {
                 if (StudyContactRoleCode.CENTRAL_CONTACT.getCode().equalsIgnoreCase(studyContactDTO.
                         getRoleCode().getCode())) {
                     ccDefined = true;
                 }
             }
      }      
       return ccDefined;      
  }
  private void enforceCollaborator(Ii studyProtocolIi, List<AbstractionCompletionDTO> abstractionList)
  throws PAException {
      ArrayList<StudySiteDTO> criteriaList = new ArrayList<StudySiteDTO>();
      for (StudySiteFunctionalCode cd : StudySiteFunctionalCode.values()) {
          if (cd.isCollaboratorCode()) {
              StudySiteDTO searchCode = new StudySiteDTO();
              searchCode.setFunctionalCode(CdConverter.convertToCd(cd));
              criteriaList.add(searchCode);
          }
      }
      List<StudySiteDTO> spList = studySiteService.getByStudyProtocol(studyProtocolIi, criteriaList);
      List<String> newspList = new ArrayList<String>(); 
      for (StudySiteDTO spdto : spList) {
          newspList.add(spdto.getFunctionalCode().getCode() + spdto.getResearchOrganizationIi().getExtension());
        }
      if (hasDuplicate(newspList)) {
          abstractionList.add(createError("Error", "Select Collaborators from "
              + " Administrative Data menu.",  "Trial can not have a duplicate collaborator playing the same role."));
   }    
  }
/**
 * @param spartDto
 * @return
 * @throws PAException
 */
private Organization getPoOrg(StudySiteDTO spartDto)
        throws PAException {
    CorrelationUtils cUtils = new CorrelationUtils();
    Organization orgBo = cUtils.getPAOrganizationByPAHealthCareFacilityId(IiConverter.convertToLong(spartDto
            .getHealthcareFacilityIi()));
    return orgBo;
}
  private List<String> getPIForTreatingSite(List<StudySiteContactDTO> spContactDtos) {
      List<String> piList = new ArrayList<String>();
      for (StudySiteContactDTO dto : spContactDtos) {
          if (StudySiteContactRoleCode.PRINCIPAL_INVESTIGATOR.getCode()
                  .equalsIgnoreCase(dto.getRoleCode().getCode())
          || StudySiteContactRoleCode.SUB_INVESTIGATOR.getCode()
                  .equalsIgnoreCase(dto.getRoleCode().getCode())) {
              piList.add(dto.getHealthCareProviderIi().getExtension() 
                      + dto.getClinicalResearchStaffIi().getExtension());
          }
      }
      return piList;
  }
  private List<String> getTreatingSiteOrg(List<StudySiteDTO> spartList) {
      List<String> treatingSiteList = new ArrayList<String>();
      for (StudySiteDTO spdto : spartList) {
        treatingSiteList.add(spdto.getHealthcareFacilityIi().getExtension());
      }
    return treatingSiteList;
  }
  
  private <T> boolean hasDuplicate(Collection<T> list) {
      Set<T> set = new HashSet<T>();    
      // Set#add returns false if the set does not change, which    
      // indicates that a duplicate element has been added.
      boolean dup = false;
      for (T each : list) {
          if (!set.add(each)) {
              dup = true;
          }
      }
      return dup;
  }

  private void enforceInterventions(Ii studyProtocolIi, List<AbstractionCompletionDTO> abstractionList,
          List<AbstractionCompletionDTO> abstractionWarnList)
  throws PAException {
    List<PlannedActivityDTO> paList = plannedActivityService.getByStudyProtocol(studyProtocolIi);
    boolean interventionsList = false;
    for (PlannedActivityDTO pa : paList) {
      if (ActivityCategoryCode.INTERVENTION.equals(ActivityCategoryCode.getByCode(CdConverter
              .convertCdToString(pa.getCategoryCode())))) {
        interventionsList = true;
        //validation rules for inactive interventions
        InterventionDTO iDto = interventionSvc.get(pa.getInterventionIdentifier());
        if (ActiveInactiveCode.INACTIVE.getCode().equalsIgnoreCase(iDto.getStatusCode().getCode())) {
            abstractionWarnList.add(createError("Warning", "Select Interventions from Scientific Data menu.",
            "Intervention '" + iDto.getName().getValue() + "' status has been set to inactive"
            + ", Please select another Intervention."));
        }
        
      }
    }
    if (!interventionsList) {
      abstractionList.add(createError("Error", "Select Interventions from Scientific Data menu.",
      "No Interventions exists for the trial."));
    }
  }


  private void enforceOutcomeMeasure(Ii studyProtocolIi, List<AbstractionCompletionDTO> abstractionList)
  throws PAException {
    List<StudyOutcomeMeasureDTO> somList = studyOutcomeMeasureService.getByStudyProtocol(studyProtocolIi);
    boolean isPrimayFound = false;
    for (StudyOutcomeMeasureDTO somDto : somList) {
        if (BlConverter.covertToBool(somDto.getPrimaryIndicator())) {
            isPrimayFound = true;
            break;
        }
    }
    if (!isPrimayFound) {
      abstractionList.add(createError("Error", "Select Outcome Measure from  "
          + "Interventional/Observational under Scientific Data menu.",
          "Trial must include at least one PRIMARY outcome measure."));
    }
  }

  private void enforceGeneralTrailDetails(StudyProtocolDTO studyProtocolDTO,
          List<AbstractionCompletionDTO> abstractionList) {
        if (studyProtocolDTO.getAssignedIdentifier().getExtension() == null) {
         abstractionList.add(createError("Error", "Select General Trial Details from Administrative Data menu.",
              "NCI Trial Identifier must be Entered"));
        }
        if (studyProtocolDTO.getOfficialTitle().getValue() == null) {
          abstractionList.add(createError("Error", "Select General Trial Details from Administrative Data menu.",
              "Official Title must be Entered"));
        } else if (PAUtil.isGreatenThan(studyProtocolDTO.getOfficialTitle(),
                PAAttributeMaxLen.LEN_600)) {
            abstractionList.add(createError("Error", "Select General Trial Details from Administrative Data menu.",
                "Official Title cannot be more than 600 chracters "));
        }
        if (PAUtil.isGreatenThan(studyProtocolDTO.getAcronym(), PAAttributeMaxLen.ACRONYM)) {
            abstractionList.add(createError("Error", "Select General Trial Details from Administrative Data menu.",
            "Acronym must not be more than 14 characters "));
        }
        if (PAUtil.isGreatenThan(studyProtocolDTO.getScientificDescription(), PAAttributeMaxLen.LEN_32000)) {
            abstractionList.add(createError("Error", "Select General Trial Details from Administrative Data menu.",
            "Detailed Description must not be more than 32000 characters "));
        }
        if (PAUtil.isGreatenThan(studyProtocolDTO.getKeywordText(), PAAttributeMaxLen.KEYWORD)) {
            abstractionList.add(createError("Error", "Select General Trial Details from Administrative Data menu.",
            "Keywords must not be more than 160 characters "));
        }
      }


  private void enforceTrialDescriptionDetails(StudyProtocolDTO studyProtocolDTO,
          List<AbstractionCompletionDTO> abstractionList) {
      if (studyProtocolDTO.getPublicTitle().getValue() == null) {
          abstractionList.add(createError("Error", "Select Trial Description from Scientific Data menu.",
          "Brief Title must be Entered"));
      } else if (!PAUtil.isWithinRange(studyProtocolDTO.getPublicTitle(),
          PAAttributeMaxLen.LEN_18, PAAttributeMaxLen.LEN_300)) {
          abstractionList.add(createError("Error", "Select Trial Description from Scientific Data menu.",
          "Brief Title must be between 18 and 300 characters "));
      }
      if (studyProtocolDTO.getPublicDescription().getValue() == null) {
          abstractionList.add(createError("Error", "Select Trial Description from Scientific Data menu.",
          "Brief Summary must be Entered"));
      } else if (PAUtil.isGreatenThan(studyProtocolDTO.getPublicDescription(),
          PAAttributeMaxLen.LEN_5000)) {
          abstractionList.add(createError("Error", "Select Trial Description from Scientific Data menu.",
          "Brief Summary must not be more than 5000 characters "));
      }

  }

  private void enforceNCISpecificInfo(StudyProtocolDTO studyProtocolDTO, List<AbstractionCompletionDTO> abstractionList)
  {
   if (studyProtocolDTO.getAccrualReportingMethodCode().getCode() == null) {
     abstractionList.add(createError("Error", "Select NCI Specific Information from Administrative Data menu.",
          "Reporting Data Set Method must be Entered"));
    }
  }
  private void enforceDocument(String protocolDoc, String irbDoc, List<AbstractionCompletionDTO> abstractionList) {
    if (protocolDoc == null) {
      abstractionList.add(createError("Error", "Select Trial Related Documents from Administrative Data menu.",
          "Protocol_Document is required"));
    }
    if (irbDoc == null) {
      abstractionList.add(createError("Error", "Select Trial Related Documents from Administrative Data menu.",
           "IRB_Approval_Document is required"));
    }
  }

  private void enforceObservational(ObservationalStudyProtocolDTO ospDTO,
      List<AbstractionCompletionDTO> abstractionList) {
    if (ospDTO.getStudyModelCode().getCode() == null) {
      abstractionList.add(createError("Error", "Select Design Details from "
          + "Observational Trial Design under Scientific Data menu.", "Study Model must be Entered"));
    }
    if (ospDTO.getStudyModelCode().getCode() != null
        && ospDTO.getStudyModelCode().getCode().equalsIgnoreCase("Other")
        && ospDTO.getStudyModelOtherText() ==  null) {
    abstractionList.add(createError("Error", "Select Design Details from "
          + "Observational Trial Design under Scientific Data menu.", "Study Model Comment must be Entered"));
    }

    if (ospDTO.getTimePerspectiveCode().getCode() == null) {
     abstractionList.add(createError("Error", "Select Design Details from "
          + "Observational Trial Design under Scientific Data menu.", "Time Perspective must be Entered"));
    }
    if (ospDTO.getTimePerspectiveCode().getCode() != null
        && ospDTO.getTimePerspectiveCode().getCode().equalsIgnoreCase("Other")
        && ospDTO.getTimePerspectiveOtherText() == null) {
     abstractionList.add(createError("Error", "Select Design Details from "
          + "Observational Trial Design under Scientific Data menu.", "Time Perspective Comment must be Entered"));
    }
    if (ospDTO.getBiospecimenRetentionCode().getCode() == null) {
     abstractionList.add(createError("Error", "Select Design Details from "
          + "Observational Trial Design under Scientific Data menu.", "Bio-specimen Retention must be Entered"));
    }
    if (ospDTO.getNumberOfGroups().getValue() == null) {
     abstractionList.add(createError("Error", "Select Design Details from "
          + "Observational Trial Design under Scientific Data menu.", "Number of Groups/Cohorts must be Entered"));
    }
    if (ospDTO.getTargetAccrualNumber().getLow().getValue() == null) {
      abstractionList.add(createError("Error", "Select Design Details from "
          + "Observational Trial Design under Scientific Data menu.", "Target Enrollment must be Entered"));
    }
  }

  @SuppressWarnings({"PMD" })
  private void enforceInterventional(InterventionalStudyProtocolDTO ispDTO,
      List<AbstractionCompletionDTO> abstractionList) {
  if (ispDTO.getPrimaryPurposeCode().getCode() == null) {
     abstractionList.add(createError("Error", "Select Design Details from "
          + "Interventional Trial Design under Scientific Data menu.", "Primary Purpose must be Entered"));
    }
    if (ispDTO.getPrimaryPurposeCode().getCode() != null
        && ispDTO.getPrimaryPurposeCode().getCode().equalsIgnoreCase("Other")
        && ispDTO.getPrimaryPurposeOtherText() == null) {
      abstractionList.add(createError("Error", "Select Design Details from "
          + "Interventional Trial Design under Scientific Data menu.", "Provide comment if Other"));
    }
   /* if (ispDTO.getPrimaryPurposeCode().getCode() != null) {
      if (ispDTO.getPrimaryPurposeCode().getCode().equalsIgnoreCase("Early Detection")
          || ispDTO.getPrimaryPurposeCode().getCode().equalsIgnoreCase("Epidemiologic")
          || ispDTO.getPrimaryPurposeCode().getCode().equalsIgnoreCase("Observational")
          || ispDTO.getPrimaryPurposeCode().getCode().equalsIgnoreCase("Outcome")
          || ispDTO.getPrimaryPurposeCode().getCode().equalsIgnoreCase("Ancillary")
          || ispDTO.getPrimaryPurposeCode().getCode().equalsIgnoreCase("Correlative")) {
        abstractionList.add(createError("Error", "Select Design Details from "
            + "Interventional Trial Design under Scientific Data menu.", "Primary Purpose must not be "
            + ispDTO.getPrimaryPurposeCode().getCode()
            + ", Please Modify the Primary Purpose"));
      }
    }*/
    if (ispDTO.getPhaseCode().getCode() == null) {
      abstractionList.add(createError("Error", "Select Design Details from "
          + "Interventional Trial Design under Scientific Data menu.", "Trial Phase must be Entered"));
    }
    if (ispDTO.getDesignConfigurationCode().getCode() == null) {
      abstractionList.add(createError("Error", "Select Design Details from "
          + "Interventional Trial Design under Scientific Data menu.", "Intervention Model must be Entered"));
    }
    if (ispDTO.getNumberOfInterventionGroups().getValue() == null) {
      abstractionList.add(createError("Error", "Select Design Details from "
          + "Interventional Trial Design under Scientific Data menu.", "Number of Arms must be Entered"));
    }
    if (ispDTO.getBlindingSchemaCode().getCode() == null) {
      abstractionList.add(createError("Error", "Select Design Details from "
          + "Interventional Trial Design under Scientific Data menu.", "Masking must be Entered"));
    }
    if (ispDTO.getAllocationCode().getCode() == null) {
      abstractionList.add(createError("Error", "Select Design Details from "
          + "Interventional Trial Design under Scientific Data menu.", "Allocation must be Entered"));
    }
    if (ispDTO.getTargetAccrualNumber().getLow().getValue() == null) {
      abstractionList.add(createError("Error",
          "Select Design Details from "
          + "Interventional Trial Design under Scientific Data menu.", "Target Enrollment must be Entered"));
    }
  }

  private void enforceArmInterventional(Ii studyProtocolIi, List<AbstractionCompletionDTO> abstractionList)
  throws PAException {
    List<PlannedActivityDTO> paList =   plannedActivityService.getByStudyProtocol(studyProtocolIi);
    HashMap<String, String> intervention = new HashMap<String, String>();
    for (PlannedActivityDTO pa : paList) {
      if (ActivityCategoryCode.INTERVENTION.equals(ActivityCategoryCode.getByCode(CdConverter
              .convertCdToString(pa.getCategoryCode())))) {
          List<ArmDTO> armDtos = armService.getByPlannedActivity(pa.getIdentifier()); 
    
          if (armDtos == null || armDtos.isEmpty()) {
              abstractionList.add(createError("Error",
                      "Select Arm from Scientific Data menu and associated Intervention." ,  "Every intervention "
                       + "in interventional trial must be associated with at least one arm in interventional trial"));
          }
          for (ArmDTO armDTO : armDtos) {
              intervention.put(armDTO.getName().getValue() , armDTO.getName().getValue());
          }
      }
    }
    List<ArmDTO> arms = armService.getByStudyProtocol(studyProtocolIi); //PoPaServiceBeanLookup.getArmService()
    for (ArmDTO armDTO : arms) {
        if (ArmTypeCode.NO_INTERVENTION.getCode().equals(armDTO.getTypeCode().getCode())) {
            continue;
        }
        if (!intervention.containsKey(armDTO.getName().getValue())) {
          abstractionList.add(createError("Error",
                    "Select Arm from Scientific Data menu and associated Interventional." ,
                    "Arm " + armDTO.getName().getValue() + " does not have any Intervention associated"));

        }
    }
  }


  private  void enforceEligibility(Ii studyProtocolIi, List<AbstractionCompletionDTO> abstractionList)
  throws PAException {
      List<PlannedEligibilityCriterionDTO> paECs = 
          plannedActivityService.getPlannedEligibilityCriterionByStudyProtocol(studyProtocolIi);
                
      if (paECs == null || paECs.isEmpty()) {
          abstractionList.add(
          createError("Error",
                  "Select Eligibilty Criteria from specific Interventional/Observational under Scientific Data menu.",
              " Does not have any Eligibilty Criteria"));
          return;
      }
      boolean otherCriteriaExist = false;
      for (PlannedEligibilityCriterionDTO paEC : paECs) {
          if (ActivityCategoryCode.OTHER.getCode().equals(paEC.getCategoryCode().getCode())) {
              otherCriteriaExist = true;
          }
      } // for loop
      if (!otherCriteriaExist) {
          abstractionList.add(createError("Error",
                  "Select Eligibilty Criteria from specific Interventional/Observational under Scientific "
                   + "Data menu and Add Other Criteria.", " Minimum one Other criteria must be added "));

      }

  }

  private void enforceIdentifierLength(StudyProtocolDTO spDto, List<AbstractionCompletionDTO> abstractionList)
  throws PAException {
      List<StudySiteDTO> sParts = new ArrayList<StudySiteDTO>();
      StudySiteDTO spartDTO = new StudySiteDTO();
      spartDTO.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.LEAD_ORGANIZATION));
      sParts.add(spartDTO);
      spartDTO = new StudySiteDTO();
      spartDTO.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.IDENTIFIER_ASSIGNER));
      sParts.add(spartDTO);
      List<StudySiteDTO> dtos = studySiteService.getByStudyProtocol(spDto.getIdentifier(), sParts);
        for (StudySiteDTO dto : dtos) {
              if (PAUtil.isGreatenThan(dto.getLocalStudyProtocolIdentifier(),
                      PAAttributeMaxLen.LEN_30)) {
                  if (StudySiteFunctionalCode.LEAD_ORGANIZATION.getCode().equals(
                          dto.getFunctionalCode().getCode())) {
                      abstractionList.add(createError("Error" ,
                              "Select General Trial Details from Administrative Data menu." ,
                              "Lead Organization Trial Identifier  cannot be more than 30 characters"));
                  } else if (StudySiteFunctionalCode.IDENTIFIER_ASSIGNER.getCode().equals(
                          dto.getFunctionalCode().getCode())) {
                      abstractionList.add(createError("Error" ,
                              "Select General Trial Details from Administrative Data menu." ,
                              "NCT Number cannot be more than 30 characters"));
                  }

              }
          }

  }
  private AbstractionCompletionDTO createError(String errorType, String comment, String errorDescription) {
      AbstractionCompletionDTO acDto = new AbstractionCompletionDTO();
      acDto.setErrorType(errorType);
      acDto.setComment(comment);
      acDto.setErrorDescription(errorDescription);
      return acDto;
  }
  
  private boolean isDeviceFound(Ii studyProtocolIi) throws PAException {
        boolean found = false;
            List<PlannedActivityDTO> paList = plannedActivityService.getByStudyProtocol(studyProtocolIi);
            for (PlannedActivityDTO pa : paList) {
                if (pa.getCategoryCode() != null 
                        && ActivityCategoryCode.INTERVENTION.equals(ActivityCategoryCode.getByCode(CdConverter
                        .convertCdToString(pa.getCategoryCode()))) 
                        &&  pa.getSubcategoryCode() != null && pa.getSubcategoryCode().getCode() != null 
                        && InterventionTypeCode.DEVICE.getCode().equalsIgnoreCase(pa.getSubcategoryCode().getCode())) {
                        found = true;
                        break;
                }
            }
            return found;
        }
 private static String convertBLToString(Bl bl) {
            if (bl == null) {
                return null;
            }
            Boolean b = bl.getValue();
            if (b != null && b.booleanValue()) {
                return YES;
            } else {
                return NO;
            }
        }
}
