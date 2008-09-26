package gov.nih.nci.pa.util;

import gov.nih.nci.pa.domain.Condition;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.DocumentWorkflowStatus;
import gov.nih.nci.pa.domain.FundingMechanism;
import gov.nih.nci.pa.domain.HealthCareFacility;
import gov.nih.nci.pa.domain.HealthCareProvider;
import gov.nih.nci.pa.domain.InterventionalStudyProtocol;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.domain.RegulatoryAuthority;
import gov.nih.nci.pa.domain.StudyCondition;
import gov.nih.nci.pa.domain.StudyContact;
import gov.nih.nci.pa.domain.StudyContactRole;
import gov.nih.nci.pa.domain.StudyCoordinatingCenter;
import gov.nih.nci.pa.domain.StudyCoordinatingCenterRole;
import gov.nih.nci.pa.domain.StudyOverallStatus;
import gov.nih.nci.pa.domain.StudyParticipation;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyRegulatoryAuthority;
import gov.nih.nci.pa.domain.StudyResourcing;
import gov.nih.nci.pa.domain.StudySiteAccrualStatus;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.enums.YesNoCode;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
/**
 * 
 * @author Hugh
 *
 */
public class TestSchema {
        /** . **/
        public static ArrayList<Long> studyProtocolIds;

        static {            
            Configuration config = new AnnotationConfiguration().

            addAnnotatedClass(StudyProtocol.class).
            addAnnotatedClass(InterventionalStudyProtocol.class).
            addAnnotatedClass(StudyCondition.class).
            addAnnotatedClass(Condition.class).
            addAnnotatedClass(Organization.class).
            addAnnotatedClass(StudyCoordinatingCenter.class).
            addAnnotatedClass(StudyCoordinatingCenterRole.class).
            addAnnotatedClass(StudyOverallStatus.class).
            addAnnotatedClass(DocumentWorkflowStatus.class).
            addAnnotatedClass(Person.class).
            addAnnotatedClass(HealthCareProvider.class).
            addAnnotatedClass(StudyContact.class).
            addAnnotatedClass(StudyParticipation.class).
            addAnnotatedClass(StudyContactRole.class).
            addAnnotatedClass(Country.class).
            addAnnotatedClass(RegulatoryAuthority.class).
            addAnnotatedClass(StudyRegulatoryAuthority.class).
            addAnnotatedClass(HealthCareFacility.class).
            addAnnotatedClass(StudyResourcing.class).
            addAnnotatedClass(FundingMechanism.class).
            addAnnotatedClass(StudySiteAccrualStatus.class).
            
            setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect").
            setProperty("hibernate.connection.driver_class", "org.hsqldb.jdbcDriver").
            setProperty("hibernate.connection.url", "jdbc:hsqldb:mem:ctods").
            setProperty("hibernate.connection.username", "sa").
            setProperty("hibernate.connection.password", "").
            setProperty("hibernate.connection.pool_size", "1").
            setProperty("hibernate.connection.autocommit", "true").
            setProperty("hibernate.cache.provider_class", "org.hibernate.cache.HashtableCacheProvider").
            setProperty("hibernate.hbm2ddl.auto", "create-drop").
            setProperty("hibernate.show_sql", "True");
            HibernateUtil.getHibernateHelper().setConfiguration(config);
            HibernateUtil.getHibernateHelper().setSessionFactory(config.buildSessionFactory());
        }

        /**
         * 
         * @return Session
         */
        public static Session getSession() {
            return HibernateUtil.getHibernateHelper().getSessionFactory().openSession();
        }
        /**
         * 
         */
        public static void reset() {
            HibernateUtil.getHibernateHelper().openTestSession();
        }
        
        /**
         * 
         */
        public static void reset1() {
            // clean up HQLDB schema
            Session session = HibernateUtil.getHibernateHelper().getSessionFactory().openSession();
            try {
                Connection connection = session.connection();
                try {
                    Statement statement = connection.createStatement();
                    try {
                        statement.executeUpdate("delete from STUDY_OVERALL_STATUS");
                        statement.executeUpdate("delete from STUDY_CONDITIONS");
                        statement.executeUpdate("delete from CONDITIONS");
                        statement.executeUpdate("delete from STUDY_PROTOCOL");
                        connection.commit();
                    } finally {
                        statement.close();
                    }
                } catch (HibernateException e) {
                    connection.rollback();
                    //throw new RuntimeException(e);
                } catch (SQLException e) {
                    connection.rollback();
                    //throw new RuntimeException(e);
                }
            } catch (SQLException e) {
                //throw new RuntimeException(e);
            } finally {
                session.close();
            }

            // start session
            HibernateUtil.getHibernateHelper().openTestSession();
        }

        /**
         * 
         * @param <T> t
         * @param obj o
         */
        public static <T> void addUpdObject(T obj) {
            Session session = HibernateUtil.getCurrentSession();
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
        
        /**
         * 
         */
        public static void primeData() {
            studyProtocolIds = new ArrayList<Long>();

            StudyProtocol sp = new StudyProtocol();   
            sp.setOfficialTitle("cacncer for THOLA");
            sp.setStartDate(PAUtil.dateStringToTimestamp("1/1/2000"));
            sp.setStartDateTypeCode(ActualAnticipatedTypeCode.ACTUAL);
            sp.setPrimaryCompletionDate(PAUtil.dateStringToTimestamp("12/31/2009"));
            sp.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode.ANTICIPATED);
            addUpdObject(sp); 
            sp.setId(sp.getId());
            studyProtocolIds.add(sp.getId());
            
            StudyOverallStatus sos = new StudyOverallStatus();
            sos.setStatusCode(StudyStatusCode.APPROVED);
            sos.setStatusDate(PAUtil.dateStringToTimestamp("8/1/2008"));
            sos.setStudyProtocol(sp);
            addUpdObject(sos);
            sos = new StudyOverallStatus();
            sos.setStatusCode(StudyStatusCode.ACTIVE);
            sos.setStatusDate(PAUtil.dateStringToTimestamp("8/15/2008"));
            sos.setStudyProtocol(sp);
            addUpdObject(sos);
            
            //
            Condition co = new Condition();
            // co.setId(5L);
            co.setCode("1AV55FGSY44");
            co.setName("BAD-DISEASE");
            co.setParentCode("ParentCode");          
            addUpdObject(co);
            co.setId(co.getId());
            //
            StudyCondition con = new StudyCondition();
            con.setStudyProtocol(sp);
            con.setLeadIndicator(YesNoCode.YES);
            con.setCondition(co);
            con.setStudyProtocol(sp);
            addUpdObject(con);
            con.setId(con.getId());
            HibernateUtil.getCurrentSession().clear();
        }
}
