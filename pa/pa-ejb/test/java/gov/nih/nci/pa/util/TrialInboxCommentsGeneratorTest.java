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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.DocumentWorkflowStatusServiceLocal;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyIndldeServiceLocal;
import gov.nih.nci.pa.service.StudyOverallStatusServiceLocal;
import gov.nih.nci.pa.service.StudyResourcingServiceLocal;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceLocal;
import gov.nih.nci.pa.service.util.AbstractionCompletionServiceRemote;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.mockito.InOrder;

/**
 * @author ludetc
 *
 */
public class TrialInboxCommentsGeneratorTest {

    private static final String IRB_DOCUMENT_UPDATED = "IRB Document was updated.";
    private static final String PARTICIPATING_DOCUMENT_UPDATED = "Participating Document was updated.";
    private static final String STATUS_DATES_UPDATED = "Status & Dates were updated. ";
    private static final String RECRUITMENT_STATUS_DATE_UPDATED = "Participating Sites Recruitment Status and Date"
            + " was updated.";
    private static final String IND_IDE_UPDATED = "Ind Ide was updated.";
    private static final String GRANT_INFORMATION_UPDATED = "Grant information was updated.";

    private AbstractionCompletionServiceRemote abstractionCompletionService =
            mock(AbstractionCompletionServiceRemote.class);
    private DocumentWorkflowStatusServiceLocal documentWorkflowStatusService =
            mock(DocumentWorkflowStatusServiceLocal.class);
    private StudyIndldeServiceLocal studyIndldeService = mock(StudyIndldeServiceLocal.class);
    private StudyOverallStatusServiceLocal studyOverallStatusService = mock(StudyOverallStatusServiceLocal.class);
    private StudyResourcingServiceLocal studyResourcingService = mock(StudyResourcingServiceLocal.class);
    private StudySiteAccrualStatusServiceLocal studySiteAccrualStatusService =
            mock(StudySiteAccrualStatusServiceLocal.class);
    private TrialInboxCommentsGenerator sut;

    /**
     * Creates a real TrialInboxCommentsGenerator and inject the mock services in it.
     * @return A real TrialInboxCommentsGenerator with mock services injected.
     */
    private TrialInboxCommentsGenerator createTrialInboxCommentsGenerator() {
        TrialInboxCommentsGenerator service =
                new TrialInboxCommentsGenerator(documentWorkflowStatusService, abstractionCompletionService,
                        studyOverallStatusService, studySiteAccrualStatusService, studyIndldeService,
                        studyResourcingService);
        return service;
    }

    /**
     * Creates a mock TrialInboxCommentsGenerator and inject the mock services in it.
     * @return A mock TrialInboxCommentsGenerator with mock services injected.
     */
    private TrialInboxCommentsGenerator createTrialInboxCommentsGeneratorMock() {
        TrialInboxCommentsGenerator service = mock(TrialInboxCommentsGenerator.class);
        doCallRealMethod().when(service).setAbstractionCompletionService(abstractionCompletionService);
        doCallRealMethod().when(service).setDocumentWorkFlowStatusService(documentWorkflowStatusService);
        doCallRealMethod().when(service).setStudyIndldeService(studyIndldeService);
        doCallRealMethod().when(service).setStudyOverallStatusService(studyOverallStatusService);
        doCallRealMethod().when(service).setStudyResourcingService(studyResourcingService);
        doCallRealMethod().when(service).setStudySiteAccrualStatusService(studySiteAccrualStatusService);
        setDependencies(service);
        return service;
    }

    /**
     * Inject the mock services in the given TrialInboxCommentsGenerator.
     * @param service The TrialInboxCommentsGenerator to setup with mock services
     */
    private void setDependencies(TrialInboxCommentsGenerator service) {
        service.setAbstractionCompletionService(abstractionCompletionService);
        service.setDocumentWorkFlowStatusService(documentWorkflowStatusService);
        service.setStudyIndldeService(studyIndldeService);
        service.setStudyOverallStatusService(studyOverallStatusService);
        service.setStudyResourcingService(studyResourcingService);
        service.setStudySiteAccrualStatusService(studySiteAccrualStatusService);
    }

