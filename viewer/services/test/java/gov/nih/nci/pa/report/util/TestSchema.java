package gov.nih.nci.pa.report.util;

import gov.nih.nci.pa.domain.DocumentWorkflowStatus;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.util.ArrayList;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.Transaction;


/**
 *
 * @author Hugh Reinhart
 * @since 05/05/2009
 */
public class TestSchema {
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
        User user = new User();
        user.setLoginName("testUser");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setOrganization("testOrganization");
        user.setUpdateDate(new Date());
        addUpdObject(user);

        StudyProtocol sp = new StudyProtocol();
        sp.setOfficialTitle("cancer for THOLA");
        sp.setStartDate(PAUtil.dateStringToTimestamp("1/1/2000"));
        sp.setStartDateTypeCode(ActualAnticipatedTypeCode.ACTUAL);
        sp.setPrimaryCompletionDate(PAUtil.dateStringToTimestamp("12/31/2009"));
        sp.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode.ANTICIPATED);
        sp.setIdentifier("NCI-2009-00001");
        sp.setSubmissionNumber(Integer.valueOf(1));
        sp.setUserLastCreated("testUser");
        sp.setDateLastCreated(PAUtil.dateStringToTimestamp("1/1/2000"));
        addUpdObject(sp);

        DocumentWorkflowStatus dws = new DocumentWorkflowStatus();
        dws.setStudyProtocol(sp);
        dws.setStatusCode(DocumentWorkflowStatusCode.SUBMITTED);
        dws.setCommentText("Submitted by cancer center.");
        dws.setUserLastCreated("testUser");
        addUpdObject(dws);

        dws = new DocumentWorkflowStatus();
        dws.setStudyProtocol(sp);
        dws.setStatusCode(DocumentWorkflowStatusCode.ACCEPTED);
        dws.setCommentText("Accepted by CTRO staff");
        dws.setUserLastCreated("testUser");
        addUpdObject(dws);

        dataLoaded = true;
    }
}
