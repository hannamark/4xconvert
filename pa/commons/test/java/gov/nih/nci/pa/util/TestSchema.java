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

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.Arm;
import gov.nih.nci.pa.domain.ClinicalResearchStaff;
import gov.nih.nci.pa.domain.ClinicalResearchStaffTest;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.CountryTest;
import gov.nih.nci.pa.domain.Disease;
import gov.nih.nci.pa.domain.DiseaseAltername;
import gov.nih.nci.pa.domain.DiseaseAlternameTest;
import gov.nih.nci.pa.domain.DiseaseParent;
import gov.nih.nci.pa.domain.DiseaseParentTest;
import gov.nih.nci.pa.domain.DiseaseTest;
import gov.nih.nci.pa.domain.Document;
import gov.nih.nci.pa.domain.DocumentWorkFlowStatusTest;
import gov.nih.nci.pa.domain.DocumentWorkflowStatus;
import gov.nih.nci.pa.domain.HealthCareFacility;
import gov.nih.nci.pa.domain.HealthCareFacilityTest;
import gov.nih.nci.pa.domain.HealthCareProvider;
import gov.nih.nci.pa.domain.HealthCareProviderTest;
import gov.nih.nci.pa.domain.Intervention;
import gov.nih.nci.pa.domain.InterventionAlternateName;
import gov.nih.nci.pa.domain.InterventionalStudyProtocol;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.OrganizationTest;
import gov.nih.nci.pa.domain.OversightCommittee;
import gov.nih.nci.pa.domain.PAProperties;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.domain.PersonTest;
import gov.nih.nci.pa.domain.PlannedActivity;
import gov.nih.nci.pa.domain.PlannedEligibilityCriterion;
import gov.nih.nci.pa.domain.PlannedMarker;
import gov.nih.nci.pa.domain.PlannedSubstanceAdministration;
import gov.nih.nci.pa.domain.RegulatoryAuthority;
import gov.nih.nci.pa.domain.ResearchOrganization;
import gov.nih.nci.pa.domain.StratumGroup;
import gov.nih.nci.pa.domain.StudyCheckout;
import gov.nih.nci.pa.domain.StudyContact;
import gov.nih.nci.pa.domain.StudyContactTest;
import gov.nih.nci.pa.domain.StudyDisease;
import gov.nih.nci.pa.domain.StudyDiseaseTest;
import gov.nih.nci.pa.domain.StudyIndlde;
import gov.nih.nci.pa.domain.StudyMilestone;
import gov.nih.nci.pa.domain.StudyMilestoneTest;
import gov.nih.nci.pa.domain.StudyOutcomeMeasure;
import gov.nih.nci.pa.domain.StudyOverallStatus;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyProtocolTest;
import gov.nih.nci.pa.domain.StudyRecruitmentStatus;
import gov.nih.nci.pa.domain.StudyRecruitmentStatusTest;
import gov.nih.nci.pa.domain.StudyRegulatoryAuthority;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.domain.StudySiteAccrualStatus;
import gov.nih.nci.pa.domain.StudySiteContact;
import gov.nih.nci.pa.domain.StudySiteTest;
import gov.nih.nci.pa.enums.AccrualReportingMethodCode;
import gov.nih.nci.pa.enums.ActStatusCode;
import gov.nih.nci.pa.enums.ActiveInactiveCode;
import gov.nih.nci.pa.enums.ActiveInactivePendingCode;
import gov.nih.nci.pa.enums.ActivityCategoryCode;
import gov.nih.nci.pa.enums.ActivitySubcategoryCode;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.ArmTypeCode;
import gov.nih.nci.pa.enums.AssayPurposeCode;
import gov.nih.nci.pa.enums.AssayTypeCode;
import gov.nih.nci.pa.enums.AssayUseCode;
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
import gov.nih.nci.pa.enums.TissueCollectionMethodCode;
import gov.nih.nci.pa.enums.TissueSpecimenTypeCode;
import gov.nih.nci.pa.enums.UnitsCode;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Hugh
 *
 */
