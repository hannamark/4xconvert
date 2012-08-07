/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The pa
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This pa Software License (the License) is between NCI and You. You (or
 * Your) shall mean a person or an entity, and all other entities that control,
 * are controlled by, or are under common control with the entity. Control for
 * purposes of this definition means (i) the direct or indirect power to cause
 * the direction or management of such entity, whether by contract or otherwise,
 * or (ii) ownership of fifty percent (50%) or more of the outstanding shares,
 * or (iii) beneficial ownership of such entity.
 *
 * This License is granted provided that You agree to the conditions described
 * below. NCI grants You a non-exclusive, worldwide, perpetual, fully-paid-up,
 * no-charge, irrevocable, transferable and royalty-free right and license in
 * its rights in the pa Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the pa Software; (ii) distribute and
 * have distributed to and by third parties the pa Software and any
 * modifications and derivative works thereof; and (iii) sublicense the
 * foregoing rights set out in (i) and (ii) to third parties, including the
 * right to license such rights to further third parties. For sake of clarity,
 * and not by way of limitation, NCI shall have no right of accounting or right
 * of payment from You or Your sub-licensees for the rights granted under this
 * License. This License is granted at no charge to You.
 *
 * Your redistributions of the source code for the Software must retain the
 * above copyright notice, this list of conditions and the disclaimer and
 * limitation of liability of Article 6, below. Your redistributions in object
 * code form must reproduce the above copyright notice, this list of conditions
 * and the disclaimer of Article 6 in the documentation and/or other materials
 * provided with the distribution, if any.
 *
 * Your end-user documentation included with the redistribution, if any, must
 * include the following acknowledgment: This product includes software
 * developed by 5AM and the National Cancer Institute. If You do not include
 * such end-user documentation, You shall include this acknowledgment in the
 * Software itself, wherever such third-party acknowledgments normally appear.
 *
 * You may not use the names "The National Cancer Institute", "NCI", or "5AM"
 * to endorse or promote products derived from this Software. This License does
 * not authorize You to use any trademarks, service marks, trade names, logos or
 * product names of either NCI or 5AM, except as required to comply with the
 * terms of this License.
 *
 * For sake of clarity, and not by way of limitation, You may incorporate this
 * Software into Your proprietary programs and into any third party proprietary
 * programs. However, if You incorporate the Software into third party
 * proprietary programs, You agree that You are solely responsible for obtaining
 * any permission from such third parties required to incorporate the Software
 * into such third party proprietary programs and for informing Your
 * sub-licensees, including without limitation Your end-users, of their
 * obligation to secure any required permissions from such third parties before
 * incorporating the Software into such third party proprietary software
 * programs. In the event that You fail to obtain such permissions, You agree
 * to indemnify NCI for any claims against NCI by such third parties, except to
 * the extent prohibited by law, resulting from Your failure to obtain such
 * permissions.
 *
 * For sake of clarity, and not by way of limitation, You may add Your own
 * copyright statement to Your modifications and to the derivative works, and
 * You may provide additional or different license terms and conditions in Your
 * sublicenses of modifications of the Software, or any derivative works of the
 * Software as a whole, provided Your use, reproduction, and distribution of the
 * Work otherwise complies with the conditions stated in this License.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO
 * EVENT SHALL THE NATIONAL CANCER INSTITUTE, 5AM SOLUTIONS, INC. OR THEIR
 * AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.nih.nci.pa.util;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.AnatomicSite;
import gov.nih.nci.pa.domain.Arm;
import gov.nih.nci.pa.domain.ClinicalResearchStaff;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.Document;
import gov.nih.nci.pa.domain.DocumentWorkflowStatus;
import gov.nih.nci.pa.domain.HealthCareFacility;
import gov.nih.nci.pa.domain.HealthCareProvider;
import gov.nih.nci.pa.domain.ICD9Disease;
import gov.nih.nci.pa.domain.Intervention;
import gov.nih.nci.pa.domain.InterventionAlternateName;
import gov.nih.nci.pa.domain.InterventionalStudyProtocol;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.OrganizationalContact;
import gov.nih.nci.pa.domain.OversightCommittee;
import gov.nih.nci.pa.domain.PAProperties;
import gov.nih.nci.pa.domain.PDQDisease;
import gov.nih.nci.pa.domain.PDQDiseaseAltername;
import gov.nih.nci.pa.domain.PDQDiseaseParent;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.domain.PlannedActivity;
import gov.nih.nci.pa.domain.PlannedEligibilityCriterion;
import gov.nih.nci.pa.domain.PlannedMarker;
import gov.nih.nci.pa.domain.PlannedSubstanceAdministration;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.RegulatoryAuthority;
import gov.nih.nci.pa.domain.ResearchOrganization;
import gov.nih.nci.pa.domain.SDCDisease;
import gov.nih.nci.pa.domain.StratumGroup;
import gov.nih.nci.pa.domain.StudyAccrualAccess;
import gov.nih.nci.pa.domain.StudyCheckout;
import gov.nih.nci.pa.domain.StudyContact;
import gov.nih.nci.pa.domain.StudyDisease;
import gov.nih.nci.pa.domain.StudyIndlde;
import gov.nih.nci.pa.domain.StudyMilestone;
import gov.nih.nci.pa.domain.StudyOnhold;
import gov.nih.nci.pa.domain.StudyOutcomeMeasure;
import gov.nih.nci.pa.domain.StudyOverallStatus;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyProtocolDates;
import gov.nih.nci.pa.domain.StudyRecruitmentStatus;
import gov.nih.nci.pa.domain.StudyRegulatoryAuthority;
import gov.nih.nci.pa.domain.StudyResourcing;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.domain.StudySiteAccrualStatus;
import gov.nih.nci.pa.domain.StudySiteContact;
import gov.nih.nci.pa.enums.AccrualReportingMethodCode;
import gov.nih.nci.pa.enums.ActStatusCode;
import gov.nih.nci.pa.enums.ActiveInactiveCode;
import gov.nih.nci.pa.enums.ActiveInactivePendingCode;
import gov.nih.nci.pa.enums.ActivityCategoryCode;
import gov.nih.nci.pa.enums.ActivitySubcategoryCode;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.AllocationCode;
import gov.nih.nci.pa.enums.AmendmentReasonCode;
import gov.nih.nci.pa.enums.ArmTypeCode;
import gov.nih.nci.pa.enums.AssayPurposeCode;
import gov.nih.nci.pa.enums.AssayTypeCode;
import gov.nih.nci.pa.enums.AssayUseCode;
import gov.nih.nci.pa.enums.AssignmentActionCode;
import gov.nih.nci.pa.enums.BlindingRoleCode;
import gov.nih.nci.pa.enums.BlindingSchemaCode;
import gov.nih.nci.pa.enums.CheckOutType;
import gov.nih.nci.pa.enums.DesignConfigurationCode;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.EntityStatusCode;
import gov.nih.nci.pa.enums.ExpandedAccessStatusCode;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.GrantorCode;
import gov.nih.nci.pa.enums.HolderTypeCode;
import gov.nih.nci.pa.enums.IndldeTypeCode;
import gov.nih.nci.pa.enums.InterventionTypeCode;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.enums.NihInstituteCode;
import gov.nih.nci.pa.enums.OnholdReasonCode;
import gov.nih.nci.pa.enums.PhaseAdditionalQualifierCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.PrimaryPurposeAdditionalQualifierCode;
import gov.nih.nci.pa.enums.PrimaryPurposeCode;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.enums.StructuralRoleStatusCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;
import gov.nih.nci.pa.enums.TissueCollectionMethodCode;
import gov.nih.nci.pa.enums.TissueSpecimenTypeCode;
import gov.nih.nci.pa.enums.UnitsCode;
import gov.nih.nci.pa.enums.UserOrgType;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.time.DateUtils;
import org.hibernate.Session;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;

