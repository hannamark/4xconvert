/**
 * 
 */
package gov.nih.nci.registry.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.registry.dto.TrialDTO;
import gov.nih.nci.registry.util.Constants;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.junit.Test;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpSession;

/**
 * @author Vrushali
 *
 */
public class SubmitTrialActionTest extends AbstractRegWebTest{
    private static final String FILE_NAME = "ProtocolDoc.doc";
    private SubmitTrialAction submitAction;
    @Test
    public void testExcute() throws Exception {
        submitAction = new SubmitTrialAction();
        submitAction.execute();
        assertEquals("success",submitAction.execute());
    }
    @Test
    public void testEdit() throws Exception {
        submitAction = new SubmitTrialAction();
        assertEquals("edit", submitAction.edit());
    }
    @Test 
    public void testCancle() {
        submitAction = new SubmitTrialAction();
        assertEquals("redirect_to_search", submitAction.cancel());
    }
    @Test
    public void testReview() throws Exception{
        submitAction = new SubmitTrialAction();
        submitAction.setTrialDTO(getMockTrialDTO());
        URL fileUrl = ClassLoader.getSystemClassLoader().getResource(FILE_NAME);
        File f = new File(fileUrl.toURI());
        submitAction.setProtocolDoc(f);
        submitAction.setIrbApproval(f);
        submitAction.setProtocolDocFileName(FILE_NAME);
        submitAction.setIrbApprovalFileName(FILE_NAME);
        assertEquals("review", submitAction.review());
    }
    @Test
    public void testReviewWithAllDoc() throws Exception{
        submitAction = new SubmitTrialAction();
        submitAction.setTrialDTO(getMockTrialDTO());
        URL fileUrl = ClassLoader.getSystemClassLoader().getResource(FILE_NAME);
        File f = new File(fileUrl.toURI());
        submitAction.setProtocolDoc(f);
        submitAction.setIrbApproval(f);
        submitAction.setInformedConsentDocument(f);
        submitAction.setParticipatingSites(f);
        submitAction.setOtherDocument(f);
        submitAction.setProtocolDocFileName(FILE_NAME);
        submitAction.setIrbApprovalFileName(FILE_NAME);
        submitAction.setInformedConsentDocumentFileName(FILE_NAME);
        submitAction.setParticipatingSitesFileName(FILE_NAME);
        submitAction.setOtherDocumentFileName(FILE_NAME);
        assertEquals("review", submitAction.review());
    }
    @Test
    public void testReviewWithStatusCodeChangedToAdComplete() throws Exception{
        submitAction = new SubmitTrialAction();
        TrialDTO dto = getMockTrialDTO();
        dto.setStatusDate("01/20/2009");
        dto.setStatusCode("Administratively Complete");
        dto.setCompletionDateType("Anticipated");
        dto.setCompletionDate("01/20/2009");
        dto.setStartDateType("Anticipated");
        dto.setStartDate("01/20/2008");
        dto.setReason("reason");
        URL fileUrl = ClassLoader.getSystemClassLoader().getResource(FILE_NAME);
        File f = new File(fileUrl.toURI());
        submitAction.setProtocolDoc(f);
        submitAction.setIrbApproval(f);


        submitAction.setProtocolDocFileName(FILE_NAME);
        submitAction.setIrbApprovalFileName(FILE_NAME);
        submitAction.setTrialDTO(dto);
        assertEquals("error", submitAction.review());
    }

