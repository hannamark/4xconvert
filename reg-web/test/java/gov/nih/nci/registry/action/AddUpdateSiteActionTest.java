package gov.nih.nci.registry.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.enums.StudySiteContactRoleCode;
import gov.nih.nci.pa.iso.dto.ParticipatingSiteDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.ParticipatingSiteServiceLocal;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.StudySiteContactServiceLocal;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.registry.dto.SubmittedOrganizationDTO;
import gov.nih.nci.registry.util.TrialUtil;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import gov.nih.nci.services.correlation.HealthCareProviderDTO;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
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
    private ClinicalResearchStaffDTO researchStaffDTO;
    private HealthCareProviderDTO healthCareProviderDTO;

    @Before
    public void before() throws PAException {
        paServiceUtils = mock(PAServiceUtils.class);
        trialUtil = mock(TrialUtil.class);
        registryUserServiceLocal = mock(RegistryUserServiceLocal.class);
        participatingSiteServiceLocal = mock(ParticipatingSiteServiceLocal.class);
        studySiteContactServiceLocal = mock(StudySiteContactServiceLocal.class);
        studyProtocolServiceLocal = mock(StudyProtocolServiceLocal.class);

        StudySiteDTO studySiteDTO = new StudySiteDTO();
        studySiteDTO.setIdentifier(IiConverter.convertToStudySiteIi(1L));
        when(
                trialUtil.getParticipatingSite(
                        eq(IiConverter.convertToStudyProtocolIi(1L)),
                        eq(IiConverter.convertToPoHealthCareFacilityIi("1"))))
                .thenReturn(studySiteDTO);

        researchStaffDTO = new ClinicalResearchStaffDTO();
        healthCareProviderDTO = new HealthCareProviderDTO();
        when(paServiceUtils.getPoHcfIi("1")).thenReturn(
                IiConverter.convertToPoHealthCareFacilityIi("1"));
        when(
                paServiceUtils.getCrsDTO(IiConverter.convertToPoPersonIi("1"),
                        "1")).thenReturn(researchStaffDTO);
        when(
                paServiceUtils.getHcpDTO(any(String.class), any(Ii.class),
                        any(String.class))).thenReturn(healthCareProviderDTO);

        RegistryUser user = getRegistryUser();
        when(registryUserServiceLocal.getUser(any(String.class))).thenReturn(
                user);

        existentSiteDTO = getExistentSiteDTO();
        when(trialUtil.getSubmittedOrganizationDTO(eq(studySiteDTO)))
                .thenReturn(existentSiteDTO);

        ParticipatingSiteDTO participatingSiteDTO = new ParticipatingSiteDTO();
        participatingSiteDTO
                .setIdentifier(IiConverter.convertToStudySiteIi(1L));
        when(
                participatingSiteServiceLocal.updateStudySiteParticipant(
                        any(StudySiteDTO.class),
                        any(StudySiteAccrualStatusDTO.class))).thenReturn(
                participatingSiteDTO);
        when(
                participatingSiteServiceLocal.createStudySiteParticipant(
                        any(StudySiteDTO.class),
                        any(StudySiteAccrualStatusDTO.class), any(Ii.class)))
                .thenReturn(participatingSiteDTO);

        StudyProtocolDTO studyDTO = setupSpDto();
        when(
                studyProtocolServiceLocal.getStudyProtocol(eq(IiConverter
                        .convertToStudyProtocolIi(1L)))).thenReturn(studyDTO);

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
        assertEquals(ActionSupport.SUCCESS, fwd);
        assertEquals(
                action.getText("add.site.updateSuccess"),
                ServletActionContext.getRequest().getSession()
                        .getAttribute(AddUpdateSiteAction.SUCCESS_MESSAGE_KEY));
        assertFalse(action.hasActionErrors());
        assertFalse(action.hasFieldErrors());

        ArgumentCaptor<StudySiteDTO> ssDTO = ArgumentCaptor
                .forClass(StudySiteDTO.class);
        ArgumentCaptor<StudySiteAccrualStatusDTO> accDTO = ArgumentCaptor
                .forClass(StudySiteAccrualStatusDTO.class);

        verify(participatingSiteServiceLocal, times(1))
                .updateStudySiteParticipant(ssDTO.capture(), accDTO.capture());
        verify(participatingSiteServiceLocal, times(1))
                .addStudySiteInvestigator(
                        IiConverter.convertToStudySiteIi(1L),
                        researchStaffDTO,
                        healthCareProviderDTO,
                        null,
                        StudySiteContactRoleCode.PRINCIPAL_INVESTIGATOR
                                .getCode());
        assertEquals(IiConverter.convertToStudySiteIi(1L), ssDTO.getValue()
                .getIdentifier());
        assertEquals(IiConverter.convertToStudyProtocolIi(1L), ssDTO.getValue()
                .getStudyProtocolIdentifier());
        assertEquals(StConverter.convertToSt("LOCAL_ID"), ssDTO.getValue()
                .getLocalStudyProtocolIdentifier());
        assertEquals(StConverter.convertToSt("PCODE"), ssDTO.getValue()
                .getProgramCodeText());

        assertEquals(CdConverter.convertToCd(RecruitmentStatusCode.ACTIVE),
                accDTO.getValue().getStatusCode());
        assertEquals(TsConverter.convertToTs(PAUtil
                .dateStringToTimestamp("01/15/2012")), accDTO.getValue()
                .getStatusDate());
    }

    @Test
    public void testSuccessfulAddSite() throws PAException,
            IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {
        prepareAction();
        SubmittedOrganizationDTO newSiteDTO = new SubmittedOrganizationDTO();
        PropertyUtils.copyProperties(newSiteDTO, existentSiteDTO);
        newSiteDTO.setId(null);

        action.setSiteDTO(newSiteDTO);
        String fwd = action.save();
        assertEquals(ActionSupport.SUCCESS, fwd);
        assertEquals(
                action.getText("add.site.success"),
                ServletActionContext.getRequest().getSession()
                        .getAttribute(AddUpdateSiteAction.SUCCESS_MESSAGE_KEY));
        assertFalse(action.hasActionErrors());
        assertFalse(action.hasFieldErrors());

        ArgumentCaptor<StudySiteDTO> ssDTO = ArgumentCaptor
                .forClass(StudySiteDTO.class);
        ArgumentCaptor<StudySiteAccrualStatusDTO> accDTO = ArgumentCaptor
                .forClass(StudySiteAccrualStatusDTO.class);
        ArgumentCaptor<Ii> ii = ArgumentCaptor.forClass(Ii.class);

        verify(participatingSiteServiceLocal, times(1))
                .createStudySiteParticipant(ssDTO.capture(), accDTO.capture(),
                        ii.capture());
        verify(participatingSiteServiceLocal, times(1))
                .addStudySiteInvestigator(
                        IiConverter.convertToStudySiteIi(1L),
                        researchStaffDTO,
                        healthCareProviderDTO,
                        null,
                        StudySiteContactRoleCode.PRINCIPAL_INVESTIGATOR
                                .getCode());
        assertNull(ssDTO.getValue().getIdentifier().getExtension());
        assertEquals(IiConverter.convertToStudyProtocolIi(1L), ssDTO.getValue()
                .getStudyProtocolIdentifier());
        assertEquals(StConverter.convertToSt("LOCAL_ID"), ssDTO.getValue()
                .getLocalStudyProtocolIdentifier());
        assertEquals(StConverter.convertToSt("PCODE"), ssDTO.getValue()
                .getProgramCodeText());

        assertEquals(CdConverter.convertToCd(RecruitmentStatusCode.ACTIVE),
                accDTO.getValue().getStatusCode());
        assertEquals(TsConverter.convertToTs(PAUtil
                .dateStringToTimestamp("01/15/2012")), accDTO.getValue()
                .getStatusDate());
    }

    @Test
    public void testAddSiteValidationErrors_enforcePartialRulesForProp1()
            throws PAException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {
        prepareAction();
        SubmittedOrganizationDTO newSiteDTO = new SubmittedOrganizationDTO();
        PropertyUtils.copyProperties(newSiteDTO, existentSiteDTO);

        newSiteDTO.setId(null);
        newSiteDTO.setSiteLocalTrialIdentifier("");
        newSiteDTO.setInvestigatorId(null);
        newSiteDTO.setRecruitmentStatus("");
        newSiteDTO.setRecruitmentStatusDate("01/01/100");

        action.setSiteDTO(newSiteDTO);
        String fwd = action.save();
        assertEquals(ActionSupport.ERROR, fwd);
        assertTrue(action.hasFieldErrors());

        Map<String, List<String>> fieldErrs = action.getFieldErrors();
        assertEquals(fieldErrs.get("localIdentifier"),
                Arrays.asList("error.siteLocalTrialIdentifier.required"));
        assertEquals(fieldErrs.get("investigator"),
                Arrays.asList("error.selectedPersId.required"));
        assertEquals(
                fieldErrs.get("statusCode"),
                Arrays.asList("error.participatingOrganizations.recruitmentStatus"));
        assertEquals(fieldErrs.get("statusDate"),
                Arrays.asList("error.submit.invalidDate"));

    }

    @Test
    public void testAddSiteValidationErrors_enforcePartialRulesForProp2()
            throws PAException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {
        prepareAction();
        SubmittedOrganizationDTO newSiteDTO = new SubmittedOrganizationDTO();
        PropertyUtils.copyProperties(newSiteDTO, existentSiteDTO);

        newSiteDTO.setId(null);
        newSiteDTO.setDateOpenedforAccrual("01/01/2030");
        newSiteDTO.setDateClosedforAccrual("01/01/2030");

        action.setSiteDTO(newSiteDTO);
        String fwd = action.save();
        assertEquals(ActionSupport.ERROR, fwd);
        assertTrue(action.hasFieldErrors());

        Map<String, List<String>> fieldErrs = action.getFieldErrors();
        assertEquals(fieldErrs.get("accrualOpenedDate"),
                Arrays.asList("error.submit.invalidStatusDate"));
        assertEquals(fieldErrs.get("accrualClosedDate"),
                Arrays.asList("error.submit.invalidStatusDate"));

    }

    @Test
    public void testAddSiteValidationErrors_enforcePartialRulesForProp3()
            throws PAException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {
        prepareAction();
        SubmittedOrganizationDTO newSiteDTO = new SubmittedOrganizationDTO();
        PropertyUtils.copyProperties(newSiteDTO, existentSiteDTO);

        newSiteDTO.setId(null);
        newSiteDTO.setDateOpenedforAccrual("");

        action.setSiteDTO(newSiteDTO);
        String fwd = action.save();
        assertEquals(ActionSupport.ERROR, fwd);
        assertTrue(action.hasFieldErrors());

        Map<String, List<String>> fieldErrs = action.getFieldErrors();
        assertEquals(fieldErrs.get("accrualOpenedDate"),
                Arrays.asList("error.proprietary.dateOpenReq",
                        "Date Opened for Acrual must not be empty for "
                                + "Active" + " recruitment status"));

        newSiteDTO.setId(null);
        newSiteDTO.setDateOpenedforAccrual("01/01/2012");
        newSiteDTO.setDateClosedforAccrual("12/31/2011");

        action.setSiteDTO(newSiteDTO);
        fwd = action.save();
        assertEquals(ActionSupport.ERROR, fwd);
        assertTrue(action.hasFieldErrors());

        fieldErrs = action.getFieldErrors();
        assertEquals(fieldErrs.get("accrualClosedDate"),
                Arrays.asList("error.proprietary.dateClosedAccrualBigger"));

    }

    @Test
    public void testAddSiteValidationErrors_enforcePartialRulesForProp4()
            throws PAException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {
        prepareAction();
        SubmittedOrganizationDTO newSiteDTO = new SubmittedOrganizationDTO();
        PropertyUtils.copyProperties(newSiteDTO, existentSiteDTO);

        newSiteDTO.setId(null);
        newSiteDTO.setRecruitmentStatus(RecruitmentStatusCode.WITHDRAWN
                .getCode());

        action.setSiteDTO(newSiteDTO);
        String fwd = action.save();
        assertEquals(ActionSupport.ERROR, fwd);
        assertTrue(action.hasFieldErrors());

        Map<String, List<String>> fieldErrs = action.getFieldErrors();
        assertEquals(
                fieldErrs.get("accrualOpenedDate"),
                Arrays.asList("Date Opened for Acrual must be empty for "
                        + RecruitmentStatusCode.WITHDRAWN.getCode()
                        + " recruitment status"));

        newSiteDTO.setRecruitmentStatus(RecruitmentStatusCode.ACTIVE.getCode());
        newSiteDTO.setDateOpenedforAccrual("");

        action.setSiteDTO(newSiteDTO);
        fwd = action.save();
        assertEquals(ActionSupport.ERROR, fwd);
        assertTrue(action.hasFieldErrors());

        fieldErrs = action.getFieldErrors();
        assertEquals(fieldErrs.get("accrualOpenedDate"), Arrays.asList(
                "error.proprietary.dateOpenReq",
                "Date Opened for Acrual must not be empty for "
                        + RecruitmentStatusCode.ACTIVE.getCode()
                        + " recruitment status"));

        newSiteDTO.setRecruitmentStatus(RecruitmentStatusCode.COMPLETED
                .getCode());
        newSiteDTO.setDateOpenedforAccrual("01/01/2012");
        newSiteDTO.setDateClosedforAccrual("");

        action.setSiteDTO(newSiteDTO);
        fwd = action.save();
        assertEquals(ActionSupport.ERROR, fwd);
        assertTrue(action.hasFieldErrors());

        fieldErrs = action.getFieldErrors();
        assertEquals(
                fieldErrs.get("accrualClosedDate"),
                Arrays.asList(

                "Date Closed for Acrual must not be empty for "
                        + RecruitmentStatusCode.COMPLETED.getCode()
                        + " recruitment status"));

    }

}
