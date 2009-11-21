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

import gov.nih.nci.accrual.service.util.MockCsmUtil;
import gov.nih.nci.pa.domain.ActivityRelationship;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.Disease;
import gov.nih.nci.pa.domain.HealthCareFacility;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.Patient;
import gov.nih.nci.pa.domain.PerformedActivity;
import gov.nih.nci.pa.domain.PerformedClinicalResult;
import gov.nih.nci.pa.domain.PerformedDiagnosis;
import gov.nih.nci.pa.domain.PerformedHistopathology;
import gov.nih.nci.pa.domain.PerformedImage;
import gov.nih.nci.pa.domain.PerformedImaging;
import gov.nih.nci.pa.domain.PerformedLesionDescription;
import gov.nih.nci.pa.domain.PerformedMedicalHistoryResult;
import gov.nih.nci.pa.domain.PerformedObservation;
import gov.nih.nci.pa.domain.PerformedProcedure;
import gov.nih.nci.pa.domain.PerformedRadiationAdministration;
import gov.nih.nci.pa.domain.PerformedSubjectMilestone;
import gov.nih.nci.pa.domain.PerformedSubstanceAdministration;
import gov.nih.nci.pa.domain.PlannedActivity;
import gov.nih.nci.pa.domain.StudyDisease;
import gov.nih.nci.pa.domain.StudyOverallStatus;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.domain.StudySiteAccrualAccess;
import gov.nih.nci.pa.domain.StudySubject;
import gov.nih.nci.pa.domain.Submission;
import gov.nih.nci.pa.enums.AccrualReportingMethodCode;
import gov.nih.nci.pa.enums.AccrualSubmissionStatusCode;
import gov.nih.nci.pa.enums.ActStatusCode;
import gov.nih.nci.pa.enums.ActiveInactiveCode;
import gov.nih.nci.pa.enums.ActiveInactivePendingCode;
import gov.nih.nci.pa.enums.ActivityCategoryCode;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.EntityStatusCode;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.PatientEthnicityCode;
import gov.nih.nci.pa.enums.PatientGenderCode;
import gov.nih.nci.pa.enums.PatientRaceCode;
import gov.nih.nci.pa.enums.PaymentMethodCode;
import gov.nih.nci.pa.enums.StructuralRoleStatusCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.util.PAUtil;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

/**
* @author Hugh Reinhart
* @since 08/07/2009
*/
public class TestSchema {
    public static List<Disease> diseases;
    public static List<StudyProtocol> studyProtocols;
    public static List<Submission> submissions;
    public static List<StudySite> studySites;
    public static List<StudyDisease> studyDiseases;
    public static List<Patient> patients;
    public static List<StudySubject> studySubjects;
    public static List<PerformedSubjectMilestone> performedSubjectMilestones;
    public static List<StudyOverallStatus> studyOverallStatuses;
    public static List<HealthCareFacility> healthCareFacilities;
    public static List<Organization> organizations;
    public static List<StudySiteAccrualAccess> studySiteAccrualAccess;
    public static List<Country> countries;
    public static List<PerformedObservation> performedObservations;
    public static List<PerformedImaging> performedImagings;
    public static List<PerformedSubstanceAdministration> performedSubstanceAdministrations;
    public static List<PerformedRadiationAdministration> performedRadiationAdministrations;
    public static List<PerformedProcedure> performedProcedures;
    public static List<PerformedDiagnosis> performedDiagnosis;
    public static List<PerformedImage> performedImages;
    public static List<PerformedHistopathology> performedHistopathologies;
    public static List<PerformedClinicalResult> performedClinicalResults;
    public static List<PerformedMedicalHistoryResult> performedMedicalHistoryResults;
    public static List<PerformedLesionDescription> performedLesionDescriptions;
    public static List<PlannedActivity> plannedActivities;
    public static List<PerformedActivity> performedActivities;
    public static List<ActivityRelationship> activityRelationships;

    public static int outcomesSpId = 0;
    public static int outcomesSsId = 0;

    private static CtrpHibernateHelper testHelper = new TestHibernateHelper();

