/**
 *
 */
package gov.nih.nci.registry.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import gov.nih.nci.pa.dto.CountryRegAuthorityDTO;
import gov.nih.nci.pa.dto.PaOrganizationDTO;
import gov.nih.nci.pa.dto.RegulatoryAuthOrgDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.registry.dto.TrialDTO;
import gov.nih.nci.registry.dto.TrialFundingWebDTO;
import gov.nih.nci.registry.dto.TrialIndIdeDTO;
import gov.nih.nci.registry.util.Constants;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.junit.Test;
import org.mockito.InOrder;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpSession;

/**
 * @author Vrushali
 *
 */
public class UpdateTrialActionTest extends AbstractRegWebTest {
    private final UpdateTrialAction action = new UpdateTrialAction();
    @Test
    public void testTrialActionProperty(){
       assertNull(action.getTrialAction());
       action.setTrialAction("trialAction");
       assertNotNull(action.getTrialAction());
    }
    @Test
    public void testStudyProtocolIdProperty(){
       assertNull(action.getStudyProtocolId ());
       action.setStudyProtocolId("studyProtocolId");
       assertNotNull(action.getStudyProtocolId());
    }
    @Test
    public void testPropertyIRBDoc() {
        assertNull(action.getIrbApproval());
        action.setIrbApproval(new File("irbApprovalFileName.doc"));
        assertNotNull(action.getIrbApproval());

    }
    @Test
    public void testProtocolFileNameProperty(){
        assertNull(action.getIrbApprovalFileName());
        action.setIrbApprovalFileName("irbApprovalFileName");
        assertNotNull(action.getIrbApprovalFileName());
    }
    @Test
    public void testTrialDTOProperty() {
        assertNotNull(action.getTrialDTO());
        action.setTrialDTO(null);
        assertNull(action.getTrialDTO());
    }
    @Test
    public void testCollaboratorsListProperty(){
        assertNotNull(action.getCollaborators());
        action.setCollaborators(null);
        assertNull(action.getCollaborators());
    }
    @Test
    public void testParticipatingSitesListProperty() {
        assertNotNull(action.getParticipatingSitesList());
        action.setParticipatingSitesList(null);
        assertNull(action.getParticipatingSitesList());
    }
    @Test
    public void testIndIdeUpdateDtosProperty() {
        assertNotNull(action.getIndIdeUpdateDtos());
        action.setIndIdeUpdateDtos(null);
        assertNull(action.getIndIdeUpdateDtos());
    }
    @Test
    public void testIndIdeAddDtosProperty() {
        assertNotNull(action.getIndIdeAddDtos());
        action.setIndIdeAddDtos(null);
        assertNull(action.getIndIdeAddDtos());
    }
    @Test
    public void testFundingAddDtosProperty() {
        assertNotNull(action.getFundingAddDtos());
        action.setFundingAddDtos(null);
        assertNull(action.getFundingAddDtos());
    }
    @Test
    public void testFundingDtosProperty() {
        assertNotNull(action.getFundingDtos());
        action.setFundingDtos(null);
        assertNull(action.getFundingDtos());
    }
    @Test
    public void testProgramcodenihselectedvalueProperty() {
        assertNull(action.getProgramcodenihselectedvalue());
        action.setProgramcodenihselectedvalue("programcodenihselectedvalue");
        assertNotNull(action.getProgramcodenihselectedvalue());
    }
    @Test
    public void testProgramcodenciselectedvalueProperty() {
        assertNull(action.getProgramcodenciselectedvalue());
        action.setProgramcodenciselectedvalue("programcodenciselectedvalue");
        assertNotNull(action.getProgramcodenciselectedvalue());
    }
    @Test
    public void testPaOrganizationDTOProperty() {
        assertNull(action.getPaOrganizationDTO());
        action.setPaOrganizationDTO(new PaOrganizationDTO());
        assertNotNull(action.getPaOrganizationDTO());
    }

