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
import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Int;
import gov.nih.nci.coppa.iso.Pq;
import gov.nih.nci.coppa.iso.St;
import gov.nih.nci.coppa.iso.Tel;
import gov.nih.nci.coppa.iso.TelEmail;
import gov.nih.nci.coppa.iso.TelPhone;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.domain.RegulatoryAuthority;
import gov.nih.nci.pa.enums.BlindingRoleCode;
import gov.nih.nci.pa.enums.HolderTypeCode;
import gov.nih.nci.pa.enums.ReviewBoardApprovalStatusCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.enums.StudyParticipationContactRoleCode;
import gov.nih.nci.pa.enums.StudyParticipationFunctionalCode;
import gov.nih.nci.pa.iso.dto.ArmDTO;
import gov.nih.nci.pa.iso.dto.DiseaseDTO;
import gov.nih.nci.pa.iso.dto.InterventionDTO;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.PlannedActivityDTO;
import gov.nih.nci.pa.iso.dto.PlannedEligibilityCriterionDTO;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudyDiseaseDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyOutcomeMeasureDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyParticipationContactDTO;
import gov.nih.nci.pa.iso.dto.StudyParticipationDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.util.AddressConverterUtil;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.ArmServiceLocal;
import gov.nih.nci.pa.service.DiseaseServiceRemote;
import gov.nih.nci.pa.service.DocumentWorkflowStatusServiceLocal;
import gov.nih.nci.pa.service.InterventionAlternateNameServiceRemote;
import gov.nih.nci.pa.service.InterventionServiceRemote;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PlannedActivityServiceLocal;
import gov.nih.nci.pa.service.StudyContactServiceLocal;
import gov.nih.nci.pa.service.StudyDiseaseServiceLocal;
import gov.nih.nci.pa.service.StudyIndldeServiceLocal;
import gov.nih.nci.pa.service.StudyOutcomeMeasureServiceLocal;
import gov.nih.nci.pa.service.StudyOverallStatusServiceLocal;
import gov.nih.nci.pa.service.StudyParticipationContactServiceLocal;
import gov.nih.nci.pa.service.StudyParticipationServiceLocal;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.StudyRegulatoryAuthorityServiceLocal;
import gov.nih.nci.pa.service.StudyResourcingServiceRemote;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceLocal;
import gov.nih.nci.pa.service.SubGroupsServiceLocal;
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceRemote;
import gov.nih.nci.pa.util.HibernateSessionInterceptor;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.apache.log4j.Logger;

/**
 * service bean for generating TSR.
 * 
 * @author Naveen Amiruddin , Kalpana Guthikonda
 * @since 01/21/2009
 */
@Stateless
@SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.ExcessiveMethodLength" , "PMD.TooManyMethods",
  "PMD.AvoidDuplicateLiterals", "PMD.ExcessiveClassLength", "PMD.TooManyFields", "PMD.NPathComplexity" })
@Interceptors(HibernateSessionInterceptor.class)
public class TSRReportGeneratorServiceBean implements TSRReportGeneratorServiceRemote {
    
    @EJB
    StudyProtocolServiceLocal studyProtocolService = null;
    @EJB
    StudyOverallStatusServiceLocal studyOverallStatusService = null;
    @EJB
    StudyIndldeServiceLocal studyIndldeService  = null;
    @EJB
    StudyDiseaseServiceLocal studyDiseaseService = null;
    @EJB
    ArmServiceLocal armService = null;
    @EJB
    PlannedActivityServiceLocal plannedActivityService = null;
    @EJB
    SubGroupsServiceLocal subGroupsService = null;
    @EJB
    StudyParticipationServiceLocal studyParticipationService = null;
    @EJB
    StudyParticipationContactServiceLocal studyParticipationContactService = null;
    @EJB
    StudyContactServiceLocal studyContactService = null;
    @EJB
    StudySiteAccrualStatusServiceLocal studySiteAccrualStatusService = null;
    @EJB
    StudyOutcomeMeasureServiceLocal studyOutcomeMeasureService = null;
    @EJB
    StudyRegulatoryAuthorityServiceLocal studyRegulatoryAuthorityService = null;
    @EJB
    OrganizationCorrelationServiceRemote ocsr = null;
    @EJB
    DocumentWorkflowStatusServiceLocal documentWorkflowStatusService = null;
    @EJB
    RegulatoryInformationServiceRemote regulatoryInformationService = null;
    @EJB
    DiseaseServiceRemote diseaseService = null;
    @EJB
    InterventionServiceRemote interventionService = null;
    @EJB
    InterventionAlternateNameServiceRemote interventionAlternateNameService = null;
    @EJB
    StudyResourcingServiceRemote  studyResourcingService = null;
    @EJB
    PAOrganizationServiceRemote  paOrganizationService = null;
  /** The Constant BR. */
  private static final String BR = "<BR>";
  
  /** The Constant TBL_B. */
  private static final String TBL_B = "<TABLE border='0' cellpadding='5%' cellspacing='0%'>";
  
  /** The Constant TBL_E. */
  private static final String TBL_E = "</TABLE>";
  
  /** The Constant TR_B. */
  private static final String TR_B = "<TR>";
  
  /** The Constant TR_E. */
  private static final String TR_E = "</TR>";
  
  /** The Constant TD_B. */
  private static final String TD_B = "<TD>";
  
  /** The Constant TD_E. */
  private static final String TD_E = "</TD>";
  
  /** The Constant UL_B. */
  private static final String UL_B = "<UL>";
  
  /** The Constant UL_E. */
  private static final String UL_E = "</UL>";
  
  /** The Constant LI_B. */
  private static final String LI_B = "<LI>";
  
  /** The Constant LI_E. */
  private static final String LI_E = "</LI>";
  
  /** The Constant BLD_B. */
  private static final String BLD_B = "<B>";
  
  /** The Constant BLD_E. */
  private static final String BLD_E = "</B>";
  
  /** The Constant NO_DATA. */
  private static final String NO_DATA = " ";
  
  /** The Constant NO_INFO. */
  private static final String NO_INFO = " <I> Information Not Provided </I>";
  
  /** The Constant EMPTY. */
  private static final String EMPTY = "";
  
  /** The Constant YES. */
  private static final String YES = "Yes";
  
  /** The Constant DL_B. */
  private static final String DL_B = "<DL>";
  
  /** The Constant DL_E. */
  private static final String DL_E = "</DL>";
  
  /** The Constant DT_B. */
  private static final String DT_B = "<DT>";
  
  /** The Constant DT_E. */
  private static final String DT_E = "</DT>";
  
  /** The Constant DD_B. */
  private static final String DD_B = "<DD>";
  
  /** The Constant DD_E. */
  private static final String DD_E = "</DD>";
  
  /** The Constant NO. */
  private static final String NO = "No";
  
  /** The Constant ERROR_COUNT. */
  private static final int ERROR_COUNT = 5;
  
  /** The Constant MAX_AGE. */
  private static final int MAX_AGE = 999;
   
  /** The Constant FONT_TITLE. */
  private static final String FONT_TITLE = "<FONT SIZE='5'>";
  
  /** The Constant FONT_SECTION. */
  private static final String FONT_SECTION = "<FONT SIZE='4' color='brown'>";
  
  /** The Constant FONT_LABEL. */
  private static final String FONT_LABEL = "<FONT SIZE='3'>";
  
