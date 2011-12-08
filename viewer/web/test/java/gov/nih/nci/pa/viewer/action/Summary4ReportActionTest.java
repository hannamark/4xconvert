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
package gov.nih.nci.pa.viewer.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.report.dto.criteria.Summ4RepCriteriaDto;
import gov.nih.nci.pa.report.dto.result.Summ4RepResultDto;
import gov.nih.nci.pa.report.service.Summary4ReportLocal;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.viewer.dto.criteria.Summ4RepCriteriaWebDto;
import gov.nih.nci.pa.viewer.dto.result.KeyValueDTO;
import gov.nih.nci.pa.viewer.dto.result.Summ4ResultWebDto;
import gov.nih.nci.pa.viewer.util.ServiceLocator;
import gov.nih.nci.pa.viewer.util.ViewerConstants;
import gov.nih.nci.pa.viewer.util.ViewerServiceLocator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.junit.Before;
import org.junit.Test;

/**
 * Test for Summary4ReportAction.
 * 
 * @author Michael Visee
 */
public class Summary4ReportActionTest extends AbstractReportActionTest<Summary4ReportAction> {
    
    private Summary4ReportLocal summary4ReportService = mock(Summary4ReportLocal.class);
    
    /**
     * Initialization for parent class tests.
     * @throws TooManyResultsException in case of error
     */
    @Before
    public void initAction() throws TooManyResultsException {
        action = createSummary4ReportAction();
        action.setCriteria(new Summ4RepCriteriaWebDto());
        when(summary4ReportService.getFamilies(100)).thenReturn(new HashMap<String, String>());
    }

    /**
     * Creates a real Summary4ReportAction and inject the mock services in it.
     * @return A real Summary4ReportAction with mock services injected.
     */
    private Summary4ReportAction createSummary4ReportAction() {
        Summary4ReportAction action = new Summary4ReportAction();
        setDependencies(action);
        return action;
    }

    /**
     * Creates a mock Summary4ReportAction and inject the mock services in it.
     * @return A mock Summary4ReportAction with mock services injected.
     */
    private Summary4ReportAction createSummary4ReportActionMock() {
        Summary4ReportAction action = mock(Summary4ReportAction.class);
        doCallRealMethod().when(action).setSummary4ReportService(summary4ReportService);
        setDependencies(action);
        return action;
    }

    private void setDependencies(Summary4ReportAction action) {
        action.setSummary4ReportService(summary4ReportService);
    }
    
    /**
     * Test the prepare method.
     */
    @Test
    public void testPrepare() {
        Summary4ReportAction sut = mock(Summary4ReportAction.class);
        doCallRealMethod().when(sut).prepare();
        ServiceLocator serviceLocator = mock(ServiceLocator.class);
        ViewerServiceLocator.getInstance().setServiceLocator(serviceLocator);
        when(serviceLocator.getSummary4ReportService()).thenReturn(summary4ReportService);
        sut.prepare();
        verify(sut).setSummary4ReportService(summary4ReportService);
    }

    /**
     * Test the execute method.
     */
    @Test
    public void testExecute() {
        Summary4ReportAction sut = createSummary4ReportActionMock();
        doCallRealMethod().when(sut).execute();
        doCallRealMethod().when(sut).getUserRole();
        doCallRealMethod().when(sut).getCriteria();
        doCallRealMethod().when(sut).setCriteria(any(Summ4RepCriteriaWebDto.class));
        String result = sut.execute();
        assertEquals("Wrong result returned", "success", result);
        assertNotNull("No criteria created", sut.getCriteria());
        verify(sut).loadFamilies();
        verify(sut).loadOrganizations();
    }

    /**
     * Test the loadFamilies method in the success case.
     * @throws TooManyResultsException in case of error
     */
    @Test
    public void testLoadFamiliesSuccess() throws TooManyResultsException {
        Summary4ReportAction sut = createSummary4ReportAction();
        Map<String, String> familyMap = new HashMap<String, String>();
        familyMap.put("1", "name 1");
        when(summary4ReportService.getFamilies(100)).thenReturn(familyMap);
        sut.loadFamilies();
        verify(summary4ReportService).getFamilies(100);
        List<KeyValueDTO> result = sut.getFamilies();
        assertNotNull("No result returned", result);
        assertEquals("Wrong result size", 1, result.size());
        assertEquals("Wrong name or key", "name 1", result.get(0).getValue());
    }

