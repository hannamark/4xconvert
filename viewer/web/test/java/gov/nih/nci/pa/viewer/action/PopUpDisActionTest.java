package gov.nih.nci.pa.viewer.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.pa.iso.dto.PDQDiseaseDTO;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PDQDiseaseServiceLocal;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.ServiceLocator;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.junit.Before;
import org.junit.Test;

import com.mockrunner.mock.web.MockHttpServletRequest;

public class PopUpDisActionTest extends AbstractReportActionTest<PopUpDisAction> {

	
	
	@Before 
	public void setUp() throws PAException {
	  action =  new PopUpDisAction();	  
	  ServiceLocator svcLoc = mock(ServiceLocator.class);
      PaRegistry.getInstance().setServiceLocator(svcLoc);
      PDQDiseaseServiceLocal pdqDisSvc = mock(PDQDiseaseServiceLocal.class);
      when(svcLoc.getDiseaseService()).thenReturn(pdqDisSvc);
      List<PDQDiseaseDTO> diseaseList = new ArrayList<PDQDiseaseDTO>();
      PDQDiseaseDTO pdqDisDto = new PDQDiseaseDTO();
      pdqDisDto.setPreferredName(StConverter.convertToSt("disease"));
      diseaseList.add(pdqDisDto);
      when(pdqDisSvc.search(any(PDQDiseaseDTO.class))).thenReturn(diseaseList);
	}
	
	/**
     * @return MockHttpServletRequest
     */
    protected MockHttpServletRequest getRequest() {
        return (MockHttpServletRequest) ServletActionContext.getRequest();
    }
    
	@Test
	public void testDisplayList() {
	 action.setSearchName("");
	 action.setIncludeSynonym("true");
	 action.setExactMatch("true");
	 action.displayList();
	 assertNotNull(getRequest().getAttribute("failureMessage"));
	}
	
	@Test
	public void testDisplayListWithSearchResults() {
	 action.setSearchName("disease");
	 action.setIncludeSynonym("true");
	 action.setExactMatch("true");
	 assertEquals("success", action.displayList());
	 assertEquals(1, action.getDisWebList().size());
	 assertEquals("disease", action.getDisWebList().get(0).getPreferredName());
	}
}
