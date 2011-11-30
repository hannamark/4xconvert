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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.AnatomicSite;
import gov.nih.nci.pa.dto.PaOrganizationDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.IdentifierType;
import gov.nih.nci.pa.iso.dto.PlannedMarkerDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.noniso.dto.PDQDiseaseNode;
import gov.nih.nci.pa.report.service.Summ4RepLocal;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PDQDiseaseServiceLocal;
import gov.nih.nci.pa.service.PlannedMarkerServiceLocal;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.service.util.PAOrganizationServiceRemote;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.service.util.TSRReportGeneratorServiceRemote;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.ServiceLocator;
import gov.nih.nci.pa.viewer.dto.result.KeyValueDTO;
import gov.nih.nci.pa.viewer.util.ViewerServiceLocator;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpServletResponse;
import com.mockrunner.mock.web.MockHttpSession;
import com.opensymphony.xwork2.Action;

/**
 * Tests for AdHocReportAction.
 * 
 * @author Michael Visee
 */
public class AdHocReportActionTest extends AbstractReportActionTest<AdHocReportAction> {
    
    private PDQDiseaseServiceLocal diseaseService = mock(PDQDiseaseServiceLocal.class);
    private LookUpTableServiceRemote lookUpTableService = mock(LookUpTableServiceRemote.class);
    private PAOrganizationServiceRemote paOrganizationService = mock(PAOrganizationServiceRemote.class);
    private PlannedMarkerServiceLocal plannedMarkerService = mock(PlannedMarkerServiceLocal.class);
    private ProtocolQueryServiceLocal protocolQueryService = mock(ProtocolQueryServiceLocal.class);
    private TSRReportGeneratorServiceRemote tsrReportGeneratorService = mock(TSRReportGeneratorServiceRemote.class);
    private Summ4RepLocal summ4ReportService = mock(Summ4RepLocal.class);
    
    private StudyProtocolQueryCriteria criteria;

    @Before
    public void setUp() throws PAException {
        ServiceLocator serviceLocator = mock(ServiceLocator.class);
        PaRegistry.getInstance().setServiceLocator(serviceLocator);
        when(serviceLocator.getDiseaseService()).thenReturn(diseaseService);
        when(serviceLocator.getLookUpTableService()).thenReturn(lookUpTableService);
        when(serviceLocator.getPAOrganizationService()).thenReturn(paOrganizationService);
        when(serviceLocator.getPlannedMarkerService()).thenReturn(plannedMarkerService);
        when(serviceLocator.getProtocolQueryService()).thenReturn(protocolQueryService);
        when(serviceLocator.getTSRReportGeneratorService()).thenReturn(tsrReportGeneratorService);
        gov.nih.nci.pa.viewer.util.ServiceLocator viewerServiceLocator = 
                mock(gov.nih.nci.pa.viewer.util.ServiceLocator.class);
        ViewerServiceLocator.getInstance().setServiceLocator(viewerServiceLocator);
        when(viewerServiceLocator.getSumm4ReportService()).thenReturn(summ4ReportService);

        List<StudyProtocolQueryDTO> protList = new ArrayList<StudyProtocolQueryDTO>();
        StudyProtocolQueryDTO protListItem = new StudyProtocolQueryDTO();
        protListItem.setLeadOrganizationId(1L);
        protListItem.setLeadOrganizationName("some name");
        protListItem.setDocumentWorkflowStatusCode(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE);
        protListItem.setDocumentWorkflowStatusDate(new Date());
        protListItem.setNciIdentifier("NCI-2011-000001");
        protListItem.setPiFullName("PI");
        protListItem.setDiseaseNames(new TreeSet<String>());
        protListItem.getDiseaseNames().add("cancer");
        protListItem.setInterventionType(new TreeSet<String>());
        protListItem.getInterventionTypes().add("Drug");
        protListItem.setSumm4FundingSrcCategory("NATIONAL");
        protList.add(protListItem);
        when(protocolQueryService.getStudyProtocolByCriteriaForReporting(any(StudyProtocolQueryCriteria.class)))
            .thenReturn(protList);
        when(tsrReportGeneratorService.generateRtfTsrReport(any(Ii.class))).thenReturn(new ByteArrayOutputStream());

        List<PaOrganizationDTO> paOrgDtoList = new ArrayList<PaOrganizationDTO>();
        PaOrganizationDTO paOrgDto = new PaOrganizationDTO();
        paOrgDto.setName("Org Name");
        paOrgDto.setId("1");
        paOrgDtoList.add(paOrgDto);
        when(paOrganizationService.getOrganizationsAssociatedWithStudyProtocol(any(String.class)))
            .thenReturn(paOrgDtoList);
        action = new AdHocReportAction();
        criteria = new StudyProtocolQueryCriteria();
        action.setCriteria(criteria);
        action.prepare();
    }
    
