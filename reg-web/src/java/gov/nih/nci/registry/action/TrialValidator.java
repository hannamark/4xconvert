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

import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.PrimaryPurposeCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.registry.dto.StudyOverallStatusWebDTO;
import gov.nih.nci.registry.dto.TrialDTO;
import gov.nih.nci.registry.enums.TrialStatusCode;
import gov.nih.nci.registry.util.Constants;
import gov.nih.nci.registry.util.RegistryServiceLocator;
import gov.nih.nci.registry.util.RegistryUtil;

import java.io.File;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.InvalidValue;

/**
 * 
 * @author Vrushali
 *
 */
@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.NPathComplexity" })
public class TrialValidator {
    private static String actualString = "Actual";
    private static String anticipatedString = "Anticipated";
    private static final Logger LOG = Logger.getLogger(TrialValidator.class);
    /**
     * 
     * @param fileName name
     * @param file file
     * @param errorField err
     * @param errorMsg errmsg
     * @return map
     */
    public Map<String, String> validateDcoument(String fileName, File file,
            String errorField, String errorMsg) {
            Map<String, String> addFieldError = new HashMap<String, String>();
        if (PAUtil.isNotEmpty(errorMsg) && PAUtil.isEmpty(fileName)) {
            addFieldError.put(errorField, getText(errorMsg));
        }
        if (PAUtil.isNotEmpty(fileName)) {
            if (!file.exists()) {
                addFieldError.put(errorField, 
                        getText("error.submit.invalidDocument"));
            }
            if (!RegistryUtil.isValidFileType(fileName)) {
                addFieldError.put(errorField, 
                        getText("error.submit.invalidFileType"));                
            }
        }
        return addFieldError;
    }

    private String getText(String errorMsg) {
        // TODO Auto-generated method stub
        return ResourceBundle.getBundle("ApplicationResources").getString(errorMsg);
    }

