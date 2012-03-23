package gov.nih.nci.pa.service.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.enums.SubmissionTypeCode;
import gov.nih.nci.pa.enums.UserOrgType;
import gov.nih.nci.pa.service.PAException;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class ProtocolQueryResultsServiceTest {

    static final long ADMIN_USERID = 34;
    static final long ADMINISTRATED_ORG = 74;
    static final long MEMB_USERID = 77;

    // copied from service
    private static final int UPDATING_IDX = 5;
    private static final int SUBMISSION_NUMBER_IDX = 7;

    ProtocolQueryResultsServiceBean bean;
    DataAccessServiceLocal daMock;
    RegistryUserServiceLocal usrMock;

    RegistryUser admin;
    RegistryUser memb;

    BigInteger studyProtocolIdentifier = new BigInteger("14");
    String officialTitle = "title";
    Boolean proprietaryTrialIndicator = true;
    Date recordVerificationDate = new Date();
    Boolean ctgovXmlRequiredIndicator = true;
    Boolean updating = true;
    Date dateLastCreated = new Date();
    Integer submissionNumber = 1;
    String nciNumber = "NCI-2099-00001";
    String nctNumber = "nct gog 03";
    String leadOrgPoid = "123";
    String leadOrgName = "Duke Medical Center";
    String leadOrgSpIdentifier = "duk001";
    String currentDwfStatusCode = DocumentWorkflowStatusCode.SUBMITTED.getName();
    Date currentDwfStatusDate = new Date();
    String currentStudyOverallStatus = StudyStatusCode.ACTIVE.getName();
    String currentAdminMilestone = MilestoneCode.ADMINISTRATIVE_READY_FOR_QC.getName();
    String currentScientificMilestone = MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE.getName();
    String currentOtherMilestone = MilestoneCode.SUBMISSION_ACCEPTED.getName();
    Integer adminCheckoutIdentifier = 19;
    String adminCheckoutUser = "cyx";
    Integer scientificCheckoutIdentifiER = 21;
    String scientificCheckoutUser = "jkd";
    String studyPiFirstName = "John";
    String studyPiLastName = "Doe";
    String userLastCreatedLogin = "userlogin";
    String userLastCreatedFirst = "Jane";
    String userLastCreatedLast = "Smith";
    String dcpId = "DCPID";
    String ctepId = "CTEPID";
    Date amendmentDate = new Date();
    Date updatedDate = new Date();
    Object[] qryResult = { studyProtocolIdentifier, officialTitle, proprietaryTrialIndicator, recordVerificationDate
            , ctgovXmlRequiredIndicator, updating, dateLastCreated, submissionNumber, nciNumber, nctNumber, leadOrgPoid
            , leadOrgName, leadOrgSpIdentifier, currentDwfStatusCode, currentDwfStatusDate, currentStudyOverallStatus
            , currentAdminMilestone, currentScientificMilestone, currentOtherMilestone, adminCheckoutIdentifier
            , adminCheckoutUser, scientificCheckoutIdentifiER, scientificCheckoutUser, studyPiFirstName,
            studyPiLastName
            , userLastCreatedLogin, userLastCreatedFirst, userLastCreatedLast, dcpId, ctepId, amendmentDate, updatedDate };
    Object[] siteQryResult = { studyProtocolIdentifier, BigInteger.valueOf(MEMB_USERID) };    

    @Before
    public void init() throws Exception {
        ProtocolQueryResultsServiceBean svc = new ProtocolQueryResultsServiceBean();
        daMock = mock(DataAccessServiceLocal.class);
        usrMock = mock(RegistryUserServiceLocal.class);
        svc.setDataAccessService(daMock);
        svc.setRegistryUserService(usrMock);
        bean = svc;

        // set up users
        admin = new RegistryUser();
        admin.setAffiliatedOrgUserType(UserOrgType.ADMIN);
        admin.setId(ADMIN_USERID);
        when(usrMock.getUserById(ADMIN_USERID)).thenReturn(admin);
        when(usrMock.getPartialUserById(ADMIN_USERID)).thenReturn(admin);
        memb = new RegistryUser();
        memb.setAffiliatedOrgUserType(UserOrgType.MEMBER);
        memb.setAffiliatedOrganizationId(1L);
        memb.setId(MEMB_USERID);
        when(usrMock.getUserById(MEMB_USERID)).thenReturn(memb);
        when(usrMock.getPartialUserById(MEMB_USERID)).thenReturn(memb);

        // set up owned study
        List<Object> ownedStudies = new ArrayList<Object>();
        ownedStudies.add(studyProtocolIdentifier);
        DAQuery queryMemb = new DAQuery();
        queryMemb.setSql(true);
        queryMemb.setText("SELECT study_id FROM study_owner WHERE user_id = :userId");
        queryMemb.addParameter("userId", MEMB_USERID);
        when(daMock.findByQuery(queryMemb)).thenReturn(ownedStudies);

        // set up main query
        DAQuery qryMain = new DAQuery();
        qryMain.setSql(true);
        qryMain.setText(ProtocolQueryResultsServiceBean.QRY_STRING);
        Set<Long> ids = new HashSet<Long>();
        ids.add(studyProtocolIdentifier.longValue());
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("ids", ids);
        qryMain.setParameters(params);
        List<Object> result = new ArrayList<Object>();
        result.add(qryResult);
        when(daMock.findByQuery(qryMain)).thenReturn(result);
        
        // set up subordinate query
        DAQuery qryStudyId = new DAQuery();
        qryStudyId.setSql(true);
        qryStudyId.setText(ProtocolQueryResultsServiceBean.STUDY_ID_QRY_STRING);
        params = new HashMap<String, Object>();
        params.put("orgId", 1L);
        qryStudyId.setParameters(params);
        result = new ArrayList<Object>();
        result.add(siteQryResult);
        when(daMock.findByQuery(qryStudyId)).thenReturn(result);
        
        // set up other Identifier
        DAQuery qryMain1 = new DAQuery();
        qryMain1.setSql(true);
        qryMain1.setText(ProtocolQueryResultsServiceBean.OTHER_IDENTIFIERS_QRY_STRING);
        Set<Long> ids1 = new HashSet<Long>();
        ids1.add(studyProtocolIdentifier.longValue());
        Map<String, Object> params1 = new HashMap<String, Object>();
        params1.put("ids", ids1);
        qryMain1.setParameters(params1);
        List<Object> result1 = new ArrayList<Object>();
        when(daMock.findByQuery(qryMain1)).thenReturn(result1);
        
        // fetch last updated date
        DAQuery qryMain2 = new DAQuery();
        qryMain2.setSql(true);
        qryMain2.setText(ProtocolQueryResultsServiceBean.LAST_UPDATED_DATE);
        Set<Long> ids2 = new HashSet<Long>();
        ids1.add(studyProtocolIdentifier.longValue());
        Map<String, Object> params2 = new HashMap<String, Object>();
        params2.put("ids", ids2);
        qryMain2.setParameters(params2);
        List<Object> result2 = new ArrayList<Object>();
        when(daMock.findByQuery(qryMain1)).thenReturn(result2);
        
    }

    @Test
    public void emptyListTest() throws Exception {
        assertEquals(0, bean.getResults(null, false, null).size());
        assertEquals(0, bean.getResults(new ArrayList<Long>(), false, null).size());
        assertEquals(0, bean.getResults(new ArrayList<Long>(), false, 1L).size());
        assertEquals(0, bean.getResults(new ArrayList<Long>(), true, null).size());
        assertEquals(0, bean.getResults(new ArrayList<Long>(), true, 1L).size());
    }

    @Test
    public void noOwnedTrialsTest() throws Exception {
        List<Long> ids = new ArrayList<Long>();
        for (long x = 0; x < 501; x++) {           
            ids.add(x);
        }
        assertEquals(0, bean.getResults(ids, true, null).size());
    }

    @Test
    public void submissionTypeTest() throws Exception {
        List<Long> ids = new ArrayList<Long>();
        StudyProtocol id = new StudyProtocol();
        id.setId(studyProtocolIdentifier.longValue());
        ids.add(studyProtocolIdentifier.longValue());
        // update
        List<StudyProtocolQueryDTO> trials = bean.getResults(ids, false, MEMB_USERID);
        assertEquals(1, trials.size());
        assertEquals(SubmissionTypeCode.U, trials.get(0).getSubmissionTypeCode());
        // original
        qryResult[UPDATING_IDX] = null;
        trials = bean.getResults(ids, false, MEMB_USERID);
        assertEquals(1, trials.size());
        assertEquals(SubmissionTypeCode.O, trials.get(0).getSubmissionTypeCode());
        // amendment
        qryResult[SUBMISSION_NUMBER_IDX] = 2;
        trials = bean.getResults(ids, false, MEMB_USERID);
        assertEquals(1, trials.size());
        assertEquals(SubmissionTypeCode.A, trials.get(0).getSubmissionTypeCode());
    }

    @Test
    public void adminTest() throws Exception {
        List<Long> ids = new ArrayList<Long>();
        StudyProtocol id = new StudyProtocol();
        id.setId(studyProtocolIdentifier.longValue());
        ids.add(studyProtocolIdentifier.longValue());
        // get all trials
        assertEquals(1, bean.getResults(ids, false, ADMIN_USERID).size());
        // get owned trials, not affiliated, not trial owner
        assertEquals(0, bean.getResults(ids, true, ADMIN_USERID).size());
        // get owned trials, affiliated, not trial owner
        admin.setAffiliateOrg(leadOrgPoid);
        assertEquals(1, bean.getResults(ids, true, ADMIN_USERID).size());
        // get owned trials, affiliated, trial owner
        List<Object> ownedStudies = new ArrayList<Object>();
        ownedStudies.add(studyProtocolIdentifier);
        DAQuery qry = new DAQuery();
        qry.setSql(true);
        qry.setText("SELECT study_id FROM study_owner WHERE user_id = :userId");
        qry.addParameter("userId", ADMIN_USERID);
        when(daMock.findByQuery(qry)).thenReturn(ownedStudies);
        assertEquals(1, bean.getResults(ids, true, ADMIN_USERID).size());
        // get owned trials, not affiliated, trial owner
        admin.setAffiliateOrg("xyzzy");
        assertEquals(1, bean.getResults(ids, true, ADMIN_USERID).size());
    }

    @Test
    public void memberTest() throws Exception {
        List<Long> ids = new ArrayList<Long>();
        StudyProtocol id = new StudyProtocol();
        id.setId(studyProtocolIdentifier.longValue());
        ids.add(studyProtocolIdentifier.longValue());
        // all trials
        assertEquals(1, bean.getResults(ids, false, MEMB_USERID).size());
        // owned trials, still returns since user is trial owner
        assertEquals(1, bean.getResults(ids, true, MEMB_USERID).size());
    }

    @Test
    public void nullSafeResultsTest() throws Exception {
        List<Long> ids = new ArrayList<Long>();
        StudyProtocol id = new StudyProtocol();
        id.setId(studyProtocolIdentifier.longValue());
        ids.add(studyProtocolIdentifier.longValue());
        // don't null [0] as study_protocol_identifier never null
        for (int x = 1; x < qryResult.length; x++) {
            qryResult[x] = null;
        }
        assertEquals(1, bean.getResults(ids, false, null).size());
    }
    
    @Test
    public void testSetFlags() {
        StudyProtocolQueryDTO dto = new StudyProtocolQueryDTO();
        dto.setProprietaryTrial(true);
        List<String> rssOrgs = Arrays.asList("RSS");
        dto.setDocumentWorkflowStatusCode(DocumentWorkflowStatusCode.ABSTRACTED);
        ((ProtocolQueryResultsServiceBean)bean).setFlags(dto, qryResult, rssOrgs);
        assertTrue(dto.isSiteSelfRegistrable());
        
        dto = new StudyProtocolQueryDTO();
        dto.setProprietaryTrial(true);
        rssOrgs = Arrays.asList("Duke Medical Center");
        dto.setDocumentWorkflowStatusCode(DocumentWorkflowStatusCode.ABSTRACTED);
        ((ProtocolQueryResultsServiceBean)bean).setFlags(dto, qryResult, rssOrgs);
        assertFalse(dto.isSiteSelfRegistrable());
        
        dto = new StudyProtocolQueryDTO();
        dto.setProprietaryTrial(true);
        rssOrgs = Arrays.asList("RSS");
        dto.setDocumentWorkflowStatusCode(DocumentWorkflowStatusCode.REJECTED);
        bean.setFlags(dto, qryResult, rssOrgs);
        assertFalse(dto.isSiteSelfRegistrable());       
        
        
    }
    
    
    /**
     * @throws PAException 
     * 
     */
    @Test
    public void testGetStudiesOnWhichUserHasSite() throws PAException {
        Map<Long, Boolean> map = bean.getStudiesOnWhichUserHasSite(memb) ;
        assertEquals(1, map.size());
        assertEquals(Boolean.TRUE, map.get(studyProtocolIdentifier.longValue()));
    }
    
    
}
