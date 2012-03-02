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
import java.util.Arrays;

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
    private final SubmitProprietaryTrialAction action = new SubmitProprietaryTrialAction();
    @Test
    public void testProperty() {
        assertNull(action.getSum4FundingCatCode());
        action.setSum4FundingCatCode("sum4FundingCatCode");
        assertNotNull(action.getSum4FundingCatCode());
    }
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
        assertTrue(action.getOtherDocument().length==0);
        action.setOtherDocument(new File[] {new File(FILE_NAME)});
        assertEquals(new File(FILE_NAME), action.getOtherDocument()[0]);
    }
    @Test
    public void testOtherFileNameProperty(){
        assertTrue(action.getOtherDocumentFileName().length==0);
        action.setOtherDocumentFileName(new String[] {"otherDocFileName"});
        assertEquals("otherDocFileName", action.getOtherDocumentFileName()[0]);
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
        assertEquals("success", action.selectTypeOfTrial());
    }
    @Test
    public void testExcute(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        request.setSession(session);
        ServletActionContext.setRequest(request);
        assertEquals("redirect_to_search",action.execute());
        action.setSum4FundingCatCode("sum4FundingCatCode");
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
        action.setOtherDocument(new File[] {f});
        action.setProtocolDocFileName(FILE_NAME);
        action.setOtherDocumentFileName(new String[] {FILE_NAME});
        assertEquals("review", action.review());
    }
    @Test
    public void testReviewFileNotFound() throws Exception{
        action.setTrialDTO(getMockProprietaryTrialDTO());
        File f = new File(FILE_NAME);
        action.setProtocolDoc(f);
        action.setOtherDocument(new File[] {f});
        action.setProtocolDocFileName(FILE_NAME);
        action.setOtherDocumentFileName(new String[] {FILE_NAME});
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
        action.getTrialDTO().setSiteStatusCode("Active");
        assertEquals("error", action.review());
        assertTrue(action.getActionErrors().contains("Date Opened for Accrual must be a valid date for Active"));
        action.setTrialDTO(getMockProprietaryTrialDTO());
        action.getTrialDTO().setSiteStatusCode("Active");
        action.getTrialDTO().setDateOpenedforAccrual("12/09/2009");
        assertEquals("review", action.review());
        action.setTrialDTO(getMockProprietaryTrialDTO());
        action.getTrialDTO().setSiteStatusCode("Administratively Complete");
        action.getTrialDTO().setDateOpenedforAccrual("11/09/2009");
        action.getTrialDTO().setDateClosedforAccrual("12/09/2009");
        assertEquals("review", action.review());
        action.setTrialDTO(getMockProprietaryTrialDTO());
        action.getTrialDTO().setSiteStatusCode("Administratively Complete");
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
        action.setOtherDocument(new File[] {(f)});
        action.setOtherDocumentFileName(new String[] {FILE_NAME});
        assertEquals("error", action.review());
    }
    @Test
    public void testReviewInvalidDocAndMissingFiled() throws Exception{
        action.setTrialDTO(getMockProprietaryTrialDTO());
        action.getTrialDTO().setLocalSiteIdentifier(null);
        URL fileUrl = ClassLoader.getSystemClassLoader().getResource(FILE_NAME);
        File f = new File(fileUrl.toURI());
        action.setOtherDocument(new File[] {f});
        action.setOtherDocumentFileName(new String[] {"filename.zip"});
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
