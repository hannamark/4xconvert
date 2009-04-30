/***
* caBIG Open Source Software License
* 
* Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Clinical Trials Protocol Application
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
package gov.nih.nci.registry.action;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyParticipationContactDTO;
import gov.nih.nci.pa.iso.dto.StudyParticipationDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.registry.dto.TrialDTO;
import gov.nih.nci.registry.dto.TrialDocumentWebDTO;
import gov.nih.nci.registry.dto.TrialFundingWebDTO;
import gov.nih.nci.registry.dto.TrialIndIdeDTO;
import gov.nih.nci.registry.enums.TrialStatusCode;
import gov.nih.nci.registry.util.Constants;
import gov.nih.nci.registry.util.RegistryServiceLocator;
import gov.nih.nci.registry.util.RegistryUtil;
import gov.nih.nci.registry.util.TrialUtil;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @author Bala Nair
 * @author Harsha
 * 
 */
@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.NPathComplexity", "PMD.ExcessiveClassLength",
    "PMD.TooManyFields"  })
public class SubmitTrialAction extends ActionSupport implements ServletResponseAware {
    private static final Logger LOG = Logger.getLogger(SubmitTrialAction.class);
    private Long cbValue;
    private String page;
    private File protocolDoc;
    private String protocolDocFileName;
    private File irbApproval;
    private String irbApprovalFileName;
    private Long id = null;
    private HttpServletResponse servletResponse;
    private String trialAction = "submit";
    private static String sessionTrialDTO = "trialDTO";
    /**
     * Adding new members for PO integration and additional Use cases.
     */
    // Collection for holding the ideInd information
    private File participatingSites = null;
    private String participatingSitesFileName = null;
    private File informedConsentDocument = null;
    private String informedConsentDocumentFileName = null;
    private File otherDocument = null;
    private String otherDocumentFileName = null;
    private TrialDTO trialDTO;


    /**
     * 
     * @return res
     */
    public String execute() {
        trialDTO = new TrialDTO();
        trialDTO.setResponsiblePartyType("pi");
        trialDTO.setTrialType("Interventional");
        TrialValidator.removeSessionAttributes();
        return SUCCESS;
    }

