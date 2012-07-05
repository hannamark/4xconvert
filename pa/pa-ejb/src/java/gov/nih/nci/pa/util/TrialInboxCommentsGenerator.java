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

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.dto.AbstractionCompletionDTO;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.DocumentWorkflowStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.service.DocumentWorkflowStatusServiceLocal;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyIndldeServiceLocal;
import gov.nih.nci.pa.service.StudyOverallStatusServiceLocal;
import gov.nih.nci.pa.service.StudyProtocolService;
import gov.nih.nci.pa.service.StudyResourcingServiceLocal;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceLocal;
import gov.nih.nci.pa.service.util.AbstractionCompletionServiceRemote;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

/**
 * Helper class for Generation of inbox processing comments.
 *
 * @author kkanchinadam
 */
@SuppressWarnings("PMD.TooManyMethods")
public class TrialInboxCommentsGenerator {
    
    private static final String SPACE = " ";
    private static final String IRB_DOCUMENT_UPDATED = "IRB Document was updated.";
    private static final String PARTICIPATING_DOCUMENT_UPDATED = "Participating Document was updated.";
    private static final String STATUS_DATES_UPDATED = "Status & Dates were updated.";
    private static final String RECRUITMENT_STATUS_DATE_UPDATED = "Participating Sites Recruitment Status "
            + "and Date was updated.";
    private static final String IND_IDE_UPDATED = "Ind Ide was updated.";
    private static final String GRANT_INFORMATION_UPDATED = "Grant information was updated.";
    private static final String IDENTIFIERS_ADDED = "New identifier(s) were added.";
    
    private AbstractionCompletionServiceRemote abstractionCompletionService;
    private DocumentWorkflowStatusServiceLocal documentWorkFlowStatusService;
    private StudyIndldeServiceLocal studyIndldeService;
    private StudyOverallStatusServiceLocal studyOverallStatusService;
    private StudyResourcingServiceLocal studyResourcingService;
    private StudySiteAccrualStatusServiceLocal studySiteAccrualStatusService;
    private StudyProtocolService studyProtocolService;
    private List<String> inboxProcessingComments = new ArrayList<String>(); 
    
    /**
     * Default constructor.
     */
    public TrialInboxCommentsGenerator() {
        super();
    }
    
    /**
     * Constructor.
     * @param documentWorkFlowStatusService document workflow status service
     * @param abstractionCompletionService abstraction completion service
     * @param studyOverallStatusService study overall status service
     * @param studySiteAccrualStatusService study site accrual status service
     * @param studyIndldeService study ind ide service
     * @param studyResourcingService study resourcing service
     * @param protocolService StudyProtocolService
     */
    @SuppressWarnings("PMD.ExcessiveParameterList")
    public TrialInboxCommentsGenerator(DocumentWorkflowStatusServiceLocal documentWorkFlowStatusService,
            AbstractionCompletionServiceRemote abstractionCompletionService,
            StudyOverallStatusServiceLocal studyOverallStatusService,
            StudySiteAccrualStatusServiceLocal studySiteAccrualStatusService,
            StudyIndldeServiceLocal studyIndldeService, StudyResourcingServiceLocal studyResourcingService, 
            StudyProtocolService protocolService) {
        this.documentWorkFlowStatusService = documentWorkFlowStatusService;
        this.abstractionCompletionService = abstractionCompletionService;
        this.studyOverallStatusService = studyOverallStatusService;
        this.studySiteAccrualStatusService = studySiteAccrualStatusService;
        this.studyIndldeService = studyIndldeService;
        this.studyResourcingService = studyResourcingService;
        this.studyProtocolService = protocolService;
    }

    /**
     * Checks all the modifications of a study protocol.
     * @param studyProtocolDTO study protocol dto
     * @param documentDTOs document dto
     * @param overallStatusDTO overall status dto
     * @param participatingSites participating sites dtos
     * @param studyIndIdeDTOs study Ind Ide dtos
     * @param studyResourcingDTOs study resourcing dtos
     * @param originalSecondaryIDs originalSecondaryIDs
     * @throws PAException the exception
     */
    @SuppressWarnings("PMD.ExcessiveParameterList")
    public void checkForInboxProcessingComments(StudyProtocolDTO studyProtocolDTO, List<DocumentDTO> documentDTOs,
            StudyOverallStatusDTO overallStatusDTO, List<StudySiteAccrualStatusDTO> participatingSites,
            List<StudyIndldeDTO> studyIndIdeDTOs, List<StudyResourcingDTO> studyResourcingDTOs, 
            Set<Ii> originalSecondaryIDs) throws PAException {
        inboxProcessingComments = new ArrayList<String>();
        checkDocumentUpdates(documentDTOs, DocumentTypeCode.IRB_APPROVAL_DOCUMENT, IRB_DOCUMENT_UPDATED);
        checkDocumentUpdates(documentDTOs, DocumentTypeCode.PARTICIPATING_SITES, PARTICIPATING_DOCUMENT_UPDATED);
        checkTrialUpdateForReview(studyProtocolDTO);
        checkOtherIdentifiers(studyProtocolDTO, originalSecondaryIDs);
        checkStatusDatesUpdates(studyProtocolDTO, overallStatusDTO);
        checkParticipatingSitesRecruitmentStatusDate(participatingSites);
        checkIndIdeUpdates(studyIndIdeDTOs);
        checkGrantUpdates(studyResourcingDTOs);
    }

