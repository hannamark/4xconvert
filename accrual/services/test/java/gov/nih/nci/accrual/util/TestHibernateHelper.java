/*
* caBIG Open Source Software License
*
* Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Protocol  Abstraction (PA) Application
* was created with NCI funding and is part of  the caBIG initiative. The  software subject to  this notice  and license
* includes both  human readable source code form and machine readable, binary, object code form (the caBIG Software).
*
* This caBIG Software License (the License) is between caBIG  Participant  and  You.  You (or Your) shall  mean a
* person or an entity, and all other entities that control, are  controlled by,  or  are under common  control  with the
* entity.  Control for purposes of this definition means
*
* (i) the direct or indirect power to cause the direction or management of such entity,whether by contract
* or otherwise,or
*
* (ii) ownership of fifty percent (50%) or more of the outstanding shares, or
*
* (iii) beneficial ownership of such entity.
* License.  Provided that You agree to the conditions described below, caBIG Participant  grants  You a  non-exclusive,
* worldwide, perpetual, fully-paid-up, no-charge, irrevocable,  transferable  and royalty-free  right and license in its
* rights in the caBIG Software, including any copyright or patent rights therein, to
*
* (i) use,install, disclose, access, operate,  execute, reproduce,  copy, modify, translate,  market,  publicly display,
* publicly perform, and prepare derivative works of the caBIG Software in any manner and for any  purpose,  and to have
* or permit others to do so;
*
* (ii) make, have made, use, practice, sell, and offer  for sale,  import, and/or  otherwise  dispose of caBIG Software
* (or portions thereof);
*
* (iii) distribute and have distributed  to  and by third   parties the   caBIG  Software  and any   modifications  and
* derivative works thereof; and (iv) sublicense the  foregoing rights  set  out in (i), (ii) and (iii) to third parties,
* including the right to license such rights to further third parties. For sake of clarity,and not by way of limitation,
* caBIG Participant shall have no right of accounting or right of payment from You or Your sub licensees for the rights
* granted under this License.   This  License  is  granted  at no  charge  to You. Your downloading, copying, modifying,
* displaying, distributing or use of caBIG Software constitutes acceptance  of  all of the terms and conditions of this
* Agreement.  If You do not agree to such terms and conditions,  You have no right to download,  copy,  modify, display,
* distribute or use the caBIG Software.
*
* 1.  Your redistributions of the source code for the caBIG Software must retain the above copyright notice, this  list
* of conditions and the disclaimer and limitation of liability of Article 6 below.   Your redistributions in object code
* form must reproduce the above copyright notice,  this list of  conditions  and the  disclaimer  of  Article  6  in the
* documentation and/or other materials provided with the distribution, if any.
*
* 2.  Your end-user documentation included with the redistribution, if any,  must include the  following acknowledgment:
* This product includes software developed by ScenPro, Inc.   If  You  do not include such end-user documentation, You
* shall include this acknowledgment in the caBIG Software itself, wherever such third-party acknowledgments normally
* appear.
*
* 3.  You may not use the names ScenPro, Inc., The National Cancer Institute, NCI, Cancer Bioinformatics Grid or
* caBIG to endorse or promote products derived from this caBIG Software.  This License does not authorize You to use
* any trademarks, service marks, trade names, logos or product names of either caBIG Participant, NCI or caBIG, except
* as required to comply with the terms of this License.
*
* 4.  For sake of clarity, and not by way of limitation, You  may incorporate this caBIG Software into Your proprietary
* programs and into any third party proprietary programs.  However, if You incorporate the  caBIG Software  into  third
* party proprietary programs,  You agree  that You are  solely responsible  for obtaining any permission from such third
* parties required to incorporate the caBIG Software  into such third party proprietary programs and for informing Your
* sub licensees, including without limitation Your end-users, of their obligation  to  secure  any  required permissions
* from such third parties before incorporating the caBIG Software into such third party proprietary  software programs.
* In the event that You fail to obtain such permissions,  You  agree  to  indemnify  caBIG  Participant  for any claims
* against caBIG Participant by such third parties, except to the extent prohibited by law,  resulting from Your failure
* to obtain such permissions.
*
* 5.  For sake of clarity, and not by way of limitation, You may add Your own copyright statement  to Your modifications
* and to the derivative works, and You may provide  additional  or  different  license  terms  and  conditions  in  Your
* sublicenses of modifications of the caBIG  Software,  or  any  derivative  works  of  the caBIG Software as a whole,
* provided Your use, reproduction,  and  distribution  of the Work otherwise complies with the conditions stated in this
* License.
*
* 6.  THIS caBIG SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES  ( INCLUDING, BUT NOT LIMITED TO,
* THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN
* NO EVENT SHALL THE ScenPro, Inc. OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
* OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  LIMITED  TO,  PROCUREMENT OF SUBSTITUTE GOODS  OR SERVICES; LOSS OF USE,
* DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
* LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG SOFTWARE, EVEN
* IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package gov.nih.nci.accrual.util;

import gov.nih.nci.accrual.domain.Epoch;
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
import gov.nih.nci.pa.domain.ObservationResult;
import gov.nih.nci.pa.domain.ObservationalStudyProtocol;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.OrganizationalContact;
import gov.nih.nci.pa.domain.OversightCommittee;
import gov.nih.nci.pa.domain.PAProperties;
import gov.nih.nci.pa.domain.Patient;
import gov.nih.nci.pa.domain.PerformedActivity;
import gov.nih.nci.pa.domain.PerformedAdministrativeActivity;
import gov.nih.nci.pa.domain.PerformedObservation;
import gov.nih.nci.pa.domain.PerformedObservationResult;
import gov.nih.nci.pa.domain.PerformedSubjectMilestone;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.domain.PlannedActivity;
import gov.nih.nci.pa.domain.PlannedAdministrativeActivity;
import gov.nih.nci.pa.domain.PlannedEligibilityCriterion;
import gov.nih.nci.pa.domain.PlannedObservationResult;
import gov.nih.nci.pa.domain.PlannedStudySubjectMilestone;
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
import gov.nih.nci.pa.domain.StudyObjective;
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
import gov.nih.nci.pa.domain.StudySubject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

/**
 * @author Hugh Reinhart
 * @since 7/7/2009
 */
public class TestHibernateHelper implements CtrpHibernateHelper {

    private final Configuration configuration;
    private final SessionFactory sessionFactory;
    private final Session testSession;

    /**
     * Default constructor.
     */
    public TestHibernateHelper() {
        configuration = new AnnotationConfiguration().

        // temp
        addAnnotatedClass(Epoch.class).

        // Accrual classes
        addAnnotatedClass(ObservationResult.class).
        addAnnotatedClass(Patient.class).
        addAnnotatedClass(PerformedActivity.class).
        addAnnotatedClass(PerformedAdministrativeActivity.class).
        addAnnotatedClass(PerformedObservation.class).
        addAnnotatedClass(PerformedObservationResult.class).
        addAnnotatedClass(PerformedSubjectMilestone.class).
        addAnnotatedClass(PlannedAdministrativeActivity.class).
        addAnnotatedClass(PlannedObservationResult.class).
        addAnnotatedClass(PlannedStudySubjectMilestone.class).
        addAnnotatedClass(StudySubject.class).

        // PA classes
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
        addAnnotatedClass(StudyObjective.class).

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