    /**
     * Test the checkForInboxProcessingComments method.
     * @throws PAException if an error occurs
     * 
     */
    @Test
    public void testCheckForInboxProcessingComments() throws PAException {
        sut = createTrialInboxCommentsGeneratorMock();
        StudyProtocolDTO studyProtocolDTO = new StudyProtocolDTO();
        List<DocumentDTO> documentDTOs = new ArrayList<DocumentDTO>();
        StudyOverallStatusDTO overallStatusDTO = new StudyOverallStatusDTO();
        List<StudySiteAccrualStatusDTO> participatingSites = new ArrayList<StudySiteAccrualStatusDTO>();
        List<StudyIndldeDTO> studyIndIdeDTOs = new ArrayList<StudyIndldeDTO>();
        List<StudyResourcingDTO> studyResourcingDTOs = new ArrayList<StudyResourcingDTO>();
        doCallRealMethod().when(sut).checkForInboxProcessingComments(studyProtocolDTO, documentDTOs, overallStatusDTO,
                                                                     participatingSites, studyIndIdeDTOs,
                                                                     studyResourcingDTOs);
        doCallRealMethod().when(sut).getInboxProcessingComments();
        sut.checkForInboxProcessingComments(studyProtocolDTO, documentDTOs, overallStatusDTO, participatingSites,
                                            studyIndIdeDTOs, studyResourcingDTOs);
        assertEquals("Wrong comment returned", "", sut.getInboxProcessingComments());
        InOrder inOrder = inOrder(sut);
        inOrder.verify(sut).checkDocumentUpdates(documentDTOs, DocumentTypeCode.IRB_APPROVAL_DOCUMENT,
                                                 IRB_DOCUMENT_UPDATED);
        inOrder.verify(sut).checkDocumentUpdates(documentDTOs, DocumentTypeCode.PARTICIPATING_SITES,
                                                 PARTICIPATING_DOCUMENT_UPDATED);
        inOrder.verify(sut).checkTrialUpdateForReview(studyProtocolDTO);
        inOrder.verify(sut).checkStatusDatesUpdates(studyProtocolDTO, overallStatusDTO);
        inOrder.verify(sut).checkParticipatingSitesRecruitmentStatusDate(participatingSites);
        inOrder.verify(sut).checkIndIdeUpdates(studyIndIdeDTOs);
        inOrder.verify(sut).checkGrantUpdates(studyResourcingDTOs);
    }

    /**
     * test the checkDocumentUpdates method with no document.
     */
    @Test
    public void testCheckDocumentUpdates() {
        sut = createTrialInboxCommentsGenerator();
        List<DocumentDTO> documentDTOs = new ArrayList<DocumentDTO>();
        sut.checkDocumentUpdates(documentDTOs, DocumentTypeCode.IRB_APPROVAL_DOCUMENT, "comment");
        assertEquals("Wrong comment returned", "", sut.getInboxProcessingComments());
    }

    /**
     * test the checkDocumentUpdates method with a document updated.
     */
    @Test
    public void testCheckDocumentUpdatesUpdate() {
        sut = createTrialInboxCommentsGenerator();
        List<DocumentDTO> documentDTOs = createDocumentList(DocumentTypeCode.IRB_APPROVAL_DOCUMENT);
        sut.checkDocumentUpdates(documentDTOs, DocumentTypeCode.IRB_APPROVAL_DOCUMENT, IRB_DOCUMENT_UPDATED);
        assertEquals("Wrong comment returned", IRB_DOCUMENT_UPDATED + " ", sut.getInboxProcessingComments());
    }

    /**
     * test the checkDocumentUpdates method with a document of another type.
     */
    @Test
    public void testCheckDocumentUpdatesNoUpdate() {
        sut = createTrialInboxCommentsGenerator();
        List<DocumentDTO> documentDTOs = createDocumentList(DocumentTypeCode.PARTICIPATING_SITES);
        sut.checkDocumentUpdates(documentDTOs, DocumentTypeCode.IRB_APPROVAL_DOCUMENT, IRB_DOCUMENT_UPDATED);
        assertEquals("Wrong comment returned", "", sut.getInboxProcessingComments());
    }

