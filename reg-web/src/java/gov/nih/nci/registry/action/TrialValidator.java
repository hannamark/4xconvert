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

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAAttributeMaxLen;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.registry.dto.StudyOverallStatusWebDTO;
import gov.nih.nci.registry.dto.TrialDTO;
import gov.nih.nci.registry.util.Constants;
import gov.nih.nci.registry.util.RegistryUtil;
import gov.nih.nci.registry.util.TrialUtil;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.InvalidValue;

/**
 *
 * @author Vrushali
 *
 */
public class TrialValidator {
    private static final String PRIMARY_COMPLETION_DATE = "trialDTO.primaryCompletionDate";
    private static final String PRIMARY_COMPLETION_DATE_TYPE = "trialDTO.primaryCompletionDateType";
    private static final String ACTUAL_DATETYPE = "Actual";
    private static final String ANTICIPATED_DATETYPE = "Anticipated";
    private static final Logger LOG = Logger.getLogger(TrialValidator.class);
    private static final Set<String> TRIAL_STATUS_REQ_SET = new HashSet<String>();
    static {
        TRIAL_STATUS_REQ_SET.add(StudyStatusCode.ADMINISTRATIVELY_COMPLETE.getCode());
        TRIAL_STATUS_REQ_SET.add(StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL.getCode());
        TRIAL_STATUS_REQ_SET.add(StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION.getCode());
        TRIAL_STATUS_REQ_SET.add(StudyStatusCode.WITHDRAWN.getCode());
    }

    /**
     * @param messageKey key of message to look up
     * @return message
     */
    protected static String getText(String messageKey) {
        return ResourceBundle.getBundle("ApplicationResources").getString(messageKey);
    }
    
    /**
     *
     * @param trialDto dto
     * @return map
     */
    public Map<String, String> validateTrial(TrialDTO trialDto) {
        Map<String, String> addFieldError = new HashMap<String, String>();
        validateTrialDTO(trialDto, addFieldError);
        validatePrimaryPurposeAdditionalQualifier(trialDto, addFieldError);
        validatePrimaryPurposeOtherText(trialDto, addFieldError);
        validateDateAndFormat(trialDto, addFieldError);
        validateStudyStatusReason(trialDto, addFieldError);
        validateXMLReqElement(trialDto, addFieldError);
        validateSummaryFourInfo(trialDto, addFieldError);
        return addFieldError;
    }
    
    private void validateStudyStatusReason(TrialDTO trialDto, Map<String, String> addFieldError) {
        if (StringUtils.isNotEmpty(trialDto.getStatusCode())
              && TRIAL_STATUS_REQ_SET.contains(trialDto.getStatusCode())) {
              addErrors(trialDto.getReason(), "trialDTO.reason", "error.submit.trialStatusReason", addFieldError);
        }
        if (StringUtils.length(trialDto.getReason()) > PAAttributeMaxLen.LEN_2000) {
            addFieldError.put("trialDTO.reason", getText("error.reason.maxLength"));
         }
    }
    
    private void validatePrimaryPurposeOtherText(TrialDTO trialDto, Map<String, String> addFieldError) {
        if (PAUtil.isPrimaryPurposeOtherTextReq(trialDto.getPrimaryPurposeCode(),
               trialDto.getPrimaryPurposeAdditionalQualifierCode(), trialDto.getPrimaryPurposeOtherText())) {
              addFieldError.put("trialDTO.primaryPurposeOtherText", getText("error.submit.otherPurposeText"));
        }
    }
    
    private void validatePrimaryPurposeAdditionalQualifier(TrialDTO trialDto, Map<String, String> addFieldError) {
        if (PAUtil.isPrimaryPurposeOtherCodeReq(trialDto.getPrimaryPurposeCode(),
                trialDto.getPrimaryPurposeAdditionalQualifierCode())) {
                addFieldError.put("trialDTO.primaryPurposeAdditionalQualifierCode",
                   getText("error.submit.otherPurposeCode"));
        }
    }

    private void validateTrialDTO(TrialDTO trialDto, Map<String, String> addFieldError) {
        InvalidValue[] invalidValues = new ClassValidator<TrialDTO>(TrialDTO.class).getInvalidValues(trialDto);
        for (int i = 0; i < invalidValues.length; i++) {
            addFieldError.put("trialDTO." + invalidValues[i].getPropertyName(), getText(invalidValues[i].getMessage()
                .trim()));
        }
    }

