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
package gov.nih.nci.pa.service.util;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.ArmBeanLocal;
import gov.nih.nci.pa.service.ArmServiceLocal;
import gov.nih.nci.pa.service.DiseaseBeanLocal;
import gov.nih.nci.pa.service.DiseaseServiceLocal;
import gov.nih.nci.pa.service.DocumentWorkflowStatusBeanLocal;
import gov.nih.nci.pa.service.DocumentWorkflowStatusServiceLocal;
import gov.nih.nci.pa.service.InterventionAlternateNameServiceBean;
import gov.nih.nci.pa.service.InterventionAlternateNameServiceRemote;
import gov.nih.nci.pa.service.InterventionBeanLocal;
import gov.nih.nci.pa.service.InterventionServiceLocal;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PlannedActivityBeanLocal;
import gov.nih.nci.pa.service.PlannedActivityServiceLocal;
import gov.nih.nci.pa.service.StratumGroupBeanLocal;
import gov.nih.nci.pa.service.StratumGroupServiceLocal;
import gov.nih.nci.pa.service.StudyContactBeanLocal;
import gov.nih.nci.pa.service.StudyContactServiceLocal;
import gov.nih.nci.pa.service.StudyDiseaseBeanLocal;
import gov.nih.nci.pa.service.StudyDiseaseServiceLocal;
import gov.nih.nci.pa.service.StudyIndldeBeanLocal;
import gov.nih.nci.pa.service.StudyIndldeServiceLocal;
import gov.nih.nci.pa.service.StudyOutcomeMeasureBeanLocal;
import gov.nih.nci.pa.service.StudyOutcomeMeasureServiceLocal;
import gov.nih.nci.pa.service.StudyOverallStatusBeanLocal;
import gov.nih.nci.pa.service.StudyOverallStatusServiceLocal;
import gov.nih.nci.pa.service.StudyProtocolBeanLocal;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.StudyRegulatoryAuthorityBeanLocal;
import gov.nih.nci.pa.service.StudyRegulatoryAuthorityServiceLocal;
import gov.nih.nci.pa.service.StudyResourcingBeanLocal;
import gov.nih.nci.pa.service.StudyResourcingServiceLocal;
import gov.nih.nci.pa.service.StudySiteAccrualStatusBeanLocal;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceLocal;
import gov.nih.nci.pa.service.StudySiteBeanLocal;
import gov.nih.nci.pa.service.StudySiteContactBeanLocal;
import gov.nih.nci.pa.service.StudySiteContactServiceLocal;
import gov.nih.nci.pa.service.StudySiteServiceLocal;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceBean;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceRemote;
import gov.nih.nci.pa.service.util.report.AbstractTsrReportGenerator;
import gov.nih.nci.pa.service.util.report.PdfTsrReportGenerator;
import gov.nih.nci.pa.service.util.report.TSRErrorReport;
import gov.nih.nci.pa.service.util.report.TSRReport;
import gov.nih.nci.pa.service.util.report.TSRReportArmGroup;
import gov.nih.nci.pa.service.util.report.TSRReportCollaborator;
import gov.nih.nci.pa.service.util.report.TSRReportDiseaseCondition;
import gov.nih.nci.pa.service.util.report.TSRReportEligibilityCriteria;
import gov.nih.nci.pa.service.util.report.TSRReportGeneralTrialDetails;
import gov.nih.nci.pa.service.util.report.TSRReportHumanSubjectSafety;
import gov.nih.nci.pa.service.util.report.TSRReportIndIde;
import gov.nih.nci.pa.service.util.report.TSRReportIntervention;
import gov.nih.nci.pa.service.util.report.TSRReportInvestigator;
import gov.nih.nci.pa.service.util.report.TSRReportNihGrant;
import gov.nih.nci.pa.service.util.report.TSRReportOutcomeMeasure;
import gov.nih.nci.pa.service.util.report.TSRReportParticipatingSite;
import gov.nih.nci.pa.service.util.report.TSRReportRegulatoryInformation;
import gov.nih.nci.pa.service.util.report.TSRReportStatusDate;
import gov.nih.nci.pa.service.util.report.TSRReportSubGroupStratificationCriteria;
import gov.nih.nci.pa.service.util.report.TSRReportSummary4Information;
import gov.nih.nci.pa.service.util.report.TSRReportTrialDesign;
import gov.nih.nci.pa.service.util.report.TSRReportTrialIdentification;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.TestSchema;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.lowagie.text.DocumentException;