  /** The Constant FONT_TEXT. */
  private static final String FONT_TEXT = "<FONT SIZE='3'>";
  
  
  /** The Constant FONT_END. */
  private static final String FONT_END = "</FONT>";
  
  /** The Constant CENTER_B. */
  private static final String CENTER_B = "<CENTER>";
  
  /** The Constant CENTER_E. */
  private static final String CENTER_E = "</CENTER>";
 
  /** The Constant NBSP. */
  private static final String NBSP = "&nbsp;&nbsp;&nbsp;";
  
  /** The Constant LOG. */
  private static final Logger LOG  = Logger.getLogger(TSRReportGeneratorServiceBean.class);
  
  /** The correlation utils. */
  private final CorrelationUtils correlationUtils = new CorrelationUtils();
  
  /**
   * Generate tsr html. 
   * @param studyProtocolIi ii of studyprotocol 
   * @return String xml output 
   * @throws PAException on error
   */
  public String generateTSRHtml(Ii studyProtocolIi) throws PAException {
      LOG.debug("Entering generateTSRXml");

      if (studyProtocolIi == null) {
          throw new PAException("Study Protocol Identifier is null");
      }
     
      StringBuffer htmldata = new StringBuffer();
      htmldata.append(FONT_TITLE).append(CENTER_B).append(appendBoldData("Trial Summary Report"))
              .append(CENTER_E).append(FONT_END);
      htmldata.append(BR);
      htmldata.append(CENTER_B);
      htmldata.append(appendData("Date", PAUtil.today(), false, true)).append(CENTER_E);
      StudyProtocolDTO spDTO = studyProtocolService.getStudyProtocol(studyProtocolIi); 
      try {
          appendSecondaryIdentifiers(htmldata , spDTO);
          appendTitles(htmldata , spDTO);
          appendOrgsAndPersons(htmldata , spDTO.getIdentifier());
          apppendTrialStatus(htmldata , spDTO);
          appendRegulatoryInformation(htmldata , spDTO);
          appendHumanSubjectSafety(htmldata , spDTO);
          appendIdeIde(htmldata , spDTO);
          appendNihGrants(htmldata , spDTO);
          appendSummary4(studyProtocolIi, htmldata);
          appendCollaborators(studyProtocolIi, htmldata);
          appendDisease(studyProtocolIi, htmldata);
          appendTrialDesign(htmldata, spDTO);
          appendEligibilityCriteria(htmldata, spDTO);
          appendArm(htmldata, spDTO);
          List<StudyOutcomeMeasureDTO> somDtos =
            studyOutcomeMeasureService.getByStudyProtocol(spDTO.getIdentifier()); 
         createPrimaryOutcome(htmldata, somDtos);
         createSecondaryOutcome(htmldata, somDtos);
         appendParticipatingSites(htmldata, spDTO);
      } catch (Exception e) {
          htmldata = new StringBuffer();
          htmldata.append(FONT_TITLE).append(CENTER_B).append(appendBoldData("Trial Summary Report"))
                  .append(CENTER_E).append(FONT_END);
          htmldata.append(BR);
          htmldata.append(appendBoldData("Unable to generate  Trial Summary Report")).append(BR);
          htmldata.append(" for ").append(appendBoldData(spDTO.getAssignedIdentifier().getExtension())).append(BR);
          htmldata.append("Study Title ").append(spDTO.getOfficialTitle().getValue()).append(BR);
          htmldata.append("Please contact CTRP staff").append(BR);
          htmldata.append("error_type ").append(appendBoldData(e.toString())).append(BR);
          StackTraceElement[] st =  e.getStackTrace();
          int x = 1;
          for (StackTraceElement se : st) {
              htmldata.append("Error Reason : ").append(appendBoldData(se.toString())).append(BR);
              x++;
              if (x > ERROR_COUNT) {
                  break;
              }
          }


      }
      return htmldata.toString();
  }

  /**
   * Append participating sites. 
   * @param html the html
   * @param spDTO the sp dto
   * @throws PAException the PA exception
   */
  private void appendParticipatingSites(StringBuffer html, StudyProtocolDTO spDTO)
      throws PAException {
    appendTitle(html, appendBoldData("Participating Sites"));
    html.append(BR);
     StudyParticipationDTO srDTO = new StudyParticipationDTO();
     srDTO.setFunctionalCode(CdConverter.convertToCd(StudyParticipationFunctionalCode.TREATING_SITE));
     List<StudyParticipationDTO> spList =
         studyParticipationService.getByStudyProtocol(spDTO.getIdentifier(), srDTO);
     //CorrelationUtils cUtils = new CorrelationUtils();
     for (StudyParticipationDTO sp : spList) {

         List<StudySiteAccrualStatusDTO> ssasList = studySiteAccrualStatusService
         .getCurrentStudySiteAccrualStatusByStudyParticipation(sp.getIdentifier());

         Organization orgBo = correlationUtils.getPAOrganizationByPAHealthCareFacilityId(
                 IiConverter.convertToLong(sp.getHealthcareFacilityIi()));

         html.append(appendData("Facility Name" , orgBo.getName() , true , true));
         html.append(appendData("Location" , getLocation(orgBo), true , true));
         if (ssasList != null && (!ssasList.isEmpty())) {
           html.append(appendData("Site Recruitment Status" , getData(ssasList.get(0).getStatusCode() , true)
               + " as of " + PAUtil.normalizeDateString(TsConverter.convertToTimestamp(
                   ssasList.get(0).getStatusDate()).toString()) , true , true));
         }
         html.append(appendData("Target Accrual" , getData(sp.getTargetAccrualNumber(), true) , true , true));
         List<StudyParticipationContactDTO> spcDTOs = 
                         studyParticipationContactService.getByStudyParticipation(sp.getIdentifier());
         createInvestigators(html, spcDTOs);
         createContact(html, spcDTOs);
         html.append(BR);
     }
     if (spDTO.getRecordVerificationDate() != null && spDTO.getRecordVerificationDate().getValue() != null) {
       html.append(appendData("Record Verification Date", PAUtil.normalizeDateString(
       TsConverter.convertToTimestamp(spDTO.getRecordVerificationDate()).toString()) , true , true));
     }
  }

  /**
   * Gets the location.
   * 
   * @param orgBo the org bo
   * 
   * @return the location
   */
  private String getLocation(Organization orgBo) {
      String location = "";
      
       String city = orgBo.getCity() != null ? orgBo.getCity() : " ";
       String state =  orgBo.getState() != null ? orgBo.getState() : " ";
       String pc = orgBo.getPostalCode() != null ? orgBo.getPostalCode() : " ";
       String country = orgBo.getCountryName() != null ? orgBo.getCountryName() : " ";
      
       location = city + ", " + state + " " + pc + " " + country;
       
      return location;
      
  }
  /**
   * Creates the investigators. 
   * @param html the html
   * @param spcDTOs the spc dt os
   * @throws PAException the PA exception
   */
  private void createInvestigators(StringBuffer html, List<StudyParticipationContactDTO> spcDTOs)
  throws PAException {
    boolean first = true;
    for (StudyParticipationContactDTO spcDTO : spcDTOs) {
      if (StudyParticipationContactRoleCode.PRIMARY_CONTACT.getCode().
          equals(spcDTO.getRoleCode().getCode())) {
        continue;
      }
      if (first) {
        first = false;
        html.append(appendLabel("Investigator(s)", "", true , true));
        html.append(TBL_B);
        html.append(TR_B);
        appendTDAndData(html, appendTRBold("First Name"));
        appendTDAndData(html, appendTRBold("Last Name"));
        appendTDAndData(html, appendTRBold("Role"));
        html.append(TR_E);
      }
      Person p = correlationUtils.getPAPersonByPAClinicalResearchStaffId(
          Long.valueOf(spcDTO.getClinicalResearchStaffIi().getExtension()));
      html.append(TR_B);
      appendTDAndData(html, p.getFirstName());
      appendTDAndData(html, p.getLastName());
      appendTDAndData(html, getTableData(spcDTO.getRoleCode(), true));
      html.append(TR_E);
    }
    html.append(first ? "" : TBL_E);
  }