    private void checkOtherIdentifiers(StudyProtocolDTO studyProtocolDTO,
            Set<Ii> originalSecondaryIDs) throws PAException {
        int sizeBefore = originalSecondaryIDs.size();
        int sizeAfter = ISOUtil.isDSetNotEmpty(studyProtocolDTO
                .getSecondaryIdentifiers()) ? studyProtocolDTO
                .getSecondaryIdentifiers().getItem().size() : 0;
        if (sizeBefore != sizeAfter) {
            inboxProcessingComments.add(IDENTIFIERS_ADDED);
        }

    }

    /**
     * @return the inbox comments.
     */
    public String getInboxProcessingComments() {
        StringBuilder sb = new StringBuilder();
        for (String comment : inboxProcessingComments) {
            sb.append(comment).append(SPACE);
        }
        return sb.toString();
    }

    /**
     * Check the document updates.
     * @param documentDTOs The document dtos.
     * @param docTypeCode The document type to check
     * @param comment The update comment
     */
    void checkDocumentUpdates(List<DocumentDTO> documentDTOs, DocumentTypeCode docTypeCode, String comment) {
        if (CollectionUtils.isNotEmpty(documentDTOs)) {
            String codeString = docTypeCode.getCode();
            for (DocumentDTO doc : documentDTOs) {
                if (codeString.equals(CdConverter.convertCdToString(doc.getTypeCode()))) {
                    inboxProcessingComments.add(comment);
                    return;
                }
            }
        }
    }
    
    /**
     * Validates the abstraction completion.
     * @param studyProtocolDTO The study protocol dto
     * @throws PAException if an error occurs
     */
    void checkTrialUpdateForReview(StudyProtocolDTO studyProtocolDTO) throws PAException {
        Ii spIi = studyProtocolDTO.getIdentifier();
        DocumentWorkflowStatusDTO isoDocWrkStatus = documentWorkFlowStatusService.getCurrentByStudyProtocol(spIi);
        DocumentWorkflowStatusCode statusCode =
                CdConverter.convertCdToEnum(DocumentWorkflowStatusCode.class, isoDocWrkStatus.getStatusCode());
        if (statusCode != null && statusCode.isAbstractedOrAbove()) {
            List<AbstractionCompletionDTO> errorList = abstractionCompletionService.validateAbstractionCompletion(spIi);
            if (!errorList.isEmpty()) {
                StringBuilder sbuf = new StringBuilder();
                sbuf.append("<b>Type :</b>  <b>Description :</b> <b>Comments :</b><br>");
                for (AbstractionCompletionDTO abDTO : errorList) {
                    sbuf.append(abDTO.getErrorType()).append(':').append(abDTO.getErrorDescription()).append(':')
                        .append(abDTO.getComment()).append("<br>");
                }
                inboxProcessingComments.add(sbuf.toString());
            }
        }
    }

    /**
     * Check the status dates updates.
     * @param studyProtocolDTO The study protocol dto
     * @param overallStatusDTO The overall status dto
     * @throws PAException if an error occurs
     */
    void checkStatusDatesUpdates(StudyProtocolDTO studyProtocolDTO, StudyOverallStatusDTO overallStatusDTO)
            throws PAException {
        if (studyOverallStatusService.isTrialStatusOrDateChanged(overallStatusDTO, studyProtocolDTO.getIdentifier())) {
            inboxProcessingComments.add(STATUS_DATES_UPDATED);
        }
    }

    /**
     * Checks the participating sites updates.
     * @param participatingSites The participating sites
     * @throws PAException if an error occurs
     */
    void checkParticipatingSitesRecruitmentStatusDate(List<StudySiteAccrualStatusDTO> participatingSites)
            throws PAException {
        if (CollectionUtils.isNotEmpty(participatingSites)) {
            for (StudySiteAccrualStatusDTO ssDto : participatingSites) {
                StudySiteAccrualStatusDTO dto =
                        studySiteAccrualStatusService.getCurrentStudySiteAccrualStatusByStudySite(ssDto
                            .getStudySiteIi());
                if (isParticipatingSiteUpdated(dto, ssDto)) {
                    inboxProcessingComments.add(RECRUITMENT_STATUS_DATE_UPDATED);
                    return;
                }
            }
        }
    }

