package gov.nih.nci.registry.action;

import static org.junit.Assert.assertTrue;
import java.util.Date;

import org.apache.struts2.dispatcher.StreamResult;

import org.apache.struts2.ServletActionContext;
import org.junit.Test;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpServletResponse;
import com.mockrunner.mock.web.MockHttpSession;

import gov.nih.nci.pa.dto.FamilyDTO;
import gov.nih.nci.pa.service.util.FamilyProgramCodeServiceLocal;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProgramCodesActionTest extends AbstractRegWebTest {
	
	@Test
    public void testAjaxChangeDate() throws Exception {        
        ProgramCodesAction action = getAction();
        action.setPoId("12345");
        action.setReportingDate("12/12/2000");        
        assertTrue(action.ajaxChangeDate() instanceof StreamResult);        
    }
	
	@Test
	public void testajaxChangeLength() throws Exception {
		ProgramCodesAction action = getAction();
		action.setPoId("12345");
		action.setReportingLength("10");
        assertTrue(action.ajaxChangeLength() instanceof StreamResult);
	}
	
	private ProgramCodesAction getAction() throws Exception {
		ProgramCodesAction action = new ProgramCodesAction();
        action.setServletRequest(getRequest());
        action.setServletResponse(getResponse());
        action.prepare();
        action.setFamilyProgramCodeService(getProgramCodeServiceMock());
        return action;
    }
	
	private FamilyProgramCodeServiceLocal getProgramCodeServiceMock() {
		final FamilyProgramCodeServiceLocal mock = mock(FamilyProgramCodeServiceLocal.class);
		
		FamilyDTO dto = new FamilyDTO(Long.valueOf("12345"), Long.valueOf("123456"), new Date(), 12);
		
		when(mock.getFamilyDTOByPoId(any(Long.class)))
                .thenReturn(dto);
                
		when(mock.update(any(FamilyDTO.class)))
                .thenReturn(dto);
		
		return mock;
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