    @Test
    public void testReviewWithGrants() throws Exception{
        submitAction = new SubmitTrialAction();
        submitAction.setTrialDTO(getMockTrialDTO());
        
        URL fileUrl = ClassLoader.getSystemClassLoader().getResource(FILE_NAME);
        File f = new File(fileUrl.toURI());
        submitAction.setProtocolDoc(f);
        submitAction.setIrbApproval(f);
        submitAction.setProtocolDocFileName(FILE_NAME);
        submitAction.setIrbApprovalFileName(FILE_NAME);

        //set grant in session
        HttpSession sess = new MockHttpSession();
        MockHttpServletRequest request = new MockHttpServletRequest();
        sess.setAttribute(Constants.GRANT_LIST, getfundingDtos());
        request.setSession(sess);
        ServletActionContext.setRequest(request);
        assertEquals("review", submitAction.review());
    }
    @Test
    public void testReviewWithIndIde() throws URISyntaxException{
        submitAction = new SubmitTrialAction();
        submitAction.setTrialDTO(getMockTrialDTO());
        URL fileUrl = ClassLoader.getSystemClassLoader().getResource(FILE_NAME);
        File f = new File(fileUrl.toURI());
        submitAction.setProtocolDoc(f);
        submitAction.setIrbApproval(f);

        submitAction.setProtocolDocFileName(FILE_NAME);
        submitAction.setIrbApprovalFileName(FILE_NAME);
        //set Ind in session
        HttpSession sess = new MockHttpSession();
        MockHttpServletRequest request = new MockHttpServletRequest();
        sess.setAttribute(Constants.INDIDE_LIST, getIndDtos());
        request.setSession(sess);
        ServletActionContext.setRequest(request);
        assertEquals("review", submitAction.review());
    }
    @Test
    public void testReviewWithError() throws Exception{
        submitAction = new SubmitTrialAction();
        TrialDTO dto = getMockTrialDTO();
        dto.setPhaseCode("Other");
        dto.setContactEmail("dhh");
        dto.setContactPhone("");
        dto.setStartDate("startDate");
        dto.setCompletionDate("completionDate");
        dto.setStatusDate("statusDate");
        dto.setResponsiblePartyType("");
        dto.setPrimaryPurposeCode("Other");
        dto.setStatusCode("Administratively Complete");
        URL fileUrl = ClassLoader.getSystemClassLoader().getResource(FILE_NAME);
        File f = new File(fileUrl.toURI());
        submitAction.setProtocolDoc(f);
        submitAction.setProtocolDocFileName(FILE_NAME);
        fileUrl = ClassLoader.getSystemClassLoader().getResource("test.txt");
        f = new File(fileUrl.toURI());
        submitAction.setIrbApproval(f);
        submitAction.setIrbApprovalFileName("test.txt");
        submitAction.setTrialDTO(dto);
        assertEquals("error", submitAction.review());
    }
    @Test
    public void testCreateWithNoDTO(){
        submitAction = new SubmitTrialAction();
        assertEquals("error", submitAction.create());      
    }
    @Test
    public void testCreateWithDupTrial(){
        submitAction = new SubmitTrialAction();
        TrialDTO dto = getMockTrialDTO();
        dto.setLocalProtocolIdentifier("DupTestinglocalStudyProtocolId");
        HttpSession sess = new MockHttpSession();
        sess.setAttribute("trialDTO", dto);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(sess);
        ServletActionContext.setRequest(request);
        assertEquals("error", submitAction.create());     
    }
    @Test
    public void testCreateWithDupliTrial(){
        submitAction = new SubmitTrialAction();
        TrialDTO dto = getMockTrialDTO();
        dto.setLeadOrganizationIdentifier("2");
        dto.setLocalProtocolIdentifier("DupTestinglocalStudyProtocolId");
        HttpSession sess = new MockHttpSession();
        sess.setAttribute("trialDTO", dto);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(sess);
        ServletActionContext.setRequest(request);
        assertEquals("redirect_to_search", submitAction.create());     
    }
    @Test
    public void testCreateWithRespPartyAsPi(){
        submitAction = new SubmitTrialAction();
        TrialDTO dto = getMockTrialDTO();
        HttpSession sess = new MockHttpSession();
        sess.setAttribute("trialDTO", dto);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(sess);
        ServletActionContext.setRequest(request);
        assertEquals("redirect_to_search", submitAction.create());     
    }
    @Test
    public void testCreateWithRespPartyAsSponsor(){
        submitAction = new SubmitTrialAction();
        TrialDTO dto = getMockTrialDTO();
        dto.setResponsiblePartyType("sponsor");
        dto.setResponsiblePersonIdentifier("3");
        HttpSession sess = new MockHttpSession();
        sess.setAttribute("trialDTO", dto);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(sess);
        ServletActionContext.setRequest(request);
        assertEquals("redirect_to_search", submitAction.create());     
    }
    @Test
    public void testCreateWithException(){
        submitAction = new SubmitTrialAction();
        HttpSession sess = new MockHttpSession();
        TrialDTO dto = getMockTrialDTO();
        dto.setPhaseCode("Other");
        dto.setPhaseOtherText("phaseOtherText");
        dto.setPrimaryPurposeCode("Other");
        dto.setPrimaryPurposeOtherText("primaryPurposeOtherText");
        dto.setOfficialTitle("testthrowException");
        sess.setAttribute("trialDTO", dto);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(sess);
        ServletActionContext.setRequest(request);
        assertEquals("error", submitAction.create());
    }

