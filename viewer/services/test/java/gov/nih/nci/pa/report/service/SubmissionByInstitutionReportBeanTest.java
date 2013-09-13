/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The viewer
 * Software was developed in conjunction with the National Cancer Institute 
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent 
 * government employees are authors, any rights in such works shall be subject 
 * to Title 17 of the United States Code, section 105. 
 *
 * This viewer Software License (the License) is between NCI and You. You (or 
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
 * its rights in the viewer Software to (i) use, install, access, operate, 
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the viewer Software; (ii) distribute and 
 * have distributed to and by third parties the viewer Software and any 
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
package gov.nih.nci.pa.report.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import gov.nih.nci.pa.domain.StudyMilestone;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.report.dto.criteria.InstitutionCriteriaDto;
import gov.nih.nci.pa.report.dto.result.MilestoneResultDto;
import gov.nih.nci.pa.report.dto.result.SummaryByInstitutionResultDto;
import gov.nih.nci.pa.report.enums.SubmissionTypeCode;
import gov.nih.nci.pa.report.util.ReportUtil;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.DAQuery;
import gov.nih.nci.pa.service.util.DataAccessServiceLocal;
import gov.nih.nci.pa.util.ISOUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
/**
 * @author Michael Visee
 */
public class SubmissionByInstitutionReportBeanTest {
    private DataAccessServiceLocal dataAccessService = mock(DataAccessServiceLocal.class);
    
    /**
     * Creates a real SubmissionByInstitutionReportBean and inject the mock services in it.
     * @return A real SubmissionByInstitutionReportBean with mock services injected.
     */
    private SubmissionByInstitutionReportBean createSubmissionByInstitutionReportBean() {
        SubmissionByInstitutionReportBean service = new SubmissionByInstitutionReportBean();
        setDependencies(service);
        return service;
    }
    
    /**
     * Creates a mock SubmissionByInstitutionReportBean and inject the mock services in it.
     * @return A mock SubmissionByInstitutionReportBean with mock services injected.
     */
    private SubmissionByInstitutionReportBean createSubmissionByInstitutionReportBeanMock() {
        SubmissionByInstitutionReportBean service = mock(SubmissionByInstitutionReportBean.class);
        doCallRealMethod().when(service).setDataAccessService(dataAccessService);
        setDependencies(service);
        return service;
    }

    /**
     * Inject the mock services in the given service.
     * @param service
     */
    private void setDependencies(SubmissionByInstitutionReportBean service) {
        service.setDataAccessService(dataAccessService);
    }
    
    /**
     * Test the get method.
     * @throws PAException in case of error.
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    @Test
    public void testGet() throws PAException {
        SubmissionByInstitutionReportBean sut = createSubmissionByInstitutionReportBeanMock();
        InstitutionCriteriaDto criteria = createValidCriteria();
        doCallRealMethod().when(sut).get(criteria);
        List<SummaryByInstitutionResultDto> result = sut.get(criteria);
        InOrder inOrder = inOrder(sut);
        ArgumentCaptor<List> resultCaptor = ArgumentCaptor.forClass(List.class);
        ArgumentCaptor<Map> resultMapCaptor1 = ArgumentCaptor.forClass(Map.class);
        ArgumentCaptor<Map> resultMapCaptor2 = ArgumentCaptor.forClass(Map.class);
        inOrder.verify(sut).loadStudies(eq(criteria), resultCaptor.capture(), resultMapCaptor1.capture());
        inOrder.verify(sut).loadMilestones(eq(criteria), resultMapCaptor2.capture());
        assertEquals("Wrong result returned", resultCaptor.getValue(), result);
        assertEquals("Wrong maps", resultMapCaptor1.getValue(), resultMapCaptor2.getValue());
        assertTrue("Wrong result returned", result.isEmpty());
        assertTrue("Wrong map returned", resultMapCaptor1.getValue().isEmpty());
    }
    
    private InstitutionCriteriaDto createValidCriteria() {
        InstitutionCriteriaDto criteria = new InstitutionCriteriaDto();
        DateMidnight low = new DateMidnight().minusDays(2);
        DateMidnight high = low.plusDays(1);
        criteria.setTimeInterval(IvlConverter.convertTs().convertToIvl(low.toDate(), high.toDate()));
        criteria.setSubmissionType(CdConverter.convertStringToCd(SubmissionTypeCode.BOTH.name()));
        Set<String> institutions = new HashSet<String>(Arrays.asList(InstitutionCriteriaDto.ALL_ORGANIZATIONS_KEY ));
        criteria.setInstitutions(ReportUtil.convertToDSet(institutions));
        return criteria;
    }
    
    /**
     * test the loadStudies method.
     */
    @Test
    public void testLoadStudies() {
        SubmissionByInstitutionReportBean sut = createSubmissionByInstitutionReportBeanMock();
        InstitutionCriteriaDto criteria = createValidCriteria();
        List<SummaryByInstitutionResultDto> result = new ArrayList<SummaryByInstitutionResultDto>();
        Map<Long, SummaryByInstitutionResultDto> resultById = new HashMap<Long, SummaryByInstitutionResultDto>();
        doCallRealMethod().when(sut).loadStudies(criteria, result, resultById);
        List<Object> rows = new ArrayList<Object>();
        Object[] row = new Object[]{null,null,null,null,null,null,1L,null,null,null};
        rows.add(row);
        when(dataAccessService.findByQuery(any(DAQuery.class))).thenReturn(rows);
        SummaryByInstitutionResultDto resultDto = new SummaryByInstitutionResultDto();
        when(sut.readMainResult(row)).thenReturn(resultDto);
        sut.loadStudies(criteria, result, resultById);
        assertEquals("Wrong result size", 1, result.size());
        assertEquals("Wrong result", resultDto, result.get(0));
        assertEquals("Wrong map size", 1, resultById.size());
        assertEquals("Wrong result in map", resultDto, resultById.get(1L));
    }
    
    /**
     * Test the readMainResult method.
     */
    @Test
    public void testReadMainResult() {
        SubmissionByInstitutionReportBean sut = createSubmissionByInstitutionReportBean();
        Timestamp now = new Timestamp(new DateTime().getMillis());
        Object[] row =
                new Object[]{"NCI-ID", 1, "Submitter", now, DocumentWorkflowStatusCode.SUBMITTED.getCode(), now, 1L,
                        "leadOrg", "leadOrgTriaId" };
        SummaryByInstitutionResultDto result = sut.readMainResult(row);
        assertEquals("Wrong assigned identifier", "NCI-ID", result.getAssignedIdentifier().getValue());
        assertEquals("Wrong submission number", 1, result.getSubmissionNumber().getValue().intValue());
        assertEquals("Wrong submitter", "Submitter", result.getSubmitterOrg().getValue());
        assertEquals("Wrong submission date", now, result.getSubmissionDate().getValue());
        assertEquals("Wrong processing status", DocumentWorkflowStatusCode.SUBMITTED.getCode(), result.getDws()
            .getCode());
        assertEquals("Wrong processing date", now, result.getDwsDate().getValue());
        assertEquals("Wrong lead org", "leadOrg", result.getLeadOrg().getValue());
        assertEquals("Wrong lead org trial id", "leadOrgTriaId", result.getLeadOrgTrialIdentifier().getValue());

    }
    
    /**
     * Test the loadMilestones method.
     */
    @Test
    public void testLoadMilestones() {
        SubmissionByInstitutionReportBean sut = createSubmissionByInstitutionReportBean();
        InstitutionCriteriaDto criteria = createValidCriteria();
        Map<Long, SummaryByInstitutionResultDto> resultById = new HashMap<Long, SummaryByInstitutionResultDto>();
        resultById.put(1L, new SummaryByInstitutionResultDto());
        resultById.put(2L, new SummaryByInstitutionResultDto());
        resultById.put(3L, new SummaryByInstitutionResultDto());
        DateTime baseDate = new DateMidnight().minusDays(10).toDateTime();
        List<StudyMilestone> milestones = new ArrayList<StudyMilestone>();
        milestones.add(createMilestone(1L, baseDate, MilestoneCode.SUBMISSION_ACCEPTED));
        milestones.add(createMilestone(1L, baseDate, MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE));
        milestones.add(createMilestone(2L, baseDate, MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE));
        milestones.add(createMilestone(2L, baseDate, MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE));
        milestones.add(createMilestone(3L, baseDate, MilestoneCode.SUBMISSION_ACCEPTED));
        MockScrollableResult scrollable = new MockScrollableResult(milestones);
        when(dataAccessService.scrollByQuery(any(DAQuery.class))).thenReturn(scrollable);
        sut.loadMilestones(criteria, resultById);
        MilestoneResultDto result1 = resultById.get(1L).getMilestoneResult();
        assertEquals("Wrong milestone", MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE, 
                     CdConverter.convertCdToEnum(MilestoneCode.class, result1.getAdminMilestone()));
        MilestoneResultDto result2 = resultById.get(2L).getMilestoneResult();
        assertEquals("Wrong milestone", MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE, 
                     CdConverter.convertCdToEnum(MilestoneCode.class, result2.getAdminMilestone()));
        assertEquals("Wrong milestone", MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE, 
                     CdConverter.convertCdToEnum(MilestoneCode.class, result2.getScientificMilestone()));
        MilestoneResultDto result3 = resultById.get(3L).getMilestoneResult();
        assertEquals("Wrong milestone", MilestoneCode.SUBMISSION_ACCEPTED, 
                     CdConverter.convertCdToEnum(MilestoneCode.class, result3.getMilestone()));
        assertTrue("Scrollable not closed", scrollable.isClosed());
    }
    
    /**
     * Test the setMilestones method with only admin milestone.
     */
    @Test
    public void testSetMilestonesAdmin() {
        SubmissionByInstitutionReportBean sut = createSubmissionByInstitutionReportBean();
        SummaryByInstitutionResultDto resultDto = new SummaryByInstitutionResultDto();
        DateTime baseDate = new DateMidnight().minusDays(10).toDateTime();
        List<StudyMilestone> milestones = new ArrayList<StudyMilestone>();
        milestones.add(createMilestone(baseDate, MilestoneCode.SUBMISSION_ACCEPTED));
        milestones.add(createMilestone(baseDate.plusDays(1), MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE));
        sut.setMilestones(resultDto, milestones);
        MilestoneResultDto result = resultDto.getMilestoneResult();
        assertEquals("Wrong admin milestone",MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE, 
                     CdConverter.convertCdToEnum(MilestoneCode.class, result.getAdminMilestone()));
        assertEquals("Wrong admin milestone date",new Timestamp(baseDate.plusDays(1).getMillis()), 
                      result.getAdminMilestoneDate().getValue());
        assertTrue("Wrong scientific milestone", ISOUtil.isCdNull(result.getScientificMilestone()));
        assertTrue("Wrong scientific milestone date", ISOUtil.isTsNull(result.getScientificMilestoneDate()));
        assertTrue("Wrong milestone", ISOUtil.isCdNull(result.getMilestone()));
        assertTrue("Wrong milestone date", ISOUtil.isTsNull(result.getMilestoneDate()));
    }

    /**
     * Test the setMilestones method with only scientific milestone.
     */
    @Test
    public void testsetMilestonesScientific() {
        SubmissionByInstitutionReportBean sut = createSubmissionByInstitutionReportBean();
        SummaryByInstitutionResultDto resultDto = new SummaryByInstitutionResultDto();
        DateTime baseDate = new DateMidnight().minusDays(10).toDateTime();
        List<StudyMilestone> milestones = new ArrayList<StudyMilestone>();
        milestones.add(createMilestone(baseDate, MilestoneCode.SUBMISSION_ACCEPTED));
        milestones.add(createMilestone(baseDate.plusDays(1), MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE));
        sut.setMilestones(resultDto, milestones);
        MilestoneResultDto result = resultDto.getMilestoneResult();
        assertTrue("Wrong admin milestone", ISOUtil.isCdNull(result.getAdminMilestone()));
        assertTrue("Wrong admin milestone date", ISOUtil.isTsNull(result.getAdminMilestoneDate()));
        assertEquals("Wrong scientific milestone", MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE,
                     CdConverter.convertCdToEnum(MilestoneCode.class, result.getScientificMilestone()));
        assertEquals("Wrong scientific milestone date", new Timestamp(baseDate.plusDays(1).getMillis()), result
            .getScientificMilestoneDate().getValue());

        assertTrue("Wrong milestone", ISOUtil.isCdNull(result.getMilestone()));
        assertTrue("Wrong milestone date", ISOUtil.isTsNull(result.getMilestoneDate()));
    }
    
    /**
     * Test the setMilestones method with both admin and scientific milestones.
     */
    @Test
    public void testsetMilestonesBoth() {
        SubmissionByInstitutionReportBean sut = createSubmissionByInstitutionReportBean();
        SummaryByInstitutionResultDto resultDto = new SummaryByInstitutionResultDto();
        DateTime baseDate = new DateMidnight().minusDays(10).toDateTime();
        List<StudyMilestone> milestones = new ArrayList<StudyMilestone>();
        milestones.add(createMilestone(baseDate, MilestoneCode.SUBMISSION_ACCEPTED));
        milestones.add(createMilestone(baseDate.plusDays(1), MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE));
        milestones.add(createMilestone(baseDate.plusDays(2), MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE));
        sut.setMilestones(resultDto, milestones);
        MilestoneResultDto result = resultDto.getMilestoneResult();
        assertEquals("Wrong admin milestone",MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE, 
                     CdConverter.convertCdToEnum(MilestoneCode.class, result.getAdminMilestone()));
        assertEquals("Wrong admin milestone date",new Timestamp(baseDate.plusDays(1).getMillis()), 
                      result.getAdminMilestoneDate().getValue());
        assertEquals("Wrong scientific milestone", MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE,
                     CdConverter.convertCdToEnum(MilestoneCode.class, result.getScientificMilestone()));
        assertEquals("Wrong scientific milestone date", new Timestamp(baseDate.plusDays(2).getMillis()), result
            .getScientificMilestoneDate().getValue());

        assertTrue("Wrong milestone", ISOUtil.isCdNull(result.getMilestone()));
        assertTrue("Wrong milestone date", ISOUtil.isTsNull(result.getMilestoneDate()));
    }
    
    /**
     * Test the setMilestones method with no admin or scientific milestone.
     */
    @Test
    public void testsetMilestones() {
        SubmissionByInstitutionReportBean sut = createSubmissionByInstitutionReportBean();
        SummaryByInstitutionResultDto resultDto = new SummaryByInstitutionResultDto();
        DateTime baseDate = new DateMidnight().minusDays(10).toDateTime();
        List<StudyMilestone> milestones = new ArrayList<StudyMilestone>();
        milestones.add(createMilestone(baseDate, MilestoneCode.SUBMISSION_ACCEPTED));
        milestones.add(createMilestone(baseDate.plusDays(1), MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE));
        milestones.add(createMilestone(baseDate.plusDays(2), MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE));
        milestones.add(createMilestone(baseDate.plusDays(3), MilestoneCode.TRIAL_SUMMARY_REPORT));
        sut.setMilestones(resultDto, milestones);
        MilestoneResultDto result = resultDto.getMilestoneResult();
        assertTrue("Wrong admin milestone", ISOUtil.isCdNull(result.getAdminMilestone()));
        assertTrue("Wrong admin milestone date", ISOUtil.isTsNull(result.getAdminMilestoneDate()));
        assertTrue("Wrong scientific milestone", ISOUtil.isCdNull(result.getScientificMilestone()));
        assertTrue("Wrong scientific milestone date", ISOUtil.isTsNull(result.getScientificMilestoneDate()));
        assertEquals("Wrong milestone", MilestoneCode.TRIAL_SUMMARY_REPORT, 
                CdConverter.convertCdToEnum(MilestoneCode.class, result.getMilestone()));
        assertEquals("Wrong milestone date", new Timestamp(baseDate.plusDays(3).getMillis()), result
            .getMilestoneDate().getValue());
    }
    
    private StudyMilestone createMilestone(DateTime date, MilestoneCode code) {
        StudyMilestone milestone = new StudyMilestone();
        milestone.setMilestoneDate(new Timestamp(date.getMillis()));
        milestone.setMilestoneCode(code);
        return milestone;
    }
    
    private StudyMilestone createMilestone(Long spId, DateTime date, MilestoneCode code) {
        StudyMilestone milestone = createMilestone(date, code);
        StudyProtocol studyProtocol = new StudyProtocol();
        studyProtocol.setId(spId);
        milestone.setStudyProtocol(studyProtocol);
        return milestone;
    }
}
