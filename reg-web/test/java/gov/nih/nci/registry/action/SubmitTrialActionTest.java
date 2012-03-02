/**
 *
 */
package gov.nih.nci.registry.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.PrimaryPurposeAdditionalQualifierCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.util.CommonsConstant;
import gov.nih.nci.registry.dto.TrialDTO;
import gov.nih.nci.registry.util.Constants;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.junit.Before;
import org.junit.Test;

import com.mockrunner.mock.web.MockHttpServletRequest;

/**
 * @author Vrushali
 *
 */
public class SubmitTrialActionTest extends AbstractRegWebTest{
    private static final String FILE_NAME = "ProtocolDoc.doc";
    private SubmitTrialAction action = new SubmitTrialAction();
    /**
     * Initialization method.
     */
    @Before
    public void init() {
        action.prepare();
    }
    
    @Test
    public void testExecute() throws Exception {
       
        assertEquals("redirect_to_search",action.execute());
        action.setSum4FundingCatCode("sum4FundingCatCode");
        assertEquals("success",action.execute());
    }
    
    @Test
    public void testEdit() throws Exception {
        HttpSession session = ServletActionContext.getRequest().getSession();
        session.setAttribute("trialDTO", getMockTrialDTO());
        assertEquals("edit", action.edit());
    }
    
    @Test
    public void testCancel() {
        assertEquals("redirect_to_search", action.cancel());
    }
    
    @Test
    public void testReview() throws Exception{
        action.setTrialDTO(getMockTrialDTO());
        URL fileUrl = ClassLoader.getSystemClassLoader().getResource(FILE_NAME);
        File f = new File(fileUrl.toURI());
        action.setProtocolDoc(f);
        action.setIrbApproval(f);
        action.setProtocolDocFileName(FILE_NAME);
        action.setIrbApprovalFileName(FILE_NAME);
        action.setPageFrom("submitTrial");
        assertEquals("review", action.review());
    }
    
    @Test
    public void testReviewWithAllDoc() throws Exception{
        action.setTrialDTO(getMockTrialDTO());
        URL fileUrl = ClassLoader.getSystemClassLoader().getResource(FILE_NAME);
        File f = new File(fileUrl.toURI());
        action.setProtocolDoc(f);
        action.setIrbApproval(f);
        action.setInformedConsentDocument(f);
        action.setParticipatingSites(f);
        action.setOtherDocument(new File[] {f});
        action.setProtocolDocFileName(FILE_NAME);
        action.setIrbApprovalFileName(FILE_NAME);
        action.setInformedConsentDocumentFileName(FILE_NAME);
        action.setParticipatingSitesFileName(FILE_NAME);
        action.setOtherDocumentFileName(new String[] {FILE_NAME});
        action.setPageFrom("submitTrial");
        assertEquals("review", action.review());
    }
    
    @Test
    public void testReviewWithStatusCodeChangedToAdComplete() throws Exception{
        TrialDTO dto = getMockTrialDTO();
        dto.setStatusDate("01/20/2009");
        dto.setStatusCode("Administratively Complete");
        dto.setPrimaryCompletionDateType("Anticipated");
        dto.setPrimaryCompletionDate("01/20/2009");
        dto.setStartDateType("Anticipated");
        dto.setStartDate("01/20/2008");
        dto.setReason("reason");
        URL fileUrl = ClassLoader.getSystemClassLoader().getResource(FILE_NAME);
        File f = new File(fileUrl.toURI());
        action.setProtocolDoc(f);
        action.setIrbApproval(f);
        action.setProtocolDocFileName(FILE_NAME);
        action.setIrbApprovalFileName(FILE_NAME);
        action.setTrialDTO(dto);
        assertEquals("error", action.review());
    }