  /**
   * Creates the contact.
   * @param html the html
   * @param spcDTOs the spc dt os
   * @throws PAException the PA exception
   */
  private void createContact(StringBuffer html,  List<StudyParticipationContactDTO> spcDTOs)
  throws PAException {
    for (StudyParticipationContactDTO spcDTO : spcDTOs) {
        if (!StudyParticipationContactRoleCode.PRIMARY_CONTACT.getCode().
                equals(spcDTO.getRoleCode().getCode())) {
            continue;
        }
        List<String> phones = DSetConverter.convertDSetToList(spcDTO.getTelecomAddresses(), "PHONE");
        List<String> emails = DSetConverter.convertDSetToList(spcDTO.getTelecomAddresses(), "EMAIL");
        Person p = correlationUtils.getPAPersonByPAClinicalResearchStaffId(
                Long.valueOf(spcDTO.getClinicalResearchStaffIi().getExtension()));
        html.append(appendData("Contact" , p.getFirstName() + " " + p.getLastName() , true , true));
        if (phones != null && !phones.isEmpty()) {
          html.append(appendData("Phone" , phones.get(0) , true , true));
        }
        if (emails != null && !emails.isEmpty()) {
          html.append(appendData("Email" , emails.get(0) , true , true));
        }
    }
  }

  /**
   * Creates the secondary outcome.
   * @param html the html
   * @param somDtos the som dtos
   */
  private void createSecondaryOutcome(StringBuffer html,
      List<StudyOutcomeMeasureDTO> somDtos) {

    for (StudyOutcomeMeasureDTO smDTO : somDtos) {
      if (!smDTO.getPrimaryIndicator().getValue().booleanValue()) {
    appendTitle(html, appendBoldData("Secondary Outcome Measures"));
        html.append(appendData("Description", getInfo(smDTO.getName(), true), true , true));
        html.append(appendData("TimeFrame", getInfo(smDTO.getTimeFrame(), true), true , true));
        html.append(appendData("Safety Issue?", convertBLToString((smDTO.getSafetyIndicator()), false), true , true));
        html.append(BR);
      }
    }
  }

  /**
   * Creates the primary outcome.
   * @param html the html
   * @param somDtos the som dtos
   */
  private void createPrimaryOutcome(StringBuffer html,
      List<StudyOutcomeMeasureDTO> somDtos) {

    for (StudyOutcomeMeasureDTO smDTO : somDtos) {
      if (smDTO.getPrimaryIndicator().getValue().booleanValue()) {
    appendTitle(html, appendBoldData("Primary Outcome Measures"));
        html.append(BR);
        html.append(appendData("Description", getInfo(smDTO.getName(), true), true , true));
        html.append(appendData("TimeFrame", getInfo(smDTO.getTimeFrame(), true), true , true));
        html.append(appendData("Safety Issue?", convertBLToString((smDTO.getSafetyIndicator()), false), true , true));
        html.append(BR);
      }
    }
  }

  /**
   * Append arm.
   * @param html the html
   * @param spDTO the sp dto
   * 
   * @throws PAException the PA exception
   */
  private void appendArm(StringBuffer html, StudyProtocolDTO spDTO) throws PAException {
    List<ArmDTO> arms = armService.getByStudyProtocol(spDTO.getIdentifier());
    for (ArmDTO armDTO : arms) {
      appendTitle(html, appendBoldData("Arm/Group(s)"));
      html.append(BR);
      html.append(appendData("Label", getInfo(armDTO.getName(), true), true , true));
      html.append(appendData("Type", getData(armDTO.getTypeCode(), true), true , true));
      html.append(appendData("Description", getInfo(armDTO.getDescriptionText(), true), true , true));
      html.append(appendLabel("Intervention(s)", "" , true, true));
      List<PlannedActivityDTO> paList = plannedActivityService.getByArm(armDTO.getIdentifier());
      html.append(TBL_B);
      html.append(TR_B);
      appendTDAndData(html, appendTRBold("Type"));
      appendTDAndData(html, appendTRBold("Lead"));
      appendTDAndData(html, appendTRBold("Name"));
      appendTDAndData(html, appendTRBold("Description"));
      html.append(TR_E);
      for (PlannedActivityDTO pa : paList) {

          InterventionDTO inter = interventionService.get(pa.getInterventionIdentifier());
          html.append(TR_B);
          appendTDAndData(html, getTableData(pa.getSubcategoryCode(), true));
          appendTDAndData(html, BlConverter.covertToBool(pa.getLeadProductIndicator()) ? "Yes" : "No");
          appendTDAndData(html, getData(inter.getName(), true));
          appendTDAndData(html, getData(pa.getTextDescription(), true));
          html.append(TR_E);
      }
      html.append(TBL_E);
    }
  }