    /**
     *  Reset the schema.
     */
    @SuppressWarnings("unused")
    public static void reset() throws Exception {
        /* just to exercise the getHibernateHelper with a null value */
        AccrualHibernateUtil ahu = new AccrualHibernateUtil();
        AccrualHibernateUtil.testHelper = null;
        AccrualHibernateUtil.getHibernateHelper();
        /* end test */

        AccrualHibernateUtil.testHelper = testHelper;
        Session session = AccrualHibernateUtil.getHibernateHelper().getCurrentSession();
        session.flush();
        Connection connection = session.connection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("delete from activity_relationship");
        statement.executeUpdate("delete from performed_observation_result");
        statement.executeUpdate("delete from performed_activity");
        statement.executeUpdate("delete from study_disease");
        statement.executeUpdate("delete from study_subject");
        statement.executeUpdate("delete from patient");
        statement.executeUpdate("delete from study_site");
        statement.executeUpdate("delete from submission");
        statement.executeUpdate("delete from study_overall_status");
        statement.executeUpdate("delete from study_protocol");
        statement.executeUpdate("delete from disease");
        statement.executeUpdate("delete from healthcare_facility");
        statement.executeUpdate("delete from organization");
        primeData();
    }

    /**
     * @param <T> t
     * @param obj o
     */
    public static <T> void addUpdObject(T obj) {
        Session session = AccrualHibernateUtil.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(obj);
        transaction.commit();
    }

    /**
     *
     * @param <T> t
     * @param oList o
     */
    public static <T> void addUpdObjects(List<T> oList) {
        for (T obj : oList) {
            addUpdObject(obj);
        }
    }

    public static void primeData() {
        diseases = new ArrayList<Disease>();
        studyProtocols = new ArrayList<StudyProtocol>();
        studyOverallStatuses = new ArrayList<StudyOverallStatus>();
        submissions = new ArrayList<Submission>();
        studySites = new ArrayList<StudySite>();
        studyDiseases = new ArrayList<StudyDisease>();
        patients = new ArrayList<Patient>();
        studySubjects = new ArrayList<StudySubject>();
        performedSubjectMilestones = new ArrayList<PerformedSubjectMilestone>();
        healthCareFacilities = new ArrayList<HealthCareFacility>();
        organizations = new ArrayList<Organization>();
        studySiteAccrualAccess = new ArrayList<StudySiteAccrualAccess>();
        countries = new ArrayList<Country>();
        performedObservations = new ArrayList<PerformedObservation>();
        performedImagings = new ArrayList<PerformedImaging>();
        performedSubstanceAdministrations = new ArrayList<PerformedSubstanceAdministration>();
        performedRadiationAdministrations = new ArrayList<PerformedRadiationAdministration>();
        performedProcedures = new ArrayList<PerformedProcedure>();
        performedDiagnosis = new ArrayList<PerformedDiagnosis>();
        performedImages = new ArrayList<PerformedImage>();
        performedHistopathologies = new ArrayList<PerformedHistopathology>();
        performedClinicalResults = new  ArrayList<PerformedClinicalResult>();
        performedMedicalHistoryResults = new ArrayList<PerformedMedicalHistoryResult>();
        performedLesionDescriptions = new ArrayList<PerformedLesionDescription>();
        plannedActivities = new ArrayList<PlannedActivity>();
        performedActivities = new ArrayList<PerformedActivity>();
        activityRelationships = new ArrayList<ActivityRelationship>();

        // Organization
        Organization org = new Organization();
        org.setCity("city");
        org.setCountryName("country name");
        org.setIdentifier("po org id");
        org.setName("orga name");
        org.setPostalCode("12345");
        org.setState("MD");
        org.setStatusCode(EntityStatusCode.ACTIVE);
        addUpdObject(org);
        organizations.add(org);

        org = new Organization();
        org.setCity("city2");
        org.setCountryName("country name2");
        org.setIdentifier("po org id2");
        org.setName("orga name2");
        org.setPostalCode("22345");
        org.setState("MD");
        org.setStatusCode(EntityStatusCode.ACTIVE);
        addUpdObject(org);
        organizations.add(org);

        // HealthcareFacility
        HealthCareFacility hcf = new HealthCareFacility();
        hcf.setIdentifier("po hcf id");
        hcf.setOrganization(organizations.get(0));
        hcf.setStatusCode(StructuralRoleStatusCode.ACTIVE);
        hcf.setStatusDateRangeLow(new Timestamp(new Date().getTime()));
        addUpdObject(hcf);
        healthCareFacilities.add(hcf);

        hcf = new HealthCareFacility();
        hcf.setIdentifier("po hcf id1");
        hcf.setOrganization(organizations.get(1));
        hcf.setStatusCode(StructuralRoleStatusCode.ACTIVE);
        hcf.setStatusDateRangeLow(new Timestamp(new Date().getTime()));
        addUpdObject(hcf);
        healthCareFacilities.add(hcf);

        // StudyProtocol
        StudyProtocol sp = new StudyProtocol();
        sp.setOfficialTitle("Phase II study for Melanoma");
        sp.setStartDate(PAUtil.dateStringToTimestamp("1/1/2000"));
        sp.setStartDateTypeCode(ActualAnticipatedTypeCode.ACTUAL);
        sp.setPrimaryCompletionDate(PAUtil.dateStringToTimestamp("12/31/2009"));
        sp.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode.ANTICIPATED);
        sp.setAccrualReportingMethodCode(AccrualReportingMethodCode.ABBREVIATED);
        sp.setIdentifier("NCI-2009-00001");
        sp.setStatusCode(ActStatusCode.ACTIVE);
        sp.setSubmissionNumber(Integer.valueOf(1));
        sp.setProprietaryTrialIndicator(false);
        addUpdObject(sp);
        studyProtocols.add(sp);