    @Test
    public void testReviewWithGrants() throws Exception{
        action.setTrialDTO(getMockTrialDTO());
        URL fileUrl = ClassLoader.getSystemClassLoader().getResource(FILE_NAME);
        File f = new File(fileUrl.toURI());
        action.setProtocolDoc(f);
        action.setIrbApproval(f);
        action.setProtocolDocFileName(FILE_NAME);
        action.setIrbApprovalFileName(FILE_NAME);
        action.setPageFrom("submitTrial");
        //set grant in session
        HttpSession session = ServletActionContext.getRequest().getSession();
        session.setAttribute(Constants.GRANT_LIST, getfundingDtos());
        assertEquals("review", action.review());
    }
    
    @Test
    public void testReviewWithIndIde() throws URISyntaxException{
        action.setTrialDTO(getMockTrialDTO());
        URL fileUrl = ClassLoader.getSystemClassLoader().getResource(FILE_NAME);
        File f = new File(fileUrl.toURI());
        action.setProtocolDoc(f);
        action.setIrbApproval(f);

        action.setProtocolDocFileName(FILE_NAME);
        action.setIrbApprovalFileName(FILE_NAME);
        action.setPageFrom("submitTrial");
        //set Ind in session
        HttpSession session = ServletActionContext.getRequest().getSession();
        session.setAttribute(Constants.INDIDE_LIST, getIndDtos());
        assertEquals("review", action.review());
    }
    
    @Test
    public void testReviewWithError() throws Exception{
        TrialDTO dto = getMockTrialDTO();
        dto.setPhaseCode("Other");
        dto.setContactEmail("dhh");
        dto.setContactPhone("");
        dto.setStartDate("startDate");
        dto.setPrimaryCompletionDate("completionDate");
        dto.setStatusDate("statusDate");
        dto.setResponsiblePartyType("");
        dto.setPrimaryPurposeCode("Other");
        dto.setStatusCode("Administratively Complete");
        URL fileUrl = ClassLoader.getSystemClassLoader().getResource(FILE_NAME);
        File f = new File(fileUrl.toURI());
        action.setProtocolDoc(f);
        action.setProtocolDocFileName(FILE_NAME);
        fileUrl = ClassLoader.getSystemClassLoader().getResource("test.txt");
        f = new File(fileUrl.toURI());
        action.setIrbApproval(f);
        action.setIrbApprovalFileName("test.txt");
        action.setTrialDTO(dto);
        assertEquals("error", action.review());
    }
    
    @Test
    public void testCreateWithNoDTO(){
        assertEquals("error", action.create());
    }
    
    @Test
    public void testCreateWithDupliTrial(){
        TrialDTO dto = getMockTrialDTO();
        dto.setLeadOrganizationIdentifier("2");
        dto.setLeadOrgTrialIdentifier("DupTestinglocalStudyProtocolId");
        HttpSession session = ServletActionContext.getRequest().getSession();
        session.setAttribute("trialDTO", dto);
        assertEquals("redirect_to_search", action.create());
    }
    
    @Test
    public void testCreateWithRespPartyAsPi(){
        TrialDTO dto = getMockTrialDTO();
        HttpSession session = ServletActionContext.getRequest().getSession();
        session.setAttribute("trialDTO", dto);
        assertEquals("redirect_to_search", action.create());
    }
    
    @Test
    public void testCreateWithRespPartyAsSponsor(){
        TrialDTO dto = getMockTrialDTO();
        dto.setResponsiblePartyType(TrialDTO.RESPONSIBLE_PARTY_TYPE_SPONSOR);
        dto.setResponsiblePersonIdentifier("3");
        HttpSession session = ServletActionContext.getRequest().getSession();
        session.setAttribute("trialDTO", dto);
        assertEquals("redirect_to_search", action.create());
    }
    
