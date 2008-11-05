package gov.nih.nci.pa.util;

import gov.nih.nci.pa.domain.Arm;
import gov.nih.nci.pa.domain.Condition;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.Document;
import gov.nih.nci.pa.domain.DocumentWorkflowStatus;
import gov.nih.nci.pa.domain.FundingMechanism;
import gov.nih.nci.pa.domain.HealthCareFacility;
import gov.nih.nci.pa.domain.HealthCareProvider;
import gov.nih.nci.pa.domain.Intervention;
import gov.nih.nci.pa.domain.InterventionAlternateName;
import gov.nih.nci.pa.domain.InterventionalStudyProtocol;
import gov.nih.nci.pa.domain.ObservationalStudyProtocol;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.domain.PlannedActivity;
import gov.nih.nci.pa.domain.RegulatoryAuthority;
import gov.nih.nci.pa.domain.ResearchOrganization;
import gov.nih.nci.pa.domain.StratumGroup;
import gov.nih.nci.pa.domain.StudyCondition;
import gov.nih.nci.pa.domain.StudyContact;
import gov.nih.nci.pa.domain.StudyCoordinatingCenter;
import gov.nih.nci.pa.domain.StudyCoordinatingCenterRole;
import gov.nih.nci.pa.domain.StudyIndlde;
import gov.nih.nci.pa.domain.StudyOutcomeMeasure;
import gov.nih.nci.pa.domain.StudyOverallStatus;
import gov.nih.nci.pa.domain.StudyParticipation;
import gov.nih.nci.pa.domain.StudyParticipationContact;
import gov.nih.nci.pa.domain.StudyParticipationContactTelecomAddress;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyRecruitmentStatus;
import gov.nih.nci.pa.domain.StudyRegulatoryAuthority;
import gov.nih.nci.pa.domain.StudyResourcing;
import gov.nih.nci.pa.domain.StudySiteAccrualStatus;
import gov.nih.nci.pa.enums.ActionCategoryCode;
import gov.nih.nci.pa.enums.ActionSubcategoryCode;
import gov.nih.nci.pa.enums.ActiveInactiveCode;
import gov.nih.nci.pa.enums.ActiveInactivePendingCode;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.enums.ExpandedAccessStatusCode;
import gov.nih.nci.pa.enums.HolderTypeCode;
import gov.nih.nci.pa.enums.InterventionTypeCode;
import gov.nih.nci.pa.enums.NihInstHolderCode;
import gov.nih.nci.pa.enums.StatusCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.enums.StudyParticipationFunctionalCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.enums.YesNoCode;

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
/**
 * 
 * @author Hugh
 *
 */
