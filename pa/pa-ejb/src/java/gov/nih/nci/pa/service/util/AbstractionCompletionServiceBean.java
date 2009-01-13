/*
 * Copyright Notice. Copyright 2008, ScenPro, Inc, (“caBIG™ Participant”). The Protocol Abstraction (PA) Application was
 * created with NCI funding and is part of the caBIG™ initiative. The software subject to this notice and license
 * includes both human readable source code form and machine readable, binary, object code form (the “caBIG™ Software”).
 * This caBIG™ Software License (the “License”) is between caBIG™ Participant and You. “You (or “Your”) shall mean a
 * person or an entity, and all other entities that control, are controlled by, or are under common control with the
 * entity. “Control” for purposes of this definition means (i) the direct or indirect power to cause the direction or
 * management of such entity, whether by contract or otherwise, or (ii) ownership of fifty percent (50%) or more of the
 * outstanding shares, or (iii) beneficial ownership of such entity. License. Provided that You agree to the conditions
 * described below, caBIG™ Participant grants You a non-exclusive, worldwide, perpetual, fully-paid-up, no-charge,
 * irrevocable, transferable and royalty-free right and license in its rights in the caBIG™ Software, including any
 * copyright or patent rights therein, to (i) use, install, disclose, access, operate, execute, reproduce, copy, modify,
 * translate, market, publicly display, publicly perform, and prepare derivative works of the caBIG™ Software in any
 * manner and for any purpose, and to have or permit others to do so; (ii) make, have made, use, practice, sell, and
 * offer for sale, import, and/or otherwise dispose of caBIG™ Software (or portions thereof); (iii) distribute and have
 * distributed to and by third parties the caBIG™ Software and any modifications and derivative works thereof; and (iv)
 * sublicense the foregoing rights set out in (i), (ii) and (iii) to third parties, including the right to license such
 * rights to further third parties. For sake of clarity, and not by way of limitation, caBIG™ Participant shall have no
 * right of accounting or right of payment from You or Your sub licensees for the rights granted under this License.
 * This License is granted at no charge to You. Your downloading, copying, modifying, displaying, distributing or use of
 * caBIG™ Software constitutes acceptance of all of the terms and conditions of this Agreement. If You do not agree to
 * such terms and conditions, You have no right to download, copy, modify, display, distribute or use the caBIG™
 * Software. 1. Your redistributions of the source code for the caBIG™ Software must retain the above copyright notice,
 * this list of conditions and the disclaimer and limitation of liability of Article 6 below. Your redistributions in
 * object code form must reproduce the above copyright notice, this list of conditions and the disclaimer of Article 6
 * in the documentation and/or other materials provided with the distribution, if any. 2. Your end-user documentation
 * included with the redistribution, if any, must include the following acknowledgment: “This product includes software
 * developed by ScenPro, Inc.” If You do not include such end-user documentation, You shall include this acknowledgment
 * in the caBIG™ Software itself, wherever such third-party acknowledgments normally appear. 3. You may not use the
 * names “ScenPro, Inc.”, “The National Cancer Institute”, “NCI”, “Cancer Bioinformatics Grid” or “caBIG™” to endorse or
 * promote products derived from this caBIG™ Software. This License does not authorize You to use any trademarks,
 * service marks, trade names, logos or product names of either caBIG™ Participant, NCI or caBIG™, except as required to
 * comply with the terms of this License. 4. For sake of clarity, and not by way of limitation, You may incorporate this
 * caBIG™ Software into Your proprietary programs and into any third party proprietary programs. However, if You
 * incorporate the caBIG™ Software into third party proprietary programs, You agree that You are solely responsible for
 * obtaining any permission from such third parties required to incorporate the caBIG™ Software into such third party
 * proprietary programs and for informing Your sub licensees, including without limitation Your end-users, of their
 * obligation to secure any required permissions from such third parties before incorporating the caBIG™ Software into
 * such third party proprietary software programs. In the event that You fail to obtain such permissions, You agree to
 * indemnify caBIG™ Participant for any claims against caBIG™ Participant by such third parties, except to the extent
 * prohibited by law, resulting from Your failure to obtain such permissions. 5. For sake of clarity, and not by way of
 * limitation, You may add Your own copyright statement to Your modifications and to the derivative works, and You may
 * provide additional or different license terms and conditions in Your sublicenses of modifications of the caBIG™
 * Software, or any derivative works of the caBIG™ Software as a whole, provided Your use, reproduction, and
 * distribution of the Work otherwise complies with the conditions stated in this License. 6. THIS caBIG™ SOFTWARE IS
 * PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE
 * ScenPro, Inc. OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG™ SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 */