    /**
     * create protocol.
     * 
     * @return String
     */
    @SuppressWarnings({"PMD.ExcessiveMethodLength" })
    public String create() {
        try {
            trialDTO = (TrialDTO) ServletActionContext.getRequest().getSession().getAttribute(sessionTrialDTO);
            if (trialDTO == null) {
               return ERROR; 
            }
            //duplicate check--
          //check for duplicate using the Lead Org Trial Identifier and Lead Org Identifier
            Organization paOrg = new Organization();
            paOrg.setIdentifier(trialDTO.getLeadOrganizationIdentifier());
            paOrg = RegistryServiceLocator.getPAOrganizationService().getOrganizationByIndetifers(paOrg);
            if (paOrg == null) {
                return ERROR;
            } else {
                StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
                criteria.setLeadOrganizationTrialIdentifier(trialDTO.getLocalProtocolIdentifier());
                criteria.setLeadOrganizationId(paOrg.getId().toString());
                criteria.setExcludeRejectProtocol(Boolean.TRUE);
                List<StudyProtocolQueryDTO> records = RegistryServiceLocator.
                                    getProtocolQueryService().getStudyProtocolByCriteria(criteria);
                if (records != null && !records.isEmpty()) {
                    addActionError("Duplicate Trial Submission: A trial exists in the system with the same "
                            + "Lead Organization Trial Identifier for the selected Lead Organization");
                    ServletActionContext.getRequest().setAttribute(
                              "failureMessage" , "Duplicate Trial Submission: A trial exists in the system " 
                              + "with the same  Lead Organization Trial Identifier for the " 
                              + "selected Lead Organization");
                    return ERROR;
                }
            }
            TrialUtil util = new TrialUtil();
            StudyProtocolDTO studyProtocolDTO = null;
            if (trialDTO.getTrialType().equals("Interventional")) {
                studyProtocolDTO = util.convertToInterventionalStudyProtocolDTO(trialDTO);
            } else {
                studyProtocolDTO = util.convertToInterventionalStudyProtocolDTO(trialDTO);
            }
            StudyOverallStatusDTO overallStatusDTO = util.convertToStudyOverallStatusDTO(trialDTO);
            List<DocumentDTO> documentDTOs = util.convertToISODocumentList(trialDTO.getDocDtos());
            OrganizationDTO leadOrgDTO = util.convertToLeadOrgDTO(trialDTO);
            PersonDTO principalInvestigatorDTO = util.convertToLeadPI(trialDTO);
            OrganizationDTO sponsorOrgDTO = util.convertToSponsorOrgDTO(trialDTO);
            StudyParticipationDTO leadOrgParticipationIdDTO = util.convertToStudyParticipationDTO(trialDTO);
            StudyParticipationDTO nctIdentifierParticipationIdDTO = util.convertToNCTStudyParticipationDTO(trialDTO);
            StudyContactDTO studyContactDTO = null;
            StudyParticipationContactDTO studyParticipationContactDTO = null;
            OrganizationDTO summary4orgDTO = util.convertToSummary4OrgDTO(trialDTO);
            StudyResourcingDTO summary4studyResourcingDTO = util.convertToSummary4StudyResourcingDTO(trialDTO);
            PersonDTO responsiblePartyContactDTO = null;
            if (trialDTO.getResponsiblePartyType().equalsIgnoreCase("pi")) {
                studyContactDTO = util.convertToStudyContactDTO(trialDTO);
            } else {
                studyParticipationContactDTO = util.convertToStudyParticipationContactDTO(trialDTO);
                responsiblePartyContactDTO = util.convertToResponsiblePartyContactDTO(trialDTO);
            }
            List<StudyIndldeDTO> studyIndldeDTOs = util.convertISOINDIDEList(trialDTO.getIndIdeDtos());
            List<StudyResourcingDTO> studyResourcingDTOs = util.convertISOGrantsList(trialDTO.getFundingDtos());
            
            Ii studyProtocolIi =  RegistryServiceLocator.getTrialRegistrationService().
            createInterventionalStudyProtocol(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs,
                    studyResourcingDTOs, documentDTOs,
                    leadOrgDTO, principalInvestigatorDTO, sponsorOrgDTO, leadOrgParticipationIdDTO,
                    nctIdentifierParticipationIdDTO, studyContactDTO, studyParticipationContactDTO,
                    summary4orgDTO, summary4studyResourcingDTO, responsiblePartyContactDTO);
           //send a notification mail
            RegistryServiceLocator.getMailManagerService().sendNotificationMail(studyProtocolIi);
            TrialValidator.removeSessionAttributes();
            ServletActionContext.getRequest().getSession().setAttribute("spidfromviewresults", studyProtocolIi);
            ServletActionContext.getRequest().getSession().setAttribute("protocolId", studyProtocolIi.getExtension());

        } catch (Exception e) {
            if (e != null && e.getMessage() != null) {
                addActionError(e.getMessage());
            } else {
                addActionError("Error occured, please try again");
            }
            ServletActionContext.getRequest().setAttribute("failureMessage", e.getMessage());
            LOG.error("Exception occured while submitting trial: " + e);
            return ERROR;
        }
        return "redirect_to_search";
    }

