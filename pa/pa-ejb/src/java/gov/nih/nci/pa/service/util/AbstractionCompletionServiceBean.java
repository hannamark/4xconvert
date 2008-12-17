package gov.nih.nci.pa.service.util;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.dto.AbstractionCompletionDTO;
import gov.nih.nci.pa.enums.ActivityCategoryCode;
import gov.nih.nci.pa.enums.ArmTypeCode;
import gov.nih.nci.pa.enums.DocumentTypeCode;
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
              + "as Number of Arms assigned in ‘Interventional Trial Design’.");
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
    } else {
      AbstractionCompletionDTO webDTO = new AbstractionCompletionDTO();
      webDTO.setErrorType("Error");
      webDTO.setComment("Select Trial Funding from Administrative Data menu.");
      webDTO.setErrorDescription("No grants exists for the trial.");
      abstractionList.add(webDTO);
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


  private void enforceTrialINDIDE(Ii studyProtocolIi) throws PAException {
    List<StudyIndldeDTO> siList = PoPaServiceBeanLookup.getStudyIndldeService().getByStudyProtocol(studyProtocolIi);
    if (!(siList.isEmpty())) { // NOPMD
    } else {
      AbstractionCompletionDTO webDTO = new AbstractionCompletionDTO();
      webDTO.setErrorType("Error");
      webDTO.setComment("Select Trial IND/IDE under Regulatory Information from Administrative Data menu.");
      webDTO.setErrorDescription("No IND/IDE exists for the trial.");
      abstractionList.add(webDTO);
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
    if (!(spList.isEmpty())) { // NOPMD
    } else {
      AbstractionCompletionDTO webDTO = new AbstractionCompletionDTO();
      webDTO.setErrorType("Error");
      webDTO.setComment("Select Treating Sites from Participating Sites under Administrative Data menu.");
      webDTO.setErrorDescription("No Treating Sites exists for the trial.");
      abstractionList.add(webDTO);
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
    if (studyProtocolDTO.getAcronym().getValue() == null) {
      webDTO = new AbstractionCompletionDTO();
      webDTO.setErrorType("Error");
      webDTO.setComment("Select General Trial Details from Administrative Data menu.");
      webDTO.setErrorDescription("Acronym must be Entered");
      abstractionList.add(webDTO);
    }
    if (studyProtocolDTO.getPublicDescription().getValue() == null) {
      webDTO = new AbstractionCompletionDTO();
      webDTO.setErrorType("Error");
      webDTO.setComment("Select General Trial Details from Administrative Data menu.");
      webDTO.setErrorDescription("Brief Summary must be Entered");
      abstractionList.add(webDTO);
    }
//    if (studyProtocolDTO.getScientificDescription().getValue() == null) {
//      webDTO = new AbstractionCompletionDTO();
//      webDTO.setErrorType("Error");
//      webDTO.setComment("Select General Trial Details from Administrative Data menu.");
//      webDTO.setErrorDescription("Detailed Description must be Entered");
//      abstractionList.add(webDTO);
//    }
//    if (studyProtocolDTO.getKeywordText().getValue() == null) {
//      webDTO = new AbstractionCompletionDTO();
//      webDTO.setErrorType("Error");
//      webDTO.setComment("Select General Trial Details from Administrative Data menu.");
//      webDTO.setErrorDescription("Keywords must be Entered");
//      abstractionList.add(webDTO);
//    }
  }


  private void enforceNCISpecificInfo(StudyProtocolDTO studyProtocolDTO, StudyResourcingDTO studyResourcingDTO) {
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
      webDTO.setErrorDescription("Summary 4 funding category Method must be Entered");
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
    if (ispDTO.getStudyClassificationCode().getCode() == null) {
      webDTO = new AbstractionCompletionDTO();
      webDTO.setErrorType("Error");
      webDTO.setComment("Select Design Details from specific " 
          + "Interventional/Observational under Scientific Data menu.");
      webDTO.setErrorDescription("StudyClassification must be Entered");
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