/**
 * 
 * @author Hugh
 * 
 */
public class TestSchema {
    public static final Timestamp TODAY = new Timestamp(new Date().getTime());
    public static final Timestamp YESTERDAY = new Timestamp(DateUtils.addDays(new Date(), -1).getTime());
    public static final Timestamp TOMMORROW = new Timestamp(DateUtils.addDays(new Date(), 1).getTime());
    public static final Timestamp ONE_YEAR_FROM_TODAY = new Timestamp(DateUtils.addYears(new Date(), 1).getTime());
    public static List<Long> registryUserIds;
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
    public static List<Long> pdqDiseaseIds;
    public static List<Long> sdcDiseaseIds;
    public static List<Long> icd9DiseaseIds;
    public static List<Long> anatomicSiteIds;
    public static List<Long> outcomeIds;
    public static List<Long> regAuthIds;
    public static List<Long> personIds;
    public static List<Long> organizationalContactIds;
    public static List<Long> studyDiseaseIds;
    public static List<Long> studyOnholdIds;
    public static List<Country> countries;
    private static User user;
    public static Long inactiveProtocolId;

    /**
     * 
     * @param <T> t
     * @param obj o
     */
    public static <T> void addUpdObject(T obj) {
        Session session = PaHibernateUtil.getCurrentSession();
        session.saveOrUpdate(obj);
        session.flush();
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
        registryUserIds = new ArrayList<Long>();
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
        organizationalContactIds = new ArrayList<Long>();
        pdqDiseaseIds = new ArrayList<Long>();
        sdcDiseaseIds = new ArrayList<Long>();
        icd9DiseaseIds = new ArrayList<Long>();
        outcomeIds = new ArrayList<Long>();
        regAuthIds = new ArrayList<Long>();
        personIds = new ArrayList<Long>();
        countries = new ArrayList<Country>();
        anatomicSiteIds = new ArrayList<Long>();
        studyDiseaseIds = new ArrayList<Long>();
        studyOnholdIds = new ArrayList<Long>();
        
        User curator = getUser(true);
        addUpdObject(curator);

        StudyProtocol sp = new InterventionalStudyProtocol();
        sp.setOfficialTitle("cancer for THOLA");
        StudyProtocolDates dates = sp.getDates();
        dates.setStartDate(TODAY);
        dates.setStartDateTypeCode(ActualAnticipatedTypeCode.ACTUAL);
        dates.setPrimaryCompletionDate(ONE_YEAR_FROM_TODAY);
        dates.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode.ANTICIPATED);
        sp.setPrimaryPurposeCode(PrimaryPurposeCode.BASIC_SCIENCE);
        sp.setAccrualReportingMethodCode(AccrualReportingMethodCode.ABBREVIATED);
        sp.setStatusCode(ActStatusCode.ACTIVE);
        sp.setPhaseCode(PhaseCode.I);
        sp.setFdaRegulatedIndicator(Boolean.TRUE);
        sp.setSection801Indicator(Boolean.TRUE);
        sp.setDelayedpostingIndicator(Boolean.TRUE);

        RegistryUser ru = getRegistryUser();
        registryUserIds.add(ru.getId());
        sp.setUserLastCreated(ru.getUserLastCreated());
        sp.setUserLastUpdated(ru.getUserLastUpdated());

        Set<Ii> studySecondaryIdentifiers = new HashSet<Ii>();
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
        sos.setStatusDate(YESTERDAY);
        sos.setStudyProtocol(sp);
        addUpdObject(sos);
        sos = new StudyOverallStatus();
        sos.setStatusCode(StudyStatusCode.ACTIVE);
        sos.setStatusDate(TODAY);
        sos.setStudyProtocol(sp);
        addUpdObject(sos);
        
        StudyAccrualAccess studyAccrualAccess = new StudyAccrualAccess();
        studyAccrualAccess.setActionCode(AssignmentActionCode.ASSIGNED);
        studyAccrualAccess.setComments("TEST");
        studyAccrualAccess.setDateLastCreated(new Date());
        studyAccrualAccess.setRegistryUser(ru);
        studyAccrualAccess.setStatusCode(ActiveInactiveCode.ACTIVE);
        studyAccrualAccess.setStatusDateRangeLow(new Timestamp(new Date().getTime()));
        studyAccrualAccess.setStudyProtocol(sp);
        studyAccrualAccess.setUserLastCreated(ru.getCsmUser());
        addUpdObject(studyAccrualAccess);

        Organization org = TestSchema.createOrganizationObj();
        addUpdObject(org);

        HealthCareFacility hfc = TestSchema.createHealthCareFacilityObj(org);
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