  /**
   * Append eligibility criteria.
   * @param html the html
   * @param spDTO the sp dto
   * @throws PAException the PA exception
   */
  private void appendEligibilityCriteria(StringBuffer html, StudyProtocolDTO spDTO)
      throws PAException {
    appendTitle(html, appendBoldData("Eligibility Criteria"));
    html.append(BR);
    List<PlannedEligibilityCriterionDTO> paECs =
            plannedActivityService.getPlannedEligibilityCriterionByStudyProtocol(spDTO.getIdentifier());
      if (paECs == null || paECs.isEmpty()) {
          return;
      }
      String criterionName = null;
      String descriptionText = null;
      Pq pq = null;
      StringBuffer incCrit = new StringBuffer();
      StringBuffer exCrit = new StringBuffer();
      StringBuffer nullCrit = new StringBuffer();

      Boolean incIndicator = null;
      html.append(appendData("Accepts Healthy Volunteers?",
          convertBLToString(spDTO.getAcceptHealthyVolunteersIndicator(), false), true , true));
      for (PlannedEligibilityCriterionDTO paEC : paECs) {
          criterionName = StConverter.convertToString(paEC.getCriterionName());
          descriptionText  = StConverter.convertToString(paEC.getTextDescription());
          incIndicator = paEC.getInclusionIndicator() != null ? paEC.getInclusionIndicator().getValue() : null;
          pq = paEC.getValue();
          if (criterionName != null && criterionName.equalsIgnoreCase("GENDER")
                  && paEC.getEligibleGenderCode() != null) {
              html.append(appendData("Gender", getData(paEC.getEligibleGenderCode(), true), true , true));
          } else if (criterionName != null && criterionName.equalsIgnoreCase("MINIMUM-AGE")) {
              if (pq.getValue().intValue() == 0) {
                  html.append(appendData("Minimum Age", "N/A" , true , true));
              } else {
                  html.append(appendData("Minimum Age", pq.getValue()  + " " + pq.getUnit(), true , true));
              }
          } else if (criterionName != null && criterionName.equalsIgnoreCase("MAXIMUM-AGE")) {
              if (pq.getValue().intValue() == MAX_AGE) {
                  html.append(appendData("Maximum Age", "N/A" , true , true));
              } else {
                  html.append(appendData("Maximum Age", pq.getValue()  + " " + pq.getUnit(), true , true));
              }
          } else if (descriptionText != null && (!(descriptionText.equals("")))) {
            incCrit.append(UL_B);
            exCrit.append(UL_B);
            nullCrit.append(UL_B);
            if (incIndicator == null) {
                nullCrit.append(LI_B);
                nullCrit.append(descriptionText);
                nullCrit.append(LI_E);
            } else if (incIndicator) {
                incCrit.append(LI_B);
                incCrit.append(descriptionText);
                incCrit.append(LI_E);
              } else {
                exCrit.append(LI_B);
                exCrit.append(descriptionText);
                exCrit.append(LI_E);
              }
              incCrit.append(UL_E);
              exCrit.append(UL_E);
              nullCrit.append(UL_E);
            } else {
            incCrit.append(UL_B);
            exCrit.append(UL_B);
            nullCrit.append(UL_B);
            if (incIndicator == null) {
                nullCrit.append(LI_B);
                nullCrit.append(getData(paEC.getCriterionName(), true) + "   "
                    + getData(paEC.getOperator(), true) + "  "
                    + pq.getValue() + "  " + pq.getUnit());
                nullCrit.append(LI_E);
            } else if (incIndicator) {
                incCrit.append(LI_B);
                incCrit.append(getData(paEC.getCriterionName(), true) + "   "
                    + getData(paEC.getOperator(), true) + "  "
                    + pq.getValue() + "  " + pq.getUnit());
                incCrit.append(LI_E);
              } else {
                exCrit.append(LI_B);
                exCrit.append(getData(paEC.getCriterionName(), true) + "   "
                    + getData(paEC.getOperator(), true) + "  "
                    + pq.getValue() + "  " + pq.getUnit());
                exCrit.append(LI_E);
              }
              nullCrit.append(UL_E);
              incCrit.append(UL_E);
              exCrit.append(UL_E);
          }
      } // for loop
      html.append(BR);
      html.append(BR);
      if (nullCrit.length() > 0) {
          html.append(NBSP).append(appendBoldData("Other Criteria")).append(nullCrit);
      }
      if (incCrit.length() > 0) {
            html.append(NBSP).append(appendBoldData("Inclusion Criteria")).append(incCrit);
      }
      if (exCrit.length() > 0) {
            html.append(NBSP).append(appendBoldData("Exclusion Criteria")).append(exCrit);
      }
  }

  /**
   * Append trial design.
   * @param html the html
   * @param spDTO the sp dto
   * @throws PAException the PA exception
   */
  private void appendTrialDesign(StringBuffer html, StudyProtocolDTO spDTO)
  throws PAException {
    appendTitle(html, appendBoldData("Trial Design"));
    html.append(BR);
    InterventionalStudyProtocolDTO ispDTO =
                    studyProtocolService.getInterventionalStudyProtocol(spDTO.getIdentifier());
    html.append(appendData("Primary Purpose", getData(ispDTO.getPrimaryPurposeCode(), true), true , true));
    html.append(appendData("Phase", getData(ispDTO.getPhaseCode(), true), true , true));
    html.append(appendData("Intervention Model", getData(ispDTO.getDesignConfigurationCode(), true), true , true));
    html.append(appendData("Number of Arms", getData(ispDTO.getNumberOfInterventionGroups(), true), true , true));
    html.append(appendData("Masking", getData(ispDTO.getBlindingSchemaCode(), true), true , true));
    String maskedRoles = "";
    String subject = NO;
    String investigator = NO;
    String caregiver = NO;
    String outcomesAssessor = NO;
    if (ispDTO.getBlindedRoleCode() != null) {
      List<Cd> cds =  DSetConverter.convertDsetToCdList(ispDTO.getBlindedRoleCode());
      if (cds != null) {
        for (Cd cd : cds) {
          if (BlindingRoleCode.CAREGIVER.getCode().equals(cd.getCode())) {
            caregiver = YES;
          } else if (BlindingRoleCode.INVESTIGATOR.getCode().equals(cd.getCode())) {
            investigator = YES;
          } else if (BlindingRoleCode.OUTCOMES_ASSESSOR.getCode().equals(cd.getCode())) {
            outcomesAssessor = YES;
          } else if (BlindingRoleCode.SUBJECT.getCode().equals(cd.getCode())) {
            subject = YES;
          }
        } // for
      } // if
      maskedRoles = "Subject: " + subject + "; Investigator: " + investigator + "; Caregiver: "
      + caregiver + "; Outcomes Assessor: " + outcomesAssessor;
      html.append(appendData("Masked Roles", maskedRoles, true , true));
    } // if
    html.append(appendData("Allocation", getData(ispDTO.getAllocationCode(), true), true , true));
    html.append(appendData("Study Classification", getData(ispDTO.getStudyClassificationCode(), true), true , true));
    html.append(appendData("Target Enrollment", getData(ispDTO.getMaximumTargetAccrualNumber(), true), true , true));
  }

  /**
   * Append disease.
   * @param studyProtocolIi the study protocol ii
   * @param html the html
   * @throws PAException the PA exception
   */
  private void appendDisease(Ii studyProtocolIi, StringBuffer html)
  throws PAException {
    appendTitle(html, appendBoldData("Disease/Condition"));
    html.append(BR).append(BR).append(TBL_B);
    List<StudyDiseaseDTO> sdDtos =
                    studyDiseaseService.getByStudyProtocol(studyProtocolIi);
    boolean first = true;
    for (StudyDiseaseDTO sdDto : sdDtos) {
      if (first) {
        first = false;
        html.append(TR_B);
        appendTDAndData(html, appendTRBold("Name"));
        appendTDAndData(html, appendTRBold("Lead"));
        html.append(TR_E);
      }

      DiseaseDTO d = diseaseService.get(sdDto.getDiseaseIdentifier());
      html.append(TR_B);
      appendTDAndData(html, getData(d.getPreferredName(), true));
      appendTDAndData(html, convertBLToString(sdDto.getLeadDiseaseIndicator() , false));
      html.append(TR_E);
    }
    html.append(TBL_E);
  }

