/**
 *
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.ClinicalResearchStaff;
import gov.nih.nci.pa.domain.ClinicalResearchStaffTest;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.CountryTest;
import gov.nih.nci.pa.domain.DocumentWorkFlowStatusTest;
import gov.nih.nci.pa.domain.DocumentWorkflowStatus;
import gov.nih.nci.pa.domain.HealthCareFacility;
import gov.nih.nci.pa.domain.HealthCareFacilityTest;
import gov.nih.nci.pa.domain.HealthCareProvider;
import gov.nih.nci.pa.domain.HealthCareProviderTest;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.OrganizationTest;
import gov.nih.nci.pa.domain.PAProperties;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.domain.PersonTest;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.ResearchOrganization;
import gov.nih.nci.pa.domain.StudyContact;
import gov.nih.nci.pa.domain.StudyContactTest;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyProtocolTest;
import gov.nih.nci.pa.domain.StudyRecruitmentStatus;
import gov.nih.nci.pa.domain.StudyRecruitmentStatusTest;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.domain.StudySiteTest;
import gov.nih.nci.pa.enums.AccrualReportingMethodCode;
import gov.nih.nci.pa.enums.ActStatusCode;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.AmendmentReasonCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.PrimaryPurposeCode;
import gov.nih.nci.pa.enums.StructuralRoleStatusCode;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.util.CTGovXmlGeneratorServiceBean;
import gov.nih.nci.pa.service.util.CTGovXmlGeneratorServiceRemote;
import gov.nih.nci.pa.service.util.LookUpTableServiceBean;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceBean;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.service.util.RegistryUserServiceBean;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.pa.service.util.TSRReportGeneratorServiceBean;
import gov.nih.nci.pa.service.util.TSRReportGeneratorServiceRemote;
import gov.nih.nci.pa.util.TestSchema;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Vrushali
 *
 */
public class MailManagerServiceTest {

	MailManagerBeanLocal bean = new MailManagerBeanLocal();
    ProtocolQueryServiceLocal protocolQrySrv = new ProtocolQueryServiceBean();
    RegistryUserServiceLocal registryUserSrv = new RegistryUserServiceBean();
    CTGovXmlGeneratorServiceRemote ctGovXmlSrv = new CTGovXmlGeneratorServiceBean ();
    TSRReportGeneratorServiceRemote tsrReptSrv = new TSRReportGeneratorServiceBean();
    LookUpTableServiceRemote lookUpTableSrv = new LookUpTableServiceBean();
    DocumentWorkflowStatusServiceLocal docWrkStatSrv = new DocumentWorkflowStatusServiceBean();
    StudySiteServiceLocal studySiteService = new StudySiteBeanLocal();

    Ii nonProprietaryTrialIi;
    Ii proprietaryTrialIi;

    private static String email1 = "example1@example.com";
    private static String email2 = "example2@example.com";
    private static String smtpHost = "mail.smtp.host";
    private static String fromAddress = "fromAddress@example.com";