    /**
     * Creates a real AdHocReportAction and inject the mock services in it.
     * @return A real AdHocReportAction with mock services injected.
     */
    private AdHocReportAction createAdHocReportAction() {
        AdHocReportAction action = new AdHocReportAction();
        setDependencies(action);
        return action;
    }

    /**
     * Creates a mock AdHocReportAction and inject the mock services in it.
     * @return A mock AdHocReportAction with mock services injected.
     */
    private AdHocReportAction createAdHocReportActionMock() {
        AdHocReportAction action = mock(AdHocReportAction.class);
        doCallRealMethod().when(action).setDiseaseService(diseaseService);
        doCallRealMethod().when(action).setLookUpTableService(lookUpTableService);
        doCallRealMethod().when(action).setPaOrganizationService(paOrganizationService);
        doCallRealMethod().when(action).setPlannedMarkerService(plannedMarkerService);
        doCallRealMethod().when(action).setProtocolQueryService(protocolQueryService);
        doCallRealMethod().when(action).setTsrReportGeneratorService(tsrReportGeneratorService);
        doCallRealMethod().when(action).setSumm4ReportService(summ4ReportService);
        setDependencies(action);
        return action;
    }
    
    private void setDependencies(AdHocReportAction action) {
        action.setDiseaseService(diseaseService);
        action.setLookUpTableService(lookUpTableService);
        action.setPaOrganizationService(paOrganizationService);
        action.setPlannedMarkerService(plannedMarkerService);
        action.setProtocolQueryService(protocolQueryService);
        action.setTsrReportGeneratorService(tsrReportGeneratorService);
        action.setSumm4ReportService(summ4ReportService);
    }
    
    /**
     * Test the prepare method.
     */
    @Test
    public void testPrepare() {
        AdHocReportAction sut = mock(AdHocReportAction.class);
        doCallRealMethod().when(sut).prepare();
        ServiceLocator serviceLocator = mock(ServiceLocator.class);
        PaRegistry.getInstance().setServiceLocator(serviceLocator);
        when(serviceLocator.getDiseaseService()).thenReturn(diseaseService);
        when(serviceLocator.getLookUpTableService()).thenReturn(lookUpTableService);
        when(serviceLocator.getPAOrganizationService()).thenReturn(paOrganizationService);
        when(serviceLocator.getPlannedMarkerService()).thenReturn(plannedMarkerService);
        when(serviceLocator.getProtocolQueryService()).thenReturn(protocolQueryService);
        when(serviceLocator.getTSRReportGeneratorService()).thenReturn(tsrReportGeneratorService);
        sut.prepare();
        verify(sut).setDiseaseService(diseaseService);
        verify(sut).setLookUpTableService(lookUpTableService);
        verify(sut).setPaOrganizationService(paOrganizationService);
        verify(sut).setPlannedMarkerService(plannedMarkerService);
        verify(sut).setProtocolQueryService(protocolQueryService);
        verify(sut).setTsrReportGeneratorService(tsrReportGeneratorService);
    }
    
    /**
     * Test the execute method.
     */
    @Test
    public void testExecute() {
        AdHocReportAction sut = createAdHocReportActionMock();
        doCallRealMethod().when(sut).execute();
        doCallRealMethod().when(sut).getUserRole();
        doCallRealMethod().when(sut).getCriteria();
        doCallRealMethod().when(sut).setCriteria(any(StudyProtocolQueryCriteria.class));
        String result = sut.execute();
        assertEquals("Wrong result returned", "success", result);
        assertNotNull("No criteria created", sut.getCriteria());
    }