  /**
   * Append collaborators.
   * @param studyProtocolIi the study protocol ii
   * @param html the html
   * @throws PAException the PA exception
   */
  private void appendCollaborators(Ii studyProtocolIi, StringBuffer html)
      throws PAException {
    boolean first = true;

    ArrayList<StudyParticipationDTO> criteriaList = new ArrayList<StudyParticipationDTO>();
    for (StudyParticipationFunctionalCode cd : StudyParticipationFunctionalCode.values()) {
        if (cd.isCollaboratorCode()) {
            StudyParticipationDTO searchCode = new StudyParticipationDTO();
            searchCode.setFunctionalCode(CdConverter.convertToCd(cd));
            criteriaList.add(searchCode);
        }
    }

    List<StudyParticipationDTO> spList = studyParticipationService.getByStudyProtocol(studyProtocolIi, criteriaList);
    for (StudyParticipationDTO sp : spList) {
        //CorrelationUtils cUtils = new CorrelationUtils();
        Organization orgBo = correlationUtils.getPAOrganizationByPAResearchOrganizationId(
                IiConverter.convertToLong(sp.getResearchOrganizationIi()));
        if (first) {
          first = false;
          appendTitle(html, appendBoldData("Collaborators"));
          html.append(BR).append(BR).append(TBL_B);
          html.append(TR_B);
          appendTDAndData(html, appendTRBold("Name"));
          appendTDAndData(html, appendTRBold("Role"));
          html.append(TR_E);
      }
      html.append(TR_B);
      appendTDAndData(html, orgBo.getName());
      appendTDAndData(html, getTableData(sp.getFunctionalCode(), true));
      html.append(TR_E);
    }
    html.append(TBL_E);
  }

  /**
   * Append summary4.
   * 
   * @param studyProtocolIi the study protocol ii
   * @param html the html
   * 
   * @throws PAException the PA exception
   */
  private void appendSummary4(Ii studyProtocolIi, StringBuffer html)
      throws PAException {
    appendTitle(html, appendBoldData("Summary 4 Information"));
    html.append(BR);
    StudyResourcingDTO studyResourcingDTO = studyResourcingService.getsummary4ReportedResource(studyProtocolIi);
    if (studyResourcingDTO != null) {
    html.append(appendData("Funding Category", getData(studyResourcingDTO.getTypeCode(), true), true , true));
    }
    Organization org = null;
    if (studyResourcingDTO != null && studyResourcingDTO.getOrganizationIdentifier() != null
            && studyResourcingDTO.getOrganizationIdentifier().getExtension() != null) {
      Organization o = new Organization();
      o.setId(Long.valueOf(studyResourcingDTO.getOrganizationIdentifier().getExtension()));
      org = paOrganizationService.getOrganizationByIndetifers(o);
     }
     if (org != null) {
         html.append(appendData("Funding Sponsor/Source", org.getName(), true , true));
     } else {
         html.append(appendData("Funding Sponsor/Source", "", true , true)); 
     }
  }

  /**
   * Append nih grants.
   * 
   * @param html the html
   * @param spDto the sp dto
   * 
   * @throws PAException the PA exception
   */
  private void appendNihGrants(StringBuffer html , StudyProtocolDTO spDto) throws  PAException {

      List<StudyResourcingDTO> funds = studyResourcingService.getstudyResourceByStudyProtocol(spDto.getIdentifier());
      boolean first = true;
      for (StudyResourcingDTO fund : funds) {
          if (first) {
            appendTitle(html, appendBoldData("NIH Grants"));
            html.append(BR).append(BR).append(TBL_B);
            first = false;
              html.append(TR_B);
              appendTDAndData(html, appendTRBold("Funding Mechanism"));
              appendTDAndData(html, appendTRBold("NIH Institution Code"));
              appendTDAndData(html, appendTRBold("Serial Number"));
              appendTDAndData(html, appendTRBold("NCI Division/Program Code"));
              html.append(TR_E);
          }
          html.append(TR_B);
          appendTDAndData(html, getTableData(fund.getFundingMechanismCode(), true));
          appendTDAndData(html, getTableData(fund.getNihInstitutionCode(), true));
          appendTDAndData(html, getData(fund.getSerialNumber(), true));
          appendTDAndData(html, getTableData(fund.getNciDivisionProgramCode(), true));
          /*appendTDAndData(html, getData(fund.getFundingTypeCode(), true));
          appendTDAndData(html, getData(fund.getSuffixGrantYear(), true));
          appendTDAndData(html, getData(fund.getSuffixOther(), true));*/
          html.append(TR_E);
      }
      html.append(first ? "" : TBL_E);
  }
  
  /**
   * Append ide ide.
   * 
   * @param html the html
   * @param spDto the sp dto
   * 
   * @throws PAException the PA exception
   */
  private void appendIdeIde(StringBuffer html , StudyProtocolDTO spDto) throws  PAException {

      List<StudyIndldeDTO> indides =  studyIndldeService.getByStudyProtocol(spDto.getIdentifier());
      boolean first = true;
      for (StudyIndldeDTO indDto : indides) {
          if (first) {
              appendTitle(html, appendBoldData("IND/IDE"));
              html.append(BR).append(BR).append(TBL_B);
              first = false;
              html.append(TR_B);
              appendTDAndData(html, appendTRBold("Type"));
              appendTDAndData(html, appendTRBold("Grantor"));
              appendTDAndData(html, appendTRBold("Number"));
              appendTDAndData(html, appendTRBold("Holder Type"));
              appendTDAndData(html, appendTRBold("Holder"));             
              appendTDAndData(html, appendTRBold("Expanded Access"));
              appendTDAndData(html, appendTRBold("Expanded Access Status"));
              html.append(TR_E);
          }
          html.append(TR_B);
          appendTDAndData(html, getTableData(indDto.getIndldeTypeCode(), true));
          appendTDAndData(html, getTableData(indDto.getGrantorCode(), true));
          appendTDAndData(html, getData(indDto.getIndldeNumber() , true));
          appendTDAndData(html, getTableData(indDto.getHolderTypeCode() , true));
          if (HolderTypeCode.ORGANIZATION.getCode().equals(indDto.getHolderTypeCode().getCode())) {
              appendTDAndData(html, NBSP);
          } else if (HolderTypeCode.INVESTIGATOR.getCode().equals(indDto.getHolderTypeCode().getCode())) {
              appendTDAndData(html, NBSP);
          } else if (HolderTypeCode.NIH.getCode().equals(indDto.getHolderTypeCode().getCode())) {
              appendTDAndData(html , getTableData(indDto.getNihInstHolderCode(), true));
          } else if (HolderTypeCode.NCI.getCode().equals(indDto.getHolderTypeCode().getCode())) {
              appendTDAndData(html , getTableData(indDto.getNciDivProgHolderCode(), true));
          }
          appendTDAndData(html, convertBLToString(indDto.getExpandedAccessIndicator() , false));
          appendTDAndData(html , getTableData(indDto.getExpandedAccessStatusCode(), true));
          html.append(TR_E);
      }
      html.append(first ? "" : TBL_E);
  }
  
