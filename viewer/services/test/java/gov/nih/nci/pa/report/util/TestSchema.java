package gov.nih.nci.pa.report.util;

import gov.nih.nci.pa.domain.DocumentWorkflowStatus;
import gov.nih.nci.pa.domain.HealthCareFacility;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.ResearchOrganization;
import gov.nih.nci.pa.domain.StudyMilestone;
import gov.nih.nci.pa.domain.StudyParticipation;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.EntityStatusCode;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.enums.ReviewBoardApprovalStatusCode;
import gov.nih.nci.pa.enums.StructuralRoleStatusCode;
import gov.nih.nci.pa.enums.StudyParticipationFunctionalCode;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;


/**
 *
 * @author Hugh Reinhart
 * @since 05/05/2009
 */
public class TestSchema {

    public static List<User> user;
    public static List<Organization> organization;
    public static List<HealthCareFacility> healthCareFacility;
    public static List<ResearchOrganization> researchOrganization;
    public static List<StudyProtocol> studyProtocol;
    public static List<DocumentWorkflowStatus> documentWorkflowStatus;
    public static List<StudyMilestone> studyMilestone;
    public static List<StudyParticipation> studyParticipation;

    private static boolean dataLoaded = false;
    private static CtrpHibernateHelper testHelper = new TestHibernateHelper();

    /**
     *
     */
    public static void reset() {
        ViewerHibernateUtil.testHelper = testHelper;
        if (!dataLoaded) {
            primeData();
        }
    }

    /**
     * @param <T> t
     * @param obj o
     */
    public static <T> void addUpdObject(T obj) {
        Session session = ViewerHibernateUtil.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(obj);
        transaction.commit();
    }

    /**
     *
     * @param <T> t
     * @param oList o
     */
    public static <T> void addUpdObjects(ArrayList<T> oList) {
        for (T obj : oList) {
            addUpdObject(obj);
        }
    }

    public static void primeData() {
        user = new ArrayList<User>();
        organization = new ArrayList<Organization>();
        healthCareFacility = new ArrayList<HealthCareFacility>();
        researchOrganization = new ArrayList<ResearchOrganization>();
        studyProtocol = new ArrayList<StudyProtocol>();
        documentWorkflowStatus = new ArrayList<DocumentWorkflowStatus>();
        studyMilestone = new ArrayList<StudyMilestone>();
        studyParticipation = new ArrayList<StudyParticipation>();

        User usr = new User();
        usr.setLoginName("testUser");
        usr.setFirstName("John");
        usr.setLastName("Doe");
        usr.setOrganization("testOrganization");
        usr.setUpdateDate(new Date());
        user.add(usr);
        addUpdObject(usr);

        Organization org = new Organization();
        org.setCity("city");
        org.setCountryName("countryName");
        org.setDateLastCreated(new Date());
        org.setIdentifier("ORG ID");
        org.setName("Duke");
        org.setPostalCode("12345");
        org.setState("NC");
        org.setStatusCode(EntityStatusCode.ACTIVE);
        org.setUserLastCreated(usr.getLoginName());
        organization.add(org);
        addUpdObject(org);

        HealthCareFacility hcf = new HealthCareFacility();
        hcf.setDateLastCreated(new Date());
        hcf.setIdentifier(org.getIdentifier());
        hcf.setOrganization(org);
        hcf.setStatusCode(StructuralRoleStatusCode.ACTIVE);
        hcf.setStatusDateRangeLow(new Timestamp(new Date().getTime()));
        hcf.setUserLastCreated(usr.getLoginName());
        healthCareFacility.add(hcf);
        addUpdObject(hcf);

        ResearchOrganization ro = new ResearchOrganization();
        ro.setDateLastCreated(new Date());
        ro.setIdentifier(org.getIdentifier());
        ro.setOrganization(org);
        ro.setStatusCode(StructuralRoleStatusCode.ACTIVE);
        ro.setStatusDateRangeLow(new Timestamp(new Date().getTime()));
        ro.setUserLastCreated(usr.getLoginName());
        researchOrganization.add(ro);
        addUpdObject(ro);

        StudyProtocol sp = new StudyProtocol();
        sp.setOfficialTitle("cancer for THOLA");
        sp.setStartDate(PAUtil.dateStringToTimestamp("1/1/2000"));
        sp.setStartDateTypeCode(ActualAnticipatedTypeCode.ACTUAL);
        sp.setPrimaryCompletionDate(PAUtil.dateStringToTimestamp("12/31/2009"));
        sp.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode.ANTICIPATED);
        sp.setIdentifier("NCI-2009-00001");
        sp.setSubmissionNumber(Integer.valueOf(1));
        sp.setUserLastCreated(usr.getLoginName());
        sp.setDateLastCreated(PAUtil.dateStringToTimestamp("1/1/2009"));
        studyProtocol.add(sp);
        addUpdObject(sp);

        DocumentWorkflowStatus dws = new DocumentWorkflowStatus();
        dws.setStudyProtocol(sp);
        dws.setStatusCode(DocumentWorkflowStatusCode.SUBMITTED);
        dws.setCommentText("Submitted by cancer center.");
        dws.setUserLastCreated(usr.getLoginName());
        addUpdObject(dws);

        dws = new DocumentWorkflowStatus();
        dws.setStudyProtocol(sp);
        dws.setStatusCode(DocumentWorkflowStatusCode.ACCEPTED);
        dws.setCommentText("Accepted by CTRO staff");
        dws.setUserLastCreated(usr.getLoginName());
        documentWorkflowStatus.add(dws);
        addUpdObject(dws);

        StudyMilestone sm = new StudyMilestone();
        sm.setCommentText("test data");
        sm.setMilestoneCode(MilestoneCode.SUBMISSION_RECEIVED);
        sm.setMilestoneDate(PAUtil.dateStringToTimestamp("1/1/2000"));
        sm.setStudyProtocol(sp);
        studyMilestone.add(sm);
        addUpdObject(sm);

        StudyParticipation spart = new StudyParticipation();
        spart.setDateLastCreated(new Date());
        spart.setFunctionalCode(StudyParticipationFunctionalCode.LEAD_ORGANIZATION);
        spart.setResearchOrganization(ro);
        spart.setLocalStudyProtocolIdentifier("local sp id");
        spart.setReviewBoardApprovalStatusCode(ReviewBoardApprovalStatusCode.SUBMITTED_APPROVED);
        spart.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        spart.setStudyProtocol(sp);
        spart.setUserLastCreated(usr.getLoginName());
        studyParticipation.add(spart);
        addUpdObject(spart);

        dataLoaded = true;
    }
}