    private void validateDateAndFormat(TrialDTO trialDto, Map<String, String> addFieldError) {
        String err = getText("error.submit.invalidDate"); // validate date and its format
        addErrorForDate(trialDto.getStatusDate(), "trialDTO.statusDate", err, addFieldError);
        addErrorForDate(trialDto.getStartDate(), "trialDTO.startDate", err, addFieldError);
        addErrorForDate(trialDto.getPrimaryCompletionDate(), PRIMARY_COMPLETION_DATE, err, addFieldError);
        if (isValidDateAndCodeOrType(trialDto.getStatusCode(), trialDto.getStatusDate())
                && isValidDateAndCodeOrType(trialDto.getPrimaryCompletionDateType(),
                                            trialDto.getPrimaryCompletionDate())
                && isValidDateAndCodeOrType(trialDto.getStartDateType(), trialDto.getStartDate())) {
            addFieldError.putAll(validateTrialDates(trialDto));
        }
    }

    private boolean isValidDateAndCodeOrType(String code, String strDate) {
        return StringUtils.isNotEmpty(code) && RegistryUtil.isValidDate(strDate);
    }

    private void addErrorForDate(String fieldValue, String fieldName, String errMsg, 
            Map<String, String> fieldErrorMap) {
        if (!RegistryUtil.isValidDate(fieldValue)) {
            fieldErrorMap.put(fieldName, errMsg);
        }
    }