  /**
   * Append human subject safety.
   * 
   * @param html the html
   * @param spDto the sp dto
   * 
   * @throws PAException the PA exception
   */
  private void appendHumanSubjectSafety(StringBuffer html , StudyProtocolDTO spDto) throws  PAException {
      Boolean b = BlConverter.covertToBoolean(spDto.getReviewBoardApprovalRequiredIndicator());
      if (b != null && b) {
      appendTitle(html, appendBoldData("Human Subject Safety"));
      html.append(BR);
          List<StudyParticipationDTO> partList = studyParticipationService.getByStudyProtocol(spDto.getIdentifier());
          for (StudyParticipationDTO part : partList) {
              if (ReviewBoardApprovalStatusCode.SUBMITTED_APPROVED.getCode().equals(
                      part.getReviewBoardApprovalStatusCode().getCode())
                  || ReviewBoardApprovalStatusCode.SUBMITTED_EXEMPT.getCode().equals(
                          part.getReviewBoardApprovalStatusCode().getCode())
                  || ReviewBoardApprovalStatusCode.SUBMITTED_PENDING.getCode().equals(
                                  part.getReviewBoardApprovalStatusCode().getCode())
                  || ReviewBoardApprovalStatusCode.SUBMITTED_DENIED.getCode().equals(
                                                  part.getReviewBoardApprovalStatusCode().getCode())) {

                  html.append(appendData("Board Approval Status" , getData(part.getReviewBoardApprovalStatusCode(),
                      true),  true , true));
                  html.append(appendData("Board Approval Number" ,
                          getInfo(part.getReviewBoardApprovalNumber() , true) ,    true , true));
                  Organization paOrg = correlationUtils.getPAOrganizationByPAOversightCommitteeId(
                          IiConverter.convertToLong(part.getOversightCommitteeIi()));
                  if (paOrg != null) {
                      OrganizationDTO poOrg = null;
                      try {
                          poOrg = PoRegistry.getOrganizationEntityService().
                              getOrganization(IiConverter.converToPoOrganizationIi(paOrg.getIdentifier()));
                      } catch (NullifiedEntityException e) {
                          throw new PAException(" Po Identifier is nullified " + paOrg.getIdentifier() , e);
                      }
                      html.append(appendData("Board" , paOrg.getName(), true , true));
                      //if (part.getHealthcareFacilityIi() != null
                        //   && part.getHealthcareFacilityIi().getExtension() != null) {
                        //Organization affOrg = correlationUtils.getPAOrganizationByPAHealthCareFacilityId(
                          //  IiConverter.convertToLong(part.getHealthcareFacilityIi()));
                        //if (affOrg != null) {
                         // html.append(appendData(" affiliated with " , affOrg.getName(), false , true));
                        //}
                     // }
                     html.append(appendData(" affiliated with " , 
                             part.getReviewBoardOrganizationalAffiliation().getValue(), false , true));
                     html.append(appendData("Full Address" , AddressConverterUtil.convertToAddress(
                              poOrg.getPostalAddress()), true , true));
                      Object[] telList = poOrg.getTelecomAddress().getItem().toArray();
                      for (Object tel : telList) {
                          if (tel instanceof TelPhone) {
                              html.append(appendData("Phone" ,
                                      ((TelPhone) tel).getValue().getSchemeSpecificPart() , true , true));
                              break;
                          }
                      }

                      for (Object tel : telList) {
                          if (tel instanceof TelEmail) {
                              html.append(appendData("Email" ,
                                      ((TelEmail) tel).getValue().getSchemeSpecificPart() , true , true));
                              break;
                          }
                      } // for
                  } // po org
              }
          }
      }
  }
  
  /**
   * Append regulatory information.
   * 
   * @param html the html
   * @param spDto the sp dto
   * 
   * @throws PAException the PA exception
   */
  private void appendRegulatoryInformation(StringBuffer html , StudyProtocolDTO spDto) throws  PAException {

//    html.append(appendData("Reporting Dataset Method",
//        getData(spDto.getAccrualReportingMethodCode(), true), true , false));
      appendTitle(html, appendBoldData("Regulatory Information"));
      html.append(BR);
      List<StudyRegulatoryAuthorityDTO> sraDTOList = 
              studyRegulatoryAuthorityService.getCurrentByStudyProtocol(spDto.getIdentifier());
      StudyRegulatoryAuthorityDTO sraDTO = null;
      if (!sraDTOList.isEmpty()) {
          sraDTO = sraDTOList.get(0);
      }  
      if (sraDTO != null) {
           String data = null;
           RegulatoryAuthority ra = regulatoryInformationService.
                      get(Long.valueOf(sraDTO.getRegulatoryAuthorityIdentifier().getExtension()));

           Country country =  regulatoryInformationService.getRegulatoryAuthorityCountry(
                  Long.valueOf(sraDTO.getRegulatoryAuthorityIdentifier().getExtension()));
          if (country != null && ra != null) {
              data = country.getName() + ": " + ra.getAuthorityName();
          } else if (country != null) {
              data = country.getName();
          } else if (ra != null) {
              data = ra.getAuthorityName();
          }
          html.append(appendData("Trial Oversight Authority", data, true , true));
      }
      html.append(appendData("DMC Appointed?", convertBLToString(
              spDto.getDataMonitoringCommitteeAppointedIndicator(), true), true , true));
      html.append(appendData("FDA Regulated Intervention?", convertBLToString(
              spDto.getFdaRegulatedIndicator(), true), true , true));
      html.append(appendData("Section 801?", convertBLToString(
              spDto.getSection801Indicator(), true), true , true));
      List<StudyIndldeDTO> indIde = studyIndldeService.getByStudyProtocol(spDto.getIdentifier());
      if (indIde != null && (!indIde.isEmpty())) {
          html.append(appendData("IND/IDE Study?", YES, true , true));
      } else {
          html.append(appendData("IND/IDE Study?", NO, true , true));
      }
      html.append(appendData("Delayed Posting?", convertBLToString(
              spDto.getDelayedpostingIndicator(), true), true , true));

  }

  /**
   * Append secondary identifiers.
   * 
   * @param html the html
   * @param studyProtocolDto the study protocol dto
   * 
   * @throws PAException the PA exception
   */
  private void appendSecondaryIdentifiers(StringBuffer html , StudyProtocolDTO studyProtocolDto) throws  PAException {
      StudyParticipationDTO spartDTO = new StudyParticipationDTO();
      spartDTO.setFunctionalCode(CdConverter.convertToCd(StudyParticipationFunctionalCode.LEAD_ORGANIZATION));
      List<StudyParticipationDTO> sParts = studyParticipationService
          .getByStudyProtocol(studyProtocolDto.getIdentifier(), spartDTO);
     
      appendTitle(html, appendBoldData("Trial Identification"));
      html.append(BR);
      html.append(appendData("NCI Identifier" , studyProtocolDto.getAssignedIdentifier().getExtension() , true , true));
      if (!sParts.isEmpty()) {
      for (StudyParticipationDTO spart : sParts) {
          html.append(appendData("Lead Organization Identifier" , 
                  getInfo(spart.getLocalStudyProtocolIdentifier() , true) , true, true));
          break;
      }
      spartDTO = new StudyParticipationDTO();
      spartDTO.setFunctionalCode(CdConverter.convertToCd(StudyParticipationFunctionalCode.IDENTIFIER_ASSIGNER));
      sParts = studyParticipationService.getByStudyProtocol(studyProtocolDto.getIdentifier(), spartDTO);
      for (StudyParticipationDTO spart : sParts) {
          html.append(appendData("NCT Number" ,
                  getInfo(spart.getLocalStudyProtocolIdentifier() , true) , true, true));
          break;
       }
      } 
      if (studyProtocolDto.getAmendmentNumber() != null) {
          html.append(appendData("Amendment Number" , studyProtocolDto.getAmendmentNumber().getValue() , true, true));
      }
      if (studyProtocolDto.getAmendmentDate() != null && studyProtocolDto.getAmendmentDate().getValue() != null) {
          html.append(appendData("Amendment Date" , PAUtil.normalizeDateString(
                   TsConverter.convertToTimestamp(studyProtocolDto.getAmendmentDate()).toString()) , true, true));
      }
      html.append(TBL_E);
    }