public class TestSchema {
        public static List<Long> studyProtocolIds;
        public static List<Long> studySiteIds;
        public static List<Long> studySiteContactIds;
        public static List<Long> healthCareFacilityIds;
        public static List<Long> healthCareProviderIds;
        public static List<Long> clinicalResearchStaffIds;
        public static List<Long> plannedActivityIds;
        public static List<Long> interventionIds;
        public static List<Long> armIds;
        public static List<Long> researchOrganizationIds;
        public static List<Long> oversightCommitteeIds;
        public static List<Long> diseaseIds;
        public static List<Long> outcomeIds;
        public static List<Long> regAuthIds;
        public static List<Long> personIds;
        public static List<Country> countries;

        private static CtrpHibernateHelper testHelper = new TestHibernateHelper();

        private static User user;

        /**
         *
         */
        public static void reset() {
            // clean up HQLDB schema
            HibernateUtil.setTestHelper(testHelper);
            Session session = HibernateUtil.getCurrentSession();
            session.clear();
            try {
                Connection connection = session.connection();
                try {
                    Statement statement = connection.createStatement();
                    statement.executeUpdate("delete from submission");
                    statement.executeUpdate("delete from study_site_accrual_access");
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
                    statement.executeUpdate("delete from PLANNED_MARKER");
                    statement.executeUpdate("delete from PLANNED_PROCEDURE");
                    statement.executeUpdate("delete from PLANNED_ELIGIBILITY_CRITERION");
                    statement.executeUpdate("delete from PLANNED_SUBSTANCE_ADMINISTRATION");
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
                    statement.executeUpdate("delete from STUDY_OTHERIDENTIFIERS");
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
                    statement.executeUpdate("delete from STUDY_PROTOCOL_STAGE");
                    connection.commit();
                    statement.close();
                } catch (HibernateException e) {
                    connection.rollback();
                } catch (SQLException e) {
                    connection.rollback();
                }
            } catch (SQLException e) {

            } finally {
                session.clear();
            }
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
            countries = new ArrayList<Country>();

            User curator = getUser();
            addUpdObject(curator);

            StudyProtocol sp = new InterventionalStudyProtocol();
            sp.setOfficialTitle("cancer for THOLA");
            sp.setStartDate(ISOUtil.dateStringToTimestamp("1/1/2000"));
            sp.setStartDateTypeCode(ActualAnticipatedTypeCode.ACTUAL);
            sp.setPrimaryCompletionDate(ISOUtil.dateStringToTimestamp("12/31/2009"));
            sp.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode.ANTICIPATED);
            sp.setAccrualReportingMethodCode(AccrualReportingMethodCode.ABBREVIATED);
            sp.setStatusCode(ActStatusCode.ACTIVE);
            Set<Ii> studySecondaryIdentifiers =  new HashSet<Ii>();
            Ii spSecId = new Ii();
            spSecId.setExtension("NCI-2009-00001");
            spSecId.setRoot(IiConverter.STUDY_PROTOCOL_ROOT);
            studySecondaryIdentifiers.add(spSecId);
            sp.setOtherIdentifiers(studySecondaryIdentifiers);
            sp.setSubmissionNumber(Integer.valueOf(1));
            sp.setProprietaryTrialIndicator(Boolean.FALSE);
            sp.setCtgovXmlRequiredIndicator(Boolean.TRUE);
            addUpdObject(sp);
            sp.setId(sp.getId());
            studyProtocolIds.add(sp.getId());

            StudyOverallStatus sos = new StudyOverallStatus();
            sos.setStatusCode(StudyStatusCode.APPROVED);
            sos.setStatusDate(ISOUtil.dateStringToTimestamp("8/1/2008"));
            sos.setStudyProtocol(sp);
            addUpdObject(sos);
            sos = new StudyOverallStatus();
            sos.setStatusCode(StudyStatusCode.ACTIVE);
            sos.setStatusDate(ISOUtil.dateStringToTimestamp("8/15/2008"));
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
            sPart.setStatusDateRangeLow(ISOUtil.dateStringToTimestamp("6/1/2008"));
            sPart.setStudyProtocol(sp);
            addUpdObject(sPart);
            studySiteIds.add(sPart.getId());

            Country country = new Country();
            country.setAlpha2("ZZ");
            country.setAlpha3("ZZZ");
            country.setName("Zanzibar");
            country.setNumeric("67");
            addUpdObject(country);
            countries.add(country);

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
            spc.setStatusDateRangeLow(ISOUtil.dateStringToTimestamp("1/15/2008"));
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
            sg.setUserLastUpdated(curator);
            addUpdObject(sg);
            sg = new StratumGroup();
            sg.setStudyProtocol(sp);
            sg.setDescription("Description2");
            sg.setGroupNumberText("Code2");
            sg.setUserLastUpdated(curator);
            addUpdObject(sg);