    /**
     * Test the loadFamilies method in the error case.
     * @throws TooManyResultsException in case of error
     */
    @Test
    public void testLoadFamiliesError() throws TooManyResultsException {
        Summary4ReportAction sut = createSummary4ReportActionMock();
        doCallRealMethod().when(sut).loadFamilies();
        when(summary4ReportService.getFamilies(100)).thenThrow(new TooManyResultsException(200));
        sut.loadFamilies();
        verify(sut).addActionError(null);
    }

    /**
     * Test the loadOrganizations method in the success case.
     * @throws TooManyResultsException in case of error
     */
    @Test
    public void testLoadOrganizations() throws TooManyResultsException {
        Summary4ReportAction sut = createSummary4ReportAction();
        Summ4RepCriteriaWebDto criteria = new Summ4RepCriteriaWebDto();
        criteria.setFamilyId("1");
        sut.setCriteria(criteria);
        Map<String, String> orgMap = new HashMap<String, String>();
        orgMap.put("org", "type");
        when(summary4ReportService.getOrganizations("1", 100)).thenReturn(orgMap);
        String result = sut.loadOrganizations();
        assertEquals("Wrong result returned", "success", result);
        assertNotNull("No organization collection", sut.getOrganizations());
        assertEquals("Wrong size", 1, sut.getOrganizations().size());
        assertEquals("Wrong organization", "org (TYPE)", sut.getOrganizations().get("org"));
    }

    /**
     * Test the loadOrganizations method with no criteria.
     */
    @Test
    public void testLoadOrganizationsNoCriteria() {
        Summary4ReportAction sut = createSummary4ReportAction();
        String result = sut.loadOrganizations();
        assertEquals("Wrong result returned", "success", result);
        assertNotNull("No organization collection", sut.getOrganizations());
        assertEquals("Wrong size", 0, sut.getOrganizations().size());
    }

    /**
     * Test the loadOrganizations method with no familiId.
     */
    @Test
    public void testLoadOrganizationsNoFamilyId() {
        Summary4ReportAction sut = createSummary4ReportAction();
        sut.setCriteria(new Summ4RepCriteriaWebDto());
        String result = sut.loadOrganizations();
        assertEquals("Wrong result returned", "success", result);
        assertNotNull("No organization collection", sut.getOrganizations());
        assertEquals("Wrong size", 0, sut.getOrganizations().size());
    }

    /**
     * Test the loadOrganizations methods in the success case.
     * @throws TooManyResultsException in case of error
     */
    @Test
    public void testLoadOrganizationsError() throws TooManyResultsException {
        Summary4ReportAction sut = createSummary4ReportActionMock();
        doCallRealMethod().when(sut).loadOrganizations();
        doCallRealMethod().when(sut).getCriteria();
        doCallRealMethod().when(sut).setCriteria(any(Summ4RepCriteriaWebDto.class));
        doCallRealMethod().when(sut).getOrganizations();
        doCallRealMethod().when(sut).getUserRole();
        Summ4RepCriteriaWebDto criteria = new Summ4RepCriteriaWebDto();
        criteria.setFamilyId("1");
        sut.setCriteria(criteria);
        when(summary4ReportService.getOrganizations("1", 100)).thenThrow(new TooManyResultsException(200));
        String result = sut.loadOrganizations();
        assertEquals("Wrong result returned", "success", result);
        verify(sut).addActionError(null);
        assertNotNull("No organization collection", sut.getOrganizations());
        assertEquals("Wrong size", 0, sut.getOrganizations().size());
    }

