/**
 * 
 */
package gov.nih.nci.registry.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import gov.nih.nci.pa.dto.CountryRegAuthorityDTO;
import gov.nih.nci.pa.dto.PaOrganizationDTO;
import gov.nih.nci.pa.dto.RegulatoryAuthOrgDTO;
import gov.nih.nci.pa.util.CtrpHibernateHelper;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.registry.dto.RegulatoryAuthorityWebDTO;
import gov.nih.nci.registry.dto.StudyIndldeWebDTO;
import gov.nih.nci.registry.dto.TrialDTO;
import gov.nih.nci.registry.dto.TrialFundingWebDTO;
import gov.nih.nci.registry.dto.TrialIndIdeDTO;
import gov.nih.nci.registry.util.Constants;
import gov.nih.nci.registry.util.TestHibernateHelper;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpSession;

/**
 * @author Vrushali
 *
 */
public class UpdateTrialActionTest extends AbstractRegWebTest {
    private UpdateTrialAction action = new UpdateTrialAction();
    private static CtrpHibernateHelper testHelper = new TestHibernateHelper();
    @Before 
    public void setup() {
        HibernateUtil.testHelper = testHelper;
        Session session = HibernateUtil.getCurrentSession();
        session.clear();
    }
    @Test
    public void testServletResponseProperty(){
        assertNull(action.getServletResponse());
        action.setServletResponse(null);
        assertNull(action.getServletResponse());
    }
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
        assertNotNull(action.getParticipatingSites());
        action.setParticipatingSites(null);
        assertNull(action.getParticipatingSites());
    }
    @Test
    public void testCountryListProperty() {
        assertNotNull(action.getCountryList());
        action.setCountryList(null);
        assertNull(action.getCountryList());
    }
    @Test
    public void testRegIdAuthOrgListProperty() {
        assertNotNull(action.getRegIdAuthOrgList());
        action.setRegIdAuthOrgList(null);
        assertNull(action.getRegIdAuthOrgList());
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
    public void testLstProperty() {
        assertNull(action.getLst());
        action.setLst("lst");
        assertNotNull(action.getLst());
    }
    @Test
    public void testSelectedRegAuthProperty() {
        assertNull(action.getSelectedRegAuth());
        action.setSelectedRegAuth("selectedRegAuth");
        assertNotNull(action.getSelectedRegAuth());
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
    public void testCountryRegAuthorityDTOProperty() {
        assertNull(action.getCountryRegAuthorityDTO());
        action.setCountryRegAuthorityDTO(new CountryRegAuthorityDTO());
        assertNotNull(action.getCountryRegAuthorityDTO());
    }
    @Test
    public void testRegulatoryAuthOrgDTOProperty() {
        assertNull(action.getRegulatoryAuthOrgDTO());
        action.setRegulatoryAuthOrgDTO(new RegulatoryAuthOrgDTO());
        assertNotNull(action.getRegulatoryAuthOrgDTO());
    }
    @Test
    public void testStudyIndldeWebDTOProperty() {
        assertNull(action.getStudyIndldeWebDTO());
        action.setStudyIndldeWebDTO(new StudyIndldeWebDTO());
        assertNotNull(action.getStudyIndldeWebDTO());
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
    public void testRegulatoryAuthorityProperty() {
        assertNull(action.getRegulatoryAuthority());
        action.setRegulatoryAuthority(new RegulatoryAuthorityWebDTO());
        assertNotNull(action.getRegulatoryAuthority());
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
        List<StudyIndldeWebDTO> indIdeUpdateDtos = new ArrayList<StudyIndldeWebDTO>();
        StudyIndldeWebDTO indIdeDto = new StudyIndldeWebDTO();
        indIdeDto.setIndldeType("IND");
        indIdeDto.setGrantor("grantor");
        indIdeDto.setIndldeNumber("indldeNumber");
        indIdeDto.setHolderType("NCI");
        indIdeDto.setNciDivProgHolder("nciDivProgHolder");
        indIdeDto.setExpandedAccessIndicator("Yes");
        indIdeDto.setExpandedAccessStatus("expandedAccessStatus");
        indIdeDto.setStudyProtocolIi("1");
        indIdeDto.setProgramCode("programCode");
        indIdeDto.setId("1");
        indIdeUpdateDtos.add(indIdeDto);
        tDto.setIndIdeUpdateDtos(indIdeUpdateDtos);
        session.setAttribute("trialDTO", tDto);
        request.setSession(session);
        ServletActionContext.setRequest(request);
        assertEquals("edit", action.edit());
    }
    @Test
    public void testGetRegAuthoritiesList() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        request.setSession(session);
        ServletActionContext.setRequest(request);
        assertEquals("success", action.getRegAuthoritiesList());
        session = new MockHttpSession();
        request.setupAddParameter("countryid", "1");
        request.setSession(session);
        ServletActionContext.setRequest(request);
        assertEquals("success", action.getRegAuthoritiesList());
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
        primeData();
        assertEquals("error", action.view());
    }
    @Test
    public void testReviewUpdate() throws URISyntaxException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        request.setSession(session);
        ServletActionContext.setRequest(request);
        action.setTrialDTO(getMockTrialDTO());
        assertEquals("error", action.reviewUpdate());
        request = new MockHttpServletRequest();
        session = new MockHttpSession();
        session.setAttribute(Constants.COUNTRY_LIST, new ArrayList<CountryRegAuthorityDTO>());
        session.setAttribute(Constants.REG_AUTH_LIST, new ArrayList<RegulatoryAuthOrgDTO>());
        session.setAttribute(Constants.GRANT_ADD_LIST, new ArrayList<TrialFundingWebDTO>());
        session.setAttribute(Constants.INDIDE_ADD_LIST, new ArrayList<TrialIndIdeDTO>());
        RegulatoryAuthorityWebDTO regAuthDto = new RegulatoryAuthorityWebDTO();
        regAuthDto.setDataMonitoringIndicator("dataMonitoringIndicator");
        regAuthDto.setDelayedPostingIndicator("delayedPostingIndicator");
        regAuthDto.setFdaRegulatedInterventionIndicator("fdaRegulatedInterventionIndicator");
        regAuthDto.setSection801Indicator("section801Indicator");
        regAuthDto.setTrialOversgtAuthCountry("trialOversgtAuthCountry");
        regAuthDto.setTrialOversgtAuthOrgName("trialOversgtAuthOrgName");
        action.setRegulatoryAuthority(regAuthDto);
        action.setSelectedRegAuth("2");
        action.setLst("3");
        action.setIrbApprovalFileName("ProtocolDoc.doc");
        URL fileUrl = ClassLoader.getSystemClassLoader().getResource("ProtocolDoc.doc");
        File f = new File(fileUrl.toURI());
        action.setIrbApproval(f);
        request.setSession(session);
        ServletActionContext.setRequest(request);
        action.setTrialDTO(getMockTrialDTO());
        assertEquals("review", action.reviewUpdate());
        request = new MockHttpServletRequest();
        session = new MockHttpSession();
        session.setAttribute(Constants.COUNTRY_LIST, new ArrayList<CountryRegAuthorityDTO>());
        session.setAttribute(Constants.REG_AUTH_LIST, new ArrayList<RegulatoryAuthOrgDTO>());
        session.setAttribute(Constants.GRANT_ADD_LIST, new ArrayList<TrialFundingWebDTO>());
        session.setAttribute(Constants.INDIDE_ADD_LIST, new ArrayList<TrialIndIdeDTO>());
        request.setSession(session);
        action.setRegulatoryAuthority(new RegulatoryAuthorityWebDTO());
        action.setSelectedRegAuth("2");
        action.setLst("3");
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
        action.setRegulatoryAuthority(new RegulatoryAuthorityWebDTO());
        action.setSelectedRegAuth("2");
        action.setLst("3");
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
        action.setRegulatoryAuthority(new RegulatoryAuthorityWebDTO());
        action.setSelectedRegAuth("2");
        action.setLst("3");
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
        action.setParticipatingSites(paOrgList);
        assertEquals("error", action.reviewUpdate());
        
        paOrgList = new ArrayList<PaOrganizationDTO>();
        paOrgDto = new PaOrganizationDTO();
        paOrgDto.setName("name");
        paOrgDto.setRecruitmentStatus("recruitmentStatus");
        paOrgDto.setRecruitmentStatusDate(null);
        paOrgList.add(paOrgDto);
        action.setParticipatingSites(paOrgList);
        assertEquals("error", action.reviewUpdate());
    }
    @Test 
    public void testReviewUpdateWithFundingDtos() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        request.setSession(session);
        ServletActionContext.setRequest(request);
        action.setRegulatoryAuthority(new RegulatoryAuthorityWebDTO());
        action.setSelectedRegAuth("2");
        action.setLst("3");
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
        action.setRegulatoryAuthority(new RegulatoryAuthorityWebDTO());
        action.setSelectedRegAuth("2");
        action.setLst("3");
        action.setTrialDTO(getMockTrialDTO());
        List<StudyIndldeWebDTO> indIdeDtos = new ArrayList<StudyIndldeWebDTO>();
        indIdeDtos.add(new StudyIndldeWebDTO());
        action.setIndIdeUpdateDtos(indIdeDtos);
        assertEquals("error", action.reviewUpdate());
        indIdeDtos = new ArrayList<StudyIndldeWebDTO>();
        StudyIndldeWebDTO indIdeDto = new StudyIndldeWebDTO();
        indIdeDto.setGrantor("grantor");
        indIdeDtos.add(indIdeDto);
        action.setIndIdeUpdateDtos(indIdeDtos);
        assertEquals("error", action.reviewUpdate());
        indIdeDtos = new ArrayList<StudyIndldeWebDTO>();
        indIdeDto = new StudyIndldeWebDTO();
        indIdeDto.setGrantor("grantor");
        indIdeDto.setIndldeNumber("indldeNumber");
        indIdeDtos.add(indIdeDto);
        action.setIndIdeUpdateDtos(indIdeDtos);
        assertEquals("error", action.reviewUpdate());

        indIdeDtos = new ArrayList<StudyIndldeWebDTO>();
        indIdeDto = new StudyIndldeWebDTO();
        indIdeDto.setGrantor("grantor");
        indIdeDto.setIndldeNumber("indldeNumber");
        indIdeDto.setHolderType("holderType");
        indIdeDto.setExpandedAccessIndicator("Yes");
        indIdeDtos.add(indIdeDto);
        action.setIndIdeUpdateDtos(indIdeDtos);
        assertEquals("error", action.reviewUpdate());
        
        indIdeDtos = new ArrayList<StudyIndldeWebDTO>();
        indIdeDto = new StudyIndldeWebDTO();
        indIdeDto.setGrantor("grantor");
        indIdeDto.setIndldeNumber("indldeNumber");
        indIdeDto.setHolderType("NIH");
        indIdeDto.setExpandedAccessIndicator("Yes");
        indIdeDtos.add(indIdeDto);
        action.setIndIdeUpdateDtos(indIdeDtos);
        assertEquals("error", action.reviewUpdate());
        indIdeDtos = new ArrayList<StudyIndldeWebDTO>();
        indIdeDto = new StudyIndldeWebDTO();
        indIdeDto.setGrantor("grantor");
        indIdeDto.setIndldeNumber("indldeNumber");
        indIdeDto.setHolderType("NCI");
        indIdeDto.setExpandedAccessIndicator("Yes");
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
        tDto.setResponsiblePartyType("Sponsor");
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
        List<StudyIndldeWebDTO> indIdeUpdateDtos = new ArrayList<StudyIndldeWebDTO>();
        StudyIndldeWebDTO indIdeDto = new StudyIndldeWebDTO();
        indIdeDto.setIndldeType("IND");
        indIdeDto.setGrantor("grantor");
        indIdeDto.setIndldeNumber("indldeNumber");
        indIdeDto.setHolderType("NCI");
        indIdeDto.setNciDivProgHolder("nciDivProgHolder");
        indIdeDto.setExpandedAccessIndicator("Yes");
        indIdeDto.setExpandedAccessStatus("expandedAccessStatus");
        indIdeDto.setId("1");
        indIdeUpdateDtos.add(indIdeDto);
        indIdeDto = new StudyIndldeWebDTO();
        indIdeDto.setId("2");
        indIdeDto.setIndldeType("IDE");
        indIdeDto.setGrantor("grantor");
        indIdeDto.setIndldeNumber("indldeNumber");
        indIdeDto.setHolderType("NIH");
        indIdeDto.setNihInstHolder("nihInstHolder");
        indIdeDto.setExpandedAccessIndicator("Yes");
        indIdeDto.setExpandedAccessStatus("expandedAccessStatus");
        indIdeUpdateDtos.add(indIdeDto);
        indIdeDto = new StudyIndldeWebDTO();
        indIdeDto.setId("2");
        indIdeDto.setIndldeType("IDE");
        indIdeDto.setGrantor("grantor");
        indIdeDto.setIndldeNumber("indldeNumber");
        indIdeDto.setHolderType("NIH");
        indIdeDto.setNihInstHolder("nihInstHolder");
        indIdeDto.setExpandedAccessIndicator("No");
        indIdeDto.setExpandedAccessStatus("");
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
}
