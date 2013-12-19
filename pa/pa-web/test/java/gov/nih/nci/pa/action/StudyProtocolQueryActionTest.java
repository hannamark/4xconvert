/**
 *
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyProtocolService;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.service.util.TSRReportGeneratorServiceRemote;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.service.MockCorrelationUtils;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.apache.struts2.ServletActionContext;
import org.junit.Before;
import org.junit.Test;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;


/**
 * @author asharma
 *
 */
public class StudyProtocolQueryActionTest extends AbstractPaActionTest {

    private StudyProtocolQueryAction spqAction;
    private StudyProtocolQueryCriteria criteria;
    @Override
    @Before
    public void setUp() {
        spqAction = new StudyProtocolQueryAction();
        spqAction.setCorrelationUtils(new MockCorrelationUtils());
        spqAction.setServletRequest(getRequest());
        spqAction.setServletResponse(getResponse());
        spqAction.prepare();       
        criteria = new StudyProtocolQueryCriteria();
        criteria.setNciIdentifier("NCI-2009-00001");
        criteria.setCtgovXmlRequiredIndicator("");
        spqAction.setCriteria(criteria);
        getRequest().setUserInRole(Constants.SUABSTRACTOR, true);
        UsernameHolder.setUser("suAbstractor");
        getSession().setAttribute(Constants.IS_SU_ABSTRACTOR, Boolean.TRUE);
        
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.StudyProtocolQueryAction#execute()}.
     * @throws PAException in case of error
     */
    @Test
    public void testExecute() throws PAException {
        assertEquals("success", spqAction.execute());
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.StudyProtocolQueryAction#showCriteria()}.
     * @throws PAException in case of error
     */
    @Test
    public void testShowCriteria() throws PAException {
        assertEquals("criteriaProtected", spqAction.showCriteria());
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.StudyProtocolQueryAction#query()}.
     * @throws PAException in case of error
     */
    @Test
    public void testQuery() throws PAException {
        assertEquals("success", spqAction.query());
    }
    

    /**
     * Test method for {@link gov.nih.nci.pa.action.StudyProtocolQueryAction#query()}.
     * @throws PAException in case of error
     */
    @Test
    public void testQueryNew() throws PAException {
        List<StudyProtocolQueryDTO> records = new ArrayList<StudyProtocolQueryDTO>();
        StudyProtocolQueryDTO dto = new StudyProtocolQueryDTO();
        dto.setNciIdentifier("NCI-2009-00001");
        records.add(dto);
        ProtocolQueryServiceLocal protocolQueryService = mock(ProtocolQueryServiceLocal.class); 
        when(protocolQueryService.getStudyProtocolByCriteria(criteria)).thenReturn(records);
        spqAction.setProtocolQueryService(protocolQueryService);
        assertEquals("success", spqAction.query());
    }
    
    @Test
    public void testAnyIdentifierTypeHandling() throws PAException {
        StudyProtocolQueryAction action = new StudyProtocolQueryAction();
        action.setServletRequest(getRequest());
        action.prepare();
        StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
        criteria.setIdentifierType("All");
        action.setIdentifier("ID");
        action.setCriteria(criteria);
        assertEquals("success", action.query());
        
        assertEquals("ID", criteria.getAnyTypeIdentifier());
        assertNull(criteria.getCtepIdentifier());
        assertNull(criteria.getDcpIdentifier());
        assertNull(criteria.getNctNumber());
        assertNull(criteria.getLeadOrganizationTrialIdentifier());
        assertNull(criteria.getNciIdentifier());
        assertNull(criteria.getOtherIdentifier());        
        
    }
    
    @Test
    public void testIdentifiersValidation() throws PAException {
        StudyProtocolQueryAction action = new StudyProtocolQueryAction();
        action.setServletRequest(getRequest());
        action.prepare();
        StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
        criteria.setIdentifierType("");
        action.setIdentifier("ABC");
        criteria.setOfficialTitle("");
        criteria.setOrganizationType("");
        criteria.setPhaseCode("");
        criteria.setPrimaryPurposeCode("");
        action.setCriteria(criteria);

        assertEquals("error", action.query());
        assertEquals("error.studyProtocol.identifierType", action
                .getFieldErrors().get("criteria.identifierType").get(0));

        action.clearErrorsAndMessages();
        criteria.setIdentifierType("All");
        assertEquals("success", action.query());

    }    

    /**
     * Test method for {@link gov.nih.nci.pa.action.StudyProtocolQueryAction#view()}.
     * @throws PAException in case of error
     */
    @Test
    public void testView() throws PAException {
        spqAction.setStudyProtocolId(1L);
        assertEquals("view", spqAction.view());
        List<String> commands = spqAction.getCheckoutCommands();
        assertEquals(3, commands.size());
        assertTrue(commands.contains("adminCheckOut"));
        assertTrue(commands.contains("scientificCheckOut"));
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.StudyProtocolQueryAction#viewTSR()}.
     * @throws PAException in case of error
     */
    @Test
    public void testViewTSR() throws PAException {
        TSRReportGeneratorServiceRemote tsrReportGeneratorService = mock(TSRReportGeneratorServiceRemote.class);
        spqAction.setTsrReportGeneratorService(tsrReportGeneratorService);
        ByteArrayOutputStream reportData = new ByteArrayOutputStream();
        when(tsrReportGeneratorService.generateRtfTsrReport(IiConverter.convertToIi(1L))).thenReturn(reportData);
        getRequest().setupAddParameter("studyProtocolId", "1");
        assertEquals("none", spqAction.viewTSR());
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.StudyProtocolQueryAction#adminCheckOut()}.
     * @throws PAException in case of error
     */
    @Test
    public void testAdminCheckOut() throws PAException {
        spqAction.setStudyProtocolId(1L);
        assertEquals("viewRefresh", spqAction.adminCheckOut());
    }
    
    /**
     * Test method for {@link gov.nih.nci.pa.action.StudyProtocolQueryAction#adminAndScientificCheckOut()}.
     * @throws PAException 
     */
    @Test
    public void testAdminAndScientificCheckOut() throws PAException {
        spqAction.setStudyProtocolId(1L);
        assertEquals("viewRefresh", spqAction.adminAndScientificCheckOut());
    }
    
    /**
     * Test method for {@link gov.nih.nci.pa.action.StudyProtocolQueryAction#adminAndScientificCheckOut()}.
     * @throws PAException 
     */
    @Test
    public void testAdminAndScientificCheckOutFailure() throws PAException {
        getSession().removeAttribute(Constants.IS_SU_ABSTRACTOR);
        spqAction.setStudyProtocolId(1L);
        try {
            spqAction.adminAndScientificCheckOut();
            Assert.fail("Expected an exception, because the user is not a super abstractor.");
        } catch (PAException e) {
        }

    }
    
    /**
     * Test method for {@link gov.nih.nci.pa.action.StudyProtocolQueryAction#adminAndScientificCheckIn()}.
     * @throws PAException 
     */
    @Test
    public void testAdminAndScientificCheckInFailure() throws PAException {
    	getSession().removeAttribute(Constants.IS_SU_ABSTRACTOR);
    	spqAction.setStudyProtocolId(1L);
    	try {
    		spqAction.adminAndScientificCheckIn();
    		Assert.fail("Expected an exception, because the user is not a super abstractor.");
    	} catch (PAException e) {
    		
    	}
    }
    

    /**
     * Test method for {@link gov.nih.nci.pa.action.StudyProtocolQueryAction#adminCheckOut()}.
     * @throws PAException in case of error
     */
    @Test
    public void testAdminCheckIn() throws PAException {
        spqAction.setStudyProtocolId(1L);
        assertEquals("viewRefresh", spqAction.adminCheckOut());
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.StudyProtocolQueryAction#scientificCheckOut()}.
     * @throws PAException in case of error
     */
    @Test
    public void testScientificCheckOut() throws PAException {
        spqAction.setStudyProtocolId(1L);
        assertEquals("viewRefresh", spqAction.scientificCheckOut());
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.StudyProtocolQueryAction#scientificCheckIn()}.
     * @throws PAException in case of error
     */
    @Test
    public void testScientificCheckIn() throws PAException {
        spqAction.setStudyProtocolId(1L);
        assertEquals("viewRefresh", spqAction.scientificCheckIn());
    }
    @Test
    public void testSave() throws PAException {
        spqAction.setStudyProtocolId(1L);
        String result = spqAction.save();
        assertEquals("view", result);
        assertEquals("dashboard.save.success", ServletActionContext.getRequest().getAttribute("successMessage"));
    }

}