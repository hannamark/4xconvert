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
*
*
*/
package gov.nih.nci.pa.util;

import gov.nih.nci.pa.domain.Arm;
import gov.nih.nci.pa.domain.ClinicalResearchStaff;
import gov.nih.nci.pa.domain.ClinicalResearchStaffTest;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.Disease;
import gov.nih.nci.pa.domain.DiseaseAltername;
import gov.nih.nci.pa.domain.DiseaseAlternameTest;
import gov.nih.nci.pa.domain.DiseaseParent;
import gov.nih.nci.pa.domain.DiseaseParentTest;
import gov.nih.nci.pa.domain.DiseaseTest;
import gov.nih.nci.pa.domain.Document;
import gov.nih.nci.pa.domain.DocumentWorkflowStatus;
import gov.nih.nci.pa.domain.FundingMechanism;
import gov.nih.nci.pa.domain.HealthCareFacility;
import gov.nih.nci.pa.domain.HealthCareFacilityTest;
import gov.nih.nci.pa.domain.HealthCareProvider;
import gov.nih.nci.pa.domain.HealthCareProviderTest;
import gov.nih.nci.pa.domain.Intervention;
import gov.nih.nci.pa.domain.InterventionAlternateName;
import gov.nih.nci.pa.domain.InterventionalStudyProtocol;
import gov.nih.nci.pa.domain.NIHinstitute;
import gov.nih.nci.pa.domain.ObservationalStudyProtocol;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.OrganizationTest;
import gov.nih.nci.pa.domain.OrganizationalContact;
import gov.nih.nci.pa.domain.OversightCommittee;
import gov.nih.nci.pa.domain.PAProperties;
import gov.nih.nci.pa.domain.Patient;
import gov.nih.nci.pa.domain.PerformedActivity;
import gov.nih.nci.pa.domain.PerformedAdministrativeActivity;
import gov.nih.nci.pa.domain.PerformedSubjectMilestone;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.domain.PersonTest;
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
import gov.nih.nci.pa.domain.StudyDiseaseTest;
import gov.nih.nci.pa.domain.StudyIndlde;
import gov.nih.nci.pa.domain.StudyMilestone;
import gov.nih.nci.pa.domain.StudyMilestoneTest;
import gov.nih.nci.pa.domain.StudyObjective;
import gov.nih.nci.pa.domain.StudyOnhold;
import gov.nih.nci.pa.domain.StudyOutcomeMeasure;
import gov.nih.nci.pa.domain.StudyOverallStatus;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyRecruitmentStatus;
import gov.nih.nci.pa.domain.StudyRegulatoryAuthority;
import gov.nih.nci.pa.domain.StudyRelationship;
import gov.nih.nci.pa.domain.StudyResourcing;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.domain.StudySiteAccrualStatus;
import gov.nih.nci.pa.domain.StudySiteContact;
import gov.nih.nci.pa.domain.StudySubject;
import gov.nih.nci.pa.domain.Submission;
import gov.nih.nci.pa.enums.AccrualReportingMethodCode;
import gov.nih.nci.pa.enums.ActiveInactiveCode;
import gov.nih.nci.pa.enums.ActiveInactivePendingCode;
import gov.nih.nci.pa.enums.ActivityCategoryCode;
import gov.nih.nci.pa.enums.ActivitySubcategoryCode;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.ArmTypeCode;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.ExpandedAccessStatusCode;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.GrantorCode;
import gov.nih.nci.pa.enums.HolderTypeCode;
import gov.nih.nci.pa.enums.IndldeTypeCode;
import gov.nih.nci.pa.enums.InterventionTypeCode;
import gov.nih.nci.pa.enums.NihInstituteCode;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.enums.StructuralRoleStatusCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.enums.UnitsCode;

import java.math.BigDecimal;
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
public class TestSchema {
        /** . **/
        public static ArrayList<Long> studyProtocolIds;
        public static ArrayList<Long> studySiteIds;
        public static ArrayList<Long> studySiteContactIds;
        public static ArrayList<Long> healthCareFacilityIds;
        public static ArrayList<Long> healthCareProviderIds;
        public static ArrayList<Long> clinicalResearchStaffIds;
        public static ArrayList<Long> plannedActivityIds;
        public static ArrayList<Long> interventionIds;
        public static ArrayList<Long> armIds;
        public static ArrayList<Long> researchOrganizationIds;
        public static ArrayList<Long> oversightCommitteeIds;
        public static ArrayList<Long> diseaseIds;
        public static ArrayList<Long> outcomeIds;
        public static ArrayList<Long> regAuthIds;
        public static ArrayList<Long> personIds;

