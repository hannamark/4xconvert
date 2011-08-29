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
package gov.nih.nci.pa.service.util;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.ArmServiceLocal;
import gov.nih.nci.pa.service.DocumentServiceLocal;
import gov.nih.nci.pa.service.InterventionServiceLocal;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PlannedActivityServiceLocal;
import gov.nih.nci.pa.service.PlannedMarkerServiceLocal;
import gov.nih.nci.pa.service.StudyContactServiceLocal;
import gov.nih.nci.pa.service.StudyDiseaseServiceLocal;
import gov.nih.nci.pa.service.StudyIndldeServiceLocal;
import gov.nih.nci.pa.service.StudyOutcomeMeasureServiceLocal;
import gov.nih.nci.pa.service.StudyOverallStatusServiceLocal;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.StudyRecruitmentStatusServiceLocal;
import gov.nih.nci.pa.service.StudyRegulatoryAuthorityServiceLocal;
import gov.nih.nci.pa.service.StudyResourcingServiceLocal;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceLocal;
import gov.nih.nci.pa.service.StudySiteContactServiceLocal;
import gov.nih.nci.pa.service.StudySiteServiceLocal;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceRemote;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

/**
 * @author Michael Visee
 */
public class AbstractionCompletionServiceBeanTest {
    private ArmServiceLocal armService = mock(ArmServiceLocal.class);
    private DocumentServiceLocal documentService = mock(DocumentServiceLocal.class);
    private InterventionServiceLocal interventionService = mock(InterventionServiceLocal.class);
    private OrganizationCorrelationServiceRemote organizationCorrelationService = mock(OrganizationCorrelationServiceRemote.class);
    private PlannedActivityServiceLocal plannedActivityService = mock(PlannedActivityServiceLocal.class);
    private PlannedMarkerServiceLocal plannedMarkerService = mock(PlannedMarkerServiceLocal.class);
    private RegistryUserServiceLocal registryUserService = mock(RegistryUserServiceLocal.class);
    private RegulatoryInformationServiceRemote regulatoryInformationService = mock(RegulatoryInformationServiceRemote.class);
    private StudyContactServiceLocal studyContactService = mock(StudyContactServiceLocal.class);
    private StudyDiseaseServiceLocal studyDiseaseService = mock(StudyDiseaseServiceLocal.class);
    private StudyIndldeServiceLocal studyIndldeService = mock(StudyIndldeServiceLocal.class);
    private StudyOutcomeMeasureServiceLocal studyOutcomeMeasureService = mock(StudyOutcomeMeasureServiceLocal.class);
    private StudyOverallStatusServiceLocal studyOverallStatusService = mock(StudyOverallStatusServiceLocal.class);
    private StudyProtocolServiceLocal studyProtocolService = mock(StudyProtocolServiceLocal.class);
    private StudyRecruitmentStatusServiceLocal studyRecruitmentStatusService = mock(StudyRecruitmentStatusServiceLocal.class);
    private StudyRegulatoryAuthorityServiceLocal studyRegulatoryAuthorityService = mock(StudyRegulatoryAuthorityServiceLocal.class);
    private StudyResourcingServiceLocal studyResourcingService = mock(StudyResourcingServiceLocal.class);
    private StudySiteServiceLocal studySiteService = mock(StudySiteServiceLocal.class);
    private StudySiteAccrualStatusServiceLocal studySiteAccrualStatusService = mock(StudySiteAccrualStatusServiceLocal.class);
    private StudySiteContactServiceLocal studySiteContactService = mock(StudySiteContactServiceLocal.class);
    
    /**
     * Creates a real AbstractionCompletionServiceBean and inject the mock services in it.
     * @return A real AbstractionCompletionServiceBean with mock services injected.
     */
    private AbstractionCompletionServiceBean createAbstractionCompletionServiceBean() {
        AbstractionCompletionServiceBean sbstractionCompletionServiceBean = new AbstractionCompletionServiceBean();
        setDependencies(sbstractionCompletionServiceBean);
        return sbstractionCompletionServiceBean;
    }

