/**
 * 
 */
package gov.nih.nci.registry.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.registry.dto.ProprietaryTrialDTO;

import java.io.File;
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
public class SubmitProprietaryTrialActionTest extends AbstractRegWebTest {
    private static final String FILE_NAME = "ProtocolDoc.doc";
    private SubmitProprietaryTrialAction action = new SubmitProprietaryTrialAction();
    @Test
    public void testPropertyTrialDTO() {
        assertNull(action.getTrialDTO());
        action.setTrialDTO(new ProprietaryTrialDTO());
        assertNotNull(action.getTrialDTO());
        
    }
    @Test
    public void testPropertyProtocolDoc() {
        assertNull(action.getProtocolDoc());
        action.setProtocolDoc(new File(FILE_NAME));
        assertNotNull(action.getProtocolDoc());
        
    }
    @Test
    public void testProtocolFileNameProperty(){
        assertNull(action.getProtocolDocFileName());
        action.setProtocolDocFileName("protocolDocFileName");
        assertNotNull(action.getProtocolDocFileName());
    }
    @Test
    public void testOtherDocProperty(){
        assertNull(action.getOtherDocument());
        action.setOtherDocument(new File(FILE_NAME));
        assertNotNull(action.getOtherDocument());
    }
    @Test
    public void testOtherFileNameProperty(){
        assertNull(action.getOtherDocumentFileName());
        action.setOtherDocumentFileName("otherDocFileName");
        assertNotNull(action.getOtherDocumentFileName());
    }
    @Test
    public void testTrialActionProperty(){
       assertNotNull(action.getTrialAction());
       action.setTrialAction(null);
       assertNull(action.getTrialAction());
    }
    @Test
    public void testServletResponseProperty(){
        assertNull(action.getServletResponse());
        action.setServletResponse(null);
        assertNull(action.getServletResponse());
    }
    @Test
    public void testSelectedTrialTypeProperty() {
        assertNotNull(action.getSelectedTrialType());
        action.setSelectedTrialType(null);
        assertNull(action.getSelectedTrialType());
    }
    @Test
    public void testSelectTypeOfTrial() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        request.setSession(session);
        ServletActionContext.setRequest(request);
        assertEquals("show_Disclaimer_Page", action.selectTypeOfTrial());
        session = new MockHttpSession();
        session.setAttribute("disclaimer", "accept");
        request.setSession(session);
        ServletActionContext.setRequest(request);
        assertEquals("success",action.selectTypeOfTrial());
        session = new MockHttpSession();
        session.setAttribute("disclaimer", "test");
        request.setSession(session);
        ServletActionContext.setRequest(request);
        assertEquals("show_Disclaimer_Page",action.selectTypeOfTrial());
    }
    @Test
    public void testExcute(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        request.setSession(session);
        ServletActionContext.setRequest(request);
        assertEquals("success", action.execute());
    }
    @Test
    public void testEdit() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        request.setSession(session);
        assertEquals("edit", action.edit());
    }
    @Test 
    public void testCancle() {
        assertEquals("redirect_to_search", action.cancel());
    }
    @Test
    public void testReview() throws Exception{
        action.setTrialDTO(getMockProprietaryTrialDTO());
        URL fileUrl = ClassLoader.getSystemClassLoader().getResource(FILE_NAME);
        File f = new File(fileUrl.toURI());
        action.setProtocolDoc(f);
        action.setOtherDocument(f);
        action.setProtocolDocFileName(FILE_NAME);
        action.setOtherDocumentFileName(FILE_NAME);
        assertEquals("review", action.review());
    }
    @Test
    public void testReviewFileNotFound() throws Exception{
        action.setTrialDTO(getMockProprietaryTrialDTO());
        File f = new File(FILE_NAME);
        action.setProtocolDoc(f);
        action.setOtherDocument(f);
        action.setProtocolDocFileName(FILE_NAME);
        action.setOtherDocumentFileName(FILE_NAME);
        assertEquals("error", action.review());
    }
    @Test
    public void testReviewNoDoc() throws Exception{
        action.setTrialDTO(getMockProprietaryTrialDTO());
        assertEquals("review", action.review());
    }
    @Test
    public void testReviewMissingPhasePurpose() throws Exception{
        action.setTrialDTO(getMockProprietaryTrialDTO());
        action.getTrialDTO().setPhaseCode(null);
        action.getTrialDTO().setPrimaryPurposeCode(null);
        assertEquals("review", action.review());
    }
    @Test
    public void testReviewSiteRecStatus() throws Exception{
        action.setTrialDTO(getMockProprietaryTrialDTO());
        action.getTrialDTO().setSiteStatusCode("Recruiting");
        assertEquals("error", action.review());
        assertTrue(action.getActionErrors().contains("Date Opened for Acrual must be a valid date for Recruiting"));
        action.setTrialDTO(getMockProprietaryTrialDTO());
        action.getTrialDTO().setSiteStatusCode("Recruiting");
        action.getTrialDTO().setDateOpenedforAccrual("12/09/2009");
        assertEquals("review", action.review());
        action.setTrialDTO(getMockProprietaryTrialDTO());
        action.getTrialDTO().setSiteStatusCode("Terminated");
        action.getTrialDTO().setDateOpenedforAccrual("11/09/2009");
        action.getTrialDTO().setDateClosedforAccrual("12/09/2009");
        assertEquals("review", action.review());
        action.setTrialDTO(getMockProprietaryTrialDTO());
        action.getTrialDTO().setSiteStatusCode("Terminated");
        action.getTrialDTO().setDateOpenedforAccrual("11/09/2009");
        action.getTrialDTO().setDateClosedforAccrual("10/09/2009");
        assertEquals("error", action.review());
        assertTrue(action.getActionErrors().contains("Date Closed for Accrual must be same or bigger  than Date Opened for Accrual."));
    }
    @Test
    public void testReviewMissingNCTNumberAndProtocolDoc() throws Exception{
        action.setTrialDTO(getMockProprietaryTrialDTO());
        action.getTrialDTO().setNctIdentifier(null);
        URL fileUrl = ClassLoader.getSystemClassLoader().getResource(FILE_NAME);
        File f = new File(fileUrl.toURI());
        action.setOtherDocument(f);
        action.setOtherDocumentFileName(FILE_NAME);
        assertEquals("error", action.review());
    }
    @Test
    public void testReviewInvalidDocAndMissingFiled() throws Exception{
        action.setTrialDTO(getMockProprietaryTrialDTO());
        action.getTrialDTO().setLocalSiteIdentifier(null);
        URL fileUrl = ClassLoader.getSystemClassLoader().getResource(FILE_NAME);
        File f = new File(fileUrl.toURI());
        action.setOtherDocument(f);
        action.setOtherDocumentFileName("filename.zip");
        assertEquals("error", action.review());
        action.setTrialDTO(getMockProprietaryTrialDTO());
        action.getTrialDTO().setNctIdentifier(null);
        action.getTrialDTO().setPhaseCode(null);
        assertEquals("error", action.review());
        
    }
    @Test
    public void testCreateWithNoDTO() {
        assertEquals("error", action.create());
    }
    @Test
    public void testCreate() {
        HttpSession sess = new MockHttpSession();
        ProprietaryTrialDTO tDto = getMockProprietaryTrialDTO();
        tDto.setDocDtos(getDocumentDtos());
        sess.setAttribute("trialDTO", tDto);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(sess);
        ServletActionContext.setRequest(request);
        assertEquals("review", action.create());

        tDto.setSummaryFourFundingCategoryCode("summaryFourFundingCategoryCode");
        tDto.setSummaryFourOrgIdentifier("2");
        tDto.setOfficialTitle("testthrowException");
        sess.setAttribute("trialDTO", tDto);
        request = new MockHttpServletRequest();
        request.setSession(sess);
        ServletActionContext.setRequest(request);
        assertEquals("error", action.create());
    }
}
