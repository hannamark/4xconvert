/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The pa
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This pa Software License (the License) is between NCI and You. You (or
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
 * its rights in the pa Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the pa Software; (ii) distribute and
 * have distributed to and by third parties the pa Software and any
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

package gov.nih.nci.pa.util;

import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.pa.dto.AbstractionCompletionDTO;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.DocumentWorkflowStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.service.DocumentWorkflowStatusServiceLocal;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyIndldeServiceLocal;
import gov.nih.nci.pa.service.StudyOverallStatusServiceLocal;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.StudyResourcingServiceLocal;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceLocal;
import gov.nih.nci.pa.service.exception.PAValidationException;
import gov.nih.nci.pa.service.util.AbstractionCompletionServiceRemote;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Helper class for TrialRegistrationBeanLocal.
 *
 * @author kkanchinadam
 */

public class TrialRegistrationHelper {
    private final List<String> inboxProcessingComments = new ArrayList<String>();
    private final DocumentWorkflowStatusServiceLocal docWrkFlowStatusService;
    private final AbstractionCompletionServiceRemote abstractionCompletionService;
    private final StudyProtocolServiceLocal studyProtocolService;
    private final StudyOverallStatusServiceLocal studyOverallStatusService;
    private final StudySiteAccrualStatusServiceLocal studySiteAccrualStatusService;
    private final StudyIndldeServiceLocal studyIndldeService;
    private final StudyResourcingServiceLocal studyResourcingService;

    private static final String SPACE = " ";
    private static final String IRB_DOCUMENT_UPDATED = "IRB Document was updated.";
    private static final String PARTICIPATING_DOCUMENT_UPDATED = "Participating Document was updated.";
    private static final String PRIMARY_PURPOSE_UPDATED = "Primary Purpose was updated.";
    private static final String STATUS_DATES_UPDATED = "Status & Dates were updated.";
    private static final String RECRUITMENT_STATUS_DATE_UPDATED = "Participating Sites Recruitment Status "
            + "and Date was updated.";
    private static final String IND_IDE_UPDATED = "Ind Ide was updated.";
    private static final String GRANT_INFORMATION_UPDATED = "Grant information was updated.";
    /**
     * Validation exception start text.
     */
    public static final String VALIDATION_EXCEPTION = "Validation Exception ";
    /**
     * Email validation.
     */
    private static final String EMAIL_NOT_NULL = "Email cannot be null, ";
    /**
     * Phone validation.
     */
    private static final String PHONE_NOT_NULL = "Phone cannot be null, ";

    /**
     * @param docWrkFlowStatusService document workflow status service
     * @param abstractionCompletionService abstraction completion service
     * @param studyProtocolService study protocol service
     * @param studyOverallStatusService study overall status service
     * @param studySiteAccrualStatusService study site accrual status service
     * @param studyIndldeService study ind ide service
     * @param studyResourcingService study resourcing service
     */
    public TrialRegistrationHelper(DocumentWorkflowStatusServiceLocal docWrkFlowStatusService,
            AbstractionCompletionServiceRemote abstractionCompletionService,
            StudyProtocolServiceLocal studyProtocolService, StudyOverallStatusServiceLocal studyOverallStatusService,
            StudySiteAccrualStatusServiceLocal studySiteAccrualStatusService,
            StudyIndldeServiceLocal studyIndldeService, StudyResourcingServiceLocal studyResourcingService) {
        super();
        this.docWrkFlowStatusService = docWrkFlowStatusService;
        this.abstractionCompletionService = abstractionCompletionService;
        this.studyProtocolService = studyProtocolService;
        this.studyOverallStatusService = studyOverallStatusService;
        this.studySiteAccrualStatusService = studySiteAccrualStatusService;
        this.studyIndldeService = studyIndldeService;
        this.studyResourcingService = studyResourcingService;
    }

    /**
     *
     * @param studyProtocolDTO study protocol dto
     * @param documentDTOs document dto
     * @param overallStatusDTO overall status dto
     * @param participatingSites participating sites dtos
     * @param studyIndIdeDTOs study Ind Ide dtos
     * @param studyResourcingDTOs study resourcing dtos
     * @throws PAException the exception
     */
    public void checkForInboxProcessingComments(StudyProtocolDTO studyProtocolDTO,
            List<DocumentDTO> documentDTOs, StudyOverallStatusDTO overallStatusDTO,
            List<StudySiteAccrualStatusDTO> participatingSites, List<StudyIndldeDTO> studyIndIdeDTOs,
            List<StudyResourcingDTO> studyResourcingDTOs)
            throws PAException {
        inboxProcessingComments.clear();
        checkIrbDocUpdates(documentDTOs);
        checkParticipatingDocUpdates(documentDTOs);
        checkTrialUpdateForReview(studyProtocolDTO);
        checkPrimaryPurposeUpdates(studyProtocolDTO);
        checkStatusDatesUpdates(studyProtocolDTO, overallStatusDTO);
        checkParticipatingSitesRecruitmentStatusDate(participatingSites);
        checkIndIdeUpdates(studyIndIdeDTOs);
        checkGrantUpdates(studyResourcingDTOs);
    }

    /**
     * @return the inbox comments.
     */
    public String getInboxProcessingComments() {
        StringBuffer sbuf = new StringBuffer();
        for (String comment : inboxProcessingComments) {
            sbuf.append(comment).append(SPACE);
        }
        return sbuf.toString();
    }

    private void checkIrbDocUpdates(List<DocumentDTO> documentDTOs) {
        if (CollectionUtils.isNotEmpty(documentDTOs)) {
            for (DocumentDTO doc : documentDTOs) {
                if (DocumentTypeCode.IRB_APPROVAL_DOCUMENT.getCode().equals(
                        CdConverter.convertCdToString(doc.getTypeCode()))) {
                    inboxProcessingComments.add(IRB_DOCUMENT_UPDATED);
                    return;
                }
            }
        }
    }

    private void checkParticipatingDocUpdates(List<DocumentDTO> documentDTOs) {
        if (CollectionUtils.isNotEmpty(documentDTOs)) {
            for (DocumentDTO doc : documentDTOs) {
                if (DocumentTypeCode.PARTICIPATING_SITES.getCode().equals(
                        CdConverter.convertCdToString(doc.getTypeCode()))) {
                    inboxProcessingComments.add(PARTICIPATING_DOCUMENT_UPDATED);
                    return;
                }
            }
        }
    }

    private void checkTrialUpdateForReview(StudyProtocolDTO studyProtocolDTO) throws PAException {
        DocumentWorkflowStatusDTO isoDocWrkStatus = docWrkFlowStatusService.getCurrentByStudyProtocol(studyProtocolDTO
                .getIdentifier());
        if (PAUtil.isAbstractedAndAbove(isoDocWrkStatus.getStatusCode())) {
            List<AbstractionCompletionDTO> errorList = abstractionCompletionService
                    .validateAbstractionCompletion(studyProtocolDTO.getIdentifier());
            if (!errorList.isEmpty()) {
                StringBuffer sbuf = new StringBuffer();
                sbuf.append("<b>Type :</b>  <b>Description :</b> <b>Comments :</b><br>");
                for (AbstractionCompletionDTO abDTO : errorList) {
                    sbuf.append(abDTO.getErrorType()).append(':').append(abDTO.getErrorDescription()).append(':')
                            .append(abDTO.getComment()).append("<br>");
                }
                inboxProcessingComments.add(sbuf.toString());
            }
        }
    }

    private void checkPrimaryPurposeUpdates(StudyProtocolDTO studyProtocolDTO) throws PAException {
        StudyProtocolDTO spDTO = studyProtocolService.getStudyProtocol(studyProtocolDTO.getIdentifier());
        if (!studyProtocolDTO.getPrimaryPurposeCode().equals(spDTO.getPrimaryPurposeCode())) {
            inboxProcessingComments.add(PRIMARY_PURPOSE_UPDATED);
        }
    }

    private void checkStatusDatesUpdates(StudyProtocolDTO studyProtocolDTO,
            StudyOverallStatusDTO overallStatusDTO) throws PAException {
        if (studyOverallStatusService.isTrialStatusOrDateChanged(overallStatusDTO, studyProtocolDTO.getIdentifier())) {
            inboxProcessingComments.add(STATUS_DATES_UPDATED);
        }
    }

    private void checkParticipatingSitesRecruitmentStatusDate(List<StudySiteAccrualStatusDTO> participatingSites)
        throws PAException {
        if (CollectionUtils.isNotEmpty(participatingSites)) {
            for (StudySiteAccrualStatusDTO ssDto : participatingSites) {
                StudySiteAccrualStatusDTO dto = studySiteAccrualStatusService
                        .getCurrentStudySiteAccrualStatusByStudySite(ssDto.getStudySiteIi());
                if (!ssDto.getStatusCode().equals(dto.getStatusCode())
                        || !ssDto.getStatusDate().equals(dto.getStatusDate())) {
                    inboxProcessingComments.add(RECRUITMENT_STATUS_DATE_UPDATED);
                    return;
                }
            }
        }
    }

    private void checkIndIdeUpdates(List<StudyIndldeDTO> studyIndIdeDTOs) throws PAException {
        if (CollectionUtils.isNotEmpty(studyIndIdeDTOs)) {
            for (StudyIndldeDTO indIdeDto : studyIndIdeDTOs) {
                if (PAUtil.isIiNull(indIdeDto.getIdentifier())) {
                    inboxProcessingComments.add(IND_IDE_UPDATED);
                    return;
                }
                StudyIndldeDTO dto = studyIndldeService.get(indIdeDto.getIdentifier());
                if (isIndUpdated(indIdeDto, dto)) {
                    inboxProcessingComments.add(IND_IDE_UPDATED);
                    return;
                }
            }
        }
    }

    /**
     * @param indIdeDto
     * @param dto
     * @return
     */
    private boolean isIndUpdated(StudyIndldeDTO indIdeDto, StudyIndldeDTO dto) {
        return !(indIdeDto.getIndldeTypeCode().getCode().equals(dto.getIndldeTypeCode().getCode()))
                || !(indIdeDto.getIndldeNumber().getValue().equals(dto.getIndldeNumber().getValue()))
                || !(indIdeDto.getGrantorCode().getCode().equals(dto.getGrantorCode().getCode()));
    }

    private void checkGrantUpdates(List<StudyResourcingDTO> studyResourcingDTOs) throws PAException {
        if (CollectionUtils.isNotEmpty(studyResourcingDTOs)) {
            for (StudyResourcingDTO grantDto : studyResourcingDTOs) {
                if (PAUtil.isIiNull(grantDto.getIdentifier())) {
                    inboxProcessingComments.add(GRANT_INFORMATION_UPDATED);
                    return;
                }
                StudyResourcingDTO dto = studyResourcingService.get(grantDto.getIdentifier());
                if (isGrantUpdated(grantDto, dto)) {
                    inboxProcessingComments.add(GRANT_INFORMATION_UPDATED);
                    return;
                }
            }
        }
    }

    private boolean isGrantUpdated(StudyResourcingDTO grantDto, StudyResourcingDTO dto) {
        return !(grantDto.getFundingMechanismCode().getCode().equals(dto.getFundingMechanismCode().getCode()))
        || !(grantDto.getNihInstitutionCode().getCode().equals(dto.getNihInstitutionCode().getCode()))
        || !(grantDto.getSerialNumber().getValue().equals(dto.getSerialNumber().getValue()))
        || !(grantDto.getNciDivisionProgramCode().getCode().equals(dto.getNciDivisionProgramCode()
                .getCode()));
    }
    /**
     * @param spDTO spDTO
     * @param summary4organizationDTO summary4organizationDTO
     * @param summary4studyResourcingDTO summary4studyResourcingDTO
     * @throws PAException on error
     */
    public void enforceSummaryFourSponsorAndCategory(StudyProtocolDTO spDTO, OrganizationDTO summary4organizationDTO,
            StudyResourcingDTO summary4studyResourcingDTO) throws PAException {
        StringBuffer sb = new StringBuffer();
        String summ4Category = null;
        // validate of null objects
        sb.append(summary4organizationDTO == null ? "Summary Four Organization DTO cannot be null, " : "");
        if (summary4studyResourcingDTO != null) {
            summ4Category = CdConverter.convertCdToString(summary4studyResourcingDTO.getTypeCode());
        } else {
            sb.append("Summary Four Study Resourcing DTO cannot be null, ");
        }
        if (StringUtils.isEmpty(summ4Category)) {
            sb.append("Summary 4 Sponsor Category cannot be null, ");
        } else {
            validateSponsorType(spDTO, sb, summ4Category);
        }
        if (sb.length() > 0) {
            throw new PAException("Validation Exception " + sb.toString());
        }
    }

    /**
     * @param spDTO
     * @param sb
     * @param summ4Category
     */
    private void validateSponsorType(StudyProtocolDTO spDTO, StringBuffer sb,
            String summ4Category) {
        if ((null == SummaryFourFundingCategoryCode.getByCode(summ4Category))
                || (BlConverter.convertToBool(spDTO.getProprietaryTrialIndicator())
                && !isSum4IndustrialSponsorType(summ4Category))
                || (!BlConverter.convertToBool(spDTO.getProprietaryTrialIndicator())
                && isSum4IndustrialSponsorType(summ4Category))) {
                sb.append("Please enter valid value for Summary 4 Sponsor Category.");
        }
    }

    /**
     * @param spDTO
     * @param summ4Category
     * @return
     */
    private boolean isSum4IndustrialSponsorType(String summ4Category) {
        return  StringUtils.equalsIgnoreCase(summ4Category, SummaryFourFundingCategoryCode.INDUSTRIAL.getCode());
    }
    /**
     * Check basic contact info as well as study contacts for PI role and site contacts for Resp Party role.
     * @param studyProtocolDTO trial
     * @param studyContactDTO  study contact
     * @param studySiteContactDTO site contact
     * @param respPartyExists does resp Party exist
     * @throws PAException when error.
     */
    public static void enforceBusinessRulesForStudyContact(StudyProtocolDTO studyProtocolDTO,
            StudyContactDTO studyContactDTO, StudySiteContactDTO studySiteContactDTO,
            boolean respPartyExists) throws PAException {
        enforceBusinessRulesForStudyContact(studyProtocolDTO, studyContactDTO, studySiteContactDTO);
        if (studyProtocolDTO.getCtgovXmlRequiredIndicator().getValue().booleanValue()) {
            checkTelecomReqsForPiAndRespParty(studyContactDTO, studySiteContactDTO, respPartyExists);
        }
    }

    /**
     * Validates the study protocol making sure needed fields are set.
     * @param studyProtocolDTO the study protocol to validate
     * @throws PAException upon validation error
     */
    public static void validateStudyProtocol(StudyProtocolDTO studyProtocolDTO) throws PAException {
        StringBuffer sb = new StringBuffer();
        if (studyProtocolDTO == null) {
            throw new PAValidationException("Study Protocol cannot be null.");
        }
        if (PAUtil.isTsNull(studyProtocolDTO.getStartDate())) {
            sb.append("Study Protocol start date cannot be null.\n");
        }
        if (PAUtil.isCdNull(studyProtocolDTO.getStartDateTypeCode())) {
            sb.append("Study Protocol start date type code cannot be null.\n");
        }
        if (PAUtil.isTsNull(studyProtocolDTO.getPrimaryCompletionDate())) {
            sb.append("Study Protocol primary completion date cannot be null.\n");
        }
        if (PAUtil.isCdNull(studyProtocolDTO.getPrimaryCompletionDateTypeCode())) {
            sb.append("Study Protocol primary completion date type code cannot be null.\n");
        }
        if (PAUtil.isBlNull(studyProtocolDTO.getCtgovXmlRequiredIndicator())) {
            sb.append("Study Protocol Ct.gov XML indicator cannot be null.");
        }
        if (sb.length() > 0) {
            throw new PAException(VALIDATION_EXCEPTION + sb.toString());
        }
    }

    private static void checkTelecomReqsForPiAndRespParty(StudyContactDTO studyContactDTO,
            StudySiteContactDTO studySiteContactDTO, boolean respPartyExists) throws PAException {
        StringBuffer sb = new StringBuffer();
        sb.append(checkTelecomReqsForPi(studyContactDTO, !respPartyExists));
        sb.append(checkTelecomReqsForRespParty(studySiteContactDTO, respPartyExists));
        if (sb.length() > 0) {
            throw new PAException(VALIDATION_EXCEPTION + sb.toString());
        }
    }

    private static String checkTelecomReqsForRespParty(StudySiteContactDTO studySiteContactDTO,
           boolean respPartyExists) {
        StringBuffer sb = new StringBuffer();
        if (respPartyExists && (studySiteContactDTO == null
                || studySiteContactDTO.getTelecomAddresses() == null
                || CollectionUtils.isEmpty(studySiteContactDTO.getTelecomAddresses().getItem()))) {
            sb.append("Telecom information must be provided for Responsible Party StudySiteContact,");
        }
        return sb.toString();
    }

    private static String checkTelecomReqsForPi(StudyContactDTO studyContactDTO, boolean piExists) {
        StringBuffer sb = new StringBuffer();
        if (piExists && (studyContactDTO == null
                || studyContactDTO.getTelecomAddresses() == null
                    || CollectionUtils.isEmpty(studyContactDTO.getTelecomAddresses().getItem()))) {
            sb.append("Telecom information must be provided for Principal Investigator StudyContact,");
        }
        return sb.toString();
    }

    /**
     * Validation for basic StudyContact business rules.
     * @param studyProtocolDTO trial
     * @param studyContactDTO contact
     * @param studySiteContactDTO site contact
     * @throws PAException when error.
     */
    public static void enforceBusinessRulesForStudyContact(StudyProtocolDTO studyProtocolDTO,
            StudyContactDTO studyContactDTO, StudySiteContactDTO studySiteContactDTO) throws PAException {
        StringBuffer sb = new StringBuffer();
        if (studyProtocolDTO.getCtgovXmlRequiredIndicator().getValue().booleanValue()) {
            sb.append(checkStudyContactEmptyTelecom(studyContactDTO, studySiteContactDTO));
            if (sb.length() > 0) {
                throw new PAException(VALIDATION_EXCEPTION + sb.toString());
            }
        }
    }

    private static String checkStudyContactEmptyTelecom(StudyContactDTO studyContactDTO,
            StudySiteContactDTO studySiteContactDTO) throws PAException {
        StringBuffer sb = new StringBuffer();
        sb.append(checkStudyOrStudySiteContactTelecom(studyContactDTO, studySiteContactDTO));
        sb.append(isAddressSet(studyContactDTO, studySiteContactDTO));
        return sb.toString();
    }

    private static String isAddressSet(StudyContactDTO studyContactDTO,
            StudySiteContactDTO studySiteContactDTO) throws PAException {
        StringBuffer sb = new StringBuffer();
        if (studyContactDTO != null) {
            sb.append(checkTelecomAddress(studyContactDTO.getTelecomAddresses(), "StudyContact"));
        }
        if (studySiteContactDTO != null) {
            sb.append(checkTelecomAddress(studySiteContactDTO.getTelecomAddresses(), "StudySiteContact"));
        }
        return sb.toString();
    }

    private static String checkStudyOrStudySiteContactTelecom(StudyContactDTO studyContactDTO,
            StudySiteContactDTO studySiteContactDTO) throws PAException {
        StringBuffer sb = new StringBuffer();
        if (studyContactDTO != null && studySiteContactDTO != null) {
            sb.append("Only one of StudyContact or StudySiteContact can be used, ");
        }
        if (studyContactDTO == null && studySiteContactDTO == null) {
            sb.append("One of StudyContact or StudySiteContact has to be used, ");
        }
        return sb.toString();
    }
    /**
     * Check for email and phone in a telecom address.
     * @param telecomAddress set of telecom info.
     * @param contactType contact type.
     * @throws PAException when error.
     * @return error string.
     */
    public static String checkTelecomAddress(DSet<Tel> telecomAddress, String contactType) throws PAException {
        StringBuffer sb = new StringBuffer();
        if (DSetConverter.getFirstElement(telecomAddress, PAConstants.EMAIL) == null) {
            sb.append(contactType).append(' ').append(EMAIL_NOT_NULL);
        }
        if (DSetConverter.getFirstElement(telecomAddress, PAConstants.PHONE) == null) {
            sb.append(contactType).append(' ').append(PHONE_NOT_NULL);
        }
        return sb.toString();
    }
}