    private void validateSummaryFourInfo(TrialDTO trialDto, Map<String, String> addFieldError) {
        if (!StringUtils.isEmpty(trialDto.getSummaryFourFundingCategoryCode())
             && StringUtils.isEmpty(trialDto.getSummaryFourOrgIdentifier())) {
                addFieldError.put("summary4FundingSponsor", "Select the Summary 4 Funding Sponsor");
        }
        if (StringUtils.isEmpty(trialDto.getSummaryFourFundingCategoryCode())
            && !StringUtils.isEmpty(trialDto.getSummaryFourOrgIdentifier())) {
            addFieldError.put("trialDTO.summaryFourFundingCategoryCode", "Select the Summary 4 Funding Sponsor Type");
        }
    }
    private void validateXMLReqElement(TrialDTO trialDto, Map<String, String> fieldErrorMap) {
       if (trialDto.isXmlRequired()) {
           validateRespPartyInfo(trialDto, fieldErrorMap);
           addErrors(trialDto.getSelectedRegAuth(), "regulatory.oversight.auth.name", "error.oversight.orgName",
             fieldErrorMap);
           addErrors(trialDto.getLst(), "trialDTO.lst", "error.oversight.countryName", fieldErrorMap);
        }
    }
    private void validateRespPartyInfo(TrialDTO trialDto, Map<String, String> fieldErrorMap) {
       addErrors(trialDto.getResponsiblePartyType(), "trialDTO.responsiblePartyType", "error.submit.ResponsibleParty",
            fieldErrorMap);
       addErrors(trialDto.getSponsorIdentifier(), "trialDTO.sponsorIdentifier", "error.submit.sponsor", fieldErrorMap);
       if (!TrialDTO.RESPONSIBLE_PARTY_TYPE_PI.equalsIgnoreCase(trialDto.getResponsiblePartyType())) {
              addErrors(trialDto.getResponsiblePersonIdentifier(), "sponsorContactMissing",
              "error.submit.sponsorResponsibleParty", fieldErrorMap);
       }
       addErrors(trialDto.getContactPhone(), "trialDTO.contactPhone", "error.submit.contactPhone", fieldErrorMap);
       if (StringUtils.isNotEmpty(trialDto.getContactPhone()) && !PAUtil.isValidPhone(trialDto.getContactPhone())) {
          fieldErrorMap.put("trialDTO.contactPhone", getText("error.register.invalidPhoneNumber"));
       }
       addErrors(trialDto.getContactEmail(), "trialDTO.contactEmail", "error.submit.contactEmail", fieldErrorMap);
       if (StringUtils.isNotEmpty(trialDto.getContactEmail()) && !PAUtil.isValidEmail(trialDto.getContactEmail())) {
           fieldErrorMap.put("trialDTO.contactEmail", getText("error.register.invalidContactEmailAddress"));
       }
    }
    private void addErrors(String fieldValue, String fieldName, String errMsg, Map<String, String> fieldErrorMap) {
       if (StringUtils.isEmpty(fieldValue)) {
            fieldErrorMap.put(fieldName, getText(errMsg));
       }
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
    public Collection<String> enforceBusinessRulesForDates(TrialDTO trialDto) throws PAException {
        Collection<String> addActionError = new HashSet<String>();
        StudyStatusCode newCode = StudyStatusCode.getByCode(trialDto.getStatusCode());
        Timestamp newStatusTimestamp = PAUtil.dateStringToTimestamp(trialDto.getStatusDate());
        StudyOverallStatusWebDTO  dto = getStatusDTO(trialDto.getIdentifier());
        StudyStatusCode oldStatusCode = StudyStatusCode.getByCode(dto.getStatusCode());
        if (oldStatusCode != null && !oldStatusCode.canTransitionTo(newCode)) {
            addActionError.add("Invalid study status transition from '" + oldStatusCode.getCode()
                    + "' to '" + newCode.getCode() + "'.  ");
        }
        if (trialDto.getStartDateType() != null && trialDto.getPrimaryCompletionDateType() != null) {
            validateStudyStatusApprovedToActiveOrWithdrawn(trialDto, addActionError, newCode, newStatusTimestamp,
                    oldStatusCode);
            validateStartDateType(trialDto, addActionError, newCode);
            validateStudyStatusForCompleteOrAdminComplete(trialDto, addActionError, newCode);
            validatePrimaryCompletionDateType(trialDto, newCode, addActionError);
        }
        return addActionError;
    }

    private void validatePrimaryCompletionDateType(TrialDTO trialDto, StudyStatusCode newCode,
            Collection<String> addActionError) {
        if (!(StudyStatusCode.COMPLETE == newCode || StudyStatusCode.ADMINISTRATIVELY_COMPLETE == newCode)
                && !ANTICIPATED_DATETYPE.equals(trialDto.getPrimaryCompletionDateType())) {
            addActionError.add("Trial primary completion date must be 'Anticipated' when the status is "
                    + "not 'Complete' or 'Administratively Complete'.");
        }
    }
    private void validateStartDateType(TrialDTO trialDto, Collection<String> addActionError, StudyStatusCode newCode) {
        if (!StudyStatusCode.APPROVED.getCode().equals(newCode.getCode()) && !StudyStatusCode.WITHDRAWN.getCode()
         .equals(newCode.getCode()) && ANTICIPATED_DATETYPE.equals(trialDto.getStartDateType())) {
              addActionError.add("Trial start date can be 'Anticipated' only if the status is "
                  + "'Approved' or 'Withdrawn'.");
        }
    }
    private void validateStudyStatusForCompleteOrAdminComplete(TrialDTO trialDto, Collection<String> addActionError,
            StudyStatusCode newCode) throws PAException {
        if (StudyStatusCode.COMPLETE == newCode || StudyStatusCode.ADMINISTRATIVELY_COMPLETE == newCode) {
            StudyOverallStatusDTO oldStatusDto = PaRegistry.getStudyOverallStatusService()
                 .getCurrentByStudyProtocol(IiConverter.convertToIi(trialDto.getIdentifier()));
            if (ANTICIPATED_DATETYPE.equals(trialDto.getPrimaryCompletionDateType())) {
              addActionError.add("Primary Completion Date cannot be 'Anticipated' when "
                    + "Current Trial Status is '" + newCode.getCode() + "'.");
            }
            if (PAUtil.dateStringToTimestamp(trialDto.getPrimaryCompletionDate())
                 .before(TsConverter.convertToTimestamp(oldStatusDto.getStatusDate()))) {
                 addActionError.add("Primary Completion Date must be the same or greater than "
                   + "Current Trial Status Date when Current Trial Status is '" + newCode.getCode() + "'.");
            }
        }
    }
    private void validateStudyStatusApprovedToActiveOrWithdrawn(TrialDTO trialDto, Collection<String> addActionError,
            StudyStatusCode newCode, Timestamp newStatusTimestamp, StudyStatusCode oldStatusCode) {
        if (StringUtils.equalsIgnoreCase(StudyStatusCode.APPROVED.getCode(), oldStatusCode.getCode())) {
            valiateTransitionToActiveStatus(trialDto, addActionError, newCode, newStatusTimestamp);
            if (StudyStatusCode.WITHDRAWN.equals(newCode) && ACTUAL_DATETYPE.equals(trialDto.getStartDateType())) {
               addActionError.add("Trial Start date type should be 'Anticipated' and Trial Start date "
                    + "should be future date if Trial Status is changed from 'Approved' to 'Withdrawn'.  ");
            }
        }
    }
    private void valiateTransitionToActiveStatus(TrialDTO trialDto, Collection<String> addActionError,
         StudyStatusCode newCode, Timestamp newStatusTimestamp) {
         if (StudyStatusCode.ACTIVE.equals(newCode)) {
             if (!PAUtil.dateStringToTimestamp(trialDto.getStartDate()).equals(newStatusTimestamp)) {
                 addActionError.add("When transitioning from 'Approved' to 'Active' the trial start "
                      + "date must be the same as the status date.");
             }
             if (!ACTUAL_DATETYPE.equals(trialDto.getStartDateType())) {
                 addActionError.add("When transitioning from 'Approved' to 'Active' "
                      + "the trial start date must be 'Actual'.");
             }
         }
    }
    private StudyOverallStatusWebDTO getStatusDTO(String id) throws PAException {
        StudyOverallStatusWebDTO webDTO = new StudyOverallStatusWebDTO();
        StudyOverallStatusDTO sos = PaRegistry.getStudyOverallStatusService()
        .getCurrentByStudyProtocol(IiConverter.convertToIi(id));
        if (sos != null) {
            webDTO.setStatusCode(CdConverter.convertCdToString(sos.getStatusCode()));
            webDTO.setStatusDate(TsConverter.convertToString(sos.getStatusDate()));
            webDTO.setReason(StConverter.convertToString(sos.getReasonText()));
        }
        return webDTO;
    }
    
    /**
     * Removes the session attributes.
     */
    public static void removeSessionAttributes() {
        ServletActionContext.getRequest().getSession().removeAttribute("indIdeList");
        ServletActionContext.getRequest().getSession().removeAttribute("grantList");
        ServletActionContext.getRequest().getSession().removeAttribute("secondaryIdentifiersList");
        ServletActionContext.getRequest().getSession().removeAttribute("PoLeadOrg");
        ServletActionContext.getRequest().getSession().removeAttribute("PoLeadPI");
        ServletActionContext.getRequest().getSession().removeAttribute("PoSponsor");
        ServletActionContext.getRequest().getSession().removeAttribute("Sponsorselected");
        ServletActionContext.getRequest().getSession().removeAttribute("PoResponsibleContact");
        ServletActionContext.getRequest().getSession().removeAttribute("PoSummary4Sponsor");
        ServletActionContext.getRequest().getSession().removeAttribute(TrialUtil.SESSION_TRIAL_ATTRIBUTE);
        ServletActionContext.getRequest().getSession().removeAttribute("spidfromviewresults");
        ServletActionContext.getRequest().getSession().removeAttribute("indIdeUpdateList");
        ServletActionContext.getRequest().getSession().removeAttribute("collaboratorsList");
        ServletActionContext.getRequest().getSession().removeAttribute("participatingSitesList");
        ServletActionContext.getRequest().getSession().removeAttribute(
                DocumentTypeCode.PROTOCOL_DOCUMENT.getShortName());
        ServletActionContext.getRequest().getSession().removeAttribute(
                DocumentTypeCode.IRB_APPROVAL_DOCUMENT.getShortName());
        ServletActionContext.getRequest().getSession().removeAttribute(
                DocumentTypeCode.PARTICIPATING_SITES.getShortName());
        ServletActionContext.getRequest().getSession().removeAttribute(
                DocumentTypeCode.INFORMED_CONSENT_DOCUMENT.getShortName());
        ServletActionContext.getRequest().getSession().removeAttribute(DocumentTypeCode.OTHER.getShortName());
        ServletActionContext.getRequest().getSession().removeAttribute(
                DocumentTypeCode.CHANGE_MEMO_DOCUMENT.getShortName());
        ServletActionContext.getRequest().getSession().removeAttribute(
                DocumentTypeCode.PROTOCOL_HIGHLIGHTED_DOCUMENT.getShortName());
    }

    /**
     * Add trial information to the session.  Elements (possibly) added:
     * <ul>
     * <li>IND/IDEs
     * <li>Grants
     * <li>Secondary Identifiers
     * <li>protocol documents
     * <li>study protocol II
     * </ul>
     * @param tDTO trial DTO from which to populate the session
     */
    public static void addSessionAttributes(TrialDTO tDTO) {
        if (tDTO == null) {
            return;
        }
        addToSession(tDTO.getIndIdeDtos(), Constants.INDIDE_LIST);
        addToSession(tDTO.getFundingDtos(), Constants.GRANT_LIST);
        if (StringUtils.isNotEmpty(tDTO.getAssignedIdentifier())) {
            addToSession(tDTO.getSecondaryIdentifierAddList(), Constants.SECONDARY_IDENTIFIERS_LIST);
        } else {
            addToSession(tDTO.getSecondaryIdentifierList(), Constants.SECONDARY_IDENTIFIERS_LIST);
        }

        final Ii spII = IiConverter.convertToIi(tDTO.getIdentifier());
        if (!ISOUtil.isIiNull(spII)) {
            try {
                List<DocumentDTO> documentISOList = PaRegistry.getDocumentService().getDocumentsByStudyProtocol(spII);
                if (!documentISOList.isEmpty()) {
                    ServletActionContext.getRequest().setAttribute(Constants.PROTOCOL_DOCUMENT, documentISOList);
                }
            } catch (PAException e) {
                LOG.info("Swallowed an exception adding trial session attributes", e);
            }
            ServletActionContext.getRequest().getSession().setAttribute("spidfromviewresults", spII);
        }
    }

    private static void addToSession(List<?> list, String sessionAttributeName) {
        if (!list.isEmpty()) {
            ServletActionContext.getRequest().getSession().setAttribute(sessionAttributeName, list);
        }
    }

    /**
     * @param tDTO dto
     */
    public static void addSessionAttributesForUpdate(TrialDTO tDTO) {
        if (tDTO == null) {
            return;
        }
        addToSession(tDTO.getIndIdeUpdateDtos(), Constants.INDIDE_UPDATE_LIST);
        addToSession(tDTO.getFundingDtos(), Constants.GRANT_LIST);
        addToSession(tDTO.getIndIdeAddDtos(), Constants.INDIDE_ADD_LIST);
        addToSession(tDTO.getFundingAddDtos(), Constants.GRANT_ADD_LIST);
        addToSession(tDTO.getCollaborators(), Constants.COLLABORATORS_LIST);
        addToSession(tDTO.getCountryList(), Constants.COUNTRY_LIST);
        addToSession(tDTO.getRegIdAuthOrgList(), Constants.REG_AUTH_LIST);
        addToSession(tDTO.getParticipatingSites(), Constants.PARTICIPATING_SITES_LIST);
        addToSession(tDTO.getSecondaryIdentifierAddList(), Constants.SECONDARY_IDENTIFIERS_LIST);
        List<DocumentDTO> documentISOList;
        final Ii spII = IiConverter.convertToIi(tDTO.getIdentifier());
        if (ISOUtil.isIiNull(spII)) {
            try {
                documentISOList = PaRegistry.getDocumentService().getDocumentsByStudyProtocol(spII);
                if (!(documentISOList.isEmpty())) {
                    ServletActionContext.getRequest().setAttribute(Constants.PROTOCOL_DOCUMENT, documentISOList);
                }
            } catch (PAException e) {
                LOG.info("Swallowed an exception adding trial session attributes for update", e);
            }
            ServletActionContext.getRequest().getSession().setAttribute("spidfromviewresults", spII);
        }
    }
    /**
     * validate the submit trial dates.
     */
    private Map<String, String> validateTrialDates(TrialDTO trialDto) {
        Map<String, String> addFieldError = new HashMap<String, String>();
        String startDateFieldName = "trialDTO.startDate";
        enforceRuleStatusDate(trialDto, addFieldError);
        if (isNotEmpty(trialDto.getStartDate(), trialDto.getStartDateType())) {
            enforceRuleForStartDate(trialDto, addFieldError, startDateFieldName);
        }
        if (isNotEmpty(trialDto.getPrimaryCompletionDate(), trialDto.getPrimaryCompletionDateType())) {
            enforceRuleForPrimaryCompletionDate(trialDto, addFieldError);
        }
        enforceRuleForStatusActive(trialDto, addFieldError, startDateFieldName);
        enforceRuleForStatusApproved(trialDto, addFieldError);
        enforceRuleForStatusCompletedAndPrimaryCompletionDate(trialDto, addFieldError);
        if (isNotEmpty(trialDto.getStatusCode(), trialDto.getPrimaryCompletionDateType())) {
             enforceRuleForStatusCompletedAndPrimaryCompletionType(trialDto, addFieldError);
        }
        enforceRuleForStartDateAndPrimaryCompletionDate(trialDto, addFieldError, startDateFieldName);
        return addFieldError;
    }
    /**
     * Constraint/Rule: 18 Current Trial Status Date must be current or past.
     */
    private void enforceRuleStatusDate(TrialDTO trialDto, Map<String, String> addFieldError) {
        if (StringUtils.isNotEmpty(trialDto.getStatusDate())) {
            Timestamp statusDate = PAUtil.dateStringToTimestamp(trialDto.getStatusDate());
            Timestamp currentTimeStamp = new Timestamp((new Date()).getTime());
            if (currentTimeStamp.before(statusDate)) {
                addFieldError.put("trialDTO.statusDate", getText("error.submit.invalidStatusDate"));
            }
        }
    }

    /**
     * Constraint/Rule: 23 If Current Trial Status is 'Completed', Primary Completion Date must be the same as Current
     * Trial Status Date and have 'actual' type.
     */
    private void enforceRuleForStatusCompletedAndPrimaryCompletionDate(TrialDTO trialDto,
            Map<String, String> addFieldError) {
        if (isNotEmpty(trialDto.getStatusCode(), trialDto.getStatusDate())
                && isNotEmpty(trialDto.getPrimaryCompletionDate(), trialDto.getPrimaryCompletionDateType())
                && StudyStatusCode.COMPLETE.getCode().equals(trialDto.getStatusCode())
                && !trialDto.getPrimaryCompletionDateType().equals(ActualAnticipatedTypeCode.ACTUAL.getCode())) {
            addFieldError.put(PRIMARY_COMPLETION_DATE, getText("error.submit.invalidPrimaryCompletionDate"));
        }
    }

    private boolean isNotEmpty(String code, String date) {
        return StringUtils.isNotEmpty(code) && StringUtils.isNotBlank(date);
    }
    /**
     * Constraint/Rule:  21 If Current Trial Status is 'Active', Trial Start Date must be the same as
     * Current Trial Status Date and have 'actual' type.
     * pa2.0 release adding Trial Start date is smaller or same Current Trial Status Date.
     */
    private void enforceRuleForStatusActive(TrialDTO trialDto, Map<String, String> addFieldError,
       String startDateFieldName) {
        if (isNotEmpty(trialDto.getStatusCode(), trialDto.getStatusDate())
            && isNotEmpty(trialDto.getStartDate(), trialDto.getStartDateType())
            && StudyStatusCode.ACTIVE.getCode().equals(trialDto.getStatusCode())) {
              Timestamp statusDate = PAUtil.dateStringToTimestamp(trialDto.getStatusDate());
              Timestamp trialStartDate = PAUtil.dateStringToTimestamp(trialDto.getStartDate());
              if (trialStartDate.after(statusDate) || !trialDto.getStartDateType().equals(
                                  ActualAnticipatedTypeCode.ACTUAL.getCode())) {
                  addFieldError.put(startDateFieldName, getText("error.submit.invalidStartDate"));
              }
          }
    }
    /**
     * Constraint/Rule: 19 Trial Start Date must be current/past if 'actual' trial start date type
     * is selected and must be future if 'anticipated' trial start date type is selected.
     */
    private void enforceRuleForStartDate(TrialDTO trialDto, Map<String, String> addFieldError,
         String startDateFieldName) {
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
                  addFieldError.put(startDateFieldName, getText("error.submit.invalidAnticipatedStartDate"));
              }
         }
    }

    /**
     * Constraint/Rule: 20 Primary Completion Date must be current/past if 'actual' primary completion date type is
     * selected and must be future if 'anticipated' trial primary completion date type is selected.
     */
    private void enforceRuleForPrimaryCompletionDate(TrialDTO trialDto, Map<String, String> addFieldError) {
        Timestamp primaryCompletionDate = PAUtil.dateStringToTimestamp(trialDto.getPrimaryCompletionDate());
        Timestamp currentTimeStamp = new Timestamp((new Date()).getTime());
        if (trialDto.getPrimaryCompletionDateType().equals(ActualAnticipatedTypeCode.ACTUAL.getCode())) {
            if (currentTimeStamp.before(primaryCompletionDate)) {
                addFieldError.put(PRIMARY_COMPLETION_DATE, getText("error.submit.invalidActualPrimaryCompletionDate"));
            }
        } else if (trialDto.getPrimaryCompletionDateType().equals(ActualAnticipatedTypeCode.ANTICIPATED.getCode())
                   && currentTimeStamp.after(primaryCompletionDate)) {
                addFieldError.put(PRIMARY_COMPLETION_DATE,
                                  getText("error.submit.invalidAnticipatedPrimaryCompletionDate"));
        }
    }

    /**
     * Constraint/Rule: 22 If Current Trial Status is 'Approved', Trial Start Date must have 'anticipated' type. Trial
     * Start Date must have 'actual' type for any other Current Trial Status value besides 'Approved'.
     */
    private void enforceRuleForStatusApproved(TrialDTO trialDto, Map<String, String> addFieldError) {
        if (StringUtils.isNotEmpty(trialDto.getStatusCode()) && StringUtils.isNotEmpty(trialDto.getStartDateType())) {
            Set<String> statusCode = new HashSet<String>();
            statusCode.add(StudyStatusCode.APPROVED.getCode());
            statusCode.add(StudyStatusCode.IN_REVIEW.getCode());
            statusCode.add(StudyStatusCode.WITHDRAWN.getCode());
            if (statusCode.contains(trialDto.getStatusCode())) {
                if (!trialDto.getStartDateType().equals(ActualAnticipatedTypeCode.ANTICIPATED.getCode())) {
                    addFieldError.put("trialDTO.startDateType", getText("error.submit.invalidStartDateTypeApproved"));
                }
            } else {
                if (!trialDto.getStartDateType().equals(ActualAnticipatedTypeCode.ACTUAL.getCode())) {
                    addFieldError.put("trialDTO.startDateType", getText("error.submit.invalidStartDateTypeOther"));
                }
            }
        }
    }

    /**
     * Constraint/Rule: 24 If Current Trial Status is 'Completed' or 'Administratively Completed', Primary Completion
     * Date must have 'actual' type. Primary Completion Date must have 'anticipated' type for any other Current Trial
     * Status value besides 'Completed' or 'Administratively Completed'.
     */
    private void enforceRuleForStatusCompletedAndPrimaryCompletionType(TrialDTO trialDto,
            Map<String, String> addFieldError) {
        Set<String> statusCode = new HashSet<String>();
        statusCode.add(StudyStatusCode.COMPLETE.getCode());
        statusCode.add(StudyStatusCode.ADMINISTRATIVELY_COMPLETE.getCode());
        if (statusCode.contains(trialDto.getStatusCode())) {
            if (!trialDto.getPrimaryCompletionDateType().equals(ActualAnticipatedTypeCode.ACTUAL.getCode())) {
                addFieldError.put(PRIMARY_COMPLETION_DATE_TYPE,
                                  getText("error.submit.invalidPrimaryCompletionDateType"));
            }
        } else {
            if (StringUtils.isNotEmpty(trialDto.getPrimaryCompletionDateType())
                && !trialDto.getPrimaryCompletionDateType().equals(ActualAnticipatedTypeCode.ANTICIPATED.getCode())) {
                addFieldError.put(PRIMARY_COMPLETION_DATE_TYPE,
                                  getText("error.submit.invalidPrimaryCompletionDateTypeOther"));
            }
        }
    }

    /**
     * Constraint/Rule:25 Trial Start Date must be same/smaller than Primary Completion Date.
     */
    private void enforceRuleForStartDateAndPrimaryCompletionDate(TrialDTO trialDto, Map<String, String> addFieldError,
            String startDateFieldName) {
        if (StringUtils.isNotEmpty(trialDto.getStartDate())
                && StringUtils.isNotEmpty(trialDto.getPrimaryCompletionDate())) {
            Timestamp startDate = PAUtil.dateStringToTimestamp(trialDto.getStartDate());
            Timestamp primaryCompletionDate = PAUtil.dateStringToTimestamp(trialDto.getPrimaryCompletionDate());
            if (primaryCompletionDate.before(startDate)) {
                addFieldError.put(startDateFieldName, getText("error.submit.invalidTrialDates"));
            }
        }
    }
}