    /**
     * Creates a list of documents with one document of the given type.
     * @param docTypeCode The document type
     * @return a list of documents with one document of the given type.
     */
    private List<DocumentDTO> createDocumentList(DocumentTypeCode docTypeCode) {
        List<DocumentDTO> documentDTOs = new ArrayList<DocumentDTO>();
        DocumentDTO document = new DocumentDTO();
        document.setTypeCode(CdConverter.convertToCd(docTypeCode));
        documentDTOs.add(document);
        return documentDTOs;
    }

    /**
     * Test the checkTrialUpdateForReview method with no document workflow status.
     * @throws PAException if an error occurs
     */
    @Test
    public void testCheckTrialUpdateForReviewNoStatus() throws PAException {
        sut = createTrialInboxCommentsGenerator();
        StudyProtocolDTO studyProtocolDTO = setupDocumentWorkflowStatusService(null);
        sut.checkTrialUpdateForReview(studyProtocolDTO);
        assertEquals("Wrong comment returned", "", sut.getInboxProcessingComments());
        verify(documentWorkflowStatusService).getCurrentByStudyProtocol(studyProtocolDTO.getIdentifier());
    }

    /**
     * Test the checkTrialUpdateForReview method with a non abstracted document workflow status.
     * @throws PAException if an error occurs
     */
    @Test
    public void testCheckTrialUpdateForReviewNotAbstracted() throws PAException {
        sut = createTrialInboxCommentsGenerator();
        StudyProtocolDTO studyProtocolDTO = setupDocumentWorkflowStatusService(DocumentWorkflowStatusCode.SUBMITTED);
        sut.checkTrialUpdateForReview(studyProtocolDTO);
        assertEquals("Wrong comment returned", "", sut.getInboxProcessingComments());
        verify(documentWorkflowStatusService).getCurrentByStudyProtocol(studyProtocolDTO.getIdentifier());
    }

    /**
     * Test the checkTrialUpdateForReview method with a abstracted document workflow status and no abstraction
     * validation error.
     * @throws PAException if an error occurs
     */
    @Test
    public void testCheckTrialUpdateForReviewAbstractedNoError() throws PAException {
        sut = createTrialInboxCommentsGenerator();
        StudyProtocolDTO studyProtocolDTO = setupDocumentWorkflowStatusService(DocumentWorkflowStatusCode.ABSTRACTED);
        sut.checkTrialUpdateForReview(studyProtocolDTO);
        assertEquals("Wrong comment returned", "", sut.getInboxProcessingComments());
        verify(documentWorkflowStatusService).getCurrentByStudyProtocol(studyProtocolDTO.getIdentifier());
        verify(abstractionCompletionService).validateAbstractionCompletion(studyProtocolDTO.getIdentifier());
    }

    /**
     * Test the checkTrialUpdateForReview method with a abstracted document workflow status and abstraction validation
     * error.
     * @throws PAException if an error occurs
     */
    @Test
    public void testCheckTrialUpdateForReviewAbstractedError() throws PAException {
        sut = createTrialInboxCommentsGenerator();
        StudyProtocolDTO studyProtocolDTO = setupDocumentWorkflowStatusService(DocumentWorkflowStatusCode.ABSTRACTED);
        List<AbstractionCompletionDTO> errors = createErrorList();
        when(abstractionCompletionService.validateAbstractionCompletion(studyProtocolDTO.getIdentifier()))
            .thenReturn(errors);
        sut.checkTrialUpdateForReview(studyProtocolDTO);
        assertEquals("Wrong comment returned", "<b>Type :</b>  <b>Description :</b> <b>Comments :</b><br>"
                + "errorType:errorDescription:errorComment<br> ", sut.getInboxProcessingComments());
        verify(documentWorkflowStatusService).getCurrentByStudyProtocol(studyProtocolDTO.getIdentifier());
        verify(abstractionCompletionService).validateAbstractionCompletion(studyProtocolDTO.getIdentifier());
    }