            Intervention inv = new Intervention();
            inv.setName("Chocolate Bar");
            inv.setDescriptionText("Oral intervention to improve morale");
            inv.setDateLastUpdated(new Date());
            inv.setStatusCode(ActiveInactivePendingCode.ACTIVE);
            inv.setStatusDateRangeLow(ISOUtil.dateStringToTimestamp("1/1/2000"));
            inv.setTypeCode(InterventionTypeCode.DIETARY_SUPPLEMENT);
            inv.setUserLastUpdated(curator);
            addUpdObject(inv);
            interventionIds.add(inv.getId());

            InterventionAlternateName invo = new InterventionAlternateName();
            invo.setDateLastUpdated(new Date());
            invo.setIntervention(inv);
            invo.setName("Hershey");
            invo.setStatusCode(ActiveInactiveCode.ACTIVE);
            invo.setStatusDateRangeLow(ISOUtil.dateStringToTimestamp("1/1/2000"));
            invo.setUserLastUpdated(curator);
            invo.setNameTypeCode("synonym");
            addUpdObject(invo);
            invo = new InterventionAlternateName();
            invo.setDateLastUpdated(new Date());
            invo.setIntervention(inv);
            invo.setName("Nestle");
            invo.setStatusCode(ActiveInactiveCode.ACTIVE);
            invo.setStatusDateRangeLow(ISOUtil.dateStringToTimestamp("1/1/2000"));
            invo.setUserLastUpdated(curator);
            invo.setNameTypeCode("synonym");
            addUpdObject(invo);

            PlannedActivity pa = new PlannedActivity();
            pa.setCategoryCode(ActivityCategoryCode.INTERVENTION);
            pa.setDateLastUpdated(new Date());
            pa.setIntervention(inv);
            pa.setLeadProductIndicator(true);
            pa.setStudyProtocol(sp);
            pa.setSubcategoryCode(ActivitySubcategoryCode.DIETARY_SUPPLEMENT.getCode());
            pa.setUserLastUpdated(curator);
            addUpdObject(pa);
            plannedActivityIds.add(pa.getId());

            StudyOutcomeMeasure som = new StudyOutcomeMeasure();
            som.setName("StudyOutcomeMeasure");
            som.setStudyProtocol(sp);
            som.setPrimaryIndicator(Boolean.TRUE);
            som.setUserLastUpdated(curator);
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
            arm.setUserLastCreated(curator);
            arm.setUserLastUpdated(curator);
            arm.getInterventions().add(pa);
            addUpdObject(arm);
            armIds.add(arm.getId());

            DocumentWorkflowStatus dws = new DocumentWorkflowStatus();
            dws.setStudyProtocol(sp);
            dws.setStatusCode(DocumentWorkflowStatusCode.ABSTRACTED);
            dws.setCommentText("Comment Text1");
            dws.setUserLastUpdated(curator);
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
            pec.setCategoryCode(ActivityCategoryCode.ELIGIBILITY_CRITERION);
            pec.setCriterionName("WHC");
            pec.setInclusionIndicator(Boolean.TRUE);
            pec.setOperator(">");
            pec.setStudyProtocol(sp);
            pec.setMinValue(new BigDecimal("12"));
            pec.setMinUnit(UnitsCode.MONTHS.getCode());
            pec.setMaxValue(new BigDecimal("24"));
            pec.setMaxUnit(UnitsCode.MONTHS.getCode());
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
            StudyMilestone studyMilestonetss1 = StudyMilestoneTest.createTrialSummarySentStudyMilestoneObj(sp);
            addUpdObject(studyMilestonetss1);
            StudyMilestone studyMilestonetss2 = StudyMilestoneTest.createTrialSummarySentStudyMilestoneObjFiveDays(sp);
            addUpdObject(studyMilestonetss2);
            RegulatoryAuthority rega = new RegulatoryAuthority();
            rega.setCountry(country);
            rega.setAuthorityName("Authority");
            addUpdObject(rega);
            regAuthIds.add(rega.getId());

            StudyCheckout scheckout = new StudyCheckout();
            scheckout.setStudyProtocol(sp);
            scheckout.setUserIdentifier("Abstractor");
            addUpdObject(scheckout);