    @Test
    public void testTrialFundingDTOProperty() {
        assertNull(action.getTrialFundingDTO());
        action.setTrialFundingDTO(new TrialFundingWebDTO());
        assertNotNull(action.getTrialFundingDTO());
    }
    @Test
    public void testTrialIndIdeDTOProperty() {
        assertNull(action.getTrialIndIdeDTO());
        action.setTrialIndIdeDTO(new TrialIndIdeDTO());
        assertNotNull(action.getTrialIndIdeDTO());
    }
    @Test
    public void testIndIdeUpdateDtosLenProperty() {
        assertEquals(0, action.getIndIdeUpdateDtosLen());
        action.setIndIdeUpdateDtosLen(2);
        assertEquals(2, action.getIndIdeUpdateDtosLen());
    }
    @Test
    public void testCancle() {
        assertEquals("redirect_to_search", action.cancel());
    }
    @Test
    public void testEdit() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        TrialDTO tDto = getMockTrialDTO();
        session.setAttribute("trialDTO", tDto);
        request.setSession(session);
        ServletActionContext.setRequest(request);
        assertEquals("edit", action.edit());
        tDto.setFundingDtos(getfundingDtos());
        tDto.setIndIdeDtos(getIndDtos());
        tDto.setFundingAddDtos(getfundingDtos());
        tDto.setIndIdeAddDtos(getIndDtos());
        List<PaOrganizationDTO> paOrgList = new ArrayList<PaOrganizationDTO>();
        PaOrganizationDTO paOrgDto = new PaOrganizationDTO();
        paOrgDto.setName("name");
        paOrgList.add(paOrgDto);
        tDto.setCollaborators(paOrgList);
        tDto.setParticipatingSites(paOrgList);
        tDto.setSelectedRegAuth("selectedRegAuth");
        tDto.setLst("lst");
        List<RegulatoryAuthOrgDTO> regIdAuthOrgList = new ArrayList<RegulatoryAuthOrgDTO>();
        regIdAuthOrgList.add(new RegulatoryAuthOrgDTO());
        tDto.setRegIdAuthOrgList(regIdAuthOrgList);
        List<TrialIndIdeDTO> indIdeUpdateDtos = new ArrayList<TrialIndIdeDTO>();
        TrialIndIdeDTO indIdeDto = new TrialIndIdeDTO();
        indIdeDto.setIndIde("IND");
        indIdeDto.setGrantor("grantor");
        indIdeDto.setNumber("indldeNumber");
        indIdeDto.setHolderType("NCI");
        indIdeDto.setNciDivProgHolder("nciDivProgHolder");
        indIdeDto.setExpandedAccess("Yes");
        indIdeDto.setExpandedAccessType("expandedAccessStatus");
        indIdeDto.setStudyProtocolId("1");
        indIdeDto.setProgramCode("programCode");
        indIdeDto.setIndIdeId("1");
        indIdeUpdateDtos.add(indIdeDto);
        tDto.setIndIdeUpdateDtos(indIdeUpdateDtos);
        session.setAttribute("trialDTO", tDto);
        request.setSession(session);
        ServletActionContext.setRequest(request);
        assertEquals("edit", action.edit());
    }
    @Test
    public void testView() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        request.setSession(session);
        ServletActionContext.setRequest(request);
        assertEquals("error", action.view());
        request = new MockHttpServletRequest();
        session = new MockHttpSession();
        action.setStudyProtocolId(null);
        request.setupAddParameter("studyProtocolId", "1");
        request.setSession(session);
        ServletActionContext.setRequest(request);
        action.view();
    }
    @Test
    public void testReviewUpdate() throws URISyntaxException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        request.setSession(session);
        ServletActionContext.setRequest(request);
        action.setTrialDTO(getMockTrialDTO());
        action.getTrialDTO().setLst("");
        action.getTrialDTO().setSelectedRegAuth("");
        assertEquals("error", action.reviewUpdate());

        request = new MockHttpServletRequest();
        session = new MockHttpSession();
        session.setAttribute(Constants.COUNTRY_LIST, new ArrayList<CountryRegAuthorityDTO>());
        session.setAttribute(Constants.REG_AUTH_LIST, new ArrayList<RegulatoryAuthOrgDTO>());
        session.setAttribute(Constants.GRANT_ADD_LIST, new ArrayList<TrialFundingWebDTO>());
        session.setAttribute(Constants.INDIDE_ADD_LIST, new ArrayList<TrialIndIdeDTO>());
        action.setTrialDTO(getMockTrialDTO());
        action.getTrialDTO().setDataMonitoringCommitteeAppointedIndicator("dataMonitoringIndicator");
        action.getTrialDTO().setDelayedPostingIndicator("delayedPostingIndicator");
        action.getTrialDTO().setFdaRegulatoryInformationIndicator("fdaRegulatedInterventionIndicator");
        action.getTrialDTO().setSection801Indicator("section801Indicator");
        action.getTrialDTO().setSelectedRegAuth("2");
        action.getTrialDTO().setLst("3");
        action.setIrbApprovalFileName("ProtocolDoc.doc");
        URL fileUrl = ClassLoader.getSystemClassLoader().getResource("ProtocolDoc.doc");
        File f = new File(fileUrl.toURI());
        action.setIrbApproval(f);
        request.setSession(session);
        ServletActionContext.setRequest(request);
        assertEquals("review", action.reviewUpdate());

        request = new MockHttpServletRequest();
        session = new MockHttpSession();
        session.setAttribute(Constants.COUNTRY_LIST, new ArrayList<CountryRegAuthorityDTO>());
        session.setAttribute(Constants.REG_AUTH_LIST, new ArrayList<RegulatoryAuthOrgDTO>());
        session.setAttribute(Constants.GRANT_ADD_LIST, new ArrayList<TrialFundingWebDTO>());
        session.setAttribute(Constants.INDIDE_ADD_LIST, new ArrayList<TrialIndIdeDTO>());
        request.setSession(session);
        action.getTrialDTO().setSelectedRegAuth("2");
        action.getTrialDTO().setLst("3");
        action.setIrbApprovalFileName("ProtocolDoc.doc");
        action.setIrbApproval(new File("ProtocolDoc.doc"));
        ServletActionContext.setRequest(request);
        action.setTrialDTO(getMockTrialDTO());
        assertEquals("error", action.reviewUpdate());
    }
    @Test
    public void testReviewUpdateWithCollaborators() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        request.setSession(session);
        ServletActionContext.setRequest(request);
        action.getTrialDTO().setSelectedRegAuth("2");
        action.getTrialDTO().setLst("3");
        action.setTrialDTO(getMockTrialDTO());
        List<PaOrganizationDTO> paOrgList = new ArrayList<PaOrganizationDTO>();
        PaOrganizationDTO paOrgDto = new PaOrganizationDTO();
        paOrgDto.setName("name");
        paOrgDto.setFunctionalRole("functionalRole");
        paOrgList.add(paOrgDto);
        paOrgDto = new PaOrganizationDTO();
        paOrgDto.setName("name");
        paOrgDto.setFunctionalRole(null);
        paOrgList.add(paOrgDto);
        action.setCollaborators(paOrgList);
        assertEquals("error", action.reviewUpdate());
    }
    @Test
    public void testReviewUpdateWithParticipatingSite() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        request.setSession(session);
        ServletActionContext.setRequest(request);

        action.getTrialDTO().setSelectedRegAuth("2");
        action.getTrialDTO().setLst("3");
        action.setTrialDTO(getMockTrialDTO());
        List<PaOrganizationDTO> paOrgList = new ArrayList<PaOrganizationDTO>();
        PaOrganizationDTO paOrgDto = new PaOrganizationDTO();
        paOrgDto.setName("name");
        paOrgDto.setRecruitmentStatus("recruitmentStatus");
        paOrgDto.setRecruitmentStatusDate("recruitmentStatusDate");
        paOrgList.add(paOrgDto);
        paOrgDto = new PaOrganizationDTO();
        paOrgDto.setName("name");
        paOrgDto.setRecruitmentStatus(null);
        paOrgDto.setRecruitmentStatusDate("recruitmentStatusDate");
        paOrgList.add(paOrgDto);
        action.setParticipatingSitesList(paOrgList);
        assertEquals("error", action.reviewUpdate());

        paOrgList = new ArrayList<PaOrganizationDTO>();
        paOrgDto = new PaOrganizationDTO();
        paOrgDto.setName("name");
        paOrgDto.setRecruitmentStatus("recruitmentStatus");
        paOrgDto.setRecruitmentStatusDate(null);
        paOrgList.add(paOrgDto);
        action.setParticipatingSitesList(paOrgList);
        assertEquals("error", action.reviewUpdate());
    }
    @Test
    public void testReviewUpdateWithFundingDtos() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        request.setSession(session);
        ServletActionContext.setRequest(request);

        action.getTrialDTO().setSelectedRegAuth("2");
        action.getTrialDTO().setLst("3");
        action.setTrialDTO(getMockTrialDTO());
        List<TrialFundingWebDTO> fundingDtos = getfundingDtos();
        TrialFundingWebDTO dto = new TrialFundingWebDTO();
        fundingDtos.add(dto);
        action.setFundingDtos(fundingDtos);
        assertEquals("error", action.reviewUpdate());

        fundingDtos = new ArrayList<TrialFundingWebDTO>();
        dto = new TrialFundingWebDTO();
        dto.setFundingMechanismCode("fundingMechanismCode");
        fundingDtos.add(dto);
        action.setFundingDtos(fundingDtos);
        assertEquals("error", action.reviewUpdate());

        fundingDtos = new ArrayList<TrialFundingWebDTO>();
        dto = new TrialFundingWebDTO();
        dto.setFundingMechanismCode("fundingMechanismCode");
        dto.setNciDivisionProgramCode("nciDivisionProgramCode");
        dto.setStudyProtocolId("1");
        fundingDtos.add(dto);
        action.setFundingDtos(fundingDtos);
        assertEquals("error", action.reviewUpdate());

        fundingDtos = new ArrayList<TrialFundingWebDTO>();
        dto = new TrialFundingWebDTO();
        dto.setFundingMechanismCode("fundingMechanismCode");
        dto.setNciDivisionProgramCode("nciDivisionProgramCode");
        dto.setNihInstitutionCode("nihInstitutionCode");
        fundingDtos.add(dto);
        action.setFundingDtos(fundingDtos);
        assertEquals("error", action.reviewUpdate());
    }
    @Test
    public void testReviewUpdateWithIndIdeDtos() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        request.setSession(session);
        ServletActionContext.setRequest(request);
        action.getTrialDTO().setSelectedRegAuth("2");
        action.getTrialDTO().setLst("3");
        action.setTrialDTO(getMockTrialDTO());
        List<TrialIndIdeDTO> indIdeDtos = new ArrayList<TrialIndIdeDTO>();
        indIdeDtos.add(new TrialIndIdeDTO());
        action.setIndIdeUpdateDtos(indIdeDtos);
        assertEquals("error", action.reviewUpdate());
        indIdeDtos = new ArrayList<TrialIndIdeDTO>();
        TrialIndIdeDTO indIdeDto = new TrialIndIdeDTO();
        indIdeDto.setGrantor("grantor");
        indIdeDtos.add(indIdeDto);
        action.setIndIdeUpdateDtos(indIdeDtos);
        assertEquals("error", action.reviewUpdate());
        indIdeDtos = new ArrayList<TrialIndIdeDTO>();
        indIdeDto = new TrialIndIdeDTO();
        indIdeDto.setGrantor("grantor");
        indIdeDto.setNumber("indldeNumber");
        indIdeDtos.add(indIdeDto);
        action.setIndIdeUpdateDtos(indIdeDtos);
        assertEquals("error", action.reviewUpdate());

        indIdeDtos = new ArrayList<TrialIndIdeDTO>();
        indIdeDto = new TrialIndIdeDTO();
        indIdeDto.setGrantor("grantor");
        indIdeDto.setNumber("indldeNumber");
        indIdeDto.setHolderType("holderType");
        indIdeDto.setExpandedAccess("Yes");
        indIdeDtos.add(indIdeDto);
        action.setIndIdeUpdateDtos(indIdeDtos);
        assertEquals("error", action.reviewUpdate());

        indIdeDtos = new ArrayList<TrialIndIdeDTO>();
        indIdeDto = new TrialIndIdeDTO();
        indIdeDto.setGrantor("grantor");
        indIdeDto.setNumber("indldeNumber");
        indIdeDto.setHolderType("NIH");
        indIdeDto.setExpandedAccessType("Yes");
        indIdeDtos.add(indIdeDto);
        action.setIndIdeUpdateDtos(indIdeDtos);
        assertEquals("error", action.reviewUpdate());
        indIdeDtos = new ArrayList<TrialIndIdeDTO>();
        indIdeDto = new TrialIndIdeDTO();
        indIdeDto.setGrantor("grantor");
        indIdeDto.setNumber("indldeNumber");
        indIdeDto.setHolderType("NCI");
        indIdeDto.setExpandedAccessType("Yes");
        indIdeDtos.add(indIdeDto);
        action.setIndIdeUpdateDtos(indIdeDtos);
        assertEquals("error", action.reviewUpdate());
    }
    @Test
    public void testUpdate() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        TrialDTO tDto = getMockTrialDTO();
        tDto.setIdentifier("1");
        session.setAttribute("trialDTO", tDto);
        request.setSession(session);
        ServletActionContext.setRequest(request);
        assertEquals("redirect_to_search", action.update());
        request = new MockHttpServletRequest();
        session = new MockHttpSession();
        tDto = getMockTrialDTO();
        tDto.setIdentifier("1");
        tDto.setDocDtos(getDocumentDtos());
        tDto.setResponsiblePartyType(TrialDTO.RESPONSIBLE_PARTY_TYPE_SPONSOR);
        tDto.setResponsiblePersonName("responsiblePersonName");
        tDto.setResponsiblePersonIdentifier("12");
        tDto.setResponsibleGenericContactName("responsibleGenericContactName");
        tDto.setIndIdeDtos(getIndDtos());
        tDto.setFundingAddDtos(getfundingDtos());
        session.setAttribute("trialDTO", tDto);
        request.setSession(session);
        ServletActionContext.setRequest(request);
        assertEquals("redirect_to_search", action.update());
    }
    @Test
    public void testUpdateWithSumm4AndGrants() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        TrialDTO tDto = getMockTrialDTO();
        tDto.setSummaryFourOrgIdentifier("1");
        tDto.setSummaryFourOrgName("summaryFourOrgName");
        tDto.setIdentifier("3");
        tDto.setFundingDtos(getfundingDtos());
        tDto.setIndIdeDtos(getIndDtos());
        tDto.setFundingAddDtos(getfundingDtos());
        tDto.setIndIdeAddDtos(getIndDtos());
        session.setAttribute("trialDTO", tDto);
        request.setSession(session);
        ServletActionContext.setRequest(request);
        assertEquals("redirect_to_search", action.update());
    }
    @Test
    public void testUpdateWithIndIde() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        TrialDTO tDto = getMockTrialDTO();
        List<TrialIndIdeDTO> indIdeUpdateDtos = new ArrayList<TrialIndIdeDTO>();
        TrialIndIdeDTO indIdeDto = new TrialIndIdeDTO();
        indIdeDto.setIndIde("IND");
        indIdeDto.setGrantor("grantor");
        indIdeDto.setNumber("indldeNumber");
        indIdeDto.setHolderType("NCI");
        indIdeDto.setNciDivProgHolder("nciDivProgHolder");
        indIdeDto.setExpandedAccessType("Yes");
        indIdeDto.setExpandedAccess("expandedAccessStatus");
        indIdeDto.setIndIdeId("1");
        indIdeUpdateDtos.add(indIdeDto);
        indIdeDto = new TrialIndIdeDTO();
        indIdeDto.setIndIdeId("2");
        indIdeDto.setIndIde("IDE");
        indIdeDto.setGrantor("grantor");
        indIdeDto.setNumber("indldeNumber");
        indIdeDto.setHolderType("NIH");
        indIdeDto.setNihInstHolder("nihInstHolder");
        indIdeDto.setExpandedAccessType("Yes");
        indIdeDto.setExpandedAccess("expandedAccessStatus");
        indIdeUpdateDtos.add(indIdeDto);
        indIdeDto = new TrialIndIdeDTO();
        indIdeDto.setIndIdeId("2");
        indIdeDto.setIndIde("IDE");
        indIdeDto.setGrantor("grantor");
        indIdeDto.setNumber("indldeNumber");
        indIdeDto.setHolderType("NIH");
        indIdeDto.setNihInstHolder("nihInstHolder");
        indIdeDto.setExpandedAccessType("No");
        indIdeDto.setExpandedAccess("");
        indIdeUpdateDtos.add(indIdeDto);
        tDto.setIndIdeUpdateDtos(indIdeUpdateDtos );
        session.setAttribute("trialDTO", tDto);
        request.setSession(session);
        ServletActionContext.setRequest(request);
        assertEquals("redirect_to_search", action.update());
    }
    @Test
    public void testUpdateWithCollaborator() {
        List<PaOrganizationDTO> paOrgList = new ArrayList<PaOrganizationDTO>();
        PaOrganizationDTO paOrgDto = new PaOrganizationDTO();
        paOrgDto.setName("name");
        paOrgDto.setFunctionalRole("functionalRole");
        paOrgDto.setId("1");
        paOrgList.add(paOrgDto);
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        TrialDTO tDto = getMockTrialDTO();
        tDto.setCollaborators(paOrgList);
        session.setAttribute("trialDTO", tDto);
        request.setSession(session);
        ServletActionContext.setRequest(request);
        assertEquals("redirect_to_search", action.update());
    }
    @Test
    public void testUpdateWithParticipatingSite() {
        List<PaOrganizationDTO> paOrgList = new ArrayList<PaOrganizationDTO>();
        PaOrganizationDTO paOrgDto = new PaOrganizationDTO();
        paOrgDto.setName("name");
        paOrgDto.setRecruitmentStatus("recruitmentStatus");
        paOrgDto.setRecruitmentStatusDate("recruitmentStatusDate");
        paOrgDto.setId("1");
        paOrgList.add(paOrgDto);
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        TrialDTO tDto = getMockTrialDTO();
        tDto.setParticipatingSites(paOrgList);
        session.setAttribute("trialDTO", tDto);
        request.setSession(session);
        ServletActionContext.setRequest(request);
        assertEquals("redirect_to_search", action.update());
    }
    
    @Test 
    public void validateRespPartyInfoValidationPassed() {
        TrialDTO trial = getMockTrialDTO();
        action.setTrialDTO(trial);
        assertTrue(action.validateRespPartyInfo());
    }
    
    @Test 
    public void validateRespPartyInfoIncorrectSponsorIdentifier() {
        TrialDTO trial = getMockTrialDTO();
        trial.setSponsorIdentifier(null);
        action.setTrialDTO(trial);
        assertFalse(action.validateRespPartyInfo());
    }
    
    @Test 
    public void validateRespPartyInfoIncorrectResponsiblePartyType() {
        TrialDTO trial = getMockTrialDTO();
        trial.setResponsiblePartyType(null);
        action.setTrialDTO(trial);
        assertFalse(action.validateRespPartyInfo());
    }
    
    @Test 
    public void validateRespPartyInfoIncorrectResponsiblePartyTypePI() {
        TrialDTO trial = getMockTrialDTO();
        trial.setResponsiblePartyType("not PI");
        action.setTrialDTO(trial);
        assertFalse(action.validateRespPartyInfo());
    }
    
    @Test 
    public void validateRespPartyInfoIncorrectContactPhone() {
        TrialDTO trial = getMockTrialDTO();
        trial.setContactPhone(null);
        action.setTrialDTO(trial);
        assertFalse(action.validateRespPartyInfo());
    }
    
    @Test 
    public void validateRespPartyInfoIncorrectContactEmail() {
        TrialDTO trial = getMockTrialDTO();
        trial.setContactEmail(null);
        action.setTrialDTO(trial);
        assertFalse(action.validateRespPartyInfo());
    }
    
    @Test 
    public void validateSummaryFourInfoValidationPassed() {
        TrialDTO trial = getMockTrialDTO();
        action.setTrialDTO(trial);
        assertTrue(action.validateSummaryFourInfo());
    }
    
    @Test 
    public void  validateSummaryFourInfoIncorrectSummaryFourFundingCategoryCode() {
        TrialDTO trial = getMockTrialDTO();
        trial.setSummaryFourFundingCategoryCode(null);
        action.setTrialDTO(trial);
        assertFalse(action. validateSummaryFourInfo());
    }
    
    @Test 
    public void  validateSummaryFourInfoIncorrectSummaryFourOrgName() {
        TrialDTO trial = getMockTrialDTO();
        trial.setSummaryFourOrgName(null);
        action.setTrialDTO(trial);
        assertFalse(action. validateSummaryFourInfo());
    }
    
    @Test
    public void validateTrialNotUpdatableFieldsFailed() throws PAException, IOException {
        UpdateTrialAction action = mock(UpdateTrialAction.class);
        doCallRealMethod().when(action).validateTrial();
        when(action.validateRespPartyInfo()).thenReturn(false);

        String errorMessage = action.validateTrial();

        assertEquals("Required fields are missing. You may not complete an update. Please submit an amendment instead.",
                     errorMessage);
    }

    @Test
    public void validateTrialBusinessRulesFailed() throws PAException, IOException {
        UpdateTrialAction action = mock(UpdateTrialAction.class);
        doCallRealMethod().when(action).validateTrial();
        when(action.validateRespPartyInfo()).thenReturn(true);
        when(action.validateSummaryFourInfo()).thenReturn(true);
        when(action.hasFieldErrors()).thenReturn(true);
        doNothing().when(action).enforceBusinessRules();

        String errorMessage = action.validateTrial();

        assertEquals("The form has errors and could not be submitted, please check the fields highlighted below",
                     errorMessage);
    }

    @Test
    public void validateTrial() throws PAException, IOException {
        UpdateTrialAction action = mock(UpdateTrialAction.class);
        doCallRealMethod().when(action).validateTrial();
        when(action.validateRespPartyInfo()).thenReturn(true);
        when(action.validateSummaryFourInfo()).thenReturn(true);
        when(action.hasFieldErrors()).thenReturn(true);
        doNothing().when(action).enforceBusinessRules();

        action.validateTrial();

        InOrder inOrder = inOrder(action);
        inOrder.verify(action).clearErrorsAndMessages();
        inOrder.verify(action).validateRespPartyInfo();
        inOrder.verify(action).validateSummaryFourInfo();
        inOrder.verify(action).enforceBusinessRules();
        inOrder.verify(action).hasFieldErrors();
    }
}