    @Before
    public void setUp() throws Exception {
        TestSchema.reset();

        bean.ctGovXmlGeneratorService = ctGovXmlSrv;
        bean.protocolQueryService = protocolQrySrv;
        bean.registryUserService = registryUserSrv;
        bean.tsrReportGeneratorService = tsrReptSrv;
        bean.lookUpTableService = lookUpTableSrv;
        bean.docWrkflStatusSrv = docWrkStatSrv;
        bean.studySiteService = studySiteService;

        //setup owners for both prop/nonprop trials.
        RegistryUser owner1 = new RegistryUser();
        owner1.setLastName("fname1");
        owner1.setFirstName("lname1");
        owner1.setEmailAddress(email1);
        TestSchema.addUpdObject(owner1);

        RegistryUser owner2 = new RegistryUser();
        owner2.setLastName("fname2");
        owner2.setFirstName("lname2");
        owner2.setEmailAddress(email2);
        TestSchema.addUpdObject(owner2);

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

        // Non Prop Trial
        StudyProtocol nonPropTrial = StudyProtocolTest.createStudyProtocolObj();
        nonPropTrial.getStudyOwners().add(owner1);
        nonPropTrial.getStudyOwners().add(owner2);
        TestSchema.addUpdObject(nonPropTrial);
        nonProprietaryTrialIi = IiConverter.convertToIi(nonPropTrial.getId());

        StudyContact sc = StudyContactTest.createStudyContactObj(nonPropTrial, c, hcp, crs);
        TestSchema.addUpdObject(sc);

        StudySite spc = StudySiteTest.createStudySiteObj(nonPropTrial, hcf);
        spc.setResearchOrganization(ro);
        TestSchema.addUpdObject(spc);

        StudyRecruitmentStatus studyRecStatus = StudyRecruitmentStatusTest.createStudyRecruitmentStatusobj(nonPropTrial);
        TestSchema.addUpdObject(studyRecStatus);

        DocumentWorkflowStatus docWrk = DocumentWorkFlowStatusTest.createDocumentWorkflowStatus(nonPropTrial);
        TestSchema.addUpdObject(docWrk);

        // Prop Trial
        StudyProtocol propTrial = createProprietaryStudyProtocolObj();
        propTrial.getStudyOwners().add(owner1);
        propTrial.getStudyOwners().add(owner2);

        Set<Ii> otherIdentifiers = new HashSet<Ii>();
        otherIdentifiers.add(IiConverter.convertToAssignedIdentifierIi("NCI-2009-00002"));

        propTrial.setOtherIdentifiers(otherIdentifiers);
        propTrial.setSubmissionNumber(Integer.valueOf(1));
        //propTrial.setCtgovXmlRequiredIndicator(Boolean.FALSE);
        TestSchema.addUpdObject(propTrial);
        proprietaryTrialIi = IiConverter.convertToIi(propTrial.getId());

        sc = StudyContactTest.createStudyContactObj(propTrial, c, hcp, crs);
        TestSchema.addUpdObject(sc);

        spc = StudySiteTest.createStudySiteObj(propTrial, hcf);
        spc.setResearchOrganization(ro);
        TestSchema.addUpdObject(spc);

        studyRecStatus = StudyRecruitmentStatusTest.createStudyRecruitmentStatusobj(propTrial);
        TestSchema.addUpdObject(studyRecStatus);

        docWrk = DocumentWorkFlowStatusTest.createDocumentWorkflowStatus(propTrial);
        TestSchema.addUpdObject(docWrk);

        RegistryUser registryUser = new RegistryUser();
        registryUser.setFirstName("firstName");
        registryUser.setLastName("lastName");
        registryUser.setUserLastCreated("Abstractor");
        TestSchema.addUpdObject(registryUser);

        // properties
        PAProperties prop = new PAProperties();
        prop.setName("smtp");
        prop.setValue(smtpHost);
        TestSchema.addUpdObject(prop);

        prop = new PAProperties();
        prop.setName("fromaddress");
        prop.setValue(fromAddress);
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
    }

    @Test (expected=PAException.class)
    public void testSendTSREmail() throws PAException {
        bean.sendTSREmail(nonProprietaryTrialIi);
    }

    @Test
    public void testSendAcceptEmail() throws PAException {
        PAProperties prop = new PAProperties();
        prop.setName("trial.accept.body");
        prop.setValue("${CurrentDate} ${SubmitterName}${nciTrialIdentifier}, ${title} ${leadOrgTrialIdentifier}.");
        TestSchema.addUpdObject(prop);

        prop = new PAProperties();
        prop.setName("trial.accept.subject");
        prop.setValue("trial.accept.subject");
        TestSchema.addUpdObject(prop);

        bean.sendAcceptEmail(nonProprietaryTrialIi);
    }

