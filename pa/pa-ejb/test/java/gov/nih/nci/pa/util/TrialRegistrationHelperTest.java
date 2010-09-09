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
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Ts;
import gov.nih.nci.pa.dto.AbstractionCompletionDTO;
import gov.nih.nci.pa.enums.AccrualSubmissionStatusCode;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.PrimaryPurposeCode;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.DocumentWorkflowStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.DocumentWorkflowStatusServiceLocal;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyIndldeServiceLocal;
import gov.nih.nci.pa.service.StudyOverallStatusServiceLocal;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.StudyResourcingServiceLocal;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceLocal;
import gov.nih.nci.pa.service.util.AbstractionCompletionServiceRemote;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * @author ludetc
 *
 */
public class TrialRegistrationHelperTest {

    private final DocumentWorkflowStatusServiceLocal docWrkFlowStatusService = mock(DocumentWorkflowStatusServiceLocal.class);
    private final AbstractionCompletionServiceRemote abstractionCompletionService = mock(AbstractionCompletionServiceRemote.class);
    private final StudyProtocolServiceLocal studyProtocolService = mock(StudyProtocolServiceLocal.class);
    private final StudyOverallStatusServiceLocal studyOverallStatusService = mock(StudyOverallStatusServiceLocal.class);
    private final StudySiteAccrualStatusServiceLocal studySiteAccrualStatusService = mock(StudySiteAccrualStatusServiceLocal.class);
    private final StudyIndldeServiceLocal studyIndldeService = mock(StudyIndldeServiceLocal.class);
    private final StudyResourcingServiceLocal studyResourcingService = mock(StudyResourcingServiceLocal.class);

    private final List<DocumentDTO> irbDocs = new ArrayList<DocumentDTO>();
    private final List<StudySiteAccrualStatusDTO> ssasList = new ArrayList<StudySiteAccrualStatusDTO>();
    private final List<StudyIndldeDTO> studyIndList = new ArrayList<StudyIndldeDTO>();
    private final List<StudyResourcingDTO> studyResList = new ArrayList<StudyResourcingDTO>();

    private final StudyProtocolDTO spDto = new StudyProtocolDTO();

    private TrialRegistrationHelper helper;

    private static final String IRB_DOCUMENT_UPDATED = "IRB Document was updated.";
    private static final String PARTICIPATING_DOCUMENT_UPDATED = "Participating Document was updated.";
    private static final String PRIMARY_PURPOSE_UPDATED = "Primary Purpose was updated.";
    private static final String STATUS_DATES_UPDATED = "Status & Dates were updated.";
    private static final String RECRUITMENT_STATUS_DATE_UPDATED = "Participating Sites Recruitment Status "
            + "and Date was updated.";
    private static final String IND_IDE_UPDATED = "Ind Ide was updated.";
    private static final String GRANT_INFORMATION_UPDATED = "Grant information was updated.";

    @Before
    public void setup() throws PAException {
        setupArgs();

        setupMocks();

        helper = new TrialRegistrationHelper(docWrkFlowStatusService, abstractionCompletionService,
                studyProtocolService, studyOverallStatusService, studySiteAccrualStatusService,
                studyIndldeService, studyResourcingService);

    }

    private void setupArgs() {
        DocumentDTO irbDocDto = new DocumentDTO();
        irbDocDto.setTypeCode(CdConverter.convertStringToCd(DocumentTypeCode.IRB_APPROVAL_DOCUMENT.getCode()));
        DocumentDTO partSiteDocDto = new DocumentDTO();
        partSiteDocDto.setTypeCode(CdConverter.convertStringToCd(DocumentTypeCode.PARTICIPATING_SITES.getCode()));
        irbDocs.add(partSiteDocDto);
        irbDocs.add(irbDocDto);

        spDto.setPrimaryPurposeCode(CdConverter.convertStringToCd(PrimaryPurposeCode.DIAGNOSTIC.getCode()));

        StudySiteAccrualStatusDTO ssasDto = new StudySiteAccrualStatusDTO();
        ssasDto.setStatusCode(CdConverter.convertStringToCd(AccrualSubmissionStatusCode.OPENED.getCode()));
        ssasDto.setStatusDate(new Ts());
        ssasList.add(ssasDto);

        StudyIndldeDTO siiDto = new StudyIndldeDTO();
        siiDto.setIdentifier(IiConverter.convertToStudyIndIdeIi(1L));
        siiDto.setIndldeTypeCode(CdConverter.convertStringToCd("indCode"));
        siiDto.setIndldeNumber(StConverter.convertToSt("indNumber"));
        siiDto.setGrantorCode(CdConverter.convertStringToCd("grantorCode"));
        studyIndList.add(siiDto);

        siiDto = new StudyIndldeDTO();
        studyIndList.add(siiDto);

        StudyResourcingDTO sResDto = new StudyResourcingDTO();
        sResDto.setIdentifier(IiConverter.convertToStudyResourcingIi(1L));
        sResDto.setFundingMechanismCode(CdConverter.convertStringToCd("Funding Mech"));
        sResDto.setNihInstitutionCode(CdConverter.convertStringToCd("Nih inst code"));
        sResDto.setSerialNumber(StConverter.convertToSt("serial"));
        sResDto.setNciDivisionProgramCode(CdConverter.convertStringToCd("nci code"));
        studyResList.add(sResDto);

    }