        static {
            Configuration config = new AnnotationConfiguration().

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
            addAnnotatedClass(StudySite.class).
            addAnnotatedClass(Country.class).
            addAnnotatedClass(RegulatoryAuthority.class).
            addAnnotatedClass(StudyRegulatoryAuthority.class).
            addAnnotatedClass(HealthCareFacility.class).
            addAnnotatedClass(StudyResourcing.class).
            addAnnotatedClass(FundingMechanism.class).
            addAnnotatedClass(StudySiteAccrualStatus.class).
            addAnnotatedClass(StudySiteContact.class).
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

            // Accrual classes
            addAnnotatedClass(Patient.class).
            addAnnotatedClass(PerformedActivity.class).
            addAnnotatedClass(PerformedAdministrativeActivity.class).
            addAnnotatedClass(PerformedSubjectMilestone.class).
            addAnnotatedClass(StudySubject.class).
            addAnnotatedClass(Submission.class).

            setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect").
            setProperty("hibernate.connection.driver_class", "org.hsqldb.jdbcDriver").
            setProperty("hibernate.connection.url", "jdbc:hsqldb:mem:ctods").
            setProperty("hibernate.connection.username", "sa").
            setProperty("hibernate.connection.password", "").
            setProperty("hibernate.connection.pool_size", "1").
            setProperty("hibernate.connection.autocommit", "true").
            setProperty("hibernate.cache.provider_class", "org.hibernate.cache.HashtableCacheProvider").
            setProperty("hibernate.hbm2ddl.auto", "create-drop").
            setProperty("hibernate.show_sql", TestProperties.getShowSQL()); // set using test.schema.showsql in build.properties
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
                    statement.executeUpdate("delete from study_subject");
                    statement.executeUpdate("delete from patient");
                    statement.executeUpdate("delete from STUDY_ONHOLD");
                    statement.executeUpdate("delete from STUDY_RELATIONSHIP");
                    statement.executeUpdate("delete from STUDY_MILESTONE");
                    statement.executeUpdate("delete from STUDY_CONTACT");
                    statement.executeUpdate("delete from STUDY_REGULATORY_AUTHORITY");
                    statement.executeUpdate("delete from REGULATORY_AUTHORITY");
                    statement.executeUpdate("delete from DOCUMENT_WORKFLOW_STATUS");
                    statement.executeUpdate("delete from ARM_INTERVENTION");
                    statement.executeUpdate("delete from PLANNED_ACTIVITY");
                    statement.executeUpdate("delete from ARM");
                    statement.executeUpdate("delete from STUDY_OUTCOME_MEASURE");
                    statement.executeUpdate("delete from STUDY_INDLDE");
                    statement.executeUpdate("delete from STUDY_RECRUITMENT_STATUS");
                    statement.executeUpdate("delete from STUDY_OVERALL_STATUS");
                    statement.executeUpdate("delete from STUDY_SITE_ACCRUAL_STATUS");
                    statement.executeUpdate("delete from STUDY_SITE_CONTACT");
                    statement.executeUpdate("delete from STUDY_SITE");
                    statement.executeUpdate("delete from DOCUMENT");
                    statement.executeUpdate("delete from STUDY_RESOURCING");
                    statement.executeUpdate("delete from STRATUM_GROUP");
                    statement.executeUpdate("delete from STUDY_DISEASE");
                    statement.executeUpdate("delete from STUDY_RECRUITMENT_STATUS");
                    statement.executeUpdate("delete from STUDY_PROTOCOL");
                    statement.executeUpdate("delete from CLINICAL_RESEARCH_STAFF");
                    statement.executeUpdate("delete from COUNTRY");
                    statement.executeUpdate("delete from INTERVENTION_ALTERNATE_NAME");
                    statement.executeUpdate("delete from INTERVENTION");
                    statement.executeUpdate("delete from ORGANIZATIONAL_CONTACT");
                    statement.executeUpdate("delete from HEALTHCARE_FACILITY");
                    statement.executeUpdate("delete from HEALTHCARE_PROVIDER");
                    statement.executeUpdate("delete from RESEARCH_ORGANIZATION");
                    statement.executeUpdate("delete from OVERSIGHT_COMMITTEE");
                    statement.executeUpdate("delete from ORGANIZATION");
                    statement.executeUpdate("delete from PERSON");
                    statement.executeUpdate("delete from DISEASE_PARENT");
                    statement.executeUpdate("delete from DISEASE_ALTERNAME");
                    statement.executeUpdate("delete from DISEASE");
                    statement.executeUpdate("delete from STUDY_OBJECTIVE");
                    statement.executeUpdate("delete from REGULATORY_AUTHORITY");
                    connection.commit();
                    statement.close();
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
            studySiteIds = new ArrayList<Long>();
            studySiteContactIds = new ArrayList<Long>();
            healthCareFacilityIds = new ArrayList<Long>();
            healthCareProviderIds = new ArrayList<Long>();
            clinicalResearchStaffIds = new ArrayList<Long>();
            plannedActivityIds = new ArrayList<Long>();
            interventionIds = new ArrayList<Long>();
            armIds = new ArrayList<Long>();
            researchOrganizationIds = new ArrayList<Long>();
            oversightCommitteeIds = new ArrayList<Long>();
            diseaseIds = new ArrayList<Long>();
            outcomeIds = new ArrayList<Long>();
            regAuthIds = new ArrayList<Long>();
            personIds = new ArrayList<Long>();

            StudyProtocol sp = new StudyProtocol();
            sp.setOfficialTitle("cacncer for THOLA");
            sp.setStartDate(PAUtil.dateStringToTimestamp("1/1/2000"));
            sp.setStartDateTypeCode(ActualAnticipatedTypeCode.ACTUAL);
            sp.setPrimaryCompletionDate(PAUtil.dateStringToTimestamp("12/31/2009"));
            sp.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode.ANTICIPATED);
            sp.setAccrualReportingMethodCode(AccrualReportingMethodCode.ABBREVIATED);
            sp.setIdentifier("NCI-2009-00001");
            sp.setSubmissionNumber(Integer.valueOf(1));

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