        Person per = TestSchema.createPersonObj();
        per.setFirstName("Joe");
        per.setLastName("the Clinician");
        addUpdObject(per);
        personIds.add(per.getId());

        HealthCareProvider hcp = TestSchema.createHealthCareProviderObj(per, org);
        addUpdObject(hcp);
        healthCareProviderIds.add(hcp.getId());

        ClinicalResearchStaff crs = TestSchema.createClinicalResearchStaffObj(org, per);
        addUpdObject(crs);
        clinicalResearchStaffIds.add(crs.getId());

        OrganizationalContact orgContact = createOrganizationalContactObj(org, per);
        orgContact.setIdentifier("abcd");
        addUpdObject(orgContact);
        organizationalContactIds.add(orgContact.getId());

        StudySite sPart = new StudySite();
        sPart.setFunctionalCode(StudySiteFunctionalCode.TREATING_SITE);
        sPart.setHealthCareFacility(hfc);
        sPart.setLocalStudyProtocolIdentifier("Local SP ID 01");
        sPart.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        sPart.setStatusDateRangeLow(ISOUtil.dateStringToTimestamp("6/1/2008"));
        sPart.setAccrualDateRangeLow(ISOUtil.dateStringToTimestamp("03/11/2012"));
        sPart.setStudyProtocol(sp);
        addUpdObject(sPart);
        studySiteIds.add(sPart.getId());

        StudySite sPart2 = new StudySite();
        sPart2.setFunctionalCode(StudySiteFunctionalCode.LEAD_ORGANIZATION);
        sPart2.setResearchOrganization(rOrg);
        sPart2.setLocalStudyProtocolIdentifier("Local SP ID 02");
        sPart2.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        sPart2.setStatusDateRangeLow(ISOUtil.dateStringToTimestamp("6/1/2008"));        
        sPart2.setStudyProtocol(sp);
        addUpdObject(sPart2);
        studySiteIds.add(sPart2.getId());

        StudyResourcing summary4Resourcing = new StudyResourcing();
        summary4Resourcing.setSummary4ReportedResourceIndicator(Boolean.TRUE);
        summary4Resourcing.setOrganizationIdentifier("1");
        summary4Resourcing.setTypeCode(SummaryFourFundingCategoryCode.INSTITUTIONAL);
        summary4Resourcing.setStudyProtocol(sp);
        addUpdObject(summary4Resourcing);

        Country country = new Country();
        country.setAlpha2("US");
        country.setAlpha3("USA");
        country.setName("United States");
        country.setNumeric("840");
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

        RegulatoryAuthority ra = new RegulatoryAuthority();
        ra.setAuthorityName("Food and Drug Administration");
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
        sc.setClinicalResearchStaff(crs);
        sc.setEmail("study-contact@example.com");
        sc.setPhone("123-456-7890");
        addUpdObject(sc);

        StudySiteAccrualStatus ssas = new StudySiteAccrualStatus();
        ssas.setStatusCode(RecruitmentStatusCode.CLOSED_TO_ACCRUAL);
        ssas.setStatusDate(TODAY);
        ssas.setStudySite(sPart);
        addUpdObject(ssas);
        
        StudySiteAccrualStatus ssas2 = new StudySiteAccrualStatus();
        ssas2.setStatusCode(RecruitmentStatusCode.IN_REVIEW);
        ssas2.setStatusDate(TODAY);
        ssas2.setStudySite(sPart2);
        addUpdObject(ssas2);        

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
        PDQDisease dis01 = TestSchema.createPdqDisease("Toe Cancer");
        addUpdObject(dis01);
        pdqDiseaseIds.add(dis01.getId());
        PDQDisease dis02 = TestSchema.createPdqDisease("Heel Cancer");
        addUpdObject(dis02);
        pdqDiseaseIds.add(dis02.getId());
        PDQDisease dis03 = TestSchema.createPdqDisease("Foot Cancer");
        addUpdObject(dis03);
        pdqDiseaseIds.add(dis03.getId());
        PDQDisease dis04 = TestSchema.createPdqDisease("Leg Cancer");
        addUpdObject(dis04);
        pdqDiseaseIds.add(dis04.getId());

        createAnatomicSites();

        PDQDiseaseParent disPar1 = TestSchema.createPdqDiseaseParent(dis01, dis03);
        addUpdObject(disPar1);
        PDQDiseaseParent disPar2 = TestSchema.createPdqDiseaseParent(dis02, dis03);
        addUpdObject(disPar2);
        PDQDiseaseParent disPar3 = TestSchema.createPdqDiseaseParent(dis03, dis04);
        addUpdObject(disPar3);

        PDQDiseaseAltername diseaseAltername = TestSchema.createPdqDiseaseAltername("Little Piggy Cancer", dis01);
        addUpdObject(diseaseAltername);

        StudyDisease studyDisease = TestSchema.createStudyDiseaseObj(sp, dis01);
        addUpdObject(studyDisease);
        studyDiseaseIds.add(studyDisease.getId());
        StudyDisease studyDisease2 = TestSchema.createStudyDiseaseObj(sp, dis02);
        addUpdObject(studyDisease2);
        studyDiseaseIds.add(studyDisease2.getId());

        StudyMilestone studyMilestone = createStudyMilestoneObj("comment 01", sp);
        addUpdObject(studyMilestone);
        StudyMilestone studyMilestonetss1 = createTrialSummarySentStudyMilestoneObj(sp);
        addUpdObject(studyMilestonetss1);
        StudyMilestone studyMilestonetss2 = createTrialSummarySentStudyMilestoneObjFiveDays(sp);
        addUpdObject(studyMilestonetss2);

        RegulatoryAuthority rega = new RegulatoryAuthority();
        rega.setCountry(country);
        rega.setAuthorityName("Authority");
        addUpdObject(rega);
        regAuthIds.add(rega.getId());

        StudyCheckout scheckout = new StudyCheckout();
        scheckout.setStudyProtocol(sp);
        scheckout.setCheckOutType(CheckOutType.ADMINISTRATIVE);
        scheckout.setUserIdentifier("Abstractor");
        scheckout.setCheckOutDate(new Timestamp((new Date()).getTime()));
        addUpdObject(scheckout);