    /**
     * validate the submit trial form elements.
     */
    private void validateForm() {
        TrialValidator validator = new TrialValidator();
        Map<String, String> err = new HashMap<String, String>();
        err = validator.validateTrialDTO(trialDTO);
        addErrors(err);
        if (PAUtil.isNotEmpty(trialDTO.getStatusCode())
                && RegistryUtil.isValidDate(trialDTO.getStatusDate())
                && PAUtil.isNotEmpty(trialDTO.getCompletionDateType())
                && RegistryUtil.isValidDate(trialDTO.getCompletionDate())
                && PAUtil.isNotEmpty(trialDTO.getStartDateType())
                && RegistryUtil.isValidDate(trialDTO.getStartDate())) {
            validateTrialDates(trialDTO);
        }
        err = new HashMap<String, String>();
        err = validator.validateDcoument(protocolDocFileName, protocolDoc, "trialDTO.protocolDocFileName",
                "error.submit.protocolDocument");
        addErrors(err);
        err = new HashMap<String, String>();
        err = validator.validateDcoument(irbApprovalFileName, irbApproval, "trialDTO.irbApprovalFileName",
                "error.submit.irbApproval");
        addErrors(err);
        err = new HashMap<String, String>();
        err = validator.validateDcoument(participatingSitesFileName, participatingSites,
                "trialDTO.participatingSitesFileName", "");
        addErrors(err);
        err = new HashMap<String, String>();
        err = validator.validateDcoument(informedConsentDocumentFileName, informedConsentDocument, 
                "trialDTO.informedConsentDocumentFileName", "");
        //protocol Highlighted doc
        addErrors(err);
        err = new HashMap<String, String>();
        err = validator.validateDcoument(otherDocumentFileName, otherDocument, "trialDTO.otherDocumentFileName", "");
        addErrors(err);
    }
    /**
     * validate the submit trial dates.
     */
    @SuppressWarnings({"PMD.ExcessiveMethodLength" })
    private void validateTrialDates(TrialDTO trialDto) {
        String startDateFieldName = "trialDTO.startDate";
        // Constraint/Rule: 18 Current Trial Status Date must be current or past.
        if (PAUtil.isNotEmpty(trialDto.getStatusDate())) {
            Timestamp statusDate = PAUtil.dateStringToTimestamp(trialDto.getStatusDate());
            Timestamp currentTimeStamp = new Timestamp((new Date()).getTime());
            if (currentTimeStamp.before(statusDate)) {
                addFieldError("trialDTO.statusDate", getText("error.submit.invalidStatusDate"));                
            }
        }        
        // Constraint/Rule: 19 Trial Start Date must be current/past if ‘actual’ trial start date type 
        // is selected and must be future if ‘anticipated’ trial start date type is selected.
        if (PAUtil.isNotEmpty(trialDto.getStartDate()) && PAUtil.isNotEmpty(trialDto.getStartDateType())) {
            if (trialDto.getStartDateType().equals(ActualAnticipatedTypeCode.ACTUAL.getCode())) {
                Timestamp trialStartDate = PAUtil.dateStringToTimestamp(trialDto.getStartDate());
                Timestamp currentTimeStamp = new Timestamp((new Date()).getTime());
                if (currentTimeStamp.before(trialStartDate)) {
                    addFieldError(startDateFieldName, getText("error.submit.invalidActualStartDate"));                
                }
            } else if (trialDto.getStartDateType().equals(ActualAnticipatedTypeCode.ANTICIPATED.getCode())) {
                Timestamp trialStartDate = PAUtil.dateStringToTimestamp(trialDto.getStartDate());
                Timestamp currentTimeStamp = new Timestamp((new Date()).getTime());
                if (currentTimeStamp.after(trialStartDate)) {
                 addFieldError(startDateFieldName, getText("error.submit.invalidAnticipatedStartDate"));                
                }
            }          
        }        
        // Constraint/Rule: 20 Primary Completion Date must be current/past if ‘actual’ 
        // primary completion date type is selected and must be future if ‘anticipated’ 
        // trial primary completion date type is selected.
        if (PAUtil.isNotEmpty(trialDto.getCompletionDate()) && PAUtil.isNotEmpty(trialDto.getCompletionDateType())) {
            if (trialDto.getCompletionDateType().equals(ActualAnticipatedTypeCode.ACTUAL.getCode())) {
                 Timestamp completionDate = PAUtil.dateStringToTimestamp(trialDto.getCompletionDate());
                 Timestamp currentTimeStamp = new Timestamp((new Date()).getTime());
                 if (currentTimeStamp.before(completionDate)) {
                       addFieldError("trialDTO.completionDate", 
                               getText("error.submit.invalidActualCompletionDate"));                
                   }
            } else if (trialDto.getCompletionDateType().equals(ActualAnticipatedTypeCode.ANTICIPATED.getCode())) {
                    Timestamp completionDate = PAUtil.dateStringToTimestamp(trialDto.getCompletionDate());
                    Timestamp currentTimeStamp = new Timestamp((new Date()).getTime());
                    if (currentTimeStamp.after(completionDate)) {
                            addFieldError("trialDTO.completionDate", 
                                    getText("error.submit.invalidAnticipatedCompletionDate"));                
                        }
            }          
        }        
        // Constraint/Rule:  21 If Current Trial Status is ‘Active’, Trial Start Date must be the same as 
        // Current Trial Status Date and have ‘actual’ type.
        //Commenting this rule in pa2.0 release
        /*if (PAUtil.isNotEmpty(trialDto.getStatusCode())
            && PAUtil.isNotEmpty(trialDto.getStatusDate())
            && PAUtil.isNotEmpty(trialDto.getStartDate())
            && PAUtil.isNotEmpty(trialDto.getStartDateType())
            && TrialStatusCode.ACTIVE.getCode().equals(trialDto.getStatusCode())) {
              Timestamp statusDate = PAUtil.dateStringToTimestamp(trialDto.getStatusDate());
              Timestamp trialStartDate = PAUtil.dateStringToTimestamp(trialDto.getStartDate());
              if (!statusDate.equals(trialStartDate) 
                              || !trialDto.getStartDateType().equals(
                                  ActualAnticipatedTypeCode.ACTUAL.getCode())) {
                  addFieldError(startDateFieldName, getText("error.submit.invalidStartDate"));
              }                
          }*/            
        // Constraint/Rule: 22 If Current Trial Status is ‘Approved’, Trial Start Date must have ‘anticipated’ type. 
        //  Trial Start Date must have ‘actual’ type for any other Current Trial Status value besides ‘Approved’. 
        if (PAUtil.isNotEmpty(trialDto.getStatusCode())
                         && PAUtil.isNotEmpty(trialDto.getStartDateType())) {
          if (TrialStatusCode.APPROVED.getCode().equals(
                          trialDto.getStatusCode())) {
              if (!trialDto.getStartDateType().equals(
                              ActualAnticipatedTypeCode.ANTICIPATED.getCode())) {
                  addFieldError("trialDTO.startDateType", 
                          getText("error.submit.invalidStartDateTypeApproved"));
              }                
          } else {
              if (!trialDto.getStartDateType().equals(
                       ActualAnticipatedTypeCode.ACTUAL.getCode())) {
                          addFieldError("trialDTO.startDateType", 
                                  getText("error.submit.invalidStartDateTypeOther"));
              }              
          }
        }        
        // Constraint/Rule: 23 If Current Trial Status is ‘Completed’, Primary Completion Date must be the 
        // same as Current Trial Status Date and have ‘actual’ type.
        if (PAUtil.isNotEmpty(trialDto.getStatusCode()) && PAUtil.isNotEmpty(trialDto.getStatusDate())
            && PAUtil.isNotEmpty(trialDto.getCompletionDate()) && PAUtil.isNotEmpty(trialDto.getCompletionDateType())
            && TrialStatusCode.COMPLETE.getCode().equals(trialDto.getStatusCode())) {
                  Timestamp statusDate = PAUtil.dateStringToTimestamp(trialDto.getStatusDate());
                  Timestamp trialCompletionDate = PAUtil.dateStringToTimestamp(trialDto.getCompletionDate());
                  if (!statusDate.equals(trialCompletionDate) || !trialDto.getCompletionDateType().equals(
                          ActualAnticipatedTypeCode.ACTUAL.getCode())) {
                              addFieldError("trialDTO.completionDate", 
                                  getText("error.submit.invalidCompletionDate"));
              }                
          }            
        // Constraint/Rule: 24 If Current Trial Status is ‘Completed’ or ‘Administratively Completed’, 
        // Primary Completion Date must have ‘actual’ type. Primary Completion Date must have ‘anticipated’ type 
        // for any other Current Trial Status value besides ‘Completed’ or ‘Administratively Completed’. 
        if (PAUtil.isNotEmpty(trialDto.getStatusCode()) && PAUtil.isNotEmpty(trialDto.getCompletionDateType())) {
          if (TrialStatusCode.COMPLETE.getCode().equals(trialDto.getStatusCode()) 
              || TrialStatusCode.ADMINISTRATIVELY_COMPLETE.getCode().equals(trialDto.getStatusCode())) {
              if (!trialDto.getCompletionDateType().equals(ActualAnticipatedTypeCode.ACTUAL.getCode())) {
                 addFieldError("trialDTO.completionDateType", getText("error.submit.invalidCompletionDateType"));
              }
          } else {
              if (PAUtil.isNotEmpty(trialDto.getCompletionDateType()) && !trialDto.getCompletionDateType().equals(
                  ActualAnticipatedTypeCode.ANTICIPATED.getCode())) {
                  addFieldError("trialDTO.completionDateType", 
                          getText("error.submit.invalidCompletionDateTypeOther"));                  
              }
          }          
        }        
        // Constraint/Rule:25 Trial Start Date must be same/smaller than Primary Completion Date. 
        if (PAUtil.isNotEmpty(trialDto.getStartDate()) && PAUtil.isNotEmpty(trialDto.getCompletionDate())) {
            Timestamp trialStartDate = PAUtil.dateStringToTimestamp(trialDto.getStartDate());
            Timestamp trialCompletionDate = PAUtil.dateStringToTimestamp(trialDto.getCompletionDate());
            if (trialCompletionDate.before(trialStartDate)) {
                addFieldError(startDateFieldName, getText("error.submit.invalidTrialDates"));                
            }
        }        
    }
 
