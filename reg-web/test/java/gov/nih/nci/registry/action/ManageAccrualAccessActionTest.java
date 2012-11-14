package gov.nih.nci.registry.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.enums.AccrualAccessSourceCode;
import gov.nih.nci.pa.service.util.StudySiteAccrualAccessServiceLocal;
import gov.nih.nci.registry.action.ManageAccrualAccessAction.TrialCategory;
import gov.nih.nci.registry.service.MockRegistryUserService;
import gov.nih.nci.registry.test.util.RegistrationMockServiceLocator;

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
    private ManageAccrualAccessAction action;

    @Before
    public void setUp() throws Exception {
        ssaas = mock(StudySiteAccrualAccessServiceLocal.class);
        RegistrationMockServiceLocator.studySiteAccrualAccessService = ssaas;
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
    public void assignUnAssignSASubmitterTest() throws Exception {
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
}