    @Test
    public void testCheckForInboxProcessingComments() throws PAException {
        helper.checkForInboxProcessingComments(spDto, irbDocs, new StudyOverallStatusDTO(),
                ssasList, studyIndList, studyResList);

        String comments = helper.getInboxProcessingComments();

        assertTrue(comments.contains(PARTICIPATING_DOCUMENT_UPDATED));
        assertTrue(comments.contains(IND_IDE_UPDATED));
        assertTrue(comments.contains(IRB_DOCUMENT_UPDATED));
        assertTrue(comments.contains(RECRUITMENT_STATUS_DATE_UPDATED));
        assertTrue(comments.contains(STATUS_DATES_UPDATED));
        assertTrue(comments.contains(PRIMARY_PURPOSE_UPDATED));
        assertFalse(helper.getInboxProcessingComments().contains(GRANT_INFORMATION_UPDATED));
    }

    /**
     * @throws PAException
     */
    @Test
    public void testNoInboxProccessingComments() throws PAException {
        String comments;
        irbDocs.clear();
        DocumentDTO otherDocDTO = new DocumentDTO();
        otherDocDTO.setTypeCode(CdConverter.convertStringToCd(DocumentTypeCode.OTHER.getCode()));

        spDto.setPrimaryPurposeCode(CdConverter.convertStringToCd(PrimaryPurposeCode.PREVENTION.getCode()));
        when(studyOverallStatusService.isTrialStatusOrDateChanged(any(StudyOverallStatusDTO.class), any(Ii.class)))
             .thenReturn(false);

        StudySiteAccrualStatusDTO acSiteDto = new StudySiteAccrualStatusDTO();
        acSiteDto.setStatusCode(CdConverter.convertToCd(RecruitmentStatusCode.ACTIVE_NOT_RECRUITING));
        acSiteDto.setStatusDate(TsConverter.convertToTs(new Timestamp(new Date().getTime())));
        when(studySiteAccrualStatusService.getCurrentStudySiteAccrualStatusByStudySite(any(Ii.class))).thenReturn(acSiteDto);
        ssasList.clear();
        ssasList.add(acSiteDto);
        helper.checkForInboxProcessingComments(spDto, irbDocs, new StudyOverallStatusDTO(),
                ssasList, null, studyResList);

        comments = helper.getInboxProcessingComments();

        assertFalse(comments.contains(PARTICIPATING_DOCUMENT_UPDATED));
        assertFalse(comments.contains(IND_IDE_UPDATED));
        assertFalse(comments.contains(IRB_DOCUMENT_UPDATED));
        assertFalse(comments.contains(RECRUITMENT_STATUS_DATE_UPDATED));
        assertFalse(comments.contains(STATUS_DATES_UPDATED));
        assertFalse(comments.contains(PRIMARY_PURPOSE_UPDATED));
        assertFalse(helper.getInboxProcessingComments().contains(GRANT_INFORMATION_UPDATED));
    }

