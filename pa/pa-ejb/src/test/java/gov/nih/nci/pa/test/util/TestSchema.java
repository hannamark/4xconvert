package gov.nih.nci.pa.test.util;

import gov.nih.nci.pa.domain.Condition;
import gov.nih.nci.pa.domain.Document;
import gov.nih.nci.pa.domain.DocumentIdentification;
import gov.nih.nci.pa.domain.DocumentWorkflowStatus;
import gov.nih.nci.pa.domain.HealthcareSite;
import gov.nih.nci.pa.domain.InterventionalStudyProtocol;
import gov.nih.nci.pa.domain.Investigator;
import gov.nih.nci.pa.domain.ObservationalStudyProtocol;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.Protocol;
import gov.nih.nci.pa.domain.ProtocolStatus;
import gov.nih.nci.pa.domain.StudyCondition;
import gov.nih.nci.pa.domain.StudyCoordinatingCenter;
import gov.nih.nci.pa.domain.StudyCoordinatingCenterRole;
import gov.nih.nci.pa.domain.StudyInvestigator;
import gov.nih.nci.pa.domain.StudyOverallStatus;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.enums.SponsorMonitorCode;
import gov.nih.nci.pa.enums.StudyPhaseCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.enums.StudyTypeCode;
import gov.nih.nci.pa.enums.YesNoCode;
import gov.nih.nci.pa.service.SessionEntry;
import gov.nih.nci.pa.util.HibernateUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

public class TestSchema {
        public static ArrayList<Long> protocolIds;
        public static ArrayList<Long> healthcareSiteIds;

        static {            
            Configuration config = new AnnotationConfiguration().

            addAnnotatedClass(Document.class).
            addAnnotatedClass(DocumentIdentification.class).
            addAnnotatedClass(StudyProtocol.class).
            addAnnotatedClass(StudyCondition.class).
            addAnnotatedClass(Condition.class).
            addAnnotatedClass(Organization.class).
            addAnnotatedClass(StudyCoordinatingCenter.class).
            addAnnotatedClass(StudyCoordinatingCenterRole.class).
            addAnnotatedClass(StudyOverallStatus.class).
            addAnnotatedClass(DocumentWorkflowStatus.class).
          
            
            
            
            setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect").
            setProperty("hibernate.connection.driver_class", "org.hsqldb.jdbcDriver").
            setProperty("hibernate.connection.url", "jdbc:hsqldb:mem:ctods").
            setProperty("hibernate.connection.username", "sa").
            setProperty("hibernate.connection.password", "").
            setProperty("hibernate.connection.pool_size", "1").
            setProperty("hibernate.connection.autocommit", "true").
            setProperty("hibernate.cache.provider_class", "org.hibernate.cache.HashtableCacheProvider").
            setProperty("hibernate.hbm2ddl.auto", "create-drop").
            setProperty("hibernate.show_sql", TestProperties.getShowSQL());
            HibernateUtil.getHibernateHelper().setConfiguration(config);
            HibernateUtil.getHibernateHelper().setSessionFactory(config.buildSessionFactory());
        }

        /**
         * 
         */
        public static void reset() {
        }
        
