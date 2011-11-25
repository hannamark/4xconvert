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
*/
package gov.nih.nci.pa.viewer.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import gov.nih.nci.iso21090.St;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.report.dto.criteria.StandardCriteriaDto;
import gov.nih.nci.pa.report.dto.result.TrialListResultDto;
import gov.nih.nci.pa.report.enums.SubmissionTypeCode;
import gov.nih.nci.pa.report.service.SubmitterOrganizationLocal;
import gov.nih.nci.pa.report.service.TrialListLocal;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.viewer.dto.criteria.InstitutionCriteriaWebDto;
import gov.nih.nci.pa.viewer.dto.result.TrialCountsResultWebDto;
import gov.nih.nci.pa.viewer.dto.result.TrialListResultWebDto;
import gov.nih.nci.pa.viewer.util.ServiceLocator;
import gov.nih.nci.pa.viewer.util.ViewerConstants;
import gov.nih.nci.pa.viewer.util.ViewerServiceLocator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.struts2.ServletActionContext;
import org.junit.Before;
import org.junit.Test;

/**
 * Test for SubmissionByInstitutionAction.
 * 
 * @author Michael Visee
 */
public class SubmissionByInstitutionActionTest extends AbstractReportActionTest<SubmissionByInstitutionAction> {
    
    private SubmitterOrganizationLocal submitterOrganizationReportService = mock(SubmitterOrganizationLocal.class);
    private TrialListLocal trialListReportService = mock(TrialListLocal.class);
    
    /**
     * Initialization for parent class tests.
     */
    @Before
    public void initAction() {
        action = createSubmissionByInstitutionAction();
        action.setCriteria(new InstitutionCriteriaWebDto());
    }
    
    /**
     * Creates a real SubmissionByInstitutionAction and inject the mock services in it.
     * @return A real SubmissionByInstitutionAction with mock services injected.
     */
    private SubmissionByInstitutionAction createSubmissionByInstitutionAction() {
        SubmissionByInstitutionAction action = new SubmissionByInstitutionAction();
        setDependencies(action);
        return action;
    }

    /**
     * Creates a mock SubmissionByInstitutionAction and inject the mock services in it.
     * @return A mock SubmissionByInstitutionAction with mock services injected.
     */
    private SubmissionByInstitutionAction createSubmissionByInstitutionActionMock() {
        SubmissionByInstitutionAction action = mock(SubmissionByInstitutionAction.class);
        doCallRealMethod().when(action).setSubmitterOrganizationReportService(submitterOrganizationReportService);
        doCallRealMethod().when(action).setTrialListReportService(trialListReportService);
        setDependencies(action);
        return action;
    }

    private void setDependencies(SubmissionByInstitutionAction action) {
        action.setSubmitterOrganizationReportService(submitterOrganizationReportService);
        action.setTrialListReportService(trialListReportService);
    }
    
    /**
     * Test the prepare method.
     */
    @Test
    public void testPrepare() {
        SubmissionByInstitutionAction sut = mock(SubmissionByInstitutionAction.class);
        doCallRealMethod().when(sut).prepare();
        ServiceLocator serviceLocator = mock(ServiceLocator.class);
        ViewerServiceLocator.getInstance().setServiceLocator(serviceLocator);
        when(serviceLocator.getSubmitterOrganizationReportService()).thenReturn(submitterOrganizationReportService);
        when(serviceLocator.getTrialListReportService()).thenReturn(trialListReportService);
        sut.prepare();
        verify(sut).setSubmitterOrganizationReportService(submitterOrganizationReportService);
        verify(sut).setTrialListReportService(trialListReportService);
    }
    
    /**
     * Test the execute method.
     */
    @Test
    public void testExecute() {
        SubmissionByInstitutionAction sut = createSubmissionByInstitutionActionMock();
        doCallRealMethod().when(sut).execute();
        doCallRealMethod().when(sut).getUserRole();
        doCallRealMethod().when(sut).getCriteria();
        doCallRealMethod().when(sut).setCriteria(any(InstitutionCriteriaWebDto.class));
        String result = sut.execute();
        assertEquals("Wrong result returned", "success", result);
        assertNotNull("No criteria created", sut.getCriteria());
    }
    