    /**
     * Creates a mock AbstractionCompletionServiceBean and inject the mock services in it.
     * @return A mock AbstractionCompletionServiceBean with mock services injected.
     */
    private AbstractionCompletionServiceBean createAbstractionCompletionServiceBeanMock() {
        AbstractionCompletionServiceBean service = mock(AbstractionCompletionServiceBean.class);
        doCallRealMethod().when(service).setArmService(armService);
        doCallRealMethod().when(service).setDocumentService(documentService);
        doCallRealMethod().when(service).setInterventionService(interventionService);
        doCallRealMethod().when(service).setOrganizationCorrelationService(organizationCorrelationService);
        doCallRealMethod().when(service).setPlannedActivityService(plannedActivityService);
        doCallRealMethod().when(service).setPlannedMarkerService(plannedMarkerService);
        doCallRealMethod().when(service).setRegistryUserService(registryUserService);
        doCallRealMethod().when(service).setRegulatoryInformationService(regulatoryInformationService);
        doCallRealMethod().when(service).setStudyContactService(studyContactService);
        doCallRealMethod().when(service).setStudyDiseaseService(studyDiseaseService);
        doCallRealMethod().when(service).setStudyIndldeService(studyIndldeService);
        doCallRealMethod().when(service).setStudyOutcomeMeasureService(studyOutcomeMeasureService);
        doCallRealMethod().when(service).setStudyOverallStatusService(studyOverallStatusService);
        doCallRealMethod().when(service).setStudyProtocolService(studyProtocolService);
        doCallRealMethod().when(service).setStudyRecruitmentStatusService(studyRecruitmentStatusService);
        doCallRealMethod().when(service).setStudyRegulatoryAuthorityService(studyRegulatoryAuthorityService);
        doCallRealMethod().when(service).setStudyResourcingService(studyResourcingService);
        doCallRealMethod().when(service).setStudySiteService(studySiteService);
        doCallRealMethod().when(service).setStudySiteAccrualStatusService(studySiteAccrualStatusService);
        doCallRealMethod().when(service).setStudySiteContactService(studySiteContactService);
        setDependencies(service);
        return service;
    }

    /**
     * Inject the mock services in the given AbstractionCompletionServiceBean.
     * @param service The AbstractionCompletionServiceBean to setup with mock services
     */
    private void setDependencies(AbstractionCompletionServiceBean service) {
        service.setArmService(armService);
        service.setDocumentService(documentService);
        service.setInterventionService(interventionService);
        service.setOrganizationCorrelationService(organizationCorrelationService);
        service.setPlannedActivityService(plannedActivityService);
        service.setPlannedMarkerService(plannedMarkerService);
        service.setRegistryUserService(registryUserService);
        service.setRegulatoryInformationService(regulatoryInformationService);
        service.setStudyContactService(studyContactService);
        service.setStudyDiseaseService(studyDiseaseService);
        service.setStudyIndldeService(studyIndldeService);
        service.setStudyOutcomeMeasureService(studyOutcomeMeasureService);
        service.setStudyOverallStatusService(studyOverallStatusService);
        service.setStudyProtocolService(studyProtocolService);
        service.setStudyRecruitmentStatusService(studyRecruitmentStatusService);
        service.setStudyRegulatoryAuthorityService(studyRegulatoryAuthorityService);
        service.setStudyResourcingService(studyResourcingService);
        service.setStudySiteService(studySiteService);
        service.setStudySiteAccrualStatusService(studySiteAccrualStatusService);
        service.setStudySiteContactService(studySiteContactService);
    }
    
    /**
     * test the isStudySiteRecruiting method with no site. 
     * Input: No site associated with the study 
     * Output: false
     * @throws PAException If an error occurs
     */
    @Test
    public void testIsStudySiteRecruitingNoSite() throws PAException {
        testIsStudySiteRecruiting(new ArrayList<StudySiteDTO>(), new ArrayList<StudySiteAccrualStatusDTO>(), false);
    }

    /**
     * test the isStudySiteRecruiting method with no site. 
     * Input: one site associated with the study 
     *        No Accrual status associated with the site 
     * Output: false
     * @throws PAException If an error occurs
     */
    @Test
    public void testIsStudySiteRecruitingNoSiteAccrualStatus() throws PAException {
        List<StudySiteDTO> studySites = createStudySites();
        testIsStudySiteRecruiting(studySites, new ArrayList<StudySiteAccrualStatusDTO>(), false);
    }

