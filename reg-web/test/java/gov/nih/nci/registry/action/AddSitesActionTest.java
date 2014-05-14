package gov.nih.nci.registry.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
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
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.enums.StudySiteContactRoleCode;
import gov.nih.nci.pa.iso.dto.ParticipatingSiteDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.ParticipatingSiteServiceLocal;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.StudySiteContactServiceLocal;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.service.util.ParticipatingOrgServiceLocal;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.registry.action.AddSitesAction.AddSiteResult;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import gov.nih.nci.services.correlation.HealthCareProviderDTO;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.SerializationUtils;
import org.apache.struts2.ServletActionContext;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpServletResponse;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Denis G. Krylov
 * 
 */
public class AddSitesActionTest extends AbstractRegWebTest {
    private AddSitesAction action;

    private PAServiceUtils paServiceUtils;
    private RegistryUserServiceLocal registryUserServiceLocal;
    private ParticipatingSiteServiceLocal participatingSiteServiceLocal;
    private ParticipatingOrgServiceLocal participatingOrgServiceLocal;
    private StudySiteContactServiceLocal studySiteContactServiceLocal;
    private StudyProtocolServiceLocal studyProtocolServiceLocal;
    private ProtocolQueryServiceLocal protocolQueryServiceLocal;

    private ClinicalResearchStaffDTO researchStaffDTO;
    private HealthCareProviderDTO healthCareProviderDTO;
    private OrganizationDTO affiliation;

    private RegistryUser user;

    @SuppressWarnings({ "deprecation", "unchecked" })
    @Before
    public void before() throws PAException {
        paServiceUtils = mock(PAServiceUtils.class);
        registryUserServiceLocal = mock(RegistryUserServiceLocal.class);
        participatingSiteServiceLocal = mock(ParticipatingSiteServiceLocal.class);
        participatingOrgServiceLocal = mock(ParticipatingOrgServiceLocal.class);
        studySiteContactServiceLocal = mock(StudySiteContactServiceLocal.class);
        studyProtocolServiceLocal = mock(StudyProtocolServiceLocal.class);
        protocolQueryServiceLocal = mock(ProtocolQueryServiceLocal.class);

        StudySiteDTO studySiteDTO = new StudySiteDTO();
        studySiteDTO.setIdentifier(IiConverter.convertToStudySiteIi(1L));

        researchStaffDTO = new ClinicalResearchStaffDTO();
        healthCareProviderDTO = new HealthCareProviderDTO();

        affiliation = new OrganizationDTO();
        final Ii affiliationID = IiConverter.convertToIi("1");
        affiliation.setIdentifier(affiliationID);

        when(paServiceUtils.getPOOrganizationEntity(affiliationID)).thenReturn(
                affiliation);

        when(paServiceUtils.getPoPersonEntity(any(Ii.class))).thenReturn(
                new PersonDTO());
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
        when(
                participatingSiteServiceLocal.getParticipatingSite(
                        any(Ii.class), any(String.class))).thenReturn(
                studySiteDTO);

        when(
                participatingOrgServiceLocal.getOrganizationsThatAreNotSiteYet(
                        eq(1L), any(Collection.class))).thenReturn(
                Arrays.asList(affiliation));

        StudyProtocolDTO studyDTO = setupSpDto();
        when(
                studyProtocolServiceLocal.getStudyProtocol(eq(IiConverter
                        .convertToStudyProtocolIi(1L)))).thenReturn(studyDTO);

        final StudyProtocolQueryDTO queryDTO = getStudyProtocolQueryDTO();

        when(protocolQueryServiceLocal.getTrialSummaryByStudyProtocolId(1L))
                .thenReturn(queryDTO);
        when(
                protocolQueryServiceLocal
                        .getStudyProtocolByCriteria(any(StudyProtocolQueryCriteria.class)))
                .thenAnswer(new Answer<List<StudyProtocolQueryDTO>>() {
                    @Override
                    public List<StudyProtocolQueryDTO> answer(
                            InvocationOnMock invocation) throws Throwable {
                        List<StudyProtocolQueryDTO> list = new ArrayList<StudyProtocolQueryDTO>();
                        StudyProtocolQueryCriteria criteria = (StudyProtocolQueryCriteria) invocation
                                .getArguments()[0];

                        List<String> dws = new ArrayList<String>();
                        for (DocumentWorkflowStatusCode code : DocumentWorkflowStatusCode
                                .values()) {
                            if (code.isAcceptedOrAbove()) {
                                dws.add(code.getCode());
                            }
                        }

                        if ("Title".equals(criteria.getOfficialTitle())
                                && "NCI-2009-00001".equals(criteria
                                        .getAnyTypeIdentifier())
                                && Boolean.TRUE.equals(criteria
                                        .isExcludeRejectProtocol())
                                && "p".equalsIgnoreCase(criteria
                                        .getTrialCategory())
                                && dws.equals(criteria
                                        .getDocumentWorkflowStatusCodes())) {
                            list.add(queryDTO);
                        }
                        return list;
                    }
                });

    }