    /**
     * 
     * @param trialDto dto
     * @return map
     */
    @SuppressWarnings({"PMD", "PMD.AvoidDeeplyNestedIfStmts" })
    public Map<String, String> validateTrialDTO(TrialDTO trialDto) {
        Map<String, String> addFieldError = new HashMap<String, String>(); 
        InvalidValue[] invalidValues = null;
        ClassValidator<TrialDTO> classValidator = new ClassValidator(trialDto.getClass());
                invalidValues = classValidator.getInvalidValues(trialDto);
                for (int i = 0; i < invalidValues.length; i++) {
                    addFieldError.put("trialDTO." + invalidValues[i].getPropertyName(), 
                            getText(invalidValues[i].getMessage().trim()));
        }
        if (!(trialDto.getResponsiblePartyType().equals("pi"))
             && (PAUtil.isEmpty(trialDto.getResponsiblePersonIdentifier()))) {
            addFieldError.put("ResponsiblePartyNotSelected", 
                    getText("error.submit.sponsorResponsibelParty"));
        }
        //validate Phase and Purpose when Selected value is OTHER
        if (PAUtil.isNotEmpty(trialDto.getPhaseCode()) && (PhaseCode.OTHER.getCode().equals(trialDto.getPhaseCode()) 
                        && PAUtil.isEmpty(trialDto.getPhaseOtherText()))) {
                addFieldError.put("trialDTO.phaseOtherText", 
                        getText("error.submit.otherPhaseText"));
        }
        if (PAUtil.isNotEmpty(trialDto.getPrimaryPurposeCode()) 
                && (PrimaryPurposeCode.OTHER.getCode().equals(trialDto.getPrimaryPurposeCode()) 
                        && PAUtil.isEmpty(trialDto.getPrimaryPurposeOtherText()))) {
                addFieldError.put("trialDTO.primaryPurposeOtherText", 
                        getText("error.submit.otherPurposeText"));
        }
        String err = "error.submit.invalidDate";      // validate date and its format
        if (!RegistryUtil.isValidDate(trialDto.getStatusDate())) {
                    addFieldError.put("trialDTO.statusDate", getText(err));
        }
        if (!RegistryUtil.isValidDate(trialDto.getStartDate())) {
                    addFieldError.put("trialDTO.startDate", getText(err));
        }
        if (!RegistryUtil.isValidDate(trialDto.getCompletionDate())) {
                    addFieldError.put("trialDTO.completionDate", getText(err));
        }
        if (PAUtil.isNotEmpty(trialDto.getStatusCode())) {
            if ((StudyStatusCode.ADMINISTRATIVELY_COMPLETE.getCode().equals(trialDto.getStatusCode())
                 || StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL.getCode().equals(trialDto.getStatusCode())
                 || StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION.getCode().
                 equals(trialDto.getStatusCode())
                 || StudyStatusCode.WITHDRAWN.getCode().equals(trialDto.getStatusCode()))
                 && PAUtil.isEmpty(trialDto.getReason())) {
                addFieldError.put("trialDTO.reason", getText("error.submit.trialStatusReason"));
            }
        }
        if (PAUtil.isNotEmpty(trialDto.getStatusCode())
                && RegistryUtil.isValidDate(trialDto.getStatusDate())
                && PAUtil.isNotEmpty(trialDto.getCompletionDateType())
                && RegistryUtil.isValidDate(trialDto.getCompletionDate())
                && PAUtil.isNotEmpty(trialDto.getStartDateType())
                && RegistryUtil.isValidDate(trialDto.getStartDate())) {
            addFieldError.putAll(validateTrialDates(trialDto));
        }
        return addFieldError;
    }
    /**
     *  
     * @param trialDto dto
     * @return t
     * @throws PAException ex
     */
    public boolean isTrialStatusOrDateChanged(TrialDTO trialDto) throws PAException {
        StudyOverallStatusWebDTO  dto = getStatusDTO(trialDto.getIdentifier());
        StudyStatusCode oldStatusCode = StudyStatusCode.getByCode(dto.getStatusCode());
        Timestamp oldStatusDate = PAUtil.dateStringToTimestamp(dto.getStatusDate());
        
        boolean codeChanged = (StudyStatusCode.getByCode(trialDto.getStatusCode()) == null)
                ? (oldStatusCode != null) : !StudyStatusCode.getByCode(trialDto.getStatusCode()).equals(oldStatusCode);
        boolean statusDateChanged = (oldStatusDate == null) 
                ? (PAUtil.dateStringToTimestamp(trialDto.getStatusDate()) != null) 
                : !oldStatusDate.equals(PAUtil.dateStringToTimestamp(trialDto.getStatusDate()));
        if (!codeChanged && !statusDateChanged) {
            return false;
        }
        return true;
    }
    /**
     * 
     * @param trialDto dto
     * @return b
     * @throws PAException ex
     */
    @SuppressWarnings({"PMD.AvoidDeeplyNestedIfStmts", "PMD.ExcessiveMethodLength" })
    public Collection<String> enforceBusinessRulesForDates(TrialDTO trialDto) throws PAException {
        Collection<String> addActionError = new HashSet<String>();
        StudyStatusCode newCode = StudyStatusCode.getByCode(trialDto.getStatusCode());
        Timestamp newStatusTimestamp = PAUtil.dateStringToTimestamp(trialDto.getStatusDate());
        StudyOverallStatusWebDTO  dto = getStatusDTO(trialDto.getIdentifier());
        StudyStatusCode oldStatusCode = StudyStatusCode.getByCode(dto.getStatusCode());
        if (oldStatusCode != null && !oldStatusCode.canTransitionTo(newCode)) {
            addActionError.add("Illegal study status transition from '" + oldStatusCode.getCode()
                    + "' to '" + newCode.getCode() + "'.  ");
        }
        if ((trialDto.getStartDateType() != null) && (trialDto.getCompletionDateType() != null)) {
            if (StudyStatusCode.APPROVED.equals(oldStatusCode) && StudyStatusCode.ACTIVE.equals(newCode)) {
                if (!PAUtil.dateStringToTimestamp(trialDto.getStartDate()).equals(newStatusTimestamp)) {
                    addActionError.add("When transitioning from 'Approved' to 'Active' the trial start "
                            + "date must be the same as the status date.");
                }
                if (!trialDto.getStartDateType().equals(actualString)) {
                    addActionError.add("When transitioning from 'Approved' to 'Active' "
                            + "the trial start date must be 'Actual'.");
                }
            }
            if (!StudyStatusCode.APPROVED.equals(newCode) && !StudyStatusCode.WITHDRAWN.equals(newCode)
                    && trialDto.getStartDateType().equals(anticipatedString)) {
                addActionError.add("Trial start date can be 'Anticipated' only if the status is "
                            + "'Approved' or 'Withdrawn'.");
            }
            if (StudyStatusCode.APPROVED.equals(oldStatusCode) && StudyStatusCode.WITHDRAWN.equals(newCode)
                    && trialDto.getStartDateType().equals(actualString)) {
                addActionError.add("Trial Start date type should be 'Anticipated' and Trial Start date "
                            + "should be future date if Trial Status is changed from 'Approved' to 'Withdrawn'.  ");
            }
            if (StudyStatusCode.COMPLETE.equals(newCode) || StudyStatusCode.ADMINISTRATIVELY_COMPLETE.equals(newCode)) {
                StudyOverallStatusDTO oldStatusDto = RegistryServiceLocator.getStudyOverallStatusService()
                        .getCurrentByStudyProtocol(IiConverter.convertToIi(trialDto.getIdentifier()));
                if (trialDto.getCompletionDateType().equals(anticipatedString)) {
                    addActionError.add("Primary Completion Date cannot be 'Anticipated' when "
                            + "Current Trial Status is '" + newCode.getCode() + "'.");
                }
                if (PAUtil.dateStringToTimestamp(trialDto.getCompletionDate())
                        .before(TsConverter.convertToTimestamp(oldStatusDto.getStatusDate()))) {
                    addActionError.add("Primary Completion Date must be the same or greater than "
                            + "Current Trial Status Date when Current Trial Status is '" + newCode.getCode() + "'.");
                }
            } else {
                if (!trialDto.getCompletionDateType().equals(anticipatedString)) {
                    addActionError.add("Trial completion date must be 'Anticipated' when the status is "
                            + "not 'Complete' or 'Administratively Complete'.");
                }
            }
        }
        return addActionError;
    }