    private StudyProtocolDTO setupDocumentWorkflowStatusService(DocumentWorkflowStatusCode dwfs) throws PAException {
        StudyProtocolDTO studyProtocolDTO = createStudyProtocolDTO();
        DocumentWorkflowStatusDTO documentWorkflowStatusDTO = new DocumentWorkflowStatusDTO();
        if (dwfs != null) {
            documentWorkflowStatusDTO.setStatusCode(CdConverter.convertToCd(dwfs));
        }
        when(documentWorkflowStatusService.getCurrentByStudyProtocol(studyProtocolDTO.getIdentifier()))
            .thenReturn(documentWorkflowStatusDTO);
        return studyProtocolDTO;
    }

    private StudyProtocolDTO createStudyProtocolDTO() {
        StudyProtocolDTO studyProtocolDTO = new StudyProtocolDTO();
        studyProtocolDTO.setIdentifier(IiConverter.convertToStudyProtocolIi(1L));
        return studyProtocolDTO;
    }

    private List<AbstractionCompletionDTO> createErrorList() {
        List<AbstractionCompletionDTO> errors = new ArrayList<AbstractionCompletionDTO>();
        AbstractionCompletionDTO error = new AbstractionCompletionDTO();
        error.setErrorType("errorType");
        error.setErrorDescription("errorDescription");
        error.setComment("errorComment");
        errors.add(error);
        return errors;
    }

    /**
     * Test the checkStatusDatesUpdates method with no status dates changes.
     * @throws PAException if an error occurs
     */
    @Test
    public void testCheckStatusDatesUpdatesNoUpdate() throws PAException {
        sut = createTrialInboxCommentsGenerator();
        StudyProtocolDTO studyProtocolDTO = createStudyProtocolDTO();
        StudyOverallStatusDTO overallStatusDTO = new StudyOverallStatusDTO();
        when(studyOverallStatusService.isTrialStatusOrDateChanged(overallStatusDTO, studyProtocolDTO.getIdentifier()))
            .thenReturn(false);
        sut.checkStatusDatesUpdates(studyProtocolDTO, overallStatusDTO);
        assertEquals("Wrong comment returned", "", sut.getInboxProcessingComments());
        verify(studyOverallStatusService)
            .isTrialStatusOrDateChanged(overallStatusDTO, studyProtocolDTO.getIdentifier());
    }

    /**
     * Test the checkStatusDatesUpdates method with status dates changes.
     * @throws PAException if an error occurs
     */
    @Test
    public void testCheckStatusDatesUpdatesUpdate() throws PAException {
        sut = createTrialInboxCommentsGenerator();
        StudyProtocolDTO studyProtocolDTO = createStudyProtocolDTO();
        StudyOverallStatusDTO overallStatusDTO = new StudyOverallStatusDTO();
        when(studyOverallStatusService.isTrialStatusOrDateChanged(overallStatusDTO, studyProtocolDTO.getIdentifier()))
            .thenReturn(true);
        sut.checkStatusDatesUpdates(studyProtocolDTO, overallStatusDTO);
        assertEquals("Wrong comment returned", STATUS_DATES_UPDATED, sut.getInboxProcessingComments());
        verify(studyOverallStatusService)
            .isTrialStatusOrDateChanged(overallStatusDTO, studyProtocolDTO.getIdentifier());
    }

    /**
     * Test the checkParticipatingSitesRecruitmentStatusDate method with an empty input
     * @throws PAException if an error occurs
     */
    @Test
    public void testCheckParticipatingSitesRecruitmentStatusDateEmpty() throws PAException {
        sut = createTrialInboxCommentsGenerator();
        List<StudySiteAccrualStatusDTO> participatingSites = new ArrayList<StudySiteAccrualStatusDTO>();
        sut.checkParticipatingSitesRecruitmentStatusDate(participatingSites);
        assertEquals("Wrong comment returned", "", sut.getInboxProcessingComments());
    }