        scheckout = new StudyCheckout();
        scheckout.setStudyProtocol(sp);
        scheckout.setCheckOutType(CheckOutType.SCIENTIFIC);
        scheckout.setUserIdentifier("Abstractor");
        scheckout.setCheckOutDate(new Timestamp((new Date()).getTime()));
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

        SDCDisease sdc01 = TestSchema.createSdcDisease("Toe Cancer");
        addUpdObject(sdc01);
        sdcDiseaseIds.add(sdc01.getId());
        SDCDisease sdc02 = TestSchema.createSdcDisease("Heel Cancer");
        addUpdObject(sdc02);
        sdcDiseaseIds.add(sdc02.getId());
        SDCDisease sdc03 = TestSchema.createSdcDisease("Foot Cancer");
        addUpdObject(sdc03);
        sdcDiseaseIds.add(sdc03.getId());
        SDCDisease sdc04 = TestSchema.createSdcDisease("Leg Cancer");
        addUpdObject(sdc04);
        sdcDiseaseIds.add(sdc04.getId());

        ICD9Disease icd901 = TestSchema.createICD9Disease("code1", "name1");
        addUpdObject(icd901);
        icd9DiseaseIds.add(icd901.getId());

        ICD9Disease icd902 = TestSchema.createICD9Disease("code2", "name2");
        addUpdObject(icd902);
        icd9DiseaseIds.add(icd902.getId());

        ICD9Disease icd903 = TestSchema.createICD9Disease("code3", "name3");
        addUpdObject(icd903);
        icd9DiseaseIds.add(icd903.getId());

        ICD9Disease icd904 = TestSchema.createICD9Disease("code4", "namedif4");
        addUpdObject(icd904);
        icd9DiseaseIds.add(icd904.getId());

        ICD9Disease icd905 = TestSchema.createICD9Disease("code5", "namedif5");
        addUpdObject(icd905);
        icd9DiseaseIds.add(icd905.getId());
        