    /**
     * Test the getReport in case of success.
     */
    @Test
    public void testGetReportSuccess() {
        Summary4ReportAction sut = createSummary4ReportActionMock();
        doCallRealMethod().when(sut).getAgentDeviceMap();
        doCallRealMethod().when(sut).getReport();
        doCallRealMethod().when(sut).getResultList();
        doCallRealMethod().when(sut).setResultList(anyListOf(Summ4ResultWebDto.class));
        doCallRealMethod().when(sut).getUserRole();
        when(sut.isReportInError()).thenReturn(false);
        List<Summ4ResultWebDto> resultList = new ArrayList<Summ4ResultWebDto>();
        Summ4RepResultDto item = new Summ4RepResultDto();
        item.setSubSortCriteria(StConverter.convertToSt("criteria"));
        Summ4ResultWebDto webDto1 = new Summ4ResultWebDto(item);
        resultList.add(webDto1);
        Summ4ResultWebDto webDto2 = new Summ4ResultWebDto(item);
        resultList.add(webDto2);
        sut.setResultList(resultList);
        String result = sut.getReport();
        assertEquals("Wrong result returned", "success", result);
        verify(sut).loadFamilies();
        verify(sut).loadOrganizations();
        verify(sut).isReportInError();
        Map<String, List<Summ4ResultWebDto>> agentDeviceMap = sut.getAgentDeviceMap();
        HttpSession session = ServletActionContext.getRequest().getSession();
        assertEquals("agentDeviceMap not in session", agentDeviceMap,
                     session.getAttribute(ViewerConstants.SUMM4_AGENT_DEVICE_RESULT_MAP));
        assertEquals("Wrong map size", 1, agentDeviceMap.size());
        List<Summ4ResultWebDto> webDtos = agentDeviceMap.get("criteria");
        assertEquals("Wrong webdtos list size", 2, webDtos.size());
        assertEquals("Wrong item returned", webDto1, webDtos.get(0));
        assertEquals("Wrong item returned", webDto2, webDtos.get(1));
    }

    /**
     * Test the getReport in case of error.
     */
    @Test
    public void testGetReportError() {
        Summary4ReportAction sut = createSummary4ReportActionMock();
        doCallRealMethod().when(sut).getReport();
        doCallRealMethod().when(sut).getResultList();
        doCallRealMethod().when(sut).setResultList(anyListOf(Summ4ResultWebDto.class));
        doCallRealMethod().when(sut).getUserRole();
        when(sut.isReportInError()).thenReturn(true);
        String result = sut.getReport();
        assertEquals("Wrong result returned", "success", result);
        verify(sut).loadFamilies();
        verify(sut).loadOrganizations();
        verify(sut).isReportInError();
        assertNull("Wrong result list", sut.getResultList());
    }

    /**
     * Test the isReportInError in case of success.
     * @throws PAException in case of error
     */
    @Test
    public void testIsReportInErrorSuccess() throws PAException {
        Summary4ReportAction sut = createSummary4ReportAction();
        Summ4RepCriteriaWebDto criteria = new Summ4RepCriteriaWebDto();
        criteria.setOrgName("org");
        sut.setCriteria(criteria);
        List<Summ4RepResultDto> items = new ArrayList<Summ4RepResultDto>();
        Summ4RepResultDto item = new Summ4RepResultDto();
        item.setSubSortCriteria(StConverter.convertToSt("criteria"));
        items.add(item);
        when(summary4ReportService.get(any(Summ4RepCriteriaDto.class))).thenReturn(items);
        boolean result = sut.isReportInError();
        assertFalse("Wrong result returned", result);
        Collection<String> errors = sut.getActionErrors();
        assertEquals("Wrong number of errors", 0, errors.size());
        List<Summ4ResultWebDto> resultList = sut.getResultList();
        assertEquals("Wrong resultlist size", 1, resultList.size());
        assertEquals("Wrong result", "criteria", resultList.get(0).getSubSortCriteria());
    }

    /**
     * Test the isReportInError in case of invalid criteria.
     */
    @Test
    public void testIsReportInErrorInvalid() {
        Summary4ReportAction sut = createSummary4ReportAction();
        sut.setCriteria(new Summ4RepCriteriaWebDto());
        boolean result = sut.isReportInError();
        assertTrue("Wrong result returned", result);
    }