    @Test
    public void testQueryIdentifierTypes() throws PAException {
        action.setCriteria(new StudyProtocolQueryCriteria());
        action.setIdentifier("anything");
        action.getCriteria().setIdentifierType(IdentifierType.NCI.getCode());
        assertEquals("success", action.getReport());
        assertEquals(1, action.getResultList().size());
        action.getCriteria().setIdentifierType(IdentifierType.NCT.getCode());
        assertEquals("success", action.getReport());
        assertEquals(1, action.getResultList().size());
        action.getCriteria().setIdentifierType(IdentifierType.CTEP.getCode());
        assertEquals("success", action.getReport());
        assertEquals(1, action.getResultList().size());
        action.getCriteria().setIdentifierType(IdentifierType.DCP.getCode());
        assertEquals("success", action.getReport());
        assertEquals(1, action.getResultList().size());
        action.getCriteria().setIdentifierType(IdentifierType.LEAD_ORG.getCode());
        assertEquals("success", action.getReport());
        assertEquals(1, action.getResultList().size());
        action.getCriteria().setIdentifierType(IdentifierType.OTHER_IDENTIFIER.getCode());
        assertEquals("success", action.getReport());
        assertEquals(1, action.getResultList().size());

        assertEquals("cancer", action.getResultList().get(0).getDiseaseNames().iterator().next());
        assertEquals("Drug", action.getResultList().get(0).getInterventionTypes().iterator().next());
        assertEquals("NATIONAL", action.getResultList().get(0).getSumm4FundingSrcCategory());
    }
   
    /**
     * Test the isReportInError in case of field error.
     */
    @Test
    public void testIsReportInErrorFieldError() {
        AdHocReportAction sut = createAdHocReportActionMock();
        doCallRealMethod().when(sut).isReportInError();
        when(sut.hasFieldErrors()).thenReturn(true);
        boolean result = sut.isReportInError();
        verify(sut).validateIdentifierSearchParameters();
        assertTrue(result);
    }
    
    /**
     * Test the isReportInError in case of report error.
     * @throws PAException in case of error
     */
    @Test
    public void testIsReportInErrorReportError() throws PAException {
        StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
        AdHocReportAction sut = createAdHocReportActionMock();
        doCallRealMethod().when(sut).isReportInError();
        doCallRealMethod().when(sut).setCriteria(criteria);
        sut.setCriteria(criteria);
        when(sut.hasFieldErrors()).thenReturn(false);
        when(protocolQueryService.getStudyProtocolByCriteriaForReporting(criteria)).thenThrow(new PAException("PA"));
        boolean result = sut.isReportInError();
        verify(sut).validateIdentifierSearchParameters();
        verify(sut).hasFieldErrors();
        verify(sut).populateIdentifierSearchParameters();
        verify(sut).addActionError("PA");
        assertTrue(result);
    }
    
    /**
     * Test the isReportInError in case of success.
     * @throws PAException in case of error
     */
    @Test
    public void testIsReportInErrorSuccess() throws PAException {
        StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
        AdHocReportAction sut = createAdHocReportActionMock();
        doCallRealMethod().when(sut).isReportInError();
        doCallRealMethod().when(sut).setCriteria(criteria);
        doCallRealMethod().when(sut).getResultList();
        doCallRealMethod().when(sut).setResultList(anyListOf(StudyProtocolQueryDTO.class));
        sut.setCriteria(criteria);
        when(sut.hasFieldErrors()).thenReturn(false);
        List<StudyProtocolQueryDTO> resultList = new ArrayList<StudyProtocolQueryDTO>();
        when(protocolQueryService.getStudyProtocolByCriteriaForReporting(criteria)).thenReturn(resultList);
        boolean result = sut.isReportInError();
        verify(sut).validateIdentifierSearchParameters();
        verify(sut).hasFieldErrors();
        verify(sut).populateIdentifierSearchParameters();
        verify(sut).setResultList(resultList);
        verify(sut, never()).addActionError(anyString());
        assertFalse(result);
        assertEquals("Wrong result list", resultList, sut.getResultList());
    }

    /**
     * Test the validateIdentifierSearchParameters with a type error.
     */
    @Test
    public void testValidateIdentifierSearchParametersTypeError() {
        AdHocReportAction sut = createAdHocReportAction();
        StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
        criteria.setIdentifierType("type");
        sut.setCriteria(criteria);
        sut.validateIdentifierSearchParameters();
        assertTrue(sut.getFieldErrors().containsKey("identifier"));
    }