            Organization org = OrganizationTest.createOrganizationObj();
            addUpdObject(org);

            HealthCareFacility hfc = HealthCareFacilityTest.createHealthCareFacilityObj(org);
            addUpdObject(hfc);
            healthCareFacilityIds.add(hfc.getId());

            ResearchOrganization rOrg = new ResearchOrganization();
            rOrg.setOrganization(org);
            rOrg.setStatusCode(StructuralRoleStatusCode.ACTIVE);
            rOrg.setIdentifier("abc");
            addUpdObject(rOrg);
            researchOrganizationIds.add(rOrg.getId());

            OversightCommittee oCommittee = new OversightCommittee();
            oCommittee.setOrganization(org);
            oCommittee.setStatusCode(StructuralRoleStatusCode.ACTIVE);
            oCommittee.setIdentifier("abc");
            addUpdObject(oCommittee);
            oversightCommitteeIds.add(oCommittee.getId());

            Person per = PersonTest.createPersonObj();
            per.setFirstName("Joe");
            per.setLastName("the Clinician");
            addUpdObject(per);
            personIds.add(per.getId());

            HealthCareProvider hcp = HealthCareProviderTest.createHealthCareProviderObj(per, org);
            addUpdObject(hcp);
            healthCareProviderIds.add(hcp.getId());

            ClinicalResearchStaff crs = ClinicalResearchStaffTest.createClinicalResearchStaffObj(org, per);
            addUpdObject(crs);
            clinicalResearchStaffIds.add(crs.getId());

            StudySite sPart = new StudySite();
            sPart.setFunctionalCode(StudySiteFunctionalCode.LEAD_ORGANIZATION);
            sPart.setHealthCareFacility(hfc);
            sPart.setLocalStudyProtocolIdentifier("Local SP ID 01");
            sPart.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
            sPart.setStatusDateRangeLow(PAUtil.dateStringToTimestamp("6/1/2008"));
            sPart.setStudyProtocol(sp);
            addUpdObject(sPart);
            studySiteIds.add(sPart.getId());