    /**
     * Test the getReport method in the successful case.
     * @throws PAException in case of error
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetReportSuccess() throws PAException {
        SubmissionByInstitutionAction sut = createSubmissionByInstitutionActionMock();
        doCallRealMethod().when(sut).getCriteria();
        doCallRealMethod().when(sut).setCriteria(any(InstitutionCriteriaWebDto.class));
        doCallRealMethod().when(sut).getReport();
        doCallRealMethod().when(sut).getResultList();
        doCallRealMethod().when(sut).setResultList(anyListOf(TrialListResultWebDto.class));
        doCallRealMethod().when(sut).getUserRole();
        InstitutionCriteriaWebDto criteria = new InstitutionCriteriaWebDto();
        criteria.setSubmissionType(SubmissionTypeCode.BOTH.name());
        sut.setCriteria(criteria);
        List<TrialListResultDto> results = new ArrayList<TrialListResultDto>();
        when(trialListReportService.get(any(StandardCriteriaDto.class))).thenReturn(results);
        List<TrialListResultWebDto> webResults = new ArrayList<TrialListResultWebDto>();
        when(sut.getWebList(results, SubmissionTypeCode.BOTH, false)).thenReturn(webResults);
        String result = sut.getReport();
        assertEquals("Wrong result returned", "success", result);
        assertEquals("Wrong result list", webResults, sut.getResultList());
        List<TrialCountsResultWebDto> sessionResult =
                (List<TrialCountsResultWebDto>) ServletActionContext.getRequest().getSession()
                    .getAttribute(ViewerConstants.RESULT_LIST);
        assertEquals("Wrong result in session", webResults, sessionResult);
    }

    /**
     * Test the getReport method in the error case.
     * @throws PAException in case of error
     */
    @Test
    public void testGetReportError() throws PAException {
        SubmissionByInstitutionAction sut = createSubmissionByInstitutionAction();
        InstitutionCriteriaWebDto criteria = new InstitutionCriteriaWebDto();
        sut.setCriteria(criteria);
        when(trialListReportService.get(any(StandardCriteriaDto.class))).thenThrow(new PAException("PA"));
        String result = sut.getReport();
        Collection<String> errors = sut.getActionErrors();
        assertNotNull("No error collection", errors);
        assertEquals("Wrong number of errors", 1, errors.size());
        assertEquals("Wrong error message", "PA", errors.iterator().next());
        assertEquals("Wrong result returned", "success", result);
        assertNull("Wrong result list", sut.getResultList());
    }
    
    /**
     * Test the getWebList method.
     */
    @Test
    public void testGetWebList() {
        SubmissionByInstitutionAction sut = createSubmissionByInstitutionActionMock();
        List<TrialListResultDto> trialListResultDtos = createTrialListResultDtos();
        doCallRealMethod().when(sut).getWebList(trialListResultDtos, SubmissionTypeCode.BOTH, false);
        List<TrialListResultWebDto> result = sut.getWebList(trialListResultDtos, SubmissionTypeCode.BOTH, false);
        verify(sut).addSummaryResults(anyListOf(TrialListResultWebDto.class), eq(SubmissionTypeCode.BOTH), eq(false),
                                      eq(1), eq(2));
        assertNotNull("No result returned", result);
        assertEquals("Wrong result size", 2, result.size());
    }

    private List<TrialListResultDto> createTrialListResultDtos() {
        List<TrialListResultDto> dtos = new ArrayList<TrialListResultDto>();
        TrialListResultDto dto1 = new TrialListResultDto();
        dto1.setSubmissionNumber(IntConverter.convertToInt(1));
        dto1.setDws(CdConverter.convertStringToCd(DocumentWorkflowStatusCode.ACCEPTED.name()));
        dtos.add(dto1);
        TrialListResultDto dto2 = new TrialListResultDto();
        dto2.setSubmissionNumber(IntConverter.convertToInt(2));
        dto2.setDws(CdConverter.convertStringToCd(DocumentWorkflowStatusCode.ACCEPTED.name()));
        dtos.add(dto2);
        return dtos;
    }
    
    /**
     * Test the addSummaryResults method for original submissions.
     */
    @Test
    public void testAddSummaryResultsOriginal() {
        SubmissionByInstitutionAction sut = createSubmissionByInstitutionAction();
        List<TrialListResultWebDto> results = new ArrayList<TrialListResultWebDto>();
        sut.addSummaryResults(results, SubmissionTypeCode.ORIGINAL, false, 1, 3);
        assertEquals("Wrong result size", 1, results.size());
        checkTrialListResultWebDto(results.get(0), "Total of original submissions", "Original", "1");
    }

