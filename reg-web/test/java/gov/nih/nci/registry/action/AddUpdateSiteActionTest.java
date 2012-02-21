package gov.nih.nci.registry.action;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.*;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.iso.dto.ParticipatingSiteDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.ParticipatingSiteServiceLocal;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.StudySiteContactServiceLocal;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.registry.dto.SubmittedOrganizationDTO;
import gov.nih.nci.registry.util.TrialUtil;

import org.apache.struts2.ServletActionContext;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Denis G. Krylov
 * 
 */
public class AddUpdateSiteActionTest extends AbstractRegWebTest {
    private AddUpdateSiteAction action;

    private PAServiceUtils paServiceUtils;
    private TrialUtil trialUtil;
    private RegistryUserServiceLocal registryUserServiceLocal;
    private ParticipatingSiteServiceLocal participatingSiteServiceLocal;
    private StudySiteContactServiceLocal studySiteContactServiceLocal;
    private StudyProtocolServiceLocal studyProtocolServiceLocal;

    private SubmittedOrganizationDTO existentSiteDTO;

    @Before
    public void before() throws PAException {
        paServiceUtils = mock(PAServiceUtils.class);
        trialUtil = mock(TrialUtil.class);
        registryUserServiceLocal = mock(RegistryUserServiceLocal.class);
        participatingSiteServiceLocal = mock(ParticipatingSiteServiceLocal.class);
        studySiteContactServiceLocal = mock(StudySiteContactServiceLocal.class);
        studyProtocolServiceLocal = mock (StudyProtocolServiceLocal.class);

        StudySiteDTO studySiteDTO = new StudySiteDTO();
        studySiteDTO.setIdentifier(IiConverter.convertToStudySiteIi(1L));
        when(
                trialUtil.getParticipatingSite(
                        eq(IiConverter.convertToStudyProtocolIi(1L)),
                        eq(IiConverter.convertToPoHealthCareFacilityIi("1"))))
                .thenReturn(studySiteDTO);

        when(paServiceUtils.getPoHcfIi("1")).thenReturn(
                IiConverter.convertToPoHealthCareFacilityIi("1"));

        RegistryUser user = getRegistryUser();
        when(registryUserServiceLocal.getUser(any(String.class))).thenReturn(
                user);

        existentSiteDTO = getExistentSiteDTO();
        when(trialUtil.getSubmittedOrganizationDTO(eq(studySiteDTO)))
                .thenReturn(existentSiteDTO);

        ParticipatingSiteDTO participatingSiteDTO = new ParticipatingSiteDTO();
        participatingSiteDTO.setIdentifier(IiConverter.convertToStudySiteIi(1L));
        when(
                participatingSiteServiceLocal.updateStudySiteParticipant(
                        any(StudySiteDTO.class),
                        any(StudySiteAccrualStatusDTO.class)))
                .thenReturn(participatingSiteDTO);

    }

    /**
     * @return
     */
    private RegistryUser getRegistryUser() {
        RegistryUser user = new RegistryUser();
        user.setId(1L);
        user.setAffiliateOrg("NCI");
        user.setAffiliatedOrganizationId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        return user;
    }

    /**
     * 
     */
    private SubmittedOrganizationDTO getExistentSiteDTO() {
        SubmittedOrganizationDTO dto = new SubmittedOrganizationDTO();
        dto.setDateClosedforAccrual("01/31/2012");
        dto.setDateOpenedforAccrual("01/01/2012");
        dto.setId("1");
        dto.setInvestigator("John Doe");
        dto.setInvestigatorId(1L);
        dto.setName("NCI");
        dto.setNameInvestigator("John Doe");
        dto.setProgramCode("PCODE");
        dto.setRecruitmentStatus("Active");
        dto.setRecruitmentStatusDate("01/15/2012");
        dto.setSiteLocalTrialIdentifier("LOCAL_ID");
        dto.setTargetAccrualNumber("1000");
        return dto;
    }

    /**
     * 
     */
    private void prepareAction() {
        action = new AddUpdateSiteAction();
        action.setTrialUtil(trialUtil);
        action.setPaServiceUtil(paServiceUtils);
        action.setRegistryUserService(registryUserServiceLocal);
        action.setParticipatingSiteService(participatingSiteServiceLocal);
        action.setStudySiteContactService(studySiteContactServiceLocal);
        action.setStudyProtocolService(studyProtocolServiceLocal);
        action.setStudyProtocolId("1");
    }

    /**
     * @throws PAException
     * 
     */
    @Test
    public void testPopulateSiteDTO() throws PAException {
        prepareAction();
        action.populateSiteDTO();
        assertEquals(existentSiteDTO, action.getSiteDTO());
    }

    @Test
    public void testView() throws PAException {
        prepareAction();
        String fwd = action.view();
        assertEquals(existentSiteDTO, action.getSiteDTO());
        assertEquals(ActionSupport.SUCCESS, fwd);
        assertEquals(
                existentSiteDTO,
                ServletActionContext.getRequest().getSession()
                        .getAttribute(TrialUtil.SESSION_TRIAL_SITE_ATTRIBUTE));
    }

    @Test
    public void testSuccessfulUpdateSite() throws PAException {
        prepareAction();

        action.setSiteDTO(existentSiteDTO);
        String fwd = action.save();

        ArgumentCaptor<StudySiteDTO> ssDTO = ArgumentCaptor
                .forClass(StudySiteDTO.class);
        ArgumentCaptor<StudySiteAccrualStatusDTO> accDTO = ArgumentCaptor
                .forClass(StudySiteAccrualStatusDTO.class);

        verify(participatingSiteServiceLocal, times(1))
                .updateStudySiteParticipant(ssDTO.capture(), accDTO.capture());
        assertEquals(IiConverter.convertToStudySiteIi(1L), ssDTO.getValue()
                .getIdentifier());

        // assertEquals(ActionSupport.SUCCESS, fwd);

    }

}