    @Test
    public void testCreateWithException(){
        TrialDTO dto = getMockTrialDTO();
        dto.setPhaseCode("Other");
        dto.setPhaseAdditionalQualifier("phaseOtherText");
        dto.setPrimaryPurposeCode("Other");
        dto.setPrimaryPurposeAdditionalQualifierCode(PrimaryPurposeAdditionalQualifierCode.EPIDEMIOLOGIC.getCode());
        dto.setOfficialTitle("testthrowException");
        HttpSession session = ServletActionContext.getRequest().getSession();
        session.setAttribute("trialDTO", dto);
        assertEquals("error", action.create());
    }

    @Test
    public void testTrialDTOProperty(){
        assertNull(action.getTrialDTO());
        action.setTrialDTO(getMockTrialDTO());
        assertNotNull(action.getTrialDTO());
    }
    
    @Test
    public void testTrialActionProperty(){
        assertNotNull(action.getTrialAction());
        action.setTrialAction(null);
        assertNull(action.getTrialAction());
    }
    
    @Test
    public void testIdProperty(){
        assertNull(action.getId());
        action.setId(1L);
        assertNotNull(action.getId());
    }
    
    @Test
    public void testCbValueProperty(){
        assertNull(action.getCbValue());
        action.setCbValue(1L);
        assertNotNull(action.getCbValue());
    }
    
    @Test
    public void testPageProperty(){
        assertNull(action.getPage());
        action.setPage("page");
        assertNotNull(action.getPage());
    }

    @Test
    public void testServletResponseProperty(){
        assertNull(action.getServletResponse());
        action.setServletResponse(null);
        assertNull(action.getServletResponse());
    }
    
    @Test
    public void testProtocolDocProperty() throws URISyntaxException{
        assertNull(action.getProtocolDoc());
        URL fileUrl = ClassLoader.getSystemClassLoader().getResource(FILE_NAME);
        File f = new File(fileUrl.toURI());
        action.setProtocolDoc(f);
        assertNotNull(action.getProtocolDoc());
    }
    
    @Test
    public void testIRBDocProperty(){
        assertNull(action.getIrbApproval());
        action.setIrbApproval(new File(FILE_NAME));
        assertNotNull(action.getIrbApproval());
    }
    
    @Test
    public void testInformedConsentDocProperty(){
        assertNull(action.getInformedConsentDocument());
        action.setInformedConsentDocument(new File(FILE_NAME));
        assertNotNull(action.getInformedConsentDocument());
    }
    
    @Test
    public void testParticipatingSiteDocProperty(){
        assertNull(action.getParticipatingSites());
        action.setParticipatingSites(new File(FILE_NAME));
        assertNotNull(action.getParticipatingSites());
    }
    
    @Test
    public void testOtherDocProperty(){
        assertTrue(action.getOtherDocument().length==0);
        action.setOtherDocument(new File[] {new File(FILE_NAME)});
        assertNotNull(action.getOtherDocument());
    }
    
    @Test
    public void testProtocolFileNameProperty(){
        assertNull(action.getProtocolDocFileName());
        action.setProtocolDocFileName("protocolDocFileName");
        assertNotNull(action.getProtocolDocFileName());
    }
    
    @Test
    public void testIRBFileNameProperty(){
        assertNull(action.getIrbApprovalFileName());
        action.setIrbApprovalFileName("irbApprovalFileName");
        assertNotNull(action.getIrbApprovalFileName());
    }
    
    @Test
    public void testInformedConsentFileNameProperty(){
        assertNull(action.getInformedConsentDocumentFileName());
        action.setInformedConsentDocumentFileName("informedConsentDocumentFileName");
        assertNotNull(action.getInformedConsentDocumentFileName());
    }
    
    @Test
    public void testParticipatingSiteFileNameProperty(){
        assertNull(action.getParticipatingSitesFileName());
        action.setParticipatingSitesFileName("participatingSitesFileName");
        assertNotNull(action.getParticipatingSitesFileName());
    }
    
    @Test
    public void testOtherFileNameProperty(){
        assertTrue(action.getOtherDocumentFileName().length==0);
        action.setOtherDocumentFileName(new String[] {"otherDocFileName"});
        assertNotNull(action.getOtherDocumentFileName());
    }
    