    @Test
    public void testTrialDTOProperty(){
        submitAction = new SubmitTrialAction();
        assertNull(submitAction.getTrialDTO());
        submitAction.setTrialDTO(getMockTrialDTO());
        assertNotNull(submitAction.getTrialDTO());
    }
    @Test
    public void testTrialActionProperty(){
        submitAction = new SubmitTrialAction();
        assertNotNull(submitAction.getTrialAction());
        submitAction.setTrialAction(null);
        assertNull(submitAction.getTrialAction());
    }
    @Test
    public void testIdProperty(){
        submitAction = new SubmitTrialAction();
        assertNull(submitAction.getId());
        submitAction.setId(1L);
        assertNotNull(submitAction.getId());
    }
    @Test
    public void testCbValueProperty(){
        submitAction = new SubmitTrialAction();
        assertNull(submitAction.getCbValue());
        submitAction.setCbValue(1L);
        assertNotNull(submitAction.getCbValue());
    }
    @Test
    public void testPageProperty(){
        submitAction = new SubmitTrialAction();
        assertNull(submitAction.getPage());
        submitAction.setPage("page");
        assertNotNull(submitAction.getPage());
    }

    @Test
    public void testServletResponseProperty(){
        submitAction = new SubmitTrialAction();
        assertNull(submitAction.getServletResponse());
        submitAction.setServletResponse(null);
        assertNull(submitAction.getServletResponse());
    }
    @Test
    public void testProtocolDocProperty() throws URISyntaxException{
        submitAction = new SubmitTrialAction();
        assertNull(submitAction.getProtocolDoc());
        URL fileUrl = ClassLoader.getSystemClassLoader().getResource(FILE_NAME);
        File f = new File(fileUrl.toURI());     
        submitAction.setProtocolDoc(f);
        assertNotNull(submitAction.getProtocolDoc());
    }
    @Test
    public void testIRBDocProperty(){
        submitAction = new SubmitTrialAction();
        assertNull(submitAction.getIrbApproval());
        submitAction.setIrbApproval(new File(FILE_NAME));
        assertNotNull(submitAction.getIrbApproval());
    }
    @Test
    public void testInformedConsentDocProperty(){
        submitAction = new SubmitTrialAction();
        assertNull(submitAction.getInformedConsentDocument());
        submitAction.setInformedConsentDocument(new File(FILE_NAME));
        assertNotNull(submitAction.getInformedConsentDocument());
    }
    @Test
    public void testParticipatingSiteDocProperty(){
        submitAction = new SubmitTrialAction();
        assertNull(submitAction.getParticipatingSites());
        submitAction.setParticipatingSites(new File(FILE_NAME));
        assertNotNull(submitAction.getParticipatingSites());
    }
    @Test
    public void testOtherDocProperty(){
        submitAction = new SubmitTrialAction();
        assertNull(submitAction.getOtherDocument());
        submitAction.setOtherDocument(new File(FILE_NAME));
        assertNotNull(submitAction.getOtherDocument());
    }
    @Test
    public void testProtocolFileNameProperty(){
        submitAction = new SubmitTrialAction();
        assertNull(submitAction.getProtocolDocFileName());
        submitAction.setProtocolDocFileName("protocolDocFileName");
        assertNotNull(submitAction.getProtocolDocFileName());
    }
    @Test
    public void testIRBFileNameProperty(){
        submitAction = new SubmitTrialAction();
        assertNull(submitAction.getIrbApprovalFileName());
        submitAction.setIrbApprovalFileName("irbApprovalFileName");
        assertNotNull(submitAction.getIrbApprovalFileName());
    }
    @Test
    public void testInformedConsentFileNameProperty(){
        submitAction = new SubmitTrialAction();
        assertNull(submitAction.getInformedConsentDocumentFileName());
        submitAction.setInformedConsentDocumentFileName("informedConsentDocumentFileName");
        assertNotNull(submitAction.getInformedConsentDocumentFileName());
    }
    @Test
    public void testParticipatingSiteFileNameProperty(){
        submitAction = new SubmitTrialAction();
        assertNull(submitAction.getParticipatingSitesFileName());
        submitAction.setParticipatingSitesFileName("participatingSitesFileName");
        assertNotNull(submitAction.getParticipatingSitesFileName());
    }
    @Test
    public void testOtherFileNameProperty(){
        submitAction = new SubmitTrialAction();
        assertNull(submitAction.getOtherDocumentFileName());
        submitAction.setOtherDocumentFileName("otherDocFileName");
        assertNotNull(submitAction.getOtherDocumentFileName());
    }
    @Test
    public void testReviewWithNoTrialDate() throws Exception{
        submitAction = new SubmitTrialAction();
        TrialDTO dto = getMockTrialDTO();
        dto.setStartDate("");
        dto.setCompletionDate("");
        dto.setStatusDate("");
        dto.setStatusCode("");
        dto.setStartDateType("");
        dto.setCompletionDateType("");
        submitAction.setTrialDTO(dto);
        assertEquals("error", submitAction.review());
    }