    @Test
    public void testSendAmendAcceptEmail() throws PAException {
        PAProperties prop = new PAProperties();
        prop.setName("trial.amend.accept.body");
        prop.setValue("${CurrentDate} ${SubmitterName}${nciTrialIdentifier}, ${title}, (${amendmentNumber}), ${amendmentDate}.");
        TestSchema.addUpdObject(prop);


        prop = new PAProperties();
        prop.setName("trial.amend.accept.subject");
        prop.setValue("trial.amend.accept.subject.");
        TestSchema.addUpdObject(prop);

        bean.sendAmendAcceptEmail(nonProprietaryTrialIi);
    }

    @Test
    public void testSendAmendNotificationEmail() throws PAException {
        PAProperties prop = new PAProperties();
        prop.setName("trial.amend.body");
        prop.setValue("${CurrentDate} ${SubmitterName}${nciTrialIdentifier}, ${title}, (${amendmentNumber}), ${amendmentDate}.");
        TestSchema.addUpdObject(prop);

        prop = new PAProperties();
        prop.setName("trial.amend.subject");
        prop.setValue("trial.amend.subject -${leadOrgTrialIdentifier}, ${nciTrialIdentifier}.");
        TestSchema.addUpdObject(prop);

        bean.sendAmendNotificationMail(nonProprietaryTrialIi);
    }

    @Test
    public void testSendAmendRejectEmail() throws PAException {
        PAProperties prop = new PAProperties();
        prop.setName("trial.amend.reject.body");
        prop.setValue("${CurrentDate} ${SubmitterName}${nciTrialIdentifier} ${leadOrgTrialIdentifier}, ${trialTitle}, (${amendmentNumber}), ${amendmentDate},${reasonForRejection}.");
        TestSchema.addUpdObject(prop);

        prop = new PAProperties();
        prop.setName("trial.amend.reject.subject");
        prop.setValue("trial.amend.reject.subject");
        TestSchema.addUpdObject(prop);

        bean.sendAmendRejectEmail(nonProprietaryTrialIi, "rejectReason");
    }

    @Test
    public void testSendNotificationMail() throws PAException {
        PAProperties prop = new PAProperties();
        prop.setName("trial.register.body");
        prop.setValue("${CurrentDate} ${SubmitterName} ${nciTrialIdentifier}, ${leadOrgTrialIdentifier}, ${leadOrgName}, ${trialTitle}.");
        TestSchema.addUpdObject(prop);

        prop = new PAProperties();
        prop.setName("trial.register.subject");
        prop.setValue("trial.register.subject - ${leadOrgTrialIdentifier}, ${nciTrialIdentifier}.");
        TestSchema.addUpdObject(prop);

        bean.sendNotificationMail(nonProprietaryTrialIi);
    }

    @Test
    public void testSendRejectionEmail() throws PAException {
        PAProperties prop = new PAProperties();
        prop.setName("rejection.body");
        prop.setValue("${CurrentDate} ${SubmitterName}${leadOrgTrialIdentifier}, ${trialTitle},${nciTrialIdentifier}, ${receiptDate}, ${reasoncode}.");
        TestSchema.addUpdObject(prop);

        prop = new PAProperties();
        prop.setName("rejection.subject");
        prop.setValue("rejection.subect");
        TestSchema.addUpdObject(prop);

        bean.sendRejectionEmail(nonProprietaryTrialIi);
    }

    @Test (expected=PAException.class)
    public void testSendXmlTSREmail() throws PAException {
        bean.sendXMLAndTSREmail(nonProprietaryTrialIi);
    }

    @Test
    public void testSendUpdateNotificationMail() throws PAException {
        PAProperties prop = new PAProperties();
        prop.setName("trial.update.subject");
        prop.setValue("trial.update.subject ${leadOrgTrialIdentifier}, ${nciTrialIdentifier}.");
        TestSchema.addUpdObject(prop);

        prop = new PAProperties();
        prop.setName("trial.update.body");
        prop.setValue("${CurrentDate} ${SubmitterName}${nciTrialIdentifier}, ${trialTitle}, (${leadOrgTrialIdentifier}).");
        TestSchema.addUpdObject(prop);

        bean.sendUpdateNotificationMail(nonProprietaryTrialIi);
    }