    @Test
    public void testReviewWithNoTrialDate() throws Exception{
        TrialDTO dto = getMockTrialDTO();
        dto.setStartDate("");
        dto.setPrimaryCompletionDate("");
        dto.setStatusDate("");
        dto.setStatusCode("");
        dto.setStartDateType("");
        dto.setPrimaryCompletionDateType("");
        action.setTrialDTO(dto);
        assertEquals("error", action.review());
    }

    @Test
    public void testValidateTrialDatesRule18Fail() {
        TrialDTO dto = getMockTrialDTO();
        dto.setStatusDate(getTomorrowDate());
        action.setTrialDTO(dto);
        assertEquals("error", action.review());
        assertTrue(action.getFieldErrors().containsKey("trialDTO.statusDate"));
        assertEquals("Please enter a valid date", action.getFieldErrors().get("trialDTO.statusDate").get(0));
    }
    
    @Test
    public void testValidateTrialDatesRule18Pass() {
        TrialDTO dto = getMockTrialDTO();
        dto.setStatusDate("02/22/2009");
        action.setTrialDTO(dto);
        action.setPageFrom("submitTrial");
        assertEquals("error", action.review());
    }
    
    @Test
    public void testValidateTrialDatesRule19ActualFail() {
        TrialDTO dto = getMockTrialDTO();
        dto.setStartDate(getTomorrowDate());
        dto.setStartDateType("Actual");
        action.setTrialDTO(dto);
        assertEquals("error", action.review());
        assertTrue(action.getFieldErrors().containsKey("trialDTO.startDate"));
        assertEquals("Please enter a valid date", action.getFieldErrors().get("trialDTO.startDate").get(0));
    }
    
    @Test
    public void testValidateTrialDatesRule19AnticipatedFail(){
        TrialDTO dto = getMockTrialDTO();
        dto.setStartDate("02/22/2000");
        dto.setStartDateType("Anticipated");
        action.setTrialDTO(dto);
        assertEquals("error", action.review());
        assertTrue(action.getFieldErrors().containsKey("trialDTO.startDate"));
    }
    
    @Test
    public void testValidateTrialDatesRule20ActualFail() {
        TrialDTO dto = getMockTrialDTO();
        dto.setPrimaryCompletionDate(getTomorrowDate());
        dto.setPrimaryCompletionDateType("Actual");
        action.setTrialDTO(dto);
        assertEquals("error", action.review());
        assertTrue(action.getFieldErrors().containsKey("trialDTO.primaryCompletionDate"));
        assertEquals("Please enter a valid date", action.getFieldErrors().get("trialDTO.primaryCompletionDate")
            .get(0));
    }
    
    @Test
    public void testValidateTrialDatesRule20AnticipatedFail(){
        TrialDTO dto = getMockTrialDTO();
        dto.setPrimaryCompletionDate("02/22/2000");
        dto.setPrimaryCompletionDateType("Anticipated");
        action.setTrialDTO(dto);
        assertEquals("error", action.review());
        assertTrue(action.getFieldErrors().containsKey("trialDTO.primaryCompletionDate"));
    }
    
    @Test
    public void testValidateTrialDatesRule22ApprovedFail(){
        TrialDTO dto = getMockTrialDTO();
        dto.setStatusCode(StudyStatusCode.ADMINISTRATIVELY_COMPLETE.getCode());
        dto.setStartDateType(ActualAnticipatedTypeCode.ANTICIPATED.getCode());
        action.setTrialDTO(dto);
        assertEquals("error", action.review());
        assertTrue(action.getFieldErrors().containsKey("trialDTO.startDateType"));
    }
    