    /**
     * @throws PAException
     */
    @Test
    public void testNullForInboxProcessingComments() throws PAException {
        String comments;
        helper.checkForInboxProcessingComments(spDto, null, new StudyOverallStatusDTO(),
                null, null, null);
        comments = helper.getInboxProcessingComments();
        assertFalse(comments.contains(PARTICIPATING_DOCUMENT_UPDATED));
        assertFalse(comments.contains(IND_IDE_UPDATED));
        assertFalse(comments.contains(IRB_DOCUMENT_UPDATED));
        assertFalse(comments.contains(RECRUITMENT_STATUS_DATE_UPDATED));
        assertTrue(comments.contains(STATUS_DATES_UPDATED));
        assertTrue(comments.contains(PRIMARY_PURPOSE_UPDATED));
        assertFalse(helper.getInboxProcessingComments().contains(GRANT_INFORMATION_UPDATED));
    }

    @Test
    public void testStudyIndNullId() throws PAException {
        studyIndList.clear();
        studyIndList.add(new StudyIndldeDTO());

        helper.checkForInboxProcessingComments(spDto, irbDocs, new StudyOverallStatusDTO(),
                ssasList, studyIndList, studyResList);

        assertTrue(helper.getInboxProcessingComments().contains(IND_IDE_UPDATED));

        helper.checkForInboxProcessingComments(spDto, irbDocs, new StudyOverallStatusDTO(),
                ssasList, null, studyResList);
        assertFalse(helper.getInboxProcessingComments().contains(IND_IDE_UPDATED));

    }

    @Test
    public void testNullStudyResId() throws PAException {
        studyResList.clear();
        studyResList.add(new StudyResourcingDTO());

        helper.checkForInboxProcessingComments(spDto, irbDocs, new StudyOverallStatusDTO(),
                ssasList, studyIndList, studyResList);

        assertTrue(helper.getInboxProcessingComments().contains(GRANT_INFORMATION_UPDATED));

        helper.checkForInboxProcessingComments(spDto, irbDocs, new StudyOverallStatusDTO(),
                ssasList, studyIndList, null);
        assertFalse(helper.getInboxProcessingComments().contains(GRANT_INFORMATION_UPDATED));

    }

    @Test
    public void testGrantUpdated() throws PAException {
        studyResList.clear();
        StudyResourcingDTO sResDto = new StudyResourcingDTO();
        sResDto.setIdentifier(IiConverter.convertToStudyResourcingIi(1L));
        sResDto.setFundingMechanismCode(CdConverter.convertStringToCd("Funding Mech2"));
        sResDto.setNihInstitutionCode(CdConverter.convertStringToCd("Nih inst code"));
        sResDto.setSerialNumber(StConverter.convertToSt("serial"));
        sResDto.setNciDivisionProgramCode(CdConverter.convertStringToCd("nci code"));
        studyResList.add(sResDto);
        helper.checkForInboxProcessingComments(spDto, irbDocs, new StudyOverallStatusDTO(),
                ssasList, studyIndList, studyResList);

        assertTrue(helper.getInboxProcessingComments().contains(GRANT_INFORMATION_UPDATED));

        helper.checkForInboxProcessingComments(spDto, irbDocs, new StudyOverallStatusDTO(),
                ssasList, studyIndList, null);
        assertFalse(helper.getInboxProcessingComments().contains(GRANT_INFORMATION_UPDATED));
    }