    /**
     * 
     * @param id id
     * @return dto
     * @throws PAException ex
     */
    private StudyOverallStatusWebDTO getStatusDTO(String id) throws PAException {
        StudyOverallStatusWebDTO webDTO = new StudyOverallStatusWebDTO();    
       StudyOverallStatusDTO sos = RegistryServiceLocator.getStudyOverallStatusService()
        .getCurrentByStudyProtocol(IiConverter.convertToIi(id));
        if (sos != null) {
            webDTO.setStatusCode(CdConverter.convertCdToString(sos.getStatusCode()));
            webDTO.setStatusDate(TsConverter.convertToString(sos.getStatusDate()));
            webDTO.setReason(StConverter.convertToString(sos.getReasonText()));
        }
        return webDTO;
    }
    /**
     * 
     * @param fileName
     * @param file
     * @param errorField
     * @param errorMsg
     */
    public static void removeSessionAttributes() {
        ServletActionContext.getRequest().getSession().removeAttribute("indIdeList");
        ServletActionContext.getRequest().getSession().removeAttribute("grantList");
        ServletActionContext.getRequest().getSession().removeAttribute("PoLeadOrg");
        ServletActionContext.getRequest().getSession().removeAttribute("PoLeadPI");
        ServletActionContext.getRequest().getSession().removeAttribute("PoSponsor");
        ServletActionContext.getRequest().getSession().removeAttribute("Sponsorselected");
        ServletActionContext.getRequest().getSession().removeAttribute("PoResponsibleContact");
        ServletActionContext.getRequest().getSession().removeAttribute("PoSummary4Sponsor");
        ServletActionContext.getRequest().getSession().removeAttribute("trialDTO");
        ServletActionContext.getRequest().getSession().removeAttribute("spidfromviewresults");
        ServletActionContext.getRequest().getSession().removeAttribute("indIdeUpdateList");
        ServletActionContext.getRequest().getSession().removeAttribute("collaboratorsList");
        ServletActionContext.getRequest().getSession().removeAttribute("participatingSitesList");
        
    }
    /**
     * 
     * @param tDTO dto
     */
    public static void addSessionAttributes(TrialDTO tDTO) {
        if (tDTO == null) {
            return;
        }
        if (!tDTO.getIndIdeDtos().isEmpty()) {
            ServletActionContext.getRequest().getSession().setAttribute(Constants.INDIDE_LIST,
                    tDTO.getIndIdeDtos());
        }
        if (!tDTO.getFundingDtos().isEmpty()) {
            ServletActionContext.getRequest().getSession().setAttribute(Constants.GRANT_LIST,
                    tDTO.getFundingDtos());
        }
        List<DocumentDTO> documentISOList;
        try {
            documentISOList = RegistryServiceLocator.getDocumentService()
            .getDocumentsByStudyProtocol(IiConverter.convertToIi(tDTO.getIdentifier()));
            if (!(documentISOList.isEmpty())) {
                ServletActionContext.getRequest().setAttribute(Constants.PROTOCOL_DOCUMENT, documentISOList);
            }
            ServletActionContext.getRequest().getSession().setAttribute("spidfromviewresults", 
                    IiConverter.convertToIi(tDTO.getIdentifier()));
        } catch (PAException e) {
            LOG.error("exception in edit" + e.getMessage());
        }
    }
    