    /**
     * Test the checkParticipatingSitesRecruitmentStatusDate method with no existing site
     * @throws PAException if an error occurs
     */
    @Test
    public void testCheckParticipatingSitesRecruitmentStatusDateNoExistingSite() throws PAException {
        sut = createTrialInboxCommentsGenerator();
        List<StudySiteAccrualStatusDTO> participatingSites = new ArrayList<StudySiteAccrualStatusDTO>();
        participatingSites.add(createParticipatingSite());
        sut.checkParticipatingSitesRecruitmentStatusDate(participatingSites);
        assertEquals("Wrong comment returned", "", sut.getInboxProcessingComments());
        verify(studySiteAccrualStatusService).getCurrentStudySiteAccrualStatusByStudySite(participatingSites.get(0)
                                                                                              .getStudySiteIi());
    }

    /**
     * Test the checkParticipatingSitesRecruitmentStatusDate method with no update
     * @throws PAException if an error occurs
     */
    @Test
    public void testCheckParticipatingSitesRecruitmentStatusDateNoUpdate() throws PAException {
        sut = createTrialInboxCommentsGenerator();
        List<StudySiteAccrualStatusDTO> participatingSites = new ArrayList<StudySiteAccrualStatusDTO>();
        participatingSites.add(createParticipatingSite());
        StudySiteAccrualStatusDTO existingSite = createParticipatingSite();
        when(studySiteAccrualStatusService.getCurrentStudySiteAccrualStatusByStudySite(existingSite.getStudySiteIi()))
            .thenReturn(existingSite);
        sut.checkParticipatingSitesRecruitmentStatusDate(participatingSites);
        assertEquals("Wrong comment returned", "", sut.getInboxProcessingComments());
        verify(studySiteAccrualStatusService)
            .getCurrentStudySiteAccrualStatusByStudySite(existingSite.getStudySiteIi());
    }

    /**
     * Test the checkParticipatingSitesRecruitmentStatusDate method with an update
     * @throws PAException if an error occurs
     */
    @Test
    public void testCheckParticipatingSitesRecruitmentStatusDateUpdate() throws PAException {
        sut = createTrialInboxCommentsGenerator();
        List<StudySiteAccrualStatusDTO> participatingSites = new ArrayList<StudySiteAccrualStatusDTO>();
        participatingSites.add(createParticipatingSite());
        StudySiteAccrualStatusDTO existingSite = createParticipatingSite();
        existingSite.setStatusCode(CdConverter.convertStringToCd("other status code"));
        when(studySiteAccrualStatusService.getCurrentStudySiteAccrualStatusByStudySite(existingSite.getStudySiteIi()))
            .thenReturn(existingSite);
        sut.checkParticipatingSitesRecruitmentStatusDate(participatingSites);
        assertEquals("Wrong comment returned", RECRUITMENT_STATUS_DATE_UPDATED + " ", sut.getInboxProcessingComments());
        verify(studySiteAccrualStatusService)
            .getCurrentStudySiteAccrualStatusByStudySite(existingSite.getStudySiteIi());
    }

    private StudySiteAccrualStatusDTO createParticipatingSite() {
        StudySiteAccrualStatusDTO site = new StudySiteAccrualStatusDTO();
        site.setStudySiteIi(IiConverter.convertToStudySiteIi(1L));
        site.setStatusCode(CdConverter.convertStringToCd("statusCode"));
        site.setStatusDate(TsConverter.convertToTs(new Date(0)));
        return site;
    }

    /**
     * Test the isParticipatingSiteUpdated method without existing site
     */
    @Test
    public void testIsParticipatingSiteUpdatedNoExisting() {
        sut = createTrialInboxCommentsGenerator();
        assertFalse(sut.isParticipatingSiteUpdated(null, createParticipatingSite()));
    }

    /**
     * Test the isParticipatingSiteUpdated method with the same sites
     */
    @Test
    public void testIsParticipatingSiteUpdatedEqual() {
        sut = createTrialInboxCommentsGenerator();
        assertFalse(sut.isParticipatingSiteUpdated(createParticipatingSite(), createParticipatingSite()));
    }