            PlannedSubstanceAdministration psa = new PlannedSubstanceAdministration();
            psa.setDoseMinValue(new BigDecimal("2"));
            psa.setDoseMinUnit("10Milligrams");
            psa.setDoseMaxValue(new BigDecimal("4"));
            psa.setDoseMaxUnit("15Milligrams");
            psa.setDoseDescription("TestDose");
            psa.setDoseFormCode("Tablet");
            psa.setDoseFrequencyCode("BID");
            psa.setDoseRegimen("doseRegimen");
            psa.setDoseTotalMinUnit("doseTotalUom");
            psa.setDoseTotalMinValue(new BigDecimal("5"));
            psa.setRouteOfAdministrationCode("Oral");
            psa.setCategoryCode(ActivityCategoryCode.SUBSTANCE_ADMINISTRATION);
            psa.setStudyProtocol(sp);
            addUpdObject(psa);

            PlannedMarker marker01 = new PlannedMarker();
            marker01.setStudyProtocol(sp);
            marker01.setName("Marker #1");
            marker01.setAssayTypeCode(AssayTypeCode.PCR);
            marker01.setAssayUseCode(AssayUseCode.RESEARCH);
            marker01.setAssayPurposeCode(AssayPurposeCode.RESEARCH);
            marker01.setTissueCollectionMethodCode(TissueCollectionMethodCode.MANDATORY);
            marker01.setTissueSpecimenTypeCode(TissueSpecimenTypeCode.PLASMA);
            marker01.setStatusCode(ActiveInactivePendingCode.PENDING);
            addUpdObject(marker01);

            PlannedMarker marker02 = new PlannedMarker();
            marker02.setStudyProtocol(sp);
            marker02.setName("Marker #2");
            marker02.setAssayTypeCode(AssayTypeCode.OTHER);
            marker02.setAssayTypeOtherText("Assay Type Other Text");
            marker02.setAssayUseCode(AssayUseCode.RESEARCH);
            marker02.setAssayPurposeCode(AssayPurposeCode.OTHER);
            marker02.setAssayPurposeOtherText("Assay Purpose Other Text");
            marker02.setTissueCollectionMethodCode(TissueCollectionMethodCode.MANDATORY);
            marker02.setTissueSpecimenTypeCode(TissueSpecimenTypeCode.PLASMA);
            marker02.setStatusCode(ActiveInactivePendingCode.PENDING);
            addUpdObject(marker02);