        public static void reset1() {
            // clean up HQLDB schema
            Session session = HibernateUtil.getHibernateHelper().getSessionFactory().openSession();
            try {
                Connection connection = session.connection();
                try {
                    Statement statement = connection.createStatement();
                    try {
                        statement.executeUpdate("delete from STUDY_PROTOCOL");
                        statement.executeUpdate("delete from STUDY_CONDITIONS");
                        statement.executeUpdate("delete from CONDITIONS");
                        statement.executeUpdate("delete from Document_Identification");
                        connection.commit();
                    }
                    finally {
                        statement.close();
                    }
                }
                catch (HibernateException e) {
                    connection.rollback();
                    throw new RuntimeException(e);
                }
                catch (SQLException e) {
                    connection.rollback();
                    throw new RuntimeException(e);
                }
            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
            finally {
                session.close();
            }

            // start session
            HibernateUtil.getHibernateHelper().openTestSession();
        }

        public static <T> void addUpdObject (T obj) {
            Session session = HibernateUtil.getCurrentSession();
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(obj);
            transaction.commit();
        }

        public static <T> void addUpdObjects (ArrayList<T> oList) {
            for(T obj : oList) {
                addUpdObject(obj);
            }
        }
        
        public static void primeData(){
            StudyProtocol sp = new StudyProtocol();   
            sp.setOfficialTitle("cacncer for THOLA");
            addUpdObject(sp); 
            sp.setId(sp.getId());           
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

//        public static void primeData1() {
//            ArrayList<HealthcareSite> hs = new ArrayList<HealthcareSite>();
//            ArrayList<Investigator> i = new ArrayList<Investigator>();
//            ArrayList<Protocol> p = new ArrayList<Protocol>();
//            ArrayList<ProtocolStatus> ps = new ArrayList<ProtocolStatus>();
//            ArrayList<StudySite> ss = new ArrayList<StudySite>();
//            ArrayList<StudyInvestigator> si = new ArrayList<StudyInvestigator>();
//
//            hs.add(new HealthcareSite());
//            hs.get(0).setName("name.1");
//            hs.get(0).setNciInstituteCode("nciInstituteCode.1");
//            hs.add(new HealthcareSite());
//            hs.get(1).setName("name.2");
//            hs.get(1).setNciInstituteCode("nciInstituteCode.2");
//            addUpdObjects(hs);
//            healthcareSiteIds = new ArrayList<Long>();
//            healthcareSiteIds.add(hs.get(0).getId());
//            healthcareSiteIds.add(hs.get(1).getId());
//
//            i.add(new Investigator());
//            i.get(0).setLastName("lastName.0");
//            i.add(new Investigator());
//            i.get(1).setLastName("lastName.1");
//            addUpdObjects(i);
//
//            p.add(new Protocol());
//            p.get(0).setLongTitleText("longTitleText.0");
//            p.get(0).setNciIdentifier("nciIdentifier.0");
//            p.get(0).setShortTitleText("shortTitleText.0");
//            p.get(0).setSponsorMonitorCode(SponsorMonitorCode.CTEP);
//            p.get(0).setStudyPhaseCode(StudyPhaseCode.PHASE2);
//            p.get(0).setStudyTypeCode(StudyTypeCode.DIAGNOSTIC);
//            p.add(new Protocol());
//            p.get(1).setLongTitleText("longTitleText.1");
//            p.get(1).setNciIdentifier("nciIdentifier.1");
//            p.get(1).setShortTitleText("shortTitleText.1");
//            p.get(1).setSponsorMonitorCode(SponsorMonitorCode.DEA);
//            p.get(1).setStudyPhaseCode(StudyPhaseCode.PHASE3);
//            p.get(1).setStudyTypeCode(StudyTypeCode.TREATMENT);
//            addUpdObjects(p);
//            protocolIds = new ArrayList<Long>();
//            protocolIds.add(p.get(0).getId());
//            protocolIds.add(p.get(1).getId());
//
//            ps.add(new ProtocolStatus());
//            ps.get(0).setProtocol(p.get(0));
//            ps.get(0).setStudyStatusCode(StudyStatusCode.ACTIVE);
//            ps.get(0).setStudyStatusDate(new Date());
//            ps.add(new ProtocolStatus());
//            ps.get(1).setProtocol(p.get(1));
//            ps.get(1).setStudyStatusCode(StudyStatusCode.COMPLETE);
//            ps.get(1).setStudyStatusDate(new Date());
//            addUpdObjects(ps);
//            p.get(0).getProtocolStatuses().add(ps.get(0));
//            p.get(1).getProtocolStatuses().add(ps.get(1));
//            addUpdObjects(p);
//
//            ss.add(new StudySite());
//            ss.get(0).setHealtcareSite(hs.get(0));
//            ss.get(0).setLeadOrganizationProtocolId("1");
//            ss.get(0).setProtocol(p.get(0));
//            ss.get(0).setLeadOrganizationProtocolId("leadOrganizationProtocolId.0");
//            ss.add(new StudySite());
//            ss.get(1).setHealtcareSite(hs.get(1));
//            ss.get(1).setLeadOrganizationProtocolId("2");
//            ss.get(1).setProtocol(p.get(1));
//            ss.get(1).setLeadOrganizationProtocolId("leadOrganizationProtocolId.1");
//            addUpdObjects(ss);
//            p.get(0).getStudySites().add(ss.get(0));
//            p.get(1).getStudySites().add(ss.get(1));
//            addUpdObjects(p);
//
//
//            si.add(new StudyInvestigator());
//            si.get(0).setInvestigator(i.get(0));
//            si.get(0).setProtocol(p.get(0));
//            si.get(0).setResponsibilityRoleCode("responsibilityRoleCode.0");
//            si.add(new StudyInvestigator());
//            si.get(1).setInvestigator(i.get(1));
//            si.get(1).setProtocol(p.get(1));
//            si.get(1).setResponsibilityRoleCode("responsibilityRoleCode.1");
//            addUpdObjects(si);
//
//            HibernateUtil.getCurrentSession().clear();
//        }
}