/**
 *
 * @author NAmiruddin, kkanchinadam
 *
 */
public class TSRReportGeneratorServiceTest {

    private final TSRReportGeneratorServiceBean bean = new TSRReportGeneratorServiceBean();

    private final StudyProtocolServiceLocal studyProtocolService = new StudyProtocolBeanLocal();

    StudyOverallStatusServiceLocal studyOverallStatusService = new StudyOverallStatusBeanLocal();

    StudyIndldeServiceLocal studyIndldeService  = new StudyIndldeBeanLocal();

    StudyDiseaseServiceLocal studyDiseaseService = new StudyDiseaseBeanLocal();

    ArmServiceLocal armService = new ArmBeanLocal() ;

    PlannedActivityServiceLocal plannedActivityService = new PlannedActivityBeanLocal();

    StratumGroupServiceLocal subGroupsService = new StratumGroupBeanLocal();

    StudySiteServiceLocal studySiteService = new StudySiteBeanLocal();

    StudySiteContactServiceLocal studySiteContactService = new StudySiteContactBeanLocal();

    StudyContactServiceLocal studyContactService = new StudyContactBeanLocal();

    StudySiteAccrualStatusServiceLocal studySiteAccrualStatusService = new StudySiteAccrualStatusBeanLocal();

    StudyOutcomeMeasureServiceLocal studyOutcomeMeasureService = new StudyOutcomeMeasureBeanLocal();

    StudyRegulatoryAuthorityServiceLocal studyRegulatoryAuthorityService = new StudyRegulatoryAuthorityBeanLocal();

    OrganizationCorrelationServiceRemote ocsr = new OrganizationCorrelationServiceBean();

    DocumentWorkflowStatusServiceLocal documentWorkflowStatusService = new DocumentWorkflowStatusBeanLocal();

    RegulatoryInformationServiceRemote regulatoryInformationService = new RegulatoryInformationBean();

    DiseaseServiceLocal diseaseService = new DiseaseBeanLocal();

    InterventionServiceLocal interventionService = new InterventionBeanLocal();

    InterventionAlternateNameServiceRemote interventionAlternateNameService = new InterventionAlternateNameServiceBean();

    StudyResourcingServiceLocal  studyResourcingService = new StudyResourcingBeanLocal();

    PAOrganizationServiceRemote  paOrganizationService = new PAOrganizationServiceBean();

    @Before
    public void setUp() throws Exception {
        bean.setStudyProtocolService(studyProtocolService);
        bean.setArmService(armService);
        bean.setOcsr(ocsr);
        bean.setPlannedActivityService(plannedActivityService);
        bean.setStudyContactService(studyContactService);
        bean.setStudyDiseaseService(studyDiseaseService);
        bean.setStudyIndldeService(studyIndldeService);
        bean.setStudySiteService(studySiteService);
        bean.setStudyOutcomeMeasureService(studyOutcomeMeasureService);
        bean.setStudyOverallStatusService(studyOverallStatusService);
        bean.setStudySiteContactService(studySiteContactService);
        bean.setStudyRegulatoryAuthorityService(studyRegulatoryAuthorityService);
        bean.setStudySiteAccrualStatusService(studySiteAccrualStatusService);
        bean.setRegulatoryInformationService(regulatoryInformationService);
        bean.setDiseaseService(diseaseService);
        bean.setInterventionAlternateNameService(interventionAlternateNameService);
        bean.setInterventionService(interventionService);
        bean.setStudyResourcingService(studyResourcingService);
        bean.setPaOrganizationService(paOrganizationService);

        TestSchema.reset();
        TestSchema.primeData();
      }

