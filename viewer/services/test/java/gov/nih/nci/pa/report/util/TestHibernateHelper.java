package gov.nih.nci.pa.report.util;

import gov.nih.nci.pa.domain.Arm;
import gov.nih.nci.pa.domain.ClinicalResearchStaff;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.Disease;
import gov.nih.nci.pa.domain.DiseaseAltername;
import gov.nih.nci.pa.domain.DiseaseParent;
import gov.nih.nci.pa.domain.Document;
import gov.nih.nci.pa.domain.DocumentWorkflowStatus;
import gov.nih.nci.pa.domain.FundingMechanism;
import gov.nih.nci.pa.domain.HealthCareFacility;
import gov.nih.nci.pa.domain.HealthCareProvider;
import gov.nih.nci.pa.domain.Intervention;
import gov.nih.nci.pa.domain.InterventionAlternateName;
import gov.nih.nci.pa.domain.InterventionalStudyProtocol;
import gov.nih.nci.pa.domain.NIHinstitute;
import gov.nih.nci.pa.domain.ObservationalStudyProtocol;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.OrganizationalContact;
import gov.nih.nci.pa.domain.OversightCommittee;
import gov.nih.nci.pa.domain.PAProperties;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.domain.PlannedActivity;
import gov.nih.nci.pa.domain.PlannedEligibilityCriterion;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.RegulatoryAuthority;
import gov.nih.nci.pa.domain.ResearchOrganization;
import gov.nih.nci.pa.domain.StratumGroup;
import gov.nih.nci.pa.domain.StudyContact;
import gov.nih.nci.pa.domain.StudyCoordinatingCenter;
import gov.nih.nci.pa.domain.StudyCoordinatingCenterRole;
import gov.nih.nci.pa.domain.StudyDisease;
import gov.nih.nci.pa.domain.StudyIndlde;
import gov.nih.nci.pa.domain.StudyMilestone;
import gov.nih.nci.pa.domain.StudyOnhold;
import gov.nih.nci.pa.domain.StudyOutcomeMeasure;
import gov.nih.nci.pa.domain.StudyOverallStatus;
import gov.nih.nci.pa.domain.StudyParticipation;
import gov.nih.nci.pa.domain.StudyParticipationContact;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyRecruitmentStatus;
import gov.nih.nci.pa.domain.StudyRegulatoryAuthority;
import gov.nih.nci.pa.domain.StudyRelationship;
import gov.nih.nci.pa.domain.StudyResourcing;
import gov.nih.nci.pa.domain.StudySiteAccrualStatus;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

/**
 * HibernateHelper class used during unit testing.
 */
public class TestHibernateHelper implements CtrpHibernateHelper {

    private static final Class<?>[] CSM_CLASSES = {gov.nih.nci.security.authorization.domainobjects.Application.class,
        gov.nih.nci.security.authorization.domainobjects.Group.class,
        gov.nih.nci.security.authorization.domainobjects.Privilege.class,
        gov.nih.nci.security.authorization.domainobjects.ProtectionElement.class,
        gov.nih.nci.security.authorization.domainobjects.ProtectionGroup.class,
        gov.nih.nci.security.authorization.domainobjects.Role.class,
        gov.nih.nci.security.authorization.domainobjects.User.class,
        gov.nih.nci.security.authorization.domainobjects.UserGroupRoleProtectionGroup.class,
        gov.nih.nci.security.authorization.domainobjects.UserProtectionElement.class,
        gov.nih.nci.security.authorization.domainobjects.FilterClause.class };

    private final Configuration configuration;
    private final SessionFactory sessionFactory;
    private final Session testSession;

    /**
     * Default constructor.
     */
    public TestHibernateHelper() {
        configuration = new AnnotationConfiguration().
        // COPPA-PA classes
        addAnnotatedClass(StudyProtocol.class).
        addAnnotatedClass(StudyRelationship.class).
        addAnnotatedClass(InterventionalStudyProtocol.class).
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
        addAnnotatedClass(OversightCommittee.class).
        addAnnotatedClass(Document.class).
        addAnnotatedClass(StudyRecruitmentStatus.class).
        addAnnotatedClass(StratumGroup.class).
        addAnnotatedClass(ResearchOrganization.class).
        addAnnotatedClass(PlannedActivity.class).
        addAnnotatedClass(PlannedEligibilityCriterion.class).
        addAnnotatedClass(Intervention.class).
        addAnnotatedClass(InterventionAlternateName.class).
        addAnnotatedClass(ObservationalStudyProtocol.class).
        addAnnotatedClass(StudyOutcomeMeasure.class).
        addAnnotatedClass(StudyIndlde.class).
        addAnnotatedClass(Arm.class).
        addAnnotatedClass(ClinicalResearchStaff.class).
        addAnnotatedClass(OrganizationalContact.class).
        addAnnotatedClass(Disease.class).
        addAnnotatedClass(DiseaseAltername.class).
        addAnnotatedClass(DiseaseParent.class).
        addAnnotatedClass(StudyDisease.class).
        addAnnotatedClass(StudyMilestone.class).
        addAnnotatedClass(StudyOnhold.class).
        addAnnotatedClass(NIHinstitute.class).
        addAnnotatedClass(PAProperties.class).
        addAnnotatedClass(RegistryUser.class).
        addAnnotatedClass(StudyRelationship.class).
        // hibernate properties
        setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect").
        setProperty("hibernate.connection.driver_class", "org.hsqldb.jdbcDriver").
        setProperty("hibernate.connection.url", "jdbc:hsqldb:mem:ctods").
        setProperty("hibernate.connection.username", "sa").
        setProperty("hibernate.connection.password", "").
        setProperty("hibernate.connection.pool_size", "1").
        setProperty("hibernate.connection.autocommit", "true").
        setProperty("hibernate.cache.provider_class", "org.hibernate.cache.HashtableCacheProvider").
        setProperty("hibernate.hbm2ddl.auto", "create-drop").
        setProperty("hibernate.show_sql", "false");

        for (Class<?> cls : CSM_CLASSES) {
            configuration.addClass(cls);
        }
        sessionFactory = configuration.buildSessionFactory();
        testSession = sessionFactory.openSession();
    }

    /**
     * {@inheritDoc}
     */
    public Session getCurrentSession() {
        return testSession;
    }

    /**
     * {@inheritDoc}
     */
   public Configuration getConfiguration() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public SessionFactory getSessionFactory() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public void openAndBindSession() {
    }

    /**
     * {@inheritDoc}
     */
    public void unbindAndCleanupSession() {
    }
}