@SuppressWarnings("PMD")
public class TestSchema {
        /** . **/
        public static ArrayList<Long> studyProtocolIds;
        public static ArrayList<Long> studyParticipationIds;
        public static ArrayList<Long> studyParticipationContactIds;
        public static ArrayList<Long> healthCareFacilityIds;
        public static ArrayList<Long> healthCareProviderIds;
        public static ArrayList<Long> plannedActivityIds;
        public static ArrayList<Long> interventionIds;
        public static ArrayList<Long> armIds;

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
            addAnnotatedClass(Country.class).
            addAnnotatedClass(RegulatoryAuthority.class).
            addAnnotatedClass(StudyRegulatoryAuthority.class).
            addAnnotatedClass(HealthCareFacility.class).
            addAnnotatedClass(StudyResourcing.class).
            addAnnotatedClass(FundingMechanism.class).
            addAnnotatedClass(StudySiteAccrualStatus.class).
            addAnnotatedClass(StudyParticipationContact.class).
            addAnnotatedClass(StudyParticipationContactTelecomAddress.class).
            addAnnotatedClass(Document.class).
            addAnnotatedClass(StudyRecruitmentStatus.class).
            addAnnotatedClass(StratumGroup.class).
            addAnnotatedClass(ResearchOrganization.class).
            addAnnotatedClass(PlannedActivity.class).
            addAnnotatedClass(Intervention.class).
            addAnnotatedClass(InterventionAlternateName.class).
            addAnnotatedClass(ObservationalStudyProtocol.class).
            addAnnotatedClass(StudyOutcomeMeasure.class).
            addAnnotatedClass(StudyIndlde.class).
            addAnnotatedClass(Arm.class).
                        
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
                        statement.executeUpdate("delete from ARM_INTERVENTION");
                        statement.executeUpdate("delete from ARM");
                        statement.executeUpdate("delete from STUDY_OUTCOME_MEASURE");
                        statement.executeUpdate("delete from STUDY_INDLDE");
                        statement.executeUpdate("delete from STUDY_RECRUITMENT_STATUS");
                        statement.executeUpdate("delete from STUDY_OVERALL_STATUS");
                        statement.executeUpdate("delete from STUDY_CONDITIONS");
                        statement.executeUpdate("delete from CONDITIONS");
                        statement.executeUpdate("delete from STUDY_PARTICIPATION_CONTACT_TELECOM_ADDRESS");
                        statement.executeUpdate("delete from STUDY_PARTICIPATION_CONTACT");
                        statement.executeUpdate("delete from STUDY_PARTICIPATION");
                        statement.executeUpdate("delete from DOCUMENT");
                        statement.executeUpdate("delete from STRATUM_GROUP");
                        statement.executeUpdate("delete from STUDY_PROTOCOL");
                        statement.executeUpdate("delete from COUNTRY");
                        statement.executeUpdate("delete from INTERVENTION");
                        statement.executeUpdate("delete from HEALTHCARE_FACILITY");
                        statement.executeUpdate("delete from ORGANIZATION");
                        statement.executeUpdate("delete from HEALTHCARE_PROVIDER");
                        statement.executeUpdate("delete from PERSON");
                        connection.commit();
                    } finally {
                        statement.close();
                    }
                } catch (HibernateException e) {
                    connection.rollback();
                    throw new RuntimeException(e);
                } catch (SQLException e) {
                    connection.rollback();
                    throw new RuntimeException(e);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
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
            studyParticipationIds = new ArrayList<Long>();
            studyParticipationContactIds = new ArrayList<Long>();
            healthCareFacilityIds = new ArrayList<Long>();
            healthCareProviderIds = new ArrayList<Long>();
            plannedActivityIds = new ArrayList<Long>();
            interventionIds = new ArrayList<Long>();
            armIds = new ArrayList<Long>();

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
            
            Organization org = new Organization();
            org.setIdentifier("ORG ID 01");
            org.setName("Org Name 01");
            addUpdObject(org);
            
            HealthCareFacility hfc = new HealthCareFacility();
            hfc.setIdentifier("HCF ID 01");
            hfc.setOrganization(org);
            addUpdObject(hfc);
            healthCareFacilityIds.add(hfc.getId());
            
            Person per = new Person();
            per.setFirstName("Joe");
            per.setLastName("the Clinician");
            addUpdObject(per);
            
            HealthCareProvider hcp = new HealthCareProvider();
            hcp.setPerson(per);
            hcp.setIdentifier(66L);
            addUpdObject(hcp);
            healthCareProviderIds.add(hcp.getId());
            
            
            StudyParticipation sPart = new StudyParticipation();
            sPart.setFunctionalCode(StudyParticipationFunctionalCode.LEAD_ORAGANIZATION);
            sPart.setHealthCareFacility(hfc);
            sPart.setLocalStudyProtocolIdentifier("Local SP ID 01");
            sPart.setStatusCode(StatusCode.ACTIVE);
            sPart.setStatusDateRangeLow(PAUtil.dateStringToTimestamp("6/1/2008"));
            sPart.setStudyProtocol(sp);
            addUpdObject(sPart);
            studyParticipationIds.add(sPart.getId());
            
            Country country = new Country();
            country.setAlpha2("ZZ");
            country.setAlpha3("ZZZ");
            country.setName("Zanzibar");
            country.setNumeric("67");
            addUpdObject(country);
            
            StudyParticipationContact spc = new StudyParticipationContact();
            spc.setAddressLine("Address 1");
            spc.setCity("City");
            spc.setCountry(country);
            spc.setDeliveryAddressLine("Del. Address 1");
            spc.setPostalCode("ZZZZZ");
            spc.setPrimaryIndicator(true);
            spc.setRoleCode(StudyContactRoleCode.COORDINATING_INVESTIGATOR);
            spc.setState("ZZ");
            spc.setStatusCode(StatusCode.ACTIVE);
            spc.setStatusDateRangeLow(PAUtil.dateStringToTimestamp("1/15/2008"));
            spc.setStudyParticipation(sPart);
            spc.setStudyProtocol(sp);
            spc.setHealthCareProvider(hcp);
            addUpdObject(spc);
            studyParticipationContactIds.add(spc.getId());
            
            StudyParticipationContactTelecomAddress spcta 
                    = new StudyParticipationContactTelecomAddress();
            spcta.setStudyParticipationContact(spc);
            spcta.setTelecomAddress("java@nci.nih.gov");
            addUpdObject(spcta);
            
            Document doc = new Document();
            doc.setStudyProtocol(sp);
            doc.setTypeCode(DocumentTypeCode.Protocol_Document);
            doc.setActiveIndicator(true);
            doc.setFileName("Protocol_Document.doc");
            addUpdObject(doc);
            doc = new Document();
            doc.setStudyProtocol(sp);
            doc.setTypeCode(DocumentTypeCode.IRB_Approval_Document);
            doc.setActiveIndicator(true);
            doc.setFileName("IRB_Approval_Document.doc");
            doc.setInactiveCommentText("Testing");
            addUpdObject(doc);
            
            StratumGroup sg = new StratumGroup();
            sg.setStudyProtocol(sp);
            sg.setDescription("Description1");
            sg.setGroupNumberText("Code1");
            addUpdObject(sg);
            sg = new StratumGroup();
            sg.setStudyProtocol(sp);
            sg.setDescription("Description2");
            sg.setGroupNumberText("Code2");
            addUpdObject(sg);
            
            Intervention inv = new Intervention();
            inv.setName("Chocolate Bar");
            inv.setDescriptionText("Oral intervention to improve morale");
            inv.setDateLastUpdated(new Date());
            inv.setStatusCode(ActiveInactivePendingCode.ACTIVE);
            inv.setStatusDateRangeLow(PAUtil.dateStringToTimestamp("1/1/2000"));
            inv.setTypeCode(InterventionTypeCode.DIETARY_SUPPLEMENT);
            inv.setUserLastUpdated("Joe");
            addUpdObject(inv);
            interventionIds.add(inv.getId());
            
            InterventionAlternateName invo = new InterventionAlternateName();
            invo.setDateLastUpdated(new Date());
            invo.setIntervention(inv);
            invo.setName("Hershey");
            invo.setStatusCode(ActiveInactiveCode.ACTIVE);
            invo.setStatusDateRangeLow(PAUtil.dateStringToTimestamp("1/1/2000"));
            invo.setUserLastUpdated("Joe");
            addUpdObject(invo);
            invo = new InterventionAlternateName();
            invo.setDateLastUpdated(new Date());
            invo.setIntervention(inv);
            invo.setName("Nestle");
            invo.setStatusCode(ActiveInactiveCode.ACTIVE);
            invo.setStatusDateRangeLow(PAUtil.dateStringToTimestamp("1/1/2000"));
            invo.setUserLastUpdated("Joe");            
            addUpdObject(invo);
            
            PlannedActivity pa = new PlannedActivity();
            pa.setAlternateName("alternateName");
            pa.setCategoryCode(ActionCategoryCode.INTERVENTION);
            pa.setDateLastUpdated(new Date());
            pa.setDescriptionText("descriptionText");
            pa.setIntervention(inv);
            pa.setLeadProductIndicator(true);
            pa.setName("name");
            pa.setStudyProtocol(sp);
            pa.setSubcategoryCode(ActionSubcategoryCode.DIETARY_SUPPLEMENT);
            pa.setUserLastUpdated("Joe");
            addUpdObject(pa);
            plannedActivityIds.add(pa.getId());
            
            StudyOutcomeMeasure som = new StudyOutcomeMeasure();
            som.setName("StudyOutcomeMeasure");
            som.setStudyProtocol(sp);
            som.setPrimaryIndicator(Boolean.TRUE);
            addUpdObject(som); 
            
            StudyIndlde si = new StudyIndlde();
            si.setExpandedAccessStatusCode(ExpandedAccessStatusCode.AVAILABLE);
            si.setStudyProtocol(sp);
            si.setExpandedAccessIndicator(Boolean.TRUE);
            si.setHolderTypeCode(HolderTypeCode.NIH);
            si.setNihInstHolderCode(NihInstHolderCode.NCRR);
            addUpdObject(si); 
            
            Arm arm = new Arm();
            arm.setStudyProtocol(sp);
            arm.setName("ARM 01");
            arm.setUserLastCreated("old user");
            arm.setUserLastUpdated("old user");
            arm.getInterventions().add(pa);
            addUpdObject(arm);
            armIds.add(arm.getId());
            
            HibernateUtil.getCurrentSession().clear();
            
        }
}