    /**
     * 
     * @param tDTO dto
     */
    public static void addSessionAttributesForUpdate(TrialDTO tDTO) {
        if (tDTO == null) {
            return;
        }
        if (!tDTO.getIndIdeUpdateDtos().isEmpty()) {
            ServletActionContext.getRequest().getSession().setAttribute(Constants.INDIDE_UPDATE_LIST,
                    tDTO.getIndIdeUpdateDtos());
        }
        if (!tDTO.getFundingDtos().isEmpty()) {
            ServletActionContext.getRequest().getSession().setAttribute(Constants.GRANT_LIST,
                    tDTO.getFundingDtos());
        }
        if (!tDTO.getIndIdeAddDtos().isEmpty()) {
            ServletActionContext.getRequest().getSession().setAttribute(Constants.INDIDE_ADD_LIST,
                    tDTO.getIndIdeAddDtos());
        }
        if (!tDTO.getFundingAddDtos().isEmpty()) {
            ServletActionContext.getRequest().getSession().setAttribute(Constants.GRANT_ADD_LIST,
                    tDTO.getFundingAddDtos());
        }
        if (!tDTO.getCollaborators().isEmpty()) {
            ServletActionContext.getRequest().getSession().setAttribute(Constants.COLLABORATORS_LIST,
                    tDTO.getCollaborators());
        }
        if (!tDTO.getCountryList().isEmpty()) {
            ServletActionContext.getRequest().getSession().setAttribute(Constants.COUNTRY_LIST,
                    tDTO.getCountryList());
        }
        if (!tDTO.getRegIdAuthOrgList().isEmpty()) {
            ServletActionContext.getRequest().getSession().setAttribute(Constants.REG_AUTH_LIST,
                    tDTO.getRegIdAuthOrgList());
        }
        if (!tDTO.getParticipatingSites().isEmpty()) {
            ServletActionContext.getRequest().getSession().setAttribute(Constants.PARTICIPATING_SITES_LIST,
                    tDTO.getParticipatingSites());
        }
        List<DocumentDTO> documentISOList;
        try {
            documentISOList = RegistryServiceLocator.getDocumentService()
            .getDocumentsByStudyProtocol(IiConverter.convertToIi(tDTO.getIdentifier()));
            if (!(documentISOList.isEmpty())) {
                ServletActionContext.getRequest().setAttribute(Constants.PROTOCOL_DOCUMENT, documentISOList);
            }
            ServletActionContext.getRequest().getSession().setAttribute("spidfromviewresults", 
                    IiConverter.convertToIi(tDTO.getIdentifier()));
        } catch (PAException e) {
            LOG.error("exception in edit" + e.getMessage());
        }
    }
    
