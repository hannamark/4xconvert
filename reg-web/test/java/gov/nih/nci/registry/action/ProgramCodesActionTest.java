package gov.nih.nci.registry.action;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.dto.FamilyDTO;
import gov.nih.nci.pa.iso.dto.ProgramCodeDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.FamilyProgramCodeServiceLocal;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.StreamResult;
import org.junit.Test;
import org.springframework.util.ReflectionUtils;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpServletResponse;
import com.mockrunner.mock.web.MockHttpSession;

public class ProgramCodesActionTest extends AbstractRegWebTest {
	
	@Test
	public void handleExceptionTest() throws Exception {
		ProgramCodesAction action = getAction();
		action.setServletRequest(getRequest());
	    action.setServletResponse(getResponse());
	    action.prepare();
	    action.setRegistryUserService(getRegistryServiceMock());	
		action.execute();		
	}
	
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
	
	@Test
	public void testAjaxException() throws Exception {
		ProgramCodesAction action = new ProgramCodesAction();
        action.setServletRequest(getRequest());
        action.setServletResponse(getResponse());
        action.prepare();
        action.setFamilyProgramCodeService(getProgramCodeServiceMock());        
        when(action.getFamilyProgramCodeService().update(any(FamilyDTO.class))).thenThrow(new RuntimeException());        
        assertFalse(action.ajaxChangeLength() instanceof StreamResult);
        assertFalse(action.ajaxChangeDate() instanceof StreamResult);
    }
    
    @Test
    public void testCreateProgramCode() throws Exception {        
        ProgramCodesAction action = getAction();
        action.setPoId("12345");
        assertTrue(action.createProgramCode() instanceof StreamResult);     
        
      try {

          Field field = StreamResult.class.getDeclaredField("inputStream");
          ReflectionUtils.makeAccessible(field);

          StreamResult sr = action.createProgramCode();

          InputStream is = (InputStream) field.get(sr);
          String json = IOUtils.toString(is);
          assertEquals("{\"data\":[]}", json);

      } catch (Exception e) {
          e.printStackTrace();
          fail("should not throw exception");
      }
    }
    
    @Test
    public void testUpdateProgramCode() throws Exception {        
        ProgramCodesAction action = getAction();
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        action.setServletRequest(mockRequest);
        action.setServletResponse(mockResponse);
        
        action.setPoId("12345");
        when(mockRequest.getParameter("currentProgramCode")).thenReturn("PG1");
        when(mockRequest.getParameter("currentProgramCodeId")).thenReturn("1");
        when(mockRequest.getParameter("updatedProgramCode")).thenReturn("PG1-updated");
        when(mockRequest.getParameter("updatedProgramName")).thenReturn("PG1 Name-updated");
        
        assertTrue(action.updateProgramCode() instanceof StreamResult);     
        
      try {

          Field field = StreamResult.class.getDeclaredField("inputStream");
          ReflectionUtils.makeAccessible(field);

          StreamResult sr = action.updateProgramCode();

          InputStream is = (InputStream) field.get(sr);
          String json = IOUtils.toString(is);
          assertEquals("{\"data\":[]}", json);

      } catch (Exception e) {
          e.printStackTrace();
          fail("should not throw exception");
      }
    }
    
    @Test
    public void testFetchProgramCodesForFamily() throws Exception {        
        ProgramCodesAction action = getAction();
        action.setPoId("12345");
        assertTrue(action.fetchProgramCodesForFamily() instanceof StreamResult);     
        
      try {

          Field field = StreamResult.class.getDeclaredField("inputStream");
          ReflectionUtils.makeAccessible(field);

          StreamResult sr = action.fetchProgramCodesForFamily();

          InputStream is = (InputStream) field.get(sr);
          String json = IOUtils.toString(is);
          assertEquals("{\"data\":[{\"isActive\":true,\"programCode\":\"PG1\",\"programName\":\"Program Name1\"}]}", json);

      } catch (Exception e) {
          e.printStackTrace();
          fail("should not throw exception");
      }
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
		createAndAddProgramCodes(dto);
		
		when(mock.getFamilyDTOByPoId(any(Long.class)))
                .thenReturn(dto);
                
		when(mock.update(any(FamilyDTO.class)))
                .thenReturn(dto);
		
		return mock;
	}
	
	private RegistryUserServiceLocal getRegistryServiceMock() throws PAException {
		
		final RegistryUserServiceLocal mock = mock(RegistryUserServiceLocal.class);		
		
		RegistryUser registryUser = mock(RegistryUser.class);
		
	    when(registryUser.getAffiliatedOrganizationId()).thenReturn(null);
	    
	    when(mock.getUser(any(String.class))).thenReturn(registryUser);
		
		return mock;
    }
    
     private void createAndAddProgramCodes(FamilyDTO familyDTO) {

            ProgramCodeDTO pg1 = new ProgramCodeDTO();
            pg1.setProgramName("Program Name1");
            pg1.setProgramCode("PG1");
            pg1.setActive(true);
            familyDTO.getProgramCodes().add(pg1);
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