    @Test
    public void testValidateTrialDatesRule23CompletedFail(){
        TrialDTO dto = getMockTrialDTO();
        dto.setStatusCode("Complete");
        dto.setStartDateType("Anticipated");
        action.setTrialDTO(dto);
        assertEquals("error", action.review());
    }
    
    @Test
    public void testValidateTrialDatesRule25Fail(){
        TrialDTO dto = getMockTrialDTO();
        dto.setStartDate("01/22/2010");
        dto.setPrimaryCompletionDate("01/22/2009");
        action.setTrialDTO(dto);
        assertEquals("error", action.review());
        assertTrue(action.getFieldErrors().containsKey("trialDTO.startDate"));
    }
    
    @Test
    public void testValidateTrialDatesRule21Fail(){
        //Trial Start date is greater Current Trial Status Date
        TrialDTO dto = getMockTrialDTO();
        dto.setStatusCode("Active");
        dto.setStatusDate("01/22/2008");
        dto.setStartDate("01/22/2009");
        action.setTrialDTO(dto);
        assertEquals("error", action.review());
        assertTrue(action.getFieldErrors().containsKey("trialDTO.startDate"));
    }
    
    @Test
    public void testValidateTrialDatesRule21PassStatusAndStartDatesAreSame() throws URISyntaxException {
        //Trial Start date is same Current Trial Status Date
        TrialDTO dto = getMockTrialDTO();
        dto.setStatusCode("Active");
        dto.setStatusDate("01/22/2009");
        dto.setStartDate("01/22/2009");
        action.setTrialDTO(dto);
        URL fileUrl = ClassLoader.getSystemClassLoader().getResource(FILE_NAME);
        File f = new File(fileUrl.toURI());
        action.setProtocolDoc(f);
        action.setIrbApproval(f);
        action.setProtocolDocFileName(FILE_NAME);
        action.setIrbApprovalFileName(FILE_NAME);
        assertEquals("review", action.review());
    }
    
    @Test
    public void testValidateTrialDatesRule21PassStartSmallerThanStatusDates() throws URISyntaxException {
        TrialDTO dto = getMockTrialDTO();
        dto.setStatusCode("Active");
        //Trial Start date is smaller Current Trial Status Date
        dto.setStatusDate("01/22/2009");
        dto.setStartDate("01/22/2008");
        action.setTrialDTO(dto);
        URL fileUrl = ClassLoader.getSystemClassLoader().getResource(FILE_NAME);
        File f = new File(fileUrl.toURI());
        action.setProtocolDoc(f);
        action.setIrbApproval(f);
        action.setProtocolDocFileName(FILE_NAME);
        action.setIrbApprovalFileName(FILE_NAME);
        assertEquals("review", action.review());
    }
    
    @Test
    public void testValidateRule21StartTypeAnticipated() {
        TrialDTO dto = getMockTrialDTO();
        dto.setStatusCode("Active");
        dto.setStartDateType("Anticipated");
        //Trial Start date is smaller or same Current Trial Status Date
        dto.setStatusDate("01/22/2009");
        dto.setStartDate("01/22/2008");
        action.setTrialDTO(dto);
        assertEquals("error", action.review());
    }
    
    @Test
    public void testGetTrialOversightAuthorityOrganizationNameList() {
        assertEquals("success", action.getTrialOversightAuthorityOrganizationNameList());
        MockHttpServletRequest request = (MockHttpServletRequest)ServletActionContext.getRequest();
        request.setupAddParameter("countryid", "");
        assertEquals("success", action.getTrialOversightAuthorityOrganizationNameList());
        request.setupAddParameter("countryid", "1");
        assertEquals("success", action.getTrialOversightAuthorityOrganizationNameList());
    }
    
