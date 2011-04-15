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

package gov.nih.nci.pa.service;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.ClinicalResearchStaff;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.DocumentWorkflowStatus;
import gov.nih.nci.pa.domain.HealthCareFacility;
import gov.nih.nci.pa.domain.HealthCareProvider;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.PAProperties;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.ResearchOrganization;
import gov.nih.nci.pa.domain.StudyContact;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyRecruitmentStatus;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.enums.AccrualReportingMethodCode;
import gov.nih.nci.pa.enums.ActStatusCode;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.AmendmentReasonCode;
import gov.nih.nci.pa.enums.PhaseAdditionalQualifierCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.PrimaryPurposeAdditionalQualifierCode;
import gov.nih.nci.pa.enums.PrimaryPurposeCode;
import gov.nih.nci.pa.enums.StructuralRoleStatusCode;
import gov.nih.nci.pa.iso.dto.PlannedMarkerDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
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
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.TestSchema;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Vrushali
 *
 */
public class MailManagerServiceTest extends AbstractHibernateTestCase {

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
    private static String smtpHost = "";
    private static String fromAddress = "fromAddress@example.com";

    @Before
    public void setUp() throws Exception {
        bean.setCtGovXmlGeneratorService(ctGovXmlSrv);
        bean.setProtocolQueryService(protocolQrySrv);
        bean.setRegistryUserService(registryUserSrv);
        bean.setTsrReportGeneratorService(tsrReptSrv);
        bean.setLookUpTableService(lookUpTableSrv);
        bean.setDocWrkflStatusSrv(docWrkStatSrv);
        bean.setStudySiteService(studySiteService);

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

        Organization o = TestSchema.createOrganizationObj();
        TestSchema.addUpdObject(o);
        Person p = TestSchema.createPersonObj();
        p.setIdentifier("11");
        TestSchema.addUpdObject(p);
        HealthCareFacility hcf = TestSchema.createHealthCareFacilityObj(o);
        TestSchema.addUpdObject(hcf);

        HealthCareProvider hcp = TestSchema.createHealthCareProviderObj(p, o);
        TestSchema.addUpdObject(hcp);

        Country c = TestSchema.createCountryObj();
        TestSchema.addUpdObject(c);

        ClinicalResearchStaff crs = TestSchema.createClinicalResearchStaffObj(o, p);
        TestSchema.addUpdObject(crs);

        ResearchOrganization ro = new ResearchOrganization();
        ro.setOrganization(o);
        ro.setStatusCode(StructuralRoleStatusCode.ACTIVE);
        ro.setIdentifier("abc");
        TestSchema.addUpdObject(ro);

        // Non Prop Trial
        StudyProtocol nonPropTrial = TestSchema.createStudyProtocolObj();
        nonPropTrial.getStudyOwners().add(owner1);
        nonPropTrial.getStudyOwners().add(owner2);
        TestSchema.addUpdObject(nonPropTrial);
        nonProprietaryTrialIi = IiConverter.convertToIi(nonPropTrial.getId());

        StudyContact sc = TestSchema.createStudyContactObj(nonPropTrial, c, hcp, crs);
        TestSchema.addUpdObject(sc);

        StudySite spc = TestSchema.createStudySiteObj(nonPropTrial, hcf);
        spc.setResearchOrganization(ro);
        TestSchema.addUpdObject(spc);
        nonPropTrial.getStudySites().add(spc);

        StudyRecruitmentStatus studyRecStatus = TestSchema.createStudyRecruitmentStatus(nonPropTrial);
        TestSchema.addUpdObject(studyRecStatus);

        DocumentWorkflowStatus docWrk = TestSchema.createDocumentWorkflowStatus(nonPropTrial);
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

        sc = TestSchema.createStudyContactObj(propTrial, c, hcp, crs);
        TestSchema.addUpdObject(sc);

        spc = TestSchema.createStudySiteObj(propTrial, hcf);
        spc.setResearchOrganization(ro);
        TestSchema.addUpdObject(spc);
        propTrial.getStudySites().add(spc);

        studyRecStatus = TestSchema.createStudyRecruitmentStatus(propTrial);
        TestSchema.addUpdObject(studyRecStatus);

        docWrk = TestSchema.createDocumentWorkflowStatus(propTrial);
        TestSchema.addUpdObject(docWrk);

        RegistryUser registryUser = new RegistryUser();
        registryUser.setFirstName("firstName");
        registryUser.setLastName("lastName");
        registryUser.setUserLastCreated(TestSchema.getUser());
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

        prop = new PAProperties();
        prop.setName("CDE_MARKER_REQUEST_BODY");
        prop.setValue("${markerName} ${foundInHugo} ${hugoCodeClause} ${markerTextClause}");
        TestSchema.addUpdObject(prop);

        prop = new PAProperties();
        prop.setName("CDE_MARKER_REQUEST_SUBJECT");
        prop.setValue("CDE Marker Request ${markerName} for ${trialIdentifier}");
        TestSchema.addUpdObject(prop);

        prop = new PAProperties();
        prop.setName("CDE_MARKER_REQUEST_HUGO_CLAUSE");
        prop.setValue("${hugoCode}");
        TestSchema.addUpdObject(prop);

        prop = new PAProperties();
        prop.setName("CDE_MARKER_REQUEST_MARKER_TEXT_CLAUSE");
        prop.setValue("${markerText}");
        TestSchema.addUpdObject(prop);

        prop = new PAProperties();
        prop.setName("CDE_REQUEST_TO_EMAIL");
        prop.setValue("to@example.com");
        TestSchema.addUpdObject(prop);
    }