    /**
     * Test the addSummaryResults method for amendment submissions.
     */
    @Test
    public void testAddSummaryResultsAmendment() {
        SubmissionByInstitutionAction sut = createSubmissionByInstitutionAction();
        List<TrialListResultWebDto> results = new ArrayList<TrialListResultWebDto>();
        sut.addSummaryResults(results, SubmissionTypeCode.AMENDMENT, false, 1, 3);
        assertEquals("Wrong result size", 1, results.size());
        checkTrialListResultWebDto(results.get(0), "Total of amendments", "Amendment", "2");
    }

    /**
     * Test the addSummaryResults method for both submissions.
     */
    @Test
    public void testAddSummaryResultsBoth() {
        SubmissionByInstitutionAction sut = createSubmissionByInstitutionAction();
        List<TrialListResultWebDto> results = new ArrayList<TrialListResultWebDto>();
        sut.addSummaryResults(results, SubmissionTypeCode.BOTH, true, 1, 3);
        assertEquals("Wrong result size", 4, results.size());
        checkTrialListResultWebDto(results.get(0), "Total of original submissions", "Original", "1");
        checkTrialListResultWebDto(results.get(1), "Total of amendments", "Amendment", "2");
        checkTrialListResultWebDto(results.get(2), "Total all submission", "All", "3");
        checkTrialListResultWebDto(results.get(3), "Total", "3", null);
    }

    private void checkTrialListResultWebDto(TrialListResultWebDto result, String assignedIdentifier,
            String submissionType, String submitterOrg) {
        assertEquals("Wrong assigned identifier", assignedIdentifier, result.getAssignedIdentifier());
        assertEquals("Wrong submission type", submissionType, result.getSubmissionType());
        assertEquals("Wrong submitter org", submitterOrg, result.getSubmitterOrg());
    }

    /**
     * Test the getSubmitterOrganizations method in the success case.
     * @throws PAException in case of error
     */
    @Test
    public void testgetSubmitterOrganizationsSuccess() throws PAException {
        SubmissionByInstitutionAction sut = createSubmissionByInstitutionAction();
        List<St> orgs = new ArrayList<St>();
        orgs.add(StConverter.convertToSt("org1"));
        orgs.add(StConverter.convertToSt("org2"));
        when(submitterOrganizationReportService.get()).thenReturn(orgs);
        List<String> result = sut.getSubmitterOrganizations();
        assertNotNull("No result returned", result);
        assertEquals("Wrong result size", 2, result.size());
        assertEquals("Wrong organization", "org1", result.get(0));
        assertEquals("Wrong organization", "org2", result.get(1));
    }
    
    /**
     * Test the getSubmitterOrganizations method in the error case.
     * @throws PAException in case of error
     */
    @Test
    public void testgetSubmitterOrganizationsError() throws PAException {
        SubmissionByInstitutionAction sut = createSubmissionByInstitutionAction();
        when(submitterOrganizationReportService.get()).thenThrow(new PAException("PA"));
        List<String> result = sut.getSubmitterOrganizations();
        assertNotNull("No result returned", result);
        assertEquals("Wrong result size", 0, result.size());
    }
    
    /**
     * Test the getSelectedInstitutions method for all case
     */
    @Test
    public void testGetSelectedInstitutionsAll() {
        SubmissionByInstitutionAction sut = createSubmissionByInstitutionAction();
        InstitutionCriteriaWebDto criteria = new InstitutionCriteriaWebDto();
        sut.setCriteria(criteria);
        Set<String> institutions = new HashSet<String>();
        institutions.add("1");
        criteria.setInstitutions(institutions);
        String result = sut.getSelectedInstitutions();
        assertEquals("All", result);
    }
    
    /**
     * Test the getSelectedInstitutions method 
     */
    @Test
    public void testGetSelectedInstitutions() {
        SubmissionByInstitutionAction sut = createSubmissionByInstitutionAction();
        InstitutionCriteriaWebDto criteria = new InstitutionCriteriaWebDto();
        sut.setCriteria(criteria);
        Set<String> institutions = new HashSet<String>();
        institutions.add("org 1");
        institutions.add("org 2");
        criteria.setInstitutions(institutions);
        String result = sut.getSelectedInstitutions();
        assertEquals("org 1, org 2", result);
    }
   
}
