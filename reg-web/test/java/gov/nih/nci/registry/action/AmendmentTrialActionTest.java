/**
 * 
 */
package gov.nih.nci.registry.action;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.pa.util.CtrpHibernateHelper;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.registry.dto.TrialDTO;
import gov.nih.nci.registry.util.Constants;
import gov.nih.nci.registry.util.TestHibernateHelper;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import javax.servlet.http.HttpSession;

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
public class AmendmentTrialActionTest extends AbstractRegWebTest {
    
    private AmendmentTrialAction trialAction;
    private static final String FILE_NAME = "ProtocolDoc.doc";
    private static CtrpHibernateHelper testHelper = new TestHibernateHelper();
    @Before 
    public void setup(){
        HibernateUtil.testHelper = testHelper;
        Session session = HibernateUtil.getCurrentSession();
        session.clear();
    }
    @Test
    public void testView() throws Exception {
        trialAction = new AmendmentTrialAction();
        trialAction.setStudyProtocolId("1");
        //trialAction.view();
        primeData();
        assertEquals("success",trialAction.view());
    }
    @Test
    public void testViewWithIdInRequest() throws Exception {
        trialAction = new AmendmentTrialAction();
        HttpSession sess = new MockHttpSession();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setupAddParameter("studyProtocolId", "1");
        request.setSession(sess);
        ServletActionContext.setRequest(request);
        //trialAction.view();
        assertEquals("success",trialAction.view());
    }
    @Test
    public void testEdit() throws Exception {
        trialAction = new AmendmentTrialAction();
        assertEquals("edit", trialAction.edit());
    }
    @Test
    public void testReview() throws Exception{
        trialAction = new AmendmentTrialAction();
        trialAction.setTrialDTO(getMockTrialDTO());
        URL fileUrl = ClassLoader.getSystemClassLoader().getResource(FILE_NAME);
        File f = new File(fileUrl.toURI());

        trialAction.setProtocolDoc(f);
        trialAction.setIrbApproval(f);
        trialAction.setChangeMemoDoc(f);
        
        trialAction.setProtocolDocFileName(FILE_NAME);
        trialAction.setIrbApprovalFileName(FILE_NAME);
        trialAction.setChangeMemoDocFileName(FILE_NAME);
        assertEquals("review", trialAction.review());
       
        
    }
    @Test
    public void testReviewWithAllDoc() throws Exception{
        trialAction = new AmendmentTrialAction();
        trialAction.setTrialDTO(getMockTrialDTO());
        URL fileUrl = ClassLoader.getSystemClassLoader().getResource(FILE_NAME);
        File f = new File(fileUrl.toURI());
        trialAction.setProtocolDoc(f);
        trialAction.setIrbApproval(f);
        trialAction.setChangeMemoDoc(f);
        trialAction.setInformedConsentDocument(f);
        trialAction.setParticipatingSites(f);
        trialAction.setProtocolHighlightDocument(f);
        
        trialAction.setProtocolDocFileName(FILE_NAME);
        trialAction.setIrbApprovalFileName(FILE_NAME);
        trialAction.setChangeMemoDocFileName(FILE_NAME);
        trialAction.setInformedConsentDocumentFileName(FILE_NAME);
        trialAction.setParticipatingSitesFileName(FILE_NAME);
        trialAction.setProtocolHighlightDocumentFileName(FILE_NAME);
        assertEquals("review", trialAction.review());
    }
    @Test
    public void testReviewWithError() throws Exception{
        trialAction = new AmendmentTrialAction();
        TrialDTO dto = getMockTrialDTO();
        dto.setPhaseCode("Other");
        dto.setContactEmail("dhh");
        dto.setContactPhone("");
        dto.setStartDate("startDate");
        dto.setAmendmentDate("01/20/2010");
        dto.setCompletionDate("completionDate");
        dto.setStatusDate("statusDate");
        dto.setResponsiblePartyType("");
        dto.setPrimaryPurposeCode("Other");
        dto.setStatusCode("Administratively Complete");
        URL fileUrl = ClassLoader.getSystemClassLoader().getResource(FILE_NAME);
        File f = new File(fileUrl.toURI());
        trialAction.setProtocolDoc(f);
        trialAction.setProtocolDocFileName(FILE_NAME);
        fileUrl = ClassLoader.getSystemClassLoader().getResource("test.txt");
        f = new File(fileUrl.toURI());
        trialAction.setIrbApproval(f);
        trialAction.setIrbApprovalFileName("test.txt");
        trialAction.setTrialDTO(dto);
        assertEquals("error", trialAction.review());
    }
    @Test
    public void testReviewWithAllDatesEmpty() throws Exception{
        trialAction = new AmendmentTrialAction();
        TrialDTO dto = getMockTrialDTO();
        dto.setIdentifier("2");
        dto.setStartDate("");
        dto.setStatusDate("");
        dto.setAmendmentDate("");
        dto.setCompletionDate("");
        URL fileUrl = ClassLoader.getSystemClassLoader().getResource(FILE_NAME);
        File f = new File(fileUrl.toURI());
        trialAction.setProtocolDoc(f);
        trialAction.setIrbApproval(f);
        trialAction.setChangeMemoDoc(f);
        
        trialAction.setProtocolDocFileName(FILE_NAME);
        trialAction.setIrbApprovalFileName(FILE_NAME);
        trialAction.setChangeMemoDocFileName(FILE_NAME);
        trialAction.setTrialDTO(dto);
        assertEquals("error", trialAction.review());
    }
    @Test
    public void testReviewWithStatusCodeChangedToApproved() throws Exception{
        trialAction = new AmendmentTrialAction();
        TrialDTO dto = getMockTrialDTO();
        dto.setStatusCode("Approved");
        URL fileUrl = ClassLoader.getSystemClassLoader().getResource(FILE_NAME);
        File f = new File(fileUrl.toURI());
        trialAction.setProtocolDoc(f);
        trialAction.setIrbApproval(f);
        trialAction.setChangeMemoDoc(f);
        
        trialAction.setProtocolDocFileName(FILE_NAME);
        trialAction.setIrbApprovalFileName(FILE_NAME);
        trialAction.setChangeMemoDocFileName(FILE_NAME);
        trialAction.setTrialDTO(dto);
        assertEquals("error", trialAction.review());
    }
    @Test
    public void testReviewWithStatusCodeChangedToAdComplete() throws Exception{
        trialAction = new AmendmentTrialAction();
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

        trialAction.setProtocolDoc(f);
        trialAction.setIrbApproval(f);
        trialAction.setChangeMemoDoc(f);
        
        trialAction.setProtocolDocFileName(FILE_NAME);
        trialAction.setIrbApprovalFileName(FILE_NAME);
        trialAction.setChangeMemoDocFileName(FILE_NAME);
        trialAction.setTrialDTO(dto);
        assertEquals("error", trialAction.review());
    }