            HibernateUtil.getCurrentSession().clear();
        }
        public static Ii nonPropTrialData() {
            Organization o = OrganizationTest.createOrganizationObj();
            TestSchema.addUpdObject(o);
            Person p = PersonTest.createPersonObj();
            p.setIdentifier("11");
            TestSchema.addUpdObject(p);
            HealthCareFacility hcf = HealthCareFacilityTest.createHealthCareFacilityObj(o);
            TestSchema.addUpdObject(hcf);

            HealthCareProvider hcp = HealthCareProviderTest.createHealthCareProviderObj(p, o);
            TestSchema.addUpdObject(hcp);

            Country c = CountryTest.createCountryObj();
            TestSchema.addUpdObject(c);

            ClinicalResearchStaff crs = ClinicalResearchStaffTest.createClinicalResearchStaffObj(o, p);
            TestSchema.addUpdObject(crs);


            ResearchOrganization ro = new ResearchOrganization();
            ro.setOrganization(o);
            ro.setStatusCode(StructuralRoleStatusCode.ACTIVE);
            ro.setIdentifier("abc");
            TestSchema.addUpdObject(ro);

            StudyProtocol nonpropTrial = new StudyProtocol();
            StudyProtocolTest.createStudyProtocolObj(nonpropTrial);
            nonpropTrial.setCtgovXmlRequiredIndicator(Boolean.FALSE);
            TestSchema.addUpdObject(nonpropTrial);
            IiConverter.convertToIi(nonpropTrial.getId());

            StudyContact sc = StudyContactTest.createStudyContactObj(nonpropTrial, c, hcp, crs);
            TestSchema.addUpdObject(sc);

            StudySite spc = StudySiteTest.createStudySiteObj(nonpropTrial, hcf);
            spc.setResearchOrganization(ro);
            TestSchema.addUpdObject(spc);

            StudyRecruitmentStatus studyRecStatus = StudyRecruitmentStatusTest.createStudyRecruitmentStatusobj(nonpropTrial);
            TestSchema.addUpdObject(studyRecStatus);

            DocumentWorkflowStatus docWrk = DocumentWorkFlowStatusTest.createDocumentWorkflowStatus(nonpropTrial);
            TestSchema.addUpdObject(docWrk);
            // properties
            PAProperties prop = new PAProperties();
            prop.setName("smtp");
            prop.setValue("mail.smtp.host");
            TestSchema.addUpdObject(prop);

            prop = new PAProperties();
            prop.setName("fromaddress");
            prop.setValue("fromAddress@example.com");
            TestSchema.addUpdObject(prop);

            prop = new PAProperties();
            prop.setName("tsr.amend.body");
            prop.setValue("${CurrentDate} ${SubmitterName}${leadOrgTrialIdentifier}, ${trialTitle},${nciTrialIdentifier}, (${amendmentNumber}), ${amendmentDate}, (${fileName}), ${fileName2}.");
            TestSchema.addUpdObject(prop);

            prop = new PAProperties();
            prop.setName("tsr.proprietary.body");
            prop.setValue("${CurrentDate} ${SubmitterName}${leadOrgTrialIdentifier}, ${trialTitle},${nciTrialIdentifier}, (${amendmentNumber}), ${amendmentDate}, (${fileName}), ${fileName2}.");
            TestSchema.addUpdObject(prop);

            prop = new PAProperties();
            prop.setName("xml.subject");
            prop.setValue("xml.subject, ${leadOrgTrialIdentifier}, ${nciTrialIdentifier}.");
            TestSchema.addUpdObject(prop);

            prop = new PAProperties();
            prop.setName("xml.body");
            prop.setValue("${CurrentDate} ${SubmitterName}${nciTrialIdentifier}, ${trialTitle}, (${leadOrgTrialIdentifier}), ${receiptDate} ${fileName}.");
            TestSchema.addUpdObject(prop);

            prop = new PAProperties();
            prop.setName("noxml.tsr.amend.body");
            prop.setValue("${CurrentDate} ${SubmitterName}${leadOrgTrialIdentifier}, ${trialTitle},${nciTrialIdentifier}, (${amendmentNumber}), ${amendmentDate}, (${fileName}), ${fileName2}.");
            TestSchema.addUpdObject(prop);

            return IiConverter.convertToIi(nonpropTrial.getId());
        }

        /**
         * @return
         */
        public static Ii createAmendStudyProtocol() {
                StudyProtocol sp = new InterventionalStudyProtocol();
                sp.setOfficialTitle("cancer for THOLA");
                sp.setStartDate(ISOUtil.dateStringToTimestamp("1/1/2000"));
                sp.setStartDateTypeCode(ActualAnticipatedTypeCode.ACTUAL);
                sp.setPrimaryCompletionDate(ISOUtil.dateStringToTimestamp("12/31/2009"));
                sp.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode.ANTICIPATED);
                sp.setAccrualReportingMethodCode(AccrualReportingMethodCode.ABBREVIATED);
                Set<Ii> studySecondaryIdentifiers =  new HashSet<Ii>();
                Ii spSecId = new Ii();
                spSecId.setExtension("NCI-2009-00001");
                studySecondaryIdentifiers.add(spSecId);
                sp.setOtherIdentifiers(studySecondaryIdentifiers);
                sp.setSubmissionNumber(Integer.valueOf(1));
                sp.setProprietaryTrialIndicator(Boolean.FALSE);
                sp.setCtgovXmlRequiredIndicator(Boolean.TRUE);
                sp.setSubmissionNumber(2);
                sp.setAmendmentDate(new Timestamp((new Date()).getTime()));
                TestSchema.addUpdObject(sp);
                sp.setId(sp.getId());
                return IiConverter.convertToStudyProtocolIi(sp.getId());
            }

        public static User getUser() {
            return getUser(false);
        }

        public static User getUser(Boolean createNew) {
            if ( user == null || createNew ) {
                user = new User();
                user.setLoginName("Abstractor: " + new Date());
                user.setFirstName("Joe");
                user.setLastName("Smith");
                user.setUpdateDate(new Date());
                TestSchema.addUpdObject(user);
            }
            return user;
        }
}