    @Test
    public void testPartialSave() {
        action.setTrialDTO(new TrialDTO());
        assertEquals("error", action.partialSave());

        action = new SubmitTrialAction();
        TrialDTO  trialDto = getMockTrialDTO();
        trialDto.setLeadOrganizationIdentifier("");
        action.setTrialDTO(trialDto);
        assertEquals("error", action.partialSave());

        action = new SubmitTrialAction();
        action.setTrialDTO(getMockTrialDTO());
        assertEquals("review", action.partialSave());

        action = new SubmitTrialAction();
        trialDto = getMockTrialDTO();
        trialDto.setStudyProtocolId("1");
        trialDto.setFdaRegulatoryInformationIndicator(CommonsConstant.NO);
        trialDto.setSection801Indicator(CommonsConstant.NO);
        trialDto.setDelayedPostingIndicator(CommonsConstant.NO);
        trialDto.setDataMonitoringCommitteeAppointedIndicator(CommonsConstant.NO);
        action.setTrialDTO(trialDto);
        assertEquals("review", action.partialSave());

        action = new SubmitTrialAction();
        trialDto = getMockTrialDTO();
        trialDto.setStudyProtocolId("1");
        trialDto.setFdaRegulatoryInformationIndicator(CommonsConstant.YES);
        trialDto.setSection801Indicator(CommonsConstant.YES);
        trialDto.setDelayedPostingIndicator(CommonsConstant.YES);
        trialDto.setDataMonitoringCommitteeAppointedIndicator(CommonsConstant.YES);
        action.setTrialDTO(trialDto);
        HttpSession session = ServletActionContext.getRequest().getSession();
        session.setAttribute(Constants.GRANT_LIST, getfundingDtos());
        assertEquals("review", action.partialSave());
        session.removeAttribute(Constants.GRANT_LIST);

        action = new SubmitTrialAction();
        trialDto = getMockTrialDTO();
        trialDto.setStudyProtocolId("1");
        trialDto.setFdaRegulatoryInformationIndicator(CommonsConstant.YES);
        trialDto.setSection801Indicator(CommonsConstant.YES);
        trialDto.setDelayedPostingIndicator(CommonsConstant.YES);
        trialDto.setDataMonitoringCommitteeAppointedIndicator(CommonsConstant.YES);
        action.setTrialDTO(trialDto);
        session.setAttribute(Constants.INDIDE_LIST, getIndDtos());
        assertEquals("review", action.partialSave());
    }
    
    @Test
    public void testComplete() {
        assertEquals("error", action.completePartialSubmission());

        action = new SubmitTrialAction();
        action.prepare();
        MockHttpServletRequest request = (MockHttpServletRequest)ServletActionContext.getRequest();
        request.setupAddParameter("studyProtocolId", "1");
        assertEquals("success", action.completePartialSubmission());
        
        action = new SubmitTrialAction();
        action.prepare();
        request.setupAddParameter("studyProtocolId", "2");
        assertEquals("success", action.completePartialSubmission());
        
        action = new SubmitTrialAction();
        action.prepare();
        request.setupAddParameter("studyProtocolId", "3");
        assertEquals("success", action.completePartialSubmission());
    }
    
    @Test
    public void testDelete() {
        assertEquals("error", action.deletePartialSubmission());

        action = new SubmitTrialAction();
        action.prepare();
        MockHttpServletRequest request = (MockHttpServletRequest)ServletActionContext.getRequest();
        request.setupAddParameter("studyProtocolId", "1");
        assertEquals("redirect_to_search", action.deletePartialSubmission());
        
        action = new SubmitTrialAction();
        action.prepare();
        request.setupAddParameter("studyProtocolId", "2");
        assertEquals("redirect_to_search", action.deletePartialSubmission());
        
        action = new SubmitTrialAction();
        action.prepare();
        request.setupAddParameter("studyProtocolId", "3");
        assertEquals("redirect_to_search", action.deletePartialSubmission());
    }
    
    @Test
    public void testProperty() {
        assertNull(action.getSum4FundingCatCode());
        action.setSum4FundingCatCode("sum4FundingCatCode");
        assertNotNull(action.getSum4FundingCatCode());
    }
}