  /**
   * Append titles.
   * @param html the html
   * @param studyProtocolDto the study protocol dto
   */
  private void appendTitles(StringBuffer html , StudyProtocolDTO studyProtocolDto) {
    appendTitle(html, appendBoldData("General Trial Details"));
    html.append(BR)
        .append(appendData("Official Title", getInfo(studyProtocolDto.getOfficialTitle(), true), true, true));
    html.append(BR).append(appendData("Brief Title", getInfo(studyProtocolDto.getPublicTitle(), true), true, true));
    html.append(BR).append(appendData("Acronym", getInfo(studyProtocolDto.getAcronym(), true), true, true));
    html.append(BR)
        .append(appendData("Brief Summary", getInfo(studyProtocolDto.getPublicDescription(), true), true, true));
    html.append(BR).append(appendData("Detailed Description", 
                getInfo(studyProtocolDto.getScientificDescription(), true), true, true));
    html.append(BR).append(appendData("Keywords", getInfo(studyProtocolDto.getKeywordText(), true), true, true));
    html.append(BR).append(appendData("Reporting Dataset Method", 
                getInfo(studyProtocolDto.getAccrualReportingMethodCode().getDisplayName(), true), true, true));
    html.append(BR);

  }

  /**
   * Append orgs and persons.
   * 
   * @param html the html
   * @param studyProtocolIi the study protocol ii
   * 
   * @throws PAException the PA exception
   */
  @SuppressWarnings({"PMD" })
  private void appendOrgsAndPersons(StringBuffer html , Ii studyProtocolIi) throws PAException {
      Organization sponsor = ocsr.getOrganizationByFunctionRole(
              studyProtocolIi, CdConverter.convertToCd(StudyParticipationFunctionalCode.SPONSOR));
      Organization lead = ocsr.getOrganizationByFunctionRole(
              studyProtocolIi, CdConverter.convertToCd(StudyParticipationFunctionalCode.LEAD_ORGANIZATION));
      Person leadPi = null;
      Person centralContact = null;
      StudyContactDTO scDto = new StudyContactDTO();
      scDto.setRoleCode(CdConverter.convertToCd(StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR));
      List<StudyContactDTO> scDtos = studyContactService.getByStudyProtocol(studyProtocolIi, scDto);
      html.append(appendData("Sponsor" , sponsor.getName(), true , true));
      html.append(appendData("Lead Organization" , lead.getName(), true , true));
      html.append(BR).append(BR);
      for (StudyContactDTO pi : scDtos) {
          leadPi = correlationUtils.getPAPersonByPAClinicalResearchStaffId(
                  Long.valueOf(pi.getClinicalResearchStaffIi().getExtension()));
          html.append(appendData("Principal Investigator" , leadPi.getFullName(), false , true));
          html.append(BR);
          html.append(appendData("Affliated with" , lead.getName(), false, true));
          break;

      }
      scDto = new StudyContactDTO();
      scDto.setRoleCode(CdConverter.convertToCd(StudyContactRoleCode.CENTRAL_CONTACT));
      scDtos = studyContactService.getByStudyProtocol(studyProtocolIi, scDto);
      DSet<Tel> dset = null;
      List<String> emails = null;
      List<String> phones = null;
      for (StudyContactDTO cc : scDtos) {
          centralContact = correlationUtils.getPAPersonByPAClinicalResearchStaffId(
                  Long.valueOf(cc.getClinicalResearchStaffIi().getExtension()));
          html.append(DL_B  + DT_B);
          html.append(appendData("Central Contact" , centralContact.getFullName() , false , true));
          html.append(DT_E);
          dset = cc.getTelecomAddresses();
          emails = DSetConverter.convertDSetToList(dset, "EMAIL");
          if (emails != null && !emails.isEmpty()) {
              html.append(DD_B);
              html.append(appendData("Email" , emails.get(0) , false , true));
              html.append(DD_E);
          }
          phones = DSetConverter.convertDSetToList(dset, "PHONE");
          if (phones != null && !phones.isEmpty()) {
              html.append(DD_B);
              html.append(appendData("Phone" , phones.get(0) , false , true));
              html.append(DD_E + DL_E);
          }
          break;

      }
      // responsible party section
      scDto = new StudyContactDTO();
      scDto.setRoleCode(CdConverter.convertToCd(StudyContactRoleCode.RESPONSIBLE_PARTY_STUDY_PRINCIPAL_INVESTIGATOR));
      scDtos = studyContactService.getByStudyProtocol(studyProtocolIi, scDto);
      Person rp = null;
      Organization sponsorResponsible = null;
      if (scDtos != null && !scDtos.isEmpty()) {
          scDto = scDtos.get(0);
          rp = correlationUtils.getPAPersonByPAClinicalResearchStaffId(
                  Long.valueOf(scDto.getClinicalResearchStaffIi().getExtension()));
          dset = scDto.getTelecomAddresses();
          
          StudyParticipationDTO spartDTO = new StudyParticipationDTO();
          spartDTO.setFunctionalCode(
                  CdConverter.convertToCd(StudyParticipationFunctionalCode.LEAD_ORGANIZATION));
          List<StudyParticipationDTO> sParts = studyParticipationService.getByStudyProtocol(studyProtocolIi, spartDTO);
          for (StudyParticipationDTO spart : sParts) {
              sponsorResponsible = correlationUtils.getPAOrganizationByPAResearchOrganizationId(
                      Long.valueOf(spart.getResearchOrganizationIi().getExtension()));
          }
          
      } else {
          StudyParticipationContactDTO spart = new StudyParticipationContactDTO();
          spart.setRoleCode(CdConverter.convertToCd(
                  StudyParticipationContactRoleCode.RESPONSIBLE_PARTY_SPONSOR_CONTACT));
          List<StudyParticipationContactDTO> spDtos = studyParticipationContactService
              .getByStudyProtocol(studyProtocolIi, spart);
          
          if (spDtos != null && !spDtos.isEmpty()) {
              rp = correlationUtils.getPAPersonByPAOrganizationalContactId((
                      Long.valueOf(spDtos.get(0).getOrganizationalContactIi().getExtension())));
              dset = spDtos.get(0).getTelecomAddresses();
              
              StudyParticipationDTO spDto = new StudyParticipationDTO();
              spDto.setFunctionalCode(CdConverter.convertToCd(StudyParticipationFunctionalCode.SPONSOR));
              List<StudyParticipationDTO> spartDtos =
                  studyParticipationService.getByStudyProtocol(studyProtocolIi, spDto);
              if (spartDtos != null && !spartDtos.isEmpty()) {
                  spDto = spartDtos.get(0);
                  sponsorResponsible = new CorrelationUtils().getPAOrganizationByPAResearchOrganizationId(
                              Long.valueOf(spDto.getResearchOrganizationIi().getExtension()));
              }

          }
      }
      html.append(DL_B  + DT_B);
      html.append(appendData("Responsible Party" , rp.getFullName() , false , true));
      html.append(DT_E + DT_B);
      
      // get the organization
      html.append(appendData("Organization" , sponsorResponsible.getName(), false , true));
      html.append(DT_E);
      emails = DSetConverter.convertDSetToList(dset, "EMAIL");
      if (emails != null && !emails.isEmpty()) {
          html.append(DD_B);
          html.append(appendData("Email" , emails.get(0) , false , true));
          html.append(DD_E);
      }
      phones = DSetConverter.convertDSetToList(dset, "PHONE");
      if (phones != null && !phones.isEmpty()) {
          html.append(DD_B);
          html.append(appendData("Phone" , phones.get(0) , false , true));
          html.append(DD_E);
      }
      html.append(DT_B);
      html.append(appendData("Overall Official" , leadPi.getFullName(), false , true));
      html.append(BR);
      html.append(appendData("Affliated with" ,  lead.getName(), false, true));
      html.append(DT_E + DD_B);
      html.append(appendData("Role" , "Principal Investigator", false , true));
      html.append(DD_E + DL_E);
  }

