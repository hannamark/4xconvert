package gov.nih.nci.registry.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.enums.AccrualAccessSourceCode;
import gov.nih.nci.pa.service.util.FamilyServiceLocal;
import gov.nih.nci.pa.service.util.StudySiteAccrualAccessServiceLocal;
import gov.nih.nci.registry.action.ManageAccrualAccessAction.TrialCategory;
import gov.nih.nci.registry.service.MockRegistryUserService;
import gov.nih.nci.registry.test.util.RegistrationMockServiceLocator;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.junit.Before;
import org.junit.Test;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Hugh Reinhart
 * @since Nov 13, 2012
 */
public class ManageAccrualAccessActionTest extends AbstractRegWebTest {

    private StudySiteAccrualAccessServiceLocal ssaas;
    private FamilyServiceLocal fs;
    private ManageAccrualAccessAction action;

    @Before
    public void setUp() throws Exception {
        ssaas = mock(StudySiteAccrualAccessServiceLocal.class);
        RegistrationMockServiceLocator.studySiteAccrualAccessService = ssaas;
        fs = mock(FamilyServiceLocal.class);
        RegistrationMockServiceLocator.familyService = fs;
        action = new ManageAccrualAccessAction();
        ManageAccrualAccessAction.AccrualAccessModel model = new ManageAccrualAccessAction.AccrualAccessModel();
        model.setUser(MockRegistryUserService.userList.get(3));
        action.setModel(model);
        UsernameHolder.setUser("reguser2");
        action.setTrialCategory(TrialCategory.ALL.getName());
        action.prepare();
    }

    @Test
    public void executeTest() throws Exception {
        assertEquals(ActionSupport.SUCCESS, action.execute());
        assertNull(action.getModel().getUser());
        assertTrue(action.getModel().getUnassignedTrials().isEmpty());
        assertTrue(action.getModel().getAssignedTrials().isEmpty());
    }

    @Test
    public void assignAllTest() throws Exception {
        assertEquals(ActionSupport.SUCCESS, action.execute());
        action.setUserId(4L);
        action.change();
        assertEquals(1, action.getModel().getUnassignedTrials().size());
        assertEquals(0, action.getModel().getAssignedTrials().size());
        assertEquals(ActionSupport.SUCCESS, action.assignAll());
        verify(ssaas, times(1)).assignTrialLevelAccrualAccess(
                any(RegistryUser.class), 
                eq(AccrualAccessSourceCode.REG_ADMIN_PROVIDED), 
                anyCollectionOf(Long.class), 
                anyString(), 
                any(RegistryUser.class));
    }


    @Test
    public void assignSASubmitterUserNotSelectedTest() throws Exception {
        assertEquals(ActionSupport.SUCCESS, action.execute());
        HttpServletRequest req = ServletActionContext.getRequest();
        String err = (String) req.getAttribute(ManageAccrualAccessAction.FAILURE_MSG);
        assertNull(err);
        assertEquals(ActionSupport.SUCCESS, action.assignUnAssignSASubmitter());
        err = (String) req.getAttribute(ManageAccrualAccessAction.FAILURE_MSG);
        assertEquals("manage.accrual.access.user.not.found.error", err);
    }

    @Test
    public void assignSASubmitterTest() throws Exception {
        assertEquals(ActionSupport.SUCCESS, action.execute());
        action.setUserId(4L);
        action.change();
        assertEquals(1, action.getModel().getUnassignedTrials().size());
        assertEquals(0, action.getModel().getAssignedTrials().size());
        assertEquals(ActionSupport.SUCCESS, action.assignUnAssignSASubmitter());
        verify(ssaas, times(1)).assignTrialLevelAccrualAccess(
                any(RegistryUser.class), 
                eq(AccrualAccessSourceCode.REG_SITE_ADMIN_ROLE), 
                anyCollectionOf(Long.class), 
                anyString(), 
                any(RegistryUser.class));
    }

    @Test
    public void usassignSASubmitterTest() throws Exception {
        MockRegistryUserService.userList.get(3).setSiteAccrualSubmitter(true);
        when(ssaas.getActiveTrialLevelAccrualAccess(any(RegistryUser.class))).thenReturn(Arrays.asList(3L));
        assertEquals(ActionSupport.SUCCESS, action.execute());
        action.setUserId(4L);
        action.change();
        assertEquals(0, action.getModel().getUnassignedTrials().size());
        assertEquals(1, action.getModel().getAssignedTrials().size());
        assertEquals(ActionSupport.SUCCESS, action.assignUnAssignSASubmitter());
        verify(ssaas, times(1)).unassignTrialLevelAccrualAccess(
                any(RegistryUser.class), 
                eq(AccrualAccessSourceCode.REG_SITE_ADMIN_ROLE), 
                anyCollectionOf(Long.class), 
                anyString(), 
                any(RegistryUser.class));
    }


    @Test
    public void assignOFSubmitterUserNotSelectedTest() throws Exception {
        assertEquals(ActionSupport.SUCCESS, action.execute());
        HttpServletRequest req = ServletActionContext.getRequest();
        String err = (String) req.getAttribute(ManageAccrualAccessAction.FAILURE_MSG);
        assertNull(err);
        assertEquals(ActionSupport.SUCCESS, action.assignUnAssignOFSubmitter());
        err = (String) req.getAttribute(ManageAccrualAccessAction.FAILURE_MSG);
        assertEquals("manage.accrual.access.user.not.found.error", err);
    }

    @Test
    public void assignOFSubmitterTest() throws Exception {
        assertEquals(ActionSupport.SUCCESS, action.execute());
        action.setOfUserId(4L);
        assertEquals(0, action.getModel().getUnassignedTrials().size());
        assertEquals(0, action.getModel().getAssignedTrials().size());
        assertEquals(ActionSupport.SUCCESS, action.assignUnAssignOFSubmitter());
        verify(ssaas, never()).assignTrialLevelAccrualAccess(
                any(RegistryUser.class), 
                any(AccrualAccessSourceCode.class), 
                anyCollectionOf(Long.class), 
                anyString(), 
                any(RegistryUser.class));
        verify(fs, times(1)).assignFamilyAccrualAccess(any(RegistryUser.class), any(RegistryUser.class), anyString());
    }


    @Test
    public void unassignOFSubmitterTest() throws Exception {
        MockRegistryUserService.userList.get(3).setFamilyAccrualSubmitter(true);
        when(ssaas.getActiveTrialLevelAccrualAccess(any(RegistryUser.class))).thenReturn(Arrays.asList(3L));
        assertEquals(ActionSupport.SUCCESS, action.execute());
        action.setOfUserId(4L);
        assertEquals(0, action.getModel().getUnassignedTrials().size());
        assertEquals(0, action.getModel().getAssignedTrials().size());
        assertEquals(ActionSupport.SUCCESS, action.assignUnAssignOFSubmitter());
        verify(ssaas, never()).unassignTrialLevelAccrualAccess(
                any(RegistryUser.class), 
                any(AccrualAccessSourceCode.class), 
                anyCollectionOf(Long.class), 
                anyString(), 
                any(RegistryUser.class));
        verify(fs, times(1)).unassignAllAccrualAccess(any(RegistryUser.class), any(RegistryUser.class));
    }

    @Test
    public void assignmentHistoryTest() throws Exception {
        assertEquals("history", action.assignmentHistory());
    }

    @Test
    public void assignmentByTrialTest() throws Exception {
        assertEquals("byTrial", action.assignmentByTrial());
    }
}