package gov.nih.nci.pa.service.util;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.dto.AbstractionCompletionDTO;
import gov.nih.nci.pa.enums.ActivityCategoryCode;
import gov.nih.nci.pa.enums.ArmTypeCode;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.enums.StudyParticipationContactRoleCode;
import gov.nih.nci.pa.enums.StudyParticipationFunctionalCode;
import gov.nih.nci.pa.iso.dto.ArmDTO;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.ObservationalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.PlannedActivityDTO;
import gov.nih.nci.pa.iso.dto.PlannedEligibilityCriterionDTO;
import gov.nih.nci.pa.iso.dto.StudyDiseaseDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyOutcomeMeasureDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyParticipationContactDTO;
import gov.nih.nci.pa.iso.dto.StudyParticipationDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.correlation.PoPaServiceBeanLookup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Stateless;

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
  "PMD.AvoidDuplicateLiterals", "PMD.ExcessiveClassLength", "PMD.NPathComplexity" })
@Stateless
public class AbstractionCompletionServiceBean implements AbstractionCompletionServiceRemote {
  
  private List<AbstractionCompletionDTO> abstractionList = null;
  
  /**
   * @param studyProtocolIi studyProtocolIi
   * @return AbstractionCompletionDTO list
   * @throws PAException on error
   */
  public List<AbstractionCompletionDTO> validateAbstractionCompletion(Ii studyProtocolIi) throws PAException {
    if (studyProtocolIi == null) {
      throw new PAException("Study Protocol Identifer is null");
    }
    abstractionList = new ArrayList<AbstractionCompletionDTO>();
    StudyProtocolDTO studyProtocolDTO = PoPaServiceBeanLookup.getStudyProtocolService()
                        .getStudyProtocol(studyProtocolIi);
    enforceGeneralTrailDetails(studyProtocolDTO);

    StudyResourcingDTO studyResourcingDTO = PoPaServiceBeanLookup.getStudyResourcingService()
    .getsummary4ReportedResource(studyProtocolIi);
    enforceNCISpecificInfo(studyProtocolDTO, studyResourcingDTO);

    enforceRegulatoryInfo(studyProtocolIi);
    
    enforceTrialINDIDE(studyProtocolIi);
    
    enforceTrialStatus(studyProtocolIi, studyProtocolDTO);    
    
    List<DocumentDTO> isoList = PoPaServiceBeanLookup.getDocumentService()
    .getDocumentsByStudyProtocol(studyProtocolIi);
    String protocolDoc = null;
    String irbDoc = null;
    if (!(isoList.isEmpty())) {
      for (DocumentDTO dto : isoList) {              
        if (dto.getTypeCode().getCode().equalsIgnoreCase(
            DocumentTypeCode.Protocol_Document.getCode())) {
          protocolDoc = dto.getTypeCode().getCode().toString();
        } else if (dto.getTypeCode().getCode().equalsIgnoreCase(
            DocumentTypeCode.IRB_Approval_Document.getCode())) {  
          irbDoc = dto.getTypeCode().getCode().toString();
        } 
      }
    } 
    enforceDocument(protocolDoc, irbDoc);

    if (studyProtocolDTO.getStudyProtocolType().getValue().equalsIgnoreCase("InterventionalStudyProtocol")) {
      InterventionalStudyProtocolDTO ispDTO = new InterventionalStudyProtocolDTO();
      ispDTO = PoPaServiceBeanLookup.getStudyProtocolService().getInterventionalStudyProtocol(studyProtocolIi);
      enforceInterventional(ispDTO);
      if (ispDTO.getNumberOfInterventionGroups().getValue() != null) {
        List<ArmDTO> aList = PoPaServiceBeanLookup.getArmService().getByStudyProtocol(studyProtocolIi);
        if (aList.size() == ispDTO.getNumberOfInterventionGroups().getValue()) { // NOPMD
        } else {
          AbstractionCompletionDTO webDTO = new AbstractionCompletionDTO();
          webDTO.setErrorType("Error");
          webDTO.setComment("Select Arm from Interventional Trial Design under Scientific Data menu.");
          webDTO.setErrorDescription("Number of interventional trial arm records must be the same"
              + " as Number of Arms assigned in ‘Interventional Trial Design’.");
          abstractionList.add(webDTO);
        }
      }
    } else if (studyProtocolDTO.getStudyProtocolType().getValue().equalsIgnoreCase("ObservationalStudyProtocol")) {
      ObservationalStudyProtocolDTO ospDTO = new ObservationalStudyProtocolDTO();
      ospDTO = PoPaServiceBeanLookup.getStudyProtocolService().getObservationalStudyProtocol(studyProtocolIi);
      enforceObservational(ospDTO);
      if (ospDTO.getNumberOfGroups().getValue() != null) {
        List<ArmDTO> aList = PoPaServiceBeanLookup.getArmService().getByStudyProtocol(studyProtocolIi);
        if (aList.size() == ospDTO.getNumberOfGroups().getValue()) { // NOPMD
        } else {
          AbstractionCompletionDTO webDTO = new AbstractionCompletionDTO();
          webDTO.setErrorType("Error");
          webDTO.setComment("Select Groups from Observational Trial Design under Scientific Data menu.");
          webDTO.setErrorDescription("Number of Observational study group records must be the same"
              + " as Number of Groups assigned in ‘Observational Study Design’.");
          abstractionList.add(webDTO);
        }
      }
    }

    enforceOutcomeMeasure(studyProtocolIi);
    enforceInterventions(studyProtocolIi);
    enforceTreatingSite(studyProtocolIi);
    enforceArmGroup(studyProtocolIi, studyProtocolDTO);
    enforceTrialFunding(studyProtocolIi);
    enforceDisease(studyProtocolIi);
    enforceArmInterventional(studyProtocolIi);
    enforceEligibility(studyProtocolIi);
    return abstractionList;
  }
  private void enforceDisease(Ii studyProtocolIi) throws PAException {
      boolean leadExist = false;
      List<StudyDiseaseDTO> sdDtos = 
          PoPaServiceBeanLookup.getStudyDiseaseService().getByStudyProtocol(studyProtocolIi);
      for (StudyDiseaseDTO sdDto : sdDtos) {
          if (sdDto.getLeadDiseaseIndicator() != null && sdDto.getLeadDiseaseIndicator().getValue()) {
              leadExist = true;
              break;
          }
      }
      if (!leadExist) {
          abstractionList.add(createError("Error", "Select Disease/Condition from Scientific Data Menu", 
                  "There should be minimum one disease for a StudyProtocol"));
      }
      
  }
  @SuppressWarnings({"PMD" })
  private void enforceTrialFunding(Ii studyProtocolIi) throws PAException {
    List<StudyResourcingDTO> srList = PoPaServiceBeanLookup.getStudyResourcingService().
    getstudyResourceByStudyProtocol(studyProtocolIi);
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
            AbstractionCompletionDTO webDTO = new AbstractionCompletionDTO();
            webDTO.setErrorType("Error");
            webDTO.setComment("Select Trial Funding from Administrative Data menu.");
            webDTO.setErrorDescription("Trial should not have Duplicate grants.");
            abstractionList.add(webDTO);
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
      StudyProtocolDTO studyProtocolDTO) throws PAException {
    if (studyProtocolDTO.getStudyProtocolType().getValue().equalsIgnoreCase("InterventionalStudyProtocol")) {
      List<ArmDTO> aList = PoPaServiceBeanLookup.getArmService().getByStudyProtocol(studyProtocolIi);
      if (!(aList.isEmpty())) { // NOPMD
      } else {
        AbstractionCompletionDTO webDTO = new AbstractionCompletionDTO();
        webDTO.setErrorType("Error");
        webDTO.setComment("Select Arm from Interventional Trial Design under Scientific Data menu.");
        webDTO.setErrorDescription("No Arm exists for the trial.");
        abstractionList.add(webDTO);
      }
    } else if (studyProtocolDTO.getStudyProtocolType().getValue().equalsIgnoreCase("ObservationalStudyProtocol")) {
      List<ArmDTO> aList = PoPaServiceBeanLookup.getArmService().getByStudyProtocol(studyProtocolIi);
      if (!(aList.isEmpty())) { // NOPMD
      } else {
        AbstractionCompletionDTO webDTO = new AbstractionCompletionDTO();
        webDTO.setErrorType("Error");
        webDTO.setComment("Select Groups from Observational Trial Design under Scientific Data menu.");
        webDTO.setErrorDescription("No Groups exists for the trial.");
        abstractionList.add(webDTO);
      }
    }
  }