    @Test(expected=PAException.class)
    public void nullParameter() throws Exception {
        bean.generateTsrReport(null);
    }

    @Test
    public void generateTsrReport() throws Exception {
        Ii ii = IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocolIds.get(0));
        ByteArrayOutputStream x = bean.generateTsrReport(ii);
        assertNotNull(x);
        assertTrue(x.size() > 0);
        writeToFile(x, "./tsr_report.pdf");
    }

    @Test
    public void generateProprietaryTsrReportToFile() throws Exception {
        // Setup the data.

        TSRReport tsrReport = new TSRReport("Trial Summary Report", PAUtil.today(), PAUtil.today());
        AbstractTsrReportGenerator pdfTsrReportGenerator = new PdfTsrReportGenerator(tsrReport, true);
        setupDataForPdfTest(pdfTsrReportGenerator);
        // GENERATE PDF
        writeToFile(pdfTsrReportGenerator.generateTsrReport(), "./Proprietary_Tsr_Report.pdf");
    }

    @Test
    public void generateNonProprietaryTsrReportToFile() throws Exception {
        // Setup the data.

        TSRReport tsrReport = new TSRReport("Trial Summary Report", PAUtil.today(), PAUtil.today());
        AbstractTsrReportGenerator pdfTsrReportGenerator = new PdfTsrReportGenerator(tsrReport, false);
        setupDataForPdfTest(pdfTsrReportGenerator);
        // GENERATE PDF
        writeToFile(pdfTsrReportGenerator.generateTsrReport(), "./Non_Proprietary_Tsr_Report.pdf");
    }

    @Test
    public void generateErrorReportToFile() throws Exception {
        // Setup the data.
        TSRErrorReport tsrReport = new TSRErrorReport("Trial Summary Report", "Study ID", "Study Title");
        tsrReport.setErrorType("The exception type goes here.");
        tsrReport.getErrorReasons().add("Reason 1");
        tsrReport.getErrorReasons().add("Reason 2");
        tsrReport.getErrorReasons().add("Reason 3");
        AbstractTsrReportGenerator pdfTsrReportGenerator = new PdfTsrReportGenerator(tsrReport);

        // GENERATE PDF
        writeToFile(pdfTsrReportGenerator.generateErrorReport(), "./Error_Report.pdf");
    }

    private void writeToFile(ByteArrayOutputStream os, String fileName) throws DocumentException, IOException {
        OutputStream fos = new FileOutputStream(fileName);
        os.writeTo(fos);
    }

    private void setupDataForPdfTest(AbstractTsrReportGenerator pdfTsrReportGenerator) throws Exception {
        // Trial Identification Data
        TSRReportTrialIdentification trialIdentification = new TSRReportTrialIdentification();
        trialIdentification.setTrialCategory("Proprietary. UnitŽ de Fabrication et Contr™les Hospitaliers");
        trialIdentification.setNciIdentifier("NCI-2010-00001. This is unnecessary. To see if this spans correctly across \n multiple lines.\n Special Characters: ± ¨ ª >= <= = ³ ²  ­ ");
        trialIdentification.setLeadOrgIdentifier("5AM_NCI");
        trialIdentification.setNctNumber("12345");
        trialIdentification.setDcpIdentifier("5678");
        trialIdentification.setCtepIdentifier("3442323");
        trialIdentification.setAmendmentNumber("7367632746");
        trialIdentification.getOtherIdentifiers().add("OID - 1");
        trialIdentification.getOtherIdentifiers().add("OID - 2");
        trialIdentification.getOtherIdentifiers().add("OID - 3");
        pdfTsrReportGenerator.setTrialIdentification(trialIdentification);

        // General Trial Details Data
        TSRReportGeneralTrialDetails generalTrialDetails = new TSRReportGeneralTrialDetails();
        generalTrialDetails.setOfficialTitle("NPT for 5AM_NCI_1_2_3. lets seee if this spans correctly . bcos this can be (line break) \none or two paragraphs.");
        generalTrialDetails.setBriefTitle("A very brief title for the abstraction validation title.");
        generalTrialDetails.setAcronym("Information not provided");
        generalTrialDetails.setBriefSummary("This is the summary of abstraction validation brief summary.");
        generalTrialDetails.setDetailedDescription("Detailed description. This should be detailed!");
        generalTrialDetails.setKeywords("Certain Keywords");
        generalTrialDetails.setReportingDatasetMethod("Abbreviated");
        generalTrialDetails.setSponsor("Mayo Clinic");
        generalTrialDetails.setLeadOrganization("Mayo Clinic");
        generalTrialDetails.setPi("Steve M. Anderson affiliated with Mayo Clinic");
        generalTrialDetails.setResponsibleParty("Name/Official Title: PartyName, email: example@example.org, 123-222-2222 x9878");
        generalTrialDetails.setOverallOfficial("Steve M. Anderson affiliated with Mayo Clinic in the role of Principal Investigator");
        generalTrialDetails.setCentralContact("Clinical Research Department, email: email@example.org, phone: 232-232-2323");
        pdfTsrReportGenerator.setGeneralTrialDetails(generalTrialDetails);

        // Status Date.
        TSRReportStatusDate statusDate = new TSRReportStatusDate();
        statusDate.setCurrentTrialStatus("Active as of 12/15/2009");
        statusDate.setReasonText("Some Reason");
        statusDate.setTrialStartDate("12/17/2009 - Actual");
        statusDate.setPrimaryCompletionDate("01/28/2010 - Anticipated");
        pdfTsrReportGenerator.setStatusDate(statusDate);

        // Regulatory Information
        TSRReportRegulatoryInformation regulatoryInformation = new TSRReportRegulatoryInformation();
        regulatoryInformation.setTrialOversightAuthority("United States: Food and Drug Administration");
        regulatoryInformation.setDmcAppointed("No");
        regulatoryInformation.setFdaRegulatedIntervention("Yes");
        regulatoryInformation.setSection801("Yes");
        regulatoryInformation.setIndIdeStudy("Yes");
        regulatoryInformation.setDelayedPosting("No");
        pdfTsrReportGenerator.setRegulatoryInformation(regulatoryInformation);

        // Human Subject Study
        TSRReportHumanSubjectSafety humanSubjectSafety = new TSRReportHumanSubjectSafety();
        humanSubjectSafety.setBoardApprovalStatus("Submitted, Pending");
        humanSubjectSafety.setBoardApprovalNumber("Information Not Provided");
        humanSubjectSafety.setBoard("ORGANIZATION Name");
        humanSubjectSafety.setAffiliation("Board Affiliated with NCI, 123 Main Street, Fairfax VA 22033, phone: 000-111-2222, email: email@example.org");
        pdfTsrReportGenerator.setHumanSubjectSafety(humanSubjectSafety);

        // IND/IDES
        List<TSRReportIndIde> indIdes = new ArrayList<TSRReportIndIde>();
        TSRReportIndIde indIde = new TSRReportIndIde();
        indIde.setType("IDE");
        indIde.setGrantor("CDRH");
        indIde.setNumber("125");
        indIde.setHolderType("NIH");
        indIde.setHolder("NIDCR - National Insititute of Dental and Craniofacial Research");
        indIde.setExpandedAccess("Yes");
        indIde.setExpandedAccessStatus("Available");
        indIdes.add(indIde);
        indIde = new TSRReportIndIde();
        indIde.setType("IDE");
        indIde.setNumber("");
        indIde.setHolderType("NIH");
        indIde.setHolder("NIDCR - National Insititute of Dental and Craniofacial Research");
        indIde.setExpandedAccess("Yes");
        indIde.setExpandedAccessStatus("Available");
        indIdes.add(indIde);
        pdfTsrReportGenerator.setIndIdes(indIdes);

        // Nih Grants
        List<TSRReportNihGrant> nihGrants = new ArrayList<TSRReportNihGrant>();
        TSRReportNihGrant nihGrant = new TSRReportNihGrant();
        nihGrant.setFundingMechanism("Z02");
        nihGrant.setNihInstitutionCode("WT");
        nihGrant.setSerialNumber("12345");
        nihGrant.setProgramCode("N/A");
        nihGrants.add(nihGrant);

        nihGrant = new TSRReportNihGrant();
        nihGrant.setFundingMechanism("X98");
        nihGrant.setNihInstitutionCode("BC");
        nihGrant.setSerialNumber("23432423");
        nihGrant.setProgramCode("DCCPS");
        nihGrants.add(nihGrant);
        pdfTsrReportGenerator.setNihGrants(nihGrants);

        // Summary 4 Information
        TSRReportSummary4Information summaryInfo = new TSRReportSummary4Information();
        summaryInfo.setFundingCategory("Industrial");
        summaryInfo.setFundingSponsor("Cancer Therapy Evaluation Program");
        summaryInfo.setProgramCode("Summary4_123");
        pdfTsrReportGenerator.setSummary4Information(summaryInfo);

        // Collaborators
        List<TSRReportCollaborator> collaborators = new ArrayList<TSRReportCollaborator>();
        TSRReportCollaborator colab = new TSRReportCollaborator();
        colab.setName("Fairfax Northern Virginia Hematology and Oncology PC");
        colab.setRole("Funding Source");
        collaborators.add(colab);

        colab = new TSRReportCollaborator();
        colab.setName("George Mason University");
        colab.setRole("Agent Source");
        collaborators.add(colab);

        colab = new TSRReportCollaborator();
        colab.setName("NCI Division of Cancer Prevention");
        colab.setRole("Laboratory");
        collaborators.add(colab);
        pdfTsrReportGenerator.setCollaborators(collaborators);

        // Disease Conditions
        List<TSRReportDiseaseCondition> diseaseConditions = new ArrayList<TSRReportDiseaseCondition>();
        TSRReportDiseaseCondition disease = new TSRReportDiseaseCondition();
        disease.setName("Adult Rhabdomyosarcoma");
        diseaseConditions.add(disease);

        disease = new TSRReportDiseaseCondition();
        disease.setName("Adult Disease 2");
        diseaseConditions.add(disease);
        pdfTsrReportGenerator.setDiseaseConditions(diseaseConditions);

        // Trial Design
        TSRReportTrialDesign trialDesign = new TSRReportTrialDesign();
        trialDesign.setPrimaryPurpose("Treatment");
        trialDesign.setPhase("Pilot");
        trialDesign.setInterventionModel("Single Group");
        trialDesign.setNumberOfArms("2");
        trialDesign.setMasking("Single Blind");
        trialDesign.setMaskedRoles("Subject: No; Investigator: Yes; Caregive: No; Outcomes Assessor: No");
        trialDesign.setAllocation("Randomized Controlled Trial");
        trialDesign.setStudyClassification("Efficacy");
        trialDesign.setTargetEnrollment("2");
        pdfTsrReportGenerator.setTrialDesign(trialDesign);

        // Eligibility Criteria
        TSRReportEligibilityCriteria eligibilityCriteria = new TSRReportEligibilityCriteria();
        eligibilityCriteria.setAcceptsHealthyVolunteers("No");
        eligibilityCriteria.setGender("Male");
        eligibilityCriteria.setMinimumAge("20 Years");
        eligibilityCriteria.setMaximumAge("90 Years");
        eligibilityCriteria.getOtherCriteria().add("Other Criteria 1");
        eligibilityCriteria.getOtherCriteria().add("Other Criteria 2");
        eligibilityCriteria.getOtherCriteria().add("Other Criteria 3");
        eligibilityCriteria.getInclusionCriteria().add("Inclusion Criteria 1");
        eligibilityCriteria.getInclusionCriteria().add("Inclusion Criteria 2");
        eligibilityCriteria.getExclusionCriteria().add("Exclusion Criteria 1");
        eligibilityCriteria.getExclusionCriteria().add("Exclusion Criteria 2");
        pdfTsrReportGenerator.setEligibilityCriteria(eligibilityCriteria);

        // ARM Groups
        List<TSRReportArmGroup> armGroups = new ArrayList<TSRReportArmGroup>();
        TSRReportArmGroup ag = new TSRReportArmGroup();
        ag.setLabel("A1");
        ag.setType("Placebo Comparator");
        ag.setDescription("Description...this is a test");
        TSRReportIntervention armIntervention = new TSRReportIntervention();
        armIntervention.setType("Radiation 1");
        armIntervention.setName("3 Dimensional Conformal Radiation Therapy");
        armIntervention.setAlternateName("3-D CRT, Conformal Radiation");
        armIntervention.setDescription("10.0-50.0 Jcm2, GUM for 23.0 mBq, Every two months, 32 total dose; 23.0-12.0 Unit/mL Approach site; abdomen/pelvis");
        ag.getInterventions().add(armIntervention);

        armIntervention = new TSRReportIntervention();
        armIntervention.setType("Intervention 1");
        armIntervention.setName("Radiation Therapy");
        armIntervention.setAlternateName("3-D CRT intervention 1");
        armIntervention.setDescription("20.0-50.0 Jcm2, GUM for 23.0 mBq, Every two months, 32 total dose; 23.0-12.0 Unit/mL Approach site; Abdomen");
        ag.getInterventions().add(armIntervention);

        armGroups.add(ag);

        ag = new TSRReportArmGroup();
        ag.setLabel("B1");
        ag.setType("Some Comparator Type");
        ag.setDescription("Description...this is a test for Some comparator type");
        armIntervention = new TSRReportIntervention();
        armIntervention.setType("Radiation 2");
        armIntervention.setName("3 Dimensional Conformal Radiation Therapy");
        armIntervention.setAlternateName("3-D CRT, Conformal Radiation");
        armIntervention.setDescription("10.0-50.0 Jcm2, GUM for 23.0 mBq, Every two months, 32 total dose; 23.0-12.0 Unit/mL Approach site; abdomen/pelvis");
        ag.getInterventions().add(armIntervention);

        armIntervention = new TSRReportIntervention();
        armIntervention.setType("Intervention 2");
        armIntervention.setName("Radiation Therapy");
        armIntervention.setAlternateName("3-D CRT intervention 1");
        armIntervention.setDescription("20.0-50.0 Jcm2, GUM for 23.0 mBq, Every two months, 32 total dose; 23.0-12.0 Unit/mL Approach site; Abdomen");
        ag.getInterventions().add(armIntervention);
        armGroups.add(ag);
        pdfTsrReportGenerator.setArmGroups(armGroups);

        // Prop Trial Interventions
        TSRReportIntervention inter = new TSRReportIntervention();
        inter.setType("Intervention 1");
        inter.setName("3 Dimensional Conformal Radiation Therapy");
        inter.setAlternateName("3-D CRT, Conformal Radiation");
        inter.setDescription("10.0-50.0 Jcm2, GUM for 23.0 mBq, Every two months, 32 total dose; 23.0-12.0 Unit/mL Approach site; abdomen/pelvis");
        pdfTsrReportGenerator.getInterventions().add(inter);

        inter = new TSRReportIntervention();
        inter.setType("Intervention 1");
        inter.setName("Radiation Therapy");
        inter.setAlternateName("3-D CRT intervention 1");
        inter.setDescription("20.0-50.0 Jcm2, GUM for 23.0 mBq, Every two months, 32 total dose; 23.0-12.0 Unit/mL Approach site; Abdomen");
        pdfTsrReportGenerator.getInterventions().add(inter);

        // Primary Outcome Measures
        List<TSRReportOutcomeMeasure> primaryOutcomeMeasures = new ArrayList<TSRReportOutcomeMeasure>();
        TSRReportOutcomeMeasure pom = new TSRReportOutcomeMeasure();
        pom.setTitle("Primary Outcome Measure 1");
        pom.setTimeFrame("Primary Time Frame 1");
        pom.setSafetyIssue("yes");
        primaryOutcomeMeasures.add(pom);

        pom = new TSRReportOutcomeMeasure();
        pom.setTitle("Primary Outcome Measure 2");
        pom.setTimeFrame("Primary Time Frame 2");
        pom.setSafetyIssue("no");
        primaryOutcomeMeasures.add(pom);
        pdfTsrReportGenerator.setPrimaryOutcomeMeasures(primaryOutcomeMeasures);

        // Secondary Outcome Measures
        List<TSRReportOutcomeMeasure> secondaryOutcomeMeasures = new ArrayList<TSRReportOutcomeMeasure>();
        TSRReportOutcomeMeasure som = new TSRReportOutcomeMeasure();
        som.setTitle("Secondary Outcome Measure 1");
        som.setTimeFrame("Secondary Time Frame 1");
        som.setSafetyIssue("NO");
        secondaryOutcomeMeasures.add(som);

        som = new TSRReportOutcomeMeasure();
        som.setTitle("Secondary Outcome Measure 2");
        som.setTimeFrame("Secondary Time Frame 2");
        som.setSafetyIssue("YES");
        secondaryOutcomeMeasures.add(som);
        pdfTsrReportGenerator.setSecondaryOutcomeMeasures(secondaryOutcomeMeasures);

        // Sub Groups Stratification Criteria
        List<TSRReportSubGroupStratificationCriteria> sgsCrits = new ArrayList<TSRReportSubGroupStratificationCriteria>();
        TSRReportSubGroupStratificationCriteria crit = new TSRReportSubGroupStratificationCriteria();
        crit.setLabel("Label 1");
        crit.setDescription("This is a description.......");
        sgsCrits.add(crit);

        crit = new TSRReportSubGroupStratificationCriteria();
        crit.setLabel("Label 2");
        crit.setDescription("This is a description for label 2.....");
        sgsCrits.add(crit);
        pdfTsrReportGenerator.setSgsCriterias(sgsCrits);

        // Participating Sites
        List<TSRReportParticipatingSite> participatingSites = new ArrayList<TSRReportParticipatingSite>();
        TSRReportParticipatingSite site = new TSRReportParticipatingSite();
        site.setFacility("Virginia Surgery Associates PC, Fairfax, VA 22033");
        site.setContact("Sarah Guisti, phone: 123-333-2222, email: sarah@example.org");
        site.setRecruitmentStatus("Recruiting as of 12/23/2009");
        site.setTargetAccrual("25");
        site.setClosedForAccrualDate("12/21/2011");
        site.setOpenForAccrualDate("12/01/2009");
        site.setLocalTrialIdentifier("234232334");
        site.getInvestigators().add(new TSRReportInvestigator("Sarah", "M.", "Guisti", "Principal Investigator"));
        site.getInvestigators().add(new TSRReportInvestigator("John", "M.", "Guisti", "PI"));
        site.setProgramCode("SITE1_CODE");
        participatingSites.add(site);

        site = new TSRReportParticipatingSite();
        site.setFacility("MD Eye Associates, Bethesda, MD");
        site.setContact("James Patterson, phone: 222-555-7775, email: james@patterson.org");
        site.setLocalTrialIdentifier("234232334");
        site.setRecruitmentStatus("No Recruitment");
        site.setTargetAccrual("N/A");
        site.setClosedForAccrualDate("01/21/2011");
        site.setOpenForAccrualDate("01/01/2009");
        site.getInvestigators().add(new TSRReportInvestigator("Sarah", "M.", "Guisti", "Principal Investigator"));
        site.getInvestigators().add(new TSRReportInvestigator("John", "M.", "Guisti", "PI"));
        site.setProgramCode("SITE1_CODE_2");
        participatingSites.add(site);
        pdfTsrReportGenerator.setParticipatingSites(participatingSites);
    }
}