    @Test
    public void testReviewWithGrants() throws Exception{
        trialAction = new AmendmentTrialAction();
        trialAction.setTrialDTO(getMockTrialDTO());
        
        URL fileUrl = ClassLoader.getSystemClassLoader().getResource(FILE_NAME);
        File f = new File(fileUrl.toURI());

        trialAction.setProtocolDoc(f);
        trialAction.setIrbApproval(f);
        trialAction.setChangeMemoDoc(f);
        
        trialAction.setProtocolDocFileName(FILE_NAME);
        trialAction.setIrbApprovalFileName(FILE_NAME);
        trialAction.setChangeMemoDocFileName(FILE_NAME);
        //set grant in session
        HttpSession sess = new MockHttpSession();
        MockHttpServletRequest request = new MockHttpServletRequest();
        sess.setAttribute(Constants.GRANT_LIST, getfundingDtos());
        request.setSession(sess);
        ServletActionContext.setRequest(request);
        assertEquals("review", trialAction.review());
    }
    @Test
    public void testReviewWithIndIde() throws URISyntaxException{
        trialAction = new AmendmentTrialAction();
        trialAction.setTrialDTO(getMockTrialDTO());
        URL fileUrl = ClassLoader.getSystemClassLoader().getResource(FILE_NAME);
        File f = new File(fileUrl.toURI());

        trialAction.setProtocolDoc(f);
        trialAction.setIrbApproval(f);
        trialAction.setChangeMemoDoc(f);
        
        trialAction.setProtocolDocFileName(FILE_NAME);
        trialAction.setIrbApprovalFileName(FILE_NAME);
        trialAction.setChangeMemoDocFileName(FILE_NAME);
        //set grant in session
        HttpSession sess = new MockHttpSession();
        MockHttpServletRequest request = new MockHttpServletRequest();
        sess.setAttribute(Constants.INDIDE_LIST, getIndDtos());
        request.setSession(sess);
        ServletActionContext.setRequest(request);
        assertEquals("review", trialAction.review());
        
    }
    @Test
    public void testReviewWhenRespPartyIsSponsor() throws URISyntaxException{
        trialAction = new AmendmentTrialAction();
        TrialDTO dto = getMockTrialDTO();
        dto.setResponsiblePartyType("sponsor");
        dto.setResponsiblePersonIdentifier("2");
        dto.setResponsiblePersonName("responsiblePersonName");
        trialAction.setTrialDTO(dto);
        URL fileUrl = ClassLoader.getSystemClassLoader().getResource(FILE_NAME);
        File f = new File(fileUrl.toURI());

        trialAction.setProtocolDoc(f);
        trialAction.setIrbApproval(f);
        trialAction.setChangeMemoDoc(f);
        
        trialAction.setProtocolDocFileName(FILE_NAME);
        trialAction.setIrbApprovalFileName(FILE_NAME);
        trialAction.setChangeMemoDocFileName(FILE_NAME);
        assertEquals("review", trialAction.review());
    }
    @Test
    public void testReviewWithErrorHavingGrantsAndInd() throws Exception{
        trialAction = new AmendmentTrialAction();
        TrialDTO dto = getMockTrialDTO();
        dto.setPhaseCode("");
        dto.setPrimaryPurposeCode("");
        dto.setContactEmail("dhh");
        dto.setContactPhone("");
        dto.setFundingDtos(getfundingDtos());
        dto.setIndIdeDtos(getIndDtos());
        trialAction.setTrialDTO(dto);
        
        URL fileUrl = ClassLoader.getSystemClassLoader().getResource(FILE_NAME);
        File f = new File(fileUrl.toURI());

        trialAction.setProtocolDoc(f);
        trialAction.setIrbApproval(f);
        
        trialAction.setProtocolDocFileName(FILE_NAME);
        trialAction.setIrbApprovalFileName(FILE_NAME);

        //set grant in session
        HttpSession sess = new MockHttpSession();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(sess);
        ServletActionContext.setRequest(request);
        assertEquals("error", trialAction.review());
    }
    @Test 
    public void testCancle() {
        trialAction = new AmendmentTrialAction();
        assertEquals("redirect_to_search", trialAction.cancel());
    }
    @Test
    public void testAmendWhenRespPartyIsPI(){
        trialAction = new AmendmentTrialAction();
        HttpSession sess = new MockHttpSession();
        sess.setAttribute("trialDTO", getMockTrialDTO());
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(sess);
        ServletActionContext.setRequest(request);
        assertEquals("redirect_to_search", trialAction.amend());
    }
    @Test
    public void testAmendWhenRespPartyIsSponsor(){
        trialAction = new AmendmentTrialAction();
        HttpSession sess = new MockHttpSession();
        TrialDTO dto = getMockTrialDTO();
        dto.setResponsiblePartyType("sponsor");
        dto.setResponsiblePersonIdentifier("2");
        dto.setResponsiblePersonName("responsiblePersonName");
        dto.setDocDtos(getDocumentDtos());
        sess.setAttribute("trialDTO", dto);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(sess);
        ServletActionContext.setRequest(request);
        assertEquals("redirect_to_search", trialAction.amend());
    }
    @Test
    public void testAmendWithSummaryFour(){
        trialAction = new AmendmentTrialAction();
        HttpSession sess = new MockHttpSession();
        TrialDTO dto = getMockTrialDTO();
        dto.setSummaryFourFundingCategoryCode("National");
        dto.setSummaryFourOrgIdentifier("2");
        dto.setSummaryFourOrgName("summaryFourOrgName");
        sess.setAttribute("trialDTO", dto);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(sess);
        ServletActionContext.setRequest(request);
        assertEquals("redirect_to_search", trialAction.amend());
    }
    @Test
    public void testAmendWithGrants(){
        trialAction = new AmendmentTrialAction();
        HttpSession sess = new MockHttpSession();
        TrialDTO dto = getMockTrialDTO();
        dto.setFundingDtos(getfundingDtos());
        sess.setAttribute("trialDTO", dto);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(sess);
        ServletActionContext.setRequest(request);
        assertEquals("redirect_to_search", trialAction.amend());
    }
    @Test
    public void testAmendWithIndIde(){
        trialAction = new AmendmentTrialAction();
        HttpSession sess = new MockHttpSession();
        TrialDTO dto = getMockTrialDTO();
        dto.setIndIdeDtos(getIndDtos());
        sess.setAttribute("trialDTO", dto);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(sess);
        ServletActionContext.setRequest(request);
        assertEquals("redirect_to_search", trialAction.amend());
    }
    @Test
    public void testAmendWhenPhaseAndPurposeOther(){
        trialAction = new AmendmentTrialAction();
        HttpSession sess = new MockHttpSession();
        TrialDTO dto = getMockTrialDTO();
        dto.setPhaseCode("Other");
        dto.setPhaseOtherText("phaseOtherText");
        dto.setPrimaryPurposeCode("Other");
        dto.setPrimaryPurposeOtherText("primaryPurposeOtherText");
        sess.setAttribute("trialDTO", dto);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(sess);
        ServletActionContext.setRequest(request);
        assertEquals("redirect_to_search", trialAction.amend());
    }
    @Test
    public void testAmendWithException(){
        trialAction = new AmendmentTrialAction();
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
        assertEquals("error", trialAction.amend());
    }
    @Test
    public void testAmendWhenNoDTO(){
        trialAction = new AmendmentTrialAction();
        assertEquals("error", trialAction.amend());     
    }
    @Test
    public void testTrialDTOProperty(){
        trialAction = new AmendmentTrialAction();
        assertNull(trialAction.getTrialDTO());
        trialAction.setTrialDTO(getMockTrialDTO());
        assertNotNull(trialAction.getTrialDTO());
    }
    @Test
    public void testTrialActionProperty(){
        trialAction = new AmendmentTrialAction();
        assertNull(trialAction.getTrialAction());
        trialAction.setTrialAction("trialAction");
        assertNotNull(trialAction.getTrialAction());
    }
    @Test
    public void testStudyProtocolIdProperty(){
        trialAction = new AmendmentTrialAction();
        assertNull(trialAction.getStudyProtocolId());
        trialAction.setStudyProtocolId("studyProtocolId");
        assertNotNull(trialAction.getStudyProtocolId());
    }
    @Test
    public void testServletResponseProperty(){
        trialAction = new AmendmentTrialAction();
        assertNull(trialAction.getServletResponse());
        trialAction.setServletResponse(null);
        assertNull(trialAction.getServletResponse());
    }
    @Test
    public void testProtocolDocProperty(){
        trialAction = new AmendmentTrialAction();
        assertNull(trialAction.getProtocolDoc());
        trialAction.setProtocolDoc(new File(FILE_NAME));
        assertNotNull(trialAction.getProtocolDoc());
    }
    @Test
    public void testIRBDocProperty(){
        trialAction = new AmendmentTrialAction();
        assertNull(trialAction.getIrbApproval());
        trialAction.setIrbApproval(new File(FILE_NAME));
        assertNotNull(trialAction.getIrbApproval());
    }
    @Test
    public void testInformedConsentDocProperty(){
        trialAction = new AmendmentTrialAction();
        assertNull(trialAction.getInformedConsentDocument());
        trialAction.setInformedConsentDocument(new File(FILE_NAME));
        assertNotNull(trialAction.getInformedConsentDocument());
    }
    @Test
    public void testParticipatingSiteDocProperty(){
        trialAction = new AmendmentTrialAction();
        assertNull(trialAction.getParticipatingSites());
        trialAction.setParticipatingSites(new File(FILE_NAME));
        assertNotNull(trialAction.getParticipatingSites());
    }
    @Test
    public void testChangeMemoDocProperty(){
        trialAction = new AmendmentTrialAction();
        assertNull(trialAction.getChangeMemoDoc());
        trialAction.setChangeMemoDoc(new File(FILE_NAME));
        assertNotNull(trialAction.getChangeMemoDoc());
    }
    @Test
    public void testProtocolHighlightDocProperty(){
        trialAction = new AmendmentTrialAction();
        assertNull(trialAction.getProtocolHighlightDocument());
        trialAction.setProtocolHighlightDocument(new File(FILE_NAME));
        assertNotNull(trialAction.getProtocolHighlightDocument());
    }
    @Test
    public void testProtocolFileNameProperty(){
        trialAction = new AmendmentTrialAction();
        assertNull(trialAction.getProtocolDocFileName());
        trialAction.setProtocolDocFileName("protocolDocFileName");
        assertNotNull(trialAction.getProtocolDocFileName());
    }
    @Test
    public void testIRBFileNameProperty(){
        trialAction = new AmendmentTrialAction();
        assertNull(trialAction.getIrbApprovalFileName());
        trialAction.setIrbApprovalFileName("irbApprovalFileName");
        assertNotNull(trialAction.getIrbApprovalFileName());
    }
    @Test
    public void testInformedConsentFileNameProperty(){
        trialAction = new AmendmentTrialAction();
        assertNull(trialAction.getInformedConsentDocumentFileName());
        trialAction.setInformedConsentDocumentFileName("informedConsentDocumentFileName");
        assertNotNull(trialAction.getInformedConsentDocumentFileName());
    }
    @Test
    public void testParticipatingSiteFileNameProperty(){
        trialAction = new AmendmentTrialAction();
        assertNull(trialAction.getParticipatingSitesFileName());
        trialAction.setParticipatingSitesFileName("participatingSitesFileName");
        assertNotNull(trialAction.getParticipatingSitesFileName());
    }
    @Test
    public void testChangeMemoFileNameProperty(){
        trialAction = new AmendmentTrialAction();
        assertNull(trialAction.getChangeMemoDocFileName());
        trialAction.setChangeMemoDocFileName("changeMemoDocFileName");
        assertNotNull(trialAction.getChangeMemoDocFileName());
    }
    @Test
    public void testProtocolHighlightFileNameProperty(){
        trialAction = new AmendmentTrialAction();
        assertNull(trialAction.getProtocolHighlightDocumentFileName());
        trialAction.setProtocolHighlightDocumentFileName("protocolHighlight");
        assertNotNull(trialAction.getProtocolHighlightDocumentFileName());
    }
    @Test
    public void testValidateTrialDates(){
        trialAction = new AmendmentTrialAction();
        TrialDTO dto = getMockTrialDTO();
        trialAction.setTrialDTO(dto);
        assertEquals("error", trialAction.review());
    }
    @Test
    public void testTrialDatesEmpty(){
        trialAction = new AmendmentTrialAction();
        TrialDTO dto = getMockTrialDTO();
        dto.setStartDate("");
        dto.setStatusDate("");
        dto.setCompletionDate("");
        trialAction.setTrialDTO(dto);
        assertEquals("error", trialAction.review());
    }
    @Test
    public void testValidateTrialDatesRule18Pass() {
        trialAction = new AmendmentTrialAction();
        TrialDTO dto = getMockTrialDTO();
        dto.setStatusDate("02/22/2009");
        trialAction.setTrialDTO(dto);
        assertEquals("error", trialAction.review());
    }
    @Test
    public void testValidateTrialDatesRule18Fail(){
        trialAction = new AmendmentTrialAction();
        TrialDTO dto = getMockTrialDTO();
        dto.setStatusDate("02/22/2010");
        trialAction.setTrialDTO(dto);
        assertEquals("error", trialAction.review());
        assertTrue(trialAction.getFieldErrors().containsKey("trialDTO.statusDate"));
    }
    @Test
    public void testInValidStatusTransition () {
        trialAction = new AmendmentTrialAction();
        TrialDTO dto = getMockTrialDTO();
        dto.setStatusCode("Approved");
        dto.setCompletionDateType("Actual");
        trialAction.setTrialDTO(dto);
        assertEquals("error", trialAction.review());
        assertNotNull(trialAction.getActionErrors());
    }
    @Test
    public void testStatusToAdComplete() throws URISyntaxException {
        trialAction = new AmendmentTrialAction();
        TrialDTO dto = getMockTrialDTO();
        dto.setStatusCode("Administratively Complete");
        dto.setReason("reason");
        dto.setCompletionDateType("Actual");
        dto.setCompletionDate("01/10/2008");
        trialAction.setTrialDTO(dto);
        URL fileUrl = ClassLoader.getSystemClassLoader().getResource(FILE_NAME);
        File f = new File(fileUrl.toURI());

        trialAction.setProtocolDoc(f);
        trialAction.setIrbApproval(f);
        trialAction.setChangeMemoDoc(f);
        
        trialAction.setProtocolDocFileName(FILE_NAME);
        trialAction.setIrbApprovalFileName(FILE_NAME);
        trialAction.setChangeMemoDocFileName(FILE_NAME);

        assertEquals("error", trialAction.review());
        assertNotNull(trialAction.getActionErrors());
    }
    @Test
    public void testInReviewStatus() throws Exception{
        trialAction = new AmendmentTrialAction();
        TrialDTO dto = getMockTrialDTO();
        dto.setStatusCode("In Review");
        trialAction.setTrialDTO(dto);
        URL fileUrl = ClassLoader.getSystemClassLoader().getResource(FILE_NAME);
        File f = new File(fileUrl.toURI());

        trialAction.setProtocolDoc(f);
        trialAction.setIrbApproval(f);
        trialAction.setChangeMemoDoc(f);
        
        trialAction.setProtocolDocFileName(FILE_NAME);
        trialAction.setIrbApprovalFileName(FILE_NAME);
        trialAction.setChangeMemoDocFileName(FILE_NAME);
        assertEquals("error", trialAction.review());
    }
}