  private void enforceTrialStatus(Ii studyProtocolIi,
      StudyProtocolDTO studyProtocolDTO) throws PAException {
    List<StudyOverallStatusDTO> sosList = PoPaServiceBeanLookup.getStudyOverallStatusService()
              .getCurrentByStudyProtocol(studyProtocolIi);
    if (!(sosList.isEmpty())) { // NOPMD
    } else {
      AbstractionCompletionDTO webDTO = new AbstractionCompletionDTO();
      webDTO.setErrorType("Error");
      webDTO.setComment("Select Trial Status from Administrative Data menu.");
      webDTO.setErrorDescription("No Trial Status exists for the trial.");
      abstractionList.add(webDTO);
    }
    if (studyProtocolDTO.getStartDate().getValue() == null 
        && studyProtocolDTO.getStartDateTypeCode().getCode() == null
        && studyProtocolDTO.getPrimaryCompletionDate().getValue() == null
        && studyProtocolDTO.getPrimaryCompletionDateTypeCode().getCode() == null) {
      AbstractionCompletionDTO webDTO = new AbstractionCompletionDTO();
      webDTO.setErrorType("Error");
      webDTO.setComment("Select Trial Status from Administrative Data menu.");
      webDTO.setErrorDescription("StartDate/StartDateType and " 
          + "PrimaryCompletionDate/PrimaryCompletionDateType must be Entered.");
      abstractionList.add(webDTO);
    }
  }

