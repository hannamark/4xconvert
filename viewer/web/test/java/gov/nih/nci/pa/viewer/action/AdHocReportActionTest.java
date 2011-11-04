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
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.dto.PaOrganizationDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.IdentifierType;
import gov.nih.nci.pa.iso.dto.PlannedMarkerDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
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
import org.junit.Before;
import org.junit.Test;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpServletResponse;
import com.mockrunner.mock.web.MockHttpSession;

public class AdHocReportActionTest extends AbstractReportActionTest<AdHocReportAction> {

    StudyProtocolQueryCriteria criteria;

    @Before
    public void setUp() throws PAException {
        ProtocolQueryServiceLocal protQuerySvc = mock(ProtocolQueryServiceLocal.class);
        gov.nih.nci.pa.viewer.util.ServiceLocator viewerSvcLoc = mock(gov.nih.nci.pa.viewer.util.ServiceLocator.class);
        ViewerServiceLocator.getInstance().setServiceLocator(viewerSvcLoc);
        when(viewerSvcLoc.getProtocolQueryService()).thenReturn(protQuerySvc);
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
        when(protQuerySvc.getStudyProtocolByCriteriaForReporting(any(StudyProtocolQueryCriteria.class))).thenReturn(protList);
        TSRReportGeneratorServiceRemote tsrSvcRemote = mock(TSRReportGeneratorServiceRemote.class);
        ServiceLocator svcLoc = mock(ServiceLocator.class);
        PaRegistry.getInstance().setServiceLocator(svcLoc);
        when(svcLoc.getTSRReportGeneratorService()).thenReturn(tsrSvcRemote);
        when(tsrSvcRemote.generateRtfTsrReport(any(Ii.class))).thenReturn(new ByteArrayOutputStream());
        PAOrganizationServiceRemote paOrgSvc = mock(PAOrganizationServiceRemote.class);
        when(svcLoc.getPAOrganizationService()).thenReturn(paOrgSvc);
        List<PaOrganizationDTO> paOrgDtoList = new ArrayList<PaOrganizationDTO>();
        PaOrganizationDTO paOrgDto = new PaOrganizationDTO();
        paOrgDto.setName("Org Name");
        paOrgDtoList.add(paOrgDto);
        when(paOrgSvc.getOrganizationsAssociatedWithStudyProtocol(any(String.class))).thenReturn(paOrgDtoList);
        action =  new AdHocReportAction();
        criteria = new StudyProtocolQueryCriteria();
        action.setCriteria(criteria);
    }

    @Test
    public void testExecute() throws PAException {
        assertEquals("success", action.execute());
    }

    @Test
    public void testFillDiseaseId() throws PAException {
        assertEquals("success", action.fillInDiseaseId());
    }

    @Test
    public void testQueryTypeButNoIdentifier() throws PAException {
        action.setCriteria(new StudyProtocolQueryCriteria());
        action.getCriteria().setIdentifierType("anything");
        assertEquals("success", action.getReport());
        assertEquals("error.studyProtocol.identifier", action.getFieldErrors().get("identifier").get(0));
    }

    @Test
    public void testQueryIdentifierNoType() throws PAException {
        action.setCriteria(new StudyProtocolQueryCriteria());
        action.setIdentifier("anything");
        assertEquals("success", action.getReport());
        assertEquals("error.studyProtocol.identifierType", action.getFieldErrors().get("criteria.identifierType").get(0));
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

    @Test
    public void testViewTSR() throws PAException {
        getRequest().setupAddParameter("studyProtocolId", "1");
        assertEquals("none", action.viewTSR());
    }

    @Test
    public void testCheckLeadOrgAndSumm4SponsorLists() throws PAException {
        assertEquals(1, action.getLeadOrgList().size());
        assertEquals(1, action.getSumm4FunsingSponsorsList().size());
    }
    
    @Test
    public void getParticipatingSiteList() throws PAException {
        ServiceLocator svcLoc = createServiceLocatorWithMocks();
        action.getParticipatingSiteList();
        verify(svcLoc.getPAOrganizationService())
            .getOrganizationsAssociatedWithStudyProtocol(PAConstants.PARTICIPATING_SITE);
    }

    @Test
    public void getAnatomicSitesList() throws PAException {
        ServiceLocator svcLoc = createServiceLocatorWithMocks();
        action.getAnatomicSitesList();
        verify(svcLoc.getLookUpTableService()).getAnatomicSites();
    }
    
    @Test
    public void getPlannedMarkersList() throws PAException {
        ServiceLocator svcLoc = createServiceLocatorWithMocks();
        action.getPlannedMarkersList();
        verify(svcLoc.getPlannedMarkerService()).getAll();
    }
    
    @Test
    public void getPlannedMarkersListConvertion() throws PAException {
        ServiceLocator svcLoc = createServiceLocatorWithMocks();
        
        List<PlannedMarkerDTO> dtos = new ArrayList<PlannedMarkerDTO>();
        PlannedMarkerDTO dto1 = new PlannedMarkerDTO();
        dto1.setIdentifier(IiConverter.convertToIi(2L));
        dto1.setLongName(StConverter.convertToSt("name1"));
        dtos.add(dto1);
        
        when(svcLoc.getPlannedMarkerService().getAll()).thenReturn(dtos);
        
        List<KeyValueDTO> result = action.getPlannedMarkersList();
        assertEquals(Long.valueOf(2), result.get(0).getKey());
        assertEquals("name1", result.get(0).getValue());
        
    }

    private ServiceLocator createServiceLocatorWithMocks() {
        ServiceLocator svcLoc = mock(ServiceLocator.class);
        PaRegistry.getInstance().setServiceLocator(svcLoc);
        PAOrganizationServiceRemote paOrganizationServiceMock = mock(PAOrganizationServiceRemote.class);
        when(svcLoc.getPAOrganizationService()).thenReturn(paOrganizationServiceMock);
        LookUpTableServiceRemote lookUpTableServiceRemoteMock = mock(LookUpTableServiceRemote.class);
        when(svcLoc.getLookUpTableService()).thenReturn(lookUpTableServiceRemoteMock);
        PlannedMarkerServiceLocal plannedMarkerServiceLocal = mock(PlannedMarkerServiceLocal.class);
        when(svcLoc.getPlannedMarkerService()).thenReturn(plannedMarkerServiceLocal);
        return svcLoc;
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
    protected MockHttpServletResponse  getResponse() {
        return (MockHttpServletResponse) ServletActionContext.getResponse();
    }
}