    /**
     * Test the validateIdentifierSearchParameters with an identifier error.
     */
    @Test
    public void testValidateIdentifierSearchParametersIdentifierError() {
        AdHocReportAction sut = createAdHocReportAction();
        StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
        sut.setCriteria(criteria);
        sut.setIdentifier("identifier");
        sut.validateIdentifierSearchParameters();
        assertTrue(sut.getFieldErrors().containsKey("criteria.identifierType"));
    }

    /**
     * Test the validateIdentifierSearchParameters with no error.
     */
    @Test
    public void testValidateIdentifierSearchParametersNoError() {
        AdHocReportAction sut = createAdHocReportAction();
        StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
        criteria.setIdentifierType("type");
        sut.setCriteria(criteria);
        sut.setIdentifier("identifier");
        sut.validateIdentifierSearchParameters();
        assertTrue(sut.getFieldErrors().isEmpty());
    }

    /**
     * Test the validateIdentifierSearchParameters with no data.
     */
    @Test
    public void testValidateIdentifierSearchParametersNoData() {
        AdHocReportAction sut = createAdHocReportAction();
        StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
        sut.setCriteria(criteria);
        sut.validateIdentifierSearchParameters();
        assertTrue(sut.getFieldErrors().isEmpty());
    }

    /**
     * Test the populateIdentifierSearchParameters method.
     */
    @Test
    public void testPopulateIdentifierSearchParameters() {
        String identifierType = IdentifierType.NCI.getCode();
        String identifier = "identifier";
        AdHocReportAction sut = createAdHocReportAction();
        StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
        criteria.setIdentifierType(identifierType);
        sut.setCriteria(criteria);
        sut.setIdentifier(identifier);
        sut.populateIdentifierSearchParameters();
        assertEquals("Wrong identifier", identifier, criteria.getNciIdentifier());
    }
    
    /**
     * test the viewTSR method in the successful case.
     * @throws PAException in case of error
     */
    @Test
    public void testViewTSR() throws PAException {
        AdHocReportAction sut = createAdHocReportAction();
        getRequest().setupAddParameter("studyProtocolId", "1");
        MockHttpServletResponse response = getResponse();
        sut.setServletResponse(response);
        ByteArrayOutputStream reportData = new ByteArrayOutputStream();
        reportData.write(65);
        when(tsrReportGeneratorService.generateRtfTsrReport(any(Ii.class))).thenReturn(reportData);
        String result = sut.viewTSR();
        assertEquals("Wrong result returned", "none", result);
        assertEquals("Wrong Content-disposition", "inline; filename=TsrReport.rtf", response.getHeader("Content-disposition"));
        assertEquals("Wrong Content Type", "application/rtf;", response.getContentType());
        assertEquals("", "A", response.getOutputStreamContent());
    }

    /**
     * test the viewTSR method in the exception case.
     * @throws PAException in case of error
     */
    @Test
    public void testViewTSRError() throws PAException {
        AdHocReportAction sut = createAdHocReportAction();
        getRequest().setupAddParameter("studyProtocolId", "1");  
        when(tsrReportGeneratorService.generateRtfTsrReport(any(Ii.class))).thenThrow(new PAException("PA"));
        assertEquals("Wrong result returned", "none", sut.viewTSR());
    }

    /**
     * Test the getLeadOrgList method.
     * @throws PAException in case of error
     */
    @Test
    public void testGetLeadOrgList() throws PAException {
        AdHocReportAction sut = createAdHocReportAction();
        List<PaOrganizationDTO> organizations = new ArrayList<PaOrganizationDTO>();
        when(paOrganizationService.getOrganizationsAssociatedWithStudyProtocol(PAConstants.LEAD_ORGANIZATION))
            .thenReturn(organizations);
        assertEquals("Wrong result returned", organizations, sut.getLeadOrgList());
    }
    
    /**
     * Test the getSumm4FunsingSponsorsList method.
     * @throws PAException in case of error
     */
    @Test
    public void testGetSumm4FunsingSponsorsList() throws PAException {
        AdHocReportAction sut = createAdHocReportAction();
        List<PaOrganizationDTO> organizations = new ArrayList<PaOrganizationDTO>();
        when(paOrganizationService.getOrganizationsAssociatedWithStudyProtocol(PAConstants.SUMM4_SPONSOR))
            .thenReturn(organizations);
        assertEquals("Wrong result returned", organizations, sut.getSumm4FunsingSponsorsList());
    }