        // Study On-Hold
        sp = new InterventionalStudyProtocol();
        sp.setOfficialTitle("cancer for THOLA");
        dates = sp.getDates();
        dates.setStartDate(TODAY);
        dates.setStartDateTypeCode(ActualAnticipatedTypeCode.ACTUAL);
        dates.setPrimaryCompletionDate(ONE_YEAR_FROM_TODAY);
        dates.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode.ANTICIPATED);
        sp.setPrimaryPurposeCode(PrimaryPurposeCode.BASIC_SCIENCE);
        sp.setAccrualReportingMethodCode(AccrualReportingMethodCode.ABBREVIATED);
        sp.setStatusCode(ActStatusCode.ACTIVE);
        sp.setPhaseCode(PhaseCode.I);
        sp.setFdaRegulatedIndicator(Boolean.TRUE);
        sp.setSection801Indicator(Boolean.TRUE);
        sp.setDelayedpostingIndicator(Boolean.TRUE);
        sp.setUserLastCreated(ru.getUserLastCreated());
        sp.setUserLastUpdated(ru.getUserLastUpdated());

        studySecondaryIdentifiers = new HashSet<Ii>();
        spSecId = new Ii();
        spSecId.setExtension("NCI-2009-00002");
        spSecId.setRoot(IiConverter.STUDY_PROTOCOL_ROOT);
        studySecondaryIdentifiers.add(spSecId);
        sp.setOtherIdentifiers(studySecondaryIdentifiers);
        sp.setSubmissionNumber(Integer.valueOf(1));
        sp.setProprietaryTrialIndicator(Boolean.FALSE);
        sp.setCtgovXmlRequiredIndicator(Boolean.TRUE);
        addUpdObject(sp);
        studyProtocolIds.add(sp.getId());
        
        StudyOnhold onhold = new StudyOnhold();
        onhold.setStudyProtocol(sp);
        onhold.setOnholdReasonCode(OnholdReasonCode.SUBMISSION_INCOM);
        onhold.setOnholdReasonText("onhold_reason_text");
        onhold.setOnholdDate(TODAY);
        onhold.setPreviousStatusCode(DocumentWorkflowStatusCode.SUBMITTED);
        addUpdObject(onhold);
        studyOnholdIds.add(onhold.getId());

        // Inactive study
        sp = new InterventionalStudyProtocol();
        sp.setOfficialTitle("cancer for THOLA");
        dates = sp.getDates();
        dates.setStartDate(TODAY);
        dates.setStartDateTypeCode(ActualAnticipatedTypeCode.ACTUAL);
        dates.setPrimaryCompletionDate(ONE_YEAR_FROM_TODAY);
        dates.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode.ANTICIPATED);
        sp.setPrimaryPurposeCode(PrimaryPurposeCode.BASIC_SCIENCE);
        sp.setAccrualReportingMethodCode(AccrualReportingMethodCode.ABBREVIATED);
        sp.setStatusCode(ActStatusCode.INACTIVE);
        sp.setPhaseCode(PhaseCode.I);
        sp.setFdaRegulatedIndicator(Boolean.TRUE);
        sp.setSection801Indicator(Boolean.TRUE);
        sp.setDelayedpostingIndicator(Boolean.TRUE);

        ru = getRegistryUser();
        registryUserIds.add(ru.getId());
        sp.setUserLastCreated(ru.getUserLastCreated());
        sp.setUserLastUpdated(ru.getUserLastUpdated());

        studySecondaryIdentifiers = new HashSet<Ii>();
        spSecId = new Ii();
        spSecId.setExtension("NCI-2009-00001");
        spSecId.setRoot(IiConverter.STUDY_PROTOCOL_ROOT);
        studySecondaryIdentifiers.add(spSecId);
        sp.setOtherIdentifiers(studySecondaryIdentifiers);
        sp.setSubmissionNumber(Integer.valueOf(0));
        sp.setProprietaryTrialIndicator(Boolean.FALSE);
        sp.setCtgovXmlRequiredIndicator(Boolean.TRUE);
        addUpdObject(sp);
        sp.setId(sp.getId());
        studyProtocolIds.add(sp.getId());
        inactiveProtocolId = sp.getId();
        addAbstractedWorkflowStatus(inactiveProtocolId);
        
        PaHibernateUtil.getCurrentSession().flush();
        PaHibernateUtil.getCurrentSession().clear();
    }

    public static void addAbstractedWorkflowStatus(Long spId) {
        DocumentWorkflowStatus dws = new DocumentWorkflowStatus();
        StudyProtocol sp = new StudyProtocol();
        sp.setId(spId);
        dws.setStudyProtocol(sp);
        dws.setStatusCode(DocumentWorkflowStatusCode.ABSTRACTED);
        dws.setCommentText("Comment Text1");
        dws.setUserLastUpdated(getUser());
        addUpdObject(dws);
    }

    public static void createAnatomicSites() {
        AnatomicSite as01 = TestSchema.createAnatomicSiteObj("Lung");
        addUpdObject(as01);
        anatomicSiteIds.add(as01.getId());
        AnatomicSite as02 = TestSchema.createAnatomicSiteObj("Kidney");
        addUpdObject(as02);
        anatomicSiteIds.add(as02.getId());
        AnatomicSite as03 = TestSchema.createAnatomicSiteObj("Heart");
        addUpdObject(as03);
        anatomicSiteIds.add(as03.getId());
    }

    public static Ii nonPropTrialData() {
        Organization o = TestSchema.createOrganizationObj();
        TestSchema.addUpdObject(o);
        Person p = TestSchema.createPersonObj();
        p.setIdentifier("11");
        TestSchema.addUpdObject(p);
        HealthCareFacility hcf = TestSchema.createHealthCareFacilityObj(o);
        TestSchema.addUpdObject(hcf);

        HealthCareProvider hcp = TestSchema.createHealthCareProviderObj(p, o);
        TestSchema.addUpdObject(hcp);

        Country c = createCountryObj();
        TestSchema.addUpdObject(c);

        ClinicalResearchStaff crs = TestSchema.createClinicalResearchStaffObj(o, p);
        TestSchema.addUpdObject(crs);

        ResearchOrganization ro = new ResearchOrganization();
        ro.setOrganization(o);
        ro.setStatusCode(StructuralRoleStatusCode.ACTIVE);
        ro.setIdentifier("abc");
        TestSchema.addUpdObject(ro);

        StudyProtocol nonpropTrial = new StudyProtocol();
        TestSchema.createStudyProtocolObj(nonpropTrial);
        nonpropTrial.setCtgovXmlRequiredIndicator(Boolean.FALSE);
        TestSchema.addUpdObject(nonpropTrial);
        IiConverter.convertToIi(nonpropTrial.getId());

        StudyContact sc = TestSchema.createStudyContactObj(nonpropTrial, c, hcp, crs);
        TestSchema.addUpdObject(sc);

        StudySite spc = TestSchema.createStudySiteObj(nonpropTrial, hcf);
        spc.setResearchOrganization(ro);
        TestSchema.addUpdObject(spc);

        StudyRecruitmentStatus studyRecStatus = createStudyRecruitmentStatus(nonpropTrial);
        TestSchema.addUpdObject(studyRecStatus);

        DocumentWorkflowStatus docWrk = TestSchema.createDocumentWorkflowStatus(nonpropTrial);
        TestSchema.addUpdObject(docWrk);

        StudyMilestone milestone = TestSchema.createStudyMilestoneObj("READY ", nonpropTrial);
        milestone.setMilestoneCode(MilestoneCode.READY_FOR_TSR);
        TestSchema.addUpdObject(milestone);

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
        StudyProtocolDates dates = sp.getDates();
        dates.setStartDate(TODAY);
        dates.setStartDateTypeCode(ActualAnticipatedTypeCode.ACTUAL);
        dates.setPrimaryCompletionDate(ONE_YEAR_FROM_TODAY);
        dates.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode.ANTICIPATED);
        sp.setPrimaryPurposeCode(PrimaryPurposeCode.BASIC_SCIENCE);
        sp.setAccrualReportingMethodCode(AccrualReportingMethodCode.ABBREVIATED);
        Set<Ii> studySecondaryIdentifiers = new HashSet<Ii>();
        Ii spSecId = new Ii();
        spSecId.setExtension("NCI-2009-00001");
        studySecondaryIdentifiers.add(spSecId);
        sp.setOtherIdentifiers(studySecondaryIdentifiers);
        sp.setSubmissionNumber(Integer.valueOf(1));
        sp.setProprietaryTrialIndicator(Boolean.FALSE);
        sp.setCtgovXmlRequiredIndicator(Boolean.TRUE);
        sp.setSubmissionNumber(2);
        sp.setAmendmentDate(TODAY);
        TestSchema.addUpdObject(sp);
        sp.setId(sp.getId());
        return IiConverter.convertToStudyProtocolIi(sp.getId());
    }

    public static User getUser() {
        return getUser(false);
    }

    public static User getUser(Boolean createNew) {
        if (user == null || createNew) {
            user = new User();
            user.setLoginName("Abstractor: " + new Date());
            user.setFirstName("Joe");
            user.setLastName("Smith");
            user.setUpdateDate(new Date());
            TestSchema.addUpdObject(user);
            UsernameHolder.setUser(user.getLoginName());
        }
        return user;
    }

    public static void clearUser() {
        user = null;
        UsernameHolder.setUser(null);
    }

    public static Country createCountryObj() {
        Country c1 = new Country();
        c1.setAlpha2("CA");
        c1.setAlpha3("CAM");
        c1.setName("Cayman Islands");
        return c1;
    }

    public static StudyRecruitmentStatus createStudyRecruitmentStatus(StudyProtocol sp) {
        StudyRecruitmentStatus create = new StudyRecruitmentStatus();
        create.setStudyProtocol(sp);
        create.setStatusCode(RecruitmentStatusCode.ACTIVE);
        create.setStatusDate(TODAY);
        create.setUserLastUpdated(getUser());
        create.setDateLastUpdated(TODAY);
        return create;
    }

    private static StudyMilestone createStudyMilestoneObj(String comment, StudyProtocol studyProtocol) {
        StudyMilestone result = new StudyMilestone();
        result.setCommentText(comment);
        result.setMilestoneCode(MilestoneCode.TRIAL_SUMMARY_SENT);
        result.setMilestoneDate(TODAY);
        result.setStudyProtocol(studyProtocol);
        return result;
    }

    private static StudyMilestone createTrialSummarySentStudyMilestoneObj(StudyProtocol studyProtocol) {
        StudyMilestone result = new StudyMilestone();
        result.setMilestoneCode(MilestoneCode.TRIAL_SUMMARY_SENT);
        result.setMilestoneDate(TODAY);
        result.setStudyProtocol(studyProtocol);
        return result;
    }

    private static StudyMilestone createTrialSummarySentStudyMilestoneObjFiveDays(StudyProtocol studyProtocol) {
        StudyMilestone result = new StudyMilestone();
        Calendar offsetTime = Calendar.getInstance();
        offsetTime.set(2009, 12, 2);
        result.setMilestoneCode(MilestoneCode.TRIAL_SUMMARY_SENT);
        result.setMilestoneDate(new Timestamp(offsetTime.getTime().getTime()));
        result.setStudyProtocol(studyProtocol);
        return result;
    }

    public static StudyDisease createStudyDiseaseObj(StudyProtocol studyProtocol, PDQDisease disease) {
        StudyDisease create = new StudyDisease();
        create.setStudyProtocol(studyProtocol);
        create.setDisease(disease);
        create.setUserLastCreated(getUser());
        create.setDateLastCreated(TODAY);
        create.setUserLastUpdated(getUser());
        create.setDateLastUpdated(TODAY);
        return create;
    }

    private static SDCDisease createSdcDisease(String preferredName) {
        SDCDisease create = new SDCDisease();
        create.setDiseaseCode("diseaseCode");
        create.setCtepCategory("diseaseCategory");
        create.setCtepSubCategory("diseaseSubCategory");
        create.setDisplayName("menuDisplayName");
        create.setPreferredName(preferredName);
        create.setUserLastCreated(getUser());
        create.setDateLastCreated(TODAY);
        create.setUserLastUpdated(getUser());
        create.setDateLastUpdated(TODAY);
        return create;
    }

    private static ICD9Disease createICD9Disease(String diseaseCode, String name) {
        ICD9Disease create = new ICD9Disease();
        create.setDiseaseCode(diseaseCode);
        create.setName(name);
        create.setUserLastCreated(getUser());
        create.setDateLastCreated(TODAY);
        create.setUserLastUpdated(getUser());
        create.setDateLastUpdated(TODAY);
        return create;
    }

    public static StudyContact createStudyContactObj(StudyProtocol sp, Country c, HealthCareProvider hc,
            ClinicalResearchStaff crs) {
        StudyContact sc = new StudyContact();
        sc.setPrimaryIndicator(Boolean.TRUE);
        sc.setAddressLine("1111, terra cotta circle");
        sc.setDeliveryAddressLine("xxx");
        sc.setCity("herndon");
        sc.setCountry(c);
        sc.setPostalCode("20111");
        sc.setUserLastUpdated(getUser());
        sc.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        sc.setDateLastUpdated(TODAY);
        sc.setStudyProtocol(sp);
        sc.setRoleCode(StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR);
        sc.setHealthCareProvider(hc);
        sc.setClinicalResearchStaff(crs);
        return sc;
    }

    public static RegulatoryAuthority createRegulatoryObj(Country c) {
        RegulatoryAuthority ra = new RegulatoryAuthority();
        ra.setAuthorityName("BWI reg body");
        ra.setCountry(c);
        ra.setUserLastUpdated(getUser());
        ra.setDateLastUpdated(TODAY);
        return ra;
    }

    public static PDQDisease createPdqDisease(String preferredName) {
        PDQDisease create = new PDQDisease();
        create.setDiseaseCode("diseaseCode");
        create.setDisplayName("menuDisplayName");
        create.setNtTermIdentifier("ntTermIdentifier");
        create.setPreferredName(preferredName);
        create.setStatusCode(ActiveInactivePendingCode.ACTIVE);
        create.setStatusDateRangeLow(TODAY);
        create.setUserLastCreated(getUser());
        create.setDateLastCreated(TODAY);
        create.setUserLastUpdated(getUser());
        create.setDateLastUpdated(TODAY);
        return create;
    }

    public static PDQDiseaseParent createPdqDiseaseParent(PDQDisease disease, PDQDisease parentDisease) {
        PDQDiseaseParent create = new PDQDiseaseParent();
        create.setDisease(disease);
        create.setParentDisease(parentDisease);
        create.setParentDiseaseCode("parentDiseaseCode");
        create.setStatusCode(ActiveInactiveCode.ACTIVE);
        create.setStatusDateRangeLow(TODAY);
        create.setUserLastCreated(getUser());
        create.setDateLastCreated(TODAY);
        create.setUserLastUpdated(getUser());
        create.setDateLastUpdated(TODAY);
        return create;
    }

    public static PDQDiseaseAltername createPdqDiseaseAltername(String alternateName, PDQDisease disease) {
        PDQDiseaseAltername create = new PDQDiseaseAltername();
        create.setAlternateName(alternateName);
        create.setDisease(disease);
        create.setStatusCode(ActiveInactiveCode.ACTIVE);
        create.setStatusDateRangeLow(TODAY);
        create.setUserLastCreated(getUser());
        create.setDateLastCreated(TODAY);
        create.setUserLastUpdated(getUser());
        create.setDateLastUpdated(TODAY);
        return create;
    }

    public static AnatomicSite createAnatomicSiteObj(String preferredName) {
        AnatomicSite create = new AnatomicSite();
        create.setCode(preferredName);
        create.setDisplayName("displayName");
        create.setCodingSystem("Summary 4 Anatomic Sites");
        create.setUserLastCreated(getUser());
        create.setDateLastCreated(TODAY);
        create.setUserLastUpdated(getUser());
        create.setDateLastUpdated(TODAY);
        return create;
    }
    
    public static PlannedMarker createPlannedMarker() {
        PlannedMarker result = new PlannedMarker(); 
        result.setName("name");
        result.setAssayTypeCode(AssayTypeCode.CGH);
        result.setAssayUseCode(AssayUseCode.CORRELATIVE);
        result.setAssayPurposeCode(AssayPurposeCode.ELIGIBILITY_CRITERION);
        result.setTissueCollectionMethodCode(TissueCollectionMethodCode.MANDATORY);
        result.setTissueSpecimenTypeCode(TissueSpecimenTypeCode.CITRATED_PLASMA);
        result.setStatusCode(ActiveInactivePendingCode.ACTIVE);       
        return result;
    }

    public static ClinicalResearchStaff createClinicalResearchStaffObj(Organization o, Person p) {
        ClinicalResearchStaff crs = new ClinicalResearchStaff();
        crs.setOrganization(o);
        crs.setPerson(p);
        crs.setIdentifier("1");
        crs.setStatusCode(StructuralRoleStatusCode.PENDING);
        return crs;
    }

    public static DocumentWorkflowStatus createDocumentWorkflowStatus(StudyProtocol sp) {
        DocumentWorkflowStatus create = new DocumentWorkflowStatus();
        create.setStudyProtocol(sp);
        create.setStatusCode(DocumentWorkflowStatusCode.ACCEPTED);
        create.setStatusDateRangeLow(TODAY);
        create.setCommentText("Common Text");
        create.setUserLastUpdated(getUser());
        create.setDateLastUpdated(TODAY);
        return create;
    }
    
    public static DocumentWorkflowStatus createRejectedDocumentWorkflowStatus(StudyProtocol sp) {
        DocumentWorkflowStatus create = new DocumentWorkflowStatus();
        create.setStudyProtocol(sp);
        create.setStatusCode(DocumentWorkflowStatusCode.REJECTED);
        create.setStatusDateRangeLow(TODAY);
        create.setCommentText("Common Text");
        create.setUserLastUpdated(getUser());
        create.setDateLastUpdated(TODAY);
        return create;
    }

    public static DocumentWorkflowStatus createOnHoldDocumentWorkflowStatus(StudyProtocol sp) {
        DocumentWorkflowStatus create = new DocumentWorkflowStatus();        
        create.setStudyProtocol(sp);
        create.setStatusCode(DocumentWorkflowStatusCode.ON_HOLD);
        create.setStatusDateRangeLow(TODAY);
        create.setCommentText("On Hold");
        create.setUserLastUpdated(getUser());
        create.setDateLastUpdated(TODAY);
        return create;
    }


    public static HealthCareFacility createHealthCareFacilityObj(Organization o) {
        HealthCareFacility hc = new HealthCareFacility();
        hc.setOrganization(o);
        hc.setIdentifier("1");
        hc.setStatusCode(StructuralRoleStatusCode.PENDING);
        return hc;
    }

    public static HealthCareProvider createHealthCareProviderObj(Person p, Organization o) {
        HealthCareProvider hc = new HealthCareProvider();
        hc.setOrganization(o);
        hc.setPerson(p);
        hc.setIdentifier("1");
        hc.setStatusCode(StructuralRoleStatusCode.PENDING);
        return hc;
    }

    public static OrganizationalContact createOrganizationalContactObj(Organization o, Person p) {
        OrganizationalContact oc = new OrganizationalContact();
        oc.setOrganization(o);
        oc.setIdentifier("1");
        oc.setPerson(p);
        oc.setStatusCode(StructuralRoleStatusCode.PENDING);
        return oc;
    }

    public static Organization createOrganizationObj() {
        return createOrganizationObj(getUser());
    }

    public static Organization createOrganizationObj(User user) {
        Organization create = new Organization();
        create.setName("Mayo University");
        create.setUserLastUpdated(user);
        create.setDateLastUpdated(TODAY);
        create.setIdentifier("1");
        create.setStatusCode(EntityStatusCode.PENDING);
        return create;
    }

    public static Person createPersonObj() {
        Person p = new Person();
        p.setFirstName("Naveen");
        p.setLastName("Amiruddin");
        p.setMiddleName("S");
        p.setIdentifier("abc");
        p.setStatusCode(EntityStatusCode.PENDING);
        return p;
    }

    public static StudyProtocol createStudyProtocolObj() {
        StudyProtocol sp = new StudyProtocol();
        createStudyProtocolObj(sp);
        return sp;
    }

    public static StudyProtocol createStudyProtocolObj(StudyProtocol sp) {
        final User user = getUser();
        PaHibernateUtil.getCurrentSession().flush();

        sp.setAcronym("Acronym .....");
        sp.setAccrualReportingMethodCode(AccrualReportingMethodCode.ABBREVIATED);
        sp.setDataMonitoringCommitteeAppointedIndicator(Boolean.TRUE);
        sp.setDelayedpostingIndicator(Boolean.TRUE);
        sp.setExpandedAccessIndicator(Boolean.TRUE);
        sp.setFdaRegulatedIndicator(Boolean.TRUE);
        Set<Ii> studySecondaryIdentifiers = new HashSet<Ii>();
        Ii spSecId = new Ii();
        spSecId.setRoot(IiConverter.STUDY_PROTOCOL_ROOT);
        spSecId.setExtension("NCI-2009-00001");
        AnatomicSite as = new AnatomicSite();
        as.setCode("Lung");
        as.setCodingSystem("Summary 4 Anatomic Sites");
        addUpdObject(as);
        sp.setSummary4AnatomicSites(new TreeSet<AnatomicSite>());
        sp.getSummary4AnatomicSites().add(as);
        studySecondaryIdentifiers.add(spSecId);
        sp.setOtherIdentifiers(studySecondaryIdentifiers);
        sp.setKeywordText("keywordText");
        sp.setOfficialTitle("Cancer for kids");
        sp.setPhaseCode(PhaseCode.I);
        sp.setPhaseAdditionalQualifierCode(PhaseAdditionalQualifierCode.PILOT);
        sp.setPrimaryPurposeCode(PrimaryPurposeCode.BASIC_SCIENCE);
        sp.setPrimaryPurposeAdditionalQualifierCode(PrimaryPurposeAdditionalQualifierCode.ANCILLARY);
        sp.setPrimaryPurposeOtherText("primaryPurposeOtherText");
        sp.setPublicDescription("publicDescription");
        sp.setPublicTitle("publicTitle");
        sp.setRecordVerificationDate(TODAY);
        sp.setScientificDescription("scientificDescription");
        sp.setSection801Indicator(Boolean.TRUE);
        
        sp.setDateLastUpdated(TODAY);
        sp.setUserLastUpdated(user);
        sp.setDateLastCreated(TODAY);
        sp.setUserLastUpdated(user);
        sp.setStatusCode(ActStatusCode.ACTIVE);
        sp.setAmendmentReasonCode(AmendmentReasonCode.BOTH);
        sp.setStatusDate(TODAY);
        sp.setAmendmentDate(TODAY);
        sp.setAmendmentNumber("amendmentNumber");
        sp.setSubmissionNumber(2);
        sp.setProprietaryTrialIndicator(Boolean.FALSE);
        sp.setCtgovXmlRequiredIndicator(Boolean.TRUE);
        StudyProtocolDates dates = sp.getDates();
        dates.setStartDate(TODAY);
        dates.setStartDateTypeCode(ActualAnticipatedTypeCode.ANTICIPATED);
        dates.setPrimaryCompletionDate(ONE_YEAR_FROM_TODAY);
        dates.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode.ACTUAL);
        dates.setCompletionDate(ONE_YEAR_FROM_TODAY);
        dates.setCompletionDateTypeCode(ActualAnticipatedTypeCode.ACTUAL);
        addUpdObject(sp);
        addOwners(sp);
        return sp;
    }

    public static void addOwners(StudyProtocol sp) {
        RegistryUser newUser = getRegistryUserObj();
        newUser.getStudyProtocols().add(sp);
        sp.getStudyOwners().add(newUser);
        addUpdObject(newUser.getCsmUser());
        addUpdObject(newUser);

        RegistryUser newUser2 = getRegistryUserObj();
        newUser2.getStudyProtocols().add(sp);
        sp.getStudyOwners().add(newUser2);
        addUpdObject(newUser2.getCsmUser());
        addUpdObject(newUser2);
    }

    private static RegistryUser getRegistryUserObj() {
        RegistryUser create = new RegistryUser();
        create.setAddressLine("xxxxx");

        create.setAffiliateOrg("aff");
        create.setCity("city");
        create.setCountry("country");
        User csmUser = new User();
        csmUser.setLoginName("loginname" + new Date());
        csmUser.setFirstName("firstname");
        csmUser.setLastName("lastname");
        csmUser.setUpdateDate(new Date());
        create.setCsmUser(csmUser);
        create.setFirstName("firstname");
        create.setLastName("lastname");
        create.setMiddleName("middlename");
        create.setPhone("1111");
        create.setPostalCode("00000");
        create.setState("va");
        create.setPrsOrgName("prsOrgName");
        create.setAffiliatedOrganizationId(501L);
        create.setAffiliatedOrgUserType(UserOrgType.ADMIN);

        return create;
    }

    public static InterventionalStudyProtocol createInterventionalStudyProtocolObj(InterventionalStudyProtocol isp) {
        StudyProtocolDates dates = isp.getDates();
        dates.setStartDate(TODAY);
        dates.setStartDateTypeCode(ActualAnticipatedTypeCode.ACTUAL);
        dates.setPrimaryCompletionDate(ONE_YEAR_FROM_TODAY);
        dates.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode.ACTUAL);
        isp.setPrimaryPurposeCode(PrimaryPurposeCode.BASIC_SCIENCE);
        isp.setAllocationCode(AllocationCode.NA);
        isp.setBlindingRoleCodeCaregiver(BlindingRoleCode.CAREGIVER);
        isp.setBlindingRoleCodeSubject(BlindingRoleCode.SUBJECT);
        isp.setBlindingRoleCodeInvestigator(BlindingRoleCode.INVESTIGATOR);
        isp.setBlindingRoleCodeOutcome(BlindingRoleCode.OUTCOMES_ASSESSOR);
        isp.setBlindingSchemaCode(BlindingSchemaCode.DOUBLE_BLIND);
        isp.setDesignConfigurationCode(DesignConfigurationCode.CROSSOVER);
        isp.setNumberOfInterventionGroups(Integer.valueOf(5));
        isp.setProprietaryTrialIndicator(Boolean.FALSE);
        isp.setSubmissionNumber(Integer.valueOf(1));
        return isp;
    }

    public static StudySite createStudySiteObj(StudyProtocol sp, HealthCareFacility hcf) {
        StudySite create = new StudySite();
        create.setFunctionalCode(StudySiteFunctionalCode.LEAD_ORGANIZATION);
        create.setLocalStudyProtocolIdentifier("Ecog1");
        create.setUserLastUpdated(getUser());
        create.setDateLastUpdated(TODAY);
        create.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        create.setStatusDateRangeLow(TODAY);
        create.setStudyProtocol(sp);
        create.setHealthCareFacility(hcf);
        return create;
    }
    
    public static StudySite createParticipatingSite(StudyProtocol sp) {
        
        Organization org = createOrganizationObj();
        addUpdObject(org);

        HealthCareFacility hfc = TestSchema.createHealthCareFacilityObj(org);
        addUpdObject(hfc);                
        
        StudySite site = new StudySite();
        site.setFunctionalCode(StudySiteFunctionalCode.TREATING_SITE);
        site.setLocalStudyProtocolIdentifier(sp.getId()+"_SITE");
        site.setUserLastUpdated(getUser());
        site.setDateLastUpdated(TODAY);
        site.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        site.setStatusDateRangeLow(TODAY);
        site.setStudyProtocol(sp);
        site.setHealthCareFacility(hfc);
        addUpdObject(site);
        
        StudySiteAccrualStatus ssas = new StudySiteAccrualStatus();
        ssas.setDateLastCreated(new Date());
        ssas.setStatusCode(RecruitmentStatusCode.ACTIVE);
        ssas.setStatusDate(new Timestamp(new Date().getTime()));
        ssas.setStudySite(site);
        ssas.setUserLastCreated(getUser());
        addUpdObject(ssas);
        
        site.getStudySiteAccrualStatuses().add(ssas);
        
        return site;
    }    

    public static RegistryUser getRegistryUser() {
        User user = getUser();
        RegistryUser ru = new RegistryUser();
        ru.setFirstName("Test");
        ru.setLastName("User");
        ru.setEmailAddress("test@example.com");
        ru.setPhone("123-456-7890");
        ru.setCsmUser(user);
        ru.setUserLastCreated(user);
        ru.setUserLastUpdated(user);
        TestSchema.addUpdObject(ru);
        return ru;
    }
}