    /**
     * @return
     */
    private StudyProtocolQueryDTO getStudyProtocolQueryDTO() {
        final StudyProtocolQueryDTO queryDTO = new StudyProtocolQueryDTO();
        queryDTO.setStudyProtocolId(1L);
        queryDTO.setOfficialTitle("Title");
        queryDTO.setNciIdentifier("NCI-2009-00001");
        queryDTO.setSiteSelfRegistrable(true);
        return queryDTO;
    }

    /**
     * @return
     */
    private RegistryUser getRegistryUser() {
        user = new RegistryUser();
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
    private void prepareAction() {
        action = new AddSitesAction();
        action.prepare();
        action.setPaServiceUtils(paServiceUtils);
        action.setRegistryUserService(registryUserServiceLocal);
        action.setParticipatingSiteService(participatingSiteServiceLocal);
        action.setStudySiteContactService(studySiteContactServiceLocal);
        action.setStudyProtocolService(studyProtocolServiceLocal);
        action.setProtocolQueryService(protocolQueryServiceLocal);
        action.setServletRequest(ServletActionContext.getRequest());
        action.setServletResponse(ServletActionContext.getResponse());
        action.setParticipatingOrgService(participatingOrgServiceLocal);

        setTextProvider(action);

    }

    /**
     * @throws PAException
     * 
     */
    @Test
    public void testExecuteNoAffiliationError() throws PAException {
        prepareAction();
        user.setAffiliatedOrganizationId(null);
        String result = action.execute();
        assertEquals(ActionSupport.SUCCESS, result);
        assertEquals(AddSitesAction.NO_AFFILIATION_ERR_MSG,
                ServletActionContext.getRequest()
                        .getAttribute("failureMessage"));
    }

    @SuppressWarnings("rawtypes")
    @Test
    public void testExecuteResets() throws PAException {
        prepareAction();
        action.setRecords(new ArrayList<StudyProtocolQueryDTO>());
        action.getRecords().add(new StudyProtocolQueryDTO());
        ServletActionContext
                .getRequest()
                .getSession()
                .setAttribute(AddSitesAction.RESULTS_SESSION_KEY,
                        new ArrayList());
        action.execute();

        assertTrue(action.getRecords().isEmpty());
        assertNull(ServletActionContext.getRequest().getSession()
                .getAttribute(AddSitesAction.RESULTS_SESSION_KEY));

    }

    @SuppressWarnings("rawtypes")
    @Test
    public void testSearchEmptyQuery() throws PAException {
        prepareAction();
        String result = action.search();
        assertEquals(ActionSupport.ERROR, result);
        assertEquals(
                "Please provide a search criteria; otherwise the number of trials returned may be unmanageable.",
                ServletActionContext.getRequest()
                        .getAttribute("failureMessage"));

    }

    @Test
    public void testSearchNoTrials() throws PAException {
        prepareAction();

        action.getCriteria().setIdentifier("NCI");
        action.getCriteria().setOfficialTitle("Title");
        String result = action.search();
        assertEquals(ActionSupport.ERROR, result);
        assertEquals(
                "No trials which match the criteria and to which you can add sites found.",
                ServletActionContext.getRequest()
                        .getAttribute("failureMessage"));

    }

    @Test
    public void testSearchTooManyResults() throws PAException {
        prepareAction();

        action.getCriteria().setIdentifier("NCI-2009-00001");
        action.getCriteria().setOfficialTitle("Title");
        Mockito.reset(protocolQueryServiceLocal);
        when(
                protocolQueryServiceLocal
                        .getStudyProtocolByCriteria(any(StudyProtocolQueryCriteria.class)))
                .thenAnswer(new Answer<List<StudyProtocolQueryDTO>>() {
                    @Override
                    public List<StudyProtocolQueryDTO> answer(
                            InvocationOnMock invocation) throws Throwable {
                        List<StudyProtocolQueryDTO> list = new ArrayList<StudyProtocolQueryDTO>();
                        for (int i = 0; i < 101; i++) {
                            list.add(getStudyProtocolQueryDTO());
                        }
                        return list;
                    }
                });

        String result = action.search();
        assertEquals(ActionSupport.ERROR, result);
        assertEquals(
                "Search criteria you provided has produced "
                        + 101
                        + " (!) matching trials,"
                        + " which is too many to manage at once on this page. The limit is "
                        + 100
                        + ". "
                        + "If you could, please revise the criteria to produce a smaller number of trials"
                        + " that will be more manageable.",
                ServletActionContext.getRequest()
                        .getAttribute("failureMessage"));

    }
    
    @SuppressWarnings("deprecation")
    @Test
    public void testSortOrgsByNameWithAffiliationBeingAlwaysFirst() throws PAException {
        prepareAction();
        List<OrganizationDTO> orgs = new ArrayList<OrganizationDTO>();
        
        OrganizationDTO mayo = new OrganizationDTO();
        mayo.setIdentifier(IiConverter.convertToIi(1000L));
        mayo.setName(EnOnConverter.convertToEnOn("Mayo"));
        
        OrganizationDTO abra = new OrganizationDTO();
        abra.setIdentifier(IiConverter.convertToIi(2000L));
        abra.setName(EnOnConverter.convertToEnOn("Abrahamson"));
        
        orgs.add(mayo);
        orgs.add(abra);
        orgs.add(affiliation);
        
        action.sortOrgsByNameWithAffiliationBeingAlwaysFirst(orgs);
        
        assertEquals(affiliation, orgs.get(0));
        assertEquals(abra, orgs.get(1));
        assertEquals(mayo, orgs.get(2));

    }

    @SuppressWarnings("unchecked")
    @Test
    public void testSearch() throws PAException {
        prepareAction();

        action.getCriteria().setIdentifier("NCI-2009-00001");
        action.getCriteria().setOfficialTitle("Title");
        String result = action.search();
        assertEquals(ActionSupport.SUCCESS, result);

        List<StudyProtocolQueryDTO> list = (List<StudyProtocolQueryDTO>) ServletActionContext
                .getRequest().getSession()
                .getAttribute(AddSitesAction.RESULTS_SESSION_KEY);
        assertTrue(CollectionUtils.isNotEmpty(list));
        assertEquals(1, list.size());

        StudyProtocolQueryDTO queryDTO = list.get(0);
        assertEquals(new Long(1), queryDTO.getStudyProtocolId());
        assertEquals("Title", queryDTO.getOfficialTitle());
        assertEquals("NCI-2009-00001", queryDTO.getNciIdentifier());

        assertTrue(CollectionUtils.isNotEmpty(queryDTO
                .getOrgsThatCanBeAddedAsSite()));
        assertEquals(affiliation, queryDTO.getOrgsThatCanBeAddedAsSite().get(0));

    }

    @SuppressWarnings("unchecked")
    @Test
    public void testValidateSiteDataNoErrors() throws PAException, IOException {
        prepareAction();

        List<StudyProtocolQueryDTO> list = new ArrayList<StudyProtocolQueryDTO>();
        final StudyProtocolQueryDTO queryDTO = getStudyProtocolQueryDTO();
        queryDTO.setOrgsThatCanBeAddedAsSite(Arrays.asList(affiliation));
        list.add(queryDTO);
        ServletActionContext.getRequest().getSession()
                .setAttribute(AddSitesAction.RESULTS_SESSION_KEY, list);
        action.validateSiteData();

        MockHttpServletResponse response = (MockHttpServletResponse) ServletActionContext
                .getResponse();
        String jsonStr = response.getOutputStreamContent();
        JSONObject json = new JSONObject(jsonStr);
        assertNotNull(json);
        System.out.println(json);
        JSONArray arr = json.getJSONArray("errors");
        assertNotNull(arr);
        assertEquals(0, arr.length());

    }

    @Test(expected = PAException.class)
    public void testValidateSiteDataNoTrialsFound() throws PAException,
            IOException {
        prepareAction();

        List<StudyProtocolQueryDTO> list = new ArrayList<StudyProtocolQueryDTO>();
        ServletActionContext.getRequest().getSession()
                .setAttribute(AddSitesAction.RESULTS_SESSION_KEY, list);
        action.validateSiteData();

    }

    @Test
    public void testValidateSiteDataMissingRequiredFields() throws PAException,
            IOException {
        prepareAction();

        List<StudyProtocolQueryDTO> list = new ArrayList<StudyProtocolQueryDTO>();
        final StudyProtocolQueryDTO queryDTO = getStudyProtocolQueryDTO();
        queryDTO.setOrgsThatCanBeAddedAsSite(Arrays.asList(affiliation));
        list.add(queryDTO);
        ServletActionContext.getRequest().getSession()
                .setAttribute(AddSitesAction.RESULTS_SESSION_KEY, list);

        MockHttpServletRequest r = (MockHttpServletRequest) ServletActionContext
                .getRequest();
        r.setupAddParameter("trial_1_site_0_org_poid", "");
        r.setupAddParameter("trial_1_site_0_localID", "");
        r.setupAddParameter("trial_1_site_0_pi_poid", "");
        r.setupAddParameter("trial_1_site_0_status", "");
        r.setupAddParameter("trial_1_site_0_statusDate", "");
        r.setupAddParameter("trial_1_site_0_dateOpen", "01/01/2014");

        action.validateSiteData();

        MockHttpServletResponse response = (MockHttpServletResponse) ServletActionContext
                .getResponse();
        String jsonStr = response.getOutputStreamContent();
        JSONObject json = new JSONObject(jsonStr);
        assertNotNull(json);
        System.out.println(json);
        JSONArray arr = json.getJSONArray("errors");
        assertNotNull(arr);
        assertEquals(1, arr.length());

        JSONObject err = arr.getJSONObject(0);
        assertEquals(0, err.getInt("index"));
        assertEquals(1, err.getInt("spID"));
        assertTrue(err.getString("errors").contains(
                "Local Trial Identifier is required"));
        assertTrue(err.getString("errors").contains(
                "Please choose a Site Principal Investigator using the lookup"));
        assertTrue(err.getString("errors").contains(
                "Please enter a value for Recruitment Status"));
        assertTrue(err.getString("errors").contains(
                "A valid Recruitment Status Date is required"));

    }

    @Test
    public void testSaveNoTrialsFound() throws PAException, IOException {
        prepareAction();

        List<StudyProtocolQueryDTO> list = new ArrayList<StudyProtocolQueryDTO>();
        ServletActionContext.getRequest().getSession()
                .setAttribute(AddSitesAction.RESULTS_SESSION_KEY, list);
        assertEquals("error", action.save());
        assertTrue(action.hasActionErrors());
        assertEquals("Unexpected error: no trials found.", action
                .getActionErrors().iterator().next());

    }

    @Test
    public void testSave() throws PAException, IOException {
        prepareAction();

        List<StudyProtocolQueryDTO> list = new ArrayList<StudyProtocolQueryDTO>();
        final StudyProtocolQueryDTO queryDTO = getStudyProtocolQueryDTO();
        queryDTO.setOrgsThatCanBeAddedAsSite(Arrays.asList(affiliation));
        list.add(queryDTO);
        ServletActionContext.getRequest().getSession()
                .setAttribute(AddSitesAction.RESULTS_SESSION_KEY, list);

        MockHttpServletRequest r = (MockHttpServletRequest) ServletActionContext
                .getRequest();
        r.setupAddParameter("trial_1_site_0_org_poid", "1");
        r.setupAddParameter("trial_1_site_0_localID", "MN001");
        r.setupAddParameter("trial_1_site_0_pgcode", "PCODE");
        r.setupAddParameter("trial_1_site_0_pi_poid", "1");
        r.setupAddParameter("trial_1_site_0_status", "Active");
        r.setupAddParameter("trial_1_site_0_statusDate", "01/01/2014");

        assertEquals("confirmation", action.save());
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
        assertEquals(StConverter.convertToSt("MN001"), ssDTO.getValue()
                .getLocalStudyProtocolIdentifier());
        assertEquals(StConverter.convertToSt("PCODE"), ssDTO.getValue()
                .getProgramCodeText());

        assertEquals(CdConverter.convertToCd(RecruitmentStatusCode.ACTIVE),
                accDTO.getValue().getStatusCode());
        assertEquals(TsConverter.convertToTs(PAUtil
                .dateStringToTimestamp("01/01/2014")), accDTO.getValue()
                .getStatusDate());

        assertEquals(1, action.getSummary().size());
        AddSiteResult result = action.getSummary().get(0);
        assertEquals("SUCCESS", result.getResult());
        assertEquals(queryDTO, result.getTrial());
        assertEquals("1", result.getSiteDTO().getSitePoId());
    }
}