            Country country = new Country();
            country.setAlpha2("ZZ");
            country.setAlpha3("ZZZ");
            country.setName("Zanzibar");
            country.setNumeric("67");
            addUpdObject(country);

            StudySiteContact spc = new StudySiteContact();
            spc.setAddressLine("Address 1");
            spc.setCity("City");
            spc.setCountry(country);
            spc.setPhone("111");
            spc.setEmail("naveen@yahoo.com");
            spc.setDeliveryAddressLine("Del. Address 1");
            spc.setPostalCode("ZZZZZ");
            spc.setPrimaryIndicator(true);
            spc.setRoleCode(StudySiteContactRoleCode.SUBMITTER);
            spc.setState("ZZ");
            spc.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
            spc.setStatusDateRangeLow(PAUtil.dateStringToTimestamp("1/15/2008"));
            spc.setStudySite(sPart);
            spc.setStudyProtocol(sp);
            spc.setHealthCareProvider(hcp);
            spc.setClinicalResearchStaff(crs);

            addUpdObject(spc);
            studySiteContactIds.add(spc.getId());


            Document doc = new Document();
            doc.setStudyProtocol(sp);
            doc.setTypeCode(DocumentTypeCode.PROTOCOL_DOCUMENT);
            doc.setActiveIndicator(true);
            doc.setFileName("Protocol_Document.doc");
            addUpdObject(doc);
            doc = new Document();
            doc.setStudyProtocol(sp);
            doc.setTypeCode(DocumentTypeCode.IRB_APPROVAL_DOCUMENT);
            doc.setActiveIndicator(true);
            doc.setFileName("IRB_Approval_Document.doc");
            doc.setInactiveCommentText("Testing");
            addUpdObject(doc);

            StratumGroup sg = new StratumGroup();
            sg.setStudyProtocol(sp);
            sg.setDescription("Description1");
            sg.setGroupNumberText("Code1");
            sg.setUserLastUpdated("curator");
            addUpdObject(sg);
            sg = new StratumGroup();
            sg.setStudyProtocol(sp);
            sg.setDescription("Description2");
            sg.setGroupNumberText("Code2");
            sg.setUserLastUpdated("curator");
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
            invo.setNameTypeCode("synonym");
            addUpdObject(invo);
            invo = new InterventionAlternateName();
            invo.setDateLastUpdated(new Date());
            invo.setIntervention(inv);
            invo.setName("Nestle");
            invo.setStatusCode(ActiveInactiveCode.ACTIVE);
            invo.setStatusDateRangeLow(PAUtil.dateStringToTimestamp("1/1/2000"));
            invo.setUserLastUpdated("Joe");
            invo.setNameTypeCode("synonym");
            addUpdObject(invo);

            PlannedActivity pa = new PlannedActivity();
            pa.setCategoryCode(ActivityCategoryCode.INTERVENTION);
            pa.setDateLastUpdated(new Date());
            pa.setIntervention(inv);
            pa.setLeadProductIndicator(true);
            pa.setStudyProtocol(sp);
            pa.setSubcategoryCode(ActivitySubcategoryCode.DIETARY_SUPPLEMENT);
            pa.setUserLastUpdated("Joe");
            addUpdObject(pa);
            plannedActivityIds.add(pa.getId());

            StudyOutcomeMeasure som = new StudyOutcomeMeasure();
            som.setName("StudyOutcomeMeasure");
            som.setStudyProtocol(sp);
            som.setPrimaryIndicator(Boolean.TRUE);
            som.setUserLastUpdated("curator");
            addUpdObject(som);
            outcomeIds.add(som.getId());

            StudyIndlde si = new StudyIndlde();
            si.setIndldeTypeCode(IndldeTypeCode.IND);
            si.setGrantorCode(GrantorCode.CDER);
            si.setIndldeNumber("1234");
            si.setExpandedAccessStatusCode(ExpandedAccessStatusCode.AVAILABLE);
            si.setStudyProtocol(sp);
            si.setExpandedAccessIndicator(Boolean.TRUE);
            si.setHolderTypeCode(HolderTypeCode.NIH);
            si.setNihInstHolderCode(NihInstituteCode.NCMHD);
            addUpdObject(si);