    @Test
    public void testSendNotificationMailProprietary() throws PAException {
        PAProperties prop = new PAProperties();
        prop.setName("proprietarytrial.register.body");
        prop.setValue("${CurrentDate} ${SubmitterName} ${nciTrialIdentifier}, ${leadOrgTrialIdentifier}, ${leadOrgName}, ${trialTitle}, ${subOrgTrialIdentifier}, ${subOrg}.");
        TestSchema.addUpdObject(prop);

        prop = new PAProperties();
        prop.setName("proprietarytrial.register.subject");
        prop.setValue("proprietarytrial.register.subject - ${leadOrgTrialIdentifier}, ${nciTrialIdentifier}.");
        TestSchema.addUpdObject(prop);
        bean.sendNotificationMail(proprietaryTrialIi);
    }

    @Test
    public void testSendMailWithAttachment() throws PAException {
        bean.sendMailWithAttachment(email1, "testSubject", "testBody", null);
    }

    @Test (expected=PAException.class)
    public void testSendTSREmailProprietary() throws PAException {
        bean.sendTSREmail(proprietaryTrialIi);
    }

    private StudyProtocol createProprietaryStudyProtocolObj() {
        StudyProtocol sp = new StudyProtocol();
        java.sql.Timestamp now = new java.sql.Timestamp((new java.util.Date()).getTime());

        sp.setAcronym("Acronym .....");
        sp.setAccrualReportingMethodCode(AccrualReportingMethodCode.ABBREVIATED);
        sp.setDataMonitoringCommitteeAppointedIndicator(Boolean.TRUE);
        sp.setDelayedpostingIndicator(Boolean.TRUE);
        sp.setExpandedAccessIndicator(Boolean.TRUE);
        sp.setFdaRegulatedIndicator(Boolean.TRUE);
        Set<Ii> studySecondaryIdentifiers =  new HashSet<Ii>();
        Ii spSecId = new Ii();
        spSecId.setRoot(IiConverter.STUDY_PROTOCOL_ROOT);
        spSecId.setExtension("NCI-P-2009-00001");
        studySecondaryIdentifiers.add(spSecId);
        sp.setOtherIdentifiers(studySecondaryIdentifiers);
        sp.setKeywordText("keywordText");
        sp.setOfficialTitle("Cancer for kids");
        sp.setPhaseCode(PhaseCode.I);
        sp.setPhaseOtherText("phaseOtherText");
        sp.setPrimaryPurposeCode(PrimaryPurposeCode.BASIC_SCIENCE);
        sp.setPrimaryPurposeOtherText("primaryPurposeOtherText");
        sp.setPrimaryCompletionDate(now);
        sp.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode.ACTUAL);
        sp.setPublicDescription("publicDescription");
        sp.setPublicTitle("publicTitle");
        sp.setRecordVerificationDate(now);
        sp.setScientificDescription("scientificDescription");
        sp.setSection801Indicator(Boolean.TRUE);
        sp.setStartDate(now);
        sp.setStartDateTypeCode(ActualAnticipatedTypeCode.ANTICIPATED);
        sp.setDateLastUpdated(new java.sql.Timestamp((new java.util.Date()).getTime()));
        sp.setUserLastUpdated("Abstractor");
        sp.setDateLastCreated(now);
        sp.setUserLastCreated("Abstractor");
        sp.setStatusCode(ActStatusCode.ACTIVE);
        sp.setAmendmentReasonCode(AmendmentReasonCode.BOTH);
        sp.setStatusDate(now);
        sp.setAmendmentDate(now);
        sp.setAmendmentNumber("amendmentNumber");
        sp.setSubmissionNumber(1);
        sp.setProprietaryTrialIndicator(Boolean.TRUE);
        sp.setCtgovXmlRequiredIndicator(Boolean.FALSE);
        return sp;
    }
}