    /**
     * test the isStudySiteRecruiting method with no site. 
     * Input: one site associated with the study one non recruiting
     *        Accrual status associated with the site 
     * Output: false
     * @throws PAException If an error occurs
     */
    @Test
    public void testIsStudySiteRecruitingNotRecruitingSite() throws PAException {
        List<StudySiteDTO> studySites = createStudySites();
        List<StudySiteAccrualStatusDTO> statuses = createStudySiteAccrualStatuses(RecruitmentStatusCode.WITHDRAWN);
        testIsStudySiteRecruiting(studySites, statuses, false);
    }

    /**
     * test the isStudySiteRecruiting method with no site. 
     * Input: one site associated with the study one recruiting
     *        Accrual status associated with the site 
     * Output: true
     * @throws PAException If an error occurs
     */
    @Test
    public void testIsStudySiteRecruitingRecruitingSite() throws PAException {
        List<StudySiteDTO> studySites = createStudySites();
        List<StudySiteAccrualStatusDTO> statuses = createStudySiteAccrualStatuses(RecruitmentStatusCode.ACTIVE);
        testIsStudySiteRecruiting(studySites, statuses, true);
    }

    /**
     * Creates a list of StudySiteDTO with only one StudySiteDTO.
     * @return a list of StudySiteDTO with only one StudySiteDTO.
     */
    private List<StudySiteDTO> createStudySites() {
        List<StudySiteDTO> studySites = new ArrayList<StudySiteDTO>();
        StudySiteDTO dto = new StudySiteDTO();
        dto.setIdentifier(IiConverter.convertToIi(1L));
        studySites.add(dto);
        return studySites;
    }

    /**
     * Creates a list of StudySiteAccrualStatusDTO with one StudySiteAccrualStatusDTO having the given status.
     * @param accrualStatus The RecruitmentStatusCode to set in the dto.
     * @return a list of StudySiteAccrualStatusDTO with one StudySiteAccrualStatusDTO having the given status
     */
    private List<StudySiteAccrualStatusDTO> createStudySiteAccrualStatuses(RecruitmentStatusCode accrualStatus) {
        List<StudySiteAccrualStatusDTO> studySiteAccrualStatuses = new ArrayList<StudySiteAccrualStatusDTO>();
        StudySiteAccrualStatusDTO dto = new StudySiteAccrualStatusDTO();
        dto.setIdentifier(IiConverter.convertToIi(2L));
        dto.setStatusCode(CdConverter.convertToCd(accrualStatus));
        studySiteAccrualStatuses.add(dto);
        return studySiteAccrualStatuses;
    }

    private void testIsStudySiteRecruiting(List<StudySiteDTO> studySites,
            List<StudySiteAccrualStatusDTO> studySiteAccrualStatuses, boolean expectedResult) throws PAException {
        AbstractionCompletionServiceBean sut = createAbstractionCompletionServiceBean();
        Ii spIi = IiConverter.convertToIi(1L);
        when(studySiteService.getByStudyProtocol(eq(spIi), any(StudySiteDTO.class))).thenReturn(studySites);
        Ii siteId = IiConverter.convertToIi(1L);
        when(studySiteAccrualStatusService.getStudySiteAccrualStatusByStudySite(siteId))
            .thenReturn(studySiteAccrualStatuses);
        boolean result = sut.isStudySiteRecruiting(spIi);
        assertEquals("Wrong result returned by isStudySiteRecruiting", expectedResult, result);
        ArgumentCaptor<StudySiteDTO> studySiteDTOCaptor = ArgumentCaptor.forClass(StudySiteDTO.class);
        verify(studySiteService).getByStudyProtocol(eq(spIi), studySiteDTOCaptor.capture());
        StudySiteDTO studySiteDto = studySiteDTOCaptor.getValue();
        assertEquals("Wrong functional code passed to studySiteService",
                     StudySiteFunctionalCode.TREATING_SITE.getCode(),
                     CdConverter.convertCdToString(studySiteDto.getFunctionalCode()));
        if (studySites.isEmpty()) {
            verify(studySiteAccrualStatusService, never()).getStudySiteAccrualStatusByStudySite(siteId);
        } else {
            verify(studySiteAccrualStatusService).getStudySiteAccrualStatusByStudySite(siteId);
        }
    }

}