    /**
     * Test the isParticipatingSiteUpdated method with an updated status code
     */
    @Test
    public void testIsParticipatingSiteUpdatedStatusCodeUpdated() {
        sut = createTrialInboxCommentsGenerator();
        StudySiteAccrualStatusDTO updated = createParticipatingSite();
        updated.setStatusCode(CdConverter.convertStringToCd("other status code"));
        assertTrue(sut.isParticipatingSiteUpdated(createParticipatingSite(), updated));
    }

    /**
     * Test the isParticipatingSiteUpdated method with an updated status date
     */
    @Test
    public void testIsParticipatingSiteUpdatedStatusDateUpdated() {
        sut = createTrialInboxCommentsGenerator();
        StudySiteAccrualStatusDTO updated = createParticipatingSite();
        updated.setStatusDate(TsConverter.convertToTs(new Date(1)));
        assertTrue(sut.isParticipatingSiteUpdated(createParticipatingSite(), updated));
    }

    /**
     * Test the checkIndIdeUpdates method with an empty input
     * @throws PAException if an error occurs
     */
    @Test
    public void testCheckIndIdeUpdatesEmpty() throws PAException {
        sut = createTrialInboxCommentsGenerator();
        List<StudyIndldeDTO> studyIndIdeDTOs = new ArrayList<StudyIndldeDTO>();
        sut.checkIndIdeUpdates(studyIndIdeDTOs);
        assertEquals("Wrong comment returned", "", sut.getInboxProcessingComments());
    }

    /**
     * Test the checkIndIdeUpdates method with a new input.
     * @throws PAException if an error occurs
     */
    @Test
    public void testCheckIndIdeUpdatesNew() throws PAException {
        sut = createTrialInboxCommentsGenerator();
        List<StudyIndldeDTO> studyIndIdeDTOs = new ArrayList<StudyIndldeDTO>();
        StudyIndldeDTO studyIndldeDTO = createStudyIndldeDTO();
        studyIndldeDTO.setIdentifier(null);
        studyIndIdeDTOs.add(studyIndldeDTO);
        sut.checkIndIdeUpdates(studyIndIdeDTOs);
        assertEquals("Wrong comment returned", IND_IDE_UPDATED + " ", sut.getInboxProcessingComments());
    }

    /**
     * Test the checkIndIdeUpdates method with a not updated input.
     * @throws PAException if an error occurs
     */
    @Test
    public void testCheckIndIdeUpdatesNoUpdate() throws PAException {
        sut = createTrialInboxCommentsGenerator();
        List<StudyIndldeDTO> studyIndIdeDTOs = new ArrayList<StudyIndldeDTO>();
        studyIndIdeDTOs.add(createStudyIndldeDTO());
        StudyIndldeDTO existing = createStudyIndldeDTO();
        when(studyIndldeService.get(existing.getIdentifier())).thenReturn(existing);
        sut.checkIndIdeUpdates(studyIndIdeDTOs);
        assertEquals("Wrong comment returned", "", sut.getInboxProcessingComments());
    }

    /**
     * Test the checkIndIdeUpdates method with an updated input.
     * @throws PAException if an error occurs
     */
    @Test
    public void testCheckIndIdeUpdatesUpdate() throws PAException {
        sut = createTrialInboxCommentsGenerator();
        List<StudyIndldeDTO> studyIndIdeDTOs = new ArrayList<StudyIndldeDTO>();
        studyIndIdeDTOs.add(createStudyIndldeDTO());
        StudyIndldeDTO existing = createStudyIndldeDTO();
        existing.setIndldeTypeCode(CdConverter.convertStringToCd("Another IndldeTypeCode"));
        when(studyIndldeService.get(existing.getIdentifier())).thenReturn(existing);
        sut.checkIndIdeUpdates(studyIndIdeDTOs);
        assertEquals("Wrong comment returned", IND_IDE_UPDATED + " ", sut.getInboxProcessingComments());
    }