    private void setupMocks() throws PAException {
        DocumentWorkflowStatusDTO dwsDto = new DocumentWorkflowStatusDTO();
        dwsDto.setStatusCode(CdConverter.convertStringToCd(DocumentWorkflowStatusCode.ABSTRACTED.getCode()));

        when(docWrkFlowStatusService.getCurrentByStudyProtocol(any(Ii.class))).thenReturn(dwsDto);

        AbstractionCompletionDTO absCompDto = new AbstractionCompletionDTO();
        absCompDto.setErrorType("some type");
        absCompDto.setErrorDescription("some desc");
        absCompDto.setComment("some comment");

        List<AbstractionCompletionDTO> absCompList = new ArrayList<AbstractionCompletionDTO>();
        absCompList.add(absCompDto);

        when(abstractionCompletionService.validateAbstractionCompletion(any(Ii.class))).thenReturn(absCompList);

        StudyProtocolDTO spDto = new StudyProtocolDTO();
        spDto.setPrimaryPurposeCode(CdConverter.convertStringToCd(PrimaryPurposeCode.PREVENTION.getCode()));

        when(studyProtocolService.getStudyProtocol(any(Ii.class))).thenReturn(spDto);

        when(studyOverallStatusService.isTrialStatusOrDateChanged(any(StudyOverallStatusDTO.class), any(Ii.class))).thenReturn(true);

        StudySiteAccrualStatusDTO acSiteDto = new StudySiteAccrualStatusDTO();
        when(studySiteAccrualStatusService.getCurrentStudySiteAccrualStatusByStudySite(any(Ii.class))).thenReturn(acSiteDto);

        StudyIndldeDTO siDto = new StudyIndldeDTO();
        siDto.setIndldeTypeCode(CdConverter.convertStringToCd("indCode2"));
        siDto.setIndldeNumber(StConverter.convertToSt("indNumber"));
        siDto.setGrantorCode(CdConverter.convertStringToCd("grantorCode"));
        when(studyIndldeService.get(any(Ii.class))).thenReturn(siDto);

        StudyResourcingDTO sResDto = new StudyResourcingDTO();
        sResDto.setFundingMechanismCode(CdConverter.convertStringToCd("Funding Mech"));
        sResDto.setNihInstitutionCode(CdConverter.convertStringToCd("Nih inst code"));
        sResDto.setSerialNumber(StConverter.convertToSt("serial"));
        sResDto.setNciDivisionProgramCode(CdConverter.convertStringToCd("nci code"));
        when(studyResourcingService.get(any(Ii.class))).thenReturn(sResDto);

    }
    @Test
    public void testEnforceSummaryFourSponsorAndCategory() {
        StudyProtocolDTO spDTO = new StudyProtocolDTO();
        try {
            helper.enforceSummaryFourSponsorAndCategory(spDTO, null, null);
            fail();
        }catch (PAException e) {
            assertEquals("Validation Exception Summary Four Organization DTO cannot be null , "
                + "Summary Four Study Resourcing DTO cannot be null , Summary 4 Sponsor Category cannot be null , "
                    , e.getMessage());
        }
        StudyResourcingDTO sum4Cat = new StudyResourcingDTO();
        try {
            helper.enforceSummaryFourSponsorAndCategory(spDTO, new OrganizationDTO(), sum4Cat);
            fail();
        }catch (PAException e) {
            assertEquals("Validation Exception Summary 4 Sponsor Category cannot be null , " , e.getMessage());
        }
        spDTO.setProprietaryTrialIndicator(BlConverter.convertToBl(Boolean.TRUE));
        sum4Cat.setTypeCode(CdConverter.convertStringToCd("some Code"));
        try {
            helper.enforceSummaryFourSponsorAndCategory(spDTO, new OrganizationDTO(), sum4Cat);
            fail();
        }catch (PAException e) {
            assertEquals("Validation Exception Please enter valid value for Summary 4 Sponsor Category."
                    , e.getMessage());
        }
        sum4Cat.setTypeCode(CdConverter.convertToCd(SummaryFourFundingCategoryCode.EXTERNALLY_PEER_REVIEWED));
        try {
            helper.enforceSummaryFourSponsorAndCategory(spDTO, new OrganizationDTO(), sum4Cat);
            fail();
        }catch (PAException e) {
            assertEquals("Validation Exception Please enter valid value for Summary 4 Sponsor Category."
                    , e.getMessage());
        }
        sum4Cat.setTypeCode(CdConverter.convertToCd(SummaryFourFundingCategoryCode.INDUSTRIAL));
        try {
            helper.enforceSummaryFourSponsorAndCategory(spDTO, new OrganizationDTO(), sum4Cat);
        } catch (PAException e) {
            //will never reach here.
        }
        spDTO.setProprietaryTrialIndicator(BlConverter.convertToBl(Boolean.FALSE));
        try {
            helper.enforceSummaryFourSponsorAndCategory(spDTO, new OrganizationDTO(), sum4Cat);
            fail();
        }catch (PAException e) {
            assertEquals("Validation Exception Please enter valid value for Summary 4 Sponsor Category."
                    , e.getMessage());
        }
        sum4Cat.setTypeCode(CdConverter.convertToCd(SummaryFourFundingCategoryCode.NATIONAL));
        try {
            helper.enforceSummaryFourSponsorAndCategory(spDTO, new OrganizationDTO(), sum4Cat);
        } catch (PAException e) {
            //will never reach here.
        }
    }

}