    @Test (expected=PAException.class)
    public void testSendTSREmail() throws PAException {
        bean.sendTSREmail(nonProprietaryTrialIi);
    }

    @Test
    public void testSendAdminAcceptanceEmail() throws PAException {
        testSendAdminAcceptanceOrRejectionEmail(true, "");
    }

    @Test
    public void testSendAdminRejectionEmail() throws PAException {
        testSendAdminAcceptanceOrRejectionEmail(false, "Reason for Rejection");
        testSendAdminAcceptanceOrRejectionEmail(false, null);
    }

    private void testSendAdminAcceptanceOrRejectionEmail(boolean accept,String reason) throws PAException {
        RegistryUser user = new RegistryUser();
        user.setLastName("LastName");
        user.setFirstName("FirstName");
        user.setEmailAddress(email1);
        user.setAffiliateOrg("Affiliated Organization");
        TestSchema.addUpdObject(user);

        PAProperties prop = new PAProperties();
        prop.setName("trial.admin.accept.subject"); // same property being used for both accept/reject emails.
        prop.setValue("Administration Status Request");
        TestSchema.addUpdObject(prop);

        prop = new PAProperties();
        prop.setValue("Date: ${CurrentDate} -- Name: ${SubmitterName} -- Organization: ${affliateOrgName}");
        if (accept) {
            prop.setName("trial.admin.accept.body");
        } else {
            prop.setName("trial.admin.reject.body");
        }
        TestSchema.addUpdObject(prop);

        if (accept) {
            bean.sendAdminAcceptanceEmail(user.getId());
        } else {
            bean.sendAdminRejectionEmail(user.getId(), reason);
        }
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

    @Test (expected=PAException.class)
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
        bean.sendXMLAndTSREmail(email1, email1, nonProprietaryTrialIi);
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

    @Test (expected=PAException.class)
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

    public void testSendMarkerCDERequestEmail() throws PAException {
        PlannedMarkerDTO dto = new PlannedMarkerDTO();
        dto.setName(StConverter.convertToSt("Marker #1"));
        dto.setHugoBiomarkerCode(CdConverter.convertStringToCd("HUGO"));
        bean.sendMarkerCDERequestMail(proprietaryTrialIi, "from@example.com", dto, "Marker Text");
    }

    private StudyProtocol createProprietaryStudyProtocolObj() {
        StudyProtocol sp = new StudyProtocol();
        Timestamp now = new Timestamp(new Date().getTime());

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
        sp.setPhaseAdditionalQualifierCode(PhaseAdditionalQualifierCode.PILOT);
        sp.setPrimaryPurposeCode(PrimaryPurposeCode.BASIC_SCIENCE);
        sp.setPrimaryPurposeAdditionalQualifierCode(PrimaryPurposeAdditionalQualifierCode.CORRELATIVE);
        sp.setPrimaryCompletionDate(now);
        sp.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode.ACTUAL);
        sp.setPublicDescription("publicDescription");
        sp.setPublicTitle("publicTitle");
        sp.setRecordVerificationDate(now);
        sp.setScientificDescription("scientificDescription");
        sp.setSection801Indicator(Boolean.TRUE);
        sp.setStartDate(now);
        sp.setStartDateTypeCode(ActualAnticipatedTypeCode.ANTICIPATED);
        sp.setDateLastUpdated(new Timestamp(new Date().getTime()));
        sp.setUserLastUpdated(TestSchema.getUser());
        sp.setDateLastCreated(now);
        sp.setUserLastCreated(TestSchema.getUser());
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