    @Test
    public void testValidateTrialDatesRule18Fail() {
        submitAction = new SubmitTrialAction();
        TrialDTO dto = getMockTrialDTO();
        dto.setStatusDate("02/22/2010");
        submitAction.setTrialDTO(dto);
        assertEquals("error", submitAction.review());
        assertTrue(submitAction.getFieldErrors().containsKey("trialDTO.statusDate"));
    }
    @Test
    public void testValidateTrialDatesRule18Pass() {
        submitAction = new SubmitTrialAction();
        TrialDTO dto = getMockTrialDTO();
        dto.setStatusDate("02/22/2009");
        submitAction.setTrialDTO(dto);
        assertEquals("error", submitAction.review());
    }
    @Test
    public void testValidateTrialDatesRule19ActualFail() {
        submitAction = new SubmitTrialAction();
        TrialDTO dto = getMockTrialDTO();
        dto.setStartDate("02/22/2010");
        dto.setStartDateType("Actual");
        submitAction.setTrialDTO(dto);
        assertEquals("error", submitAction.review());
        assertTrue(submitAction.getFieldErrors().containsKey("trialDTO.startDate"));
    }
    @Test
    public void testValidateTrialDatesRule19AnticipatedFail(){
        submitAction = new SubmitTrialAction();
        TrialDTO dto = getMockTrialDTO();
        dto.setStartDate("02/22/2000");
        dto.setStartDateType("Anticipated");
        submitAction.setTrialDTO(dto);
        assertEquals("error", submitAction.review());
        assertTrue(submitAction.getFieldErrors().containsKey("trialDTO.startDate"));
    }
    @Test
    public void testValidateTrialDatesRule20ActualFail(){
        submitAction = new SubmitTrialAction();
        TrialDTO dto = getMockTrialDTO();
        dto.setCompletionDate("02/22/2010");
        dto.setCompletionDateType("Actual");
        submitAction.setTrialDTO(dto);
        assertEquals("error", submitAction.review());
        assertTrue(submitAction.getFieldErrors().containsKey("trialDTO.completionDate"));
    }
    @Test
    public void testValidateTrialDatesRule20AnticipatedFail(){
        submitAction = new SubmitTrialAction();
        TrialDTO dto = getMockTrialDTO();
        dto.setCompletionDate("02/22/2000");
        dto.setCompletionDateType("Anticipated");
        submitAction.setTrialDTO(dto);
        assertEquals("error", submitAction.review());
        assertTrue(submitAction.getFieldErrors().containsKey("trialDTO.completionDate"));
    }
    @Test
    public void testValidateTrialDatesRule22ApprovedFail(){
        submitAction = new SubmitTrialAction();
        TrialDTO dto = getMockTrialDTO();
        dto.setStatusCode("Approved");
        dto.setStartDateType("Actual");
        submitAction.setTrialDTO(dto);
        assertEquals("error", submitAction.review());
        assertTrue(submitAction.getFieldErrors().containsKey("trialDTO.startDateType"));
    }
    @Test
    public void testValidateTrialDatesRule23CompletedFail(){
        submitAction = new SubmitTrialAction();
        TrialDTO dto = getMockTrialDTO();
        dto.setStatusCode("Complete");
        dto.setStartDateType("Anticipated");
        submitAction.setTrialDTO(dto);
        assertEquals("error", submitAction.review());
        assertTrue(submitAction.getFieldErrors().containsKey("trialDTO.completionDate"));
    }
    @Test
    public void testValidateTrialDatesRule25Fail(){
        submitAction = new SubmitTrialAction();
        TrialDTO dto = getMockTrialDTO();
        dto.setStartDate("01/22/2010");
        dto.setCompletionDate("01/22/2009");
        submitAction.setTrialDTO(dto);
        assertEquals("error", submitAction.review());
        assertTrue(submitAction.getFieldErrors().containsKey("trialDTO.startDate"));
    }
    @Test
    public void testValidateTrialDatesRule21Fail(){
        submitAction = new SubmitTrialAction();
        //Trial Start date is greater Current Trial Status Date
        TrialDTO dto = getMockTrialDTO();
        dto.setStatusCode("Active");
        dto.setStatusDate("01/22/2008");
        dto.setStartDate("01/22/2009");
        submitAction.setTrialDTO(dto);
        assertEquals("error", submitAction.review());
        assertTrue(submitAction.getFieldErrors().containsKey("trialDTO.startDate"));
    }
    @Test
    public void testValidateTrialDatesRule21PassStatusAndStartDatesAreSame() throws URISyntaxException {
        //Trial Start date is same Current Trial Status Date
        submitAction = new SubmitTrialAction();
        TrialDTO dto = getMockTrialDTO();
        dto.setStatusCode("Active");
        dto.setStatusDate("01/22/2009");
        dto.setStartDate("01/22/2009");
        submitAction.setTrialDTO(dto);
        URL fileUrl = ClassLoader.getSystemClassLoader().getResource(FILE_NAME);
        File f = new File(fileUrl.toURI());
        submitAction.setProtocolDoc(f);
        submitAction.setIrbApproval(f);
        submitAction.setProtocolDocFileName(FILE_NAME);
        submitAction.setIrbApprovalFileName(FILE_NAME);
        assertEquals("review", submitAction.review());
    }
    @Test
    public void testValidateTrialDatesRule21PassStartSmallerThanStatusDates() throws URISyntaxException {
        submitAction = new SubmitTrialAction();
        TrialDTO dto = getMockTrialDTO();
        dto.setStatusCode("Active");
        //Trial Start date is smaller Current Trial Status Date
        dto.setStatusDate("01/22/2009");
        dto.setStartDate("01/22/2008");
        submitAction.setTrialDTO(dto);
        URL fileUrl = ClassLoader.getSystemClassLoader().getResource(FILE_NAME);
        File f = new File(fileUrl.toURI());
        submitAction.setProtocolDoc(f);
        submitAction.setIrbApproval(f);
        submitAction.setProtocolDocFileName(FILE_NAME);
        submitAction.setIrbApprovalFileName(FILE_NAME);
        assertEquals("review", submitAction.review());
    }
    @Test
    public void testValidateRule21StartTypeAnticipated() {
        submitAction = new SubmitTrialAction();
        TrialDTO dto = getMockTrialDTO();
        dto.setStatusCode("Active");
        dto.setStartDateType("Anticipated");
        //Trial Start date is smaller or same Current Trial Status Date
        dto.setStatusDate("01/22/2009");
        dto.setStartDate("01/22/2008");
        submitAction.setTrialDTO(dto);
        assertEquals("error", submitAction.review());
    }
}