  @SuppressWarnings({"PMD" })
  private void enforceTrialINDIDE(Ii studyProtocolIi) throws PAException {    
    List<StudyIndldeDTO> siList = PoPaServiceBeanLookup.getStudyIndldeService().
    getByStudyProtocol(studyProtocolIi);
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
            AbstractionCompletionDTO webDTO = new AbstractionCompletionDTO();
            webDTO.setErrorType("Error");
            webDTO.setComment("Select Trial IND/IDE under Regulatory Information from Administrative Data menu.");
            webDTO.setErrorDescription("Trial IND/IDE should not have Duplicate values.");
            abstractionList.add(webDTO);
            if (i == siList.size()) { //NOPMD              
            } else {
              j++;
            }
          }
        }
      }
    }
  }


  private void enforceRegulatoryInfo(Ii studyProtocolIi) throws PAException {
    StudyRegulatoryAuthorityDTO sraDTO = PoPaServiceBeanLookup.getStudyRegulatoryAuthorityService()
    .getByStudyProtocol(studyProtocolIi);
    if (sraDTO != null) { // NOPMD
    } else {
      AbstractionCompletionDTO webDTO = new AbstractionCompletionDTO();
      webDTO.setErrorType("Error");
      webDTO.setComment("Select Regulatory under Regulatory Information from Administrative Data menu.");
      webDTO.setErrorDescription("Regulatory Information fields must be Entered.");
      abstractionList.add(webDTO);
    }
  }


  private void enforceTreatingSite(Ii studyProtocolIi) throws PAException {
    StudyParticipationDTO srDTO = new StudyParticipationDTO();
    srDTO.setFunctionalCode(CdConverter.convertToCd(StudyParticipationFunctionalCode.TREATING_SITE));
    List<StudyParticipationDTO> spList =  
        PoPaServiceBeanLookup.getStudyParticipationService().getByStudyProtocol(studyProtocolIi, srDTO);
    
    if (spList == null || spList.isEmpty()) { 
      AbstractionCompletionDTO webDTO = new AbstractionCompletionDTO();
      webDTO.setErrorType("Error");
      webDTO.setComment("Select Treating Sites from Participating Sites under Administrative Data menu.");
      webDTO.setErrorDescription("No Treating Sites exists for the trial.");
      abstractionList.add(webDTO);
      return;
    }
    if (spList != null) {
        for (StudyParticipationDTO spartDto : spList) {

            List<StudyParticipationContactDTO> spContactDtos =  
                PoPaServiceBeanLookup.getStudyParticipationContactService().getByStudyParticipation(
                        spartDto.getIdentifier());
            boolean piFound = false;
            boolean contactFound = false;
            for (StudyParticipationContactDTO spContactDto : spContactDtos) {
                if (StudyParticipationContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR.getCode()
                            .equalsIgnoreCase(spContactDto.getRoleCode().getCode())
                    || StudyParticipationContactRoleCode.STUDY_SUB_INVESTIGATOR.getCode()
                            .equalsIgnoreCase(spContactDto.getRoleCode().getCode())) {
                    piFound = true;
                } else if (StudyParticipationContactRoleCode.STUDY_PRIMARY_CONTACT.getCode()
                        .equalsIgnoreCase(spContactDto.getRoleCode().getCode())) {
                    contactFound = true;
                }
            }
            if (!piFound) {
                abstractionList.add(createError("Error", 
                        "Select Treating Sites from Participating Sites under Administrative Data menu.", 
                        "Treating site # " + spartDto.getIdentifier().getExtension() + " Must have an Investigator"));
                
            }
            if (!contactFound) {
                abstractionList.add(createError("Error", 
                        "Select Treating Sites from Participating Sites under Administrative Data menu.", 
                        "Treating site # " + spartDto.getIdentifier().getExtension() + " Must have a Contact"));
                
            }
            
        }
        
    }
  }


  private void enforceInterventions(Ii studyProtocolIi) throws PAException {
    List<PlannedActivityDTO> paList = PoPaServiceBeanLookup.getPlannedActivityService().
    getByStudyProtocol(studyProtocolIi);
    if (!(paList.isEmpty())) { // NOPMD
    } else {
      AbstractionCompletionDTO webDTO = new AbstractionCompletionDTO();
      webDTO.setErrorType("Error");
      webDTO.setComment("Select Interventions from Scientific Data menu.");
      webDTO.setErrorDescription("No Interventions exists for the trial.");
      abstractionList.add(webDTO);
    }
  }


  private void enforceOutcomeMeasure(Ii studyProtocolIi) throws PAException {
    List<StudyOutcomeMeasureDTO> somList = PoPaServiceBeanLookup.getStudyOutcomeMeasureService().
    getByStudyProtocol(studyProtocolIi);
    if (!(somList.isEmpty())) { // NOPMD
    } else {
      AbstractionCompletionDTO webDTO = new AbstractionCompletionDTO();
      webDTO.setErrorType("Error");
      webDTO.setComment("Select Outcome Measure from specific " 
          + "Interventional/Observational under Scientific Data menu.");
      webDTO.setErrorDescription("No OutcomeMeasures exists for the trial.");
      abstractionList.add(webDTO);
    }
  }
  
  private void enforceGeneralTrailDetails(StudyProtocolDTO studyProtocolDTO) {
    AbstractionCompletionDTO webDTO = null;      
    if (studyProtocolDTO.getAssignedIdentifier().getExtension() == null) {
      webDTO = new AbstractionCompletionDTO();
      webDTO.setErrorType("Error");
      webDTO.setComment("Select General Trial Details from Administrative Data menu.");
      webDTO.setErrorDescription("NCI Trial Identifier must be Entered");
      abstractionList.add(webDTO);
    }
    if (studyProtocolDTO.getOfficialTitle().getValue() == null) {
      webDTO = new AbstractionCompletionDTO();
      webDTO.setErrorType("Error");
      webDTO.setComment("Select General Trial Details from Administrative Data menu.");
      webDTO.setErrorDescription("Official Title must be Entered");
      abstractionList.add(webDTO);
    }
    if (studyProtocolDTO.getPublicTitle().getValue() == null) {
      webDTO = new AbstractionCompletionDTO();
      webDTO.setErrorType("Error");
      webDTO.setComment("Select General Trial Details from Administrative Data menu.");
      webDTO.setErrorDescription("Brief Title must be Entered");
      abstractionList.add(webDTO);
    }    
    if (studyProtocolDTO.getPublicDescription().getValue() == null) {
      webDTO = new AbstractionCompletionDTO();
      webDTO.setErrorType("Error");
      webDTO.setComment("Select General Trial Details from Administrative Data menu.");
      webDTO.setErrorDescription("Brief Summary must be Entered");
      abstractionList.add(webDTO);
    }
  }


  private void enforceNCISpecificInfo(StudyProtocolDTO studyProtocolDTO, StudyResourcingDTO studyResourcingDTO)
  throws PAException {
    AbstractionCompletionDTO webDTO = null;
    if (studyProtocolDTO.getAccrualReportingMethodCode().getCode() == null) {
      webDTO = new AbstractionCompletionDTO();
      webDTO.setErrorType("Error");
      webDTO.setComment("Select NCI Specific Information from Administrative Data menu.");
      webDTO.setErrorDescription("Reporting Data Set Method must be Entered");
      abstractionList.add(webDTO);
    }
    if (studyResourcingDTO == null 
        || studyResourcingDTO.getTypeCode().getCode() == null) {
      webDTO = new AbstractionCompletionDTO();
      webDTO.setErrorType("Error");
      webDTO.setComment("Select NCI Specific Information from Administrative Data menu.");
      webDTO.setErrorDescription("Summary 4 Funding Category must be Entered");
      abstractionList.add(webDTO);
    }
    Organization o = new Organization();
    Organization org = null;
    if (studyResourcingDTO != null 
        && studyResourcingDTO.getOrganizationIdentifier().getExtension() != null) {
    o.setId(Long.valueOf(studyResourcingDTO.getOrganizationIdentifier().getExtension()));
    org = PoPaServiceBeanLookup.getPAOrganizationService().getOrganizationByIndetifers(o);
    }
    if (studyResourcingDTO == null 
        || studyResourcingDTO.getOrganizationIdentifier().getExtension() == null 
        || org == null 
        || org.getName() == null) {
      webDTO = new AbstractionCompletionDTO();
      webDTO.setErrorType("Error");
      webDTO.setComment("Select NCI Specific Information from Administrative Data menu.");
      webDTO.setErrorDescription("Summary 4 Funding Sponsor/Source must be Entered");
      abstractionList.add(webDTO);
    }
  }
  private void enforceDocument(String protocolDoc, String irbDoc) {
    AbstractionCompletionDTO webDTO = null;
    if (protocolDoc == null) {
      webDTO = new AbstractionCompletionDTO();
      webDTO.setErrorType("Error");
      webDTO.setComment("Select Trial Related Documents from Administrative Data menu.");
      webDTO.setErrorDescription("Protocol_Document is required");
      abstractionList.add(webDTO);
    }
    if (irbDoc == null) {
      webDTO = new AbstractionCompletionDTO();
      webDTO.setErrorType("Error");
      webDTO.setComment("Select Trial Related Documents from Administrative Data menu.");
      webDTO.setErrorDescription("IRB_Approval_Document is required");
      abstractionList.add(webDTO);
    }
  }

  private void enforceObservational(ObservationalStudyProtocolDTO ospDTO) {
    AbstractionCompletionDTO webDTO = null;
    if (ospDTO.getStudyModelCode().getCode() == null) {
      webDTO = new AbstractionCompletionDTO();
      webDTO.setErrorType("Error");
      webDTO.setComment("Select Design Details from specific " 
          + "Interventional/Observational under Scientific Data menu.");
      webDTO.setErrorDescription("Study Model must be Entered");
      abstractionList.add(webDTO);
    }
    if (ospDTO.getStudyModelCode().getCode() != null
        && ospDTO.getStudyModelCode().getCode().equalsIgnoreCase("Other") 
        && ospDTO.getStudyModelOtherText() ==  null) {
      webDTO = new AbstractionCompletionDTO();
      webDTO.setErrorType("Error");
      webDTO.setComment("Select Design Details from specific " 
          + "Interventional/Observational under Scientific Data menu.");
      webDTO.setErrorDescription("Study Model Comment must be Entered");
      abstractionList.add(webDTO);
    }

    if (ospDTO.getTimePerspectiveCode().getCode() == null) {
      webDTO = new AbstractionCompletionDTO();
      webDTO.setErrorType("Error");
      webDTO.setComment("Select Design Details from specific " 
          + "Interventional/Observational under Scientific Data menu.");
      webDTO.setErrorDescription("Time Perspective must be Entered");
      abstractionList.add(webDTO);
    }
    if (ospDTO.getTimePerspectiveCode().getCode() != null
        && ospDTO.getTimePerspectiveCode().getCode().equalsIgnoreCase("Other") 
        && ospDTO.getTimePerspectiveOtherText() == null) {
      webDTO = new AbstractionCompletionDTO();
      webDTO.setErrorType("Error");
      webDTO.setComment("Select Design Details from specific " 
          + "Interventional/Observational under Scientific Data menu.");
      webDTO.setErrorDescription("Time Perspective Comment must be Entered");
      abstractionList.add(webDTO);
    }
    if (ospDTO.getBiospecimenRetentionCode().getCode() == null) {
      webDTO = new AbstractionCompletionDTO();
      webDTO.setErrorType("Error");
      webDTO.setComment("Select Design Details from specific " 
          + "Interventional/Observational under Scientific Data menu.");
      webDTO.setErrorDescription("Bio-specimen Retention must be Entered");
      abstractionList.add(webDTO);
    }        
    if (ospDTO.getNumberOfGroups().getValue() == null) {
      webDTO = new AbstractionCompletionDTO();
      webDTO.setErrorType("Error");
      webDTO.setComment("Select Design Details from specific " 
          + "Interventional/Observational under Scientific Data menu.");
      webDTO.setErrorDescription("Number of Groups/Cohorts must be Entered");    
      abstractionList.add(webDTO);
    }
    if (ospDTO.getMaximumTargetAccrualNumber().getValue() == null) {
      webDTO = new AbstractionCompletionDTO();
      webDTO.setErrorType("Error");
      webDTO.setComment("Select Design Details from specific " 
          + "Interventional/Observational under Scientific Data menu.");
      webDTO.setErrorDescription("Target Enrollment must be Entered");
      abstractionList.add(webDTO);
    }
  }

  private void enforceInterventional(InterventionalStudyProtocolDTO ispDTO) {
    AbstractionCompletionDTO webDTO = null;
    if (ispDTO.getPrimaryPurposeCode().getCode() == null) {
      webDTO = new AbstractionCompletionDTO();
      webDTO.setErrorType("Error");
      webDTO.setComment("Select Design Details from specific " 
          + "Interventional/Observational under Scientific Data menu.");
      webDTO.setErrorDescription("Primary Purpose must be Entered");
      abstractionList.add(webDTO);
    }
    if (ispDTO.getPrimaryPurposeCode().getCode() != null
        && ispDTO.getPrimaryPurposeCode().getCode().equalsIgnoreCase("Other") 
        && ispDTO.getPrimaryPurposeOtherText() == null) {
      webDTO = new AbstractionCompletionDTO();
      webDTO.setErrorType("Error");
      webDTO.setComment("Select Design Details from specific " 
          + "Interventional/Observational under Scientific Data menu.");
      webDTO.setErrorDescription("Provide comment if Other");
      abstractionList.add(webDTO);
    }

    if (ispDTO.getPhaseCode().getCode() == null) {
      webDTO = new AbstractionCompletionDTO();
      webDTO.setErrorType("Error");
      webDTO.setComment("Select Design Details from specific " 
          + "Interventional/Observational under Scientific Data menu.");
      webDTO.setErrorDescription("Trial Phase must be Entered");
      abstractionList.add(webDTO);
    }
    if (ispDTO.getDesignConfigurationCode().getCode() == null) {
      webDTO = new AbstractionCompletionDTO();
      webDTO.setErrorType("Error");
      webDTO.setComment("Select Design Details from specific " 
          + "Interventional/Observational under Scientific Data menu.");
      webDTO.setErrorDescription("Intervention Model must be Entered");
      abstractionList.add(webDTO);
    }
    if (ispDTO.getNumberOfInterventionGroups().getValue() == null) {
      webDTO = new AbstractionCompletionDTO();
      webDTO.setErrorType("Error");
      webDTO.setComment("Select Design Details from specific " 
          + "Interventional/Observational under Scientific Data menu.");
      webDTO.setErrorDescription("Number of Arms must be Entered");
      abstractionList.add(webDTO);
    }
    if (ispDTO.getBlindingSchemaCode().getCode() == null) {
      webDTO = new AbstractionCompletionDTO();
      webDTO.setErrorType("Error");
      webDTO.setComment("Select Design Details from specific " 
          + "Interventional/Observational under Scientific Data menu.");
      webDTO.setErrorDescription("Masking must be Entered");
      abstractionList.add(webDTO);
    }
    if (ispDTO.getAllocationCode().getCode() == null) {
      webDTO = new AbstractionCompletionDTO();
      webDTO.setErrorType("Error");
      webDTO.setComment("Select Design Details from specific " 
          + "Interventional/Observational under Scientific Data menu.");
      webDTO.setErrorDescription("Allocation must be Entered");
      abstractionList.add(webDTO);
    }   
    if (ispDTO.getMaximumTargetAccrualNumber().getValue() == null) {
      webDTO = new AbstractionCompletionDTO();
      webDTO.setErrorType("Error");
      webDTO.setComment("Select Design Details from specific " 
          + "Interventional/Observational under Scientific Data menu.");
      webDTO.setErrorDescription("Target Enrollment must be Entered");
      abstractionList.add(webDTO);
    }
  }
  
  private void enforceArmInterventional(Ii studyProtocolIi) throws PAException {
      List<PlannedActivityDTO> paList = PoPaServiceBeanLookup.getPlannedActivityService()
          .getByStudyProtocol(studyProtocolIi);
      HashMap<String, String> intervention = new HashMap<String, String>();  
    for (PlannedActivityDTO pa : paList) {
      if (ActivityCategoryCode.INTERVENTION.equals(ActivityCategoryCode.getByCode(CdConverter
              .convertCdToString(pa.getCategoryCode())))) {
          List<ArmDTO> armDtos = PoPaServiceBeanLookup.getArmService().getByPlannedActivity(pa.getIdentifier());
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
    List<ArmDTO> arms = PoPaServiceBeanLookup.getArmService().getByStudyProtocol(studyProtocolIi);
//    AbstractionCompletionDTO webDTO = null;
    for (ArmDTO armDTO : arms) {
        if (ArmTypeCode.NO_INTERVENTION.getCode().equals(armDTO.getTypeCode())) {
            continue;
        }
        if (!intervention.containsKey(armDTO.getName().getValue())) {
            //abstractionList.add
                createError("Error", 
                    "Select Arm from Scientific Data menu and associated Interventional." , 
                    "Arm " + armDTO.getName().getValue() + " does not have any Intervention associated");
            
        }
    } 
  }

  
  private  void enforceEligibility(Ii studyProtocolIi) throws PAException {
      List<PlannedEligibilityCriterionDTO> paECs = 
          PoPaServiceBeanLookup.getPlannedActivityService().
              getPlannedEligibilityCriterionByStudyProtocol(studyProtocolIi);
      if (paECs == null || paECs.isEmpty()) {
          abstractionList.add(
          createError("Error", 
                  "Select Eligibilty Criteria from specific Interventional/Observational under Scientific Data menu.", 
              " does not have any Eligibilty Criteria"));
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
  
  
  private AbstractionCompletionDTO createError(String errorType, String comment, String errorDescription) {
      AbstractionCompletionDTO acDto = new AbstractionCompletionDTO();
      acDto.setErrorType(errorType);
      acDto.setComment(comment);
      acDto.setErrorDescription(errorDescription);
      return acDto; 
  }

}