    /**
     * Test the getParticipatingSiteList method.
     * @throws PAException in case of error
     */
    @Test
    public void testGetParticipatingSiteList() throws PAException {
        AdHocReportAction sut = createAdHocReportAction();
        List<PaOrganizationDTO> organizations = new ArrayList<PaOrganizationDTO>();
        when(paOrganizationService.getOrganizationsAssociatedWithStudyProtocol(PAConstants.PARTICIPATING_SITE))
            .thenReturn(organizations);
        assertEquals("Wrong result returned", organizations, sut.getParticipatingSiteList());
    }

    /**
     * Test the getAnatomicSitesList method.
     * @throws PAException in case of error
     */
    @Test
    public void testGetAnatomicSitesList() throws PAException {
        AdHocReportAction sut = createAdHocReportAction();
        List<AnatomicSite> anatomicSites = new ArrayList<AnatomicSite>();
        when(lookUpTableService.getAnatomicSites()).thenReturn(anatomicSites);
        assertEquals("Wrong result returned", anatomicSites, sut.getAnatomicSitesList());
    }

    /**
     * Test the getPlannedMarkersList metod.
     * @throws PAException in case of error
     */
    @Test
    public void testGetPlannedMarkersList() throws PAException {
        AdHocReportAction sut = createAdHocReportAction();
        List<PlannedMarkerDTO> markers = createPlannedMarkerDTOs();
        when(plannedMarkerService.getAll()).thenReturn(markers);
        List<KeyValueDTO> result = sut.getPlannedMarkersList();
        assertNotNull("No result returned", result);
        assertEquals("Wrong result size", 1, result.size());
        KeyValueDTO keyValue = result.get(0);
        assertEquals("Wrong key", 1L, keyValue.getKey().longValue());
        assertEquals("Wrong value", "Long Name", keyValue.getValue());
    }
    
    private List<PlannedMarkerDTO> createPlannedMarkerDTOs() {
        List<PlannedMarkerDTO> markers = new ArrayList<PlannedMarkerDTO>();
        PlannedMarkerDTO marker = new PlannedMarkerDTO();
        marker.setIdentifier(IiConverter.convertToIi(1L));
        marker.setLongName(StConverter.convertToSt("Long Name"));
        markers.add(marker);
        return markers;
    }
    /**
     * Test the getDisplayTree method.
     * @throws JSONException if an error occurs.
     */
    @Test
    public void testGetDisplayTree() throws JSONException {
        List<PDQDiseaseNode> tree = new ArrayList<PDQDiseaseNode>();
        PDQDiseaseNode node = new PDQDiseaseNode();
        node.setId(1L);
        node.setParentId(2L);
        node.setName("name value");
        tree.add(node);
        when(diseaseService.getDiseaseTree()).thenReturn(tree);
        String result = action.getDiseaseTree();
        assertEquals("Wrong json string returned", "[{\"id\":1,\"name\":\"name value\",\"parentId\":2}]", result);
    }
    
    /**
     * test loadOrganizations without family id
     * 
     * @throws TooManyResultsException 
     */
    @Test
    public void loadOrganizationsWithoutFamilyId() throws TooManyResultsException {
        // user selects type of report
        assertEquals(Action.SUCCESS, action.loadOrganizations());
        verify(summ4ReportService, never()).getOrganizations(anyString(), anyInt());
        assertEquals(0, action.getActionErrors().size());
    }
    
    /**
    * test loadOrganizations without criteria
    * 
    * @throws TooManyResultsException 
    */
   @Test
   public void loadOrganizationsWithoutCriteria() throws TooManyResultsException {       
       assertEquals(Action.SUCCESS, action.loadOrganizations());
       criteria = null;
       verify(summ4ReportService, never()).getOrganizations(anyString(), anyInt());
       assertEquals(0, action.getActionErrors().size());
   }
    
    /**
     * test loadOrganizations with family id
     * 
     * @throws TooManyResultsException 
     */
    @Test
    public void loadOrganizationsWithFamilyId() throws TooManyResultsException {
        String familyId = "1";
        criteria.setFamilyId(familyId);
        assertEquals(Action.SUCCESS, action.loadOrganizations());
        verify(summ4ReportService).getOrganizations(familyId, 100);
        assertEquals(0, action.getActionErrors().size());
    }
    