  /**
   * Apppend trial status.
   * 
   * @param html the html
   * @param spDto the sp dto
   * 
   * @throws PAException the PA exception
   */
  private void apppendTrialStatus(StringBuffer html , StudyProtocolDTO spDto) throws PAException {
      List<StudyOverallStatusDTO> sostatuses = studyOverallStatusService.
              getByStudyProtocol(spDto.getIdentifier());
      appendTitle(html, appendBoldData("Status/Dates"));
      html.append(BR).append(BR).append(TBL_B);
      html.append(TR_B);
      appendTDAndData(html, appendTRBold("Status"));
      appendTDAndData(html, appendTRBold("As of Date"));
      appendTDAndData(html, appendTRBold("Reason Text"));
      html.append(TR_E);
      for (StudyOverallStatusDTO sostatus : sostatuses) {
          html.append(TR_B);
          appendTDAndData(html, getTableData(sostatus.getStatusCode(), true));
          appendTDAndData(html, PAUtil.normalizeDateString(
                  TsConverter.convertToTimestamp(sostatus.getStatusDate()).toString()));
          appendTDAndData(html, sostatus.getReasonText().getValue() == null ? ""
              : sostatus.getReasonText().getValue());
          html.append(TR_E);
      }
      html.append(TBL_E);
      html.append(appendData("Trial Start Date" , PAUtil.normalizeDateString(TsConverter.convertToTimestamp(
              spDto.getStartDate()).toString()) , true , true)).append(" - ").
              append(getData(spDto.getStartDateTypeCode(), true));

      html.append(appendData("Primary Completion Date" , PAUtil.normalizeDateString(TsConverter.convertToTimestamp(
              spDto.getPrimaryCompletionDate()).toString()) , true , true)).append(" - ").
              append(getData(spDto.getPrimaryCompletionDateTypeCode(), true));

  }
  
  /**
   * Append td column and data.
   * 
   * @param html the html
   * @param column the column
   * @param data the data
   * @param appendTR the append tr
   */
  /*private void appendTDColumnAndData(StringBuffer html , String column , String data , boolean appendTR) {
      if (appendTR) {
          html.append(TR_B);
      }
      html.append(TD_B).append(FONT_LABEL).append(BLD_B).append(column).append(BLD_E)
          .append(FONT_LABEL).append(TD_E)
          .append(TD_B).append(data).append(TD_E);
      if (appendTR) {
          html.append(TR_E);
      }

  }*/
  
  /**
   * Append td and data.
   * 
   * @param html the html
   * @param data the data
   */
  private void appendTDAndData(StringBuffer html , String data) {
      html.append(TD_B).append(data).append(TD_E);
  }

  /**
   * Gets the info.
   * 
   * @param st the st
   * @param appendNoData the append no data
   * 
   * @return the info
   */
  private String getInfo(String st , boolean appendNoData) {
      if (st == null || st.equalsIgnoreCase("")  && appendNoData) {
          return NO_INFO;
      }
      return st;
  }

  
  /**
   * Gets the info.
   * 
   * @param st the st
   * @param appendNoData the append no data
   * 
   * @return the info
   */
  private String getInfo(St st , boolean appendNoData) {
      if (st.getValue() == null || st.getValue().equalsIgnoreCase("")  && appendNoData) {
          return NO_INFO;
      }
      return st.getValue();
  }

  /**
   * Gets the data.
   * 
   * @param st the st
   * @param appendNoData the append no data
   * 
   * @return the data
   */
  private String getData(St st , boolean appendNoData) {
      if (st.getValue() == null || st.getValue().equalsIgnoreCase("")  && appendNoData) {
          return NO_DATA;
      }
      return st.getValue();
  }
  
  /**
   * Gets the data.
   * 
   * @param i the i
   * @param appendNoData the append no data
   * 
   * @return the data
   */
  private String getData(Int i , boolean appendNoData) {
    if (i.getValue() == null && appendNoData) {
        return NO_DATA;
    }
    return i.getValue().toString();
}
  
  /**
   * Gets the data.
   * 
   * @param cd the cd
   * @param appendNoData the append no data
   * 
   * @return the data
   */
  private String getTableData(Cd cd , boolean appendNoData) {
      String data = CdConverter.convertCdToString(cd);
      if (data != null) {
          return data;
      } else if (appendNoData) {
          return NO_DATA;
      } else {
          return null;
      }
  }
  
  /**
   * Gets the data.
   * 
   * @param cd the cd
   * @param appendNoData the append no data
   * 
   * @return the data
   */
  private String getData(Cd cd , boolean appendNoData) {
      String data = CdConverter.convertCdToString(cd);
      if (data != null) {
          return data;
      } else if (appendNoData) {
          return NO_INFO;
      } else {
          return null;
      }
  }
  /**
   * Append bold data.
   * 
   * @param data the data
   * 
   * @return the string
   */
  private String appendBoldData(String data) {
      return BLD_B + data + BLD_E;
  }
  
  /**
   * Append tr bold.
   * 
   * @param data the data
   * 
   * @return the string
   */
  private String appendTRBold(String data) {
    return BLD_B  + "<U>" + data + "</U>" + BLD_E;
}
  
  /**
   * Append data.
   * 
   * @param column the column
   * @param data the data
   * @param newLine the new line
   * @param bold the bold
   * 
   * @return the string
   */
  private String appendData(String column , String data, boolean newLine , boolean bold) {
      return (newLine ? BR : EMPTY) + (bold ? BLD_B : EMPTY) + FONT_LABEL
          + column + ": " + FONT_END + (bold ? BLD_E : EMPTY) 
          + FONT_TEXT + getInfo(data, true) + FONT_END;
  }
  
  /**
   * Append Label.
   * 
   * @param column the column
   * @param data the data
   * @param newLine the new line
   * @param bold the bold
   * 
   * @return the string
   */
  private String appendLabel(String column , String data, boolean newLine , boolean bold) {
      return (newLine ? BR : EMPTY) + (bold ? BLD_B : EMPTY) + FONT_LABEL
          + column + ": " + FONT_END + (bold ? BLD_E : EMPTY) 
          + FONT_TEXT + data + FONT_END;
  }
  /**
   * Append title.
   * 
   * @param html the html
   * @param title the title
   */
  private void appendTitle(StringBuffer html , String title) {
      html.append(BR).append(BR).append(FONT_SECTION).append(title).append(FONT_END);
  }
  
  /**
   * Convert bl to string.
   * 
   * @param bl the bl
   * @param appendNoData the append no data
   * 
   * @return the string
   */
  private static String convertBLToString(Bl bl , boolean appendNoData) {
      if (bl == null && appendNoData) {
          return NO_DATA;
      } else if (bl == null) {
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