            Arm arm = new Arm();
            arm.setStudyProtocol(sp);
            arm.setName("ARM 01");
            arm.setTypeCode(ArmTypeCode.EXPERIMENTAL);
            arm.setDescriptionText("arm description");
            arm.setUserLastCreated("old user");
            arm.setUserLastUpdated("old user");
            arm.getInterventions().add(pa);
            addUpdObject(arm);
            armIds.add(arm.getId());

            DocumentWorkflowStatus dws = new DocumentWorkflowStatus();
            dws.setStudyProtocol(sp);
            dws.setStatusCode(DocumentWorkflowStatusCode.ABSTRACTED);
            dws.setCommentText("Comment Text1");
            dws.setUserLastUpdated("curator");
            addUpdObject(dws);

            RegulatoryAuthority ra = new RegulatoryAuthority();
            ra.setAuthorityName("AuthorityName");
            ra.setCountry(country);
            addUpdObject(ra);

            StudyRegulatoryAuthority sra = new StudyRegulatoryAuthority();
            sra.setRegulatoryAuthority(ra);
            sra.setStudyProtocol(sp);
            addUpdObject(sra);

            StudyContact sc = new StudyContact();
            sc.setPrimaryIndicator(Boolean.TRUE);
            sc.setStudyProtocol(sp);
            sc.setRoleCode(StudyContactRoleCode.SCIENTIFIC_LEADERSHIP);
            sc.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
            addUpdObject(sc);

            StudySiteAccrualStatus ssas = new StudySiteAccrualStatus();
            ssas.setStatusCode(RecruitmentStatusCode.ACTIVE_NOT_RECRUITING);
            ssas.setStatusDate(new java.sql.Timestamp(new java.util.Date().getTime()));
            ssas.setStudySite(sPart);
            addUpdObject(ssas);

            PlannedEligibilityCriterion pec = new PlannedEligibilityCriterion();
            pec.setCriterionName("WHC");
            pec.setInclusionIndicator(Boolean.TRUE);
            pec.setOperator(">");
            pec.setStudyProtocol(sp);
            pec.setValue(new BigDecimal("14"));
            pec.setUnit(UnitsCode.MONTHS.getCode());
            addUpdObject(pec);

            Disease dis01 = DiseaseTest.createDiseaseObj("Toe Cancer");
            addUpdObject(dis01);
            diseaseIds.add(dis01.getId());
            Disease dis02 = DiseaseTest.createDiseaseObj("Heel Cancer");
            addUpdObject(dis02);
            diseaseIds.add(dis02.getId());
            Disease dis03 = DiseaseTest.createDiseaseObj("Foot Cancer");
            addUpdObject(dis03);
            diseaseIds.add(dis03.getId());
            Disease dis04 = DiseaseTest.createDiseaseObj("Leg Cancer");
            addUpdObject(dis04);
            diseaseIds.add(dis04.getId());

            DiseaseParent disPar1 = DiseaseParentTest.createDiseaseParentObj(dis01, dis03);
            addUpdObject(disPar1);
            DiseaseParent disPar2 = DiseaseParentTest.createDiseaseParentObj(dis02, dis03);
            addUpdObject(disPar2);
            DiseaseParent disPar3 = DiseaseParentTest.createDiseaseParentObj(dis03, dis04);
            addUpdObject(disPar3);

            DiseaseAltername diseaseAltername = DiseaseAlternameTest.createDiseaseAlternameObj("Little Piggy Cancer", dis01);
            addUpdObject(diseaseAltername);

            StudyDisease studyDisease = StudyDiseaseTest.createStudyDiseaseObj(sp, dis01);
            addUpdObject(studyDisease);

            StudyMilestone studyMilestone = StudyMilestoneTest.createStudyMilestoneObj("comment 01", sp);
            addUpdObject(studyMilestone);

            RegulatoryAuthority rega = new RegulatoryAuthority();
            rega.setCountry(country);
            rega.setAuthorityName("Authority");
            addUpdObject(rega);
            regAuthIds.add(rega.getId());

            HibernateUtil.getCurrentSession().clear();
        }
}