    /**
     * Test the isReportInError in case of error.
     * @throws PAException in case of error
     */
    @Test
    public void testIsReportInErrorError() throws PAException {
        Summary4ReportAction sut = createSummary4ReportAction();
        Summ4RepCriteriaWebDto criteria = new Summ4RepCriteriaWebDto();
        criteria.setOrgName("org");
        sut.setCriteria(criteria);
        when(summary4ReportService.get(any(Summ4RepCriteriaDto.class))).thenThrow(new PAException("PA"));
        boolean result = sut.isReportInError();
        assertTrue("Wrong result returned", result);
        Collection<String> errors = sut.getActionErrors();
        assertEquals("Wrong number of errors", 1, errors.size());
        assertEquals("Wrong error message", "PA", errors.iterator().next());
    }

    /**
     * Test the isCriteriaValid in the success case.
     */
    @Test
    public void testIsCriteriaValidSuccess() {
        Summary4ReportAction sut = createSummary4ReportAction();
        Summ4RepCriteriaWebDto criteria = new Summ4RepCriteriaWebDto();
        criteria.setOrgName("org");
        sut.setCriteria(criteria);
        boolean result = sut.isCriteriaValid();
        assertTrue("Wrong result returned", result);
        Collection<String> errors = sut.getActionErrors();
        assertEquals("Wrong number of errors", 0, errors.size());
    }

    /**
     * Test the isCriteriaValid in the error case.
     */
    @Test
    public void testIsCriteriaValidAllError() {
        Summary4ReportAction sut = createSummary4ReportAction();
        Summ4RepCriteriaWebDto criteria = new Summ4RepCriteriaWebDto();
        criteria.setIntervalStartDate(null);
        criteria.setIntervalEndDate(null);
        sut.setCriteria(criteria);
        boolean result = sut.isCriteriaValid();
        assertFalse("Wrong result returned", result);
        Collection<String> errors = sut.getActionErrors();
        assertEquals("Wrong number of errors", 3, errors.size());
        String[] messages = errors.toArray(new String[3]);
        assertEquals("Wrong error message", "An Organization name is required.", messages[0]);
        assertEquals("Wrong error message", "A Start Date is required.", messages[1]);
        assertEquals("Wrong error message", "An End Date is required.", messages[2]);
    }

    /**
     * Test the getOrganizationNames method in the success case.
     * @throws TooManyResultsException in case of error
     * @throws PAException in case of error
     */
    @Test
    public void testgetOrganizationNamesSuccess() throws PAException, TooManyResultsException {
        Summary4ReportAction sut = createSummary4ReportAction();
        sut.setTerm("term");
        List<String> orgs = Arrays.asList("org2", "org1");
        when(summary4ReportService.searchPoOrgNames("term", 100)).thenReturn(orgs);
        String result = sut.getOrganizationNames();
        assertEquals("Wrong result returned", "success", result);
        assertEquals("Wrong result size", 2, sut.getAutoCompleteResult().size());
        assertEquals("Wrong result returned", "org1", sut.getAutoCompleteResult().get(0));
        assertEquals("Wrong result returned", "org2", sut.getAutoCompleteResult().get(1));
    }

    /**
     * Test the getOrganizationNames method in the error case.
     * @throws TooManyResultsException in case of error
     * @throws PAException in case of error
     */
    @Test
    public void testgetOrganizationNamesError() throws PAException, TooManyResultsException {
        Summary4ReportAction sut = createSummary4ReportAction();
        sut.setTerm("term");
        when(summary4ReportService.searchPoOrgNames("term", 100)).thenThrow(new TooManyResultsException(100));
        String result = sut.getOrganizationNames();
        assertEquals("Wrong result returned", "success", result);
        assertEquals("Wrong result size", 1, sut.getAutoCompleteResult().size());
        assertEquals("Wrong result returned", "term", sut.getAutoCompleteResult().get(0));
    }

}