    /**
     * @param response servletResponse
     */
    public void setServletResponse(HttpServletResponse response) {
        this.servletResponse = response;
    }

    /**
     * @return servletResponse
     */
    public HttpServletResponse getServletResponse() {
        return servletResponse;
    }

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return cbValue
     */
    public Long getCbValue() {
        return cbValue;
    }

    /**
     * @param cbValue cbValue
     */
    public void setCbValue(Long cbValue) {
        this.cbValue = cbValue;
    }

    /**
     * @return page
     */
    public String getPage() {
        return page;
    }

    /**
     * @param page page
     */
    public void setPage(String page) {
        this.page = page;
    }

    /**
     * @return protocolDoc
     */
    public File getProtocolDoc() {
        return protocolDoc;
    }

    /**
     * @param protocolDoc protocolDoc
     */
    public void setProtocolDoc(File protocolDoc) {
        this.protocolDoc = protocolDoc;
    }

    /**
     * @return protocolDocFileName
     */
    public String getProtocolDocFileName() {
        return protocolDocFileName;
    }

    /**
     * @param protocolDocFileName protocolDocFileName
     */
    public void setProtocolDocFileName(String protocolDocFileName) {
        this.protocolDocFileName = protocolDocFileName;
    }

    /**
     * @return irbApproval
     */
    public File getIrbApproval() {
        return irbApproval;
    }

