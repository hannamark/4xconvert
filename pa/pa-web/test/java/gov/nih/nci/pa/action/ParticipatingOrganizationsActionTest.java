package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.dto.OrganizationWebDTO;
import gov.nih.nci.pa.dto.ParticipatingOrganizationsTabWebDTO;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceRemote;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.service.MockCorrelationUtils;
import gov.nih.nci.service.MockOrganizationCorrelationService;

import org.apache.struts2.ServletActionContext;
import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author hreinhart
 *
 */
public class ParticipatingOrganizationsActionTest extends AbstractPaActionTest {
    private static ParticipatingOrganizationsAction act;
    private static OrganizationCorrelationServiceRemote ocs;
    
    @Before
    public void prepare() throws Exception {
        act = new ParticipatingOrganizationsAction();
        act.prepare();
        act.oCService = new MockOrganizationCorrelationService();
        act.cUtils = new MockCorrelationUtils();
    }

    @Test
    public void listOrganizationsTest() throws Exception {
        // select from menu
        String ar = act.execute();
        assertEquals(ActionSupport.SUCCESS, ar);
        assertNotNull(act.getOrganizationList());
    }
    
    @Test
    public void addOrganizationTest() throws Exception {
        
        // click the add button
        String ar = act.create();
        assertEquals(ActionSupport.SUCCESS, ar);
        
        // try to save without entering any info.
        ar = act.facilitySave();
        assertEquals(Action.ERROR, ar);
        assertTrue(act.hasFieldErrors());
        assertTrue(act.getFieldErrors().containsKey("recStatus"));
        assertTrue(act.getFieldErrors().containsKey("recStatusDate"));
        assertTrue(act.getFieldErrors().containsKey("editOrg.name"));

        // enter recruitment information and save
        act.setRecStatus(RecruitmentStatusCode.NOT_YET_RECRUITING.getCode());
        act.setRecStatusDate(PAUtil.today());
        ar = act.facilitySave();
        assertEquals(Action.ERROR, ar);
        assertTrue(act.hasFieldErrors());
        assertFalse(act.getFieldErrors().containsKey("recStatus"));
        assertFalse(act.getFieldErrors().containsKey("recStatusDate"));
        assertTrue(act.getFieldErrors().containsKey("editOrg.name"));

        // look-up org information and save
        String poIdentifier = "1234";
        String newOrgName = MockOrganizationCorrelationService.getOrgNameFromPoIdentifier(poIdentifier);
        ParticipatingOrganizationsTabWebDTO tab = new ParticipatingOrganizationsTabWebDTO();
        OrgSearchCriteria org = new OrgSearchCriteria();
        org.setOrgName(newOrgName);
        act.setOrgFromPO(org);
        Organization facility = new Organization();
        facility.setIdentifier(poIdentifier);
        facility.setName(newOrgName);
        tab.setFacilityOrganization(facility);
        ServletActionContext.getRequest().getSession().setAttribute(Constants.PARTICIPATING_ORGANIZATIONS_TAB, tab);
        
        ar = act.facilitySave();
        assertEquals(ParticipatingOrganizationsAction.ACT_FACILITY_SAVE, ar);
        assertFalse(act.hasFieldErrors());

        // confirmation message displayed
        String message = (String)ServletActionContext.getRequest().getAttribute(Constants.SUCCESS_MESSAGE);
        assertEquals(Constants.CREATE_MESSAGE, message);

        // select from menu again (return to list screen)
        ar = act.execute();
        assertEquals(ActionSupport.SUCCESS, ar);
        
        // confirm that list contains new org.
        boolean found = false;
        for (OrganizationWebDTO webDto : act.organizationList) {
            if(MockOrganizationCorrelationService.getOrgNameFromPoIdentifier(poIdentifier).equals(webDto.getName())) {
                found = true;
            }
        }
        assertTrue("Added treating site not found.", found);
     }
}