    private StudyIndldeDTO createStudyIndldeDTO() {
        StudyIndldeDTO studyIndldeDTO = new StudyIndldeDTO();
        studyIndldeDTO.setIdentifier(IiConverter.convertToStudyIndIdeIi(1L));
        studyIndldeDTO.setIndldeTypeCode(CdConverter.convertStringToCd("IndldeTypeCode"));
        studyIndldeDTO.setIndldeNumber(StConverter.convertToSt("IndldeNumber"));
        studyIndldeDTO.setGrantorCode(CdConverter.convertStringToCd("grantorCode"));
        return studyIndldeDTO;
    }

    /**
     * test the isIndUpdated method with equal inputs.
     */
    @Test
    public void testIsIndUpdatedEqual() {
        sut = createTrialInboxCommentsGenerator();
        assertFalse(sut.isIndUpdated(createStudyIndldeDTO(), createStudyIndldeDTO()));
    }

    /**
     * test the isIndUpdated method with the IndldeTypeCode updated.
     */
    @Test
    public void testIsIndUpdatedIndldeTypeCodeUpdated() {
        sut = createTrialInboxCommentsGenerator();
        StudyIndldeDTO updated = createStudyIndldeDTO();
        updated.setIndldeTypeCode(CdConverter.convertStringToCd("Another IndldeTypeCode"));
        assertTrue(sut.isIndUpdated(createStudyIndldeDTO(), updated));
    }

    /**
     * test the isIndUpdated method with the IndldeNumber updated.
     */
    @Test
    public void testIsIndUpdatedIndldeNumberUpdated() {
        sut = createTrialInboxCommentsGenerator();
        StudyIndldeDTO updated = createStudyIndldeDTO();
        updated.setIndldeNumber(StConverter.convertToSt("Another number"));
        assertTrue(sut.isIndUpdated(createStudyIndldeDTO(), updated));
    }

    /**
     * test the isIndUpdated method with the GrantorCode updated.
     */
    @Test
    public void testIsIndUpdatedGrantorCodeUpdated() {
        sut = createTrialInboxCommentsGenerator();
        StudyIndldeDTO updated = createStudyIndldeDTO();
        updated.setGrantorCode(CdConverter.convertStringToCd("Another Granto Code"));
        assertTrue(sut.isIndUpdated(createStudyIndldeDTO(), updated));
    }

    /**
     * Test the checkGrantUpdates method with an empty input.
     * @throws PAException if an error occurs
     */
    @Test
    public void testCheckGrantUpdatesEmpty() throws PAException {
        sut = createTrialInboxCommentsGenerator();
        List<StudyResourcingDTO> studyResourcingDTOs = new ArrayList<StudyResourcingDTO>();
        sut.checkGrantUpdates(studyResourcingDTOs);
        assertEquals("Wrong comment returned", "", sut.getInboxProcessingComments());
    }

    /**
     * Test the checkGrantUpdates method with an new input.
     * @throws PAException if an error occurs
     */
    @Test
    public void testCheckGrantUpdatesNew() throws PAException {
        sut = createTrialInboxCommentsGenerator();
        List<StudyResourcingDTO> studyResourcingDTOs = new ArrayList<StudyResourcingDTO>();
        StudyResourcingDTO studyResourcingDTO = createStudyResourcingDTO();
        studyResourcingDTO.setIdentifier(null);
        studyResourcingDTOs.add(studyResourcingDTO);
        sut.checkGrantUpdates(studyResourcingDTOs);
        assertEquals("Wrong comment returned", GRANT_INFORMATION_UPDATED + " ", sut.getInboxProcessingComments());
    }

    /**
     * Test the checkGrantUpdates method with a not updated input.
     * @throws PAException if an error occurs
     */
    @Test
    public void testCheckGrantUpdatesNoUpdate() throws PAException {
        sut = createTrialInboxCommentsGenerator();
        List<StudyResourcingDTO> studyResourcingDTOs = new ArrayList<StudyResourcingDTO>();
        studyResourcingDTOs.add(createStudyResourcingDTO());
        StudyResourcingDTO existing = createStudyResourcingDTO();
        when(studyResourcingService.get(existing.getIdentifier())).thenReturn(existing);
        sut.checkGrantUpdates(studyResourcingDTOs);
        assertEquals("Wrong comment returned", "", sut.getInboxProcessingComments());
    }