    /**
     * @param irbApproval irbApproval
     */
    public void setIrbApproval(File irbApproval) {
        this.irbApproval = irbApproval;
    }

    /**
     * @return irbApprovalFileName
     */
    public String getIrbApprovalFileName() {
        return irbApprovalFileName;
    }

    /**
     * @param irbApprovalFileName irbApprovalFileName
     */
    public void setIrbApprovalFileName(String irbApprovalFileName) {
        this.irbApprovalFileName = irbApprovalFileName;
    }


    /**
     * @return the informedConsentDocument
     */
    public File getInformedConsentDocument() {
        return informedConsentDocument;
    }

    /**
     * @param informedConsentDocument the informedConsentDocument to set
     */
    public void setInformedConsentDocument(File informedConsentDocument) {
        this.informedConsentDocument = informedConsentDocument;
    }

    /**
     * @return the otherDocument
     */
    public File getOtherDocument() {
        return otherDocument;
    }

    /**
     * @param otherDocument the otherDocument to set
     */
    public void setOtherDocument(File otherDocument) {
        this.otherDocument = otherDocument;
    }

    /**
     * @return the informedConsentDocumentFileName
     */
    public String getInformedConsentDocumentFileName() {
        return informedConsentDocumentFileName;
    }

    /**
     * @param informedConsentDocumentFileName the
     *            informedConsentDocumentFileName to set
     */
    public void setInformedConsentDocumentFileName(String informedConsentDocumentFileName) {
        this.informedConsentDocumentFileName = informedConsentDocumentFileName;
    }

    /**
     * @return the otherDocumentFileName
     */
    public String getOtherDocumentFileName() {
        return otherDocumentFileName;
    }

    /**
     * @param otherDocumentFileName the otherDocumentFileName to set
     */
    public void setOtherDocumentFileName(String otherDocumentFileName) {
        this.otherDocumentFileName = otherDocumentFileName;
    }


    /**
     * @return the participatingSites
     */
    public File getParticipatingSites() {
        return participatingSites;
    }

    /**
     * @param participatingSites the participatingSites to set
     */
    public void setParticipatingSites(File participatingSites) {
        this.participatingSites = participatingSites;
    }

    /**
     * @return the participatingSitesFileName
     */
    public String getParticipatingSitesFileName() {
        return participatingSitesFileName;
    }

    /**
     * @param participatingSitesFileName the participatingSitesFileName to set
     */
    public void setParticipatingSitesFileName(String participatingSitesFileName) {
        this.participatingSitesFileName = participatingSitesFileName;
    }
    /**
     * @param trialAction the trialAction to set
     */
    public void setTrialAction(String trialAction) {
        this.trialAction = trialAction;
    }