    /**
    * test loadParticipatingSites without family id
    * 
    * @throws TooManyResultsException 
    */
   @Test
   public void loadParticipatingSitesWithoutFamilyId() throws TooManyResultsException {
       // user selects type of report
       assertEquals(Action.SUCCESS, action.loadParticipatingSites());
       verify(summ4ReportService, never()).getOrganizations(anyString(), anyInt());
       assertEquals(0, action.getActionErrors().size());
   }
   
   /**
    * test loadParticipatingSites without criteria
    * 
    * @throws TooManyResultsException 
    */
   @Test
   public void loadParticipatingSitesWithoutCriteria() throws TooManyResultsException {       
       assertEquals(Action.SUCCESS, action.loadParticipatingSites());
       criteria = null;
       verify(summ4ReportService, never()).getOrganizations(anyString(), anyInt());
       assertEquals(0, action.getActionErrors().size());
   }
   
   /**
    * test loadParticipatingSites with family id
    * 
    * @throws TooManyResultsException 
    */
   @Test
   public void loadParticipatingSitesWithFamilyId() throws TooManyResultsException {
       String familyId = "1";
       criteria.setParticipatingSiteFamilyId(familyId);
       assertEquals(Action.SUCCESS, action.loadParticipatingSites());
       verify(summ4ReportService).getOrganizations(familyId, 100);
       assertEquals(0, action.getActionErrors().size());
   }
   
    
    /**
     * 
     * test loadFamilies.
     * @throws TooManyResultsException 
     */
    @Test
    public void loadFamilies() throws TooManyResultsException {       
        action.loadFamilies();
        verify(summ4ReportService).getFamilies(100);
        assertEquals(0, action.getActionErrors().size());
    }
    
    
    /**
     * Test the getReport in case of success.
     */
    @Test
    public void testGetReportSuccess() {
        AdHocReportAction action = createAdHocReportActionMock();;
        
        doCallRealMethod().when(action).getReport();
        doCallRealMethod().when(action).getResultList();
        doCallRealMethod().when(action).setResultList(anyListOf(StudyProtocolQueryDTO.class));
        doCallRealMethod().when(action).getUserRole();
        when(action.isReportInError()).thenReturn(false);
        List<StudyProtocolQueryDTO> resultList = new ArrayList<StudyProtocolQueryDTO>();
        StudyProtocolQueryDTO sp1 = new StudyProtocolQueryDTO();
        StudyProtocolQueryDTO sp2 = new StudyProtocolQueryDTO();
        resultList.add(sp1);
        resultList.add(sp2);
        action.setResultList(resultList);
        String result = action.getReport();
        assertEquals("Wrong result returned", "success", result);
        verify(action).loadFamilies();
        verify(action).loadOrganizations();
        verify(action).loadParticipatingSites();
        verify(action).isReportInError();
        
        assertEquals("Wrong webdtos list size", 2, action.getResultList().size());
        assertEquals("Wrong result list", resultList, action.getResultList());       
    }
    
    
    
    /**
     * Test the getReport in case of error.
     */
    @Test
    public void testGetReportError() {
        AdHocReportAction action = createAdHocReportActionMock();
        doCallRealMethod().when(action).getReport();
        doCallRealMethod().when(action).getResultList();
        doCallRealMethod().when(action).setResultList(anyListOf(StudyProtocolQueryDTO.class));
        doCallRealMethod().when(action).getUserRole();
        when(action.isReportInError()).thenReturn(true);
        String result = action.getReport();
        assertEquals("Wrong result returned", "success", result);
        verify(action).loadFamilies();
        verify(action).loadOrganizations();
        verify(action).loadParticipatingSites();
        verify(action).isReportInError();
        assertNull("Wrong result list", action.getResultList());
    }
    
    /**
     * @return MockHttpServletRequest
     */
    protected MockHttpServletRequest getRequest() {
        return (MockHttpServletRequest) ServletActionContext.getRequest();
    }

    /**
     * @return MockHttpSession
     */
    protected MockHttpSession getSession() {
        return (MockHttpSession) ServletActionContext.getRequest().getSession();
    }

    /**
     * Gets the response.
     * 
     * @return the response
     */
    protected MockHttpServletResponse getResponse() {
        return (MockHttpServletResponse) ServletActionContext.getResponse();
    }
    
    
}