    /**
     * Test if a participating site has been updated.
     * @param existing The existing participating site
     * @param updated The possibly updated participating site
     * @return true if the participating site has been updated
     */
    boolean isParticipatingSiteUpdated(StudySiteAccrualStatusDTO existing, StudySiteAccrualStatusDTO updated) {
        return existing != null
                && (!existing.getStatusCode().equals(updated.getStatusCode()) || !existing.getStatusDate()
                    .equals(updated.getStatusDate()));
    }

    /**
     * checks the indide updates.
     * @param studyIndIdeDTOs the study indide dtos
     * @throws PAException if an error occurs
     */
    void checkIndIdeUpdates(List<StudyIndldeDTO> studyIndIdeDTOs) throws PAException {
        if (CollectionUtils.isNotEmpty(studyIndIdeDTOs)) {
            for (StudyIndldeDTO indIdeDto : studyIndIdeDTOs) {
                if (ISOUtil.isIiNull(indIdeDto.getIdentifier())) {
                    inboxProcessingComments.add(IND_IDE_UPDATED);
                    return;
                }
                StudyIndldeDTO dto = studyIndldeService.get(indIdeDto.getIdentifier());
                if (isIndUpdated(dto, indIdeDto)) {
                    inboxProcessingComments.add(IND_IDE_UPDATED);
                    return;
                }
            }
        }
    }

    /**
     * test if a StudyIndldeDTO has been updated.
     * @param existing The existing StudyIndldeDTO
     * @param updated The possibly updated StudyIndldeDTO
     * @return true if the StudyIndldeDTO has been updated.
     */
    boolean isIndUpdated(StudyIndldeDTO existing, StudyIndldeDTO updated) {
        return !(existing.getIndldeTypeCode().getCode().equals(updated.getIndldeTypeCode().getCode()))
                || !(existing.getIndldeNumber().getValue().equals(updated.getIndldeNumber().getValue()))
                || !(existing.getGrantorCode().getCode().equals(updated.getGrantorCode().getCode()));
    }

    /**
     * Checks the grant updates.
     * @param studyResourcingDTOs The studyResourcingDTOs
     * @throws PAException if an error occurs
     */
    void checkGrantUpdates(List<StudyResourcingDTO> studyResourcingDTOs) throws PAException {
        if (CollectionUtils.isNotEmpty(studyResourcingDTOs)) {
            for (StudyResourcingDTO grantDto : studyResourcingDTOs) {
                if (ISOUtil.isIiNull(grantDto.getIdentifier())) {
                    inboxProcessingComments.add(GRANT_INFORMATION_UPDATED);
                    return;
                }
                StudyResourcingDTO dto = studyResourcingService.get(grantDto.getIdentifier());
                if (isGrantUpdated(dto, grantDto)) {
                    inboxProcessingComments.add(GRANT_INFORMATION_UPDATED);
                    return;
                }
            }
        }
    }

    /**
     * Test if a grant has been updated.
     * @param existing The existing grant
     * @param updated The possibly updated grant
     * @return true if the grant has been updated
     */
    boolean isGrantUpdated(StudyResourcingDTO existing, StudyResourcingDTO updated) {
        return !(existing.getFundingMechanismCode().getCode().equals(updated.getFundingMechanismCode().getCode()))
                || !(existing.getNihInstitutionCode().getCode().equals(updated.getNihInstitutionCode().getCode()))
                || !(existing.getSerialNumber().getValue().equals(updated.getSerialNumber().getValue()))
                || !(existing.getNciDivisionProgramCode().getCode().equals(updated.getNciDivisionProgramCode()
                    .getCode()));
    }

    /**
     * @param abstractionCompletionService the abstractionCompletionService to set
     */
    public void setAbstractionCompletionService(AbstractionCompletionServiceRemote abstractionCompletionService) {
        this.abstractionCompletionService = abstractionCompletionService;
    }

    /**
     * @param documentWorkFlowStatusService the documentWorkFlowStatusService to set
     */
    public void setDocumentWorkFlowStatusService(DocumentWorkflowStatusServiceLocal documentWorkFlowStatusService) {
        this.documentWorkFlowStatusService = documentWorkFlowStatusService;
    }

    /**
     * @param studyIndldeService the studyIndldeService to set
     */
    public void setStudyIndldeService(StudyIndldeServiceLocal studyIndldeService) {
        this.studyIndldeService = studyIndldeService;
    }

    /**
     * @param studyOverallStatusService the studyOverallStatusService to set
     */
    public void setStudyOverallStatusService(StudyOverallStatusServiceLocal studyOverallStatusService) {
        this.studyOverallStatusService = studyOverallStatusService;
    }

    /**
     * @param studyResourcingService the studyResourcingService to set
     */
    public void setStudyResourcingService(StudyResourcingServiceLocal studyResourcingService) {
        this.studyResourcingService = studyResourcingService;
    }

    /**
     * @param studySiteAccrualStatusService the studySiteAccrualStatusService to set
     */
    public void setStudySiteAccrualStatusService(StudySiteAccrualStatusServiceLocal studySiteAccrualStatusService) {
        this.studySiteAccrualStatusService = studySiteAccrualStatusService;
    }

}