    /**
     * validate the submit trial dates.
     * @param trialDto dto
     */
    @SuppressWarnings({"PMD.ExcessiveMethodLength" })
    private Map<String, String> validateTrialDates(TrialDTO trialDto) {
        Map<String, String> addFieldError = new HashMap<String, String>();
        String startDateFieldName = "trialDTO.startDate";
        // Constraint/Rule: 18 Current Trial Status Date must be current or past.
        if (PAUtil.isNotEmpty(trialDto.getStatusDate())) {
            Timestamp statusDate = PAUtil.dateStringToTimestamp(trialDto.getStatusDate());
            Timestamp currentTimeStamp = new Timestamp((new Date()).getTime());
            if (currentTimeStamp.before(statusDate)) {
                addFieldError.put("trialDTO.statusDate", getText("error.submit.invalidStatusDate"));                
            }
        }        
        //Constraint/Rule: 19 Trial Start Date must be current/past if 'actual' trial start date type 
        //is selected and must be future if 'anticipated' trial start date type is selected.
        if (PAUtil.isNotEmpty(trialDto.getStartDate()) && PAUtil.isNotEmpty(trialDto.getStartDateType())) {
            if (trialDto.getStartDateType().equals(ActualAnticipatedTypeCode.ACTUAL.getCode())) {
                Timestamp trialStartDate = PAUtil.dateStringToTimestamp(trialDto.getStartDate());
                Timestamp currentTimeStamp = new Timestamp((new Date()).getTime());
                if (currentTimeStamp.before(trialStartDate)) {
                  addFieldError.put(startDateFieldName, getText("error.submit.invalidActualStartDate"));                
                }
           } else if (trialDto.getStartDateType().equals(ActualAnticipatedTypeCode.ANTICIPATED.getCode())) {
              Timestamp trialStartDate = PAUtil.dateStringToTimestamp(trialDto.getStartDate());
              Timestamp currentTimeStamp = new Timestamp((new Date()).getTime());
              if (currentTimeStamp.after(trialStartDate)) {
                  addFieldError.put(startDateFieldName, 
                          getText("error.submit.invalidAnticipatedStartDate"));                
              }
            }          
        }        
        // Constraint/Rule: 20 Primary Completion Date must be current/past if 'actual' 
        //primary completion date type is selected and must be future if 'anticipated' 
        //trial primary completion date type is selected.
        if (PAUtil.isNotEmpty(trialDto.getCompletionDate()) && PAUtil.isNotEmpty(trialDto.getCompletionDateType())) {
            if (trialDto.getCompletionDateType().equals(ActualAnticipatedTypeCode.ACTUAL.getCode())) {
                 Timestamp completionDate = PAUtil.dateStringToTimestamp(trialDto.getCompletionDate());
                 Timestamp currentTimeStamp = new Timestamp((new Date()).getTime());
                 if (currentTimeStamp.before(completionDate)) {
                       addFieldError.put("trialDTO.completionDate", 
                               getText("error.submit.invalidActualCompletionDate"));                
                   }
            } else if (trialDto.getCompletionDateType().equals(ActualAnticipatedTypeCode.ANTICIPATED.getCode())) {
                    Timestamp completionDate = PAUtil.dateStringToTimestamp(trialDto.getCompletionDate());
                    Timestamp currentTimeStamp = new Timestamp((new Date()).getTime());
                    if (currentTimeStamp.after(completionDate)) {
                            addFieldError.put("trialDTO.completionDate", 
                                    getText("error.submit.invalidAnticipatedCompletionDate"));                
                        }
            }          
        }        
        // Constraint/Rule:  21 If Current Trial Status is 'Active', Trial Start Date must be the same as 
        //Current Trial Status Date and have 'actual' type.
        //pa2.0 release adding Trial Start date is smaller or same Current Trial Status Date 
        if (PAUtil.isNotEmpty(trialDto.getStatusCode())
            && PAUtil.isNotEmpty(trialDto.getStatusDate())
            && PAUtil.isNotEmpty(trialDto.getStartDate())
            && PAUtil.isNotEmpty(trialDto.getStartDateType())
            && TrialStatusCode.ACTIVE.getCode().equals(trialDto.getStatusCode())) {
              Timestamp statusDate = PAUtil.dateStringToTimestamp(trialDto.getStatusDate());
              Timestamp trialStartDate = PAUtil.dateStringToTimestamp(trialDto.getStartDate());
              if (trialStartDate.after(statusDate) 
                              || !trialDto.getStartDateType().equals(
                                  ActualAnticipatedTypeCode.ACTUAL.getCode())) {
                  addFieldError.put(startDateFieldName, getText("error.submit.invalidStartDate"));
              }                
          }            
        //Constraint/Rule: 22 If Current Trial Status is 'Approved', Trial Start Date must have 'anticipated' type. 
        //Trial Start Date must have 'actual' type for any other Current Trial Status value besides 'Approved'. 
        if (PAUtil.isNotEmpty(trialDto.getStatusCode()) && PAUtil.isNotEmpty(trialDto.getStartDateType())) {
          if (TrialStatusCode.APPROVED.getCode().equals(trialDto.getStatusCode())
                  || TrialStatusCode.IN_REVIEW.getCode().equals(trialDto.getStatusCode())
                  || StudyStatusCode.WITHDRAWN.getCode().equals(trialDto.getStatusCode())) {
              if (!trialDto.getStartDateType().equals(
                              ActualAnticipatedTypeCode.ANTICIPATED.getCode())) {
                  addFieldError.put("trialDTO.startDateType", 
                          getText("error.submit.invalidStartDateTypeApproved"));
              }                
          } else {
              if (!trialDto.getStartDateType().equals(
                       ActualAnticipatedTypeCode.ACTUAL.getCode())) {
                          addFieldError.put("trialDTO.startDateType", 
                                  getText("error.submit.invalidStartDateTypeOther"));
              }              
          }
        }        
        //Constraint/Rule: 23 If Current Trial Status is 'Completed', Primary Completion Date must be the 
        //same as Current Trial Status Date and have 'actual' type.
        if (PAUtil.isNotEmpty(trialDto.getStatusCode()) && PAUtil.isNotEmpty(trialDto.getStatusDate())
            && PAUtil.isNotEmpty(trialDto.getCompletionDate()) && PAUtil.isNotEmpty(trialDto.getCompletionDateType())
            && TrialStatusCode.COMPLETE.getCode().equals(trialDto.getStatusCode())//) {
                  //Timestamp statusDate = PAUtil.dateStringToTimestamp(trialDto.getStatusDate());
                  //Timestamp trialCompletionDate = PAUtil.dateStringToTimestamp(trialDto.getCompletionDate());
                  && !trialDto.getCompletionDateType().equals(
                          ActualAnticipatedTypeCode.ACTUAL.getCode())) {
                              addFieldError.put("trialDTO.completionDate", 
                                  getText("error.submit.invalidCompletionDate"));
          }            
        //Constraint/Rule: 24 If Current Trial Status is 'Completed' or 'Administratively Completed', 
        //Primary Completion Date must have 'actual' type. Primary Completion Date must have 'anticipated' type 
        //for any other Current Trial Status value besides 'Completed' or 'Administratively Completed'. 
        if (PAUtil.isNotEmpty(trialDto.getStatusCode()) && PAUtil.isNotEmpty(trialDto.getCompletionDateType())) {
          if (TrialStatusCode.COMPLETE.getCode().equals(trialDto.getStatusCode()) 
              || TrialStatusCode.ADMINISTRATIVELY_COMPLETE.getCode().equals(trialDto.getStatusCode())) {
              if (!trialDto.getCompletionDateType().equals(ActualAnticipatedTypeCode.ACTUAL.getCode())) {
                 addFieldError.put("trialDTO.completionDateType", getText("error.submit.invalidCompletionDateType"));
              }
          } else {
              if (PAUtil.isNotEmpty(trialDto.getCompletionDateType()) && !trialDto.getCompletionDateType().equals(
                  ActualAnticipatedTypeCode.ANTICIPATED.getCode())) {
                  addFieldError.put("trialDTO.completionDateType", 
                          getText("error.submit.invalidCompletionDateTypeOther"));                  
              }
          }          
        }        
        // Constraint/Rule:25 Trial Start Date must be same/smaller than Primary Completion Date. 
        if (PAUtil.isNotEmpty(trialDto.getStartDate()) && PAUtil.isNotEmpty(trialDto.getCompletionDate())) {
            Timestamp trialStartDate = PAUtil.dateStringToTimestamp(trialDto.getStartDate());
            Timestamp trialCompletionDate = PAUtil.dateStringToTimestamp(trialDto.getCompletionDate());
            if (trialCompletionDate.before(trialStartDate)) {
                addFieldError.put(startDateFieldName, getText("error.submit.invalidTrialDates"));                
            }
        }   
        return addFieldError;
    }

}