    /**
     * Test the checkGrantUpdates method with an updated input.
     * @throws PAException if an error occurs
     */
    @Test
    public void testCheckGrantUpdatesUpdate() throws PAException {
        sut = createTrialInboxCommentsGenerator();
        List<StudyResourcingDTO> studyResourcingDTOs = new ArrayList<StudyResourcingDTO>();
        studyResourcingDTOs.add(createStudyResourcingDTO());
        StudyResourcingDTO existing = createStudyResourcingDTO();
        existing.setFundingMechanismCode(CdConverter.convertStringToCd("Another Funding Mechanism Code"));
        when(studyResourcingService.get(existing.getIdentifier())).thenReturn(existing);
        sut.checkGrantUpdates(studyResourcingDTOs);
        assertEquals("Wrong comment returned", GRANT_INFORMATION_UPDATED + " ", sut.getInboxProcessingComments());
    }

    private StudyResourcingDTO createStudyResourcingDTO() {
        StudyResourcingDTO dto = new StudyResourcingDTO();
        dto.setIdentifier(IiConverter.convertToStudyResourcingIi(1L));
        dto.setFundingMechanismCode(CdConverter.convertStringToCd("fundingMechanismCode"));
        dto.setNihInstitutionCode(CdConverter.convertStringToCd("nihInstitutionCode"));
        dto.setSerialNumber(StConverter.convertToSt("serialNumber"));
        dto.setNciDivisionProgramCode(CdConverter.convertStringToCd("nciDivisionProgramCode"));
        return dto;
    }

    /**
     * Test the isGrantUpdated method with equal inputs.
     */
    @Test
    public void testisGrantUpdatedEqual() {
        sut = createTrialInboxCommentsGenerator();
        assertFalse(sut.isGrantUpdated(createStudyResourcingDTO(), createStudyResourcingDTO()));
    }

    /**
     * Test the isGrantUpdated method with FundingMechanismCode updated.
     */
    @Test
    public void testisGrantUpdatedFundingMechanismCodeUpdated() {
        sut = createTrialInboxCommentsGenerator();
        StudyResourcingDTO updated = createStudyResourcingDTO();
        updated.setFundingMechanismCode(CdConverter.convertStringToCd("Another Funding mechanism code"));
        assertTrue(sut.isGrantUpdated(createStudyResourcingDTO(), updated));
    }

    /**
     * Test the isGrantUpdated method with NihInstitutionCode updated.
     */
    @Test
    public void testisGrantUpdatedNihInstitutionCodeUpdated() {
        sut = createTrialInboxCommentsGenerator();
        StudyResourcingDTO updated = createStudyResourcingDTO();
        updated.setNihInstitutionCode(CdConverter.convertStringToCd("Another NihInstitutionCode"));
        assertTrue(sut.isGrantUpdated(createStudyResourcingDTO(), updated));
    }

    /**
     * Test the isGrantUpdated method with SerialNumber updated.
     */
    @Test
    public void testisGrantUpdatedSerialNumberUpdated() {
        sut = createTrialInboxCommentsGenerator();
        StudyResourcingDTO updated = createStudyResourcingDTO();
        updated.setSerialNumber(StConverter.convertToSt("Another SerialNumber"));
        assertTrue(sut.isGrantUpdated(createStudyResourcingDTO(), updated));
    }

    /**
     * Test the isGrantUpdated method with NciDivisionProgramCode updated.
     */
    @Test
    public void testisGrantUpdatedNciDivisionProgramCodeUpdated() {
        sut = createTrialInboxCommentsGenerator();
        StudyResourcingDTO updated = createStudyResourcingDTO();
        updated.setNciDivisionProgramCode(CdConverter.convertStringToCd("Another NciDivisionProgramCode"));
        assertTrue(sut.isGrantUpdated(createStudyResourcingDTO(), updated));
    }

}