        sp = new StudyProtocol();
        sp.setOfficialTitle("A Phase II/III Randomized, Placebo-Controlled Double-Blind Clinical Trial of Ginger");
        sp.setStartDate(PAUtil.dateStringToTimestamp("1/1/2009"));
        sp.setStartDateTypeCode(ActualAnticipatedTypeCode.ACTUAL);
        sp.setPrimaryCompletionDate(PAUtil.dateStringToTimestamp("12/31/2010"));
        sp.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode.ANTICIPATED);
        sp.setAccrualReportingMethodCode(AccrualReportingMethodCode.ABBREVIATED);
        sp.setIdentifier("NCI-2009-00002");
        sp.setStatusCode(ActStatusCode.INACTIVE);
        sp.setSubmissionNumber(Integer.valueOf(1));
        sp.setProprietaryTrialIndicator(false);
        addUpdObject(sp);
        studyProtocols.add(sp);

        sp = new StudyProtocol();
        sp.setOfficialTitle("A Phase II/III Randomized, Placebo-Controlled Double-Blind Clinical Trial of Ginger");
        sp.setStartDate(PAUtil.dateStringToTimestamp("1/1/2009"));
        sp.setStartDateTypeCode(ActualAnticipatedTypeCode.ACTUAL);
        sp.setPrimaryCompletionDate(PAUtil.dateStringToTimestamp("12/31/2010"));
        sp.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode.ANTICIPATED);
        sp.setAccrualReportingMethodCode(AccrualReportingMethodCode.ABBREVIATED);
        sp.setIdentifier("NCI-2009-00002");
        sp.setStatusCode(ActStatusCode.ACTIVE);
        sp.setSubmissionNumber(Integer.valueOf(2));
        sp.setProprietaryTrialIndicator(false);
        addUpdObject(sp);
        studyProtocols.add(sp);

        // StudyOverallStatus
        StudyOverallStatus sos = new StudyOverallStatus();
        sos.setStatusCode(StudyStatusCode.APPROVED);
        sos.setStatusDate(PAUtil.dateStringToTimestamp("6/1/2009"));
        sos.setStudyProtocol(studyProtocols.get(0));
        addUpdObject(sos);
        studyOverallStatuses.add(sos);

        sos = new StudyOverallStatus();
        sos.setStatusCode(StudyStatusCode.ACTIVE);
        sos.setStatusDate(PAUtil.dateStringToTimestamp("6/15/2009"));
        sos.setStudyProtocol(studyProtocols.get(0));
        addUpdObject(sos);
        studyOverallStatuses.add(sos);

        // Disease
        Disease disease = new Disease();
        disease.setDiseaseCode("code");
        disease.setMenuDisplayName("menu name");
        disease.setNtTermIdentifier("NT term");
        disease.setPreferredName("name");
        disease.setStatusCode(ActiveInactivePendingCode.ACTIVE);
        disease.setStatusDateRangeLow(PAUtil.dateStringToTimestamp("1/1/2000"));
        addUpdObject(disease);
        diseases.add(disease);

        // Submission
        Submission sub = new Submission();
        sub.setCutOffDate(PAUtil.dateStringToTimestamp("6/3/2009"));
        sub.setDescription("description");
        sub.setLabel("label");
        sub.setStatusCode(AccrualSubmissionStatusCode.SUBMITTED);
        sub.setStatusDateRangeHigh(null);
        sub.setStatusDateRangeLow(PAUtil.dateStringToTimestamp("7/15/2009"));
        sub.setStudyProtocol(studyProtocols.get(0));
        addUpdObject(sub);
        submissions.add(sub);

        // StudySite
        StudySite ss = new StudySite();
        ss.setLocalStudyProtocolIdentifier("T1 Local SP 001");
        ss.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        ss.setFunctionalCode(StudySiteFunctionalCode.LEAD_ORGANIZATION);
        ss.setStudyProtocol(studyProtocols.get(0));
        addUpdObject(ss);
        studySites.add(ss);

        ss = new StudySite();
        ss.setLocalStudyProtocolIdentifier("T1 Local SP 001");
        ss.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        ss.setFunctionalCode(StudySiteFunctionalCode.TREATING_SITE);
        ss.setHealthCareFacility(healthCareFacilities.get(0));
        ss.setStudyProtocol(studyProtocols.get(0));
        addUpdObject(ss);
        studySites.add(ss);

        ss = new StudySite();
        ss.setLocalStudyProtocolIdentifier("T1 Local SP 002");
        ss.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        ss.setFunctionalCode(StudySiteFunctionalCode.TREATING_SITE);
        ss.setHealthCareFacility(healthCareFacilities.get(1));
        ss.setStudyProtocol(studyProtocols.get(0));
        addUpdObject(ss);
        studySites.add(ss);

        ss = new StudySite();
        ss.setLocalStudyProtocolIdentifier("T2 Local SP 001");
        ss.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        ss.setFunctionalCode(StudySiteFunctionalCode.LEAD_ORGANIZATION);
        ss.setStudyProtocol(studyProtocols.get(1));
        addUpdObject(ss);
        studySites.add(ss);

        ss = new StudySite();
        ss.setLocalStudyProtocolIdentifier("T2 Local SP 001");
        ss.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        ss.setFunctionalCode(StudySiteFunctionalCode.TREATING_SITE);
        ss.setHealthCareFacility(healthCareFacilities.get(0));
        ss.setStudyProtocol(studyProtocols.get(1));
        addUpdObject(ss);
        studySites.add(ss);

        // Outcomes SP
        outcomesSpId = studyProtocols.size();
        sp = new StudyProtocol();
        sp.setIdentifier("Outcomes");
        sp.setSubmissionNumber(1);
        addUpdObject(sp);
        studyProtocols.add(sp);

        outcomesSsId = studySites.size();
        ss = new StudySite();
        ss.setLocalStudyProtocolIdentifier("Outcomes");
        ss.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        ss.setFunctionalCode(StudySiteFunctionalCode.TREATING_SITE);
        ss.setHealthCareFacility(healthCareFacilities.get(0));
        ss.setStudyProtocol(sp);
        addUpdObject(ss);
        studySites.add(ss);



        // StudySiteAccrualAccess
        StudySiteAccrualAccess ssaa = new StudySiteAccrualAccess();
        ssaa.setCsmUserId(MockCsmUtil.users.get(0).getUserId());
        ssaa.setStudySite(studySites.get(1));
        ssaa.setStatusCode(ActiveInactiveCode.ACTIVE);
        ssaa.setStatusDateRangeLow(new Timestamp(new Date().getTime()));
        addUpdObject(ssaa);
        studySiteAccrualAccess.add(ssaa);

        ssaa = new StudySiteAccrualAccess();
        ssaa.setCsmUserId(MockCsmUtil.users.get(0).getUserId());
        ssaa.setStudySite(studySites.get(2));
        ssaa.setStatusCode(ActiveInactiveCode.ACTIVE);
        ssaa.setStatusDateRangeLow(new Timestamp(new Date().getTime()));
        addUpdObject(ssaa);
        studySiteAccrualAccess.add(ssaa);

        ssaa = new StudySiteAccrualAccess();
        ssaa.setCsmUserId(MockCsmUtil.users.get(0).getUserId());
        ssaa.setStudySite(studySites.get(4));
        ssaa.setStatusCode(ActiveInactiveCode.ACTIVE);
        ssaa.setStatusDateRangeLow(new Timestamp(new Date().getTime()));
        addUpdObject(ssaa);
        studySiteAccrualAccess.add(ssaa);

        ssaa = new StudySiteAccrualAccess();
        ssaa.setCsmUserId(MockCsmUtil.users.get(1).getUserId());
        ssaa.setStudySite(studySites.get(1));
        ssaa.setStatusCode(ActiveInactiveCode.ACTIVE);
        ssaa.setStatusDateRangeLow(new Timestamp(new Date().getTime()));
        addUpdObject(ssaa);
        studySiteAccrualAccess.add(ssaa);

        // StudyDisease
        StudyDisease sd = new StudyDisease();
        sd.setDisease(diseases.get(0));
        sd.setLeadDiseaseIndicator(true);
        sd.setStudyProtocol(studyProtocols.get(0));
        addUpdObject(sd);
        studyDiseases.add(sd);

        // Patient
        Patient p = new Patient();
        p.setBirthDate(PAUtil.dateStringToTimestamp("7/11/1963"));
        p.setCountryIdentifier(new Long(100));
        p.setEthnicCode(PatientEthnicityCode.HISPANIC);
        p.setIdentifier("PO PATIENT ID 01");
        p.setPersonIdentifier("PO PERSON ID 01");
        p.setRaceCode(PatientRaceCode.AMERICAN_INDIAN.getName());
        p.setSexCode(PatientGenderCode.FEMALE);
        p.setStatusCode(StructuralRoleStatusCode.ACTIVE);
        p.setStatusDateRangeLow(PAUtil.dateStringToTimestamp("1/1/2009"));
        p.setZip("TX");
        addUpdObject(p);
        patients.add(p);

        p = new Patient();
        p.setBirthDate(PAUtil.dateStringToTimestamp("5/10/1963"));
        p.setCountryIdentifier(new Long(100));
        p.setEthnicCode(PatientEthnicityCode.NOT_HISPANIC);
        p.setIdentifier("PO PATIENT ID 02");
        p.setPersonIdentifier("PO PERSON ID 02");
        p.setRaceCode(PatientRaceCode.WHITE.getName());
        p.setSexCode(PatientGenderCode.MALE);
        p.setStatusCode(StructuralRoleStatusCode.ACTIVE);
        p.setStatusDateRangeLow(PAUtil.dateStringToTimestamp("1/1/2009"));
        p.setZip("TX");
        addUpdObject(p);
        patients.add(p);

        p = new Patient();
        p.setBirthDate(PAUtil.dateStringToTimestamp("8/11/1963"));
        p.setCountryIdentifier(new Long(100));
        p.setEthnicCode(PatientEthnicityCode.NOT_HISPANIC);
        p.setIdentifier("PO PATIENT ID 03");
        p.setPersonIdentifier("PO PERSON ID 03");
        p.setRaceCode(PatientRaceCode.WHITE.getName());
        p.setSexCode(PatientGenderCode.FEMALE);
        p.setStatusCode(StructuralRoleStatusCode.ACTIVE);
        p.setStatusDateRangeLow(PAUtil.dateStringToTimestamp("1/1/2009"));
        p.setZip("TX");
        addUpdObject(p);
        patients.add(p);

        p = new Patient();
        p.setBirthDate(PAUtil.dateStringToTimestamp("1/3/1960"));
        p.setCountryIdentifier(new Long(100));
        p.setEthnicCode(PatientEthnicityCode.NOT_REPORTED);
        p.setIdentifier("PO PATIENT ID 04");
        p.setPersonIdentifier("PO PERSON ID 04");
        p.setRaceCode(PatientRaceCode.NOT_REPORTED.getName());
        p.setSexCode(PatientGenderCode.MALE);
        p.setStatusCode(StructuralRoleStatusCode.ACTIVE);
        p.setStatusDateRangeLow(PAUtil.dateStringToTimestamp("1/1/2009"));
        p.setZip("TX");
        addUpdObject(p);
        patients.add(p);

        p = new Patient();
        p.setBirthDate(PAUtil.dateStringToTimestamp("9/7/1968"));
        p.setCountryIdentifier(new Long(100));
        p.setEthnicCode(PatientEthnicityCode.UNKNOWN);
        p.setIdentifier("PO PATIENT ID 05");
        p.setPersonIdentifier("PO PERSON ID 05");
        p.setRaceCode(PatientRaceCode.UNKNOWN.getName());
        p.setSexCode(PatientGenderCode.FEMALE);
        p.setStatusCode(StructuralRoleStatusCode.ACTIVE);
        p.setStatusDateRangeLow(PAUtil.dateStringToTimestamp("1/1/2009"));
        p.setZip("TX");
        addUpdObject(p);
        patients.add(p);

        // StudySubject
        StudySubject subj = new StudySubject();
        subj.setDisease(diseases.get(0));
        subj.setPatient(patients.get(0));
        subj.setPaymentMethodCode(PaymentMethodCode.MEDICARE);
        subj.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        subj.setStatusDateRangeLow(PAUtil.dateStringToTimestamp("1/1/2009"));
        subj.setStudyProtocol(studyProtocols.get(0));
        subj.setStudySite(studySites.get(0));
        addUpdObject(subj);
        studySubjects.add(subj);

        // PerformedSubjectMilestone
        PerformedSubjectMilestone m = new PerformedSubjectMilestone();
        m.setCategoryCode(ActivityCategoryCode.OTHER);
        m.setInformedConsentDate(PAUtil.dateStringToTimestamp("6/13/2009"));
        m.setStudyProtocol(studyProtocols.get(0));
        addUpdObject(m);
        performedSubjectMilestones.add(m);

        Country c =  new Country();
        c.setAlpha2("US");
        c.setAlpha3("USA");
        addUpdObject(c);
        countries.add(c);

        // PerformedObservation
        PerformedObservation performedObservation = new PerformedObservation();
        performedObservation.setMethodCode("methodCode");
        performedObservation.setTargetSiteCode("targetSiteCode");
        performedObservation.setStudyProtocol(studyProtocols.get(0));
        addUpdObject(performedObservation);
        performedObservations.add(performedObservation);

        // PerformedImaging
        PerformedImaging pimaging = new PerformedImaging();
        pimaging.setStudyProtocol(studyProtocols.get(0));
        pimaging.setContrastAgentEnhancementIndicator(true);
        addUpdObject(pimaging);
        performedImagings.add(pimaging);

        // PerformedSubstanceAdministration
        PerformedSubstanceAdministration psa = new PerformedSubstanceAdministration();
        psa.setStudyProtocol(studyProtocols.get(0));
        psa.setDoseValue(new BigDecimal("2"));
        psa.setDoseUnit("10Milligrams");
        psa.setDoseDescription("TestDose");
        psa.setDoseFormCode("Tablet");
        psa.setDoseFrequencyCode("BID");
        psa.setDoseRegimen("doseRegimen");
        psa.setDoseTotalUnit("doseTotalUom");
        psa.setDoseTotalValue(new BigDecimal("5"));
        psa.setRouteOfAdministrationCode("Oral");
        psa.setCategoryCode(ActivityCategoryCode.SUBSTANCE_ADMINISTRATION);
        addUpdObject(psa);
        performedSubstanceAdministrations.add(psa);

        // PerformedRadiationAdministration
        PerformedRadiationAdministration pra = new PerformedRadiationAdministration();
        pra.setStudyProtocol(studyProtocols.get(0));
        pra.setMachineTypeCode("machineTypeCode");
        addUpdObject(pra);
        performedRadiationAdministrations.add(pra);

         // PerformedProcedure
        PerformedProcedure pp = new PerformedProcedure();
        pp.setStudyProtocol(studyProtocols.get(0));
        pp.setCategoryCode(ActivityCategoryCode.OTHER);
        pp.setTextDescription("SurgeryDescription");
        addUpdObject(pp);
        performedProcedures.add(pp);

        // PerformedDiagnosis
        PerformedDiagnosis pd = new PerformedDiagnosis();
        pd.setResultCode("PerformedDiagnosis");
        pd.setResultDateRangeLow(PAUtil.dateStringToTimestamp("11/06/2009"));
        pd.setStudyProtocol(studyProtocols.get(0));
        pd.setPerformedObservation(performedObservation);
        addUpdObject(pd);
        performedDiagnosis.add(pd);

        // PerformedImage
        PerformedImage pi = new PerformedImage();
        pi.setResultCode("PerformedImage");
        pi.setResultDateRangeLow(PAUtil.dateStringToTimestamp("11/06/2009"));
        pi.setStudyProtocol(studyProtocols.get(0));
        pi.setPerformedObservation(performedObservation);
        addUpdObject(pi);
        performedImages.add(pi);

        // PerformedHistopathology
        PerformedHistopathology ph = new PerformedHistopathology();
        ph.setGradeCode("GradeCode");
        ph.setDescription("description");
        ph.setStudyProtocol(studyProtocols.get(0));
        ph.setPerformedObservation(performedObservation);
        addUpdObject(ph);
        performedHistopathologies.add(ph);

        // PerformedClinicalResult
        PerformedClinicalResult pcr = new PerformedClinicalResult();
        pcr.setStageCodingSystem("StageCodingSystem");
        pcr.setResultQuantityValue(new BigDecimal(1));
        pcr.setResultQuantityUnit("Year");
        pcr.setStudyProtocol(studyProtocols.get(0));
        pcr.setPerformedObservation(performedObservation);
        addUpdObject(pcr);
        performedClinicalResults.add(pcr);

        // PerformedMedicalHistoryResult
        PerformedMedicalHistoryResult pmhr = new PerformedMedicalHistoryResult();
        pmhr.setTypeCode("PriorTherapy");
        pmhr.setDescription("PriorTherapy Description");
        pmhr.setResultQuantityValue(new BigDecimal(2));
        pmhr.setResultQuantityUnit("Unitary");
        pmhr.setStudyProtocol(studyProtocols.get(0));
        pmhr.setPerformedObservation(performedObservation);
        addUpdObject(pmhr);
        performedMedicalHistoryResults.add(pmhr);

        // PerformedLesionDescription
        PerformedLesionDescription pld = new PerformedLesionDescription();
        pld.setResultCode("PerformedLesionDescription");
        pld.setLesionNumber(new Integer(1));
        pld.setResultDateRangeLow(PAUtil.dateStringToTimestamp("11/06/2009"));
        pld.setStudyProtocol(studyProtocols.get(0));
        pld.setPerformedObservation(performedObservation);
        addUpdObject(pld);
        performedLesionDescriptions.add(pld);

        PlannedActivity pa = new PlannedActivity();
        pa.setCategoryCode(ActivityCategoryCode.OTHER);
        pa.setTextDescription("PlannedActivity");
        pa.setStudyProtocol(studyProtocols.get(0));
        addUpdObject(pa);
        plannedActivities.add(pa);

        PerformedActivity pera = new PerformedActivity();
        pera.setCategoryCode(ActivityCategoryCode.OTHER);
        pera.setTextDescription("PerformedActivity");
        pera.setStudyProtocol(studyProtocols.get(0));
        addUpdObject(pera);
        performedActivities.add(pera);

        ActivityRelationship ar = new ActivityRelationship();
        ar.setTypeCode("ActivityRelationship");
        ar.setPlannedActivity(pa);
        ar.setPerformedActivity(pera);
        addUpdObject(ar);
        activityRelationships.add(ar);
    }

}