    /**
     * @return the trialAction
     */
    public String getTrialAction() {
        return trialAction;
    }

    /**
     * @return the trialDTO
     */
    public TrialDTO getTrialDTO() {
        return trialDTO;
    }

    /**
     * @param trialDTO the trialDTO to set
     */
    public void setTrialDTO(TrialDTO trialDTO) {
        this.trialDTO = trialDTO;
    }

     /**
     * @param err
     */
    private void addErrors(Map<String, String> err) {
        if (!err.isEmpty()) {
            for (String msg : err.keySet()) {
                addFieldError(msg, err.get(msg));
            }
        }
    }
    /**
     * @return
     * @throws IOException
     */
    private List<TrialDocumentWebDTO> addDocDTOToList() throws IOException {
        TrialUtil util = new TrialUtil();
        List<TrialDocumentWebDTO> docDTOList = new ArrayList<TrialDocumentWebDTO>();
        if (PAUtil.isNotEmpty(protocolDocFileName)) {
            docDTOList.add(util.convertToDocumentDTO(DocumentTypeCode.PROTOCOL_DOCUMENT.getCode(), 
                    protocolDocFileName, protocolDoc));
        }
        if (PAUtil.isNotEmpty(irbApprovalFileName)) {
            docDTOList.add(util.convertToDocumentDTO(DocumentTypeCode.IRB_APPROVAL_DOCUMENT.getCode(), 
                        irbApprovalFileName, irbApproval));
        }
        if (PAUtil.isNotEmpty(informedConsentDocumentFileName)) {
            docDTOList.add(util.convertToDocumentDTO(DocumentTypeCode.INFORMED_CONSENT_DOCUMENT.getCode(),
                        informedConsentDocumentFileName, informedConsentDocument));
        }
        if (PAUtil.isNotEmpty(participatingSitesFileName)) {
            docDTOList.add(util.convertToDocumentDTO(DocumentTypeCode.PARTICIPATING_SITES.getCode(),
                        participatingSitesFileName, participatingSites));
         }
         if (PAUtil.isNotEmpty(otherDocumentFileName)) {
             docDTOList.add(util.convertToDocumentDTO(DocumentTypeCode.OTHER.getCode(), 
                        otherDocumentFileName, otherDocument));  
         }
        return docDTOList;
    }
    /**
     * 
     * @return s
     */
    public String review() {
        try {
            clearErrorsAndMessages();
            validateForm();
            if (hasFieldErrors()) {
                ServletActionContext.getRequest().setAttribute(
                        "failureMessage" , "The form has errors and could not be submitted, "
                        + "please check the fields highlighted below");
                TrialValidator.addSessionAttributes(trialDTO);
                return ERROR;
            }
            List<TrialDocumentWebDTO> docDTOList = addDocDTOToList();
           trialDTO.setDocDtos(docDTOList);
           //get the document and put in list
           //add the IndIde,FundingList
           List<TrialIndIdeDTO> indList = (List<TrialIndIdeDTO>) ServletActionContext.getRequest()
           .getSession().getAttribute(Constants.INDIDE_LIST);
           if (indList != null) {
               trialDTO.setIndIdeDtos(indList);
           }
           
           List<TrialFundingWebDTO> grantList = (List<TrialFundingWebDTO>) ServletActionContext.getRequest()
           .getSession().getAttribute(Constants.GRANT_LIST);
           if (grantList != null) {
               trialDTO.setFundingDtos(grantList);
           }

        } catch (IOException e) {
            LOG.error(e.getMessage());
            return ERROR;
        }
        TrialValidator.removeSessionAttributes();
        ServletActionContext.getRequest().getSession().setAttribute("trialDTO", trialDTO);
        LOG.info("Calling the review page...");
        return "review";    
    }
    /**
     * 
     * @return s
     */
    public String cancel() {
        TrialValidator.removeSessionAttributes();
        return "redirect_to_search";
    }
    /**
     * 
     * @return s
     */
    public String edit() {
        trialDTO = (TrialDTO) ServletActionContext.getRequest().getSession().getAttribute(sessionTrialDTO);
        TrialValidator.addSessionAttributes(trialDTO);
        return "edit";
    }

}
