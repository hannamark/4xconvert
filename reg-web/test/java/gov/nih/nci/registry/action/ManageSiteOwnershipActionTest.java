package gov.nih.nci.registry.action;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.registry.util.SelectedRegistryUser;
import gov.nih.nci.registry.util.SelectedStudyProtocol;
import gov.nih.nci.registry.util.TrialUtil;

import java.util.ArrayList;

import org.apache.struts2.ServletActionContext;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpSession;
import com.mockrunner.mock.web.MockServletContext;

/**
 * @author Denis G. Krylov
 *
 */
public class ManageSiteOwnershipActionTest extends AbstractRegWebTest {
    private ManageSiteOwnershipAction action;
    
    private PAServiceUtils paServiceUtils;
    private TrialUtil trialUtil;

    @Before
    public void before() throws PAException {
        paServiceUtils = Mockito.mock(PAServiceUtils.class);
        trialUtil = Mockito.mock(TrialUtil.class);

        StudySiteDTO studySiteDTO = new StudySiteDTO();
        studySiteDTO.setIdentifier(IiConverter.convertToStudySiteIi(1L));
        when(trialUtil.getParticipatingSite(any(Ii.class), any(Ii.class)))
                .thenReturn(studySiteDTO);
    }

    @Test
    public void testSearch() throws PAException {
        action = new ManageSiteOwnershipAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(new MockHttpSession());
        request.setRemoteUser("RegUser");
        ServletActionContext.setServletContext(new MockServletContext());
        ServletActionContext.setRequest(request);
        assertEquals("viewResults", action.search());
    }

    @Test
    public void testView() throws PAException {
        action = new ManageSiteOwnershipAction();
        assertEquals("viewResults", action.view());
    }

    @Test
	public void testSetRegUser() throws PAException {
        action = new ManageSiteOwnershipAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setupAddParameter("regUserId", "3");
		request.setupAddParameter("isOwner", "true");
		request.setSession(new MockHttpSession());
        request.setRemoteUser("RegUser");
        ServletActionContext.setServletContext(new MockServletContext());
        ServletActionContext.setRequest(request);
        action.search();
        request.getSession().setAttribute("regUsersList", action.getRegistryUsers());
        action.setRegUser();
    }

    @Test
    public void testSetTrial() throws PAException {
        action = new ManageSiteOwnershipAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setupAddParameter("trialId", "3");
        request.setupAddParameter("isSelected", "true");
        request.setSession(new MockHttpSession());
        request.setRemoteUser("RegUser");
        ServletActionContext.setServletContext(new MockServletContext());
        ServletActionContext.setRequest(request);
        action.search();
        request.getSession().setAttribute("studyProtocolsList", action.getStudyProtocols());
        action.setTrial();
    }

    @Test
    public void testAssignOwnershipException() throws PAException {
        action = new ManageSiteOwnershipAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(new MockHttpSession());
        request.setRemoteUser("RegUser");
        ServletActionContext.setServletContext(new MockServletContext());
        ServletActionContext.setRequest(request);
        try {
            action.assignOwnership();
        } catch(PAException e) {
            //expected
        }
    }

    @Test
    public void testAssignOwnership() throws PAException {
        action = new ManageSiteOwnershipAction();
        action.setPaServiceUtil(paServiceUtils);
        action.setTrialUtil(trialUtil);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(new MockHttpSession());
        request.setRemoteUser("RegUser");
        ServletActionContext.setServletContext(new MockServletContext());
        ServletActionContext.setRequest(request);
        action.search();
        request.getSession().setAttribute("regUsersList", new ArrayList<SelectedRegistryUser>());
        request.getSession().setAttribute("studyProtocolsList", new ArrayList<SelectedStudyProtocol>());
        assertEquals("viewResults", action.assignOwnership());
        action.search();
        action.getRegistryUsers().get(0).setSelected(true);
        action.getStudyProtocols().get(0).setSelected(true);
        request.getSession().setAttribute("regUsersList", action.getRegistryUsers());
        request.getSession().setAttribute("studyProtocolsList", action.getStudyProtocols());
        assertEquals("viewResults", action.assignOwnership());
    }

    @Test
    public void testUnAssignOwnershipException() throws PAException {
        action = new ManageSiteOwnershipAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(new MockHttpSession());
        request.setRemoteUser("RegUser");
        ServletActionContext.setServletContext(new MockServletContext());
        ServletActionContext.setRequest(request);
        try {
            action.unassignOwnership();
        } catch(PAException e) {
            //expected
        }
    }

    @Test
    public void testUnAssignOwnership() throws PAException {
        action = new ManageSiteOwnershipAction();
        action.setPaServiceUtil(paServiceUtils);
        action.setTrialUtil(trialUtil);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(new MockHttpSession());
        request.setRemoteUser("RegUser");
        ServletActionContext.setServletContext(new MockServletContext());
        ServletActionContext.setRequest(request);
        action.search();
        request.getSession().setAttribute("regUsersList", new ArrayList<SelectedRegistryUser>());
        request.getSession().setAttribute("studyProtocolsList", new ArrayList<SelectedStudyProtocol>());
        assertEquals("viewResults", action.unassignOwnership());
        action.search();
        action.getRegistryUsers().get(0).setSelected(true);
        action.getStudyProtocols().get(0).setSelected(true);
        request.getSession().setAttribute("regUsersList", action.getRegistryUsers());
        request.getSession().setAttribute("studyProtocolsList", action.getStudyProtocols());
        assertEquals("viewResults", action.unassignOwnership());
    }
}
